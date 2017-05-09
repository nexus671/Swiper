package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
    private final Swiper game;
    private final Viewport gameViewPort;
    private final Label gameOverLabel;
    private final Label playLabel;
    private final Label menuLabel;
    private final Label scoreLabel;
    ShapeRenderer shapeRenderer;
    Rectangle playButton;
    Rectangle menuButton;
    private final Sound sound1;
    private final Sound sound2;

    public GameOverScreen(Swiper game,PlayScreen playScreen) {
        this.game = game;
        game.toggleAds(true);
        sound1 = Gdx.audio.newSound(Gdx.files.internal("g5.ogg"));
        sound2 = Gdx.audio.newSound(Gdx.files.internal("c5.ogg"));
        background = new Sprite(new Texture(Gdx.files.internal("Gray.png")));
        background.scale(5);
        gameViewPort = new StretchViewport(game.GAME_WIDTH,game.GAME_HEIGHT);
        gameOverLabel = new Label("Game Over!",new Label.LabelStyle(game.titleFont, Color.WHITE));
        scoreLabel = new Label("Your score was "+playScreen.getScore()+" the high score is "+playScreen.getHighscore(),new Label.LabelStyle(game.textFont, Color.WHITE));
        playLabel = new Label("Play Again",new Label.LabelStyle(game.textFont, Color.WHITE));
        menuLabel = new Label("Main Menu",new Label.LabelStyle(game.textFont, Color.WHITE));
        background.setPosition(game.GAME_WIDTH, game.GAME_HEIGHT);
        gameOverLabel.setPosition((Gdx.graphics.getWidth()/2)-gameOverLabel.getWidth()/2,Gdx.graphics.getHeight() - gameOverLabel.getHeight());
        scoreLabel.setFontScale(.50f);
        playLabel.setPosition((Gdx.graphics.getWidth()/2)-(playLabel.getWidth()/2), Gdx.graphics.getHeight()*.55f);
        menuLabel.setPosition((Gdx.graphics.getWidth()/2)-(menuLabel.getWidth()/2),Gdx.graphics.getHeight()*.35f);
        scoreLabel.setPosition(0,Gdx.graphics.getHeight()* .80f);
        Gdx.gl.glLineWidth(32);

        shapeRenderer = new ShapeRenderer();

        playButton = new Rectangle(0,Gdx.graphics.getHeight()*.40f -playLabel.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*.20f);
        menuButton = new Rectangle(0,Gdx.graphics.getHeight()*.60f - menuLabel.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*.20f);

    }
    @Override
    public void show() {

    }


    @SuppressWarnings("Duplicates")
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        background.draw(game.batch, 1);
        gameOverLabel.draw(game.batch,1);
        playLabel.draw(game.batch,1);
        menuLabel.draw(game.batch,1);
        scoreLabel.draw(game.batch,1);
        game.batch.end();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.line(0,Gdx.graphics.getHeight()*.60f +playLabel.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*.60f+playLabel.getHeight());
        shapeRenderer.line(0,Gdx.graphics.getHeight()*.40f +menuLabel.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*.40f +menuLabel.getHeight());
        shapeRenderer.line(0,Gdx.graphics.getHeight()*.20f + menuLabel.getHeight(),Gdx.graphics.getWidth(),Gdx.graphics.getHeight()*.20f + menuLabel.getHeight());
        shapeRenderer.end();
        handleInput();
    }

    @SuppressWarnings("Duplicates")
    private void handleInput()   {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            sound1.play();
            sound2.play();
            game.setScreen(new PlayScreen(game));
        }
        else if(Gdx.input.justTouched()){
            while(Gdx.input.isTouched()) {
            }
            if(!Gdx.input.isTouched()) {
                if (playButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    sound1.play();
                    sound2.play();
                    game.setScreen(new PlayScreen(game));
                } else if (menuButton.contains(Gdx.input.getX(),Gdx.input.getY())){
                    sound1.play();
                    sound2.play();
                    game.setScreen(new MenuScreen(game));
                }
            }
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
        shapeRenderer.dispose();
        sound1.dispose();
        sound2.dispose();
    }
}
