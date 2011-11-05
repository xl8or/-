package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import com.facebook.katana.util.jsonmirror.types.JMBase;
import com.facebook.katana.util.jsonmirror.types.JMDict;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookUser extends JMCachingDictDestination implements Parcelable {

   public static final Creator<FacebookUser> CREATOR = new FacebookUser.1();
   public static final long INVALID_ID = 255L;
   public static final String TAG = "FacebookUser";
   @JMAutogen.ExplicitType(
      jsonFieldName = "name",
      type = StringUtils.JMStrippedString.class
   )
   public final String mDisplayName;
   @JMAutogen.ExplicitType(
      jsonFieldName = "first_name",
      type = StringUtils.JMStrippedString.class
   )
   public final String mFirstName;
   @JMAutogen.ExplicitType(
      jsonFieldName = "pic_square",
      type = StringUtils.JMStrippedString.class
   )
   public final String mImageUrl;
   @JMAutogen.ExplicitType(
      jsonFieldName = "last_name",
      type = StringUtils.JMStrippedString.class
   )
   public final String mLastName;
   @JMAutogen.InferredType(
      jsonFieldName = "uid"
   )
   public final long mUserId;


   protected FacebookUser() {
      this.mUserId = 65535L;
      this.mFirstName = null;
      this.mLastName = null;
      this.mDisplayName = null;
      this.mImageUrl = null;
   }

   public FacebookUser(long var1, String var3, String var4, String var5, String var6) {
      this.mUserId = var1;
      this.mFirstName = var3;
      this.mLastName = var4;
      this.mDisplayName = var5;
      this.mImageUrl = var6;
   }

   public FacebookUser(Parcel var1) {
      long var2 = var1.readLong();
      this.mUserId = var2;
      String var4 = var1.readString();
      this.mFirstName = var4;
      String var5 = var1.readString();
      this.mLastName = var5;
      String var6 = var1.readString();
      this.mDisplayName = var6;
      String var7 = var1.readString();
      this.mImageUrl = var7;
   }

   public static FacebookUser newInstance(Class<? extends FacebookUser> var0, String var1) {
      FacebookUser var2;
      FacebookUser var3;
      try {
         var2 = (FacebookUser)var0.newInstance();
      } catch (IllegalAccessException var8) {
         var3 = null;
         return var3;
      } catch (InstantiationException var9) {
         var3 = null;
         return var3;
      }

      try {
         var2.setString("mDisplayName", var1);
      } catch (JMException var7) {
         var3 = null;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public static FacebookUser parseFromJSON(Class<? extends FacebookUser> var0, JsonParser var1) throws JsonParseException, IOException, JMException {
      JMDict var2 = JMAutogen.generateJMParser(var0);
      Object var3 = JMParser.parseJsonResponse(var1, (JMBase)var2);
      FacebookUser var4;
      if(var3 instanceof FacebookUser) {
         var4 = (FacebookUser)var3;
      } else {
         var4 = null;
      }

      return var4;
   }

   public int describeContents() {
      return 0;
   }

   public String getDisplayName() {
      String var1;
      if(this.mDisplayName == null) {
         Log.e("FacebookUser", "display name was requested, but is null");
         var1 = "";
      } else {
         var1 = this.mDisplayName;
      }

      return var1;
   }

   public JSONObject toJSONObject() {
      JSONObject var1;
      JSONObject var13;
      try {
         var1 = new JSONObject();
         long var2 = this.mUserId;
         var1.put("uid", var2);
         String var5 = this.mFirstName;
         var1.put("first_name", var5);
         String var7 = this.mLastName;
         var1.put("last_name", var7);
         String var9 = this.mDisplayName;
         var1.put("name", var9);
         String var11 = this.mImageUrl;
         var1.put("pic_square", var11);
      } catch (JSONException var15) {
         var13 = new JSONObject();
         return var13;
      }

      var13 = var1;
      return var13;
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.mUserId;
      var1.writeLong(var3);
      String var5 = this.mFirstName;
      var1.writeString(var5);
      String var6 = this.mLastName;
      var1.writeString(var6);
      String var7 = this.mDisplayName;
      var1.writeString(var7);
      String var8 = this.mImageUrl;
      var1.writeString(var8);
   }

   static class 1 implements Creator<FacebookUser> {

      1() {}

      public FacebookUser createFromParcel(Parcel var1) {
         return new FacebookUser(var1);
      }

      public FacebookUser[] newArray(int var1) {
         return new FacebookUser[var1];
      }
   }
}
