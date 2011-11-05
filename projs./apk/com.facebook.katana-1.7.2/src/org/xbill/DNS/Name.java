package org.xbill.DNS;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNAMERecord;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.NameTooLongException;
import org.xbill.DNS.Options;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.WireParseException;

public class Name implements Comparable, Serializable {

   private static final int LABEL_COMPRESSION = 192;
   private static final int LABEL_MASK = 192;
   private static final int LABEL_NORMAL = 0;
   private static final int MAXLABEL = 63;
   private static final int MAXLABELS = 128;
   private static final int MAXNAME = 255;
   private static final int MAXOFFSETS = 7;
   private static final DecimalFormat byteFormat;
   public static final Name empty;
   private static final byte[] emptyLabel;
   private static final byte[] lowercase;
   public static final Name root;
   private static final long serialVersionUID = -7257019940971525644L;
   private static final Name wild;
   private static final byte[] wildLabel;
   private int hashcode;
   private byte[] name;
   private long offsets;


   static {
      byte[] var0 = new byte[]{(byte)0};
      emptyLabel = var0;
      wildLabel = new byte[]{(byte)1, (byte)42};
      byteFormat = new DecimalFormat();
      lowercase = new byte[256];
      byteFormat.setMinimumIntegerDigits(3);
      int var1 = 0;

      while(true) {
         int var2 = lowercase.length;
         if(var1 >= var2) {
            root = new Name();
            Name var7 = root;
            byte[] var8 = emptyLabel;
            var7.appendSafe(var8, 0, 1);
            empty = new Name();
            Name var9 = empty;
            byte[] var10 = new byte[0];
            var9.name = var10;
            wild = new Name();
            Name var11 = wild;
            byte[] var12 = wildLabel;
            var11.appendSafe(var12, 0, 1);
            return;
         }

         if(var1 >= 65 && var1 <= 90) {
            byte[] var5 = lowercase;
            byte var6 = (byte)(var1 - 65 + 97);
            var5[var1] = var6;
         } else {
            byte[] var3 = lowercase;
            byte var4 = (byte)var1;
            var3[var1] = var4;
         }

         ++var1;
      }
   }

   private Name() {}

   public Name(String var1) throws TextParseException {
      this(var1, (Name)null);
   }

   public Name(String var1, Name var2) throws TextParseException {
      if(var1.equals("")) {
         throw parseException(var1, "empty name");
      } else if(var1.equals("@")) {
         if(var2 == null) {
            copy(empty, this);
         } else {
            copy(var2, this);
         }
      } else if(var1.equals(".")) {
         copy(root, this);
      } else {
         byte[] var3 = new byte[64];
         boolean var4 = false;
         byte var5 = 0;
         int var6 = -1;
         int var7 = var5;
         byte var8 = 0;
         boolean var9 = false;
         int var10 = var8;
         byte var11 = 1;
         int var12 = 0;
         int var13 = var11;

         while(true) {
            int var14 = var1.length();
            if(var7 >= var14) {
               if(var10 > 0 && var10 < 3) {
                  throw parseException(var1, "bad escape");
               }

               if(var9) {
                  throw parseException(var1, "bad escape");
               }

               boolean var32;
               if(var6 == -1) {
                  byte[] var31 = emptyLabel;
                  this.appendFromString(var1, var31, 0, 1);
                  var32 = true;
               } else {
                  byte var35 = (byte)(var13 - 1);
                  var3[0] = var35;
                  this.appendFromString(var1, var3, 0, 1);
                  var32 = var4;
               }

               if(var2 == null) {
                  return;
               }

               if(var32) {
                  return;
               }

               byte[] var33 = var2.name;
               int var34 = var2.getlabels();
               this.appendFromString(var1, var33, 0, var34);
               return;
            }

            byte var15 = (byte)var1.charAt(var7);
            if(var9) {
               label114: {
                  int var19;
                  byte var21;
                  if(var15 >= 48 && var15 <= 57 && var10 < 3) {
                     ++var10;
                     int var16 = var12 * 10;
                     int var17 = var15 - 48;
                     var12 = var16 + var17;
                     if(var12 > 255) {
                        throw parseException(var1, "bad escape");
                     }

                     if(var10 < 3) {
                        break label114;
                     }

                     byte var18 = (byte)var12;
                     var19 = var10;
                     var21 = var18;
                  } else {
                     if(var10 > 0 && var10 < 3) {
                        throw parseException(var1, "bad escape");
                     }

                     var19 = var10;
                     var21 = var15;
                  }

                  if(var13 > 63) {
                     throw parseException(var1, "label too long");
                  }

                  int var22 = var13 + 1;
                  var3[var13] = var21;
                  var10 = var19;
                  var9 = false;
                  var6 = var13;
                  var13 = var22;
               }
            } else if(var15 == 92) {
               var10 = 0;
               boolean var26 = false;
               var9 = true;
            } else if(var15 == 46) {
               if(var6 == -1) {
                  throw parseException(var1, "invalid empty label");
               }

               byte var28 = (byte)(var13 - 1);
               var3[0] = var28;
               this.appendFromString(var1, var3, 0, 1);
               byte var29 = 1;
               var6 = -1;
               var13 = var29;
            } else {
               if(var6 == -1) {
                  var6 = var7;
               }

               if(var13 > 63) {
                  throw parseException(var1, "label too long");
               }

               int var30 = var13 + 1;
               var3[var13] = var15;
               var13 = var30;
            }

            ++var7;
         }
      }
   }

