package com.MomoDev.CryptPhoto;

import android.graphics.Bitmap;

public class BitmapTransfer {
    public static Bitmap bitmap =null;

    public static void setBitmap(Bitmap bitmap) {
        BitmapTransfer.bitmap = bitmap;
    }
    public static Bitmap getBitmap(){
        return bitmap;
    }
}
