/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.taunova.template.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

/**
 *
 * @author maryan
 */
public class FilesystemWalkerTest {
    
    static final String IMAGE_TMPL = "site-protocolstack/templates/image-figure.tmpl";
    static final String TMPL_EXT = ".tmpl";
    static final String PAGE_EXT = ".page"; 
    
    
    public FilesystemWalkerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println("BeforeClass: create testFolder");
        File main_folder = new File("testFolder/");
        File template = new File(main_folder.getAbsolutePath() + File.separator + "templates/image-test" + TMPL_EXT);
        File structure = new File(main_folder.getAbsolutePath() + File.separator + "structure/test" + TMPL_EXT);
        File page = new File(main_folder.getAbsolutePath() + File.separator + "index" + PAGE_EXT);
        File image_folder = new File(main_folder.getAbsolutePath() + File.separator + "images");
        
        String image_test_content = "<img src=\"$image-file\" alt=\"Just an image\">";
        String test_tmpl = "<div class=\"content\">"
                + "<\\div>";
        String page_content = "Hello $file-structure-test world"
                + " lorem $image-test-im-jpg dust"
                + "file $file-structure-test image.";
        
        if(!template.exists()) {
            if(template.getParentFile().mkdirs())
                template.createNewFile();
        }
            
        if(!structure.exists()) {
            if(structure.getParentFile().mkdirs())
                structure.createNewFile();
        }
        
        if(!page.exists()) {
            page.createNewFile();
        }
        
        if(!image_folder.exists()) {
            image_folder.mkdir();
        }
        
        
        FileUtils.writeStringToFile(template, image_test_content);
        FileUtils.writeStringToFile(structure, test_tmpl);
        FileUtils.writeStringToFile(page, page_content);

    }
    
    @AfterClass
    public static void tearDownClass() throws IOException  {
        System.out.println("AfterClass: delete testFolder");
        File main_folder = new File("testFolder/");
        if(main_folder.exists()) {
            FileUtils.deleteDirectory(main_folder);
        }
       
    }

    /**
     * Test of processFolder method, of class FilesystemWalker.
     * @throws java.lang.Exception
     */
    @Ignore
    @Test
    public void testProcessFolder_3args() throws Exception {
        System.out.println("FilesystemWalker: processFolder()");
    }

    /**
     * Test of listReferencedFiles method, of class FilesystemWalker.
     * @throws java.lang.Exception
     */
    
    @Test
    public void testListReferencedFiles() throws Exception {
        System.out.println("FilesystemWalker: listReferencedFiles()");
  
        String templateContent = FileUtils.readFileToString(new File("testFolder/index.page"));
        
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        Map<String, String> expResult = new HashMap<>();
        File inFolder = new File("testFolder/");
        expResult.put("structure/test.tmpl", "file-structure-test");
        expResult.put("templates/image-test.tmpl", "image-test-im-jpg");
        expResult.put("structure/test.tmpl", "file-structure-test");
        
        Map<String, String> result = instance.listReferencedFiles(templateContent, inFolder);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of listImages method, of class FilesystemWalker.
     * @throws java.lang.Exception
     */
    
    @Test
    public void testListImages() throws Exception {
        System.out.println("FilesystemWalker: listImages()");
        String templateContent = FileUtils.readFileToString(new File("testFolder/index.page"));
        
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        File inFolder = new File("testFolder/");
        Map<String, String> expResult = new HashMap<>();
        expResult.put("templates/image-test.tmpl", "image-test-im-jpg");
                
        Map<String, String> result = instance.listImages(templateContent, inFolder);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of processFolder method, of class FilesystemWalker.
     * @throws java.lang.Exception
     */
    @Ignore
    @Test
    public void testProcessFolder_4args() throws Exception {
        System.out.println("FilesystemWalker: processFolder_4args()");
        File inFolder = new File("testFolder/");
        File folder = new File("testFolder/");
        String path = "out-folder1/";
        boolean createFolder = false;
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        instance.processFolder(inFolder, folder, path, createFolder);
        FileUtils.deleteDirectory(new File(path));
    }

    /**
     * Test of processFile method, of class FilesystemWalker.
     * @throws java.lang.Exception
     */
    
    @Test
    public void testProcessFile() throws Exception {
        System.out.println("FilesystemWalker: processFile");
        File inFolder = new File("testFolder/");
        File file = new File("testFolder/index.page");
        String target = "out-folder/";
        
        FilesystemWalker instance = new FilesystemWalker("Test");
        instance.processFile(inFolder, file, target);
        
        for (String key : instance.metrics.keySet()) {
            assertTrue(instance.metrics.get(key)>0);
            assertEquals(key, file.getAbsolutePath());
        }
        FileUtils.deleteDirectory(new File(target));
    }

    /**
     * Test of processAndSaveFile method, of class FilesystemWalker.
     * @throws java.lang.Exception
     */
    @Ignore
    @Test
    public void testProcessAndSaveFile() throws Exception {
        System.out.println("processAndSaveFile");
        File inFolder = null;
        File file = null;
        String target = "";
        FilesystemWalker instance = null;
        instance.processAndSaveFile(inFolder, file, target);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveFile method, of class FilesystemWalker.
     * @throws java.io.IOException
     */
    
    @Test
    public void testSaveFile() throws IOException {
        System.out.println("FilesystemWalker: saveFile()");
        File outFile = new File("testSave/test.txt");
        String text = "test";
        FileUtils.writeStringToFile(outFile, text);
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        instance.saveFile(outFile, text);
        String read_file = FileUtils.readFileToString(outFile);
        assertEquals(text, read_file);    
        FileUtils.deleteDirectory(new File("testSave/"));
    }

    /**
     * Test of copyFileIfNeeded method, of class FilesystemWalker.
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    
    @Test
    public void testCopyFileIfNeeded() throws IOException, InterruptedException {
        System.out.println("FilesystemWalker: copyFileIfNeeded()");
        File fileFrom = new File("testFolder/index.page");
        File fileTo = new File("testFolder/test/index1.page");
        //fileTo.createNewFile();
        FilesystemWalker instance = new FilesystemWalker("Test");
        instance.copyFileIfNeeded(fileFrom, fileTo);
        Thread.sleep(1000);
        assertEquals(FileUtils.readFileToString(fileTo), FileUtils.readFileToString(fileFrom));
    }

    /**
     * Test of processTmplFile method, of class FilesystemWalker.
     * @throws java.lang.Exception
     */
    
    @Test
    public void testProcessTmplFile() throws Exception {
        System.out.println("FilesystemWalker: processTmplFile()");
        
        File inFolder = new File("testFolder/");
        File file = new File("testFolder/index.page");
        Map<String, FileInfo> fileMap = new HashMap<>();
        
        String expResult = "Hello <div class=\"content\"><\\div> world lorem"
                + " <img src=\"image/im.jpg\" alt=\"Just an image\"> dustfile"
                + " <div class=\"content\"><\\div> image.";
        
        fileMap.put("structure/test.tmpl", new FileInfo("<div class=\"content\"><\\div>"));
        fileMap.put("templates/image-test.tmpl", new FileInfo("<img src=\"image/im.jpg\" alt=\"Just an image\">"));
        
        
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        String result = instance.processTmplFile(inFolder, file);
        
        assertEquals(instance.fileMap.keySet(), fileMap.keySet());
        for (String dep : instance.fileMap.keySet()) {
            assertEquals(instance.fileMap.get(dep).getText(), fileMap.get(dep).getText());
        }
      
        assertEquals(expResult, result);
    }
    
}
