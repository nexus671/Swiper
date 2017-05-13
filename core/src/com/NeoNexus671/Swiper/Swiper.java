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
    public static float volume = 1.0f;
    public static int speakerCurrent = 0;
    public float densityIndependentSize;


    public Swiper(AdHandler handler) {
        this.handler = handler;
    }


    @Override
    public void create() {
        batch = new SpriteBatch();
        aspectRatio = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        densityIndependentSize =  75 * Gdx.graphics.getDensity();
        int fontSize = Math.round(densityIndependentSize);
        createFonts(fontSize);
        setScreen(new MenuScreen(this));
    }


    private void createFonts(int fontsize) {
        FileHandle fontFile = Gdx.files.internal(SF_TTF);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = (int)Math.round((fontsize*.70));
        textFont = generator.generateFont(parameter);
        parameter.size = fontsize;
        titleFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public static float getVolume() {
        return volume;
    }

    public static void setVolume(float volume) {
        Swiper.volume = volume;
    }

    public static void muteVolume(){
        volume = 0.0f;
    }
    public static void unmuteVolume(){
        volume = 1.0f;
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
