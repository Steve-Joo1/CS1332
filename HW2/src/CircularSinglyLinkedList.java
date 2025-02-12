import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Sehwan Joo
 * @version 1.0
 * @userid sjoo38
 * @GTID 903763255
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 * 
 * By typing 'I agree' below, you are agreeing that this is your
 * own work and that you are responsible for all the contents of 
 * this file. If this is left blank, this homework will receive a zero.
 * 
 * Agree Here: I agree
 * 
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index should be between 0 and " + size);
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            CircularSinglyLinkedListNode<T> current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }

            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data, current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        }

        CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data);

        if (size == 0) {
            newNode.setNext(newNode);
            head = newNode;
        } else {
            newNode.setNext(head.getNext());
            head.setNext(newNode);
            newNode.setData(head.getData());
            head.setData(data);
        }
        size++;
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        }

        CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data, head);

        if (size == 0) {
            newNode.setNext(newNode);
            head = newNode;
        } else {
            newNode.setNext(head.getNext());
            head.setNext(newNode);
            newNode.setData(head.getData());
            head.setData(data);
            head = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should be between 0 and " + (size - 1));
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            CircularSinglyLinkedListNode<T> current = head;

            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            T removedData = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            size--;
            return removedData;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove data from an empty list");
        }

        T removedData = head.getData();

        if (size == 1) {
            head = null;
        } else {
            T nextData = head.getNext().getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
        }
        size--;
        return removedData;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove data from an empty list");
        }
        T removedData;
        if (size == 1) {
            removedData = head.getData();
            head = null;
        } else {
            CircularSinglyLinkedListNode<T> current = head;
            while (current.getNext().getNext() != head) {
                current = current.getNext();
            }
            removedData = current.getNext().getData();
            current.setNext(head);
        }
        size--;
        return removedData;
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should be between 0 and " + (size - 1));
        }
        CircularSinglyLinkedListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from data structure");
        }
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove data from an empty list");
        }

        CircularSinglyLinkedListNode<T> current = head;
        CircularSinglyLinkedListNode<T> prev = null;
        CircularSinglyLinkedListNode<T> lastOccurrence = null;
        CircularSinglyLinkedListNode<T> prevLastOccurrence = null;

        do {
            if (current.getData().equals(data)) {
                lastOccurrence = current;
                prevLastOccurrence = prev;
            }
            prev = current;
            current = current.getNext();
        } while (current != head);

        if (lastOccurrence == null) {
            throw new NoSuchElementException("Cannot find data from the list");
        }

        if (lastOccurrence == head) {  // Special case: last occurrence is the head
            if (size == 1) {  // Only one element in the list
                head = null;
            } else {  // More than one element
                CircularSinglyLinkedListNode<T> tail = head;
                while (tail.getNext() != head) {
                    tail = tail.getNext();
                }
                head = head.getNext();  // Move head forward
                tail.setNext(head);     // Update tail to point to new head
            }
        } else {  // General case
            prevLastOccurrence.setNext(lastOccurrence.getNext());
        }

        size--;
        return lastOccurrence.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        if (size > 0) {
            CircularSinglyLinkedListNode<T> current = head;
            int i = 0;
            do {
                arr[i] = current.getData();
                current = current.getNext();
                i++;
            } while (current != head);
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
