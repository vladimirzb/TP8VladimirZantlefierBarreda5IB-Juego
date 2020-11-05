package com.example.tp6vladimirzantlefierbarreda5ib;

import android.media.MediaPlayer;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.instant.CallFunc;
import org.cocos2d.actions.instant.CallFuncN;
import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.menus.Menu;
import org.cocos2d.menus.MenuItemImage;
import org.cocos2d.nodes.CocosNode;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class clsSplashScreen {
    CCGLSurfaceView _VistaDelJuego;
    CCSize _Pantalla;
    MediaPlayer _musicaDeFondo;



    public  clsSplashScreen(CCGLSurfaceView VistaDelJuego)
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

    class capaJuego extends Layer {

        public capaJuego()
        {
            ponerMusicaDeFondo();
            Log.d("CapaJuego", "Bob nos construye la capita");

            ponerBotones();

            Log.d("CapaJuego", "Ubico al shiba gordo de fondo");
            ponerImagenFondo();


            Log.d("CapaJuego", "Habilito el touch");
            setIsTouchEnabled(true);

        }

        void ponerMusicaDeFondo()
        {

            _musicaDeFondo= MediaPlayer.create(Director.sharedDirector().getActivity(),R.raw.musicadefondo);
            _musicaDeFondo.setLooping(true);
            _musicaDeFondo.start();
        }

        void sonidoExplosion()
        {
            MediaPlayer _ExplosionSonido;
            _ExplosionSonido= MediaPlayer.create(Director.sharedDirector().getActivity(),R.raw.explosion);
            _ExplosionSonido.setLooping(false);
            _ExplosionSonido.start();
        }

        void ponerBotones()
        {
            int posicionX1 = Math.round(_Pantalla.getWidth() / 2);
            int posicionY1 = Math.round(_Pantalla.getHeight() / 2);

            Log.d("PonerBotones", "Voy a crear el boton de disparo");
            MenuItemImage botonPlay;
            botonPlay= MenuItemImage.item("play.png", "play.png",this,"presionaBotonPlay");

            Log.d("PonerBotones", "Voy a crear el boton de play");
            float posicionBotonX, posicionBotonY;
            posicionBotonX= botonPlay.getWidth();
            posicionBotonY= botonPlay.getHeight();
            botonPlay.setPosition(posicionX1,posicionY1);



            Menu menuDeBoton;
            menuDeBoton= Menu.menu(botonPlay);
            menuDeBoton.setPosition(0,0);
            super.addChild(menuDeBoton);
        }

        public  void presionaBotonPlay()
        {
            Log.d("BotonPlay", "Se presiono el boton de play");
            _musicaDeFondo.stop();
            MainActivity activida = (MainActivity) Director.sharedDirector().getActivity();
            activida.Juego();

        }




        void ponerImagenFondo(){
            Sprite imagenFondo;
            Log.d("PonerFondo","Asigno el fondo del shiba gordito al sprite");
            imagenFondo= Sprite.sprite("marfondo.png");

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
