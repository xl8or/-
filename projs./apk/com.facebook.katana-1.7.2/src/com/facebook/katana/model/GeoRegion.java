package com.facebook.katana.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.katana.util.jsonmirror.JMAutogen;
import com.facebook.katana.util.jsonmirror.JMCachingDictDestination;
import java.util.Iterator;
import java.util.List;

public class GeoRegion extends JMCachingDictDestination {

   public static final long INVALID_ID = 255L;
   @JMAutogen.InferredType(
      jsonFieldName = "abbr_name"
   )
   public final String abbrName = null;
   @JMAutogen.InferredType(
      jsonFieldName = "page_fbid"
   )
   public final long id = 65535L;
   @JMAutogen.InferredType(
      jsonFieldName = "type"
   )
   public final String type = null;


   private GeoRegion() {}

   public static GeoRegion.ImplicitLocation createImplicitLocation(List<GeoRegion> var0) {
      GeoRegion.ImplicitLocation var1;
      if(var0 == null) {
         var1 = null;
      } else {
         String var2 = null;
         String var3 = null;
         Long var4 = null;
         Iterator var5 = var0.iterator();

         while(var5.hasNext()) {
            GeoRegion var6 = (GeoRegion)var5.next();
            String var7 = var6.type;
            String var8 = GeoRegion.Type.city.toString();
            if(var7.equals(var8)) {
               var2 = var6.abbrName;
               var4 = Long.valueOf(var6.id);
            } else {
               String var9 = var6.type;
               String var10 = GeoRegion.Type.state.toString();
               if(var9.equals(var10)) {
                  var3 = var6.abbrName;
               }
            }
         }

         String var11 = null;
         if(var2 != null) {
            var11 = var2;
         } else if(var3 != null) {
            var11 = var3;
         }

         if(var11 != null) {
            var1 = new GeoRegion.ImplicitLocation(var11, var4);
         } else {
            var1 = null;
         }
      }

      return var1;
   }

   public static class ImplicitLocation implements Parcelable {

      public static final Creator<GeoRegion.ImplicitLocation> CREATOR = new GeoRegion.ImplicitLocation.1();
      public String label;
      public Long pageId;


      public ImplicitLocation(Parcel var1) {
         String var2 = var1.readString();
         this.label = var2;
         Long var3 = Long.valueOf(var1.readLong());
         this.pageId = var3;
      }

      public ImplicitLocation(String var1, Long var2) {
         this.label = var1;
         this.pageId = var2;
      }

      public int describeContents() {
         return 0;
      }

      public void writeToParcel(Parcel var1, int var2) {
         String var3 = this.label;
         var1.writeString(var3);
         long var4 = this.pageId.longValue();
         var1.writeLong(var4);
      }

      static class 1 implements Creator<GeoRegion.ImplicitLocation> {

         1() {}

         public GeoRegion.ImplicitLocation createFromParcel(Parcel var1) {
            return new GeoRegion.ImplicitLocation(var1);
         }

         public GeoRegion.ImplicitLocation[] newArray(int var1) {
            return new GeoRegion.ImplicitLocation[var1];
         }
      }
   }

   public static enum Type {

      // $FF: synthetic field
      private static final GeoRegion.Type[] $VALUES;
      city("city", 0),
      state("state", 1);


      static {
         GeoRegion.Type[] var0 = new GeoRegion.Type[2];
         GeoRegion.Type var1 = city;
         var0[0] = var1;
         GeoRegion.Type var2 = state;
         var0[1] = var2;
         $VALUES = var0;
      }

      private Type(String var1, int var2) {}
   }
}
