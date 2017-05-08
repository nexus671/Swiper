package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    Rectangle startButton;
    Rectangle quitButton;


    public MenuScreen(Swiper game) {
        this.game = game;
        game.toggleAds(true);
        background = new Sprite(new Texture(Gdx.files.internal("Gray.png")));
        background.scale(5);
        camera = new OrthographicCamera();
        gameViewPort = new StretchViewport(game.gameWidth,game.gameHeight,camera);
        titleLabel = new Label("Swiper!",new Label.LabelStyle(game.titleFont, Color.WHITE));
        startLabel = new Label("Start",new Label.LabelStyle(game.textFont, Color.WHITE));
        quitLabel  = new Label("Quit",new Label.LabelStyle(game.textFont, Color.WHITE));
        background.setPosition(-game.gameWidth / 2, -game.gameHeight / 2);
        titleLabel.setPosition(-game.gameWidth/3,(game.gameHeight/2)-150);
        startLabel.setPosition(-game.gameWidth/5, 50);
        quitLabel.setPosition(-game.gameWidth/7,-150);
        Gdx.gl.glLineWidth(32);

        shapeRenderer = new ShapeRenderer();

         startButton = new Rectangle(0,game.gameHeight-400,game.gameWidth*2+100,300);
         quitButton = new Rectangle(0,game.gameHeight-100,game.gameWidth*2+100,350);

    }

    @Override
    public void show() {

    }
    @SuppressWarnings("Duplicates")
    @Override
    public void render(float delta)   {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        background.draw(game.batch, 1);
        titleLabel.draw(game.batch,1);
        quitLabel.draw(game.batch,1);
        startLabel.draw(game.batch,1);
        game.batch.end();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(-game.gameWidth,200,game.gameWidth,200);
        shapeRenderer.line(-game.gameWidth,0,game.gameWidth,0);
        shapeRenderer.line(-game.gameWidth,-200,game.gameWidth,-200);
        shapeRenderer.end();
        handleInput();

    }

    private void handleInput()   {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new PlayScreen(game));
        }
        else if(Gdx.input.justTouched()){
            while(Gdx.input.isTouched()) {
            }
            if(!Gdx.input.isTouched()) {
                if (startButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    game.setScreen(new PlayScreen(game));
                } else if (quitButton.contains(Gdx.input.getX(),Gdx.input.getY())){
                    Gdx.app.exit();
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
        shapeRenderer.dispose();

    }
}
