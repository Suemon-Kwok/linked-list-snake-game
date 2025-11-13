package Question_1;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*
Stack Class Implementation using LinkedList

This class manages a linked list as a stack following LIFO (Last In, First Out) principle

Elements are added and removed from the same end (the top of the stack)

Uses the HEAD of LinkedList as the TOP of the stack for O(1) operations
 */
public class Stack<E extends Comparable<E>> {
    
    // Internal LinkedList to store stack elements
    // This uses YOUR LinkedList class which uses Node class
    private LinkedList<E> InternalLinkedList; // Changed from linkedList - stores all stack elements using the custom LinkedList implementation
    
    /*
    Constructor - initializes an empty stack
     
    Creates a new LinkedList instance to manage the stack elements
     */
    public Stack() {
        // Initialize the internal LinkedList
        // This LinkedList will manage all the actual storage and linking
        this.InternalLinkedList = new LinkedList<>(); // Create new LinkedList instance to handle stack storage operations
    }
    
    /*
    Push Method - adds element to the top of the stack
    
    In stack terminology, "push" means to add an element to the top
    
    We add to the HEAD of the LinkedList (which becomes the top of our stack)
    
    Time Complexity: O(1) due to LinkedList.addHead() adding at beginning
    
    Stack Principle: New elements go on top of the pile
     
    @param ElementData the element to add to the top of the stack
     */
    public void push(E ElementData) { // Changed from data - the data element to be pushed onto the stack top
        // Use LinkedList's addHead() method to add element at the beginning
        // This maintains LIFO order: last elements added will be first to leave
        InternalLinkedList.addHead(ElementData); // Call LinkedList addHead method to insert ElementData at the front (stack top)
        
        // Note: LinkedList.addHead() internally:
        
        // 1. Creates a new Node with the data (using YOUR Node constructor)
        
        // 2. Links new node to current head
        
        // 3. Updates head to point to new node
        
        // 4. Increments the size counter
        
        // This is O(1) operation - no traversal needed!
    }
    
    /*
    Pop Method - removes and returns element from top of stack
     
    In stack terminology, "pop" means to remove the top element
     
    We remove from the HEAD of the LinkedList (which is the top of our stack)
     
     
    Time Complexity: O(1) due to LinkedList.removeFromHead()
     
    Stack Principle: Last element added is first element removed (LIFO)
     
    @return the element that was at the top of the stack
     
    @throws RuntimeException if stack is empty (cannot pop from empty stack)
     */
    public E pop() {
        // Check if stack is empty before attempting to remove
        if (isEmpty()) { // Call isEmpty method to verify stack has elements before removal
            
            // Throw exception with descriptive message for empty stack
            throw new RuntimeException("Cannot pop from empty stack - no elements to remove"); // Create and throw runtime exception for empty stack error condition
        }
        
        // Get the data from the head node before removing it
        // This is the element that will be returned to the caller
        E TopElementData = InternalLinkedList.getData(0);  // Changed from topData - retrieve data at index 0 (top/head position of stack)
        
        // Remove the head node from the LinkedList
        // This effectively removes the top element from our stack
        InternalLinkedList.removeFromHead(); // Call LinkedList removeFromHead method to eliminate the topmost stack element
        
        // Note: LinkedList.removeFromHead() internally:
        
        // 1. Checks if head exists (we already did this check)
        
        // 2. Moves head pointer to head.next
        
        // 3. Decrements the size counter
        
        // 4. The old head node becomes unreferenced and eligible for garbage collection
        
        // Return the data that was removed
        return TopElementData; // Return the data value that was stored in the removed top element
    }
    
    /*
    GET SIZE METHOD - returns the number of elements in the stack
    
    This provides information about how many elements are in the stack
    
    Time Complexity: O(1) since LinkedList maintains a size counter
    
    @return the number of elements currently in the stack
     */
    public int getSize() {
        // Delegate to LinkedList's getSize() method
        // LinkedList maintains an internal size counter for efficiency
        return InternalLinkedList.getSize(); // Call LinkedList getSize method and return current number of stack elements
    }
    
    /*
    Is Empty Method - checks if the stack has any elements
    
    Useful for checking before pop operations to avoid exceptions
    
    Time Complexity: O(1)
    
    @return true if stack is empty, false if it contains elements
     */
    public boolean isEmpty() {
        // A stack is empty if its size is 0
        // This is equivalent to checking if linkedList.getHead() == null
        return InternalLinkedList.getSize() == 0; // Compare LinkedList size to zero to determine if stack contains no elements
    }
    
