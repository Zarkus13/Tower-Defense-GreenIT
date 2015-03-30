/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author oli
 */
public class PurchasableItem {
    private int price;

    public PurchasableItem(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
