import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Cannot start the BST with null data.");
        }

        size = 0;
        for (T temp : data) {
            add(temp);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data into BST.");
        }

        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else {
            addHelper(data, root);
        }
    }

    /**
     * Recursive helper method for add method.
     * @param data the data of the node to be added
     * @param curr the current node
     */
    private void addHelper(T data, BSTNode<T> curr) {
        if (data.compareTo(curr.getData()) < 0) {
            if (curr.getLeft() == null) {
                curr.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addHelper(data, curr.getLeft());
            }
        } else if (data.compareTo(curr.getData()) > 0) {
            if (curr.getRight() == null) {
                curr.setRight(new BSTNode<>(data));
                size++;
            } else {
                addHelper(data, curr.getRight());
            }
        } else {
            return;
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from BST.");
        }

        BSTNode<T> removedNode = new BSTNode<>(null);
        root = removeHelper(data, root, removedNode);
        size--;
        return removedNode.getData();
    }

    /**
     * Recursive helper method for remove method.
     * @param data the data of the node to be removed
     * @param curr the current node
     * @param removedNode the node to be removed
     * @return the removed node after the traversal
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> curr, BSTNode<T> removedNode) {
        if (curr == null) {
            throw new NoSuchElementException("There is no data in the BST.");
        } else {
            if (data.compareTo(curr.getData()) < 0) {
                curr.setLeft(removeHelper(data, curr.getLeft(), removedNode));
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(removeHelper(data, curr.getRight(), removedNode));
            } else {
                removedNode.setData(curr.getData());

                if (curr.getLeft() == null && curr.getRight() == null) {
                    return null;
                } else if (curr.getLeft() == null) {
                    return curr.getRight();
                } else if (curr.getRight() == null) {
                    return curr.getLeft();
                } else {
                    BSTNode<T> child = new BSTNode<>(null);
                    curr.setRight(successorHelper(curr.getRight(), child));
                    curr.setData(child.getData());
                }
            }
        }
        return curr;
    }

    /**
     * Recursive successor helper method for removeHelper method.
     * @param curr the current node during the traversal
     * @param child the child node of the current node
     * @return successor node
     */
    private BSTNode<T> successorHelper(BSTNode<T> curr, BSTNode<T> child) {
        if (curr.getLeft() == null) {
            child.setData(curr.getData());
            return curr.getRight();
        }
        curr.setLeft(successorHelper(curr.getLeft(), child));
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data from BST.");
        }

        return getHelper(data, root);
    }

    /**
     * Recursive helper method for get method.
     * @param data the data to search for
     * @param curr the current node during the traversal
     * @return the data of the current node
     * java.util.NoSuchElementException   if the data is not in the tree
     */
    private T getHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("There is no data in the BST.");
        }

        if (data.compareTo(curr.getData()) < 0) {
            return getHelper(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(data, curr.getRight());
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search null data from BST.");
        }

        return containsHelper(data, root);
    }

    /**
     * Recursive helper method for contain method.
     * @param data the data to search for
     * @param curr the current node during the traversal
     * @return the result whether the data is in the BST
     */
    private boolean containsHelper(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        }

        if (data.compareTo(curr.getData()) < 0) {
            return containsHelper(data, curr.getLeft());
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(data, curr.getRight());
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderList = new ArrayList<>();
        preorderHelper(preorderList, root);
        return preorderList;
    }

    /**
     * Recursive helper method for pre-order method.
     * @param preorderList the list that saves data to BST in pre-order
     * @param curr the current node during the traversal
     */
    private void preorderHelper(List<T> preorderList, BSTNode<T> curr) {
        if (curr == null) {
            return;
        } else {
            preorderList.add(curr.getData());
            preorderHelper(preorderList, curr.getLeft());
            preorderHelper(preorderList, curr.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderList = new ArrayList<>();
        inorderHelper(inorderList, root);
        return inorderList;
    }

    /**
     * Recursive helper method for in-order method.
     * @param inorderList the list that saves data to BST in in-order
     * @param curr the current node during the traversal
     */
    private void inorderHelper(List<T> inorderList, BSTNode<T> curr) {
        if (curr == null) {
            return;
        } else {
            inorderHelper(inorderList, curr.getLeft());
            inorderList.add(curr.getData());
            inorderHelper(inorderList, curr.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderList = new ArrayList<>();
        postorderHelper(postorderList, root);

        return postorderList;
    }

    /**
     * Recursive helper method for post-order method.
     * @param postorderList the list that saves data to BST in post-order
     * @param curr the current node during the traversal
     */
    private void postorderHelper(List<T> postorderList, BSTNode<T> curr) {
        if (curr == null) {
            return;
        } else {
            postorderHelper(postorderList, curr.getLeft());
            postorderHelper(postorderList, curr.getRight());
            postorderList.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> levelOrderQueue = new LinkedList<>();
        List<T> levelOrderList = new ArrayList<>();

        if (root != null) {
            levelOrderQueue.add(root);
        }

        while (!levelOrderQueue.isEmpty()) {
            BSTNode<T> temp = new BSTNode<>(null);
            temp = levelOrderQueue.remove();
            levelOrderList.add(temp.getData());

            if (temp.getLeft() != null) {
                levelOrderQueue.add(temp.getLeft());
            }

            if (temp.getRight() != null) {
                levelOrderQueue.add(temp.getRight());
            }
        }

        return levelOrderList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Recursive helper method for height method.
     * @param curr the current node during the traversal
     * @return the height of the BST
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int leftHeight = heightHelper(curr.getLeft());
            int rightHeight = heightHelper(curr.getRight());

            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("The value of k should be between 0 and " + size);
        }

        List<T> kLargestList = new LinkedList<>();

        kLargestHelper(k, root, kLargestList);

        return kLargestList;
    }

    /**
     * Recursive helper method for kLargest method.
     * @param k the number of largest elements to return
     * @param curr the current node during the traversal
     * @param kLargestList the sorted list consisting of the k largest elements
     */
    private void kLargestHelper(int k, BSTNode<T> curr, List<T> kLargestList) {
        if (curr == null) {
            return;
        } else {
            System.out.println(curr.getData());

            if (kLargestList.size() < k) {
                kLargestHelper(k, curr.getRight(), kLargestList);
            }

            if (kLargestList.size() < k) {
                kLargestList.add(0, curr.getData());
            }

            if (kLargestList.size() < k) {
                kLargestHelper(k, curr.getLeft(), kLargestList);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

