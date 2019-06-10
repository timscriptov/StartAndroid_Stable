package com.anjlab.android.iab.v3.utils;
import android.util.*;

import org.jetbrains.annotations.NotNull;

public class Utils
{
	@NotNull
    public static String fromBase64(String message) {
        byte[] data = Base64.decode(message, Base64.DEFAULT);
        return new String(data);
    }
	
}
