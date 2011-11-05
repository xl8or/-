package com.google.android.gtalkservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Presence implements Parcelable {

   public static final Creator<Presence> CREATOR = new Presence.1();
   public static final int HAS_CAMERA_V1 = 4;
   public static final int HAS_PMUC_V1 = 8;
   public static final int HAS_VIDEO_V1 = 2;
   public static final int HAS_VOICE_V1 = 1;
   public static final int NO_CAPABILITIES = 0;
   public static final Presence OFFLINE = new Presence();
   private static final int STATUS_MIN_VERSION_FOR_INVISIBILITY = 2;
   private boolean mAllowInvisibility;
   private boolean mAvailable;
   private int mCapabilities;
   private List<String> mDefaultStatusList;
   private List<String> mDndStatusList;
   private boolean mInvisible;
   private Presence.Show mShow;
   private String mStatus;
   private int mStatusListContentsMax;
   private int mStatusListMax;
   private int mStatusMax;


   public Presence() {
      Presence.Show var1 = Presence.Show.NONE;
      this((boolean)0, var1, (String)null, 8);
   }

   public Presence(Parcel var1) {
      byte var2 = 1;
      super();
      int var3 = var1.readInt();
      this.setStatusMax(var3);
      int var4 = var1.readInt();
      this.setStatusListMax(var4);
      int var5 = var1.readInt();
      this.setStatusListContentsMax(var5);
      byte var6;
      if(var1.readInt() != 0) {
         var6 = 1;
      } else {
         var6 = 0;
      }

      this.setAllowInvisibility((boolean)var6);
      byte var7;
      if(var1.readInt() != 0) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      this.setAvailable((boolean)var7);
      String var8 = var1.readString();
      Presence.Show var9 = (Presence.Show)Enum.valueOf(Presence.Show.class, var8);
      this.setShow(var9);
      String var10 = var1.readString();
      this.mStatus = var10;
      if(var1.readInt() == 0) {
         var2 = 0;
      }

      this.setInvisible((boolean)var2);
      ArrayList var12 = new ArrayList();
      this.mDefaultStatusList = var12;
      List var13 = this.mDefaultStatusList;
      var1.readStringList(var13);
      ArrayList var14 = new ArrayList();
      this.mDndStatusList = var14;
      List var15 = this.mDndStatusList;
      var1.readStringList(var15);
      int var16 = var1.readInt();
      this.setCapabilities(var16);
   }

   public Presence(Presence var1) {
      int var2 = var1.mStatusMax;
      this.mStatusMax = var2;
      int var3 = var1.mStatusListMax;
      this.mStatusListMax = var3;
      int var4 = var1.mStatusListContentsMax;
      this.mStatusListContentsMax = var4;
      boolean var5 = var1.mAllowInvisibility;
      this.mAllowInvisibility = var5;
      boolean var6 = var1.mAvailable;
      this.mAvailable = var6;
      Presence.Show var7 = var1.mShow;
      this.mShow = var7;
      String var8 = var1.mStatus;
      this.mStatus = var8;
      boolean var9 = var1.mInvisible;
      this.mInvisible = var9;
      List var10 = var1.mDefaultStatusList;
      this.mDefaultStatusList = var10;
      List var11 = var1.mDndStatusList;
      this.mDndStatusList = var11;
      int var12 = var1.mCapabilities;
      this.mCapabilities = var12;
   }

   public Presence(boolean var1, Presence.Show var2, String var3, int var4) {
      this.mAvailable = var1;
      this.mShow = var2;
      this.mStatus = var3;
      this.mInvisible = (boolean)0;
      ArrayList var5 = new ArrayList();
      this.mDefaultStatusList = var5;
      ArrayList var6 = new ArrayList();
      this.mDndStatusList = var6;
      this.mCapabilities = var4;
   }

   private boolean addToList(List<String> var1, String var2) {
      boolean var3 = false;
      if(!TextUtils.isEmpty(var2)) {
         Iterator var4 = var1.iterator();

         String var5;
         String var6;
         do {
            if(!var4.hasNext()) {
               int var7 = this.getStatusMax();
               if(var2.length() > var7) {
                  var2 = var2.substring(0, var7);
               }

               var1.add(0, var2);
               this.checkListContentsLength(var1);
               var3 = true;
               break;
            }

            var5 = ((String)var4.next()).trim();
            var6 = var2.trim();
         } while(!var5.equals(var6));
      }

      return var3;
   }

   private List<String> checkListContentsLength(List<String> var1) {
      int var2 = this.getStatusListContentsMax();
      int var3 = var1.size();
      if(var3 > var2) {
         for(int var4 = var3 + -1; var4 >= var2; var4 += -1) {
            var1.remove(var4);
         }
      }

      return var1;
   }

   private boolean listEqual(List<String> var1, List<String> var2) {
      boolean var3 = false;
      int var4 = var1.size();
      int var5 = var2.size();
      if(var4 == var5) {
         int var6 = 0;

         while(true) {
            if(var6 >= var4) {
               var3 = true;
               break;
            }

            String var7 = (String)var1.get(var6);
            String var8 = (String)var2.get(var6);
            if(!var7.equals(var8)) {
               break;
            }

            ++var6;
         }
      }

      return var3;
   }

   private void setStatus(String var1, boolean var2) {
      this.mStatus = var1;
      if(var2) {
         int[] var3 = Presence.2.$SwitchMap$com$google$android$gtalkservice$Presence$Show;
         int var4 = this.mShow.ordinal();
         switch(var3[var4]) {
         case 1:
            List var5 = this.mDndStatusList;
            this.addToList(var5, var1);
            return;
         case 2:
            List var7 = this.mDefaultStatusList;
            this.addToList(var7, var1);
            return;
         default:
         }
      }
   }

   public boolean allowInvisibility() {
      return this.mAllowInvisibility;
   }

   public void clearStatusLists() {
      this.mDefaultStatusList.clear();
      this.mDndStatusList.clear();
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Presence var1) {
      boolean var2 = false;
      if(var1 != null) {
         boolean var3 = this.mAvailable;
         boolean var4 = var1.mAvailable;
         if(var3 == var4) {
            Presence.Show var5 = this.mShow;
            Presence.Show var6 = var1.mShow;
            if(var5 == var6) {
               if(this.mStatus != null) {
                  String var7 = this.mStatus;
                  String var8 = var1.mStatus;
                  if(!var7.equals(var8)) {
                     return var2;
                  }
               } else if(var1.mStatus != null) {
                  return var2;
               }

               boolean var9 = this.mInvisible;
               boolean var10 = var1.mInvisible;
               if(var9 == var10) {
                  int var11 = this.mStatusMax;
                  int var12 = var1.mStatusMax;
                  if(var11 == var12) {
                     int var13 = this.mStatusListMax;
                     int var14 = var1.mStatusListMax;
                     if(var13 == var14) {
                        int var15 = this.mStatusListContentsMax;
                        int var16 = var1.mStatusListContentsMax;
                        if(var15 == var16) {
                           boolean var17 = this.mAllowInvisibility;
                           boolean var18 = var1.mAllowInvisibility;
                           if(var17 == var18) {
                              List var19 = this.mDefaultStatusList;
                              List var20 = var1.mDefaultStatusList;
                              if(this.listEqual(var19, var20)) {
                                 List var21 = this.mDndStatusList;
                                 List var22 = var1.mDndStatusList;
                                 if(this.listEqual(var21, var22)) {
                                    int var23 = this.mCapabilities;
                                    int var24 = var1.mCapabilities;
                                    if(var23 == var24) {
                                       var2 = true;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   public int getCapabilities() {
      return this.mCapabilities;
   }

   public List<String> getDefaultStatusList() {
      List var1 = this.mDefaultStatusList;
      return new ArrayList(var1);
   }

   public List<String> getDndStatusList() {
      List var1 = this.mDndStatusList;
      return new ArrayList(var1);
   }

   public Presence.Show getShow() {
      return this.mShow;
   }

   public String getStatus() {
      return this.mStatus;
   }

   public int getStatusListContentsMax() {
      return this.mStatusListContentsMax;
   }

   public int getStatusListMax() {
      return this.mStatusListMax;
   }

   public int getStatusMax() {
      return this.mStatusMax;
   }

   public boolean isAvailable() {
      return this.mAvailable;
   }

   public boolean isInvisible() {
      return this.mInvisible;
   }

   public String printDetails() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("{ available=");
      boolean var3 = this.mAvailable;
      var1.append(var3);
      StringBuilder var5 = var1.append(", show=");
      Presence.Show var6 = this.mShow;
      var1.append(var6);
      StringBuilder var8 = var1.append(", ");
      String var9;
      if(this.mStatus == null) {
         var9 = "";
      } else {
         var9 = this.mStatus;
      }

      var1.append(var9);
      StringBuilder var11 = (new StringBuilder()).append(", invisible=");
      boolean var12 = this.mInvisible;
      String var13 = var11.append(var12).toString();
      var1.append(var13);
      StringBuilder var15 = var1.append(", allowInvisible=");
      boolean var16 = this.mAllowInvisibility;
      var1.append(var16);
      StringBuilder var18 = var1.append(", caps=0x");
      String var19 = Integer.toHexString(this.mCapabilities);
      var1.append(var19);
      StringBuilder var21 = var1.append(", default={");
      int var22 = 0;
      Iterator var23;
      int var25;
      if(this.mDefaultStatusList != null) {
         for(var23 = this.mDefaultStatusList.iterator(); var23.hasNext(); var22 = var25) {
            String var24 = (String)var23.next();
            var25 = var22 + 1;
            if(var22 > 0) {
               StringBuilder var26 = var1.append(", ");
            }

            var1.append(var24);
         }
      }

      StringBuilder var28 = var1.append("}, dnd={");
      if(this.mDndStatusList != null) {
         var22 = 0;

         int var30;
         for(var23 = this.mDndStatusList.iterator(); var23.hasNext(); var22 = var30) {
            String var29 = (String)var23.next();
            var30 = var22 + 1;
            if(var22 > 0) {
               StringBuilder var31 = var1.append(", ");
            }

            var1.append(var29);
         }
      }

      StringBuilder var33 = var1.append("}");
      StringBuilder var34 = var1.append("}");
      return var1.toString();
   }

   public void setAllowInvisibility(boolean var1) {
      this.mAllowInvisibility = var1;
   }

   public void setAvailable(boolean var1) {
      this.mAvailable = var1;
   }

   public void setCapabilities(int var1) {
      this.mCapabilities = var1;
   }

   public boolean setInvisible(boolean var1) {
      this.mInvisible = var1;
      boolean var2;
      if(var1 && !this.allowInvisibility()) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public void setShow(Presence.Show var1) {
      this.mShow = var1;
   }

   public void setStatus(Presence.Show var1, String var2) {
      this.setShow(var1);
      this.setStatus(var2, (boolean)1);
   }

   public void setStatus(String var1) {
      this.setStatus(var1, (boolean)0);
   }

   public void setStatusListContentsMax(int var1) {
      this.mStatusListContentsMax = var1;
   }

   public void setStatusListMax(int var1) {
      this.mStatusListMax = var1;
   }

   public void setStatusMax(int var1) {
      this.mStatusMax = var1;
   }

   public String toString() {
      String var1;
      if(!this.isAvailable()) {
         var1 = "UNAVAILABLE";
      } else if(this.isInvisible()) {
         var1 = "INVISIBLE";
      } else {
         StringBuilder var2 = new StringBuilder(40);
         Presence.Show var3 = this.mShow;
         Presence.Show var4 = Presence.Show.NONE;
         if(var3 == var4) {
            StringBuilder var5 = var2.append("AVAILABLE(x)");
         } else {
            String var10 = this.mShow.toString();
            var2.append(var10);
         }

         if((this.mCapabilities & 8) != 0) {
            StringBuilder var6 = var2.append(" pmuc-v1");
         }

         if((this.mCapabilities & 1) != 0) {
            StringBuilder var7 = var2.append(" voice-v1");
         }

         if((this.mCapabilities & 2) != 0) {
            StringBuilder var8 = var2.append(" video-v1");
         }

         if((this.mCapabilities & 4) != 0) {
            StringBuilder var9 = var2.append(" camera-v1");
         }

         var1 = var2.toString();
      }

      return var1;
   }

   public void writeToParcel(Parcel var1, int var2) {
      byte var3 = 1;
      int var4 = this.getStatusMax();
      var1.writeInt(var4);
      int var5 = this.getStatusListMax();
      var1.writeInt(var5);
      int var6 = this.getStatusListContentsMax();
      var1.writeInt(var6);
      byte var7;
      if(this.allowInvisibility()) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      var1.writeInt(var7);
      byte var8;
      if(this.mAvailable) {
         var8 = 1;
      } else {
         var8 = 0;
      }

      var1.writeInt(var8);
      String var9 = this.mShow.toString();
      var1.writeString(var9);
      String var10 = this.mStatus;
      var1.writeString(var10);
      if(!this.mInvisible) {
         var3 = 0;
      }

      var1.writeInt(var3);
      List var11 = this.mDefaultStatusList;
      var1.writeStringList(var11);
      List var12 = this.mDndStatusList;
      var1.writeStringList(var12);
      int var13 = this.getCapabilities();
      var1.writeInt(var13);
   }

   public static enum Show {

      // $FF: synthetic field
      private static final Presence.Show[] $VALUES;
      AVAILABLE("AVAILABLE", 4),
      AWAY("AWAY", 1),
      DND("DND", 3),
      EXTENDED_AWAY("EXTENDED_AWAY", 2),
      NONE("NONE", 0);


      static {
         Presence.Show[] var0 = new Presence.Show[5];
         Presence.Show var1 = NONE;
         var0[0] = var1;
         Presence.Show var2 = AWAY;
         var0[1] = var2;
         Presence.Show var3 = EXTENDED_AWAY;
         var0[2] = var3;
         Presence.Show var4 = DND;
         var0[3] = var4;
         Presence.Show var5 = AVAILABLE;
         var0[4] = var5;
         $VALUES = var0;
      }

      private Show(String var1, int var2) {}
   }

   static class 1 implements Creator<Presence> {

      1() {}

      public Presence createFromParcel(Parcel var1) {
         return new Presence(var1);
      }

      public Presence[] newArray(int var1) {
         return new Presence[var1];
      }
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$gtalkservice$Presence$Show = new int[Presence.Show.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$gtalkservice$Presence$Show;
            int var1 = Presence.Show.DND.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$gtalkservice$Presence$Show;
            int var3 = Presence.Show.AVAILABLE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
