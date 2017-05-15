package com.NeoNexus671.Swiper.screens;

import com.NeoNexus671.Swiper.SimpleDirectionGestureDetector;
import com.NeoNexus671.Swiper.SimpleDirectionGestureDetector.DirectionListener;
import com.NeoNexus671.Swiper.Swiper;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;


/**
 * Created by acurr on 2/8/2017.
 */
public class PlayScreen implements Screen {
    public static final String HIGH_SCORE = "highScore";
    public static final String DOWN = "Down";
    public static final String LEFT = "Left";
    public static final String RIGHT = "Right";
    public static final String UP = "Up";
    public static final String HIGHSCORE = "highscore";
    public static final String SWIPER = "Swiper";
    public static final String PING_OGG = "ping.ogg";
    public static final String D5_OGG = "d5.ogg";
    public static final String F5_OGG = "f5.ogg";
    public static final String C5_OGG = "c5.ogg";
    public static final String G5_OGG = "g5.ogg";
    public static final String GRAY_PNG = "Gray.png";
    public static final String GREEN_PNG = "Green.png";
    public static final String ORANGE_PNG = "Orange.png";
    public static final String YELLOW_PNG = "Yellow.png";
    public static final String CYAN_PNG = "Cyan.png";
    private final Texture cyan;
    private final Texture green;
    private final Texture orange;
    private final Texture yellow;
    private final Sprite gray;
    private final Swiper game;
    private final Viewport gameViewPort;

    private final Array<Sprite> colors;
    private final Array<Integer> keys;
    private final Array<Sound> sounds;
    private final Random random;
    private final Sound sound1;
    private final Sound sound2;
    private final Sound sound3;
    private final Sound sound4;
    private final Sound death;
    private final Label scoreLabel;
    private final Label highScoreLabel;
    private final Array<Label> controls;
    private final Preferences preferences;
    private int currentColor;
    private int score;
    private float alphaColor;
    private float alphaControl;
    private float timer;
    private int direction;
    private Boolean swipe;
    private Boolean firstTap;


