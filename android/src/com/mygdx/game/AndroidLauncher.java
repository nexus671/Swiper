package com.mygdx.game;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {
	private static final String TAG = "AndroidLauncher";
	protected AdView adView;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout layout = new RelativeLayout(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new Swiper(), config);
		layout.addView(gameView);
		adView = new AdView(this);

		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Log.i(TAG, "Ad Loaded....");
			}
		});
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-4083946983046027/6249509590");
		AdRequest.Builder builder = new AdRequest.Builder();
		builder.addTestDevice("6381DD4BC5C8B60502B4F508ACF4E414");
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);

		//layout.addView(adView,adParams); Stoping ads for now till can move to the bottom
		adView.loadAd(builder.build());
		setContentView(layout);
		//initialize(new Swiper(), config);
	}
}