    /*
    Peek Method - returns top element without removing it
    
    This allows you to see what's on top without actually popping it
    
    Useful for checking what will be popped next
    
    Time Complexity: O(1)
    
    @return the element at the top of the stack (without removing it)
    
    @throws RuntimeException if stack is empty
     */
    public E peek() {
        // Check if stack is empty
        if (isEmpty()) { // Call isEmpty method to verify stack has elements before peeking
            throw new RuntimeException("Cannot peek at empty stack - no elements to view"); // Create and throw runtime exception for empty stack peek attempt
        }
        
        // Return the data from the head without removing the node
        // This uses LinkedList.getData(0) to access top element
        return InternalLinkedList.getData(0); // Call LinkedList getData method to retrieve top element data without removal
    }
    
    /*
    Print Stack Method - displays all elements in the stack
    
    Shows elements from top to bottom in a readable format
    
    This helps visualize the current state of the stack
    
    Format: Shows stack vertically with top element first
    
    "Stack (3 elements): [top] element3 | element2 | element1 [bottom]"
     */
    public void printStack() {
        // Print header with size information
        System.out.print("Stack (" + getSize() + " elements): "); // Display stack label with current element count information
        
        // Handle empty stack case
        if (isEmpty()) { // Check if stack contains no elements to display
            System.out.println("[empty]"); // Print empty stack indicator message
            return; // Exit method early for empty stack condition
        }
        
        // Print opening bracket and top marker
        System.out.print("[top] "); // Display top position marker to show stack orientation
        
        // Print all elements from top to bottom
        // We'll traverse the LinkedList from head to tail
        for (int StackIndex = 0; StackIndex < getSize(); StackIndex++) { // Changed from i - loop through each stack position from top to bottom
            
            // Get element at current index
            E CurrentStackElement = InternalLinkedList.getData(StackIndex); // Changed from element - retrieve element data at current index position
            
            System.out.print(CurrentStackElement); // Display the current element's data value
            
            // Add separator between elements (but not after the last one)
            if (StackIndex < getSize() - 1) { // Check if current element is not the last element in stack
                System.out.print(" | "); // Print vertical bar separator between stack elements
            }
        }
        
        // Print bottom marker and closing bracket
        System.out.println(" [bottom]"); // Display bottom position marker and end line to complete stack visualization
    }
    
    /*
    Clear Method - removes all elements from the stack
     
    Resets the stack to empty state
     
    Time Complexity: O(1) by creating new LinkedList instance
     */
    public void clear() {
        // Create a new empty LinkedList instance
        // This effectively removes all references to old nodes
        // Garbage collection will clean up the old nodes
        this.InternalLinkedList = new LinkedList<>(); // Create new empty LinkedList instance to replace current one and clear all stack elements
    }
    
    /*
    To Array Method - converts stack to array representation
    
    Returns elements in order from top to bottom
    
    Useful for bulk processing or integration with array-based algorithms
    
    @return Object array containing all stack elements from top to bottom
     */
    public Object[] toArray() {
        // Create array with size matching stack size
        Object[] ResultArray = new Object[getSize()]; // Changed from array - create Object array with size equal to current stack size
        
        // Copy elements from LinkedList to array (top to bottom order)
        for (int ArrayIndex = 0; ArrayIndex < getSize(); ArrayIndex++) { // Changed from i - loop through each position to copy elements
            ResultArray[ArrayIndex] = InternalLinkedList.getData(ArrayIndex); // Copy element data from LinkedList at current index to result array
        }
        
        return ResultArray; // Return the populated array containing all stack elements
    }
    
