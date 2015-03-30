/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.data;

import com.greenit.game.models.BestPractice;
import com.greenit.game.models.Bonus;
import com.greenit.game.models.CentralUnit;
import com.greenit.game.models.EnergyProvider;
import com.greenit.game.models.MiniGoal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author oli
 */
public class DataAccess {
    private static List<CentralUnit> centralUnits;
    private static List<Bonus> bonus;
    private static List<BestPractice> bestPractices;
    private static List<EnergyProvider> energyProviders;
    private static List<EnergyProvider> alternateSources;
    private static List<MiniGoal> miniGoals;
    
    public static List<CentralUnit> getCentralUnits() {
        if(centralUnits == null){
            centralUnits = new ArrayList<CentralUnit>();
        }
        return centralUnits;
    }

    public static void setCentralUnits(List<CentralUnit> centralUnits) {
        DataAccess.centralUnits = centralUnits;
    }

    public static List<Bonus> getBonus() {
        if(bonus == null){
            bonus = new ArrayList<Bonus>();
        }
        return bonus;
    }

    public static void setBonus(List<Bonus> bonus) {
        DataAccess.bonus = bonus;
    }

    public static List<BestPractice> getBestPractices() {
        if(bestPractices == null){
            bestPractices = new ArrayList<BestPractice>();
        }
        return bestPractices;
    }

    public static void setBestPractices(List<BestPractice> bestPractices) {
        DataAccess.bestPractices = bestPractices;
    }

    public static List<EnergyProvider> getAlternateSources() {
        if(alternateSources == null){
            alternateSources = new ArrayList<EnergyProvider>();
        }
        return alternateSources;
    }

    public static void setAlternateSources(List<EnergyProvider> alternateSources) {
        DataAccess.alternateSources = alternateSources;
    }

    public static List<EnergyProvider> getEnergyProviders() {
        if(energyProviders == null){
            energyProviders = new ArrayList<EnergyProvider>();
        }
        return energyProviders;
    }

    public static void setEnergyProviders(List<EnergyProvider> energyProviders) {
        DataAccess.energyProviders = energyProviders;
    }

    public static List<MiniGoal> getMiniGoals() {
        if(miniGoals == null){
            miniGoals = new ArrayList<MiniGoal>();
        }
        return miniGoals;
    }

    public static void setMiniGoals(List<MiniGoal> miniGoals) {
        DataAccess.miniGoals = miniGoals;
    }
}
