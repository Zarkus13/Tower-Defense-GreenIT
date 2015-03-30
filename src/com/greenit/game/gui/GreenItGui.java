package com.greenit.game.gui;

import com.greenit.game.GreenITApplication;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author simon
 */
public class GreenItGui implements ScreenController {
    private static Nifty nifty;
    private static Screen screen;
    
    @Override
    public void bind(Nifty tnifty, Screen tscreen) {
        nifty = tnifty; 
        screen = tscreen;
        nifty.getSoundSystem().setMusicVolume(0.1f);
    }
    
    @Override
    public void onStartScreen() { 
       
    }
    
    @Override
    public void onEndScreen() {
    }
    
    public void startNewGame(){
        GreenITApplication.getApp().createNewGame();
        nifty.exit();
//        nifty.fromXml("Interface/inGameMenu.xml", "inGameStart");
    }

    public static Nifty getNifty() {
        return nifty;
    }
}

