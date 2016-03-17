package com.example.viethoang.firstgameproject;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewFlipper;

import com.example.viethoang.firstgameproject.helper.AssetLoader;
import com.example.viethoang.firstgameproject.manager.SoundManager;
import com.example.viethoang.firstgameproject.view.MainGameView;

public class MainActivity extends Activity {

    public static Resources resources;
    public static Display display;
    public static SoundManager soundManager;
    public static ViewFlipper viewFlipper;
    private View viewGame;
    private View viewMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Turn Title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get display
        display = getWindowManager().getDefaultDisplay();
        // Get resources
        resources = getResources();

        AssetLoader.load(getWindowManager().getDefaultDisplay(), getResources());
        setContentView(R.layout.activity_main);

        soundManager = new SoundManager(this);

        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        viewGame = (View) findViewById(R.id.game);
        viewMenu = (View) findViewById(R.id.menu);

        viewMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (x >= (display.getWidth() - AssetLoader.play.getWidth()) / 2
                                && x <= (display.getWidth() + AssetLoader.play.getWidth()) / 2
                                && y >= (display.getHeight() - AssetLoader.play.getHeight()) / 2
                                && y <= (display.getHeight() + AssetLoader.play.getHeight()) / 2) {
                            viewFlipper.showNext();
                        }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
