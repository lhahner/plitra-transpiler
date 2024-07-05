package org.bachelor.transpiler.pl1transpiler.checker;

//TODO
public class StringChecker implements ITypeExpression {
	
	private String assignment;
	
	public String getAssignement() {
		return assignment;
	}

	public void setAssignement(String assignement) {
		this.assignment = assignement;
	}

	public StringChecker(String assignment) {
		this.assignment = assignment;
	}
	
	public void getType() throws IllegalArgumentException{
		if(!(assignment.charAt(0) == '\'')) {
			throw new IllegalArgumentException("Assignment for String Type not allowed.");
		}
	}
}	
