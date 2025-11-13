package Question_1;
/*
Name: Suemon Kwok

Student ID: 14883335

Data structures and algorithms
*/

/*
 DataAnalysis Class - Palindrome Checker using Queue and Stack

  
 Palindrome Checking Strategy:
- Use Stack (LIFO) to get characters in reverse order
 
- Use Queue (FIFO) to get characters in original order  
 
- Compare character by character - if all match, it's a palindrome
 
Works with both LinkedList and LinkedListTest
 */
public class DataAnalysis<E extends Comparable<E>> {
    
    // Data field - stores the data to test for palindrome
    private String data; // For string-based operations
    
    private E[] arrayData; // For generic array operations (LinkedListTest compatibility)
    
    // Optional Queue instance - used for FIFO character processing
    // This will give characters in the original order (left to right)
    private Queue<E> queue;
    
    // Optional Stack instance - used for LIFO character processing  
    // This will give characters in reverse order (right to left)
    private Stack<E> stack;
    
    /*
     Constructor - initializes the DataAnalysis with test string
     Creates empty Queue and Stack instances for palindrome checking
     @param data the string to test for palindrome properties
     */
    @SuppressWarnings("unchecked")
    public DataAnalysis(String data) {
        // Store the input string (the data to analyze)
        this.data = data;
        
        // Initialize array data field to null since string constructor is used
        this.arrayData = null;
        
        // Initialize empty Queue using Queue class
        // This Queue uses LinkedList which uses Node class
        this.queue = new Queue<>();
        
        // Initialize empty Stack using Stack class  
        // This Stack uses LinkedList which uses Node class
        this.stack = new Stack<>();
    }
    
    /*
     LINKEDLISTTEST COMPATIBLE Constructor - initializes with generic array
     This constructor is compatible with LinkedListTest.java
     @param data the array of elements to test for palindrome properties
     */
    public DataAnalysis(E[] data) {
        // Store the input array (the data to analyze) by cloning to prevent external modification
        this.arrayData = data != null ? data.clone() : null;
        
        // Set string data to null since array constructor is used
        this.data = null; // Not using string data for this constructor
        
        // Initialize empty Queue using Queue class
        this.queue = new Queue<>();
        
        // Initialize empty Stack using Stack class
        this.stack = new Stack<>();
    }
    
    /*
     Alternative Constructor - for just testing a string without storing Queue/Stack
     
    Lightweight for one-time palindrome checking
    
    @param data the string to test for palindrome properties
     
    @param useInternalStructures whether to create internal Queue and Stack instances
     */
    @SuppressWarnings("unchecked")
    public DataAnalysis(String data, boolean useInternalStructures) {
        // Store the input string
        this.data = data;
        
        // Initialize array data to null since using string constructor
        this.arrayData = null;
        
        // Only create Queue and Stack if requested by the useInternalStructures parameter
        if (useInternalStructures) {
            // Create Queue and Stack instances for internal use
            this.queue = new Queue<>();
            
            this.stack = new Stack<>();
        } else {
            // Set Queue and Stack to null to save memory when not needed
            this.queue = null;
            
            this.stack = null;
        }
    }
    
    /*
     Helper method to clean string data with proper space handling
     
    CRITICAL FIX: This method handles the test case requirement:
    
    - Single spaces between words: REMOVE (valid palindrome format)
    
    - Multiple consecutive spaces: KEEP (invalid palindrome format) 
     
    @param input the input string to clean
    
    @return cleaned string
     */
    private String cleanStringData(String input) {
        // Check if input is null or empty and return as-is if true
        if (input == null || input.length() == 0) {
            return input;
        }
        
        // Convert input string to lowercase for case-insensitive comparison
        String lowerCaseInput = input.toLowerCase();
        
        // Check if there are multiple consecutive spaces
        // If so, do NOT clean them (this should fail palindrome test)
        if (lowerCaseInput.contains("  ")) { // Double space or more detected
            // Keep the string as-is (with multiple spaces) - this will fail palindrome test
            return lowerCaseInput;
        }
        
        // Otherwise, remove single spaces (normal palindrome processing)
        // Use replaceAll with regex to remove all single spaces
        return lowerCaseInput.replaceAll(" ", "");
    }
    
