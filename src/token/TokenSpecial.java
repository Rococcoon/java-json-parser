package token;

/**
 * Token class that represents non-data, non-structure tokesn
 * these are the EOF(end of file) and Illegal token(for illegal characters)
 * Inherits from AbstractToken.
 */
public class TokenSpecial extends AbstractToken {

  /**
   * Constructor for TokenSpecial
   * 
   * @param type    The specific special TokenType
   * @param literal The string litteral ("EOF" for end of file) and ASCII null '0'
   */
  public TokenSpecial(TokenType type, String literal) {
    // Calls the AbstractToken constructor
    super(type, literal);
  }

  /**
   * Implements the evaluate method
   * Special tokens represent control flow or errors and do not convert to
   * usable data.
   * 
   * @return null.
   */
  @Override
  public Object evaluate() {
    return null;
  }
}
