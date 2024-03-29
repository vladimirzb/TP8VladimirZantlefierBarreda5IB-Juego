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

public class clsJuego {
    MediaPlayer _musicaDeFondo;
    CCGLSurfaceView _VistaDelJuego;
    Boolean _estaTocandoAlJugador;
    CCSize _Pantalla;
    Sprite _Jugador;
    ArrayList _listaEnemigos;
    ArrayList _listaCorazones;
    //
    ArrayList _listaSprites;

    int _vidaJugador=3;
    int _contHastaTantoVIDAVERIFICADOR;
    //
    Label _PuntajeLabel;
    Label _vidaLabel;

    int _contPuntaje=0;

    ///Complejidad creciente
    int _contHastaTanto;
    float _velocidadMoveTo=3;
    float _VelocidadAparecerEnemigo=3;

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
            ponerMusicaDeFondo();
            Log.d("CapaJuego", "Bob nos construye la capita");

            Log.d("CapaJuego", "Voy a ubicar el jugador en su posicion inicial");
            ponerJugador(3f);

            Log.d("CapaJuego", "LLamo al schedule del enemigo");
            _listaEnemigos = new ArrayList();
            super.schedule("ponerEnemigo",_VelocidadAparecerEnemigo);
            super.schedule("ponerEnemigo2",_VelocidadAparecerEnemigo);
            super.schedule("ponerEnemigo3",_VelocidadAparecerEnemigo);

            //PUNTAJE LOGICA
            Log.d("CapaJuego", "LLamo a poner puntaje");
            ponerPuntaje(1.0f);
            super.schedule("puntajeContador",1.0f);

            //VIDA
            _listaCorazones = new ArrayList();
            Log.d("CapaJuego", "LLamo a poner vida");
            ponerVida(1.0f);
            super.schedule("vidaVerificador",1.0f);
            super.schedule("detectarColisionesCORAZONES", 0.25f);



            ///Complejidad creciente
            super.schedule("contarHastaTanto",1.0f);


            Log.d("CapaJuego", "Ubico al shiba gordo de fondo");
            ponerImagenFondo();

            Log.d("CapaJuego", "Inicio el verificador de colisiones");
            super.schedule("detectarColisiones", 0.2f);

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
        public  void vidaVerificador(float diferenciaTiempo)
        {

                if (_vidaJugador<3 && _listaCorazones.size() ==0 )
                {
                    _contHastaTantoVIDAVERIFICADOR ++ ;
                    Log.d("vidaVerificador","contador:" + _contHastaTantoVIDAVERIFICADOR);
                    if (_contHastaTantoVIDAVERIFICADOR==5)
                    {
                        Log.d("vidaVerificador","Pongo corazon");
                        ponerCorazon();
                        _contHastaTantoVIDAVERIFICADOR=0;
                    }
                }
        }

        public void ponerCorazon()
        {
            Sprite _Corazon;
            Log.d("PonerCorazon", "Le asigno la imagen grafica al Sprite del jugador");
            _Corazon= Sprite.sprite("heart_020.png");
            int alturaJugadorArreglador1= Math.round(_Corazon.getHeight()/2);
            int anchoJugadorArreglador1= Math.round(_Corazon.getWidth()/2);

            Log.d("PonerCorazon", "Le pongo su posicion inicial");

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

                    _Corazon.setPosition(posicionX1, posicionY1);


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

                    _Corazon.setPosition(posicionX2, posicionY2);

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

                    _Corazon.setPosition(posicionX3, posicionY3);

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

                    _Corazon.setPosition(posicionX4, posicionY4);

                    break;
            }

