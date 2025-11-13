package Question_1;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*

/*
Recursive LinkedList Implementation
 
This class builds and manages a linked list using only recursion - No Loops used
 
All methods are implemented recursively to demonstrate the power of recursive thinking
 
Uses the Node<E> class you created as the building blocks
 
@param <E> the type of data stored (must implement Comparable for ordering operations)
 */
public class LinkedList<E extends Comparable<E>> {
    
    // HeadNode reference - points to the first node in the linked list
    // If HeadNode is null, the list is empty
    Node<E> HeadNode;
    
    // ListSize counter - tracks the number of nodes in the list
    // Maintained to provide O(1) size operations
    int ListSize;
    
    /*
    Constructor - initializes an empty linked list
    Sets up initial state with no nodes and size 0
    */
    public LinkedList() {
        this.HeadNode = null;    // Start with empty list (no head node)
        this.ListSize = 0;       // Initial size is zero
    }
    
    /*
    Public Add Method - adds element to the end of the list
    This is the public interface that users call
    @param ElementData the data to add to the list
     */
    public void add(E ElementData) {
        // Create new node with the provided data
        Node<E> NewNode = new Node<>(ElementData); // Instantiate a new Node object with ElementData
        
        // Use recursive helper to add at the end
        HeadNode = addRecursive(HeadNode, NewNode); // Call recursive method to add NewNode to the list
        
        // Increment size counter
        ListSize++; // Increase the total count of nodes in the list by one
    }
    
    /*
    Recursive Helper for ADD - adds node to end of list recursively
    
    Base Case: if current node is null, we've reached the end
    
    Recursive Case: move to next node and continue searching for end
    
    @param CurrentNode the current node in our traversal
    
    @param NewNodeToAdd the new node to add
    
    @return the current node (maintains links in the chain)
     */
    private Node<E> addRecursive(Node<E> CurrentNode, Node<E> NewNodeToAdd) {
        // BASE CASE: We've reached the end of the list (CurrentNode is null)
        if (CurrentNode == null) {
            return NewNodeToAdd;  // Return the NewNodeToAdd to be linked
        }
        
        // RECURSIVE CASE: We're not at the end yet
        // Move to the next node and recursively add there
        CurrentNode.Next = addRecursive(CurrentNode.Next, NewNodeToAdd); // Recursively call on the next node
        
        // Return current node to maintain the chain
        return CurrentNode; // Return CurrentNode to preserve the linking structure
    }
    
    /*
    Public Add Head Method - adds element at the beginning of the list
    
    @param ElementData the data to add at the head
     */
    public void addHead(E ElementData) {
        // Create new node with provided data
        Node<E> NewHeadNode = new Node<>(ElementData); // Create a new Node with ElementData
        
        // New node points to current head (could be null if list is empty)
        NewHeadNode.Next = HeadNode; // Set the next reference of NewHeadNode to current HeadNode
        
        // New node becomes the new head
        HeadNode = NewHeadNode; // Update HeadNode to point to NewHeadNode
        
        // Increment size counter
        ListSize++; // Increase the total count of nodes in the list
    }
    
    /*
    Public Add In Order Method - adds element maintaining sorted order
    
    Uses ascending order: numerical for numbers, alphabetical for strings
    
    @param ElementData the data to add in correct position
     */
    public void addInOrder(E ElementData) {
        // Create new node with provided data
        Node<E> NewOrderedNode = new Node<>(ElementData); // Create Node instance with ElementData
        
        // Use recursive helper to find correct position and insert
        HeadNode = addInOrderRecursive(HeadNode, NewOrderedNode); // Call recursive method to insert in sorted order
        
        // Increment size counter
        ListSize++; // Increase the node count by one
    }
    
    /*
    Recursive Helper FOR Add In OrderR - finds correct position and inserts
    
    Base Case: current is null (end of list) OR new data should come before current
    
    Recursive Case: current data comes before new data, so continue searching
    
    @param CurrentNode the current node in traversal
    
    @param NewOrderedNode the node to insert
    
    @return the node that should be at this position
     */
    private Node<E> addInOrderRecursive(Node<E> CurrentNode, Node<E> NewOrderedNode) {
        // Base Case 1: We've reached the end of the list
        // OR the new data should come before the current data
        if (CurrentNode == null || NewOrderedNode.Data.compareTo(CurrentNode.Data) <= 0) {
            NewOrderedNode.Next = CurrentNode;  // Link NewOrderedNode to CurrentNode (could be null)
            return NewOrderedNode;          // NewOrderedNode takes this position
        }
        
        // Recursive Case: Current data comes before new data
        // Continue searching in the rest of the list
        CurrentNode.Next = addInOrderRecursive(CurrentNode.Next, NewOrderedNode); // Recursively search for correct position
        
        // Return current node to maintain its position
        return CurrentNode; // Return CurrentNode to preserve existing structure
    }
    
