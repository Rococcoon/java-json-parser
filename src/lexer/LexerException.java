package lexer;

/**
 * Extension of RuntimeException class
 * Allows error handling at runtime for errors found in the lexer
 * Will terminate the program when an error in the lexer is encountered
 */
public class LexerException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a LexerException with details about the error location and
   * context
   *
   * @param message  A descriptive message about the error
   * @param position The character index where the error occurred
   * @param context  The portion of the input string around the error point
   */
  public LexerException(String message, int position, String context) {
    // Calls the parent (RuntimeException) constructor with a formatted message
    super(String.format("Lexing Error at position %d: %s.\nContext: '%s'",
        position, message, context));
  }
}
