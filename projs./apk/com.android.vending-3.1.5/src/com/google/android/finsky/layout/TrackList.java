package com.google.android.finsky.layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.finsky.adapters.TrackListAdapter;
import com.google.android.finsky.model.Track;
import com.google.android.finsky.utils.FinskyLog;
import java.io.IOException;
import java.util.List;

public class TrackList extends LinearLayout implements OnPreparedListener, OnCompletionListener {

   private TrackListAdapter mAdapter;
   private TrackList.PlaybackMode mCurrentPlaybackMode;
   private Track mCurrentTrack;
   private int mCurrentTrackIndex = -1;
   private boolean mIsPreparing = 0;
   private ListView mListView;
   private final OnClickListener mOnTrackClickListener;
   private MediaPlayer mPlayer;


   public TrackList(Context var1, AttributeSet var2) {
      super(var1, var2);
      TrackList.4 var3 = new TrackList.4();
      this.mOnTrackClickListener = var3;
      this.setOrientation(1);
   }

   private void playOneTrack(int var1) {
      if(!this.mIsPreparing || this.mCurrentTrackIndex != var1) {
         this.resetMediaPlayer();
         if(this.mCurrentTrackIndex >= 0) {
            Track.TrackMode var2 = Track.TrackMode.READY;
            this.updateCurrentTrackMode(var2);
            this.mCurrentTrackIndex = -1;
            this.mCurrentTrack = null;
         }

         this.mCurrentTrackIndex = var1;
         Track var3 = (Track)this.mAdapter.getItem(var1);
         this.mCurrentTrack = var3;
         String var4 = this.mCurrentTrack.url;

         try {
            this.mPlayer.setDataSource(var4);
            this.mPlayer.prepareAsync();
            this.mIsPreparing = (boolean)1;
         } catch (IllegalStateException var12) {
            return;
         } catch (IOException var13) {
            Object[] var8 = new Object[1];
            String var9 = var13.toString();
            var8[0] = var9;
            FinskyLog.e("Music preview playback error: %s", var8);
            Context var10 = this.getContext();
            String var11 = this.getContext().getString(2131230931);
            Toast.makeText(var10, var11, 0).show();
         }

         Track.TrackMode var5 = Track.TrackMode.LOADING;
         this.updateCurrentTrackMode(var5);
      }
   }

   private void resetMediaPlayer() {
      if(this.mPlayer.isPlaying()) {
         Track.TrackMode var1 = Track.TrackMode.READY;
         this.updateCurrentTrackMode(var1);
         this.mPlayer.stop();
      }

      this.mPlayer.reset();
   }

   private void setupLayout() {
      LinearLayout var1 = (LinearLayout)((LayoutInflater)this.getContext().getSystemService("layout_inflater")).inflate(2130968666, (ViewGroup)null);
      Button var2 = (Button)var1.findViewById(2131755246);
      TrackList.1 var3 = new TrackList.1();
      var2.setOnClickListener(var3);
      Button var4 = (Button)var1.findViewById(2131755245);
      TrackList.2 var5 = new TrackList.2();
      var4.setOnClickListener(var5);
      Button var6 = (Button)var1.findViewById(2131755247);
      TrackList.3 var7 = new TrackList.3();
      var6.setOnClickListener(var7);
      Context var8 = this.getContext();
      ListView var9 = new ListView(var8);
      this.mListView = var9;
      this.mListView.setDivider((Drawable)null);
      this.mListView.setFadingEdgeLength(0);
      this.mListView.setCacheColorHint(-3355444);
      this.addView(var1);
      ListView var10 = this.mListView;
      this.addView(var10);
   }

   private void skipToSong(int var1) {
      TrackList.PlaybackMode var2 = this.mCurrentPlaybackMode;
      TrackList.PlaybackMode var3 = TrackList.PlaybackMode.PLAY_ALL_TRACKS;
      if(var2 == var3) {
         if(var1 >= 0) {
            int var4 = this.mAdapter.getCount();
            if(var1 < var4) {
               this.playOneTrack(var1);
               return;
            }
         }

         this.resetMediaPlayer();
         Track.TrackMode var5 = Track.TrackMode.READY;
         this.updateCurrentTrackMode(var5);
         TrackList.PlaybackMode var6 = TrackList.PlaybackMode.PLAY_ONE_TRACK;
         this.mCurrentPlaybackMode = var6;
         this.mCurrentTrack = null;
         this.mCurrentTrackIndex = -1;
      }
   }

   private void updateCurrentTrackMode(Track.TrackMode var1) {
      if(this.mCurrentTrack != null) {
         Track.TrackMode var2 = Track.TrackMode.LOADING;
         if(var1 == var2) {
            ListView var3 = this.mListView;
            int var4 = this.mCurrentTrackIndex;
            var3.setSelection(var4);
         }

         this.mCurrentTrack.mode = var1;
         this.mAdapter.notifyDataSetChanged();
      }
   }

