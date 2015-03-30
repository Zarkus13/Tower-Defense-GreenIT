/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.models.BestPractice;
import java.util.List;

/**
 *
 * @author oli
 */
public class BestPracticesController extends Thread {
    private List<BestPractice> currentPractices;
    private static BestPracticesController controller;
    
    public BestPracticesController() {
        
    }

    public static BestPracticesController getBestPracticesController(){
        if(controller == null){
            controller = new BestPracticesController();
        }
        return controller;
    }
    
    public void addPractice(BestPractice bestPractice){
        currentPractices.add(bestPractice);
    }
    
    @Override
    public void run() {
        boolean alive = true;
        while(alive){
            try{
                float moneyRate = 0;
                float envRate = 0;
                float consumptionRate = 0;
                int activePractices = currentPractices.size(); 
                if(activePractices <= 0){
                    moneyRate = 1;
                    envRate = 1;
                    consumptionRate = 1;
                } else {
                   for(BestPractice bestPractice : currentPractices){
                        bestPractice.setDuration(bestPractice.getDuration()-1);
                        if(bestPractice.getDuration() <= 0){
                            currentPractices.remove(bestPractice);
                            continue;
                        }
                        moneyRate += bestPractice.getMoneyRate();
                        envRate += bestPractice.getEnvRate();
                        consumptionRate += bestPractice.getConsumptionRate();
                    }
                    moneyRate = moneyRate / activePractices;
                    envRate = envRate / activePractices;
                    consumptionRate = consumptionRate / activePractices; 
                }
                
                Message rateMsg = new Message(MessageType.MODIF_RATES);
                rateMsg.getModifications().put("consumption", consumptionRate);
                rateMsg.getModifications().put("money", moneyRate);
                rateMsg.getModifications().put("env", envRate);
                MessageHandler.sendMessage(rateMsg);
                
                Thread.sleep(1000);
            } catch (Exception e){
                alive = false;
            }
        }
    }    
}
