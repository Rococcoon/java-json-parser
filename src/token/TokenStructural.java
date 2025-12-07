package token;

/**
 * a class that represents JSON structural characters
 * [, ], {, }, ; ','
 * inherits from the AbstractToken class
 */
public class TokenStructural extends AbstractToken {

  /**
   * Constructor for TokenStructural
   * 
   * @param type    The structural TokenType
   * @param literal The raw character strin
   */
  public TokenStructural(TokenType type, String literal) {
    // Call to the AbstractToken constructor
    super(type, literal);
  }

  /**
   * Class specific implementation of the evaluate method signature.
   * This token type has no real value/data so we return null
   *
   * @return null
   */
  @Override
  public Object evaluate() {
    return null;
  }
}