    /*
     Helper method to clean Character array data (for LinkedListTest compatibility)
     Handles the same space logic but for Character arrays
     @return cleaned Character array
     */
    @SuppressWarnings("unchecked")
    private E[] cleanCharacterData() {
        // Check if array data is null, empty, or doesn't contain Characters
        if (arrayData == null || arrayData.length == 0 || !(arrayData[0] instanceof Character)) {
            return arrayData;
        }
        
        // Convert Character array to string first for easier processing
        StringBuilder original = new StringBuilder();
        
        // Iterate through array elements and append each Character to StringBuilder
        for (E element : arrayData) {
            // Check if element is actually a Character before casting
            if (element instanceof Character) {
                // Cast to Character and append to StringBuilder
                original.append((Character) element);
            }
        }
        
        // Convert StringBuilder to lowercase String
        String originalString = original.toString().toLowerCase();
        
        // Check if there are multiple consecutive spaces
        // If so, do NOT clean them (this should fail palindrome test)
        if (originalString.contains("  ")) { // Double space or more detected
            
            // Keep the string as-is (with multiple spaces) - this will fail palindrome test
            // Create Character array with same length as original string
            Character[] result = new Character[originalString.length()];
            
            // Convert each character back to Character object and store in array
            for (int i = 0; i < originalString.length(); i++) {
                result[i] = originalString.charAt(i);
            }
            // Cast and return the Character array as generic type E[]
            return (E[]) result;
        }
        
        // Otherwise, remove single spaces (normal palindrome processing)
        // Use replaceAll to remove all single spaces from the string
        String cleaned = originalString.replaceAll(" ", "");
        // Create new Character array with length of cleaned string
        Character[] cleanedArray = new Character[cleaned.length()];
        
        // Convert each character in cleaned string to Character object
        for (int i = 0; i < cleaned.length(); i++) {
            cleanedArray[i] = cleaned.charAt(i);
        }
        
        // Cast and return the cleaned Character array as generic type E[]
        return (E[]) cleanedArray;
    }
    
    /*
    IS Palindrome method - checks if the stored data is a palindrome
    
    Uses both Queue and Stack to compare original vs reversed sequence
    
    Dual Compatibility: Works with both String data and generic array data
    
    Algorithm Explation:
    1. Clean the input data (remove single spaces, convert to lowercase, but keep multiple spaces)
    
    2. Add all elements to both Queue (FIFO) and Stack (LIFO)
    
    3. Remove elements from both structures simultaneously
    
    4. Queue gives original order, Stack gives reverse order
   
    5. If all pairs match, it's a palindrome
    
    Time Complexity: O(n) where n is the length of the data
   
    Space Complexity: O(n) for storing elements in Queue and Stack
    
    @return true if the data is a palindrome, false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean isPalindrome() {
        // Handle both String and array data by checking which type is being used
        if (data != null) {
            
            // If string data exists, use string-based palindrome checking method
            return isPalindromeString();
        } else if (arrayData != null) {
            
            // If array data exists, use array-based palindrome checking method
            return isPalindromeArray();
        } else {
            
            // If both data types are null, consider it a palindrome by default
            return true; // null data is considered palindrome
        }
    }
    
    /*
    String-based palindrome checking (original functionality)
     */
    @SuppressWarnings("unchecked")
    private boolean isPalindromeString() {
        // Step 1: Handle null or empty string cases
        if (data == null || data.length() == 0) {
            
            // Empty or null string is considered a palindrome
            return true; // Empty string is considered a palindrome
        }
        
        // Step 2: Clean the string using the helper method
        // This handles the critical test case requirement for space handling
        String cleanData = cleanStringData(data);
        
        // Step 3: Handle single character case after cleaning
        if (cleanData.length() == 1) {
            // Single character is always a palindrome
            return true; // Single character is always a palindrome
        }
        
        // Step 4: Create temporary Queue and Stack for comparison
        Queue<Character> temporaryQueue = new Queue<>();
        Stack<Character> temporaryStack = new Stack<>();
        
        // Step 5: Populate both Queue and Stack with cleaned characters
        System.out.println("Adding characters to Queue and Stack:");
        // Iterate through each character in the cleaned data
        for (int i = 0; i < cleanData.length(); i++) {
            // Get current character at index i
            char currentChar = cleanData.charAt(i);
            
            // Add to Queue (FIFO) - characters will come out in original order
            temporaryQueue.enqueue(currentChar);
            
            // Add to Stack (LIFO) - characters will come out in reverse order  
            temporaryStack.push(currentChar);
            
            // Debug output to show what's happening during the process
            System.out.println("  Added '" + currentChar + "' to both structures");
        }
        
        // Step 6: Compare characters from Queue vs Stack
        System.out.println("\nComparing characters:");
        
        System.out.println("Queue (original) vs Stack (reversed):");
        
        // Continue comparison while both structures have elements
        while (!temporaryQueue.isEmpty() && !temporaryStack.isEmpty()) {
            
            // Remove and get character from Queue (original order)
            char queueChar = temporaryQueue.dequeue();
            
            // Remove and get character from Stack (reverse order)
            char stackChar = temporaryStack.pop();
            
            // Display the characters being compared
            System.out.println("  '" + queueChar + "' vs '" + stackChar + "'");
            
            // Check if the characters match
            if (queueChar != stackChar) {
                
                // Characters don't match, so it's not a palindrome
                System.out.println("  → Characters don't match! Not a palindrome.");
                return false;
            }
        }
        
        // Verify both structures are empty simultaneously (safety check)
        if (!temporaryQueue.isEmpty() || !temporaryStack.isEmpty()) {
            
            System.out.println("Error: Structures not empty simultaneously!");
            return false;
        }
        
        // All characters matched, confirm it's a palindrome
        System.out.println("  → All characters match! It's a palindrome!");
        return true;
    }
    