   public Name(DNSInput var1) throws WireParseException {
      byte[] var2 = new byte[64];
      boolean var3 = false;
      boolean var4 = false;

      while(!var4) {
         int var5 = var1.readU8();
         switch(var5 & 192) {
         case 0:
            if(this.getlabels() >= 128) {
               throw new WireParseException("too many labels");
            }

            if(var5 == 0) {
               byte[] var6 = emptyLabel;
               this.append(var6, 0, 1);
               var4 = true;
            } else {
               byte var7 = (byte)var5;
               var2[0] = var7;
               var1.readByteArray(var2, 1, var5);
               this.append(var2, 0, 1);
            }
            break;
         case 192:
            int var8 = var1.readU8();
            var5 = ((var5 & -193) << 8) + var8;
            if(Options.check("verbosecompression")) {
               PrintStream var9 = System.err;
               StringBuilder var10 = (new StringBuilder()).append("currently ");
               int var11 = var1.current();
               String var12 = var10.append(var11).append(", pointer to ").append(var5).toString();
               var9.println(var12);
            }

            int var13 = var1.current() - 2;
            if(var5 >= var13) {
               throw new WireParseException("bad compression");
            }

            if(!var3) {
               var1.save();
               var3 = true;
            }

            var1.jump(var5);
            if(Options.check("verbosecompression")) {
               PrintStream var14 = System.err;
               String var15 = "current name \'" + this + "\', seeking to " + var5;
               var14.println(var15);
            }
            break;
         default:
            throw new WireParseException("bad label type");
         }
      }

      if(var3) {
         var1.restore();
      }
   }

   public Name(Name var1, int var2) {
      int var3 = var1.labels();
      if(var2 > var3) {
         throw new IllegalArgumentException("attempted to remove too many labels");
      } else {
         byte[] var4 = var1.name;
         this.name = var4;
         int var5 = var3 - var2;
         this.setlabels(var5);

         for(int var6 = 0; var6 < 7; ++var6) {
            int var7 = var3 - var2;
            if(var6 >= var7) {
               return;
            }

            int var8 = var6 + var2;
            int var9 = var1.offset(var8);
            this.setoffset(var6, var9);
         }

      }
   }

   public Name(byte[] var1) throws IOException {
      DNSInput var2 = new DNSInput(var1);
      this(var2);
   }

   private final void append(byte[] var1, int var2, int var3) throws NameTooLongException {
      int var4;
      if(this.name == null) {
         var4 = 0;
      } else {
         int var9 = this.name.length;
         int var10 = this.offset(0);
         var4 = var9 - var10;
      }

      int var5 = var2;
      int var6 = 0;

      int var7;
      for(var7 = 0; var6 < var3; ++var6) {
         byte var8 = var1[var5];
         if(var8 > 63) {
            throw new IllegalStateException("invalid label");
         }

         int var11 = var8 + 1;
         var5 += var11;
         var7 += var11;
      }

      int var12 = var4 + var7;
      if(var12 > 255) {
         throw new NameTooLongException();
      } else {
         int var13 = this.getlabels();
         int var14 = var13 + var3;
         if(var14 > 128) {
            throw new IllegalStateException("too many labels");
         } else {
            byte[] var15 = new byte[var12];
            if(var4 != 0) {
               byte[] var16 = this.name;
               int var17 = this.offset(0);
               System.arraycopy(var16, var17, var15, 0, var4);
            }

            System.arraycopy(var1, var2, var15, var4, var7);
            this.name = var15;

            for(int var18 = 0; var18 < var3; ++var18) {
               int var19 = var13 + var18;
               this.setoffset(var19, var4);
               int var20 = var15[var4] + 1;
               var4 += var20;
            }

            this.setlabels(var14);
         }
      }
   }

