package edu.rosehulman.boylecj.wmhdonlineradio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Connor on 1/22/2017.
 */

public class StreamHandler {

    private MediaPlayer mMediaPlayer;
    private MainActivity mContext;
    private Uri mUri;
    private int numConnectionAttempts;
    private static final int NUM_ALLOWED_CONNECTION_ATTEMPTS = 1;


    public StreamHandler(MainActivity context) {
        this.mContext = context;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mUri = Uri.parse(mContext.getResources().getString(R.string.stream_url));
        numConnectionAttempts = 0;
    }

    public void connect() throws IOException, IncorrectStreamStateException {
        if (mMediaPlayer.isPlaying()) throw new IncorrectStreamStateException(mMediaPlayer.isPlaying());

        mMediaPlayer.setDataSource(mContext, mUri);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mContext.setPauseEnabled();
                mediaPlayer.start();
            }
        });
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
                if (numConnectionAttempts < NUM_ALLOWED_CONNECTION_ATTEMPTS) {
                    Log.d(Constants.TAG, "Couldn't connect, trying again...");
                    numConnectionAttempts++;
                    try {
                        connect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IncorrectStreamStateException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(Constants.TAG, "Exceeded number of allowed connection attempts");
                    mContext.resetPause();
                    mContext.setPauseEnabled();
                }
                return true;
            }
        });
        mMediaPlayer.prepareAsync();
        mContext.setPauseDisabled();
    }

    public void disconnect() throws IncorrectStreamStateException {
        if (!mMediaPlayer.isPlaying()) throw new IncorrectStreamStateException(mMediaPlayer.isPlaying());
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public void close() {
        mMediaPlayer.release();
    }

    class IncorrectStreamStateException extends Exception {
        boolean playing;

        public IncorrectStreamStateException(boolean state){
            this.playing = state;
        }
        @Override
        public String getMessage() {
            return "Invalid state! Player is in the "+(this.playing ? "playing" : "stopped") + "state.";
        }
    }

    public void resetConnectionAttempts(){
        numConnectionAttempts = 0;
    }

}
