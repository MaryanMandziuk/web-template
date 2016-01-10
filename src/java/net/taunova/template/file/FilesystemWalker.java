/*
 * Copyright 2009 TauNova (http://taunova.com). All rights reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package net.taunova.template.file;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.taunova.template.AsyncService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Basic file-system walker with additional features support, including
 * references, to be processed templates, etc.
 * 
 * @author Renat.Gilmanov
 */
public class FilesystemWalker {
    
    private static final String IGNORE_FLAG    = ".ignore";
    private static final String NO_MIRROR_FLAG = ".nomirror";    
    
    private static final String GLOB_EXT    = ".properties";
    private static final String TMPL_EXT    = ".tmpl";
    private static final String HTML_EXT    = ".html";       
    private static final String PAGE_EXT    = ".page";        

    protected AsyncService processor = new AsyncService();
    protected Map<String, FileInfo> fileMap = new HashMap<>();
    protected Properties globals =  new Properties();
    private final String settingsName;
    
    /**
     * Constructs the walker.
     * 
     * @param settingsName
     */
    public FilesystemWalker(String settingsName) {
        this.settingsName = settingsName;
    }
           
    /**
     * Processes specified folder.
     * 
     * @param folder a folder to be processed
     * @param path a result path
     * @param createFolder specifies if folder should be created 
     * @throws IOException 
     */
    public void processFolder(File folder, String path, boolean createFolder) throws IOException {        
        final File propertiesFile = new File(folder.getAbsoluteFile()
                + File.separator
                + settingsName + ".properties");
        
        if(propertiesFile.isFile()) {
            globals.load(new FileReader(propertiesFile));
        }
        
        processFolder(folder, folder, path, createFolder);
        processor.shutdown();
    }
    
    /**
     * Lists files referenced by the specified template.
     * 
     * @param templateContent template content
     * @return map filled with file-name and file-tag pairs
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

        File noMirrorFlag = new File(folder.getAbsoluteFile()
                + File.separator
                + NO_MIRROR_FLAG);        
        
        if (ignoreFlag.isFile()) {
            return;
        }

        // create a target folder
        String target = path;

        if (createFolder && !noMirrorFlag.isFile()) {
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
        final String fileExt = "." + FilenameUtils.getExtension(file.getName());
        final String fileName = FilenameUtils.getBaseName(file.getName());        
        
        switch(fileExt) {
            case PAGE_EXT: 
                String result = processTmplFile(inFolder, file);                
                File outFile = new File(target + File.separator + fileName + HTML_EXT);                
                // store result
                saveFile(outFile, result);
                break;
            case GLOB_EXT:
                break;
            case TMPL_EXT:                 
                // do not process template files
                break;
            case IGNORE_FLAG:                 
                break;                
            case NO_MIRROR_FLAG:                 
                break;                                
            default:                
                copyFileIfNeeded(file, new File(target + File.separator + file.getName()));
                break;
        }
    }

    /**
     * 
     * @param outFile
     * @param text 
     */
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
     * Copies a file if there is no target file or target file is older.
     * 
     * @param fileFrom source file
     * @param fileTo destination file
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
     * Processes template file.
     * 
     * @param inFolder parent folder
     * @param file template file to be processed
     * @return processed content
     * @throws IOException 
     */
    protected String processTmplFile(File inFolder, File file) throws IOException {

        String template = FileUtils.readFileToString(file);
        Map<String, String> dependencies = listReferencedFiles(template);

        VelocityContext context = new VelocityContext();

        for(String key : globals.stringPropertyNames()) {
            context.put(key, globals.getProperty(key));
        }
        
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
