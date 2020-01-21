package Lexers.Generators;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
public class DFAConstructor {
    private Hashtable<Integer,ArrayList<Integer>> followPoses;
    private RegularSymbol root;
    private int tsize;
    private int acceptable;
    private Set<Integer> FStates;
    public DFAConstructor(Hashtable<Integer,ArrayList<Integer>> followpos, RegularSymbol root,int acceptablePointer){
        this.followPoses = followpos;
        this.root = root;
        this.tsize = followpos.size() * 2;
        this.acceptable = acceptablePointer;
        this.FStates = new HashSet<Integer>();
    }
    
    public ArrayList<Integer> getFStates(){
        return new ArrayList<Integer>(FStates);
    }
    
    
    
    public int getAcceptable(){
        return acceptable;
    }
    
    private String integerListToString(ArrayList<Integer> lofnums){
        StringBuilder b = new StringBuilder();
        for(Integer i : lofnums){
            b.append(i);
        }
        return b.toString();
    }
    
    private static String match(String s1, String s2){
	//System.out.println("args: "+s1+" , "+s2);
	List<Character> li1 = new ArrayList<Character>();
	List<Character> li2 = new ArrayList<Character>();
	for(Character c : s1.toCharArray()){
	    li1.add(c);
	}
	for(Character c : s2.toCharArray()){
	    li2.add(c);
	}
	List<Character> rl = DFAConstructor.intersection(li1,li2);
	StringBuilder b = new StringBuilder();
	for(Character c: rl){b.append(c);}
        return b.toString();
    }
    
    public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

    
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    public static <T> List<T> subtraction(List<T> list1, List<T> list2){
	List<T> list = new ArrayList<T>();
	for(T t: list1){
	    if(!list2.contains(t)){
		list.add(t);
	    }
	}
	return list;
    }

    public int[][] getDFATranTable(HashMap<Character,String> symbolposes){
        
        //1. Array Initialization.
        int syms = symbolposes.size();
        int[][] DTran = new int[tsize][];
        for(int i = 0; i < tsize; i++){
            DTran[i] = new int[syms];
        }
        
        for(int i =0; i < tsize; i++){
            for(int j = 0; j < syms;j++){
                DTran[i][j] = -1; // for errors.
            }
        }
        
        //2. Set initial state.
        ArrayList<ArrayList<Integer>> DStates = new ArrayList<ArrayList<Integer>>(10);
        ArrayList<ArrayList<Integer>> OStates = new ArrayList<ArrayList<Integer>>(10);
        DStates.add(root.getFirstPos()); //sum of set elements.
        int oldstates = 0; //Non-marked State.
        int newstates = 0; //New State.
        int cnewstate = 0; //Count of new States.
        int symbol = 0;        
        boolean isNew = false;        
        //3. Cycle.
        while(DStates.size() > 0){
            ArrayList<Integer> S = DStates.get(0);
            ArrayList<Integer> O = DStates.get(0);
            OStates.add(O);//012 0123.
            DStates.remove(0);//remove S from DStates.
            
	    
            if(S.indexOf(acceptable) != -1){
                FStates.add(oldstates);
            }

            symbol = 0;//Integer represents symbol in DTran.
            for(Character a: symbolposes.keySet()){
                isNew = true;
                //Getting U.
                String s = integerListToString(S); //poses in S.
                String t = symbolposes.get(a);//poses in symbol.

                String m = DFAConstructor.match(s,t);//intersection.
                //System.out.println(m);
                ArrayList<Integer> U = new ArrayList<Integer>();
                for(Character c: m.toCharArray()){
                    int p = Character.getNumericValue(c);//each followpos(p) in S for symbol a.
		    
                    U = (ArrayList<Integer>) DFAConstructor.union(U,followPoses.get(p));
                }
                //If it is the new State.
                for(ArrayList<Integer> P : OStates){// 1: 012, 2:012, 0123.
                    if(DFAConstructor.subtraction(U,P).size() == 0){
                        isNew = false;
                        O = (ArrayList<Integer>)P;
                        break;
                    }
                }

                if(!isNew){
                    newstates = OStates.indexOf(O);
                }
                else {
                    DStates.add(U);
                    //System.out.println("new");
                    cnewstate+=1;
                    newstates = cnewstate;
                }
                //System.out.println("DTran["+oldstates+"]["+symbol+"] = "+newstates);
                DTran[oldstates][symbol] = newstates;
                symbol++;
            }
            oldstates+=1;
        }
        return DTran;
    }
    
    //This method returns only enumerated letters (letter,number).
    public HashMap<Character,Integer> getAlphabet(HashMap<Character,String> symbolposes){
        HashMap<Character,Integer> alpha = new HashMap<Character,Integer>();
        int symbol = 0;
        for(Character a: symbolposes.keySet()){
            alpha.put(a,symbol);
            symbol++;
        }
        return alpha;
    }
}