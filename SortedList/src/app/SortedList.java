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
     * returns the Data of the 'index' element.
     * 
     * lots of opportunity to optimize this
     * 
     */
    public T getItem(int index) {

        if (Head == null)
            throw new IllegalArgumentException("The list has no items");

        Node iterator = Head;
        for (int i = 0; i < index; i++) {
            iterator = iterator.Next;
        }
        return iterator.Data;
    }

    /*
     * Removes all items from the list
     */
    public void Clear() {
        Head = null;
        Tail = null;
        Count = 0;

    }

    public int Count() {
        return this.Count;
    }

    /**
     * 
     * inserts the object T into the linked list in sorted order there are 4
     * conditions to worry about 1. is the list empty? 2. does the item go at the
     * Head? 3. does the item go at the Tail? 4. if none of the above, find the item
     * the new item goes *after*
     * 
     * @param t: the data for the node
     * @throws Exception
     */
    public void add(final T item) throws Exception {
        // System.out.printf("[Adding %s]", item.toString());
        // DumpList();

        try {

            var newNode = new Node(item);

            //
            // is the list empty?
            if (Count == 0) {
                Head = newNode;
                Tail = Head;
                return;
            }

            //
            // does it go at the front?
            if (_comparator.compare(Head.Data, item) <= 0) {
                newNode.Next = Head;
                Head = newNode;
                return;
            }

            //
            // does it go at the end?

            if (_comparator.compare(Tail.Data, item) >= 0) {
                Tail.Next = newNode;
                Tail = newNode;
                return;
            }

            Node currentNode = Head;
            //
            // loop through all the elements in the list. You start with currentNode
            // and nextNode referencsing the Head. after the first loop, currentNode
            // stays referening the head, but
            for (Node nextNode = Head.Next; nextNode != null; nextNode = nextNode.Next) {
                boolean insertNext = isNextNode(nextNode, newNode.Data);
                if (insertNext) {
                    //
                    // we know it goes next. put it there.
                    newNode.Next = nextNode;
                    currentNode.Next = newNode;
                    return;
                }

                currentNode = nextNode; // set current node so that it is one behind next node
            }
            //
            // we didn't find a place to insert it!
            throw new Exception(
                    "we hould not have gotten to the tail as we checked earler if the new node shoudl be the Tail!");
        } catch (Exception e) {
            System.out.println("exception: " + e.toString());
            throw e;
        } finally {
            Count++;
            DumpList(); // Uncomment this is you want to dump the list each time you add
            
        }
    }

    private boolean isNextNode(Node node, T data) {
       
        int compare = _comparator.compare(node.Data, data);
        if (compare <= 0)
            return true;
        return false;
    }

    

    /**
     * Remove the first occurence of targetItem from the list, shifting everything
     * after it up one position. targetItem is considered to be in the list if an
     * item that is equal to it (using .equals) is in the list. (This convention for
     * something being in the list should be followed throughout.)
     * 
     * @return true if the item was in the list, false otherwise
     */
    public boolean remove(T targetItem) {
        //
        // if the list is empty, can't remove anything
        try {

            if (Count == 0)
                return false;

            Node currentNode = Head;
            Node nextNode = null;
            System.console().writer().printf("Before Removing %s\n", targetItem.toString());
            // DumpList();
            //
            // loop through all the elements in the list. You start with currentNode
            // and nextNode referencsing the Head. after the first loop, currentNode
            // stays referening the head, but
            for (nextNode = Head; nextNode != null; nextNode = nextNode.Next) {
                int compare = _comparator.compare(nextNode.Data, targetItem);
                if (compare == 0) {
                    if (nextNode == Head) {
                        Head = currentNode.Next;
                        currentNode = null;

                    } else {

                        currentNode.Next = nextNode.Next;
                        if (currentNode.Next == null) {
                            Tail = currentNode;

                        }
                    }

                    Count--;
                    return true;

                }
                currentNode = nextNode;
            }

            assert (nextNode == null);
            return false;

        } catch (Exception e) {
            System.out.println("exception: " + e.toString());
            throw e;
        } finally {
            System.console().writer().printf("After Removing: %s\n", targetItem.toString());
            // DumpList();
        }

    }

    public void DumpList() {

        if (this.Count == 0) {
            System.console().writer().println("List is empty\n");
            return;
        }
        System.out.printf("[Count=%d][Head=%s][Tail=%s] Items: ", this.Count, Head.Data.toString(),
                Tail.Data.toString());

        for (Node node = Head; node != null; node = node.Next) {

            System.console().writer().printf("%s,", node.Data.toString());
        }
        System.out.println("");

    }

}