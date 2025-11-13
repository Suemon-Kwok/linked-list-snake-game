package Question_2;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*
Recursive LinkedList Implementation

This class builds and manages a linked list using only recursion - NO LOOPS!

All methods are implemented recursively to demonstrate the power of recursive thinking

Uses the Node<E> class you created as the building blocks

@param <E> the type of data stored (must implement Comparable for ordering operations)
 */
public class LinkedList<E extends Comparable<E>> {
    
    // Head reference - points to the first node in the linked list
    // If HeadNode is null, the list is empty
    Node<E> HeadNode;
    
    // Size counter - tracks the number of nodes in the list
    // Maintained to provide O(1) size operations
    int ListSize;
    
    /*
    Constructor - initializes an empty linked list
    
    Sets up initial state with no nodes and size 0
     */
    public LinkedList() {
        // Initialize the head pointer to null, indicating an empty list
        this.HeadNode = null;    // Start with empty list (no head node)
        
        // Initialize the size counter to zero since list starts empty
        this.ListSize = 0;       // Initial size is zero
    }
    
    /*
    Public Add Method - adds element to the end of the list
    
    This is the public interface that users call
    
    @param DataToAdd the data to add to the list
     */
    public void add(E DataToAdd) {
        // Create new node with the provided data using Node constructor
        Node<E> NewNode = new Node<>(DataToAdd);
        
        // Use recursive helper to add at the end, updating head reference
        HeadNode = addRecursive(HeadNode, NewNode);
        
        // Increment size counter to reflect addition of new node
        ListSize++;
    }
    
    /*
    Recursive Helper for add - adds node to end of list recursively
    
    Base Case: if current node is null, we've reached the end
    
    Recursive Case: move to next node and continue searching for end
    
    @param CurrentNode the current node in our traversal
    
    @param NodeToAdd the new node to add
    
    @return the current node (maintains links in the chain)
     */
    private Node<E> addRecursive(Node<E> CurrentNode, Node<E> NodeToAdd) {
        // Base Case: We've reached the end of the list (current is null)
        if (CurrentNode == null) {
            // Return the new node to be linked at this position
            return NodeToAdd;  // Return the new node to be linked
        }
        
        // Recursive Case: We're not at the end yet
        // Move to the next node and recursively add there
        CurrentNode.Next = addRecursive(CurrentNode.Next, NodeToAdd);
        
        // Return current node to maintain the chain structure
        return CurrentNode;
    }
    
    /*
    Public Add Head Method - adds element at the beginning of the list
    
    @param DataToAdd the data to add at the head
     */
    public void addHead(E DataToAdd) {
        // Create new node with provided data using Node constructor
        Node<E> NewNode = new Node<>(DataToAdd);
        
        // New node points to current head (could be null if list is empty)
        NewNode.Next = HeadNode;
        
        // New node becomes the new head of the list
        HeadNode = NewNode;
        
        // Increment size counter to reflect addition of new head node
        ListSize++;
    }
    
    /*
    Public Add In Order Method - adds element maintaining sorted order
    
    Uses ascending order: numerical for numbers, alphabetical for strings
    
    @param DataToAdd the data to add in correct position
     */
    public void addInOrder(E DataToAdd) {
        // Create new node with provided data using Node constructor
        Node<E> NewNode = new Node<>(DataToAdd);
        
        // Use recursive helper to find correct position and insert
        HeadNode = addInOrderRecursive(HeadNode, NewNode);
        
        // Increment size counter to reflect addition of new ordered node
        ListSize++;
    }
    
    /*
    Recursive Helper for Add In Order  - finds correct position and inserts
    
    Base Case: current is null (end of list) OR new data should come before current
    
    Recursive Case: current data comes before new data, so continue searching
    
    @param CurrentNode the current node in traversal
    
    @param NodeToInsert the node to insert
    
    @return the node that should be at this position
     */
    private Node<E> addInOrderRecursive(Node<E> CurrentNode, Node<E> NodeToInsert) {
        // Base Case 1: We've reached the end of the list
        // OR the new data should come before the current data (alphabetical/numerical order)
        if (CurrentNode == null || NodeToInsert.Data.compareTo(CurrentNode.Data) <= 0) {
            // Link new node to current (could be null if at end)
            NodeToInsert.Next = CurrentNode;  // Link new node to current (could be null)
            
            // New node takes this position in the ordered sequence
            return NodeToInsert;          // New node takes this position
        }
        
        // Recursive Case: Current data comes before new data in sort order
        // Continue searching in the rest of the list for correct insertion point
        CurrentNode.Next = addInOrderRecursive(CurrentNode.Next, NodeToInsert);
        
        // Return current node to maintain its position in the sequence
        return CurrentNode;
    }
    
    /*
    Public Contains Method - checks if list contains a specific node
    
    @param NodeToFind the node to search for
    
    @return true if found, false otherwise
     */
    public boolean contains(Node<E> NodeToFind) {
        // Use recursive helper starting from head to search entire list
        return containsRecursive(HeadNode, NodeToFind);
    }
    
