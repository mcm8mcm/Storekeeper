package ua.in.algoritm.storekeeper;

import android.content.Context;

/**
 * Created by MCM on 04.02.2018.
 */

public class DataProvider {
    private Inventorization CurrDoc = null;
    private Context currContext;
    private DataProvider MainDataProvider = new DataProvider(currContext);

    public Inventorization getCurrDoc(){
        return this.CurrDoc;
    }

    public DataProvider(Context context){
        currContext = context;
    }

    public void createNewDoc(){

    }

    public void saveDoc(){

    }


}
