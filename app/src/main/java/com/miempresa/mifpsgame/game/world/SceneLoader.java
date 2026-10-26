package com.miempresa.mifpsgame.game.world;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class SceneLoader {
    private SimpleApplication app;
    private BulletAppState bulletAppState;

    public SceneLoader(SimpleApplication app, BulletAppState bullet) {
        this.app = app;
        this.bulletAppState = bullet;
    }

    public void loadScene() {
        // Cargar modelo del nivel (Asegúrate de tener este archivo después en tu carpeta assets)
        try {
            Spatial scene = app.getAssetManager().loadModel("Models/Level/nivel1.j3o");
            // Agregar fisica de colision al escenario
            RigidBodyControl landscape = new RigidBodyControl(0.0f); // masa 0 = estatico
            scene.addControl(landscape);
            app.getRootNode().attachChild(scene);
            bulletAppState.getPhysicsSpace().add(landscape);
        } catch (Exception e) {
            System.out.println("Modelo nivel1.j3o no encontrado aun. Solo cargando el suelo.");
        }

        // Crear suelo de emergencia (plano)
        Box floorBox = new Box(50f, 0.1f, 50f);
        Geometry floor = new Geometry("Floor", floorBox);
        Material matFloor = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");

        // Comentamos la textura temporalmente hasta que agregues tus imágenes a la carpeta assets
        matFloor.setTexture("ColorMap", app.getAssetManager().loadTexture("Textures/terrain_diffuse.jpg"));

        floor.setMaterial(matFloor);
        floor.setLocalTranslation(0f, -0.1f, 0f);
        RigidBodyControl floorPhys = new RigidBodyControl(0f);
        floor.addControl(floorPhys);
        app.getRootNode().attachChild(floor);
        bulletAppState.getPhysicsSpace().add(floorPhys);
    }
}