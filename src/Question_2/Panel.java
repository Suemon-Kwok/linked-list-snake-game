package Question_2;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

// Import the Graphics class for drawing operations on the panel
import java.awt.*;

// Import KeyEvent class to handle keyboard input events
import java.awt.event.KeyEvent;

// Import KeyListener interface to listen for keyboard events
import java.awt.event.KeyListener;

// Import ArrayList class to store collections of game objects
import java.util.ArrayList;

// Import Random class to generate random numbers for game object placement
import java.util.Random;

// Import logging classes for error handling and debugging
import java.util.logging.Level;
import java.util.logging.Logger;

// Import JPanel class which this Panel class extends
import javax.swing.JPanel;

/*
Enhanced Panel class for the Snake Game

Implements the unique mechanics where snake eats letters and loses them when hitting numbers

Now includes self-collision detection where snake triggers game over when eating its own tail
 */
public class Panel extends JPanel implements KeyListener {
    
    // Game constants - these values define the game's basic parameters and remain unchanged
    
    // GridSize defines the size of each grid cell in pixels for movement calculations
    private static final int GridSize = 20;          
    
    // PanelWidth defines the total width of the game panel in pixels
    private static final int PanelWidth = 1000;      
    
    // PanelHeight defines the total height of the game panel in pixels
    private static final int PanelHeight = 1000;     
    
    // GameSpeed defines the delay between game updates in milliseconds (lower = faster)
    private static final int GameSpeed = 150;        
    
    // CollisionRadius defines the distance threshold for collision detection in pixels
    private static final int CollisionRadius = 15;  
    
    // Game objects - these are the main components that make up the game state
    
    // Snake object represents the player-controlled snake that moves around the board
    private Snake snake;                               
    
    // GameObjects list stores all the letters and numbers currently on the game board
    private ArrayList<GameObject> GameObjects;         
    
    // Random object generates random numbers for positioning game objects
    private Random random;                             
    
    // SnakeTrail stores the recent positions of the snake head for smooth body rendering
    private ArrayList<int[]> SnakeTrail;              
    
    // MaxTrailLength limits how many trail positions we remember to prevent memory issues
    private static final int MaxTrailLength = 50;   
    
    // Movement variables - these control how the snake moves around the board
    
    // VelocityX stores the horizontal movement speed (negative = left, positive = right)
    private int VelocityX = 0;    
    
    // VelocityY stores the vertical movement speed (negative = up, positive = down)
    private int VelocityY = 0;    
    
    // GameRunning flag tracks whether the game is currently active or has ended
    private boolean GameRunning = true;                
    
    // Score and statistics - these track the player's performance during the game
    
    // LettersEaten counts how many letter objects the snake has consumed
    private int LettersEaten = 0;                      
    
    // NumbersHit counts how many number objects the snake has collided with
    private int NumbersHit = 0;                        
    
    // GameOverReason tracks why the game ended for appropriate display messages
    private String GameOverReason = "";               
    
    /*
    Constructor - initializes the game
    
    Sets up all the initial game state and prepares the panel for gameplay
     */
    public Panel() {
        // Add this panel as a key listener to receive keyboard input
        this.addKeyListener(this);
        
        // Make this panel focusable so it can receive keyboard events
        this.setFocusable(true);
        
        // Set the background color to black for better contrast with game objects
        this.setBackground(Color.BLACK);
        
        // Initialize game components by creating new instances of required objects
        
        // Create random number generator for positioning game objects randomly
        random = new Random();
        
        // Create snake at center of panel with @ symbol and centered coordinates
        snake = new Snake("@", PanelWidth/2, PanelHeight/2);
        
        // Create empty list to store all game objects (letters and numbers)
        GameObjects = new ArrayList<>();
        
        // Create empty list to store snake's movement trail for smooth rendering
        SnakeTrail = new ArrayList<>();
        
        // Initialize trail with starting position so snake body can be drawn properly
        // Add center coordinates as first trail position
        SnakeTrail.add(new int[]{PanelWidth/2, PanelHeight/2});
        
        // Generate initial game objects to populate the game board
        GenerateInitialObjects();
    }
    
