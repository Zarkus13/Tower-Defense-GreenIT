/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.gui.GreenItGui;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author Alexis
 */
public class ExitButtonActionListener implements ActionListener {
    
    private Game game;
    private GreenITApplication app;
    private float mouseX, mouseY;
    
    public ExitButtonActionListener(Game game) {
        this.game = game;
        this.app = game.getApp();
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        mouseX = game.getApp().getInputManager().getCursorPosition().x;
        mouseY = game.getApp().getInputManager().getCursorPosition().y;
        float exitMaxX = game.getExitImg().getLocalTranslation().x + game.percHSlots * app.getSettings().getHeight();
        float exitMinX = game.getExitImg().getLocalTranslation().x - game.percHSlots * app.getSettings().getHeight();
        float exitMaxY = game.getExitImg().getLocalTranslation().y + game.percHSlots * app.getSettings().getHeight();
        float exitMinY = game.getExitImg().getLocalTranslation().y - game.percHSlots * app.getSettings().getHeight();
        
        if(mouseX <= exitMaxX && mouseX >= exitMinX && mouseY <= exitMaxY && mouseY >= exitMinY)
        {
            GreenITApplication.getApp().destroy();
            GreenITApplication.getApp().getGame().detachAllChildren();
            GreenITApplication.getApp().getGuiNode().detachAllChildren();
//            GreenItGui.getNifty().getSoundSystem().getMusic("music").stop(); 
            GreenItGui.getNifty().fromXml("Interface/greenItGui.xml", "start");
        }
    }
    
}
