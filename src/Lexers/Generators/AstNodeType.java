package Lexers.Generators;
public class AstNodeType {
    public static final int EMPTYS = 20;
    public static final int SYMBOL = 21;
    public static final int UNION = 22;
    public static final int CONCAT = 23;
    public static final int STAROP = 24;
    public static final int PLUSOP = 25;
    public static final int END = 0;

    public static String AstNodeTypeToString(int type) {
	switch(type) {
	    case EMPTYS: return "e";
	    case SYMBOL: return "c";
	    case UNION:  return "|";
	    case CONCAT: return "^";
	    case STAROP: return "*";
	    case PLUSOP: return "+";
	    case END: return "#";
	    default: return "";
        }
    }
}