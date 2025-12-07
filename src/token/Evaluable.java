package token;

/**
 * Interface
 * allows us to get the value of the Token and create a usable java value
 * each different token class will implement this bassed on their type
 */
public interface Evaluable {
  /**
   * method signature for classes evaluate
   * 
   * @return The canonical Java object form of the token's value.
   */
  Object evaluate();
}
