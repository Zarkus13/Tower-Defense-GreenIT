/*
 * Ceci est un control reutilisable a volonté il fait un bouton retour au menu principal;
 */
package com.greenit.game.gui;

import com.greenit.game.GreenITApplication;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;



/**
 *
 * @author GLABL
 */
public class ControlerExit implements Controller{    
    private Nifty nifty;
    private Screen screen;
    
    public void bind(Nifty tnifty, 
                     Screen tscreen,
                     Element element,
                     Properties prprts,
                     Attributes atrbts) {
   
        nifty = tnifty;
        screen = tscreen;
    }
 

    public void init(Properties prprts, Attributes atrbts) {
        
    }

    public void onStartScreen() {
        
    }

    public void onFocus(boolean bln) {
       
    }
    public void exit(){
        GreenITApplication.getApp().destroy();
//        GreenItGui.getNifty().getSoundSystem().getMusic("music").stop(); 
        nifty.fromXml("Interface/greenItGui.xml", "start");
    }

    public boolean inputEvent(NiftyInputEvent nie) {
        return false;
        
        
    }
    
  }



