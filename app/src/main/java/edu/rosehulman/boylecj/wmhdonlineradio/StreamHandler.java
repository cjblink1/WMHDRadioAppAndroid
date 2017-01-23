package edu.rosehulman.boylecj.wmhdonlineradio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by Connor on 1/22/2017.
 */

public class StreamHandler {

    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private Uri mUri;


    public StreamHandler(Context context) {
        this.mContext = context;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mUri = Uri.parse(mContext.getResources().getString(R.string.stream_url));
    }

    public void connect() throws IOException, IncorrectStreamStateException {
        if (mMediaPlayer.isPlaying()) throw new IncorrectStreamStateException(mMediaPlayer.isPlaying());
        mMediaPlayer.setDataSource(mContext, mUri);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mMediaPlayer.prepareAsync();
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

}