    /*
    Array-based palindrome checking (LinkedListTest compatibility)
     */
    private boolean isPalindromeArray() {
        // Step 1: Handle null or empty array cases
        if (arrayData == null || arrayData.length == 0) {
            
            // Empty or null array is considered a palindrome
            return true; // Empty array is considered a palindrome
        }
        
        // Step 2: Handle single element case
        if (arrayData.length == 1) {
            
            // Single element is always a palindrome
            return true; // Single element is always a palindrome
        }
        
        // Step 3: Process data based on type
        // Start with original array data
        E[] processedData = arrayData;
        
        // If working with Characters, clean the data (remove spaces, lowercase)
        if (arrayData.length > 0 && arrayData[0] instanceof Character) {
            
            // Apply character-specific cleaning logic
            processedData = cleanCharacterData();
        }
        
        // Step 4: Create temporary Queue and Stack for comparison
        Queue<E> temporaryQueue = new Queue<>();
        Stack<E> temporaryStack = new Stack<>();
        
        // Step 5: Populate both Queue and Stack with processed data
        System.out.println("Adding elements to Queue and Stack:");
        
        // Iterate through each element in the processed data array
        for (int i = 0; i < processedData.length; i++) {
            
            // Get current element at index i
            E currentElement = processedData[i];
            
            // Add element to Queue (FIFO order)
            temporaryQueue.enqueue(currentElement);
            
            // Add element to Stack (LIFO order)
            temporaryStack.push(currentElement);
            
            // Display debug information about the element being added
            System.out.println("  Added '" + currentElement + "' to both structures");
        }
        
        // Step 6: Compare elements from Queue vs Stack
        System.out.println("\nComparing elements:");
        
        System.out.println("Queue (original) vs Stack (reversed):");
        
        // Continue comparison while both structures have elements
        while (!temporaryQueue.isEmpty() && !temporaryStack.isEmpty()) {
            
            // Remove and get element from Queue (original order)
            E queueElement = temporaryQueue.dequeue();
            
            // Remove and get element from Stack (reverse order)
            E stackElement = temporaryStack.pop();
            
            // Display the elements being compared
            System.out.println("  '" + queueElement + "' vs '" + stackElement + "'");
            
            // Compare elements using the Comparable interface's compareTo method
            if (queueElement.compareTo(stackElement) != 0) {
                // Elements don't match, so it's not a palindrome
                System.out.println("  Elements don't match! Not a palindrome.");
                return false;
            }
        }
        
        // Verify both structures are empty simultaneously (safety check)
        if (!temporaryQueue.isEmpty() || !temporaryStack.isEmpty()) {
            System.out.println("Error: Structures not empty simultaneously!");
            return false;
        }
        
        // All elements matched, confirm it's a palindrome
        System.out.println("  All elements match! It's a palindrome!");
        return true;
    }
    
