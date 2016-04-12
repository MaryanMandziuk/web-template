/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova.template.file;

import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maryan
 */
public class ValueComparatorTest {
    
    public ValueComparatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of compare method, of class ValueComparator.
     */
    @Test
    public void testCompare() {
        System.out.println("ValueComparator: compare()");
        
        String a = "";
        String b = "";
        String c = "string";
        String d = "dot";
        
        
        Map<String, Long> base = new HashMap<>();
        base.put(a, 12L);
        base.put(b, 14L);
        base.put(c, 4L);
        base.put(d, Long.MIN_VALUE);
        
        ValueComparator instance = new ValueComparator(base);
        
        assertEquals(-1, instance.compare(a, b));
        assertEquals(1, instance.compare(d, c));
        assertEquals(-1, instance.compare(a, a));
        
        
        
    }
    
    @Test
    public void testValueComparator() {
        System.out.println("ValueComparator: new ValueComparator");
        
        Map<String, Long> base  = new HashMap<>();
        Map<String, Long> base2 = null;
        
        assertNull(base2);
        
        ValueComparator obj = new ValueComparator(base);
        ValueComparator obj2 = new ValueComparator(base2);
        
        assertNotNull(obj);
        assertNotNull(obj2);
        
    }
    
}
