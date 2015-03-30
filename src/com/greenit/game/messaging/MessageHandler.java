/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.messaging;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alexis
 */
public class MessageHandler {
    private static Map<MessageType, List<Observer>> observers;
    
    // Bloc d'initialisation static pour initialiser la collection d'observers
    static {
        // Utilisation d'un EnumMap pour plus d'efficacité
        observers = new EnumMap<MessageType, List<Observer>>(MessageType.class);
    }
    
    // Constructeur en private pour éviter l'instanciation
    private MessageHandler() {}

    // Envoie du message à tous les objets s'étant inscrit à ce type de message
    public static void sendMessage(Message message) {
        if(observers.containsKey(message.getType())){
            for(Observer obs : observers.get(message.getType()))
            {
                obs.onReceive(message);
            }
        }
    }
    
    public static void addObserver(MessageType type, Observer observer) {
        /*
         * S'il existe déjà une liste d'observers pour ce type de message, alors
         * on rajoute simplement l'objet à la liste.
         * Sinon on crée cette liste pour ce type de message.
         */
        if(observers.containsKey(type)) 
        {
            observers.get(type).add(observer);
        }
        else
        {
            observers.put(type, new ArrayList<Observer>());
            observers.get(type).add(observer);
        }
    }
    
    // Suppression de l'observer donné pour le type de message donné
    public static void removeObserver(MessageType type, Observer observer) {
        observers.get(type).remove(observer);
    }
}
