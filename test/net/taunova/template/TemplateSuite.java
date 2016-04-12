/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova.template;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author maryan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({net.taunova.template.ApplicationTest.class, net.taunova.template.file.FileSuite.class, net.taunova.template.AsyncServiceTest.class})
public class TemplateSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
}
