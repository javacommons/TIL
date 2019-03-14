// srcdir/pkg2/B.java

package pkg1.pkg2;

import java.lang.*; import a.b.C;

public @interface ExampleAnnotation {
}

class ABC {public String fldA="あいう"; List<String> fldB; }
    
public class B implements Runnable {
    public void run() {
        for(int i=0;i<10;i++) System.out.println("HELLOあいう");
    }
}
