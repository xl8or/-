package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.BookInfo;
import com.google.android.finsky.remoting.protos.Common;
import com.google.android.finsky.remoting.protos.Doc;
import com.google.android.finsky.remoting.protos.MusicInfo;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class DeviceDoc {

   private DeviceDoc() {}

   public static final class Trailer extends MessageMicro {

      public static final int DURATION_FIELD_NUMBER = 5;
      public static final int THUMBNAIL_URL_FIELD_NUMBER = 3;
      public static final int TITLE_FIELD_NUMBER = 2;
      public static final int TRAILER_ID_FIELD_NUMBER = 1;
      public static final int WATCH_URL_FIELD_NUMBER = 4;
      private int cachedSize = -1;
      private String duration_ = "";
      private boolean hasDuration;
      private boolean hasThumbnailUrl;
      private boolean hasTitle;
      private boolean hasTrailerId;
      private boolean hasWatchUrl;
      private String thumbnailUrl_ = "";
      private String title_ = "";
      private String trailerId_ = "";
      private String watchUrl_ = "";


      public Trailer() {}

      public static DeviceDoc.Trailer parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.Trailer()).mergeFrom(var0);
      }

      public static DeviceDoc.Trailer parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.Trailer)((DeviceDoc.Trailer)(new DeviceDoc.Trailer()).mergeFrom(var0));
      }

      public final DeviceDoc.Trailer clear() {
         DeviceDoc.Trailer var1 = this.clearTrailerId();
         DeviceDoc.Trailer var2 = this.clearTitle();
         DeviceDoc.Trailer var3 = this.clearThumbnailUrl();
         DeviceDoc.Trailer var4 = this.clearWatchUrl();
         DeviceDoc.Trailer var5 = this.clearDuration();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.Trailer clearDuration() {
         this.hasDuration = (boolean)0;
         this.duration_ = "";
         return this;
      }

      public DeviceDoc.Trailer clearThumbnailUrl() {
         this.hasThumbnailUrl = (boolean)0;
         this.thumbnailUrl_ = "";
         return this;
      }

      public DeviceDoc.Trailer clearTitle() {
         this.hasTitle = (boolean)0;
         this.title_ = "";
         return this;
      }

      public DeviceDoc.Trailer clearTrailerId() {
         this.hasTrailerId = (boolean)0;
         this.trailerId_ = "";
         return this;
      }

      public DeviceDoc.Trailer clearWatchUrl() {
         this.hasWatchUrl = (boolean)0;
         this.watchUrl_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getDuration() {
         return this.duration_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasTrailerId()) {
            String var2 = this.getTrailerId();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasTitle()) {
            String var4 = this.getTitle();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasThumbnailUrl()) {
            String var6 = this.getThumbnailUrl();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasWatchUrl()) {
            String var8 = this.getWatchUrl();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         if(this.hasDuration()) {
            String var10 = this.getDuration();
            int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
            var1 += var11;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getThumbnailUrl() {
         return this.thumbnailUrl_;
      }

      public String getTitle() {
         return this.title_;
      }

      public String getTrailerId() {
         return this.trailerId_;
      }

      public String getWatchUrl() {
         return this.watchUrl_;
      }

      public boolean hasDuration() {
         return this.hasDuration;
      }

      public boolean hasThumbnailUrl() {
         return this.hasThumbnailUrl;
      }

      public boolean hasTitle() {
         return this.hasTitle;
      }

      public boolean hasTrailerId() {
         return this.hasTrailerId;
      }

      public boolean hasWatchUrl() {
         return this.hasWatchUrl;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasTrailerId) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public DeviceDoc.Trailer mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setTrailerId(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setTitle(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setThumbnailUrl(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setWatchUrl(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setDuration(var11);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.Trailer setDuration(String var1) {
         this.hasDuration = (boolean)1;
         this.duration_ = var1;
         return this;
      }

      public DeviceDoc.Trailer setThumbnailUrl(String var1) {
         this.hasThumbnailUrl = (boolean)1;
         this.thumbnailUrl_ = var1;
         return this;
      }

      public DeviceDoc.Trailer setTitle(String var1) {
         this.hasTitle = (boolean)1;
         this.title_ = var1;
         return this;
      }

      public DeviceDoc.Trailer setTrailerId(String var1) {
         this.hasTrailerId = (boolean)1;
         this.trailerId_ = var1;
         return this;
      }

      public DeviceDoc.Trailer setWatchUrl(String var1) {
         this.hasWatchUrl = (boolean)1;
         this.watchUrl_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasTrailerId()) {
            String var2 = this.getTrailerId();
            var1.writeString(1, var2);
         }

         if(this.hasTitle()) {
            String var3 = this.getTitle();
            var1.writeString(2, var3);
         }

         if(this.hasThumbnailUrl()) {
            String var4 = this.getThumbnailUrl();
            var1.writeString(3, var4);
         }

         if(this.hasWatchUrl()) {
            String var5 = this.getWatchUrl();
            var1.writeString(4, var5);
         }

         if(this.hasDuration()) {
            String var6 = this.getDuration();
            var1.writeString(5, var6);
         }
      }
   }

   public static final class VideoDetails extends MessageMicro {

      public static final int CONTENT_RATING_FIELD_NUMBER = 4;
      public static final int CREDIT_FIELD_NUMBER = 1;
      public static final int DISLIKES_FIELD_NUMBER = 6;
      public static final int DURATION_FIELD_NUMBER = 2;
      public static final int GENRE_FIELD_NUMBER = 7;
      public static final int LIKES_FIELD_NUMBER = 5;
      public static final int RELEASE_DATE_FIELD_NUMBER = 3;
      public static final int RENTAL_TERM_FIELD_NUMBER = 9;
      public static final int TRAILER_FIELD_NUMBER = 8;
      private int cachedSize;
      private String contentRating_;
      private List<DeviceDoc.VideoCredit> credit_;
      private long dislikes_;
      private String duration_;
      private List<String> genre_;
      private boolean hasContentRating;
      private boolean hasDislikes;
      private boolean hasDuration;
      private boolean hasLikes;
      private boolean hasReleaseDate;
      private long likes_;
      private String releaseDate_;
      private List<DeviceDoc.VideoRentalTerm> rentalTerm_;
      private List<DeviceDoc.Trailer> trailer_;


      public VideoDetails() {
         List var1 = Collections.emptyList();
         this.credit_ = var1;
         this.duration_ = "";
         this.releaseDate_ = "";
         this.contentRating_ = "";
         this.likes_ = 0L;
         this.dislikes_ = 0L;
         List var2 = Collections.emptyList();
         this.genre_ = var2;
         List var3 = Collections.emptyList();
         this.trailer_ = var3;
         List var4 = Collections.emptyList();
         this.rentalTerm_ = var4;
         this.cachedSize = -1;
      }

      public static DeviceDoc.VideoDetails parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.VideoDetails()).mergeFrom(var0);
      }

      public static DeviceDoc.VideoDetails parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.VideoDetails)((DeviceDoc.VideoDetails)(new DeviceDoc.VideoDetails()).mergeFrom(var0));
      }

      public DeviceDoc.VideoDetails addCredit(DeviceDoc.VideoCredit var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.credit_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.credit_ = var2;
            }

            this.credit_.add(var1);
            return this;
         }
      }

      public DeviceDoc.VideoDetails addGenre(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.genre_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.genre_ = var2;
            }

            this.genre_.add(var1);
            return this;
         }
      }

      public DeviceDoc.VideoDetails addRentalTerm(DeviceDoc.VideoRentalTerm var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.rentalTerm_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.rentalTerm_ = var2;
            }

            this.rentalTerm_.add(var1);
            return this;
         }
      }

      public DeviceDoc.VideoDetails addTrailer(DeviceDoc.Trailer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.trailer_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.trailer_ = var2;
            }

            this.trailer_.add(var1);
            return this;
         }
      }

      public final DeviceDoc.VideoDetails clear() {
         DeviceDoc.VideoDetails var1 = this.clearCredit();
         DeviceDoc.VideoDetails var2 = this.clearDuration();
         DeviceDoc.VideoDetails var3 = this.clearReleaseDate();
         DeviceDoc.VideoDetails var4 = this.clearContentRating();
         DeviceDoc.VideoDetails var5 = this.clearLikes();
         DeviceDoc.VideoDetails var6 = this.clearDislikes();
         DeviceDoc.VideoDetails var7 = this.clearGenre();
         DeviceDoc.VideoDetails var8 = this.clearTrailer();
         DeviceDoc.VideoDetails var9 = this.clearRentalTerm();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.VideoDetails clearContentRating() {
         this.hasContentRating = (boolean)0;
         this.contentRating_ = "";
         return this;
      }

      public DeviceDoc.VideoDetails clearCredit() {
         List var1 = Collections.emptyList();
         this.credit_ = var1;
         return this;
      }

      public DeviceDoc.VideoDetails clearDislikes() {
         this.hasDislikes = (boolean)0;
         this.dislikes_ = 0L;
         return this;
      }

      public DeviceDoc.VideoDetails clearDuration() {
         this.hasDuration = (boolean)0;
         this.duration_ = "";
         return this;
      }

      public DeviceDoc.VideoDetails clearGenre() {
         List var1 = Collections.emptyList();
         this.genre_ = var1;
         return this;
      }

      public DeviceDoc.VideoDetails clearLikes() {
         this.hasLikes = (boolean)0;
         this.likes_ = 0L;
         return this;
      }

      public DeviceDoc.VideoDetails clearReleaseDate() {
         this.hasReleaseDate = (boolean)0;
         this.releaseDate_ = "";
         return this;
      }

      public DeviceDoc.VideoDetails clearRentalTerm() {
         List var1 = Collections.emptyList();
         this.rentalTerm_ = var1;
         return this;
      }

      public DeviceDoc.VideoDetails clearTrailer() {
         List var1 = Collections.emptyList();
         this.trailer_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getContentRating() {
         return this.contentRating_;
      }

      public DeviceDoc.VideoCredit getCredit(int var1) {
         return (DeviceDoc.VideoCredit)this.credit_.get(var1);
      }

      public int getCreditCount() {
         return this.credit_.size();
      }

      public List<DeviceDoc.VideoCredit> getCreditList() {
         return this.credit_;
      }

      public long getDislikes() {
         return this.dislikes_;
      }

      public String getDuration() {
         return this.duration_;
      }

      public String getGenre(int var1) {
         return (String)this.genre_.get(var1);
      }

      public int getGenreCount() {
         return this.genre_.size();
      }

      public List<String> getGenreList() {
         return this.genre_;
      }

      public long getLikes() {
         return this.likes_;
      }

      public String getReleaseDate() {
         return this.releaseDate_;
      }

      public DeviceDoc.VideoRentalTerm getRentalTerm(int var1) {
         return (DeviceDoc.VideoRentalTerm)this.rentalTerm_.get(var1);
      }

      public int getRentalTermCount() {
         return this.rentalTerm_.size();
      }

      public List<DeviceDoc.VideoRentalTerm> getRentalTermList() {
         return this.rentalTerm_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getCreditList().iterator(); var2.hasNext(); var1 += var4) {
            DeviceDoc.VideoCredit var3 = (DeviceDoc.VideoCredit)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         if(this.hasDuration()) {
            String var5 = this.getDuration();
            int var6 = CodedOutputStreamMicro.computeStringSize(2, var5);
            var1 += var6;
         }

         if(this.hasReleaseDate()) {
            String var7 = this.getReleaseDate();
            int var8 = CodedOutputStreamMicro.computeStringSize(3, var7);
            var1 += var8;
         }

         if(this.hasContentRating()) {
            String var9 = this.getContentRating();
            int var10 = CodedOutputStreamMicro.computeStringSize(4, var9);
            var1 += var10;
         }

         if(this.hasLikes()) {
            long var11 = this.getLikes();
            int var13 = CodedOutputStreamMicro.computeInt64Size(5, var11);
            var1 += var13;
         }

         if(this.hasDislikes()) {
            long var14 = this.getDislikes();
            int var16 = CodedOutputStreamMicro.computeInt64Size(6, var14);
            var1 += var16;
         }

         int var17 = 0;

         int var19;
         for(Iterator var18 = this.getGenreList().iterator(); var18.hasNext(); var17 += var19) {
            var19 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var18.next());
         }

         int var20 = var1 + var17;
         int var21 = this.getGenreList().size() * 1;
         int var22 = var20 + var21;

         int var25;
         for(Iterator var23 = this.getTrailerList().iterator(); var23.hasNext(); var22 += var25) {
            DeviceDoc.Trailer var24 = (DeviceDoc.Trailer)var23.next();
            var25 = CodedOutputStreamMicro.computeMessageSize(8, var24);
         }

         int var28;
         for(Iterator var26 = this.getRentalTermList().iterator(); var26.hasNext(); var22 += var28) {
            DeviceDoc.VideoRentalTerm var27 = (DeviceDoc.VideoRentalTerm)var26.next();
            var28 = CodedOutputStreamMicro.computeMessageSize(9, var27);
         }

         this.cachedSize = var22;
         return var22;
      }

      public DeviceDoc.Trailer getTrailer(int var1) {
         return (DeviceDoc.Trailer)this.trailer_.get(var1);
      }

      public int getTrailerCount() {
         return this.trailer_.size();
      }

      public List<DeviceDoc.Trailer> getTrailerList() {
         return this.trailer_;
      }

      public boolean hasContentRating() {
         return this.hasContentRating;
      }

      public boolean hasDislikes() {
         return this.hasDislikes;
      }

      public boolean hasDuration() {
         return this.hasDuration;
      }

      public boolean hasLikes() {
         return this.hasLikes;
      }

      public boolean hasReleaseDate() {
         return this.hasReleaseDate;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         Iterator var2 = this.getCreditList().iterator();

         do {
            if(!var2.hasNext()) {
               var2 = this.getTrailerList().iterator();

               while(var2.hasNext()) {
                  if(!((DeviceDoc.Trailer)var2.next()).isInitialized()) {
                     return var1;
                  }
               }

               var1 = true;
               break;
            }
         } while(((DeviceDoc.VideoCredit)var2.next()).isInitialized());

         return var1;
      }

      public DeviceDoc.VideoDetails mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               DeviceDoc.VideoCredit var3 = new DeviceDoc.VideoCredit();
               var1.readMessage(var3);
               this.addCredit(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setDuration(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setReleaseDate(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setContentRating(var9);
               break;
            case 40:
               long var11 = var1.readInt64();
               this.setLikes(var11);
               break;
            case 48:
               long var14 = var1.readInt64();
               this.setDislikes(var14);
               break;
            case 58:
               String var17 = var1.readString();
               this.addGenre(var17);
               break;
            case 66:
               DeviceDoc.Trailer var19 = new DeviceDoc.Trailer();
               var1.readMessage(var19);
               this.addTrailer(var19);
               break;
            case 74:
               DeviceDoc.VideoRentalTerm var21 = new DeviceDoc.VideoRentalTerm();
               var1.readMessage(var21);
               this.addRentalTerm(var21);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.VideoDetails setContentRating(String var1) {
         this.hasContentRating = (boolean)1;
         this.contentRating_ = var1;
         return this;
      }

      public DeviceDoc.VideoDetails setCredit(int var1, DeviceDoc.VideoCredit var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.credit_.set(var1, var2);
            return this;
         }
      }

      public DeviceDoc.VideoDetails setDislikes(long var1) {
         this.hasDislikes = (boolean)1;
         this.dislikes_ = var1;
         return this;
      }

      public DeviceDoc.VideoDetails setDuration(String var1) {
         this.hasDuration = (boolean)1;
         this.duration_ = var1;
         return this;
      }

      public DeviceDoc.VideoDetails setGenre(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.genre_.set(var1, var2);
            return this;
         }
      }

      public DeviceDoc.VideoDetails setLikes(long var1) {
         this.hasLikes = (boolean)1;
         this.likes_ = var1;
         return this;
      }

      public DeviceDoc.VideoDetails setReleaseDate(String var1) {
         this.hasReleaseDate = (boolean)1;
         this.releaseDate_ = var1;
         return this;
      }

      public DeviceDoc.VideoDetails setRentalTerm(int var1, DeviceDoc.VideoRentalTerm var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.rentalTerm_.set(var1, var2);
            return this;
         }
      }

      public DeviceDoc.VideoDetails setTrailer(int var1, DeviceDoc.Trailer var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.trailer_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getCreditList().iterator();

         while(var2.hasNext()) {
            DeviceDoc.VideoCredit var3 = (DeviceDoc.VideoCredit)var2.next();
            var1.writeMessage(1, var3);
         }

         if(this.hasDuration()) {
            String var4 = this.getDuration();
            var1.writeString(2, var4);
         }

         if(this.hasReleaseDate()) {
            String var5 = this.getReleaseDate();
            var1.writeString(3, var5);
         }

         if(this.hasContentRating()) {
            String var6 = this.getContentRating();
            var1.writeString(4, var6);
         }

         if(this.hasLikes()) {
            long var7 = this.getLikes();
            var1.writeInt64(5, var7);
         }

         if(this.hasDislikes()) {
            long var9 = this.getDislikes();
            var1.writeInt64(6, var9);
         }

         Iterator var11 = this.getGenreList().iterator();

         while(var11.hasNext()) {
            String var12 = (String)var11.next();
            var1.writeString(7, var12);
         }

         Iterator var13 = this.getTrailerList().iterator();

         while(var13.hasNext()) {
            DeviceDoc.Trailer var14 = (DeviceDoc.Trailer)var13.next();
            var1.writeMessage(8, var14);
         }

         Iterator var15 = this.getRentalTermList().iterator();

         while(var15.hasNext()) {
            DeviceDoc.VideoRentalTerm var16 = (DeviceDoc.VideoRentalTerm)var15.next();
            var1.writeMessage(9, var16);
         }

      }
   }

   public static final class AppDetails extends MessageMicro {

      public static final int APPCATEGORY_FIELD_NUMBER = 7;
      public static final int CONTENT_RATING_FIELD_NUMBER = 8;
      public static final int DEVELOPER_EMAIL_FIELD_NUMBER = 11;
      public static final int DEVELOPER_NAME_FIELD_NUMBER = 1;
      public static final int DEVELOPER_WEBSITE_FIELD_NUMBER = 12;
      public static final int INSTALLATION_SIZE_FIELD_NUMBER = 9;
      public static final int MAJOR_VERSION_NUMBER_FIELD_NUMBER = 2;
      public static final int NUM_DOWNLOADS_FIELD_NUMBER = 13;
      public static final int PACKAGE_NAME_FIELD_NUMBER = 14;
      public static final int PERMISSION_FIELD_NUMBER = 10;
      public static final int RECENT_CHANGES_HTML_FIELD_NUMBER = 15;
      public static final int TITLE_FIELD_NUMBER = 5;
      public static final int UPLOAD_DATE_FIELD_NUMBER = 16;
      public static final int VERSION_CODE_FIELD_NUMBER = 3;
      public static final int VERSION_STRING_FIELD_NUMBER = 4;
      private List<String> appCategory_;
      private int cachedSize;
      private int contentRating_;
      private String developerEmail_;
      private String developerName_ = "";
      private String developerWebsite_;
      private boolean hasContentRating;
      private boolean hasDeveloperEmail;
      private boolean hasDeveloperName;
      private boolean hasDeveloperWebsite;
      private boolean hasInstallationSize;
      private boolean hasMajorVersionNumber;
      private boolean hasNumDownloads;
      private boolean hasPackageName;
      private boolean hasRecentChangesHtml;
      private boolean hasTitle;
      private boolean hasUploadDate;
      private boolean hasVersionCode;
      private boolean hasVersionString;
      private long installationSize_;
      private int majorVersionNumber_ = 0;
      private String numDownloads_;
      private String packageName_;
      private List<String> permission_;
      private String recentChangesHtml_;
      private String title_ = "";
      private String uploadDate_;
      private int versionCode_ = 0;
      private String versionString_ = "";


      public AppDetails() {
         List var1 = Collections.emptyList();
         this.appCategory_ = var1;
         this.contentRating_ = 0;
         this.installationSize_ = 0L;
         List var2 = Collections.emptyList();
         this.permission_ = var2;
         this.developerEmail_ = "";
         this.developerWebsite_ = "";
         this.numDownloads_ = "";
         this.packageName_ = "";
         this.recentChangesHtml_ = "";
         this.uploadDate_ = "";
         this.cachedSize = -1;
      }

      public static DeviceDoc.AppDetails parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.AppDetails()).mergeFrom(var0);
      }

      public static DeviceDoc.AppDetails parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.AppDetails)((DeviceDoc.AppDetails)(new DeviceDoc.AppDetails()).mergeFrom(var0));
      }

      public DeviceDoc.AppDetails addAppCategory(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.appCategory_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.appCategory_ = var2;
            }

            this.appCategory_.add(var1);
            return this;
         }
      }

      public DeviceDoc.AppDetails addPermission(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.permission_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.permission_ = var2;
            }

            this.permission_.add(var1);
            return this;
         }
      }

      public final DeviceDoc.AppDetails clear() {
         DeviceDoc.AppDetails var1 = this.clearDeveloperName();
         DeviceDoc.AppDetails var2 = this.clearMajorVersionNumber();
         DeviceDoc.AppDetails var3 = this.clearVersionCode();
         DeviceDoc.AppDetails var4 = this.clearVersionString();
         DeviceDoc.AppDetails var5 = this.clearTitle();
         DeviceDoc.AppDetails var6 = this.clearAppCategory();
         DeviceDoc.AppDetails var7 = this.clearContentRating();
         DeviceDoc.AppDetails var8 = this.clearInstallationSize();
         DeviceDoc.AppDetails var9 = this.clearPermission();
         DeviceDoc.AppDetails var10 = this.clearDeveloperEmail();
         DeviceDoc.AppDetails var11 = this.clearDeveloperWebsite();
         DeviceDoc.AppDetails var12 = this.clearNumDownloads();
         DeviceDoc.AppDetails var13 = this.clearPackageName();
         DeviceDoc.AppDetails var14 = this.clearRecentChangesHtml();
         DeviceDoc.AppDetails var15 = this.clearUploadDate();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.AppDetails clearAppCategory() {
         List var1 = Collections.emptyList();
         this.appCategory_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails clearContentRating() {
         this.hasContentRating = (boolean)0;
         this.contentRating_ = 0;
         return this;
      }

      public DeviceDoc.AppDetails clearDeveloperEmail() {
         this.hasDeveloperEmail = (boolean)0;
         this.developerEmail_ = "";
         return this;
      }

      public DeviceDoc.AppDetails clearDeveloperName() {
         this.hasDeveloperName = (boolean)0;
         this.developerName_ = "";
         return this;
      }

      public DeviceDoc.AppDetails clearDeveloperWebsite() {
         this.hasDeveloperWebsite = (boolean)0;
         this.developerWebsite_ = "";
         return this;
      }

      public DeviceDoc.AppDetails clearInstallationSize() {
         this.hasInstallationSize = (boolean)0;
         this.installationSize_ = 0L;
         return this;
      }

      public DeviceDoc.AppDetails clearMajorVersionNumber() {
         this.hasMajorVersionNumber = (boolean)0;
         this.majorVersionNumber_ = 0;
         return this;
      }

      public DeviceDoc.AppDetails clearNumDownloads() {
         this.hasNumDownloads = (boolean)0;
         this.numDownloads_ = "";
         return this;
      }

      public DeviceDoc.AppDetails clearPackageName() {
         this.hasPackageName = (boolean)0;
         this.packageName_ = "";
         return this;
      }

      public DeviceDoc.AppDetails clearPermission() {
         List var1 = Collections.emptyList();
         this.permission_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails clearRecentChangesHtml() {
         this.hasRecentChangesHtml = (boolean)0;
         this.recentChangesHtml_ = "";
         return this;
      }

      public DeviceDoc.AppDetails clearTitle() {
         this.hasTitle = (boolean)0;
         this.title_ = "";
         return this;
      }

      public DeviceDoc.AppDetails clearUploadDate() {
         this.hasUploadDate = (boolean)0;
         this.uploadDate_ = "";
         return this;
      }

      public DeviceDoc.AppDetails clearVersionCode() {
         this.hasVersionCode = (boolean)0;
         this.versionCode_ = 0;
         return this;
      }

      public DeviceDoc.AppDetails clearVersionString() {
         this.hasVersionString = (boolean)0;
         this.versionString_ = "";
         return this;
      }

      public String getAppCategory(int var1) {
         return (String)this.appCategory_.get(var1);
      }

      public int getAppCategoryCount() {
         return this.appCategory_.size();
      }

      public List<String> getAppCategoryList() {
         return this.appCategory_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getContentRating() {
         return this.contentRating_;
      }

      public String getDeveloperEmail() {
         return this.developerEmail_;
      }

      public String getDeveloperName() {
         return this.developerName_;
      }

      public String getDeveloperWebsite() {
         return this.developerWebsite_;
      }

      public long getInstallationSize() {
         return this.installationSize_;
      }

      public int getMajorVersionNumber() {
         return this.majorVersionNumber_;
      }

      public String getNumDownloads() {
         return this.numDownloads_;
      }

      public String getPackageName() {
         return this.packageName_;
      }

      public String getPermission(int var1) {
         return (String)this.permission_.get(var1);
      }

      public int getPermissionCount() {
         return this.permission_.size();
      }

      public List<String> getPermissionList() {
         return this.permission_;
      }

      public String getRecentChangesHtml() {
         return this.recentChangesHtml_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasDeveloperName()) {
            String var2 = this.getDeveloperName();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasMajorVersionNumber()) {
            int var4 = this.getMajorVersionNumber();
            int var5 = CodedOutputStreamMicro.computeInt32Size(2, var4);
            var1 += var5;
         }

         if(this.hasVersionCode()) {
            int var6 = this.getVersionCode();
            int var7 = CodedOutputStreamMicro.computeInt32Size(3, var6);
            var1 += var7;
         }

         if(this.hasVersionString()) {
            String var8 = this.getVersionString();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         if(this.hasTitle()) {
            String var10 = this.getTitle();
            int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
            var1 += var11;
         }

         int var12 = 0;

         int var14;
         for(Iterator var13 = this.getAppCategoryList().iterator(); var13.hasNext(); var12 += var14) {
            var14 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var13.next());
         }

         int var15 = var1 + var12;
         int var16 = this.getAppCategoryList().size() * 1;
         int var17 = var15 + var16;
         if(this.hasContentRating()) {
            int var18 = this.getContentRating();
            int var19 = CodedOutputStreamMicro.computeInt32Size(8, var18);
            var17 += var19;
         }

         if(this.hasInstallationSize()) {
            long var20 = this.getInstallationSize();
            int var22 = CodedOutputStreamMicro.computeInt64Size(9, var20);
            var17 += var22;
         }

         int var23 = 0;

         int var25;
         for(Iterator var24 = this.getPermissionList().iterator(); var24.hasNext(); var23 += var25) {
            var25 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var24.next());
         }

         int var26 = var17 + var23;
         int var27 = this.getPermissionList().size() * 1;
         int var28 = var26 + var27;
         if(this.hasDeveloperEmail()) {
            String var29 = this.getDeveloperEmail();
            int var30 = CodedOutputStreamMicro.computeStringSize(11, var29);
            var28 += var30;
         }

         if(this.hasDeveloperWebsite()) {
            String var31 = this.getDeveloperWebsite();
            int var32 = CodedOutputStreamMicro.computeStringSize(12, var31);
            var28 += var32;
         }

         if(this.hasNumDownloads()) {
            String var33 = this.getNumDownloads();
            int var34 = CodedOutputStreamMicro.computeStringSize(13, var33);
            var28 += var34;
         }

         if(this.hasPackageName()) {
            String var35 = this.getPackageName();
            int var36 = CodedOutputStreamMicro.computeStringSize(14, var35);
            var28 += var36;
         }

         if(this.hasRecentChangesHtml()) {
            String var37 = this.getRecentChangesHtml();
            int var38 = CodedOutputStreamMicro.computeStringSize(15, var37);
            var28 += var38;
         }

         if(this.hasUploadDate()) {
            String var39 = this.getUploadDate();
            int var40 = CodedOutputStreamMicro.computeStringSize(16, var39);
            var28 += var40;
         }

         this.cachedSize = var28;
         return var28;
      }

      public String getTitle() {
         return this.title_;
      }

      public String getUploadDate() {
         return this.uploadDate_;
      }

      public int getVersionCode() {
         return this.versionCode_;
      }

      public String getVersionString() {
         return this.versionString_;
      }

      public boolean hasContentRating() {
         return this.hasContentRating;
      }

      public boolean hasDeveloperEmail() {
         return this.hasDeveloperEmail;
      }

      public boolean hasDeveloperName() {
         return this.hasDeveloperName;
      }

      public boolean hasDeveloperWebsite() {
         return this.hasDeveloperWebsite;
      }

      public boolean hasInstallationSize() {
         return this.hasInstallationSize;
      }

      public boolean hasMajorVersionNumber() {
         return this.hasMajorVersionNumber;
      }

      public boolean hasNumDownloads() {
         return this.hasNumDownloads;
      }

      public boolean hasPackageName() {
         return this.hasPackageName;
      }

      public boolean hasRecentChangesHtml() {
         return this.hasRecentChangesHtml;
      }

      public boolean hasTitle() {
         return this.hasTitle;
      }

      public boolean hasUploadDate() {
         return this.hasUploadDate;
      }

      public boolean hasVersionCode() {
         return this.hasVersionCode;
      }

      public boolean hasVersionString() {
         return this.hasVersionString;
      }

      public final boolean isInitialized() {
         return true;
      }

      public DeviceDoc.AppDetails mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setDeveloperName(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setMajorVersionNumber(var5);
               break;
            case 24:
               int var7 = var1.readInt32();
               this.setVersionCode(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setVersionString(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setTitle(var11);
               break;
            case 58:
               String var13 = var1.readString();
               this.addAppCategory(var13);
               break;
            case 64:
               int var15 = var1.readInt32();
               this.setContentRating(var15);
               break;
            case 72:
               long var17 = var1.readInt64();
               this.setInstallationSize(var17);
               break;
            case 82:
               String var20 = var1.readString();
               this.addPermission(var20);
               break;
            case 90:
               String var22 = var1.readString();
               this.setDeveloperEmail(var22);
               break;
            case 98:
               String var24 = var1.readString();
               this.setDeveloperWebsite(var24);
               break;
            case 106:
               String var26 = var1.readString();
               this.setNumDownloads(var26);
               break;
            case 114:
               String var28 = var1.readString();
               this.setPackageName(var28);
               break;
            case 122:
               String var30 = var1.readString();
               this.setRecentChangesHtml(var30);
               break;
            case 130:
               String var32 = var1.readString();
               this.setUploadDate(var32);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.AppDetails setAppCategory(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.appCategory_.set(var1, var2);
            return this;
         }
      }

      public DeviceDoc.AppDetails setContentRating(int var1) {
         this.hasContentRating = (boolean)1;
         this.contentRating_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setDeveloperEmail(String var1) {
         this.hasDeveloperEmail = (boolean)1;
         this.developerEmail_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setDeveloperName(String var1) {
         this.hasDeveloperName = (boolean)1;
         this.developerName_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setDeveloperWebsite(String var1) {
         this.hasDeveloperWebsite = (boolean)1;
         this.developerWebsite_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setInstallationSize(long var1) {
         this.hasInstallationSize = (boolean)1;
         this.installationSize_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setMajorVersionNumber(int var1) {
         this.hasMajorVersionNumber = (boolean)1;
         this.majorVersionNumber_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setNumDownloads(String var1) {
         this.hasNumDownloads = (boolean)1;
         this.numDownloads_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setPackageName(String var1) {
         this.hasPackageName = (boolean)1;
         this.packageName_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setPermission(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.permission_.set(var1, var2);
            return this;
         }
      }

      public DeviceDoc.AppDetails setRecentChangesHtml(String var1) {
         this.hasRecentChangesHtml = (boolean)1;
         this.recentChangesHtml_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setTitle(String var1) {
         this.hasTitle = (boolean)1;
         this.title_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setUploadDate(String var1) {
         this.hasUploadDate = (boolean)1;
         this.uploadDate_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setVersionCode(int var1) {
         this.hasVersionCode = (boolean)1;
         this.versionCode_ = var1;
         return this;
      }

      public DeviceDoc.AppDetails setVersionString(String var1) {
         this.hasVersionString = (boolean)1;
         this.versionString_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasDeveloperName()) {
            String var2 = this.getDeveloperName();
            var1.writeString(1, var2);
         }

         if(this.hasMajorVersionNumber()) {
            int var3 = this.getMajorVersionNumber();
            var1.writeInt32(2, var3);
         }

         if(this.hasVersionCode()) {
            int var4 = this.getVersionCode();
            var1.writeInt32(3, var4);
         }

         if(this.hasVersionString()) {
            String var5 = this.getVersionString();
            var1.writeString(4, var5);
         }

         if(this.hasTitle()) {
            String var6 = this.getTitle();
            var1.writeString(5, var6);
         }

         Iterator var7 = this.getAppCategoryList().iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            var1.writeString(7, var8);
         }

         if(this.hasContentRating()) {
            int var9 = this.getContentRating();
            var1.writeInt32(8, var9);
         }

         if(this.hasInstallationSize()) {
            long var10 = this.getInstallationSize();
            var1.writeInt64(9, var10);
         }

         Iterator var12 = this.getPermissionList().iterator();

         while(var12.hasNext()) {
            String var13 = (String)var12.next();
            var1.writeString(10, var13);
         }

         if(this.hasDeveloperEmail()) {
            String var14 = this.getDeveloperEmail();
            var1.writeString(11, var14);
         }

         if(this.hasDeveloperWebsite()) {
            String var15 = this.getDeveloperWebsite();
            var1.writeString(12, var15);
         }

         if(this.hasNumDownloads()) {
            String var16 = this.getNumDownloads();
            var1.writeString(13, var16);
         }

         if(this.hasPackageName()) {
            String var17 = this.getPackageName();
            var1.writeString(14, var17);
         }

         if(this.hasRecentChangesHtml()) {
            String var18 = this.getRecentChangesHtml();
            var1.writeString(15, var18);
         }

         if(this.hasUploadDate()) {
            String var19 = this.getUploadDate();
            var1.writeString(16, var19);
         }
      }
   }

   public static final class SongDetails extends MessageMicro {

      public static final int PRICE_FIELD_NUMBER = 2;
      public static final int TRACK_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private boolean hasPrice;
      private boolean hasTrack;
      private Common.Offer price_ = null;
      private MusicInfo.Track track_ = null;


      public SongDetails() {}

      public static DeviceDoc.SongDetails parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.SongDetails()).mergeFrom(var0);
      }

      public static DeviceDoc.SongDetails parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.SongDetails)((DeviceDoc.SongDetails)(new DeviceDoc.SongDetails()).mergeFrom(var0));
      }

      public final DeviceDoc.SongDetails clear() {
         DeviceDoc.SongDetails var1 = this.clearTrack();
         DeviceDoc.SongDetails var2 = this.clearPrice();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.SongDetails clearPrice() {
         this.hasPrice = (boolean)0;
         this.price_ = null;
         return this;
      }

      public DeviceDoc.SongDetails clearTrack() {
         this.hasTrack = (boolean)0;
         this.track_ = null;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Common.Offer getPrice() {
         return this.price_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasTrack()) {
            MusicInfo.Track var2 = this.getTrack();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasPrice()) {
            Common.Offer var4 = this.getPrice();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public MusicInfo.Track getTrack() {
         return this.track_;
      }

      public boolean hasPrice() {
         return this.hasPrice;
      }

      public boolean hasTrack() {
         return this.hasTrack;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if((!this.hasTrack() || this.getTrack().isInitialized()) && (!this.hasPrice() || this.getPrice().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public DeviceDoc.SongDetails mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               MusicInfo.Track var3 = new MusicInfo.Track();
               var1.readMessage(var3);
               this.setTrack(var3);
               break;
            case 18:
               Common.Offer var5 = new Common.Offer();
               var1.readMessage(var5);
               this.setPrice(var5);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.SongDetails setPrice(Common.Offer var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPrice = (boolean)1;
            this.price_ = var1;
            return this;
         }
      }

      public DeviceDoc.SongDetails setTrack(MusicInfo.Track var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasTrack = (boolean)1;
            this.track_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasTrack()) {
            MusicInfo.Track var2 = this.getTrack();
            var1.writeMessage(1, var2);
         }

         if(this.hasPrice()) {
            Common.Offer var3 = this.getPrice();
            var1.writeMessage(2, var3);
         }
      }
   }

   public static final class AlbumDetails extends MessageMicro {

      public static final int ALBUM_FIELD_NUMBER = 1;
      public static final int SONG_FIELD_NUMBER = 2;
      private MusicInfo.Album album_ = null;
      private int cachedSize;
      private boolean hasAlbum;
      private List<DeviceDoc.SongDetails> song_;


      public AlbumDetails() {
         List var1 = Collections.emptyList();
         this.song_ = var1;
         this.cachedSize = -1;
      }

      public static DeviceDoc.AlbumDetails parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.AlbumDetails()).mergeFrom(var0);
      }

      public static DeviceDoc.AlbumDetails parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.AlbumDetails)((DeviceDoc.AlbumDetails)(new DeviceDoc.AlbumDetails()).mergeFrom(var0));
      }

      public DeviceDoc.AlbumDetails addSong(DeviceDoc.SongDetails var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.song_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.song_ = var2;
            }

            this.song_.add(var1);
            return this;
         }
      }

      public final DeviceDoc.AlbumDetails clear() {
         DeviceDoc.AlbumDetails var1 = this.clearAlbum();
         DeviceDoc.AlbumDetails var2 = this.clearSong();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.AlbumDetails clearAlbum() {
         this.hasAlbum = (boolean)0;
         this.album_ = null;
         return this;
      }

      public DeviceDoc.AlbumDetails clearSong() {
         List var1 = Collections.emptyList();
         this.song_ = var1;
         return this;
      }

      public MusicInfo.Album getAlbum() {
         return this.album_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasAlbum()) {
            MusicInfo.Album var2 = this.getAlbum();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         int var6;
         for(Iterator var4 = this.getSongList().iterator(); var4.hasNext(); var1 += var6) {
            DeviceDoc.SongDetails var5 = (DeviceDoc.SongDetails)var4.next();
            var6 = CodedOutputStreamMicro.computeMessageSize(2, var5);
         }

         this.cachedSize = var1;
         return var1;
      }

      public DeviceDoc.SongDetails getSong(int var1) {
         return (DeviceDoc.SongDetails)this.song_.get(var1);
      }

      public int getSongCount() {
         return this.song_.size();
      }

      public List<DeviceDoc.SongDetails> getSongList() {
         return this.song_;
      }

      public boolean hasAlbum() {
         return this.hasAlbum;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(!this.hasAlbum() || this.getAlbum().isInitialized()) {
            Iterator var2 = this.getSongList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((DeviceDoc.SongDetails)var2.next()).isInitialized());
         }

         return var1;
      }

      public DeviceDoc.AlbumDetails mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               MusicInfo.Album var3 = new MusicInfo.Album();
               var1.readMessage(var3);
               this.setAlbum(var3);
               break;
            case 18:
               DeviceDoc.SongDetails var5 = new DeviceDoc.SongDetails();
               var1.readMessage(var5);
               this.addSong(var5);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.AlbumDetails setAlbum(MusicInfo.Album var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasAlbum = (boolean)1;
            this.album_ = var1;
            return this;
         }
      }

      public DeviceDoc.AlbumDetails setSong(int var1, DeviceDoc.SongDetails var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.song_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasAlbum()) {
            MusicInfo.Album var2 = this.getAlbum();
            var1.writeMessage(1, var2);
         }

         Iterator var3 = this.getSongList().iterator();

         while(var3.hasNext()) {
            DeviceDoc.SongDetails var4 = (DeviceDoc.SongDetails)var3.next();
            var1.writeMessage(2, var4);
         }

      }
   }

   public static final class PlusPerson extends MessageMicro {

      public static final int DISPLAY_NAME_FIELD_NUMBER = 2;
      public static final int PROFILE_IMAGE_URL_FIELD_NUMBER = 4;
      private int cachedSize = -1;
      private String displayName_ = "";
      private boolean hasDisplayName;
      private boolean hasProfileImageUrl;
      private String profileImageUrl_ = "";


      public PlusPerson() {}

      public static DeviceDoc.PlusPerson parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.PlusPerson()).mergeFrom(var0);
      }

      public static DeviceDoc.PlusPerson parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.PlusPerson)((DeviceDoc.PlusPerson)(new DeviceDoc.PlusPerson()).mergeFrom(var0));
      }

      public final DeviceDoc.PlusPerson clear() {
         DeviceDoc.PlusPerson var1 = this.clearDisplayName();
         DeviceDoc.PlusPerson var2 = this.clearProfileImageUrl();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.PlusPerson clearDisplayName() {
         this.hasDisplayName = (boolean)0;
         this.displayName_ = "";
         return this;
      }

      public DeviceDoc.PlusPerson clearProfileImageUrl() {
         this.hasProfileImageUrl = (boolean)0;
         this.profileImageUrl_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getDisplayName() {
         return this.displayName_;
      }

      public String getProfileImageUrl() {
         return this.profileImageUrl_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasDisplayName()) {
            String var2 = this.getDisplayName();
            int var3 = CodedOutputStreamMicro.computeStringSize(2, var2);
            var1 = 0 + var3;
         }

         if(this.hasProfileImageUrl()) {
            String var4 = this.getProfileImageUrl();
            int var5 = CodedOutputStreamMicro.computeStringSize(4, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasDisplayName() {
         return this.hasDisplayName;
      }

      public boolean hasProfileImageUrl() {
         return this.hasProfileImageUrl;
      }

      public final boolean isInitialized() {
         return true;
      }

      public DeviceDoc.PlusPerson mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 18:
               String var3 = var1.readString();
               this.setDisplayName(var3);
               break;
            case 34:
               String var5 = var1.readString();
               this.setProfileImageUrl(var5);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.PlusPerson setDisplayName(String var1) {
         this.hasDisplayName = (boolean)1;
         this.displayName_ = var1;
         return this;
      }

      public DeviceDoc.PlusPerson setProfileImageUrl(String var1) {
         this.hasProfileImageUrl = (boolean)1;
         this.profileImageUrl_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasDisplayName()) {
            String var2 = this.getDisplayName();
            var1.writeString(2, var2);
         }

         if(this.hasProfileImageUrl()) {
            String var3 = this.getProfileImageUrl();
            var1.writeString(4, var3);
         }
      }
   }

   public static final class VideoCredit extends MessageMicro {

      public static final int ACTOR = 0;
      public static final int CREDIT_FIELD_NUMBER = 2;
      public static final int CREDIT_TYPE_FIELD_NUMBER = 1;
      public static final int DIRECTOR = 1;
      public static final int NAME_FIELD_NUMBER = 3;
      public static final int PRODUCER = 2;
      public static final int WRITER = 3;
      private int cachedSize;
      private int creditType_ = 0;
      private String credit_ = "";
      private boolean hasCredit;
      private boolean hasCreditType;
      private List<String> name_;


      public VideoCredit() {
         List var1 = Collections.emptyList();
         this.name_ = var1;
         this.cachedSize = -1;
      }

      public static DeviceDoc.VideoCredit parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.VideoCredit()).mergeFrom(var0);
      }

      public static DeviceDoc.VideoCredit parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.VideoCredit)((DeviceDoc.VideoCredit)(new DeviceDoc.VideoCredit()).mergeFrom(var0));
      }

      public DeviceDoc.VideoCredit addName(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.name_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.name_ = var2;
            }

            this.name_.add(var1);
            return this;
         }
      }

      public final DeviceDoc.VideoCredit clear() {
         DeviceDoc.VideoCredit var1 = this.clearCreditType();
         DeviceDoc.VideoCredit var2 = this.clearCredit();
         DeviceDoc.VideoCredit var3 = this.clearName();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.VideoCredit clearCredit() {
         this.hasCredit = (boolean)0;
         this.credit_ = "";
         return this;
      }

      public DeviceDoc.VideoCredit clearCreditType() {
         this.hasCreditType = (boolean)0;
         this.creditType_ = 0;
         return this;
      }

      public DeviceDoc.VideoCredit clearName() {
         List var1 = Collections.emptyList();
         this.name_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCredit() {
         return this.credit_;
      }

      public int getCreditType() {
         return this.creditType_;
      }

      public String getName(int var1) {
         return (String)this.name_.get(var1);
      }

      public int getNameCount() {
         return this.name_.size();
      }

      public List<String> getNameList() {
         return this.name_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasCreditType()) {
            int var2 = this.getCreditType();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasCredit()) {
            String var4 = this.getCredit();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         int var6 = 0;

         int var8;
         for(Iterator var7 = this.getNameList().iterator(); var7.hasNext(); var6 += var8) {
            var8 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var7.next());
         }

         int var9 = var1 + var6;
         int var10 = this.getNameList().size() * 1;
         int var11 = var9 + var10;
         this.cachedSize = var11;
         return var11;
      }

      public boolean hasCredit() {
         return this.hasCredit;
      }

      public boolean hasCreditType() {
         return this.hasCreditType;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasCreditType && this.hasCredit) {
            var1 = true;
         }

         return var1;
      }

      public DeviceDoc.VideoCredit mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setCreditType(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setCredit(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.addName(var7);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.VideoCredit setCredit(String var1) {
         this.hasCredit = (boolean)1;
         this.credit_ = var1;
         return this;
      }

      public DeviceDoc.VideoCredit setCreditType(int var1) {
         this.hasCreditType = (boolean)1;
         this.creditType_ = var1;
         return this;
      }

      public DeviceDoc.VideoCredit setName(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.name_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasCreditType()) {
            int var2 = this.getCreditType();
            var1.writeInt32(1, var2);
         }

         if(this.hasCredit()) {
            String var3 = this.getCredit();
            var1.writeString(2, var3);
         }

         Iterator var4 = this.getNameList().iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            var1.writeString(3, var5);
         }

      }
   }

   public static final class PlusOneData extends MessageMicro {

      public static final int CIRCLES_PEOPLE_FIELD_NUMBER = 4;
      public static final int CIRCLES_TOTAL_FIELD_NUMBER = 3;
      public static final int SET_BY_USER_FIELD_NUMBER = 1;
      public static final int TOTAL_FIELD_NUMBER = 2;
      private int cachedSize;
      private List<DeviceDoc.PlusPerson> circlesPeople_;
      private long circlesTotal_ = 0L;
      private boolean hasCirclesTotal;
      private boolean hasSetByUser;
      private boolean hasTotal;
      private boolean setByUser_ = 0;
      private long total_ = 0L;


      public PlusOneData() {
         List var1 = Collections.emptyList();
         this.circlesPeople_ = var1;
         this.cachedSize = -1;
      }

      public static DeviceDoc.PlusOneData parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.PlusOneData()).mergeFrom(var0);
      }

      public static DeviceDoc.PlusOneData parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.PlusOneData)((DeviceDoc.PlusOneData)(new DeviceDoc.PlusOneData()).mergeFrom(var0));
      }

      public DeviceDoc.PlusOneData addCirclesPeople(DeviceDoc.PlusPerson var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.circlesPeople_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.circlesPeople_ = var2;
            }

            this.circlesPeople_.add(var1);
            return this;
         }
      }

      public final DeviceDoc.PlusOneData clear() {
         DeviceDoc.PlusOneData var1 = this.clearSetByUser();
         DeviceDoc.PlusOneData var2 = this.clearTotal();
         DeviceDoc.PlusOneData var3 = this.clearCirclesTotal();
         DeviceDoc.PlusOneData var4 = this.clearCirclesPeople();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.PlusOneData clearCirclesPeople() {
         List var1 = Collections.emptyList();
         this.circlesPeople_ = var1;
         return this;
      }

      public DeviceDoc.PlusOneData clearCirclesTotal() {
         this.hasCirclesTotal = (boolean)0;
         this.circlesTotal_ = 0L;
         return this;
      }

      public DeviceDoc.PlusOneData clearSetByUser() {
         this.hasSetByUser = (boolean)0;
         this.setByUser_ = (boolean)0;
         return this;
      }

      public DeviceDoc.PlusOneData clearTotal() {
         this.hasTotal = (boolean)0;
         this.total_ = 0L;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public DeviceDoc.PlusPerson getCirclesPeople(int var1) {
         return (DeviceDoc.PlusPerson)this.circlesPeople_.get(var1);
      }

      public int getCirclesPeopleCount() {
         return this.circlesPeople_.size();
      }

      public List<DeviceDoc.PlusPerson> getCirclesPeopleList() {
         return this.circlesPeople_;
      }

      public long getCirclesTotal() {
         return this.circlesTotal_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasSetByUser()) {
            boolean var2 = this.getSetByUser();
            int var3 = CodedOutputStreamMicro.computeBoolSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasTotal()) {
            long var4 = this.getTotal();
            int var6 = CodedOutputStreamMicro.computeInt64Size(2, var4);
            var1 += var6;
         }

         if(this.hasCirclesTotal()) {
            long var7 = this.getCirclesTotal();
            int var9 = CodedOutputStreamMicro.computeInt64Size(3, var7);
            var1 += var9;
         }

         int var12;
         for(Iterator var10 = this.getCirclesPeopleList().iterator(); var10.hasNext(); var1 += var12) {
            DeviceDoc.PlusPerson var11 = (DeviceDoc.PlusPerson)var10.next();
            var12 = CodedOutputStreamMicro.computeMessageSize(4, var11);
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean getSetByUser() {
         return this.setByUser_;
      }

      public long getTotal() {
         return this.total_;
      }

      public boolean hasCirclesTotal() {
         return this.hasCirclesTotal;
      }

      public boolean hasSetByUser() {
         return this.hasSetByUser;
      }

      public boolean hasTotal() {
         return this.hasTotal;
      }

      public final boolean isInitialized() {
         return true;
      }

      public DeviceDoc.PlusOneData mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               boolean var3 = var1.readBool();
               this.setSetByUser(var3);
               break;
            case 16:
               long var5 = var1.readInt64();
               this.setTotal(var5);
               break;
            case 24:
               long var8 = var1.readInt64();
               this.setCirclesTotal(var8);
               break;
            case 34:
               DeviceDoc.PlusPerson var11 = new DeviceDoc.PlusPerson();
               var1.readMessage(var11);
               this.addCirclesPeople(var11);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.PlusOneData setCirclesPeople(int var1, DeviceDoc.PlusPerson var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.circlesPeople_.set(var1, var2);
            return this;
         }
      }

      public DeviceDoc.PlusOneData setCirclesTotal(long var1) {
         this.hasCirclesTotal = (boolean)1;
         this.circlesTotal_ = var1;
         return this;
      }

      public DeviceDoc.PlusOneData setSetByUser(boolean var1) {
         this.hasSetByUser = (boolean)1;
         this.setByUser_ = var1;
         return this;
      }

      public DeviceDoc.PlusOneData setTotal(long var1) {
         this.hasTotal = (boolean)1;
         this.total_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasSetByUser()) {
            boolean var2 = this.getSetByUser();
            var1.writeBool(1, var2);
         }

         if(this.hasTotal()) {
            long var3 = this.getTotal();
            var1.writeInt64(2, var3);
         }

         if(this.hasCirclesTotal()) {
            long var5 = this.getCirclesTotal();
            var1.writeInt64(3, var5);
         }

         Iterator var7 = this.getCirclesPeopleList().iterator();

         while(var7.hasNext()) {
            DeviceDoc.PlusPerson var8 = (DeviceDoc.PlusPerson)var7.next();
            var1.writeMessage(4, var8);
         }

      }
   }

   public static final class VideoRentalTerm extends MessageMicro {

      public static final int OFFER_ABBREVIATION_FIELD_NUMBER = 2;
      public static final int OFFER_TYPE_FIELD_NUMBER = 1;
      public static final int RENTAL_HEADER_FIELD_NUMBER = 3;
      public static final int TERM_FIELD_NUMBER = 4;
      private int cachedSize;
      private boolean hasOfferAbbreviation;
      private boolean hasOfferType;
      private boolean hasRentalHeader;
      private String offerAbbreviation_ = "";
      private int offerType_ = 1;
      private String rentalHeader_ = "";
      private List<DeviceDoc.VideoRentalTerm.Term> term_;


      public VideoRentalTerm() {
         List var1 = Collections.emptyList();
         this.term_ = var1;
         this.cachedSize = -1;
      }

      public static DeviceDoc.VideoRentalTerm parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.VideoRentalTerm()).mergeFrom(var0);
      }

      public static DeviceDoc.VideoRentalTerm parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.VideoRentalTerm)((DeviceDoc.VideoRentalTerm)(new DeviceDoc.VideoRentalTerm()).mergeFrom(var0));
      }

      public DeviceDoc.VideoRentalTerm addTerm(DeviceDoc.VideoRentalTerm.Term var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.term_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.term_ = var2;
            }

            this.term_.add(var1);
            return this;
         }
      }

      public final DeviceDoc.VideoRentalTerm clear() {
         DeviceDoc.VideoRentalTerm var1 = this.clearOfferType();
         DeviceDoc.VideoRentalTerm var2 = this.clearOfferAbbreviation();
         DeviceDoc.VideoRentalTerm var3 = this.clearRentalHeader();
         DeviceDoc.VideoRentalTerm var4 = this.clearTerm();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.VideoRentalTerm clearOfferAbbreviation() {
         this.hasOfferAbbreviation = (boolean)0;
         this.offerAbbreviation_ = "";
         return this;
      }

      public DeviceDoc.VideoRentalTerm clearOfferType() {
         this.hasOfferType = (boolean)0;
         this.offerType_ = 1;
         return this;
      }

      public DeviceDoc.VideoRentalTerm clearRentalHeader() {
         this.hasRentalHeader = (boolean)0;
         this.rentalHeader_ = "";
         return this;
      }

      public DeviceDoc.VideoRentalTerm clearTerm() {
         List var1 = Collections.emptyList();
         this.term_ = var1;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getOfferAbbreviation() {
         return this.offerAbbreviation_;
      }

      public int getOfferType() {
         return this.offerType_;
      }

      public String getRentalHeader() {
         return this.rentalHeader_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasOfferType()) {
            int var2 = this.getOfferType();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasOfferAbbreviation()) {
            String var4 = this.getOfferAbbreviation();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasRentalHeader()) {
            String var6 = this.getRentalHeader();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         int var10;
         for(Iterator var8 = this.getTermList().iterator(); var8.hasNext(); var1 += var10) {
            DeviceDoc.VideoRentalTerm.Term var9 = (DeviceDoc.VideoRentalTerm.Term)var8.next();
            var10 = CodedOutputStreamMicro.computeGroupSize(4, var9);
         }

         this.cachedSize = var1;
         return var1;
      }

      public DeviceDoc.VideoRentalTerm.Term getTerm(int var1) {
         return (DeviceDoc.VideoRentalTerm.Term)this.term_.get(var1);
      }

      public int getTermCount() {
         return this.term_.size();
      }

      public List<DeviceDoc.VideoRentalTerm.Term> getTermList() {
         return this.term_;
      }

      public boolean hasOfferAbbreviation() {
         return this.hasOfferAbbreviation;
      }

      public boolean hasOfferType() {
         return this.hasOfferType;
      }

      public boolean hasRentalHeader() {
         return this.hasRentalHeader;
      }

      public final boolean isInitialized() {
         return true;
      }

      public DeviceDoc.VideoRentalTerm mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setOfferType(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setOfferAbbreviation(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setRentalHeader(var7);
               break;
            case 35:
               DeviceDoc.VideoRentalTerm.Term var9 = new DeviceDoc.VideoRentalTerm.Term();
               var1.readGroup(var9, 4);
               this.addTerm(var9);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.VideoRentalTerm setOfferAbbreviation(String var1) {
         this.hasOfferAbbreviation = (boolean)1;
         this.offerAbbreviation_ = var1;
         return this;
      }

      public DeviceDoc.VideoRentalTerm setOfferType(int var1) {
         this.hasOfferType = (boolean)1;
         this.offerType_ = var1;
         return this;
      }

      public DeviceDoc.VideoRentalTerm setRentalHeader(String var1) {
         this.hasRentalHeader = (boolean)1;
         this.rentalHeader_ = var1;
         return this;
      }

      public DeviceDoc.VideoRentalTerm setTerm(int var1, DeviceDoc.VideoRentalTerm.Term var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.term_.set(var1, var2);
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasOfferType()) {
            int var2 = this.getOfferType();
            var1.writeInt32(1, var2);
         }

         if(this.hasOfferAbbreviation()) {
            String var3 = this.getOfferAbbreviation();
            var1.writeString(2, var3);
         }

         if(this.hasRentalHeader()) {
            String var4 = this.getRentalHeader();
            var1.writeString(3, var4);
         }

         Iterator var5 = this.getTermList().iterator();

         while(var5.hasNext()) {
            DeviceDoc.VideoRentalTerm.Term var6 = (DeviceDoc.VideoRentalTerm.Term)var5.next();
            var1.writeGroup(4, var6);
         }

      }

      public static final class Term extends MessageMicro {

         public static final int BODY_FIELD_NUMBER = 6;
         public static final int HEADER_FIELD_NUMBER = 5;
         private String body_ = "";
         private int cachedSize = -1;
         private boolean hasBody;
         private boolean hasHeader;
         private String header_ = "";


         public Term() {}

         public final DeviceDoc.VideoRentalTerm.Term clear() {
            DeviceDoc.VideoRentalTerm.Term var1 = this.clearHeader();
            DeviceDoc.VideoRentalTerm.Term var2 = this.clearBody();
            this.cachedSize = -1;
            return this;
         }

         public DeviceDoc.VideoRentalTerm.Term clearBody() {
            this.hasBody = (boolean)0;
            this.body_ = "";
            return this;
         }

         public DeviceDoc.VideoRentalTerm.Term clearHeader() {
            this.hasHeader = (boolean)0;
            this.header_ = "";
            return this;
         }

         public String getBody() {
            return this.body_;
         }

         public int getCachedSize() {
            if(this.cachedSize < 0) {
               int var1 = this.getSerializedSize();
            }

            return this.cachedSize;
         }

         public String getHeader() {
            return this.header_;
         }

         public int getSerializedSize() {
            int var1 = 0;
            if(this.hasHeader()) {
               String var2 = this.getHeader();
               int var3 = CodedOutputStreamMicro.computeStringSize(5, var2);
               var1 = 0 + var3;
            }

            if(this.hasBody()) {
               String var4 = this.getBody();
               int var5 = CodedOutputStreamMicro.computeStringSize(6, var4);
               var1 += var5;
            }

            this.cachedSize = var1;
            return var1;
         }

         public boolean hasBody() {
            return this.hasBody;
         }

         public boolean hasHeader() {
            return this.hasHeader;
         }

         public final boolean isInitialized() {
            return true;
         }

         public DeviceDoc.VideoRentalTerm.Term mergeFrom(CodedInputStreamMicro var1) throws IOException {
            while(true) {
               int var2 = var1.readTag();
               switch(var2) {
               case 42:
                  String var3 = var1.readString();
                  this.setHeader(var3);
                  break;
               case 50:
                  String var5 = var1.readString();
                  this.setBody(var5);
                  break;
               default:
                  if(this.parseUnknownField(var1, var2)) {
                     break;
                  }
               case 0:
                  return this;
               }
            }
         }

         public DeviceDoc.VideoRentalTerm.Term parseFrom(CodedInputStreamMicro var1) throws IOException {
            return (new DeviceDoc.VideoRentalTerm.Term()).mergeFrom(var1);
         }

         public DeviceDoc.VideoRentalTerm.Term parseFrom(byte[] var1) throws InvalidProtocolBufferMicroException {
            return (DeviceDoc.VideoRentalTerm.Term)((DeviceDoc.VideoRentalTerm.Term)(new DeviceDoc.VideoRentalTerm.Term()).mergeFrom(var1));
         }

         public DeviceDoc.VideoRentalTerm.Term setBody(String var1) {
            this.hasBody = (boolean)1;
            this.body_ = var1;
            return this;
         }

         public DeviceDoc.VideoRentalTerm.Term setHeader(String var1) {
            this.hasHeader = (boolean)1;
            this.header_ = var1;
            return this;
         }

         public void writeTo(CodedOutputStreamMicro var1) throws IOException {
            if(this.hasHeader()) {
               String var2 = this.getHeader();
               var1.writeString(5, var2);
            }

            if(this.hasBody()) {
               String var3 = this.getBody();
               var1.writeString(6, var3);
            }
         }
      }
   }

   public static final class DocumentDetails extends MessageMicro {

      public static final int ALBUM_DETAILS_FIELD_NUMBER = 2;
      public static final int APP_DETAILS_FIELD_NUMBER = 1;
      public static final int ARTIST_DETAILS_FIELD_NUMBER = 3;
      public static final int BOOK_DETAILS_FIELD_NUMBER = 5;
      public static final int SONG_DETAILS_FIELD_NUMBER = 4;
      public static final int VIDEO_DETAILS_FIELD_NUMBER = 6;
      private DeviceDoc.AlbumDetails albumDetails_ = null;
      private DeviceDoc.AppDetails appDetails_ = null;
      private DeviceDoc.ArtistDetails artistDetails_ = null;
      private BookInfo.BookDetails bookDetails_ = null;
      private int cachedSize = -1;
      private boolean hasAlbumDetails;
      private boolean hasAppDetails;
      private boolean hasArtistDetails;
      private boolean hasBookDetails;
      private boolean hasSongDetails;
      private boolean hasVideoDetails;
      private DeviceDoc.SongDetails songDetails_ = null;
      private DeviceDoc.VideoDetails videoDetails_ = null;


      public DocumentDetails() {}

      public static DeviceDoc.DocumentDetails parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.DocumentDetails()).mergeFrom(var0);
      }

      public static DeviceDoc.DocumentDetails parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.DocumentDetails)((DeviceDoc.DocumentDetails)(new DeviceDoc.DocumentDetails()).mergeFrom(var0));
      }

      public final DeviceDoc.DocumentDetails clear() {
         DeviceDoc.DocumentDetails var1 = this.clearAppDetails();
         DeviceDoc.DocumentDetails var2 = this.clearAlbumDetails();
         DeviceDoc.DocumentDetails var3 = this.clearArtistDetails();
         DeviceDoc.DocumentDetails var4 = this.clearSongDetails();
         DeviceDoc.DocumentDetails var5 = this.clearBookDetails();
         DeviceDoc.DocumentDetails var6 = this.clearVideoDetails();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.DocumentDetails clearAlbumDetails() {
         this.hasAlbumDetails = (boolean)0;
         this.albumDetails_ = null;
         return this;
      }

      public DeviceDoc.DocumentDetails clearAppDetails() {
         this.hasAppDetails = (boolean)0;
         this.appDetails_ = null;
         return this;
      }

      public DeviceDoc.DocumentDetails clearArtistDetails() {
         this.hasArtistDetails = (boolean)0;
         this.artistDetails_ = null;
         return this;
      }

      public DeviceDoc.DocumentDetails clearBookDetails() {
         this.hasBookDetails = (boolean)0;
         this.bookDetails_ = null;
         return this;
      }

      public DeviceDoc.DocumentDetails clearSongDetails() {
         this.hasSongDetails = (boolean)0;
         this.songDetails_ = null;
         return this;
      }

      public DeviceDoc.DocumentDetails clearVideoDetails() {
         this.hasVideoDetails = (boolean)0;
         this.videoDetails_ = null;
         return this;
      }

      public DeviceDoc.AlbumDetails getAlbumDetails() {
         return this.albumDetails_;
      }

      public DeviceDoc.AppDetails getAppDetails() {
         return this.appDetails_;
      }

      public DeviceDoc.ArtistDetails getArtistDetails() {
         return this.artistDetails_;
      }

      public BookInfo.BookDetails getBookDetails() {
         return this.bookDetails_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasAppDetails()) {
            DeviceDoc.AppDetails var2 = this.getAppDetails();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasAlbumDetails()) {
            DeviceDoc.AlbumDetails var4 = this.getAlbumDetails();
            int var5 = CodedOutputStreamMicro.computeMessageSize(2, var4);
            var1 += var5;
         }

         if(this.hasArtistDetails()) {
            DeviceDoc.ArtistDetails var6 = this.getArtistDetails();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasSongDetails()) {
            DeviceDoc.SongDetails var8 = this.getSongDetails();
            int var9 = CodedOutputStreamMicro.computeMessageSize(4, var8);
            var1 += var9;
         }

         if(this.hasBookDetails()) {
            BookInfo.BookDetails var10 = this.getBookDetails();
            int var11 = CodedOutputStreamMicro.computeMessageSize(5, var10);
            var1 += var11;
         }

         if(this.hasVideoDetails()) {
            DeviceDoc.VideoDetails var12 = this.getVideoDetails();
            int var13 = CodedOutputStreamMicro.computeMessageSize(6, var12);
            var1 += var13;
         }

         this.cachedSize = var1;
         return var1;
      }

      public DeviceDoc.SongDetails getSongDetails() {
         return this.songDetails_;
      }

      public DeviceDoc.VideoDetails getVideoDetails() {
         return this.videoDetails_;
      }

      public boolean hasAlbumDetails() {
         return this.hasAlbumDetails;
      }

      public boolean hasAppDetails() {
         return this.hasAppDetails;
      }

      public boolean hasArtistDetails() {
         return this.hasArtistDetails;
      }

      public boolean hasBookDetails() {
         return this.hasBookDetails;
      }

      public boolean hasSongDetails() {
         return this.hasSongDetails;
      }

      public boolean hasVideoDetails() {
         return this.hasVideoDetails;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if((!this.hasAlbumDetails() || this.getAlbumDetails().isInitialized()) && (!this.hasArtistDetails() || this.getArtistDetails().isInitialized()) && (!this.hasSongDetails() || this.getSongDetails().isInitialized()) && (!this.hasBookDetails() || this.getBookDetails().isInitialized()) && (!this.hasVideoDetails() || this.getVideoDetails().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public DeviceDoc.DocumentDetails mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               DeviceDoc.AppDetails var3 = new DeviceDoc.AppDetails();
               var1.readMessage(var3);
               this.setAppDetails(var3);
               break;
            case 18:
               DeviceDoc.AlbumDetails var5 = new DeviceDoc.AlbumDetails();
               var1.readMessage(var5);
               this.setAlbumDetails(var5);
               break;
            case 26:
               DeviceDoc.ArtistDetails var7 = new DeviceDoc.ArtistDetails();
               var1.readMessage(var7);
               this.setArtistDetails(var7);
               break;
            case 34:
               DeviceDoc.SongDetails var9 = new DeviceDoc.SongDetails();
               var1.readMessage(var9);
               this.setSongDetails(var9);
               break;
            case 42:
               BookInfo.BookDetails var11 = new BookInfo.BookDetails();
               var1.readMessage(var11);
               this.setBookDetails(var11);
               break;
            case 50:
               DeviceDoc.VideoDetails var13 = new DeviceDoc.VideoDetails();
               var1.readMessage(var13);
               this.setVideoDetails(var13);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.DocumentDetails setAlbumDetails(DeviceDoc.AlbumDetails var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasAlbumDetails = (boolean)1;
            this.albumDetails_ = var1;
            return this;
         }
      }

      public DeviceDoc.DocumentDetails setAppDetails(DeviceDoc.AppDetails var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasAppDetails = (boolean)1;
            this.appDetails_ = var1;
            return this;
         }
      }

      public DeviceDoc.DocumentDetails setArtistDetails(DeviceDoc.ArtistDetails var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasArtistDetails = (boolean)1;
            this.artistDetails_ = var1;
            return this;
         }
      }

      public DeviceDoc.DocumentDetails setBookDetails(BookInfo.BookDetails var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasBookDetails = (boolean)1;
            this.bookDetails_ = var1;
            return this;
         }
      }

      public DeviceDoc.DocumentDetails setSongDetails(DeviceDoc.SongDetails var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasSongDetails = (boolean)1;
            this.songDetails_ = var1;
            return this;
         }
      }

      public DeviceDoc.DocumentDetails setVideoDetails(DeviceDoc.VideoDetails var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasVideoDetails = (boolean)1;
            this.videoDetails_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasAppDetails()) {
            DeviceDoc.AppDetails var2 = this.getAppDetails();
            var1.writeMessage(1, var2);
         }

         if(this.hasAlbumDetails()) {
            DeviceDoc.AlbumDetails var3 = this.getAlbumDetails();
            var1.writeMessage(2, var3);
         }

         if(this.hasArtistDetails()) {
            DeviceDoc.ArtistDetails var4 = this.getArtistDetails();
            var1.writeMessage(3, var4);
         }

         if(this.hasSongDetails()) {
            DeviceDoc.SongDetails var5 = this.getSongDetails();
            var1.writeMessage(4, var5);
         }

         if(this.hasBookDetails()) {
            BookInfo.BookDetails var6 = this.getBookDetails();
            var1.writeMessage(5, var6);
         }

         if(this.hasVideoDetails()) {
            DeviceDoc.VideoDetails var7 = this.getVideoDetails();
            var1.writeMessage(6, var7);
         }
      }
   }

   public static final class ArtistDetails extends MessageMicro {

      public static final int ALBUM_FIELD_NUMBER = 2;
      public static final int ARTIST_FIELD_NUMBER = 1;
      private List<MusicInfo.Album> album_;
      private MusicInfo.Artist artist_ = null;
      private int cachedSize;
      private boolean hasArtist;


      public ArtistDetails() {
         List var1 = Collections.emptyList();
         this.album_ = var1;
         this.cachedSize = -1;
      }

      public static DeviceDoc.ArtistDetails parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.ArtistDetails()).mergeFrom(var0);
      }

      public static DeviceDoc.ArtistDetails parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.ArtistDetails)((DeviceDoc.ArtistDetails)(new DeviceDoc.ArtistDetails()).mergeFrom(var0));
      }

      public DeviceDoc.ArtistDetails addAlbum(MusicInfo.Album var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.album_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.album_ = var2;
            }

            this.album_.add(var1);
            return this;
         }
      }

      public final DeviceDoc.ArtistDetails clear() {
         DeviceDoc.ArtistDetails var1 = this.clearArtist();
         DeviceDoc.ArtistDetails var2 = this.clearAlbum();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.ArtistDetails clearAlbum() {
         List var1 = Collections.emptyList();
         this.album_ = var1;
         return this;
      }

      public DeviceDoc.ArtistDetails clearArtist() {
         this.hasArtist = (boolean)0;
         this.artist_ = null;
         return this;
      }

      public MusicInfo.Album getAlbum(int var1) {
         return (MusicInfo.Album)this.album_.get(var1);
      }

      public int getAlbumCount() {
         return this.album_.size();
      }

      public List<MusicInfo.Album> getAlbumList() {
         return this.album_;
      }

      public MusicInfo.Artist getArtist() {
         return this.artist_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasArtist()) {
            MusicInfo.Artist var2 = this.getArtist();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         int var6;
         for(Iterator var4 = this.getAlbumList().iterator(); var4.hasNext(); var1 += var6) {
            MusicInfo.Album var5 = (MusicInfo.Album)var4.next();
            var6 = CodedOutputStreamMicro.computeMessageSize(2, var5);
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasArtist() {
         return this.hasArtist;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(!this.hasArtist() || this.getArtist().isInitialized()) {
            Iterator var2 = this.getAlbumList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((MusicInfo.Album)var2.next()).isInitialized());
         }

         return var1;
      }

      public DeviceDoc.ArtistDetails mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               MusicInfo.Artist var3 = new MusicInfo.Artist();
               var1.readMessage(var3);
               this.setArtist(var3);
               break;
            case 18:
               MusicInfo.Album var5 = new MusicInfo.Album();
               var1.readMessage(var5);
               this.addAlbum(var5);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.ArtistDetails setAlbum(int var1, MusicInfo.Album var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.album_.set(var1, var2);
            return this;
         }
      }

      public DeviceDoc.ArtistDetails setArtist(MusicInfo.Artist var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasArtist = (boolean)1;
            this.artist_ = var1;
            return this;
         }
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasArtist()) {
            MusicInfo.Artist var2 = this.getArtist();
            var1.writeMessage(1, var2);
         }

         Iterator var3 = this.getAlbumList().iterator();

         while(var3.hasNext()) {
            MusicInfo.Album var4 = (MusicInfo.Album)var3.next();
            var1.writeMessage(2, var4);
         }

      }
   }

   public static final class DeviceDocument extends MessageMicro {

      public static final int CREATOR_FIELD_NUMBER = 8;
      public static final int DESCRIPTION_HTML_FIELD_NUMBER = 10;
      public static final int DETAILS_FIELD_NUMBER = 9;
      public static final int DETAILS_URL_FIELD_NUMBER = 3;
      public static final int DOCID_FIELD_NUMBER = 2;
      public static final int FINSKY_DOC_FIELD_NUMBER = 1;
      public static final int MORE_BY_BROWSE_URL_FIELD_NUMBER = 12;
      public static final int MORE_BY_HEADER_FIELD_NUMBER = 14;
      public static final int MORE_BY_LIST_URL_FIELD_NUMBER = 6;
      public static final int PLUS_ONE_DATA_FIELD_NUMBER = 16;
      public static final int RELATED_BROWSE_URL_FIELD_NUMBER = 11;
      public static final int RELATED_HEADER_FIELD_NUMBER = 13;
      public static final int RELATED_LIST_URL_FIELD_NUMBER = 5;
      public static final int REVIEWS_URL_FIELD_NUMBER = 4;
      public static final int SHARE_URL_FIELD_NUMBER = 7;
      public static final int TITLE_FIELD_NUMBER = 15;
      public static final int WARNING_MESSAGE_FIELD_NUMBER = 17;
      private int cachedSize = -1;
      private String creator_ = "";
      private String descriptionHtml_ = "";
      private String detailsUrl_ = "";
      private DeviceDoc.DocumentDetails details_ = null;
      private String docid_ = "";
      private Doc.Document finskyDoc_ = null;
      private boolean hasCreator;
      private boolean hasDescriptionHtml;
      private boolean hasDetails;
      private boolean hasDetailsUrl;
      private boolean hasDocid;
      private boolean hasFinskyDoc;
      private boolean hasMoreByBrowseUrl;
      private boolean hasMoreByHeader;
      private boolean hasMoreByListUrl;
      private boolean hasPlusOneData;
      private boolean hasRelatedBrowseUrl;
      private boolean hasRelatedHeader;
      private boolean hasRelatedListUrl;
      private boolean hasReviewsUrl;
      private boolean hasShareUrl;
      private boolean hasTitle;
      private boolean hasWarningMessage;
      private String moreByBrowseUrl_ = "";
      private String moreByHeader_ = "";
      private String moreByListUrl_ = "";
      private DeviceDoc.PlusOneData plusOneData_ = null;
      private String relatedBrowseUrl_ = "";
      private String relatedHeader_ = "";
      private String relatedListUrl_ = "";
      private String reviewsUrl_ = "";
      private String shareUrl_ = "";
      private String title_ = "";
      private String warningMessage_ = "";


      public DeviceDocument() {}

      public static DeviceDoc.DeviceDocument parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new DeviceDoc.DeviceDocument()).mergeFrom(var0);
      }

      public static DeviceDoc.DeviceDocument parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (DeviceDoc.DeviceDocument)((DeviceDoc.DeviceDocument)(new DeviceDoc.DeviceDocument()).mergeFrom(var0));
      }

      public final DeviceDoc.DeviceDocument clear() {
         DeviceDoc.DeviceDocument var1 = this.clearFinskyDoc();
         DeviceDoc.DeviceDocument var2 = this.clearDocid();
         DeviceDoc.DeviceDocument var3 = this.clearDetailsUrl();
         DeviceDoc.DeviceDocument var4 = this.clearReviewsUrl();
         DeviceDoc.DeviceDocument var5 = this.clearRelatedListUrl();
         DeviceDoc.DeviceDocument var6 = this.clearRelatedBrowseUrl();
         DeviceDoc.DeviceDocument var7 = this.clearRelatedHeader();
         DeviceDoc.DeviceDocument var8 = this.clearMoreByListUrl();
         DeviceDoc.DeviceDocument var9 = this.clearMoreByBrowseUrl();
         DeviceDoc.DeviceDocument var10 = this.clearMoreByHeader();
         DeviceDoc.DeviceDocument var11 = this.clearShareUrl();
         DeviceDoc.DeviceDocument var12 = this.clearTitle();
         DeviceDoc.DeviceDocument var13 = this.clearCreator();
         DeviceDoc.DeviceDocument var14 = this.clearDetails();
         DeviceDoc.DeviceDocument var15 = this.clearDescriptionHtml();
         DeviceDoc.DeviceDocument var16 = this.clearPlusOneData();
         DeviceDoc.DeviceDocument var17 = this.clearWarningMessage();
         this.cachedSize = -1;
         return this;
      }

      public DeviceDoc.DeviceDocument clearCreator() {
         this.hasCreator = (boolean)0;
         this.creator_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearDescriptionHtml() {
         this.hasDescriptionHtml = (boolean)0;
         this.descriptionHtml_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearDetails() {
         this.hasDetails = (boolean)0;
         this.details_ = null;
         return this;
      }

      public DeviceDoc.DeviceDocument clearDetailsUrl() {
         this.hasDetailsUrl = (boolean)0;
         this.detailsUrl_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearFinskyDoc() {
         this.hasFinskyDoc = (boolean)0;
         this.finskyDoc_ = null;
         return this;
      }

      public DeviceDoc.DeviceDocument clearMoreByBrowseUrl() {
         this.hasMoreByBrowseUrl = (boolean)0;
         this.moreByBrowseUrl_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearMoreByHeader() {
         this.hasMoreByHeader = (boolean)0;
         this.moreByHeader_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearMoreByListUrl() {
         this.hasMoreByListUrl = (boolean)0;
         this.moreByListUrl_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearPlusOneData() {
         this.hasPlusOneData = (boolean)0;
         this.plusOneData_ = null;
         return this;
      }

      public DeviceDoc.DeviceDocument clearRelatedBrowseUrl() {
         this.hasRelatedBrowseUrl = (boolean)0;
         this.relatedBrowseUrl_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearRelatedHeader() {
         this.hasRelatedHeader = (boolean)0;
         this.relatedHeader_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearRelatedListUrl() {
         this.hasRelatedListUrl = (boolean)0;
         this.relatedListUrl_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearReviewsUrl() {
         this.hasReviewsUrl = (boolean)0;
         this.reviewsUrl_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearShareUrl() {
         this.hasShareUrl = (boolean)0;
         this.shareUrl_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearTitle() {
         this.hasTitle = (boolean)0;
         this.title_ = "";
         return this;
      }

      public DeviceDoc.DeviceDocument clearWarningMessage() {
         this.hasWarningMessage = (boolean)0;
         this.warningMessage_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getCreator() {
         return this.creator_;
      }

      public String getDescriptionHtml() {
         return this.descriptionHtml_;
      }

      public DeviceDoc.DocumentDetails getDetails() {
         return this.details_;
      }

      public String getDetailsUrl() {
         return this.detailsUrl_;
      }

      public String getDocid() {
         return this.docid_;
      }

      public Doc.Document getFinskyDoc() {
         return this.finskyDoc_;
      }

      public String getMoreByBrowseUrl() {
         return this.moreByBrowseUrl_;
      }

      public String getMoreByHeader() {
         return this.moreByHeader_;
      }

      public String getMoreByListUrl() {
         return this.moreByListUrl_;
      }

      public DeviceDoc.PlusOneData getPlusOneData() {
         return this.plusOneData_;
      }

      public String getRelatedBrowseUrl() {
         return this.relatedBrowseUrl_;
      }

      public String getRelatedHeader() {
         return this.relatedHeader_;
      }

      public String getRelatedListUrl() {
         return this.relatedListUrl_;
      }

      public String getReviewsUrl() {
         return this.reviewsUrl_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasFinskyDoc()) {
            Doc.Document var2 = this.getFinskyDoc();
            int var3 = CodedOutputStreamMicro.computeMessageSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDocid()) {
            String var4 = this.getDocid();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasDetailsUrl()) {
            String var6 = this.getDetailsUrl();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasReviewsUrl()) {
            String var8 = this.getReviewsUrl();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         if(this.hasRelatedListUrl()) {
            String var10 = this.getRelatedListUrl();
            int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
            var1 += var11;
         }

         if(this.hasMoreByListUrl()) {
            String var12 = this.getMoreByListUrl();
            int var13 = CodedOutputStreamMicro.computeStringSize(6, var12);
            var1 += var13;
         }

         if(this.hasShareUrl()) {
            String var14 = this.getShareUrl();
            int var15 = CodedOutputStreamMicro.computeStringSize(7, var14);
            var1 += var15;
         }

         if(this.hasCreator()) {
            String var16 = this.getCreator();
            int var17 = CodedOutputStreamMicro.computeStringSize(8, var16);
            var1 += var17;
         }

         if(this.hasDetails()) {
            DeviceDoc.DocumentDetails var18 = this.getDetails();
            int var19 = CodedOutputStreamMicro.computeMessageSize(9, var18);
            var1 += var19;
         }

         if(this.hasDescriptionHtml()) {
            String var20 = this.getDescriptionHtml();
            int var21 = CodedOutputStreamMicro.computeStringSize(10, var20);
            var1 += var21;
         }

         if(this.hasRelatedBrowseUrl()) {
            String var22 = this.getRelatedBrowseUrl();
            int var23 = CodedOutputStreamMicro.computeStringSize(11, var22);
            var1 += var23;
         }

         if(this.hasMoreByBrowseUrl()) {
            String var24 = this.getMoreByBrowseUrl();
            int var25 = CodedOutputStreamMicro.computeStringSize(12, var24);
            var1 += var25;
         }

         if(this.hasRelatedHeader()) {
            String var26 = this.getRelatedHeader();
            int var27 = CodedOutputStreamMicro.computeStringSize(13, var26);
            var1 += var27;
         }

         if(this.hasMoreByHeader()) {
            String var28 = this.getMoreByHeader();
            int var29 = CodedOutputStreamMicro.computeStringSize(14, var28);
            var1 += var29;
         }

         if(this.hasTitle()) {
            String var30 = this.getTitle();
            int var31 = CodedOutputStreamMicro.computeStringSize(15, var30);
            var1 += var31;
         }

         if(this.hasPlusOneData()) {
            DeviceDoc.PlusOneData var32 = this.getPlusOneData();
            int var33 = CodedOutputStreamMicro.computeMessageSize(16, var32);
            var1 += var33;
         }

         if(this.hasWarningMessage()) {
            String var34 = this.getWarningMessage();
            int var35 = CodedOutputStreamMicro.computeStringSize(17, var34);
            var1 += var35;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getShareUrl() {
         return this.shareUrl_;
      }

      public String getTitle() {
         return this.title_;
      }

      public String getWarningMessage() {
         return this.warningMessage_;
      }

      public boolean hasCreator() {
         return this.hasCreator;
      }

      public boolean hasDescriptionHtml() {
         return this.hasDescriptionHtml;
      }

      public boolean hasDetails() {
         return this.hasDetails;
      }

      public boolean hasDetailsUrl() {
         return this.hasDetailsUrl;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasFinskyDoc() {
         return this.hasFinskyDoc;
      }

      public boolean hasMoreByBrowseUrl() {
         return this.hasMoreByBrowseUrl;
      }

      public boolean hasMoreByHeader() {
         return this.hasMoreByHeader;
      }

      public boolean hasMoreByListUrl() {
         return this.hasMoreByListUrl;
      }

      public boolean hasPlusOneData() {
         return this.hasPlusOneData;
      }

      public boolean hasRelatedBrowseUrl() {
         return this.hasRelatedBrowseUrl;
      }

      public boolean hasRelatedHeader() {
         return this.hasRelatedHeader;
      }

      public boolean hasRelatedListUrl() {
         return this.hasRelatedListUrl;
      }

      public boolean hasReviewsUrl() {
         return this.hasReviewsUrl;
      }

      public boolean hasShareUrl() {
         return this.hasShareUrl;
      }

      public boolean hasTitle() {
         return this.hasTitle;
      }

      public boolean hasWarningMessage() {
         return this.hasWarningMessage;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasFinskyDoc && this.hasDocid && this.getFinskyDoc().isInitialized() && (!this.hasDetails() || this.getDetails().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public DeviceDoc.DeviceDocument mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Doc.Document var3 = new Doc.Document();
               var1.readMessage(var3);
               this.setFinskyDoc(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setDocid(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setDetailsUrl(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setReviewsUrl(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setRelatedListUrl(var11);
               break;
            case 50:
               String var13 = var1.readString();
               this.setMoreByListUrl(var13);
               break;
            case 58:
               String var15 = var1.readString();
               this.setShareUrl(var15);
               break;
            case 66:
               String var17 = var1.readString();
               this.setCreator(var17);
               break;
            case 74:
               DeviceDoc.DocumentDetails var19 = new DeviceDoc.DocumentDetails();
               var1.readMessage(var19);
               this.setDetails(var19);
               break;
            case 82:
               String var21 = var1.readString();
               this.setDescriptionHtml(var21);
               break;
            case 90:
               String var23 = var1.readString();
               this.setRelatedBrowseUrl(var23);
               break;
            case 98:
               String var25 = var1.readString();
               this.setMoreByBrowseUrl(var25);
               break;
            case 106:
               String var27 = var1.readString();
               this.setRelatedHeader(var27);
               break;
            case 114:
               String var29 = var1.readString();
               this.setMoreByHeader(var29);
               break;
            case 122:
               String var31 = var1.readString();
               this.setTitle(var31);
               break;
            case 130:
               DeviceDoc.PlusOneData var33 = new DeviceDoc.PlusOneData();
               var1.readMessage(var33);
               this.setPlusOneData(var33);
               break;
            case 138:
               String var35 = var1.readString();
               this.setWarningMessage(var35);
               break;
            default:
               if(this.parseUnknownField(var1, var2)) {
                  break;
               }
            case 0:
               return this;
            }
         }
      }

      public DeviceDoc.DeviceDocument setCreator(String var1) {
         this.hasCreator = (boolean)1;
         this.creator_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setDescriptionHtml(String var1) {
         this.hasDescriptionHtml = (boolean)1;
         this.descriptionHtml_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setDetails(DeviceDoc.DocumentDetails var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDetails = (boolean)1;
            this.details_ = var1;
            return this;
         }
      }

      public DeviceDoc.DeviceDocument setDetailsUrl(String var1) {
         this.hasDetailsUrl = (boolean)1;
         this.detailsUrl_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setDocid(String var1) {
         this.hasDocid = (boolean)1;
         this.docid_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setFinskyDoc(Doc.Document var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasFinskyDoc = (boolean)1;
            this.finskyDoc_ = var1;
            return this;
         }
      }

      public DeviceDoc.DeviceDocument setMoreByBrowseUrl(String var1) {
         this.hasMoreByBrowseUrl = (boolean)1;
         this.moreByBrowseUrl_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setMoreByHeader(String var1) {
         this.hasMoreByHeader = (boolean)1;
         this.moreByHeader_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setMoreByListUrl(String var1) {
         this.hasMoreByListUrl = (boolean)1;
         this.moreByListUrl_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setPlusOneData(DeviceDoc.PlusOneData var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasPlusOneData = (boolean)1;
            this.plusOneData_ = var1;
            return this;
         }
      }

      public DeviceDoc.DeviceDocument setRelatedBrowseUrl(String var1) {
         this.hasRelatedBrowseUrl = (boolean)1;
         this.relatedBrowseUrl_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setRelatedHeader(String var1) {
         this.hasRelatedHeader = (boolean)1;
         this.relatedHeader_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setRelatedListUrl(String var1) {
         this.hasRelatedListUrl = (boolean)1;
         this.relatedListUrl_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setReviewsUrl(String var1) {
         this.hasReviewsUrl = (boolean)1;
         this.reviewsUrl_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setShareUrl(String var1) {
         this.hasShareUrl = (boolean)1;
         this.shareUrl_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setTitle(String var1) {
         this.hasTitle = (boolean)1;
         this.title_ = var1;
         return this;
      }

      public DeviceDoc.DeviceDocument setWarningMessage(String var1) {
         this.hasWarningMessage = (boolean)1;
         this.warningMessage_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasFinskyDoc()) {
            Doc.Document var2 = this.getFinskyDoc();
            var1.writeMessage(1, var2);
         }

         if(this.hasDocid()) {
            String var3 = this.getDocid();
            var1.writeString(2, var3);
         }

         if(this.hasDetailsUrl()) {
            String var4 = this.getDetailsUrl();
            var1.writeString(3, var4);
         }

         if(this.hasReviewsUrl()) {
            String var5 = this.getReviewsUrl();
            var1.writeString(4, var5);
         }

         if(this.hasRelatedListUrl()) {
            String var6 = this.getRelatedListUrl();
            var1.writeString(5, var6);
         }

         if(this.hasMoreByListUrl()) {
            String var7 = this.getMoreByListUrl();
            var1.writeString(6, var7);
         }

         if(this.hasShareUrl()) {
            String var8 = this.getShareUrl();
            var1.writeString(7, var8);
         }

         if(this.hasCreator()) {
            String var9 = this.getCreator();
            var1.writeString(8, var9);
         }

         if(this.hasDetails()) {
            DeviceDoc.DocumentDetails var10 = this.getDetails();
            var1.writeMessage(9, var10);
         }

         if(this.hasDescriptionHtml()) {
            String var11 = this.getDescriptionHtml();
            var1.writeString(10, var11);
         }

         if(this.hasRelatedBrowseUrl()) {
            String var12 = this.getRelatedBrowseUrl();
            var1.writeString(11, var12);
         }

         if(this.hasMoreByBrowseUrl()) {
            String var13 = this.getMoreByBrowseUrl();
            var1.writeString(12, var13);
         }

         if(this.hasRelatedHeader()) {
            String var14 = this.getRelatedHeader();
            var1.writeString(13, var14);
         }

         if(this.hasMoreByHeader()) {
            String var15 = this.getMoreByHeader();
            var1.writeString(14, var15);
         }

         if(this.hasTitle()) {
            String var16 = this.getTitle();
            var1.writeString(15, var16);
         }

         if(this.hasPlusOneData()) {
            DeviceDoc.PlusOneData var17 = this.getPlusOneData();
            var1.writeMessage(16, var17);
         }

         if(this.hasWarningMessage()) {
            String var18 = this.getWarningMessage();
            var1.writeString(17, var18);
         }
      }
   }
}
