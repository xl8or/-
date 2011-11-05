package com.android.exchange.adapter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.android.email.mail.DeviceAccessException;
import com.android.exchange.Eas;
import com.android.exchange.EasException;
import com.android.exchange.adapter.Tags;
import com.android.exchange.utility.FileLogger;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class Parser extends Observable {

   private static final int CHUNK_SIZE = 16384;
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
   private static String[][] tagTables = new String[150];
   private boolean capture = 0;
   private ArrayList<Integer> captureArray;
   BufferedOutputStream captureFile = null;
   private boolean capturebytes = 0;
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
   public byte[] opaqueData;
   public int page;
   public int startTag;
   private int[] startTagArray;
   public int tag;
   private String[] tagTable;
   public String text;
   long tid;
   public int type;


   public Parser(InputStream var1) throws IOException {
      String[] var2 = new String[32];
      this.nameArray = var2;
      int[] var3 = new int[128];
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
            long var9 = Thread.currentThread().getId();
            this.tid = var9;
            return;
         }

         String[] var7 = var4[var5];
         if(var7.length > 0) {
            tagTables[var5] = var7;
         }

         ++var5;
      }
   }

   private final int getNext(boolean var1, OutputStream var2, Observer var3) throws IOException {
      int var4 = this.endTag;
      if(this.type == 3) {
         int var5 = this.depth - 1;
         this.depth = var5;
      } else {
         this.endTag = Integer.MIN_VALUE;
      }

      int var12;
      if(this.noContent) {
         this.type = 3;
         this.noContent = (boolean)0;
         this.endTag = var4;
         if(this.logging) {
            try {
               int var6 = this.tagTable.length;
               int var7 = this.startTag - 5;
               if(var6 > var7) {
                  String[] var8 = this.tagTable;
                  int var9 = this.startTag - 5;
                  String var10 = var8[var9];
                  String var11 = "<" + var10 + " />";
                  this.log(var11);
               } else {
                  StringBuilder var13 = (new StringBuilder()).append("noContent : new Tag - check Tags.java!!! Added tag = ");
                  int var14 = this.startTag;
                  String var15 = var13.append(var14).toString();
                  this.log(var15);
               }
            } catch (Exception var69) {
               this.log("exception occurred while getting next TAG");
            }
         }

         var12 = this.type;
      } else {
         this.text = null;
         this.name = null;

         int var17;
         for(var17 = this.nextId(); var17 == 0; var17 = this.nextId()) {
            this.nextId = Integer.MIN_VALUE;
            int var18 = this.readByte();
            int var19 = var18 << 6;
            this.page = var19;
            String[] var20 = tagTables[var18];
            this.tagTable = var20;
         }

         this.nextId = Integer.MIN_VALUE;
         switch(var17) {
         case -1:
            this.type = 1;
            break;
         case 1:
            this.type = 3;
            if(this.logging) {
               String[] var35 = this.nameArray;
               int var36 = this.depth;
               String var37 = var35[var36];
               this.name = var37;
            }

            int[] var38 = this.startTagArray;
            int var39 = this.depth;
            int var40 = var38[var39];
            this.endTag = var40;
            this.startTag = var40;
            break;
         case 3:
            if(var2 == null) {
               this.type = 4;
               if(var1) {
                  int var41 = this.readInlineInt();
                  this.num = var41;
               } else {
                  String var53 = this.readInlineString();
                  this.text = var53;
               }
            } else {
               this.text = null;
               int var54 = this.readInlineStream(var2, var3);
               this.num = var54;
            }

            if(this.logging) {
               try {
                  int var42 = this.tagTable.length;
                  int var43 = this.startTag - 5;
                  if(var42 > var43) {
                     String[] var44 = this.tagTable;
                     int var45 = this.startTag - 5;
                     String var46 = var44[var45];
                     this.name = var46;
                     StringBuilder var47 = new StringBuilder();
                     String var48 = this.name;
                     StringBuilder var49 = var47.append(var48).append(": ");
                     String var50;
                     if(var1) {
                        var50 = Integer.toString(this.num);
                     } else {
                        var50 = this.text;
                     }

                     String var51 = var49.append(var50).toString();
                     this.log(var51);
                  } else {
                     StringBuilder var55 = (new StringBuilder()).append("STR_I : new Tag - check Tags.java!!! Added tag = ");
                     int var56 = this.startTag;
                     String var57 = var55.append(var56).toString();
                     this.log(var57);
                  }
               } catch (Exception var67) {
                  this.log("exception occurred while getting next TAG");
               }
            }
            break;
         case 195:
            int var58 = this.readInt();

            byte[] var59;
            int var62;
            for(var59 = new byte[var58]; var58 > 0; var58 -= var62) {
               InputStream var60 = this.in;
               int var61 = var59.length - var58;
               var62 = var60.read(var59, var61, var58);
            }

            this.type = 4;
            this.opaqueData = var59;
            break;
         default:
            this.type = 2;
            int var21 = var17 & 63;
            this.startTag = var21;
            byte var22;
            if((var17 & 64) == 0) {
               var22 = 1;
            } else {
               var22 = 0;
            }

            this.noContent = (boolean)var22;
            int var23 = this.depth + 1;
            this.depth = var23;
            if(this.logging) {
               try {
                  int var24 = this.tagTable.length;
                  int var25 = this.startTag - 5;
                  if(var24 > var25) {
                     String[] var26 = this.tagTable;
                     int var27 = this.startTag - 5;
                     String var28 = var26[var27];
                     this.name = var28;
                     String[] var29 = this.nameArray;
                     int var30 = this.depth;
                     String var31 = this.name;
                     var29[var30] = var31;
                  } else {
                     StringBuilder var63 = (new StringBuilder()).append("default : new Tag - check Tags.java!!! Added tag = ");
                     int var64 = this.startTag;
                     String var65 = var63.append(var64).toString();
                     this.log(var65);
                  }
               } catch (Exception var68) {
                  this.log("exception occurred while getting next TAG");
               }
            }

            int[] var32 = this.startTagArray;
            int var33 = this.depth;
            int var34 = this.startTag;
            var32[var33] = var34;
         }

         var12 = this.type;
      }

      return var12;
   }

   private final int getNextStream(boolean var1) throws IOException {
      int var2 = this.endTag;
      if(this.type == 3) {
         int var3 = this.depth - 1;
         this.depth = var3;
      } else {
         this.endTag = Integer.MIN_VALUE;
      }

      int var10;
      if(this.noContent) {
         this.type = 3;
         this.noContent = (boolean)0;
         this.endTag = var2;
         if(this.logging) {
            try {
               int var4 = this.tagTable.length;
               int var5 = this.startTag - 5;
               if(var4 > var5) {
                  String[] var6 = this.tagTable;
                  int var7 = this.startTag - 5;
                  String var8 = var6[var7];
                  String var9 = "<" + var8 + " />";
                  this.log(var9);
               } else {
                  StringBuilder var11 = (new StringBuilder()).append("getNextStream() : new Tag - check Tags.java!!! Added tag = ");
                  int var12 = this.startTag;
                  String var13 = var11.append(var12).toString();
                  this.log(var13);
               }
            } catch (Exception var37) {
               this.log("exception occurred while getting next TAG");
            }
         }

         var10 = this.type;
      } else {
         this.text = null;
         this.name = null;

         int var15;
         for(var15 = this.nextId(); var15 == 0; var15 = this.nextId()) {
            this.nextId = Integer.MIN_VALUE;
            int var16 = this.readByte();
            int var17 = var16 << 6;
            this.page = var17;
            String[] var18 = tagTables[var16];
            this.tagTable = var18;
         }

         this.nextId = Integer.MIN_VALUE;
         switch(var15) {
         case -1:
            this.type = 1;
            break;
         case 1:
            this.type = 3;
            if(this.logging) {
               String[] var25 = this.nameArray;
               int var26 = this.depth;
               String var27 = var25[var26];
               this.name = var27;
            }

            int[] var28 = this.startTagArray;
            int var29 = this.depth;
            int var30 = var28[var29];
            this.endTag = var30;
            this.startTag = var30;
            break;
         case 3:
            this.type = 4;
            this.readInlineBytes();
            break;
         case 195:
            int var31 = this.readInt();

            int var35;
            byte[] var32;
            for(var32 = new byte[var31]; var31 > 0; var31 -= var35) {
               InputStream var33 = this.in;
               int var34 = var32.length - var31;
               var35 = var33.read(var32, var34, var31);
            }

            this.type = 4;
            String var36 = new String(var32, "UTF-8");
            this.text = var36;
            break;
         default:
            this.type = 2;
            int var19 = var15 & 63;
            this.startTag = var19;
            byte var20;
            if((var15 & 64) == 0) {
               var20 = 1;
            } else {
               var20 = 0;
            }

            this.noContent = (boolean)var20;
            int var21 = this.depth + 1;
            this.depth = var21;
            int[] var22 = this.startTagArray;
            int var23 = this.depth;
            int var24 = this.startTag;
            var22[var23] = var24;
         }

         var10 = this.type;
      }

      return var10;
   }

   private int nextId() throws IOException {
      if(this.nextId == Integer.MIN_VALUE) {
         int var1 = this.read();
         this.nextId = var1;
      }

      return this.nextId;
   }

   private int read() throws IOException {
      int var5;
      if(this.in != null) {
         int var1 = this.in.read();
         if(this.capture) {
            ArrayList var2 = this.captureArray;
            Integer var3 = Integer.valueOf(var1);
            var2.add(var3);
         }

         var5 = var1;
      } else {
         var5 = -1;
      }

      return var5;
   }

   private int readByte() throws IOException {
      int var1 = this.read();
      if(var1 == -1) {
         throw new Parser.EofException();
      } else {
         return var1;
      }
   }

   private void readInlineBytes() throws IOException {
      int var1;
      do {
         var1 = this.readStreamIntoFile();
         if(var1 == 0) {
            return;
         }
      } while(var1 != -1);

      throw new Parser.EofException();
   }

   private int readInlineInt() throws IOException {
      int var1 = 0;
      byte var2 = 1;
      boolean var3 = true;

      while(true) {
         while(true) {
            int var4 = this.readByte();
            if(var4 == 0) {
               return var2 * var1;
            }

            if(!var3 || var4 != 45) {
               var3 = false;
               if(var4 < 48 || var4 > 57) {
                  throw new IOException("Non integer");
               }

               int var5 = var1 * 10;
               int var6 = var4 - 48;
               var1 = var5 + var6;
            } else {
               var2 = -1;
               var3 = false;
            }
         }
      }
   }

   private int readInlineStream(OutputStream var1, Observer var2) throws IOException {
      long var3 = 0L;
      long var12;
      if(var1 != null) {
         ByteArrayOutputStream var5 = new ByteArrayOutputStream(256);
         if(var2 != null) {
            this.addObserver(var2);
         }

         int var6 = 0;
         int var7 = 0;

         while(true) {
            boolean var26 = false;

            try {
               var26 = true;
               int var8 = this.read();
               if(var8 == -1 || var8 == 0) {
                  if(var6 > 0) {
                     byte[] var9 = Base64.decode(var5.toByteArray(), 0);
                     var1.write(var9);
                     var26 = false;
                  } else {
                     var26 = false;
                  }
                  break;
               }

               var5.write(var8);
               ++var6;
               if(var6 == 256) {
                  byte[] var15 = Base64.decode(var5.toByteArray(), 0);
                  var1.write(var15);
                  var7 += var6;
                  long var16 = (long)var6;
                  var3 += var16;
                  var6 = 0;
                  if(var7 >= 16384) {
                     var7 -= 16384;
                     this.setChanged();
                     Long var18 = new Long(var3);
                     this.notifyObservers(var18);
                  }

                  var5.reset();
               }
            } finally {
               if(var26) {
                  long var20 = (long)var6;
                  long var22 = var3 + var20;
                  this.setChanged();
                  Long var24 = new Long(var22);
                  this.notifyObservers(var24);
                  if(var2 != null) {
                     this.deleteObserver(var2);
                  }

               }
            }
         }

         long var10 = (long)var6;
         var12 = var3 + var10;
         this.setChanged();
         Long var14 = new Long(var12);
         this.notifyObservers(var14);
         if(var2 != null) {
            this.deleteObserver(var2);
         }
      } else {
         var12 = 0L;
      }

      return (int)var12;
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

   private int readStreamIntoFile() throws IOException {
      int var1 = this.in.read();
      if(this.capturebytes) {
         this.captureFile.write(var1);
      }

      return var1;
   }

   public void captureBytesOff() {
      try {
         this.captureFile.flush();
         this.captureFile.close();
         this.capturebytes = (boolean)0;
      } catch (IOException var2) {
         this.capturebytes = (boolean)0;
      }
   }

   public void captureBytesOn(Context var1, String var2) {
      this.capturebytes = (boolean)1;

      try {
         FileOutputStream var3 = var1.openFileOutput(var2, 2);
         BufferedOutputStream var4 = new BufferedOutputStream(var3);
         this.captureFile = var4;
      } catch (FileNotFoundException var5) {
         var5.printStackTrace();
      }
   }

   public void captureOff(Context param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void captureOn() {
      this.capture = (boolean)1;
      ArrayList var1 = new ArrayList();
      this.captureArray = var1;
   }

   public String crypt(byte[] var1) {
      if(var1 != null && var1.length != 0) {
         String var2 = new String();
         int var3 = 0;

         while(true) {
            int var4 = var1.length;
            if(var3 >= var4) {
               return var2;
            }

            if(var1[var3] >= 0) {
               if((var1[var3] & 255) < 16) {
                  StringBuilder var5 = (new StringBuilder()).append(var2).append("0");
                  String var6 = Integer.toHexString(var1[var3] & 255);
                  var2 = var5.append(var6).toString();
               } else {
                  StringBuilder var7 = (new StringBuilder()).append(var2);
                  String var8 = Integer.toHexString(var1[var3]);
                  var2 = var7.append(var8).toString();
               }
            } else {
               StringBuilder var9 = (new StringBuilder()).append(var2);
               String var10 = Integer.toHexString(var1[var3] * -1 | 128);
               var2 = var9.append(var10).toString();
            }

            ++var3;
         }
      } else {
         throw new IllegalArgumentException("Byte array to encript cannot be null or zero length");
      }
   }

   public int getValue(OutputStream var1, Observer var2) throws IOException {
      this.getNext((boolean)0, var1, var2);
      if(this.type == 3) {
         ;
      }

      int var4 = this.num;
      int var5 = this.getNext((boolean)0, (OutputStream)null, (Observer)null);
      if(this.type != 3) {
         throw new IOException("No END found!");
      } else {
         int var6 = this.startTag;
         this.endTag = var6;
         return var4;
      }
   }

   public String getValue() throws IOException {
      int var1 = this.getNext((boolean)0, (OutputStream)null, (Observer)null);
      String var9;
      if(this.type == 3) {
         if(this.logging) {
            try {
               int var2 = this.tagTable.length;
               int var3 = this.startTag - 5;
               if(var2 > var3) {
                  StringBuilder var4 = (new StringBuilder()).append("No value for tag: ");
                  String[] var5 = this.tagTable;
                  int var6 = this.startTag - 5;
                  String var7 = var5[var6];
                  String var8 = var4.append(var7).toString();
                  this.log(var8);
               } else {
                  StringBuilder var10 = (new StringBuilder()).append("getValue() : new Tag - check Tags.java!!! Added tag = ");
                  int var11 = this.startTag;
                  String var12 = var10.append(var11).toString();
                  this.log(var12);
               }
            } catch (Exception var17) {
               this.log("exception occurred while getting next TAG");
            }
         }

         var9 = "";
      } else {
         String var14 = this.text;
         int var15 = this.getNext((boolean)0, (OutputStream)null, (Observer)null);
         if(this.type != 3) {
            throw new IOException("No END found!");
         }

         int var16 = this.startTag;
         this.endTag = var16;
         var9 = var14;
      }

      return var9;
   }

   public int getValueInt() throws IOException {
      int var1 = this.getNext((boolean)1, (OutputStream)null, (Observer)null);
      int var2;
      if(this.type == 3) {
         var2 = 0;
      } else {
         int var3 = this.num;
         int var4 = this.getNext((boolean)0, (OutputStream)null, (Observer)null);
         if(this.type != 3) {
            throw new IOException("No END found!");
         }

         int var5 = this.startTag;
         this.endTag = var5;
         var2 = var3;
      }

      return var2;
   }

   public int getValueIntForReminder() throws IOException {
      int var1 = this.getNext((boolean)1, (OutputStream)null, (Observer)null);
      int var2;
      if(this.type == 3) {
         var2 = -1;
      } else {
         int var3 = this.num;
         int var4 = this.getNext((boolean)0, (OutputStream)null, (Observer)null);
         if(this.type != 3) {
            throw new IOException("No END found!");
         }

         int var5 = this.startTag;
         this.endTag = var5;
         var2 = var3;
      }

      return var2;
   }

   public byte[] getValueOpaque() throws IOException {
      // $FF: Couldn't be decompiled
   }

   public boolean hasContent() {
      boolean var1;
      if(!this.noContent) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void log(String var1) {
      int var2 = var1.indexOf(10);
      if(var2 > 0) {
         var1 = var1.substring(0, var2);
      }

      StringBuilder var3 = new StringBuilder();
      String var4 = this.logTag;
      StringBuilder var5 = var3.append(var4).append("<");
      long var6 = this.tid;
      int var8 = Log.v(var5.append(var6).append(">").toString(), var1);
      if(Eas.FILE_LOG) {
         StringBuilder var9 = new StringBuilder();
         String var10 = this.logTag;
         StringBuilder var11 = var9.append(var10).append("<");
         long var12 = this.tid;
         FileLogger.log(var11.append(var12).append(">").toString(), var1);
      }
   }

   public int nextTag(int var1) throws IOException {
      int var2 = var1 & 63;
      this.endTag = var2;

      int var6;
      while(true) {
         if(this.getNext((boolean)0, (OutputStream)null, (Observer)null) != 1) {
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
      int var1 = this.getNext((boolean)0, (OutputStream)null, (Observer)null);
      return this.type;
   }

   public boolean parse() throws IOException, EasException, DeviceAccessException {
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
         if(this.getNext((boolean)0, (OutputStream)null, (Observer)null) == 1) {
            throw new Parser.EofException();
         }
      } while(this.type != 3 || this.startTag != var1);

   }

   public boolean skipTag(boolean var1, Context var2, String var3) throws IOException {
      int var4 = this.startTag;
      int var5 = this.depth;
      int var6 = 0;
      if(var1) {
         this.captureBytesOn(var2, var3);
      }

      do {
         byte var7 = 0;
         boolean var14 = false;

         label141: {
            int var8;
            label140: {
               try {
                  var14 = true;
                  var8 = this.getNextStream((boolean)var7);
                  var14 = false;
                  break label140;
               } catch (IOException var15) {
                  var15.printStackTrace();
                  var14 = false;
               } finally {
                  if(var14) {
                     if(this.type == 3 && this.startTag == var4 && this.depth == var5 && var1) {
                        this.captureBytesOff();
                     }

                  }
               }

               if(this.type == 3 && this.startTag == var4 && this.depth == var5 && var1) {
                  this.captureBytesOff();
               }
               break label141;
            }

            var6 = var8;
            if(this.type == 3 && this.startTag == var4 && this.depth == var5 && var1) {
               this.captureBytesOff();
            }
         }

         if(this.type == 3 && this.startTag == var4 && this.depth == var5) {
            boolean var9;
            if(var1) {
               var9 = true;
            } else {
               var9 = false;
            }

            return var9;
         }
      } while(var6 != 1);

      throw new Parser.EofException();
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
