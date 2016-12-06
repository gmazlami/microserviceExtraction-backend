package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.classprocessing;

import java.util.Arrays;
import java.util.List;

public class StopWords {
	
	public static final List<String> JAVA_KEYWORDS = Arrays.asList("abstract","continue","for","new","switch","assert","default","goto","package","synchronized",
											"boolean","do","if","private","this",
											"break","double","implements","protected","throw",
											"byte","else","import","public","throws",
											"case","enum","instanceof","return","transient",
											"catch","extends","int","short","try",
											"char","final","interface","static","void",
											"class","finally","long","strictfp", "volatile",
											"const","float","native","super","while");


	public static final List<String> RUBY_KEYWORDS = Arrays.asList("alias", "and","BEGIN", "begin", "break",
			"case", "class","def", "defined?", "do",
			"else", "elsif", "END", "end", "ensure",
			"false", "for","if","module","next", "nil", "not", "or",
			"redo", "rescue", "retry", "return","self", "super", "then", "true",
			"undef", "unless", "until","when", "while", "yield");


	public static final List<String> PYTHON_KEYWORDS = Arrays.asList("and", "as", "assert", "break", "class", "continue", "def", "del",
			"elif", "else", "except", "exec", "finally", "for", "from", "global", "if", "import",
			"in", "is", "lambda", "not", "or", "pass", "print", "raise", "return", "try", "while",
			"with", "yield");

}
