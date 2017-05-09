package com.NeoNexus671.Swiper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.NeoNexus671.Swiper.screens.MenuScreen;

public class Swiper extends Game {
	public SpriteBatch batch;
	public final float GAME_WIDTH = 1280;
    public  final float GAME_HEIGHT = 720;
    AdHandler handler;
	public BitmapFont titleFont;
	public BitmapFont textFont;
	public float aspectRatio;

	public Swiper(AdHandler handler){
		this.handler = handler;
	}

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		aspectRatio = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
		createFonts();
		setScreen(new MenuScreen(this));
	}



	public void createFonts() {
		FileHandle fontFile = Gdx.files.internal("SF.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 150;
		textFont = generator.generateFont(parameter);
		parameter.size = 200;
		titleFont = generator.generateFont(parameter);
		generator.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	public void toggleAds(boolean toggle){
		handler.showAds(toggle);
	}
}
