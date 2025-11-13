/* 
Which object have you chosen as a monitor object to synchronize your code?
I chose Port object as the monitor object to synchronize the code.

Why did you choose that object as a monitor object to synchronize your code?
Port object was chosen because it represents the shared resource that all ships are trying to access.

The port can only accommodate one ship at a time, so it makes logical sense to synchronize on the 
port object to ensure mutual exclusion. 

This way only one ship can dock at the port at any given time, preventing race conditions and crashes. 

The port object acts as a natural bottleneck and synchronization point for all ship threads.
*/


package Question_3;

/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

public class Ship extends Thread {
    static boolean HasArrived = false; // Static flag shared by all ships to indicate if any ship has arrived at port
    
    int x; // Current x-coordinate position of this ship
    
    int y; // Current y-coordinate position of this ship
    
    boolean Moving = false; // Flag to track if this ship is currently moving towards port
    
    boolean HasFinished = false; // Flag to track if this ship has completed its journey (successfully or failed)
    
    boolean SynchronizedMode = true; // Mode flag indicating whether to use synchronized or unsynchronized docking
    
    Port port; // Reference to the port object that this ship will dock at
    
    Panel panel; // Reference to panel object to update GUI counters
    
    private static volatile int ShipsCurrentlyMoving = 0; // Static counter to track how many ships are moving simultaneously (used for crash detection)

    public Ship(int x, int y, Port port, boolean SynchronizedMode, Panel panel) {
        
        this.x = x; // Set initial x position of ship
        
        this.y = y; // Set initial y position of ship
        
        this.port = port; // Set reference to the port this ship will dock at
        
        this.SynchronizedMode = SynchronizedMode; // Set synchronization mode for this ship
        
        this.panel = panel; // Set reference to panel for GUI updates
    }
    
    public void setSynchronizedMode(boolean SynchronizedMode) {
        this.SynchronizedMode = SynchronizedMode; // Update synchronization mode for this ship
    }
    
    @Override
    public void run() {
        try {
            // Wait a random time before attempting to move (simulates ships starting at different times)
            Thread.sleep((int)(Math.random() * 1000)); // Random start delay between 0-1000ms to stagger ship departures
            
            if (SynchronizedMode) { // If operating in synchronized mode
                moveToPortSynchronized(); // Use synchronized docking method
            } else { // If operating in unsynchronized mode
                moveToPortUnsynchronized(); // Use unsynchronized docking method
            }
        } catch (InterruptedException e) {
            System.out.println("Ship interrupted: " + e.getMessage()); // Handle thread interruption
        }
    }
    
    private void moveToPortSynchronized() throws InterruptedException {
        // Synchronized version - use port as monitor object for thread coordination
        synchronized(port) { // Lock on port object to ensure only one ship can access at a time
            
            // Check if port is available
            
            while (port.has_ship) { // If port is occupied by another ship
                
                port.wait(); // Wait (release lock) until port becomes available
            }
            
            // Port is now available, reserve it
            port.has_ship = true; // Mark port as occupied
            
            Moving = true; // Mark this ship as moving
            
            // Move to port
            moveShipToPort(); // Call helper method to animate ship movement
            
            // Stay at port for 1 second
            HasArrived = true; // Set global flag that ship has arrived
            Thread.sleep(1000); // Stay docked for 1 second
            
            // Leave port
            HasArrived = false; // Clear global arrival flag
            
            port.has_ship = false; // Mark port as available again
            
            Moving = false; // Mark ship as no longer moving
            
            HasFinished = true; // Mark ship as completed its journey
            
            // Update GUI counter - successful completion increments both counters
            if (panel != null) { // If panel reference is valid
                
                panel.incrementFinishedShips(); // This increments both finished and attempted counters
            }
            
            // Notify other waiting ships
            port.notifyAll(); // Wake up all ships waiting for port to become available
        }
    }
    
