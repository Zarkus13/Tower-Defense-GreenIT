/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author oli
 */
public class BestPractice extends PurchasableItem {
    private String name;
    private int duration;
    private float envRate;
    private float moneyRate;
    private float consumptionRate;

    public BestPractice(String name, int duration, float envRate, 
            float moneyRate, float consumptionRate, int price) {
        super(price);
        this.name = name;
        this.duration = duration;
        this.envRate = envRate;
        this.moneyRate = moneyRate;
        this.consumptionRate = consumptionRate;
    }

    public float getConsumptionRate() {
        return consumptionRate;
    }

    public void setConsumptionRate(float consumptionRate) {
        this.consumptionRate = consumptionRate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getEnvRate() {
        return envRate;
    }

    public void setEnvRate(float envRate) {
        this.envRate = envRate;
    }

    public float getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(float moneyRate) {
        this.moneyRate = moneyRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
