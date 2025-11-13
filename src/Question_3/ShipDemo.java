package Question_3;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

public class ShipDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> { // Execute GUI creation on Event Dispatch Thread for thread safety
            
            JFrame frame = new JFrame("Ship-Island Simulation - Synchronized vs Unsynchronized"); // Create main application window with title
            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set application to exit when window is closed
            
            Panel panel = new Panel(); // Create main panel that contains the simulation
            
            frame.getContentPane().add(panel); // Add the panel to the frame's content area
            
            frame.setSize(1000, 750); // Set window dimensions to 1000x750 pixels
            
            frame.setLocationRelativeTo(null); // Center the window on screen
            
            frame.setVisible(true); // Make the window visible to user
            
            // Request focus for key listening
            panel.requestFocusInWindow(); // Give keyboard focus to panel so it can receive key events
        });
    }
}