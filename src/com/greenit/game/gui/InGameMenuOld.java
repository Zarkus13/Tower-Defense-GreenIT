/**
 * Ceci est le controler du menu 
 * Elle comprend le control bouton quitter(controlerTest)
 * et une methode qui rajoute des items dans le menu
 */
package com.greenit.game.gui;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.controllers.SlotsActionListener;
import com.greenit.game.data.DataAccess;
import com.greenit.game.models.CentralUnit;
import com.greenit.game.models.RequestType;
import com.greenit.game.models.TypeUtil;
import com.greenit.game.views.Slot;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.PopupBuilder;
import de.lessvoid.nifty.controls.dynamic.PanelCreator;
import de.lessvoid.nifty.controls.dynamic.TextCreator;
import de.lessvoid.nifty.controls.dynamic.attributes.ControlEffectOnHoverAttributes;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.elements.Element;

/**
 *
 * @author GLABL
 */
public class InGameMenuOld implements ScreenController {

    private static Nifty nifty;
    private static Screen screen;
    private Slot clickedSlot;
    private int itemId;

    public InGameMenuOld() {
        itemId = 1;
//        this.clickedSlot = slot;

    }

    public void bind(Nifty tNifty, Screen tScreen) {
        nifty = tNifty;
        screen = tScreen;
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void addItem(String itemName, Element parent) {
        PanelCreator item = new PanelCreator();
        
        TextCreator text = new TextCreator(itemName);
        text.setAlign("center");
        text.setVAlign("center");
        text.setColor("white");
        text.setHeight("20px");
        text.setFont("/Interface/Fonts/Arial.fnt");
        
        itemId += 1;
        item.setChildLayout("center");
        item.setWidth("100%");
        item.setHeight("100%");
        item.setAlign("left");
        item.setVAlign("center");
        item.setId("centralUnit" + itemId);
        item.setName("centralUnit" + itemId);
        item.setFont("/Interface/Fonts/Arial.fnt");
        item.setColor("#f74f");
        item.setVisible("true");
        item.setInteractOnClick("menuItemClick("+itemName+");");
        item.setBackgroundImage("/Textures/centralUnit.png");
//        item.getRenderer(TextRenderer.class).setText(itemName);
        ControlEffectOnHoverAttributes effect = new ControlEffectOnHoverAttributes();
        effect.setName("autoScroll");
        effect.setAttribute("start", "0");
        effect.setAttribute("end", "10");


        item.addEffectsOnHover(effect);
        
        
        Element panel = item.create(nifty, screen, parent);
        text.create(nifty, screen, panel);
        parent.layoutElements();
    }

    public void addAllItems() {
        SlotsActionListener.setMenuShown(true);
        int count = 0;
        Element container = screen.findElementByName("container");
        Element panel = null;
        for (CentralUnit centralUnit : DataAccess.getCentralUnits()) {
            count++;
            if(count >= 6 || panel == null){
                count = 0;
                PanelCreator currentPanel = new PanelCreator();
                currentPanel.setWidth(2*((int) (Game.percHSlots * GreenITApplication.getApp().getSettings().getHeight())) + "px");
                currentPanel.setHeight(2*((int) (Game.percHSlots * GreenITApplication.getApp().getSettings().getHeight())) + "px");
                currentPanel.setName("panel"+System.currentTimeMillis());
                currentPanel.setChildLayout("vertical");
                panel = currentPanel.create(nifty, screen, container);
            }
            addItem(centralUnit.getModel(), panel);
        }
    }

    public void menuItemClick(String itemName) {
        PopupBuilder popup = new PopupBuilder("chooseRequestPopup");
        popup.childLayoutCenter();
        
        popup.registerPopup(nifty);
        Element popupElement = nifty.createPopup("chooseRequestPopup");
        
        PanelCreator popupContainer = new PanelCreator();
        popupContainer.setChildLayout("vertical");
        popupContainer.setWidth("200px");
        popupContainer.setHeight("600px");
        popupContainer.setAlign("center");
        popupContainer.setVAlign("center");
        popupContainer.setVisible("true");
        popupContainer.setBackgroundColor("#000000");
        
        Element popupContainerElement = popupContainer.create(nifty, screen, popupElement);
        
        choosePanel(itemName, "sql", popupContainerElement);
        choosePanel(itemName, "dns", popupContainerElement);
        choosePanel(itemName, "sip", popupContainerElement);
        choosePanel(itemName, "http", popupContainerElement);
        choosePanel(itemName, "ftp", popupContainerElement);
        choosePanel(itemName, "imap", popupContainerElement);

        nifty.showPopup(screen, popupElement.getId(), null);
        
        System.out.println("End menuItemClick");
    }

    private void choosePanel(String itemName, String type, Element popupContainer ){
        
        PanelCreator panel = new PanelCreator();
        TextCreator text = new TextCreator(type);
        
        text.setAlign("center");
        text.setVAlign("center");
        text.setColor("white");
        text.setHeight("20px");
        text.setFont("/Interface/Fonts/Arial.fnt");

        panel.setChildLayout("center");
        panel.setWidth("100px");
        panel.setHeight("100px");
        panel.setAlign("center");
        panel.setVAlign("center");
        panel.setFont("/Interface/Fonts/Arial.fnt");
        panel.setVisible("true");
        panel.setInteractOnClick("chooseRequestType( "+itemName+", "+type+" );");
        
        Element panelElement = panel.create(nifty, screen, popupContainer);
        text.create(nifty, screen, panelElement);
    }
    
    private CentralUnit getUnit(String model){
        for(CentralUnit unit : DataAccess.getCentralUnits()){
            if(unit.getModel().equalsIgnoreCase(model)){
                return unit.clone();
            }
        }
        return null;
    }
    
    public void chooseRequestType(String itemName, String type){
        SlotsActionListener.setMenuShown(false);
        CentralUnit unit = getUnit(itemName);
        if(unit == null){
            System.out.println("No units");
            return;
        }
        RequestType choosedType = TypeUtil.requestTypeFromString(type);
        unit.setRequestType(choosedType);
        SlotsActionListener.getCurrentSlot().createCentralUnit(unit);
        nifty.exit();
    }
    
    public Slot getClickedSlot() {
        return clickedSlot;
    }

    public void setClickedSlot(Slot clickedSlot) {
        this.clickedSlot = clickedSlot;
    }
    
    
}
