/* * KawaTokenMarker.java - Kawa token marker * Copyright (C) 1998, 1999 Slava Pestov * * You may use and modify this package for any purpose. Redistribution is * permitted, in both source and binary form, provided that this notice * remains intact in all source distributions of this package. */package jedit.lang;import javax.swing.text.Segment;import jedit.Token;import jedit.KeywordMap;import jedit.TokenMarker;/** * Kawa token marker. * * @author Kendy JEAN-MARIE * 2004 - Univ. Montpellier II - France */public class KawaTokenMarker extends SchemeTokenMarker {    // private members    private static KeywordMap kawaKeywords;    public KawaTokenMarker() {// this.keywords = getKeywords();    super(false, getKeywordMap());// false because it's not Scheme    }    public static KeywordMap getKeywordMap() {        if (kawaKeywords == null) {            kawaKeywords = new KeywordMap(false); // FALSE BECAUSE KAWA IS CASE-SENSITIVE            addStandardTypes();            addSpecialnamedconstants();            addProcedures();            addLists();            addSignallingandrecoveringfromexceptions();            addStrings();            addMultidimensionalArrays();        }        return kawaKeywords;    }                        private static void addStandardTypes(){  	    kawaKeywords.add("object", Token.KEYWORD1); kawaKeywords.add("number", Token.KEYWORD1); kawaKeywords.add("quantity", Token.KEYWORD1); kawaKeywords.add("complex", Token.KEYWORD1); kawaKeywords.add("real", Token.KEYWORD1); kawaKeywords.add("rational", Token.KEYWORD1); kawaKeywords.add("integer", Token.KEYWORD1); kawaKeywords.add("symbol", Token.KEYWORD1); kawaKeywords.add("keyword", Token.KEYWORD1); kawaKeywords.add("list", Token.KEYWORD1); kawaKeywords.add("pair", Token.KEYWORD1); kawaKeywords.add("string", Token.KEYWORD1); kawaKeywords.add("character", Token.KEYWORD1); kawaKeywords.add("vector", Token.KEYWORD1); kawaKeywords.add("String", Token.KEYWORD1); 	    }	    	    	    	                private static void addSpecialnamedconstants(){ 	    kawaKeywords.add("#!optional", Token.LITERAL2); kawaKeywords.add("#!rest", Token.LITERAL2); kawaKeywords.add("#!key", Token.LITERAL2); kawaKeywords.add("#!eof", Token.LITERAL2); kawaKeywords.add("#!void", Token.LITERAL2); kawaKeywords.add("#!null", Token.LITERAL2); 	    }	    	    	    	                private static void addProcedures(){  	   kawaKeywords.add("apply", Token.KEYWORD1); kawaKeywords.add("constant-fold", Token.KEYWORD1); kawaKeywords.add("procedure-property", Token.KEYWORD1); kawaKeywords.add("set-procedure-property!", Token.KEYWORD1); kawaKeywords.add("define-procedure", Token.KEYWORD1); kawaKeywords.add("make-procedure", Token.KEYWORD1); kawaKeywords.add("cut", Token.KEYWORD1); kawaKeywords.add("cute", Token.KEYWORD1); 	    }	    	    	    	    	    	                private static void addLists(){   	   kawaKeywords.add("reverse!", Token.KEYWORD2); 	    }	    	    	    	                private static void addSignallingandrecoveringfromexceptions(){   	   kawaKeywords.add("catch", Token.KEYWORD2); kawaKeywords.add("throw", Token.KEYWORD2); kawaKeywords.add("error", Token.KEYWORD2); kawaKeywords.add("primitive-throw", Token.KEYWORD2); kawaKeywords.add("try-finally", Token.KEYWORD2); kawaKeywords.add("try-catch", Token.KEYWORD2); kawaKeywords.add("dynamic-wind", Token.KEYWORD2); 	    }	    	    	    	    	                private static void addStrings(){   	   kawaKeywords.add("string-upcase", Token.KEYWORD3); kawaKeywords.add("string-downcase", Token.KEYWORD3); kawaKeywords.add("string-capitalize", Token.KEYWORD3); kawaKeywords.add("string-upcase!", Token.KEYWORD3); kawaKeywords.add("string-downcase!", Token.KEYWORD3); kawaKeywords.add("string-capitalize!", Token.KEYWORD3); 	    }	    	    	    	    	                private static void addMultidimensionalArrays(){   	    kawaKeywords.add("array?", Token.KEYWORD3); kawaKeywords.add("shape", Token.KEYWORD3); kawaKeywords.add("make-array", Token.KEYWORD3); kawaKeywords.add("array", Token.KEYWORD3); kawaKeywords.add("array-rank", Token.KEYWORD3); kawaKeywords.add("array-start", Token.KEYWORD3); kawaKeywords.add("array-end", Token.KEYWORD3); kawaKeywords.add("array-ref", Token.KEYWORD3); kawaKeywords.add("array-set!", Token.KEYWORD3); kawaKeywords.add("share-array", Token.KEYWORD3); 	    }	        }