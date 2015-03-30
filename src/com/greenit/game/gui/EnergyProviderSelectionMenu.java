/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.data.DataAccess;
import com.greenit.game.models.EnergyProvider;
import com.greenit.game.views.EnergyProviderSlot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 *
 * @author Alexis
 */
public class EnergyProviderSelectionMenu extends AbstractSelectionMenu implements MouseListener {
    
    private List<EnergyProviderMenuItem> items;
    private EnergyProviderSlot energyProvider;
    private int numerous;
    
    public EnergyProviderSelectionMenu(int numerous) {
        
        List<EnergyProvider> source = (numerous == 1 ? DataAccess.getEnergyProviders() : DataAccess.getAlternateSources());
        
        nbCols = (int) Math.ceil(Math.sqrt(source.size()));
        nbRows = (source.size() % nbCols == 0 ? nbCols : nbCols - 1);
        gap = 10;
        
        System.out.println("nbCols : " + nbCols + " - nbRows : " + nbRows);
        
        this.initialisation();
        
        for(EnergyProvider ep : source) {
            this.getContent().add(new EnergyProviderMenuItem(ep, this));
        }
    }

    public void mouseClicked(MouseEvent e) { }

    
    /*** GETTERS AND SETTERS ***/
    
    public EnergyProviderSlot getEnergyProvider() {
        return energyProvider;
    }

    public void setEnergyProvider(EnergyProviderSlot energyProvider) {
        this.energyProvider = energyProvider;
    }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }
}
