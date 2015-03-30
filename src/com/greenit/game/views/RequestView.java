/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.views;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.controllers.RequestController;
import com.greenit.game.models.Request;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.Random;

/**
 *
 * @author Alexis
 */
public class RequestView extends Node {
    private GreenITApplication app;
    private Game game;
    
    private Request request;
    private RequestController controller;
    
    private float posX;
    private float posY;
    private float diameter;
        
    private Box box;
    private Geometry firstSpatial;
    private Geometry secondSpatial;
    private Material mat;
    
    private static Material matHTTP;
    private static Material matFTP;
    private static Material matDNS;
    private static Material matIMAP;
    private static Material matSIP;
    private static Material matSQL;
    private static Material matCatched;
    
    public RequestView(GreenITApplication app) {
        this.app = app;
        this.initRequestView();
    }
    
    public RequestView(Game game, Request request) {
        super("request");
        this.game = game;
        this.app = this.game.getApp();
        this.request = request;
        this.initRequestView();
    }
    
    private void initRequestView() {
        
        Random rand = new Random();

        this.diameter = Game.gethFloor()*Game.percHRequests;
        
        this.posX = Game.getlFloor() + diameter;
        this.posY = rand.nextFloat() * (Game.gethFloor() * Game.percHPath);
        this.posY = (rand.nextBoolean() ? posY : -posY);
        
        box = new Box(new Vector3f(0,0,0), this.diameter, this.diameter, 0);

        this.firstSpatial = new Geometry("FirstSpatial", box);
        this.secondSpatial = new Geometry("SecondSpatial", box);
        
        switch(request.getType()){
            case HTTP :
                this.mat = RequestView.getMatHTTP();
                break;
           case FTP :
               this.mat = RequestView.getMatFTP();
               break;
           case DNS :
               this.mat = RequestView.getMatDNS();
               break;
           case IMAP :
               this.mat = RequestView.getMatIMAP();
               break;
           case SIP :
               this.mat = RequestView.getMatSIP();
               break;
           case SQL :
               this.mat = RequestView.getMatSQL();
               break;
        }
        
        // Ajout pour la transparence, mais bug un peu
//        this.firstSpatial.setQueueBucket(Bucket.Transparent);
//        this.secondSpatial.setQueueBucket(Bucket.Transparent);
        
        this.firstSpatial.setMaterial(mat);
        this.secondSpatial.setMaterial(mat);
                
        this.setLocalTranslation(this.posX, this.posY, 0.0001f);
        
        this.attachChild(this.firstSpatial);
        this.attachChild(this.secondSpatial);
        
        this.controller = new RequestController(this, this.game, request);
        this.controller.start();
    }

    public RequestController getController() {
        return controller;
    }

    public Geometry getFirstSpatial() {
        return firstSpatial;
    }

    public Request getRequest() {
        return request;
    }

    public Geometry getSecondSpatial() {
        return secondSpatial;
    }

    public void setController(RequestController controller) {
        this.controller = controller;
    }

    public static Material getMatDNS() {
        if(matDNS == null) {
            matDNS = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            matDNS.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/request.png"));
            matDNS.setColor("Color", ColorRGBA.Blue);
            matDNS.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matDNS;
    }

    public static Material getMatFTP() {
        if(matFTP == null) {
            matFTP = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            matFTP.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/request.png"));
            matFTP.setColor("Color", ColorRGBA.Red);
            matFTP.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matFTP;
    }

    public static Material getMatHTTP() {
        if(matHTTP == null) {
            matHTTP = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            matHTTP.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/request.png"));
            matHTTP.setColor("Color", ColorRGBA.Green);
            matHTTP.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matHTTP;
    }

    public static Material getMatIMAP() {
        if(matIMAP == null) {
            matIMAP = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            matIMAP.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/request.png"));
            matIMAP.setColor("Color", ColorRGBA.Yellow);
            matIMAP.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matIMAP;
    }

    public static Material getMatSIP() {
        if(matSIP == null) {
            matSIP = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            matSIP.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/request.png"));
            matSIP.setColor("Color", ColorRGBA.Magenta);
            matSIP.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matSIP;
    }

    public static Material getMatSQL() {
        if(matSQL == null) {
            matSQL = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            matSQL.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/request.png"));
            matSQL.setColor("Color", ColorRGBA.Cyan);
            matSQL.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matSQL;
    }

    public static Material getMatCatched() {
        if(matCatched == null) {
            matCatched = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            matCatched.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/sphereRequestCatched.png"));
            matCatched.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matCatched;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }
}
