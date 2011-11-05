package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Doc;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class MusicInfo {

   private MusicInfo() {}

   public static final class Detail extends MessageMicro {

      public static final int ARTIST_FIELD_NUMBER = 10;
      public static final int ARTIST_NAME_FIELD_NUMBER = 1;
      public static final int ARTWORK_FIELD_NUMBER = 7;
      public static final int CENSORING_FIELD_NUMBER = 3;
      public static final int DEPRECATED_GENRE_FIELD_NUMBER = 2;
      public static final int DESCRIPTION_FIELD_NUMBER = 5;
      public static final int DURATION_SEC_FIELD_NUMBER = 4;
      public static final int EXPLICIT_CONTENT = 1;
      public static final int GENRE_FIELD_NUMBER = 11;
      public static final int LABEL_FIELD_NUMBER = 9;
      public static final int MUSIC_LANGUAGE_FIELD_NUMBER = 8;
      public static final int NONE = 0;
      public static final int ORIGINAL_RELEASE_DATE_FIELD_NUMBER = 6;
      private List<String> artistName_;
      private List<MusicInfo.Artist> artist_;
      private List<Doc.Image> artwork_;
      private int cachedSize;
      private int censoring_;
      private String deprecatedGenre_;
      private String description_;
      private int durationSec_;
      private List<String> genre_;
      private boolean hasCensoring;
      private boolean hasDeprecatedGenre;
      private boolean hasDescription;
      private boolean hasDurationSec;
      private boolean hasLabel;
      private boolean hasMusicLanguage;
      private boolean hasOriginalReleaseDate;
      private String label_;
      private String musicLanguage_;
      private String originalReleaseDate_;


      public Detail() {
         List var1 = Collections.emptyList();
         this.artistName_ = var1;
         this.deprecatedGenre_ = "";
         this.censoring_ = 0;
         this.durationSec_ = 0;
         this.description_ = "";
         this.originalReleaseDate_ = "";
         List var2 = Collections.emptyList();
         this.artwork_ = var2;
         this.musicLanguage_ = "";
         this.label_ = "";
         List var3 = Collections.emptyList();
         this.artist_ = var3;
         List var4 = Collections.emptyList();
         this.genre_ = var4;
         this.cachedSize = -1;
      }

      public static MusicInfo.Detail parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new MusicInfo.Detail()).mergeFrom(var0);
      }

      public static MusicInfo.Detail parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (MusicInfo.Detail)((MusicInfo.Detail)(new MusicInfo.Detail()).mergeFrom(var0));
      }

      public MusicInfo.Detail addArtist(MusicInfo.Artist var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.artist_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.artist_ = var2;
            }

            this.artist_.add(var1);
            return this;
         }
      }

      public MusicInfo.Detail addArtistName(String var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.artistName_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.artistName_ = var2;
            }

            this.artistName_.add(var1);
            return this;
         }
      }

      public MusicInfo.Detail addArtwork(Doc.Image var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.artwork_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.artwork_ = var2;
            }

            this.artwork_.add(var1);
            return this;
         }
      }

      public MusicInfo.Detail addGenre(String var1) {
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

      public final MusicInfo.Detail clear() {
         MusicInfo.Detail var1 = this.clearArtistName();
         MusicInfo.Detail var2 = this.clearDeprecatedGenre();
         MusicInfo.Detail var3 = this.clearCensoring();
         MusicInfo.Detail var4 = this.clearDurationSec();
         MusicInfo.Detail var5 = this.clearDescription();
         MusicInfo.Detail var6 = this.clearOriginalReleaseDate();
         MusicInfo.Detail var7 = this.clearArtwork();
         MusicInfo.Detail var8 = this.clearMusicLanguage();
         MusicInfo.Detail var9 = this.clearLabel();
         MusicInfo.Detail var10 = this.clearArtist();
         MusicInfo.Detail var11 = this.clearGenre();
         this.cachedSize = -1;
         return this;
      }

      public MusicInfo.Detail clearArtist() {
         List var1 = Collections.emptyList();
         this.artist_ = var1;
         return this;
      }

      public MusicInfo.Detail clearArtistName() {
         List var1 = Collections.emptyList();
         this.artistName_ = var1;
         return this;
      }

      public MusicInfo.Detail clearArtwork() {
         List var1 = Collections.emptyList();
         this.artwork_ = var1;
         return this;
      }

      public MusicInfo.Detail clearCensoring() {
         this.hasCensoring = (boolean)0;
         this.censoring_ = 0;
         return this;
      }

      public MusicInfo.Detail clearDeprecatedGenre() {
         this.hasDeprecatedGenre = (boolean)0;
         this.deprecatedGenre_ = "";
         return this;
      }

      public MusicInfo.Detail clearDescription() {
         this.hasDescription = (boolean)0;
         this.description_ = "";
         return this;
      }

      public MusicInfo.Detail clearDurationSec() {
         this.hasDurationSec = (boolean)0;
         this.durationSec_ = 0;
         return this;
      }

      public MusicInfo.Detail clearGenre() {
         List var1 = Collections.emptyList();
         this.genre_ = var1;
         return this;
      }

      public MusicInfo.Detail clearLabel() {
         this.hasLabel = (boolean)0;
         this.label_ = "";
         return this;
      }

      public MusicInfo.Detail clearMusicLanguage() {
         this.hasMusicLanguage = (boolean)0;
         this.musicLanguage_ = "";
         return this;
      }

      public MusicInfo.Detail clearOriginalReleaseDate() {
         this.hasOriginalReleaseDate = (boolean)0;
         this.originalReleaseDate_ = "";
         return this;
      }

      public MusicInfo.Artist getArtist(int var1) {
         return (MusicInfo.Artist)this.artist_.get(var1);
      }

      public int getArtistCount() {
         return this.artist_.size();
      }

      public List<MusicInfo.Artist> getArtistList() {
         return this.artist_;
      }

      public String getArtistName(int var1) {
         return (String)this.artistName_.get(var1);
      }

      public int getArtistNameCount() {
         return this.artistName_.size();
      }

      public List<String> getArtistNameList() {
         return this.artistName_;
      }

      public Doc.Image getArtwork(int var1) {
         return (Doc.Image)this.artwork_.get(var1);
      }

      public int getArtworkCount() {
         return this.artwork_.size();
      }

      public List<Doc.Image> getArtworkList() {
         return this.artwork_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getCensoring() {
         return this.censoring_;
      }

      public String getDeprecatedGenre() {
         return this.deprecatedGenre_;
      }

      public String getDescription() {
         return this.description_;
      }

      public int getDurationSec() {
         return this.durationSec_;
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

      public String getLabel() {
         return this.label_;
      }

      public String getMusicLanguage() {
         return this.musicLanguage_;
      }

      public String getOriginalReleaseDate() {
         return this.originalReleaseDate_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var3;
         for(Iterator var2 = this.getArtistNameList().iterator(); var2.hasNext(); var1 += var3) {
            var3 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var2.next());
         }

         int var4 = 0 + var1;
         int var5 = this.getArtistNameList().size() * 1;
         int var6 = var4 + var5;
         if(this.hasDeprecatedGenre()) {
            String var7 = this.getDeprecatedGenre();
            int var8 = CodedOutputStreamMicro.computeStringSize(2, var7);
            var6 += var8;
         }

         if(this.hasCensoring()) {
            int var9 = this.getCensoring();
            int var10 = CodedOutputStreamMicro.computeInt32Size(3, var9);
            var6 += var10;
         }

         if(this.hasDurationSec()) {
            int var11 = this.getDurationSec();
            int var12 = CodedOutputStreamMicro.computeInt32Size(4, var11);
            var6 += var12;
         }

         if(this.hasDescription()) {
            String var13 = this.getDescription();
            int var14 = CodedOutputStreamMicro.computeStringSize(5, var13);
            var6 += var14;
         }

         if(this.hasOriginalReleaseDate()) {
            String var15 = this.getOriginalReleaseDate();
            int var16 = CodedOutputStreamMicro.computeStringSize(6, var15);
            var6 += var16;
         }

         int var19;
         for(Iterator var17 = this.getArtworkList().iterator(); var17.hasNext(); var6 += var19) {
            Doc.Image var18 = (Doc.Image)var17.next();
            var19 = CodedOutputStreamMicro.computeMessageSize(7, var18);
         }

         if(this.hasMusicLanguage()) {
            String var20 = this.getMusicLanguage();
            int var21 = CodedOutputStreamMicro.computeStringSize(8, var20);
            var6 += var21;
         }

         if(this.hasLabel()) {
            String var22 = this.getLabel();
            int var23 = CodedOutputStreamMicro.computeStringSize(9, var22);
            var6 += var23;
         }

         int var26;
         for(Iterator var24 = this.getArtistList().iterator(); var24.hasNext(); var6 += var26) {
            MusicInfo.Artist var25 = (MusicInfo.Artist)var24.next();
            var26 = CodedOutputStreamMicro.computeMessageSize(10, var25);
         }

         int var27 = 0;

         int var29;
         for(Iterator var28 = this.getGenreList().iterator(); var28.hasNext(); var27 += var29) {
            var29 = CodedOutputStreamMicro.computeStringSizeNoTag((String)var28.next());
         }

         int var30 = var6 + var27;
         int var31 = this.getGenreList().size() * 1;
         int var32 = var30 + var31;
         this.cachedSize = var32;
         return var32;
      }

      public boolean hasCensoring() {
         return this.hasCensoring;
      }

      public boolean hasDeprecatedGenre() {
         return this.hasDeprecatedGenre;
      }

      public boolean hasDescription() {
         return this.hasDescription;
      }

      public boolean hasDurationSec() {
         return this.hasDurationSec;
      }

      public boolean hasLabel() {
         return this.hasLabel;
      }

      public boolean hasMusicLanguage() {
         return this.hasMusicLanguage;
      }

      public boolean hasOriginalReleaseDate() {
         return this.hasOriginalReleaseDate;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         Iterator var2 = this.getArtworkList().iterator();

         do {
            if(!var2.hasNext()) {
               var2 = this.getArtistList().iterator();

               while(var2.hasNext()) {
                  if(!((MusicInfo.Artist)var2.next()).isInitialized()) {
                     return var1;
                  }
               }

               var1 = true;
               break;
            }
         } while(((Doc.Image)var2.next()).isInitialized());

         return var1;
      }

      public MusicInfo.Detail mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.addArtistName(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setDeprecatedGenre(var5);
               break;
            case 24:
               int var7 = var1.readInt32();
               this.setCensoring(var7);
               break;
            case 32:
               int var9 = var1.readInt32();
               this.setDurationSec(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setDescription(var11);
               break;
            case 50:
               String var13 = var1.readString();
               this.setOriginalReleaseDate(var13);
               break;
            case 58:
               Doc.Image var15 = new Doc.Image();
               var1.readMessage(var15);
               this.addArtwork(var15);
               break;
            case 66:
               String var17 = var1.readString();
               this.setMusicLanguage(var17);
               break;
            case 74:
               String var19 = var1.readString();
               this.setLabel(var19);
               break;
            case 82:
               MusicInfo.Artist var21 = new MusicInfo.Artist();
               var1.readMessage(var21);
               this.addArtist(var21);
               break;
            case 90:
               String var23 = var1.readString();
               this.addGenre(var23);
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

      public MusicInfo.Detail setArtist(int var1, MusicInfo.Artist var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.artist_.set(var1, var2);
            return this;
         }
      }

      public MusicInfo.Detail setArtistName(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.artistName_.set(var1, var2);
            return this;
         }
      }

      public MusicInfo.Detail setArtwork(int var1, Doc.Image var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.artwork_.set(var1, var2);
            return this;
         }
      }

      public MusicInfo.Detail setCensoring(int var1) {
         this.hasCensoring = (boolean)1;
         this.censoring_ = var1;
         return this;
      }

      public MusicInfo.Detail setDeprecatedGenre(String var1) {
         this.hasDeprecatedGenre = (boolean)1;
         this.deprecatedGenre_ = var1;
         return this;
      }

      public MusicInfo.Detail setDescription(String var1) {
         this.hasDescription = (boolean)1;
         this.description_ = var1;
         return this;
      }

      public MusicInfo.Detail setDurationSec(int var1) {
         this.hasDurationSec = (boolean)1;
         this.durationSec_ = var1;
         return this;
      }

      public MusicInfo.Detail setGenre(int var1, String var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.genre_.set(var1, var2);
            return this;
         }
      }

      public MusicInfo.Detail setLabel(String var1) {
         this.hasLabel = (boolean)1;
         this.label_ = var1;
         return this;
      }

      public MusicInfo.Detail setMusicLanguage(String var1) {
         this.hasMusicLanguage = (boolean)1;
         this.musicLanguage_ = var1;
         return this;
      }

      public MusicInfo.Detail setOriginalReleaseDate(String var1) {
         this.hasOriginalReleaseDate = (boolean)1;
         this.originalReleaseDate_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getArtistNameList().iterator();

         while(var2.hasNext()) {
            String var3 = (String)var2.next();
            var1.writeString(1, var3);
         }

         if(this.hasDeprecatedGenre()) {
            String var4 = this.getDeprecatedGenre();
            var1.writeString(2, var4);
         }

         if(this.hasCensoring()) {
            int var5 = this.getCensoring();
            var1.writeInt32(3, var5);
         }

         if(this.hasDurationSec()) {
            int var6 = this.getDurationSec();
            var1.writeInt32(4, var6);
         }

         if(this.hasDescription()) {
            String var7 = this.getDescription();
            var1.writeString(5, var7);
         }

         if(this.hasOriginalReleaseDate()) {
            String var8 = this.getOriginalReleaseDate();
            var1.writeString(6, var8);
         }

         Iterator var9 = this.getArtworkList().iterator();

         while(var9.hasNext()) {
            Doc.Image var10 = (Doc.Image)var9.next();
            var1.writeMessage(7, var10);
         }

         if(this.hasMusicLanguage()) {
            String var11 = this.getMusicLanguage();
            var1.writeString(8, var11);
         }

         if(this.hasLabel()) {
            String var12 = this.getLabel();
            var1.writeString(9, var12);
         }

         Iterator var13 = this.getArtistList().iterator();

         while(var13.hasNext()) {
            MusicInfo.Artist var14 = (MusicInfo.Artist)var13.next();
            var1.writeMessage(10, var14);
         }

         Iterator var15 = this.getGenreList().iterator();

         while(var15.hasNext()) {
            String var16 = (String)var15.next();
            var1.writeString(11, var16);
         }

      }
   }

   public static final class Track extends MessageMicro {

      public static final int ALBUM_ID_FIELD_NUMBER = 4;
      public static final int ALBUM_NAME_FIELD_NUMBER = 5;
      public static final int AUDIO_FIELD_NUMBER = 7;
      public static final int DETAIL_FIELD_NUMBER = 3;
      public static final int DISC_NUMBER_FIELD_NUMBER = 8;
      public static final int ID_FIELD_NUMBER = 1;
      public static final int MESSAGE_TYPE_ID = 16347447;
      public static final int NAME_FIELD_NUMBER = 2;
      public static final int TRACK_NUMBER_FIELD_NUMBER = 6;
      private String albumId_ = "";
      private String albumName_ = "";
      private List<MusicInfo.Audio> audio_;
      private int cachedSize;
      private MusicInfo.Detail detail_ = null;
      private int discNumber_ = 0;
      private boolean hasAlbumId;
      private boolean hasAlbumName;
      private boolean hasDetail;
      private boolean hasDiscNumber;
      private boolean hasId;
      private boolean hasName;
      private boolean hasTrackNumber;
      private String id_ = "";
      private String name_ = "";
      private int trackNumber_ = 0;


      public Track() {
         List var1 = Collections.emptyList();
         this.audio_ = var1;
         this.cachedSize = -1;
      }

      public static MusicInfo.Track parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new MusicInfo.Track()).mergeFrom(var0);
      }

      public static MusicInfo.Track parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (MusicInfo.Track)((MusicInfo.Track)(new MusicInfo.Track()).mergeFrom(var0));
      }

      public MusicInfo.Track addAudio(MusicInfo.Audio var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.audio_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.audio_ = var2;
            }

            this.audio_.add(var1);
            return this;
         }
      }

      public final MusicInfo.Track clear() {
         MusicInfo.Track var1 = this.clearId();
         MusicInfo.Track var2 = this.clearName();
         MusicInfo.Track var3 = this.clearDetail();
         MusicInfo.Track var4 = this.clearAlbumId();
         MusicInfo.Track var5 = this.clearAlbumName();
         MusicInfo.Track var6 = this.clearDiscNumber();
         MusicInfo.Track var7 = this.clearTrackNumber();
         MusicInfo.Track var8 = this.clearAudio();
         this.cachedSize = -1;
         return this;
      }

      public MusicInfo.Track clearAlbumId() {
         this.hasAlbumId = (boolean)0;
         this.albumId_ = "";
         return this;
      }

      public MusicInfo.Track clearAlbumName() {
         this.hasAlbumName = (boolean)0;
         this.albumName_ = "";
         return this;
      }

      public MusicInfo.Track clearAudio() {
         List var1 = Collections.emptyList();
         this.audio_ = var1;
         return this;
      }

      public MusicInfo.Track clearDetail() {
         this.hasDetail = (boolean)0;
         this.detail_ = null;
         return this;
      }

      public MusicInfo.Track clearDiscNumber() {
         this.hasDiscNumber = (boolean)0;
         this.discNumber_ = 0;
         return this;
      }

      public MusicInfo.Track clearId() {
         this.hasId = (boolean)0;
         this.id_ = "";
         return this;
      }

      public MusicInfo.Track clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public MusicInfo.Track clearTrackNumber() {
         this.hasTrackNumber = (boolean)0;
         this.trackNumber_ = 0;
         return this;
      }

      public String getAlbumId() {
         return this.albumId_;
      }

      public String getAlbumName() {
         return this.albumName_;
      }

      public MusicInfo.Audio getAudio(int var1) {
         return (MusicInfo.Audio)this.audio_.get(var1);
      }

      public int getAudioCount() {
         return this.audio_.size();
      }

      public List<MusicInfo.Audio> getAudioList() {
         return this.audio_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public MusicInfo.Detail getDetail() {
         return this.detail_;
      }

      public int getDiscNumber() {
         return this.discNumber_;
      }

      public String getId() {
         return this.id_;
      }

      public String getName() {
         return this.name_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasId()) {
            String var2 = this.getId();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasName()) {
            String var4 = this.getName();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasDetail()) {
            MusicInfo.Detail var6 = this.getDetail();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasAlbumId()) {
            String var8 = this.getAlbumId();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         if(this.hasAlbumName()) {
            String var10 = this.getAlbumName();
            int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
            var1 += var11;
         }

         if(this.hasTrackNumber()) {
            int var12 = this.getTrackNumber();
            int var13 = CodedOutputStreamMicro.computeInt32Size(6, var12);
            var1 += var13;
         }

         int var16;
         for(Iterator var14 = this.getAudioList().iterator(); var14.hasNext(); var1 += var16) {
            MusicInfo.Audio var15 = (MusicInfo.Audio)var14.next();
            var16 = CodedOutputStreamMicro.computeMessageSize(7, var15);
         }

         if(this.hasDiscNumber()) {
            int var17 = this.getDiscNumber();
            int var18 = CodedOutputStreamMicro.computeInt32Size(8, var17);
            var1 += var18;
         }

         this.cachedSize = var1;
         return var1;
      }

      public int getTrackNumber() {
         return this.trackNumber_;
      }

      public boolean hasAlbumId() {
         return this.hasAlbumId;
      }

      public boolean hasAlbumName() {
         return this.hasAlbumName;
      }

      public boolean hasDetail() {
         return this.hasDetail;
      }

      public boolean hasDiscNumber() {
         return this.hasDiscNumber;
      }

      public boolean hasId() {
         return this.hasId;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public boolean hasTrackNumber() {
         return this.hasTrackNumber;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasId && this.hasName && this.hasDetail && this.getDetail().isInitialized()) {
            Iterator var2 = this.getAudioList().iterator();

            do {
               if(!var2.hasNext()) {
                  var1 = true;
                  break;
               }
            } while(((MusicInfo.Audio)var2.next()).isInitialized());
         }

         return var1;
      }

      public MusicInfo.Track mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setId(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setName(var5);
               break;
            case 26:
               MusicInfo.Detail var7 = new MusicInfo.Detail();
               var1.readMessage(var7);
               this.setDetail(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setAlbumId(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setAlbumName(var11);
               break;
            case 48:
               int var13 = var1.readInt32();
               this.setTrackNumber(var13);
               break;
            case 58:
               MusicInfo.Audio var15 = new MusicInfo.Audio();
               var1.readMessage(var15);
               this.addAudio(var15);
               break;
            case 64:
               int var17 = var1.readInt32();
               this.setDiscNumber(var17);
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

      public MusicInfo.Track setAlbumId(String var1) {
         this.hasAlbumId = (boolean)1;
         this.albumId_ = var1;
         return this;
      }

      public MusicInfo.Track setAlbumName(String var1) {
         this.hasAlbumName = (boolean)1;
         this.albumName_ = var1;
         return this;
      }

      public MusicInfo.Track setAudio(int var1, MusicInfo.Audio var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.audio_.set(var1, var2);
            return this;
         }
      }

      public MusicInfo.Track setDetail(MusicInfo.Detail var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDetail = (boolean)1;
            this.detail_ = var1;
            return this;
         }
      }

      public MusicInfo.Track setDiscNumber(int var1) {
         this.hasDiscNumber = (boolean)1;
         this.discNumber_ = var1;
         return this;
      }

      public MusicInfo.Track setId(String var1) {
         this.hasId = (boolean)1;
         this.id_ = var1;
         return this;
      }

      public MusicInfo.Track setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public MusicInfo.Track setTrackNumber(int var1) {
         this.hasTrackNumber = (boolean)1;
         this.trackNumber_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasId()) {
            String var2 = this.getId();
            var1.writeString(1, var2);
         }

         if(this.hasName()) {
            String var3 = this.getName();
            var1.writeString(2, var3);
         }

         if(this.hasDetail()) {
            MusicInfo.Detail var4 = this.getDetail();
            var1.writeMessage(3, var4);
         }

         if(this.hasAlbumId()) {
            String var5 = this.getAlbumId();
            var1.writeString(4, var5);
         }

         if(this.hasAlbumName()) {
            String var6 = this.getAlbumName();
            var1.writeString(5, var6);
         }

         if(this.hasTrackNumber()) {
            int var7 = this.getTrackNumber();
            var1.writeInt32(6, var7);
         }

         Iterator var8 = this.getAudioList().iterator();

         while(var8.hasNext()) {
            MusicInfo.Audio var9 = (MusicInfo.Audio)var8.next();
            var1.writeMessage(7, var9);
         }

         if(this.hasDiscNumber()) {
            int var10 = this.getDiscNumber();
            var1.writeInt32(8, var10);
         }
      }
   }

   public static final class Audio extends MessageMicro {

      public static final int AUDIO_TYPE_FIELD_NUMBER = 1;
      public static final int DOWNLOAD = 1;
      public static final int DURATION_MSEC_FIELD_NUMBER = 3;
      public static final int SNIPPET = 0;
      public static final int STREAM = 2;
      public static final int URL_FIELD_NUMBER = 2;
      private int audioType_ = 0;
      private int cachedSize = -1;
      private int durationMsec_ = 0;
      private boolean hasAudioType;
      private boolean hasDurationMsec;
      private boolean hasUrl;
      private String url_ = "";


      public Audio() {}

      public static MusicInfo.Audio parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new MusicInfo.Audio()).mergeFrom(var0);
      }

      public static MusicInfo.Audio parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (MusicInfo.Audio)((MusicInfo.Audio)(new MusicInfo.Audio()).mergeFrom(var0));
      }

      public final MusicInfo.Audio clear() {
         MusicInfo.Audio var1 = this.clearAudioType();
         MusicInfo.Audio var2 = this.clearUrl();
         MusicInfo.Audio var3 = this.clearDurationMsec();
         this.cachedSize = -1;
         return this;
      }

      public MusicInfo.Audio clearAudioType() {
         this.hasAudioType = (boolean)0;
         this.audioType_ = 0;
         return this;
      }

      public MusicInfo.Audio clearDurationMsec() {
         this.hasDurationMsec = (boolean)0;
         this.durationMsec_ = 0;
         return this;
      }

      public MusicInfo.Audio clearUrl() {
         this.hasUrl = (boolean)0;
         this.url_ = "";
         return this;
      }

      public int getAudioType() {
         return this.audioType_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public int getDurationMsec() {
         return this.durationMsec_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasAudioType()) {
            int var2 = this.getAudioType();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasUrl()) {
            String var4 = this.getUrl();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasDurationMsec()) {
            int var6 = this.getDurationMsec();
            int var7 = CodedOutputStreamMicro.computeInt32Size(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getUrl() {
         return this.url_;
      }

      public boolean hasAudioType() {
         return this.hasAudioType;
      }

      public boolean hasDurationMsec() {
         return this.hasDurationMsec;
      }

      public boolean hasUrl() {
         return this.hasUrl;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasUrl) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public MusicInfo.Audio mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setAudioType(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setUrl(var5);
               break;
            case 24:
               int var7 = var1.readInt32();
               this.setDurationMsec(var7);
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

      public MusicInfo.Audio setAudioType(int var1) {
         this.hasAudioType = (boolean)1;
         this.audioType_ = var1;
         return this;
      }

      public MusicInfo.Audio setDurationMsec(int var1) {
         this.hasDurationMsec = (boolean)1;
         this.durationMsec_ = var1;
         return this;
      }

      public MusicInfo.Audio setUrl(String var1) {
         this.hasUrl = (boolean)1;
         this.url_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasAudioType()) {
            int var2 = this.getAudioType();
            var1.writeInt32(1, var2);
         }

         if(this.hasUrl()) {
            String var3 = this.getUrl();
            var1.writeString(2, var3);
         }

         if(this.hasDurationMsec()) {
            int var4 = this.getDurationMsec();
            var1.writeInt32(3, var4);
         }
      }
   }

   public static final class Album extends MessageMicro {

      public static final int DETAIL_FIELD_NUMBER = 3;
      public static final int ID_FIELD_NUMBER = 1;
      public static final int MESSAGE_TYPE_ID = 16361094;
      public static final int NAME_FIELD_NUMBER = 2;
      public static final int NUM_DISCS_FIELD_NUMBER = 4;
      private int cachedSize = -1;
      private MusicInfo.Detail detail_ = null;
      private boolean hasDetail;
      private boolean hasId;
      private boolean hasName;
      private boolean hasNumDiscs;
      private String id_ = "";
      private String name_ = "";
      private int numDiscs_ = 0;


      public Album() {}

      public static MusicInfo.Album parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new MusicInfo.Album()).mergeFrom(var0);
      }

      public static MusicInfo.Album parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (MusicInfo.Album)((MusicInfo.Album)(new MusicInfo.Album()).mergeFrom(var0));
      }

      public final MusicInfo.Album clear() {
         MusicInfo.Album var1 = this.clearId();
         MusicInfo.Album var2 = this.clearName();
         MusicInfo.Album var3 = this.clearDetail();
         MusicInfo.Album var4 = this.clearNumDiscs();
         this.cachedSize = -1;
         return this;
      }

      public MusicInfo.Album clearDetail() {
         this.hasDetail = (boolean)0;
         this.detail_ = null;
         return this;
      }

      public MusicInfo.Album clearId() {
         this.hasId = (boolean)0;
         this.id_ = "";
         return this;
      }

      public MusicInfo.Album clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public MusicInfo.Album clearNumDiscs() {
         this.hasNumDiscs = (boolean)0;
         this.numDiscs_ = 0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public MusicInfo.Detail getDetail() {
         return this.detail_;
      }

      public String getId() {
         return this.id_;
      }

      public String getName() {
         return this.name_;
      }

      public int getNumDiscs() {
         return this.numDiscs_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasId()) {
            String var2 = this.getId();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasName()) {
            String var4 = this.getName();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasDetail()) {
            MusicInfo.Detail var6 = this.getDetail();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         if(this.hasNumDiscs()) {
            int var8 = this.getNumDiscs();
            int var9 = CodedOutputStreamMicro.computeInt32Size(4, var8);
            var1 += var9;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasDetail() {
         return this.hasDetail;
      }

      public boolean hasId() {
         return this.hasId;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public boolean hasNumDiscs() {
         return this.hasNumDiscs;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasId && this.hasName && this.hasDetail && this.getDetail().isInitialized()) {
            var1 = true;
         }

         return var1;
      }

      public MusicInfo.Album mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setId(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setName(var5);
               break;
            case 26:
               MusicInfo.Detail var7 = new MusicInfo.Detail();
               var1.readMessage(var7);
               this.setDetail(var7);
               break;
            case 32:
               int var9 = var1.readInt32();
               this.setNumDiscs(var9);
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

      public MusicInfo.Album setDetail(MusicInfo.Detail var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDetail = (boolean)1;
            this.detail_ = var1;
            return this;
         }
      }

      public MusicInfo.Album setId(String var1) {
         this.hasId = (boolean)1;
         this.id_ = var1;
         return this;
      }

      public MusicInfo.Album setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public MusicInfo.Album setNumDiscs(int var1) {
         this.hasNumDiscs = (boolean)1;
         this.numDiscs_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasId()) {
            String var2 = this.getId();
            var1.writeString(1, var2);
         }

         if(this.hasName()) {
            String var3 = this.getName();
            var1.writeString(2, var3);
         }

         if(this.hasDetail()) {
            MusicInfo.Detail var4 = this.getDetail();
            var1.writeMessage(3, var4);
         }

         if(this.hasNumDiscs()) {
            int var5 = this.getNumDiscs();
            var1.writeInt32(4, var5);
         }
      }
   }

   public static final class Artist extends MessageMicro {

      public static final int DESCRIPTION_FIELD_NUMBER = 3;
      public static final int ID_FIELD_NUMBER = 1;
      public static final int MESSAGE_TYPE_ID = 16316931;
      public static final int NAME_FIELD_NUMBER = 2;
      private int cachedSize = -1;
      private String description_ = "";
      private boolean hasDescription;
      private boolean hasId;
      private boolean hasName;
      private String id_ = "";
      private String name_ = "";


      public Artist() {}

      public static MusicInfo.Artist parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new MusicInfo.Artist()).mergeFrom(var0);
      }

      public static MusicInfo.Artist parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (MusicInfo.Artist)((MusicInfo.Artist)(new MusicInfo.Artist()).mergeFrom(var0));
      }

      public final MusicInfo.Artist clear() {
         MusicInfo.Artist var1 = this.clearId();
         MusicInfo.Artist var2 = this.clearName();
         MusicInfo.Artist var3 = this.clearDescription();
         this.cachedSize = -1;
         return this;
      }

      public MusicInfo.Artist clearDescription() {
         this.hasDescription = (boolean)0;
         this.description_ = "";
         return this;
      }

      public MusicInfo.Artist clearId() {
         this.hasId = (boolean)0;
         this.id_ = "";
         return this;
      }

      public MusicInfo.Artist clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getDescription() {
         return this.description_;
      }

      public String getId() {
         return this.id_;
      }

      public String getName() {
         return this.name_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasId()) {
            String var2 = this.getId();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasName()) {
            String var4 = this.getName();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasDescription()) {
            String var6 = this.getDescription();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasDescription() {
         return this.hasDescription;
      }

      public boolean hasId() {
         return this.hasId;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasId && this.hasName) {
            var1 = true;
         }

         return var1;
      }

      public MusicInfo.Artist mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setId(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setName(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setDescription(var7);
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

      public MusicInfo.Artist setDescription(String var1) {
         this.hasDescription = (boolean)1;
         this.description_ = var1;
         return this;
      }

      public MusicInfo.Artist setId(String var1) {
         this.hasId = (boolean)1;
         this.id_ = var1;
         return this;
      }

      public MusicInfo.Artist setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasId()) {
            String var2 = this.getId();
            var1.writeString(1, var2);
         }

         if(this.hasName()) {
            String var3 = this.getName();
            var1.writeString(2, var3);
         }

         if(this.hasDescription()) {
            String var4 = this.getDescription();
            var1.writeString(3, var4);
         }
      }
   }
}
