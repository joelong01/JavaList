package app;

import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {

        SortedList<Integer> list = new SortedList<Integer>(new AscendingIntegerComparer());

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
        valid = TestList(list, true);
        DumpFirstItems(100, list, valid);
        System.out.printf("\nAdd %d items in normal way took took %d ms\n\n", itemCount, durationMs);

        // resort
        System.out.printf("Resorting %d items  ...\n", itemCount);
        startTime = System.nanoTime();
        list.resort(new DescendingIntegerComparer());
        valid = TestList(list, false);
        DumpFirstItems(100, list, valid);
        System.out.printf("\nResorting %d items took took %d ms\n\n", itemCount, durationMs);

        // add 10,000 items from big to little to the front this should be fast
        System.out.println("Starting Adding from largest to smallest using add()...");
        list.Clear();
        startTime = System.nanoTime();
        for (int i = itemCount; i > 0; i--) {
            list.add(i);
        }
        endTime = System.nanoTime();
        durationMs = (endTime - startTime) / 1000000;
        valid = TestList(list, false);
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
        valid = TestList(list, false);
        DumpFirstItems(100, list, valid);
        // list.DumpList();
        System.out.printf("\nadd random numbers using .add() took %d ms\n\n", durationMs);

        System.out.printf("converting %d items to an array ...\n", itemCount);
        startTime = System.nanoTime();
        var array = list.toArray();
        System.out.print("Array contents: ");
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%d,", array[i]);
        }
        System.out.printf("\ntoArray took %d ms\n\n", durationMs);
       
        

    }

    public static void DumpFirstItems(int count, SortedList<Integer> list, boolean valid) {
        System.out.printf("[Valid = %s] ", valid ? "True" : "False");
        for (int i = 0; i < count && i < list.Count(); i++) {
            System.out.printf("%d,", list.getItem(i));
        }
    }

    private static boolean TestList(SortedList<Integer> list, boolean ascending) {
        int val = (ascending) ? -Integer.MAX_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < list.Count(); i++) {
            int itemVal = list.getItem(i);
            if ((ascending && val > itemVal) || (!ascending && val < itemVal)) {

                System.out.printf("the item at position %d has value %d which is %s than %d ", i, itemVal,
                        ascending ? ">" : "<", itemVal);
                return false;
            }
            val = itemVal;
        }
        return true;
    }

}
