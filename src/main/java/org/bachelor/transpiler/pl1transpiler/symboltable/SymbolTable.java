package org.bachelor.transpiler.pl1transpiler.symboltable;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import com.sun.jdi.request.*;

/**
 * The Symboltable class is used by many modules
 * over the project. It utilizes the Pl1Symbols enum 
 * and inserts all the data into a hashtable.
 * Afterwards Identifier from methods, packages and variables
 * are added during runitme.
 * 
 * @author Lennart Hahner
 */
public class SymbolTable {

	/** The Hashtable which is the underlying datastructure. */
	private static Hashtable<Integer, String[]> hashtable = new Hashtable<>();
	
	/** The symbols. */
	private static SymbolTable symbols = null;

	/**
	 * Implements the Singleton design pattern
	 * which is used because only one Symboltable 
	 * should exist during runtime.
	 * Gets the single instance of SymbolTable.
	 *
	 * @return single instance of SymbolTable
	 */
	public static SymbolTable getInstance() {
		if (symbols == null) {
			symbols = new SymbolTable();
		}
		return symbols;
	}

	/**
	 * Instantiates a new symbol table.
	 * Also adds all Terminalsymbols from
	 * the Pl1Symbols Enum with the requirded idenfication
	 * "word". Any other values which do not have the 
	 * "word" flag, are Non-terminal Symbols.
	 */
	private SymbolTable() {
		try {
			Pl1Symbols pl1Symbols;
			for (Pl1Symbols s : Pl1Symbols.values()) {
				int i = 0;
				String[] tmp = { s.toString(), "word" };
				int id = hashtable.size() + 1;
				hashtable.put(id, tmp);
				i++;
			}
		} catch (Exception e) {
			System.out.println("Error in Symboltable" + e);
		}
	}

	/**
	 * @deprecated
	 *
	 * @param words the words
	 * @throws DuplicateRequestException the duplicate request exception
	 */
	private static void initWords(String[] words) throws DuplicateRequestException {
		if (getBySymbol(words[0]) != null) {
			return;
		}
		int id = hashtable.size() + 1;
		hashtable.put(id, words);
	}

	/**
	 * Method is used to insert an Identfier into
	 * the SymbolTable. This should only be used
	 * during lexical analysis or syntactic analysis.
	 *
	 * @param symbol Array of String which should be inserted in the Symboltable
	 * @return the string[] which was inserted
	 * @throws DuplicateRequestException the duplicate request exception
	 */
	public static String[] insertId(String[] symbol) throws DuplicateRequestException {
		if (getBySymbol(symbol[0]) != null && getSymbolScope(symbol[0]) == Integer.parseInt(symbol[2])) {
			return null;
		}
		int id = hashtable.size() + 1;
		hashtable.put(id, symbol);
		return hashtable.get(id);
	}

	/**
	 * This searches in all symbols of 
	 * the Hashtable for a specifc symbol.
	 * A symbol can be an identifier but also
	 * a PL/I Word like "DECLARE".
	 *
	 * @param symbol the symbol which should be searched for.
	 * @return the found value from the hashtable.
	 */
	public static String getBySymbol(String symbol) {
		for (int i = 1; i <= hashtable.size(); i++) {
			if (hashtable.get(i)[0].equals(symbol)) {
				return hashtable.get(i)[0];
			} else {
				continue;
			}
		}
		return null;
	}

	/**
	 * Searches the SymbolTable for a specific
	 * Type. It will return an ArrayList
	 * containing all the found values. There
	 * are mainly three different types of symbols.
	 * a word, a procedure, a id and a package.
	 * A word is save with the flag "word".
	 * While procedure is "proc", id is "id"
	 * and package is "pack".
	 * 
	 *
	 * @param type the type you are searching for
	 * @return a List of found symbols, which are either identifier or PL/I words
	 * 
	 * <i>TODO Test<i>
	 */
	public static ArrayList<String[]> getByType(String type) {
		ArrayList<String[]> tmp = new ArrayList<String[]>();

		for (int i = 1; i <= hashtable.size(); i++) {
			if (hashtable.get(i)[hashtable.get(i).length - 1].equals(type)) {
				tmp.add(hashtable.get(i));
			} else {
				continue;
			}
		}
		if (tmp.size() != 0) {
			return tmp;
		} else {
			return null;
		}
	}

	/**
	 * This will return the scope of a symbol.
	 * In the entiries for each Identifier there are there numbers save.
	 * One is the scope which is at position 2 of the saved String Array.
	 * Keep in mind that Hierachie level and scope are different parameters.
	 *
	 * @param symbol the symbol for which the scope is needed.
	 * @return the symbol scope
	 */
	public static int getSymbolScope(String symbol) {
		for (int i = 1; i <= hashtable.size(); i++) {
			if (hashtable.get(i)[0].equals(symbol)) {
				return Integer.parseInt(hashtable.get(i)[2]);
			} else {
				continue;
			}
		}
		return 0;
	}

	/**
	 * This will return a list with all identifiers.
	 * It reduces the SymbolTable by the at time of calling
	 * inserted identfiers. Since these values can change,
	 * always call this after lexical and syntactical anylsis.
	 *
	 * @return a List of the Identifiers in the Symboltable.
	 */
	public static ArrayList<String[]> getAllIdentifier() {
		ArrayList<String[]> identfiertList = new ArrayList<String[]>();
		for (int i = 1; i <= hashtable.size(); i++) {
			if (hashtable.get(i)[hashtable.get(i).length - 1].equals("id")) {
				identfiertList.add(hashtable.get(i));
			} else {
				continue;
			}
		}
		return identfiertList;
	}

	/**
	 * Prints the Symboltable.
	 */
	public void printAll() {
		for (int i = 1; i <= hashtable.size(); i++) {
			System.out.print("\n" + i + " : ");
			for (int j = 0; j < hashtable.get(i).length; j++) {
				System.out.print(hashtable.get(i)[j] + ", ");
			}
		}
	}
}