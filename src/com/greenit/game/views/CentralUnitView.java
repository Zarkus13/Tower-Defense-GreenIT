/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.views;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.controllers.CentralUnitController;
import com.greenit.game.models.CentralUnit;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Alexis
 */
public class CentralUnitView extends Node {
    private Game game;
    private GreenITApplication app;
    private CentralUnit centralUnit;
    private CentralUnitController controller;
    private Slot slot;
    
    private Box boxCentralUnit;
    private Geometry geomCentralUnit;
    private Material matCentralUnit;
    
    private Box boxCharge;
    private Geometry geomCharge;
    private Material matCharge;
    
    public CentralUnitView(Game game, Slot slot, CentralUnit centralUnit ) {        
        this.centralUnit = centralUnit;
        this.game = game;
        this.slot = slot;
        
        this.boxCentralUnit = new Box(Game.percHSlots*Game.gethFloor(), Game.percHSlots*Game.gethFloor(), 0);
        this.boxCharge = new Box(Game.percHSlots*Game.gethFloor(), Game.percHRequests*Game.gethFloor()/2, 0);
        
        this.attachChild(this.getGeomCentralUnit());
        this.attachChild(this.getGeomCharge());
        
        controller = new CentralUnitController(centralUnit, this);
        controller.start();
        
        Vector3f vector = this.slot.getGeom().getLocalTranslation();
        vector.setZ(0.01f);
        this.setLocalTranslation(vector);
    }
    
    public void updateCharge() {
        float maxWidth = Game.percHSlots*Game.gethFloor() - Game.percHRequests*Game.gethFloor();
        float loadPerc = (this.centralUnit.getCurrentLoad()/centralUnit.getMaxLoad());
        float width = maxWidth * loadPerc;

        System.out.println("Load Percentage =  "+loadPerc);
        
        this.geomCharge.setMesh(new Box(width, Game.percHRequests*Game.gethFloor()/2, 0));
        this.geomCharge.setLocalTranslation(-(maxWidth - width), -Game.percHSlots*Game.gethFloor(), 0);
    }

    
    /*** GETTERS AND SETTERS ***/
    
    public Geometry getGeomCentralUnit() {
        if(this.geomCentralUnit == null) {
            this.geomCentralUnit = new Geometry("A central unit", this.boxCentralUnit);
            this.geomCentralUnit.setMaterial(this.getMatCentralUnit());
            this.geomCentralUnit.setQueueBucket(RenderQueue.Bucket.Transparent);
            this.geomCentralUnit.setLocalTranslation(Vector3f.ZERO);
        }
        return geomCentralUnit;
    }

    public Material getMatCentralUnit() {
        if(this.matCentralUnit == null) {
            this.matCentralUnit = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            this.matCentralUnit.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/centralUnit"+
                    centralUnit.getRequestType().toString().toUpperCase()+".png"));
            this.matCentralUnit.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matCentralUnit;
    }

    public Geometry getGeomCharge() {
        if(this.geomCharge == null) {
            this.geomCharge = new Geometry("Charge", this.boxCharge);
            this.geomCharge.setMaterial(this.getMatCharge());
            this.geomCharge.setQueueBucket(RenderQueue.Bucket.Transparent);
            this.geomCharge.setLocalTranslation(0, -Game.percHSlots*Game.gethFloor(), 0);
           
            
            float maxWidth = Game.percHSlots*Game.gethFloor() - Game.percHRequests*Game.gethFloor();
            float width = 0 ;

            this.geomCharge.setMesh(new Box(width, Game.percHRequests*Game.gethFloor()/2, 0));
            this.geomCharge.setLocalTranslation(-(maxWidth - width), -Game.percHSlots*Game.gethFloor(), 0);
        }
        return geomCharge;
    }

    public Material getMatCharge() {
        if(this.matCharge == null) {
            this.matCharge = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            this.matCharge.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/UCCharge.png"));
            this.matCharge.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
            this.matCharge.setColor("Color", ColorRGBA.Green);
        }
        return matCharge;
    }

    public Slot getSlot() {
        return slot;
    }

    public CentralUnitController getController() {
        return controller;
    }

    public void setController(CentralUnitController controller) {
        this.controller = controller;
    }
    
    public CentralUnit getCentralUnit(){
        return centralUnit;
    }
}
