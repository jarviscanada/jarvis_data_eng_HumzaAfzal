package ca.jrvs.apps.trading.dao;

public class ResourceNotFoundException extends Exception {

  public ResourceNotFoundException(String errorMessage) {
    super(errorMessage);
  }

  public ResourceNotFoundException(String errorMessage, Throwable error) {
    super(errorMessage, error);
  }

}
