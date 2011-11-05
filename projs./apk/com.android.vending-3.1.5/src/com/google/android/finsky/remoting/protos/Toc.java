package com.google.android.finsky.remoting.protos;

import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Toc {

   private Toc() {}

   public static final class TocResponse extends MessageMicro {

      public static final int CORPUS_FIELD_NUMBER = 1;
      public static final int HOME_URL_FIELD_NUMBER = 4;
      public static final int TOS_CONTENT_FIELD_NUMBER = 3;
      public static final int TOS_VERSION_FIELD_NUMBER = 2;
      private int cachedSize;
      private List<Toc.CorpusMetadata> corpus_;
      private boolean hasHomeUrl;
      private boolean hasTosContent;
      private boolean hasTosVersion;
      private String homeUrl_;
      private String tosContent_;
      private int tosVersion_;


      public TocResponse() {
         List var1 = Collections.emptyList();
         this.corpus_ = var1;
         this.tosVersion_ = 0;
         this.tosContent_ = "";
         this.homeUrl_ = "";
         this.cachedSize = -1;
      }

      public static Toc.TocResponse parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Toc.TocResponse()).mergeFrom(var0);
      }

      public static Toc.TocResponse parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Toc.TocResponse)((Toc.TocResponse)(new Toc.TocResponse()).mergeFrom(var0));
      }

      public Toc.TocResponse addCorpus(Toc.CorpusMetadata var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.corpus_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.corpus_ = var2;
            }

            this.corpus_.add(var1);
            return this;
         }
      }

      public final Toc.TocResponse clear() {
         Toc.TocResponse var1 = this.clearCorpus();
         Toc.TocResponse var2 = this.clearTosVersion();
         Toc.TocResponse var3 = this.clearTosContent();
         Toc.TocResponse var4 = this.clearHomeUrl();
         this.cachedSize = -1;
         return this;
      }

      public Toc.TocResponse clearCorpus() {
         List var1 = Collections.emptyList();
         this.corpus_ = var1;
         return this;
      }

      public Toc.TocResponse clearHomeUrl() {
         this.hasHomeUrl = (boolean)0;
         this.homeUrl_ = "";
         return this;
      }

      public Toc.TocResponse clearTosContent() {
         this.hasTosContent = (boolean)0;
         this.tosContent_ = "";
         return this;
      }

      public Toc.TocResponse clearTosVersion() {
         this.hasTosVersion = (boolean)0;
         this.tosVersion_ = 0;
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public Toc.CorpusMetadata getCorpus(int var1) {
         return (Toc.CorpusMetadata)this.corpus_.get(var1);
      }

      public int getCorpusCount() {
         return this.corpus_.size();
      }

      public List<Toc.CorpusMetadata> getCorpusList() {
         return this.corpus_;
      }

      public String getHomeUrl() {
         return this.homeUrl_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getCorpusList().iterator(); var2.hasNext(); var1 += var4) {
            Toc.CorpusMetadata var3 = (Toc.CorpusMetadata)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(1, var3);
         }

         if(this.hasTosVersion()) {
            int var5 = this.getTosVersion();
            int var6 = CodedOutputStreamMicro.computeInt32Size(2, var5);
            var1 += var6;
         }

         if(this.hasTosContent()) {
            String var7 = this.getTosContent();
            int var8 = CodedOutputStreamMicro.computeStringSize(3, var7);
            var1 += var8;
         }

         if(this.hasHomeUrl()) {
            String var9 = this.getHomeUrl();
            int var10 = CodedOutputStreamMicro.computeStringSize(4, var9);
            var1 += var10;
         }

         this.cachedSize = var1;
         return var1;
      }

      public String getTosContent() {
         return this.tosContent_;
      }

      public int getTosVersion() {
         return this.tosVersion_;
      }

      public boolean hasHomeUrl() {
         return this.hasHomeUrl;
      }

      public boolean hasTosContent() {
         return this.hasTosContent;
      }

      public boolean hasTosVersion() {
         return this.hasTosVersion;
      }

      public final boolean isInitialized() {
         Iterator var1 = this.getCorpusList().iterator();

         boolean var2;
         while(true) {
            if(var1.hasNext()) {
               if(((Toc.CorpusMetadata)var1.next()).isInitialized()) {
                  continue;
               }

               var2 = false;
               break;
            }

            var2 = true;
            break;
         }

         return var2;
      }

      public Toc.TocResponse mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               Toc.CorpusMetadata var3 = new Toc.CorpusMetadata();
               var1.readMessage(var3);
               this.addCorpus(var3);
               break;
            case 16:
               int var5 = var1.readInt32();
               this.setTosVersion(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setTosContent(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setHomeUrl(var9);
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

      public Toc.TocResponse setCorpus(int var1, Toc.CorpusMetadata var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.corpus_.set(var1, var2);
            return this;
         }
      }

      public Toc.TocResponse setHomeUrl(String var1) {
         this.hasHomeUrl = (boolean)1;
         this.homeUrl_ = var1;
         return this;
      }

      public Toc.TocResponse setTosContent(String var1) {
         this.hasTosContent = (boolean)1;
         this.tosContent_ = var1;
         return this;
      }

      public Toc.TocResponse setTosVersion(int var1) {
         this.hasTosVersion = (boolean)1;
         this.tosVersion_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getCorpusList().iterator();

         while(var2.hasNext()) {
            Toc.CorpusMetadata var3 = (Toc.CorpusMetadata)var2.next();
            var1.writeMessage(1, var3);
         }

         if(this.hasTosVersion()) {
            int var4 = this.getTosVersion();
            var1.writeInt32(2, var4);
         }

         if(this.hasTosContent()) {
            String var5 = this.getTosContent();
            var1.writeString(3, var5);
         }

         if(this.hasHomeUrl()) {
            String var6 = this.getHomeUrl();
            var1.writeString(4, var6);
         }
      }
   }

   public static final class CorpusMetadata extends MessageMicro {

      public static final int BACKEND_FIELD_NUMBER = 1;
      public static final int LANDING_URL_FIELD_NUMBER = 3;
      public static final int LIBRARY_NAME_FIELD_NUMBER = 4;
      public static final int LIBRARY_URL_FIELD_NUMBER = 5;
      public static final int NAME_FIELD_NUMBER = 2;
      private int backend_ = 0;
      private int cachedSize = -1;
      private boolean hasBackend;
      private boolean hasLandingUrl;
      private boolean hasLibraryName;
      private boolean hasLibraryUrl;
      private boolean hasName;
      private String landingUrl_ = "";
      private String libraryName_ = "";
      private String libraryUrl_ = "";
      private String name_ = "";


      public CorpusMetadata() {}

      public static Toc.CorpusMetadata parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new Toc.CorpusMetadata()).mergeFrom(var0);
      }

      public static Toc.CorpusMetadata parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (Toc.CorpusMetadata)((Toc.CorpusMetadata)(new Toc.CorpusMetadata()).mergeFrom(var0));
      }

      public final Toc.CorpusMetadata clear() {
         Toc.CorpusMetadata var1 = this.clearBackend();
         Toc.CorpusMetadata var2 = this.clearName();
         Toc.CorpusMetadata var3 = this.clearLandingUrl();
         Toc.CorpusMetadata var4 = this.clearLibraryName();
         Toc.CorpusMetadata var5 = this.clearLibraryUrl();
         this.cachedSize = -1;
         return this;
      }

      public Toc.CorpusMetadata clearBackend() {
         this.hasBackend = (boolean)0;
         this.backend_ = 0;
         return this;
      }

      public Toc.CorpusMetadata clearLandingUrl() {
         this.hasLandingUrl = (boolean)0;
         this.landingUrl_ = "";
         return this;
      }

      public Toc.CorpusMetadata clearLibraryName() {
         this.hasLibraryName = (boolean)0;
         this.libraryName_ = "";
         return this;
      }

      public Toc.CorpusMetadata clearLibraryUrl() {
         this.hasLibraryUrl = (boolean)0;
         this.libraryUrl_ = "";
         return this;
      }

      public Toc.CorpusMetadata clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public int getBackend() {
         return this.backend_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getLandingUrl() {
         return this.landingUrl_;
      }

      public String getLibraryName() {
         return this.libraryName_;
      }

      public String getLibraryUrl() {
         return this.libraryUrl_;
      }

      public String getName() {
         return this.name_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasBackend()) {
            int var2 = this.getBackend();
            int var3 = CodedOutputStreamMicro.computeInt32Size(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasName()) {
            String var4 = this.getName();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasLandingUrl()) {
            String var6 = this.getLandingUrl();
            int var7 = CodedOutputStreamMicro.computeStringSize(3, var6);
            var1 += var7;
         }

         if(this.hasLibraryName()) {
            String var8 = this.getLibraryName();
            int var9 = CodedOutputStreamMicro.computeStringSize(4, var8);
            var1 += var9;
         }

         if(this.hasLibraryUrl()) {
            String var10 = this.getLibraryUrl();
            int var11 = CodedOutputStreamMicro.computeStringSize(5, var10);
            var1 += var11;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasBackend() {
         return this.hasBackend;
      }

      public boolean hasLandingUrl() {
         return this.hasLandingUrl;
      }

      public boolean hasLibraryName() {
         return this.hasLibraryName;
      }

      public boolean hasLibraryUrl() {
         return this.hasLibraryUrl;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public final boolean isInitialized() {
         boolean var1;
         if(!this.hasBackend) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public Toc.CorpusMetadata mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 8:
               int var3 = var1.readInt32();
               this.setBackend(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setName(var5);
               break;
            case 26:
               String var7 = var1.readString();
               this.setLandingUrl(var7);
               break;
            case 34:
               String var9 = var1.readString();
               this.setLibraryName(var9);
               break;
            case 42:
               String var11 = var1.readString();
               this.setLibraryUrl(var11);
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

      public Toc.CorpusMetadata setBackend(int var1) {
         this.hasBackend = (boolean)1;
         this.backend_ = var1;
         return this;
      }

      public Toc.CorpusMetadata setLandingUrl(String var1) {
         this.hasLandingUrl = (boolean)1;
         this.landingUrl_ = var1;
         return this;
      }

      public Toc.CorpusMetadata setLibraryName(String var1) {
         this.hasLibraryName = (boolean)1;
         this.libraryName_ = var1;
         return this;
      }

      public Toc.CorpusMetadata setLibraryUrl(String var1) {
         this.hasLibraryUrl = (boolean)1;
         this.libraryUrl_ = var1;
         return this;
      }

      public Toc.CorpusMetadata setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasBackend()) {
            int var2 = this.getBackend();
            var1.writeInt32(1, var2);
         }

         if(this.hasName()) {
            String var3 = this.getName();
            var1.writeString(2, var3);
         }

         if(this.hasLandingUrl()) {
            String var4 = this.getLandingUrl();
            var1.writeString(3, var4);
         }

         if(this.hasLibraryName()) {
            String var5 = this.getLibraryName();
            var1.writeString(4, var5);
         }

         if(this.hasLibraryUrl()) {
            String var6 = this.getLibraryUrl();
            var1.writeString(5, var6);
         }
      }
   }
}