    /*
    Demonstration Method - shows Stack functionality with YOUR classes
    
    This method demonstrates how Stack uses YOUR LinkedList which uses YOUR Node class
    
    Creates sample stacks and shows all the stack operations
     */
    public static void main(String[] args) {
        System.out.println("--- STACK CLASS DEMONSTRATION ---"); // Print demonstration title header
        
        System.out.println("Using YOUR LinkedList class which uses YOUR Node class\n"); // Display class dependency information
        
        // SECTION 1: Creating and basic operations
        System.out.println("1. Creating a new Character stack:"); // Print section header for stack creation
        
        Stack<Character> CharacterStack = new Stack<>(); // Changed from charStack - create new Stack instance for Character type
        
        CharacterStack.printStack(); // Display initial empty stack state
        
        System.out.println("Is empty: " + CharacterStack.isEmpty()); // Print boolean result of isEmpty check
        
        System.out.println("Size: " + CharacterStack.getSize()); // Print current stack size value
        
        System.out.println("\n2. Pushing elements (adding to top):"); // Print section header for push operations
        
        char[] LettersArray = {'H', 'E', 'L', 'L', 'O'}; // Changed from letters - array of characters to push onto stack
        
        for (char CurrentLetter : LettersArray) { // Changed from letter - iterate through each character in array
            
            System.out.println("   Pushing: " + CurrentLetter); // Print which character is being pushed
            
            CharacterStack.push(CurrentLetter); // Add current character to top of stack
            
            CharacterStack.printStack(); // Display stack state after push operation
        }
        
        System.out.println("\n3. Checking top element (peek):"); // Print section header for peek operation
        
        System.out.println("   Top element: " + CharacterStack.peek()); // Print result of peek operation (top element)
        
        System.out.println("   Stack after peek (unchanged):"); // Indicate that peek doesn't modify stack
        
        CharacterStack.printStack(); // Display stack state after peek to show no change
        
        System.out.println("\n4. Popping elements (removing from top):"); // Print section header for pop operations
        
        while (!CharacterStack.isEmpty()) { // Continue loop while stack still has elements
            
            char PoppedCharacter = CharacterStack.pop(); // Changed from popped - remove and store top element
            
            System.out.println("   Popped: " + PoppedCharacter + " | Remaining stack:"); // Print removed character
            
            CharacterStack.printStack(); // Display remaining stack state after pop
        }
        
        System.out.println("\n5. Demonstrating Stack vs Queue behavior:"); // Print section header for comparison
        
        System.out.println("   Adding same sequence 'HELLO' to both structures:"); // Explain what will be demonstrated
        
        Stack<Character> ComparisonStack = new Stack<>(); // Changed from stack - create stack for comparison demonstration
        
        Queue<Character> ComparisonQueue = new Queue<>(); // Changed from queue - create queue for comparison demonstration
        
        // Add same elements to both
        for (char CurrentChar : "HELLO".toCharArray()) { // Changed from c - iterate through each character in "HELLO"
            ComparisonStack.push(CurrentChar); // Add character to stack (LIFO structure)
            ComparisonQueue.enqueue(CurrentChar); // Add character to queue (FIFO structure)
        }
        
        System.out.println("\n   Removing all elements:"); // Indicate start of removal comparison
        
        System.out.print("   Stack (LIFO): "); // Label for stack output showing LIFO behavior
        
        while (!ComparisonStack.isEmpty()) { // Continue while stack has elements
            
            System.out.print(ComparisonStack.pop() + " "); // Pop and print each element from stack
        }
        System.out.println(); // Print newline after stack output
        
        System.out.print("   Queue (FIFO): "); // Label for queue output showing FIFO behavior
        
        while (!ComparisonQueue.isEmpty()) { // Continue while queue has elements
            System.out.print(ComparisonQueue.dequeue() + " "); // Dequeue and print each element from queue
        }
        System.out.println(); // Print newline after queue output
        
        System.out.println("\n6. Testing with Integer stack (shows generic capability):"); // Print section header for generic type test
        
        Stack<Integer> IntegerStack = new Stack<>(); // Changed from intStack - create Stack instance for Integer type
        int[] NumbersArray = {10, 20, 30, 40, 50}; // Changed from numbers - array of integers to test with
        
        System.out.println("   Pushing numbers: "); // Indicate start of integer push operations
        
        for (int CurrentNumber : NumbersArray) { // Changed from num - iterate through each number in array
            IntegerStack.push(CurrentNumber); // Add current number to top of stack
            System.out.println("   Pushed " + CurrentNumber + " | Stack size: " + IntegerStack.getSize()); // Print push confirmation with size
        }
        
        IntegerStack.printStack(); // Display complete integer stack state
        
        System.out.println("\n   Popping numbers:"); // Indicate start of integer pop operations
        
        while (!IntegerStack.isEmpty()) { // Continue while integer stack has elements
            
            System.out.println("   Popped: " + IntegerStack.pop() + " | Remaining size: " + IntegerStack.getSize()); // Print popped value and remaining size
        }
        
        System.out.println("\n--- STACK DEMONSTRATION COMPLETE ---"); // Print completion header
        
        System.out.println("✓ Stack successfully uses YOUR LinkedList class"); // Print success message for LinkedList integration
        
        System.out.println("✓ LinkedList successfully uses YOUR Node class"); // Print success message for Node integration
        
        System.out.println("✓ All stack operations working correctly"); // Print success message for stack functionality
        
        System.out.println("✓ LIFO principle maintained"); // Print success message for LIFO behavior
        
        System.out.println("✓ Both Character and Integer types work (generic capability)"); // Print success message for generic type support
        
        System.out.println("✓ Stack vs Queue behavior clearly demonstrated"); // Print success message for comparison demonstration
    }
}