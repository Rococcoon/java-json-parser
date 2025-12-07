package token;

/**
 * Represents JSON key words, true, false and null
 *
 * Inherits from AbstractToken.
 */
public class TokenValue extends AbstractToken {

  /**
   * Constructor for TokenValue
   * 
   * @param type    The token type, from enum TokenType
   * @param literal The raw keyword string
   */
  public TokenValue(TokenType type, String literal) {
    // Calls the AbstractToken constructor
    super(type, literal);
  }

  /**
   * Class specific implementation of the evaluate method signature.
   * Will return a useable Java object, based on the TokenType fo the object
   * 
   * @return A Java Boolean object (true/false) or null.
   */
  @Override
  public Object evaluate() {
    switch (getType()) {
      case TRUE:
        return Boolean.TRUE;
      case FALSE:
        return Boolean.FALSE;
      case NULL:
        return null;
      default:
        return null;
    }
  }
}
