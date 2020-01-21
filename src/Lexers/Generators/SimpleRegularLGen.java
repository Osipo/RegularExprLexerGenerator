package Lexers.Generators;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
public class SimpleRegularLGen {
    
   
    
    public static void main(String[] args){
        while(true){
            Scanner in = new Scanner(System.in);
            System.out.println("Input string without any spaces...(they will be assumed  as the end of the input)");
            String s = in.next();
            RegularSymbol root = null;
            RegularExpressionsG tree = new RegularExpressionsG(s);
            try{
                root = (RegularSymbol)tree.program(); //first step.
            }
            catch(Exception e){
                System.err.println(e);
                return;
            }
            System.out.println("Symbols: "+tree.getSp());
            System.out.println("Pos: "+tree.getPos());
            
            RegularExpressionsInter inter = new RegularExpressionsInter(tree.getSp());
            inter.visit(root); //second step.
        
	
            //Print table of values of the followPos function.
            System.out.println("Followposes: ");
            Hashtable<Integer,ArrayList<Integer>> table = inter.getFollow();
            for(int i = 0; i<tree.getSp();i++){
                ArrayList<Integer> set = table.get(i);
                System.out.print(i +": {");
                for(Integer e : set){
                    System.out.print(e+"  ");
                }
                System.out.println("}");
            }
        
        
            HashMap<Character,String> mp = tree.getSymbolsPoses();//syntax positions of each letter.
            for(Map.Entry entry : mp.entrySet()){
                 System.out.println("Key: " + entry.getKey() + " Sposes: "
                    + entry.getValue());
            }
        
            int acept = tree.getSp();
                //Third step.
                DFAConstructor ctor = new DFAConstructor(table,root,acept);
                int[][] DTran = ctor.getDFATranTable(mp);
                
            SimpleDFA D;
                
                
            System.out.print("{");
            for(Integer i : ctor.getFStates()){
                System.out.print(i+" ");
            }
            System.out.println("}");
    
            //Fourth step. (Костыль для выражений a*, a+)
            if(table.size() == 1 && s.length() == 2 && (s.charAt(1) == '*' || s.charAt(1) == '+')){
                //System.out.println("Single");
                ArrayList<Integer> F = new ArrayList<Integer>();
                if(s.charAt(1) == '*')
                    F.add(0);
                F.add(1);
                D = new SimpleDFA(DTran,ctor.getAlphabet(mp),0,F);
            }
            else{
                D = new SimpleDFA(DTran,ctor.getAlphabet(mp),0,ctor.getFStates());
            }
            System.out.println("Input string those pattern was typed");
            String inp = in.next();
            System.out.println("Inputed: "+inp);
            System.out.println(D.identify(inp));
        }
    }
}