package app;

import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {

        SortedList<Integer> list = new SortedList<Integer>(new IntegerComparer());

        long startTime;
        long endTime;
        long durationMs;
        int itemCount = 5;
        boolean valid = false;

        //
        // first use the list to add 10,000 sorted (asceending) items - this should be
        // fast
        System.out.printf("Starting Adding %d items from smallest to largest using add() ...\n", itemCount);
        startTime = System.nanoTime();
        for (int i = 0; i < itemCount; i++) {
            list.add(i);
        }
        endTime = System.nanoTime();
        durationMs = (endTime - startTime) / 1000000;
        valid = TestList(list);
        DumpFirstItems(100, list, valid);
        System.out.printf("\nAdd %d items in normal way took took %d ms\n\n", itemCount, durationMs);

        // add 10,000 items from big to little to the front this should be fast
        System.out.println("Starting Adding from largest to smallest using add()...");
        list.Clear();
        startTime = System.nanoTime();
        for (int i = itemCount; i > 0; i--) {
            list.add(i);
        }
        endTime = System.nanoTime();
        durationMs = (endTime - startTime) / 1000000;
        valid = TestList(list);
        DumpFirstItems(100, list, valid);
        System.out.printf("\nreverse order took %d ms\n\n", durationMs);

        // add 10,000 random items to the list - this will be slow
        Random rand = new Random();
        System.out.println("add random numbers using .add() ...");
        list.Clear();
        startTime = System.nanoTime();
        for (int i = itemCount; i > 0; i--) {
            int toAdd = rand.nextInt(itemCount * 10);
            list.add(toAdd);

        }
        endTime = System.nanoTime();
        durationMs = (endTime - startTime) / 1000000;
        valid = TestList(list);
        DumpFirstItems(100, list, valid);
        // list.DumpList();
        System.out.printf("\nadd random numbers using .add() took %d ms\n\n", durationMs);

    }

    public static void DumpFirstItems(int count, SortedList<Integer> list, boolean valid) {
        System.out.printf("[Valid = %s] ", valid ? "True" : "False");
        for (int i = 0; i < count && i < list.Count(); i++) {
            System.out.printf("%d,", list.getItem(i));
        }
    }

    private static boolean TestList(SortedList<Integer> list) {
        int val = -Integer.MAX_VALUE;
        for (int i = 0; i < list.Count(); i++) {
            int itemVal = list.getItem(i);
            if (val > itemVal) {

                System.out.printf("the item at position %d has value %d which is > than %d ", i, itemVal, val);
                return false;
            }
            val = itemVal;
        }
        return true;
    }

}
