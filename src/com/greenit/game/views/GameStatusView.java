/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.views;

import com.greenit.game.controllers.GameStatusController;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;


/**
 *
 * @author oli
 */
public class GameStatusView extends Node {
    private BitmapText money;
    private Picture dollarPic;
    private BitmapText env;
    private Picture envPic;
    private GameStatusController gameStatusController;
    private float halfWidth;

    public GameStatusView(AssetManager assetManager, AppSettings settings) { 
        gameStatusController = GameStatusController.getStatus();
        
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        money = new BitmapText(font, false);
        env = new BitmapText(font, false);
        
        dollarPic = new Picture("DollarPic");
        dollarPic.setImage(assetManager, "Textures/dollar.png", true);
        dollarPic.setWidth(20);
        dollarPic.setHeight(20);
        
        envPic = new Picture("envPic");
        envPic.setImage(assetManager, "Textures/earth.png", true);
        envPic.setWidth(20);
        envPic.setHeight(20);
        
        halfWidth = settings.getWidth()/2;
        
        money.setSize(font.getCharSet().getRenderedSize());      
        money.setColor(ColorRGBA.Blue);                            
        money.setText("$ Money HUD zone");        
        
        env.setSize(font.getCharSet().getRenderedSize());      
        env.setColor(ColorRGBA.Blue);                            
        env.setText("! Carbone HUD Zone");    
                
        attachHudElements();
    }
    
    public void update() {
        String moneyText = Integer.toString(gameStatusController.getMoney());
        String envText = Integer.toString(gameStatusController.getEnvRating());
        
        money.setText(moneyText);
        env.setText(envText);
        
        attachHudElements();
    }
    
    private void attachHudElements(){
        float moneyAnchor = halfWidth - money.getLineWidth(); 
        float moneyPicAnchor = moneyAnchor - 25;
        float envPicAnchor = 5 + halfWidth;
        float envAnchor = envPicAnchor + 25;

        dollarPic.setPosition(moneyPicAnchor, 5);
        envPic.setPosition(envPicAnchor, 5);

        money.setLocalTranslation(moneyAnchor, money.getLineHeight(), 5); 
        env.setLocalTranslation(envAnchor, money.getLineHeight(), 5); 
        
        this.detachAllChildren();
        this.attachChild(money);
        this.attachChild(env);
        this.attachChild(dollarPic);
        this.attachChild(envPic);
    }
    
    public BitmapText getMoney() {
        return money;
    }

    public void setMoney(BitmapText money) {
        this.money = money;
    }

    public Picture getDollarPic() {
        return dollarPic;
    }

    public void setDollarPic(Picture dollarPic) {
        this.dollarPic = dollarPic;
    }

    public BitmapText getEnv() {
        return env;
    }

    public void setEnv(BitmapText env) {
        this.env = env;
    }

    public Picture getEnvPic() {
        return envPic;
    }

    public void setEnvPic(Picture envPic) {
        this.envPic = envPic;
    }

    public GameStatusController getGameStatusController() {
        return gameStatusController;
    }

    public void setGameStatusController(GameStatusController gameStatusController) {
        this.gameStatusController = gameStatusController;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public void setHalfWidth(float halfWidth) {
        this.halfWidth = halfWidth;
    }
}
