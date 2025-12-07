// File: lexer/Lexer.java

package lexer;

import token.AbstractToken;
import token.TokenStructural;
import token.TokenNumber;
import token.TokenString;
import token.TokenValue;
import token.TokenSpecial;
import token.TokenType;

/**
 * The Lexer class takes a String for input, walks the string and
 * creates a Token object to be passed and read by the parser.
 */
public class Lexer {
  private final String input; // The input String of JSON
  private int position; // Current position in input

  /**
   * Constructor for the Lexer
   * Takes a String of JSON as input
   * * @param input; The JSON data as a string
   */
  public Lexer(String input) {
    this.input = input;
    this.position = 0;
  }

  /**
   * Tokenizes the input String
   * Returns a List(dynamic, unlike arrays) of AbstractToken Objects
   * (Polymorphism)
   * * @return List<AbstractToken>; A list of Token objects
   */
  public java.util.List<AbstractToken> tokenizeInput() {
    // initialize the List of Tokens to return
    java.util.List<AbstractToken> tokenList = new java.util.ArrayList<>();

    while (position < input.length()) {
      // Get the charact at the current position of the input String
      char charAtPosition = input.charAt(position);
      // Initialize the Token to append to the List
      AbstractToken token = null; // Changed from Token to AbstractToken

      /*
       * Main process for the tokenizeInput function
       * Checks the current char and creates a token to be later appended to
       * the tokenList
       */
      switch (charAtPosition) {
        // Structural Tokens - Use TokenStructural
        case '{':
          token = new TokenStructural(TokenType.LEFT_BRACE, String.valueOf(charAtPosition));
          break;
        case '}':
          token = new TokenStructural(TokenType.RIGHT_BRACE, String.valueOf(charAtPosition));
          break;
        case '[':
          token = new TokenStructural(TokenType.LEFT_BRACKET, String.valueOf(charAtPosition));
          break;
        case ']':
          token = new TokenStructural(TokenType.RIGHT_BRACKET, String.valueOf(charAtPosition));
          break;
        case ':':
          token = new TokenStructural(TokenType.COLON, String.valueOf(charAtPosition));
          break;
        case ',':
          token = new TokenStructural(TokenType.COMMA, String.valueOf(charAtPosition));
          break;

        // Skip all whitespaces
        case ' ':
        case '\n':
        case '\t':
        case '\r':
          position++;
          // Skips the appending step at the end of the loop
          // skips the final position++
          continue;

        // Keyword Tokens - Use TokenValue
        case 't':
          token = handleTrue();
          break;
        case 'f':
          token = handleFalse();
          break;
        case 'n':
          token = handleNull();
          break;

        // String Literal - Use TokenString
        case '"':
          token = handleString();
          break;

        // Number Literal - Use TokenNumber
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        case '-':
          token = handleNumber();
          // The handler already decrements position--, so we don't need to do that here
          break;

        default:
          // Throw LexerException
          throw new LexerException("Unexpected character encountered.",
              position,
              input.substring(position));
      }

      // Append token to the list
      if (token != null) {
        tokenList.add(token);
      }

      // advances the position
      // This single, unconditional position++ completes the advancement for all
      // tokens.
      position++;
    }

    // When loop finishes, append EOF token then return tokenList
    tokenList.add(new TokenSpecial(TokenType.EOF, "EOF")); // Use TokenSpecial for EOF
    return tokenList;
  }

  // handle keywords

  private AbstractToken handleTrue() {
    // "true" must be great that 4 chars. Throw Illegal token if otherwise
    if (position + 3 >= input.length()) {
      // Throw exception if keyword is incomplete
      throw new LexerException("Incomplete keyword 'true'.",
          position,
          input.substring(position));
    }

    // checks if input string contains 'true' at the current position + 3
    if (input.startsWith("true", position)) {
      // advance the possition passed the Keyword 'true' and return the token
      position += 3;
      return new TokenValue(TokenType.TRUE, "true"); // Use TokenValue
    }

    // Throw exception if characters don't match 'true'
    throw new LexerException("Found 't' but not followed by 'rue'.",
        position,
        input.substring(position));
  }

