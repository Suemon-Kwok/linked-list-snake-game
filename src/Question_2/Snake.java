package Question_2;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

import java.util.ArrayList;

/*
Enhanced Snake class that uses LinkedList to store eaten letters

Snake head is represented by '@' and body shows letters in alphabetical order

This class manages the snake's position, movement, and letter collection mechanics
 */
public class Snake {
    // Instance variables to store the snake's current head position coordinates
    private int x, y;                           // Head position - x is horizontal, y is vertical pixel coordinates
    
    // LinkedList to store the letters eaten by the snake in alphabetical order
    // Uses the custom recursive LinkedList implementation with Character data type
    private LinkedList<Character> Body;         // Letters eaten by snake (maintained in alphabetical order)
    
    // Constant symbol representing the snake's head character for visual display
    private static final char HeadSymbol = '@'; // Snake head symbol displayed in the game
    
    /*
    Constructor for Snake - initializes a new snake at specified position
    
    @param InitSnake initial symbol (legacy parameter, not used in current implementation)
    
    @param x starting x position - horizontal pixel coordinate on the game panel
    
    @param y starting y position - vertical pixel coordinate on the game panel
     */
    public Snake(String InitSnake, int x, int y) {
        // Set the snake's head position to the provided coordinates
        this.x = x;
        this.y = y;
        
        // Initialize an empty LinkedList to store letters the snake will eat
        // Uses the recursive LinkedList class that maintains alphabetical order
        this.Body = new LinkedList<>(); // Initialize empty body - snake starts with no letters
    }
    
    /*
    Move the snake to new position - updates head coordinates
    
    Called by Panel class during game loop to move snake based on user input
    
    @param NewX new x coordinate - horizontal destination pixel position
    
    @param NewY new y coordinate - vertical destination pixel position
     */
    public void moveTo(int NewX, int NewY) {
        // Update the snake's head x-coordinate to the new horizontal position
        this.x = NewX;
        // Update the snake's head y-coordinate to the new vertical position
        this.y = NewY;
    }
    
    /*
    Eat a letter - add it to body in alphabetical order
    
    Called when snake collides with a letter GameObject in the Panel class
    
    @param letter the letter character to eat and add to snake body
     */
    public void eatLetter(char letter) {
        // Convert letter to uppercase for consistency and add to LinkedList in sorted order
        // Uses LinkedList's addInOrder() method which recursively finds correct position
        Body.addInOrder(Character.toUpperCase(letter));
    }
    
    /*
    Hit by a number - remove letter at specified position
    
    Called when snake collides with a number GameObject in the Panel class
    
    Number value determines which letter position to remove (1-based indexing)
    
    @param number the number that determines which letter to drop (1-based index)
     */
    public void hitByNumber(int number) {
        // Check if snake has any letters in body to remove
        if (Body.getSize() == 0) return; // No body to remove from - exit early
        
        // Convert from 1-based numbering (game logic) to 0-based indexing (programming)
        int index = number - 1;
        
        // Handle different cases based on the calculated index
        if (index >= Body.getSize()) {
            // If number is greater than snake length, remove last letter (tail)
            // Uses LinkedList's recursive removeFromTail() method
            Body.removeFromTail();
        } else if (index >= 0) {
            // Remove letter at the specified valid index position
            // Uses LinkedList's recursive remove(index) method
            Body.remove(index);
        }
        // If index < 0 (number was 0), do nothing - no removal occurs
    }
    
    /*
    Get the snake's current body as a string for display
    
    Combines head symbol with all letters in alphabetical order
    
    Used by Panel class for rendering snake and UI display
    
    @return string representation of snake (head symbol + body letters)
     */
    public String getBodyString() {
        // Create StringBuilder for efficient string concatenation
        StringBuilder sb = new StringBuilder();
        
        // Add the head symbol (@) as the first character
        sb.append(HeadSymbol); // Add head symbol to start of string
        
        // Iterate through all letters in the LinkedList body
        // Add each letter from the LinkedList to the string representation
        for (int i = 0; i < Body.getSize(); i++) {
            // Get data at index i using LinkedList's recursive getData() method
            sb.append(Body.getData(i));
        }
        
        // Convert StringBuilder to String and return complete snake representation
        return sb.toString();
    }
    
    /*
    Get snake head position coordinates
    
    Returns current head position as array for collision detection
    
    Used by Panel class for rendering and collision detection with GameObjects
    
    @return integer array with [x, y] coordinates of snake head
     */
    public int[] getHeadPosition() {
        // Create and return new array containing current head coordinates
        return new int[]{x, y};
    }
    
