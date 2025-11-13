package Question_1;
/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*
Node class for creating linked collections
 
This class serves as the building block for linked data structures

@param <E> the type of data stored in the node (must implement Comparable for ordering)
 */
public class Node<E extends Comparable<E>> {
    
    // Data field - stores the actual content of the node (can be any Comparable type)
    public E Data; // Changed from 'data' - holds the actual value/content stored in this node
    
    // Link field - references the next node in the linked structure
    // This creates the "chain" that links nodes together
    public Node<E> Next; // Changed from 'next' - pointer/reference to the subsequent node in the linked chain
    
    /*
    Parameterized Constructor - creates a node with specific data
    
    @param InputData the data to store in this node
     */
    public Node(E InputData) { // Changed parameter from 'data' to 'InputData' - the value to be stored in this node
        this.Data = InputData;        // Store the provided data in the Data field of this node instance
        this.Next = null;        // Initially, this node doesn't point to any other node (set Next reference to null)
    }
    
    /*
    Default Constructor - creates an empty node
    
    To create a node and set data later
     */
    public Node() {
        this.Data = null;        // No data initially - set Data field to null indicating empty node
        this.Next = null;        // No link to next node initially - set Next reference to null
    }
    
    /*
    Equality Method - compares this node's data with another node's data
     
    This method handles null cases safely to prevent NullPointerException
     
    @param ComparisonNode the node to compare with
     
    @return true if both nodes have equal data, false otherwise
     */
    public boolean equals(Node<E> ComparisonNode) { // Changed parameter from 'node' to 'ComparisonNode' - the node being compared against this one
        // Case 1: If the input node is null
        if (ComparisonNode == null) { // Check if the ComparisonNode parameter is a null reference
            return this.Data == null;  // Return true only if our Data is also null (both nodes are empty)
        }
        
        // Case 2: If our data is null
        if (this.Data == null) { // Check if the current node's Data field is null
            return ComparisonNode.Data == null;  // Return true only if the other node's Data is also null
        }
        
        // Case 3: Both nodes have data - use the data type's equals method
        return this.Data.equals(ComparisonNode.Data); // Use the equals method of the Data type to compare actual values
    }
    
    /*
    Comparison Method - compares this node's data with another node's data for ordering
     
    Uses the Comparable interface to determine relative ordering
     
    @param ComparisonNode the node to compare with
     
    @return 0 if equal, negative value if this < node, positive value if this > node
     */
    public int compareTo(Node<E> ComparisonNode) { // Changed parameter from 'node' to 'ComparisonNode' - the node to compare against for ordering
        // Case 1: Handle null input node or null data in input node
        if (ComparisonNode == null || ComparisonNode.Data == null) { // Check if ComparisonNode is null or its Data is null
            
            // If our data is null too, they're equal (return 0)
            // If our data is not null, we're "greater" (return positive)
            return (this.Data == null) ? 0 : 1; // Ternary operator: if this.Data is null return 0, otherwise return 1
        }
        
        // Case 2: Our data is null but other node has data
        if (this.Data == null) { // Check if current node's Data is null while ComparisonNode has data
            return -1;  // Null is considered "less than" any actual value (return negative number)
        }
        
        // Case 3: Both nodes have non-null data - use Comparable's compareTo method
        // This will work for String (alphabetical), Integer (numerical), Character, etc.
        return this.Data.compareTo(ComparisonNode.Data); // Use the compareTo method of the Data type to determine ordering
    }
    
    /*
    String representation of the node - useful for debugging and display
    
    @return string representation of the node's data
     */
    @Override
    public String toString() {
        return Data != null ? Data.toString() : "null"; // If Data is not null, return its string representation; otherwise return "null"
    }
}