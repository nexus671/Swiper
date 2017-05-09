package com.mygdx.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SimpleDirectionGestureDetector;
import com.mygdx.game.Swiper;

import java.util.Random;


/**
 * Created by acurr on 2/8/2017.
 */
public class PlayScreen implements Screen{
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
    private int currentColor;
    private final Random random;
    private final Sound sound1;
    private final Sound sound2;
    private final Sound sound3;
    private final Sound sound4;
    private final Sound death;
    private int score;
    private float alphaColor;
    private float alphaControl;
    private int timer;
    private final Label scoreLabel, highScoreLabel;
    private final Array<Label> controls;
    Preferences preferences;
    private int direction;
    private Boolean swipe;
    private Boolean firstTap;


    public PlayScreen(Swiper game) {
        this.game = game;
        game.toggleAds(false);
        gameViewPort = new StretchViewport(game.GAME_WIDTH,game.GAME_HEIGHT);
        cyan = new Texture(Gdx.files.internal("Cyan.png"));
        yellow = new Texture(Gdx.files.internal("Yellow.png"));
        orange = new Texture(Gdx.files.internal("Orange.png"));
        green = new Texture(Gdx.files.internal("Green.png"));
        gray = new Sprite(new Texture(Gdx.files.internal("Gray.png")));
        sounds = new Array<Sound>();
        sound1 = Gdx.audio.newSound(Gdx.files.internal("g5.ogg"));
        sound2 = Gdx.audio.newSound(Gdx.files.internal("c5.ogg"));
        sound3 = Gdx.audio.newSound(Gdx.files.internal("f5.ogg"));
        sound4 = Gdx.audio.newSound(Gdx.files.internal("d5.ogg"));
        death = Gdx.audio.newSound(Gdx.files.internal("ping.ogg"));
        preferences =  Gdx.app.getPreferences("Swiper");
        if(!preferences.contains("highScore")){
            preferences.putInteger("highscore",0);
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
        for (Sprite s:colors) {
            s.scale(5);
        }
        random = new Random();
        currentColor = random.nextInt(4);
        controls = new Array<Label>();
        keys = new Array<Integer>();
        int i = 0;
        if((Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)) {
            swipe =true;

            keys.add(Input.Keys.UP);
            keys.add(Input.Keys.RIGHT);
            keys.add(Input.Keys.LEFT);
            keys.add(Input.Keys.DOWN);

            Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
                @Override
                public void onUp() {
                    direction = Input.Keys.UP;
                    System.out.println("Up");
                }

                @Override
                public void onRight() {
                    direction = Input.Keys.RIGHT;
                    System.out.println("Right");
                }

                @Override
                public void onLeft() {
                    direction = Input.Keys.LEFT;
                    System.out.println("Left");
                }

                @Override
                public void onDown() {
                    direction = Input.Keys.DOWN;
                    System.out.println("Down");
                }
            }));
        }else {
            swipe =false;
            keys.add(Input.Keys.W);
            keys.add(Input.Keys.A);
            keys.add(Input.Keys.S);
            keys.add(Input.Keys.D);
        }
        for (Label l:controls) {
            l.setText(Input.Keys.toString(keys.get(i)));
            i++;
        }
        direction = 4;
        keys.shuffle();
        highScoreLabel = new Label(String.valueOf(getHighscore()),new Label.LabelStyle(game.textFont, Color.WHITE));
        scoreLabel = new Label(String.valueOf(score),new Label.LabelStyle(game.textFont, Color.WHITE));
        controls.add(new Label(Input.Keys.toString(keys.get(0)), new Label.LabelStyle(game.textFont, Color.WHITE)));
        controls.add(new Label(Input.Keys.toString(keys.get(1)), new Label.LabelStyle(game.textFont, Color.WHITE)));
        controls.add(new Label(Input.Keys.toString(keys.get(2)), new Label.LabelStyle(game.textFont, Color.WHITE)));
        controls.add(new Label(Input.Keys.toString(keys.get(3)), new Label.LabelStyle(game.textFont, Color.WHITE)));
        gray.setPosition(game.GAME_WIDTH, game.GAME_HEIGHT);
        for (Label l:controls) {
            l.setPosition((Gdx.graphics.getWidth()/2)-l.getWidth()/2,Gdx.graphics.getHeight() - l.getHeight() );
        }
        for (Sprite c:colors){
            c.setPosition(game.GAME_WIDTH, game.GAME_HEIGHT);
        }
        scoreLabel.setPosition(Gdx.graphics.getWidth()*.10f,Gdx.graphics.getHeight()*.90f);
        highScoreLabel.setPosition(Gdx.graphics.getWidth()*.10f,Gdx.graphics.getHeight()*.82f);
        score = 0;
        alphaColor = 1;
        alphaControl = 1;
        timer = 100;

        firstTap = true;
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
        controls.get(currentColor).draw(game.batch,alphaControl);
        scoreLabel.draw(game.batch,1);
        highScoreLabel.draw(game.batch,1);
        handleInput();
        game.batch.end();
    }

    private void handleInput() {
        timer--;
        alphaColor -= .01;
            if(firstTap) {
                direction = 4;
                firstTap = false;
            }

            if (swipe) {
                if(!firstTap) {
                    if (keys.get(currentColor) == direction) {
                        correct();
                        direction = 4;
                    } else if (!(keys.get(currentColor) != direction && direction == 4) || timer <= 0) {
                        inCorrect();
                    }
                }

            } else if (Gdx.input.isKeyJustPressed(keys.get(currentColor))) {
                correct();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || timer <= 0) {
                inCorrect();
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
        for (Sound s : sounds) {
            s.dispose();
        }
        for (Sprite t : colors) {
            t.getTexture().dispose();
        }

    }
    public  void setHighscore(int hs){
        preferences.putInteger("highScore",hs);
        preferences.flush();
    }
    public int getHighscore(){
        return preferences.getInteger("highScore");
    }

    private void correct(){
        score++;
        scoreLabel.setText(String.valueOf(score));

        System.out.println(score);
        currentColor = random.nextInt(4);
        sounds.get(currentColor).play();
        timer = 100;
        alphaColor = 1;
        if(alphaControl > 0) {
            alphaControl -= .05;
        }
        if(score%100 == 0){
            keys.shuffle();
            int i = 0;
            for (Label l:controls) {
                l.setText(Input.Keys.toString(keys.get(i)));
                i++;
            }
            alphaControl = 1;
        }
    }
    private void inCorrect(){
        death.play();
        if(score > getHighscore()){
            setHighscore(score);
            highScoreLabel.setText(String.valueOf(getHighscore()));
        }
        game.setScreen(new GameOverScreen(game,this));
    }

    public int getScore() {
        return score;
    }
}
