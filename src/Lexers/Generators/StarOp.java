package Lexers.Generators;
import java.util.List;
import java.util.ArrayList;
public class StarOp extends RegularSymbol {
    private String op1;
    
    public StarOp(String text){
        super(text, AstNodeType.STAROP);
        this.op1 = "";
    }
    
    public StarOp(String text, String op1){
        super(text, AstNodeType.STAROP);
        this.op1 = op1;
    }
    public String getOp1(){
        return op1;
    }
    
    @Override
    public boolean nullable(){
        return true;
    }
}