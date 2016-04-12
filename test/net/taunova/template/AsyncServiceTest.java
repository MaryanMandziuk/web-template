/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova.template;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author maryan
 */
public class AsyncServiceTest {
    
    public AsyncServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of execute method, of class AsyncService.
     */
    @Ignore
    @Test
    public void testExecute() {
        System.out.println("execute");
        Runnable task = null;
        AsyncService instance = new AsyncService();
        instance.execute(task);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of shutdown method, of class AsyncService.
     */
    @Ignore
    @Test
    public void testShutdown() {
        System.out.println("shutdown");
        AsyncService instance = new AsyncService();
        instance.shutdown();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
