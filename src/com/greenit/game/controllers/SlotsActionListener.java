/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.gui.GreenItGui;
import com.greenit.game.gui.InGameMenu;
import com.greenit.game.gui.InGameMenuOld;
import com.greenit.game.views.Slot;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import java.util.List;

/**
 *
 * @author Alexis
 */
public class SlotsActionListener implements ActionListener {

    private Game game;
    private float mouseX, mouseY;
    private float mouseXFloat, mouseYFloat;
    private static Slot currentSlot;
    private static boolean menuShown = false;
    
    public SlotsActionListener(Game game) {
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
                Mesh slot = clicked.getMesh();
                if(slot instanceof Slot){
                    GameStatusController.getStatus().setPaused(true);
                    InGameMenu.getCurrentInstance().openCentralUnitSelectionMenu((Slot) slot);
//                    currentSlot = (Slot) slot;
//                    GreenItGui.getNifty().fromXml("Interface/inGameMenu.xml", "inGameStart");
//                    InGameMenuOld inGameMenu = new InGameMenuOld();
//                    inGameMenu.addAllItems();
                }
            }
        }
    }

    public static Slot getCurrentSlot(){
        return currentSlot;
    }
    
    public static boolean isMenuShown() {
        return menuShown;
    }

    public static void setMenuShown(boolean menuShown) {
        SlotsActionListener.menuShown = menuShown;
    }
}
