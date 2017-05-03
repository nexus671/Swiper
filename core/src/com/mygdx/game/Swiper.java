package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.screens.MenuScreen;

public class Swiper extends Game {
	public SpriteBatch batch;
	public  int gameWidth;
    public  int gameHeight;
	public BitmapFont titleFont;
	public BitmapFont textFont;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameHeight = 1000;
		gameWidth = 500;
		createFonts();
		setScreen(new MenuScreen(this));
	}


	public void createFonts() {
		FileHandle fontFile = Gdx.files.internal("SF.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 100;
		textFont = generator.generateFont(parameter);
		parameter.size = 150;
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
}
