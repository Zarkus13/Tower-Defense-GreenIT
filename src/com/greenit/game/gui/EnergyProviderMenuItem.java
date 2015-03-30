/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.UtilsGreenIT;
import com.greenit.game.models.EnergyProvider;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
public class EnergyProviderMenuItem extends JPanel {
    
    private EnergyProvider energyProvider;
    private JLabel name;
    private JLabel bill;
    private JLabel envTaxe;
    private JLabel envSymbole;
    
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    
    private BufferedImage image;
    
    public EnergyProviderMenuItem(EnergyProvider energyProvider, EnergyProviderSelectionMenu epsm) {
        this.energyProvider = energyProvider;
        
        layout = new GridBagLayout();
        this.setLayout(layout);
        
        try {
            this.image = ImageIO.read(new File("assets/Textures/EnergyProviderItem" + energyProvider.getType() + ".png"));
        } catch (IOException ex) {
            System.out.println("Can't load image for provider of type " + energyProvider.getType());
        }
        
        this.addName();
        this.addBill();
        this.addEnvTaxe();
        
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = (int) (UtilsGreenIT.heightButtons*2f);
        
        g2d.drawImage(this.image, 0, 0, this.getSize().width, this.getSize().width, null);
    }
    
    
    /*** GETTERS AND SETTERS ***/
    
    private JLabel addName() {
        if(this.name == null) {
            Font font = new Font("Verdana", Font.BOLD, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen/1.5f));
            
            name = new JLabel("<html><center>" + energyProvider.getName() + "</center></html>");
            name.setFont(font);
            name.setForeground(Color.white);
        }
        
        this.getConstraints().weightx = 1;
        this.add(name, this.getConstraints());
        
        return this.name;
    }
    
    private JLabel addBill() {
        if(this.bill == null) {
            Font font = new Font("Verdana", Font.BOLD, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen/2));
            
            bill = new JLabel("$ " + String.valueOf(energyProvider.getBill()));
            bill.setFont(font);
            bill.setForeground(new Color(219, 207, 11));
        }
        
        this.getConstraints().gridy = 1;
        this.add(bill, this.getConstraints());
        
        return this.bill;
    }
    
    private JLabel addEnvTaxe() {
        if(this.envTaxe == null) {
            Font font = new Font("Verdana", Font.BOLD, (int)(UtilsGreenIT.percHRequests*UtilsGreenIT.heightScreen/2));
            
            envTaxe = new JLabel(String.valueOf(energyProvider.getEnvImpact()));
            envTaxe.setFont(font);
            envTaxe.setForeground(Color.red);
        }
        
        this.getConstraints().gridy = 2;
        this.getConstraints().gridwidth = 1;
        this.add(this.getEnvSymbole(), this.getConstraints());
        
        this.getConstraints().gridx = 1;
        this.getConstraints().anchor = GridBagConstraints.WEST;
        this.add(envTaxe, this.getConstraints());
        
        return this.envTaxe;
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

    public EnergyProvider getEnergyProvider() {
        return energyProvider;
    }

    public void setEnergyProvider(EnergyProvider energyProvider) {
        this.energyProvider = energyProvider;
    }
}
