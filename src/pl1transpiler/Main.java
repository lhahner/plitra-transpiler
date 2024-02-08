package pl1transpiler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import pl1transpiler.generated.Pl1Parser;
import pl1transpiler.generated.SimpleNode;
import pl1transpiler.tools.Pl1.SymbolTable;

public class Main {
	static String inputFile;
	static Lexer lex;
	static Pl1Parser pl1Parser;

	/**
	 * @param args[0] holds the path to the input file.
	 */
	public static void main(String[] args) {
		inputFile = args[0];
		
		SymbolTable st = new SymbolTable();
		String content = "";
		String token = "";

		try {
			final File pl1File = new File(inputFile);
			final File throughputFile = new File("./src/pl1transpiler/var/code_tp.pli");
			FileInputStream inputStream = new FileInputStream(pl1File);
			FileWriter writer = new FileWriter(throughputFile);
			Scanner sc = new Scanner(inputStream);
			lex = new Lexer();

			while (sc.hasNext()) {
				content = sc.useDelimiter(("[.:?!\"]+")).next();
				content = content.replaceAll("/*[A-Z, a-z, 0-9, *, \n, \t, \r]*/", "");
				content = content.replaceAll(";", " \n");
				content = content.replaceAll("\\),", "), ");
				content = content.replaceAll(",\n", ", ");
			}
			while (content.length() > 0) {

				if (lex.getToken(content) == "failed") {
					break;
				}

				token = lex.getToken(content);
				writer.write(token + " ");
				content = content.substring(token.length()).trim();
			}
			sc.close();
			writer.close();
			FileInputStream parseInputStream = new FileInputStream(throughputFile);

			pl1Parser = new Pl1Parser(parseInputStream);
			SimpleNode root = pl1Parser.program();
			st.printAll();
			root.dump(" ");

			// load Java Parser and give Pl1parser
			JavaGenerator jP = new JavaGenerator(pl1Parser);

			// create expression with Parsetree
			jP.createExpression(root);

			System.out.println(jP.concatExpression());
			final File javaFile = new File("./src/pl1transpiler/output/Main.java");
			FileWriter writeFile = new FileWriter(javaFile);
			writeFile.write(jP.concatExpression());
			writeFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