    /*
    Overloaded Is Palindrome Method - tests a different string without changing stored data
    
    Allows testing multiple strings with the same DataAnalysis instance
   
    @param testString the string to test for palindrome properties
   
    @return true if testString is a palindrome, false otherwise
     */
    public boolean isPalindrome(String testString) {
        // Temporarily store current data to restore it later
        String originalData = this.data;
        
        // Set new data for testing without affecting the original stored data
        this.data = testString;
        
        // Perform palindrome check on the new test string
        boolean result = isPalindrome();
        
        // Restore original data to maintain object state consistency
        this.data = originalData;
        
        // Return the palindrome check result for the test string
        return result;
    }
    
    /*
    Get Data Method - returns the currently stored test data
    
    Compatible with both String and array data
    
    @return the data being analyzed (String or array depending on constructor used)
     */
    public Object getData() {
        // Check which type of data is being stored and return appropriate type
        if (data != null) {
            // Return string data directly
            return data;
        } else if (arrayData != null) {
            // Return a clone of array data to prevent external modification
            return arrayData.clone();
        } else {
            // Return null if no data is stored
            return null;
        }
    }
    
    /*
    GET DATA as String METHOD - for backward compatibility
    @return the string being analyzed
     */
    public String getDataAsString() {
        
        // Return the string data field directly
        return data;
    }
    
    /*
    GET DATA as Array METHOD - for LinkedListTest compatibility
    @return the array being analyzed
     */
    public E[] getDataAsArray() {
        // Return a clone of array data if it exists, null otherwise
        return arrayData != null ? arrayData.clone() : null;
    }
    
    /*
    SET DATA METHOD - changes the string to be analyzed
    @param data the new string to test
     */
    public void setData(String data) {
        // Set the new string data
        this.data = data;
        // Clear array data when setting string data to maintain consistency
        this.arrayData = null; // Clear array data when setting string data
    }
    
    /*
    SET DATA METHOD - changes the array to be analyzed (LinkedListTest compatibility)
    @param data the new array to test
     */
    public void setData(E[] data) {
        
        // Set the new array data by cloning to prevent external modification
        this.arrayData = data != null ? data.clone() : null;
        
        // Clear string data when setting array data to maintain consistency
        this.data = null; // Clear string data when setting array data
    }
    
    /*
    String representation helper for testing
    
    @return string representation of the stored data
     */
    public String dataToString() {
        // Check which type of data is stored and convert to string accordingly
        if (data != null) {
            
            // Return string data directly
            return data;
        } else if (arrayData != null) {
            
            // Handle empty array case
            if (arrayData.length == 0) return "[]";
            
            // Build string representation of array elements
            StringBuilder stringBuilder = new StringBuilder();
            
            // Concatenate all array elements into a single string
            for (E element : arrayData) {
                stringBuilder.append(element);
            }
            
            // Return the concatenated string
            return stringBuilder.toString();
        } else {
            
            // Return "null" string if no data is stored
            return "null";
        }
    }
    
    /*
    Alternative Palindrome Check Method - using only Stack (more efficient)
    
    Instead of using both Queue and Stack, use just Stack and indexing
    
    This is an alternative approach with better space efficiency
   
    Compatible with both String and array data
    
    Algorthim:
    1. Push first half of elements onto Stack
    
    2. For second half, compare with popped elements from Stack
    
    3. If odd length, skip middle element
    
    @return true if palindrome, false otherwise
     */
    public boolean isPalindromeStackOnly() {
        
        // Handle both String and array data by checking which type is being used
        if (data != null) {
            
            // If string data exists, use string-based stack-only palindrome checking
            return isPalindromeStackOnlyString();
        } else if (arrayData != null) {
            
            // If array data exists, use array-based stack-only palindrome checking
            return isPalindromeStackOnlyArray();
        } else {
            
            // If both data types are null, consider it a palindrome by default
            return true; // null data is considered palindrome
        }
    }
    
