package token;

/**
 * Each Token object will be an object stored in the array created by the
 * lexer.
 * Each Token object hold the type of the token as well as a string of the
 * value
 */
public class Token {
  // Type of the token, defined in TokenType
  private final TokenType type;

  // The litteral values of the Token
  private final String literal;

  /**
   * Constructor to create a new Token instance.
   *
   * @param type    The TokenType of the new token.
   * @param literal The raw string value that forms the token.
   */
  public Token(TokenType type, String literal) {
    this.type = type;
    this.literal = literal;
  }

  // Getters

  public TokenType getType() {
    return type;
  }

  public String getLiteral() {
    return literal;
  }

  /**
   * Override toString to print values
   */
  @Override
  public String toString() {
    return "Token [type=" + type + ", literal='" + literal + "']";
  }
}
