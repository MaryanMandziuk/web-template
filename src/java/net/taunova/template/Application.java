/*
 * Copyright 2009 TauNova (http://taunova.com). All rights reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package net.taunova.template;

import net.taunova.template.file.FilesystemWalker;
import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
//import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
/**
 * Template application entry point and arguments processing.
 *
 * @author Renat.Gilmanov
 */
public class Application {

    private static final String MAIN_PROPERTIES = "main";
    
    public static void main(String[] args) {
        
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        

//        options.addOption("h", "help", false, "Show this help");
        
        options.addOption(Option.builder("f")
                .numberOfArgs(2)
                .required()
                .desc("requirie two folders")
                .build());

        try {
            CommandLine commandLine = parser.parse(options, args);
            
            if(commandLine.hasOption("f")) {
                
                final File inFolder = new File(commandLine.getOptionValues("f")[0]);
                final File outFolder = new File(commandLine.getOptionValues("f")[1]);
                
                        

                if (!inFolder.isDirectory()) {
                    System.err.println("Input folder does not exist: "
                            + inFolder.getAbsolutePath());
                   
                }

                if (!outFolder.isDirectory()) {
                    System.err.println("Output folder does not exist: "
                            + outFolder.getAbsolutePath());
                    
                }

                    try {
                        final String settingsName = (args.length > 2) ? args[2] : MAIN_PROPERTIES;
                        FilesystemWalker app = new FilesystemWalker(settingsName);
                        app.processFolder(inFolder, outFolder.getAbsolutePath(), false);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                
                
            }
            
            
//            HelpFormatter formatter = new HelpFormatter();
//            if(commandLine.hasOption("h")) {
//                
//                
//                formatter.printHelp("CommandLineParameters", options);
//            } else if(commandLine.getOptions().length == 0) {
//                formatter.printHelp("CommandLineParameters", options);
//            }
            
            
        } catch(ParseException e) {
            e.printStackTrace();
        }

//        if (exit) {
//            System.out.println("terminating...");
//            System.exit(1);
//        }               
        
        
    }
}
