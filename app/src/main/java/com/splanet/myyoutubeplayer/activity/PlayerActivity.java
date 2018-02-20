package com.splanet.myyoutubeplayer.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.splanet.myyoutubeplayer.R;

public class PlayerActivity extends YouTubeFailureRecoveryActivity {

    public static final String DEVELOPER_KEY = "AIzaSyD-Z0fbcgd9jTW4QmevtPMPeqmZ4-9XCJ0";
    public static final String ARG_YOUTUBE_ID = "ARG_YOUTUBE_ID";

    private String youTubeID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            youTubeID = bundle.getString(ARG_YOUTUBE_ID);
        }

        YouTubePlayerFragment youTubePlayerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(DEVELOPER_KEY, this);
        Log.d("PlayerActivity", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("PlayerActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("PlayerActivity", "onResume");
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            youTubePlayer.loadVideo(youTubeID);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("PlayerActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("PlayerActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("PlayerActivity", "onDestory");
    }
}
