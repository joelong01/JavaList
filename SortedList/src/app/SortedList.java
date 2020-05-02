package app;

import java.util.*;

/**
 * This class provides a sorted (ascending) singly linked list. use it like
 * this: sortedList<int> intList = new sortedList<int)(); sortedList.add(3);
 * sortedList.add(1); sortedList.add(5); sortedList.dump();
 * 
 * ...which printes out "List: 1,3,5"
 * 
 * @param <T>
 */
public class SortedList<T> {
    /**
     * this is a private node class. makign it proviate means the caller of the
     * class never has to (or gets to) know how the list is maintained
     */
    private class Node {
        T Data = null; // the data that the node holds. can be any type
        private Node Next = null; // the reference to the next node

        private Node(final T t) {
            Data = t;
        }

    }

    /**
     * Data variables used to maintain the class
     */
    private Node Head = null;
    private Node Tail = null;
    // the comparator used for sorting
    // this returns < 0 for ascending list and >0 for descending list

    private Comparator<T> _comparator = null;
    private int Count = 0;

    /**
     * default contructor
     */
    public SortedList() {
    }

    /**
     * Constructor
     * 
     * @param comparator: the Comparator<T> to enforce sorting
     * 
     */

    public SortedList(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("comparator can't be null!");
        }
        this._comparator = comparator;
    }

    /**
     * 
     * inserts the object T into the linked list
     * 
     * @param t: the data for the node
     */
    public void add(final T item) {

        try {

            if (Count == 0) {
                Head = new Node(item);
                Tail = Head;
                
                return;

            }

            var newNode = new Node(item);
            Node currentNode = Head;
            Node nextNode = null;
            //
            // loop through all the elements in the list. You start with currentNode
            // and nextNode referencsing the Head. after the first loop, currentNode
            // stays referening the head, but
            for (nextNode = Head; nextNode != null; nextNode = nextNode.Next) {
                int compare = _comparator.compare(nextNode.Data, newNode.Data);
                if (compare <= 0) {
                    //
                    // the next node is bigger than the new node. put new node here
                    if (Head == currentNode) {
                        newNode.Next = currentNode;
                        Head = newNode;                        
                        break;
                    }

                    newNode.Next = nextNode;      
                    currentNode.Next = newNode;                                  
                
                    break;
                }
                currentNode = nextNode; // set current node so that it is one behind next node
            }
            //
            // this means it goes at the tail;
            if (nextNode == null) {
                Tail.Next = newNode; // old tail references new node
                Tail = newNode;      // tail is the new node
                

            }
        } catch (Exception e) {
            System.out.println("exception: " + e.toString());
            throw e;
        } finally{
            Count++;
           // DumpList(); Uncomment this is you want to dump the list each time you add somethign to the list
        }
    }

    public void DumpList() {

        if (this.Count == 0) {
            System.console().writer().println("List is empty\n");
            return;
        }
        System.out.printf("List has %d items\n", this.Count);
        
        System.out.printf("Head: %s Tail: %s\n", Head.Data.toString(), Tail.Data.toString());

        for (Node node = Head; node != null; node = node.Next) {

            System.console().writer().println(node.Data.toString());
        }
    }

}