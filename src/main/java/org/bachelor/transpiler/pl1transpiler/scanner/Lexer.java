package org.bachelor.transpiler.pl1transpiler.scanner;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

/**
 * @deprecated This Class should not be used since it development will not be continued.
 * @author lennart.hahner
 *
 */
public class Lexer {

	public static SymbolTable st = new SymbolTable();
	/**
	 * 0 -> Start 1 -> Letter 2 -> Number ... 99 -> Error
	 */
	static int state = 0;
	static int scope = 0;
	String token;
	ArrayList<Character> charBuffer = new ArrayList<Character>();

	/**
	 * @param remainingInput holds the remaining content of the input file.
	 * @return the identified token from the input file as a string.
	 * @deprecated 
	 */
	public String getToken(String remainingInput) {

		charBuffer.clear();
		state = 0;
		int letterCounter = 0;

		while (letterCounter < remainingInput.length()) {

			switch (state) {

			case 0:

				if (remainingInput.charAt(letterCounter) == ' ' || remainingInput.charAt(letterCounter) == '\t'
						|| remainingInput.charAt(letterCounter) == '\n'
						|| remainingInput.charAt(letterCounter) == '\r') {

					letterCounter++;
					state = 0;
				}

				if (Character.isLetter(remainingInput.charAt(letterCounter))
						|| remainingInput.charAt(letterCounter) == '_') {

					state = 1;
				}

				if (Character.isDigit(remainingInput.charAt(letterCounter))) {

					state = 2;
				}

				if (remainingInput.charAt(letterCounter) == '(') {

					state = 3;
				}

				if (remainingInput.charAt(letterCounter) == '\'') {

					state = 4;
				}

				if (remainingInput.charAt(letterCounter) == ',') {

					state = 3;
				}

				if (remainingInput.charAt(letterCounter) == ';') {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					return toToken(charBuffer);
				}

				if (remainingInput.charAt(letterCounter) == '/') {

					state = 6;
				}

				if (remainingInput.charAt(letterCounter) == '&') {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;
					state = 6;
				}

				else if (letterCounter == remainingInput.length()) {
					this.install_id(toToken(charBuffer), scope);
					return toToken(charBuffer);
				}

				break;

			case 1:

				if (remainingInput.charAt(letterCounter) == ' ' || remainingInput.charAt(letterCounter) == '\t'
						|| remainingInput.charAt(letterCounter) == '\n'
						|| remainingInput.charAt(letterCounter) == '\r') {

					if (Character.isLetter(remainingInput.charAt(0)) || remainingInput.charAt(letterCounter) == '_') {
						String adder = toToken(charBuffer);
						this.install_id(adder, scope);
						return toToken(charBuffer);
					} else {
						fail();
						break;
					}

				}

				else if (Character.isLetter(remainingInput.charAt(letterCounter))
						|| remainingInput.charAt(letterCounter) == '_') {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));

					letterCounter++;
					if (letterCounter == remainingInput.length()) {
						this.install_id(toToken(charBuffer), scope);
						return toToken(charBuffer);
					}

					state = 1;
				}

				else if (Character.isDigit(remainingInput.charAt(letterCounter))) {

					if (Character.isLetter(remainingInput.charAt(0)) || remainingInput.charAt(letterCounter) == '_') {
						this.charBuffer.add((char) remainingInput.charAt(letterCounter));
						letterCounter++;
						state = 1;
					}
				}

				else if (remainingInput.charAt(letterCounter) == ',') {
					this.install_id(toToken(charBuffer), scope);
					return toToken(charBuffer);

				}

				else if (letterCounter == remainingInput.length()) {

					this.install_id(toToken(charBuffer), scope);
					return toToken(charBuffer);
				} else if (remainingInput.charAt(letterCounter) == '(') {
					return toToken(charBuffer);
				}

				break;

			case 2:

				if (remainingInput.charAt(letterCounter) == ' ' || remainingInput.charAt(letterCounter) == '\t'
						|| remainingInput.charAt(letterCounter) == '\n'
						|| remainingInput.charAt(letterCounter) == '\r') {

					if (Character.isLetter(remainingInput.charAt(0)) || remainingInput.charAt(letterCounter) == '_') {
						this.install_id(toToken(charBuffer), scope);
					}
					return toToken(charBuffer);
				}

				else if (Character.isDigit(remainingInput.charAt(letterCounter))) {
					if (letterCounter == 0 && Character.isLetter(remainingInput.charAt(letterCounter + 1))) {
						fail();
						break;
					} else {

						this.scope = Character.getNumericValue(remainingInput.charAt(letterCounter));
						this.charBuffer.add((char) remainingInput.charAt(letterCounter));
						letterCounter++;
						state = 2;
					}
				}

				else if (Character.isLetter(remainingInput.charAt(letterCounter))
						|| remainingInput.charAt(letterCounter) == '_') {

					state = 1;
				}

				else if (remainingInput.charAt(letterCounter) == '(') {

					state = 3;
				}

				else if (remainingInput.charAt(letterCounter) == ',') {

					state = 3;
				}

				else if (remainingInput.charAt(letterCounter) == ')') {

					state = 3;
				}

				else if (Character.isLetter(remainingInput.charAt(letterCounter))
						|| remainingInput.charAt(letterCounter) == '_') {
					fail();
					break;
				}

				else if (letterCounter == remainingInput.length()) {

					return toToken(charBuffer);
				}

				break;

			case 3:

				if (remainingInput.charAt(letterCounter) == ' ' || remainingInput.charAt(letterCounter) == '\t'
						|| remainingInput.charAt(letterCounter) == '\n'
						|| remainingInput.charAt(letterCounter) == '\r') {

					return toToken(charBuffer);
				}

				else if (remainingInput.charAt(letterCounter) == '(') {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;
					state = 3;

				}

				else if (remainingInput.charAt(letterCounter) == ')') {
					if (remainingInput.charAt(0) == '\'') {

						this.charBuffer.add((char) remainingInput.charAt(letterCounter));
						letterCounter++;
						
						state = 4;

					} else {

						this.charBuffer.add((char) remainingInput.charAt(letterCounter));

						return toToken(charBuffer);
					}
				}

				else if (remainingInput.charAt(letterCounter) == ',') {
					if (letterCounter != 0 && remainingInput.charAt(letterCounter - 1) == ')') {

						return toToken(charBuffer);

					} else if (letterCounter == 0 && Character.isLetter(remainingInput.charAt(letterCounter + 1))) {
						fail();

					} else {

						this.charBuffer.add((char) remainingInput.charAt(letterCounter));
						letterCounter++;
						state = 2;
					}
				}

				else if (remainingInput.charAt(letterCounter) == '\'') {
					if (remainingInput.charAt(0) == '(') {

						state = 4;

					}

					else {

						this.charBuffer.add((char) remainingInput.charAt(letterCounter));
						letterCounter++;
						state = 1;

					}

					if (letterCounter == remainingInput.length()) {

						return toToken(charBuffer);
					}

				}

				else if (Character.isDigit(remainingInput.charAt(letterCounter))) {

					state = 2;
				}

				else if (letterCounter == remainingInput.length()) {

					return toToken(charBuffer);
				}
				break;

			case 4:

				if (remainingInput.charAt(letterCounter) == ' ' || remainingInput.charAt(letterCounter) == '\t'
						|| remainingInput.charAt(letterCounter) == '\n'
						|| remainingInput.charAt(letterCounter) == '\r') {

					if (letterCounter == remainingInput.length()) {

						return toToken(charBuffer);
					}

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;

					state = 4;
				}

				else if (remainingInput.charAt(letterCounter) == '\'') {

					if (letterCounter == remainingInput.length()) {

						return toToken(charBuffer);
					}

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;

					state = 4;

				}

				else if (Character.isLetter(remainingInput.charAt(letterCounter))
						|| remainingInput.charAt(letterCounter) == '_') {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;

					state = 4;
				}

				else if (Character.isDigit(remainingInput.charAt(letterCounter))) {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;

					state = 4;
				}

				else if (remainingInput.charAt(letterCounter) == ')') {
					state = 3;
				}

				else if (remainingInput.charAt(letterCounter) == '(') {
					state = 3;
				}

				else if ((letterCounter != 0 && remainingInput.charAt(0) == '\'')) {
				
					if (remainingInput.charAt(letterCounter) == '\'') {
						this.charBuffer.add((char) remainingInput.charAt(letterCounter));
						letterCounter++;

						return toToken(charBuffer);
					} else if (remainingInput.charAt(letterCounter) == '(') {

						state = 3;
					} else if (Character.isUnicodeIdentifierPart(remainingInput.charAt(letterCounter))) {
						this.charBuffer.add((char) remainingInput.charAt(letterCounter));
						letterCounter++;
						state = 4;
					} else if (remainingInput.charAt(letterCounter) == ',') {

						return toToken(charBuffer);
					}
				}

				else if (Character.isUnicodeIdentifierPart(remainingInput.charAt(letterCounter))) {
					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;
					state = 4;
				}

				else if (letterCounter == remainingInput.length()) {

					return toToken(charBuffer);
				}

				if (letterCounter == remainingInput.length()) {

					return toToken(charBuffer);
				}

				break;

			case 5:

				if (remainingInput.charAt(letterCounter) == ';') {

					return toToken(charBuffer);
				}

				break;

			case 6:
				if (remainingInput.charAt(letterCounter) == ' ' || remainingInput.charAt(letterCounter) == '\t'
						|| remainingInput.charAt(letterCounter) == '\n'
						|| remainingInput.charAt(letterCounter) == '\r') {

					return toToken(charBuffer);
				} else if (remainingInput.charAt(letterCounter) == '/') {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;
					state = 6;
				} else if (remainingInput.charAt(letterCounter) == '*') {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;
					state = 6;
				} else if (Character.isLetter(remainingInput.charAt(letterCounter))
						|| remainingInput.charAt(letterCounter) == '_') {

					this.charBuffer.add((char) remainingInput.charAt(letterCounter));
					letterCounter++;
					state = 6;
				}
				if (letterCounter == remainingInput.length()) {

					return toToken(charBuffer);
				}
				break;

			case 99:
				return "failed";

			default:
				return "Default error";

			}

		}

		return "Fehler";
	}

	/**
	 * @param symbols Characters of the identified token.
	 * @return concats the Character array symbols to the String token.
	 */
	public String toToken(ArrayList<Character> symbols) {
		String listString = symbols.stream().map(Object::toString).collect(Collectors.joining(""));

		String tmp = String.join("", listString);
		token = tmp;
		return token;
	}

	/**
	 * sets the state to a failed status.
	 */
	void fail() {

		this.state = 99;

	}
	

	/**
	 * @param id    the String of the identfier that should be added to the
	 *              Symboltable.
	 * @param scope the Integer that represents the scope of a variable.
	 */
	void install_id(String id, int scope) {

		if (st.getBySymbol(id) != null) {
			
			return;
		} else {

			String tmp[] = { id, Integer.toString(scope), "id" };
			try {
				st.insertId(tmp);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

}
