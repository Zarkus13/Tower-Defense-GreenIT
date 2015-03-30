/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.models.Request;
import com.greenit.game.views.CaptureZone;
import com.greenit.game.views.RequestCatchedParticles;
import com.greenit.game.views.RequestView;
import com.jme3.effect.ParticleEmitter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 *
 * @author Oli
 */
public class RequestController extends Thread {
    
    private RequestView requestView;
    private Request request;
    private GreenITApplication app;
    private Game game;
    
    private boolean alive = true;
    private boolean catched = false;
    private float dpsRotation = 3f; // Distance per second (ici distance de rotation)
    private float dpsMove = 0.5f; // Distance per second (ici distance de déplacement)
    
    private long tbi; // Time between iteration
    private long prevTime;
    
    private int lastCZToCheck = 0;
    private ArrayList<Float[]> captureZonesStrips;
    private CaptureZone currentCZToCheck;
    private float xR, xCZ, yR, yCZ, rR, rCZ;
    private Vector3f lastPosition;
    
    private ParticleEmitter catchedParticles;
    
    public RequestController(RequestView requestView, Game game, Request request) {
        super(Game.requestThreads, "Another request");
        
        this.requestView = requestView;
        this.request = request;
        this.game = game;
        this.app = this.game.getApp();
        this.captureZonesStrips = this.game.getCaptureZonesStrips();
        
        this.rR = Game.gethFloor()*Game.percHRequests;
    }

    @Override
    public synchronized void run() {
        while(alive) {          
            try {
                
                tbi = System.currentTimeMillis() - prevTime;
                prevTime = System.currentTimeMillis();

                Future<Geometry> fut = this.app.enqueue(new Callable<Geometry>() {

                    public Geometry call() throws Exception {
                        if(!catched)
                            rotate();
                        
                        move();
                        
                        if(!catched)
                            checkColisionCZ();
                        return requestView.getFirstSpatial();
                    }
                });
                
                Thread.sleep(GreenITApplication.WAITANIM);
            } catch(InterruptedException ex) {
                this.alive = false;
            }
        }
    }
    
    /*************************************************************************
     * Rotation des deux parties de la requête
     ************************************************************************/
    private void rotate() {
        requestView.getFirstSpatial().rotate(0, 0, (dpsRotation * tbi)/1000);
        requestView.getSecondSpatial().rotate(0, 0, -(dpsRotation * tbi)/1000);
    }
    
    
    /*************************************************************************
     * (à faire)
     * @param failed 
     ************************************************************************/
    private void disableRequest(boolean failed){
        alive = false;
        Message detachMsg = new Message(MessageType.DETACH);
        detachMsg.getModifications().put("objectToDetach", requestView);
        MessageHandler.sendMessage(detachMsg);
        if(failed){
            Message failMsg = new Message(MessageType.FAILED_REQUEST);
            failMsg.getModifications().put("type", request.getType());
            MessageHandler.sendMessage(failMsg);
        }
    }
    
    
    /*************************************************************************
     * Déplacement de la requête en fonction de son importance
     ************************************************************************/
    private void move() {
        Vector3f pos = this.requestView.getLocalTranslation();
        float speed = (request.getImportance() * dpsMove)/2;
        
        if(!catched)
        {
            pos.x -= speed*0.01;

            if(pos.x < -Game.getlFloor()){
                disableRequest(true);
                return;
            }
        }
        else
        {
            short sens = (short) (lastPosition.y > pos.y ? 1 : -1);
            
            pos.y += sens*speed*0.01;
            
            if(sens*pos.y >= sens*lastPosition.y)
            {
                alive = false;
                game.detachChild(requestView);
                
                catchedParticles.killAllParticles();
                catchedParticles.removeFromParent();
            }
        }
        
        this.requestView.setLocalTranslation(pos);
    }
    
    
    /*************************************************************************
     * Gestion de la colision de la requête avec une zone de capture
     ************************************************************************/
    private void checkColisionCZ() {
        float distance;
        
        xR = requestView.getLocalTranslation().x;
        yR = requestView.getLocalTranslation().y;
        
        for(int i = lastCZToCheck ; i < captureZonesStrips.size() ; i++) {
            if((xR - rR/2) <= captureZonesStrips.get(i)[0] && (xR - rR/2) > captureZonesStrips.get(i)[1])
            {
                currentCZToCheck = game.getCaptureZones().get(game.getCaptureZones().size() - 1 - i);
                
                xCZ = currentCZToCheck.getPosX();
                yCZ = currentCZToCheck.getPosY();
                rCZ = currentCZToCheck.getRadius();
                
                distance = (float) Math.sqrt((xR - xCZ) * (xR - xCZ) + (yR - yCZ) * (yR - yCZ));
                
                if(distance < (rCZ + rR/2))
                {
                    catched = currentCZToCheck.handleColision(request);

                    if(catched)
                    {
                        this.emitCatchedParticles();
                        
                        this.requestView.setLocalTranslation(currentCZToCheck.getLocalTranslation());
                        this.requestView.getSecondSpatial().removeFromParent();
                        this.requestView.getFirstSpatial().setMaterial(RequestView.getMatCatched());
                        this.requestView.getFirstSpatial().setQueueBucket(Bucket.Transparent);
                        this.requestView.getFirstSpatial().setMesh(new Box(Game.percHRequests*Game.gethFloor()/2, Game.percHRequests*Game.gethFloor()/2, 0));
                        
                        this.lastPosition = currentCZToCheck.getLocalTranslation().clone();
                        this.lastPosition.setY(currentCZToCheck.getSlots().get(0).getPosY());
                    }
                    break;
                }
                
                lastCZToCheck = i;
            }
            
            if((xR - rR/2) > captureZonesStrips.get(i)[0])
            {
                break;
            }
        }
    }

    public ParticleEmitter emitCatchedParticles() {
        if(catchedParticles == null) {
            catchedParticles = new RequestCatchedParticles((ColorRGBA) this.requestView.getFirstSpatial().getMaterial().getParam("Color").getValue());
        }
        
        game.attachChild(catchedParticles);
        catchedParticles.setLocalTranslation(this.requestView.getLocalTranslation().clone());
        catchedParticles.getLocalTranslation().setZ(0.1f);
        catchedParticles.emitAllParticles();
        
        return catchedParticles;
    }
}
