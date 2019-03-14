import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.google.googlejavaformat.java.Formatter;

import io.github.optjava.generated.Java9Lexer;
import io.github.optjava.generated.Java9Parser;
import io.github.optjava.generated.Java9Parser.CompilationUnitContext;
import io.github.optjava.generated.Java9Parser.ImportDeclarationContext;
import io.github.optjava.generated.Java9Parser.PackageDeclarationContext;
import io.github.optjava.generated.Java9Parser.TypeDeclarationContext;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Listener extends Java9AbstractListener {

  CommonTokenStream tokens = null;
  Java9Parser parser = null;
  String wholeSource = "";

  public static void main(String[] args) throws Exception {
    OkHttpClient client = new OkHttpClient();
    Request request =
        new Request.Builder()
            // .url("https://github.com/javacommons/TIL/raw/master/testdata/B.java")
            .url(
                "https://github.com/javacommons/TIL/raw/dadb18fbc70516020f35365068911af5e2a1162c/testdata/B.java")
            .get()
            .build();
    Response response = client.newCall(request).execute();
    String text = response.body().string();
    System.out.println(text);
    CharStream stream = CharStreams.fromString(text);
    Listener l = new Listener();
    System.out.println(l.parse(stream));
    String formattedSource = new Formatter().formatSource(l.wholeSource);
    System.out.println(formattedSource);
  }

  public String parse(final CharStream stream) {
    try {
      final Java9Lexer lexer = new Java9Lexer(stream);
      this.tokens = new CommonTokenStream(lexer);
      this.parser = new Java9Parser(tokens);
      ParseTree tree = parser.compilationUnit();
      System.out.println(tree.toStringTree(parser));
      ParseTreeWalker walker = new ParseTreeWalker();
      walker.walk(this, tree);
      return tree.toStringTree(parser);
    } catch (IllegalArgumentException iae) {
      throw iae;
    }
  }

  public String unparse(ParserRuleContext ctx) {
    Interval interval = ctx.getSourceInterval();
    int start = interval.a;
    int stop = interval.b;
    if (start < 0 || stop < 0) return "";
    this.tokens.fill();
    if (stop >= tokens.size()) stop = tokens.size() - 1;
    StringBuilder buf = new StringBuilder();
    for (int i = start; i <= stop; i++) {
      Token t = tokens.get(i);
      if (t.getType() == Token.EOF) break;
      if (i > start) buf.append(" ");
      buf.append(t.getText());
    }
    return buf.toString();
  }

  @Override
  public void enterCompilationUnit(CompilationUnitContext ctx) {
    System.out.println("enterCompilationUnit(): " + unparse(ctx));
    this.wholeSource = unparse(ctx);
  }

  @Override
  public void enterPackageDeclaration(PackageDeclarationContext ctx) {
    System.out.println("enterPackageDeclaration(): " + unparse(ctx));
    System.out.println("<" + unparse(ctx.packageName()) + ">");
  }

  @Override
  public void enterImportDeclaration(ImportDeclarationContext ctx) {
    System.out.println("enterImportDeclaration(): " + unparse(ctx));
  }

  @Override
  public void enterTypeDeclaration(TypeDeclarationContext ctx) {
    System.out.println("enterTypeDeclaration(): " + unparse(ctx));
  }
}
