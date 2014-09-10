package com.aalanko.cli.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aalanko.cli.parser.exceptions.FlagAlreadyExistsException;
import com.aalanko.cli.parser.exceptions.MissingOptionValueException;
import com.aalanko.cli.parser.exceptions.MissingRequiredOptionException;
import com.aalanko.cli.parser.exceptions.OptionAlreadyExistsException;
import com.aalanko.cli.parser.exceptions.OptionAlreadySetException;
import com.aalanko.cli.parser.exceptions.SimpleCliParserException;

public class ParserTest {
  private Options options;
  
  private String[] empty =              "".split(" ");
  private String[] onlyRequired =       "--option1 value1 --option2 value2".split(" ");
  private String[] requiredAndFlag =    "--option1 value1 --flag1 --option2 value2".split(" ");
  private String[] missingRequired =    "--option1 value1".split(" ");
  private String[] missingValueMiddle = "--option1 --option2 user1 --flag1".split(" ");
  private String[] missingValueEnd =    "--option1 value1 --flag2 --option2".split(" ");
  private String[] setOptionTwice =     "--option1 value1 --option2 value2 --option1 value3".split(" ");
  private String[] data =               "firstData --option1 value1 --flag2 middleData --option2 value2 endData".split(" ");
  
  @Before
  public void setUp() throws OptionAlreadyExistsException, FlagAlreadyExistsException {
    options = new Options();

    options.addOption("--option1", null, true, "");
    options.addOption("--option2", null, true, "");
    options.addFlag("--flag1", "");
    options.addFlag("--flag2", "");
  }
  
  @Test(expected = MissingRequiredOptionException.class)
  public void testEmpty() throws SimpleCliParserException {
    options = Parser.parseArgs(options, empty);
  }
  
  @Test
  public void testRequired() throws SimpleCliParserException { 
    options = Parser.parseArgs(options, onlyRequired);
    
    assertEquals("value1", options.getOption("--option1"));
    assertEquals("value2", options.getOption("--option2"));
    assertFalse(options.getFlag("--flag1"));
    assertFalse(options.getFlag("--flag2"));
  }
  
  @Test(expected = MissingRequiredOptionException.class)
  public void testMissingRequiredOption() throws SimpleCliParserException {
    options = Parser.parseArgs(options, missingRequired);
  }
  
  @Test
  public void testRequiredAndFlag() throws SimpleCliParserException {
    options = Parser.parseArgs(options, requiredAndFlag);
    
    assertEquals("value1", options.getOption("--option1"));
    assertEquals("value2", options.getOption("--option2"));
    assertTrue(options.getFlag("--flag1"));
    assertFalse(options.getFlag("--flag2"));
  }
  
  @Test(expected = MissingOptionValueException.class)
  public void testMissingValueMiddle() throws SimpleCliParserException {
    options = Parser.parseArgs(options, missingValueMiddle);
  }
  
  @Test(expected = MissingOptionValueException.class)
  public void testMissingValueEnd() throws SimpleCliParserException {
    options = Parser.parseArgs(options, missingValueEnd);
  }
  
  @Test(expected = OptionAlreadySetException.class)
  public void testSetOptionTwice() throws SimpleCliParserException {
    options = Parser.parseArgs(options, setOptionTwice);
  }
  
  @Test
  public void testData() throws SimpleCliParserException {
    options = Parser.parseArgs(options, data);
    
    assertEquals("value1", options.getOption("--option1"));
    assertEquals("value2", options.getOption("--option2"));
    assertFalse(options.getFlag("--flag1"));
    assertTrue(options.getFlag("--flag2"));
    
    String[] data = options.getData();
    assertEquals("firstData", data[0]);
    assertEquals("middleData", data[1]);
    assertEquals("endData", data[2]);
  }
}