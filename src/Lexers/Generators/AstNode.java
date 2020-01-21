package Lexers.Generators;
import java.util.ArrayList;
import java.util.List;
public class AstNode {
    protected int type;
    protected String text;
    protected AstNode parent;
    protected ArrayList<AstNode> children;
    public AstNode(int type, String text, AstNode c1, AstNode c2){
        this.type = type;
        this.text = text;
        this.children = new ArrayList<AstNode>(10);
        if(c1 != null)
            addChild(c1);
        if(c2 != null)
            addChild(c2);
    }
    public AstNode(int type, AstNode c1, AstNode c2){
        this(type,null,c1,c2);
    }
    public AstNode(int type, AstNode c){
        this(type,c,null);
    }
    public AstNode(int type, String text){
        this(type,text,null,null);
    }
    public AstNode(int type){
        this(type,(String)null);
    }
    
    public void addChild(AstNode child){
        if(child == null)
            return;
        if (child.getParent() != null) {
            child.parent.children.remove(child);
        }
        children.remove(child);
        children.add(child);
        child.parent = this;
    }
    
    public int getCount(){
        return children.size();
    }

    public List<AstNode> getChildren(){
        return children;
    }
    
    public void removeChild(AstNode child){
        children.remove(child);
        if(child.parent == this)
            child.parent = null;
    }
    
    public AstNode getParent(){
        return parent;
    }
    
    public void setParent(AstNode p){
        p.addChild(this);
    }
    
    public AstNode getChildByIndex(int index){
        return children.get(index);
    }
    
    public int indexOf(){
        return parent == null ? -1 : parent.children.indexOf(this);
    }
    
    public String getSymbol(){
        return text;
    }
    



    public boolean nullable(){
        return false;
    }

    public int getType(){
        return type;
    }
    
    @Override
    public String toString(){
        return text != null ? text : AstNodeType.AstNodeTypeToString(type);
    }
}