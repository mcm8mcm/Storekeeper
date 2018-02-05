package ua.in.algoritm.storekeeper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MCM on 04.02.2018.
 */

public class Inventorization {

    public class DocLine{
        private int LineNum = 0;
        private Commodity Stc = null;
        private CommodityUnit Unit = null;
        private double Coef = 0.0;
        private double Qty = 0.0;

        public DocLine(Commodity Stc){
            setCommodity(Stc);
            this.Qty = 1.0;
        }

        public int getLineNum(){
            return this.LineNum;
        }

        public void setLineNum(int LineNum){
            this.LineNum = LineNum;
        }

        public void setCommodity(Commodity Stc){
            this.Stc = Stc;
            this.Unit = Stc.getDefaultUnit();
            this.Coef = this.Unit.getCoef();
        }
    }

    private int ID = 0;
    private Date DocDate = null;
    private String DocNum = null;
    private String Comment = null;

    private ArrayList<DocLine> Lines = null;

    public void addLine(Commodity Stc, CommodityUnit Unit, double Qty){
        if(FindLine(Stc) == 0){

        }
    }

    private void Renum(){
        int LineNum = 0;
        for (DocLine Line: Lines) {
            Line.LineNum = ++LineNum;
        }
    }

    private int FindLine(Commodity Stc){
        for (DocLine Line: Lines) {
            if(Line.Stc.equals(Stc)){
                return Line.getLineNum();
            }
        }

        return 0;
    }
}
