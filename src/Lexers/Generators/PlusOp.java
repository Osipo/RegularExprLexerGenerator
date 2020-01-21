package Lexers.Generators;
import java.util.List;
import java.util.ArrayList;
public class PlusOp extends RegularSymbol {
    private String op1;
    
    public PlusOp(String text){
        super(text, AstNodeType.PLUSOP);
        this.op1 = "";
    }
    
    public PlusOp(String text, String op1){
        super(text, AstNodeType.PLUSOP);
        this.op1 = op1;
    }
    public String getOp1(){
        return op1;
    }
    
    @Override
    public boolean nullable(){
        return false;
    }
}