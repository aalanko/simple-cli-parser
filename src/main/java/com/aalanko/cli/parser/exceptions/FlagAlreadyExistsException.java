package com.aalanko.cli.parser.exceptions;

public class FlagAlreadyExistsException extends SimpleCliParserException {

  public FlagAlreadyExistsException(String message) {
    super(message);
  }
}
