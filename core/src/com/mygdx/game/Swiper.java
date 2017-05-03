package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MenuScreen;

public class Swiper extends Game {
	public SpriteBatch batch;
	public  int gameWidth;
    public  int gameHeight;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
		gameHeight = Gdx.graphics.getHeight();
		gameWidth = 1152;

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
