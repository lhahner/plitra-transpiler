package org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper;


import org.bachelor.transpiler.pl1transpiler.errorhandling.MappingException;
import org.bachelor.transpiler.pl1transpiler.mapper.ITranslationBehavior;
import org.bachelor.transpiler.pl1transpiler.mapper.Mapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;
import org.bachelor.transpiler.pl1transpiler.symboltable.SymbolTable;
import org.bachelor.transpiler.pl1transpiler.symboltable.Template;

/**
 * This class is used to translate an Assign Node in
 * the syntaxtree provided by the parser.
 * It will be instantiated by the Context-class @see {@link #TranslationMapper} 
 * and called whenever the @see {@link #Mapper}-class finds a Assign Node.
 * The Assignment is depenig from the context of the PL/I Assignment and/or
 * from the Java-Context. For example the operator will change from
 * an initalization method, which was used by Datatype-Classes like the
 * Picture-Class or the Char-Class. 
 * 
 * <h4>Example: </h4><br>
 * <h5>PL/I-Code</h5><br>
 * <code>
 * DCL var_1 DECIMAL(6) INIT(4); <br>
 * DCL var_2 PICTURE '9V99' INIT('9.99'); <br>
 * </code>
 * <br>
 * <h5>Java-Representation</h5><br>
 * 
 * <code>
 * @DECIMAL(6)
 * public double var_1 = 4; <br>
 * public PICUTRE = var_2 new PICTURE("[0-9]\\.[0-9][0-9]"); <br>
 * var_2.init("9.99"); <br>
 * </code>
 * <br>
 * @author Lennart Hahner
 */
public class AssignMapper implements ITranslationBehavior {

	/** Identifier stored in the syntaxtree */
	private String identifier = "";

	/** Depends on the translation used in Java */
	private String operator = Template.ASSIGN.getValue();

	/** The assignment. */
	private String assignment;

	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the assignment.
	 *
	 * @return the assignment
	 */
	public String getAssignment() {
		return assignment;
	}

	/**
	 * Sets the assignment.
	 *
	 * @param assignment the new assignment
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment.contains("'") ? assignment.replaceAll("'", "\"") : assignment;
	}

	/**
	 * Behavior this Strategy should implement
	 *
	 * @param simpleNode the AssginNode from syntaxtree
	 * @return the Java-representation
	 */
	public String translate(SimpleNode simpleNode) throws MappingException {
		mapAssignNode(simpleNode);
		
		if (this.getAssignment() == null) {
			throw new MappingException("Assignment not definied for Assignment" + simpleNode.toString() + " in "
					+ this.getClass().toString());
		}
		return this.getIdentifier() + this.getOperator() + this.getAssignment() + ";";

	}

	/**
	 * Mapping of the ASSIGN Node. The translation is depending 
	 * on the context it is found in. Sometimes the Assigned
	 * Variable is not needed to be mentioned, sometimes it is.
	 * Thats why a context validation is needed to make sure it
	 * is mapped in the correct context.
	 *
	 * @param simpleNode ASSIGN-Node
	 */
	public void mapAssignNode(SimpleNode simpleNode) throws MappingException {
		String[] assignValues = this.setAssignValues(simpleNode);
		if (new Mapper().hasChildren(simpleNode)) {
			mapCalcNode(simpleNode);
			if (assignValues != null) {
				if (SymbolTable.getInstance().getBySymbol(assignValues[0]) != null) {
					this.setIdentifier(assignValues[0]);
				}
			}
			return;
		}
		if (assignValues != null) {
			if (SymbolTable.getInstance().getBySymbol(assignValues[0]) != null) {
				this.setIdentifier(assignValues[0]);
			}
			// TODO Really bad... can be "" and sets assignment
			if (assignValues[1] != null) {
				this.setAssignment(assignValues[1]);
			}
		}
	}

	/**
	 * This will call the @see {@link #CalcMapper}
	 * as a result this class sets the
	 * assignment to the provided mathimatical equation
	 * from the PL/I-Code
	 *
	 * @param simpleNode the simple node
	 */
	public void mapCalcNode(SimpleNode simpleNode) throws MappingException {
		this.setAssignment(new CalcMapper().translate((SimpleNode) simpleNode.jjtGetChild(0)));
	}

	/**
	 * This method is used to set the variable which should be assigned and with
	 * which values the variable should be assigned. It happens that a variable is
	 * initialized directly after declaration. In this case an explicit
	 * identificator is not defined. Then we need to map the sibling Node of the the
	 * Assign Node to get the identification.
	 * 
	 * @param simpleNode the ASSIGN Node of the parsetree
	 * @return the array containing all assign information
	 */
	public String[] setAssignValues(SimpleNode simpleNode) {
		Pl1ParserTreeConstants treeSymbols = null;
		String[] assignValues = new String[2];
		assignValues = (String[]) simpleNode.jjtGetValue();
		return assignValues;
	}
}
