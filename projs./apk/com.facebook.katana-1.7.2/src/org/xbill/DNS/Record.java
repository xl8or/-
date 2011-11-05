package org.xbill.DNS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DClass;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.EmptyRecord;
import org.xbill.DNS.Name;
import org.xbill.DNS.Options;
import org.xbill.DNS.RRSIGRecord;
import org.xbill.DNS.RelativeNameException;
import org.xbill.DNS.TTL;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.Type;
import org.xbill.DNS.UNKRecord;
import org.xbill.DNS.WireParseException;
import org.xbill.DNS.utils.base16;

public abstract class Record implements Cloneable, Comparable, Serializable {

   private static final DecimalFormat byteFormat = new DecimalFormat();
   private static final long serialVersionUID = 2694906050116005466L;
   protected int dclass;
   protected Name name;
   protected long ttl;
   protected int type;


   static {
      byteFormat.setMinimumIntegerDigits(3);
   }

   protected Record() {}

   Record(Name var1, int var2, int var3, long var4) {
      if(!var1.isAbsolute()) {
         throw new RelativeNameException(var1);
      } else {
         Type.check(var2);
         DClass.check(var3);
         TTL.check(var4);
         this.name = var1;
         this.type = var2;
         this.dclass = var3;
         this.ttl = var4;
      }
   }

   protected static byte[] byteArrayFromString(String var0) throws TextParseException {
      byte[] var1 = var0.getBytes();
      int var2 = 0;

      boolean var4;
      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            var4 = false;
            break;
         }

         if(var1[var2] == 92) {
            var4 = true;
            break;
         }

