/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.messaging.Observer;
import com.greenit.game.models.MiniGoal;
import com.greenit.game.models.RequestType;

/**
 *
 * @author Oli
 */
public class MiniGoalController extends Thread implements Observer {
    private MiniGoal miniGoal;
    private int duration;
    private int currentCount;
    private MessageType messageToHandle;
    private boolean completed;
    private boolean running;
    
    public MiniGoalController(MiniGoal _miniGoal, int duration) {
        super(Game.miniGoalThreads, "Another mini goal");
        
        this.miniGoal = _miniGoal;
        this.duration = duration;
        currentCount = 0;      
        completed = false;
        
        if( miniGoal.isSurvivalGoal()){
            completed = true;
            messageToHandle = MessageType.FAILED_REQUEST;
        } else {
            messageToHandle = MessageType.HANDLED_REQUEST;
        }
        MessageHandler.addObserver(messageToHandle, this);
    }

    @Override
    public void onReceive(Message message) {
        /*
         * Si le miniGoal est un survival, alors le message signifie que l'objectif est
         * raté. Sinon, ça veut dire qu'un message de plus a été traité.
         * On part du principe que l'enregistrement dans le constructeur ne peux
         * pas créer d'eerreur, on fait donc uniquement un test sur le type du miniGoal
         */
        RequestType type = (RequestType) message.getModifications().get("type");
        if(!(type.equals(miniGoal.getTypeToHandle()))){
            return;
        }
        if(miniGoal.isSurvivalGoal()){
            System.out.println("MiniGoalController, Survival:"+miniGoal.isSurvivalGoal()+" ====> get a request message, ");
            completed = false;
            running = false;
        } else {
            currentCount ++;
            System.out.print("MiniGoalController, Survival:"+miniGoal.isSurvivalGoal()+" ====> get a request message, ");
            System.out.println(currentCount+"/"+miniGoal.getNbToHandle());
            if(currentCount >= miniGoal.getNbToHandle()){
                completed = true;
                running = false;
                handleComplete();
            }
       } 
    }
    
    private void handleComplete(){
        // TODO envoyer message vers JME
        System.out.println("MiniGoalController "+miniGoal.isSurvivalGoal()+" =====> completed");

        Message cashMsg = new Message(MessageType.CASHFLOW);
        cashMsg.getModifications().put("amount", miniGoal.getCashAward());
        MessageHandler.sendMessage(cashMsg);

        Message envMsg = new Message(MessageType.ENVRATING);
        envMsg.getModifications().put("amount", miniGoal.getEnvAward());
        MessageHandler.sendMessage(envMsg);
        
        if(miniGoal.getBonus() != null){
            Message bonusMsg = new Message(MessageType.BONUS);
            bonusMsg.getModifications().put("bonus", miniGoal.getBonus());
            MessageHandler.sendMessage(bonusMsg);
        }
    }
    
    private void handleFailure(){
        //TODO envoyer message vers JME
        completed = false;
        System.out.println("You fail to achieve the miniGoal "+miniGoal.isSurvivalGoal());
    }
    
    @Override
    public void run() {
        int count = 0;
        running = true;
        while(running){
            try{
                count ++;
                if (count >= duration){
                    running = false;
                }
                Thread.sleep(1000);
            } catch (Exception e){
                running = false;
            }
        }
        if(miniGoal.isSurvivalGoal() && completed){
            handleComplete();
        } else if (!completed) {
            handleFailure();
        }
        MessageHandler.removeObserver(messageToHandle, this);
    }
  
    public MiniGoal getMiniGoal() {
        return miniGoal;
    }

    public void setMiniGoal(MiniGoal miniGoal) {
        this.miniGoal = miniGoal;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isRunning() {
        return running;
    }
}
