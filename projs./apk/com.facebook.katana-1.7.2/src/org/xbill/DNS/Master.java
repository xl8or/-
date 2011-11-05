package org.xbill.DNS;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Generator;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.RelativeNameException;
import org.xbill.DNS.SOARecord;
import org.xbill.DNS.TTL;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.Type;

public class Master {

   private int currentDClass;
   private long currentTTL;
   private int currentType;
   private long defaultTTL;
   private File file;
   private Generator generator;
   private List generators;
   private Master included;
   private Record last;
   private boolean needSOATTL;
   private boolean noExpandGenerate;
   private Name origin;
   private Tokenizer st;


   Master(File var1, Name var2, long var3) throws IOException {
      this.last = null;
      this.included = null;
      if(var2 != null && !var2.isAbsolute()) {
         throw new RelativeNameException(var2);
      } else {
         this.file = var1;
         Tokenizer var5 = new Tokenizer(var1);
         this.st = var5;
         this.origin = var2;
         this.defaultTTL = var3;
      }
   }

   public Master(InputStream var1) {
      this(var1, (Name)null, 65535L);
   }

   public Master(InputStream var1, Name var2) {
      this(var1, var2, 65535L);
   }

   public Master(InputStream var1, Name var2, long var3) {
      this.last = null;
      this.included = null;
      if(var2 != null && !var2.isAbsolute()) {
         throw new RelativeNameException(var2);
      } else {
         Tokenizer var5 = new Tokenizer(var1);
         this.st = var5;
         this.origin = var2;
         this.defaultTTL = var3;
      }
   }

   public Master(String var1) throws IOException {
      File var2 = new File(var1);
      this(var2, (Name)null, 65535L);
   }

   public Master(String var1, Name var2) throws IOException {
      File var3 = new File(var1);
      this(var3, var2, 65535L);
   }

   public Master(String var1, Name var2, long var3) throws IOException {
      File var5 = new File(var1);
      this(var5, var2, var3);
   }

   private void endGenerate() throws IOException {
      this.st.getEOL();
      this.generator = null;
   }

   private Record nextGenerated() throws IOException {
      try {
         Record var1 = this.generator.nextRecord();
         return var1;
      } catch (Tokenizer.TokenizerException var12) {
         Tokenizer var3 = this.st;
         StringBuilder var4 = (new StringBuilder()).append("Parsing $GENERATE: ");
         String var5 = var12.getBaseMessage();
         String var6 = var4.append(var5).toString();
         throw var3.exception(var6);
      } catch (TextParseException var13) {
         Tokenizer var8 = this.st;
         StringBuilder var9 = (new StringBuilder()).append("Parsing $GENERATE: ");
         String var10 = var13.getMessage();
         String var11 = var9.append(var10).toString();
         throw var8.exception(var11);
      }
   }

   private Name parseName(String var1, Name var2) throws TextParseException {
      try {
         Name var3 = Name.fromString(var1, var2);
         return var3;
      } catch (TextParseException var7) {
         Tokenizer var5 = this.st;
         String var6 = var7.getMessage();
         throw var5.exception(var6);
      }
   }

   private void parseTTLClassAndType() throws IOException {
      boolean var1 = false;
      String var2 = this.st.getString();
      int var3 = DClass.value(var2);
      this.currentDClass = var3;
      if(var3 >= 0) {
         String var4 = this.st.getString();
         var1 = true;
      }

      this.currentTTL = 65535L;

      label45: {
         String var7;
         try {
            long var5 = TTL.parseTTL(var2);
            this.currentTTL = var5;
            var7 = this.st.getString();
         } catch (NumberFormatException var18) {
            if(this.defaultTTL >= 0L) {
               long var14 = this.defaultTTL;
               this.currentTTL = var14;
            } else if(this.last != null) {
               long var16 = this.last.getTTL();
               this.currentTTL = var16;
            }
            break label45;
         }

         var2 = var7;
      }

      String var9;
      label38: {
         if(!var1) {
            int var8 = DClass.value(var2);
            this.currentDClass = var8;
            if(var8 >= 0) {
               var9 = this.st.getString();
               break label38;
            }

            this.currentDClass = 1;
         }

         var9 = var2;
      }

      int var10 = Type.value(var9);
      this.currentType = var10;
      if(var10 < 0) {
         Tokenizer var11 = this.st;
         String var12 = "Invalid type \'" + var9 + "\'";
         throw var11.exception(var12);
      } else if(this.currentTTL < 0L) {
         if(this.currentType != 6) {
            throw this.st.exception("missing TTL");
         } else {
            this.needSOATTL = (boolean)1;
            this.currentTTL = 0L;
         }
      }
   }

