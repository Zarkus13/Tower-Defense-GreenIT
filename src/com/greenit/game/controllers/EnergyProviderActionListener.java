/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.gui.InGameMenu;
import com.greenit.game.views.EnergyProviderSlot;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author Alexis
 */
public class EnergyProviderActionListener implements ActionListener {
    
    private Game game;
    private float mouseX, mouseY;
    private float mouseXFloat, mouseYFloat;
    
    public EnergyProviderActionListener(Game game) {
        this.game = game;
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed)
        {
            mouseX = game.getApp().getInputManager().getCursorPosition().x;
            mouseY = game.getApp().getInputManager().getCursorPosition().y;

            mouseXFloat = (2*Game.getlFloor() * mouseX)/game.getApp().getSettings().getWidth() - Game.getlFloor();
            mouseYFloat = (2*Game.gethFloor() * mouseY)/game.getApp().getSettings().getHeight() - Game.gethFloor();

            Ray ray = new Ray(new Vector3f(mouseXFloat, mouseYFloat, 1), game.getApp().getCamera().getDirection());
            CollisionResults results = new CollisionResults();
            this.game.collideWith(ray, results);
            
            if(results.size() > 0)
            {
                Geometry clicked = results.getClosestCollision().getGeometry();
                Node energyProvider = clicked.getParent();
                
                if(energyProvider instanceof EnergyProviderSlot){
                    System.out.println("Energy Provider clicked !");
                    InGameMenu.getCurrentInstance().openEnergyProviderSelectionMenu((EnergyProviderSlot) energyProvider);
                }
            }
        }
    }
    
}