    /*
    Recursive Helper for CONTAINS - searches for node recursively
    
    Base Case: current is null (not found) OR current equals target node
    
    Recursive Case: continue searching in rest of list
    
    @param CurrentNode the current node in traversal
    
    @param TargetNode the node we're looking for
    
    @return true if found, false if not found
     */
    private boolean containsRecursive(Node<E> CurrentNode, Node<E> TargetNode) {
        // Base Case 1: Reached end of list without finding target
        if (CurrentNode == null) {
            // Target not found in the entire list
            return false;
        }
        
        // Base Case 2: Found the target node using equals comparison
        if (CurrentNode.equals(TargetNode)) {
            // Target found at current position
            return true;
        }
        
        // Recursive Case: Continue searching in rest of list
        return containsRecursive(CurrentNode.Next, TargetNode);
    }
    
    /*
    Public Get Data Method - retrieves data at specific index
    
    @param IndexToGet the position to get data from (0-based)
    
    @return the data at specified index
    
    @throws IndexOutOfBoundsException if index is invalid
     */
    public E getData(int IndexToGet) {
        // Validate index bounds to prevent out-of-bounds access
        if (IndexToGet < 0 || IndexToGet >= ListSize) {
            // Throw exception with detailed error message
            throw new IndexOutOfBoundsException("Index: " + IndexToGet + ", Size: " + ListSize);
        }
        
        // Use recursive helper to find node at index
        Node<E> FoundNode = getNodeRecursive(HeadNode, IndexToGet);
        
        // Extract and return the data from the found node
        return FoundNode.Data;
    }
    
    /*
    Public Get Head Method - returns the first node
    
    @return the head node (could be null if list is empty)
     */
    public Node<E> getHead() {
        // Return reference to the first node in the list
        return HeadNode;
    }
    
    /*
    Public Get Node Method - retrieves node at specific index
    
    @param IndexToGet the position to get node from (0-based)
    
    @return the node at specified index
    
    @throws IndexOutOfBoundsException if index is invalid
     */
    public Node<E> getNode(int IndexToGet) {
        // Validate index bounds to prevent out-of-bounds access
        if (IndexToGet < 0 || IndexToGet >= ListSize) {
            // Throw exception with detailed error message
            throw new IndexOutOfBoundsException("Index: " + IndexToGet + ", Size: " + ListSize);
        }
        
        // Use recursive helper to find and return node at specified index
        return getNodeRecursive(HeadNode, IndexToGet);
    }
    
    /*
    Recursive Helper for GET NODE - finds node at specific index
    
    Base Case: index is 0 (we've reached target position)
    
    Recursive Case: decrement index and move to next node
    
    @param CurrentNode the current node in traversal
    
    @param RemainingIndex the remaining distance to target
    
    @return the node at target position
     */
    private Node<E> getNodeRecursive(Node<E> CurrentNode, int RemainingIndex) {
        // BASE CASE: We've reached the target index (countdown reached zero)
        if (RemainingIndex == 0) {
            // Return the node at the target position
            return CurrentNode;
        }
        
        // Recursive Case: Move closer to target
        // Decrement index and move to next node in the sequence
        return getNodeRecursive(CurrentNode.Next, RemainingIndex - 1);
    }
    
    /*
    Public Remove Method - removes node with matching data
    
    @param NodeToRemove the node whose data we want to remove
    
    @return true if removed, false if not found
     */
    public boolean remove(Node<E> NodeToRemove) {
        // Store original size to detect if removal occurred
        int OriginalSize = ListSize;
        
        // Use recursive helper to remove matching node
        HeadNode = removeNodeRecursive(HeadNode, NodeToRemove);
        
        // Return true if size decreased (removal occurred)
        return ListSize < OriginalSize;
    }
    
    /*
    Recursive Helper for Remove Node - removes first matching node
    
    Base Case: current is null (not found) OR current matches target
    
    Recursive Case: continue searching in rest of list
    
    @param CurrentNode the current node in traversal
    
    @param TargetNode the node to remove
    
    @return the node that should be at this position after removal
     */
    private Node<E> removeNodeRecursive(Node<E> CurrentNode, Node<E> TargetNode) {
        // Base Case 1: Reached end without finding target
        if (CurrentNode == null) {
            // Return null since no node exists at this position
            return null;
        }
        
        // Base Case 2: Found target node - remove it
        if (CurrentNode.equals(TargetNode)) {
            // Decrement size counter to reflect removal
            ListSize--;              // Decrement size counter
            
            // Return next node, effectively skipping/removing current node
            return CurrentNode.Next; // Return next node (skips current)
        }
        
        // Recursive Case: Continue searching in rest of list
        CurrentNode.Next = removeNodeRecursive(CurrentNode.Next, TargetNode);
        
        // Return current node to maintain its position
        return CurrentNode;
    }
    
