package com.aalanko.cli.parser;

/**
 * @author Andreas Alanko
 */
public class Option {
  /** The option name. */
  private String name;
  /** The option value. */
  private String value;
  /** The option description. */
  private String description;
  /** Flag if option is required. */
  private boolean required;
  /** Flag if value has been set. */
  private boolean isSet;

  /**
   * 
   * @param name         the option name
   * @param defaultValue the option default value
   * @param required     if the option is required to be set
   * @param description  the option description
   */
  public Option(String name, String defaultValue, boolean required, String description) {
    this.name = name;
    this.required = required;
    this.description = description;
    value = (required) ? null : defaultValue;
    isSet = false;
  }

  /**
   * Gets the option description.
   * 
   * @return the option description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the option name.
   * 
   * @return the option name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the option value.
   * 
   * @return the option value
   */
  public String getValue() {
    return value;
  }

  /**
   * Checks if the option value has been set.
   * 
   * @return true if option value has been set, false otherwise
   */
  public boolean isSet() {
    return isSet;
  }

  /**
   * Checks if the option is required to be set.
   * 
   * @return true if required, false otherwise
   */
  public boolean required() {
    return required;
  }

  /**
   * Sets the option value.
   * 
   * If the option value has already been set once the method will
   * return false.
   * 
   * @param value the option value
   * @return true if value could be set, false otherwise
   */
  public boolean setValue(String value) {
    if (isSet) {
      return false;
    }
    this.value = value;
    isSet = true;
    return true;
  }
}
