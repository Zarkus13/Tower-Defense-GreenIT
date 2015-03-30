/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.messaging.Observer;
import com.greenit.game.models.Bonus;
import com.greenit.game.models.TypeUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oli
 */
public class BonusController extends Thread implements Observer {
    private List<Bonus> currentBonus;

    public BonusController() {
        currentBonus = new ArrayList<Bonus>();
        
        MessageHandler.addObserver(MessageType.BONUS, this);
    }

    public void onReceive(Message message) {
        Bonus newBonus = (Bonus) message.getModifications().get("bonus");
        
        if(newBonus != null && newBonus instanceof Bonus){
            enableBonus(newBonus);
        }
    }
    
    private void enableBonus(Bonus bonus){
        Message bonusMsg = new Message(MessageType.BONUS_ENABLED);
        if(bonus.getTargetAttribute().equalsIgnoreCase("complexity")){
            bonusMsg.getModifications().put("type", TypeUtil.requestTypeFromString(bonus.getTarget()));
        } else {
            //TODO random uc
        }
        bonusMsg.getModifications().put("value", bonus.getValue());
        MessageHandler.sendMessage(bonusMsg);
        currentBonus.add(bonus);
    }
    
    private void disableBonus(Bonus bonus){
        Message bonusMsg = new Message(MessageType.BONUS_DISABLED);
        if(bonus.getTargetAttribute().equalsIgnoreCase("complexity")){
            bonusMsg.getModifications().put("type", TypeUtil.requestTypeFromString(bonus.getTarget()));
        } else {
            //TODO random uc
        }
        MessageHandler.sendMessage(bonusMsg);
        currentBonus.remove(bonus);
    }
    
    @Override
    public void run() {
        boolean valid = true;
        while(valid){
            try{
                for(Bonus bonus : currentBonus){
                    bonus.setDuration((byte)(bonus.getDuration()+1));
                    if(bonus.getDuration() >= 5){
                        disableBonus(bonus);
                    }
                }
            } catch (Exception e){
                valid = false;
                e.printStackTrace();
            }
        }
    }
}
