package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;

import com.mygdx.game.Swiper;


/**
 * Created by acurr on 2/7/2017.
 */
public class MenuScreen implements Screen {
    private final Sprite background;
    private final OrthographicCamera camera;
    private final Swiper game;
    private final Viewport gameViewPort;
    private final Label titleLabel;
    private final Label startLabel;
    private final Label quitLabel;
    ShapeRenderer shapeRenderer;


    public MenuScreen(Swiper game) {
        this.game = game;
        background = new Sprite(new Texture(Gdx.files.internal("Gray.png")));
        background.scale(5);
        camera = new OrthographicCamera();
        gameViewPort = new StretchViewport(game.gameWidth,game.gameHeight,camera);
        titleLabel = new Label("Swiper!",new Label.LabelStyle(game.titleFont, Color.WHITE));
        startLabel = new Label("Start",new Label.LabelStyle(game.textFont, Color.WHITE));
        quitLabel  = new Label("Quit",new Label.LabelStyle(game.textFont, Color.WHITE));
        background.setPosition(-game.gameWidth / 2, -game.gameHeight / 2);
        titleLabel.setPosition(-game.gameWidth/3,(game.gameHeight/2)-150);
        startLabel.setPosition(-game.gameWidth/5,(game.gameHeight/2)-300);
        quitLabel.setPosition(-game.gameWidth/7,(game.gameHeight/2)-450);
        Gdx.gl.glLineWidth(32);

        shapeRenderer = new ShapeRenderer();



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)   {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        background.draw(game.batch, 1);
        titleLabel.draw(game.batch,1);
        startLabel.draw(game.batch,1);
        quitLabel.draw(game.batch,1);

        handleInput();
        game.batch.end();

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(startLabel.getX()-10,startLabel.getY(),startLabel.getWidth()+20,100);
        shapeRenderer.rect(quitLabel.getX()-10,quitLabel.getY(),quitLabel.getWidth()+20,100);

        shapeRenderer.end();

    }

    private void handleInput()   {
        Rectangle shape2D = new Rectangle(300,340,530,200);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new PlayScreen(game));
        }
        else if(Gdx.input.justTouched()){
            while(Gdx.input.isTouched()) {
            }
            if(!Gdx.input.isTouched()) {
                if (shape2D.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    game.setScreen(new PlayScreen(game));
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height);
        camera.update();
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
