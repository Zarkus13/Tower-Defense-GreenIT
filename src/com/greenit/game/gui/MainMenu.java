/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.UtilsGreenIT;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Alexis
 */
public class MainMenu extends JPanel implements ActionListener {
    
    private JPanel buttonsContainer;
    private ButtonGreenIT buttonNewGame;
    private ButtonGreenIT buttonOptions;
    private ButtonGreenIT buttonExit;
    private BufferedImage backgroundImage;
    private int nbButtons = 3;
    
    public static final String NEWGAMEAC = "New game";
    public static final String OPTIONSAC = "Options";
    public static final String EXITAC = "Exit";
    
    public MainMenu() {
        this.setBackground(Color.black);
        this.setLayout(null);
        
        try {
            this.backgroundImage = ImageIO.read(new File("assets/Textures/MainMenuBackground.jpg"));
        } catch (IOException ex) {
            System.out.println("Can't load image for menu background !");
        }
        
        this.add(this.getButtonsContainer());
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(NEWGAMEAC))
            MainFrame.getFrame().newGame();
        else if(e.getActionCommand().equals(EXITAC))
            System.exit(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, UtilsGreenIT.widthScreen, UtilsGreenIT.heightScreen + 50, null);
    }
    
    
    /*** GETTERS AND SETTERS ***/    
    
    public JPanel getButtonsContainer() {
        if(this.buttonsContainer == null) {
            GridLayout layout = new GridLayout(3, 1);
            layout.setVgap(UtilsGreenIT.heightButtons);
            buttonsContainer = new JPanel(layout);
            
            int height = (nbButtons*2)*UtilsGreenIT.heightButtons;
            int x = UtilsGreenIT.widthScreen/2 - UtilsGreenIT.widthButtons/2;
            int y = UtilsGreenIT.heightScreen/2 - height/2;
            
            buttonsContainer.setBounds(x, y, UtilsGreenIT.widthButtons, height);
            buttonsContainer.setOpaque(false);
            
            buttonsContainer.add(this.getButtonNewGame());
            buttonsContainer.add(this.getButtonOptions());
            buttonsContainer.add(this.getButtonExit());
        }
        return buttonsContainer;
    }
    
    public ButtonGreenIT getButtonNewGame() {
        if(this.buttonNewGame == null) {
            buttonNewGame = new ButtonGreenIT("New game");
            buttonNewGame.setActionCommand(NEWGAMEAC);
            buttonNewGame.addActionListener(this);
        }
        return buttonNewGame;
    }

    public ButtonGreenIT getButtonOptions() {
        if(this.buttonOptions == null) {
            buttonOptions = new ButtonGreenIT("Options");
            buttonOptions.setActionCommand(OPTIONSAC);
            buttonOptions.addActionListener(this);
        }
        return buttonOptions;
    }

    public ButtonGreenIT getButtonExit() {
        if(this.buttonExit == null) {
            buttonExit = new ButtonGreenIT("Exit");
            buttonExit.setActionCommand(EXITAC);
            buttonExit.addActionListener(this);
        }
        return buttonExit;
    }
}
