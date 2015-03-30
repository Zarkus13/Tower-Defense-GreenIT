/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.models.CentralUnit;
import com.greenit.game.models.Request;
import com.greenit.game.views.CentralUnitView;
import java.util.concurrent.Callable;

/**
 *
 * @author Oli
 */
public class CentralUnitController extends Thread {
    private CentralUnit centralUnit;
    private CentralUnitView view;
    private boolean alive;

    public CentralUnitController(CentralUnit centralUnit, CentralUnitView view) {
        super(Game.captureZoneThreads, "Another capture zone");
        alive = true;
        this.centralUnit = centralUnit;
        this.view = view;
    }
    
    @Override
    public void run() {
        /*
         * Tant que l'UC fonctionne, on décrémente sa charge d'une unité par seconde.
         * Son usure augmente d'une unité toute les centralUnit.usurySpeed secondes
         * Si l'usure a atteint son maximum, l'UC est désactivée
         * Sa consommation effective est le produit de sa consommation par sa charge
         * Si la charge est nulle, on envoie 1*consommation
         */
       byte usuryCount = 0; 

       while(alive){
           try {
               
               // Traitement de la consommation
               float currentConsumption = centralUnit.getCurrentLoad() * centralUnit.getConsumption();
               if(currentConsumption <= 0){
                   currentConsumption = centralUnit.getConsumption();
               }
               Message consumptionMsg = new Message(MessageType.CONSUMPTION);
               consumptionMsg.getModifications().put("amount",(int)(currentConsumption*15));
               MessageHandler.sendMessage(consumptionMsg);
               
               // Traitement de la charge
               if(centralUnit.getCurrentLoad() > 0 ){
                   float load = centralUnit.getCurrentLoad();
                   float unload = (float)centralUnit.getProcessorSpeed();
                   unload = unload/10;
                   load = load - unload;
                   centralUnit.setCurrentLoad(load);
               }
               
               // Traitement de l'usure;
               usuryCount++ ;
               if(usuryCount >= 60){
                   usuryCount = 0;
                   int usury = centralUnit.getUsury();
                   usury++;
                   centralUnit.setUsury(usury);
                   if(centralUnit.getUsury() >= centralUnit.getUsuryThreshold()){
                       deactivateCentralUnit();
                   }    
               }
               
               System.out.println("CentralUnit "+centralUnit.getModel()+", Load : "+centralUnit.getCurrentLoad()+
                       " Usury : "+centralUnit.getUsury()+"/"+centralUnit.getUsuryThreshold());
               
               GreenITApplication.getApp().enqueue(new Callable<Object>() {

                    public Object call() throws Exception {
                        view.updateCharge();
                        
                        return null;
                    }
                });
               
               Thread.sleep(1000);
            } catch(Exception ex) {
                alive = false;
            }
       }
    }
    
    private void deactivateCentralUnit(){
        alive = false;
        view.getSlot().destroyCentralUnit();
    }
    
    public boolean handleRequest(Request request){
        /*
         * Si la requête est du bon type, on vérifie que l'UC n'est pas surchargée.
         * Si elle ne l'est pas, on augmente sa charge d'autant d'unité que la requête 
         * est complexe. On renvoie true pour la ZC
         * Sinon, on revoie false pour relacher la requête au niveau de l'UC
         * Un message de gestion de requête est envoyé.
         */
        if(request.getType().equals(centralUnit.getRequestType())){
            if(centralUnit.getCurrentLoad() >= centralUnit.getMaxLoad()){
                return false;
            } else {
                float currentLoad = centralUnit.getCurrentLoad();
                System.out.println("Handling request, complexity : "+request.getComplexity());
                float requestLoad = (float)request.getComplexity();
                requestLoad = requestLoad/10;
                System.out.println("Real charge : "+requestLoad);
                currentLoad = currentLoad + requestLoad;
                if(currentLoad <= centralUnit.getMaxLoad()) {
                    centralUnit.setCurrentLoad(currentLoad);
                    Message reqMsg = new Message(MessageType.HANDLED_REQUEST);
                    reqMsg.getModifications().put("type", centralUnit.getRequestType());
                    MessageHandler.sendMessage(reqMsg);
                }
                return true;
            }
        }
       return false;
    }
}
