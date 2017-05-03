package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Swiper;

/**
 * Created by acurr on 2/7/2017.
 */
public class MenuScreen implements Screen {
    private final Sprite background;
    private final OrthographicCamera camera;
    private final Swiper game;
    private final Viewport gameViewPort;

    public MenuScreen(Swiper game) {
        this.game = game;
        background = new Sprite(new Texture(Gdx.files.internal("Gray.png")));
        background.scale(5);
        camera = new OrthographicCamera();
        gameViewPort = new ScreenViewport(camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        background.setPosition(-game.gameWidth / 2, -game.gameHeight / 2);
        background.draw(game.batch, 1);
        handleInput();
        game.batch.end();

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.getTexture().dispose();

    }
}
