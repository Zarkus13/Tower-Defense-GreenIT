/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.messaging;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alexis
 */
public class Message {
    private MessageType type;
    private Map<String, Object> modifications;
    
    public Message(MessageType type) {
        this.type = type;
    }
    
    public Map<String, Object> getModifications() {
        if(this.modifications == null) {
            this.modifications = new HashMap<String, Object>();
        }
        return modifications;
    }

    public void setModifications(Map<String, Object> modifications) {
        this.modifications = modifications;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
