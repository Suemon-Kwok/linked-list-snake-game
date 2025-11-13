package Question_2;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*
Node class for the LinkedList implementation

This class represents individual nodes in the linked list

Each node contains data and a reference to the next node in the sequence

@param <E> the type of data stored (must implement Comparable for ordering operations)
 */
public class Node<E extends Comparable<E>> {
    
    // Instance variable to store the actual data/content of this node
    // This can be any type that implements Comparable (like Character, String, Integer)
    E Data;
    
    // Reference pointer to the next node in the linked list chain
    // If this is null, it indicates this is the last node in the list
    Node<E> Next;
    
    /*
    Constructor - creates a new node with the given data
    
    Initializes the node with provided data and sets next reference to null
    
    @param Data the data to store in this node - can be any Comparable type
     */
    public Node(E Data) {
        // Store the provided data in this node's data field
        this.Data = Data;
        
        // Initialize the next pointer to null (no next node initially)
        // This will be updated when the node is linked into a list
        this.Next = null;
    }
    
    /*
    Equals method - compares two nodes based on their data content
    
    Overrides Object's equals method to provide meaningful node comparison
    
    Used by LinkedList methods like contains() and remove() to find matching nodes
    
    @param obj the object to compare with this node
    
    @return true if the nodes contain equal data, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // First check if we're comparing the node to itself (same memory reference)
        if (this == obj) return true;
        
        // Check if the other object is null or not of the same class type
        // If either condition is true, they cannot be equal
        if (obj == null || getClass() != obj.getClass()) return false;
        
        // Cast the object to Node type for comparison (safe after class check above)
        // Use wildcard <?> since we don't know the exact generic type
        Node<?> node = (Node<?>) obj;
        
        // Compare the data content of both nodes using null-safe comparison
        // If this.data is not null, use its equals method; otherwise check if both are null
        return Data != null ? Data.equals(node.Data) : node.Data == null;
    }
    
    /*
    CompareTo method - compares nodes based on their data for ordering
    
    Implements comparison logic needed for sorted insertion in LinkedList
    
    Used by LinkedList's addInOrder() method to maintain alphabetical/numerical order
    
    @param other the other node to compare with this node
    
    @return negative integer if this < other, 0 if equal, positive integer if this > other
     */
    public int compareTo(Node<E> other) {
        // Handle null case - if other node is null, this node is considered "greater"
        if (other == null) return 1;
        
        // Delegate to the data's compareTo method (e.g., Character.compareTo for letters)
        // This enables proper alphabetical ordering for letters, numerical for numbers, etc.
        return this.Data.compareTo(other.Data);
    }
    
    /*
    ToString method for debugging and display purposes
    
    Overrides Object's toString to provide meaningful string representation
    
    Used when printing the linked list or debugging node contents
    
    @return string representation of the node's data
     */
    @Override
    public String toString() {
        // Convert the stored data to its string representation
        
        // For Character data, this returns the letter; for Integer, the number, etc.
        return Data.toString();
    }
}