    /*
    Public Remove By Index Method - removes node at specific index
    
    @param IndexToRemove the position of node to remove (0-based)
    
    @throws IndexOutOfBoundsException if index is invalid
     */
    public void remove(int IndexToRemove) {
        // Validate index bounds to prevent invalid removal attempts
        if (IndexToRemove < 0 || IndexToRemove >= ListSize) {
            // Throw exception with detailed error message
            throw new IndexOutOfBoundsException("Index: " + IndexToRemove + ", Size: " + ListSize);
        }
        
        // Use recursive helper to remove node at specified index
        HeadNode = removeAtIndexRecursive(HeadNode, IndexToRemove);
    }
    
    /*
    Recursive Helper for Remove By Index  - removes node at specific position
    
    Base Case: index is 0 (remove current node)
    
    Recursive Case: decrement index and continue to next node
    
    @param CurrentNode the current node in traversal
    
    @param RemainingIndex the remaining distance to target
    
    @return the node that should be at this position after removal
     */
    private Node<E> removeAtIndexRecursive(Node<E> CurrentNode, int RemainingIndex) {
        // This method assumes index is valid (checked by public method)
        
        // Base Case: We've reached the target index (countdown reached zero)
        if (RemainingIndex == 0) {
            // Decrement size counter to reflect removal
            ListSize--;              // Decrement size counter
            
            // Return next node, effectively skipping/removing current node
            return CurrentNode.Next; // Return next node (skips current)
        }
        
        // Recursive Case: Move closer to target
        CurrentNode.Next = removeAtIndexRecursive(CurrentNode.Next, RemainingIndex - 1);
        
        // Return current node to maintain its position
        return CurrentNode;
    }
    
    /*
    Public Remove From Head Method - removes first node
    
    @throws RuntimeException if list is empty
     */
    public void removeFromHead() {
        // Check if list is empty before attempting removal
        if (HeadNode == null) {
            // Throw exception since cannot remove from empty list
            throw new RuntimeException("Cannot remove from empty list");
        }
        
        // Move head to next node, effectively removing current head
        HeadNode = HeadNode.Next;
        
        // Decrement size counter to reflect removal of head node
        ListSize--;
    }
    
    /*
    PUBLIC REMOVE FROM TAIL METHOD - removes last node
    
    @throws RuntimeException if list is empty
     */
    public void removeFromTail() {
        // Check if list is empty before attempting removal
        if (HeadNode == null) {
            // Throw exception since cannot remove from empty list
            throw new RuntimeException("Cannot remove from empty list");
        }
        
        // Use recursive helper to remove tail node
        HeadNode = removeFromTailRecursive(HeadNode);
    }
    
    /*
    Recursive Helper for Remove From Tail - removes last node
    
    Base Case: current is last node (next is null)
    
    Recursive Case: continue to next node
    
    @param CurrentNode the current node in traversal
    
    @return the node that should be at this position after tail removal
     */
    private Node<E> removeFromTailRecursive(Node<E> CurrentNode) {
        // Base Case: This is the last node (no next node exists)
        if (CurrentNode.Next == null) {
            // Decrement size counter to reflect tail removal
            ListSize--;     // Decrement size counter
            
            // Remove this node by returning null (breaks the chain)
            return null; // Remove this node by returning null
        }
        
        // Recursive Case: Not the last node yet, continue traversal
        CurrentNode.Next = removeFromTailRecursive(CurrentNode.Next);
        
        // Return current node to maintain its position in the chain
        return CurrentNode;
    }
    
    /*
    Public Print Linked List Method - displays all elements
    
    Prints the entire list in a readable format
     */
    public void printLinkedList() {
        // Print header label for the list output
        System.out.print("LinkedList: ");
        
        // Use recursive helper to print all nodes starting from head
        printRecursive(HeadNode);
        
        // Print size information on the same line for complete summary
        System.out.println(" (Size: " + ListSize + ")");
    }
    
    /*
    Recursive Helper for print - prints all nodes from current onwards
    
    Base Case: current is null (end of list)
    
    Recursive Case: print current data and continue to next
    
    @param CurrentNode the current node in traversal
     */
    private void printRecursive(Node<E> CurrentNode) {
        // Base Case: Reached end of list, stop printing
        if (CurrentNode == null) {
            // Return without printing anything (end of recursion)
            return;
        }
        
        // Print current node's data using its toString method
        System.out.print(CurrentNode.Data);
        
        // Add arrow separator if there's a next node for visual formatting
        if (CurrentNode.Next != null) {
            // Print arrow to show connection to next node
            System.out.print(" -> ");
        }
        
        // Recursive Case: Continue printing rest of list
        printRecursive(CurrentNode.Next);
    }
    
    /*
    Public Get Size Method - returns number of elements
    
    @return the size of the list
     */
    public int getSize() {
        // Return the maintained size counter (O(1) operation)
        return ListSize;
    }
    
    /*
    Public Is Empty Method - checks if list is empty
    
    @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        // Check if size is zero, indicating empty list
        return ListSize == 0;
    }
}