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
     */
    public void add(final T item) {
        // System.out.printf("[Adding %s]", item.toString());
        // DumpList();

        var newNode = new Node(item);
        addNode(newNode);

    }

    private void addNode(Node newNode) {
        try {
            //
            // is the list empty?
            if (Count == 0) {
                Head = newNode;
                Tail = Head;
                return;
            }

            //
            // does it go at the front?
            if (_comparator.compare(Head.Data, newNode.Data) <= 0) {
                newNode.Next = Head;
                Head = newNode;
                return;
            }

            //
            // does it go at the end?

            if (_comparator.compare(Tail.Data, newNode.Data) >= 0) {
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

        } catch (Exception e) {
            System.out.println("exception: " + e.toString());
        } finally {
            Count++;
            // DumpList(); // Uncomment this is you want to dump the list each time you add

        }
    }

    private boolean isNextNode(Node node, T data) {

        int compare = _comparator.compare(node.Data, data);
        if (compare <= 0)
            return true;
        return false;
    }

    /**
     * Returns the first position of targetItem in the list.
     * 
     * @return the position of the item, or -1 if targetItem is not in the list
     */
    public int getPosition(T targetItem) {
        int pos = 0;

        for (Node nextNode = Head; nextNode != null; nextNode = nextNode.Next) {
            int compare = _comparator.compare(nextNode.Data, targetItem);
            if (compare == 0) {
                return pos;
            }

            pos++;
        }
        return -1;
    }

    /**
     * Returns the item at a given index.
     * 
     * @return the item, or throw an IndexOutOfBoundsException if the index is out
     *         of bounds.
     */
    public T get(int position) {
        if (position >= Count || position < 0)
            throw new IndexOutOfBoundsException();
        Node node = Head;
        for (int i = 0; i < position; i++) {
            node = node.Next;
        }
        return node.Data;

    }

    /** Returns true if the list contains the target item. */
    public boolean contains(T targetItem){
        if (Count == 0) return false;

        for (Node node = Head; node != null; node = node.Next) {
             if (_comparator.compare(targetItem, node.Data) == 0){
                return true;
             }
        }
        return false;
    }

    /**
     * 
     * Re-sorts the list ac ording to the given comparator. All future insertions
     * should add in the order specified by this comparator.
     */
    public void resort(Comparator<T> comparator) {

        if (Count == 0 || Count == 1)
            return;

        //
        // setting Count to 0 makes the AddNode() reset Head and Tail
        Count = 0;

        _comparator = comparator;
        Node currentNode = Head;
        Node nextNode = Head.Next;
        while (currentNode != null) {

            currentNode.Next = null; // we have to set Next to null so that addNode terminates the loop correctly
            addNode(currentNode);
            currentNode = nextNode;
            if (currentNode != null)
                nextNode = nextNode.Next; // it'd be nice to get rid of the if statement somehow via more clever looping
        }

    }

    /** Returns the length of the list: the number of items stored in it. */
    public int size() {
        return Count;
    }

    /** Returns true if the list has no items stored in it. */
    public boolean isEmpty() {
        return Count == 0;
    }

    /**
     * Returns an array version of the list. Note that, for technical reasons, the
     * type of the items contained in the list can't be communicated properly to the
     * caller, so an array of Objects gets returned.
     * 
     * @return an array of length length(), with the same items in it as are stored
     *         in the list, in the same order.
     */
    public Object[] toArray() {
        var objArray = new Object[Count];
        Node node = Head;
        for (int i = 0; i < Count; i++) {
            objArray[i] = node.Data;
            node = node.Next;
        }
        return objArray;

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