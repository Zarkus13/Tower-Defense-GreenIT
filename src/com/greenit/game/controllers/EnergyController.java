package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.messaging.Observer;
import com.greenit.game.models.EnergyProvider;

/**
 *
 * @author Oli
 */
public class EnergyController extends Thread implements Observer{
    private EnergyProvider currentProvider;
    private EnergyProvider alternateProvider;
    private int interval;
    private int consumption;
    private boolean outage;

    private float consumptionRate;
    
    public EnergyController() {
        super(Game.equipmentThreads, "Another equipment - EnergyController");
        
        interval = 10;
        consumption = 0;
        outage = false;
        consumptionRate = 0;
        
        MessageHandler.addObserver(MessageType.CHANGEPROVIDER, this);
        MessageHandler.addObserver(MessageType.CONSUMPTION, this);
        MessageHandler.addObserver(MessageType.POWEROUTAGE, this);
        MessageHandler.addObserver(MessageType.MODIF_RATES, this);
    }

    @Override
    public void onReceive(Message message) {
        /*
         * Switche entre les types de messages pour faire le traitement
         * correspondant
         */
        if(message.getType().equals(MessageType.CHANGEPROVIDER)){
            currentProvider = (EnergyProvider) message.getModifications().get("provider");
        } else if (message.getType().equals(MessageType.POWEROUTAGE)){
            outage = (Boolean) message.getModifications().get("status");
        } else if (message.getType().equals(MessageType.CONSUMPTION)){
            consumption += (Integer) message.getModifications().get("amount");
        } else if ( message.getType().equals(MessageType.MODIF_RATES)){
            consumptionRate = (Float) message.getModifications().get("consumption");
        }
    }
    
    @Override
    public void run() {
        /*
         * Vie de l'EnergyController
         * Toute les *interval* secondes, on vérifie d'où vient le courant
         * Selon, on calcule la facture et l'impact sur l'environement au moyen
         * du produit de la consommation avec l'impact sur l'environnement/conso 
         * du provider actif.
         */
        boolean alive = true;
        int count = 0;
        while(alive){
            try {
                if (count >= interval) {
                    EnergyProvider provider = null;
                    if (outage) {
                        provider = alternateProvider;
                    } else {
                        provider = currentProvider;
                    }
                
                    // bill message
                    Message billMsg = new Message(MessageType.CASHFLOW);
                    float bill = consumption * provider.getBill();
                    billMsg.getModifications().put("amount", -bill);
                    MessageHandler.sendMessage(billMsg);
                    
                    //env impact message
                    float envImpact = consumption * provider.getEnvImpact() * consumptionRate;
                    Message envImpactMsg = new Message(MessageType.ENVRATING);
                    envImpactMsg.getModifications().put("amount", envImpact);
                    MessageHandler.sendMessage(envImpactMsg);
                    consumption = 0;
                    count = 0;
                } 
                count++;
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("EnergyController will be stopped (Exception)");
                MessageHandler.removeObserver(MessageType.CHANGEPROVIDER, this);
                MessageHandler.removeObserver(MessageType.CONSUMPTION, this);
                MessageHandler.removeObserver(MessageType.POWEROUTAGE, this);
                MessageHandler.removeObserver(MessageType.MODIF_RATES, this);
                alive = false;
            }
        }
    }
            
    public EnergyProvider getCurrentProvider() {
        return currentProvider;
    }

    public void setCurrentProvider(EnergyProvider currentProvider) {
        this.currentProvider = currentProvider;
    }

    public boolean isOutage() {
        return outage;
    }

    public void setOutage(boolean outage) {
        this.outage = outage;
    }
}
