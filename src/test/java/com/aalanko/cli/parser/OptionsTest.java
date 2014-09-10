package com.aalanko.cli.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aalanko.cli.parser.Options;
import com.aalanko.cli.parser.exceptions.FlagAlreadyExistsException;
import com.aalanko.cli.parser.exceptions.FlagNotFoundException;
import com.aalanko.cli.parser.exceptions.MissingRequiredOptionException;
import com.aalanko.cli.parser.exceptions.OptionAlreadyExistsException;
import com.aalanko.cli.parser.exceptions.OptionAlreadySetException;
import com.aalanko.cli.parser.exceptions.OptionNotFoundException;
import com.aalanko.cli.parser.exceptions.SimpleCliParserException;

public class OptionsTest {
  private Options options;

  @Before
  public void setUp() throws OptionAlreadyExistsException, FlagAlreadyExistsException {
    options = new Options();
    options.addOption("option", "value", false, "description");
    options.addFlag("flag", "description");
  }
  
  @Test(expected = OptionAlreadyExistsException.class)
  public void testAddDuplicateOptions() throws OptionAlreadyExistsException, FlagAlreadyExistsException {
    options.addOption("option", "value2", false, "description");
  }
  
  @Test(expected = FlagAlreadyExistsException.class)
  public void testAddDuplicateFlags() throws OptionAlreadyExistsException, FlagAlreadyExistsException {
    options.addFlag("flag", "description");
  }
  
  @Test(expected = FlagAlreadyExistsException.class)
  public void testAddSameOptionAsFlag() throws OptionAlreadyExistsException, FlagAlreadyExistsException {
    options.addOption("flag", "defaultValue", true, "description");
  }
  
  @Test(expected = OptionAlreadyExistsException.class)
  public void testAddSameFlagAsOption() throws OptionAlreadyExistsException, FlagAlreadyExistsException {
    options.addFlag("option", "description");
  }
  
  @Test
  public void testAddDataAndSortedReturn() {
    options.addData("1");
    options.addData("2");
    options.addData("3");
    options.addData("4");
    
    String[] data = options.getData();
    
    assertEquals("1", data[0]);
    assertEquals("2", data[1]);
    assertEquals("3", data[2]);
    assertEquals("4", data[3]);
  }
  
  @Test
  public void testCheckRequired() throws SimpleCliParserException {
    options.addOption("option1", null, true, "description");
    options.addOption("option2", null, true, "description");
    
    options.setOption("option1", "value1");
    options.setOption("option2", "value2");
    
    options.checkRequiredOptionsSet();
  }
  
  @Test(expected = MissingRequiredOptionException.class)
  public void testCheckRequiredFail() throws SimpleCliParserException {
    options.addOption("option1", null, true, "description");
    options.addOption("option2", null, true, "description");
    
    options.checkRequiredOptionsSet();
  }
  
  @Test
  public void testCheckRequiredEmptyOptions() throws SimpleCliParserException {
    options = new Options();
    
    options.checkRequiredOptionsSet();
  }
  
  @Test
  public void testFlagExists() {
    assertTrue(options.flagExists("flag"));
    assertFalse(options.flagExists("flag1"));
  }
  
  @Test
  public void testOptionExists() {
    assertTrue(options.optionExists("option"));
    assertFalse(options.optionExists("option1"));
  }
  
  @Test
  public void testGetSetFlag() throws FlagNotFoundException {
    assertFalse(options.getFlag("flag"));
    options.setFlag("flag");
    assertTrue(options.getFlag("flag"));
  }
  
  @Test(expected = FlagNotFoundException.class)
  public void testSetFlagNotFound() throws FlagNotFoundException {
    options.setFlag("flag1");
  }
  
  @Test(expected = FlagNotFoundException.class)
  public void testGetFlagNotFound() throws FlagNotFoundException {
    assertTrue(options.getFlag("flag1"));
  }
  
  @Test
  public void testGetSetOption() throws OptionNotFoundException, OptionAlreadySetException {
    assertEquals("value", options.getOption("option"));
    options.setOption("option", "newValue");
    assertEquals("newValue", options.getOption("option"));
  }
  
  @Test(expected = OptionNotFoundException.class)
  public void testSetOptionNotFound() throws OptionNotFoundException, OptionAlreadySetException {
    options.setOption("option2", "newValue");
  }
  
  @Test(expected = OptionNotFoundException.class)
  public void testGetOptionNotFound() throws OptionNotFoundException {
    assertEquals("valueNotFound", options.getOption("option1"));
  }
}