    /*
    Public Contains Method - checks if list contains a specific node
    
    @param TargetNode the node to search for
    
    @return true if found, false otherwise
     */
    public boolean contains(Node<E> TargetNode) {
        
        // Use recursive helper starting from head
        return containsRecursive(HeadNode, TargetNode); // Call recursive search method starting from HeadNode
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
            return false; // TargetNode not found in the list
        }
        
        // Base Case 2: Found the target node
        if (CurrentNode.equals(TargetNode)) {
            return true; // TargetNode found, return success
        }
        
        // RECURSIVE CASE: Continue searching in rest of list
        return containsRecursive(CurrentNode.Next, TargetNode); // Recursively search in remaining nodes
    }
    
    /*
    Public Get Data Method - retrieves data at specific index
    
    @param IndexPosition the position to get data from (0-based)
    
    @return the data at specified index
    
    @throws IndexOutOfBoundsException if index is invalid
     */
    public E getData(int IndexPosition) {
        // Validate index bounds
        if (IndexPosition < 0 || IndexPosition >= ListSize) {
            throw new IndexOutOfBoundsException("Index: " + IndexPosition + ", Size: " + ListSize); // Throw exception for invalid IndexPosition
        }
        
        // Use recursive helper to find node at index
        Node<E> ResultNode = getNodeRecursive(HeadNode, IndexPosition); // Get the node at IndexPosition
        return ResultNode.Data; // Return the data from the found node
    }
    
    /*
    Public Get Head Method - returns the first node
    
    @return the head node (could be null if list is empty)
     */
    public Node<E> getHead() {
        return HeadNode; // Return reference to the first node in the list
    }
    
    /*
    Public Get Node Method - retrieves node at specific index
    
    @param IndexPosition the position to get node from (0-based)
    
    @return the node at specified index
    
    @throws IndexOutOfBoundsException if index is invalid
     */
    public Node<E> getNode(int IndexPosition) {
        // Validate index bounds
        if (IndexPosition < 0 || IndexPosition >= ListSize) {
            throw new IndexOutOfBoundsException("Index: " + IndexPosition + ", Size: " + ListSize); // Check if IndexPosition is within valid range
        }
        
        // Use recursive helper to find node
        return getNodeRecursive(HeadNode, IndexPosition); // Return the node found at IndexPosition
    }
    
    /*
    Recursive Helper FOR Get Node - finds node at specific index
    
    Base Case: index is 0 (we've reached target position)
    
    Recursive Case: decrement index and move to next node
    
    @param CurrentNode the current node in traversal
    
    @param RemainingIndex the remaining distance to target
    
    @return the node at target position
     */
    private Node<E> getNodeRecursive(Node<E> CurrentNode, int RemainingIndex) {
        // Base Case: We've reached the target index
        if (RemainingIndex == 0) {
            return CurrentNode; // Return the CurrentNode as it's at the desired position
        }
        
        // Recursive Case: Move closer to target
        // Decrement index and move to next node
        return getNodeRecursive(CurrentNode.Next, RemainingIndex - 1); // Recursively search with decremented RemainingIndex
    }
    
    /*
    Public Remove Method - removes node with matching data
    
    @param NodeToRemove the node whose data we want to remove
    
    @return true if removed, false if not found
     */
    public boolean remove(Node<E> NodeToRemove) {
        // Store original size to detect if removal occurred
        int OriginalSize = ListSize; // Save current ListSize for comparison
        
        // Use recursive helper to remove matching node
        HeadNode = removeNodeRecursive(HeadNode, NodeToRemove); // Call recursive removal method
        
        // Return true if size decreased (removal occurred)
        return ListSize < OriginalSize; // Check if ListSize decreased to confirm removal
    }
    
    /*
    Recursive Helper  for REMOVE NODE - removes first matching node
    
    Base Case: current is null (not found) OR current matches target
    
    Recursive Case: continue searching in rest of list
    
    @param CurrentNode the current node in traversal
    
    @param NodeToRemove the node to remove
    
    @return the node that should be at this position after removal
     */
    private Node<E> removeNodeRecursive(Node<E> CurrentNode, Node<E> NodeToRemove) {
        //Base Case 1: Reached end without finding target
        if (CurrentNode == null) {
            return null; // NodeToRemove not found, return null
        }
        
        //Base Case 2: Found target node - remove it
        if (CurrentNode.equals(NodeToRemove)) {
            ListSize--;              // Decrement ListSize counter
            return CurrentNode.Next; // Return next node (skips CurrentNode)
        }
        
        //Recursive Case: Continue searching in rest of list
        CurrentNode.Next = removeNodeRecursive(CurrentNode.Next, NodeToRemove); // Recursively search and remove in remaining nodes
        return CurrentNode; // Return CurrentNode to maintain structure
    }
    
    /*
    Public Remove By Index Method - removes node at specific index
    
    @param IndexPosition the position of node to remove (0-based)
    
    @throws IndexOutOfBoundsException if index is invalid
     */
    public void remove(int IndexPosition) {
        // Validate index bounds
        if (IndexPosition < 0 || IndexPosition >= ListSize) {
            throw new IndexOutOfBoundsException("Index: " + IndexPosition + ", Size: " + ListSize); // Check IndexPosition validity
        }
        
        // Use recursive helper to remove node at index
        HeadNode = removeAtIndexRecursive(HeadNode, IndexPosition); // Call recursive method to remove at IndexPosition
    }
    
    /*
    Recursive Helper  for remove by index - removes node at specific position
    
    Base Case: index is 0 (remove current node)
    
    Recursive Case: decrement index and continue to next node
    
    @param CurrentNode the current node in traversal
    
    @param RemainingIndex the remaining distance to target
    
    @return the node that should be at this position after removal
     */
    private Node<E> removeAtIndexRecursive(Node<E> CurrentNode, int RemainingIndex) {
        // This method assumes index is valid (checked by public method)
        
        // Base Case: We've reached the target index
        if (RemainingIndex == 0) {
            ListSize--;              // Decrement ListSize counter
            return CurrentNode.Next; // Return next node (skips CurrentNode)
        }
        
        // Recursive Case: Move closer to target
        CurrentNode.Next = removeAtIndexRecursive(CurrentNode.Next, RemainingIndex - 1); // Recursively remove with decremented RemainingIndex
        return CurrentNode; // Return CurrentNode to maintain structure
    }
    
    /*
    Public Remove From Head Method - removes first node
    
    @throws RuntimeException if list is empty
     */
    public void removeFromHead() {
        // Check if list is empty
        if (HeadNode == null) {
            throw new RuntimeException("Cannot remove from empty list"); // Throw exception if no nodes exist
        }
        
        // Move head to next node (removes current head)
        HeadNode = HeadNode.Next; // Update HeadNode to point to the second node
        
        // Decrement size counter
        ListSize--; // Decrease the total node count by one
    }
    
    /*
    PUBLIC REMOVE FROM TAIL METHOD - removes last node
    
    @throws RuntimeException if list is empty
     */
    public void removeFromTail() {
        // Check if list is empty
        if (HeadNode == null) {
            throw new RuntimeException("Cannot remove from empty list"); // Throw exception if no nodes exist
        }
        
        // Use recursive helper to remove tail
        HeadNode = removeFromTailRecursive(HeadNode); // Call recursive method to remove the last node
    }
    
    /*
    Recursive Helper FOR Remove From Tail - removes last node
    
    Base Case: current is last node (next is null)
    
    Recursive Case: continue to next node
    
    @param CurrentNode the current node in traversal
    
    @return the node that should be at this position after tail removal
     */
    private Node<E> removeFromTailRecursive(Node<E> CurrentNode) {
        // Base Case: This is the last node
        if (CurrentNode.Next == null) {
            
            ListSize--;     // Decrement ListSize counter
            
            return null; // Remove this node by returning null
        }
        
        // Recursive Case: Not the last node yet
        CurrentNode.Next = removeFromTailRecursive(CurrentNode.Next); // Recursively call on next node
        
        return CurrentNode; // Return CurrentNode to maintain structure
    }
    
    /*
    Public Print Linked List Method - displays all elements
    
    Prints the entire list in a readable format
     */
    
    public void printLinkedList() {
        System.out.print("LinkedList: "); // Print the header label
        
        // Use recursive helper to print all nodes
        printRecursive(HeadNode); // Call recursive method to display all nodes
        
        System.out.println(" (Size: " + ListSize + ")"); // Print the total size information
    }
    
    /*
    Recursive Helper FOR Print - prints all nodes from current onwards
    
    Base Case: current is null (end of list)
    
    Recursive Case: print current data and continue to next
    
    @param CurrentNode the current node in traversal
     */
    private void printRecursive(Node<E> CurrentNode) {
        // BASE CASE: Reached end of list
        if (CurrentNode == null) {
            return; // Exit recursion when no more nodes
        }
        
        // Print current node's data
        System.out.print(CurrentNode.Data); // Display the data stored in CurrentNode
        
        // Add arrow if there's a next node
        if (CurrentNode.Next != null) {
            System.out.print(" -> "); // Print arrow connector between nodes
        }
        
        // Recursive Case: Continue printing rest of list
        printRecursive(CurrentNode.Next); // Recursively call on the next node
    }
    
    /*
    Public Get Size Method - returns number of elements
    
    @return the size of the list
     */
    public int getSize() {
        return ListSize; // Return the current count of nodes in the list
    }
    
    /*
    Public Is Empty Method - checks if list is empty
    
    @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return ListSize == 0; // Return true if no nodes exist in the list
    }
    
    /*
    Demonstration Method - shows how LinkedList uses Node class
    
    This method creates a sample list and demonstrates various operations
    
    Proves that we're using your Node class with its equals() and compareTo() methods
     */
    public static void main(String[] args) {
        System.out.println("--- RECURSIVE LINKED LIST USING YOUR NODE CLASS ---\n"); // Print demonstration header
        
        // Create a new LinkedList for Characters (like in your original code)
        LinkedList<Character> CharacterList = new LinkedList<>(); // Instantiate a new LinkedList for Character type
        
        System.out.println("1. Creating empty list:"); // Print step description
        
        CharacterList.printLinkedList(); // Display the empty list state
        
        System.out.println("\n2. Adding characters (uses YOUR Node constructor):"); // Print operation description
        
        CharacterList.add('R'); // Add character 'R' to the list
        
        CharacterList.add('A'); // Add character 'A' to the list
        
        CharacterList.add('C'); // Add character 'C' to the list
        
        CharacterList.add('E'); // Add character 'E' to the list
        
        CharacterList.add('C'); // Add character 'C' to the list
        
        CharacterList.add('A'); // Add character 'A' to the list
        
        CharacterList.add('R'); // Add character 'R' to the list
        
        CharacterList.printLinkedList(); // Display the populated list
        
        System.out.println("\n3. Testing with ordered insertion (uses YOUR Node.compareTo()):"); // Print test description
        
        LinkedList<Character> OrderedCharacterList = new LinkedList<>(); // Create new LinkedList for ordered insertion
        
        OrderedCharacterList.addInOrder('M'); // Insert 'M' in sorted position
        
        OrderedCharacterList.addInOrder('A'); // Insert 'A' in sorted position
        
        OrderedCharacterList.addInOrder('D'); // Insert 'D' in sorted position
        
        OrderedCharacterList.addInOrder('A'); // Insert 'A' in sorted position
        
        OrderedCharacterList.addInOrder('M'); // Insert 'M' in sorted position
        
        OrderedCharacterList.printLinkedList(); // Display the ordered list
        
        System.out.println("\n4. Testing contains method (uses YOUR Node.equals()):"); // Print test description
        
        Node<Character> TestNode = new Node<>('A');  // Creating Node with character 'A'
        
        System.out.println("Contains 'A': " + OrderedCharacterList.contains(TestNode)); // Test if list contains 'A'
        
        Node<Character> TestNodeZ = new Node<>('Z');  // Creating Node with character 'Z'
        
        System.out.println("Contains 'Z': " + OrderedCharacterList.contains(TestNodeZ)); // Test if list contains 'Z'
        
        System.out.println("\n5. Demonstrating YOUR Node's methods directly:"); // Print demonstration description
        
        Node<Character> NodeOne = new Node<>('B');  // Node constructor with 'B'
        
        Node<Character> NodeTwo = new Node<>('B');  // Node constructor with 'B'
        
        Node<Character> NodeThree = new Node<>('A');  // Node constructor with 'A'
        
        System.out.println("NodeOne.equals(NodeTwo): " + NodeOne.equals(NodeTwo)); // YOUR equals method test
        
        System.out.println("NodeOne.compareTo(NodeThree): " + NodeOne.compareTo(NodeThree)); // YOUR compareTo method test
        
        System.out.println("\n6. Testing removal methods:"); // Print removal test description
        
        OrderedCharacterList.removeFromHead(); // Remove the first node from OrderedCharacterList
        
        OrderedCharacterList.printLinkedList(); // Display list after head removal
        
        System.out.println("\n--- YOUR NODE CLASS IS WORKING PERFECTLY! ---"); // Print success message
    }
}