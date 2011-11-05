package com.sonyericsson.camera;


public class CameraIntent {

   public static final String EXTRA_CAPTURING_MODE = "com.sonyericsson.camera.intent.extra.CAPTURING_MODE";
   public static final String EXTRA_FACING = "facing";
   public static final String MIME_TYPE = "image/mpo";
   public static final String MODE_3D_PANORAMA = "3D_panorama";
   public static final String MODE_AUTO = "auto";
   public static final String MODE_MANUAL = "manual";
   public static final String MODE_MULTI_ANGLE = "multi_angle";
   public static final String MODE_SCENE = "scene_recognition";
   public static final String MODE_SMILE = "smile_detection";
   public static final String MODE_SWEEP_PANORAMA = "sweep_panorama";


   private CameraIntent() {
      throw new AssertionError();
   }
}
