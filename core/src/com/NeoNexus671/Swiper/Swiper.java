package com.NeoNexus671.Swiper;

import com.NeoNexus671.Swiper.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Swiper extends Game {
    public static final String SF_TTF = "SF.ttf";
    public static final float GAME_WIDTH = 1280;
    public static final float GAME_HEIGHT = 720;
    private final AdHandler handler;
    public SpriteBatch batch;
    public BitmapFont titleFont;
    public BitmapFont textFont;
    public float aspectRatio;

    public Swiper(AdHandler handler) {
        this.handler = handler;
    }


    @Override
    public void create() {
        batch = new SpriteBatch();
        aspectRatio = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        createFonts();
        setScreen(new MenuScreen(this));
    }


    private void createFonts() {
        FileHandle fontFile = Gdx.files.internal(SF_TTF);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 150;
        textFont = generator.generateFont(parameter);
        parameter.size = 200;
        titleFont = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void toggleAds(boolean toggle) {
        handler.showAds(toggle);
    }

    @Override
    public String toString() {
        return "Swiper{" +
                "batch=" + batch +
                ", titleFont=" + titleFont +
                ", textFont=" + textFont +
                ", aspectRatio=" + aspectRatio +
                ", handler=" + handler +
                '}';
    }
}
