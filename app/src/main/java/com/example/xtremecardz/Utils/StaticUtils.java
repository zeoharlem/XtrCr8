package com.example.xtremecardz.Utils;

import android.content.Context;
import android.graphics.Typeface;

public class StaticUtils {
    public static Typeface sTypeFace(Context mCnxt, String path) {
        Typeface mtypeface = Typeface.createFromAsset(mCnxt.getAssets(), path);
        return mtypeface;
    }
}
