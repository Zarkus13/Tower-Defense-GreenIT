/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.GreenITApplication;
import com.greenit.game.UtilsGreenIT;
import com.greenit.game.data.DataLoader;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alexis
 */
public class MainFrame extends JFrame {
    
    private static MainFrame frame;
    
    private GreenITApplication app;
    private JPanel content = new JPanel();
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                DataLoader ldr = new DataLoader();
                ldr.load();
                
                MainFrame.getFrame();
            }
        });
    }
    
    public static MainFrame getFrame() {
        if(MainFrame.frame == null) {
            MainFrame.frame = new MainFrame();
        }
        
        return MainFrame.frame;
    }
    
    private MainFrame() {
        this.setSize(UtilsGreenIT.widthScreen, UtilsGreenIT.heightScreen + 50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.black);
        
        this.mainMenu();
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void mainMenu() {
        this.content = new MainMenu();
        this.content.setSize(UtilsGreenIT.widthScreen, UtilsGreenIT.heightScreen);
        
        this.setContentPane(content);
        this.getContentPane().validate();
        this.validate();
    }
    
    public void newGame() {
        this.content = InGameMenu.getNewInstance();
        this.content.setSize(UtilsGreenIT.widthScreen, UtilsGreenIT.heightScreen + 50);
        
        this.setContentPane(content);
    }
}
