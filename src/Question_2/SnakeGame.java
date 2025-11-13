package Question_2;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

// Import statement for JFrame - provides the main window container for the game
import javax.swing.JFrame;
// Import statement for JOptionPane - provides dialog boxes for user interaction
import javax.swing.JOptionPane;

/*
Main SnakeGame class - Entry point for the game

Creates and displays the game window

This class serves as the launcher for the Snake game application
 */
public class SnakeGame {

    /*
    Main method - starts the game
    
    This is the entry point that the JVM calls when the program starts
    
    @param args command line arguments passed to the program (not used in this game)
     */
    public static void main(String[] args) {
        // Call method to display game instructions in a popup dialog before starting
        showInstructions();
        
        // Create new JFrame object to serve as the main window container
        // Set the window title to clearly identify this as the Snake Game
        JFrame gameFrame = new JFrame("Snake Game - LinkedList Edition");
        
        // Set default close operation to terminate the program when window is closed
        // EXIT_ON_CLOSE ensures the entire application shuts down, not just the window
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Disable window resizing to maintain consistent game board dimensions
        // This prevents display issues that could occur with dynamic window sizing
        gameFrame.setResizable(false);
        
        // Create new Panel object which contains all the game logic and rendering
        // Panel extends JPanel and implements the main game functionality
        Panel gamePanel = new Panel();
        
        // Add the game panel to the frame's content pane
        // This makes the panel visible within the window
        gameFrame.getContentPane().add(gamePanel);
        
        // Set the window size to 1000x1000 pixels
        // This creates a square game area that matches the panel dimensions
        gameFrame.setSize(1000, 1000);
        
        // Center the window on the screen regardless of screen resolution
        // null parameter centers relative to the entire screen
        gameFrame.setLocationRelativeTo(null);
        
        // Make the window visible to the user
        // Window remains hidden until this method is called
        gameFrame.setVisible(true);
        
        // Request keyboard focus for the game panel
        // This ensures that key presses are captured by the panel's KeyListener
        gamePanel.requestFocusInWindow();
        
        // Print startup confirmation message to console for debugging
        System.out.println("Snake Game started!");
        
        // Print technical information about the data structure implementation
        System.out.println("Using LinkedList to store snake body letters in alphabetical order.");
        
        // Print debugging instruction for users who want to monitor snake status
        System.out.println("Press SPACE during game to print snake status to console.");
    }
    
    /*
    Show game instructions in a dialog
    
    Displays comprehensive instructions and game mechanics before gameplay begins
    
    Uses JOptionPane to create a modal dialog that blocks until dismissed
     */
    private static void showInstructions() {
        // Create multi-line string containing all game instructions and mechanics
        // Uses string concatenation to build comprehensive instruction text
        String gameInstructions = 
            // Game title header with decorative formatting
            "--- Snake game - Linkedlist edition ---\n\n" +
            
            // Basic control instructions section
            "How to play:\n" +
            
            "- Use WASD or Arrow Keys to move the snake\n" +
            
            "- Snake head is represented by a blue circle (@)\n" +
            
            // Letter collection mechanics explanation
            "- Eat GREEN circles (letters) to grow your snake\n" +
            
            "- Letters are added to snake body in ALPHABETICAL ORDER\n" +
            
            // Number collision mechanics explanation
            "- Hit RED squares (numbers 0-9) to drop letters from snake\n" +
            
            "- Number value determines which letter to drop (1-based index)\n" +
            
            "- If number > snake length, last letter is dropped\n\n" +
            
            // Concrete examples section to clarify mechanics
            "EXAMPLES:\n" +
            
            "Snake: @ABD → Eat 'C' → Snake: @ABCD\n" +
            
            "Snake: @ABCDEF → Hit '3' → Snake: @ABDEF (drops 'C')\n" +
            
            "Snake: @ABCDEF → Hit '9' → Snake: @ABCDE (drops last letter)\n\n" +
            
            // Technical features highlight
            "FEATURES:\n" +
            
            "- Uses recursive LinkedList implementation\n" +
            
            "- No loops in LinkedList class - all recursive methods\n" +
            
            "- Letters stored in alphabetical order using addInOrder()\n" +
            
            "- Dynamic object generation and collision detection\n\n" +
            
            // Final instruction to proceed
            "Press OK to start playing!";
        
        // Display the instruction dialog as an information message
        // Modal dialog blocks execution until user clicks OK
        // Parameters: parent component (null = center on screen), message text, dialog title, message type
        JOptionPane.showMessageDialog(null, gameInstructions, "Game Instructions", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
}