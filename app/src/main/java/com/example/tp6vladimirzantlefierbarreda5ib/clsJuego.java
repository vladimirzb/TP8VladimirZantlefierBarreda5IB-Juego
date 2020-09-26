package com.example.tp6vladimirzantlefierbarreda5ib;

import android.util.Log;

import org.cocos2d.nodes.Director;
import org.cocos2d.opengl.CCGLSurfaceView;

public class clsJuego {
    CCGLSurfaceView _VistaDelJuego;

    public  clsJuego(CCGLSurfaceView VistaDelJuego)
    {
        Log.d("Constru", "Comienza el constructor de la clase");
        _VistaDelJuego=VistaDelJuego;
    }

    public void ComenzarJuego()
    {
        Log.d("Comenzar", "Comienza el juego");

        Director.sharedDirector().attachInView(_VistaDelJuego);
    }
}
