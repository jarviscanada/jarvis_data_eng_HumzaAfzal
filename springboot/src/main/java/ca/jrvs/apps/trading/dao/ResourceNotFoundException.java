package ca.jrvs.apps.trading.dao;

import net.bytebuddy.implementation.bytecode.Throw;

public class ResourceNotFoundException extends Exception {

  public ResourceNotFoundException(String errorMessage) {
    super(errorMessage);
  }

  public ResourceNotFoundException(String errorMessage, Throwable error) {
    super(errorMessage, error);
  }

}
