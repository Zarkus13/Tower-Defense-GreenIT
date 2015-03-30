/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author Oli
 */
public class Request {
    private RequestType type;
    private byte complexity;
    private byte importance;
    private int award;
    
    public Request(RequestType type, byte complexity,
            byte importance, short award) {
        this.type = type;
        this.complexity = complexity;
        this.importance = importance;
        this.award = award;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public byte getComplexity() {
        return complexity;
    }

    public void setComplexity(byte complexity) {
        this.complexity = complexity;
    }

    public short getImportance() {
        return importance;
    }

    public void setImportance(byte importance) {
        this.importance = importance;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }
}