    /*
    Generate initial game objects (10 numbers and 1 letter)
    
    This method sets up the starting state of objects on the game board
     */
    private void GenerateInitialObjects() {
        // Clear any existing objects to ensure clean starting state
        GameObjects.clear();
        
        // Generate 10 random numbers (0-9) and place them on the board
        for (int i = 0; i < 10; i++) {
            
            // Generate random digit from 0 to 9
            int number = random.nextInt(10);
            
            // Generate random x coordinate within panel bounds with margin
            int x = random.nextInt(PanelWidth - 50) + 25;
            
            // Generate random y coordinate leaving space at top for UI elements
            int y = random.nextInt(PanelHeight - 100) + 50; 
            
            // Create new GameObject with number value and add to collection
            // Convert number to char by adding '0' character code
            // false parameter indicates this is not a letter
            GameObjects.add(new GameObject(x, y, (char)('0' + number), false));
        }
        
        // Generate 1 random letter using separate method for consistency
        GenerateNewLetter();
    }
    
    /*
    Generate a new random letter at a random location
    
    Ensures the letter doesn't spawn on top of existing objects
     */
    private void GenerateNewLetter() {
        // Generate random letter from A to Z
        char letter = (char)('A' + random.nextInt(26));
        
        // Declare variables for position coordinates
        int x, y;
        
        // Keep trying random positions until we find one that's not occupied
        do {
            // Generate random x coordinate within panel bounds with margin
            x = random.nextInt(PanelWidth - 50) + 25;
            
            // Generate random y coordinate leaving space at top for UI
            y = random.nextInt(PanelHeight - 100) + 50;
        } while (IsPositionOccupied(x, y)); // Continue loop if position is occupied
        
        // Create new GameObject with letter value and add to collection
        // true parameter indicates this is a letter (not a number)
        GameObjects.add(new GameObject(x, y, letter, true));
    }
    
    /*
    Generate a new random number at a random location
    
    Ensures the number doesn't spawn on top of existing objects
     */
    private void GenerateNewNumber() {
        // Generate random digit from 0 to 9
        int number = random.nextInt(10);
        
        // Declare variables for position coordinates
        int x, y;
        
        // Keep trying random positions until we find one that's not occupied
        do {
            // Generate random x coordinate within panel bounds with margin
            x = random.nextInt(PanelWidth - 50) + 25;
            
            // Generate random y coordinate leaving space at top for UI
            y = random.nextInt(PanelHeight - 100) + 50;
        } while (IsPositionOccupied(x, y)); // Continue loop if position is occupied
        
        // Create new GameObject with number value and add to collection
        // Convert number to char and false indicates this is not a letter
        GameObjects.add(new GameObject(x, y, (char)('0' + number), false));
    }
    
    /*
    Check if a position is occupied by snake or game objects
    
    This prevents objects from spawning on top of each other or the snake
    
    @param x x coordinate to check
    
    @param y y coordinate to check
    
    @return true if position is occupied, false if free
     */
    private boolean IsPositionOccupied(int x, int y) {
        // Check snake head position (with some tolerance for collision detection)
        
        // Get current snake head position as array
        int[] SnakePos = snake.getHeadPosition();
        
        // Check if proposed position is too close to snake head
        // Use GridSize as minimum distance to prevent overlap
        if (Math.abs(SnakePos[0] - x) < GridSize && Math.abs(SnakePos[1] - y) < GridSize) {
            
            // Return true if position conflicts with snake head
            return true;
        }
        
        // Check snake body positions to prevent spawning on body segments
        ArrayList<int[]> BodyPositions = snake.getBodyPositions();
        for (int[] BodyPos : BodyPositions) {
            // Check if proposed position is too close to any body segment
            if (Math.abs(BodyPos[0] - x) < GridSize && Math.abs(BodyPos[1] - y) < GridSize) {
                // Return true if position conflicts with snake body
                return true;
            }
        }
        
        // Check other game objects to prevent spawning on existing objects
        for (GameObject obj : GameObjects) {
            
            // Check if proposed position is too close to existing object
            // Use GridSize as minimum distance between objects
            if (Math.abs(obj.getX() - x) < GridSize && Math.abs(obj.getY() - y) < GridSize) {
                // Return true if position conflicts with existing object
                return true;
            }
        }
        
        // Return false if position is free (no conflicts found)
        return false;
    }
    
