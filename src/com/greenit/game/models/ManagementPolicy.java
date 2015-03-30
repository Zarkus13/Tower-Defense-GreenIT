/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author Oli
 */
public class ManagementPolicy {
    private String name;
    private int cost;
    private int envImpact;

    public ManagementPolicy(String name, int cost, int envImpact) {
        this.name = name;
        this.cost = cost;
        this.envImpact = envImpact;
    } 
    
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getEnvImpact() {
        return envImpact;
    }

    public void setEnvImpact(int envImpact) {
        this.envImpact = envImpact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}