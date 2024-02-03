package pl1transpiler;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import pl1transpiler.SymbolHandeling.Pl1.SymbolTable;

/*****************************************
 * Dezimaldatentyp --------------- <Zahlenkette> ::= <declare-anweisung>
 * <Bezeichner> <attribute> <declare-anweisung> ::= [DECLARE | DCL] <Bezeichner>
 * ::= <Liste> | <Liste> <Bezeichner> | <Liste> <Ziffer> <Bezeichner> | <Liste>
 * <Ziffer> <attribut> ::= <basisattribut> <skalierungsattrbut> <moduattirbut>
 * <genauigkeitsattribut> <basisattribut> ::= DECIMAL| BINARY | DEC | BIN
 * <skalierungsattritbut> ::= FIXED | FLOAT <modusattribut> ::= REAL | COMPLEX |
 * REAL | CPLX <genauigkeitsattribut> ::= (<genauigkeit>) <genauigkeit> ::=
 * <Ziffer>,<Ziffer> <Ziffer> ::= 0...9 | -9...0 <Liste> ::= A...Z
 * 
 * Zeichenkettendatentype ---------------------- <Zeichenkette> ::=
 * <declare-anweisung> <Bezeichner> [CHARACTER | CHAR] <(genauigkeit)> [VARYING
 * | VAR] <declare-anweisung> ::= [DECLARE | DCL] <Bezeichner> ::= <Liste> |
 * <Liste> <Bezeichner> | <Liste> <Ziffer> <Bezeichner> | <Liste> <Ziffer>
 * <genauigkeit> ::= <Ziffer>,<Ziffer> <Ziffer> ::= 0...9 | -9...0 <Liste> ::=
 * A...Z
 * 
 * Abbildungsketten ---------------- <Abbildungskette> ::= <declare-anweisung>
 * <Bezeichner> [PICTURE | PIC] '<abbildungsspezifiktaion>' <declare-anweisung>
 * ::= [DECLARE | DCL] <Bezeichner> ::= <Liste> | <Liste> <Bezeichner> | <Liste>
 * <Ziffer> <Bezeichner> | <Liste> <Ziffer> <abbildungsspezfikation> ::= [X | A
 * | [9 | 9V9 | V9 ] | [Z | *] | [9.9 | 9,9 | 9B9] | S] <Ziffer> ::= 0...9 |
 * -9...0 <Liste> ::= A...Z
 ****************************************/

public class Lexer {

	public static SymbolTable st = new SymbolTable();
	/**
	 * 0 -> Start 
	 * 1 -> Letter 
	 * 2 -> Number 
	 * ... 
	 * 99 -> Error
	 */
	int state = 0;
	String token;
	ArrayList<Character> charBuffer = new ArrayList<Character>();

	String getToken(String remainingInput) {
		int scope = 0;
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
			// Handle Letters
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

			// Handle Numbers
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

						scope = Character.getNumericValue(remainingInput.charAt(letterCounter));
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

			// Handle Special Characters
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
						System.out.println(remainingInput.charAt(letterCounter));
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

			// Handle String values "xx"
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
					System.out.println("Here: " + remainingInput.charAt(letterCounter));
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

			// Handle Simicolons
			case 5:

				if (remainingInput.charAt(letterCounter) == ';') {

					return toToken(charBuffer);
				}

				break;
			// Handle Comments
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

	String toToken(ArrayList<Character> symbols) {
		String listString = symbols.stream().map(Object::toString).collect(Collectors.joining(""));

		String tmp = String.join("", listString);
		token = tmp;
		return token;
	}

	void fail() {

		this.state = 99;

	}

	void install_id(String id, int scope) {

		if (st.getBySymbol(id) != null) {
			System.out.println("PL1 Symbol!");
			return;
		} else {

			String tmp[] = { id, Integer.toString(scope), "id" };
			try {
				st.insert(tmp);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

}
