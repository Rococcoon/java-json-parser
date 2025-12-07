import lexer.Lexer;
import token.Token;
import token.TokenType;

public class Main {
  public static void main(String[] args) {
    // --- Test 1: Valid Complex JSON Structure ---
    System.out.println("--- Test 1: Valid JSON ---");
    String json1 = "{\"name\": \"Lukas\", \"id\": 1776178, \"active\": true, \"skills\": [\"Java\", \"Go\", \"JSON Parsing\"], \"score\"data\": null}";
    testLexer(json1);

    System.out.println("\n" + "-".repeat(40));

    // --- Test 2: Edge Cases and Whitespace ---
    System.out.println("--- Test 2: Whitespace and Edge Numbers ---");
    String json2 = " { \"pi\" : 3.14159, \"zero\": 0, \"neg\": -10 } ";
    testLexer(json2);

    System.out.println("\n" + "-".repeat(40));

    // --- Test 3: Illegal Tokens / Malformed JSON ---
    System.out.println("--- Test 3: Illegal Tokens (Malformed JSON) ---");
    String json3 = "{key: \"value\", bad\\char: 123, status: False}";
    testLexer(json3);
  }

  /**
   * Helper method to run the lexer on a given input and print the results.
   * 
   * @param input The JSON string to tokenize.
   */
  private static void testLexer(String input) {
    System.out.println("Input: " + input);

    // 1. Create the Lexer
    Lexer lexer = new Lexer(input);

    // 2. Tokenize the input
    java.util.List<Token> tokens = lexer.tokenizeInput();

    // 3. Print the results
    System.out.println("\nGenerated Tokens:");
    System.out.println("-----------------");

    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);

      // Format the output clearly
      String literalDisplay = token.getLiteral();
      if (token.getType() == TokenType.STRING) {
        literalDisplay = "\"" + literalDisplay + "\"";
      }
      if (token.getType() == TokenType.EOF) {
        literalDisplay = ""; // Don't print "EOF" as a literal
      }

      System.out.printf("[%2d] %-15s | Literal: %s\n",
          i,
          token.getType(),
          literalDisplay);
    }
  }
}
