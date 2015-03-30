/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game;

import com.greenit.game.views.CaptureZone;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Alexis
 */
public class CaptureZonesGenerator {
    
    private Game game;
    private List<CaptureZone> captureZones;
    private LinkedList<Float[]> blankedZones;
    
    public CaptureZonesGenerator(Game game) {
        this.game = game;
    }
    
    /*************************************************************************
     * Place les zones de capture sur le chemin en fonction du niveau de la 
     * partie.
     * @param level 
     ************************************************************************/
    public void placeCaptureZones(int level) {
        int number;
        
        if(level <= 4)
            number = (int) Math.floor(level);
        else
            number = (int) Math.round(2*Math.sin(level) + 6);
        
        captureZones = new ArrayList<CaptureZone>();

        blankedZones = new LinkedList<Float[]>();
        
        float widthStrips = 2*Game.getlFloor()/number;
        float posX, posY, radius;
        Random rand = new Random();

        for (int i = 1; i <= number; i++) {
            CaptureZone cz = new CaptureZone(this.game.getApp());
            
            radius = CaptureZone.getRadiusMin();
            
            posX = -Game.getlFloor() + i*widthStrips - widthStrips/2;
            
            posY = rand.nextFloat() * (Game.gethFloor() * Game.percHPath - radius);
            posY = (rand.nextBoolean() ? posY : -posY);
            
            cz.setRadius(radius);
            cz.setPosX(posX);
            cz.setPosY(posY);

            captureZones.add(cz);
            this.game.attachChild(cz);
        }
        
        this.resizeCaptureZones();
        this.game.setCaptureZones(captureZones);
        buildCaptureZonesStrips();
    }
    
    /**************************************************************************
     * MÃ©thode servant Ã  dÃ©finir le rayon de chaque zone de capture en fonction
     * de son placement et de sa distance par rapport aux autres zones de
     * capture et des bords
     *************************************************************************/
    public void resizeCaptureZones() {
        float lowerPos = Game.gethFloor(), higherPos = Game.gethFloor();
        int idLower = 0, idHigher = 0;
        float radiusMax;
        float x1, x2, y1, y2;
        int id;
        
        for(CaptureZone cz : captureZones) {
            radiusMax = 1000;
            id = captureZones.indexOf(cz);
            
            x1 = cz.getPosX();
            y1 = cz.getPosY();
            
            // Check distance avec ZC prÃ©cÃ©dente, ou bord gauche
            x2 = (id > 0 ? captureZones.get(id - 1).getPosX() : -Game.getlFloor());
            y2 = (id > 0 ? captureZones.get(id - 1).getPosY() : y1);
            
            radiusMax = this.checkRadiusMax(radiusMax, x1, x2, y1, y2);
            
            // Check distance avec ZC suivante, ou bord droit
            x2 = (id < captureZones.size() - 1 ? captureZones.get(id + 1).getPosX() : Game.getlFloor());
            y2 = (id < captureZones.size() - 1 ? captureZones.get(id + 1).getPosY() : y1);
            
            radiusMax = this.checkRadiusMax(radiusMax, x1, x2, y1, y2);
            
            // Check distance avec bord bas
            x2 = x1;
            y2 = Game.getBotPath();
            
            radiusMax = this.checkRadiusMax(radiusMax, x1, x2, y1, y2);
            
            // Check distance avec bord haut
            x2 = x1;
            y2 = Game.getTopPath();
            
            radiusMax = this.checkRadiusMax(radiusMax, x1, x2, y1, y2);
            
            cz.setRadius(radiusMax);
            
            // DÃ©finit quelle ZC est la plus basse, et laquelle est la plus haute
            if(cz.getPosY() - cz.getRadius() < lowerPos)
                idLower = captureZones.indexOf(cz);
            if(cz.getPosY() + cz.getRadius() > higherPos && captureZones.indexOf(cz) != idLower)
                idHigher = captureZones.indexOf(cz);
        }
        
        // On place la plus basse tout en bas du chemin, et la plus haute tout en haut
        captureZones.get(idLower).setPosY(Game.getBotPath() + captureZones.get(idLower).getRadius());
        captureZones.get(idHigher).setPosY(Game.getTopPath() - captureZones.get(idHigher).getRadius());
        
        // Sert pour un nombre de zones de capture Ã©gal Ã  2, afin de bien couvrir tout le chemin
        if(captureZones.size() == 2 && (captureZones.get(0).getRadius()*2 + captureZones.get(1).getRadius()*2 < Game.getTopPath() - Game.getBotPath()))
        {
            CaptureZone cz = captureZones.get(1);
            cz.setRadius(Game.getTopPath() - Game.getBotPath() - captureZones.get(0).getRadius());
            
            if(cz.getPosY() + cz.getRadius() > Game.getTopPath())
                cz.setPosY(Game.getTopPath() - cz.getRadius());
            else if(cz.getPosY() - cz.getRadius() < Game.getBotPath())
                cz.setPosY(Game.getBotPath() + cz.getRadius());
        }
    }
    
    /**************************************************************************
     * Sert Ã  spÃ©cifier le rayon maximum pour une zone de capture
     * @param radiusMax
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @return Le rayon maximum pour la ZC courante
     *************************************************************************/
    private float checkRadiusMax(float radiusMax, float x1, float x2, float y1, float y2) {
        float currentDist;
        
        if(y2 == Game.getTopPath() || y2 == Game.getBotPath())
            currentDist = (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
        else
            currentDist = (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2))/2;
        
        return (currentDist < radiusMax ? currentDist : radiusMax);
    }
    
    public void buildCaptureZonesStrips() {
        ArrayList<Float[]> captureZonesStrips = new ArrayList<Float[]>();
        
        for(int i = 1 ; i <= this.game.getCaptureZones().size() ; i++) {
            CaptureZone cz = this.game.getCaptureZones().get(this.game.getCaptureZones().size() - i);
            
            captureZonesStrips.add(new Float[] { (cz.getPosX() + cz.getRadius()), (cz.getPosX() - cz.getRadius()) });
        }
        
        this.game.setCaptureZonesStrips(captureZonesStrips);
    }
}
