/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.GreenITApplication;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;




/**
 *
 * @author simon
 */
public class Intro implements ScreenController {
    private Nifty appMenu;
    private Screen screen;
    
    @Override
    public void bind(Nifty nifty, 
                     Screen tscreen) {
        appMenu = nifty; tscreen = screen;
    }
    
    @Override
    public void onStartScreen() { 
        appMenu.fromXml("Interface/greenItGui.xml", "start");
    }
    
    @Override
    public void onEndScreen() {
    }
    
    public void startNewGame(){
        GreenITApplication.getApp().createNewGame();
        
        
    }
}