    /*
    Main paint method - renders the game
    
    This method is called repeatedly to update the visual display
     */
    @Override
    public void paint(Graphics g) {
        // Call parent class paint method to clear the panel
        super.paintComponent(g);
        
        // Check if game has ended and show game over screen
        if (!GameRunning) {
            
            // Draw game over screen instead of normal game elements
            DrawGameOver(g);
            
            // Exit method early since game is over
            return;
        }
        
        // Move snake if it has velocity (player is pressing movement keys)
        if (VelocityX != 0 || VelocityY != 0) {
            // Update snake position based on current velocity
            UpdateSnakePosition();
        }
        
        // Check for collisions between snake and game objects
        CheckCollisions();
        
        // Check for self-collision after movement and other collisions
        CheckSelfCollision();
        
        // Draw all game elements in proper order (back to front)
        
        // Draw game objects (letters and numbers) first so they appear behind snake
        DrawGameObjects(g);
        
        // Draw snake on top of game objects
        DrawSnake(g);
        
        // Draw UI elements last so they appear on top of everything
        DrawUI(g);
        
        // Game loop delay to control game speed
        try {
            
            // Pause execution for GameSpeed milliseconds
            Thread.sleep(GameSpeed);
        } catch (InterruptedException ex) {
            
            // Log any errors that occur during sleep
            Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Schedule next repaint to continue the game loop
        repaint();
    }
    
    /*
    Update snake position based on velocity
    
    Handles movement and boundary checking
     */
    private void UpdateSnakePosition() {
        
        // Get current snake head position
        int[] CurrentPos = snake.getHeadPosition();
        
        // Calculate new position by adding velocity to current position
        int NewX = CurrentPos[0] + VelocityX;
        int NewY = CurrentPos[1] + VelocityY;
        
        // Check boundaries to prevent snake from going off screen
        // Left boundary, right boundary, top boundary (leaving space for UI), bottom boundary
        if (NewX < 0 || NewX >= PanelWidth || NewY < 50 || NewY >= PanelHeight) {
            
            // Set game over flag if snake hits boundary
            GameRunning = false;
            
            // Set game over reason for boundary collision
            GameOverReason = "Hit boundary wall";
            
            // Exit method early since game is ending
            return;
        }
        
        // Move snake to new valid position
        snake.moveTo(NewX, NewY);
        
        // Add new position to front of trail for body rendering
        SnakeTrail.add(0, new int[]{NewX, NewY});
        
        // Keep trail length manageable to prevent memory issues
        if (SnakeTrail.size() > MaxTrailLength) {
            
            // Remove oldest position from end of trail
            SnakeTrail.remove(SnakeTrail.size() - 1);
        }
    }
    
    /*
    Check for self-collision between snake head and body
    
    Triggers game over if snake head collides with its own body segments
     */
    private void CheckSelfCollision() {
    // Only check self-collision if snake has enough body and trail for meaningful collision
    // Need at least 3 body segments and sufficient trail length for collision to be possible
    if (snake.getBodySize() >= 3 && SnakeTrail.size() > 5) {
        
        // Use Snake class method with actual trail for accurate collision detection
        if (snake.checkSelfCollision(CollisionRadius, SnakeTrail)) {
                
                // Set game over flag when self-collision is detected
                GameRunning = false;
                
                // Set appropriate game over reason message for display
                GameOverReason = "Snake ate its own tail";
                
                // Print debug message to console for debugging purposes
                System.out.println("Game Over: Snake collided with itself!");
            }
        }
    }
    
    /*
    Check collisions between snake and game objects
    Handles both letter eating and number collision mechanics
     */
    private void CheckCollisions() {
        // Get current snake head position for collision detection
        int[] SnakePos = snake.getHeadPosition();
        
        // Check collision with game objects (iterate backwards for safe removal)
        for (int i = GameObjects.size() - 1; i >= 0; i--) {
            
            // Get current game object to check
            GameObject obj = GameObjects.get(i);
            
            // Check if snake head is close enough to object for collision
            // Use GridSize as collision detection radius
            if (Math.abs(SnakePos[0] - obj.getX()) < GridSize && 
                Math.abs(SnakePos[1] - obj.getY()) < GridSize) {
                
                // Handle collision based on object type
                if (obj.isLetter()) {
                    // Snake eats a letter - positive interaction
                    
                    // Add letter to snake body in alphabetical order
                    snake.eatLetter(obj.getValue());
                    
                    // Increment counter for statistics tracking
                    LettersEaten++;
                    
                    // Remove eaten letter from game board
                    GameObjects.remove(i);
                    
                    // Generate new letter to replace the eaten one
                    GenerateNewLetter(); 
                } else {
                    // Snake hits a number - negative interaction
                    
                    // Convert character digit to integer value
                    int NumberValue = obj.getValue() - '0';
                    
                    // Remove letter from snake based on number value
                    snake.hitByNumber(NumberValue);
                    
                    // Increment counter for statistics tracking
                    NumbersHit++;
                    
                    // Remove hit number from game board
                    GameObjects.remove(i);
                    
                    // Generate new number to replace the hit one
                    GenerateNewNumber(); 
                }
                // Exit loop after handling collision (only one collision per update)
                break;
            }
        }
    }
    
    /*
    Draw all game objects (letters and numbers)
    
    Renders the collectible and obstacle objects on the game board
     */
    private void DrawGameObjects(Graphics g) {
        // Set font for object labels
        g.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Draw each game object with appropriate colors and shapes
        for (GameObject obj : GameObjects) {
            if (obj.isLetter()) {
                // Draw letters in green circles to indicate they are beneficial
                
                // Set green color for letter background
                g.setColor(Color.GREEN);
                
                // Draw filled circle for letter (oval with equal width and height)
                g.fillOval(obj.getX() - 10, obj.getY() - 10, 20, 20);
                
                // Set white color for letter text for good contrast
                g.setColor(Color.WHITE);
                
                // Draw the letter character centered in the circle
                g.drawString(String.valueOf(obj.getValue()), obj.getX() - 5, obj.getY() + 5);
            } else {
                // Draw numbers in bright magenta squares to indicate they are obstacles
                
                // Set magenta color for number background (better accessibility than red)
                g.setColor(Color.MAGENTA);
                
                // Draw filled rectangle for number
                g.fillRect(obj.getX() - 10, obj.getY() - 10, 20, 20);
                
                // Set white color for number text for good contrast
                g.setColor(Color.WHITE);
                
                // Draw the number character centered in the square
                g.drawString(String.valueOf(obj.getValue()), obj.getX() - 5, obj.getY() + 5);
            }
        }
    }
    
    /*
    Draw the snake
    
    Renders the snake head and body letters using the trail system
     */
    private void DrawSnake(Graphics g) {
        // Get current snake head position
        int[] pos = snake.getHeadPosition();
        
        // Draw the collected letters using the trail positions for smooth movement
        
        // Get complete snake body string (includes @ head symbol and letters)
        String BodyStr = snake.getBodyString();
        if (BodyStr.length() > 1) { // If there are letters in the body
            // Remove the @ symbol to get only the collected letters
            String LettersOnly = BodyStr.substring(1); 
            
            // Set font for body letters
            g.setFont(new Font("Arial", Font.BOLD, 14));
            
            // Calculate spacing between letters for smooth body appearance
            int LetterSpacing = 25;
            
            // Draw each letter following the snake's trail
            for (int i = 0; i < LettersOnly.length() && i < SnakeTrail.size() - 1; i++) {
                // Get the letter at current position
                char letter = LettersOnly.charAt(i);
                
                // Calculate position based on trail with appropriate spacing
                // Use spacing to determine which trail position to use
                int TrailIndex = Math.min((i + 1) * (LetterSpacing / GridSize), SnakeTrail.size() - 1);
                
                // Get trail position for this letter
                int[] TrailPos = SnakeTrail.get(TrailIndex);
                
                // Extract x and y coordinates from trail position
                int LetterX = TrailPos[0];
                int LetterY = TrailPos[1];
                
                // Draw a blue circle background for each letter (matching head color)
                // Use semi-transparent blue to show this is part of the snake
                g.setColor(new Color(0, 0, 255, 180)); 
                
                // Draw filled circle for letter background
                g.fillOval(LetterX - 8, LetterY - 8, 16, 16);
                
                // Draw the letter in white for good contrast against blue background
                g.setColor(Color.WHITE);
                
                // Center the letter character in the circle
                g.drawString(String.valueOf(letter), LetterX - 5, LetterY + 4);
            }
        }
        
        // Draw snake head as blue circle (on top of letters for clear visibility)
        
        // Set blue color for snake head
        g.setColor(Color.BLUE);
        // Draw large filled circle for head (larger than body letters)
        g.fillOval(pos[0] - 15, pos[1] - 15, 30, 30);
        
        // Draw @ symbol in the center of the blue circle
        
        // Set white color for head symbol
        g.setColor(Color.WHITE);
        
        // Set larger font for head symbol to make it prominent
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Center the @ symbol in the head circle
        g.drawString("@", pos[0] - 7, pos[1] + 6);
    }
    
    /*
    Draw UI elements (score, instructions, etc.)
    
    Renders all the text information and legends at the top of the screen
     */
    private void DrawUI(Graphics g) {
        // Set white color for all UI text
        g.setColor(Color.WHITE);
        
        // Set bold font for UI elements
        g.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Game title and snake status - positioned at top left
        
        // Draw game title at top left corner
        g.drawString("Snake Game", 10, 20);
        
        // Draw current snake state on second line
        g.drawString("Current Snake: " + snake.getBodyString(), 10, 35);
        
        // Snake length - positioned below snake status
        g.drawString("Length: " + snake.getLength(), 10, 50);
        
        // Statistics - positioned in center area with more spacing
        
        // Draw letters eaten counter
        g.drawString("Letters Eaten: " + LettersEaten, 350, 20);
        
        // Draw numbers hit counter below letters eaten
        g.drawString("Numbers Hit: " + NumbersHit, 350, 35);
        
        // Instructions - positioned on the right side
        
        // Set smaller font for instructions
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Draw control instructions
        g.drawString("Controls: WASD or Arrow Keys", 550, 20);
        
        // Draw gameplay instructions for letters
        g.drawString("Green circles = Letters (eat to grow)", 550, 32);
        
        // Draw gameplay instructions for numbers. Magenta due to some players being colourblind
        g.drawString("Magenta squares = Numbers (hit to drop letters)", 550, 44);
        
        // Legend - visual indicators positioned at top right
        
        // Draw green circle to represent letters. Works with blue and magenta
        g.setColor(Color.GREEN);
        g.fillOval(PanelWidth - 180, 10, 12, 12);
        
        // Draw legend text for letters
        g.setColor(Color.WHITE);
        g.drawString("= Letter", PanelWidth - 165, 20);
        
        // Draw magenta square to represent numbers
        g.setColor(Color.MAGENTA);
        g.fillRect(PanelWidth - 180, 25, 12, 12);
        
        // Draw legend text for numbers
        g.setColor(Color.WHITE);
        g.drawString("= Number", PanelWidth - 165, 35);
    }
    
    /*
    Draw game over screen
    
    Displays final game statistics when the player loses with specific reason
     */
    private void DrawGameOver(Graphics g) {
        // Draw "GAME OVER" title in large red text
        
        // Set red color for dramatic effect
        g.setColor(Color.RED);
        
        // Set very large font for main game over message
        g.setFont(new Font("Arial", Font.BOLD, 48));
        
        // Center the game over message on screen
        g.drawString("GAME OVER", PanelWidth/2 - 150, PanelHeight/2 - 80);
        
        // Draw game over reason in yellow text for visibility
        
        // Set yellow color to highlight the specific reason for game over
        g.setColor(Color.YELLOW);
        
        // Set medium font for game over reason
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Display specific reason why game ended (boundary hit or self-collision)
        g.drawString("Reason: " + GameOverReason, PanelWidth/2 - 120, PanelHeight/2 - 40);
        
        // Draw final game statistics in white text
        
        // Set white color for statistics
        g.setColor(Color.WHITE);
        
        // Set medium font for statistics
        g.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Display final snake state
        g.drawString("Final Snake: " + snake.getBodyString(), PanelWidth/2 - 150, PanelHeight/2);
        
        // Display total letters eaten during game
        g.drawString("Letters Eaten: " + LettersEaten, PanelWidth/2 - 100, PanelHeight/2 + 30);
        
        // Display total numbers hit during game
        g.drawString("Numbers Hit: " + NumbersHit, PanelWidth/2 - 100, PanelHeight/2 + 55);
        
        // Draw restart instruction in smaller text
        
        // Set smaller font for restart instruction
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Display restart instruction centered below statistics
        g.drawString("Press R to restart", PanelWidth/2 - 70, PanelHeight/2 + 90);
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        // Not used - key typed events are handled in keyPressed method
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
        // Handle restart when game is over
        if (!GameRunning) {
            
            // Check for R key press using multiple methods to ensure it works
            // Check both key code and character to handle different keyboard layouts
            if (ke.getKeyCode() == KeyEvent.VK_R || 
                Character.toLowerCase(ke.getKeyChar()) == 'r') {
                
                // Restart the game when R is pressed
                RestartGame();
            }
            // Don't process other keys when game is over
            return; 
        }
        
        // Movement controls - only when game is running
        
        // Handle up movement (W key or up arrow)
        if (ke.getKeyCode() == KeyEvent.VK_UP || ke.getKeyChar() == 'w' || ke.getKeyChar() == 'W') {
            // Set horizontal velocity to 0 (no left/right movement)
            VelocityX = 0;
            
            // Set vertical velocity to negative (up movement)
            VelocityY = -GridSize;
        }
        // Handle down movement (S key or down arrow)
        else if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyChar() == 's' || ke.getKeyChar() == 'S') {
            // Set horizontal velocity to 0 (no left/right movement)
            VelocityX = 0;
            
            // Set vertical velocity to positive (down movement)
            VelocityY = GridSize;
        }
        // Handle left movement (A key or left arrow)
        else if (ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyChar() == 'a' || ke.getKeyChar() == 'A') {
            // Set horizontal velocity to negative (left movement)
            VelocityX = -GridSize;
            
            // Set vertical velocity to 0 (no up/down movement)
            VelocityY = 0;
        }
        // Handle right movement (D key or right arrow)
        else if (ke.getKeyCode() == KeyEvent.VK_RIGHT || ke.getKeyChar() == 'd' || ke.getKeyChar() == 'D') {
            // Set horizontal velocity to positive (right movement)
            VelocityX = GridSize;
            
            // Set vertical velocity to 0 (no up/down movement)
            VelocityY = 0;
        }
        
        // Debug: Print snake status when spacebar is pressed
        if (ke.getKeyChar() == ' ') {
            // Call snake's debug method to print current status to console
            snake.printStatus();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        // Continuous movement - don't stop on key release
        // Snake continues moving in current direction until new key is pressed
    }
    
    /*
    Restart the game
    
    Resets all game state variables to their initial values including game over reason
     */
    private void RestartGame() {
        // Print debug message to console
        System.out.println("Restarting game..."); 
        
        // Reset game state flags and counters
        
        // Set game as running again
        GameRunning = true;
        
        // Stop all movement by setting velocities to 0
        VelocityX = 0;
        VelocityY = 0;
        
        // Reset statistics counters
        LettersEaten = 0;
        NumbersHit = 0;
        
        // Clear game over reason for fresh start
        GameOverReason = "";
        
        // Create new snake at center position
        snake = new Snake("@", PanelWidth/2, PanelHeight/2);
        
        // Reset trail with starting position
        
        // Clear existing trail positions
        SnakeTrail.clear();
        
        // Add center position as initial trail point
        SnakeTrail.add(new int[]{PanelWidth/2, PanelHeight/2});
        
        // Regenerate all game objects for fresh start
        GenerateInitialObjects();
        
        // Force immediate repaint to show the restarted game
        repaint(); 
    }
}