    /*
    Get body positions for collision detection
    
    Returns positions where each letter in the snake's body would be displayed
    
    Used by Panel class to check for object spawning conflicts
    
    Note: This method provides approximate positions. For accurate self-collision,
    Panel class should pass its SnakeTrail to checkSelfCollision method.
    
    @return ArrayList of int arrays, each containing [x, y] coordinates
     */
    public ArrayList<int[]> getBodyPositions() {
        ArrayList<int[]> positions = new ArrayList<>();
        
        // Calculate spacing between letters (should match Panel's LetterSpacing)
        int LetterSpacing = 25;
        int GridSize = 20;
        
        // Add positions for each letter in the body
        for (int i = 0; i < Body.getSize(); i++) {
            // Calculate position behind the head based on spacing
            // Each letter is positioned further back along the movement path
            int bodyX = x - (i + 1) * LetterSpacing;
            int bodyY = y; // For simplicity, keep same Y coordinate
            
            // Add this position to the list
            positions.add(new int[]{bodyX, bodyY});
        }
        
        return positions;
    }
    
    /*
    Check for self-collision between snake head and body
    
    Determines if snake head is too close to any body segment
    
    Used by Panel class to trigger game over when snake eats itself
    
    @param collisionRadius minimum distance before collision is detected
    
    @return true if collision detected, false otherwise
     */
    public boolean checkSelfCollision(int collisionRadius) {
        // Only check for collision if snake has body segments
        if (Body.getSize() == 0) return false;
        
        // Get all body positions using the approximate method
        ArrayList<int[]> bodyPositions = getBodyPositions();
        
        // Check if head position conflicts with any body position
        for (int[] bodyPos : bodyPositions) {
            // Calculate distance between head and this body segment
            int dx = Math.abs(x - bodyPos[0]);
            int dy = Math.abs(y - bodyPos[1]);
            
            // Check if distance is within collision radius
            if (dx < collisionRadius && dy < collisionRadius) {
                return true; // Collision detected
            }
        }
        
        return false; // No collision
    }
    
    /*
    Check for self-collision using actual trail positions from Panel
    
    More accurate collision detection using the actual snake trail
    
    Used by Panel class for precise self-collision detection
    
    @param collisionRadius minimum distance before collision is detected
    @param snakeTrail the actual trail positions from Panel class
    
    @return true if collision detected, false otherwise
     */
    public boolean checkSelfCollision(int collisionRadius, ArrayList<int[]> snakeTrail) {
        // Only check for collision if snake has body segments and sufficient trail length
        if (Body.getSize() == 0 || snakeTrail.size() < 2) return false;
        
        // Get current head position
        int[] headPos = snakeTrail.get(0);
        
        // Check collision with body segments (skip first few trail positions to avoid immediate collision)
        for (int i = Math.max(2, (25 / 20)); i < Math.min(snakeTrail.size(), Body.getSize() + 1); i++) {
            int[] trailPos = snakeTrail.get(i);
            
            // Calculate distance between head and this trail position
            int dx = Math.abs(headPos[0] - trailPos[0]);
            int dy = Math.abs(headPos[1] - trailPos[1]);
            
            // Check if distance is within collision radius
            if (dx < collisionRadius && dy < collisionRadius) {
                return true; // Collision detected
            }
        }
        
        return false; // No collision
    }
    
    /*
    Get the total length of the snake (head + body letters)
    
    Calculates complete snake size including head and all collected letters
    
    Used for game statistics and UI display
    
    @return total length as integer (head plus number of letters)
     */
    public int getLength() {
        // Add 1 to body size to account for the head (@) symbol
        return Body.getSize() + 1; // +1 for the head
    }
    
    /*
    Get the number of letters in body only (excludes head)
    
    Returns count of letters collected by snake
    
    Used for game logic and statistics tracking
    
    @return number of letters eaten as integer
     */
    public int getBodySize() {
        // Return the size of the LinkedList containing the letters
        return Body.getSize();
    }
    
    /*
    Run method (placeholder - main game loop handled by Panel)
    
    Legacy method that could contain snake-specific update logic
    
    Currently unused as game loop is managed in Panel class
     */
    public void run() {
        // This method can be used for any snake-specific logic if needed
        // Currently, movement and game logic are handled in the Panel class
        // Left empty as Panel class manages the main game loop and snake updates
    }
    
    // Getter methods for accessing snake head position components
    
    /*
    Get current x-coordinate of snake head
    
    @return horizontal position as integer pixel coordinate
     */
    public int getX() { 
        // Return current horizontal position of snake head
        return x; 
    }
    
    /*
    Get current y-coordinate of snake head  
    
    @return vertical position as integer pixel coordinate
     */
    public int getY() { 
        // Return current vertical position of snake head
        return y; 
    }
    
    /*
    Debug method to print snake status to console
    
    Outputs current position, body string, and total length
    
    Called when spacebar is pressed during gameplay for debugging
     */
    public void printStatus() {
        // Print comprehensive snake information to console for debugging
        // Includes position coordinates, visual representation, and length
        System.out.println("Snake at (" + x + "," + y + "): " + getBodyString() + 
                          " (Length: " + getLength() + ")");
    }
}