/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author Oli
 */
public class CentralUnit extends PurchasableItem {
       private String brand;
       private String model;
       private RequestType requestType;
       private int consumption;
       private int envTax;
       
       private int maxLoad;
       private float currentLoad;
       private int processorSpeed;
       
       private int usury;
       private int usuryThreshold;
       
       private int nbSlot;

    public CentralUnit(String brand, String model, 
            int consumption, int maxLoad, int processorSpeed, 
            int usuryThreshold, int nbSlot, int price) {
        super(price);
        this.currentLoad = 0;
        this.usury = 0;
        
        this.brand = brand;
        this.model = model;
        this.consumption = consumption;
        
        this.maxLoad = maxLoad;
        this.processorSpeed = processorSpeed;
        
        this.usuryThreshold = usuryThreshold;
        
        this.nbSlot = nbSlot;
        
        this.envTax = this.consumption*this.maxLoad*
                this.processorSpeed*10;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public float getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(float currentLoad) {
        this.currentLoad = currentLoad;
    }

    public int getEnvTax() {
        return envTax;
    }

    public void setEnvTax(int envTax) {
        this.envTax = envTax;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNbSlot() {
        return nbSlot;
    }

    public void setNbSlot(int nbSlot) {
        this.nbSlot = nbSlot;
    }

    public int getProcessorSpeed() {
        return processorSpeed;
    }

    public void setProcessorSpeed(int processorSpeed) {
        this.processorSpeed = processorSpeed;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public int getUsury() {
        return usury;
    }

    public void setUsury(int usury) {
        this.usury = usury;
    }

    public int getUsuryThreshold() {
        return usuryThreshold;
    }

    public void setUsuryThreshold(int usuryThreshold) {
        this.usuryThreshold = usuryThreshold;
    }

    @Override
    public CentralUnit clone() {
        CentralUnit unit = new CentralUnit(
                this.brand, 
                this.model, 
                this.consumption, 
                this.maxLoad, 
                this.processorSpeed, 
                this.usuryThreshold, 
                this.nbSlot, 
                this.getPrice());
        return unit;
    }
}
