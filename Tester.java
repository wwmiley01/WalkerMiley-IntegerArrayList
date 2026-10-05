interface TestCase {
    void run();
}

public class Tester {
    private static int passed = 0;
    private static int failed = 0;

    // "line 166: " -- the line in THIS file that called check() or assertion()
    private static String where()
    {
        StackTraceElement[] st = new Throwable().getStackTrace();
        return "line " + st[2].getLineNumber() + ": ";
    }

    public static void assertion(boolean expression)
    {
        if (expression == false)
            throw new AssertionError(where() + "assertion failed");
    }

    // check("a.get(2)", a.get(2), 30) fails with:  line 166: a.get(2) was 2, expected 30
    public static void check(String what, int actual, int expected)
    {
        if (actual != expected)
            throw new AssertionError(where() + what + " was " + actual + ", expected " + expected);
    }

    public static void check(String what, boolean actual, boolean expected)
    {
        if (actual != expected)
            throw new AssertionError(where() + what + " was " + actual + ", expected " + expected);
    }

    public static void check(String what, String actual, String expected)
    {
        if (!actual.equals(expected))
            throw new AssertionError(where() + what + " was \"" + actual + "\", expected \"" + expected + "\"");
    }
    private static void testCase(String name, TestCase test) {
        System.out.println("Starting test: " + name);
        try{
            test.run();
            System.out.println("Test okay: " + name);
            passed++;
            System.out.println("------------------------------------------------------------------------");
        }catch (AssertionError e) {
            System.out.println("TEST FAILED: " + name);
            System.out.println("    " + e.getMessage());
            failed++;
            System.out.println("!!!----FAIL----!!!!!!----FAIL----!!!!!!----FAIL----!!!!!!----FAIL----!!!");
			System.out.println("------------------------------------------------------------------------");
        }catch (Exception ex)
        {
            System.out.println("TEST FAILED: " + name);
            System.out.println("    threw " + ex + "  (" + crashLine(ex) + ")");
            failed++;
            System.out.println("!!!----FAIL----!!!!!!----FAIL----!!!!!!----FAIL----!!!!!!----FAIL----!!!");
			System.out.println("------------------------------------------------------------------------");
        }

    }

    // where an unexpected exception came from: the first line in a list class or in Tester
    private static String crashLine(Exception ex)
    {
        for (StackTraceElement e : ex.getStackTrace())
            if (!e.getClassName().startsWith("java."))
                return e.getFileName() + " line " + e.getLineNumber();
        return "unknown line";
    }

    // run a test case that should throw an exception
    private static void exceptTestCase(String name, TestCase test) {
        boolean threwRight = false;
        System.out.println("Starting test: Exception case: " + name);
        try {
            test.run();
            System.out.println("No exception was thrown by: " + name);
        } catch(IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException thrown by: " + name);
            threwRight = true;
        } catch(Exception e) {
            System.out.println("Different exception thrown by: " + name + "  " + e);
            threwRight = false;
        }

        if (threwRight)
        {
            System.out.println("Exception Test okay: " + name);
            passed++;
            System.out.println("------------------------------------------------------------------------");
        }
        else
        {
            System.out.println("EXCEPTION TEST FAILED: " + name);
            failed++;
            System.out.println("!!!----FAIL----!!!!!!----FAIL----!!!!!!----FAIL----!!!!!!----FAIL----!!!");
			System.out.println("------------------------------------------------------------------------");
        }

    }

    public static void main(String[] args) {
        Tester.testCase("construct list, add(item) and get(index)", () -> {
            IntegerArrayList a = new IntegerArrayList();

            check("a.size()", a.size(), 0);

            a.add(1);
            check("a.size()", a.size(), 1);
            check("a.get(0)", a.get(0), 1);

            a.add(2);
            check("a.size()", a.size(), 2);
            check("a.get(0)", a.get(0), 1);
            check("a.get(1)", a.get(1), 2);

            a.add(-17);
            check("a.size()", a.size(), 3);
            check("a.get(0)", a.get(0), 1);
            check("a.get(1)", a.get(1), 2);
            check("a.get(2)", a.get(2), -17);
            System.out.println(a);
        });

        Tester.exceptTestCase("get(invalidPositiveIndex)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1);
            a.get(1);
        });