   private final void appendFromString(String var1, byte[] var2, int var3, int var4) throws TextParseException {
      try {
         this.append(var2, var3, var4);
      } catch (NameTooLongException var6) {
         throw parseException(var1, "Name too long");
      }
   }

   private final void appendSafe(byte[] var1, int var2, int var3) {
      try {
         this.append(var1, var2, var3);
      } catch (NameTooLongException var5) {
         ;
      }
   }

   private String byteString(byte[] var1, int var2) {
      StringBuffer var3 = new StringBuffer();
      int var4 = var2 + 1;
      byte var5 = var1[var2];
      int var6 = var4;

      while(true) {
         int var7 = var4 + var5;
         if(var6 >= var7) {
            return var3.toString();
         }

         int var8 = var1[var6] & 255;
         if(var8 > 32 && var8 < 127) {
            if(var8 != 34 && var8 != 40 && var8 != 41 && var8 != 46 && var8 != 59 && var8 != 92 && var8 != 64 && var8 != 36) {
               char var18 = (char)var8;
               var3.append(var18);
            } else {
               StringBuffer var15 = var3.append('\\');
               char var16 = (char)var8;
               var3.append(var16);
            }
         } else {
            StringBuffer var9 = var3.append('\\');
            DecimalFormat var10 = byteFormat;
            long var11 = (long)var8;
            String var13 = var10.format(var11);
            var3.append(var13);
         }

         ++var6;
      }
   }

   public static Name concatenate(Name var0, Name var1) throws NameTooLongException {
      Name var2;
      if(var0.isAbsolute()) {
         var2 = var0;
      } else {
         var2 = new Name();
         copy(var0, var2);
         byte[] var3 = var1.name;
         int var4 = var1.offset(0);
         int var5 = var1.getlabels();
         var2.append(var3, var4, var5);
      }

      return var2;
   }

   private static final void copy(Name var0, Name var1) {
      if(var0.offset(0) == 0) {
         byte[] var2 = var0.name;
         var1.name = var2;
         long var3 = var0.offsets;
         var1.offsets = var3;
      } else {
         int var5 = var0.offset(0);
         int var6 = var0.name.length - var5;
         int var7 = var0.labels();
         byte[] var8 = new byte[var6];
         var1.name = var8;
         byte[] var9 = var0.name;
         byte[] var10 = var1.name;
         System.arraycopy(var9, var5, var10, 0, var6);

         for(int var11 = 0; var11 < var7 && var11 < 7; ++var11) {
            int var12 = var0.offset(var11) - var5;
            var1.setoffset(var11, var12);
         }

         var1.setlabels(var7);
      }
   }

   private final boolean equals(byte[] var1, int var2) {
      int var3 = this.labels();
      int var4 = this.offset(0);
      int var5 = 0;
      int var6 = var2;

      boolean var9;
      while(true) {
         if(var5 >= var3) {
            var9 = true;
            break;
         }

         byte var7 = this.name[var4];
         byte var8 = var1[var6];
         if(var7 != var8) {
            var9 = false;
            break;
         }

         byte[] var10 = this.name;
         int var11 = var4 + 1;
         byte var24 = var10[var4];
         int var12 = var6 + 1;
         if(var24 > 63) {
            throw new IllegalStateException("invalid label");
         }

         int var13 = var11;
         int var14 = var12;

         int var21;
         for(var6 = 0; var6 < var24; var14 = var21) {
            byte[] var15 = lowercase;
            byte[] var16 = this.name;
            int var17 = var13 + 1;
            int var18 = var16[var13] & 255;
            byte var19 = var15[var18];
            byte[] var20 = lowercase;
            var21 = var14 + 1;
            int var22 = var1[var14] & 255;
            byte var23 = var20[var22];
            if(var19 != var23) {
               var9 = false;
               return var9;
            }

            ++var6;
            var13 = var17;
         }

         ++var5;
         var6 = var14;
         var4 = var13;
      }

      return var9;
   }

