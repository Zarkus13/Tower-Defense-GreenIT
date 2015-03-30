/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.messaging;

/**
 *
 * @author Alexis
 */
public enum MessageType {
    HANDLED_REQUEST,
    FAILED_REQUEST,
    
    MODIF_RATES,
    
    CASHFLOW,
    ENVRATING,
    
    CHANGEPROVIDER,
    POWEROUTAGE,
    CONSUMPTION,
    
    GAMEOVER,
    
    BONUS,
    BONUS_ENABLED,
    BONUS_DISABLED,
    
    DETACH
}
