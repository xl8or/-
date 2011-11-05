package com.google.android.finsky.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.remoting.protos.BookInfo;
import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.DeviceDoc;
import com.google.android.finsky.remoting.protos.Doc;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Document implements Parcelable {

   public static Creator<Document> CREATOR = new Document.1();
   private final String mCookie;
   private final DeviceDoc.DeviceDocument mDeviceDoc;
   private final Doc.Document mFinskyDoc;
   private Map<Integer, List<Doc.Image>> mImageTypeMap;


   public Document(DeviceDoc.DeviceDocument var1, String var2) {
      this.mDeviceDoc = var1;
      Doc.Document var3 = var1.getFinskyDoc();
      this.mFinskyDoc = var3;
      this.mCookie = var2;
   }

   public static int getBackend(DeviceDoc.DeviceDocument var0) {
      return var0.getFinskyDoc().getDocid().getBackend();
   }

   private boolean isAppInstalled(PackageInfoCache var1) {
      DeviceDoc.AppDetails var2 = this.getAppDetails();
      byte var3;
      if(var2 == null) {
         var3 = 0;
      } else {
         String var4 = var2.getPackageName();
         var3 = var1.isPackageInstalled(var4);
      }

      return (boolean)var3;
   }

   private boolean isSystemApp(PackageInfoCache var1) {
      DeviceDoc.AppDetails var2 = this.getAppDetails();
      byte var3;
      if(var2 == null) {
         var3 = 0;
      } else {
         String var4 = var2.getPackageName();
         var3 = var1.isSystemPackage(var4);
      }

      return (boolean)var3;
   }

   private boolean offerTypeCheck(int var1) {
      boolean var2;
      if(this.mFinskyDoc.hasAvailability() && this.mFinskyDoc.getAvailability().hasOfferType() && this.mFinskyDoc.getAvailability().getOfferType() == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean canLaunch(PackageInfoCache var1) {
      DeviceDoc.AppDetails var2 = this.getAppDetails();
      byte var3;
      if(var2 == null) {
         var3 = 0;
      } else {
         String var4 = var2.getPackageName();
         var3 = var1.canLaunch(var4);
      }

      return (boolean)var3;
   }

   public boolean canManage(PackageInfoCache var1) {
      boolean var2 = false;
      if(this.getBackend() == 4) {
         var2 = this.ownedByUser(var1);
      } else {
         DeviceDoc.AppDetails var3 = this.getAppDetails();
         if(var3 != null && this.ownedByUser(var1)) {
            String var4 = var3.getPackageName();
            int var5 = var1.getPackageVersion(var4);
            int var6 = var3.getVersionCode();
            if(var5 <= var6) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   public boolean canRate(PackageInfoCache var1) {
      boolean var2;
      if(this.getBackend() == 3) {
         var2 = this.ownedByUser(var1);
      } else {
         var2 = true;
      }

      return var2;
   }

   public int describeContents() {
      return 0;
   }

   public DeviceDoc.AlbumDetails getAlbumDetails() {
      DeviceDoc.AlbumDetails var1;
      if(this.mDeviceDoc.hasDetails()) {
         var1 = this.mDeviceDoc.getDetails().getAlbumDetails();
      } else {
         var1 = null;
      }

      return var1;
   }

   public DeviceDoc.AppDetails getAppDetails() {
      DeviceDoc.AppDetails var1;
      if(this.mDeviceDoc.hasDetails()) {
         var1 = this.mDeviceDoc.getDetails().getAppDetails();
      } else {
         var1 = null;
      }

      return var1;
   }

   public List<String> getAppPermissionsList() {
      List var1;
      if(this.mFinskyDoc.getDocid().getBackend() == 3 && this.mDeviceDoc.hasDetails() && this.mDeviceDoc.getDetails().hasAppDetails()) {
         var1 = this.mDeviceDoc.getDetails().getAppDetails().getPermissionList();
      } else {
         var1 = null;
      }

      return var1;
   }

   public DeviceDoc.ArtistDetails getArtistDetails() {
      DeviceDoc.ArtistDetails var1;
      if(this.mDeviceDoc.hasDetails()) {
         var1 = this.mDeviceDoc.getDetails().getArtistDetails();
      } else {
         var1 = null;
      }

      return var1;
   }

   public int getAvailabilityRestriction() {
      int var1;
      if(this.mFinskyDoc.hasAvailability()) {
         var1 = this.mFinskyDoc.getAvailability().getRestriction();
      } else {
         var1 = -1;
      }

      return var1;
   }

   public List<Common.Offer> getAvailableOffers() {
      return this.mFinskyDoc.getOfferList();
   }

   public int getBackend() {
      return this.mFinskyDoc.getDocid().getBackend();
   }

   public String getBackendDocId() {
      return this.mFinskyDoc.getDocid().getBackendDocid();
   }

   public BookInfo.BookDetails getBookDetails() {
      BookInfo.BookDetails var1;
      if(this.mDeviceDoc.hasDetails()) {
         var1 = this.mDeviceDoc.getDetails().getBookDetails();
      } else {
         var1 = null;
      }

      return var1;
   }

   public String getCookie() {
      return this.mCookie;
   }

   public String getCreator() {
      return this.mDeviceDoc.getCreator();
   }

   public List<DeviceDoc.VideoCredit> getCreditsList() {
      DeviceDoc.VideoDetails var1 = this.getVideoDetails();
      List var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getCreditList();
      }

      return var2;
   }

   public String getDate() {
      return null;
   }

   public Common.Offer getDefaultOffer() {
      Common.Offer var1 = null;
      Iterator var2;
      Common.Offer var3;
      if(this.getBackend() == 4) {
         var2 = this.getAvailableOffers().iterator();

         while(var2.hasNext()) {
            var3 = (Common.Offer)var2.next();
            int var4 = var3.getOfferType();
            if(var4 == 4 && var3.hasFormattedAmount()) {
               var1 = var3;
               break;
            }

            if(var4 == 3 && var1 == null && var3.hasFormattedAmount()) {
               var1 = var3;
            }
         }
      } else if(this.getBackend() == 3 || this.getBackend() == 1) {
         var2 = this.getAvailableOffers().iterator();

         while(var2.hasNext()) {
            var3 = (Common.Offer)var2.next();
            if(var3.hasOfferType()) {
               if(var3.getOfferType() == 1 && var3.hasFormattedAmount()) {
                  var1 = var3;
                  break;
               }

               if(var3.hasFormattedAmount()) {
                  var1 = var3;
               }
            }
         }
      }

      if(var1 == null) {
         var1 = this.mFinskyDoc.getPriceDeprecated();
      }

      return var1;
   }

   public CharSequence getDescription() {
      return Html.fromHtml(this.mDeviceDoc.getDescriptionHtml());
   }

   public String getDetailsUrl() {
      return this.mDeviceDoc.getDetailsUrl();
   }

   public String getDocId() {
      return this.mDeviceDoc.getDocid();
   }

   public int getDocumentType() {
      return this.mFinskyDoc.getDocid().getType();
   }

   public String getFormattedPrice() {
      String var1;
      if(this.mFinskyDoc.getPriceDeprecated().hasFormattedAmount()) {
         var1 = this.mFinskyDoc.getPriceDeprecated().getFormattedAmount();
      } else {
         var1 = null;
      }

      return var1;
   }

   public Map<Integer, List<Doc.Image>> getImageTypeMap() {
      if(this.mImageTypeMap == null) {
         ArrayList var1 = Lists.newArrayList();
         ArrayList var2 = Lists.newArrayList();
         ArrayList var3 = Lists.newArrayList();
         ArrayList var4 = Lists.newArrayList();
         ArrayList var5 = Lists.newArrayList();
         HashMap var6 = Maps.newHashMap();
         this.mImageTypeMap = var6;
         Map var7 = this.mImageTypeMap;
         Integer var8 = Integer.valueOf(0);
         var7.put(var8, var1);
         Map var10 = this.mImageTypeMap;
         Integer var11 = Integer.valueOf(1);
         var10.put(var11, var2);
         Map var13 = this.mImageTypeMap;
         Integer var14 = Integer.valueOf(2);
         var13.put(var14, var3);
         Map var16 = this.mImageTypeMap;
         Integer var17 = Integer.valueOf(3);
         var16.put(var17, var4);
         Map var19 = this.mImageTypeMap;
         Integer var20 = Integer.valueOf(4);
         var19.put(var20, var5);

         Doc.Image var23;
         Integer var25;
         Map var24;
         boolean var26;
         for(Iterator var22 = this.mFinskyDoc.getImageList().iterator(); var22.hasNext(); var26 = ((List)var24.get(var25)).add(var23)) {
            var23 = (Doc.Image)var22.next();
            var24 = this.mImageTypeMap;
            var25 = Integer.valueOf(var23.getImageType());
         }
      }

      return this.mImageTypeMap;
   }

   public List<Doc.Image> getImages(int var1) {
      Map var2 = this.getImageTypeMap();
      Integer var3 = Integer.valueOf(var1);
      return (List)var2.get(var3);
   }

   public String getMoreByBrowseUrl() {
      String var1 = this.mDeviceDoc.getMoreByBrowseUrl();
      if(TextUtils.isEmpty(var1)) {
         var1 = this.getRelatedBrowseUrl();
      }

      return var1;
   }

   public String getMoreByHeader() {
      String var1 = this.mDeviceDoc.getMoreByHeader();
      if(TextUtils.isEmpty(var1)) {
         var1 = this.getRelatedHeader();
      }

      return var1;
   }

   public String getMoreByListUrl() {
      String var1 = this.mDeviceDoc.getMoreByListUrl();
      if(TextUtils.isEmpty(var1)) {
         var1 = this.getRelatedUrl();
      }

      return var1;
   }

   public List<DeviceDoc.VideoRentalTerm> getMovieRentalTerms() {
      DeviceDoc.VideoDetails var1 = this.getVideoDetails();
      List var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getRentalTermList();
      }

      return var2;
   }

   public List<DeviceDoc.Trailer> getMovieTrailers() {
      DeviceDoc.VideoDetails var1 = this.getVideoDetails();
      List var2;
      if(var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getTrailerList();
      }

      return var2;
   }

   public int getNormalizedContentRating() {
      DeviceDoc.AppDetails var1 = this.getAppDetails();
      int var2;
      if(var1 == null) {
         var2 = -1;
      } else {
         var2 = var1.getContentRating() + -1;
      }

      return var2;
   }

   public Common.Offer getOffer(int var1) {
      Iterator var2 = this.getAvailableOffers().iterator();

      Common.Offer var3;
      do {
         if(!var2.hasNext()) {
            var3 = null;
            break;
         }

         var3 = (Common.Offer)var2.next();
      } while(var3.getOfferType() != var1);

      return var3;
   }

   public DeviceDoc.PlusOneData getPlusOneData() {
      return this.mDeviceDoc.getPlusOneData();
   }

   public long getRatingCount() {
      return this.mFinskyDoc.getAggregateRating().getRatingsCount();
   }

   public String getRelatedBrowseUrl() {
      return this.mDeviceDoc.getRelatedBrowseUrl();
   }

   public String getRelatedHeader() {
      return this.mDeviceDoc.getRelatedHeader();
   }

   public String getRelatedUrl() {
      return this.mDeviceDoc.getRelatedListUrl();
   }

   public String getReviewsUrl() {
      return this.mDeviceDoc.getReviewsUrl();
   }

   public String getShareUrl() {
      return this.mDeviceDoc.getShareUrl();
   }

   public DeviceDoc.SongDetails getSongDetails() {
      DeviceDoc.SongDetails var1;
      if(this.mDeviceDoc.hasDetails()) {
         var1 = this.mDeviceDoc.getDetails().getSongDetails();
      } else {
         var1 = null;
      }

      return var1;
   }

   public float getStarRating() {
      return this.mFinskyDoc.getAggregateRating().getStarRating();
   }

   public String getTitle() {
      String var1 = this.mDeviceDoc.getTitle();
      if(TextUtils.isEmpty(var1)) {
         var1 = this.mFinskyDoc.getTitle();
      }

      return var1;
   }

   public DeviceDoc.VideoDetails getVideoDetails() {
      DeviceDoc.VideoDetails var1;
      if(this.mDeviceDoc.hasDetails()) {
         var1 = this.mDeviceDoc.getDetails().getVideoDetails();
      } else {
         var1 = null;
      }

      return var1;
   }

   public CharSequence getWhatsNew() {
      String var1;
      if(this.getAppDetails() != null) {
         var1 = this.getAppDetails().getRecentChangesHtml();
      } else {
         var1 = null;
      }

      Object var2;
      if(var1 != null) {
         var2 = Html.fromHtml(var1);
      } else {
         var2 = "";
      }

      return (CharSequence)var2;
   }

   public String getYouTubeWatchUrl() {
      String var1;
      if(this.getBackend() == 4) {
         var1 = this.mFinskyDoc.getUrl();
      } else {
         var1 = null;
      }

      return var1;
   }

   public boolean hasCreatorRelatedContent() {
      boolean var1;
      if(!TextUtils.isEmpty(this.mDeviceDoc.getMoreByListUrl())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasDate() {
      return false;
   }

   public boolean hasDetails() {
      return this.mDeviceDoc.hasDetails();
   }

   public boolean hasPermissions() {
      boolean var1;
      if(this.mFinskyDoc.getDocid().getBackend() == 3 && this.mDeviceDoc.hasDetails() && this.mDeviceDoc.getDetails().hasAppDetails() && this.mDeviceDoc.getDetails().getAppDetails().getPermissionCount() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasPlusOneData() {
      return this.mDeviceDoc.hasPlusOneData();
   }

   public boolean hasRating() {
      return this.mFinskyDoc.hasAggregateRating();
   }

   public boolean hasSample() {
      return this.mFinskyDoc.hasSampleDocid();
   }

   public boolean hasScreenshots() {
      byte var1 = 1;
      List var2 = this.getImages(var1);
      if(var2 != null && !var2.isEmpty()) {
         int var3 = this.getBackend();
         if(1 != var3) {
            return (boolean)var1;
         }
      }

      var1 = 0;
      return (boolean)var1;
   }

   public boolean hasVideos() {
      List var1 = this.getImages(3);
      boolean var2;
      if(var1 != null && !var1.isEmpty() && !TextUtils.isEmpty(((Doc.Image)var1.get(0)).getImageUrl())) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isAvailable(DfeToc var1) {
      int var2 = this.getBackend();
      boolean var5;
      if(var1.getCorpus(var2) == null) {
         Object[] var3 = new Object[1];
         String var4 = this.getDocId();
         var3[0] = var4;
         FinskyLog.d("Corpus for %s is not available.", var3);
         var5 = false;
      } else {
         int var6;
         if(this.mFinskyDoc.hasAvailability()) {
            var6 = this.mFinskyDoc.getAvailability().getRestriction();
         } else {
            var6 = -1;
         }

         if(var6 == 1) {
            var5 = true;
         } else {
            var5 = false;
         }

         if(!var5) {
            Object[] var7 = new Object[2];
            String var8 = this.getDocId();
            var7[0] = var8;
            Integer var9 = Integer.valueOf(var6);
            var7[1] = var9;
            FinskyLog.d("%s not available [restriction=%d].", var7);
         }
      }

      return var5;
   }

   public boolean isLocallyAvailable(PackageInfoCache var1) {
      boolean var2;
      if(this.getBackend() != 3) {
         var2 = this.ownedByUser(var1);
      } else {
         var2 = this.isAppInstalled(var1);
      }

      return var2;
   }

   public boolean isUpdateAvailable(PackageInfoCache var1) {
      boolean var2 = false;
      DeviceDoc.AppDetails var3 = this.getAppDetails();
      if(var3 != null) {
         String var4 = var3.getPackageName();
         int var5 = var1.getPackageVersion(var4);
         if(var3.getVersionCode() > var5) {
            var2 = true;
         }
      }

      return var2;
   }

   public boolean needsCheckoutFlow() {
      return this.mFinskyDoc.getPriceDeprecated().getCheckoutFlowRequired();
   }

   public boolean ownedByUser(PackageInfoCache var1) {
      boolean var2 = true;
      if(!this.isSystemApp(var1) && !this.offerTypeCheck(1) && (this.getBackend() != 4 || !this.offerTypeCheck(3) && !this.offerTypeCheck(4))) {
         var2 = false;
      }

      return var2;
   }

   public boolean sampleOwnedByUser() {
      return this.offerTypeCheck(2);
   }

   public void setPrice(TextView var1, TextView var2) {
      var1.setVisibility(0);
      if(this.mFinskyDoc.getPriceDeprecated().hasFormattedAmount()) {
         String var3 = this.mFinskyDoc.getPriceDeprecated().getFormattedAmount();
         String var4 = null;
         if(var3.contains("(")) {
            String[] var5 = var3.split("\\(|\\)");
            var3 = var5[0];
            var4 = var5[1];
         }

         var1.setText(var3);
         if(var2 != null) {
            if(var4 != null) {
               var2.setVisibility(0);
               String var6 = var4.toUpperCase();
               var2.setText(var6);
            } else {
               var2.setVisibility(4);
            }
         }
      } else {
         var1.setVisibility(4);
      }
   }

   public boolean skipPurchaseDialog(int var1) {
      byte var2 = 1;
      if(this.getBackend() != var2 || var1 != 2 && this.needsCheckoutFlow()) {
         var2 = 0;
      }

      return (boolean)var2;
   }

   public void writeToParcel(Parcel var1, int var2) {
      ParcelableProto var3 = ParcelableProto.forProto(this.mDeviceDoc);
      var1.writeParcelable(var3, 0);
      String var4 = this.mCookie;
      var1.writeString(var4);
   }

   static class 1 implements Creator<Document> {

      1() {}

      public Document createFromParcel(Parcel var1) {
         ClassLoader var2 = ParcelableProto.class.getClassLoader();
         DeviceDoc.DeviceDocument var3 = (DeviceDoc.DeviceDocument)ParcelableProto.getProtoFromParcel(var1, var2);
         String var4 = var1.readString();
         return new Document(var3, var4);
      }

      public Document[] newArray(int var1) {
         return new Document[var1];
      }
   }
}