   public static Name fromConstantString(String var0) {
      try {
         Name var1 = fromString(var0, (Name)null);
         return var1;
      } catch (TextParseException var4) {
         String var3 = "Invalid name \'" + var0 + "\'";
         throw new IllegalArgumentException(var3);
      }
   }

   public static Name fromString(String var0) throws TextParseException {
      return fromString(var0, (Name)null);
   }

   public static Name fromString(String var0, Name var1) throws TextParseException {
      Name var2;
      if(var0.equals("@") && var1 != null) {
         var2 = var1;
      } else if(var0.equals(".")) {
         var2 = root;
      } else {
         var2 = new Name(var0, var1);
      }

      return var2;
   }

   private final int getlabels() {
      return (int)(this.offsets & 255L);
   }

   private final int offset(int var1) {
      boolean var2 = true;
      int var3;
      if(var1 == 0 && this.getlabels() == 0) {
         var3 = 0;
         return var3;
      } else {
         if(var1 >= 0) {
            int var4 = this.getlabels();
            if(var1 < var4) {
               if(var1 < 7) {
                  int var5 = (7 - var1) * 8;
                  var3 = (int)(this.offsets >>> var5) & 255;
               } else {
                  int var6 = this.offset(6);
                  byte var7 = 6;
                  int var11 = var6;

                  for(int var8 = var7; var8 < var1; ++var8) {
                     int var9 = this.name[var11] + 1;
                     int var10000 = var11 + var9;
                  }

                  var3 = var11;
               }

               return var3;
            }
         }

         throw new IllegalArgumentException("label out of range");
      }
   }

   private static TextParseException parseException(String var0, String var1) {
      String var2 = "\'" + var0 + "\': " + var1;
      return new TextParseException(var2);
   }

   private final void setlabels(int var1) {
      long var2 = this.offsets & 65280L;
      this.offsets = var2;
      long var4 = this.offsets;
      long var6 = (long)var1;
      long var8 = var4 | var6;
      this.offsets = var8;
   }

   private final void setoffset(int var1, int var2) {
      if(var1 < 7) {
         int var3 = (7 - var1) * 8;
         long var4 = this.offsets;
         long var6 = 255L << var3 ^ 65535L;
         long var8 = var4 & var6;
         this.offsets = var8;
         long var10 = this.offsets;
         long var12 = (long)var2 << var3;
         long var14 = var10 | var12;
         this.offsets = var14;
      }
   }

