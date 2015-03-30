/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.views;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.controllers.GameStatusController;
import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.models.CentralUnit;
import com.greenit.game.models.RequestType;
import com.greenit.game.models.SlotPlace;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Alexis
 */

public class Slot extends Box {
    private Game game;
    private GreenITApplication app;
    private CaptureZone captureZone;
    private CentralUnitView centralUnit;
    private SlotPlace place;
    private Geometry geom;
    private Material mat;
        
    private float posX;
    private float posY;
    
    private float rightX, leftX, topY, bottomY;
    
    public Slot(Game game, CaptureZone captureZone, SlotPlace place) {
        super(Game.percHSlots*Game.gethFloor(), Game.percHSlots*Game.gethFloor(), 0);
        
        this.app = game.getApp();
        this.captureZone = captureZone;
        this.place = place;
        
        this.geom = new Geometry("Slot", this);
        
        this.mat = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        
        Texture texture = null;
        switch(place) {
            case ALONE :
                texture = GreenITApplication.getCurrentAssetManager().loadTexture("Textures/socket.png");
                break;
            case FIRST :
                texture = GreenITApplication.getCurrentAssetManager().loadTexture("Textures/socketFirst.png");
                break;
            case LAST :
                texture = GreenITApplication.getCurrentAssetManager().loadTexture("Textures/socketLast.png");
                break;
            case MIDDLE :
                texture = GreenITApplication.getCurrentAssetManager().loadTexture("Textures/socketMiddle.png");
                break;
        }
        this.mat.setTexture("ColorMap", texture);
        this.mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        
        this.geom.setMaterial(mat);
    }
    
    public void place(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        
        this.rightX = posX + Game.percHSlots * Game.gethFloor();
        this.leftX = posX - Game.percHSlots * Game.gethFloor();
        
        this.topY = posY + Game.percHSlots * Game.gethFloor();
        this.bottomY = posY - Game.percHSlots * Game.gethFloor();
        
        this.geom.setLocalTranslation(posX, posY, 0.002f);
    }
    
    public void createCentralUnit(CentralUnit centralUnit){
        if(centralUnit == null){
            System.out.println("CentralUnit Bug");
            return;
        }
        
        Message charge = new Message(MessageType.CASHFLOW);
        charge.getModifications().put("amount", -centralUnit.getPrice());
        MessageHandler.sendMessage(charge);
        
        Message envCharge = new Message(MessageType.ENVRATING);
        envCharge.getModifications().put("amount", (int)centralUnit.getEnvTax());
        MessageHandler.sendMessage(envCharge);
                
        this.centralUnit = new CentralUnitView(game, this, centralUnit);
        
        GreenITApplication.getApp().getGame().attachChild(this.centralUnit);
    }
    
    public void destroyCentralUnit(){
        GreenITApplication.getApp().getGame().detachChild(centralUnit);
        this.centralUnit = null;
    }
    
    /*** GETTERS AND SETTERS ***/

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
        this.rightX = posX + Game.percHSlots * Game.gethFloor();
        this.leftX = posX - Game.percHSlots * Game.gethFloor();
        
        this.geom.setLocalTranslation(posX, posY, 0.002f);
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        this.topY = posY + Game.percHSlots * Game.gethFloor();
        this.bottomY = posY - Game.percHSlots * Game.gethFloor();
        
        this.geom.setLocalTranslation(posX, posY, 0.002f);
    }

    public Geometry getGeom() {
        return geom;
    }

    public float getBottomY() {
        return bottomY;
    }

    public float getLeftX() {
        return leftX;
    }

    public float getRightX() {
        return rightX;
    }

    public float getTopY() {
        return topY;
    }

    public CentralUnitView getCentralUnit() {
        return centralUnit;
    }

    public void setCentralUnit(CentralUnitView centralUnit) {
        this.centralUnit = centralUnit;
    }
}
