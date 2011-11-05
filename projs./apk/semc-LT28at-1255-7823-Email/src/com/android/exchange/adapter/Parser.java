package com.android.exchange.adapter;

import android.content.Context;
import android.util.Log;
import com.android.exchange.Eas;
import com.android.exchange.EasException;
import com.android.exchange.adapter.Tags;
import com.android.exchange.utility.FileLogger;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class Parser {

   public static final int DONE = 1;
   public static final int END = 3;
   public static final int END_DOCUMENT = 3;
   private static final int EOF_BYTE = 255;
   private static final int NOT_ENDED = Integer.MIN_VALUE;
   private static final int NOT_FETCHED = Integer.MIN_VALUE;
   public static final int START = 2;
   public static final int START_DOCUMENT = 0;
   private static final int TAG_BASE = 5;
   public static final int TEXT = 4;
   private static String[][] tagTables = new String[24];
   private boolean capture = 0;
   private ArrayList<Integer> captureArray;
   private int depth;
   public int endTag;
   private InputStream in;
   private String logTag = "EAS Parser";
   private boolean logging = 0;
   public String name;
   private String[] nameArray;
   private int nextId = Integer.MIN_VALUE;
   private boolean noContent;
   public int num;
   public int page;
   public int startTag;
   private int[] startTagArray;
   public int tag;
   private String[] tagTable;
   public String text;
   public int type;


   public Parser(InputStream var1) throws IOException {
      String[] var2 = new String[32];
      this.nameArray = var2;
      int[] var3 = new int[32];
      this.startTagArray = var3;
      this.endTag = Integer.MIN_VALUE;
      String[][] var4 = Tags.pages;
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 >= var6) {
            this.setInput(var1);
            boolean var8 = Eas.PARSER_LOG;
            this.logging = var8;
            return;
         }

         String[] var7 = var4[var5];
         if(var7.length > 0) {
            tagTables[var5] = var7;
         }

         ++var5;
      }
   }

   private final int getNext(boolean var1) throws IOException {
      String var2 = null;
      int var3 = this.endTag;
      if(this.type == 3) {
         int var4 = this.depth - 1;
         this.depth = var4;
      } else {
         this.endTag = Integer.MIN_VALUE;
      }

      int var5;
      if(this.noContent) {
         this.type = 3;
         this.noContent = (boolean)0;
         this.endTag = var3;
         var5 = this.type;
      } else {
         this.text = null;
         this.name = null;

         int var6;
         for(var6 = this.nextId(); var6 == 0; var6 = this.nextId()) {
            this.nextId = Integer.MIN_VALUE;
            int var7 = this.readByte();
            int var8 = var7 << 6;
            this.page = var8;
            String[] var9 = tagTables[var7];
            this.tagTable = var9;
         }

         this.nextId = Integer.MIN_VALUE;
         switch(var6) {
         case -1:
            this.type = 1;
            break;
         case 0:
         case 2:
         default:
            this.type = 2;
            int var10 = var6 & 63;
            this.startTag = var10;
            byte var11;
            if((var6 & 64) == 0) {
               var11 = 1;
            } else {
               var11 = 0;
            }

            this.noContent = (boolean)var11;
            int var12 = this.depth + 1;
            this.depth = var12;
            if(this.logging) {
               String[] var13 = this.tagTable;
               int var14 = this.startTag - 5;
               String var15 = var13[var14];
               this.name = var15;
               String[] var16 = this.nameArray;
               int var17 = this.depth;
               String var18 = this.name;
               var16[var17] = var18;
            }

            int[] var19 = this.startTagArray;
            int var20 = this.depth;
            int var21 = this.startTag;
            var19[var20] = var21;
            break;
         case 1:
            this.type = 3;
            if(this.logging) {
               String[] var22 = this.nameArray;
               int var23 = this.depth;
               String var24 = var22[var23];
               this.name = var24;
            }

            int[] var25 = this.startTagArray;
            int var26 = this.depth;
            int var27 = var25[var26];
            this.endTag = var27;
            this.startTag = var27;
            break;
         case 3:
            this.type = 4;
            if(var1) {
               int var28 = this.readInlineInt();
               this.num = var28;
            } else {
               String var36 = this.readInlineString();
               this.text = var36;
            }

            if(this.logging) {
               String[] var29 = this.tagTable;
               int var30 = this.startTag - 5;
               String var31 = var29[var30];
               this.name = var31;
               StringBuilder var32 = new StringBuilder();
               String var33 = this.name;
               StringBuilder var34 = var32.append(var33).append(": ");
               if(var1) {
                  var2 = Integer.toString(this.num);
               } else {
                  var2 = this.text;
               }

               String var35 = var34.append(var2).toString();
               this.log(var35);
            }
         }

         var5 = this.type;
      }

      return var5;
   }

   private int nextId() throws IOException {
      if(this.nextId == Integer.MIN_VALUE) {
         int var1 = this.read();
         this.nextId = var1;
      }

      return this.nextId;
   }

   private int read() throws IOException {
      int var1 = this.in.read();
      if(this.capture) {
         ArrayList var2 = this.captureArray;
         Integer var3 = Integer.valueOf(var1);
         var2.add(var3);
      }

      return var1;
   }

   private int readByte() throws IOException {
      int var1 = this.read();
      if(var1 == -1) {
         throw new Parser.EofException();
      } else {
         return var1;
      }
   }

   private int readInlineInt() throws IOException {
      int var1 = 0;

      while(true) {
         int var2 = this.readByte();
         if(var2 == 0) {
            return var1;
         }

         if(var2 < 48 || var2 > 57) {
            throw new IOException("Non integer");
         }

         int var3 = var1 * 10;
         int var4 = var2 - 48;
         var1 = var3 + var4;
      }
   }

   private String readInlineString() throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream(256);

      while(true) {
         int var2 = this.read();
         if(var2 == 0) {
            var1.flush();
            String var3 = var1.toString("UTF-8");
            var1.close();
            return var3;
         }

         if(var2 == -1) {
            throw new Parser.EofException();
         }

         var1.write(var2);
      }
   }

   private int readInt() throws IOException {
      int var1 = 0;

      int var2;
      do {
         var2 = this.readByte();
         int var3 = var1 << 7;
         int var4 = var2 & 127;
         var1 = var3 | var4;
      } while((var2 & 128) != 0);

      return var1;
   }

   public void captureOff(Context var1, String var2) {
      try {
         FileOutputStream var3 = var1.openFileOutput(var2, 2);
         byte[] var4 = this.captureArray.toString().getBytes();
         var3.write(var4);
         var3.close();
      } catch (FileNotFoundException var7) {
         ;
      } catch (IOException var8) {
         ;
      }
   }

   public void captureOn() {
      this.capture = (boolean)1;
      ArrayList var1 = new ArrayList();
      this.captureArray = var1;
   }

   public String getValue() throws IOException {
      int var1 = this.getNext((boolean)0);
      String var7;
      if(this.type == 3) {
         if(this.logging) {
            StringBuilder var2 = (new StringBuilder()).append("No value for tag: ");
            String[] var3 = this.tagTable;
            int var4 = this.startTag - 5;
            String var5 = var3[var4];
            String var6 = var2.append(var5).toString();
            this.log(var6);
         }

         var7 = "";
      } else {
         String var8 = this.text;
         int var9 = this.getNext((boolean)0);
         if(this.type != 3) {
            throw new IOException("No END found!");
         }

         int var10 = this.startTag;
         this.endTag = var10;
         var7 = var8;
      }

      return var7;
   }

   public int getValueInt() throws IOException {
      int var1 = this.getNext((boolean)1);
      int var2;
      if(this.type == 3) {
         var2 = 0;
      } else {
         int var3 = this.num;
         int var4 = this.getNext((boolean)0);
         if(this.type != 3) {
            throw new IOException("No END found!");
         }

         int var5 = this.startTag;
         this.endTag = var5;
         var2 = var3;
      }

      return var2;
   }

   void log(String var1) {
      int var2 = var1.indexOf(10);
      if(var2 > 0) {
         var1 = var1.substring(0, var2);
      }

      Log.v(this.logTag, var1);
      if(Eas.FILE_LOG) {
         FileLogger.log(this.logTag, var1);
      }
   }

   public int nextTag(int var1) throws IOException {
      int var2 = var1 & 63;
      this.endTag = var2;

      int var6;
      while(true) {
         if(this.getNext((boolean)0) != 1) {
            if(this.type == 2) {
               int var3 = this.page;
               int var4 = this.startTag;
               int var5 = var3 | var4;
               this.tag = var5;
               var6 = this.tag;
               break;
            }

            if(this.type != 3) {
               continue;
            }

            int var7 = this.startTag;
            int var8 = this.endTag;
            if(var7 != var8) {
               continue;
            }

            var6 = 3;
            break;
         }

         if(this.endTag != 0) {
            throw new Parser.EodException();
         }

         var6 = 3;
         break;
      }

      return var6;
   }

   public int nextToken() throws IOException {
      int var1 = this.getNext((boolean)0);
      return this.type;
   }

   public boolean parse() throws IOException, EasException {
      return false;
   }

   void resetInput(InputStream var1) {
      this.in = var1;
   }

   public void setDebug(boolean var1) {
      this.logging = var1;
   }

   public void setInput(InputStream var1) throws IOException {
      this.in = var1;
      int var2 = this.readByte();
      int var3 = this.readInt();
      int var4 = this.readInt();
      int var5 = this.readInt();
      String[] var6 = tagTables[0];
      this.tagTable = var6;
   }

   public void setLoggingTag(String var1) {
      this.logTag = var1;
   }

   public void skipTag() throws IOException {
      int var1 = this.startTag;

      do {
         if(this.getNext((boolean)0) == 1) {
            throw new Parser.EofException();
         }
      } while(this.type != 3 || this.startTag != var1);

   }

   public class EasParserException extends IOException {

      private static final long serialVersionUID = 1L;


      EasParserException() {
         super("WBXML format error");
      }

      EasParserException(String var2) {
         super(var2);
      }
   }

   public class EodException extends IOException {

      private static final long serialVersionUID = 1L;


      public EodException() {}
   }

   public class EofException extends IOException {

      private static final long serialVersionUID = 1L;


      public EofException() {}
   }
}