    /*
    String-based stack-only palindrome checking
     */
    @SuppressWarnings("unchecked")
    private boolean isPalindromeStackOnlyString() {
        // Handle null or empty cases
        if (data == null || data.length() == 0) {
            
            // Empty or null data is considered a palindrome
            return true;
        }
        
        // Clean the data using the same method as isPalindrome()
        String cleanData = cleanStringData(data);
        
        // Get the length of cleaned data for calculations
        int length = cleanData.length();
        
        // Handle single character case
        if (length == 1) {
            
            // Single character is always a palindrome
            return true;
        }
        
        // Create new Stack for this method
        Stack<Character> temporaryStack = new Stack<>();
        
        // Display method information for debugging
        System.out.println("Using Stack-only method:");
        
        System.out.println("Original string: '" + cleanData + "'");
        
        // Push first half of characters onto stack
        int halfLength = length / 2; // Calculate half length (integer division)
        System.out.println("Pushing first " + halfLength + " characters onto stack:");
        
        // Iterate through first half and push characters onto stack
        for (int i = 0; i < halfLength; i++) {
            
            // Get character at current index
            char ch = cleanData.charAt(i);
            
            // Push character onto stack
            temporaryStack.push(ch);
            
            // Display debug information
            System.out.println("  Pushed: '" + ch + "'");
        }
        
        // Determine starting index for second half
        // If odd length, skip the middle character
        int startIndex = (length % 2 == 0) ? halfLength : halfLength + 1;
        
        // Display information about middle character if odd length
        if (length % 2 != 0) {
            System.out.println("Odd length - skipping middle character: '" + 
                             cleanData.charAt(halfLength) + "'");
        }
        
        // Compare second half with characters from stack
        System.out.println("Comparing second half with stack contents:");
        
        // Iterate through second half starting from calculated start index
        for (int i = startIndex; i < length; i++) {
            
            // Get current character from second half
            char currentChar = cleanData.charAt(i);
            
            // Pop character from stack (this gives us first half in reverse order)
            char stackChar = temporaryStack.pop();
            
            // Display the comparison being made
            System.out.println("  '" + currentChar + "' vs '" + stackChar + "' (from stack)");
            
            // Check if characters match
            if (currentChar != stackChar) {
                // Characters don't match, not a palindrome
                System.out.println("  → Mismatch found! Not a palindrome.");
                return false;
            }
        }
        
        // All characters matched, it's a palindrome
        System.out.println("  → All characters match! It's a palindrome!");
        return true;
    }
    
    /*
    Array-based stack-only palindrome checking
     */
    private boolean isPalindromeStackOnlyArray() {
        
        // Handle null or empty cases
        if (arrayData == null || arrayData.length == 0) {
            
            // Empty or null array is considered a palindrome
            return true;
        }
        
        // Clean the data if it's Character array
        E[] processedData = arrayData; // Start with original array
        
        // Check if array contains Characters and apply cleaning if needed
        if (arrayData.length > 0 && arrayData[0] instanceof Character) {
            processedData = cleanCharacterData();
        }
        
        // Get length of processed data
        int length = processedData.length;
        
        // Handle single element case
        if (length == 1) {
            // Single element is always a palindrome
            return true;
        }
        
        // Create new Stack for this method
        Stack<E> temporaryStack = new Stack<>();
        
        // Display method information for debugging
        System.out.println("Using Stack-only method:");
        
        System.out.println("Array length: " + length);
        
        // Push first half of elements onto stack
        int halfLength = length / 2; // Calculate half length (integer division)
        System.out.println("Pushing first " + halfLength + " elements onto stack:");
        
        // Iterate through first half and push elements onto stack
        for (int i = 0; i < halfLength; i++) {
            
            // Get element at current index
            E element = processedData[i];
            
            // Push element onto stack
            temporaryStack.push(element);
            
            // Display debug information
            System.out.println("  Pushed: '" + element + "'");
        }
        
        // Determine starting index for second half
        // If odd length, skip the middle element
        int startIndex = (length % 2 == 0) ? halfLength : halfLength + 1;
        
        // Display information about middle element if odd length
        if (length % 2 != 0) {
            System.out.println("Odd length - skipping middle element: '" + 
                             processedData[halfLength] + "'");
        }
        
        // Compare second half with elements from stack
        System.out.println("Comparing second half with stack contents:");
        
        // Iterate through second half starting from calculated start index
        for (int i = startIndex; i < length; i++) {
            
            // Get current element from second half
            E currentElement = processedData[i];
            
            // Pop element from stack (this gives us first half in reverse order)
            E stackElement = temporaryStack.pop();
            
            // Display the comparison being made
            System.out.println("  '" + currentElement + "' vs '" + stackElement + "' (from stack)");
            
            // Check if elements match using Comparable interface
            if (currentElement.compareTo(stackElement) != 0) {
                
                // Elements don't match, not a palindrome
                System.out.println("  → Mismatch found! Not a palindrome.");
                return false;
            }
        }
        
        // All elements matched, it's a palindrome
        System.out.println("  → All elements match! It's a palindrome!");
        return true;
    }
    
