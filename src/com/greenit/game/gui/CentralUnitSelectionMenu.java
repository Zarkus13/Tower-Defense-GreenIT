/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.controllers.GameStatusController;
import com.greenit.game.data.DataAccess;
import com.greenit.game.models.CentralUnit;
import com.greenit.game.views.Slot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexis
 */
public class CentralUnitSelectionMenu extends AbstractSelectionMenu implements MouseListener {
    
    private Slot slot;
    private List<CentralUnitMenuItem> items;
    
    public CentralUnitSelectionMenu() {
        nbCols = (int) Math.ceil(Math.sqrt(DataAccess.getCentralUnits().size()));
//        nbRows = DataAccess.getCentralUnits().size() % nbCols;
        nbRows = nbCols - 1;
        gap = 10;
        
        this.initialisation();
        
        items = new LinkedList<CentralUnitMenuItem>();
        
        for(CentralUnit centralUnit : DataAccess.getCentralUnits()) {
            CentralUnitMenuItem item = new CentralUnitMenuItem(centralUnit, this);
            
            this.getContent().add(item);
            this.items.add(item);
        }
    }

    public synchronized void mouseClicked(MouseEvent e) { 
        if(e.getSource() instanceof CentralUnitMenuItem)
        {
            GameStatusController.getStatus().setPaused(false);
            
            this.setVisible(false);
            
            InGameMenu.getCurrentInstance().getRequestTypeSelectionMenu().setSlot(slot);
            InGameMenu.getCurrentInstance().getRequestTypeSelectionMenu().setCentralUnit(((CentralUnitMenuItem) e.getSource()).getCentralUnit().clone());
            InGameMenu.getCurrentInstance().getRequestTypeSelectionMenu().showMenu();
//            notifyAll();
        }
    }    
    
    
    /*** GETTERS AND SETTERS ***/

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public List<CentralUnitMenuItem> getItems() {
        return items;
    }

    public void setItems(List<CentralUnitMenuItem> items) {
        this.items = items;
    }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }
}
