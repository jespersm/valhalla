/*
 * @test /nodynamiccopyright/
 * @bug 8210906
 * @summary [lworld] default value creation should not impose raw types on users.
 * @compile/fail/ref=PolyDefault.out -Xlint:all -Werror -XDrawDiagnostics -XDdev PolyDefault.java
 */
import java.util.LinkedList;
import java.util.concurrent.Callable;

public primitive class PolyDefault<E> implements Callable<E> {
    E value;
    protected PolyDefault() { this.value = E.default; }
    PolyDefault(E value) { this.value = value; }

    @Override
    public E call() throws Exception {
        return value;
    }

    static primitive class NotParameterized {
        public int i = 0;
    }

    @FunctionalInterface
    interface PolyProducer {
        PolyDefault<String> produce();
    }

    public static void main(String [] args) throws Exception {
        PolyDefault<LinkedList<Long>> foo = PolyDefault.default; // Acts as poly expression
        LinkedList<Long> a = foo.call(); // This should fine

        var nonGenericDefault = NotParameterized.default;
        var genericDefault1 = PolyDefault<LinkedList<Long>>.default;

        PolyProducer genericDefault = () -> PolyDefault.default;
    }

}
