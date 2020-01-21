package Lexers.Generators;
import java.util.List;
import java.util.ArrayList;
public class RegularSymbol extends AstNode {
    
    private int position; //syntax position for symbols (not for operators).
    
    protected ArrayList<Integer> firstP;
    protected ArrayList<Integer> lastP;
    protected boolean isNull = false;
    
    public RegularSymbol(String text){
	    super(AstNodeType.END,text);
        this.position = 0;
        firstP = new ArrayList<Integer>(10);
        lastP = new ArrayList<Integer>(10);
    }
    
    protected RegularSymbol(String text, int type){
    	super(type, text);
        this.position = 0;
        firstP = new ArrayList<Integer>(10);
        lastP = new ArrayList<Integer>(10);
    }

    public RegularSymbol(int p, String text){
            super(AstNodeType.END,text);
            this.position = p;
            firstP = new ArrayList<Integer>(10);
            lastP = new ArrayList<Integer>(10);
            firstP.add(p);
            lastP.add(p);
        
    }

    public void setSyntaxPos(int p){
    	this.position = p;
    }    

    public int getSyntaxPos(){
        return position;
    }
    
    public void addFirstP(int p){
        firstP.add(p);
    }
    
    public void addLastP(int p){
        lastP.add(p);
    }
    
    public ArrayList<Integer> getFirstPos(){
        return firstP;
    }
    public ArrayList<Integer> getLastPos(){
        return lastP;
    }
    
    public void setFirstPos(ArrayList<Integer> firsts){
       
        this.firstP.addAll(firsts);
    }
    public void setLastPos(ArrayList<Integer> lasts){
       
        this.lastP.addAll(lasts);
    }
    
    public void setNullable(){
        this.isNull = true;
    }
    
    public void setNullable(boolean b){
        this.isNull = b;
    }
    
    public boolean getNullable(){
        return isNull;
    }
    
    @Override
    public boolean nullable(){
        return false;
    }
}