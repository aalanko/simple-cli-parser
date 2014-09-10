package com.aalanko.cli.parser;

import com.aalanko.cli.parser.exceptions.MissingOptionValueException;
import com.aalanko.cli.parser.exceptions.SimpleCliParserException;

/**
 * @author Andreas Alanko
 */
public class Parser {

  /**
   * Parses the given command line arguments array.
   * 
   * @param options the options
   * @param args the command line arguments as an string array
   * @return returns the altered Options object with the parsed valued
   * @throws SimpleCliParserException thrown when the command line parser fails
   */
  public static Options parseArgs(Options options, String[] args) throws SimpleCliParserException {
    if (args.length > 0) {
      String arg, nextArg;

      for (int i = 0; i < args.length; i++) {
        arg = args[i];
        nextArg = null;
        
        if (i < args.length - 1) {
          nextArg = args[i+1];
        }

        if (options.optionExists(arg)) {
          if (nextArg != null && !options.optionExists(nextArg) && !options.flagExists(nextArg)) {
            options.setOption(arg, nextArg);
            i++;
          } else {
            throw new MissingOptionValueException("Missing option value: " + arg + " = " + nextArg);
          }
        } else if (options.flagExists(arg)) {
          options.setFlag(arg);
        } else {
          options.addData(arg);
        }
      }
    }
    
    options.checkRequiredOptionsSet();
    return options;
  }
}
