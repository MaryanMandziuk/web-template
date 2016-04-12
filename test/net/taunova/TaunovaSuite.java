/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author maryan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({net.taunova.template.TemplateSuite.class})
public class TaunovaSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {  
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
}
