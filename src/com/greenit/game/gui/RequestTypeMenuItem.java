/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.UtilsGreenIT;
import com.greenit.game.models.RequestType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alexis
 */
public class RequestTypeMenuItem extends JPanel {
    
    private RequestType type;
    private JLabel label;
    
    public RequestTypeMenuItem(RequestType type, RequestTypeSelectionMenu rtsm) {
        this.type = type;
        
        this.setLayout(null);
        this.add(this.getLabel());
        
        this.addMouseListener(rtsm);
    }

    @Override
    protected void paintComponent(Graphics g) {
//        g.setColor(Color.red);
        try {
            g.drawImage(ImageIO.read(new File("assets/Textures/RequestMenuItem" + this.type + ".png")), 0, 0, (int)(UtilsGreenIT.heightItemsMenus), (int)(UtilsGreenIT.heightItemsMenus), null);
        } catch (IOException ex) {
            System.out.println("Can't find image for request type " + this.type);
        }
    }
    
    
    /*** GETTERS AND SETTERS ***/

    public JLabel getLabel() {
        if(this.label == null) {
            label = new JLabel(this.type.toString());
            label.setForeground(Color.white);
            
            Font font = new Font("Verdana", Font.BOLD, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen));
            label.setFont(font);
            
            int x = UtilsGreenIT.heightItemsMenus/2 - label.getPreferredSize().width/2;
            int y = UtilsGreenIT.heightItemsMenus/2 - label.getPreferredSize().height/2;
            
            label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
        }
        return label;
    }

    public RequestType getType() {
        return type;
    }
}
