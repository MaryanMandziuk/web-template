/*
 * Copyright 2009 TauNova (http://taunova.com). All rights reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package net.taunova.template.file;

/**
 * Holds an information about a processed template.
 * 
 * @author Renat.Gilmanov
 */
public class FileInfo {

    private final String text;

    /**
     * Constructs <tt>FileInfo</tt> instance.
     * 
     * @param text processed template.
     */
    public FileInfo(String text) {
        this.text = text;
    }
 
    /**
     * Returns processed text.
     * 
     * @return processed text
     */
    public String getText() {
        return text;
    }
}
