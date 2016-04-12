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
    
    public FilesystemWalkerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        File files = new File(IMAGE_TMPL);
        if (!files.exists()) {
            if (files.getParentFile().mkdirs()) {
                files.createNewFile();
                System.out.println("Multiple directories are created!");
                
            } else {
                System.out.println("Failed to create multiple directories!");
            }
        }

    }
    
    @AfterClass
    public static void tearDownClass() throws IOException  {
        File files = new File("site-protocolstack/");
        if(files.exists()) {
           FileUtils.deleteDirectory(files);
        }
    }

    /**
     * Test of processFolder method, of class FilesystemWalker.
     */
    @Ignore
    @Test
    public void testProcessFolder_3args() throws Exception {
        System.out.println("FilesystemWalker: processFolder()");
        File folder = null;
        String path = "";
        boolean createFolder = false;
        FilesystemWalker instance = null;
        instance.processFolder(folder, path, createFolder);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listReferencedFiles method, of class FilesystemWalker.
     */
    @Test
    public void testListReferencedFiles() throws Exception {
        System.out.println("FilesystemWalker: listReferencedFiles()");
  
        String templateContent = "Lorem ipsum dolor sit "
                + "amet, $file-structure-fulltop "
                + "agam soleat lobortis te sit, nec  ea quem nulla. "
                + "Inani ullamcorper theophrastus mea eu, nam cu odio "
                + "impedit mediocritatem. $file-protocol-tcp Inani atomorum ne vix,"
                + " ne sit malis nusquam. "
                + "Impedit voluptaria vituperatoribus vim ne,"
                + " hinc malis errem te ius. "
                + "Te ludus $file-protocol-udp y quidam quaerendum pro.";
        
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        Map<String, String> expResult = new HashMap<>();
        
        expResult.put("structure/fulltop.tmpl", "file-structure-fulltop");
        expResult.put("protocol/tcp.tmpl", "file-protocol-tcp");
        expResult.put("protocol/udp.tmpl", "file-protocol-udp");
        
        Map<String, String> result = instance.listReferencedFiles(templateContent);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of listImages method, of class FilesystemWalker.
     */
    @Test
    public void testListImages() throws Exception {
        System.out.println("FilesystemWalker: listImages()");
        String templateContent = "Lorem ipsum dolor sit "
                + "amet, $file-structure-fulltop "
                + "agam soleat lobortis te sit, nec  ea quem nulla. "
                + "Inani ullamcorper theophrastus mea eu, nam cu odio "
                + "impedit mediocritatem. $file-protocol-tcp Inani atomorum ne vix,"
                + " ne sit malis nusquam. $image-figure-spacer-gif "
                + "Impedit voluptaria vituperatoribus vim ne,"
                + " hinc malis errem te ius. "
                + "Te ludus $file-protocol-udpy quidam quaerendum pro.";
        
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        
        Map<String, String> expResult = new HashMap<>();
        expResult.put("templates/image-figure.tmpl", "image-figure-spacer-gif");
                
        Map<String, String> result = instance.listImages(templateContent);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of processFolder method, of class FilesystemWalker.
     */
    @Ignore
    @Test
    public void testProcessFolder_4args() throws Exception {
        System.out.println("processFolder");
        File inFolder = null;
        File folder = null;
        String path = "";
        boolean createFolder = false;
        FilesystemWalker instance = null;
        instance.processFolder(inFolder, folder, path, createFolder);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processFile method, of class FilesystemWalker.
     */
    @Ignore
    @Test
    public void testProcessFile() throws Exception {
        System.out.println("processFile");
        File inFolder = null;
        File file = null;
        String target = "";
        FilesystemWalker instance = null;
        instance.processFile(inFolder, file, target);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processAndSaveFile method, of class FilesystemWalker.
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
     */
    
    @Test
    public void testSaveFile() throws IOException {
        System.out.println("saveFile");
        File outFile = new File("test.txt");
        String text = "test";
        FileUtils.writeStringToFile(outFile, text);
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        instance.saveFile(outFile, text);
        String read_file = FileUtils.readFileToString(outFile);
        assertEquals(text, read_file);
        FileUtils.deleteQuietly(outFile);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of copyFileIfNeeded method, of class FilesystemWalker.
     */
    @Ignore
    @Test
    public void testCopyFileIfNeeded() {
        System.out.println("copyFileIfNeeded");
        File fileFrom = null;
        File fileTo = null;
        FilesystemWalker instance = null;
        instance.copyFileIfNeeded(fileFrom, fileTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processTmplFile method, of class FilesystemWalker.
     */
    //@Ignore
    @Test
    public void testProcessTmplFile() throws Exception {
        System.out.println("processTmplFile");
        File inFolder = new File("testProcess/");
        File file = new File("testProcess/test.txt");
        String text = "$file-structure-fulltop\n"
                + "$image-figure-spacer-gif\n"
                + "$file-structure-fullbottom";
        FileUtils.writeStringToFile(file, text);
        String expResult = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
"<head>\n" +
"<title>Protocol Stack</title>\n" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
"<link href=\"$root/css/style.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
"</head>\n" +
"\n" +
"<body>\n" +
"\n" +
"<div class=\"main\">\n"
                + "<img src=\"image/spacer.gif\" alt=\"Just an image\">\n"
                + "  $file-navigation-footer\n" +
"\n" +
"</div>\n" +
"</body>\n" +
"</html>";
        FilesystemWalker instance = new FilesystemWalker("Mysettings");
        String result = instance.processTmplFile(inFolder, file);
        assertEquals(expResult, result);
        

        FileUtils.deleteQuietly(file);
    }
    
}
