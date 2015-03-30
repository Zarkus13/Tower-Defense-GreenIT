/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.data;

import com.greenit.game.i18n.LocalizationUtil;
import com.greenit.game.models.BestPractice;
import com.greenit.game.models.Bonus;
import com.greenit.game.models.CentralUnit;
import com.greenit.game.models.EnergyProvider;
import com.greenit.game.models.EnergyProviderType;
import com.greenit.game.models.MiniGoal;
import com.greenit.game.models.RequestType;
import com.greenit.game.models.TypeUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Locale;

/**
 *
 * @author oli
 */
public class DataLoader {
    
    public void load(){
        loadFile("centralunits");
        loadFile("bestpractices");
        loadFile("bonus");
        loadFile("mgmtpolicies");
        loadFile("energyprovider");
        loadFile("minigoal");
        loadFile("alternatesource");
        loadFile("minigoal");
    }
    
    private void loadFile(String path){
        try{
            URL url = getClass().getResource(path);
            FileReader in = new FileReader(new File(url.toURI()));
            BufferedReader reader = new BufferedReader(in);
            String currentLine = reader.readLine();
            
            while(currentLine != null){
                if(!currentLine.startsWith("#")) {
                    String[] values = currentLine.split(",");
                    createObjects(values, path);
                }
                currentLine = reader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        checkData();
    }
    
    private void createObjects(String[] values, String path){
        if(path.equals("centralunits"))
        {
            
            String name = values[0];
            String model = values[1];
            int maxLoad = Byte.parseByte(values [2]);
            int processorSpeed = Byte.parseByte(values[3]);
            int consumption = Byte.parseByte(values [4]);
            int usuryThreshold = Short.parseShort(values[5]);
            int nbSlot = Byte.parseByte(values[6]);	
            int price = Short.parseShort(values[7]);
            
            CentralUnit currentUnit = new CentralUnit(name, model, consumption, maxLoad, 
                    processorSpeed, usuryThreshold, nbSlot, price);
            DataAccess.getCentralUnits().add(currentUnit);
 
        } 
        else if (path.equals("bonus"))
        {
            String name = values[0];
            String target = values[1];
            String targetAttribute = values[2];	
            float value = Float.parseFloat(values[3]);
            
            Bonus bonus = new Bonus(name, target, targetAttribute, value);
            DataAccess.getBonus().add(bonus);
            
        } else if (path.equals("bestpractices")){
            String name = values[0];
            int price = Integer.parseInt(values[1]);
            float envRate = Float.parseFloat(values[2]);
            float consumptionRate = Float.parseFloat(values[3]);
            float moneyRate = Float.parseFloat(values[4]);
            int duration = Integer.parseInt(values[5]);
            
            BestPractice bestPractice = new BestPractice(name, duration, envRate, 
                    moneyRate, consumptionRate,  price);
            DataAccess.getBestPractices().add(bestPractice);
            
        } else if (path.equals("alternatesource") || path.equals("energyprovider")){
            String name = values[0];
            EnergyProviderType type = TypeUtil.energyProviderTypeFromString(values[1]);
            float bill = Float.parseFloat(values[2]);
            float availability = Float.parseFloat(values[3]);
            float envImpact = Float.parseFloat(values[4]);
            EnergyProvider energyProvider = new EnergyProvider(name, bill, availability, type, envImpact);
            if(path.equals("alternatesource")){
                DataAccess.getAlternateSources().add(energyProvider);
            } else {
                DataAccess.getEnergyProviders().add(energyProvider);
            }
        } if (path.equals("minigoal")){
            String name = values[0];
            boolean survival = false;
            if(values[1].equalsIgnoreCase("survival")){
                survival = true;
            }
            RequestType type = TypeUtil.requestTypeFromString(values[2]);
            String desc = values[3];
            MiniGoal miniGoal = new MiniGoal(survival, desc, name, type);
            DataAccess.getMiniGoals().add(miniGoal);
        }
    }
    
    private void checkData(){
        System.out.println("====== BONUS =======");
        for(Bonus bonus : DataAccess.getBonus()){
            System.out.println(bonus.getName());
        }
        System.out.println("=== BEST PRACTICES ===");
        for(BestPractice bestPractice : DataAccess.getBestPractices()){
            System.out.println(bestPractice.getName());
        }
        System.out.println("=== ALTERNAE SOURCES ===");
        for(EnergyProvider ep : DataAccess.getEnergyProviders()){
            System.out.println(ep.getName());
        }
        System.out.println("=== ENERGY PROVIDER ===");
        for(EnergyProvider ap : DataAccess.getEnergyProviders()){
            System.out.println(ap.getName());
        }
        System.out.println("=== MINIGOALS EN ===");
        LocalizationUtil.setLocale(new Locale("en", "US"));
        for(MiniGoal mg : DataAccess.getMiniGoals()){
            System.out.println(LocalizationUtil.getString(mg.getDecription()));
        }
        System.out.println("=== MINIGOALS FR ===");
        LocalizationUtil.setLocale(new Locale("fr", "FR"));
        for(MiniGoal mg : DataAccess.getMiniGoals()){
            System.out.println(LocalizationUtil.getString(mg.getDecription()));
        }
        
    }
}
