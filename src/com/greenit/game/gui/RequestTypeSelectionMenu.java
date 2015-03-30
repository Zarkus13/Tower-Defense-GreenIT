/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.gui;

import com.greenit.game.models.CentralUnit;
import com.greenit.game.models.RequestType;
import com.greenit.game.views.Slot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Alexis
 */
public class RequestTypeSelectionMenu extends AbstractSelectionMenu implements MouseListener {
    
    private Slot slot;
    private CentralUnit centralUnit;
    
    public RequestTypeSelectionMenu() {
        nbCols = 1;
        nbRows = RequestType.values().length;
        gap = 10;
        
        this.initialisation();
        
        for(RequestType type : RequestType.values()) {
            this.getContent().add(new RequestTypeMenuItem(type, this));
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof RequestTypeMenuItem)
        {
            this.centralUnit.setRequestType(((RequestTypeMenuItem) e.getSource()).getType());
            
            this.setVisible(false);
            
            this.slot.createCentralUnit(centralUnit);
        }
    }
    
    /*** GETTERS AND SETTERS ***/

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public CentralUnit getCentralUnit() {
        return centralUnit;
    }

    public void setCentralUnit(CentralUnit centralUnit) {
        this.centralUnit = centralUnit;
    }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }
}
