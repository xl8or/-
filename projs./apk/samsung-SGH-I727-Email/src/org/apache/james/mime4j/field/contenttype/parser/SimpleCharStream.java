package org.apache.james.mime4j.field.contenttype.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class SimpleCharStream {

   public static final boolean staticFlag;
   int available;
   protected int[] bufcolumn;
   protected char[] buffer;
   protected int[] bufline;
   public int bufpos;
   int bufsize;
   protected int column;
   protected int inBuf;
   protected Reader inputStream;
   protected int line;
   protected int maxNextCharInd;
   protected boolean prevCharIsCR;
   protected boolean prevCharIsLF;
   protected int tabSize;
   int tokenBegin;


   public SimpleCharStream(InputStream var1) {
      this(var1, 1, 1, 4096);
   }

   public SimpleCharStream(InputStream var1, int var2, int var3) {
      this(var1, var2, var3, 4096);
   }

   public SimpleCharStream(InputStream var1, int var2, int var3, int var4) {
      InputStreamReader var5 = new InputStreamReader(var1);
      this((Reader)var5, var2, var3, var4);
   }

   public SimpleCharStream(InputStream var1, String var2) throws UnsupportedEncodingException {
      byte var6 = 1;
      this(var1, var2, 1, var6, 4096);
   }

   public SimpleCharStream(InputStream var1, String var2, int var3, int var4) throws UnsupportedEncodingException {
      this(var1, var2, var3, var4, 4096);
   }

   public SimpleCharStream(InputStream var1, String var2, int var3, int var4, int var5) throws UnsupportedEncodingException {
      InputStreamReader var6;
      if(var2 == null) {
         var6 = new InputStreamReader(var1);
      } else {
         var6 = new InputStreamReader(var1, var2);
      }

      this((Reader)var6, var3, var4, var5);
   }

   public SimpleCharStream(Reader var1) {
      this(var1, 1, 1, 4096);
   }

   public SimpleCharStream(Reader var1, int var2, int var3) {
      this(var1, var2, var3, 4096);
   }

   public SimpleCharStream(Reader var1, int var2, int var3, int var4) {
      this.bufpos = -1;
      this.column = 0;
      this.line = 1;
      this.prevCharIsCR = (boolean)0;
      this.prevCharIsLF = (boolean)0;
      this.maxNextCharInd = 0;
      this.inBuf = 0;
      this.tabSize = 8;
      this.inputStream = var1;
      this.line = var2;
      int var5 = var3 - 1;
      this.column = var5;
      this.bufsize = var4;
      this.available = var4;
      char[] var6 = new char[var4];
      this.buffer = var6;
      int[] var7 = new int[var4];
      this.bufline = var7;
      int[] var8 = new int[var4];
      this.bufcolumn = var8;
   }

   public char BeginToken() throws IOException {
      this.tokenBegin = -1;
      char var1 = this.readChar();
      int var2 = this.bufpos;
      this.tokenBegin = var2;
      return var1;
   }

   public void Done() {
      this.buffer = null;
      this.bufline = null;
      this.bufcolumn = null;
   }

   protected void ExpandBuff(boolean param1) {
      // $FF: Couldn't be decompiled
   }

   protected void FillBuff() throws IOException {
      int var1 = this.maxNextCharInd;
      int var2 = this.available;
      if(var1 == var2) {
         int var3 = this.available;
         int var4 = this.bufsize;
         if(var3 == var4) {
            if(this.tokenBegin > 2048) {
               this.maxNextCharInd = 0;
               this.bufpos = 0;
               int var5 = this.tokenBegin;
               this.available = var5;
            } else if(this.tokenBegin < 0) {
               this.maxNextCharInd = 0;
               this.bufpos = 0;
            } else {
               this.ExpandBuff((boolean)0);
            }
         } else {
            int var16 = this.available;
            int var17 = this.tokenBegin;
            if(var16 > var17) {
               int var18 = this.bufsize;
               this.available = var18;
            } else {
               int var19 = this.tokenBegin;
               int var20 = this.available;
               if(var19 - var20 < 2048) {
                  this.ExpandBuff((boolean)1);
               } else {
                  int var21 = this.tokenBegin;
                  this.available = var21;
               }
            }
         }
      }

      try {
         Reader var6 = this.inputStream;
         char[] var7 = this.buffer;
         int var8 = this.maxNextCharInd;
         int var9 = this.available;
         int var10 = this.maxNextCharInd;
         int var11 = var9 - var10;
         int var12 = var6.read(var7, var8, var11);
         if(var12 == -1) {
            this.inputStream.close();
            throw new IOException();
         } else {
            int var22 = this.maxNextCharInd + var12;
            this.maxNextCharInd = var22;
         }
      } catch (IOException var23) {
         int var14 = this.bufpos - 1;
         this.bufpos = var14;
         this.backup(0);
         if(this.tokenBegin == -1) {
            int var15 = this.bufpos;
            this.tokenBegin = var15;
         }

         throw var23;
      }
   }

   public String GetImage() {
      int var1 = this.bufpos;
      int var2 = this.tokenBegin;
      String var8;
      if(var1 >= var2) {
         char[] var3 = this.buffer;
         int var4 = this.tokenBegin;
         int var5 = this.bufpos;
         int var6 = this.tokenBegin;
         int var7 = var5 - var6 + 1;
         var8 = new String(var3, var4, var7);
      } else {
         StringBuilder var9 = new StringBuilder();
         char[] var10 = this.buffer;
         int var11 = this.tokenBegin;
         int var12 = this.bufsize;
         int var13 = this.tokenBegin;
         int var14 = var12 - var13;
         String var15 = new String(var10, var11, var14);
         StringBuilder var16 = var9.append(var15);
         char[] var17 = this.buffer;
         int var18 = this.bufpos + 1;
         String var19 = new String(var17, 0, var18);
         var8 = var16.append(var19).toString();
      }

      return var8;
   }

   public char[] GetSuffix(int var1) {
      char[] var2 = new char[var1];
      if(this.bufpos + 1 >= var1) {
         char[] var3 = this.buffer;
         int var4 = this.bufpos - var1 + 1;
         System.arraycopy(var3, var4, var2, 0, var1);
      } else {
         char[] var5 = this.buffer;
         int var6 = this.bufsize;
         int var7 = this.bufpos;
         int var8 = var1 - var7 - 1;
         int var9 = var6 - var8;
         int var10 = this.bufpos;
         int var11 = var1 - var10 - 1;
         System.arraycopy(var5, var9, var2, 0, var11);
         char[] var12 = this.buffer;
         int var13 = this.bufpos;
         int var14 = var1 - var13 - 1;
         int var15 = this.bufpos + 1;
         System.arraycopy(var12, 0, var2, var14, var15);
      }

      return var2;
   }

   public void ReInit(InputStream var1) {
      this.ReInit(var1, 1, 1, 4096);
   }

   public void ReInit(InputStream var1, int var2, int var3) {
      this.ReInit(var1, var2, var3, 4096);
   }

   public void ReInit(InputStream var1, int var2, int var3, int var4) {
      InputStreamReader var5 = new InputStreamReader(var1);
      this.ReInit((Reader)var5, var2, var3, var4);
   }

   public void ReInit(InputStream var1, String var2) throws UnsupportedEncodingException {
      byte var6 = 1;
      this.ReInit(var1, var2, 1, var6, 4096);
   }

   public void ReInit(InputStream var1, String var2, int var3, int var4) throws UnsupportedEncodingException {
      this.ReInit(var1, var2, var3, var4, 4096);
   }

   public void ReInit(InputStream var1, String var2, int var3, int var4, int var5) throws UnsupportedEncodingException {
      InputStreamReader var6;
      if(var2 == null) {
         var6 = new InputStreamReader(var1);
      } else {
         var6 = new InputStreamReader(var1, var2);
      }

      this.ReInit((Reader)var6, var3, var4, var5);
   }

   public void ReInit(Reader var1) {
      this.ReInit(var1, 1, 1, 4096);
   }

   public void ReInit(Reader var1, int var2, int var3) {
      this.ReInit(var1, var2, var3, 4096);
   }

   public void ReInit(Reader var1, int var2, int var3, int var4) {
      label11: {
         this.inputStream = var1;
         this.line = var2;
         int var5 = var3 - 1;
         this.column = var5;
         if(this.buffer != null) {
            int var6 = this.buffer.length;
            if(var4 == var6) {
               break label11;
            }
         }

         this.bufsize = var4;
         this.available = var4;
         char[] var7 = new char[var4];
         this.buffer = var7;
         int[] var8 = new int[var4];
         this.bufline = var8;
         int[] var9 = new int[var4];
         this.bufcolumn = var9;
      }

      this.prevCharIsCR = (boolean)0;
      this.prevCharIsLF = (boolean)0;
      this.maxNextCharInd = 0;
      this.inBuf = 0;
      this.tokenBegin = 0;
      this.bufpos = -1;
   }

   protected void UpdateLineColumn(char var1) {
      int var2 = this.column + 1;
      this.column = var2;
      if(this.prevCharIsLF) {
         this.prevCharIsLF = (boolean)0;
         int var3 = this.line;
         this.column = 1;
         int var4 = var3 + 1;
         this.line = var4;
      } else if(this.prevCharIsCR) {
         this.prevCharIsCR = (boolean)0;
         if(var1 == 10) {
            this.prevCharIsLF = (boolean)1;
         } else {
            int var11 = this.line;
            this.column = 1;
            int var12 = var11 + 1;
            this.line = var12;
         }
      }

      switch(var1) {
      case 9:
         int var13 = this.column - 1;
         this.column = var13;
         int var14 = this.column;
         int var15 = this.tabSize;
         int var16 = this.column;
         int var17 = this.tabSize;
         int var18 = var16 % var17;
         int var19 = var15 - var18;
         int var20 = var14 + var19;
         this.column = var20;
         break;
      case 10:
         this.prevCharIsLF = (boolean)1;
      case 11:
      case 12:
      default:
         break;
      case 13:
         this.prevCharIsCR = (boolean)1;
      }

      int[] var5 = this.bufline;
      int var6 = this.bufpos;
      int var7 = this.line;
      var5[var6] = var7;
      int[] var8 = this.bufcolumn;
      int var9 = this.bufpos;
      int var10 = this.column;
      var8[var9] = var10;
   }

   public void adjustBeginLineColumn(int var1, int var2) {
      int var3 = this.tokenBegin;
      int var4 = this.bufpos;
      int var5 = this.tokenBegin;
      int var10;
      if(var4 >= var5) {
         int var6 = this.bufpos;
         int var7 = this.tokenBegin;
         int var8 = var6 - var7;
         int var9 = this.inBuf;
         var10 = var8 + var9 + 1;
      } else {
         int var26 = this.bufsize;
         int var27 = this.tokenBegin;
         int var28 = var26 - var27;
         int var29 = this.bufpos;
         int var30 = var28 + var29 + 1;
         int var31 = this.inBuf;
         var10 = var30 + var31;
      }

      int var11 = 0;
      int var12 = 0;

      int var13;
      for(var13 = 0; var11 < var10; ++var11) {
         int[] var14 = this.bufline;
         int var15 = this.bufsize;
         var12 = var3 % var15;
         int var16 = var14[var12];
         int[] var17 = this.bufline;
         ++var3;
         int var18 = this.bufsize;
         int var19 = var3 % var18;
         int var20 = var17[var19];
         if(var16 != var20) {
            break;
         }

         this.bufline[var12] = var1;
         int var21 = this.bufcolumn[var19] + var13;
         int var22 = this.bufcolumn[var12];
         int var23 = var21 - var22;
         int[] var24 = this.bufcolumn;
         int var25 = var2 + var13;
         var24[var12] = var25;
         var13 = var23;
      }

      if(var11 < var10) {
         int[] var32 = this.bufline;
         int var33 = var1 + 1;
         var32[var12] = var1;
         int[] var34 = this.bufcolumn;
         int var35 = var2 + var13;
         var34[var12] = var35;
         int var36 = var11;

         while(true) {
            var11 = var36 + 1;
            if(var36 >= var10) {
               break;
            }

            int[] var37 = this.bufline;
            int var38 = this.bufsize;
            var12 = var3 % var38;
            int var39 = var37[var12];
            int[] var40 = this.bufline;
            ++var3;
            int var41 = this.bufsize;
            int var42 = var3 % var41;
            int var43 = var40[var42];
            if(var39 != var43) {
               int[] var44 = this.bufline;
               int var45 = var33 + 1;
               var44[var12] = var33;
               var36 = var11;
               var33 = var45;
            } else {
               this.bufline[var12] = var33;
               var36 = var11;
            }
         }
      }

      int var47 = this.bufline[var12];
      this.line = var47;
      int var48 = this.bufcolumn[var12];
      this.column = var48;
   }

   public void backup(int var1) {
      int var2 = this.inBuf + var1;
      this.inBuf = var2;
      int var3 = this.bufpos - var1;
      this.bufpos = var3;
      if(var3 < 0) {
         int var4 = this.bufpos;
         int var5 = this.bufsize;
         int var6 = var4 + var5;
         this.bufpos = var6;
      }
   }

   public int getBeginColumn() {
      int[] var1 = this.bufcolumn;
      int var2 = this.tokenBegin;
      return var1[var2];
   }

   public int getBeginLine() {
      int[] var1 = this.bufline;
      int var2 = this.tokenBegin;
      return var1[var2];
   }

   public int getColumn() {
      int[] var1 = this.bufcolumn;
      int var2 = this.bufpos;
      return var1[var2];
   }

   public int getEndColumn() {
      int[] var1 = this.bufcolumn;
      int var2 = this.bufpos;
      return var1[var2];
   }

   public int getEndLine() {
      int[] var1 = this.bufline;
      int var2 = this.bufpos;
      return var1[var2];
   }

   public int getLine() {
      int[] var1 = this.bufline;
      int var2 = this.bufpos;
      return var1[var2];
   }

   protected int getTabSize(int var1) {
      return this.tabSize;
   }

   public char readChar() throws IOException {
      char var6;
      if(this.inBuf > 0) {
         int var1 = this.inBuf - 1;
         this.inBuf = var1;
         int var2 = this.bufpos + 1;
         this.bufpos = var2;
         int var3 = this.bufsize;
         if(var2 == var3) {
            this.bufpos = 0;
         }

         char[] var4 = this.buffer;
         int var5 = this.bufpos;
         var6 = var4[var5];
      } else {
         int var7 = this.bufpos + 1;
         this.bufpos = var7;
         int var8 = this.maxNextCharInd;
         if(var7 >= var8) {
            this.FillBuff();
         }

         char[] var9 = this.buffer;
         int var10 = this.bufpos;
         char var11 = var9[var10];
         this.UpdateLineColumn(var11);
         var6 = var11;
      }

      return var6;
   }

   protected void setTabSize(int var1) {
      this.tabSize = var1;
   }
}
