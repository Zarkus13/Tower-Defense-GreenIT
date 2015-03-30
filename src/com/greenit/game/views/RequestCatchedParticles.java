/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.views;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 *
 * @author Alexis
 */
public class RequestCatchedParticles extends ParticleEmitter {
    
    private static Material matParticles;
        
    public RequestCatchedParticles(ColorRGBA color) {
        super("Request catched", Type.Triangle, 10);
        
        this.setMaterial(RequestCatchedParticles.getMat());
        this.setStartColor(color);
        this.setEndColor(ColorRGBA.White);
        this.setInitialVelocity(new Vector3f(0, 1, 0));
        this.setVelocityVariation(1);
        this.setParticlesPerSec(0);
        this.setStartSize(Game.percHRequests*Game.gethFloor()/3);
        this.setEndSize(Game.percHRequests*Game.gethFloor()/3);
        this.setLowLife(1);
        this.setHighLife(1);
        this.setRotateSpeed(10);
    }
    
    private static Material getMat() {
        if(matParticles == null) {
            matParticles = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
            matParticles.setTexture("Texture", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/requestCatchedParticle.png"));
        }
        return matParticles;
    }
}
