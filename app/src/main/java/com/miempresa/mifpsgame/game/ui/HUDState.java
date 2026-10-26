package com.miempresa.mifpsgame.game.ui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.ui.Picture;

public class HUDState extends BaseAppState {
    private BitmapText healthText;
    private BitmapText ammoText;
    private Picture crosshair;

    @Override
    protected void initialize(Application app) {
        SimpleApplication sa = (SimpleApplication) app;

        // Fuente del texto
        BitmapFont font = sa.getAssetManager().loadFont("Interface/Fonts/Default.fnt");

        // Texto de salud
        healthText = new BitmapText(font, false);
        healthText.setSize(font.getCharSet().getRenderedSize());
        healthText.setText("HP: 100");
        healthText.setLocalTranslation(20, 80, 0);
        sa.getGuiNode().attachChild(healthText);

        // Texto de municion
        ammoText = new BitmapText(font, false);
        ammoText.setText("30 / 90");
        ammoText.setLocalTranslation(sa.getCamera().getWidth() - 120, 80, 0);
        sa.getGuiNode().attachChild(ammoText);

        // Mira central
        crosshair = new Picture("HUD/Crosshair");
        crosshair.setImage(sa.getAssetManager(), "Interface/crosshair.png", true);
        crosshair.setWidth(32);
        crosshair.setHeight(32);
        crosshair.setLocalTranslation(
                sa.getCamera().getWidth()/2f - 16,
                sa.getCamera().getHeight()/2f - 16, 0);
        sa.getGuiNode().attachChild(crosshair);
    }

    @Override
    protected void cleanup(Application app) {}

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}