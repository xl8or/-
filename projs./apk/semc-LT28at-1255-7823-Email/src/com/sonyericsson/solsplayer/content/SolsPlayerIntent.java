package com.sonyericsson.solsplayer.content;


public class SolsPlayerIntent {

   public static final String ACTION_CONTENT_STARTED = "com.sonyericsson.solsplayer.intent.action.CONTENT_STARTED";
   public static final String ACTION_PLAY_LOCAL = "com.sonyericsson.solsplayer.intent.action.PLAY_LOCAL";
   public static final String ACTION_PLAY_STREAMING = "com.sonyericsson.solsplayer.intent.action.PLAY_STREAMING";
   public static final String EXTRA_PATH = "path";
   public static final String EXTRA_TITLE = "extra_title";
   public static final String MIME_TYPE_MP4 = "video/mp4";
   public static final String MIME_TYPE_SOLS = "video/vnd.sony.mnv";
   private static final String PREFIX_ACTION = "com.sonyericsson.solsplayer.intent.action.";


   public SolsPlayerIntent() {}
}
