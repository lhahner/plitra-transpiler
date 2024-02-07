package pl1transpiler.tools.Pl1;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;

public class SymbolTable {
	
	static Hashtable<Integer, String[]> hashtable = new Hashtable<>();
	
	public SymbolTable () {
		try {
		Pl1Symbols pl1Symbols;
		 for (Pl1Symbols s : Pl1Symbols.values()) {
			   int i = 0;
			   String[] tmp = {s.toString(), "word"};
			   insert(tmp);
			   i++;
	        }
		}catch(Exception e) {
			System.out.println("Error in Symboltable" + e);
		}
	}
	
	public static void insert(String [] symbol) throws Exception {
		
		try {
		
		if(getBySymbol(symbol[0]) != null) {
			return;
		}
		int id = hashtable.size() + 1;
		hashtable.put(id, symbol);
		
		} catch(Exception e) {
			System.out.println(e);
		}
		
	}
	public void delete (int key) {
		hashtable.remove(key);
	}
	
	public static String[] getById(int key) {
		return hashtable.get(key);
	}
	
	public static String getBySymbol(String symbol) {
		
			for(int i = 1; i<=hashtable.size(); i++) {
				
					if(getById(i)[0].equals(symbol)) {
						return getById(i)[0];
					}
					else {
						continue;
					}
				
			
		}
		return null;
	}
	
	//can only be called when the scanner was executed once
	public static ArrayList<String []> getAllIdentifier() {
		ArrayList<String []> identfiertList = new ArrayList<String []>();
		for(int i = 1; i<=hashtable.size(); i++) {
		
			if(getById(i)[getById(i).length-1].equals("id")) {
				identfiertList.add(getById(i));
			}
			else {
				continue;
			}
		}
		
		
		return identfiertList;
	}

	
	public void printAll() {
		for(int i = 1; i<=hashtable.size(); i++) {
			
			System.out.print("\n" + i + " : ");
			
			for(int j = 0; j < getById(i).length; j++) {
				
			System.out.print(getById(i)[j] + ", ");
			
			}
		}
	}
	
}