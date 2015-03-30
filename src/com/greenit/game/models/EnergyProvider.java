/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author Oli
 */
public class EnergyProvider {
    private String name;
    private float bill;
    private float availability;
    private EnergyProviderType type;
    private float envImpact;

    public EnergyProvider(String name, float bill, float availability,
            EnergyProviderType type, float envImpact) {
        this.name = name;
        this.bill = bill;
        this.availability = availability;
        this.type = type;
        this.envImpact = envImpact;
    }

    public float getAvailability() {
        return availability;
    }

    public void setAvailability(float availability) {
        this.availability = availability;
    }

    public float getBill() {
        return bill;
    }

    public void setBill(float bill) {
        this.bill = bill;
    }

    public float getEnvImpact() {
        return envImpact;
    }

    public void setEnvImpact(float envImpact) {
        this.envImpact = envImpact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnergyProviderType getType() {
        return type;
    }

    public void setType(EnergyProviderType type) {
        this.type = type;
    }
}