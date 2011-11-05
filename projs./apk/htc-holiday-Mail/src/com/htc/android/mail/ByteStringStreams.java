package com.htc.android.mail;

import com.htc.android.mail.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ByteStringStreams {

   private int mFirstLine;
   private int mLastLine;
   private ArrayList<ByteString> mLines;


   public ByteStringStreams(ArrayList<ByteString> var1) {
      this.mLines = var1;
      this.mFirstLine = 0;
      int var2 = var1.size();
      this.mLastLine = var2;
   }

   public ByteStringStreams(ArrayList<ByteString> var1, int var2) {
      this.mLines = var1;
      this.mFirstLine = var2;
      int var3 = var1.size();
      this.mLastLine = var3;
   }

   public ByteStringStreams(ArrayList<ByteString> var1, int var2, int var3) {
      this.mLines = var1;
      this.mFirstLine = var2;
      this.mLastLine = var3;
   }

   public final InputStream getBase64InputStream() {
      ArrayList var1 = this.mLines;
      int var2 = this.mFirstLine;
      int var3 = this.mLastLine;
      return new ByteStringStreams.BSABase64InputStream(var1, var2, var3);
   }

   public final InputStream getInputStream() {
      ArrayList var1 = this.mLines;
      int var2 = this.mFirstLine;
      int var3 = this.mLastLine;
      return new ByteStringStreams.BSAInputStream(var1, var2, var3);
   }

   public final OutputStream getOutputStream() {
      ArrayList var1 = this.mLines;
      int var2 = this.mFirstLine;
      int var3 = this.mLastLine;
      return new ByteStringStreams.BSAOutputStream(var1, var2, var3);
   }

   static class BSABase64InputStream extends ByteStringStreams.BSAInputStream {

      protected BSABase64InputStream(ArrayList<ByteString> var1, int var2, int var3) {
         super(var1, var2, var3);
      }

      public final int read() throws IOException {
         while(true) {
            int var1 = this.mNumRead;
            int var2 = this.mTotalBytes;
            byte var12;
            if(var1 < var2) {
               int var3 = this.mReadOffset;
               int var4 = this.mLineLen;
               if(var3 == var4) {
                  this.nextLine();
               }

               byte[] var5 = this.mLine.mStorage;
               int var6 = this.mLine.mOffset;
               int var7 = this.mReadOffset;
               int var8 = var7 + 1;
               this.mReadOffset = var8;
               int var9 = var6 + var7;
               byte var10 = var5[var9];
               int var11 = this.mNumRead + 1;
               this.mNumRead = var11;
               if((var10 < 97 || var10 > 122) && (var10 < 65 || var10 > 90) && (var10 < 48 || var10 > 57) && var10 != 43 && var10 != 47 && var10 != 61) {
                  continue;
               }

               var12 = var10;
            } else {
               var12 = -1;
            }

            return var12;
         }
      }
   }

   static class BSAOutputStream extends OutputStream {

      private ByteString mLine;
      private int mLineLen;
      private int mLineNum = -1;
      private ArrayList<ByteString> mLines;
      private int mNumWritten;
      private int mTotalBytes;
      private int mWriteOffset;


      public BSAOutputStream(ArrayList<ByteString> var1, int var2, int var3) {
         this.mLines = var1;
         int var4 = var2 - 1;
         this.mLineNum = var4;

         for(int var5 = var2; var5 < var3; ++var5) {
            ByteString var6 = (ByteString)var1.get(var5);
            int var7 = this.mTotalBytes;
            int var8 = var6.length();
            int var9 = var7 + var8;
            this.mTotalBytes = var9;
         }

      }

      private final void nextLine() {
         int var1 = this.mLineNum + 1;
         this.mLineNum = var1;
         ArrayList var2 = this.mLines;
         int var3 = this.mLineNum;
         ByteString var4 = (ByteString)var2.get(var3);
         this.mLine = var4;
         int var5 = this.mLine.mStorage.length;
         this.mLineLen = var5;
         this.mWriteOffset = 0;
      }

      public final void close() {
         ByteString var1 = this.mLine;
         int var2 = this.mWriteOffset;
         var1.mLength = var2;
         int var3 = this.mLines.size();

         while(true) {
            var3 += -1;
            int var4 = this.mLineNum;
            if(var3 <= var4) {
               return;
            }

            this.mLines.remove(var3);
         }
      }

      public final void write(int var1) throws IOException {
         int var2 = this.mNumWritten;
         int var3 = this.mTotalBytes;
         if(var2 == var3) {
            throw new IOException("Buffer space exhausted");
         } else {
            int var4 = this.mWriteOffset;
            int var5 = this.mLineLen;
            if(var4 == var5) {
               this.nextLine();
            }

            byte[] var6 = this.mLine.mStorage;
            int var7 = this.mLine.mOffset;
            int var8 = this.mWriteOffset;
            int var9 = var7 + var8;
            byte var10 = (byte)var1;
            var6[var9] = var10;
            int var11 = this.mNumWritten + 1;
            this.mNumWritten = var11;
            int var12 = this.mWriteOffset + 1;
            this.mWriteOffset = var12;
         }
      }
   }

   static class BSAInputStream extends InputStream {

      protected ByteString mLine;
      protected int mLineLen;
      protected int mLineNum = -1;
      protected ArrayList<ByteString> mLines;
      protected int mNumRead;
      protected int mReadOffset;
      protected int mTotalBytes;


      protected BSAInputStream() {}

      public BSAInputStream(ArrayList<ByteString> var1, int var2, int var3) {
         this.mLines = var1;
         int var4 = var2 - 1;
         this.mLineNum = var4;

         for(int var5 = var2; var5 < var3; ++var5) {
            ByteString var6 = (ByteString)var1.get(var5);
            int var7 = this.mTotalBytes;
            int var8 = var6.length();
            int var9 = var7 + var8;
            this.mTotalBytes = var9;
         }

      }

      final void nextLine() {
         int var1 = this.mLineNum + 1;
         this.mLineNum = var1;
         ArrayList var2 = this.mLines;
         int var3 = this.mLineNum;
         ByteString var4 = (ByteString)var2.get(var3);
         this.mLine = var4;
         int var5 = this.mLine.length();
         this.mLineLen = var5;
         this.mReadOffset = 0;
      }

      public int read() throws IOException {
         int var1 = this.mNumRead;
         int var2 = this.mTotalBytes;
         byte var11;
         if(var1 < var2) {
            int var3 = this.mReadOffset;
            int var4 = this.mLineLen;
            if(var3 == var4) {
               this.nextLine();
            }

            int var5 = this.mNumRead + 1;
            this.mNumRead = var5;
            byte[] var6 = this.mLine.mStorage;
            int var7 = this.mLine.mOffset;
            int var8 = this.mReadOffset;
            int var9 = var8 + 1;
            this.mReadOffset = var9;
            int var10 = var7 + var8;
            var11 = var6[var10];
         } else {
            var11 = -1;
         }

         return var11;
      }

      public final int readBase64() throws IOException {
         while(true) {
            int var1 = this.mNumRead;
            int var2 = this.mTotalBytes;
            byte var12;
            if(var1 < var2) {
               int var3 = this.mReadOffset;
               int var4 = this.mLineLen;
               if(var3 == var4) {
                  this.nextLine();
               }

               byte[] var5 = this.mLine.mStorage;
               int var6 = this.mLine.mOffset;
               int var7 = this.mReadOffset;
               int var8 = var7 + 1;
               this.mReadOffset = var8;
               int var9 = var6 + var7;
               byte var10 = var5[var9];
               int var11 = this.mNumRead + 1;
               this.mNumRead = var11;
               if((var10 < 97 || var10 > 122) && (var10 < 65 || var10 > 90) && (var10 < 48 || var10 > 57) && var10 != 43 && var10 != 47 && var10 != 61) {
                  continue;
               }

               var12 = var10;
            } else {
               var12 = -1;
            }

            return var12;
         }
      }
   }
}
