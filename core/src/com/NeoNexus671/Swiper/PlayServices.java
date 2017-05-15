package com.NeoNexus671.Swiper;

/**
 * Created by acurr on 5/14/2017.
 */
public interface PlayServices
{
    void signIn();
    void signOut();
    void rateGame();
    void unlockAchievement();
    void submitScore(int highScore);
    void showAchievement();
    void showScore();
    boolean isSignedIn();
}
