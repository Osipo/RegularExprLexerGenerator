# RegularExprLexerGenerator
Console application. Consumes a string which represents a regular expression. Produces DFA based on that expression. Then it can consume any string and answer whether that word is matched to expression or not.
# Launch
java -classpath bin Lexers.Generators.SimpleRegularLGen
# Regex Syntax
For concatenation use a^b instead of ab. "^" - symbol of concatenation.
