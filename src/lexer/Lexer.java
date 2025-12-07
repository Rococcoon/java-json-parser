package lexer;

import token.Token;
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
   * Returns a List(dynamic, unlike arrays) of Token Objects
   * * @return List<Token>; A list of Token objects
   */
  public java.util.List<Token> tokenizeInput() {
    // initialize the List of Tokens to return
    java.util.List<Token> tokenList = new java.util.ArrayList<>();

    while (position < input.length()) {
      // Get the charact at the current position of the input String
      char charAtPosition = input.charAt(position);
      // Initialize the Token to append to the List
      Token token = null;

      /*
       * Main process for the tokenizeInput function
       * Checks the current char and creates a token to be later appended to
       * the tokenList
       */
      switch (charAtPosition) {
        case '{':
          token = new Token(TokenType.LEFT_BRACE, String.valueOf(charAtPosition));
          break;
        case '}':
          token = new Token(TokenType.RIGHT_BRACE, String.valueOf(charAtPosition));
          break;
        case '[':
          token = new Token(TokenType.LEFT_BRACKET, String.valueOf(charAtPosition));
          break;
        case ']':
          token = new Token(TokenType.RIGHT_BRACKET, String.valueOf(charAtPosition));
          break;
        case ':':
          token = new Token(TokenType.COLON, String.valueOf(charAtPosition));
          break;
        case ',':
          token = new Token(TokenType.COMMA, String.valueOf(charAtPosition));
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

        case 't':
          token = handleTrue();
          break;
        case 'f':
          token = handleFalse();
          break;
        case 'n':
          token = handleNull();
          break;

        case '"':
          token = handleString();
          break;

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
          // NOTE: We REMOVE the position-- here. The helper will handle it.
          break;

        default:
          token = handleDefault(charAtPosition);
          break;
      }

      // Append token to the list
      if (token != null) {
        tokenList.add(token);
      }

      // advances the position
      // FIX: This single, unconditional position++ is required now.
      // The logic shifts responsibility back to the individual handlers to ensure
      // the pointer is pointing to the character BEFORE the next token's start.
      position++;
    }

    // When loop finishes, append EOF token then return tokenList
    tokenList.add(new Token(TokenType.EOF, "EOF"));
    return tokenList;
  }

  // handle keywords

  private Token handleTrue() {
    // "true" must be great that 4 chars. Return Illegal token if otherwise
    if (position + 3 >= input.length()) {
      return new Token(TokenType.ILLEGAL, input.substring(position));
    }

    // checks if input string contains 'true' at the current position + 3
    if (input.startsWith("true", position)) {
      // advance the possition passed the Keyword 'true' and return the token
      // The main loop's position++ will advance past the last character 'e'
      position += 3;
      return new Token(TokenType.TRUE, "true");
    }

    // Return Illegal if these are not met
    return new Token(TokenType.ILLEGAL, String.valueOf(input.charAt(position)));
  }

  private Token handleFalse() {
    // "false" must be greater than 5 chars
    if (position + 4 >= input.length()) {
      return new Token(TokenType.ILLEGAL, input.substring(position));
    }

    // checks if the input string contains 'false' at the current position
    if (input.startsWith("false", position)) {
      // Advance position past "false" and return the token.
      // The main loop's position++ will advance past the last character 'e'
      position += 4;
      return new Token(TokenType.FALSE, "false");
    }

    // return Illegal token if conditions are not met
    return new Token(TokenType.ILLEGAL, String.valueOf(input.charAt(position)));
  }

  private Token handleNull() {
    // "null" must be 4 chars in length, check that input is longer, return illegal
    // otherwise
    if (position + 3 >= input.length()) {
      return new Token(TokenType.ILLEGAL, input.substring(position));
    }

    // Checks the input contains "null" at the current position
    if (input.startsWith("null", position)) {
      // advanced past "null" and return null token
      // The main loop's position++ will advance past the last character 'l'
      position += 3;
      return new Token(TokenType.NULL, "null");
    }

    // return Illegal token if conditions are not met
    return new Token(TokenType.ILLEGAL, String.valueOf(input.charAt(position)));
  }

  // handle Litterals

  private Token handleString() {
    // advance past '"'
    position++;

    // get the end '"' to know where the string ends
    int endPosition = input.indexOf('"', position);

    // if endPosition is -1, string does not end and JSON is illegal/malformatted
    if (endPosition == -1) {
      return new Token(TokenType.ILLEGAL, input.substring(position - 1));
    }

    // Get the substring, between the position and endPosition in the input
    // String
    String literal = input.substring(position, endPosition);

    // advance position to the end position
    // This is the correct way to handle a full token advance with the final
    // position++
    position = endPosition;

    // return the token with the String litteral
    return new Token(TokenType.STRING, literal);
  }

  private Token handleNumber() {
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

    // decrement by 1 so the main loop increment puts it in the right place.
    position--;

    return new Token(TokenType.NUMBER, literal);
  }

  /*
   * called when all legal cases are not met
   * returns an Illegal token
   */
  private Token handleDefault(char charAtPosition) {
    return new Token(TokenType.ILLEGAL, String.valueOf(charAtPosition));
  }
}
