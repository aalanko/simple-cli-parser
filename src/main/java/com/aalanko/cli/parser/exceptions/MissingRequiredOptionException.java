package com.aalanko.cli.parser.exceptions;

public class MissingRequiredOptionException extends SimpleCliParserException {

  public MissingRequiredOptionException(String message) {
    super(message);
  }
}
