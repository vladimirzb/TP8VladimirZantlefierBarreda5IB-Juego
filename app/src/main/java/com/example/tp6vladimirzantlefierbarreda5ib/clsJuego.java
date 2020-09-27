package com.example.tp6vladimirzantlefierbarreda5ib;

import android.util.Log;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

import java.util.Random;

public class clsJuego {
    CCGLSurfaceView _VistaDelJuego;
    CCSize _Pantalla;
    Sprite _Jugador;

    public  clsJuego(CCGLSurfaceView VistaDelJuego)
    {
        Log.d("Constru", "Comienza el constructor de la clase");
        _VistaDelJuego=VistaDelJuego;
    }

    public void ComenzarJuego()
    {
        Log.d("Comenzar", "Comienza el juego");
        Director.sharedDirector().attachInView(_VistaDelJuego);

        _Pantalla= Director.sharedDirector().displaySize();
        Log.d("ComenzarJuego", "Pantalla - Ancho: " + _Pantalla.getWidth()+ "- Alto: " + _Pantalla.getHeight());

        Log.d("Comenzar", "Declaro e instancio la escena");
        Scene escenaAUsar;
        escenaAUsar = EscenaComienzo();

        Log.d("Comenzar", "Le digo al director de mi jueguito que inicie la escena");
        Director.sharedDirector().runWithScene(escenaAUsar);
    }

    private Scene EscenaComienzo()
    {
        Log.d("EscenaComienzo","comienza");
        Scene escenaADevovler;
        escenaADevovler=Scene.node();

        Log.d("EscenaComienzo","Agrego capa");
        capaJuego unaCapa;
        unaCapa = new capaJuego();
        escenaADevovler.addChild(unaCapa);

        Log.d("EscenaComienzo","Devuelvo la escena creada");
        return escenaADevovler;

    }

    class capaJuego extends Layer{

        public capaJuego()
        {
            Log.d("CapaJuego", "Bob nos construye la capita");

            Log.d("CapaJuego", "Voy a ubicar el jugador en su posicion inicial");
            super.schedule("ponerJugador",3.0f);

            Log.d("CapaJuego", "Ubico al shiba gordo de fondo");
            ponerImagenFondo();
        }

        public void ponerJugador(float diferenciaTiempo)
        {
            Log.d("PonerJugador", "Le asigno la imagen grafica al Sprite del jugador");


            Log.d("PonerJugador", "Le pongo su posicion inicial");

            Random r = new Random();
            int low = 1;
            int high = 5;
            int cuadrante = r.nextInt(high-low) + low;

            switch(cuadrante) {
                case 1:
                    int lowX = 0;
                    int highX = Math.round(_Pantalla.getWidth()/2);
                    int posicionX1 = r.nextInt(highX-lowX) + lowX;

                    int lowY = 0;
                    int highY = Math.round(_Pantalla.getHeight()/2);
                    int posicionY1 = r.nextInt(highY-lowY) + lowY;


                    Log.d("PosicionAperecida", "x:" + posicionX1 + "   y:" + posicionY1);
                    _Jugador= Sprite.sprite("jugador.jpg");
                    _Jugador.setPosition(posicionX1,posicionY1);

                    //Ahora lo movemos al punto mas lejano de este cuadrante
                    float verticeX=_Pantalla.getWidth();
                    float verticeY= _Pantalla.getHeight();
                    _Jugador.runAction(MoveTo.action(3,verticeX,verticeY));


                    break;
                case 2:
                    // code block
                    break;
                case 3:
                    // code block
                    break;
                case 4:
                    // code block
                    break;
            }

            Log.d("PonerJugador", "Lo agrego a la capa");
            super.addChild(_Jugador,10);
        }

        void ponerImagenFondo(){
            Sprite imagenFondo;
            Log.d("PonerFondo","Asigno el fondo del shiba gordito al sprite");
            imagenFondo= Sprite.sprite("shibagorditoFondo.jpg");

            Log.d("PonerFondo","Lo ubico");
            imagenFondo.setPosition(_Pantalla.getWidth()/2, _Pantalla.getHeight()/2);

            Log.d("PonerFondo","Escalo al perro de fondo");
            float factorAncho, factorAlto;
            factorAncho= _Pantalla.getWidth()/imagenFondo.getWidth();
            factorAlto=  _Pantalla.getHeight()/imagenFondo.getHeight();
            Log.d("PonerFondo", "Lo escalo para que ocupe toda la pantalla");
            imagenFondo.runAction(ScaleBy.action(0.01f,factorAncho,factorAlto));


            super.addChild(imagenFondo,-10);
        }
    }
}