   protected void onAttachedToWindow() {
      MediaPlayer var1 = new MediaPlayer();
      this.mPlayer = var1;
      this.mPlayer.setOnPreparedListener(this);
      if(this.mAdapter != null) {
         this.mAdapter.notifyDataSetChanged();
      }
   }

   public void onCompletion(MediaPlayer var1) {
      TrackList.PlaybackMode var2 = this.mCurrentPlaybackMode;
      TrackList.PlaybackMode var3 = TrackList.PlaybackMode.PLAY_ONE_TRACK;
      if(var2 == var3) {
         Track.TrackMode var4 = Track.TrackMode.READY;
         this.updateCurrentTrackMode(var4);
      } else {
         int var5 = this.mCurrentTrackIndex + 1;
         this.skipToSong(var5);
      }
   }

   protected void onDetachedFromWindow() {
      this.mPlayer.release();
      this.mIsPreparing = (boolean)0;
      if(this.mCurrentTrack != null) {
         Track var1 = this.mCurrentTrack;
         Track.TrackMode var2 = Track.TrackMode.READY;
         var1.mode = var2;
      }

      this.mCurrentTrack = null;
      this.mCurrentTrackIndex = -1;
   }

   public void onPrepared(MediaPlayer var1) {
      this.mIsPreparing = (boolean)0;
      var1.start();
      Track.TrackMode var2 = Track.TrackMode.PLAYING;
      this.updateCurrentTrackMode(var2);
   }

   public void playAllTracks() {
      TrackList.PlaybackMode var1 = this.mCurrentPlaybackMode;
      TrackList.PlaybackMode var2 = TrackList.PlaybackMode.PLAY_ALL_TRACKS;
      if(var1 == var2) {
         this.resetMediaPlayer();
      }

      TrackList.PlaybackMode var3 = TrackList.PlaybackMode.PLAY_ALL_TRACKS;
      this.mCurrentPlaybackMode = var3;
      this.playOneTrack(0);
   }

   public void setTracks(List<Track> var1) {
      TrackList.PlaybackMode var2 = TrackList.PlaybackMode.PLAY_ONE_TRACK;
      this.mCurrentPlaybackMode = var2;
      this.setupLayout();
      Context var3 = this.getContext();
      OnClickListener var4 = this.mOnTrackClickListener;
      TrackListAdapter var5 = new TrackListAdapter(var3, 2130968663, var1, var4);
      this.mAdapter = var5;
      ListView var6 = this.mListView;
      TrackListAdapter var7 = this.mAdapter;
      var6.setAdapter(var7);
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         TrackList var2 = TrackList.this;
         int var3 = TrackList.this.mCurrentTrackIndex + -1;
         var2.skipToSong(var3);
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         TrackList.this.playAllTracks();
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(View var1) {
         int var2 = TrackList.this.mListView.getPositionForView(var1);
         TrackList var3 = TrackList.this;
         TrackList.PlaybackMode var4 = TrackList.PlaybackMode.PLAY_ONE_TRACK;
         var3.mCurrentPlaybackMode = var4;
         int var6 = TrackList.this.mCurrentTrackIndex;
         if(var2 == var6) {
            if(TrackList.this.mPlayer.isPlaying()) {
               TrackList.this.mPlayer.pause();
               TrackList var7 = TrackList.this;
               Track.TrackMode var8 = Track.TrackMode.PAUSE;
               var7.updateCurrentTrackMode(var8);
            } else if(!TrackList.this.mIsPreparing) {
               TrackList.this.mPlayer.start();
               TrackList var9 = TrackList.this;
               Track.TrackMode var10 = Track.TrackMode.PLAYING;
               var9.updateCurrentTrackMode(var10);
            }
         } else {
            TrackList.this.playOneTrack(var2);
         }
      }
   }

   private static enum PlaybackMode {

      // $FF: synthetic field
      private static final TrackList.PlaybackMode[] $VALUES;
      PLAY_ALL_TRACKS("PLAY_ALL_TRACKS", 1),
      PLAY_ONE_TRACK("PLAY_ONE_TRACK", 0);


      static {
         TrackList.PlaybackMode[] var0 = new TrackList.PlaybackMode[2];
         TrackList.PlaybackMode var1 = PLAY_ONE_TRACK;
         var0[0] = var1;
         TrackList.PlaybackMode var2 = PLAY_ALL_TRACKS;
         var0[1] = var2;
         $VALUES = var0;
      }

      private PlaybackMode(String var1, int var2) {}
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         TrackList var2 = TrackList.this;
         int var3 = TrackList.this.mCurrentTrackIndex + 1;
         var2.skipToSong(var3);
      }
   }
}
