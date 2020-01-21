package Lexers.Generators;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
public class SimpleDFA {
    private int[][] tran;
    private HashMap<Character,Integer> alphabet;
    private int state;
    private int pos; //for tracking errors.
    private int i;
    private ArrayList<Integer> F;
    private String error;
    public SimpleDFA(int[][] tranTable, ArrayList<Integer> acceptableStates){
        this(tranTable,new HashMap<Character,Integer>(),0,acceptableStates);
    }
    
    public SimpleDFA(int[][] tranTable, HashMap<Character,Integer> alphabet,ArrayList<Integer> acceptableStates){
        this(tranTable,alphabet,0,acceptableStates);
    }
    
    public SimpleDFA(int[][] tranTable, HashMap<Character,Integer> alphabet,int start,ArrayList<Integer> acceptableStates){
        this.tran = tranTable;
        this.alphabet = alphabet;
        this.state = start;
        this.pos = 0;
        this.i = 0;
        this.F = acceptableStates;
    }
    
    private void move(char c){
        Integer s = 0;//chr -> num
        if(state == -1){
            return;
        }
        s = alphabet.get(c);
        
        if(s == null){
            state = -1;
            pos = i;
            error = "Unrecognized symbol";
            return;
        }
        state = tran[state][s];
    }
    
    public String identify(String input){
        for(Character c : input.toCharArray()){
            move(c);
            i++;
        }
        if(state == -1)
            return "Unrecognized symbol on "+pos+" : "+input.charAt(pos); //for unknown symbols.
        
        if(F.indexOf(state) != -1)
            return "Yes";
        return "No. Current state: "+state+" Final state: "+F.get(0)+"";
    }
}