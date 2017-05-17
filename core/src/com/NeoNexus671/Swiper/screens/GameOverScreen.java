package com.NeoNexus671.Swiper.screens;

import com.NeoNexus671.Swiper.Swiper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by acurr on 5/6/2017.
 */
public class GameOverScreen implements Screen {
    public static final String GRAY_PNG = "Gray.png";
    public static final String MAIN_MENU = "Main Menu";
    public static final String PLAY_AGAIN = "Play Again";
    public static final String HIGHSCORE = "Highscore: ";
    public static final String SCORE = "Score: ";
    public static final String GAME_OVER = "Game Over!";
    public static final String G5_OGG = "g5.ogg";
    public static final String C5_OGG = "c5.ogg";
    private final Sprite background;
    private final Swiper game;
    private final Viewport gameViewPort;
    private final Label gameOverLabel;
    private final Label playLabel;
    private final Label menuLabel;
    private final Label scoreLabel;
    private final Label highScoreLabel;
    private final Sound sound1;
    private final Sound sound2;
    private final ShapeRenderer shapeRenderer;
    private final Rectangle playButton;
    private final Rectangle menuButton;
    private boolean firstShow;

    public GameOverScreen(Swiper game, PlayScreen playScreen) {
        this.game = game;
        game.toggleAds(true);
        firstShow = true;
        game.changeFirstTime();
        
        sound1 = Gdx.audio.newSound(Gdx.files.internal(G5_OGG));
        sound2 = Gdx.audio.newSound(Gdx.files.internal(C5_OGG));
        
        background = new Sprite(new Texture(Gdx.files.internal(GRAY_PNG)));
        background.scale(5);
        
        gameViewPort = new StretchViewport(Swiper.GAME_WIDTH, Swiper.GAME_HEIGHT);
        
        gameOverLabel = new Label(GAME_OVER, new LabelStyle(game.titleFont, Color.WHITE));
        scoreLabel = new Label(SCORE + playScreen.getScore(), new LabelStyle(game.textFont, Color.WHITE));
        highScoreLabel = new Label(HIGHSCORE + playScreen.getHighscore(), new LabelStyle(game.textFont, Color.WHITE));
        playLabel = new Label(PLAY_AGAIN, new LabelStyle(game.textFont, Color.WHITE));
        menuLabel = new Label(MAIN_MENU, new LabelStyle(game.textFont, Color.WHITE));
        
        background.setPosition(Swiper.GAME_WIDTH, Swiper.GAME_HEIGHT);
        gameOverLabel.setPosition((Gdx.graphics.getWidth() / 2) - (gameOverLabel.getWidth() / 2), Gdx.graphics.getHeight() - gameOverLabel.getHeight());
        playLabel.setPosition((Gdx.graphics.getWidth() / 2) - (playLabel.getWidth() / 2), Gdx.graphics.getHeight() * 0.55f);
        menuLabel.setPosition((Gdx.graphics.getWidth() / 2) - (menuLabel.getWidth() / 2), Gdx.graphics.getHeight() * 0.35f);
        scoreLabel.setPosition((Gdx.graphics.getWidth() / 2) - (scoreLabel.getWidth() / 2), Gdx.graphics.getHeight() * 0.82f);
        highScoreLabel.setPosition((Gdx.graphics.getWidth() / 2) - (highScoreLabel.getWidth() / 2), Gdx.graphics.getHeight() * 0.75f);
        
        Gdx.gl.glLineWidth(32);
        
        shapeRenderer = new ShapeRenderer();
        playButton = new Rectangle(0, (Gdx.graphics.getHeight() * .40f) - playLabel.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.20f);
        menuButton = new Rectangle(0, (Gdx.graphics.getHeight() * .60f) - menuLabel.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.20f);

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
        gameOverLabel.draw(game.batch, 1);
        playLabel.draw(game.batch, 1);
        menuLabel.draw(game.batch, 1);
        scoreLabel.draw(game.batch, 1);
        highScoreLabel.draw(game.batch, 1);
        game.batch.end();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.line(0, (Gdx.graphics.getHeight() * .60f) + playLabel.getHeight(), Gdx.graphics.getWidth(), (Gdx.graphics.getHeight() * .60f) + playLabel.getHeight());
        shapeRenderer.line(0, (Gdx.graphics.getHeight() * .40f) + menuLabel.getHeight(), Gdx.graphics.getWidth(), (Gdx.graphics.getHeight() * .40f) + menuLabel.getHeight());
        shapeRenderer.line(0, (Gdx.graphics.getHeight() * .20f) + menuLabel.getHeight(), Gdx.graphics.getWidth(), (Gdx.graphics.getHeight() * .20f) + menuLabel.getHeight());
        shapeRenderer.end();
        if(firstShow){
            game.playServices.showScore();
            firstShow = false;
        }
        handleInput();
    }

    @SuppressWarnings("Duplicates")
    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            sound1.play(Swiper.volume);
            sound2.play(Swiper.volume);
            game.setScreen(new PlayScreen(game));
        } else if (Gdx.input.justTouched()) {
            while (Gdx.input.isTouched()) {
            }
            if (!Gdx.input.isTouched()) {
                if (playButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    sound1.play(Swiper.volume);
                    sound2.play(Swiper.volume);
                    game.setScreen(new PlayScreen(game));
                } else if (menuButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    sound1.play(Swiper.volume);
                    sound2.play(Swiper.volume);
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

    @Override
    public String toString() {
        return "GameOverScreen{" +
                "background=" + background +
                ", game=" + game +
                ", gameViewPort=" + gameViewPort +
                ", gameOverLabel=" + gameOverLabel +
                ", playLabel=" + playLabel +
                ", menuLabel=" + menuLabel +
                ", scoreLabel=" + scoreLabel +
                ", highScoreLabel=" + highScoreLabel +
                ", sound1=" + sound1 +
                ", sound2=" + sound2 +
                ", shapeRenderer=" + shapeRenderer +
                ", playButton=" + playButton +
                ", menuButton=" + menuButton +
                '}';
    }
}
