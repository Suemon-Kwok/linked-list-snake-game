package Question_2;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*
GameObject class represents items that can appear on the game board

This includes letters (food) and numbers (obstacles)
 */
public class GameObject {
    // Private instance variables to store the game object's properties
    private int x, y;           // Position coordinates - x represents horizontal position, y represents vertical position
    
    private char value;         // The character/number value - stores either a letter (A-Z) or digit (0-9)
    
    private boolean IsLetter;   // Boolean flag - true if this object is a letter (food), false if number (obstacle)
    
    /*
    Constructor for GameObject - initializes a new game object with specified properties
    
    @param x x-coordinate - horizontal position on the game board
    
    @param y y-coordinate - vertical position on the game board
    
    @param value the character or number - the actual letter or digit this object represents
    
    @param IsLetter true if this is a letter, false if number - determines object behavior
     */
    public GameObject(int x, int y, char value, boolean IsLetter) {
        // Initialize the x-coordinate with the provided value
        this.x = x;
        
        // Initialize the y-coordinate with the provided value
        this.y = y;
        
        // Store the character/number value that this object represents
        this.value = value;
        
        // Set the flag to indicate whether this is a letter (food) or number (obstacle)
        this.IsLetter = IsLetter;
    }
    
    // Getter methods - provide read-only access to private instance variables
    
    /*
    Get the x-coordinate of this game object
    
    @return the current x position as an integer
     */
    public int getX() { 
        // Return the stored x-coordinate value
        return x; 
    }
    
    /*
    Get the y-coordinate of this game object
    
    @return the current y position as an integer
     */
    public int getY() { 
        // Return the stored y-coordinate value
        return y; 
    }
    
    /*
    Get the character/number value of this game object
    
    @return the character or digit this object represents
     */
    public char getValue() { 
        // Return the stored character/number value
        return value; 
    }
    
    /*
    Check if this game object is a letter (food item)
    
    @return true if this object is a letter, false if it's a number
     */
    public boolean isLetter() { 
        // Return the boolean flag indicating object type
        return IsLetter; 
    }
    
    // Setter methods - allow modification of private instance variables
    
    /*
    Set the x-coordinate of this game object to a new value
    
    @param x the new x-coordinate position
     */
    public void setX(int x) { 
        // Update the stored x-coordinate with the new value
        this.x = x; 
    }
    
    /*
    Set the y-coordinate of this game object to a new value
    
    @param y the new y-coordinate position
     */
    public void setY(int y) { 
        // Update the stored y-coordinate with the new value
        this.y = y; 
    }
    
    /*
    Set the character/number value of this game object
    
    @param value the new character or digit value
     */
    public void setValue(char value) { 
        // Update the stored character/number value with the new value
        this.value = value; 
    }
    
    /*
    Check if this GameObject is at the same position as given coordinates
    
    Used for collision detection between snake and game objects
    
    @param x x-coordinate to check against this object's position
    
    @param y y-coordinate to check against this object's position
    
    @return true if positions match exactly, false otherwise
     */
    public boolean isAt(int x, int y) {
        // Compare both x and y coordinates using logical AND operator
        
        // Returns true only if both coordinates match exactly
        return this.x == x && this.y == y;
    }
}