package org.bachelor.transpiler.pl1transpiler.mapper;

import java.util.HashMap;
import java.util.Hashtable;

import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.AssignMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.BodyMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.BooleanExpressionMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.BranchMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.BuiltInMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.CalcMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.CallMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.DeclarationMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.DisplayMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.EndMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.HeadMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.LoopMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.PackageMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.ProgramMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.ReadFileMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.TerminateMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.WriteMapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

/**
 * This class is used to store all strategy-classes
 * for the Strategy design-pattern. All classes
 * assigned to @see {@link #astMapper} should implement
 * the @see {@link #ITranslationBehavior} interface.
 * This class gives also an impression how many
 * of the PL/I-Expressions the Transpiler can
 * handle are implemented.
 * 
 * @author Lennart Hahner
 */
public class AstMapper {
	
	/** The HashMap which contains all the Id's and Mapping Objects */
	static HashMap<Integer, ITranslationBehavior> astMapper = new HashMap<Integer, ITranslationBehavior>();
	
	/** Instantiates a new AstMapper. */
	public AstMapper() {
		astMapper.put(Pl1ParserTreeConstants.JJTPROGRAM, new ProgramMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTPACKAGE, new PackageMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTEND, new EndMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTBODY, new BodyMapper());
//		astMapper.put(Pl1ParserTreeConstants.JJTGLOBAL, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTCONDITION, null);
		astMapper.put(Pl1ParserTreeConstants.JJTHEAD, new HeadMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTWRITE_FILE, new WriteMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTBRANCH, new BranchMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTLOOP, new LoopMapper());
//		astMapper.put(Pl1ParserTreeConstants.JJTCALC, new CalcMapper());
//		astMapper.put(Pl1ParserTreeConstants.JJTDO, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTDO_TYPE_3, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTDO_TYPE_4, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTSPECIFICATION, null);
		astMapper.put(Pl1ParserTreeConstants.JJTBOOL, new BooleanExpressionMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTREAD_FILE, new ReadFileMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTDISPLAY, new DisplayMapper());
		astMapper.put(Pl1ParserTreeConstants.JJTASSIGN, new AssignMapper());
//		astMapper.put(Pl1ParserTreeConstants.JJTVALUES, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTRETURNS, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTOPTIONS, null);
		astMapper.put(Pl1ParserTreeConstants.JJTCALLS, new CallMapper());
//		astMapper.put(Pl1ParserTreeConstants.JJTSTRINGLIST, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTFETCH, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTSTATEMENT, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTRELEASE, null);
		astMapper.put(Pl1ParserTreeConstants.JJTTERMINATE, new TerminateMapper());
//		astMapper.put(Pl1ParserTreeConstants.JJTRETURN_VAL, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTPARA, null);
		astMapper.put(Pl1ParserTreeConstants.JJTVAR, new DeclarationMapper());
//		astMapper.put(Pl1ParserTreeConstants.JJTMINOR, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTHIERACHIE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTTYPE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTFILE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTSTREAM, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTRECORD, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTINPUT, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTOUTPUT, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTUPDATE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTSEQUENTIAL, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTDIRECT, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTBUFFERED, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTUNBUFFERED, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTPICTUREEXPRESSION, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTPICTURE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTWIDEPIC, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTPICTUREATTRIBUTE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTPICTURELETTER, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTNUMBER, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTPICTURELENGTH, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTLOCATOR, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTPOINTER, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTOFFSET, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTHANDLE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTSTRING, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTWIDECHAR, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTGRAPHIC, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTCHAR, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTBIT, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTARITHMETIC, new ArithmeticMapper());
//		astMapper.put(Pl1ParserTreeConstants.JJTSIGNED, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTUNSIGNED, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTDECIMAL, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTFIXED, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTFLOAT, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTBINARY, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTSIZE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTLENGTH, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTINT, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTDOUBLE, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTREAL, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTCOMPLEX, null);
//		astMapper.put(Pl1ParserTreeConstants.JJTID, null);

	}
}
