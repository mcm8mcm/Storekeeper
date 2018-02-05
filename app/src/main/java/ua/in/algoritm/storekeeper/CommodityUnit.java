package ua.in.algoritm.storekeeper;

/**
 * Created by MCM on 04.02.2018.
 */

public class CommodityUnit {
    private int ID;
    private String UnitName = null;
    private double Weight = 0.0;
    private double Coef = 0.0;

    public double getCoef(){
        return this.Coef;
    }

    public double getWeight(){
        return this.Weight;
    }

    public String getName(){
        return this.UnitName;
    }

}
