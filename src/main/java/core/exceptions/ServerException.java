package core.exceptions;

import org.example.ArrowFabric.Code;

/**
 * If something went wrong on the server side, this exception is thrown
 */
public class ServerException extends Exception {

  public ServerException(Code code) {
  }

  public ServerException(String message) {
    super(message);
  }
}
