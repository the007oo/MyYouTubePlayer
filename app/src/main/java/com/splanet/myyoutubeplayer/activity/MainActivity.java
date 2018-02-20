package com.splanet.myyoutubeplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.splanet.myyoutubeplayer.R;
import com.splanet.myyoutubeplayer.adapter.YouTubeAdapter;
import com.splanet.myyoutubeplayer.manager.YoutubeConnector;
import com.splanet.myyoutubeplayer.model.Video;

import java.util.List;

import static com.splanet.myyoutubeplayer.activity.PlayerActivity.ARG_YOUTUBE_ID;


public class MainActivity extends AppCompatActivity {

    private android.widget.EditText searchBox;
    private android.support.v7.widget.RecyclerView recycleView;
    private Handler handler;
    private YouTubeAdapter youTubeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        setRecycleView();
        setUpSearchBox();
    }

    private void initWidget() {
        this.recycleView = (RecyclerView) findViewById(R.id.recycleView);
        this.searchBox = (EditText) findViewById(R.id.searchBox);

        handler = new Handler();
        searchOnYoutube("เพลงไทย คาราโอเกะ");
    }

    private void setUpSearchBox() {
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchOnYoutube(editable.toString());
            }
        });

        searchBox.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchOnYoutube(v.getText().toString());
                    return false;
                }
                return true;
            }
        });
    }

    private void setRecycleView() {
        youTubeAdapter = new YouTubeAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.setAdapter(youTubeAdapter);
        youTubeAdapter.setOnSetClickListener(new YouTubeAdapter.onSetClickListener() {
            @Override
            public void onSetClickListener(String id) {
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                intent.putExtra(ARG_YOUTUBE_ID, id);
                startActivity(intent);
            }
        });
    }

    private void searchOnYoutube(final String keywords) {
        new Thread() {
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(MainActivity.this);
                final List<Video> searchResults = yc.search(keywords);
                handler.post(new Runnable() {
                    public void run() {
                        updateVideosFound(searchResults);
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound(List<Video> searchResults) {
        youTubeAdapter.addItem(searchResults);
    }

}
