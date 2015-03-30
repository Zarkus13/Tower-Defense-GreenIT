/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author Oli
 */
public abstract class Equipment {
    private String brand;
    private String model;
    private short usury;
    private int consumption;
    private String comments;

    public Equipment(String brand, String model, short usury, int consumption, String comments) {
        this.brand = brand;
        this.model = model;
        this.usury = usury;
        this.consumption = consumption;
        this.comments = comments;
    }

    
    
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public short getUsury() {
        return usury;
    }

    public void setUsury(short usury) {
        this.usury = usury;
    }
}
