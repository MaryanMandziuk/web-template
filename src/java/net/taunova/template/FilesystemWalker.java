/*
 * Copyright 2009 TauNova (http://taunova.com). All rights reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package net.taunova.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 *
 * @author Renat.Gilmanov
 */
public class FilesystemWalker {
    
    private static final String IGNORE_FLAG = ".ignore";
    private static final String TMPL_EXT    = ".tmpl";
    private static final String HTML_EXT    = ".html";
    
    private static final String TMPL_NAME = "tmpl";
    private static final String PAGE_NAME = "page";        

    protected AsyncService processor = new AsyncService();
    protected Map<String, FileInfo> fileMap = new HashMap<>();
    
    /**
     * 
     */
    public FilesystemWalker() {
    }
           
    /**
     * 
     * @param folder
     * @param path
     * @param createFolder
     * @throws IOException 
     */
    public void processFolder(File folder, String path, boolean createFolder) throws IOException {        
        processFolder(folder, folder, path, createFolder);
        processor.shutdown();
    }
    
    /**
     * 
     * @param templateContent
     * @return 
     */
    protected Map<String, String> listReferencedFiles(String templateContent) {
        Map<String, String> templateMap = new HashMap<>();

        final String fileReferencePattern = "\\$file-([-|\\w|\\d]+)";
        Pattern p = Pattern.compile(fileReferencePattern);
        Matcher m = p.matcher(templateContent);
        while(m.find()) {
            String depName = m.group(1);
            String fileName =  depName.replace('-', '/') + TMPL_EXT;
            if(!templateMap.containsKey(fileName)) {
                templateMap.put(fileName, "file-" + depName);
            }
        }        
        return templateMap;
    }        
    
    /**
     * 
     * @param inFolder
     * @param folder
     * @param path
     * @param createFolder
     * @throws IOException 
     */
    protected void processFolder(File inFolder, File folder, String path, boolean createFolder) throws IOException {        
        File ignoreFlag = new File(folder.getAbsoluteFile()
                + File.separator
                + IGNORE_FLAG);

        if (ignoreFlag.isFile()) {
            return;
        }

        // create a target folder
        String target = path;

        if (createFolder) {
            target = path + File.separator + folder.getName();
            new File(target).mkdir();
        }

        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                processFolder(inFolder, file, target, true);
            } else {
                processFile(inFolder, file, target);
            }
        }
    }    
    
    /**
     * 
     * @param inFolder
     * @param file
     * @param target
     * @throws IOException 
     */
    protected void processFile(File inFolder, File file, String target) throws IOException {
        final String fileExt = FilenameUtils.getExtension(file.getName());
        final String fileName = FilenameUtils.getBaseName(file.getName());
        
        switch(fileExt) {
            case PAGE_NAME: 
                String result = processTmplFile(inFolder, file);                
                File outFile = new File(target + File.separator + fileName + HTML_EXT);                
                // store result
                saveFile(outFile, result);
                break;
            case TMPL_NAME:                 
                // do not process template files
                break;
            default:                
                copyFileIfNeeded(file, new File(target + File.separator + file.getName()));
                break;
        }
    }
    
    protected void saveFile(final File outFile, final String text) {
        processor.execute(() -> {
            try {
                FileUtils.writeStringToFile(outFile, text);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });        
    }
    
    /**
     * 
     * @param fileFrom
     * @param fileTo
     * @throws IOException 
     */
    protected void copyFileIfNeeded(final File fileFrom, final File fileTo) {
        if(!fileTo.isFile() || FileUtils.isFileNewer(fileFrom, fileTo))  {
            processor.execute(() -> {
                try {
                    FileUtils.copyFile(fileFrom, fileTo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });            
        }         
    }
    
    /**
     * 
     * @param inFolder
     * @param file
     * @return
     * @throws IOException 
     */
    protected String processTmplFile(File inFolder, File file) throws IOException {

        String template = FileUtils.readFileToString(file);
        Map<String, String> dependencies = listReferencedFiles(template);

        VelocityContext context = new VelocityContext();
        for (String dep : dependencies.keySet()) {
            if (!fileMap.containsKey(dep)) {
                File tmplFile = new File(inFolder.getName() + File.separator + dep);
                String result = processTmplFile(inFolder, tmplFile);
                FileInfo fileInfo = new FileInfo(result);
                fileMap.put(dep, fileInfo);
            }
            context.put(dependencies.get(dep), fileMap.get(dep).getText());
        }
        StringWriter result = new StringWriter();
        Velocity.evaluate(context, result, "error", template);
        return result.toString();
    }    
}
