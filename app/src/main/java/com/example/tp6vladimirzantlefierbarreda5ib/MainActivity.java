package com.example.tp6vladimirzantlefierbarreda5ib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {
    CCGLSurfaceView VistaPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        VistaPrincipal = new CCGLSurfaceView(this);
        setContentView(VistaPrincipal);
    }

    @Override
    protected void onStart() {
        super.onStart();
        clsJuego JuegoVladi;
        JuegoVladi = new clsJuego(VistaPrincipal);
        JuegoVladi.ComenzarJuego();

    }
}