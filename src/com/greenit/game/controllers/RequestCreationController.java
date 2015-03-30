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
import com.greenit.game.models.Request;
import com.greenit.game.models.RequestType;
import com.greenit.game.views.RequestView;
import com.jme3.scene.Node;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexis
 */
public class RequestCreationController extends Thread implements Observer {
    
    private boolean alive = true;
    private Game game;
    
    private int rps; // Requests par seconde
    private long tbi; // Time between iteration
    private long prevTime;
        
    private Float requestAwardRate = 1f;
    private Float sqlComplexityRate = 1f;
    private Float httpComplexityRate = 1f;
    private Float imapComplexityRate = 1f;
    private Float ftpComplexityRate = 1f;
    private Float dnsComplexityRate = 1f;
    private Float sipComplexityRate = 1f;
    
    public RequestCreationController(Game game) {
        super(Game.gameThreads, "RequestCreationController");
        MessageHandler.addObserver(MessageType.BONUS_ENABLED, this);
        MessageHandler.addObserver(MessageType.BONUS_DISABLED, this);
        this.game = game;
        
//        rps = (int) (5*game.getLevel() + Math.E/2);
        rps = 10;
        prevTime = System.currentTimeMillis();
    }

    public void onReceive(Message message) {
        if(message.getType().equals(MessageType.BONUS_ENABLED)){
            if(message.getModifications().containsKey("type")){
                RequestType type = (RequestType) message.getModifications().get("type");
                switch(type){
                    case DNS:
                        dnsComplexityRate =  (Float) message.getModifications().get("value");
                        break;
                    case FTP:
                        ftpComplexityRate = (Float) message.getModifications().get("value");
                        break;
                    case HTTP:
                        httpComplexityRate = (Float) message.getModifications().get("value");
                        break;
                    case IMAP:
                        imapComplexityRate = (Float) message.getModifications().get("value");
                        break;
                    case SIP:
                        sipComplexityRate = (Float) message.getModifications().get("value");
                        break;
                    case SQL :
                        dnsComplexityRate = (Float) message.getModifications().get("value");
                        break;
                }
            }   else {
                requestAwardRate = (Float) message.getModifications().get("value");
            }
        } else {
            if(message.getModifications().containsKey("type")){
                RequestType type = (RequestType) message.getModifications().get("type");
                switch(type){
                    case DNS:
                        dnsComplexityRate =  1f;
                        break;
                    case FTP:
                        ftpComplexityRate = 1f;
                        break;
                    case HTTP:
                        httpComplexityRate = 1f;
                        break;
                    case IMAP:
                        imapComplexityRate = 1f;
                        break;
                    case SIP:
                        sipComplexityRate = 1f;
                        break;
                    case SQL :
                        dnsComplexityRate = 1f;
                        break;
                }
            } else {
                requestAwardRate = 1f;
            }
        } 
    }
    
    @Override
    public void run() {
        Future<Node> fut;
        
        while(alive) {
            try {
//                synchronized(this) {
//                    while(GameStatusController.getStatus().isPaused())
//                    {
//                        wait(); 
//                    }
//                }
                
                tbi = System.currentTimeMillis() - prevTime;
                prevTime = System.currentTimeMillis();
                
                fut = game.getApp().enqueue(new Callable<Node>() {

                    public Node call() throws Exception {
                        addRequest();
                        return game;
                    }
                });
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                this.alive = false;
            }
        }
    }
    
    private void addRequest() {
        long nbr = (long) (tbi * rps * 0.001);
        Random rand = new Random();

        for(int i = 0 ; i <= nbr ; i++) {
            RequestType type = null;
            byte complexity = (byte)(rand.nextInt(9)+1);
            short award = (short)(5*complexity*requestAwardRate);
            switch(rand.nextInt(6)){
                case 0 : 
                    type = RequestType.HTTP;
                    complexity = (byte)(complexity*httpComplexityRate);
                    break;
               case 1 : 
                    type = RequestType.DNS;
                    complexity = (byte)(complexity*dnsComplexityRate);
                    break;
               case 2 : 
                    type = RequestType.IMAP;
                    complexity = (byte)(complexity*imapComplexityRate);
                    break;
               case 3 : 
                    type = RequestType.FTP;
                    complexity = (byte)(complexity*ftpComplexityRate);
                    break;
               case 4 :
                    type = RequestType.SIP;
                    complexity = (byte)(complexity*sipComplexityRate);
                    break;
               default:
                   type = RequestType.SQL;
                   complexity = (byte)(complexity*sqlComplexityRate);
                   break;
            }
                        
            byte importance = (byte)(rand.nextInt(9)+10);
            
            Request req = new Request(type, complexity, importance, award);
//            Request req = new Request(RequestType.HTTP, (byte)(rand.nextInt(9)+1), importance, (short)(5*(byte)(rand.nextInt(9)+1)*requestAwardRate));
            RequestView request = new RequestView(this.game, req);
            this.game.attachChild(request);
        }
    }
}
