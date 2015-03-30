/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.GreenITApplication;
import com.greenit.game.UtilsGreenIT;
import com.greenit.game.views.EnergyProviderSlot;
import com.greenit.game.views.Slot;
import com.jme3.audio.lwjgl.LwjglAudioRenderer;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Alexis
 */
public class InGameMenu extends JPanel implements ActionListener {
    
    private static InGameMenu currentInstance;
    
    private GreenITApplication app;
    private ButtonGreenIT exitButton;
    private CentralUnitSelectionMenu cusm;
    private RequestTypeSelectionMenu rtsm;
    private EnergyProviderSelectionMenu epsm;
    private EnergyProviderSelectionMenu assm;
    
    public static final String EXITAC = "Exit";
    
    private InGameMenu() {
        this.setLayout(null);
        this.setBackground(Color.black);
        
        this.add(this.getEnergyProviderSelectionMenu());
        this.add(this.getAlternateSourceSelectionMenu());
        this.add(this.getRequestTypeSelectionMenu());
        this.add(this.getCentralUnitSelectionMenu());
        this.add(this.getExitButton());
        this.add(this.getApplicationCanvas());
    }
    
    public static InGameMenu getNewInstance() {
        currentInstance = new InGameMenu();
        return currentInstance;
    }
    
    public void openCentralUnitSelectionMenu(Slot slot) {
        this.getCentralUnitSelectionMenu().setSlot(slot);
        this.getCentralUnitSelectionMenu().showMenu();
    }
    
    public void openEnergyProviderSelectionMenu(EnergyProviderSlot energyProvider) {
        if(energyProvider.getNumerous() == 1)
        {
            this.getEnergyProviderSelectionMenu().setEnergyProvider(energyProvider);
            this.getEnergyProviderSelectionMenu().showMenu();
        }
        else
        {
            this.getAlternateSourceSelectionMenu().setEnergyProvider(energyProvider);
            this.getAlternateSourceSelectionMenu().showMenu();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(EXITAC))
        {
            this.remove(this.getExitButton());
            this.remove(this.getApplicationCanvas());
            this.app.stop();
            
            MainFrame.getFrame().mainMenu();
        }
    }
    
    
    /*** GETTERS AND SETTERS ***/
    
    public static InGameMenu getCurrentInstance() {
        if(currentInstance == null) {
            currentInstance = new InGameMenu();
        }
        
        return currentInstance;
    }
    
    public final Canvas getApplicationCanvas() {
        if(this.app == null) {
            this.app = GreenITApplication.getApp();
        
            AppSettings settings = new AppSettings(true);
            settings.setWidth(UtilsGreenIT.widthScreen);
            settings.setHeight(UtilsGreenIT.heightScreen);

            this.app.setSettings(settings);
            this.app.createCanvas();
        }
        
        JmeCanvasContext ctx = (JmeCanvasContext) this.app.getContext();
        ctx.setSystemListener(app);
        ctx.getCanvas().setBounds(0, 0, UtilsGreenIT.widthScreen, UtilsGreenIT.heightScreen);
        
        return ctx.getCanvas();
    }

    public final ButtonGreenIT getExitButton() {
        if(this.exitButton == null) {
            exitButton = new ButtonGreenIT("Exit");
            exitButton.setActionCommand(EXITAC);
            exitButton.addActionListener(this);
            
            exitButton.setBounds((int) (UtilsGreenIT.widthScreen - UtilsGreenIT.widthButtons*1.5f), 0, UtilsGreenIT.widthButtons, UtilsGreenIT.widthButtons);
        }
        return exitButton;
    }

    public CentralUnitSelectionMenu getCentralUnitSelectionMenu() {
        if(this.cusm == null) {
            this.cusm = new CentralUnitSelectionMenu();
            this.cusm.setVisible(false);
        }
        
        return cusm;
    }
    
    public RequestTypeSelectionMenu getRequestTypeSelectionMenu() {
        if(this.rtsm == null) {
            this.rtsm = new RequestTypeSelectionMenu();
            this.rtsm.setVisible(false);
        }
        
        return rtsm;
    }
    
    public EnergyProviderSelectionMenu getEnergyProviderSelectionMenu() {
        if(this.epsm == null) {
            this.epsm = new EnergyProviderSelectionMenu(1);
            this.epsm.setVisible(false);
        }
        
        return this.epsm;
    }
    
    public EnergyProviderSelectionMenu getAlternateSourceSelectionMenu() {
        if(this.assm == null) {
            this.assm = new EnergyProviderSelectionMenu(2);
            this.assm.setVisible(false);
        }
        
        return this.assm;
    }
}
