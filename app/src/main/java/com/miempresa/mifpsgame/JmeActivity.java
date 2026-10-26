package com.miempresa.mifpsgame;

import com.jme3.app.AndroidHarness;
import android.os.Bundle;

public class JmeActivity extends AndroidHarness {
    public JmeActivity() {
        // Clase principal del juego jME
        appClass = "com.miempresa.mifpsgame.game.FPSGame";
        // Configuracion de pantalla
        eglBitsPerPixel = 24;
        eglAlphaBits = 0;
        eglDepthBits = 16;
        eglSamples = 0;
        eglStencilBits = 0;
        // Pantalla completa sin titulo
        screenFullScreen = true;
        // Orientacion horizontal
        mouseEventsEnabled = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
} // <--- Esta es la llave que te faltaba