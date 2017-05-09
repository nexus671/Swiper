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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by acurr on 2/7/2017.
 */
public class MenuScreen implements Screen {
    public static final String QUIT = "Quit";
    public static final String START = "Start";
    public static final String SWIPER = "Swiper!";
    public static final String GRAY_PNG = "Gray.png";
    public static final String C5_OGG = "c5.ogg";
    public static final String G5_OGG = "g5.ogg";
    public static final String GREEN_PNG = "Green.png";
    public static final String ORANGE_PNG = "Orange.png";
    public static final String YELLOW_PNG = "Yellow.png";
    public static final String CYAN_PNG = "Cyan.png";
    private final Sprite background;
    private final Texture cyan;
    private final Texture green;
    private final Texture orange;
    private final Texture yellow;
    private final Swiper game;
    private final Viewport gameViewPort;
    private final Label titleLabel;
    private final Label startLabel;
    private final Label quitLabel;
    private final Array<Sprite> colors;
    private final Sound sound1;
    private final Sound sound2;
    private final ShapeRenderer shapeRenderer;
    private final Rectangle startButton;
    private final Rectangle quitButton;
    private float alphaColor;
    private int colorindex;
    private boolean alphaSwitch;


    public MenuScreen(Swiper game) {
        this.game = game;
        game.toggleAds(true);
        cyan = new Texture(Gdx.files.internal(CYAN_PNG));
        yellow = new Texture(Gdx.files.internal(YELLOW_PNG));
        orange = new Texture(Gdx.files.internal(ORANGE_PNG));
        green = new Texture(Gdx.files.internal(GREEN_PNG));
        colors = new Array<Sprite>();
        colors.add(new Sprite(cyan));
        colors.add(new Sprite(yellow));
        colors.add(new Sprite(orange));
        colors.add(new Sprite(green));
        sound1 = Gdx.audio.newSound(Gdx.files.internal(G5_OGG));
        sound2 = Gdx.audio.newSound(Gdx.files.internal(C5_OGG));
        alphaColor = 1;
        colorindex = 0;
        alphaSwitch = true;
        background = new Sprite(new Texture(Gdx.files.internal(GRAY_PNG)));
        background.scale(5);
        gameViewPort = new StretchViewport(game.GAME_WIDTH * game.aspectRatio, game.GAME_HEIGHT);
        titleLabel = new Label(SWIPER, new LabelStyle(game.titleFont, Color.WHITE));
        startLabel = new Label(START, new LabelStyle(game.textFont, Color.WHITE));
        quitLabel = new Label(QUIT, new LabelStyle(game.textFont, Color.WHITE));
        background.setPosition(game.GAME_WIDTH, game.GAME_HEIGHT);
        titleLabel.setPosition((Gdx.graphics.getWidth() / 2) - (titleLabel.getWidth() / 2), Gdx.graphics.getHeight() - titleLabel.getHeight());
        startLabel.setPosition((Gdx.graphics.getWidth() / 2) - (startLabel.getWidth() / 2), Gdx.graphics.getHeight() * 0.55f);
        quitLabel.setPosition((Gdx.graphics.getWidth() / 2) - (quitLabel.getWidth() / 2), Gdx.graphics.getHeight() * 0.35f);
        for (Sprite s : colors) {
            s.scale(5);
        }
        for (Sprite c : colors) {
            c.setPosition(game.GAME_WIDTH, game.GAME_HEIGHT);
        }
        Gdx.gl.glLineWidth(32);
        shapeRenderer = new ShapeRenderer();

        startButton = new Rectangle(0, (Gdx.graphics.getHeight() * .40f) - startLabel.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.20f);

        quitButton = new Rectangle(0, (Gdx.graphics.getHeight() * .60f) - quitLabel.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.20f);

    }

    @Override
    public void show() {

    }

    @SuppressWarnings("Duplicates")
    @Override
    public void render(float delta) {
        if (alphaColor <= 0.1) {
            alphaSwitch = !alphaSwitch;
            if ((colorindex + 1) == colors.size) {
                colorindex = 0;
            } else {
                colorindex++;
            }
        }
        if ((alphaColor > 0.1) && alphaSwitch) {
            alphaColor -= 0.01;
        } else {
            alphaColor += 0.01;
            if (alphaColor >= 1) {
                alphaSwitch = !alphaSwitch;
            }
        }

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        background.draw(game.batch, 1);
        colors.get(colorindex).draw(game.batch, alphaColor);
        titleLabel.draw(game.batch, 1);
        quitLabel.draw(game.batch, 1);
        startLabel.draw(game.batch, 1);
        game.batch.end();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.line(0, (Gdx.graphics.getHeight() * .60f) + startLabel.getHeight(), Gdx.graphics.getWidth(), (Gdx.graphics.getHeight() * .60f) + startLabel.getHeight());
        shapeRenderer.line(0, (Gdx.graphics.getHeight() * .40f) + quitLabel.getHeight(), Gdx.graphics.getWidth(), (Gdx.graphics.getHeight() * .40f) + quitLabel.getHeight());
        shapeRenderer.line(0, (Gdx.graphics.getHeight() * .20f) + quitLabel.getHeight(), Gdx.graphics.getWidth(), (Gdx.graphics.getHeight() * .20f) + quitLabel.getHeight());
        shapeRenderer.end();
        handleInput();

    }

    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            game.setScreen(new PlayScreen(game));
        } else if (Gdx.input.justTouched()) {
            while (Gdx.input.isTouched()) {
            }
            if (!Gdx.input.isTouched()) {
                if (startButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    sound1.play();
                    sound2.play();
                    game.setScreen(new PlayScreen(game));
                } else if (quitButton.contains(Gdx.input.getX(), Gdx.input.getY())) {
                    Gdx.app.exit();
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
        for (Sprite t : colors) {
            t.getTexture().dispose();
        }
        sound1.dispose();
        sound2.dispose();
    }

    @Override
    public String toString() {
        return "MenuScreen{" +
                "background=" + background +
                ", cyan=" + cyan +
                ", green=" + green +
                ", orange=" + orange +
                ", yellow=" + yellow +
                ", game=" + game +
                ", gameViewPort=" + gameViewPort +
                ", titleLabel=" + titleLabel +
                ", startLabel=" + startLabel +
                ", quitLabel=" + quitLabel +
                ", colors=" + colors +
                ", sound1=" + sound1 +
                ", sound2=" + sound2 +
                ", shapeRenderer=" + shapeRenderer +
                ", startButton=" + startButton +
                ", quitButton=" + quitButton +
                ", alphaColor=" + alphaColor +
                ", colorindex=" + colorindex +
                ", alphaSwitch=" + alphaSwitch +
                '}';
    }
}
