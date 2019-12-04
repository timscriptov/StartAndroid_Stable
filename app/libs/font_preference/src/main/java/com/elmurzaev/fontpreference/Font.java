package com.elmurzaev.fontpreference;

public class Font {
    private String fontPath;

    Font(String fontPath) {
        this.fontPath = fontPath;
    }

    String getName() {
        return fontPath.replaceFirst("\\..*", "");
    }

    String getPath() {
        return fontPath;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Font && fontPath.equals(((Font) obj).fontPath);
    }

    @Override
    public int hashCode() {
        return fontPath.hashCode();
    }
}
