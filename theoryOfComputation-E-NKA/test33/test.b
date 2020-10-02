package hr.fer.zemris.java.custom.scripting.lexer;


import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.hw03.prob1.LexerException;
import hr.fer.zemris.java.hw03.prob1.Token;

/**
 * Lexer is used as a system for doing lexical analysis of some input that can be e.g source code of some program and output is sequence of tokens.
 * It will work in various states due to several possible types of inputs that can be provided.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Lexer {
	
	private char[] data;
	private Element element;
	private int currentIndex;
	private LexerState state;
	private TokenType type;
	private int param = 0;
	private int c = 0;
	
	/**
	 * @param text input that will be analyzed
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.OUTSIDE;
	}
	
	/**
	 * Sets state of lexer to value of enum LexerState state.
	 * @param state given state
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("State can't be null!");
		}
		this.state = state;
	}
	
	/**
	 * @returns next token from input or throws LexerException if there was mistake in running code. Return type is Element and it can be ElementString,
	 ElementFunction, ElementOperator, ElementConstantInteger or ElementConstantDouble,
	 * Lexer is using some rules about escaping, it's only possible to have \\ and \" in tag and \\ \{ out of tag.
	 */
	public Element nextToken() {
		int length = data.length;
		if(c == 1 && type == TokenType.FOR) { // this block uses private variable c for information that we entered echo, for  or end block
			this.element = new ElementString("FOR");
			c = 0;
			return element;
		} else if(c == 1 && type == TokenType.ECHO) {
			this.element = new ElementString("=");
			c = 0;
			return element;
		} else if(c == 2) {
			this.element = new ElementString("END");
			param = 0;
			c = 0;
			return element;
		}
		if(currentIndex >= length) { //if we reached end ElementString with value EOF is returned so parser could now when to stop
			this.element = new ElementString("EOF");
			return this.element;
		}
		StringBuilder sb = new StringBuilder();
		if(state == LexerState.OUTSIDE) {//if we are out of tag
			while(true) {
				if(currentIndex >  length -1)  {
					Element el = new ElementString(sb.toString()); //if end is reached
					this.element = el;
					return el;
				}
				if(data[currentIndex] == '{'){//if next char is $ then we are entering some new mode
					if(currentIndex < length -1 && data[currentIndex + 1] == '$') {
						c = check();
						//System.out.println(c);
						if(c == 1) { //for or echo block is next
							setState(LexerState.INSIDE); //Lexer changes mode to inside
							Element el = new ElementString(sb.toString());
							this.element = el;
							return el;
						}  else if(c == 2){ //end block 
							element = new ElementString(sb.toString());
							return element;
						}
					} else {
						throw new SmartScriptParserException("Wrong outside escaping...");
					}
				}
				 else if(data[currentIndex] == '\\'){ //check if there is regular escape 
					if(currentIndex == length - 1) {
						throw new SmartScriptParserException("Wrong outside escaping...");
					}
					else if(currentIndex < length - 1 && (data[currentIndex +1] == '{'  || data[currentIndex +1]  == '\\')) {
						sb.append(data[currentIndex +1]);
						currentIndex += 2;
					} else {
						throw new SmartScriptParserException("Wrong outside escaping...");
					}
				} else{
					sb.append(data[currentIndex++]);
				} 
			}
		} else if(state == LexerState.INSIDE) { //we came inside some tag 
				if(currentIndex >= length) {
					throw new SmartScriptParserException("Somewhere near end"); //because it means tag isn't closed
				}
				if(type == TokenType.ECHO)  { //we read = so we'll be extracting echo tokens
					if(data[currentIndex] == '\"') { //possible string next
						sb.append(data[currentIndex++]);
						sb = extractGeneral(data, sb);
						element = new ElementString(sb.toString());
						return element;
					}
					if(Character.isLetter(data[currentIndex]) == true) { //if letter is first then try to extract variable
						//System.out.println(data[currentIndex]);
						sb.append(data[currentIndex++]);
						sb = extractVariable(data, sb);
						Element el = new ElementVariable(sb.toString());
						this.element = el;
						//System.out.println(el.asText());
						return el;
					} else if(currentIndex < length - 1 && data[currentIndex] == '@' && Character.isLetter(data[currentIndex + 1]) == true) { 
						currentIndex++; //we read @ next token will be ElementFunction
						sb = extractVariable(data, sb);
						Element el = new ElementFunction(sb.toString());
						//System.out.println(el.asText());
						this.element = el;
						return el;
					} else if(Character.isDigit(data[currentIndex]) == true) { //try to extract number
						sb.append(data[currentIndex++]);
						element = extractNumber(data, sb);
						return element;
					} else if(data[currentIndex] == '+' || data[currentIndex] == '*' || data[currentIndex] == '/' || data[currentIndex] == '^') {
						sb.append(data[currentIndex++]); //try to extract variable
						skipBlanks();
						Element el = new ElementOperator(sb.toString());
						this.element = el;
						return el;
					}
					else if(data[currentIndex] == '-') { //check if next char is digit or something else
						if(currentIndex < length -1 && Character.isDigit(data[currentIndex + 1]) == true) {
							sb.append(data[currentIndex++]);
							element = extractNumber(data, sb);
						} else {
							sb.append(data[currentIndex++]);
							skipBlanks();
							element = new ElementOperator(sb.toString());
						}
						return element;
					}
					else if(data[currentIndex] == '$') { //possible end of the block
						boolean end = checkEnd(data);
						if(end == true) {
							setState(LexerState.OUTSIDE);
							Element el = new ElementString("ECHOEND");
							this.element = el;
							return el;	
						} else {
							throw new SmartScriptParserException("Nor regular ending");
						}
						
					}else if(Character.isSpace(data[currentIndex]) == true) {
						skipBlanks();
						return nextToken();
					}
				
				} else if(type == TokenType.FOR) { //we are extracting tokens for FOR block
					if(currentIndex >= length) {
						throw new SmartScriptParserException("Cannot extract anything...");
					}
					if(data[currentIndex] == '\"') { // possible string next
						sb.append(data[currentIndex++]);
						sb = extractGeneral(data, sb);
						element = new ElementString(sb.toString());
						param++;
						return element;
					}
					if(param == 0) { //in FOR block first variable must be first
						if(Character.isLetter(data[currentIndex]) == true) {
							sb.append(data[currentIndex++]);
							sb = extractVariable(data, sb);
							Element el = new ElementVariable(sb.toString());
							param++;
							this.element = el;					
							return el;
						} 	
						
						else  {
							throw new SmartScriptParserException("Parsing not possible, there is no variable at the first place");
						}
					} else { //we read variable, go on
						if(Character.isDigit(data[currentIndex]) == true) { //try to extract number
							sb.append(data[currentIndex++]);
							element = extractNumber(data, sb);
							param++;
							if(param > 4) { //there should be max 4 param
								throw new SmartScriptParserException("Too many arguments");
							}
							return element;
						}  else if(data[currentIndex] == '@') { //functions are not allowed in FOR block
							throw new SmartScriptParserException("Functions are not allowed in for tag");
							
						} if(Character.isLetter(data[currentIndex]) == true) { //try to extract element
							sb.append(data[currentIndex++]);
							sb = extractVariable(data, sb);
							Element el = new ElementVariable(sb.toString());
							param++;
							//System.out.println(data[currentIndex+ 4]);
							if(param > 4) {
								throw new SmartScriptParserException("Too many arguments");
							}
							this.element = el;					
							return el;
						} else if(data[currentIndex] == '+' || data[currentIndex] == '*' || data[currentIndex] == '/' || data[currentIndex] == '^') {
							throw new SmartScriptParserException("Operators are not allowed in for tag");
							
						} else if(data[currentIndex] == '-') { //-can here only be used for number
							if(currentIndex < length -1 && Character.isDigit(data[currentIndex + 1]) == true) {
								sb.append(data[currentIndex++]);
								element = extractNumber(data, sb);
								param++;
								if(param > 4) {
									throw new SmartScriptParserException("Too many arguments");
								}
								return element;
							} else {
								throw new SmartScriptParserException("Operators are not allowed in for tag");
							}
						}
						else if(Character.isSpace(data[currentIndex]) == true) { //just for check
							skipBlanks();
							return nextToken();
						}
						else if(data[currentIndex] == '$') { //possible end reached
							boolean end = checkEnd(data);
							if(end == true) {
								setState(LexerState.OUTSIDE);
								Element el = new ElementString("FOREND");
								if(param < 3) {
									throw new SmartScriptParserException("Too few arguments");
								}
								this.element = el;
								return element;
							} else {
								throw new SmartScriptParserException("Not regular ending of tag");
							}
							
						}
					}
				}
		}
		throw new SmartScriptParserException("Nothing was returned");
	}
			
	
	
	
	private StringBuilder extractVariable(char[] data, StringBuilder sb) {
		while(currentIndex < data.length) {
			if(Character.isSpace(data[currentIndex]) == true){
				skipBlanks();
				break;
			}else if(Character.isLetter(data[currentIndex]) == false && Character.isDigit(data[currentIndex]) == false && data[currentIndex] != '_') {
				break;
			}  else {
				sb.append(data[currentIndex++]);
			}
		}
		return sb;
	}
	
	private StringBuilder extractGeneral(char[] data, StringBuilder sb) {
		while(currentIndex < data.length) {
			if(data[currentIndex] == '\\') { 
				if(currentIndex < data.length -1 && (data[currentIndex +1] == '\\' || data[currentIndex +1] == '\"' )) {
					sb.append(data[currentIndex+ 1]);
					currentIndex +=2;
				} else {
					throw new SmartScriptParserException("Inside escape not allowed");	
				}
			}else if(data[currentIndex] == '\"') {
				sb.append(data[currentIndex++]);
				skipBlanks();
				break;
			}else {
				sb.append(data[currentIndex++]);
			}
		}
		
		return sb;
	}
	
	private Element extractNumber(char[] data, StringBuilder sb) {
		boolean usedDot = false;
		while(currentIndex < data.length) {
			if(Character.isDigit(data[currentIndex]) == true) {
				sb.append(data[currentIndex++]);
			} else if(data[currentIndex] == '.' && usedDot == false) {
				usedDot = true;
				sb.append('.');
				currentIndex++;
			} else if(Character.isSpace(data[currentIndex]) == true){
				skipBlanks();
				break;
			} else {
				break;
			}
		}
		
		Element el = null;
		if(usedDot == false) {
			try {
				Integer i = Integer.parseInt(sb.toString());
				el = new ElementConstantInteger(i);
			} catch(NumberFormatException ex) {
				System.out.println("Mistake happened, that is not integer");
			}
		} else {
			try {
				Double d = Double.parseDouble(sb.toString());
				el = new ElementConstantDouble(d);
			} catch(NumberFormatException ex) {
				System.out.println("Mistake happened, that is not double");
			}
		}
		return el;
	}
	
	private boolean checkEnd(char[] data) {
		if(currentIndex >= data.length -1) {
			throw new SmartScriptParserException("There's no end of tag");
		}
		if(data[currentIndex] == '$') {
			 if(data[currentIndex +1] == '}') {
				currentIndex += 2;
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Method we'll internally use for skipping blanks inside tags
	 * @returns true if we reached end of the input, false otherwise
	 */
	private void skipBlanks() {
		int length = data.length; 
		while(true) {
			if(currentIndex == length) {
				System.out.println("Pogreška u brisanju praznina");
				return;
			}
			if(data[currentIndex] == ' ') {
				currentIndex++;
			}
			else {
				return;
			}
		}
	}
	
	private int check() {
		int length = data.length;
		int dif = currentIndex;
		//System.out.println(data[currentIndex]);
		if(type == null ||( data[currentIndex] == '{' && data[currentIndex +1] == '$')) { //we're at the beginning
			currentIndex += 2;
			skipBlanks();
			if(currentIndex >= length) {
				throw new SmartScriptParserException("Cannot extract anything...");
			}
			if(data[currentIndex] == '=') {
				currentIndex++;
				skipBlanks();
				type = TokenType.ECHO;
				return 	1;
			} else if(currentIndex < length - 2 && Character.toUpperCase(data[currentIndex]) == 'F' && 
					Character.toUpperCase(data[currentIndex + 1]) == 'O' && Character.toUpperCase(data[currentIndex + 2]) == 'R') {
				currentIndex += 3;
				skipBlanks();
				type = TokenType.FOR;
				return 1;
			} else if(currentIndex < length - 2 && Character.toUpperCase(data[currentIndex]) == 'E' && 
					Character.toUpperCase(data[currentIndex + 1]) == 'N' && Character.toUpperCase(data[currentIndex + 2]) == 'D'
					) {
				currentIndex += 3;
				skipBlanks();
				if(currentIndex < length - 1 && data[currentIndex] == '$' && data[currentIndex +1] == '}') {
					currentIndex += 2;
					return 2;
				} else {
					throw new SmartScriptParserException("No ending of end tag");//moguce da ce tu trebat jos neki tag name provjeriti
				}
				
			}
		}
		throw new SmartScriptParserException("Ne postoji takav tag");
	}
	
	/**
	 * @returns last generated token, it can be called more than once and it does not start generating next token.
	 */
	public Element getToken() {
		return element;
	}

}
