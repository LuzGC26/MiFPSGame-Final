package com.miempresa.mifpsgame.game.enemies;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class EnemyControl extends AbstractControl {
    private int health = 100;

    @Override
    protected void controlUpdate(float tpf) {}

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            spatial.removeFromParent(); // El enemigo muere y desaparece del mundo
        }
    }
}