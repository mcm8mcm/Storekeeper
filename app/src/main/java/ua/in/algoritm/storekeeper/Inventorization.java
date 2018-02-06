package ua.in.algoritm.storekeeper;

import java.util.ArrayList;
import java.util.Calendar;
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
            this.Qty = 0.0;
        }

        public int getLineNum(){
            return this.LineNum;
        }

        public void setLineNum(int LineNum){
            this.LineNum = LineNum;
        }

        public double getQty(){
            return this.Qty;
        }

        public void setQty(double Qty){
            this.Qty = Qty;
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
        DocLine Line = null;
        int Index = FindLine(Stc);
        if(Index == 0){
            Line = new DocLine(Stc);
            Line.setLineNum(Index + 1);
            Lines.add(Line);
        }else{
            Line = Lines.get(Index);
        }

        Line.setQty(Line.getQty() + Qty);
        Lines.set(Index, Line);//Maybe, it is redundant expression
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
                return Line.getLineNum() - 1;
            }
        }

        return 0;
    }

    public void delLine(int Index){
        if(Lines.isEmpty()){
            return;
        }

        if(Index >= Lines.size()){
            return;//Index too big
        }

        Lines.remove(Index);
        Renum();
    }

    public ArrayList<DocLine> getLines(){
        return Lines;
    }

    public String getComment(){
        return Comment;
    }

    public void setComment(String Comment){
        this.Comment = Comment;
    }

    public void setQty(int Index, double Qty){
        if(Index >= Lines.size()){
            return;//It's a mistake
        }

        if(Qty == 0){
            delLine(Index);
            return;
        }

        Lines.get(Index).Qty = Qty;
    }

    public int getLinesCount(){
        return Lines.size();
    }

    public void newDoc(int ID){
        Lines = new ArrayList<DocLine>();
        DocNum = "";
        DocDate = Calendar.getInstance().getTime();
        Comment = "";
        this.ID = ID;
    }
}
