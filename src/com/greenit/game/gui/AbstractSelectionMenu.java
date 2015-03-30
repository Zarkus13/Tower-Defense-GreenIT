/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.UtilsGreenIT;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
public abstract class AbstractSelectionMenu extends JPanel {
    
    protected int nbCols, nbRows, gap;
    protected JPanel content;
    
    protected void initialisation() {        
        GridLayout layout = new GridLayout(nbRows, nbCols);
        layout.setVgap(gap);
        layout.setHgap(gap);
        
        this.setLayout(null);
        this.getContent().setLayout(layout);
        this.add(this.getContent());
        
        int width = (int)(nbCols*UtilsGreenIT.heightItemsMenus) + (2*gap*nbCols - gap);
        int height = (int)(nbRows*UtilsGreenIT.heightItemsMenus) + (2*gap*nbRows - gap);
        
        width = (width >= UtilsGreenIT.widthScreen ? UtilsGreenIT.widthScreen : width);
        height = (height >= UtilsGreenIT.heightScreen ? UtilsGreenIT.heightScreen : height);
        
        int widthMenu = (width + 2*gap <= UtilsGreenIT.minWidthMenus ? UtilsGreenIT.minWidthMenus : width + 2*gap);
        int heightMenu = (height + 2*gap <= UtilsGreenIT.minHeightMenus ? UtilsGreenIT.minHeightMenus : height + 2*gap);
        
        this.getContent().setBounds(widthMenu/2 - width/2, heightMenu/2 - height/2, width, height);
        this.setBounds(UtilsGreenIT.widthScreen/2 - widthMenu/2, UtilsGreenIT.heightScreen/2 - heightMenu/2, widthMenu, heightMenu);
    }    
    
    public void showMenu() {
        this.setVisible(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        try {        
            g2d.drawImage(ImageIO.read(new File("assets/Textures/BackgroundMenuInGame.jpg")), 0, 0, this.getBounds().width, this.getBounds().height, null);
        } catch (IOException ex) {
            System.out.println("Can't find image for background menu");
        }
    }
    
    /*** GETTERS AND SETTERS ***/
    
    public JPanel getContent() {
        if(this.content == null) {
            content = new JPanel();
            content.setOpaque(false);
        }
        
        return content;
    }
}