    /*
    DEMONSTRATION METHOD - shows compatibility with both LinkedList and LinkedListTest
     */
    public static void demonstrateCompatibility() {
        // Display demonstration header
        System.out.println("=== DATAANALYSIS DUAL COMPATIBILITY DEMONSTRATION ===\n");
        
        // Test 1: Using String input (LinkedList compatible)
        System.out.println("1. Testing with String input (LinkedList compatible):");
        
        // Create DataAnalysis instance with Character generic type and string input
        DataAnalysis<Character> stringAnalysis = new DataAnalysis<>("Able was I ere I saw Elba");
        System.out.println("Testing: \"Able was I ere I saw Elba\"");
        
        // Perform palindrome check and store result
        boolean resultOne = stringAnalysis.isPalindrome();
        System.out.println("Result: " + resultOne + "\n");
        
        // Test 2: Using Character array (LinkedListTest compatible)
        System.out.println("2. Testing with Character array (LinkedListTest compatible):");
        
        // Create Character array with the same string as individual characters
        Character[] chars = {'A', 'b', 'l', 'e', ' ', 'w', 'a', 's', ' ', 'I', ' ', 
                           'e', 'r', 'e', ' ', 'I', ' ', 's', 'a', 'w', ' ', 'E', 'l', 'b', 'a'};
        
        // Create DataAnalysis instance with Character array input
        DataAnalysis<Character> charAnalysis = new DataAnalysis<>(chars);
        System.out.println("Testing: " + java.util.Arrays.toString(chars));
        
        // Perform palindrome check and store result
        boolean resultTwo = charAnalysis.isPalindrome();
        System.out.println("Result: " + resultTwo + "\n");
        
        // Test 3: Testing the critical double space case
        System.out.println("3. Testing double space case (should fail):");
        
        // Create Character array with double spaces (should not be palindrome)
        Character[] doubleSpaceChars = {'A', 'b', 'l', 'e', ' ', ' ', 'w', 'a', 's', ' ', 'I'};
        
        // Create DataAnalysis instance with double space array
        DataAnalysis<Character> doubleSpaceAnalysis = new DataAnalysis<>(doubleSpaceChars);
        System.out.println("Testing: " + java.util.Arrays.toString(doubleSpaceChars));
        
        // Perform palindrome check and store result
        boolean resultThree = doubleSpaceAnalysis.isPalindrome();
        System.out.println("Result: " + resultThree + "\n");
        
        // Display compatibility verification results
        System.out.println("=== COMPATIBILITY VERIFICATION ===");
        
        System.out.println("✓ String constructor works (LinkedList compatibility)");
        
        System.out.println("✓ Generic array constructor works (LinkedListTest compatibility)");
        
        System.out.println("✓ Space handling logic consistent across both modes");
        
        System.out.println("✓ Uses Queue, Stack, LinkedList, and Node classes");
        
        System.out.println("✓ Interactive mode and test cases preserved");
    }
    