         ++var2;
      }

      if(!var4) {
         if(var1.length > 255) {
            throw new TextParseException("text string too long");
         }
      } else {
         ByteArrayOutputStream var5 = new ByteArrayOutputStream();
         byte var6 = 0;
         byte var7 = 0;
         int var8 = 0;
         boolean var9 = false;

         while(true) {
            int var10 = var1.length;
            if(var6 >= var10) {
               if(var8 > 0 && var8 < 3) {
                  throw new TextParseException("bad escape");
               }

               if(var5.toByteArray().length > 255) {
                  throw new TextParseException("text string too long");
               }

               var1 = var5.toByteArray();
               break;
            }

            byte var11 = var1[var6];
            if(var9) {
               label85: {
                  int var17;
                  byte var19;
                  if(var11 >= 48 && var11 <= 57 && var8 < 3) {
                     ++var8;
                     int var12 = var7 * 10;
                     int var13 = var11 - 48;
                     int var14 = var12 + var13;
                     if(var14 > 255) {
                        throw new TextParseException("bad escape");
                     }

                     if(var8 < 3) {
                        break label85;
                     }

                     byte var16 = (byte)var14;
                     var17 = var8;
                     var19 = var16;
                  } else {
                     if(var8 > 0 && var8 < 3) {
                        throw new TextParseException("bad escape");
                     }

                     var17 = var8;
                     var19 = var11;
                  }

                  var5.write(var19);
                  var8 = var17;
                  var9 = false;
               }
            } else if(var1[var6] == 92) {
               boolean var21 = false;
               var8 = 0;
               var9 = true;
            } else {
               byte var22 = var1[var6];
               var5.write(var22);
            }

            int var15 = var6 + 1;
         }
      }

      return var1;
   }

   protected static String byteArrayToString(byte[] var0, boolean var1) {
      StringBuffer var2 = new StringBuffer();
      if(var1) {
         StringBuffer var3 = var2.append('\"');
      }

      int var4 = 0;

      while(true) {
         int var5 = var0.length;
         if(var4 >= var5) {
            if(var1) {
               StringBuffer var18 = var2.append('\"');
            }

            return var2.toString();
         }

         int var6 = var0[var4] & 255;
         if(var6 >= 32 && var6 < 127) {
            if(var6 != 34 && var6 != 92) {
               char var16 = (char)var6;
               var2.append(var16);
            } else {
               StringBuffer var13 = var2.append('\\');
               char var14 = (char)var6;
               var2.append(var14);
            }
         } else {
            StringBuffer var7 = var2.append('\\');
            DecimalFormat var8 = byteFormat;
            long var9 = (long)var6;
            String var11 = var8.format(var9);
            var2.append(var11);
         }

         ++var4;
      }
   }

   static Name checkName(String var0, Name var1) {
      if(!var1.isAbsolute()) {
         throw new RelativeNameException(var1);
      } else {
         return var1;
      }
   }

   static int checkU16(String var0, int var1) {
      if(var1 >= 0 && var1 <= '\uffff') {
         return var1;
      } else {
         String var2 = "\"" + var0 + "\" " + var1 + " must be an unsigned 16 " + "bit value";
         throw new IllegalArgumentException(var2);
      }
   }

   static long checkU32(String var0, long var1) {
      if(var1 >= 0L && var1 <= 4294967295L) {
         return var1;
      } else {
         String var3 = "\"" + var0 + "\" " + var1 + " must be an unsigned 32 " + "bit value";
         throw new IllegalArgumentException(var3);
      }
   }

   static int checkU8(String var0, int var1) {
      if(var1 >= 0 && var1 <= 255) {
         return var1;
      } else {
         String var2 = "\"" + var0 + "\" " + var1 + " must be an unsigned 8 " + "bit value";
         throw new IllegalArgumentException(var2);
      }
   }

   public static Record fromString(Name var0, int var1, int var2, long var3, String var5, Name var6) throws IOException {
      Tokenizer var7 = new Tokenizer(var5);
      return fromString(var0, var1, var2, var3, var7, var6);
   }

   public static Record fromString(Name var0, int var1, int var2, long var3, Tokenizer var5, Name var6) throws IOException {
      boolean var7 = true;
      if(!var0.isAbsolute()) {
         throw new RelativeNameException(var0);
      } else {
         Type.check(var1);
         DClass.check(var2);
         TTL.check(var3);
         Tokenizer.Token var8 = var5.get();
         Record var24;
         if(var8.type == 3 && var8.value.equals("\\#")) {
            int var23 = var5.getUInt16();
            byte[] var9 = var5.getHex();
            if(var9 == null) {
               var9 = new byte[0];
            }

            int var10 = var9.length;
            if(var23 != var10) {
               throw var5.exception("invalid unknown RR encoding: length mismatch");
            }

            DNSInput var11 = new DNSInput(var9);
            var24 = newRecord(var0, var1, var2, var3, var23, var11);
         } else {
            var5.unget();
            var24 = getEmptyRecord(var0, var1, var2, var3, (boolean)1);
            var24.rdataFromString(var5, var6);
            Tokenizer.Token var22 = var5.get();
            if(var22.type != 1 && var22.type != 0) {
               throw var5.exception("unexpected tokens at end of record");
            }
         }

         return var24;
      }
   }

   static Record fromWire(DNSInput var0, int var1) throws IOException {
      return fromWire(var0, var1, (boolean)0);
   }

   static Record fromWire(DNSInput var0, int var1, boolean var2) throws IOException {
      Name var3 = new Name(var0);
      int var4 = var0.readU16();
      int var5 = var0.readU16();
      Record var10;
      if(var1 == 0) {
         var10 = newRecord(var3, var4, var5);
      } else {
         long var6 = var0.readU32();
         int var8 = var0.readU16();
         if(var8 == 0 && var2) {
            var10 = newRecord(var3, var4, var5, var6);
         } else {
            var10 = newRecord(var3, var4, var5, var6, var8, var0);
         }
      }

      return var10;
   }

   public static Record fromWire(byte[] var0, int var1) throws IOException {
      return fromWire(new DNSInput(var0), var1, (boolean)0);
   }

   private static final Record getEmptyRecord(Name var0, int var1, int var2, long var3, boolean var5) {
      Object var7;
      if(var5) {
         Record var6 = Type.getProto(var1);
         if(var6 != null) {
            var7 = var6.getObject();
         } else {
            var7 = new UNKRecord();
         }
      } else {
         var7 = new EmptyRecord();
      }

      ((Record)var7).name = var0;
      ((Record)var7).type = var1;
      ((Record)var7).dclass = var2;
      ((Record)var7).ttl = var3;
      return (Record)var7;
   }

   public static Record newRecord(Name var0, int var1, int var2) {
      return newRecord(var0, var1, var2, 0L);
   }

   public static Record newRecord(Name var0, int var1, int var2, long var3) {
      if(!var0.isAbsolute()) {
         throw new RelativeNameException(var0);
      } else {
         Type.check(var1);
         DClass.check(var2);
         TTL.check(var3);
         return getEmptyRecord(var0, var1, var2, var3, (boolean)0);
      }
   }

   private static Record newRecord(Name var0, int var1, int var2, long var3, int var5, DNSInput var6) throws IOException {
      byte var7;
      if(var6 != false) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      Record var13 = getEmptyRecord(var0, var1, var2, var3, (boolean)var7);
      if(var6 != false) {
         if(var6.remaining() < var5) {
            throw new WireParseException("truncated record");
         }

         var6.setActive(var5);
         var13.rrFromWire(var6);
         if(var6.remaining() > 0) {
            throw new WireParseException("invalid record length");
         }

         var6.clearActive();
      }

      return var13;
   }

   public static Record newRecord(Name var0, int var1, int var2, long var3, int var5, byte[] var6) {
      if(!var0.isAbsolute()) {
         throw new RelativeNameException(var0);
      } else {
         Type.check(var1);
         DClass.check(var2);
         TTL.check(var3);
         DNSInput var7;
         if(var6 != false) {
            var7 = new DNSInput(var6);
         } else {
            var7 = null;
         }

         Name var8 = var0;
         int var9 = var1;
         int var10 = var2;
         long var11 = var3;
         int var13 = var5;

         Record var14;
         Record var15;
         try {
            var14 = newRecord(var8, var9, var10, var11, var13, var7);
         } catch (IOException var17) {
            var15 = null;
            return var15;
         }

         var15 = var14;
         return var15;
      }
   }

   public static Record newRecord(Name var0, int var1, int var2, long var3, byte[] var5) {
      int var6 = var5.length;
      return newRecord(var0, var1, var2, var3, var6, var5);
   }

   private void toWireCanonical(DNSOutput var1, boolean var2) {
      this.name.toWireCanonical(var1);
      int var3 = this.type;
      var1.writeU16(var3);
      int var4 = this.dclass;
      var1.writeU16(var4);
      if(var2) {
         var1.writeU32(0L);
      } else {
         long var7 = this.ttl;
         var1.writeU32(var7);
      }

      int var5 = var1.current();
      var1.writeU16(0);
      this.rrToWire(var1, (Compression)null, (boolean)1);
      int var6 = var1.current() - var5 - 2;
      var1.save();
      var1.jump(var5);
      var1.writeU16(var6);
      var1.restore();
   }

   private byte[] toWireCanonical(boolean var1) {
      DNSOutput var2 = new DNSOutput();
      this.toWireCanonical(var2, var1);
      return var2.toByteArray();
   }

   protected static String unknownToString(byte[] var0) {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("\\# ");
      int var3 = var0.length;
      var1.append(var3);
      StringBuffer var5 = var1.append(" ");
      String var6 = base16.toString(var0);
      var1.append(var6);
      return var1.toString();
   }

   Record cloneRecord() {
      try {
         this = (Record)this.clone();
         return this;
      } catch (CloneNotSupportedException var2) {
         throw new IllegalStateException();
      }
   }

   public int compareTo(Object var1) {
      int var2 = 0;
      Record var3 = (Record)var1;
      int var4;
      if(this == var3) {
         var4 = 0;
      } else {
         Name var5 = this.name;
         Name var6 = var3.name;
         var4 = var5.compareTo(var6);
         if(var4 == 0) {
            int var7 = this.dclass;
            int var8 = var3.dclass;
            var4 = var7 - var8;
            if(var4 == 0) {
               int var9 = this.type;
               int var10 = var3.type;
               var4 = var9 - var10;
               if(var4 == 0) {
                  byte[] var11 = this.rdataToWireCanonical();
                  byte[] var12 = var3.rdataToWireCanonical();

                  while(true) {
                     int var13 = var11.length;
                     if(var2 < var13) {
                        int var14 = var12.length;
                        if(var2 < var14) {
                           int var15 = var11[var2] & 255;
                           int var16 = var12[var2] & 255;
                           int var17 = var15 - var16;
                           if(var17 != 0) {
                              var4 = var17;
                              break;
                           }

                           ++var2;
                           continue;
                        }
                     }

                     int var18 = var11.length;
                     int var19 = var12.length;
                     var4 = var18 - var19;
                     break;
                  }
               }
            }
         }
      }

      return var4;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(var1 != null && var1 instanceof Record) {
         Record var11 = (Record)var1;
         int var3 = this.type;
         int var4 = var11.type;
         if(var3 == var4) {
            int var5 = this.dclass;
            int var6 = var11.dclass;
            if(var5 == var6) {
               Name var7 = this.name;
               Name var8 = var11.name;
               if(var7.equals(var8)) {
                  byte[] var9 = this.rdataToWireCanonical();
                  byte[] var10 = var11.rdataToWireCanonical();
                  var2 = Arrays.equals(var9, var10);
                  return var2;
               }
            }
         }

         var2 = false;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Name getAdditionalName() {
      return null;
   }

   public int getDClass() {
      return this.dclass;
   }

   public Name getName() {
      return this.name;
   }

   abstract Record getObject();

   public int getRRsetType() {
      int var1;
      if(this.type == 46) {
         var1 = ((RRSIGRecord)this).getTypeCovered();
      } else {
         var1 = this.type;
      }

      return var1;
   }

   public long getTTL() {
      return this.ttl;
   }

   public int getType() {
      return this.type;
   }

   public int hashCode() {
      int var1 = 0;
      byte[] var2 = this.toWireCanonical((boolean)1);
      int var3 = var1;

      while(true) {
         int var4 = var2.length;
         if(var1 >= var4) {
            return var3;
         }

         int var5 = var3 << 3;
         int var6 = var2[var1] & 255;
         int var7 = var5 + var6;
         var3 += var7;
         ++var1;
      }
   }

   abstract void rdataFromString(Tokenizer var1, Name var2) throws IOException;

   public String rdataToString() {
      return this.rrToString();
   }

   public byte[] rdataToWireCanonical() {
      DNSOutput var1 = new DNSOutput();
      this.rrToWire(var1, (Compression)null, (boolean)1);
      return var1.toByteArray();
   }

   abstract void rrFromWire(DNSInput var1) throws IOException;

   abstract String rrToString();

   abstract void rrToWire(DNSOutput var1, Compression var2, boolean var3);

   public boolean sameRRset(Record var1) {
      int var2 = this.getRRsetType();
      int var3 = var1.getRRsetType();
      boolean var8;
      if(var2 == var3) {
         int var4 = this.dclass;
         int var5 = var1.dclass;
         if(var4 == var5) {
            Name var6 = this.name;
            Name var7 = var1.name;
            if(var6.equals(var7)) {
               var8 = true;
               return var8;
            }
         }
      }

      var8 = false;
      return var8;
   }

   void setTTL(long var1) {
      this.ttl = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      Name var2 = this.name;
      var1.append(var2);
      if(var1.length() < 8) {
         StringBuffer var4 = var1.append("\t");
      }

      if(var1.length() < 16) {
         StringBuffer var5 = var1.append("\t");
      }

      StringBuffer var6 = var1.append("\t");
      if(Options.check("BINDTTL")) {
         String var7 = TTL.format(this.ttl);
         var1.append(var7);
      } else {
         long var18 = this.ttl;
         var1.append(var18);
      }

      StringBuffer var9 = var1.append("\t");
      if(this.dclass != 1 || !Options.check("noPrintIN")) {
         String var10 = DClass.string(this.dclass);
         var1.append(var10);
         StringBuffer var12 = var1.append("\t");
      }

      String var13 = Type.string(this.type);
      var1.append(var13);
      String var15 = this.rrToString();
      if(!var15.equals("")) {
         StringBuffer var16 = var1.append("\t");
         var1.append(var15);
      }

      return var1.toString();
   }

   void toWire(DNSOutput var1, int var2, Compression var3) {
      this.name.toWire(var1, var3);
      int var4 = this.type;
      var1.writeU16(var4);
      int var5 = this.dclass;
      var1.writeU16(var5);
      if(var2 != 0) {
         long var6 = this.ttl;
         var1.writeU32(var6);
         int var8 = var1.current();
         var1.writeU16(0);
         this.rrToWire(var1, var3, (boolean)0);
         int var9 = var1.current() - var8 - 2;
         var1.save();
         var1.jump(var8);
         var1.writeU16(var9);
         var1.restore();
      }
   }

   public byte[] toWire(int var1) {
      DNSOutput var2 = new DNSOutput();
      this.toWire(var2, var1, (Compression)null);
      return var2.toByteArray();
   }

   public byte[] toWireCanonical() {
      return this.toWireCanonical((boolean)0);
   }

   Record withDClass(int var1, long var2) {
      Record var4 = this.cloneRecord();
      var4.dclass = var1;
      var4.ttl = var2;
      return var4;
   }

   public Record withName(Name var1) {
      if(!var1.isAbsolute()) {
         throw new RelativeNameException(var1);
      } else {
         Record var2 = this.cloneRecord();
         var2.name = var1;
         return var2;
      }
   }
}
