package com.aalanko.cli.parser;

/**
 * @author Andreas Alanko
 */
public class Flag {
  /** The flag name. */
  private String name;
  /** The flag description. */
  private String description;
  /** The flag bit. */
  private boolean isSet;

  /**
   * A flag represents a true or false value. It can be set or not.
   * 
   * @param name        the flag name
   * @param description the flag description
   */
  public Flag(String name, String description) {
    this.name = name;
    this.description = description;
    isSet = false;
  }

  /**
   * Gets the flag description.
   * 
   * @return the flag description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the flag name.
   * 
   * @return the flag name
   */
  public String getName() {
    return name;
  }

  /**
   * Checks if the flag has been set.
   * 
   * @return true if the flag has been set, false otherwise
   */
  public boolean isSet() {
    return isSet;
  }

  /**
   * Marks the flag as set.
   */
  public void set() {
    isSet = true;
  }
}
