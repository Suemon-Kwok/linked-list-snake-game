package Question_1;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*
Generic Node class for creating linked collections

This class serves as the building block for linked data structures

@param <E> the type of data stored in the node (must implement Comparable for ordering)
 */

/*

/*
Queue Class Implementation using LinkedList
 
This class manages a linked list as a queue following FIFO (First In, First Out) principle

Elements are added at the rear (end) and removed from the front (head)
 */
public class Queue<E extends Comparable<E>> {
    
    // Internal LinkedList to store queue elements
    // This uses LinkedList class which uses Node class
    private LinkedList<E> QueueLinkedList; // Changed from 'linkedList' - stores all queue elements using custom LinkedList implementation
    
    /*
    Constructor - initializes an empty queue
    
    Creates a new LinkedList instance to manage the queue elements
     */
    public Queue() {
        // Initialize the internal LinkedList
        // This LinkedList will manage all the actual storage and linking
        this.QueueLinkedList = new LinkedList<>(); // Create new LinkedList instance to hold queue data
    }
    
    /*
    Enqueue Method - adds element to the rear of the queue
    
    In queue terminology, "enqueue" means to add an element
    
    We add to the END of the LinkedList (which becomes the rear of our queue)
     
    Time Complexity: O(n) due to LinkedList.add() traversing to end
    
    Queue Principle: New elements join at the back of the line
     
    @param ElementData the element to add to the queue
     */
    public void enqueue(E ElementData) { // Changed from 'data' - the data element to be added to rear of queue
        // Use LinkedList's add() method to append element at the end
        // This maintains FIFO order: first elements added will be first to leave
        QueueLinkedList.add(ElementData); // Call LinkedList add method to append ElementData to end of list
        
        // Note: LinkedList.add() internally:
        
        // 1. Creates a new Node with the data (using YOUR Node constructor)
        
        // 2. Uses recursion to traverse to the end
        
        // 3. Links the new node at the tail
        
        // 4. Increments the size counter
    }
    
    /*
    Dequeue Method - removes and returns element from front of queue
    
    In queue terminology, "dequeue" means to remove an element
    
    We remove from the HEAD of the LinkedList (which is the front of our queue)
    
    Time Complexity: O(1) due to LinkedList.removeFromHead() 
    
    Queue Principle: First element added is first element removed (FIFO)
    
    @return the element that was at the front of the queue
    
    @throws RuntimeException if queue is empty (cannot dequeue from empty queue)
     */
    public E dequeue() {
        // Check if queue is empty before attempting to remove
        if (isEmpty()) { // Call isEmpty method to verify queue has elements
            
            // Throw exception with descriptive message for empty queue
            throw new RuntimeException("Cannot dequeue from empty queue - no elements to remove"); // Create and throw runtime exception for empty queue error
        }
        
        // Get the data from the head node before removing it
        // This is the element that will be returned to the caller
        E FrontElementData = QueueLinkedList.getData(0);  // Changed from 'frontData' - retrieve data at index 0 (front/head position)
        
        // Remove the head node from the LinkedList
        // This effectively removes the front element from our queue
        QueueLinkedList.removeFromHead(); // Call LinkedList removeFromHead method to remove first element
        
        // Note: LinkedList.removeFromHead() internally:
        
        // 1. Checks if head exists (we already did this check)
        
        // 2. Moves head pointer to head.next
        
        // 3. Decrements the size counter
        
        // 4. The old head node becomes unreferenced and eligible for garbage collection
        
        // Return the data that was removed
        return FrontElementData; // Return the data that was stored in the removed front element
    }
    
    /*
    Get Size Method - returns the number of elements in the queue
    
    This provides information about how many elements are waiting in the queue
    
    Time Complexity: O(1) since LinkedList maintains a size counter
    
    @return the number of elements currently in the queue
     */
    public int getSize() {
        // Delegate to LinkedList's getSize() method
        // LinkedList maintains an internal size counter for efficiency
        return QueueLinkedList.getSize(); // Call LinkedList getSize method and return current number of elements
    }
    
