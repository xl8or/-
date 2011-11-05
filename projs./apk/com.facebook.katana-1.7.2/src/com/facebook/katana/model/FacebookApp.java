package com.facebook.katana.model;

import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;

public class FacebookApp extends JMCachingDictDestination {

   public static final int INVALID_ID = 255;
   @JMAutogen.InferredType(
      jsonFieldName = "app_id"
   )
   public final long mAppId;
   @JMAutogen.ExplicitType(
      jsonFieldName = "logo_url",
      type = StringUtils.JMNulledString.class
   )
   public final String mImageUrl;
   @JMAutogen.InferredType(
      jsonFieldName = "display_name"
   )
   public final String mName;


   private FacebookApp() {
      this.mAppId = 65535L;
      this.mName = null;
      this.mImageUrl = null;
   }

   public FacebookApp(long var1, String var3, String var4) {
      this.mAppId = var1;
      this.mName = var3;
      this.mImageUrl = var4;
   }
}