   private long parseUInt32(String var1) {
      long var2;
      if(!Character.isDigit(var1.charAt(0))) {
         var2 = 65535L;
      } else {
         long var4;
         try {
            var4 = Long.parseLong(var1);
         } catch (NumberFormatException var7) {
            var2 = 65535L;
            return var2;
         }

         var2 = var4;
         if(var4 < 0L || var4 > 4294967295L) {
            var2 = 65535L;
         }
      }

      return var2;
   }

   private void startGenerate() throws IOException {
      String var1 = this.st.getIdentifier();
      int var2 = var1.indexOf("-");
      if(var2 < 0) {
         Tokenizer var3 = this.st;
         String var4 = "Invalid $GENERATE range specifier: " + var1;
         throw var3.exception(var4);
      } else {
         String var5 = var1.substring(0, var2);
         int var6 = var2 + 1;
         String var7 = var1.substring(var6);
         Object var8 = null;
         int var9 = var7.indexOf("/");
         String var13;
         String var14;
         if(var9 >= 0) {
            int var10 = var9 + 1;
            String var11 = var7.substring(var10);
            String var12 = var7.substring(0, var9);
            var13 = var11;
            var14 = var12;
         } else {
            var13 = (String)var8;
            var14 = var7;
         }

         long var15 = this.parseUInt32(var5);
         long var17 = this.parseUInt32(var14);
         long var19;
         if(var13 != null) {
            var19 = this.parseUInt32(var13);
         } else {
            var19 = 1L;
         }

         if(var15 >= 0L && var17 >= 0L && var15 <= var17 && var19 > 0L) {
            String var23 = this.st.getIdentifier();
            this.parseTTLClassAndType();
            if(!Generator.supportedType(this.currentType)) {
               Tokenizer var24 = this.st;
               StringBuilder var25 = (new StringBuilder()).append("$GENERATE does not support ");
               String var26 = Type.string(this.currentType);
               String var27 = var25.append(var26).append(" records").toString();
               throw var24.exception(var27);
            } else {
               String var28 = this.st.getIdentifier();
               this.st.getEOL();
               this.st.unget();
               int var29 = this.currentType;
               int var30 = this.currentDClass;
               long var31 = this.currentTTL;
               Name var33 = this.origin;
               Generator var34 = new Generator(var15, var17, var19, var23, var29, var30, var31, var28, var33);
               this.generator = var34;
               if(this.generators == null) {
                  ArrayList var35 = new ArrayList(1);
                  this.generators = var35;
               }

               List var36 = this.generators;
               Generator var37 = this.generator;
               var36.add(var37);
            }
         } else {
            Tokenizer var21 = this.st;
            String var22 = "Invalid $GENERATE range specifier: " + var1;
            throw var21.exception(var22);
         }
      }
   }

