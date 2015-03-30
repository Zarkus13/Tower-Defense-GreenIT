/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.views;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.models.SlotPlace;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author Alexis
 */
public class EnergyProviderSlot extends Node {
    
    private Game game;
    private GreenITApplication app;
    private Box boxEnergyProvider;
    private Geometry geomEnergyProvider;
    private SlotPlace place;
    private Material mat;
    private int numerous;
        
    private float posX;
    private float posY;
    
    private float rightX, leftX, topY, bottomY;
    
    public EnergyProviderSlot(Game game, int numerous) {
        this.numerous = numerous;
        this.boxEnergyProvider = new Box(Vector3f.ZERO, Game.percHSlots*Game.gethFloor(), Game.percHSlots*Game.gethFloor(), 0);
        
        this.app = game.getApp();
        
        this.geomEnergyProvider = new Geometry("EnergyProvider", this.boxEnergyProvider);
        
        this.mat = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        
        Texture texture = GreenITApplication.getCurrentAssetManager().loadTexture("Textures/energyProvider.png");
        this.mat.setTexture("ColorMap", texture);
        this.mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        
        this.geomEnergyProvider.setMaterial(mat);
        
        this.attachChild(this.geomEnergyProvider);
        this.attachChild(this.getTextNumber(numerous));
    }
    
    public void place(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        
        this.rightX = posX + Game.percHSlots * Game.gethFloor();
        this.leftX = posX - Game.percHSlots * Game.gethFloor();
        
        this.topY = posY + Game.percHSlots * Game.gethFloor();
        this.bottomY = posY - Game.percHSlots * Game.gethFloor();
        
        this.setLocalTranslation(posX, posY, 0.002f);
    }
    
    public BitmapText getTextNumber(int numerous) {
        BitmapText text = new BitmapText(GreenITApplication.getCurrentAssetManager().loadFont("Interface/Fonts/Default.fnt"));
        text.setSize(Game.getFontSize());
        text.setColor(ColorRGBA.Blue);
        text.setText(Integer.toString(numerous));
        text.setQueueBucket(Bucket.Transparent);
        
        float width = text.getLineWidth()*Game.getlFloor()/GreenITApplication.getApp().getSettings().getWidth();
        float height = text.getLineHeight()*Game.gethFloor()/GreenITApplication.getApp().getSettings().getHeight();
        text.setLocalTranslation(0, 0, 0);
        
        return text;
    }
    
    
    /*** GETTERS AND SETTERS ***/

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
        this.rightX = posX + Game.percHSlots * Game.gethFloor();
        this.leftX = posX - Game.percHSlots * Game.gethFloor();
        
        this.geomEnergyProvider.setLocalTranslation(posX, posY, 0.002f);
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        this.topY = posY + Game.percHSlots * Game.gethFloor();
        this.bottomY = posY - Game.percHSlots * Game.gethFloor();
        
        this.geomEnergyProvider.setLocalTranslation(posX, posY, 0.002f);
    }

    public Geometry getGeom() {
        return geomEnergyProvider;
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

    public int getNumerous() {
        return numerous;
    }

    public void setNumerous(int numerous) {
        this.numerous = numerous;
    }
}
