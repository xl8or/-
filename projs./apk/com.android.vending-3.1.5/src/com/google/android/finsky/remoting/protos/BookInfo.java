package com.google.android.finsky.remoting.protos;

import com.google.android.finsky.remoting.protos.Common;
import com.google.protobuf.micro.CodedInputStreamMicro;
import com.google.protobuf.micro.CodedOutputStreamMicro;
import com.google.protobuf.micro.InvalidProtocolBufferMicroException;
import com.google.protobuf.micro.MessageMicro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class BookInfo {

   private BookInfo() {}

   public static final class BookDetails extends MessageMicro {

      public static final int AUTHOR_FIELD_NUMBER = 9;
      public static final int ISBN_FIELD_NUMBER = 6;
      public static final int MESSAGE_TYPE_ID = 16777659;
      public static final int NUMBER_OF_PAGES_FIELD_NUMBER = 7;
      public static final int PUBLICATION_DATE_FIELD_NUMBER = 5;
      public static final int PUBLISHER_FIELD_NUMBER = 4;
      public static final int SUBJECT_FIELD_NUMBER = 3;
      public static final int SUBTITLE_FIELD_NUMBER = 8;
      private List<BookInfo.BookAuthor> author_;
      private int cachedSize;
      private boolean hasIsbn;
      private boolean hasNumberOfPages;
      private boolean hasPublicationDate;
      private boolean hasPublisher;
      private boolean hasSubtitle;
      private String isbn_;
      private int numberOfPages_;
      private String publicationDate_;
      private String publisher_;
      private List<BookInfo.BookSubject> subject_;
      private String subtitle_;


      public BookDetails() {
         List var1 = Collections.emptyList();
         this.author_ = var1;
         List var2 = Collections.emptyList();
         this.subject_ = var2;
         this.publisher_ = "";
         this.publicationDate_ = "";
         this.isbn_ = "";
         this.numberOfPages_ = 0;
         this.subtitle_ = "";
         this.cachedSize = -1;
      }

      public static BookInfo.BookDetails parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BookInfo.BookDetails()).mergeFrom(var0);
      }

      public static BookInfo.BookDetails parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BookInfo.BookDetails)((BookInfo.BookDetails)(new BookInfo.BookDetails()).mergeFrom(var0));
      }

      public BookInfo.BookDetails addAuthor(BookInfo.BookAuthor var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.author_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.author_ = var2;
            }

            this.author_.add(var1);
            return this;
         }
      }

      public BookInfo.BookDetails addSubject(BookInfo.BookSubject var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            if(this.subject_.isEmpty()) {
               ArrayList var2 = new ArrayList();
               this.subject_ = var2;
            }

            this.subject_.add(var1);
            return this;
         }
      }

      public final BookInfo.BookDetails clear() {
         BookInfo.BookDetails var1 = this.clearAuthor();
         BookInfo.BookDetails var2 = this.clearSubject();
         BookInfo.BookDetails var3 = this.clearPublisher();
         BookInfo.BookDetails var4 = this.clearPublicationDate();
         BookInfo.BookDetails var5 = this.clearIsbn();
         BookInfo.BookDetails var6 = this.clearNumberOfPages();
         BookInfo.BookDetails var7 = this.clearSubtitle();
         this.cachedSize = -1;
         return this;
      }

      public BookInfo.BookDetails clearAuthor() {
         List var1 = Collections.emptyList();
         this.author_ = var1;
         return this;
      }

      public BookInfo.BookDetails clearIsbn() {
         this.hasIsbn = (boolean)0;
         this.isbn_ = "";
         return this;
      }

      public BookInfo.BookDetails clearNumberOfPages() {
         this.hasNumberOfPages = (boolean)0;
         this.numberOfPages_ = 0;
         return this;
      }

      public BookInfo.BookDetails clearPublicationDate() {
         this.hasPublicationDate = (boolean)0;
         this.publicationDate_ = "";
         return this;
      }

      public BookInfo.BookDetails clearPublisher() {
         this.hasPublisher = (boolean)0;
         this.publisher_ = "";
         return this;
      }

      public BookInfo.BookDetails clearSubject() {
         List var1 = Collections.emptyList();
         this.subject_ = var1;
         return this;
      }

      public BookInfo.BookDetails clearSubtitle() {
         this.hasSubtitle = (boolean)0;
         this.subtitle_ = "";
         return this;
      }

      public BookInfo.BookAuthor getAuthor(int var1) {
         return (BookInfo.BookAuthor)this.author_.get(var1);
      }

      public int getAuthorCount() {
         return this.author_.size();
      }

      public List<BookInfo.BookAuthor> getAuthorList() {
         return this.author_;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getIsbn() {
         return this.isbn_;
      }

      public int getNumberOfPages() {
         return this.numberOfPages_;
      }

      public String getPublicationDate() {
         return this.publicationDate_;
      }

      public String getPublisher() {
         return this.publisher_;
      }

      public int getSerializedSize() {
         int var1 = 0;

         int var4;
         for(Iterator var2 = this.getSubjectList().iterator(); var2.hasNext(); var1 += var4) {
            BookInfo.BookSubject var3 = (BookInfo.BookSubject)var2.next();
            var4 = CodedOutputStreamMicro.computeMessageSize(3, var3);
         }

         if(this.hasPublisher()) {
            String var5 = this.getPublisher();
            int var6 = CodedOutputStreamMicro.computeStringSize(4, var5);
            var1 += var6;
         }

         if(this.hasPublicationDate()) {
            String var7 = this.getPublicationDate();
            int var8 = CodedOutputStreamMicro.computeStringSize(5, var7);
            var1 += var8;
         }

         if(this.hasIsbn()) {
            String var9 = this.getIsbn();
            int var10 = CodedOutputStreamMicro.computeStringSize(6, var9);
            var1 += var10;
         }

         if(this.hasNumberOfPages()) {
            int var11 = this.getNumberOfPages();
            int var12 = CodedOutputStreamMicro.computeInt32Size(7, var11);
            var1 += var12;
         }

         if(this.hasSubtitle()) {
            String var13 = this.getSubtitle();
            int var14 = CodedOutputStreamMicro.computeStringSize(8, var13);
            var1 += var14;
         }

         int var17;
         for(Iterator var15 = this.getAuthorList().iterator(); var15.hasNext(); var1 += var17) {
            BookInfo.BookAuthor var16 = (BookInfo.BookAuthor)var15.next();
            var17 = CodedOutputStreamMicro.computeMessageSize(9, var16);
         }

         this.cachedSize = var1;
         return var1;
      }

      public BookInfo.BookSubject getSubject(int var1) {
         return (BookInfo.BookSubject)this.subject_.get(var1);
      }

      public int getSubjectCount() {
         return this.subject_.size();
      }

      public List<BookInfo.BookSubject> getSubjectList() {
         return this.subject_;
      }

      public String getSubtitle() {
         return this.subtitle_;
      }

      public boolean hasIsbn() {
         return this.hasIsbn;
      }

      public boolean hasNumberOfPages() {
         return this.hasNumberOfPages;
      }

      public boolean hasPublicationDate() {
         return this.hasPublicationDate;
      }

      public boolean hasPublisher() {
         return this.hasPublisher;
      }

      public boolean hasSubtitle() {
         return this.hasSubtitle;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         Iterator var2 = this.getAuthorList().iterator();

         do {
            if(!var2.hasNext()) {
               var2 = this.getSubjectList().iterator();

               while(var2.hasNext()) {
                  if(!((BookInfo.BookSubject)var2.next()).isInitialized()) {
                     return var1;
                  }
               }

               var1 = true;
               break;
            }
         } while(((BookInfo.BookAuthor)var2.next()).isInitialized());

         return var1;
      }

      public BookInfo.BookDetails mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 26:
               BookInfo.BookSubject var3 = new BookInfo.BookSubject();
               var1.readMessage(var3);
               this.addSubject(var3);
               break;
            case 34:
               String var5 = var1.readString();
               this.setPublisher(var5);
               break;
            case 42:
               String var7 = var1.readString();
               this.setPublicationDate(var7);
               break;
            case 50:
               String var9 = var1.readString();
               this.setIsbn(var9);
               break;
            case 56:
               int var11 = var1.readInt32();
               this.setNumberOfPages(var11);
               break;
            case 66:
               String var13 = var1.readString();
               this.setSubtitle(var13);
               break;
            case 74:
               BookInfo.BookAuthor var15 = new BookInfo.BookAuthor();
               var1.readMessage(var15);
               this.addAuthor(var15);
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

      public BookInfo.BookDetails setAuthor(int var1, BookInfo.BookAuthor var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.author_.set(var1, var2);
            return this;
         }
      }

      public BookInfo.BookDetails setIsbn(String var1) {
         this.hasIsbn = (boolean)1;
         this.isbn_ = var1;
         return this;
      }

      public BookInfo.BookDetails setNumberOfPages(int var1) {
         this.hasNumberOfPages = (boolean)1;
         this.numberOfPages_ = var1;
         return this;
      }

      public BookInfo.BookDetails setPublicationDate(String var1) {
         this.hasPublicationDate = (boolean)1;
         this.publicationDate_ = var1;
         return this;
      }

      public BookInfo.BookDetails setPublisher(String var1) {
         this.hasPublisher = (boolean)1;
         this.publisher_ = var1;
         return this;
      }

      public BookInfo.BookDetails setSubject(int var1, BookInfo.BookSubject var2) {
         if(var2 == null) {
            throw new NullPointerException();
         } else {
            this.subject_.set(var1, var2);
            return this;
         }
      }

      public BookInfo.BookDetails setSubtitle(String var1) {
         this.hasSubtitle = (boolean)1;
         this.subtitle_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         Iterator var2 = this.getSubjectList().iterator();

         while(var2.hasNext()) {
            BookInfo.BookSubject var3 = (BookInfo.BookSubject)var2.next();
            var1.writeMessage(3, var3);
         }

         if(this.hasPublisher()) {
            String var4 = this.getPublisher();
            var1.writeString(4, var4);
         }

         if(this.hasPublicationDate()) {
            String var5 = this.getPublicationDate();
            var1.writeString(5, var5);
         }

         if(this.hasIsbn()) {
            String var6 = this.getIsbn();
            var1.writeString(6, var6);
         }

         if(this.hasNumberOfPages()) {
            int var7 = this.getNumberOfPages();
            var1.writeInt32(7, var7);
         }

         if(this.hasSubtitle()) {
            String var8 = this.getSubtitle();
            var1.writeString(8, var8);
         }

         Iterator var9 = this.getAuthorList().iterator();

         while(var9.hasNext()) {
            BookInfo.BookAuthor var10 = (BookInfo.BookAuthor)var9.next();
            var1.writeMessage(9, var10);
         }

      }
   }

   public static final class BookSubject extends MessageMicro {

      public static final int NAME_FIELD_NUMBER = 1;
      public static final int QUERY_FIELD_NUMBER = 2;
      private int cachedSize = -1;
      private boolean hasName;
      private boolean hasQuery;
      private String name_ = "";
      private String query_ = "";


      public BookSubject() {}

      public static BookInfo.BookSubject parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BookInfo.BookSubject()).mergeFrom(var0);
      }

      public static BookInfo.BookSubject parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BookInfo.BookSubject)((BookInfo.BookSubject)(new BookInfo.BookSubject()).mergeFrom(var0));
      }

      public final BookInfo.BookSubject clear() {
         BookInfo.BookSubject var1 = this.clearName();
         BookInfo.BookSubject var2 = this.clearQuery();
         this.cachedSize = -1;
         return this;
      }

      public BookInfo.BookSubject clearName() {
         this.hasName = (boolean)0;
         this.name_ = "";
         return this;
      }

      public BookInfo.BookSubject clearQuery() {
         this.hasQuery = (boolean)0;
         this.query_ = "";
         return this;
      }

      public int getCachedSize() {
         if(this.cachedSize < 0) {
            int var1 = this.getSerializedSize();
         }

         return this.cachedSize;
      }

      public String getName() {
         return this.name_;
      }

      public String getQuery() {
         return this.query_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasName()) {
            String var2 = this.getName();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasQuery()) {
            String var4 = this.getQuery();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public boolean hasQuery() {
         return this.hasQuery;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasName && this.hasQuery) {
            var1 = true;
         }

         return var1;
      }

      public BookInfo.BookSubject mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setName(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setQuery(var5);
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

      public BookInfo.BookSubject setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public BookInfo.BookSubject setQuery(String var1) {
         this.hasQuery = (boolean)1;
         this.query_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasName()) {
            String var2 = this.getName();
            var1.writeString(1, var2);
         }

         if(this.hasQuery()) {
            String var3 = this.getQuery();
            var1.writeString(2, var3);
         }
      }
   }

   public static final class BookAuthor extends MessageMicro {

      public static final int DEPRECATED_QUERY_FIELD_NUMBER = 2;
      public static final int DOCID_FIELD_NUMBER = 3;
      public static final int NAME_FIELD_NUMBER = 1;
      private int cachedSize = -1;
      private String deprecatedQuery_ = "";
      private Common.Docid docid_ = null;
      private boolean hasDeprecatedQuery;
      private boolean hasDocid;
      private boolean hasName;
      private String name_ = "";


      public BookAuthor() {}

      public static BookInfo.BookAuthor parseFrom(CodedInputStreamMicro var0) throws IOException {
         return (new BookInfo.BookAuthor()).mergeFrom(var0);
      }

      public static BookInfo.BookAuthor parseFrom(byte[] var0) throws InvalidProtocolBufferMicroException {
         return (BookInfo.BookAuthor)((BookInfo.BookAuthor)(new BookInfo.BookAuthor()).mergeFrom(var0));
      }

      public final BookInfo.BookAuthor clear() {
         BookInfo.BookAuthor var1 = this.clearName();
         BookInfo.BookAuthor var2 = this.clearDocid();
         BookInfo.BookAuthor var3 = this.clearDeprecatedQuery();
         this.cachedSize = -1;
         return this;
      }

      public BookInfo.BookAuthor clearDeprecatedQuery() {
         this.hasDeprecatedQuery = (boolean)0;
         this.deprecatedQuery_ = "";
         return this;
      }

      public BookInfo.BookAuthor clearDocid() {
         this.hasDocid = (boolean)0;
         this.docid_ = null;
         return this;
      }

      public BookInfo.BookAuthor clearName() {
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

      public String getDeprecatedQuery() {
         return this.deprecatedQuery_;
      }

      public Common.Docid getDocid() {
         return this.docid_;
      }

      public String getName() {
         return this.name_;
      }

      public int getSerializedSize() {
         int var1 = 0;
         if(this.hasName()) {
            String var2 = this.getName();
            int var3 = CodedOutputStreamMicro.computeStringSize(1, var2);
            var1 = 0 + var3;
         }

         if(this.hasDeprecatedQuery()) {
            String var4 = this.getDeprecatedQuery();
            int var5 = CodedOutputStreamMicro.computeStringSize(2, var4);
            var1 += var5;
         }

         if(this.hasDocid()) {
            Common.Docid var6 = this.getDocid();
            int var7 = CodedOutputStreamMicro.computeMessageSize(3, var6);
            var1 += var7;
         }

         this.cachedSize = var1;
         return var1;
      }

      public boolean hasDeprecatedQuery() {
         return this.hasDeprecatedQuery;
      }

      public boolean hasDocid() {
         return this.hasDocid;
      }

      public boolean hasName() {
         return this.hasName;
      }

      public final boolean isInitialized() {
         boolean var1 = false;
         if(this.hasName && this.hasDeprecatedQuery && (!this.hasDocid() || this.getDocid().isInitialized())) {
            var1 = true;
         }

         return var1;
      }

      public BookInfo.BookAuthor mergeFrom(CodedInputStreamMicro var1) throws IOException {
         while(true) {
            int var2 = var1.readTag();
            switch(var2) {
            case 10:
               String var3 = var1.readString();
               this.setName(var3);
               break;
            case 18:
               String var5 = var1.readString();
               this.setDeprecatedQuery(var5);
               break;
            case 26:
               Common.Docid var7 = new Common.Docid();
               var1.readMessage(var7);
               this.setDocid(var7);
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

      public BookInfo.BookAuthor setDeprecatedQuery(String var1) {
         this.hasDeprecatedQuery = (boolean)1;
         this.deprecatedQuery_ = var1;
         return this;
      }

      public BookInfo.BookAuthor setDocid(Common.Docid var1) {
         if(var1 == null) {
            throw new NullPointerException();
         } else {
            this.hasDocid = (boolean)1;
            this.docid_ = var1;
            return this;
         }
      }

      public BookInfo.BookAuthor setName(String var1) {
         this.hasName = (boolean)1;
         this.name_ = var1;
         return this;
      }

      public void writeTo(CodedOutputStreamMicro var1) throws IOException {
         if(this.hasName()) {
            String var2 = this.getName();
            var1.writeString(1, var2);
         }

         if(this.hasDeprecatedQuery()) {
            String var3 = this.getDeprecatedQuery();
            var1.writeString(2, var3);
         }

         if(this.hasDocid()) {
            Common.Docid var4 = this.getDocid();
            var1.writeMessage(3, var4);
         }
      }
   }
}
