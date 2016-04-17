/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova.template;


import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author maryan
 */
public class ApplicationTest {
    
    static final String TMPL_EXT = ".tmpl";
    static final String PAGE_EXT = ".page"; 
    
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
    
    
}
