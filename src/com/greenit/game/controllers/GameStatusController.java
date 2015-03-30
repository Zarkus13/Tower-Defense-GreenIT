/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.messaging.Observer;

/**
 *
 * @author Oli
 */
public class GameStatusController implements Observer {
    private int money;
    private int envRating;
    private int overdraft;
    private boolean paused;
    
    private float moneyRate = 1;
    private float envRate = 1;
    
    private static GameStatusController status;
    
    public static GameStatusController getStatus() {
        if(status == null) {
            status = new GameStatusController(2000, 0);
        }
        
        return status;
    }
    
    public static void endGame() {
        status = null;
    }
    
    private GameStatusController(int money, int envRating) {
        this.money = money;
        this.envRating = envRating;
        this.overdraft = 2000;
        MessageHandler.addObserver(MessageType.CASHFLOW, this);
        MessageHandler.addObserver(MessageType.ENVRATING, this);
        MessageHandler.addObserver(MessageType.MODIF_RATES, this);
    }

    @Override
    public void onReceive(Message message) {
        if(message.getType().equals(MessageType.CASHFLOW)){
            int amount = (Integer) message.getModifications().get("amount");
            money += (amount*moneyRate);
            if(money <= -overdraft){
//                Message msgToSend = new Message(MessageType.GAMEOVER);
//                String reason = "No more money";
//                msgToSend.getModifications().put("reason", reason);
//                MessageHandler.sendMessage(msgToSend);
            }
        } else if (message.getType().equals(MessageType.ENVRATING)){
            int amount = (Integer) message.getModifications().get("amount");
            envRating += (amount*envRate);
        } else if (message.getType().equals(MessageType.MODIF_RATES)){
            moneyRate = (Float)message.getModifications().get("money");
            envRate = (Float)message.getModifications().get("env");
        }
    }
    
    public int getEnvRating() {
        return envRating;
    }

    public void setEnvRating(int envRating) {
        this.envRating = envRating;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public float getEnvRate() {
        return envRate;
    }

    public void setEnvRate(float envRate) {
        this.envRate = envRate;
    }

    public float getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(float moneyRate) {
        this.moneyRate = moneyRate;
    }
}
