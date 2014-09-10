package com.aalanko.cli.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.aalanko.cli.parser.exceptions.FlagAlreadyExistsException;
import com.aalanko.cli.parser.exceptions.FlagNotFoundException;
import com.aalanko.cli.parser.exceptions.MissingRequiredOptionException;
import com.aalanko.cli.parser.exceptions.OptionAlreadyExistsException;
import com.aalanko.cli.parser.exceptions.OptionAlreadySetException;
import com.aalanko.cli.parser.exceptions.OptionNotFoundException;

/**
 * @author Andreas Alanko
 */
public class Options {
  /** Map of option names and option objects. */
  private HashMap<String, Option> options;
  /** Map of flag names and flag objects. */
  private HashMap<String, Flag> flags;
  /** List of data. */
  private ArrayList<String> data;

  public Options() {
    options = new HashMap<String, Option>();
    flags = new HashMap<String, Flag>();
    data = new ArrayList<String>();
  }
  
  /**
   * Adds a data value.
   * 
   * @param data the data value
   */
  public void addData(String data) {
    this.data.add(data);
  }
  
  /**
   * Adds a flag.
   * 
   * @param name        the flag name
   * @param description the flag description
   * @throws OptionAlreadyExistsException thrown if an option with the same name already exists
   * @throws FlagAlreadyExistsException   thrown if a flag with the same name already exists
   */
  public void addFlag(String name, String description) throws OptionAlreadyExistsException, FlagAlreadyExistsException {
    if (optionExists(name)) {
      throw new OptionAlreadyExistsException("Option \"" + name + "\" already exists");
    } else if (flagExists(name)) {
      throw new FlagAlreadyExistsException("Flag \"" + name + "\" already exists");
    }
    flags.put(name, new Flag(name, description));
  }
  
  /**
   * Adds an option.
   * 
   * @param name         the option name
   * @param defaultValue the option default value
   * @param required     if option is required
   * @param description  the option description
   * @throws OptionAlreadyExistsException thrown if an option with the same name already exists
   * @throws FlagAlreadyExistsException   thrown if a flag with the same name already exists
   */
  public void addOption(String name, String defaultValue, boolean required, String description) throws OptionAlreadyExistsException, FlagAlreadyExistsException {
    if (optionExists(name)) {
      throw new OptionAlreadyExistsException("Option \"" + name + "\" already exists");
    } else if (flagExists(name)) {
      throw new FlagAlreadyExistsException("Flag \"" + name + "\" already exists");
    }
    options.put(name, new Option(name, defaultValue, required, description));
  }
  
  /**
   * Checks if all required options are set.
   * 
   * @throws MissingRequiredOptionException thrown if all required options are not set
   */
  public void checkRequiredOptionsSet() throws MissingRequiredOptionException {
    String missing = "";
    for (Option option : options.values()) {
      if (option.required() && !option.isSet()) {
        missing = missing.concat(" \"" + option.getName() + "\"");
      }
    }
    
    if (!missing.isEmpty() && !options.isEmpty()) {
      throw new MissingRequiredOptionException("Missing required option(s):" + missing);
    }
  }

  /**
   * Checks if a flag with the given name exists.
   * 
   * @param name the flag name
   * @return true if a flag with name exists, false otherwise
   */
  public boolean flagExists(String name) {
    return flags.containsKey(name);
  }

  /**
   * Returns an String array of data values.
   * 
   * @return array of data values
   */
  public String[] getData() {
    return data.toArray(new String[data.size()]);
  }

  /**
   * Checks if the flag has been set.
   * 
   * @param name the flag name
   * @return true if the flag has been set, false otherwise
   * @throws FlagNotFoundException thrown if a flag with name doesn't exists
   */
  public boolean getFlag(String name) throws FlagNotFoundException {
    if (!flagExists(name)) {
      throw new FlagNotFoundException("Flag \"" + name + "\" was not found");
    }
    return flags.get(name).isSet();
  }

  /**
   * Gets the option value.
   * 
   * @param name the option name
   * @return the option value
   * @throws OptionNotFoundException thrown if an option with name doesn't exists
   */
  public String getOption(String name) throws OptionNotFoundException {
    if (!optionExists(name)) {
      throw new OptionNotFoundException("Option \"" + name + "\" was not found");
    }
    return options.get(name).getValue();
  }

  /**
   * Checks if the option exists.
   * 
   * @param name the option name
   * @return true if an option with name exists, false otherwise
   */
  public boolean optionExists(String name) {
    return options.containsKey(name);
  }

  /**
   * Sets the flag.
   * 
   * @param name the flag name
   * @throws FlagNotFoundException thrown if a flag with name doesn't exists
   */
  public void setFlag(String name) throws FlagNotFoundException {
    if (!flagExists(name)) {
      throw new FlagNotFoundException("Flag \"" + name + "\" already exists");
    }
    flags.get(name).set();
  }

  /**
   * Sets the option with the given name with the given value.
   * 
   * @param name  the option name
   * @param value the option value
   * @throws OptionNotFoundException   thrown if an option with name doesn't exists
   * @throws OptionAlreadySetException thrown if the option with name has already been set
   */
  public void setOption(String name, String value) throws OptionNotFoundException, OptionAlreadySetException {
    if (!optionExists(name)) {
      throw new OptionNotFoundException("Option \"" + name + "\" was not found");
    }
    
    if (!options.get(name).setValue(value)) {
      throw new OptionAlreadySetException("Option \"" + name + "\" has already been set once");
    }
  }

  /**
   * Returns a string representing the data of the object.
   * 
   * @return the string representation
   */
  public String toString() {
    StringBuilder str = new StringBuilder();
    
    for (Option option : options.values()) {
      str.append("Option: " + option.getName() + " Value: " + option.getValue());
      str.append("\n");
    }
    
    for (Flag flag : flags.values()) {
      str.append("Flag: " + flag.getName() + " Value: " + flag.isSet());
      str.append("\n");
    }
    
    for (String data : this.data) {
      str.append("Data: " + data);
      str.append("\n");
    }
    return str.toString();
  }
}