    public PlayScreen(Swiper game) {
        this.game = game;

        game.toggleAds(false);

        gameViewPort = new StretchViewport(Swiper.GAME_WIDTH, Swiper.GAME_HEIGHT);

        cyan = new Texture(Gdx.files.internal(CYAN_PNG));
        yellow = new Texture(Gdx.files.internal(YELLOW_PNG));
        orange = new Texture(Gdx.files.internal(ORANGE_PNG));
        green = new Texture(Gdx.files.internal(GREEN_PNG));
        gray = new Sprite(new Texture(Gdx.files.internal(GRAY_PNG)));

        sounds = new Array<Sound>();
        sound1 = Gdx.audio.newSound(Gdx.files.internal(G5_OGG));
        sound2 = Gdx.audio.newSound(Gdx.files.internal(C5_OGG));
        sound3 = Gdx.audio.newSound(Gdx.files.internal(F5_OGG));
        sound4 = Gdx.audio.newSound(Gdx.files.internal(D5_OGG));
        death = Gdx.audio.newSound(Gdx.files.internal(PING_OGG));

        preferences = Gdx.app.getPreferences(SWIPER);
        if (!preferences.contains(HIGH_SCORE)) {
            preferences.putInteger(HIGHSCORE, 0);
        }

        sounds.add(sound1);
        sounds.add(sound2);
        sounds.add(sound3);
        sounds.add(sound4);

        colors = new Array<Sprite>();
        colors.add(new Sprite(cyan));
        colors.add(new Sprite(yellow));
        colors.add(new Sprite(orange));
        colors.add(new Sprite(green));

        gray.scale(5);
        for (Sprite s : colors) {
            s.scale(5);
        }

        random = new Random();
        currentColor = random.nextInt(4);

        controls = new Array<Label>();
        keys = new Array<Integer>();

        if (((Gdx.app.getType() == Application.ApplicationType.Android) || (Gdx.app.getType() == Application.ApplicationType.iOS))) {
            swipe = true;

            keys.add(Keys.UP);
            keys.add(Keys.RIGHT);
            keys.add(Keys.LEFT);
            keys.add(Keys.DOWN);

            Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new DirectionListener() {
                @Override
                public void onUp() {
                    direction = Keys.UP;
                    System.out.println(UP);
                }

                @Override
                public void onRight() {
                    direction = Keys.RIGHT;
                    System.out.println(RIGHT);
                }

                @Override
                public void onLeft() {
                    direction = Keys.LEFT;
                    System.out.println(LEFT);
                }

                @Override
                public void onDown() {
                    direction = Keys.DOWN;
                    System.out.println(DOWN);
                }
            }));
        } else {
            swipe = false;
            keys.add(Keys.W);
            keys.add(Keys.A);
            keys.add(Keys.S);
            keys.add(Keys.D);
        }
        int i = 0;
        for (Label l : controls) {
            l.setText(Keys.toString(keys.get(i)));
            i++;
        }
        direction = 4;

        keys.shuffle();

        highScoreLabel = new Label(String.valueOf(getHighscore()), new LabelStyle(game.textFont, Color.WHITE));
        scoreLabel = new Label(String.valueOf(score), new LabelStyle(game.textFont, Color.WHITE));
        controls.add(new Label(Keys.toString(keys.get(0)), new LabelStyle(game.textFont, Color.WHITE)));
        controls.add(new Label(Keys.toString(keys.get(1)), new LabelStyle(game.textFont, Color.WHITE)));
        controls.add(new Label(Keys.toString(keys.get(2)), new LabelStyle(game.textFont, Color.WHITE)));
        controls.add(new Label(Keys.toString(keys.get(3)), new LabelStyle(game.textFont, Color.WHITE)));

        gray.setPosition(Swiper.GAME_WIDTH, Swiper.GAME_HEIGHT);

        for (Label l : controls) {
            l.setPosition((Gdx.graphics.getWidth() / 2) - (l.getWidth() / 2), Gdx.graphics.getHeight() - l.getHeight());
        }

        for (Sprite c : colors) {
            c.setPosition(Swiper.GAME_WIDTH, Swiper.GAME_HEIGHT);
        }

        scoreLabel.setPosition(Gdx.graphics.getWidth() * 0.10f, Gdx.graphics.getHeight() * 0.90f);
        highScoreLabel.setPosition(Gdx.graphics.getWidth() * 0.10f, Gdx.graphics.getHeight() * 0.82f)
        ;
        score = 0;
        alphaColor = 1;
        alphaControl = 1;
        timer = 100;

        firstTap = true;
    }

    public int getHighscore() {
        return preferences.getInteger(HIGH_SCORE);
    }

    private void setHighscore(int hs) {
        preferences.putInteger(HIGH_SCORE, hs);
        preferences.flush();
        game.playServices.submitScore(hs);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        gray.draw(game.batch, 1);

        colors.get(currentColor).draw(game.batch, alphaColor);
        controls.get(currentColor).draw(game.batch, alphaControl);
        scoreLabel.draw(game.batch, 1);
        highScoreLabel.draw(game.batch, 1);
        handleInput();
        game.batch.end();
    }

    private void handleInput() {
        timer -= 1.7;
        alphaColor -= 0.01;
        if (firstTap) {
            direction = 4;
            firstTap = false;
        }

        if (swipe) {
            if (!firstTap) {
                if (keys.get(currentColor) == direction) {
                    correct();
                    direction = 4;
                } else if (!((keys.get(currentColor) != direction) && (direction == 4)) || (timer <= 0)) {
                    inCorrect();
                }
            }

        } else if (Gdx.input.isKeyJustPressed(keys.get(currentColor))) {
            correct();
        } else if (Gdx.input.isKeyJustPressed(Keys.ANY_KEY) || (timer <= 0)) {
            inCorrect();
        }
    }

    private void correct() {
        score++;
        scoreLabel.setText(String.valueOf(score));
        currentColor = random.nextInt(4);
        sounds.get(currentColor).play(Swiper.volume);
        timer = 100;
        alphaColor = 1;
        if (alphaControl > 0) {
            alphaControl -= 0.05;
        }
        if ((score % 100) == 0) {
            keys.shuffle();
            int i = 0;
            for (Label l : controls) {
                l.setText(Keys.toString(keys.get(i)));
                l.setPosition((Gdx.graphics.getWidth() / 2) - (l.getWidth() / 2), Gdx.graphics.getHeight() - l.getHeight());
                i++;
            }
            alphaControl = 1;
        }
    }

    private void inCorrect() {
        death.play(Swiper.volume);
        if (score > getHighscore()) {
            setHighscore(score);
            highScoreLabel.setText(String.valueOf(getHighscore()));
        }
        game.playServices.submitScore(getHighscore());
        game.setScreen(new GameOverScreen(game, this));
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
        for (Sound s : sounds) {
            s.dispose();
        }
        for (Sprite t : colors) {
            t.getTexture().dispose();
        }

    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "PlayScreen{" +
                "cyan=" + cyan +
                ", green=" + green +
                ", orange=" + orange +
                ", yellow=" + yellow +
                ", gray=" + gray +
                ", game=" + game +
                ", gameViewPort=" + gameViewPort +
                ", colors=" + colors +
                ", keys=" + keys +
                ", sounds=" + sounds +
                ", random=" + random +
                ", sound1=" + sound1 +
                ", sound2=" + sound2 +
                ", sound3=" + sound3 +
                ", sound4=" + sound4 +
                ", death=" + death +
                ", scoreLabel=" + scoreLabel +
                ", highScoreLabel=" + highScoreLabel +
                ", controls=" + controls +
                ", preferences=" + preferences +
                ", currentColor=" + currentColor +
                ", score=" + score +
                ", alphaColor=" + alphaColor +
                ", alphaControl=" + alphaControl +
                ", timer=" + timer +
                ", direction=" + direction +
                ", swipe=" + swipe +
                ", firstTap=" + firstTap +
                '}';
    }
}
