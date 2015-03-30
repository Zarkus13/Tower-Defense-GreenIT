/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game;

import com.greenit.game.models.SlotPlace;
import com.greenit.game.views.CaptureZone;
import com.greenit.game.views.Slot;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alexis
 */
public class SlotsGenerator {
    
    private Game game;
    float L = Game.percHSlots*Game.gethFloor();
    private static Material matLiens;
    
    public SlotsGenerator(Game game) {
        this.game = game;
    }
    
    public void placeSlots(int level) {
        int nbTotSlots, nbSlots, resteSlots, nbCurrentSlots;
        int nbCaptureZones = this.game.getCaptureZones().size();
        float posXCurrentSlot, posYCurrentSlot;
        
        Random rand = new Random();
        
        if(level <= 4) 
            nbTotSlots = 3 * this.game.getCaptureZones().size() - 2;
        else
            nbTotSlots = (int) Math.ceil(2*Math.sin(nbCaptureZones) + 10);
        
        nbSlots = nbTotSlots/nbCaptureZones;
        resteSlots = nbTotSlots%nbCaptureZones;
        
        for(CaptureZone cz : this.game.getCaptureZones())
        {       
            if(resteSlots > 0) 
            {
                nbCurrentSlots = nbSlots + 1;
                resteSlots--;
            }
            else
            {
                nbCurrentSlots = nbSlots;
            }
            
            cz.setSlots(new ArrayList<Slot>());
            game.setSlots(new ArrayList<Slot>());
            
            SlotPlace currentPlace;
            
            posYCurrentSlot = rand.nextFloat() * (Game.gethFloor() - Game.percHPath*Game.gethFloor() - 8*L) + Game.percHPath*Game.gethFloor() + 4*L;
            posYCurrentSlot = (cz.getPosY() >= 0 ? posYCurrentSlot : -posYCurrentSlot);
            
            for(int i = 0 ; i < nbCurrentSlots ; i++)
            {
                if(nbCurrentSlots == 1)
                    currentPlace = SlotPlace.ALONE;
                else if(i == 0)
                    currentPlace = SlotPlace.FIRST;
                else if(i == nbCurrentSlots - 1)
                    currentPlace = SlotPlace.LAST;
                else
                    currentPlace = SlotPlace.MIDDLE;

                Slot slot = new Slot(this.game, cz, currentPlace);
                cz.getSlots().add(slot);
                game.getSlots().add(slot);
                
                if(currentPlace == SlotPlace.MIDDLE || currentPlace == SlotPlace.ALONE)
                    posXCurrentSlot = cz.getPosX();
                else if(currentPlace == SlotPlace.FIRST && nbCurrentSlots == 2)
                    posXCurrentSlot = cz.getPosX() - L;
                else if(currentPlace == SlotPlace.FIRST && nbCurrentSlots == 3)
                    posXCurrentSlot = cz.getPosX() - 2*L;
                else if(currentPlace == SlotPlace.LAST && nbCurrentSlots == 2)
                    posXCurrentSlot = cz.getPosX() + L;
                else if(currentPlace == SlotPlace.LAST && nbCurrentSlots == 3)
                    posXCurrentSlot = cz.getPosX() + 2*L;
                else
                    posXCurrentSlot = cz.getPosX();
                
                this.game.attachChild(slot.getGeom());
                slot.place(posXCurrentSlot, posYCurrentSlot);
            }
            
            checkColisionSlots(cz);
            makeLinkCZSlots(cz);
        }
    }
    
    public void checkColisionSlots(CaptureZone captureZone) {
        float posXMin, posXMax;
        float posXMinCZ, posXMaxCZ;
        float posY, posYCZ;
        
        posXMin = captureZone.getSlots().get(0).getPosX();
        posXMax = captureZone.getSlots().get(captureZone.getSlots().size() - 1).getPosX();
        posY = captureZone.getSlots().get(0).getPosY(); 
        
        for(CaptureZone cz : this.game.getCaptureZones()) {
            if(cz != captureZone && cz.getSlots().size() > 0)
            {
                posXMinCZ = cz.getSlots().get(0).getPosX();
                posXMaxCZ = cz.getSlots().get(cz.getSlots().size() - 1).getPosX();
                posYCZ = cz.getSlots().get(0).getPosY(); 

                if(
                    (
                        (posXMin - L <= posXMaxCZ + L && posXMin - L >= posXMinCZ - L) ||
                        (posXMax + L <= posXMaxCZ + L && posXMax + L >= posXMinCZ - L) ||
                        (posXMinCZ - L <= posXMax + L && posXMinCZ - L >= posXMin - L) ||
                        (posXMaxCZ + L <= posXMax + L && posXMaxCZ + L >= posXMin - L)
                    ) 
                    &&
                    (
                        (posY + L <= posYCZ + L && posY + L >= posYCZ - L) ||
                        (posY - L <= posYCZ + L && posY - L >= posYCZ - L)
                    )
                )
                {
                    for(Slot slot : captureZone.getSlots()) {
                        slot.setPosY(-slot.getPosY());
                    }
                }
            }
        }
    }
    
    public void makeLinkCZSlots(CaptureZone cz) {
        float heightLink = (cz.getPosY() - cz.getSlots().get(0).getPosY())/2;
        heightLink = (heightLink > 0 ? heightLink : -heightLink);
        
        Box box = new Box(Game.gethFloor()*0.02f, heightLink, 0);
        Geometry geom = new Geometry("Link", box);
        geom.setMaterial(getMatLiens());
        
        geom.setLocalTranslation(cz.getPosX(), (cz.getPosY() + cz.getSlots().get(0).getPosY())/2, 0.0001f);
        
        this.game.attachChild(geom);
    }

    public static Material getMatLiens() {
        if(matLiens == null) {
            matLiens = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            matLiens.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/LienZCSlots.png"));
            matLiens.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        }
        return matLiens;
    }
}
