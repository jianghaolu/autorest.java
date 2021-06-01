package com.azure.autorest.customization.implementation.ls.models;

public class FormattingOptions {
    private int tabSize;
    private boolean insertSpaces;
    private boolean trimTrailingWhitespace;
    private boolean trimFinalNewlines;

    public int getTabSize() {
        return tabSize;
    }

    public void setTabSize(int tabSize) {
        this.tabSize = tabSize;
    }

    public boolean isInsertSpaces() {
        return insertSpaces;
    }

    public void setInsertSpaces(boolean insertSpaces) {
        this.insertSpaces = insertSpaces;
    }

    public boolean isTrimTrailingWhitespace() {
        return trimTrailingWhitespace;
    }

    public void setTrimTrailingWhitespace(boolean trimTrailingWhitespace) {
        this.trimTrailingWhitespace = trimTrailingWhitespace;
    }

    public boolean isTrimFinalNewlines() {
        return trimFinalNewlines;
    }

    public void setTrimFinalNewlines(boolean trimFinalNewlines) {
        this.trimFinalNewlines = trimFinalNewlines;
    }
}
