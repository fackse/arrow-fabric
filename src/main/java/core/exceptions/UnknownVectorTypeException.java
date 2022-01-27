package core.exceptions;

import org.example.ArrowFabric.Code;

/**
 * When trying to create a vector whose type is not yet supported, this exception is thrown
 */
public class UnknownVectorTypeException extends Exception {

  public UnknownVectorTypeException(Code code) {
  }

  public UnknownVectorTypeException(String message) {
    super(message);
  }
}
