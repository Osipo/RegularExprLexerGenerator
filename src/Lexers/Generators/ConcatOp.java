package Lexers.Generators;
import java.util.List;
import java.util.ArrayList;
public class ConcatOp extends RegularSymbol {
    private String op1;
    private String op2;
    public ConcatOp(String text){
        super(text, AstNodeType.CONCAT);
        this.op1 = "";
        this.op2 = "";
    }
    public ConcatOp(String text, String op1, String op2){
        super(text, AstNodeType.CONCAT);
        this.op1 = op1;
        this.op2 = op2;
    }
    public ConcatOp(String text, AstNode c1, AstNode c2){
        super(text, AstNodeType.CONCAT);
    	addChild(c1);
        addChild(c2);
    }

    public String getOp1(){
        return op1;
    }
    public String getOp2(){
    	return op2;
    }
    
    @Override
    public boolean nullable(){
        return children.get(0).nullable() && children.get(1).nullable();
    }
}