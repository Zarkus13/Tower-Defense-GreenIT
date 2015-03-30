/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.controllers;

import com.greenit.game.Game;
import com.greenit.game.GreenITApplication;
import com.greenit.game.data.DataAccess;
import com.greenit.game.i18n.LocalizationUtil;
import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.messaging.Observer;
import com.greenit.game.models.Bonus;
import com.greenit.game.models.MiniGoal;
import java.util.Random;

/**
 *
 * @author Oli
 */
public class GameController extends Thread implements Observer {
    //private GameBoard gameBoard;
    private MiniGoalController miniGoal;
    private boolean valid;
    private int level;
    
    public GameController() {
        super(Game.gameThreads, "GameController");
        
        valid = true;
        miniGoal = null;
        MessageHandler.addObserver(MessageType.GAMEOVER, this);
    }
    
    @Override
    public void onReceive(Message message) {
        System.out.println("Game is over");
        System.out.println("Reason : "+message.getModifications().get("reason"));
        gameOver();
    }
    
    
    @Override
    public void run() {
        Random rand = new Random();
        generateMiniGoal();
        while(valid){
            try {
              int miniGoalGen = rand.nextInt(1000);
              if(miniGoalGen > 700){
                  generateMiniGoal();
              }
              this.sleep(1000);
            } catch (Exception e){
                valid = false;
                System.out.println("GameController will be stopped (Exception)");
//                gameOver();
            }
        }
    }

    private void gameOver() {
        MessageHandler.removeObserver(MessageType.GAMEOVER, this);
        valid = false;
        GreenITApplication.getApp().stop();
//        GreenITApplication.getApp().destroy();
//        GreenITApplication.getApp().getGame().detachAllChildren();
//        GreenITApplication.getApp().getGuiNode().detachAllChildren();
//            GreenItGui.getNifty().getSoundSystem().getMusic("music").stop(); 
//        GreenITApplication.getApp().getNifty().fromXml("Interface/greenItGui.xml", "start");    
    }
    
    public void generateMiniGoal(){
        if(miniGoal != null && miniGoal.isRunning()){
            System.out.println("! no new MG");
            return;
        }
        
        Random randomValues = new Random();
        int miniGoalIndex = randomValues.nextInt(DataAccess.getMiniGoals().size()-1);
        MiniGoal mg = DataAccess.getMiniGoals().get(miniGoalIndex).clone(); 
        
        int bonusIndex = randomValues.nextInt(DataAccess.getBonus().size()-1);
        Bonus bonus = DataAccess.getBonus().get(bonusIndex);
        
        mg.setBonus(bonus);
        int multiple = randomValues.nextInt(10)+1;
        int awards = randomValues.nextInt(multiple*level)*100;
        mg.setCashAward(awards);
        mg.setEnvAward(awards);
        multiple = randomValues.nextInt(10)+1;
        mg.setNbToHandle(randomValues.nextInt(multiple*level)*10);
        
        miniGoal = new MiniGoalController(mg, 1000);
        miniGoal.start();
        
        System.out.println("****** NEW MINIGOAL******");
        System.out.println(mg.getName()+", Type : "+mg.getTypeToHandle());
        System.out.println(LocalizationUtil.getString(mg.getDecription()));
    }
    
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
