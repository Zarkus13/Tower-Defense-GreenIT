/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game;

import com.greenit.game.models.SlotPlace;
import com.greenit.game.views.EnergyProviderSlot;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Renderer;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.List;

/**
 *
 * @author Alexis
 */
public class EnergyProviderGenerator {
    
    private Game game;
    private List<EnergyProviderSlot> energyProviders;
    
    public EnergyProviderGenerator(Game game) {
        this.game = game;
    }
    
    public void placeEnergyProviders(int number) {
        SlotPlace place;
        EnergyProviderSlot energyProvider;
        float posX, posY;
        
        for(int i = 0 ; i < number ; i++) {
            if(number == 1)
                place = SlotPlace.ALONE;
            else if(i == 0)
                place = SlotPlace.FIRST;
            else if(i == number - 1)
                place = SlotPlace.LAST;
            else
                place = SlotPlace.MIDDLE;
            
            energyProvider = new EnergyProviderSlot(game, (i + 1));
            
            game.attachChild(energyProvider);
            
            posY = -Game.gethFloor() + Game.percHSlots*Game.gethFloor();
            posX = -Game.getlFloor() + (i + 1) * 2*Game.percHSlots*Game.gethFloor();
            
            energyProvider.place(posX, posY);
        }
        
        BitmapText text = new BitmapText(GreenITApplication.getCurrentAssetManager().loadFont("Interface/Fonts/Default.fnt"));
        
        text.setSize(Game.getFontSize());
        text.setColor(ColorRGBA.Blue);
        text.setText("Energy providers");
        text.setQueueBucket(Bucket.Transparent);
        
        float width = text.getLineWidth()*Game.getlFloor()/GreenITApplication.getApp().getSettings().getWidth();
        float height = text.getLineHeight()*Game.gethFloor()/GreenITApplication.getApp().getSettings().getHeight();
        
        float posXText = -Game.getlFloor() + Game.gethFloor()*Game.percHSlots;
        float posYText = -Game.gethFloor() + height + 3*Game.gethFloor()*Game.percHSlots;
        
        text.setLocalTranslation(posXText, posYText, 0.001f);
        
        game.attachChild(text);
    }
}
