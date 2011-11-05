package com.google.android.finsky.download.obb;

import com.google.android.finsky.download.obb.ObbState;
import java.io.File;

public interface Obb {

   String MAIN_OBB_FILE_PREFIX = "main";
   String PATCH_OBB_FILE_PREFIX = "patch";


   void delete();

   boolean finalizeTempFile();

   String getContentUri();

   File getFile();

   long getSize();

   ObbState getState();

   File getTempFile();

   String getUrl();

   int getVersionCode();

   boolean isPatch();

   void setContentUri(String var1);

   void setState(ObbState var1);

   void syncStateWithStorage();
}