    private void moveToPortUnsynchronized() throws InterruptedException {
        // Unsynchronized version - recalibrated for 5x ship speed
        
        int MaxAttempts = 80; // Reduced max attempts since ships move faster
        
        int attempts = 0; // Counter for docking attempts
        
        while (!HasFinished && attempts < MaxAttempts) { // Continue trying until finished or max attempts reached
            attempts++; // Increment attempt counter
            
            // Shorter backoff since ships move faster
            Thread.sleep((int)(Math.random() * 100) + 20); // Random wait between 20-120ms before next attempt
            
            // Check if port appears to be available (unsynchronized check - race condition possible)
            if (!port.has_ship) { // If port appears to be free
                
                // Increased race condition window - more ships get bad timing
                if (Math.random() < 0.5) { // 50% chance of bad timing to simulate race conditions
                    Thread.sleep((int)(Math.random() * 80) + 10); // Random delay 10-90ms creates timing conflicts
                }
                
                // Double-check port availability (another race condition point)
                if (!port.has_ship) { // Second check for port availability
                    
                    // Attempt to reserve the port - this is where race conditions happen
                    boolean gotPort = false; // Flag to track if this ship successfully claimed port
                    
                    // More aggressive race condition - allow up to 2 ships to think they got it
                    synchronized(Ship.class) { // Synchronize on Ship class for static counter access
                        
                        if (ShipsCurrentlyMoving <= 1) { // Allow up to 2 ships to think they can move (race condition)
                            
                            ShipsCurrentlyMoving++; // Increment counter of moving ships
                            
                            Moving = true; // Mark this ship as moving
                            gotPort = true; // Mark that ship believes it got the port
                        }
                    }
                    
                    if (gotPort) { // If ship thinks it got the port
                        
                        // Longer collision detection window since movement is faster
                        Thread.sleep(80 + (int)(Math.random() * 80)); // Wait 80-160ms for potential collision detection
                        
                        // Check if another ship also claimed the port (race condition detection)
                        
                        boolean collision = false; // Flag to track if collision occurred
                        
                        synchronized(Ship.class) { // Synchronize access to static counter
                            
                            if (ShipsCurrentlyMoving > 1) { // If more than one ship is moving (collision detected)
                                
                                collision = true; // Mark collision as detected
                                
                                // This ship loses the race, back off
                                Moving = false; // Stop this ship from moving
                                
                                ShipsCurrentlyMoving--; // Decrement moving ships counter
                            }
                        }
                        
                        if (collision) { // If collision was detected
                            
                            // Increased crash probability since detection window is larger
                            if (Math.random() < 0.6) { // 60% chance that collision results in crash
                                
                                HasFinished = true; // Mark ship as finished (crashed)
                                
                                if (panel != null) { // If panel reference is valid
                                    
                                    panel.incrementAttemptedShips(); // Update attempted counter for crashed ship
                                }
                                
                                System.out.println("Ship crashed due to collision!"); // Log crash event
                                
                                return; // Exit method - ship is crashed and done
                            } else {
                                // Just back off and try again (30% chance to survive collision)
                                Thread.sleep(150 + (int)(Math.random() * 200)); // Wait 150-350ms before retry
                                continue; // Go back to beginning of while loop for another attempt
                            }
                        }
                        
                        // Successfully claimed port, now try to dock
                        port.has_ship = true; // Mark port as occupied
                        
                        try {
                            // Move to port (now 5x faster than original)
                            moveShipToPort(); // Call helper method to animate movement to port
                            
                            // Stay at port for docking procedure
                            HasArrived = true; // Set global flag that ship has arrived
                            Thread.sleep(1000); // Stay docked for 1 second
                            
                            // Successfully leave port
                            HasArrived = false; // Clear global arrival flag
                            port.has_ship = false; // Mark port as available
                            
                            // Clean up moving status
                            synchronized(Ship.class) { // Synchronize access to static fields
                                Moving = false; // Mark ship as no longer moving
                                ShipsCurrentlyMoving--; // Decrement moving ships counter
                            }
                            
                            HasFinished = true; // Mark ship as successfully completed
                            
                            // Successfully completed docking procedure
                            if (panel != null) { // If panel reference is valid
                                panel.incrementFinishedShips(); // Update both finished and attempted counters for successful ship
                            }
                            return; // Exit method - ship successfully completed journey
                            
                        } catch (Exception e) {
                            // Clean up on exception during docking
                            synchronized(Ship.class) { // Synchronize access to static fields
                                Moving = false; // Clear moving flag
                                ShipsCurrentlyMoving--; // Decrement moving ships counter
                            }
                            port.has_ship = false; // Clear port occupation
                            HasFinished = true; // Mark ship as finished (failed)
                            
                            if (panel != null) { // If panel reference is valid
                                panel.incrementAttemptedShips(); // Update attempted counter for failed ship
                            }
                            return; // Exit method due to exception
                        }
                    } else {
                        // Couldn't get port access, shorter wait since ships are faster
                        Thread.sleep(80 + (int)(Math.random() * 120)); // Wait 80-200ms before retry
                    }
                    
                } else {
                    // Port became busy while we were checking, shorter wait
                    Thread.sleep(120 + (int)(Math.random() * 180)); // Wait 120-300ms before retry
                }
                
            } else {
                // Port is definitely busy, shorter wait since things move faster
                Thread.sleep(200 + (int)(Math.random() * 150)); // Wait 200-350ms before retry (reduced from original)
            }
        }
        
        // If we reach here, we've exceeded max attempts
        if (!HasFinished) { // If ship hasn't finished yet
            // Too many failed attempts - give up
            HasFinished = true; // Mark ship as finished (failed due to timeout)
            Moving = false; // Clear moving flag
            if (panel != null) { // If panel reference is valid
                panel.incrementAttemptedShips(); // Update attempted counter for ship that gave up
            }
            System.out.println("Ship gave up after " + MaxAttempts + " attempts"); // Log timeout failure
        }
    }
    
    private void moveShipToPort() throws InterruptedException {
        
        // Animate ship movement to port (5x speed compared to original)
        
        int targetX = port.x - 40; // Calculate target x position (40 pixels left of port)
        
        int targetY = port.y; // Target y position is same as port y position
        
        while (x < targetX || Math.abs(y - targetY) > 5) { // Continue moving until ship reaches target position
            
            if (x < targetX) { // If ship needs to move right
                
                x += 10;  // Move 10 pixels right (increased from 6 for 5x speed)
            }
            
            if (y < targetY) { // If ship needs to move down
                
                y += 5;   // Move 5 pixels down (increased from 3 for faster movement)
            
            } else if (y > targetY) { // If ship needs to move up
                
                y -= 5;   // Move 5 pixels up (increased from 3 for faster movement)
            }
            
            Thread.sleep(17); // Small delay for smooth animation (approximately 60 FPS)
        }
    }
}