   public int compareTo(Object var1) {
      Name var2 = (Name)var1;
      int var3;
      if(this == var2) {
         var3 = 0;
      } else {
         int var4 = this.labels();
         int var5 = var2.labels();
         int var6;
         if(var4 > var5) {
            var6 = var5;
         } else {
            var6 = var4;
         }

         int var7 = 1;

         while(true) {
            if(var7 > var6) {
               var3 = var4 - var5;
               break;
            }

            int var8 = var4 - var7;
            int var9 = this.offset(var8);
            int var10 = var5 - var7;
            int var11 = var2.offset(var10);
            byte var12 = this.name[var9];
            byte var13 = var2.name[var11];

            for(int var14 = 0; var14 < var12 && var14 < var13; ++var14) {
               byte[] var15 = lowercase;
               byte[] var16 = this.name;
               int var17 = var14 + var9 + 1;
               int var18 = var16[var17] & 255;
               byte var19 = var15[var18];
               byte[] var20 = lowercase;
               byte[] var21 = var2.name;
               int var22 = var14 + var11 + 1;
               int var23 = var21[var22] & 255;
               byte var24 = var20[var23];
               int var25 = var19 - var24;
               if(var25 != 0) {
                  var3 = var25;
                  return var3;
               }
            }

            if(var12 != var13) {
               var3 = var12 - var13;
               break;
            }

            ++var7;
         }
      }

      return var3;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 == this) {
         var2 = true;
      } else if(var1 != null && var1 instanceof Name) {
         Name var11 = (Name)var1;
         if(var11.hashcode == 0) {
            int var3 = var11.hashCode();
         }

         if(this.hashcode == 0) {
            int var4 = this.hashCode();
         }

         int var5 = var11.hashcode;
         int var6 = this.hashcode;
         if(var5 != var6) {
            var2 = false;
         } else {
            int var7 = var11.labels();
            int var8 = this.labels();
            if(var7 != var8) {
               var2 = false;
            } else {
               byte[] var9 = var11.name;
               int var10 = var11.offset(0);
               var2 = this.equals(var9, var10);
            }
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public Name fromDNAME(DNAMERecord var1) throws NameTooLongException {
      Name var2 = var1.getName();
      Name var3 = var1.getTarget();
      if(!this.subdomain(var2)) {
         var2 = null;
      } else {
         int var4 = this.labels();
         int var5 = var2.labels();
         int var6 = var4 - var5;
         short var7 = this.length();
         short var8 = var2.length();
         int var9 = var7 - var8;
         int var10 = this.offset(0);
         int var11 = var3.labels();
         short var12 = var3.length();
         if(var9 + var12 > 255) {
            throw new NameTooLongException();
         }

         Name var13 = new Name();
         int var14 = var6 + var11;
         var13.setlabels(var14);
         byte[] var15 = new byte[var9 + var12];
         var13.name = var15;
         byte[] var16 = this.name;
         byte[] var17 = var13.name;
         System.arraycopy(var16, var10, var17, 0, var9);
         byte[] var18 = var3.name;
         byte[] var19 = var13.name;
         System.arraycopy(var18, 0, var19, var9, var12);
         int var20 = 0;

         int var23;
         for(byte var24 = 0; var24 < 7; var23 = var24 + 1) {
            int var21 = var6 + var11;
            if(var24 >= var21) {
               break;
            }

            var13.setoffset(var24, var20);
            int var22 = var13.name[var20] + 1;
            var20 += var22;
         }

         var2 = var13;
      }

      return var2;
   }

   public byte[] getLabel(int var1) {
      int var2 = this.offset(var1);
      byte var3 = (byte)(this.name[var2] + 1);
      byte[] var4 = new byte[var3];
      System.arraycopy(this.name, var2, var4, 0, var3);
      return var4;
   }

   public String getLabelString(int var1) {
      int var2 = this.offset(var1);
      byte[] var3 = this.name;
      return this.byteString(var3, var2);
   }

   public int hashCode() {
      int var1 = 0;
      int var2;
      if(this.hashcode != 0) {
         var2 = this.hashcode;
      } else {
         int var3 = this.offset(0);

         while(true) {
            int var4 = this.name.length;
            if(var3 >= var4) {
               this.hashcode = var1;
               var2 = this.hashcode;
               break;
            }

            int var5 = var1 << 3;
            byte[] var6 = lowercase;
            int var7 = this.name[var3] & 255;
            byte var8 = var6[var7];
            int var9 = var5 + var8;
            var1 += var9;
            ++var3;
         }
      }

      return var2;
   }

   public boolean isAbsolute() {
      boolean var1;
      if(this.labels() == 0) {
         var1 = false;
      } else {
         byte[] var2 = this.name;
         int var3 = this.name.length - 1;
         if(var2[var3] == 0) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }

   public boolean isWild() {
      boolean var1;
      if(this.labels() == 0) {
         var1 = false;
      } else if(this.name[0] == 1 && this.name[1] == 42) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int labels() {
      return this.getlabels();
   }

   public short length() {
      short var1;
      if(this.getlabels() == 0) {
         var1 = 0;
      } else {
         int var2 = this.name.length;
         int var3 = this.offset(0);
         var1 = (short)(var2 - var3);
      }

      return var1;
   }

   public Name relativize(Name var1) {
      Name var2;
      if(var1 != null && this.subdomain(var1)) {
         var2 = new Name();
         copy(this, var2);
         short var3 = this.length();
         short var4 = var1.length();
         int var5 = var3 - var4;
         int var6 = var2.labels();
         int var7 = var1.labels();
         int var8 = var6 - var7;
         var2.setlabels(var8);
         byte[] var9 = new byte[var5];
         var2.name = var9;
         byte[] var10 = this.name;
         int var11 = this.offset(0);
         byte[] var12 = var2.name;
         System.arraycopy(var10, var11, var12, 0, var5);
      } else {
         var2 = this;
      }

      return var2;
   }

   public boolean subdomain(Name var1) {
      int var2 = this.labels();
      int var3 = var1.labels();
      byte var4;
      if(var3 > var2) {
         var4 = 0;
      } else if(var3 == var2) {
         var4 = this.equals(var1);
      } else {
         byte[] var5 = this.name;
         int var6 = var2 - var3;
         int var7 = this.offset(var6);
         var4 = var1.equals(var5, var7);
      }

      return (boolean)var4;
   }

   public String toString() {
      int var1 = 0;
      int var2 = this.labels();
      String var15;
      if(var2 == 0) {
         var15 = "@";
      } else {
         if(var2 == 1) {
            byte[] var3 = this.name;
            int var4 = this.offset(0);
            if(var3[var4] == 0) {
               var15 = ".";
               return var15;
            }
         }

         StringBuffer var5 = new StringBuffer();

         for(int var6 = this.offset(0); var1 < var2; ++var1) {
            byte var7 = this.name[var6];
            if(var7 > 63) {
               throw new IllegalStateException("invalid label");
            }

            if(var7 == 0) {
               break;
            }

            byte[] var10 = this.name;
            String var11 = this.byteString(var10, var6);
            var5.append(var11);
            StringBuffer var13 = var5.append('.');
            int var14 = var7 + 1;
            var6 += var14;
         }

         if(!this.isAbsolute()) {
            int var8 = var5.length() - 1;
            var5.deleteCharAt(var8);
         }

         var15 = var5.toString();
      }

      return var15;
   }

   public void toWire(DNSOutput var1, Compression var2) {
      if(!this.isAbsolute()) {
         throw new IllegalArgumentException("toWire() called on non-absolute name");
      } else {
         int var3 = this.labels();
         int var4 = 0;

         while(true) {
            int var5 = var3 - 1;
            if(var4 >= var5) {
               var1.writeU8(0);
               return;
            }

            Name var6;
            if(var4 == 0) {
               var6 = this;
            } else {
               var6 = new Name(this, var4);
            }

            int var7 = -1;
            if(var2 != null) {
               var7 = var2.get(var6);
            }

            if(var7 >= 0) {
               int var8 = '\uc000' | var7;
               var1.writeU16(var8);
               return;
            }

            if(var2 != null) {
               int var9 = var1.current();
               var2.add(var9, var6);
            }

            int var10 = this.offset(var4);
            byte[] var11 = this.name;
            int var12 = this.name[var10] + 1;
            var1.writeByteArray(var11, var10, var12);
            ++var4;
         }
      }
   }

   public void toWire(DNSOutput var1, Compression var2, boolean var3) {
      if(var3) {
         this.toWireCanonical(var1);
      } else {
         this.toWire(var1, var2);
      }
   }

   public byte[] toWire() {
      DNSOutput var1 = new DNSOutput();
      this.toWire(var1, (Compression)null);
      return var1.toByteArray();
   }

   public void toWireCanonical(DNSOutput var1) {
      byte[] var2 = this.toWireCanonical();
      var1.writeByteArray(var2);
   }

   public byte[] toWireCanonical() {
      int var1 = this.labels();
      byte[] var22;
      if(var1 == 0) {
         var22 = new byte[0];
      } else {
         int var2 = this.name.length;
         int var3 = this.offset(0);
         byte[] var4 = new byte[var2 - var3];
         int var5 = this.offset(0);
         int var6 = 0;

         int var15;
         for(int var7 = 0; var6 < var1; var5 = var15) {
            byte var8 = this.name[var5];
            if(var8 > 63) {
               throw new IllegalStateException("invalid label");
            }

            int var9 = var7 + 1;
            byte[] var10 = this.name;
            int var11 = var5 + 1;
            byte var12 = var10[var5];
            var4[var7] = var12;
            int var13 = 0;
            int var14 = var9;

            int var19;
            for(var15 = var11; var13 < var8; var15 = var19) {
               int var16 = var14 + 1;
               byte[] var17 = lowercase;
               byte[] var18 = this.name;
               var19 = var15 + 1;
               int var20 = var18[var15] & 255;
               byte var21 = var17[var20];
               var4[var14] = var21;
               ++var13;
               var14 = var16;
            }

            ++var6;
            var7 = var14;
         }

         var22 = var4;
      }

      return var22;
   }

   public Name wild(int var1) {
      if(var1 < 1) {
         throw new IllegalArgumentException("must replace 1 or more labels");
      } else {
         try {
            Name var2 = new Name();
            copy(wild, var2);
            byte[] var3 = this.name;
            int var4 = this.offset(var1);
            int var5 = this.getlabels() - var1;
            var2.append(var3, var4, var5);
            return var2;
         } catch (NameTooLongException var7) {
            throw new IllegalStateException("Name.wild: concatenate failed");
         }
      }
   }
}
