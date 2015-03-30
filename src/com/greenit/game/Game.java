/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game;

import com.greenit.game.controllers.EnergyController;
import com.greenit.game.controllers.EnergyProviderActionListener;
import com.greenit.game.controllers.ExitButtonActionListener;
import com.greenit.game.controllers.GameController;
import com.greenit.game.controllers.RequestCreationController;
import com.greenit.game.controllers.SlotsActionListener;
import com.greenit.game.messaging.Message;
import com.greenit.game.messaging.MessageHandler;
import com.greenit.game.messaging.MessageType;
import com.greenit.game.messaging.Observer;
import com.greenit.game.views.CaptureZone;
import com.greenit.game.views.RequestView;
import com.greenit.game.views.Slot;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexis
 */
public class Game extends Node implements Observer {
    // Pour la caméra et la zone de jeu

    public static final float distCam = 10;
    public static final float angleCam = 0.206f;
    private static Float ratioPixF;
    private static Float hFloor;
    private static Float lFloor;
    public static final float percHPath = 0.40f; // Entre 0 et 1
    public static final float percHRequests = 0.03f;
    public static final float percHSlots = 0.07f;
    private static Float topPath;
    private static Float botPath;
    private static Float fontSize;
    
    public static final ThreadGroup gameThreads = new ThreadGroup(GreenITApplication.allThreads, "Game");
    public static final ThreadGroup requestThreads = new ThreadGroup(Game.gameThreads, "Requests");
    public static final ThreadGroup centralUnitThreads = new ThreadGroup(Game.gameThreads, "CentralUnits");
    public static final ThreadGroup captureZoneThreads = new ThreadGroup(Game.gameThreads, "CaptureZonesAnimations");
    public static final ThreadGroup miniGoalThreads = new ThreadGroup(Game.gameThreads, "MiniGoals");
    public static final ThreadGroup equipmentThreads = new ThreadGroup(Game.gameThreads, "Equipments");
    
    private GameController gameController = new GameController();
    private RequestCreationController requestCreationController = new RequestCreationController(this);
    private GreenITApplication app;
    private List<CaptureZone> captureZones;
    private ArrayList<Float[]> captureZonesStrips;
    private List<Slot> slots;
    private int level;
    private Picture exitImg;

    public Game(GreenITApplication app, int level) {
        this.app = app;
        this.level = level;
        
        MessageHandler.addObserver(MessageType.DETACH, this);
        
        this.initGameBoard();
//        this.createExitButton();
        
        gameController.setLevel(level);
        gameController.start();
        requestCreationController.start();
        
        app.getInputManager().addMapping("click", new MouseButtonTrigger(0));
        app.getInputManager().addListener(new SlotsActionListener(this), "click");
//        app.getInputManager().addListener(new ExitButtonActionListener(this), "click");
        app.getInputManager().addListener(new EnergyProviderActionListener(this), "click");
    }

    public void onReceive(Message message) {
        RequestView req = (RequestView)message.getModifications().get("objectToDetach");
        if(req != null){
            detachChild(req);
            req.getController().interrupt();
        }
    }
    
    public void initGameBoard() {
        Box box = new Box(Vector3f.ZERO, getlFloor(), gethFloor(), 0);
        Geometry gameBoard = new Geometry("GameBoard", box);

        Material mat = new Material(GreenITApplication.getCurrentAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", GreenITApplication.getCurrentAssetManager().loadTexture("Textures/GameBoard3.jpg"));
        gameBoard.setMaterial(mat);

        this.attachChild(gameBoard);
        
        CaptureZonesGenerator czg = new CaptureZonesGenerator(this);
        czg.placeCaptureZones(8);
        
        SlotsGenerator sg = new SlotsGenerator(this);
        sg.placeSlots(8);
        
        EnergyController ec = new EnergyController();
        
        EnergyProviderGenerator epg = new EnergyProviderGenerator(this);
        epg.placeEnergyProviders(2);
    }
    
    public void createExitButton() {
        float height = Game.percHSlots * app.getSettings().getHeight();
        
        exitImg = new Picture("Exit button");
        exitImg.setImage(app.getAssetManager(), "Textures/ExitButton.png", true);
        exitImg.setWidth(distCam);
        exitImg.setHeight(height);
        exitImg.setWidth(height*3);
        exitImg.setPosition(app.getSettings().getWidth() - 3*height, app.getSettings().getHeight() - height);
        
        app.getGuiNode().attachChild(exitImg);
    }
    
    /*** GETTERS AND SETTERS ***/
    
    public static Float getRatioPixF() {
        if (ratioPixF == null) {
            ratioPixF = GreenITApplication.heightScreen / hFloor;
        }

        return ratioPixF;
    }

    public static Float gethFloor() {
        if (hFloor == null) {
            hFloor = (float) (2 * distCam * Math.tan(angleCam));
        }
        return hFloor;
    }

    public static Float getlFloor() {
        if (lFloor == null) {
            lFloor = (GreenITApplication.widthScreen * gethFloor() / GreenITApplication.heightScreen);
        }
        return lFloor;
    }

    public static Float getBotPath() {
        if(botPath == null) {
            botPath = -(Game.gethFloor() * Game.percHPath);
        }
        return botPath;
    }

    public static Float getTopPath() {
        if(topPath == null) {
            topPath = (Game.gethFloor() * Game.percHPath);
        }
        return topPath;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GreenITApplication getApp() {
        return app;
    }


    public List<CaptureZone> getCaptureZones() {
        return captureZones;
    }

    public void setCaptureZones(List<CaptureZone> captureZones) {
        this.captureZones = captureZones;
    }

    public ArrayList<Float[]> getCaptureZonesStrips() {
        return captureZonesStrips;
    }

    public void setCaptureZonesStrips(ArrayList<Float[]> captureZonesStrips) {
        this.captureZonesStrips = captureZonesStrips;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public Picture getExitImg() {
        return exitImg;
    }

    public static Float getFontSize() {
        if(fontSize == null) {
            fontSize = 2*GreenITApplication.getApp().getGuiFont().getCharSet().getRenderedSize()*Game.getlFloor()/GreenITApplication.getApp().getSettings().getWidth();
        }
        return fontSize;
    }
}
