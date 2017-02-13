package edu.rosehulman.boylecj.wmhdonlineradio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents.ScheduleFragment;

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
    private boolean initiallyLoaded = false;
    private ProgressBarUpdateTask mPBUpdateTask;
    private UpdateTimer mTimer;

    // Might be able to be replaced by a MediaPlayer function or something
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
//        toolbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

        isPlaying = false;

        mPlayPause = (ImageButton) findViewById(R.id.play_button);

        mBigPlayPause = (ImageButton) findViewById(R.id.play_button_big);

        setListener(mPlayPause, false);

        mPanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mPanel.addPanelSlideListener(new SlideListener());

        mSongTitle = (TextView) findViewById(R.id.song_title);
        mShowInfo = (TextView) findViewById(R.id.show_info);
        mSongArtist = (TextView) findViewById(R.id.song_artist);
        mShowTitle = (TextView) findViewById(R.id.show_title);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // start progress bar updater
        mPBUpdateTask = new ProgressBarUpdateTask();

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, new HomeFragment());
            ft.commit();
        }

        mTimer = new UpdateTimer(this);
        mTimer.run();
//        new GetStreamData(this).execute("http://dj.wmhdradio.org/api/live-info");

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (frag instanceof HomeFragment) {
                    navigationView.setCheckedItem(R.id.nav_home);
                } else if (frag instanceof AboutFragment) {
                    navigationView.setCheckedItem(R.id.nav_about);
                } else if (frag instanceof  ScheduleFragment) {
                    navigationView.setCheckedItem(R.id.nav_schedule);
                }
            }
        });
    }

    @Override
    public void onDataLoaded(LiveInfoData data) {
        if (data != null && data.getCurrent() != null && data.getCurrentShow() != null) {
            mSongTitle.setText(data.getCurrent().getTrack_title());
            mShowInfo.setText(data.getCurrentShow().getName());
            mShowTitle.setText(data.getCurrentShow().getName());
            mSongArtist.setText(data.getCurrent().getArtist_name());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        String showStartString = data.getCurrentShow().getStart_timestamp() + " -0500";
        String showEndString = data.getCurrentShow().getEnd_timestamp() + " -0500";

        Date showStartDate = sdf.parse(showStartString, new ParsePosition(0));
        Date showEndDate = sdf.parse(showEndString, new ParsePosition(0));

        if (!initiallyLoaded) {

            mPBUpdateTask.execute(new Params(showStartDate.getTime(), showEndDate.getTime(), this));
            initiallyLoaded = true;
        } else {
            mPBUpdateTask.updateInfo(showStartDate.getTime(), showEndDate.getTime());
        }


    }

    // Private listener classes here:

    private class SlideListener implements SlidingUpPanelLayout.PanelSlideListener {

        @Override
        public void onPanelSlide(View panel, float slideOffset) {
        }

        @Override
        public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            setListener(mBigPlayPause, true);
            if (mPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                mPlayPause.setOnClickListener(new CloseListener());
                mPlayPause.setImageResource(R.drawable.ic_expand_more_black_24dp);
            } else {
                //TODO: set listener based on media player state
                setListener(mPlayPause, false);
            }
        }

    }

    private class CloseListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            setListener(mPlayPause, false);
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
            setListener(button, button.getId() == R.id.play_button_big);
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
            setListener(button, button.getId() == R.id.play_button_big);
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
//        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, Constants.URL_TO_SHARE);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
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
        } else if (id == R.id.nav_schedule) {
            launchSchedule();
        } else if (id == R.id.nav_about) {
            launchAbout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void launchAbout() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new AboutFragment());
        ft.addToBackStack("about");
        ft.commit();
    }

    public void launchSchedule() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new ScheduleFragment());
        ft.addToBackStack("schedule");
        ft.commit();
    }

    public void launchHome() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, new HomeFragment());
        ft.addToBackStack("home");
        ft.commit();
    }

    private void setListener(ImageButton playPause, boolean isWhite) {
        if (isWhite) {
            if (isPlaying) {
                playPause.setImageResource(R.drawable.ic_pause_white_24dp);
                playPause.setOnClickListener(new PauseListener());
            } else {
                playPause.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                playPause.setOnClickListener(new PlayListener());
            }
            return;
        }
        if (isPlaying) {
            playPause.setImageResource(R.drawable.ic_pause_black_24dp);
            playPause.setOnClickListener(new PauseListener());
        } else {
            playPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            playPause.setOnClickListener(new PlayListener());
        }
    }

    public void resetPause() {
        isPlaying = false;
        setListener(mPlayPause, mPlayPause.getId() == R.id.play_button_big);
        setListener(mBigPlayPause, mBigPlayPause.getId() == R.id.play_button_big);
    }

    public void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void setPauseDisabled() {
        mBigPlayPause.setEnabled(false);
        mPlayPause.setEnabled(false);

    }

    public void setPauseEnabled() {
        mBigPlayPause.setEnabled(true);
        mPlayPause.setEnabled(true);
    }

    public void updateProgressBar(int progress) {
        mProgressBar.setProgress(progress);
    }

    public void mediaPrepared() {
        mTimer.cancel();
        mTimer.run();
    }
    
}

