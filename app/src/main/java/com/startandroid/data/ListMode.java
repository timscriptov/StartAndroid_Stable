package com.startandroid.data;

public final class ListMode {
    public static Mode getCurrentMode() {
        return Preferences.isInGridMode() ? Mode.GRID : Mode.DEFAULT;
    }

    public enum Mode {
        DEFAULT, GRID
    }

}
