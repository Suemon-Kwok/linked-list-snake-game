# Linked List Snake Game

A unique Snake game implementation that uses a **recursive LinkedList data structure** to manage the snake's body. Built as part of a Data Structures and Algorithms course assignment.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=flat&logo=apache-netbeans-ide&logoColor=white)

## 🎮 Game Features

### Unique Mechanics
- **Letter Collection**: Eat green letters (A-Z) to grow your snake
- **Alphabetical Ordering**: Letters are automatically sorted in alphabetical order in the snake's body
- **Number Obstacles**: Hit magenta numbers (0-9) to drop letters from your snake
- **Self-Collision Detection**: Game ends if the snake eats its own tail
- **Dynamic Object Generation**: New letters and numbers spawn after collection/collision

### Example Gameplay
```
Snake: @ABD
After eating 'C' → Snake: @ABCD

Snake: @ABCDEFG
After hitting '3' → Snake: @ABDEFG (drops 'C' at position 3)
After hitting '9' → Snake: @ABDEF (drops last letter 'G')
```

## 🛠️ Technical Implementation

### Data Structures Used
- **Custom Recursive LinkedList**: All LinkedList operations implemented using recursion (NO loops!)
- **Node-based Architecture**: Generic Node class supporting any Comparable type
- **Trail System**: ArrayList tracking snake positions for smooth body rendering

### Key Classes

| Class | Description |
|-------|-------------|
| `SnakeGame.java` | Main entry point, creates game window |
| `Panel.java` | Game loop, rendering, collision detection |
| `Snake.java` | Snake entity using LinkedList for body management |
| `LinkedList.java` | Fully recursive linked list implementation |
| `Node.java` | Generic node with data and next pointer |
| `GameObject.java` | Represents letters and numbers on the board |

### Recursive Methods in LinkedList
All core operations are implemented recursively:
- `addRecursive()` - Add to end of list
- `addInOrderRecursive()` - Insert in sorted order
- `removeNodeRecursive()` - Remove by value
- `removeAtIndexRecursive()` - Remove by index
- `getNodeRecursive()` - Retrieve by index
- `containsRecursive()` - Search for element
- `printRecursive()` - Display all elements

## 🎯 How to Play

### Controls
- **WASD** or **Arrow Keys**: Move the snake
- **Spacebar**: Print snake status to console (debug)
- **R**: Restart game after game over

### Objective
- Collect as many letters as possible to grow your snake
- Avoid hitting the boundaries
- Avoid eating your own tail
- Strategic number collision to drop unwanted letters

### Game Objects
- 🔵 **Blue Circle (@)**: Snake head
- 🟢 **Green Circles**: Letters (A-Z) - collect to grow
- 🟣 **Magenta Squares**: Numbers (0-9) - hit to drop letters

## 🚀 Running the Game

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- NetBeans IDE (recommended) or any Java IDE

### Option 1: Run from JAR (Easiest)
```bash
java -jar Assignment_1_DSA_Suemon_Kwok_14883335.jar
```

### Option 2: Run from Source
1. Clone the repository:
```bash
git clone https://github.com/Suemon-Kwok/linked-list-snake-game.git
cd linked-list-snake-game
```

2. Open in NetBeans:
   - File → Open Project
   - Navigate to project folder
   - Right-click project → Run

3. Or compile manually:
```bash
cd src/Question_2
javac *.java
java Question_2.SnakeGame
```

## 📁 Project Structure

```
Question_2/
├── SnakeGame.java      # Main entry point
├── Panel.java          # Game loop and rendering (600+ lines)
├── Snake.java          # Snake entity with LinkedList body
├── LinkedList.java     # Recursive LinkedList implementation
├── Node.java           # Generic node class
└── GameObject.java     # Letter/Number objects
```

## 🔍 Code Highlights

### Recursive LinkedList Add In Order
```java
private Node<E> addInOrderRecursive(Node<E> CurrentNode, Node<E> NodeToInsert) {
    if (CurrentNode == null || NodeToInsert.Data.compareTo(CurrentNode.Data) <= 0) {
        NodeToInsert.Next = CurrentNode;
        return NodeToInsert;
    }
    CurrentNode.Next = addInOrderRecursive(CurrentNode.Next, NodeToInsert);
    return CurrentNode;
}
```

### Self-Collision Detection
```java
public boolean checkSelfCollision(int collisionRadius, ArrayList<int[]> snakeTrail) {
    if (Body.getSize() == 0 || snakeTrail.size() < 2) return false;
    int[] headPos = snakeTrail.get(0);
    
    for (int i = Math.max(2, (25 / 20)); i < Math.min(snakeTrail.size(), Body.getSize() + 1); i++) {
        int[] trailPos = snakeTrail.get(i);
        int dx = Math.abs(headPos[0] - trailPos[0]);
        int dy = Math.abs(headPos[1] - trailPos[1]);
        
        if (dx < collisionRadius && dy < collisionRadius) {
            return true;
        }
    }
    return false;
}
```

## 📊 Game Statistics

The game tracks:
- **Letters Eaten**: Total letters collected
- **Numbers Hit**: Total numbers collided with
- **Current Snake**: Visual representation (@ABCD...)
- **Length**: Total snake length (head + letters)

## 🐛 Known Issues

None currently! The game runs smoothly with:
- Proper boundary collision detection
- Accurate self-collision detection
- Smooth snake body rendering
- No object spawn conflicts

## 🤝 Contributing

Feel free to:
- Open issues for bugs
- Suggest improvements
- Fork and experiment with the code

## 📝 License




## 📧 Contact

Suemon Kwok
 
Repository: [github.com/Suemon-Kwok/linked-list-snake-game](https://github.com/Suemon-Kwok/linked-list-snake-game)

This project is based off these requirements

This question is for you to challenge yourself. For getting overall “A+” grade, you need to complete
this question and other assessments.
I have provided a package with Three classes of question 2. Feel free to use them or change them if
you have a better design. You must use the LinkedList from question 1 to build a snake game or you
create an ArrayList.
Your program must meet following requirements:
 Randomly generate 10 numbers. The range of the number is from 0 to 9 inclusive. (1%)
 Randomly generate a letter. (1%)
 Randomly set locations of these 10 numbers and the letter. (2%)
 Snake’s length is increased by 1 when it eats a letter. (3%)
 A new letter generated and allocated randomly after eating a letter. (1%)
 A new number generated and allocated randomly after hitting a number. (1%)
 Snake’s body shows the letters it has eaten. These letters need to be stored in a linked list in
alphabet order. (3%)
Example (This is an example of how it works, not actual program):
Snake: @ABD
After eating C
Snake: @ABCD
 Snake drops a letter of its body when it hits a number. (3%)
 The number’s value determines which node of the linked list needs to be dropped. If the
value is greater than the snake’s length, the last node will be dropped. (4%)
Example (This is an example of how it works, not actual program):
Snake: @ABCDEFFG
After hitting 3
Snake: @ABDEFFG
After hitting 1
Snake: @BDEFFG
After hitting 9
Snake: @BDEFF
 Design YOUR OWN GUI (1%)
You may have a look the console-based prototype. 

