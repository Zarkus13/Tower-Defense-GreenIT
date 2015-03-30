/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.views;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.controllers.CaptureZoneAnimator;
import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.models.Request;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Alexis
 */
public class CaptureZone extends Node {

    private GreenITApplication app;
    private float radius;
    private float posX;
    private float posY;
    private static Float radiusMax;
    private static Float radiusMin;
    private Box box;
    private Geometry geometry;
    private static Material material;
    private CaptureZoneAnimator animator;
    private List<Slot> slots;
    private String name;

    // Initialisation du Material des zone de captures
    static {
        material = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        material.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/TextureCaptureZone.png"));
        material.setColor("Color", ColorRGBA.Cyan);
    }

    public CaptureZone(GreenITApplication app) {
        this.app = app;

        Random rand = new Random();

        name="CZ"+System.currentTimeMillis();
        
        this.radius = rand.nextFloat() * (CaptureZone.getRadiusMax() - CaptureZone.getRadiusMin()) + CaptureZone.getRadiusMin();

        posX = rand.nextFloat() * (Game.getlFloor() - (radius + radius * 5 / 100));
        posX = (rand.nextBoolean() ? posX : -posX);

        posY = rand.nextFloat() * (Game.gethFloor() * Game.percHPath - radius);
        posY = (rand.nextBoolean() ? posY : -posY);

        initCaptureZone();
    }

    public CaptureZone(GreenITApplication app, float diameter, float posX, float posY) {
        super();
        this.app = app;
        this.radius = diameter;
        this.posX = posX;
        this.posY = posY;

        initCaptureZone();
    }

    private void initCaptureZone() {
        this.setLocalTranslation(posX, posY, 0.002f);
        this.box = new Box(Vector3f.ZERO, radius, radius, 0);

        this.geometry = new Geometry("CaptureZone" + new Random().nextInt(), this.box);
        this.geometry.setQueueBucket(Bucket.Transparent);
        this.geometry.setMaterial(material);

        this.attachChild(this.geometry);

        this.getAnimator().start();
    }

    public boolean handleColision(Request request){
        boolean handled = false;
        
        for(Slot slot : slots){
            if(slot.getCentralUnit() != null){
                if(slot.getCentralUnit().getController() != null){
                handled = slot.getCentralUnit().getController().handleRequest(request);
                    if(handled == true){
                       Message payMsg = new Message(MessageType.CASHFLOW);
                       payMsg.getModifications().put("amount", request.getAward());
                       MessageHandler.sendMessage(payMsg); 
//                       System.out.println("CZ "+name+" SLOT "+slots.indexOf(slot)+"ADDRESS"+slot+
//                               " UC Name : "+slot.getCentralUnit().getCentralUnit().getModel()+
//                               " UC Type : "+slot.getCentralUnit().getCentralUnit().getRequestType());
                       return true;
                   }
                }
            }
        }
        return handled;
    }
    
    /*** GETTERS AND SETTERS ***/
    public Geometry getGeometry() {
        return geometry;
    }

    public static Material getMaterial() {
        return material;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;

        this.setLocalTranslation(posX, posY, 0.0002f);
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;

        this.setLocalTranslation(posX, posY, 0.0002f);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;

        this.geometry.setMesh(new Box(Vector3f.ZERO, radius, radius, 0));
    }

    public static Float getRadiusMax() {
        if (radiusMax == null) {
            radiusMax = Game.gethFloor() * (Game.percHPath * 0.75f);
        }

        return radiusMax;
    }

    public static Float getRadiusMin() {
        if (radiusMin == null) {
            radiusMin = Game.gethFloor() * (Game.percHPath * 0.35f);
        }

        return radiusMin;
    }

    public static void setRadiusMax(Float radiusMax) {
        CaptureZone.radiusMax = radiusMax;
    }

    public static void setRadiusMin(Float radiusMin) {
        CaptureZone.radiusMin = radiusMin;
    }

    public CaptureZoneAnimator getAnimator() {
        if (this.animator == null) {
            this.animator = new CaptureZoneAnimator(this, this.app);
        }
        return animator;
    }

    public List<Slot> getSlots() {
        if (this.slots == null) {
            this.slots = new ArrayList<Slot>();
        }
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
}
