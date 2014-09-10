package com.aalanko.cli.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class FlagTest {
  private Flag flag;

  @Before
  public void setUp() {
    flag = new Flag("Flag1", "the first flag");
  }
  
  @Test
  public void testGetters() {
    assertEquals("Flag1", flag.getName());
    assertEquals("the first flag", flag.getDescription());
    assertFalse(flag.isSet());
  }
  
  @Test
  public void testSetFlag() {
    assertFalse(flag.isSet());
    flag.set();
    assertTrue(flag.isSet());
  }
}
