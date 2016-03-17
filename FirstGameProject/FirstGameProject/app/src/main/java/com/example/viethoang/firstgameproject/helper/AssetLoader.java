package com.example.viethoang.firstgameproject.helper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import com.example.viethoang.firstgameproject.R;
/**
 * Created by VietHoang on 03/03/2016.
 */
public class AssetLoader {

    public static Display display;
    public static Resources resources;

    public static Bitmap bg, menu;
    public static Bitmap ball, movebox;
    public static Bitmap play;

    public static void load(Display dis, Resources res){
        display = dis;
        resources = res;
        ball = BitmapFactory.decodeResource(resources, R.drawable.ball);
        movebox = BitmapFactory.decodeResource(resources, R.drawable.bat);
        bg = BitmapFactory.decodeResource(resources, R.drawable.bg);
        menu = BitmapFactory.decodeResource(resources, R.drawable.menu);
        play = BitmapFactory.decodeResource(resources, R.drawable.play);
    }

}
