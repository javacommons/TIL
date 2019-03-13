import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.codehaus.janino.Java;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.UnparseVisitor;

public class Main {

	public static void main(String[] args) throws Exception {
		File file = new File("E:\\pleiades-4.8.0-java-win-64bit-jre_20180923\\workspace\\groovy02\\srcdir\\pkg2\\B.java");
		if (!file.exists()) {
			System.out.print("ファイルが存在しません");
			return;
		}
		InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
		Java.CompilationUnit cu = new Parser(new Scanner("test.java", fileReader)).parseCompilationUnit();
		// Manipulate the AST in memory.
		// ...
		// Convert the AST back into text.
		UnparseVisitor.unparse(cu, new OutputStreamWriter(System.out));
		System.out.println("HELLO\u3042\u3044\u3046");
	}

}
