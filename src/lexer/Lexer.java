package lexer;

import token.Token;
import token.TokenType;

/*
 * Public Class Lexer
 * Takes a sting or byte as input
 * converts string of bytes into an array of Tokens
 */
public class Lexer {
  private String input; // the input array of bytes(Json)
  private int position; // Tracks the possition in the input string
  private int readPosition; // tracks the current byte being read, 1 position ahead of position
  private char ch; // The current char being read from the input String

  /*
   * Lexer Constructor
   *
   * @prama input; the Json String
   */
  public Lexer(String input) {
    this.input = input;
    this.position = 0;
    this.readPosition = 0;

    // Initiate the lexing of the input String
    readChar();
  }

  // Read and Generate Tokens

  private void readChar() {
  }

  private Token nextToken() {
  }

  // Helper functions for reading Token Types

  private String readString() {
  }

  private String readNumber() {
  }

  private String readNumber() {
  }

  private String readKeyWord() {
  }

  private String readKeyWord() {
  }

  // Helper functions to validate Strings and Numbers

  private boolean isLetter(char c) {
  }

  private boolean isDigit(char c) {
  }

}
