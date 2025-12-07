package token;

/**
 * Abstract Class for Token objects
 * defines what all token objects will require
 */
public abstract class AbstractToken implements Evaluable {

  private final TokenType type; // The type of token, based on enum TokenType
  private final String literal; // The litteral value of the Token

  /**
   * Constructor for the Abstract Token.
   * 
   * @param type    The TokenType enum value.
   * @param literal The litteral string value.
   */
  public AbstractToken(TokenType type, String literal) {
    this.type = type;
    this.literal = literal;
  }

  // Getter methods

  public TokenType getType() {
    return type;
  }

  public String getLiteral() {
    return literal;
  }

  /**
   * Method uses the Evaluabble interface to return concrete Java values that
   * can be used in programs.
   *
   * * @return The final Java object based on the class type
   */
  @Override
  public abstract Object evaluate();

  @Override
  public String toString() {
    return "Token [type=" + type + ", literal='" + literal + "']";
  }
}
