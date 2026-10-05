public class TestList
{
    // To test your linked list instead, change every IntegerArrayList in main to
    // IntegerLinkedList (there are 4, on 2 lines).  Every expected value below
    // stays the same -- that is the point of the IntegerList interface.

    static int passed = 0;
    static int failed = 0;

    /** Prints the label, the actual value, and PASS or FAIL against what we expected. */
    static void check(String label, Object actual, Object expected)
    {
        String a = String.valueOf(actual);
        String e = String.valueOf(expected);
        if (a.equals(e))
        {
            System.out.println("PASS  " + label + ": " + a);
            passed++;
        }
        else
        {
            System.out.println("FAIL  " + label + ": got " + a + ", expected " + e);
            failed++;
        }
    }

    public static void main(String[] args)
    {
        IntegerArrayList myList = new IntegerArrayList();

        System.out.println("--- Empty list ---");
        try
        {
            check("Display empty list", myList, "[]");
            check("isEmpty()", myList.isEmpty(), true);
            check("size()", myList.size(), 0);
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- add, clear, add again ---");
        try
        {
            myList.add(5);
            check("Add 5 to end of empty list", myList, "[5]");
            check("isEmpty()", myList.isEmpty(), false);
            check("size()", myList.size(), 1);

            myList.clear();
            check("Display list after clear()", myList, "[]");
            check("isEmpty()", myList.isEmpty(), true);
            check("size()", myList.size(), 0);

            myList.add(0,5);
            check("Add 5 to position 0 of empty list", myList, "[5]");
            check("isEmpty()", myList.isEmpty(), false);
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- add at the end, the front, and the middle ---");
        try
        {
            myList.add(10);
            check("Add 10 to end of list", myList, "[5, 10]");

            myList.add(11);
            check("Add 11 to end of list", myList, "[5, 10, 11]");

            myList.add(1,7);
            check("Add 7 to position 1 of list", myList, "[5, 7, 10, 11]");

            myList.add(0,2);
            check("Add 2 to position 0 of list", myList, "[2, 5, 7, 10, 11]");

            myList.add(5,12);
            check("Add 12 to position 5 (the next unused -- \"last\" -- position)", myList, "[2, 5, 7, 10, 11, 12]");
            check("size()", myList.size(), 6);
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- get, contains, indexOf ---");
        try
        {
            check("get(0)", myList.get(0), 2);
            check("get(3)", myList.get(3), 10);
            check("get(5) (the last element)", myList.get(5), 12);
            check("contains(7)", myList.contains(7), true);
            check("contains(99)", myList.contains(99), false);
            check("indexOf(10)", myList.indexOf(10), 3);
            check("indexOf(2) (the first element)", myList.indexOf(2), 0);
            check("indexOf(99) (not there)", myList.indexOf(99), -1);
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- remove the first, the last, and a middle element ---");
        try
        {
            check("remove(0) returns the removed value", myList.remove(0), 2);
            check("List after remove(0)", myList, "[5, 7, 10, 11, 12]");

            check("remove(4) (the last element) returns", myList.remove(4), 12);
            check("List after remove(4)", myList, "[5, 7, 10, 11]");

            check("remove(2) returns", myList.remove(2), 10);
            check("List after remove(2)", myList, "[5, 7, 11]");
            check("size()", myList.size(), 3);
            check("isEmpty()", myList.isEmpty(), false);
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- set and get together ---");
        try
        {
            for (int i=0; i<myList.size(); i++)
            {
                myList.set(i,myList.get(i) * 2);
            }
            check("Multiply all values by 2 using set(i) and get(i)", myList, "[10, 14, 22]");
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- remove down to empty ---");
        try
        {
            myList.remove(0);
            myList.remove(0);
            check("remove(0) on a one-element list returns", myList.remove(0), 22);
            check("List after removing every element", myList, "[]");
            check("isEmpty()", myList.isEmpty(), true);
            myList.add(3);
            check("Add 3 to a list that was emptied by remove", myList, "[3]");
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- growth: add well past the starting capacity ---");
        try
        {
            // An array-backed list starts with a small array (ours holds 8) and must
            // copy into a bigger one when it fills.  A linked list never fills --
            // but this test must still pass for it too.
            myList.clear();
            String expected = "[";
            for (int i=1; i<=20; i++)
            {
                myList.add(i * 10);
                expected = expected + (i * 10) + (i < 20 ? ", " : "]");
            }
            check("Add 10, 20, ... 200 to an empty list", myList, expected);
            check("size()", myList.size(), 20);
            check("get(19) (the last element)", myList.get(19), 200);
            myList.add(0,5);
            check("add(0,5) to the 20-element list -- get(0)", myList.get(0), 5);
            check("...and the old first element moved to get(1)", myList.get(1), 10);
            check("size()", myList.size(), 21);

            // add(index, value) must grow the array too -- it is a separate method.
            // A NEW list: clear() keeps the big array, so myList would never need to grow.
            IntegerArrayList fresh = new IntegerArrayList();
            for (int i=0; i<20; i++)
            {
                fresh.add(0, i);     // always at the FRONT, so the list ends up backwards
            }
            check("New list, add(0, i) for i = 0..19 -- size()", fresh.size(), 20);
            check("get(0) is the last one added", fresh.get(0), 19);
            check("get(19) is the first one added", fresh.get(19), 0);
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- bad indexes must throw IndexOutOfBoundsException ---");
        try
        {
            myList.clear();
            myList.add(1);
            myList.add(2);
            myList.add(3);       // [1, 2, 3], size 3
            checkThrows("get(3) on a size-3 list (index == size)", () -> myList.get(3));
            checkThrows("get(-1)", () -> myList.get(-1));
            checkThrows("set(3, 9) on a size-3 list", () -> myList.set(3, 9));
            checkThrows("remove(3) on a size-3 list", () -> myList.remove(3));
            checkThrows("add(4, 9) on a size-3 list (index > size)", () -> myList.add(4, 9));
            check("List unchanged after all those bad calls", myList, "[1, 2, 3]");
            myList.clear();
            checkThrows("remove(0) on an empty list", () -> myList.remove(0));
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println("\n--- clear ---");
        try
        {
            myList.add(8);
            myList.clear();
            check("Display list after clear()", myList, "[]");
            check("isEmpty()", myList.isEmpty(), true);
            check("size()", myList.size(), 0);
        }
        catch (RuntimeException ex) { crashed(ex); }

        System.out.println();
        System.out.println(passed + " passed, " + failed + " failed");
    }

    /** A section crashed part-way: count it as a FAIL and carry on with the next section. */
    static void crashed(RuntimeException ex)
    {
        System.out.println("FAIL  this section CRASHED: " + ex);
        System.out.println("      (the rest of this section was skipped -- later sections still run)");
        failed++;
    }

    /** PASS if running the code throws IndexOutOfBoundsException; FAIL otherwise. */
    static void checkThrows(String label, Runnable code)
    {
        try
        {
            code.run();
            System.out.println("FAIL  " + label + ": no exception was thrown");
            failed++;
        }
        catch (IndexOutOfBoundsException ex)
        {
            System.out.println("PASS  " + label + ": threw IndexOutOfBoundsException");
            passed++;
        }
        catch (RuntimeException ex)
        {
            System.out.println("FAIL  " + label + ": threw " + ex.getClass().getSimpleName()
                    + ", expected IndexOutOfBoundsException");
            failed++;
        }
    }
}
