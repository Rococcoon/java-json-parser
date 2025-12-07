package token;

/**
 * a class that represents a JSON number literal
 * inherits from the AbstractToken class
 */
public class TokenNumber extends AbstractToken {

  /**
   * Constructor for a TokenNumber.
   * 
   * @param literal Take the litteral value from the input String for the number
   */
  public TokenNumber(String literal) {
    super(TokenType.NUMBER, literal);
  }

  /**
   * Class specific implementation of the evaluate method signature.
   * allows us to return a proper Java object based on the value and tokentype
   * of the class.
   *
   * * @return The number represented as a Double object.
   */
  @Override
  public Object evaluate() {
    try {
      // get the literal from the class and parse it to a double
      return Double.parseDouble(getLiteral());
    } catch (NumberFormatException e) {
      // catch and return any errors.
      System.err.println("Error during Number evaluation: Invalid number format in literal '" + getLiteral() + "'");
      return null;
    }
  }
}