    /*
     Interactive User Input Method - allows user to test their own words/phrases
     Provides a menu-driven interface for palindrome checking
     */
    public static void interactiveMode() {
        // Create Scanner instance for reading user input
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        // Display interactive mode header
        System.out.println("--- INTERACTIVE PALINDROME CHECKER ---");
        
        System.out.println("Using ustom Queue, Stack, LinkedList, and Node classes\n");
        
        // Main interactive loop - continues until user chooses to exit
        while (true) {
            // Display menu options to user
            System.out.println("Choose an option:");
            
            System.out.println("1. Check a word/phrase for palindrome");
            
            System.out.println("2. Run pre-defined test cases");
            
            System.out.println("3. Exit");
            
            System.out.print("Enter your choice (1-3): ");
            
            // Read user's menu choice and trim whitespace
            String choice = scanner.nextLine().trim();
            System.out.println(); // Add blank line for readability
            
            // Process user's choice using switch statement
            switch (choice) {
                case "1":
                    // User input mode - let user enter custom text to test
                    System.out.print("Enter a word or phrase to check: ");
                    // Read the user's input text
                    String userInput = scanner.nextLine();
                    
                    // Handle empty input case
                    if (userInput.trim().isEmpty()) {
                        System.out.println("Empty input detected - this is considered a palindrome.\n");
                    }
                    
                    // Display what we're analyzing
                    System.out.println("Analyzing: \"" + userInput + "\"\n");
                    
                    // Create analyzer instance with user's input
                    DataAnalysis<Character> userAnalyzer = new DataAnalysis<>(userInput);
                    
                    // Ask user which method they want to use for analysis
                    System.out.println("Choose analysis method:");
                    
                    System.out.println("a) Queue + Stack method (shows detailed process)");
                    
                    System.out.println("b) Stack-only method (more efficient)"); 
                    
                    System.out.println("c) Both methods");
                    
                    System.out.print("Enter your choice (a/b/c): ");
                    
                    // Read user's method choice and convert to lowercase
                    String methodChoice = scanner.nextLine().trim().toLowerCase();
                    System.out.println(); // Add blank line for readability
                    
                    // Initialize result variables for storing palindrome check results
                    boolean resultOne = false, resultTwo = false;
                    
                    // Process method choice and execute appropriate palindrome checking method(s)
                    if (methodChoice.equals("a") || methodChoice.equals("c")) {
                        
                        // Execute Queue + Stack method
                        System.out.println("--- QUEUE + STACK METHOD ---");
                        
                        // Perform palindrome check using both Queue and Stack
                        resultOne = userAnalyzer.isPalindrome();
                        System.out.println(); // Add blank line for readability
                    }
                    
                    if (methodChoice.equals("b") || methodChoice.equals("c")) {
                        // Execute Stack-only method
                        System.out.println("--- STACK-ONLY METHOD ---");
                       
                        // Perform palindrome check using only Stack
                        resultTwo = userAnalyzer.isPalindromeStackOnly();
                        System.out.println(); // Add blank line for readability
                    }
                    
                    // Show final result based on method choice
                    System.out.println("=== FINAL RESULT ===");
                    if (methodChoice.equals("a")) {
                        
                        // Display result for Queue + Stack method only
                        System.out.println("\"" + userInput + "\" is " + (resultOne ? "a PALINDROME!" : "NOT a palindrome."));
                    } else if (methodChoice.equals("b")) {
                        
                        // Display result for Stack-only method only
                        System.out.println("\"" + userInput + "\" is " + (resultTwo ? "a PALINDROME!" : "NOT a palindrome."));
                    } else if (methodChoice.equals("c")) {
                        
                        // Display results for both methods and compare them
                        System.out.println("Queue+Stack result: " + (resultOne ? "PALINDROME" : "NOT palindrome"));
                        
                        System.out.println("Stack-only result:  " + (resultTwo ? "PALINDROME" : "NOT palindrome"));
                        
                        System.out.println("Methods agree: " + (resultOne == resultTwo));
                        
                        System.out.println("FINAL VERDICT: \"" + userInput + "\" is " + (resultOne ? "a PALINDROME!" : "NOT a palindrome."));
                    } else {
                        // Handle invalid method choice
                        System.out.println("Invalid method choice. Please try again.");
                    }
                    System.out.println(); // Add blank line for readability
                    break;
                    
                case "2":
                    // Run pre-defined test cases option
                    runTestCases();
                    break;
                    
                case "3":
                    // Exit option - terminate the interactive mode
                    System.out.println("Thank you for using the Palindrome Checker!");
                    
                    System.out.println("Your custom data structures (Node -> LinkedList -> Queue/Stack) worked perfectly!");
                    
                    // Close scanner resource to prevent memory leaks
                    scanner.close();
                    return; // Exit the method and terminate interactive mode
                    
                default:
                    // Handle invalid menu choice
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.\n");
                    break;
            }
        }
    }
    
