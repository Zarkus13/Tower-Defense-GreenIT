/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author oli
 */
public class Bonus {
    private String name;
    private String target;
    private String targetAttribute;
    private float value;
    private byte duration;
    
    public Bonus(String name, String target, String targetAttribute, float value) {
        this.name = name;
        this.target = target;
        this.targetAttribute = targetAttribute;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTargetAttribute() {
        return targetAttribute;
    }

    public void setTargetAttribute(String targetAttribute) {
        this.targetAttribute = targetAttribute;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public byte getDuration() {
        return duration;
    }

    public void setDuration(byte duration) {
        this.duration = duration;
    }

    @Override
    public Bonus clone() {
        Bonus bonus = new Bonus(
                this.name, 
                this.target, 
                this.targetAttribute, 
                this.value);
        return bonus;
    }
    
    
}   
