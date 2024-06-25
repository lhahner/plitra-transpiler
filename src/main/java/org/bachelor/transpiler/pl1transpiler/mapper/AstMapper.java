package org.bachelor.transpiler.pl1transpiler.mapper;

import java.util.HashMap;
import java.util.Hashtable;

import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.AssignMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.BodyMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.CallMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.DeclarationMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.DisplayMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.EndMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.HeadMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.PackageMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.ProgramMapper;
import org.bachelor.transpiler.pl1transpiler.mapper.NodeMapper.TerminateMapper;
import org.bachelor.transpiler.pl1transpiler.parser.Pl1ParserTreeConstants;
import org.bachelor.transpiler.pl1transpiler.parser.SimpleNode;

public class AstMapper {
	static HashMap<Integer, ITranslationBehavior> astMapper = new HashMap<Integer, ITranslationBehavior>();
	Pl1ParserTreeConstants TreeSymbols;

	public AstMapper() {
		astMapper.put(TreeSymbols.JJTPROGRAM, new ProgramMapper());
		astMapper.put(TreeSymbols.JJTPACKAGE, new PackageMapper());
		astMapper.put(TreeSymbols.JJTEND, new EndMapper());
		astMapper.put(TreeSymbols.JJTBODY, new BodyMapper());
//		astMapper.put(TreeSymbols.JJTGLOBAL, null);
//		astMapper.put(TreeSymbols.JJTCONDITION, null);
		astMapper.put(TreeSymbols.JJTHEAD, new HeadMapper());
//		astMapper.put(TreeSymbols.JJTBRANCH, null);
//		astMapper.put(TreeSymbols.JJTLOOP, null);
//		astMapper.put(TreeSymbols.JJTDO, null);
//		astMapper.put(TreeSymbols.JJTDO_TYPE_3, null);
//		astMapper.put(TreeSymbols.JJTDO_TYPE_4, null);
//		astMapper.put(TreeSymbols.JJTSPECIFICATION, null);
//		astMapper.put(TreeSymbols.JJTBOOL, null);
//		astMapper.put(TreeSymbols.JJTOP, null);
//		astMapper.put(TreeSymbols.JJTENTRY, null);
		astMapper.put(TreeSymbols.JJTDISPLAY, new DisplayMapper());
		astMapper.put(TreeSymbols.JJTASSIGN, new AssignMapper());
//		astMapper.put(TreeSymbols.JJTVALUES, null);
//		astMapper.put(TreeSymbols.JJTRETURNS, null);
//		astMapper.put(TreeSymbols.JJTOPTIONS, null);
		astMapper.put(TreeSymbols.JJTCALLS, new CallMapper());
//		astMapper.put(TreeSymbols.JJTSTRINGLIST, null);
//		astMapper.put(TreeSymbols.JJTFETCH, null);
//		astMapper.put(TreeSymbols.JJTSTATEMENT, null);
//		astMapper.put(TreeSymbols.JJTRELEASE, null);
		astMapper.put(TreeSymbols.JJTTERMINATE, new TerminateMapper());
//		astMapper.put(TreeSymbols.JJTRETURN_VAL, null);
//		astMapper.put(TreeSymbols.JJTPARA, null);
		astMapper.put(TreeSymbols.JJTVAR, new DeclarationMapper());
//		astMapper.put(TreeSymbols.JJTMINOR, null);
//		astMapper.put(TreeSymbols.JJTHIERACHIE, null);
//		astMapper.put(TreeSymbols.JJTTYPE, null);
//		astMapper.put(TreeSymbols.JJTFILE, null);
//		astMapper.put(TreeSymbols.JJTSTREAM, null);
//		astMapper.put(TreeSymbols.JJTRECORD, null);
//		astMapper.put(TreeSymbols.JJTINPUT, null);
//		astMapper.put(TreeSymbols.JJTOUTPUT, null);
//		astMapper.put(TreeSymbols.JJTUPDATE, null);
//		astMapper.put(TreeSymbols.JJTSEQUENTIAL, null);
//		astMapper.put(TreeSymbols.JJTDIRECT, null);
//		astMapper.put(TreeSymbols.JJTBUFFERED, null);
//		astMapper.put(TreeSymbols.JJTUNBUFFERED, null);
//		astMapper.put(TreeSymbols.JJTPICTUREEXPRESSION, null);
//		astMapper.put(TreeSymbols.JJTPICTURE, null);
//		astMapper.put(TreeSymbols.JJTWIDEPIC, null);
//		astMapper.put(TreeSymbols.JJTPICTUREATTRIBUTE, null);
//		astMapper.put(TreeSymbols.JJTPICTURELETTER, null);
//		astMapper.put(TreeSymbols.JJTNUMBER, null);
//		astMapper.put(TreeSymbols.JJTPICTURELENGTH, null);
//		astMapper.put(TreeSymbols.JJTLOCATOR, null);
//		astMapper.put(TreeSymbols.JJTPOINTER, null);
//		astMapper.put(TreeSymbols.JJTOFFSET, null);
//		astMapper.put(TreeSymbols.JJTHANDLE, null);
//		astMapper.put(TreeSymbols.JJTSTRING, null);
//		astMapper.put(TreeSymbols.JJTWIDECHAR, null);
//		astMapper.put(TreeSymbols.JJTGRAPHIC, null);
//		astMapper.put(TreeSymbols.JJTCHAR, null);
//		astMapper.put(TreeSymbols.JJTBIT, null);
//		astMapper.put(TreeSymbols.JJTARITHMETIC, new ArithmeticMapper());
//		astMapper.put(TreeSymbols.JJTSIGNED, null);
//		astMapper.put(TreeSymbols.JJTUNSIGNED, null);
//		astMapper.put(TreeSymbols.JJTDECIMAL, null);
//		astMapper.put(TreeSymbols.JJTFIXED, null);
//		astMapper.put(TreeSymbols.JJTFLOAT, null);
//		astMapper.put(TreeSymbols.JJTBINARY, null);
//		astMapper.put(TreeSymbols.JJTSIZE, null);
//		astMapper.put(TreeSymbols.JJTLENGTH, null);
//		astMapper.put(TreeSymbols.JJTINT, null);
//		astMapper.put(TreeSymbols.JJTDOUBLE, null);
//		astMapper.put(TreeSymbols.JJTREAL, null);
//		astMapper.put(TreeSymbols.JJTCOMPLEX, null);
//		astMapper.put(TreeSymbols.JJTID, null);

	}
}
