package com.miempresa.mifpsgame.game.player;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;

public class PlayerControl {
    private SimpleApplication app;
    private BulletAppState bulletAppState;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();

    // Variables para saber hacia dónde nos movemos (las conectaremos a la pantalla táctil después)
    public boolean moveForward, moveBack, moveLeft, moveRight;
    private float playerSpeed = 8f;
    private float jumpForce = 12f;

    // Constructor que recibe la aplicación y las físicas
    public PlayerControl(SimpleApplication app, BulletAppState bulletAppState) {
        this.app = app;
        this.bulletAppState = bulletAppState;
    }

    public void initialize() {
        // Capsula de colision del jugador
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(0.5f, 1.8f, 1);
        player = new CharacterControl(capsule, 0.05f);
        player.setJumpSpeed(jumpForce);
        player.setFallSpeed(30f);
        player.setGravity(30f);
        player.setPhysicsLocation(new Vector3f(0f, 2f, 0f));

        // Agregar el jugador al mundo físico
        app.getRootNode().addControl(player);
        bulletAppState.getPhysicsSpace().add(player);

        // Colocar la cámara a la altura de los ojos del jugador
        app.getCamera().setLocation(player.getPhysicsLocation().add(0, 1.5f, 0));
    }

    public void update(float tpf) {
        // Calcular direccion de movimiento en base a donde mira la camara
        Vector3f camDir = app.getCamera().getDirection().clone().multLocal(0.6f);
        Vector3f camLeft = app.getCamera().getLeft().clone().multLocal(0.4f);
        walkDirection.set(0, 0, 0);

        if (moveForward) walkDirection.addLocal(camDir);
        if (moveBack)    walkDirection.addLocal(camDir.negate());
        if (moveLeft)    walkDirection.addLocal(camLeft);
        if (moveRight)   walkDirection.addLocal(camLeft.negate());

        // Mantener el movimiento sobre el suelo (ignorar la inclinación Y al caminar)
        walkDirection.y = 0;

        player.setWalkDirection(walkDirection.multLocal(playerSpeed));

        // Actualizar la posicion de la camara para que siga al cuerpo físico
        app.getCamera().setLocation(player.getPhysicsLocation().add(0, 1.5f, 0));
    }
}