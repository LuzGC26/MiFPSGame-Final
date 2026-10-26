package com.miempresa.mifpsgame.game.player;

import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioData.DataType;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class WeaponControl {
    private SimpleApplication app;
    private AudioNode shootSound;
    public WeaponControl(SimpleApplication app) {
        this.app = app;
       // shootSound = new AudioNode(app.getAssetManager(), "Audio/gunshot.ogg", DataType.Buffer);
       // shootSound.setPositional(false);
        //shootSound.setVolume(2f);
    }

    public void shoot() {

        //shootSound.playInstance();
        // Trazar un rayo desde el centro de la cámara hacia adelante
        Vector3f origin = app.getCamera().getLocation();
        Vector3f direction = app.getCamera().getDirection().normalizeLocal();
        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();

        // Detectar colisión con la geometría del escenario
        app.getRootNode().collideWith(ray, results);

        // Verificar si la bala golpeó algo
        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            com.jme3.scene.Spatial hit = closest.getGeometry();

            // Verificar si lo que golpeamos tiene el control de enemigo
            com.miempresa.mifpsgame.game.enemies.EnemyControl enemyControl = hit.getControl(com.miempresa.mifpsgame.game.enemies.EnemyControl.class);

            if (enemyControl != null) {
                enemyControl.takeDamage(50);
                System.out.println("¡Impacto crítico al enemigo!");
            } else {
                System.out.println("Bala impactó en: " + hit.getName());
            }
        } else {
            System.out.println("Disparo al vacío...");
        }
    }
}