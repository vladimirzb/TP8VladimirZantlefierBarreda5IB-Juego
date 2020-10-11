package com.example.tp6vladimirzantlefierbarreda5ib;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.IntervalAction;
import org.cocos2d.actions.interval.MoveBy;
import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.actions.interval.Sequence;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCSize;

import java.util.ArrayList;
import java.util.Random;

public class clsJuego {
    CCGLSurfaceView _VistaDelJuego;
    Boolean _estaTocandoAlJugador;
    Boolean _estaTocandoAlJugador2;
    CCSize _Pantalla;
    Sprite _Jugador;
    Sprite _Jugador2;
    ArrayList _listaSprites;

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
            ponerJugador(3f);

            Log.d("CapaJuego", "Voy a ubicar el segundo jugador en su posicion inicial");
            ponerJugador2(3f);


            Log.d("CapaJuego", "Ubico al shiba gordo de fondo");
            ponerImagenFondo();

//            Log.d("CapaJuego", "Inicio el verificador de colisiones");
//            super.schedule("detectarColisiones", 0.25f);

            Log.d("CapaJuego", "Habilito el touch");
            setIsTouchEnabled(true);

        }

        public void ponerJugador(float diferenciaTiempo)
        {
                Log.d("PonerJugador", "Le asigno la imagen grafica al Sprite del jugador");
                _Jugador= Sprite.sprite("jugador.jpg");
                int alturaJugadorArreglador1= Math.round(_Jugador.getHeight()/2);
                int anchoJugadorArreglador1= Math.round(_Jugador.getWidth()/2);

                Log.d("PonerJugador", "Le pongo su posicion inicial");

                Random r = new Random();
                int low = 1;
                int high = 5;
                int cuadrante = r.nextInt(high-low) + low;

                //Numeros de cuadrantes
                //3 4 //
                //1 2 //
                //Numeros de cuadrante voy a utilizar cuadrantes como en el TP 5 ya que me permite que jamas salga de la pantalla la imagen
                switch(cuadrante) {
                    case 1:
                        Log.d("Cuadrante", "La imagen de pos random aparecion en el cuadrante 1");
                        int lowX1 = 0 + anchoJugadorArreglador1;
                        int highX1 = Math.round(_Pantalla.getWidth() / 2) - anchoJugadorArreglador1;
                        int posicionX1 = r.nextInt(highX1 - lowX1) + lowX1;

                        int lowY1 = 0 + alturaJugadorArreglador1;
                        int highY1 = Math.round(_Pantalla.getHeight() / 2) - alturaJugadorArreglador1;
                        int posicionY1 = r.nextInt(highY1 - lowY1) + lowY1;


                        Log.d("PosicionAperecida", "x:" + posicionX1 + "   y:" + posicionY1);

                        _Jugador.setPosition(posicionX1, posicionY1);


                        break;
                    case 2:
                        Log.d("Cuadrante", "La imagen de pos random aparecion en el cuadrante 2");
                        int lowX2 = Math.round(_Pantalla.getWidth() / 2) + anchoJugadorArreglador1;
                        int highX2 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                        int posicionX2 = r.nextInt(highX2 - lowX2) + lowX2;

                        int lowY2 = 0 + alturaJugadorArreglador1;
                        int highY2 = Math.round(_Pantalla.getHeight() / 2) - alturaJugadorArreglador1;
                        int posicionY2 = r.nextInt(highY2 - lowY2) + lowY2;


                        Log.d("PosicionAperecida", "x:" + posicionX2 + "   y:" + posicionY2);

                        _Jugador.setPosition(posicionX2, posicionY2);

                        break;
                    case 3:
                        Log.d("Cuadrante", "La imagen de pos random aparecion en el cuadrante 3");
                        int lowX3 = 0 + anchoJugadorArreglador1;
                        int highX3 = Math.round(_Pantalla.getWidth() / 2) - anchoJugadorArreglador1;
                        int posicionX3 = r.nextInt(highX3 - lowX3) + lowX3;

                        int lowY3 = Math.round(_Pantalla.getHeight() / 2) + alturaJugadorArreglador1;
                        int highY3 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                        int posicionY3 = r.nextInt(highY3 - lowY3) + lowY3;


                        Log.d("PosicionAperecida", "x:" + posicionX3 + "   y:" + posicionY3);

                        _Jugador.setPosition(posicionX3, posicionY3);

                        break;
                    case 4:
                        Log.d("Cuadrante", "La imagen de pos random aparecion en el cuadrante 4");
                        int lowX4 = Math.round(_Pantalla.getWidth() / 2) + anchoJugadorArreglador1;
                        int highX4 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                        int posicionX4 = r.nextInt(highX4 - lowX4) + lowX4;

                        int lowY4 = Math.round(_Pantalla.getHeight() / 2) + alturaJugadorArreglador1;
                        int highY4 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                        int posicionY4 = r.nextInt(highY4 - lowY4) + lowY4;


                        Log.d("PosicionAperecida", "x:" + posicionX4 + "   y:" + posicionY4);

                        _Jugador.setPosition(posicionX4, posicionY4);

                        break;
                    }

                Log.d("PonerJugador", "Lo agrego a la capa");
                super.addChild(_Jugador,10);


        }

        public void ponerJugador2(float diferenciaTiempo)
        {

                Log.d("PonerJugador", "Le asigno la imagen grafica al Sprite del jugador");
                _Jugador2= Sprite.sprite("jugador.jpg");
                int alturaJugadorArreglador1= Math.round(_Jugador2.getHeight()/2);
                int anchoJugadorArreglador1= Math.round(_Jugador2.getWidth()/2);

                Log.d("PonerJugador", "Le pongo su posicion inicial");

                Random r = new Random();
                int low = 1;
                int high = 5;
                int cuadrante = r.nextInt(high-low) + low;

                //Numeros de cuadrantes
                //3 4 //
                //1 2 //
                //Numeros de cuadrante voy a utilizar cuadrantes como en el TP 5 ya que me permite que jamas salga de la pantalla la imagen
                do {
                    switch(cuadrante) {
                        case 1:
                            Log.d("Cuadrante", "La imagen de pos random aparecion en el cuadrante 1");
                            int lowX1 = 0 + anchoJugadorArreglador1;
                            int highX1 = Math.round(_Pantalla.getWidth() / 2) - anchoJugadorArreglador1;
                            int posicionX1 = r.nextInt(highX1 - lowX1) + lowX1;

                            int lowY1 = 0 + alturaJugadorArreglador1;
                            int highY1 = Math.round(_Pantalla.getHeight() / 2) - alturaJugadorArreglador1;
                            int posicionY1 = r.nextInt(highY1 - lowY1) + lowY1;


                            Log.d("PosicionAperecida", "x:" + posicionX1 + "   y:" + posicionY1);

                            _Jugador2.setPosition(posicionX1, posicionY1);


                            break;
                        case 2:
                            Log.d("Cuadrante", "La imagen de pos random aparecion en el cuadrante 2");
                            int lowX2 = Math.round(_Pantalla.getWidth() / 2) + anchoJugadorArreglador1;
                            int highX2 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                            int posicionX2 = r.nextInt(highX2 - lowX2) + lowX2;

                            int lowY2 = 0 + alturaJugadorArreglador1;
                            int highY2 = Math.round(_Pantalla.getHeight() / 2) - alturaJugadorArreglador1;
                            int posicionY2 = r.nextInt(highY2 - lowY2) + lowY2;


                            Log.d("PosicionAperecida", "x:" + posicionX2 + "   y:" + posicionY2);

                            _Jugador2.setPosition(posicionX2, posicionY2);

                            break;
                        case 3:
                            Log.d("Cuadrante", "La imagen de pos random aparecion en el cuadrante 3");
                            int lowX3 = 0 + anchoJugadorArreglador1;
                            int highX3 = Math.round(_Pantalla.getWidth() / 2) - anchoJugadorArreglador1;
                            int posicionX3 = r.nextInt(highX3 - lowX3) + lowX3;

                            int lowY3 = Math.round(_Pantalla.getHeight() / 2) + alturaJugadorArreglador1;
                            int highY3 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                            int posicionY3 = r.nextInt(highY3 - lowY3) + lowY3;


                            Log.d("PosicionAperecida", "x:" + posicionX3 + "   y:" + posicionY3);

                            _Jugador2.setPosition(posicionX3, posicionY3);

                            break;
                        case 4:
                            Log.d("Cuadrante", "La imagen de pos random aparecion en el cuadrante 4");
                            int lowX4 = Math.round(_Pantalla.getWidth() / 2) + anchoJugadorArreglador1;
                            int highX4 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                            int posicionX4 = r.nextInt(highX4 - lowX4) + lowX4;

                            int lowY4 = Math.round(_Pantalla.getHeight() / 2) + alturaJugadorArreglador1;
                            int highY4 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                            int posicionY4 = r.nextInt(highY4 - lowY4) + lowY4;


                            Log.d("PosicionAperecida", "x:" + posicionX4 + "   y:" + posicionY4);

                            _Jugador2.setPosition(posicionX4, posicionY4);

                            break;
                    }

                }while (InterseccionEntreSprites(_Jugador,_Jugador2) == true);

                Log.d("PonerJugador2", "Lo agrego a la capa");
                super.addChild(_Jugador2,10);
        }

        public void detectarColisiones(float deltaTiempo)
        {
            Log.d("DetectarColisiones", "Me fijo si algun enemigo choco al jugador");
            boolean huboColision;
            huboColision=false;

            ArrayList spritesQueImpactaron;
            spritesQueImpactaron= new ArrayList();


            for (int punteroSprite=0; punteroSprite<_listaSprites.size(); punteroSprite++){
                Log.d("DetectarColisiones", "Verifico el enemigo numero" + punteroSprite);

                Sprite unSpriteAVerificar;
                unSpriteAVerificar= (Sprite) _listaSprites.get(punteroSprite);
                if(InterseccionEntreSprites(_Jugador,unSpriteAVerificar)){
                    huboColision=true;
                    super.removeChild(unSpriteAVerificar,true);
                    spritesQueImpactaron.add(punteroSprite);
                }
            }

            if (huboColision==true)
            {
                Log.d("DetectarColisiones", "Hubo una colision");

                for (int punteroSprite=spritesQueImpactaron.size()-1; punteroSprite>=0; punteroSprite--)
                {
                    _listaSprites.remove(punteroSprite);
                }
                Log.d("DetectarColisiones", "Me quedan:" + _listaSprites.size());
            }

        }
        public boolean InterseccionEntreSprites(Sprite Sprite1,
                                                Sprite Sprite2) {
            Boolean HayInterseccion=false;
//Determino los bordes de cada Sprite
            Float Sp1Arriba, Sp1Abajo, Sp1Derecha, Sp1Izquierda,
                    Sp2Arriba, Sp2Abajo, Sp2Derecha, Sp2Izquierda;

            Sp1Arriba=Sprite1.getPositionY() +
                    Sprite1.getHeight()/2;
            Sp1Abajo=Sprite1.getPositionY() -
                    Sprite1.getHeight()/2;
            Sp1Derecha=Sprite1.getPositionX() +
                    Sprite1.getWidth()/2;
            Sp1Izquierda=Sprite1.getPositionX() -
                    Sprite1.getWidth()/2;
            Sp2Arriba=Sprite2.getPositionY() +
                    Sprite2.getHeight()/2;
            Sp2Abajo=Sprite2.getPositionY() -
                    Sprite2.getHeight()/2;
            Sp2Derecha=Sprite2.getPositionX() +
                    Sprite2.getWidth()/2;
            Sp2Izquierda=Sprite2.getPositionX() -
                    Sprite2.getWidth()/2;
            Log.d("IntEntSprites", "Sp1 Arr: "+Sp1Arriba+" - Ab: "+Sp1Abajo+" - Der: "+Sp1Derecha+" - Izq: "+Sp1Izquierda);
            Log.d("IntEntSprites", "Sp2 Arr: "+Sp2Arriba+" - Ab: "+Sp2Abajo+" - Der: "+Sp2Derecha+" - Izq: "+Sp2Izquierda);

//Me fijo si el vértice superior derecho de Sp1 está dentro de Sp2
            if (Sp1Arriba>=Sp2Abajo && Sp1Arriba<=Sp2Arriba &&
                    Sp1Derecha>=Sp2Izquierda && Sp1Derecha<=Sp2Derecha) {
                HayInterseccion=true;
                Log.d("IntEntSprites", "Intersección caso 1");
            }
//Me fijo si el vértice superior izquierdo de Sp1 está dentro de Sp2
            if (Sp1Arriba>=Sp2Abajo && Sp1Arriba<=Sp2Arriba &&
                    Sp1Izquierda>=Sp2Izquierda && Sp1Izquierda<=Sp2Derecha) {
                HayInterseccion=true;
                Log.d("IntEntSprites", "Intersección caso 2");
            }
//Me fijo si el vértice inferior derecho de Sp1 está dentro de Sp2
            if (Sp1Abajo>=Sp2Abajo && Sp1Abajo<=Sp2Arriba &&
                    Sp1Derecha>=Sp2Izquierda && Sp1Derecha<=Sp2Derecha) {
                HayInterseccion=true;
                Log.d("IntEntSprites", "Intersección caso 3");
            }
//Me fijo si el vértice inferior izquierdo de Sp1 está dentro de Sp2

            if (Sp1Abajo>=Sp2Abajo && Sp1Abajo<=Sp2Arriba &&
                    Sp1Izquierda>=Sp2Izquierda && Sp1Izquierda<=Sp2Derecha) {
                HayInterseccion=true;
                Log.d("IntEntSprites", "Intersección caso 4");
            }
//Me fijo si el vértice superior derecho de Sp2 esta dentro de Sp1
            if (Sp2Arriba>=Sp1Abajo && Sp2Arriba<=Sp1Arriba &&
                    Sp2Derecha>=Sp1Izquierda && Sp2Derecha<=Sp1Derecha) {
                HayInterseccion=true;
                Log.d("IntEntSprites", "Intersección caso 5");
            }
//Me fijo si el vértice superior izquierdo de Sp1 está dentro de Sp2
            if (Sp2Arriba>=Sp1Abajo && Sp2Arriba<=Sp1Arriba &&
                    Sp2Izquierda>=Sp1Izquierda && Sp2Izquierda<=Sp1Derecha) {
                HayInterseccion=true;
                Log.d("IntEntSprites", "Intersección caso 6");
            }
//Me fijo si el vértice inferior derecho de Sp1 está dentro de Sp2
            if (Sp2Abajo>=Sp1Abajo && Sp2Abajo<=Sp1Arriba &&
                    Sp2Derecha>=Sp1Izquierda && Sp2Derecha<=Sp1Derecha) {
                HayInterseccion=true;
                Log.d("IntEntSprites", "Intersección caso 7");
            }
//Me fijo si el vértice inferior izquierdo de Sp1 está dentro de Sp2
            if (Sp2Abajo>=Sp1Abajo && Sp2Abajo<=Sp1Arriba &&
                    Sp2Izquierda>=Sp1Izquierda && Sp2Izquierda<=Sp1Derecha) {
                HayInterseccion=true;
                Log.d("IntEntSprites", "Intersección caso 8");
            }
            Log.d("IntEntSprites", "Hay intersección: "+HayInterseccion);
            return HayInterseccion;
        }
        public boolean InterseccionEntrePuntoySprite(Sprite SpriteAVerificar,
            Float puntoXAVerificar, Float puntoYAVerificar) {
            Boolean HayInterseccion=false;
            //Determino los bordes de cada Sprite
            Float SpArriba, SpAbajo, SpDerecha, SpIzquierda;
            SpArriba=SpriteAVerificar.getPositionY() +
                    SpriteAVerificar.getHeight()/2;
            SpAbajo=SpriteAVerificar.getPositionY() -
                    SpriteAVerificar.getHeight()/2;
            SpDerecha=SpriteAVerificar.getPositionX() +
                    SpriteAVerificar.getWidth()/2;
            SpIzquierda=SpriteAVerificar.getPositionX() -
                    SpriteAVerificar.getWidth()/2;

            Log.d("IntEntSpriteYPunto", "Sp Arr: "+SpArriba+" - Ab: "+SpAbajo+" - Der: "+SpDerecha+" - Izq:"+SpIzquierda);
            Log.d("IntEntSpriteYPunto", "X: "+puntoXAVerificar+" - Y: "+puntoYAVerificar);

            if (puntoXAVerificar>=SpIzquierda &&
                    puntoXAVerificar<=SpDerecha && puntoYAVerificar>=SpAbajo
                    && puntoYAVerificar<=SpArriba) {
                HayInterseccion=true;
            }
            Log.d("IntEntSpriteYPunto", "Hay intersección: "+HayInterseccion);
            return HayInterseccion;
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

        @Override
        public boolean ccTouchesBegan(MotionEvent event) {
            float xTocada, yTocada;
            xTocada= event.getX();
            yTocada=_Pantalla.getHeight()-event.getY();
            Log.d("ControlDeToque","Comienza toque: X:" + xTocada+ " - Y:" + yTocada);
            if (InterseccionEntrePuntoySprite(_Jugador, xTocada, yTocada))
            {
                moverJugador(xTocada,yTocada);
                _estaTocandoAlJugador=true;
            }
            else {
                _estaTocandoAlJugador=false;
            }

            if (InterseccionEntrePuntoySprite(_Jugador2, xTocada, yTocada))
            {
                moverJugador2(xTocada,yTocada);
                _estaTocandoAlJugador2=true;
            }
            else {
                _estaTocandoAlJugador2=false;
            }

            return true;
        }

        @Override
        public boolean ccTouchesMoved(MotionEvent event) {
            float xTocada, yTocada;
            xTocada= event.getX();
            yTocada=_Pantalla.getHeight()-event.getY();
            Log.d("ControlDeToque","Mueve toque: X:" + xTocada+ " - Y:" + yTocada);
            if (_estaTocandoAlJugador)
            {
                moverJugador(xTocada,yTocada);
                if (InterseccionEntreSprites(_Jugador,_Jugador2))
                {
                    Log.d("COLLIDER","El jugador1 toco jugador2");
                    Log.d("COLLIDER","Activo la secuncia jugador 2");
                    MoveBy moverseArriba, moverseAbajo, moverseDerecha, moverseIzquierda;
                    moverseArriba= MoveBy.action(1,0,300);
                    moverseAbajo= MoveBy.action(1,0,-300);
                    moverseDerecha= MoveBy.action(1,200,0);
                    moverseIzquierda= MoveBy.action(1,-200,0);

                    IntervalAction secuenciaEnFormaCuadrado;
                    secuenciaEnFormaCuadrado= Sequence.actions(moverseArriba,moverseDerecha,moverseAbajo,moverseIzquierda);
                    _Jugador2.runAction(secuenciaEnFormaCuadrado);
                }
            }
            if (_estaTocandoAlJugador2)
            {
                moverJugador2(xTocada,yTocada);
                if (InterseccionEntreSprites(_Jugador,_Jugador2))
                {
                    Log.d("COLLIDER","El jugador2 toco jugador1");
                    Log.d("COLLIDER","Activo la secuncia jugador 1");
                    MoveBy moverseArriba, moverseAbajo, moverseDerecha, moverseIzquierda;
                    moverseArriba= MoveBy.action(1,0,300);
                    moverseAbajo= MoveBy.action(1,0,-300);
                    moverseDerecha= MoveBy.action(1,200,0);
                    moverseIzquierda= MoveBy.action(1,-200,0);

                    IntervalAction secuenciaEnFormaCuadrado;
                    secuenciaEnFormaCuadrado= Sequence.actions(moverseArriba,moverseDerecha,moverseAbajo,moverseIzquierda);
                    _Jugador.runAction(secuenciaEnFormaCuadrado);

                }
            }
            return true;
        }

        @Override
        public boolean ccTouchesEnded(MotionEvent event) {
            float xTocada, yTocada;
            xTocada= event.getX();
            yTocada=_Pantalla.getHeight()-event.getY();
            Log.d("ControlDeToque","Final del toque: X:" + xTocada+ " - Y:" + yTocada);
            if (_estaTocandoAlJugador==true)
            {
                _estaTocandoAlJugador=false;
            }

            if (_estaTocandoAlJugador2==true)
            {
                _estaTocandoAlJugador2=false;
            }
            return true;
        }

        void moverJugador(float xAmover, float yAmover)
        {
            Log.d("MoverJugador" , "Me pidieron que me ubique en X:" +xAmover + "Y:" + yAmover);
            _Jugador.setPosition(xAmover,yAmover);
        }

        void moverJugador2(float xAmover, float yAmover)
        {
            Log.d("MoverJugador" , "Me pidieron que me ubique en X:" +xAmover + "Y:" + yAmover);
            _Jugador2.setPosition(xAmover,yAmover);
        }
    }




}
