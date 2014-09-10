package com.aalanko.cli.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.aalanko.cli.parser.exceptions.OptionAlreadySetException;

public class OptionTest {
  private Option option;
  
  @Before
  public void setUp() {
    option = new Option("myOption", "defaultValue", true, "option description");
  }
  
  @Test
  public void testGetters() {
    assertEquals("myOption", option.getName());
    assertEquals(null, option.getValue());
    assertEquals("option description", option.getDescription());
    assertTrue(option.required());
    assertFalse(option.isSet());
  }
  
  @Test
  public void testDefaultValue() {
    option = new Option ("default", "defaultValue", false, "option description");
    
    assertEquals("default", option.getName());
    assertEquals("defaultValue", option.getValue());
    assertEquals("option description", option.getDescription());
    assertFalse(option.required());
    assertFalse(option.isSet());
  }
  
  @Test
  public void testSetValue() throws OptionAlreadySetException {
    assertFalse(option.isSet());
    assertTrue(option.setValue("newValue"));
    assertEquals("newValue", option.getValue());
    assertTrue(option.isSet());
  }
  
  @Test
  public void testSetValueTwice() {
    assertFalse(option.isSet());
    assertTrue(option.setValue("newValue"));
    assertEquals("newValue", option.getValue());
    assertTrue(option.isSet());
    assertFalse(option.setValue("secondValue"));
    assertTrue(option.isSet());
  }

}
