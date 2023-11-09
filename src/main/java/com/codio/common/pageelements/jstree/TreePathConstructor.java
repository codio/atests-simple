package com.codio.common.pageelements.jstree;

public class TreePathConstructor {

    private static final String delimiter = "/";

    public static String[] getTreePathStringArray(String treePath) {
        return treePath.split(delimiter);
    }
}
