import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.codehaus.janino.CompileException;
import org.codehaus.janino.DebuggingInformation;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.Parser.ParseException;
import org.codehaus.janino.Scanner.ScanException;

public class Main {

    public static void
    main(String[] args) throws CompileException, InvocationTargetException, ParseException, ScanException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        // Now here's where the story begins...
        ExpressionEvaluator ee = new ExpressionEvaluator();

        // The expression will have two "int" parameters: "a" and "b".
        ee.setParameters(new String[] { "a", "b" }, new Class[] { int.class, int.class });

        // And the expression (i.e. "result") type is also "int".
        ee.setExpressionType(int.class);

        // And now we "cook" (scan, parse, compile and load) the fabulous expression.
        ee.cook("a + b");

        // Eventually we evaluate the expression - and that goes super-fast.
        int result = (Integer) ee.evaluate(new Object[] { 19, 23 });
        System.out.println(result);

        // Sample code that reads, scans, parses, compiles and loads
	     // "A.java" and "B.java", then instantiates an object of class
	     // "A" and invokes its "run()" method.
	     ClassLoader cl = new JavaSourceClassLoader(
	    	Thread.currentThread().getContextClassLoader(), //this.getClass().getClassLoader(),  // parentClassLoader
	        new File[] { new File("srcdir") }, // optionalSourcePath
	        (String) "UTF-8",                     // optionalCharacterEncoding
	        DebuggingInformation.NONE          // debuggingInformation
	     );

	     // Load class A from "srcdir/pkg1/A.java", and also its superclass
	     // B from "srcdir/pkg2/B.java":
	     Object o = cl.loadClass("pkg1.A").newInstance();

	     // Class "B" implements "Runnable", so we can cast "o" to
	     // "Runnable".
	     ((Runnable) o).run(); // Prints "HELLO" to "System.out".
    }
}
