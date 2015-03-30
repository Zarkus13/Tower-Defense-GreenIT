/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game;

/**
 *
 * @author Alexis
 */
public class UtilsGreenIT {
    public static final int widthScreen = 1280;
    public static final int heightScreen = 720;
    public static final float percHPath = 0.40f; // Entre 0 et 1
    public static final float percHRequests = 0.03f;
    public static final float percHSlots = 0.07f;
    public static final int heightButtons = (int) (heightScreen*percHSlots);
    public static final int widthButtons = (int) (3*heightScreen*percHSlots);
    public static final int heightItemsMenus = (int) (heightButtons*1.5f);
    public static final int minWidthMenus = (int) (widthScreen*0.3f);
    public static final int minHeightMenus = (int) (heightScreen*0.5f);
}
