/*
 * Copyright 2011 TauNova (http://taunova.net). All rights reserved.
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package net.taunova.template.file;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author Renat.Gilmanov
 */
public class ValueComparator implements Comparator<String> {
    private Map<String, Long> base;

    public ValueComparator(Map<String, Long> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}