            Log.d("PonerCorazon", "Lo agrego a la capa");
            _listaCorazones.add(_Corazon);
            super.addChild(_Corazon,10);


        }

        public void ponerPuntaje(float diferenciaTiempo)
        {
            Log.d("PonerPuntaje", "Creo el label para el puntaje");
            _PuntajeLabel= Label.label("Puntaje:0", "PUNTAJE" ,70);

            Log.d("PonerPuntaje", "Lo ubico arriba centrado");
            _PuntajeLabel.setPosition(_Pantalla.getWidth()/2,_Pantalla.getHeight() - _PuntajeLabel.getHeight()/2);

            Log.d("PonerPuntaje", "Le pongo un color");
            CCColor3B colorPuntaje;
            colorPuntaje= new CCColor3B(128,255,255);
            _PuntajeLabel.setColor(colorPuntaje);


            Log.d("PonerPuntaje", "Lo agrego a la capa");
            super.addChild(_PuntajeLabel,100);

        }
        public void ponerVida(float diferenciaTiempo)
        {
            Log.d("PonerPuntaje", "Creo el label para el puntaje");
            _vidaLabel= Label.label("Vida: 3/3", "PUNTAJE" ,70);

            Log.d("PonerPuntaje", "Lo ubico arriba centrado");
            _vidaLabel.setPosition(_PuntajeLabel.getPositionX(),_PuntajeLabel.getPositionY() - _vidaLabel.getHeight()/2);

            Log.d("PonerPuntaje", "Le pongo un color");
            CCColor3B colorPuntaje;
            colorPuntaje= new CCColor3B(128,255,255);
            _vidaLabel.setColor(colorPuntaje);


            Log.d("PonerPuntaje", "Lo agrego a la capa");
            super.addChild(_vidaLabel,100);

        }

        public void ActualizarVida()
        {

            if (_vidaJugador!=0)
            {
                _vidaLabel.setString("Vida:" + _vidaJugador +  "/3");

            }
            else
            {
                _vidaLabel.setString("MORISTE");
                Log.d("BotonPlay", "Se presiono el boton de play");
                _musicaDeFondo.stop();
                MainActivity activida = (MainActivity) Director.sharedDirector().getActivity();
                activida.GameOver();
            }


        }

        public void RestarVida()
        {


            if (_vidaJugador!=0)
            {
                _vidaJugador = _vidaJugador - 1;

            }
        }

        public void SumarVida()
        {


            if (_vidaJugador<3)
            {
                _vidaJugador = _vidaJugador + 1;

            }
        }


        public  void puntajeContador(float diferenciaTiempo)
        {
            _contPuntaje++;
            _PuntajeLabel.setString("Puntaje:" + _contPuntaje );
        }



        public  void contarHastaTanto(float diferenciaTiempo)
        {
            _contHastaTanto++;
            Log.d("contarHastaTanto", "Contador Hasta tanto: " + _contHastaTanto);

            if (_contHastaTanto== 6)
            {
                if (_velocidadMoveTo>0.9f && _VelocidadAparecerEnemigo>0.9f )
                {
                    //Que enemigos se muevan mas rapido
                    _velocidadMoveTo = _velocidadMoveTo - 0.1f;

                    //Que enemigos aparezcan mas rapidos
                    _VelocidadAparecerEnemigo= _VelocidadAparecerEnemigo - 0.1f;
                    super.unschedule("ponerEnemigo");
                    super.schedule("ponerEnemigo",_VelocidadAparecerEnemigo);

                    super.unschedule("ponerEnemigo2");
                    super.schedule("ponerEnemigo2",_VelocidadAparecerEnemigo);

                    super.unschedule("ponerEnemigo3");
                    super.schedule("ponerEnemigo3",_VelocidadAparecerEnemigo);

                }

                //Reiniciar contador
                _contHastaTanto=0;
                Log.d("contarHastaTanto", "Contador Hasta se reseteo a 0 tanto: " + _contHastaTanto);
                Log.d("contarHastaTanto", "Velocidad enemigos " + _velocidadMoveTo);
                Log.d("contarHastaTanto", "Velocidad aparecer enemigos " + _VelocidadAparecerEnemigo);
            }
        }
        public void ponerJugador(float diferenciaTiempo)
        {
            Log.d("PonerJugador", "Le asigno la imagen grafica al Sprite del jugador");
            _Jugador= Sprite.sprite("Aircraft_01_x2.png");
            int alturaJugadorArreglador1= Math.round(_Jugador.getHeight()/2);
            int anchoJugadorArreglador1= Math.round(_Jugador.getWidth()/2);


            Log.d("PonerJugador", "Le pongo su posicion inicial");
            int posicionX1 = Math.round(_Pantalla.getWidth() / 2);
            int posicionY1 = Math.round(_Pantalla.getHeight() / 2);
            _Jugador.setPosition(posicionX1, posicionY1);


            Log.d("PonerJugador", "Lo agrego a la capa");
            super.addChild(_Jugador,10);


        }

        public void ponerEnemigo(float diferenciaTiempo)
        {
            Sprite _Enemigo;
            Log.d("PonerJugador", "Le asigno la imagen grafica al Sprite del jugador");
            _Enemigo= Sprite.sprite("balaCuadrada.png");
            int alturaJugadorArreglador1= Math.round(_Enemigo.getHeight()/2);
            int anchoJugadorArreglador1= Math.round(_Enemigo.getWidth()/2);

            Log.d("PonerJugador", "Le pongo su posicion inicial");

              Random r = new Random();
            int low = 1;
            int high = 5;
            int cuadrante = r.nextInt(high-low) + low;

            ///HARDCODEADO
//            int cuadrante= 4;

            //Numeros de cuadrantes LOGICA MODIFICADA DE LOS TPS ANTERIORES, enemigos aparecen afuera de la pantalla (cuadrado representa pantalla)
               //1
            ////////
        //4 //    //  //2
            //    //
            ////////
              //3
            //Numeros de cuadrante voy a utilizar cuadrantes como en el TP 5 ya que me permite que jamas salga de la pantalla la imagen

            switch(cuadrante) {
                case 1:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 1");
                    int lowX1 = 0 + anchoJugadorArreglador1;
                    int highX1 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                    int posicionX1 = r.nextInt(highX1 - lowX1) + lowX1;

                    int posicionY1 = Math.round(_Pantalla.getHeight()) + alturaJugadorArreglador1 ;


                    Log.d("PosicionAperecida", "x:" + posicionX1 + "   y:" + posicionY1);

                    _Enemigo.setPosition(posicionX1, posicionY1);


                    break;
                case 2:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 2");
                    int posicionX2 = Math.round(_Pantalla.getWidth()) + anchoJugadorArreglador1;

                    int lowY2 = 0 + alturaJugadorArreglador1;
                    int highY2 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                    int posicionY2 = r.nextInt(highY2 - lowY2) + lowY2;


                    Log.d("PosicionAperecida", "x:" + posicionX2 + "   y:" + posicionY2);

                    _Enemigo.setPosition(posicionX2, posicionY2);

                    break;
                case 3:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 3");
                    int lowX3 = 0 + anchoJugadorArreglador1;
                    int highX3 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                    int posicionX3 = r.nextInt(highX3 - lowX3) + lowX3;

                    int posicionY3 = 0 - alturaJugadorArreglador1 ;


                    Log.d("PosicionAperecida", "x:" + posicionX3 + "   y:" + posicionY3);

                    _Enemigo.setPosition(posicionX3, posicionY3);

                    break;
                case 4:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 4");
                    int posicionX4 = 0 - anchoJugadorArreglador1;

                    int lowY4 = 0 + alturaJugadorArreglador1;
                    int highY4 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                    int posicionY4 = r.nextInt(highY4 - lowY4) + lowY4;


                    Log.d("PosicionAperecida", "x:" + posicionX4 + "   y:" + posicionY4);

                    _Enemigo.setPosition(posicionX4, posicionY4);


                    break;
            }


            float posX =  _Jugador.getPositionX();
            float posY = _Jugador.getPositionY();
            //Secuencias llamo a funcion para que se muera y recibe como paremtro el sprite que la llamo

            Log.d("PonerEnemigo", "Comienzo la secuencia");
            CallFuncN removerEnemigoFuncN;
            removerEnemigoFuncN = CallFuncN.action(this, "removerEnemigo");

            IntervalAction secuenciaEnFormaCuadrado;
            MoveTo irAJugador =  MoveTo.action(_velocidadMoveTo,posX,posY);
            secuenciaEnFormaCuadrado= Sequence.actions(irAJugador,removerEnemigoFuncN);
            _Enemigo.runAction(secuenciaEnFormaCuadrado);
            Log.d("PonerEnemigo", "Tamaño lista enemigos:" + _listaEnemigos.size());
            _listaEnemigos.add(_Enemigo);
            Log.d("PonerEnemigo", "Lo agrego a la capa");
            super.addChild(_Enemigo,10);

            
        }
        public void ponerEnemigo2(float diferenciaTiempo)
        {
            Sprite _Enemigo;
            Log.d("PonerEnemigo2", "Le asigno la imagen grafica al Sprite del PonerEnemigo2");
            _Enemigo= Sprite.sprite("balaCuadrada.png");
            int alturaJugadorArreglador1= Math.round(_Enemigo.getHeight()/2);
            int anchoJugadorArreglador1= Math.round(_Enemigo.getWidth()/2);

            Log.d("PonerEnemigo2", "Le pongo su posicion inicial");

            Random r = new Random();
            int low = 1;
            int high = 5;
            int cuadrante = r.nextInt(high-low) + low;

            ///HARDCODEADO
//            int cuadrante= 4;

            //Numeros de cuadrantes LOGICA MODIFICADA DE LOS TPS ANTERIORES, enemigos aparecen afuera de la pantalla (cuadrado representa pantalla)
            //1
            ////////
            //4 //    //  //2
            //    //
            ////////
            //3
            //Numeros de cuadrante voy a utilizar cuadrantes como en el TP 5 ya que me permite que jamas salga de la pantalla la imagen

            switch(cuadrante) {
                case 1:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 1");
                    int lowX1 = 0 + anchoJugadorArreglador1;
                    int highX1 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                    int posicionX1 = r.nextInt(highX1 - lowX1) + lowX1;

                    int posicionY1 = Math.round(_Pantalla.getHeight()) + alturaJugadorArreglador1 ;


                    Log.d("PosicionAperecida", "x:" + posicionX1 + "   y:" + posicionY1);

                    _Enemigo.setPosition(posicionX1, posicionY1);


                    break;
                case 2:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 2");
                    int posicionX2 = Math.round(_Pantalla.getWidth()) + anchoJugadorArreglador1;

                    int lowY2 = 0 + alturaJugadorArreglador1;
                    int highY2 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                    int posicionY2 = r.nextInt(highY2 - lowY2) + lowY2;


                    Log.d("PosicionAperecida", "x:" + posicionX2 + "   y:" + posicionY2);

                    _Enemigo.setPosition(posicionX2, posicionY2);

                    break;
                case 3:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 3");
                    int lowX3 = 0 + anchoJugadorArreglador1;
                    int highX3 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                    int posicionX3 = r.nextInt(highX3 - lowX3) + lowX3;

                    int posicionY3 = 0 - alturaJugadorArreglador1 ;


                    Log.d("PosicionAperecida", "x:" + posicionX3 + "   y:" + posicionY3);

                    _Enemigo.setPosition(posicionX3, posicionY3);

                    break;
                case 4:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 4");
                    int posicionX4 = 0 - anchoJugadorArreglador1;

                    int lowY4 = 0 + alturaJugadorArreglador1;
                    int highY4 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                    int posicionY4 = r.nextInt(highY4 - lowY4) + lowY4;


                    Log.d("PosicionAperecida", "x:" + posicionX4 + "   y:" + posicionY4);

                    _Enemigo.setPosition(posicionX4, posicionY4);


                    break;
            }


            float posX =  _Jugador.getPositionX();
            float posY = _Jugador.getPositionY();
            //Secuencias llamo a funcion para que se muera y recibe como paremtro el sprite que la llamo

            Log.d("PonerEnemigo2", "Comienzo la secuencia");
            CallFuncN removerEnemigoFuncN;
            removerEnemigoFuncN = CallFuncN.action(this, "removerEnemigo");

            IntervalAction secuenciaEnFormaCuadrado;
            MoveTo irAJugador =  MoveTo.action(_velocidadMoveTo,posX,posY);
            secuenciaEnFormaCuadrado= Sequence.actions(irAJugador,removerEnemigoFuncN);
            _Enemigo.runAction(secuenciaEnFormaCuadrado);
            Log.d("PonerEnemigo2", "Tamaño lista enemigos:" + _listaEnemigos.size());
            _listaEnemigos.add(_Enemigo);
            Log.d("PonerEnemigo2", "Lo agrego a la capa");
            super.addChild(_Enemigo,10);


        }
        public void ponerEnemigo3(float diferenciaTiempo)
        {
            Sprite _Enemigo;
            Log.d("PonerEnemigo3", "Le asigno la imagen grafica al Sprite del PonerEnemigo2");
            _Enemigo= Sprite.sprite("balaCuadrada.png");
            int alturaJugadorArreglador1= Math.round(_Enemigo.getHeight()/2);
            int anchoJugadorArreglador1= Math.round(_Enemigo.getWidth()/2);

            Log.d("PonerEnemigo3", "Le pongo su posicion inicial");

            Random r = new Random();
            int low = 1;
            int high = 5;
            int cuadrante = r.nextInt(high-low) + low;

            ///HARDCODEADO
//            int cuadrante= 4;

            //Numeros de cuadrantes LOGICA MODIFICADA DE LOS TPS ANTERIORES, enemigos aparecen afuera de la pantalla (cuadrado representa pantalla)
            //1
            ////////
            //4 //    //  //2
            //    //
            ////////
            //3
            //Numeros de cuadrante voy a utilizar cuadrantes como en el TP 5 ya que me permite que jamas salga de la pantalla la imagen

            switch(cuadrante) {
                case 1:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 1");
                    int lowX1 = 0 + anchoJugadorArreglador1;
                    int highX1 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                    int posicionX1 = r.nextInt(highX1 - lowX1) + lowX1;

                    int posicionY1 = Math.round(_Pantalla.getHeight()) + alturaJugadorArreglador1 ;


                    Log.d("PosicionAperecida", "x:" + posicionX1 + "   y:" + posicionY1);

                    _Enemigo.setPosition(posicionX1, posicionY1);


                    break;
                case 2:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 2");
                    int posicionX2 = Math.round(_Pantalla.getWidth()) + anchoJugadorArreglador1;

                    int lowY2 = 0 + alturaJugadorArreglador1;
                    int highY2 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                    int posicionY2 = r.nextInt(highY2 - lowY2) + lowY2;


                    Log.d("PosicionAperecida", "x:" + posicionX2 + "   y:" + posicionY2);

                    _Enemigo.setPosition(posicionX2, posicionY2);

                    break;
                case 3:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 3");
                    int lowX3 = 0 + anchoJugadorArreglador1;
                    int highX3 = Math.round(_Pantalla.getWidth()) - anchoJugadorArreglador1;
                    int posicionX3 = r.nextInt(highX3 - lowX3) + lowX3;

                    int posicionY3 = 0 - alturaJugadorArreglador1 ;


                    Log.d("PosicionAperecida", "x:" + posicionX3 + "   y:" + posicionY3);

                    _Enemigo.setPosition(posicionX3, posicionY3);

                    break;
                case 4:
                    Log.d("Cuadrante", "El sprite enemigo aparecion en el cuadrante 4");
                    int posicionX4 = 0 - anchoJugadorArreglador1;

                    int lowY4 = 0 + alturaJugadorArreglador1;
                    int highY4 = Math.round(_Pantalla.getHeight()) - alturaJugadorArreglador1;
                    int posicionY4 = r.nextInt(highY4 - lowY4) + lowY4;


                    Log.d("PosicionAperecida", "x:" + posicionX4 + "   y:" + posicionY4);

                    _Enemigo.setPosition(posicionX4, posicionY4);


                    break;
            }


            float posX =  _Jugador.getPositionX();
            float posY = _Jugador.getPositionY();
            //Secuencias llamo a funcion para que se muera y recibe como paremtro el sprite que la llamo

            Log.d("PonerEnemigo3", "Comienzo la secuencia");
            CallFuncN removerEnemigoFuncN;
            removerEnemigoFuncN = CallFuncN.action(this, "removerEnemigo");

            IntervalAction secuenciaEnFormaCuadrado;
            MoveTo irAJugador =  MoveTo.action(_velocidadMoveTo,posX,posY);
            secuenciaEnFormaCuadrado= Sequence.actions(irAJugador,removerEnemigoFuncN);
            _Enemigo.runAction(secuenciaEnFormaCuadrado);
            Log.d("PonerEnemigo3", "Tamaño lista enemigos:" + _listaEnemigos.size());
            _listaEnemigos.add(_Enemigo);
            Log.d("PonerEnemigo3", "Lo agrego a la capa");
            super.addChild(_Enemigo,10);


        }

        public void removerEnemigo(CocosNode objetollamador)
        {
            Log.d("PonerEnemigo", "BorroEnemigo");
            _listaEnemigos.remove(objetollamador);
            super.removeChild(objetollamador, true);
        }

        public void VerificarColliderEnemigosYJugador(float diferenciaTiempo)
        {

        }


        public void detectarColisiones(float deltaTiempo)
        {
            Log.d("DetectarColisiones", "Me fijo si algun enemigo choco al jugador");
            boolean huboColision;
            huboColision=false;

            ArrayList spritesQueImpactaron;
            spritesQueImpactaron= new ArrayList();
            Log.d("DetectarColisiones", "Me quedan antes de eliminar:" + _listaEnemigos.size());

            for (int punteroSprite=0; punteroSprite<_listaEnemigos.size(); punteroSprite++){
                Log.d("DetectarColisiones", "Verifico el enemigo numero" + punteroSprite);

                Sprite unSpriteAVerificar;
                unSpriteAVerificar= (Sprite) _listaEnemigos.get(punteroSprite);
                if(InterseccionEntreSprites(_Jugador,unSpriteAVerificar)){
                    unSpriteAVerificar.setPosition(-1000,-1000);
                    huboColision=true;
                    Log.d("DetectarColisiones", "posicion sprite a verificar X=" + unSpriteAVerificar.getPositionX() + "POS Y:" +unSpriteAVerificar.getPositionY());
                    Log.d("DetectarColisiones", "posicion jugador X=" + _Jugador.getPositionX() + "POS Y:" + _Jugador.getPositionY());
                    super.removeChild(unSpriteAVerificar,true);
                    Log.d("DetectarColisiones", "Choque contra el sprite:" + punteroSprite );
                    spritesQueImpactaron.add(punteroSprite);
                }
            }

            if (huboColision==true)
            {
                Log.d("DetectarColisiones", "Hubo una colision");
                Log.d("DetectarColisiones", "Me quedan en sprites que impactaron" + spritesQueImpactaron.size());

                for (int punteroSprite=spritesQueImpactaron.size()-1; punteroSprite>=0; punteroSprite--)
                {
                    _listaEnemigos.remove(punteroSprite);
                }

                sonidoExplosion();
                RestarVida();
                ActualizarVida();
                Log.d("DetectarColisiones", "Me quedan:" + _listaEnemigos.size());
            }

        }
        public void detectarColisionesCORAZONES(float deltaTiempo)
        {
            Log.d("DetectarColisionesCORAZ", "Me fijo si algun enemigo choco al jugador");
            boolean huboColision;
            huboColision=false;

            ArrayList spritesQueImpactaron;
            spritesQueImpactaron= new ArrayList();


            for (int punteroSprite=0; punteroSprite<_listaCorazones.size(); punteroSprite++){
                Log.d("DetectarColisionesCORAZ", "Verifico el corazon numero" + punteroSprite);

                Sprite unSpriteAVerificar;
                unSpriteAVerificar= (Sprite) _listaCorazones.get(punteroSprite);
                if(InterseccionEntreSprites(_Jugador,unSpriteAVerificar)){
                    unSpriteAVerificar.setPosition(-1000,-1000);
                    huboColision=true;
                    super.removeChild(unSpriteAVerificar,true);
                    spritesQueImpactaron.add(punteroSprite);
                }
            }

            if (huboColision==true)
            {
                Log.d("DetectarColisionesCORAZ", "Hubo una colision");

                for (int punteroSprite=spritesQueImpactaron.size()-1; punteroSprite>=0; punteroSprite--)
                {
                    _listaCorazones.remove(punteroSprite);
                }

                SumarVida();
                ActualizarVida();
                Log.d("DetectarColisionesCORAZ", "Me quedan:" + _listaCorazones.size());
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

                //IMPORTANTE
                //ESTE CODIGO LO VAMOS A MODIFICAR EN INTERSECCION ENTE SPRITES PARA CUANDO EL ENEMIGO TOQUE AL JUGADOR PASE ALGO
//                if (InterseccionEntreSprites(_Jugador,_Enemigo))
//                {
//                    Log.d("COLLIDER","El jugador1 toco jugador2");
//                    Log.d("COLLIDER","Activo la secuncia jugador 2");
//                    MoveBy moverseArriba, moverseAbajo, moverseDerecha, moverseIzquierda;
//                    moverseArriba= MoveBy.action(1,0,300);
//                    moverseAbajo= MoveBy.action(1,0,-300);
//                    moverseDerecha= MoveBy.action(1,200,0);
//                    moverseIzquierda= MoveBy.action(1,-200,0);
//
//                    IntervalAction secuenciaEnFormaCuadrado;
//                    secuenciaEnFormaCuadrado= Sequence.actions(moverseArriba,moverseDerecha,moverseAbajo,moverseIzquierda);
//                    _Enemigo.runAction(secuenciaEnFormaCuadrado);
//                }
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


            return true;
        }

        void moverJugador(float xAmover, float yAmover)
        {
            Log.d("MoverJugador" , "Me pidieron que me ubique en X:" +xAmover + "Y:" + yAmover);
            _Jugador.setPosition(xAmover,yAmover);
        }


    }




}