  // returns a TokenValue object(inherits from AbstractToken)
  private AbstractToken handleFalse() {
    // "false" must be greater than 5 chars
    if (position + 4 >= input.length()) {
      // Throw exception if keyword is incomplete
      throw new LexerException("Incomplete keyword 'false'.",
          position,
          input.substring(position));
    }

    // checks if the input string contains 'false' at the current position
    if (input.startsWith("false", position)) {
      // Advance position past "false" and return the token.
      position += 4;
      return new TokenValue(TokenType.FALSE, "false");
    }

    // Throw exception if characters don't match 'false'
    throw new LexerException("Found 'f' but not followed by 'alse'.",
        position,
        input.substring(position));
  }

  private AbstractToken handleNull() {
    // "null" must be 4 chars in length, check that input is longer, throw illegal
    // otherwise
    if (position + 3 >= input.length()) {
      // Throw exception if keyword is incomplete
      throw new LexerException("Incomplete keyword 'null'.",
          position,
          input.substring(position));
    }

    // Checks the input contains "null" at the current position
    if (input.startsWith("null", position)) {
      // advanced past "null" and return null token
      position += 3;
      return new TokenValue(TokenType.NULL, "null"); // Use TokenValue object
    }

    // Throw exception if characters don't match 'null'
    throw new LexerException("Found 'n' but not followed by 'ull'.",
        position,
        input.substring(position));
  }

  // handle Litterals

  private AbstractToken handleString() {
    // advance past '"'
    position++;

    // get the end '"' to know where the string ends
    int endPosition = input.indexOf('"', position);

    // if endPosition is -1, string does not end and JSON is illegal/malformatted
    if (endPosition == -1) {
      // Throw exception if string is unterminated
      throw new LexerException("Unterminated string literal.",
          position - 1,
          input.substring(position - 1));
    }

    // Get the substring, between the position and endPosition in the input
    // String
    String literal = input.substring(position, endPosition);

    // advance position to the end position
    // This is the correct way to handle a full token advance with the final
    // position++
    position = endPosition;

    // return the token with the String litteral
    return new TokenString(literal); // Use TokenString
  }

  private AbstractToken handleNumber() {
    // get the start position for the Number
    int startPosition = position;

    // advance position is start is a '-' for negative numbers
    if (input.charAt(position) == '-') {
      position++;
    }

    // advance position past while it is a number/digit
    while (position < input.length() && Character.isDigit(input.charAt(position))) {
      position++;
    }

    // handle a decimal for fractional numbers
    if (position < input.length() && input.charAt(position) == '.') {
      position++;

      // advance position past decimal place
      while (position < input.length() && Character.isDigit(input.charAt(position))) {
        position++;
      }
    }

    // Handle exponent part (e.g., 1e-10)
    if (position < input.length() && (input.charAt(position) == 'e' || input.charAt(position) == 'E')) {
      position++; // Go past 'e' or 'E'

      // Handle optional sign for exponent
      if (position < input.length() && (input.charAt(position) == '+' || input.charAt(position) == '-')) {
        position++;
      }

      // Require digits after the sign/e
      while (position < input.length() && Character.isDigit(input.charAt(position))) {
        position++;
      }
    }

    // create the string litteral of the number
    String literal = input.substring(startPosition, position);

    // If the number literal is just "-" or ends with ".", it's illegal JSON.
    if (literal.equals("-") || literal.endsWith(".")) {
      throw new LexerException("Malformed number literal.", startPosition, literal);
    }

    // decrement by 1 so the main loop increment puts it in the right place.
    position--;

    return new TokenNumber(literal); // Use TokenNumber
  }
}
