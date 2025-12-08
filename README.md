# Java JSON Lexer

## Project Overview

This project is a JSON Lexer. The goal is to complete the first stage of parsing
JSON. In this stage the program takes an array of bytes, and using JSON 
specific language requirements lexes the array into an array of tokens, each
containing information about each character. This is an important step in the
parsing process, as the parser will then use these tokens to construct an
abstract syntax tree.

### Core Functionality

The core functionality of the program is the Lexer class. Here the JSON string
gets passed to the Lexer class as input. The Lexer class then walks the input
and creates a List of token objects. The token objects contain important 
information about the JSON string such as if the char is a language structural
piece, a value such as true, false or null, or a data type such as a string
of number.

### Output

The main output is a Java List of AbstractToken objects.

---

## Design and Object-Oriented Principle

This project was intentionally structured to showcase fundamental Java OOP concepts: 

### Aggregation

Aggregation represents a "has-a" relationship where the parts can exist 
independently of the whole. In this project, the Lexer is the aggregator 
that collects and returns a List<AbstractToken>. The individual 
tokens are the parts that are created independently within the Lexer. 
The Java List of AbstractTokens is a grouping mechanism, it is not owned 
exclusively to the Lexer that creates them.

### Abstract Class

The AbstractToken.java file serves as the blueprint for all tokens in the 
program. It defines the common structure, including fields like type and 
literal. It also declares the abstract evaluate() method that all 
concrete tokens must implement.

### Inheritance

Inheritance is used to create specific token classes. First we create the 
blueprint for the token classes `AbsractToken`. Next we create specific token
classes that inherit from this class. These Token classes represent the 
different language rules each char in the JSON string can represent.
We have a variety of different AbstractToken subclasses:
* TokenStructural: For different structural parts of the language such as
  square brackets, curly braces, commas, semi-colons.
* TokenNumber: For storing numbers.
* TokenString: For storing strings.
* TokenSpecial: For special tokens, in our case EOF(End of File).
* TokenValue: For different JSON values such as true, false or null.

### Polymorphism

Polymorphism is centered around the evaluate() method. Although 
all concrete tokens implement this method, the code that runs depends on the 
specific token type. This allows a class like the Main class (and the future 
Interpreter) to call token.evaluate() without needing to know if the token is a 
TokenNumber or a TokenString, ensuring the correct conversion logic is executed 
dynamically.

### Interface

The Evaluable.java interface defines the contract for the evaluate() 
behavior. By having AbstractToken implement this interface, all 
derived tokens formally adhere to the interpretation contract, regardless of 
their implementation details.

### Error Handling

Error Handling is implemented by having the Lexer throw a custom LexerException 
upon encountering invalid JSON syntax. This is implemented in Lexer.java. 
LexerException.java terminates the tokenization process immediately and reports an
error, rather than producing a illegal tokens.

---

## How to Use the Lexer

### 1. Initialization

* Initialize the Lexer class and pass a String of JSON as an argument.
```java
Lexer lexer = new Lexer(input);
```
* Initialize a Java List of AbstractToken type.
```java
java.util.List<AbstractToken> tokens;
```

### 2. Assignment

* Assign the Java list with Tokens by calling tokenizeInput() method of the 
  Lexer object.
```java
tokens = lexer.tokenizeInput();
```

### 3. Utilize

* You now have access to the Values of the Java List of AbstractToken objects.
* Access them with the evaluate method on each AbstractToken.
```java
AbstractToken token = tokens.get(0);
Object evaluatedValue = token.evaluate();
```
* You can now use the value of the Token.

---

### UML Diagram

{{< mermaid >}}
classDiagram
    direction LR

    %% Utility Classes
    class LexerException
    class TokenType

    %% Interface
    class Evaluable {
        + evaluate()
    }

    %% Main Components (As previously working)
    class Lexer{
        - String input
        - int position
        + tokenizeInput()
    }
    class AbstractToken{
        # String literal
        # TokenType type
        + evaluate()
        + getType()
        + getLiteral()
        + toString()
    }
    class TokenStructural
    class TokenString
    class TokenNumber
    class TokenValue
    class TokenSpecial

    %% 1. Inheritance (Subclass <|-- Superclass)
    TokenStructural <|-- AbstractToken
    TokenString <|-- AbstractToken
    TokenNumber <|-- AbstractToken
    TokenValue <|-- AbstractToken
    TokenSpecial <|-- AbstractToken

    %% 2. Interface (Token -> Interface)
    AbstractToken ..|> Evaluable : implements

    %% 3. Composition (AbstractToken requires TokenType)
    AbstractToken "1" *-- "1" TokenType : has

    %% 4. Dependency (Lexer -> LexerException)
    Lexer ..> LexerException : throws

{{< /mermaid >}}
