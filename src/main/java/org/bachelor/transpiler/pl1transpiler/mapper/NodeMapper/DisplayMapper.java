package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;

import java.util.ArrayList;

import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;

public class DisplayMapper extends Mapper implements ITranslationBehavior{
	
	private final String SYSOUT = super.javaWords.SYSOUT.getValue();
	private String message;
	
	private final String SYSIN =  super.javaWords.SYSIN.getValue();
	private String parameter;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getSYSOUT() {
		return this.SYSOUT;
	}
	
	public String getSYSIN() {
		return this.SYSIN;
	}
	
	public String translate(SimpleNode simpleNode) {
		this.mapDisplayNode(simpleNode);
		if(this.getParameter() != null) {
			return this.getSYSOUT()
				     + "(" + this.getMessage() + "); \n"
				     + this.getParameter()
				     + " = "
				     + this.getSYSIN() + "; \n ";
		}
		else {
			return this.getSYSOUT()
					  + "(" + this.getMessage() + ");";
		}
		
	}
	
	private void mapDisplayNode(SimpleNode simpleNode){
		
		ArrayList<String > values = (ArrayList<String>)simpleNode.jjtGetValue();
		for(String value : values) {
			if(new SymbolTable().getBySymbol(value) != null) {
				this.setParameter(value);
			}
			else {
				this.setMessage(value);
			}
		}
	}
}
