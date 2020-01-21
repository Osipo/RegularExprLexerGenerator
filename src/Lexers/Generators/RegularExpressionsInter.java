package Lexers.Generators;
import java.util.List;
import java.util.Hashtable;
import java.util.ArrayList;
public class RegularExpressionsInter {
    
    private int sp;
    private Hashtable<Integer,ArrayList<Integer>> followpos;
    public RegularExpressionsInter(){
        this.sp = 0;
    }
    public RegularExpressionsInter(int sp){
        this.sp = sp;
        followpos = new Hashtable<Integer,ArrayList<Integer>>(sp);
        for(int i =0; i <sp;i++){
            followpos.put(i,new ArrayList<Integer>(sp*2));
        }
    }
    
    public Hashtable<Integer,ArrayList<Integer>> getFollow(){
        return followpos;
    }
    
   
    
    public void visit(RegularSymbol r){
        List<AstNode> children = r.getChildren();
        for(int i =0; i<children.size() && children.size()!=0;i++){
            RegularSymbol c = (RegularSymbol)children.get(i);
            visit(c); 
        }
        //System.out.println(r);
            
        if(r.getType() == AstNodeType.EMPTYS){
            r.setNullable(true);
        }
        else if(r.getType() == AstNodeType.SYMBOL || r.getType() == AstNodeType.END){
            r.setNullable(false);
        }
        else if(r.getType() == AstNodeType.UNION){
            r.setNullable(((RegularSymbol)r.getChildren().get(0)).getNullable() || ((RegularSymbol)r.getChildren().get(1)).getNullable());
            unitePos(r,true,0);
            unitePos(r,true,1);
        }
        else if(r.getType() == AstNodeType.CONCAT){
            r.setNullable(((RegularSymbol)r.getChildren().get(0)).getNullable() && ((RegularSymbol)r.getChildren().get(1)).getNullable());
            unitePos(r,((RegularSymbol)r.getChildren().get(0)).getNullable(),0);
            unitePos(r,((RegularSymbol)r.getChildren().get(1)).getNullable(),1);
            for(Integer i : ((RegularSymbol)r.getChildren().get(0)).getLastPos()){
                
                followpos.get(i).addAll(((RegularSymbol)r.getChildren().get(1)).getFirstPos());
            }
        }
        else if(r.getType() == AstNodeType.STAROP){
            r.setNullable(true);
            r.setFirstPos(((RegularSymbol)r.getChildren().get(0)).getFirstPos()); //Exception.
            r.setLastPos(((RegularSymbol)r.getChildren().get(0)).getLastPos());
            for(Integer i : r.getLastPos()){
                followpos.get(i).addAll(r.getFirstPos());
            }
        }
        else if(r.getType() == AstNodeType.PLUSOP){
            r.setNullable(false);
            r.setFirstPos(((RegularSymbol)r.getChildren().get(0)).getFirstPos());
            r.setLastPos(((RegularSymbol)r.getChildren().get(0)).getLastPos());
            for(Integer i : r.getLastPos()){
                followpos.get(i).addAll(r.getFirstPos());
            }
        }
    }
    
    
    
    
    private void unitePos(RegularSymbol r,boolean n,int opt){
        if(opt == 0){
            
            if(n){
                r.setFirstPos(((RegularSymbol)r.getChildren().get(0)).getFirstPos());
                r.getFirstPos().addAll(((RegularSymbol)r.getChildren().get(1)).getFirstPos());
            }
            else {
                r.setFirstPos(((RegularSymbol)r.getChildren().get(0)).getFirstPos());
            }
        }
        else if(opt == 1){
            if(n){
                r.setLastPos(((RegularSymbol)r.getChildren().get(0)).getLastPos());
                r.getLastPos().addAll(((RegularSymbol)r.getChildren().get(1)).getFirstPos());
            }
            else {
                r.setLastPos(((RegularSymbol)r.getChildren().get(1)).getLastPos());
            }
        }
    }
}