   public Record _nextRecord() throws IOException {
      Object var1 = this.included;
      if(var1 != null) {
         var1 = this.included.nextRecord();
         if(var1 != null) {
            return (Record)var1;
         }

         this.included = null;
      }

      if(this.generator != null) {
         if(this.nextGenerated() != null) {
            return (Record)var1;
         }

         this.endGenerate();
      }

      while(true) {
         Tokenizer.Token var2 = this.st.get((boolean)1, (boolean)0);
         Name var3;
         if(var2.type == 2) {
            var2 = this.st.get();
            if(var2.type == 1) {
               continue;
            }

            if(var2.type == 0) {
               var1 = null;
               break;
            }

            this.st.unget();
            if(this.last == null) {
               throw this.st.exception("no owner");
            }

            var3 = this.last.getName();
         } else {
            if(var2.type == 1) {
               continue;
            }

            if(var2.type == 0) {
               var1 = null;
               break;
            }

            if(var2.value.charAt(0) == 36) {
               String var13 = var2.value;
               if(var13.equalsIgnoreCase("$ORIGIN")) {
                  Tokenizer var14 = this.st;
                  Name var15 = Name.root;
                  Name var16 = var14.getName(var15);
                  this.origin = var16;
                  this.st.getEOL();
                  continue;
               }

               if(var13.equalsIgnoreCase("$TTL")) {
                  long var17 = this.st.getTTL();
                  this.defaultTTL = var17;
                  this.st.getEOL();
                  continue;
               }

               if(var13.equalsIgnoreCase("$INCLUDE")) {
                  String var19 = this.st.getString();
                  File var21;
                  if(this.file != null) {
                     String var20 = this.file.getParent();
                     var21 = new File(var20, var19);
                  } else {
                     var21 = new File(var19);
                  }

                  Name var22 = this.origin;
                  Tokenizer.Token var23 = this.st.get();
                  if(var23.isString()) {
                     String var24 = var23.value;
                     Name var25 = Name.root;
                     this.parseName(var24, var25);
                     this.st.getEOL();
                  }

                  long var27 = this.defaultTTL;
                  Master var29 = new Master(var21, var22, var27);
                  this.included = var29;
                  var1 = this.nextRecord();
                  break;
               }

               if(var13.equalsIgnoreCase("$GENERATE")) {
                  if(this.generator != null) {
                     throw new IllegalStateException("cannot nest $GENERATE");
                  }

                  this.startGenerate();
                  if(this.noExpandGenerate) {
                     this.endGenerate();
                     continue;
                  }

                  var1 = this.nextGenerated();
                  break;
               }

               Tokenizer var30 = this.st;
               String var31 = "Invalid directive: " + var13;
               throw var30.exception(var31);
            }

            String var32 = var2.value;
            Name var33 = this.origin;
            var3 = this.parseName(var32, var33);
            if(this.last != null) {
               Name var34 = this.last.getName();
               if(var3.equals(var34)) {
                  var3 = this.last.getName();
               }
            }
         }

         this.parseTTLClassAndType();
         int var4 = this.currentType;
         int var5 = this.currentDClass;
         long var6 = this.currentTTL;
         Tokenizer var8 = this.st;
         Name var9 = this.origin;
         Record var10 = Record.fromString(var3, var4, var5, var6, var8, var9);
         this.last = var10;
         if(this.needSOATTL) {
            long var11 = ((SOARecord)this.last).getMinimum();
            this.last.setTTL(var11);
            this.defaultTTL = var11;
            this.needSOATTL = (boolean)0;
         }

         var1 = this.last;
         break;
      }

      return (Record)var1;
   }

   public void expandGenerate(boolean var1) {
      byte var2;
      if(!var1) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      this.noExpandGenerate = (boolean)var2;
   }

   protected void finalize() {
      this.st.close();
   }

   public Iterator generators() {
      Iterator var1;
      if(this.generators != null) {
         var1 = Collections.unmodifiableList(this.generators).iterator();
      } else {
         var1 = Collections.EMPTY_LIST.iterator();
      }

      return var1;
   }

   public Record nextRecord() throws IOException {
      boolean var5 = false;

      Record var1;
      try {
         var5 = true;
         var1 = this._nextRecord();
         var5 = false;
      } finally {
         if(var5) {
            if(true) {
               this.st.close();
            }

         }
      }

      if(var1 == null) {
         this.st.close();
      }

      return var1;
   }
}
