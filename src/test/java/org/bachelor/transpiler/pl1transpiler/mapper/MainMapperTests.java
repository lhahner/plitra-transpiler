package org.bachelor.transpiler.pl1transpiler.mapper;
//Api imports
import org.junit.jupiter.api.Test;
import org.bachelor.transpiler.pl1transpiler.mapper.MainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class MainMapperTests {
	
	MainMapper java_generator;
	@BeforeEach
	void init() {
		java_generator = new MainMapper();
	}
	
	@Test
	@DisplayName("concatExpression")
	void concatExpression_returnProgramAsAString_ProgramString() {
		
	}
	
	@Test
	@DisplayName("createExpression")
    void createExpression_shouldCreateExpressionInStrucuture_ExpressionString() {
		
	}
	
	@Test
	@DisplayName("createStrucuture")
	void createStrucuture_shouldCreateStructureBasedOneExpressions_StrucutreString() {
		
	}
	
	@Test
	@DisplayName("addType")
	void addType_useTreeNodeToDefineType_TypeString() {
		
	}
	
	@Test
	@DisplayName("addPictureExpression")
	void addPictureExpression_shouldProvidePictureType_PictureString() {
		
	}
	
	@Test
	@DisplayName("addObject")
	void addObject_addsAnObjectToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addArithmetic")
	void addArithmetic_addsAnArithmeticToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addPrefixDecimal")
	void addPrefixDecimal_addsAnPrefixDecimalToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addComplex")
	void addComplex_addsAnComplexDecimalToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addBinary")
	void addBinary_addsAnBinaryToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addBigDecimal")
	void addBigDecimal_addsBigDecimalToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addFloat")
	void addFloat_addFloatToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addClass")
	void addClass_addClassToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addMethod")
	void addMethod_addMethodToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addReturnvalue")
	void addReturnvalue_addReturnvalueToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addGetter")
	void addGetter_addGetterToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("addSetter")
	void addSetter_addSetterToExpressionArray_javaExpressionIndex() {
		
	}
	
	@Test
	@DisplayName("getScope")
    void getScope_returnScopeFromSymbolTable_numberRepresentingTheScope() {
		
	}
	
	@Test
	@DisplayName("getNextScope")
    void getNextScope_returnNextScopeFromSymbolTable_numberRepresentingTheScope() {
		
	}
	
	@Test
	@DisplayName("getId")
    void getId_returnIdentifierFromSymbolTable_IdentifierText() {
		
	}
	
	@Test
	@DisplayName("addIdentifier")
    void addIdentifier_addsIdentifierToSymbolTable_IdentifierText() {
		
	}
	
	
}
