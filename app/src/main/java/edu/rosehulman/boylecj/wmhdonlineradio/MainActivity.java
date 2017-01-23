package edu.rosehulman.boylecj.wmhdonlineradio;

import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GetStreamData.DataDisplayer {

    private StreamHandler mStreamHandler;
    private ImageButton mPlayPause;
    private SlidingUpPanelLayout mPanel;
    private ImageButton mBigPlayPause;

    private TextView mShowInfo;
    private TextView mSongTitle;
    private TextView mSongArtist;
    private TextView mShowTitle;

    private ProgressBar mProgressBar;

    // Might be able to be replaced by a MediaPlayer function or something
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        isPlaying = false;

        mPlayPause = (ImageButton) findViewById(R.id.play_button);

        mBigPlayPause = (ImageButton) findViewById(R.id.play_button_big);

        setListener((mPlayPause));

        mPanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mPanel.addPanelSlideListener(new SlideListener());

        mSongTitle = (TextView) findViewById(R.id.song_title);
        mShowInfo = (TextView) findViewById(R.id.show_info);
        mSongArtist = (TextView) findViewById(R.id.song_artist);
        mShowTitle = (TextView) findViewById(R.id.show_title);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, new HomeFragment());
            ft.commit();
        }

        new GetStreamData(this).execute("http://dj.wmhdradio.org/api/live-info");
    }

    @Override
    public void onDataLoaded(Data data) {
        if (data != null && data.getCurrent() != null && data.getCurrentShow() != null) {
            mSongTitle.setText(data.getCurrent().getName());
            mShowInfo.setText(data.getCurrentShow().getName());
            mShowTitle.setText(data.getCurrentShow().getName());
            mSongArtist.setVisibility(View.GONE);
        }
    }

    // Private listener classes here:

    private class SlideListener implements SlidingUpPanelLayout.PanelSlideListener {

        @Override
        public void onPanelSlide(View panel, float slideOffset) {
        }

        @Override
        public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            setListener(mBigPlayPause);
            if (mPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                mPlayPause.setOnClickListener(new CloseListener());
                mPlayPause.setImageResource(R.drawable.ic_expand_more_black_24dp);
            } else {
                //TODO: set listener based on media player state
                setListener(mPlayPause);
            }
        }

    }

    private class CloseListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            setListener(mPlayPause);
        }
    }

    private class PlayListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("TTT", "pressed play");
            try {
                mStreamHandler.connect();
            } catch (IOException e) {
            } catch (StreamHandler.IncorrectStreamStateException e) {
                Log.e(getString(R.string.error), e.getMessage());
            }
            ImageButton button = (ImageButton)view;
            isPlaying = true;
            setListener(button);
        }
    }

    private class PauseListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("TTT", "pressed pause");
            try {
                mStreamHandler.disconnect();
            } catch (StreamHandler.IncorrectStreamStateException e) {
                Log.e(getString(R.string.error), e.getMessage());
            }
            ImageButton button = (ImageButton)view;
            isPlaying = false;
            setListener(button);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStreamHandler = new StreamHandler(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStreamHandler.close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            launchHome();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void launchHome() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new HomeFragment());
        ft.addToBackStack("home");
        ft.commit();
    }

    private void setListener(ImageButton playPause) {
        if (isPlaying) {
            playPause.setImageResource(R.drawable.ic_pause_black_24dp);
            playPause.setOnClickListener(new PauseListener());
        } else {
            playPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            playPause.setOnClickListener(new PlayListener());
        }
    }

    public void setPauseDisabled() {
        mBigPlayPause.setEnabled(false);
        mPlayPause.setEnabled(false);

    }

    public void setPauseEnabled() {
        mBigPlayPause.setEnabled(true);
        mPlayPause.setEnabled(true);
    }
    
}
