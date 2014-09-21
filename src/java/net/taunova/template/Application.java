/*
 * Copyright 2009 TauNova (http://taunova.com). All rights reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package net.taunova.template;

import java.io.File;
import java.io.IOException;

/**
 * Template application entry point and arguments processing.
 *
 * @author Renat.Gilmanov
 */
public class Application {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Usage: <in-folder> <out-folder>");
            System.exit(1);
        }

        final File inFolder = new File(args[0]);
        final File outFolder = new File(args[1]);

        boolean exit = false;

        if (!inFolder.isDirectory()) {
            System.err.println("In folder does not exist: "
                    + inFolder.getAbsolutePath());
            exit = true;
        }

        if (!outFolder.isDirectory()) {
            System.err.println("Out folder does not exist: "
                    + outFolder.getAbsolutePath());
            exit = true;
        }

        if (exit) {
            System.out.println("terminating...");
            System.exit(1);
        }

        FilesystemWalker app = new FilesystemWalker();
        app.processFolder(inFolder, outFolder.getAbsolutePath(), false);
    }
}
