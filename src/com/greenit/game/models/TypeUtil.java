/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.greenit.game.models;

/**
 *
 * @author oli
 */
public class TypeUtil {
    
    public static RequestType requestTypeFromString(String str){
        if(str.equalsIgnoreCase(RequestType.DNS.toString())){
            return RequestType.DNS;
        } else if (str.equalsIgnoreCase(RequestType.FTP.toString())){
            return RequestType.FTP;
        } else if (str.equalsIgnoreCase(RequestType.HTTP.toString())){
            return RequestType.HTTP;
        } else if (str.equalsIgnoreCase(RequestType.IMAP.toString())){
            return RequestType.IMAP;
        } else if (str.equalsIgnoreCase(RequestType.SIP.toString())){
            return RequestType.SIP;
        } else if (str.equalsIgnoreCase(RequestType.SQL.toString())){
           return RequestType.SQL; 
        } else {
            return null;
        }
    }
    
    public static EnergyProviderType energyProviderTypeFromString(String str){
        if(str.equalsIgnoreCase(EnergyProviderType.FUEL.toString())){
            return EnergyProviderType.FUEL;
        } else if(str.equalsIgnoreCase(EnergyProviderType.SOLAR.toString())){
            return EnergyProviderType.SOLAR;
        }  else if(str.equalsIgnoreCase(EnergyProviderType.NUCLEAR.toString())){
            return EnergyProviderType.NUCLEAR;
        }  else if(str.equalsIgnoreCase(EnergyProviderType.WIND.toString())){
            return EnergyProviderType.WIND;
        } else {
            return null;
        }
    }
    
}
