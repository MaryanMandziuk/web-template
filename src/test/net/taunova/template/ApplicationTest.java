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
    static final String GLOB_EXT = ".properties";

    static final String OPTION_F = "-f";

    public ApplicationTest() {
    }

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    /**
     * Generate folder with template and .nomirror 
     * @param in_folder
     * @param newFolderName
     * @param newFileName
     * @return
     * @throws IOException 
     */
    public File createTempFolder(File in_folder, String newFolderName, String newFileName) throws IOException {
        final File templates_folder = tempFolder.newFolder(in_folder.getName(), newFolderName);
        final File template = tempFolder.newFile(in_folder.getName() + File.separator 
                + templates_folder.getName() + File.separator + newFileName + TMPL_EXT);
        tempFolder.newFile(in_folder.getName() 
                + File.separator + templates_folder.getName() + File.separator + ".nomirror");
        return template;
    }
    
    

    /**
     * Test of main method, of class Application.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testMain() throws IOException {
        System.out.println("--> main");
        final File in_folder = tempFolder.newFolder("testInFolder");
        final File out_folder = tempFolder.newFolder("testOutFolder");        
        
        File template = createTempFolder(in_folder, "templates", "image-test");

        File structure = createTempFolder(in_folder, "structure", "test");

        
        final File page = tempFolder.newFile(in_folder.getName() + File.separator 
                + "index" + PAGE_EXT);

        String image_test_content = "<img src=\"$image-file\" alt=\"Just an image\">";
        String test_tmpl = "<div class=\"content\"><\\div>";
        String page_content = "Hello $file-structure-test world"
                + " lorem $image-test-im-1-ost-jpg dust"
                + "file $file-structure-test image.";

        FileUtils.writeStringToFile(template, image_test_content);
        FileUtils.writeStringToFile(structure, test_tmpl);
        FileUtils.writeStringToFile(page, page_content);

        String[] args = {OPTION_F, in_folder.getAbsolutePath(), out_folder.getAbsolutePath()};

        Application.main(args);

        // Testing for acceptable directory
        LinkedList<File> outFolderList = (LinkedList<File>) FileUtils.listFilesAndDirs(out_folder,
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

        String[] filesFolders = new String[outFolderList.size()];
        for (int i = 0; i < outFolderList.size(); i++) {
            filesFolders[i] = outFolderList.get(i).getName();
        }

        String[] exp_files = {"testOutFolder", "index.html"};
        assertArrayEquals("failure - directories not same", exp_files, filesFolders);

        // Testing for resulting html
        try {
            String result = FileUtils.readFileToString(new File(out_folder.getAbsoluteFile() 
                    + File.separator + "index.html"));
            String exp_result = "Hello <div class=\"content\"><\\div> world"
                    + " lorem <img src=\"/images/im-1-ost.jpg\" alt=\"Just an image\"> dust"
                    + "file <div class=\"content\"><\\div> image.";
            assertEquals("failure - genereted file isn't same", exp_result, result);
        } catch(IOException e) {
            System.out.println("failure - not found file" + e.getMessage());
            fail("failure - not found file" + e.getMessage());
        }
    }

    
    /**
     * Testing the functionality of application
     * @throws IOException 
     */
    @Test
    public void functionalTest() throws IOException {
        System.out.println("--> functional test");

        final File in_folder = tempFolder.newFolder("testInFolder");
        final File out_folder = tempFolder.newFolder("testOutFolder");
        
        File template_file = createTempFolder(in_folder, "templates", "image-test");

        File structure_file = createTempFolder(in_folder, "structure", "test");

        File deeper_file = createTempFolder(in_folder, "deeper", "dee");

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
        String static_content = "<div>Hello World!</div> $root";
        String static_page_content = "$file-static-staticTest";
        String properties_content = "root=dir";
        
        FileUtils.writeStringToFile(template_file, image_test_content);
        FileUtils.writeStringToFile(structure_file, template_content);
        FileUtils.writeStringToFile(page, page_content);
        FileUtils.writeStringToFile(deeper_file, deeper_content);
        FileUtils.writeStringToFile(static_file, static_content);
        FileUtils.writeStringToFile(static_page, static_page_content);
        FileUtils.writeStringToFile(properties_file, properties_content);

        String[] args = {OPTION_F, in_folder.getAbsolutePath(), out_folder.getAbsolutePath()};

        Application.main(args);

        // Testing for acceptable directory
        LinkedList<File> outFolderList = (LinkedList<File>) FileUtils.listFilesAndDirs(out_folder,
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);

        String[] filesFolders = new String[outFolderList.size()];
        for (int i = 0; i < outFolderList.size(); i++) {
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
                    + " lorem <img src=\"dir/images/im.jpg\" alt=\"Just an image\"> dust"
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

            String exp_result = "<div>Hello World!</div> dir";

            assertEquals("failure - genereted file isn't same", exp_result, result);
        
        } catch(IOException e) {
            System.out.println("failure - not found file" + e.getMessage());
            fail("failure - not found file" + e.getMessage());
        }
    }
}
