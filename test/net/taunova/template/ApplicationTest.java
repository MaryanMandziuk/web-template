/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova.template;


import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.FileUtils;
import org.junit.contrib.java.lang.system.SystemErrRule;

/**
 *
 * @author maryan
 */
public class ApplicationTest {
    
    static final String TMPL_EXT = ".tmpl";
    static final String PAGE_EXT = ".page"; 
    static final String GLOB_EXT = ".properties";
    
    public ApplicationTest() {
    }   
    
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    /**
     * Test of main method, of class Application.
     * @throws java.io.IOException
     */
    @Test
    public void testMain() throws IOException {
        System.out.println("main");
        final File in_folder = tempFolder.newFolder("testInFolder");
        final File out_folder = tempFolder.newFolder("testOutFolder");
        final File templates_folder = tempFolder.newFolder(in_folder.getName(), "templates");
        final File template = tempFolder.newFile(in_folder.getName() + File.separator 
                + templates_folder.getName() + File.separator + "image-test" + TMPL_EXT);
        final File template_nomirror = tempFolder.newFile(in_folder.getName() 
                + File.separator + templates_folder.getName() + File.separator + ".nomirror");
        final File structure_folder = tempFolder.newFolder(in_folder.getName(), "structure");
        final File structure = tempFolder.newFile(in_folder.getName() + File.separator 
                + structure_folder.getName() + File.separator + "test" + TMPL_EXT);
        final File structure_nomirror = tempFolder.newFile(in_folder.getName() 
                + File.separator + structure_folder.getName() + File.separator + ".nomirror");
        final File page = tempFolder.newFile(in_folder.getName() + File.separator 
                + "index" + PAGE_EXT);
        
        
        String image_test_content = "<img src=\"$image-file\" alt=\"Just an image\">";
        String test_tmpl = "<div class=\"content\"><\\div>";
        String page_content = "Hello $file-structure-test world"
                + " lorem $image-test-im-jpg dust"
                + "file $file-structure-test image.";
        
        FileUtils.writeStringToFile(template, image_test_content);
        FileUtils.writeStringToFile(structure, test_tmpl);
        FileUtils.writeStringToFile(page, page_content);
        
        String[] args = {in_folder.getAbsolutePath(), out_folder.getAbsolutePath()};
        
        Application.main(args);
        
        
        // Testing for acceptable directory
        LinkedList<File> outFolderList = (LinkedList<File>) FileUtils.listFilesAndDirs(out_folder, 
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
 
        String [] filesFolders = new String[outFolderList.size()]; 
        for (int i = 0; i<outFolderList.size(); i++) {
            filesFolders[i] = outFolderList.get(i).getName();
        }
        
        
        String[] exp_files = {"testOutFolder", "index.html"};
        assertArrayEquals("failure - directories not same", exp_files, filesFolders);
        
        // Testing for resulting html
        try {
        String result = FileUtils.readFileToString(new File(out_folder.getAbsoluteFile() 
                + File.separator + "index.html"));
        String exp_result = "Hello <div class=\"content\"><\\div> world"
                + " lorem <img src=\"images/im.jpg\" alt=\"Just an image\"> dust"
                + "file <div class=\"content\"><\\div> image.";
        assertEquals("failure - genereted file isn't same", exp_result, result);
        } catch(IOException e) {
            System.out.println("failure - not found file" + e.getMessage());
            fail("failure - not found file" + e.getMessage());
        }
    }
    
    @Test
    public void functionalTest() throws IOException {
        System.out.println("functional test");
        
        final File in_folder = tempFolder.newFolder("testInFolder");
        final File out_folder = tempFolder.newFolder("testOutFolder");
        
        final File templates_folder = tempFolder.newFolder(in_folder.getName(), "templates");
        final File template_file = tempFolder.newFile(in_folder.getName() + File.separator 
                + templates_folder.getName() + File.separator + "image-test" + TMPL_EXT);
        final File template_nomirror = tempFolder.newFile(in_folder.getName() 
                + File.separator + templates_folder.getName() + File.separator + ".nomirror");
        
        final File structure_folder = tempFolder.newFolder(in_folder.getName(), "structure");
        final File structure_file = tempFolder.newFile(in_folder.getName() + File.separator 
                + structure_folder.getName() + File.separator + "test" + TMPL_EXT);
        final File structure_nomirror = tempFolder.newFile(in_folder.getName() 
                + File.separator + structure_folder.getName() + File.separator + ".nomirror");
        
        final File deeper_folder = tempFolder.newFolder(in_folder.getName(), "deeper");
        final File deeper_file = tempFolder.newFile(in_folder.getName() + File.separator 
                + deeper_folder.getName() + File.separator + "dee" + TMPL_EXT);
        final File deeper_nomirror = tempFolder.newFile(in_folder.getName() 
                + File.separator + deeper_folder.getName() + File.separator + ".nomirror");
        
        final File images_folder = tempFolder.newFolder(in_folder.getName(), "images");
        final File image_file = tempFolder.newFile(in_folder.getName() + File.separator 
                + images_folder.getName() + File.separator + "testImage.jpg");
        
        final File static_folder = tempFolder.newFolder(in_folder.getName(), "static");
        final File static_file = tempFolder.newFile(in_folder.getName() + File.separator 
                + static_folder.getName() + File.separator + "staticTest" + TMPL_EXT);
        final File static_page = tempFolder.newFile(in_folder.getName() + File.separator 
                + static_folder.getName() + File.separator + "staticIndex" + PAGE_EXT);
        
        final File someBad_folder = tempFolder.newFolder(in_folder.getName(), "someBad");
        final File someBad_file = tempFolder.newFile(in_folder.getName() + File.separator 
                + someBad_folder.getName() + File.separator + "someBadFile" + TMPL_EXT);
        final File ignore_file = tempFolder.newFile(in_folder.getName() + File.separator 
                + someBad_folder.getName() + File.separator + ".ignore");
        
        final File properties_file = tempFolder.newFile(in_folder.getName() + File.separator 
                + "main" + GLOB_EXT);
        
        final File page = tempFolder.newFile(in_folder.getName() + File.separator 
                + "index" + PAGE_EXT);
        
        
        String image_test_content = "<img src=\"$image-file\" alt=\"Just an image\">";
        String template_content = "<div class=\"content\"><\\div>" 
                + " <h1>some html</h1> $file-deeper-dee exit.";
        String page_content = "Hello $file-structure-test World"
                + " lorem $image-test-im-jpg dust"
                + "file $file-structure-test";
        String deeper_content = "Some content";
        String static_content = "<div>Hello World!</div> $prop";
        String static_page_content = "$file-static-staticTest";
        String properties_content = "prop=oOps!";
        
        
        FileUtils.writeStringToFile(template_file, image_test_content);
        FileUtils.writeStringToFile(structure_file, template_content);
        FileUtils.writeStringToFile(page, page_content);
        FileUtils.writeStringToFile(deeper_file, deeper_content);
        FileUtils.writeStringToFile(static_file, static_content);
        FileUtils.writeStringToFile(static_page, static_page_content);
        FileUtils.writeStringToFile(properties_file, properties_content);
        
        
        String[] args = {in_folder.getAbsolutePath(), out_folder.getAbsolutePath()};
        
        Application.main(args);
        
        // Testing for acceptable directory
        LinkedList<File> outFolderList = (LinkedList<File>) FileUtils.listFilesAndDirs(out_folder, 
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
 
        String [] filesFolders = new String[outFolderList.size()]; 
        for (int i = 0; i<outFolderList.size(); i++) {
            filesFolders[i] = outFolderList.get(i).getName();
        }
        
        
        String[] exp_files = {"testOutFolder", "images", "testImage.jpg", "index.html",
                                "static", "staticIndex.html"};
        assertArrayEquals("failure - directories not same", exp_files, filesFolders);
        
        
        try {
        String result = FileUtils.readFileToString(new File(out_folder.getAbsoluteFile() 
                + File.separator + "index.html"));
        String exp_result = "Hello <div class=\"content\"><\\div> <h1>some html</h1> "
                + "Some content exit. World"
                + " lorem <img src=\"images/im.jpg\" alt=\"Just an image\"> dust"
                + "file <div class=\"content\"><\\div> <h1>some html</h1> "
                + "Some content exit.";
        
        assertEquals("failure - genereted file isn't same", exp_result, result);
        
        } catch(IOException e) {
            System.out.println("failure - not found file" + e.getMessage());
            fail("failure - not found file" + e.getMessage());
        }
        
        
        
        try {
        String result = FileUtils.readFileToString(new File(out_folder.getAbsoluteFile() 
                + File.separator + static_folder.getName() + File.separator + "staticIndex.html"));
        
        String exp_result = "<div>Hello World!</div> oOps!";
        
        assertEquals("failure - genereted file isn't same", exp_result, result);
        
        } catch(IOException e) {
            System.out.println("failure - not found file" + e.getMessage());
            fail("failure - not found file" + e.getMessage());
        }
    }
    
    
    
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();
    
    @Test
    public void testInputOutputFolders() throws IOException {
        System.out.println("test input and output folders");
        
        final File in_folder = tempFolder.newFolder("testInFolder");
        final File out_folder = tempFolder.newFolder("testOutFolder");
        
        String[] args = {in_folder.getAbsolutePath(), in_folder.getAbsolutePath()};
        
        exit.expectSystemExitWithStatus(1);
        
        Application.main(args);
        
        assertEquals("Output folder does not exi1st: " + new File("noFolder").getAbsolutePath(),
                systemErrRule.getLog());
        System.out.println("Hello worlds");
    }   
}
