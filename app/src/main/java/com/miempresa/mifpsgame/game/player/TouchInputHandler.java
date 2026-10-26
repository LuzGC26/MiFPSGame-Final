package com.miempresa.mifpsgame.game.player;

import com.jme3.input.controls.TouchListener;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

public class TouchInputHandler implements TouchListener {
    private PlayerControl playerControl;
    private Camera cam;

    private float joystickCenterX, joystickCenterY;
    private int moveTouchId = -1;
    private int lookTouchId = -1;
    private float prevLookX, prevLookY;
    private float lookSensitivity = 0.003f; // Sensibilidad de la cámara
    private float screenWidth;

    public TouchInputHandler(PlayerControl playerControl, Camera cam, float screenWidth) {
        this.playerControl = playerControl;
        this.cam = cam;
        this.screenWidth = screenWidth;
    }

    @Override
    public void onTouch(String name, TouchEvent evt, float tpf) {
        switch (evt.getType()) {
            case DOWN:
                if (evt.getX() < screenWidth / 2) {
                    // Mitad izquierda: joystick de movimiento
                    moveTouchId = evt.getPointerId();
                    joystickCenterX = evt.getX();
                    joystickCenterY = evt.getY();
                } else {
                    // Mitad derecha: apuntar con la cámara
                    lookTouchId = evt.getPointerId();
                    prevLookX = evt.getX();
                    prevLookY = evt.getY();
                }
                break;

            case MOVE:
                if (evt.getPointerId() == moveTouchId) {
                    // Calcular cuánto se movió el dedo desde el centro (simulando un joystick)
                    float dx = evt.getX() - joystickCenterX;
                    float dy = evt.getY() - joystickCenterY;

                    // Activar el movimiento si el dedo se desliza lo suficiente (50 píxeles de margen)
                    playerControl.moveRight = dx > 50;
                    playerControl.moveLeft = dx < -50;
                    playerControl.moveForward = dy > 50;
                    // En Android las coordenadas Y están invertidas a veces dependiendo de la pantalla,
                    // ajustamos el movimiento hacia atrás.
                    playerControl.moveBack = dy < -50;

                } else if (evt.getPointerId() == lookTouchId) {
                    float dx = evt.getX() - prevLookX;
                    float dy = evt.getY() - prevLookY;

                    // Rotar cámara de lado a lado (X) y de arriba a abajo (Y)
                    Quaternion rotX = new Quaternion().fromAngleAxis(-dx * lookSensitivity, Vector3f.UNIT_Y);
                    Quaternion rotY = new Quaternion().fromAngleAxis(dy * lookSensitivity, cam.getLeft());

                    Vector3f newDirection = rotX.mult(rotY.mult(cam.getDirection()));
                    cam.lookAtDirection(newDirection, Vector3f.UNIT_Y);

                    prevLookX = evt.getX();
                    prevLookY = evt.getY();
                }
                break;

            case UP:
                if (evt.getPointerId() == moveTouchId) {
                    // Soltamos el joystick izquierdo, detener al jugador
                    moveTouchId = -1;
                    playerControl.moveForward = false;
                    playerControl.moveBack = false;
                    playerControl.moveLeft = false;
                    playerControl.moveRight = false;
                } else if (evt.getPointerId() == lookTouchId) {
                    // Soltamos la cámara
                    lookTouchId = -1;
                }
                break;
        }
    }
}