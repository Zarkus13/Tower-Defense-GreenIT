/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.UtilsGreenIT;
import com.greenit.game.controllers.GameStatusController;
import com.greenit.game.models.CentralUnit;
import com.greenit.game.models.RequestType;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
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
public class CentralUnitMenuItem extends JPanel implements MouseListener {
    
    private CentralUnit centralUnit;
    private JLabel brand;
    private JLabel model;
    private JLabel price;
    private JLabel envTaxe;
    private JLabel envSymbole;
    
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    
    private static BufferedImage image;
    private static BufferedImage imageHoverWrong;
    private static BufferedImage imageHoverGood;
    private BufferedImage currentImage;
    private BufferedImage imageHover;
    
    static {
        try {
            image = ImageIO.read(new File("assets/Textures/BackgroundItem.png"));
            imageHoverWrong = ImageIO.read(new File("assets/Textures/BackgroundItemWrong.png"));
            imageHoverGood = ImageIO.read(new File("assets/Textures/BackgroundItemGood.png"));
        } catch (IOException ex) {
            System.out.println("Can't load background image for items");
        }
    }
    
    public CentralUnitMenuItem(CentralUnit centralUnit, CentralUnitSelectionMenu cusm) {
        this.centralUnit = centralUnit;
        this.centralUnit.setRequestType(RequestType.HTTP);
        
        this.currentImage = CentralUnitMenuItem.image;
        this.imageHover = (GameStatusController.getStatus().getMoney() >= this.centralUnit.getPrice() ? imageHoverGood : imageHoverWrong);
        
        this.layout = new GridBagLayout();
        this.setLayout(layout);
        
        this.addBrand();
        this.addModel();
        this.addPrice();
        this.addEnvTaxe();
        
        this.addMouseListener(cusm);
        this.addMouseListener(this);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = UtilsGreenIT.heightButtons*2;
        
        g2d.drawImage(this.currentImage, 0, 0, this.getSize().width, this.getSize().width, null);
    }

    public void mouseEntered(MouseEvent e) {
        this.currentImage = imageHover;
        repaint();
    }

    public void mouseExited(MouseEvent e) {
        this.currentImage = image;
        repaint();
    }
    
    
    /*** GETTERS AND SETTERS ***/

    public JLabel addBrand() {
        if(this.brand == null) {
            Font font = new Font("Verdana", Font.BOLD, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen/1.5f));
                
            brand = new JLabel(centralUnit.getBrand());
            brand.setForeground(Color.white);
            brand.setFont(font);
        }
        
        this.getConstraints().weightx = 1;
        this.add(brand, this.getConstraints());
        
        return brand;
    }

    public JLabel addModel() {
        if(this.model == null) {
            Font font = new Font("Verdana", Font.PLAIN, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen/2));
        
            model = new JLabel("<html><center>" + centralUnit.getModel() + "</center></html>");
            model.setForeground(Color.white);
            model.setFont(font);
        }
        
        this.getConstraints().gridy = 1;
        this.add(model, this.getConstraints());
        
        return model;
    }

    public JLabel addPrice() {
        if(this.price == null) {
            Font font = new Font("Verdana", Font.PLAIN, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen/2));
        
            price = new JLabel("$ " + String.valueOf(centralUnit.getPrice()));
            price.setForeground(Color.green);
            price.setForeground(new Color(219, 207, 11));
            price.setFont(font);
        }
        
        this.getConstraints().gridy = 2;
        this.getConstraints().gridwidth = 2;
//        this.getConstraints().anchor = GridBagConstraints.WEST;
        this.add(price, this.getConstraints());
        
        return price;
    }
    
    public JLabel addEnvTaxe() {
        if(this.envTaxe == null) {
            Font font = new Font("Verdana", Font.PLAIN, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen/2));
            
            envTaxe = new JLabel(String.valueOf(this.centralUnit.getEnvTax()));
            envTaxe.setForeground(Color.red);
            envTaxe.setFont(font);
        }
        
        this.getConstraints().gridx = 0;
        this.getConstraints().gridy = 3;
        this.getConstraints().gridwidth = 1;
        this.getConstraints().anchor = GridBagConstraints.EAST;
        this.add(getEnvSymbole(), this.getConstraints());
        
        this.getConstraints().gridx = 1;
        this.getConstraints().anchor = GridBagConstraints.WEST;
        this.add(envTaxe, this.getConstraints());
        
        return envTaxe;
    }
    
    public JLabel getEnvSymbole() {
        if(envSymbole == null) {
            int width = (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen/1.5f);
            
            BufferedImage img = new BufferedImage(width, width, BufferedImage.TRANSLUCENT);
            Graphics2D g2d = img.createGraphics();
            try {
                g2d.drawImage(ImageIO.read(new File("assets/Textures/earth.png")), 0, 0, width, width, null);
                g2d.dispose();
            } catch (IOException ex) {
                System.out.println("Can't load image for environnement rating");
            }

            ImageIcon image = new ImageIcon(img);
            envSymbole = new JLabel(image);
        }
        
        return envSymbole;
    }

    public GridBagConstraints getConstraints() {
        if(this.constraints == null) {
            constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 2;
            constraints.gridheight = 1;
        }
        return constraints;
    }

    public CentralUnit getCentralUnit() {
        return centralUnit;
    }

    public void setCentralUnit(CentralUnit centralUnit) {
        this.centralUnit = centralUnit;
    }

    public void mouseClicked(MouseEvent e) { }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }
}
