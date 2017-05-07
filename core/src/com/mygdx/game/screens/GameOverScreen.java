package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Swiper;

/**
 * Created by acurr on 5/6/2017.
 */
public class GameOverScreen implements Screen {
    private final Sprite background;
    private final OrthographicCamera camera;
    private final Swiper game;
    private final Viewport gameViewPort;
    private final Label gameOverLabel;
    private final Label playLabel;
    private final Label menuLabel;
    private final Label scoreLabel;
    ShapeRenderer shapeRenderer;
    Rectangle playButton;
    Rectangle menuButton;

    public GameOverScreen(Swiper game,PlayScreen playScreen) {
        this.game = game;
        background = new Sprite(new Texture(Gdx.files.internal("Gray.png")));
        background.scale(5);
        camera = new OrthographicCamera();
        gameViewPort = new StretchViewport(game.gameWidth,game.gameHeight,camera);
        gameOverLabel = new Label("Game Over!",new Label.LabelStyle(game.titleFont, Color.WHITE));
        scoreLabel = new Label("Your score was "+playScreen.getScore()+" the high score is "+playScreen.getHighscore(),new Label.LabelStyle(game.textFont, Color.WHITE));
        playLabel = new Label("Play Again",new Label.LabelStyle(game.textFont, Color.WHITE));
        menuLabel = new Label("Main Menu",new Label.LabelStyle(game.textFont, Color.WHITE));
        background.setPosition(-game.gameWidth / 2, -game.gameHeight / 2);
        gameOverLabel.setPosition(-game.gameWidth/2,(game.gameHeight/2)-150);
        scoreLabel.setFontScale(.35f);
        playLabel.setPosition(-game.gameWidth/3, 50);
        menuLabel.setPosition(-game.gameWidth/3,-150);
        scoreLabel.setPosition(-game.gameWidth/2,(game.gameHeight/2)-200);
        Gdx.gl.glLineWidth(32);

        shapeRenderer = new ShapeRenderer();

        playButton = new Rectangle(0,game.gameHeight-400,game.gameWidth*2+100,300);
        menuButton = new Rectangle(0,game.gameHeight-100,game.gameWidth*2+100,350);

    }
    @Override
    public void show() {

    }


    @SuppressWarnings("Duplicates")
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        background.draw(game.batch, 1);
        gameOverLabel.draw(game.batch,1);
        playLabel.draw(game.batch,1);
        menuLabel.draw(game.batch,1);
        scoreLabel.draw(game.batch,1);
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

    @SuppressWarnings("Duplicates")
    private void handleInput()   {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new PlayScreen(game));
        }
        else if(Gdx.input.justTouched()){
            while(Gdx.input.isTouched()) {
            }
            if(!Gdx.input.isTouched()) {
                if (playButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    game.setScreen(new PlayScreen(game));
                } else if (menuButton.contains(Gdx.input.getX(),Gdx.input.getY())){
                    game.setScreen(new MenuScreen(game));
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

    }
}