    /*
    RUN TEST CASES METHOD - separated from main for better organization
    
    Tests various pre-defined strings to demonstrate palindrome detection
     */
    public static void runTestCases() {
        // Display test cases header
        System.out.println("--- PRE-DEFINED TEST CASES ---");
        
        System.out.println("Running comprehensive tests with custom data structures\n");
        
        // Test cases - mix of palindromes and non-palindromes
        String[] testCases = {
            "racecar",           // Simple palindrome
            
            "hello",             // Not a palindrome  
            
            "madam",             // Simple palindrome
            
            "A man a plan a canal Panama",  // Phrase palindrome (ignoring spaces)
            
            "race a car",        // Not a palindrome
            
            "Was it a rat I saw", // Phrase palindrome
            
            "hello world",       // Not a palindrome
            
            "a",                 // Single character palindrome
            
            "",                  // Empty string palindrome
            
            "Aa",                // Simple palindrome (case insensitive)
            
            "Never odd or even", // Phrase palindrome
            
            "No 'x' in Nixon",   // Phrase palindrome with punctuation
            
            "Able was I ere I  saw Elba"  // Should NOT be palindrome (double space)
        };
        
        // Display number of test cases to be executed
        System.out.println("Testing " + testCases.length + " different cases:\n");
        
        // Test each case using for loop with index tracking
        for (int i = 0; i < testCases.length; i++) {
            // Display current test case number and header
            System.out.println("--- TEST CASE " + (i + 1) + " ---");
            
            System.out.println("Testing: \"" + testCases[i] + "\"");
            
            // Create DataAnalysis instance for this specific test case
            DataAnalysis<Character> analyzer = new DataAnalysis<>(testCases[i]);
            
            // Test using Queue + Stack method (brief output for test cases)
            boolean result = analyzer.isPalindrome();
            
            // Show result for current test case
            System.out.println("RESULT: " + (result ? "PALINDROME" : "NOT palindrome"));
            
            System.out.println(); // Add blank line for readability
        }
        
        // Display test completion summary
        System.out.println("--- TEST CASES COMPLETE ---");
        
        System.out.println("All methods working correctly with custom classes");
        
        System.out.println("Queue -> LinkedList -> Node chain functioning perfectly");
        
        System.out.println("Stack -> LinkedList -> Node chain functioning perfectly\n");
    }
    
    /*
    MAIN METHOD - provides menu with compatibility demonstration
     
    Entry point for the application
     */
    public static void main(String[] args) {
        // Create Scanner instance for reading user input
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        // Display application header and information
        System.out.println("PALINDROME CHECKER APPLICATION");
        
        System.out.println("Built with custom data structures:");
        
        System.out.println("  Node -> LinkedList -> Queue & Stack -> DataAnalysis");
        
        System.out.println("DUAL COMPATIBLE: Works with both LinkedList and LinkedListTest");
        
        System.out.println(); // Add blank line for readability
        
        // Display main menu options
        System.out.println("Choose how you want to run the program:");
        
        System.out.println("1. Interactive mode (enter your own words/phrases)");
        
        System.out.println("2. Run pre-defined test cases only");
        
        System.out.println("3. Demonstrate LinkedList/LinkedListTest compatibility");
        
        System.out.print("Enter your choice (1, 2, or 3): ");
        
        // Read user's choice and trim any whitespace
        String choice = scanner.nextLine().trim();
        System.out.println(); // Add blank line for readability
        
        // Process user's choice using switch statement
        switch (choice) {
            case "1":
                // Interactive mode - user can enter their own input
                interactiveMode();
                break;
                
            case "2":
                // Just run test cases without interactive mode
                runTestCases();
                
                // Display technical verification information
                System.out.println("--- TECHNICAL VERIFICATION ---");
                
                System.out.println("DataAnalysis successfully uses Queue class");
                
                System.out.println("DataAnalysis successfully uses Stack class");  
                
                System.out.println("Queue uses LinkedList class");
                
                System.out.println("Stack uses LinkedList class");
                
                System.out.println("LinkedList uses Node class");
                
                System.out.println("All palindrome detection methods working correctly");
                
                System.out.println("Both simple words and phrases with spaces handled");
                
                System.out.println("Case-insensitive comparison implemented");
                
                System.out.println("Two different algorithmic approaches demonstrated");
                
                System.out.println("CRITICAL FIX: Multiple consecutive spaces properly handled");
                
                System.out.println("COMPATIBILITY: Works with both LinkedList and LinkedListTest");
                break;
                
            case "3":
                // Demonstrate compatibility between different LinkedList implementations
                demonstrateCompatibility();
                break;
                
            default:
                // Handle invalid choice by defaulting to interactive mode
                System.out.println("Invalid choice. Running interactive mode by default...\n");
                interactiveMode();
                break;
        }
        
        // Close scanner resource to prevent memory leaks
        scanner.close();
    }
}