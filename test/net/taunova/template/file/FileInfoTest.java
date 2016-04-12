/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova.template.file;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maryan
 */
public class FileInfoTest {
    
    public FileInfoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getText method, of class FileInfo.
     */
    @Test
    public void testGetText() {
        System.out.println("FileInfo: getText()");
        
        FileInfo instance = new FileInfo("Test");
        FileInfo instance2 = new FileInfo("Text");
        FileInfo instance3 = null;
        
        String expResult = "Test";
        String result = instance.getText();
        
        assertEquals(expResult, result);
        assertEquals("Test", result);
        
        assertFalse(instance.getText().contentEquals(instance2.getText()));
        assertFalse(expResult.length() > result.length());
        assertFalse(expResult.contentEquals("trtr"));
        
        assertTrue(instance2.getText().contentEquals("Text"));
        assertTrue(instance3 == null);
        
        assertNotNull(instance2);
        
        assertNull(instance3);
        
        assertNotSame(instance, result);
    }
    
}
