package Lexers.Generators;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class RegularExpressionsG {
        //GRAMMAR: 
        //symbol -> characterOrDigit.
        //expr -> expr or term | term
        //term -> term^factor | factor
        //factor -> factor* | factor+ | el
        //el -> (expr) | symbol.
        
        //Delete Left recursion...
        
        // expr -> term rest
        //rest -> or term rest | empty
        //term -> factor rest2
        //rest2 -> ^factor rest2 | empty
        //factor -> el rest3
        //rest3 -> * rest3 | + rest3
        
        
        //================ GRAMMAR for LL(1) ===========================//
        //expr -> regex
        //regex -> term (or term)*
        //term -> factor (^ factor)*
        //factor -> el ((+|*) )*
        //el -> (expr) | symbol
        //prog -> el.
        //S -> prog  (Стартовый символ грамматики.)
        
	//Examples:
	// ((a|b)*)
	// (a^b^c^....^n)
	// (((a*)^(b*))^c^d).
	// a
	// (((a|b)*)^a^b^b)
        //==================FIRST(a) sets where a is a terminal or Nonterminal.//
        //====FIRST and FOLLOW sets are used to create syntax table for Non-recursive LL parser.
        //FIRST(E) = FIRST(T) = FIRST(F) = FIRST(EL) = { (, symbol }
        //FIRST(R) = {or,empty} // r == rest.
        //FIRST(R2) = {^,empty}
        //FIRST(R3) = {*,+}
          
    private String source; //Regex
    private char symbol; //symbol in source.
    private int pos;//scaning position.
    
    private String error;
    
    private int sp;//syntax pos for regex symbols.
    private HashMap<Character,String> symbolPoses;
    public RegularExpressionsG(String input){
        this.source = input == null ? "" : input;
        this.source = input+"#";
        this.pos = 0;
        this.symbol = source.charAt(pos);
        this.error = "";
        this.sp = 0;
        this.symbolPoses = new HashMap<Character,String>();
    }
    
    public int getSp(){
    	return sp;
    }
    
    
    /*
    public void setSp(int inc){
        this.sp += inc;
    }*/
    public int getPos(){
        return pos;
    }
    
    public HashMap<Character,String> getSymbolsPoses(){
        return symbolPoses;
    }
    
    public AstNode program() throws Exception{
        sp = 0;
        if(source.length() == 1){
            error = "Syntax error";
            return null;
        }
        AstNode p = new ConcatOp("^",source.substring(0,source.length()-1),"#");
        while(pos < source.length()-1){
            p.addChild(el());
            pos++;
        }
        p.addChild(new RegularSymbol(sp,"#"));
        return p;
    }
    
    private AstNode el() throws Exception{
        if(Character.isLetter(symbol)){
        String p = symbolPoses.get(symbol) == null ? "" : symbolPoses.get(symbol);
        symbolPoses.put(symbol,p+sp+"");
	    match(symbol);
            System.out.println("RegSymbol FOUND on : "+pos);
            AstNode r = new RegularSymbol(sp++,symbol+"");
            return r;
        }
        else if(symbol == '$'){
            match(symbol);
            AstNode r = new RegularSymbol(symbol+"",AstNodeType.EMPTYS);
            return r;
        }
        else if(symbol == '('){
            match('(');
            AstNode r = expr();
            match(')');
            return r;
        }
        else {
            error = "Syntax error";
            throw new Exception(error); 
        }
    }
    
    private AstNode expr() throws Exception{
        return regex();
    }
    
    private AstNode regex() throws Exception{
        AstNode result = term();
        while(symbol == '|'){
            match('|');
            AstNode t = term();
            result = new UnionOp("|",result,t);
        }
        return result;
    }
    
    private AstNode term() throws Exception{
        AstNode result = factor();
        while(symbol == '^'){
            match('^');
            AstNode t = factor();
            result = new ConcatOp("^",result,t);
        }
        return result;
    }
    private AstNode factor() throws Exception{
        AstNode r1 = el();
        AstNode result = r1;
        while(symbol == '+' || symbol == '*'){
            char op = symbol;
            match(symbol);
            //AstNode t = el();
            result = op == '+' ? new PlusOp("+") : new StarOp("*");
            result.addChild(r1);
            //result.addChild(t);
        }
        return result;
    }
    private boolean match(char t) throws Exception{
        if(this.symbol == t){
            symbol = this.source.charAt(++pos);
            return true;
        }
        else{
            error = "Syntax error";
            throw new Exception(error); //throw Exception.
        }
            
    }
}