package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.model.FacebookUser;
import com.facebook.katana.util.StringUtils;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import com.facebook.katana.util.jsonmirror.JMException;
import com.facebook.katana.util.jsonmirror.JMParser;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookProfile extends JMCachingDictDestination implements Parcelable {

   public static final Creator<FacebookProfile> CREATOR = new FacebookProfile.1();
   public static final long INVALID_ID = 255L;
   public static final int TYPE_EVENT = 4;
   public static final int TYPE_GROUP = 3;
   public static final int TYPE_PAGE = 1;
   public static final int TYPE_PLACE_PAGE = 2;
   public static final int TYPE_USER;
   @JMAutogen.ExplicitType(
      jsonFieldName = "name",
      type = StringUtils.JMStrippedString.class
   )
   public final String mDisplayName;
   @JMAutogen.InferredType(
      jsonFieldName = "id"
   )
   public final long mId;
   @JMAutogen.InferredType(
      jsonFieldName = "pic_square"
   )
   public final String mImageUrl;
   public final int mType;
   @JMAutogen.InferredType(
      jsonFieldName = "type"
   )
   private String mTypeString;


   public FacebookProfile() {
      this.mId = 65535L;
      this.mDisplayName = null;
      this.mImageUrl = null;
      this.mType = 0;
   }

   public FacebookProfile(long var1, String var3, String var4, int var5) {
      this.mId = var1;
      this.mDisplayName = var3;
      this.mImageUrl = var4;
      this.mType = var5;
   }

   protected FacebookProfile(Parcel var1) {
      long var2 = var1.readLong();
      this.mId = var2;
      String var4 = var1.readString();
      this.mDisplayName = var4;
      String var5 = var1.readString();
      this.mImageUrl = var5;
      int var6 = var1.readInt();
      this.mType = var6;
   }

   public FacebookProfile(FacebookUser var1) {
      long var2 = var1.mUserId;
      this.mId = var2;
      String var4 = var1.mDisplayName;
      this.mDisplayName = var4;
      String var5 = var1.mImageUrl;
      this.mImageUrl = var5;
      this.mType = 0;
   }

   public static FacebookProfile parseJson(JsonParser var0) throws JsonParseException, IOException, JMException {
      return (FacebookProfile)JMParser.parseObjectJson(var0, FacebookProfile.class);
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      boolean var6;
      if(var1 instanceof FacebookProfile) {
         long var2 = this.mId;
         long var4 = ((FacebookProfile)var1).mId;
         if(var2 == var4) {
            var6 = true;
            return var6;
         }
      }

      var6 = false;
      return var6;
   }

   public int hashCode() {
      return (int)this.mId;
   }

   protected void postprocess() throws JMException {
      if(this.mTypeString != null && this.mTypeString.equals("page")) {
         this.setLong("mType", 1L);
      }

      this.mTypeString = null;
   }

   public JSONObject toJSONObject() {
      JSONObject var1;
      JSONObject var9;
      try {
         var1 = new JSONObject();
         long var2 = this.mId;
         var1.put("id", var2);
         String var5 = this.mDisplayName;
         var1.put("name", var5);
         String var7 = this.mImageUrl;
         var1.put("pic_square", var7);
      } catch (JSONException var11) {
         var9 = new JSONObject();
         return var9;
      }

      var9 = var1;
      return var9;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("FacebookProfile(");
      String var3 = this.mDisplayName;
      StringBuilder var4 = var2.append(var3).append(" (id=");
      long var5 = this.mId;
      StringBuilder var7 = var4.append(var5).append("))");
      return var1.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      long var3 = this.mId;
      var1.writeLong(var3);
      String var5 = this.mDisplayName;
      var1.writeString(var5);
      String var6 = this.mImageUrl;
      var1.writeString(var6);
      int var7 = this.mType;
      var1.writeInt(var7);
   }

   static class 1 implements Creator<FacebookProfile> {

      1() {}

      public FacebookProfile createFromParcel(Parcel var1) {
         return new FacebookProfile(var1);
      }

      public FacebookProfile[] newArray(int var1) {
         return new FacebookProfile[var1];
      }
   }
}
