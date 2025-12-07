import lexer.Lexer;
import lexer.LexerException;
import token.AbstractToken;

public class Main {
  public static void main(String[] args) {
    // Test case for properly formatted json
    String json1 = "{\"name\": \"Lukas\", \"id\": 1776, \"active\": true, \"data\": null}";

    // Test string for improperly formatted JSON
    // '~' tilda is invalid
    String json2 = "{\"key\": ~value}";

    // Test string for improperly formatted JSON,
    // Error is 'nul', and no closing bracket
    String json3 = "{ \"valid\": false, \"bad\": nul";

    // Test 01
    System.out.println("\n" + "-".repeat(60) + "\n");
    System.out.println("TEST 01: Properly Formatted JSON");
    testLexer(json1);

    // Test 02
    System.out.println("\n" + "-".repeat(60) + "\n");
    System.out.println("TEST 02: Improperly Formatted JSON");
    testLexer(json2);

    // Test 03
    System.out.println("\n" + "-".repeat(60) + "\n");
    System.out.println("TEST 03: Improperly Formatted JSON");
    testLexer(json3);
  }

  /**
   * Method to test the lexer
   * 
   * @param input The JSON string to tokenize.
   */
  private static void testLexer(String input) {
    // print out the input string being tested
    System.out.println("Input: " + input);

    try {
      Lexer lexer = new Lexer(input);
      java.util.List<AbstractToken> tokens = lexer.tokenizeInput();

      // loop over each token in the List<AbstractToken>
      for (int i = 0; i < tokens.size(); i++) {
        // Get the current Token at index
        AbstractToken token = tokens.get(i);

        // Get a Java object using the Evaluate interface for a usable value
        Object evaluatedValue = token.evaluate();

        // Get the type of the evaluated object value
        String evaluatedType = (evaluatedValue != null) ? evaluatedValue.getClass().getSimpleName() : "null";

        System.out.printf("[%2d] %-15s | Literal: '%-10s' | Value: %s | Type: %s\n",
            i,
            token.getType(),
            token.getLiteral(),
            evaluatedValue,
            evaluatedType);
      }
    } catch (LexerException e) {
      System.out.println("\n Lexing Failed (LexerException Caught):");
      System.err.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("\n Lexing Failed (Unexpected Exception):");
    }
  }
}