        Tester.exceptTestCase("get() on empty list", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.get(0);
        });

        Tester.exceptTestCase("get(negativeIndex)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.get(-1);
        });
        
        Tester.testCase("set", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1);
            a.add(2);
            a.add(3);
            a.set(0,10);
            a.set(2,30);
            a.set(1,20);

            check("a.get(0)", a.get(0), 10);
            check("a.get(1)", a.get(1), 20);
            check("a.get(2)", a.get(2), 30);
            System.out.println(a);
        });
        
        Tester.exceptTestCase("set(invalidPositiveIndex, value)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1);
            a.set(1,100);
        });

        Tester.exceptTestCase("set(0, value) on empty list", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.set(0,100);
        });
        Tester.exceptTestCase("set(negativeIndex, value)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.set(-1, 100);
        });
        

        Tester.testCase("add(0, item)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            check("a.size()", a.size(), 0);
            a.add(1);
            check("a.size()", a.size(), 1);
            check("a.get(0)", a.get(0), 1);
            a.add(0, 2);
            check("a.size()", a.size(), 2);
            check("a.get(0)", a.get(0), 2);
            check("a.get(1)", a.get(1), 1);
            a.add(0, 3);
            check("a.size()", a.size(), 3);
            check("a.get(0)", a.get(0), 3);
            check("a.get(1)", a.get(1), 2);
            check("a.get(2)", a.get(2), 1);
            a.add(10);
            check("a.get(3)", a.get(3), 10);
            System.out.println(a);
        });

        Tester.testCase("add(middleIndex,item)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            check("a.size()", a.size(), 0);
            a.add(1);
            a.add(2);
            check("a.size()", a.size(), 2);
            check("a.get(0)", a.get(0), 1);
            check("a.get(1)", a.get(1), 2);
            a.add(1, 20);
            check("a.size()", a.size(), 3);
            check("a.get(0)", a.get(0), 1);
            check("a.get(1)", a.get(1), 20);
            check("a.get(2)", a.get(2), 2);
            a.add(2, 30);
            check("a.size()", a.size(), 4);
            check("a.get(0)", a.get(0), 1);
            check("a.get(1)", a.get(1), 20);
            check("a.get(2)", a.get(2), 30);
            check("a.get(3)", a.get(3), 2);
            a.add(10);
            check("a.get(4)", a.get(4), 10);
            System.out.println(a);
        });

        Tester.testCase("add(endIndex,item)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            check("a.size()", a.size(), 0);
            a.add(0, 1);
            check("a.size()", a.size(), 1);
            check("a.get(0)", a.get(0), 1);
            a.add(1, 2);
            check("a.size()", a.size(), 2);
            check("a.get(0)", a.get(0), 1);
            check("a.get(1)", a.get(1), 2);
            a.add(2, 3);
            check("a.size()", a.size(), 3);
            check("a.get(0)", a.get(0), 1);
            check("a.get(1)", a.get(1), 2);
            check("a.get(2)", a.get(2), 3);
            a.add(3, 4);
            check("a.get(3)", a.get(3), 4);
            a.add(4, 5);
            check("a.get(4)", a.get(4), 5);
            a.add(5, 6);
            check("a.get(5)", a.get(5), 6);
            a.add(6, 7);
            check("a.get(6)", a.get(6), 7);
            System.out.println(a);
            a.add(7, 8);
            check("a.get(7)", a.get(7), 8);
            a.add(8, 9);
            check("a.get(8)", a.get(8), 9);
            a.add(9, 10);
            check("a.get(9)", a.get(9), 10);
            a.add(10, 11);
            check("a.get(10)", a.get(10), 11);
            System.out.println(a);
        });
        
        Tester.testCase("add() requiring expansion of internal array", () -> {
            IntegerArrayList a = new IntegerArrayList();
            for(int i = 0; i < 5000; i++) {
                a.add(1);
            }
            for(int i = 0; i < 5000; i++) {
                a.add(2);
            }
            check("a.size()", a.size(), 10000);
            check("a.get(0)", a.get(0), 1);
            check("a.get(4000)", a.get(4000), 1);
            check("a.get(5000)", a.get(5000), 2);
            check("a.get(7000)", a.get(7000), 2);

            for(int i = 0; i < 5000; i++) {
                check("a.remove(a.size() - 1)", a.remove(a.size() - 1), 2);
            }

            check("a.size()", a.size(), 5000);
            a.add(0, 3);
            check("a.get(0)", a.get(0), 3);
            check("a.get(1)", a.get(1), 1);
            check("a.get(a.size() - 1)", a.get(a.size() - 1), 1);
            // (no println here: the list has 5,001 elements)
        });
        
        Tester.testCase("add(index, val) requiring expansion of internal array", () -> {
            IntegerArrayList a = new IntegerArrayList();
            for(int i = 0; i < 5000; i++) {
                a.add(i,1);
            }
            for(int i = 5000; i < 10000; i++) {
                a.add(i,2);
            }
            check("a.size()", a.size(), 10000);
            check("a.get(0)", a.get(0), 1);
            check("a.get(4000)", a.get(4000), 1);
            check("a.get(5000)", a.get(5000), 2);
            check("a.get(7000)", a.get(7000), 2);

            for(int i = 0; i < 5000; i++) {
                check("a.remove(a.size() - 1)", a.remove(a.size() - 1), 2);
            }

            check("a.size()", a.size(), 5000);
            a.add(0, 3);
            check("a.get(0)", a.get(0), 3);
            check("a.get(1)", a.get(1), 1);
            check("a.get(a.size() - 1)", a.get(a.size() - 1), 1);
            // (no println here: the list has 5,001 elements)
        });


        Tester.exceptTestCase("add(PositiveIndex, value) to empty list", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1, 2);
        });

        Tester.exceptTestCase("add(invalidPositiveIndex, value)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(5);
            a.add(10);
            a.add(3, 2);
        });
        
        Tester.exceptTestCase("add(negativeIndex, value)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(-1, 2);
        });

        Tester.testCase("remove(middleIndex), then remove(lastIndex), then remove(0)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1);
            a.add(2);
            a.add(3);
            System.out.println(a);
            check("a.size()", a.size(), 3);
            check("a.remove(1)", a.remove(1), 2);
            System.out.println(a);
            check("a.size()", a.size(), 2);
            check("a.get(0)", a.get(0), 1);
            check("a.get(1)", a.get(1), 3);
            check("a.remove(1)", a.remove(1), 3);
            System.out.println(a);
            check("a.size()", a.size(), 1);
            check("a.get(0)", a.get(0), 1);
            check("a.remove(0)", a.remove(0), 1);
            check("a.size()", a.size(), 0);
            System.out.println(a);
        });
        
         Tester.testCase("remove(indexFromLastHalf)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            for (int i=0; i<10; i++)
                a.add(i);
            System.out.println(a);
            check("a.size()", a.size(), 10);
            check("a.get(0)", a.get(0), 0);
            check("a.get(9)", a.get(9), 9);
            
            check("a.remove(7)", a.remove(7), 7);
            System.out.println(a);
            check("a.size()", a.size(), 9);
            check("a.get(0)", a.get(0), 0);
            check("a.get(8)", a.get(8), 9);
            check("a.get(7)", a.get(7), 8);
            check("a.get(6)", a.get(6), 6);
            
            check("a.remove(7)", a.remove(7), 8);
            System.out.println(a);
            check("a.size()", a.size(), 8);
            check("a.get(0)", a.get(0), 0);
            check("a.get(7)", a.get(7), 9);
            check("a.get(6)", a.get(6), 6);
            check("a.get(5)", a.get(5), 5);
            
            
        });
        
        Tester.testCase("remove(indexFromFirstHalf)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            for (int i=0; i<10; i++)
                a.add(i);
            System.out.println(a);
            check("a.size()", a.size(), 10);
            check("a.get(0)", a.get(0), 0);
            check("a.get(9)", a.get(9), 9);
            check("a.remove(2)", a.remove(2), 2);
            System.out.println(a);
            check("a.size()", a.size(), 9);
            check("a.get(0)", a.get(0), 0);
            check("a.get(1)", a.get(1), 1);
            check("a.get(2)", a.get(2), 3);
            check("a.get(6)", a.get(6), 7);
            check("a.get(7)", a.get(7), 8);
            check("a.get(8)", a.get(8), 9);
            
            check("a.remove(1)", a.remove(1), 1);
            System.out.println(a);
            check("a.size()", a.size(), 8);
            check("a.get(0)", a.get(0), 0);
            check("a.get(1)", a.get(1), 3);
            check("a.get(2)", a.get(2), 4);
            check("a.get(6)", a.get(6), 8);
            check("a.get(7)", a.get(7), 9);
        });

        Tester.testCase("remove position 0 from list", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1);
            a.add(2);
            a.add(3);
            check("a.size()", a.size(), 3);
            check("a.remove(0)", a.remove(0), 1);
            check("a.size()", a.size(), 2);
            check("a.get(0)", a.get(0), 2);
            check("a.get(1)", a.get(1), 3);
            check("a.remove(0)", a.remove(0), 2);
            check("a.size()", a.size(), 1);
            check("a.get(0)", a.get(0), 3);
            check("a.remove(0)", a.remove(0), 3);
            check("a.size()", a.size(), 0);
            System.out.println(a);
        });
        
        Tester.exceptTestCase("remove() from empty list", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.remove(0);
            
        });
        Tester.exceptTestCase("remove(invalidPositiveIndex)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(5);
            a.add(6);
            a.remove(2);
            
        });
        Tester.exceptTestCase("remove(negativeIndex)", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(5);
            a.remove(-2);
        });   

        Tester.testCase("clear and isEmpty", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1);
            check("a.size()", a.size(), 1);
            a.clear();
            check("a.size()", a.size(), 0);
            check("a.isEmpty()", a.isEmpty(), true);
            a.add(3);
            check("a.size()", a.size(), 1);
            check("a.isEmpty()", a.isEmpty(), false);
            check("a.get(0)", a.get(0), 3);
            a.clear();
            check("a.size()", a.size(), 0);
        });

        Tester.testCase("indexOf and contains", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1);
            a.add(2);
            a.add(3);
            a.remove(1);

            if (a.indexOf(2) >= 0) throw new AssertionError("a.indexOf(2) was " + a.indexOf(2) + ", expected a negative number (2 was removed)");
            check("a.contains(2)", a.contains(2), false);
            check("a.contains(1)", a.contains(1), true);
            check("a.contains(3)", a.contains(3), true);
            check("a.indexOf(1)", a.indexOf(1), 0);
            check("a.indexOf(3)", a.indexOf(3), 1);
        });

        Tester.testCase("toString", () -> {
            IntegerArrayList a = new IntegerArrayList();
            check("empty list toString()", a.toString(), "[]");
            a.add(1);
            check("one-element toString()", a.toString(), "[1]");
            a.add(2);
            a.add(3);
            check("a.toString()", a.toString(), "[1, 2, 3]");
            a.remove(2);
            check("toString() after remove(2)", a.toString(), "[1, 2]");
            System.out.println(a);
        });

        Tester.testCase("contains", () -> {
            IntegerArrayList a = new IntegerArrayList();
            a.add(1);
            a.add(2);
            a.add(3);

            check("a.contains(0)", a.contains(0), false);
            check("a.contains(1)", a.contains(1), true);
            check("a.contains(2)", a.contains(2), true);
            check("a.contains(3)", a.contains(3), true);
        });

        Tester.testCase("equals two empty lists", () -> {
            IntegerArrayList a = new IntegerArrayList();
            IntegerArrayList b = new IntegerArrayList();

            check("a.equals(b)", a.equals(b), true);
        });
        
        Tester.testCase("equals unequal size", () -> {
            IntegerArrayList a = new IntegerArrayList();
            IntegerArrayList b = new IntegerArrayList();

            a.add(1);
            check("a.equals(b)", a.equals(b), false);
        });
        
        Tester.testCase("equals equal size", () -> {
            IntegerArrayList a = new IntegerArrayList();
            IntegerArrayList b = new IntegerArrayList();

            a.add(1);
            b.add(1);
            check("a.equals(b)", a.equals(b), true);
            b.add(2);
            a.add(2);
            check("a.equals(b)", a.equals(b), true);
            b.add(3);
            a.add(4);
            check("a.equals(b)", a.equals(b), false);
            
        });

        System.out.println();
        System.out.println(passed + " tests passed, " + failed + " tests failed");
    }
}