package com.miempresa.mifpsgame.game;

import com.miempresa.mifpsgame.game.player.WeaponControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.MouseInput;
import com.miempresa.mifpsgame.game.ui.HUDState;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

// Importaciones de tus nuevas clases
import com.miempresa.mifpsgame.game.player.PlayerControl;
import com.miempresa.mifpsgame.game.world.SceneLoader;

// --- NUEVAS IMPORTACIONES PARA LA PANTALLA TÁCTIL ---
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.TouchInput;
import com.miempresa.mifpsgame.game.player.TouchInputHandler;

public class FPSGame extends SimpleApplication {

    private WeaponControl weaponControl;
    private BulletAppState bulletAppState;
    private PlayerControl playerControl;
    private SceneLoader sceneLoader;

    @Override
    public void simpleInitApp() {

        // Ocultar los textos técnicos por defecto
        setDisplayStatView(false);
        setDisplayFps(false);

// Apagar la mira '+' por defecto de la cámara base
        flyCam.setEnabled(false);
        // Inicializar motor de fisicas
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        // Cargar escenario
        sceneLoader = new SceneLoader(this, bulletAppState);
        sceneLoader.loadScene();

        // Inicializar jugador con camara FPS
        playerControl = new PlayerControl(this, bulletAppState);
        playerControl.initialize();

        // Crear enemigo de prueba (cubo rojo)
        com.jme3.scene.shape.Box boxMesh = new com.jme3.scene.shape.Box(1f, 1f, 1f);
        com.jme3.scene.Geometry enemy = new com.jme3.scene.Geometry("Enemigo", boxMesh);
        com.jme3.material.Material matEnemy = new com.jme3.material.Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matEnemy.setColor("Color", com.jme3.math.ColorRGBA.Red);
        enemy.setMaterial(matEnemy);
        enemy.setLocalTranslation(0, 1f, 5f); // 5 metros al frente
        enemy.addControl(new com.miempresa.mifpsgame.game.enemies.EnemyControl());
        rootNode.attachChild(enemy);
        // Inicializar el arma
        weaponControl = new WeaponControl(this);

        // Mapear el toque de pantalla como disparo
        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(shootListener, "Shoot");

        // Configurar iluminacion basica
        setupLighting();

        stateManager.attach(new HUDState());
        // --- NUEVO: Configurar los controles tactiles de Android ---
        inputManager.addMapping("Touch", new TouchTrigger(TouchInput.ALL));
        TouchInputHandler touchHandler = new TouchInputHandler(playerControl, cam, settings.getWidth());
        inputManager.addListener(touchHandler, "Touch");
    }

    @Override
    public void simpleUpdate(float tpf) {
        // Actualizar el movimiento del jugador
        playerControl.update(tpf);
    }

    private void setupLighting() {
        // Luz ambiental
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White.mult(0.3f));
        rootNode.addLight(ambient);

        // Luz direccional (sol)
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.5f, -1f, -0.5f).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }

    private final ActionListener shootListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            // Si la acción es Shoot y acabas de soltar la pantalla (!isPressed)
            if (name.equals("Shoot") && !isPressed) {
                weaponControl.shoot();
            }
        }
    };
}