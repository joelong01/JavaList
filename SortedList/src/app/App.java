package app;


public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        
        SortedList<Integer> list = new SortedList<Integer>(new IntegerComparer());
        list.add(3);
        list.add(8);
        list.add(1);
        list.add(9);
        list.add(7);
        list.add(11);
        list.DumpList();
    }

   
}

