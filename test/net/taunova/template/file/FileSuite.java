/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova.template.file;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author maryan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({net.taunova.template.file.FileInfoTest.class, net.taunova.template.file.ValueComparatorTest.class, net.taunova.template.file.FilesystemWalkerTest.class})
public class FileSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
}