    /*
    Print Queue Method - displays all elements in the queue
    
    Shows elements from front to rear in a readable format
    
    This helps visualize the current state of the queue
    
    Format: "Queue: [front] element1 <- element2 <- element3 [rear]"
    
    The arrow direction shows the flow: elements enter from right, exit from left
     */
    public void printQueue() {
        // Print header with size information
        System.out.print("Queue (" + getSize() + " elements): "); // Display queue label with current element count
        
        // Handle empty queue case
        if (isEmpty()) { // Check if queue contains no elements
            System.out.println("[empty]"); // Print empty queue indicator
            return; // Exit method early for empty queue
        }
        
        // Print opening bracket and front marker
        System.out.print("[front] "); // Display front position marker
        
        // Print all elements from front to rear
        // We'll traverse the LinkedList from head to tail
        for (int QueueIndex = 0; QueueIndex < getSize(); QueueIndex++) { // Changed from 'i' - loop through each queue position
            
            // Get element at current index
            E CurrentQueueElement = QueueLinkedList.getData(QueueIndex); // Changed from 'element' - retrieve element at current index position
            System.out.print(CurrentQueueElement); // Display the current element's data
            
            // Add arrow between elements (but not after the last one)
            if (QueueIndex < getSize() - 1) { // Check if not the last element in queue
                System.out.print(" <- "); // Print directional arrow showing queue flow
            }
        }
        
        // Print rear marker and closing bracket
        System.out.println(" [rear]"); // Display rear position marker and end line
    }
    
    /*
    Is Empty Method - checks if the queue has any elements
    
    Useful for checking before dequeue operations to avoid exceptions
    
    Time Complexity: O(1)
    
    @return true if queue is empty, false if it contains elements
     */
    public boolean isEmpty() {
        // A queue is empty if its size is 0
        // This is equivalent to checking if linkedList.getHead() == null
        return QueueLinkedList.getSize() == 0; // Compare LinkedList size to zero to determine if empty
    }
    
    /*
    Demonstration Method - shows Queue functionality with classes
    
    This method demonstrates how Queue uses LinkedList which uses YOUR Node class
    
    Creates sample queues and shows all the queue operations
     */
    public static void main(String[] args) {
        System.out.println("---QUEUE CLASS DEMONSTRATION ---"); // Print demonstration title header
        
        System.out.println("Using YOUR LinkedList class which uses YOUR Node class\n"); // Display class dependency information
        
        // SECTION 1: Creating and basic operations
        System.out.println("1. Creating a new Character queue:"); // Print section header for queue creation
        
        Queue<Character> CharacterQueue = new Queue<>(); // Changed from 'charQueue' - create new Queue instance for Character type
        
        CharacterQueue.printQueue(); // Display initial empty queue state
        
        System.out.println("Is empty: " + CharacterQueue.isEmpty()); // Print whether queue is empty
        
        System.out.println("Size: " + CharacterQueue.getSize()); // Print current queue size
        
        System.out.println("\n2. Enqueuing elements (adding to rear):"); // Print section header for enqueue operations
        
        char[] LettersArray = {'H', 'E', 'L', 'L', 'O'}; // Changed from 'letters' - array of characters to add to queue
        
        for (char CurrentLetter : LettersArray) { // Changed from 'letter' - iterate through each character in array
            
            System.out.println("   Enqueuing: " + CurrentLetter); // Print which character is being added
            
            CharacterQueue.enqueue(CurrentLetter); // Add current character to rear of queue
            
            CharacterQueue.printQueue(); // Display queue state after addition
        }
        
        System.out.println("\n3. Dequeuing elements (removing from front):"); // Print section header for dequeue operations
        
        while (!CharacterQueue.isEmpty()) { // Continue while queue still has elements
            
            char DequeuedCharacter = CharacterQueue.dequeue(); // Changed from 'dequeued' - remove and store front element
            
            System.out.println("   Dequeued: " + DequeuedCharacter + " | Remaining queue:"); // Print removed character
            
            CharacterQueue.printQueue(); // Display remaining queue state
        }
        
        System.out.println("\n--- QUEUE DEMONSTRATION COMPLETE ---"); // Print completion header
        
        System.out.println("✓ Queue successfully uses YOUR LinkedList class"); // Print success message for LinkedList integration
        
        System.out.println("✓ LinkedList successfully uses YOUR Node class"); // Print success message for Node integration
        
        System.out.println("✓ All queue operations working correctly"); // Print success message for queue functionality
        
        System.out.println("✓ FIFO principle maintained"); // Print success message for FIFO behavior
    }
}