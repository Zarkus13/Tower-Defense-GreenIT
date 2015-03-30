package com.greenit.game;

import com.greenit.game.controllers.GameStatusController;
import com.greenit.game.data.DataLoader;
import com.greenit.game.gui.MainFrame;
import com.greenit.game.views.GameStatusView;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oli
 */
public class GreenITApplication extends SimpleApplication {

    public static float tpf;
    public static int widthScreen;
    public static int heightScreen;
    public static final int FPS = 30;
    public static final int WAITANIM = 1000 / FPS;
    private static GreenITApplication app;
    private static AssetManager currentAssetManager;
    public static final ThreadGroup allThreads = new ThreadGroup("All");
    private Game game;
    private Nifty nifty;
    private GameStatusView hud;

    // Constructeur en private pour éviter l'instanciation
    private GreenITApplication() {
    }

    // Factory pour récupérer tout le temps la même instance de GreenITApplication
    public static GreenITApplication getApp() {
        if(app == null) {
            GreenITApplication.app = new GreenITApplication();
        }
        return app;
    }

    @Override
    public void simpleInitApp() {
        app = this;
        
        Logger.getLogger("com.jme3").setLevel(Level.SEVERE);

        GreenITApplication.currentAssetManager = this.assetManager;

        GreenITApplication.widthScreen = settings.getWidth();
        GreenITApplication.heightScreen = settings.getHeight();

        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(0, 0, Game.distCam));
        cam.lookAt(new Vector3f(0, 0, -1), Vector3f.UNIT_Y);
        
        createNewGame();

//        openMenu();
    }

    private void openMenu() {

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        /** Creation de l'object Nifty */
        nifty = niftyDisplay.getNifty();

        /** Lecture du fichier xml  */
        nifty.fromXmlWithoutStartScreen("Interface/greenItGui.xml");
        nifty.fromXmlWithoutStartScreen("Interface/intro.xml");

        /** Initialisation */
        nifty.gotoScreen("intro");
        guiViewPort.addProcessor(niftyDisplay);
    }

    private void generateHud() {
        hud = new GameStatusView(this.assetManager, this.settings);
        guiNode.detachAllChildren();
        guiNode.attachChild(hud);
    }

    @Override
    public void simpleUpdate(float tpf) {
        GreenITApplication.tpf = tpf;

        if (hud != null) {
            hud.update();
        }
    }

    @Override
    public void destroy() {
        GreenITApplication.getApp().getGame().detachAllChildren();
        GreenITApplication.getApp().getGuiNode().detachAllChildren();
        GreenITApplication.allThreads.interrupt();
        GameStatusController.endGame();
        GreenITApplication.app.getAudioRenderer().cleanup();
        
        GreenITApplication.app = null;
    }

    public static AssetManager getCurrentAssetManager() {
        return GreenITApplication.currentAssetManager;
    }

    public void createNewGame() {
        
        generateHud();

        this.game = new Game(this, 2);
        rootNode.attachChild(this.game);
    }

    public AppSettings getSettings() {
        return settings;
    }

    public Nifty getNifty() {
        return nifty;
    }

    public Game getGame() {
        return game;
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }
}