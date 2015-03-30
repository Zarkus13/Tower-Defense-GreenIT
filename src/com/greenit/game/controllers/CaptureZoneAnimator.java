/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.views.CaptureZone;
import com.jme3.scene.Geometry;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 *
 * @author Alexis
 */
public class CaptureZoneAnimator extends Thread {
    private CaptureZone captureZone;
    private GreenITApplication app;
    
    private boolean alive = true;
    private float dps = 6f; // Distance per second (ici distance de rotation)
    
    private long tbi; // Time between iteration
    private long prevTime;
    
    public CaptureZoneAnimator(CaptureZone captureZone, GreenITApplication app) {
        super(Game.captureZoneThreads, "Another CaptureZoneAnimator");
        
        this.captureZone = captureZone;
        this.app = app;
    }

    @Override
    public void run() {
        Future<Geometry> fut;
        
        while(alive) {
            try {
                tbi = System.currentTimeMillis() - prevTime;
                prevTime = System.currentTimeMillis();
                
                fut = app.enqueue(new Callable<Geometry>() {

                    public Geometry call() throws Exception {
                        rotate();
                        return captureZone.getGeometry();
                    }
                });
                
                Thread.sleep(GreenITApplication.WAITANIM);
            } catch (InterruptedException ex) {
                this.alive = false;
            }
        }
    }
    
    private void rotate() {
        captureZone.getGeometry().rotate(0, 0, (dps * tbi)/1000);
    }
}
