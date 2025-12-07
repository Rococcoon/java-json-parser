package token;

/**
 * An enum that defines the different types of tokens.
 * Each token will use this to convey the data iterated over by the lexer
 */
public enum TokenType {
  // Special Tokens
  ILLEGAL, // Unknow or invalid character
  EOF, // End Of File

  // Literals
  STRING,
  NUMBER,

  // Structure
  LEFT_BRACE, // '{'
  RIGHT_BRACE, // '}'
  LEFT_BRACKET, // '['
  RIGHT_BRACKET, // ']'
  COMMA, // ','
  COLON, // ':'

  // Values
  TRUE,
  FALSE,
  NULL
}
