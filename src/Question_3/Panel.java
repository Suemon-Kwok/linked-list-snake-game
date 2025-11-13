
package Question_3;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.lang.reflect.Field;


public class Panel extends JPanel implements KeyListener{
    
    int NumberShip = 20; // Total number of ships in the simulation
    
    boolean ProgramStarts = false; // Flag to track if the simulation has started
    
    boolean SynchronizedMode = true; // Toggle between synchronized and unsynchronized modes
    
    Ship[] ships = new Ship[NumberShip]; // Array to hold all ship objects
    
    Port port; // The port object that ships will dock at
    
    Image ShipImage; // Image sprite for ships
    
    Image IslandImage; // Image sprite for empty island/port
    
    Image BoatIslandImage; // Image sprite for island with boat docked
    
    private boolean crashed = false; // Flag to track if ships have crashed
    
    private int ShipsFinished = 0; // Counter for ships that successfully docked
    
    private int ShipsAttempted = 0; // Counter for total ships that have finished (successful + crashed)
    
    private long CrashTime = 0; // Timestamp when crash occurred for display timing
    
    private long LastCrashCheck = 0; // Timestamp for periodic crash checking to optimize performance
    
    public Panel()
    {
        this.addKeyListener(this); // Register this panel as a key listener for user input
        
        this.setFocusable(true); // Allow panel to receive keyboard focus
        
        this.setBackground(new Color(135, 206, 250)); // Set sky blue background color
        
        port = new Port(800, 400); // Create port object at coordinates (800, 400)
        
        // Initialize all ship objects with starting positions and references
        for(int i = 0; i < NumberShip; i++)
        {
            ships[i] = new Ship(20, 150 + i*25, port, SynchronizedMode, this); // Create ship with x=20, y=150+offset, port reference, sync mode, and panel reference
        }
        
        //Please do not change the path of images physically and in code
        try {
            
            ShipImage = new ImageIcon("boat.png").getImage(); // Load boat sprite image
            
            IslandImage = new ImageIcon("land.png").getImage(); // Load island sprite image
            
            BoatIslandImage = new ImageIcon("boat_land.png").getImage(); // Load island with boat sprite image
        } catch (Exception e) {
            
            System.out.println("Images not found, using colored rectangles instead"); // Fallback message if images fail to load
        }
        
        setPreferredSize(new Dimension(1000, 700)); // Set preferred window size
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); // Call parent class paintComponent for proper rendering setup
        
        g.setFont(new Font("Monospaced", Font.BOLD, 20)); // Set font for text display
       
        g.setColor(Color.BLACK); // Set text color to black
        
        // Draw instructions and status information on screen
        g.drawString("Press SPACE to start ships", 20, 30); // Display control instruction
        
        g.drawString("Press 'S' to toggle Synchronized/Unsynchronized mode", 20, 55); // Display mode toggle instruction
        
        g.drawString("Mode: " + (SynchronizedMode ? "SYNCHRONIZED" : "UNSYNCHRONIZED"), 20, 80); // Display current mode
        
        g.drawString("Ships successfully docked: " + ShipsFinished + "/" + NumberShip, 20, 105); // Display success counter
        
        g.drawString("Ships attempted: " + ShipsAttempted + "/" + NumberShip, 20, 130); // Display total attempts counter
        
        // Draw all ships that are still active (not finished)
        for(int i = 0; i < ships.length; i++)
        {
            if(ships[i] != null && !ships[i].HasFinished) // Only draw ships that exist and haven't finished
            {
                if(ShipImage != null) { // If ship image loaded successfully
                    g.drawImage(ShipImage, ships[i].x, ships[i].y, 30, 20, this); // Draw ship image at current position
                } else {
                    // Fallback: draw colored rectangle for ship if image not available
                    g.setColor(Color.BLUE); // Set rectangle color to blue
                    
                    g.fillRect(ships[i].x, ships[i].y, 30, 20); // Draw filled rectangle representing ship
                    
                    g.setColor(Color.BLACK); // Reset color to black for text
                   
                    g.drawString("S" + i, ships[i].x + 5, ships[i].y + 15); // Draw ship identifier number
                }
            }
        }
        
        // Check for crashes in unsynchronized mode (check every 100ms to avoid excessive checking)
        
        long currentTime = System.currentTimeMillis(); // Get current system time
        
        if(!SynchronizedMode && currentTime - LastCrashCheck > 100) { // Only check crashes in unsynchronized mode and throttle checks
            
            LastCrashCheck = currentTime; // Update last check timestamp
            
            checkForCrashes(); // Call crash detection method
        }
        
        // Keep showing crash message for 3 seconds after crash occurs
        if(CrashTime > 0 && currentTime - CrashTime < 3000) { // If crash occurred and less than 3 seconds have passed
            
            crashed = true; // Keep crashed flag active
        } else if(CrashTime > 0) { // If more than 3 seconds have passed since crash
            
            crashed = false; // Clear crashed flag
        }
        
        // Display crash message if ships have crashed
        if(crashed)
        {
            g.setColor(Color.RED); // Set color to red for crash message
            
            g.setFont(new Font("Monospaced", Font.BOLD, 30)); // Set larger font for crash message
            
            g.drawString("SHIPS CRASHED!", 350, 50); // Display crash warning message
            
            g.setFont(new Font("Monospaced", Font.BOLD, 20)); // Reset font size back to normal
            
            g.setColor(Color.BLACK); // Reset color back to black
        }
                
