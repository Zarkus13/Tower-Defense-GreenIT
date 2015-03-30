/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.UtilsGreenIT;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Alexis
 */
public class ButtonGreenIT extends JButton implements MouseListener {
    
    private BufferedImage backgroundImage = null;
    private String text;
    
    public ButtonGreenIT(String text) {
        this.text = text;
        
        this.setBorderPainted(false);
        this.setOpaque(false);
        this.addMouseListener(this);
        this.setLayout(null);
        this.setSize(UtilsGreenIT.widthButtons, UtilsGreenIT.heightButtons);
        
        try {
            backgroundImage = ImageIO.read(new File("assets/Textures/Button.png"));
        } catch (IOException ex) {
            System.out.println("Can't find image for button !");
        }
        
        Font font = new Font("Verdana", Font.BOLD, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen));
        
        JLabel label = new JLabel(text);
        label.setForeground(Color.white);
        label.setFont(font);
        this.add(label);
        
        Dimension sizeLabel = label.getPreferredSize();
        label.setBounds(this.getSize().width/2 - sizeLabel.width/2, this.getSize().height/2 - sizeLabel.height/2, sizeLabel.width, sizeLabel.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setSize(UtilsGreenIT.widthButtons, UtilsGreenIT.heightButtons);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
        
        this.setText(text);
    }
    
    public void mouseEntered(MouseEvent e) {
        try {
            backgroundImage = ImageIO.read(new File("assets/Textures/ButtonHover.png"));
        } catch (IOException ex) {
            System.out.println("Can't find image for button !");
        }
    }

    public void mouseExited(MouseEvent e) {
        try {
            backgroundImage = ImageIO.read(new File("assets/Textures/Button.png"));
        } catch (IOException ex) {
            System.out.println("Can't find image for button !");
        }
    }

    public void mousePressed(MouseEvent e) {
        try {
            backgroundImage = ImageIO.read(new File("assets/Textures/ButtonClicked.png"));
        } catch (IOException ex) {
            System.out.println("Can't find image for button !");
        }
    }

    public void mouseClicked(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }
    
    
}
