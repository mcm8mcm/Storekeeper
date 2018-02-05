package ua.in.algoritm.storekeeper;

import java.util.ArrayList;

/**
 * Created by MCM on 04.02.2018.
 */

public class Commodity {
    private int ID;
    private String OuterID;
    private String Name;
    private String FullName;

    private CommodityUnit DefaultUnit = null;
    private ArrayList<CommodityUnit> Units = null;

    public CommodityUnit getDefaultUnit(){
        return this.DefaultUnit;
    }

    public int getID(){
        return this.ID;
    }

    @Override
    public boolean equals(Object o){
        return this.ID == ((Commodity)o).ID;
    }
}