        // Draw port/island with appropriate image based on whether ship has arrived
        if(Ship.HasArrived) // If a ship is currently at the port
        {
            if(BoatIslandImage != null) { // If boat+island image loaded successfully
                g.drawImage(BoatIslandImage, port.x, port.y-26, 80, 80, this); // Draw island with boat image
            } else {
                // Fallback: draw island with ship using shapes
                g.setColor(new Color(34, 139, 34)); // Set forest green color for island
                
                g.fillOval(port.x, port.y, 80, 50); // Draw green oval representing island
                
                g.setColor(Color.BLUE); // Set blue color for ship
                
                g.fillRect(port.x + 25, port.y + 10, 30, 20); // Draw blue rectangle representing ship at port
                
                g.setColor(Color.BLACK); // Reset color to black
                
                g.drawString("PORT", port.x + 10, port.y + 70); // Draw port label
            }
        }
        else // If no ship is currently at the port
        {
            if(IslandImage != null) { // If island image loaded successfully
                
                g.drawImage(IslandImage, port.x, port.y-26, 80, 80, this); // Draw empty island image
            } else {
                // Fallback: draw empty island using shapes
                
                g.setColor(new Color(34, 139, 34)); // Set forest green color for island
                
                g.fillOval(port.x, port.y, 80, 50); // Draw green oval representing empty island
                
                g.setColor(Color.BLACK); // Reset color to black
                
                g.drawString("PORT", port.x + 10, port.y + 70); // Draw port label
            }
        }

        repaint(); // Trigger continuous repainting for animation
    }
    
    private void checkForCrashes() {
        
        if(!SynchronizedMode) { // Only check for crashes in unsynchronized mode
            
            int MovingCount = 0; // Counter for ships currently in motion
            
            for(int i = 0; i < ships.length; i++) // Loop through all ships
            {
                if(ships[i] != null && ships[i].Moving) // If ship exists and is currently moving
                {
                    MovingCount++; // Increment counter of moving ships
                }
            }
            if(MovingCount > 1) { // If more than one ship is moving simultaneously (collision condition)
                
                if(!crashed) { // Only set crash time once to avoid resetting timer
                    
                    CrashTime = System.currentTimeMillis(); // Record time when crash was detected
                }
                crashed = true; // Set crash flag to display crash message
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
        System.out.println("\""+ke.getKeyChar()+"\" is typed."); // Log which key was pressed for debugging
        
        if(ke.getKeyChar() == ' ') { // If spacebar was pressed
            // Start all ships simulation
            
            ProgramStarts = true; // Set program started flag
            
            ShipsFinished = 0; // Reset successful completion counter
            
            ShipsAttempted = 0; // Reset total attempts counter
            
            CrashTime = 0; // Reset crash timer
            
            crashed = false; // Clear crash flag
            
            Ship.HasArrived = false; // Clear global arrival flag
            
            // Reset static counter in Ship class using reflection
            resetShipStaticCounters(); // Call helper method to reset static counters in Ship class
            
            // Create NEW ship instances (can't restart threads once they've finished)
            for(int i = 0; i < NumberShip; i++) {
                ships[i] = new Ship(20, 150 + i*25, port, SynchronizedMode, this); // Create new ship with starting position, port reference, current mode, and panel reference
                
                ships[i].start(); // Start the ship thread to begin movement
            }
            
            // Log simulation start with current mode
            System.out.println("Started " + NumberShip + " ships in " + 
                             (SynchronizedMode ? "SYNCHRONIZED" : "UNSYNCHRONIZED") + " mode");
        }
        
        if(ke.getKeyChar() == 's' || ke.getKeyChar() == 'S') { // If 'S' or 's' key was pressed
            // Toggle synchronization mode between synchronized and unsynchronized
            SynchronizedMode = !SynchronizedMode; // Flip the boolean value
            
            // Reset port state when switching modes to ensure clean state
            port.has_ship = false; // Clear port occupation flag
            
            Ship.HasArrived = false; // Clear global ship arrival flag
            
            CrashTime = 0; // Reset crash timer
            
            crashed = false; // Clear crash flag
            
            // Log mode change and instruction for user
            System.out.println("Mode changed to: " + (SynchronizedMode ? "SYNCHRONIZED" : "UNSYNCHRONIZED"));
            
            System.out.println("Press SPACE to start simulation with new mode");
        }
    }
    
    private void resetShipStaticCounters() {
        try {
            // Use reflection to access and reset the static counter in Ship class
            Field field = Ship.class.getDeclaredField("ShipsCurrentlyMoving"); // Get reference to static field
            
            field.setAccessible(true); // Make private field accessible
            
            field.setInt(null, 0); // Reset static field to 0 (null because it's static)
        } catch (Exception e) {
            
            System.out.println("Could not reset static counter: " + e.getMessage()); // Log error if reflection fails
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        // Empty implementation - not used in this simulation
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        // Empty implementation - not used in this simulation
    }
    
    public synchronized void incrementFinishedShips() {
        ShipsFinished++; // Increment counter of ships that successfully docked
        
        ShipsAttempted++; // Also increment total attempts since successful docking counts as attempt
    }
    
    public synchronized void incrementAttemptedShips() {
        
        ShipsAttempted++; // Increment counter of total ship attempts (includes failures)
    }
}