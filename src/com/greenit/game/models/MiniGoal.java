/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author Oli
 */
public class MiniGoal {
    // static description get from file
    private boolean survivalGoal;
    private String decription;
    private String name;
    private RequestType typeToHandle;

    // dynamics values to generate when minigoal is enabled
    private int cashAward;
    private int envAward;
    private Bonus bonus;
    private int nbToHandle;

    public MiniGoal(boolean survivalGoal, String decription, String name, 
            RequestType typeToHandle) {
        this.survivalGoal = survivalGoal;
        this.decription = decription;
        this.name = name;
        this.typeToHandle = typeToHandle;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public int getCashAward() {
        return cashAward;
    }

    public void setCashAward(int cashAward) {
        this.cashAward = cashAward;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getEnvAward() {
        return envAward;
    }

    public void setEnvAward(int envAward) {
        this.envAward = envAward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 
    
    public int getNbToHandle() {
        return nbToHandle;
    }

    public void setNbToHandle(int nbToHandle) {
        this.nbToHandle = nbToHandle;
    }

    public RequestType getTypeToHandle() {
        return typeToHandle;
    }

    public void setTypeToHandle(RequestType typeToHandle) {
        this.typeToHandle = typeToHandle;
    }

    public boolean isSurvivalGoal() {
        return survivalGoal;
    }

    public void setSurvivalGoal(boolean survivalGoal) {
        this.survivalGoal = survivalGoal;
    }

    @Override
    public MiniGoal clone() {
        MiniGoal mg = new MiniGoal(
                this.survivalGoal, 
                this.decription, 
                this.name, 
                this.typeToHandle);
        return mg;
    }
    
    
}
