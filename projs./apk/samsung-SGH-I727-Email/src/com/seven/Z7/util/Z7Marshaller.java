package com.seven.Z7.util;

import android.util.Log;
import com.seven.util.IntArrayMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Z7Marshaller {

   private static final int ARRAYMAP_TAG = 10;
   private static final int BLOB_TAG = 8;
   private static final int DATE_TAG = 7;
   private static final int ERROR_TAG = 12;
   private static final int FALSE_TAG = 2;
   private static final int INTARRAYMAP_TAG = 11;
   private static final int INT_TAG = 4;
   private static final int LIST_TAG = 9;
   private static final int LONG_TAG = 5;
   public static final int MAX_SIZE = 262144;
   private static final int NULL_TAG = 0;
   private static final int SHORT_TAG = 3;
   public static final int STRING_ENCODING_UTF16BE = 3;
   public static final int STRING_ENCODING_UTF16LE = 2;
   public static final int STRING_ENCODING_UTF8 = 1;
   private static final int STRING_TAG = 6;
   private static final String TAG = "Z7Marshaller";
   private static final int TIME_ZONE_TAG = 13;
   private static final int TRANSPORT_ADDRESS_TAG = 14;
   private static final int TRUE_TAG = 1;
   private DataInputStream m_in;
   private DataOutputStream m_out;


   public Z7Marshaller(InputStream var1) {
      DataInputStream var2;
      if(var1 instanceof DataInputStream) {
         var2 = (DataInputStream)var1;
      } else {
         var2 = new DataInputStream(var1);
      }

      this.m_in = var2;
   }

   public Z7Marshaller(OutputStream var1) {
      DataOutputStream var2;
      if(var1 instanceof DataOutputStream) {
         var2 = (DataOutputStream)var1;
      } else {
         var2 = new DataOutputStream(var1);
      }

      this.m_out = var2;
   }

   public static Object decode(InputStream var0) throws IOException {
      return (new Z7Marshaller(var0)).read();
   }

   public static Object decode(byte[] var0) throws IOException {
      return decode((InputStream)(new ByteArrayInputStream(var0)));
   }

   private Object decodeObject(int var1) throws IOException {
      Object var3;
      switch(var1) {
      case 0:
         var3 = null;
         break;
      case 1:
         var3 = new Boolean((boolean)1);
         break;
      case 2:
         var3 = new Boolean((boolean)0);
         break;
      case 3:
         short var4 = this.m_in.readShort();
         var3 = new Short(var4);
         break;
      case 4:
         int var5 = this.m_in.readInt();
         var3 = new Integer(var5);
         break;
      case 5:
         long var6 = this.m_in.readLong();
         var3 = new Long(var6);
         break;
      case 6:
         var3 = this.readStringData();
         break;
      case 7:
         long var8 = this.m_in.readLong();
         var3 = new Date(var8);
         break;
      case 8:
         var3 = this.readBlobData();
         break;
      case 9:
         var3 = this.readListData();
         break;
      case 10:
      default:
         String var2 = "Bad marshal tag = " + var1;
         throw new IOException(var2);
      case 11:
         var3 = this.readIntArrayMapData();
      }

      return var3;
   }

   public static void encode(Object var0, OutputStream var1) throws IOException {
      Z7Marshaller var2 = new Z7Marshaller(var1);
      var2.write(var0);
      var2.flush();
   }

   public static byte[] encode(Object var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      encode(var0, var1);
      return var1.toByteArray();
   }

   public static int encodedSize(Object var0) throws IOException {
      Z7Marshaller.CountOutputStream var1 = new Z7Marshaller.CountOutputStream((Z7Marshaller.1)null);
      (new Z7Marshaller(var1)).write(var0);
      return var1.count;
   }

   public static String getEncodingName(int var0) {
      String var3;
      switch(var0) {
      case 1:
         var3 = "UTF-8";
         break;
      case 2:
         var3 = "UTF-16LE";
         break;
      case 3:
         var3 = "UTF-16BE";
         break;
      default:
         String var1 = "getEncodingName: Unsupported encoding received: " + var0;
         int var2 = Log.w("Z7Marshaller", var1);
         var3 = "UTF-8";
      }

      return var3;
   }

   public void flush() throws IOException {
      this.m_out.flush();
   }

   public Object read() throws IOException {
      byte var1 = this.m_in.readByte();
      return this.decodeObject(var1);
   }

   public byte[] readBlob() throws IOException {
      byte var1 = this.m_in.readByte();
      byte[] var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         if(var1 != 8) {
            StringBuilder var3 = (new StringBuilder()).append("not a blob: ");
            Object var4 = this.decodeObject(var1);
            String var5 = var3.append(var4).toString();
            throw new IOException(var5);
         }

         var2 = this.readBlobData();
      }

      return var2;
   }

   public byte[] readBlobData() throws IOException {
      int var1 = this.m_in.readInt();
      if(var1 > 262144) {
         String var2 = "Data structure exceeds maximum (" + var1 + " > " + 262144;
         throw new IOException(var2);
      } else {
         byte[] var3 = new byte[var1];
         this.m_in.readFully(var3);
         return var3;
      }
   }

   public boolean readBoolean() throws IOException {
      byte var1 = this.m_in.readByte();
      boolean var2;
      if(var1 == 1) {
         var2 = true;
      } else {
         if(var1 != 2) {
            StringBuilder var3 = (new StringBuilder()).append("not a boolean: ");
            Object var4 = this.decodeObject(var1);
            String var5 = var3.append(var4).toString();
            throw new IOException(var5);
         }

         var2 = false;
      }

      return var2;
   }

   public Date readDate() throws IOException {
      byte var1 = this.m_in.readByte();
      Date var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         if(var1 != 7) {
            StringBuilder var5 = (new StringBuilder()).append("not a date: ");
            Object var6 = this.decodeObject(var1);
            String var7 = var5.append(var6).toString();
            throw new IOException(var7);
         }

         long var3 = this.m_in.readLong();
         var2 = new Date(var3);
      }

      return var2;
   }

   public int readInt() throws IOException {
      byte var1 = this.m_in.readByte();
      if(var1 == 4) {
         return this.m_in.readInt();
      } else {
         StringBuilder var2 = (new StringBuilder()).append("not an int: ");
         Object var3 = this.decodeObject(var1);
         String var4 = var2.append(var3).toString();
         throw new IOException(var4);
      }
   }

   public IntArrayMap readIntArrayMap() throws IOException {
      byte var1 = this.m_in.readByte();
      IntArrayMap var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         if(var1 != 11) {
            StringBuilder var3 = (new StringBuilder()).append("not an int array map: ");
            Object var4 = this.decodeObject(var1);
            String var5 = var3.append(var4).toString();
            throw new IOException(var5);
         }

         var2 = this.readIntArrayMapData();
      }

      return var2;
   }

   public IntArrayMap readIntArrayMapData() throws IOException {
      int var1 = this.m_in.readInt();
      if(var1 > 262144) {
         String var2 = "Data structure exceeds maximum (" + var1 + " > " + 262144;
         throw new IOException(var2);
      } else {
         IntArrayMap var3 = new IntArrayMap(var1);
         int var4 = var1;

         while(true) {
            var4 += -1;
            if(var4 < 0) {
               return var3;
            }

            int var5 = this.m_in.readInt();
            Object var6 = this.read();
            var3.uncheckedAdd(var5, var6);
         }
      }
   }

   public List readList() throws IOException {
      byte var1 = this.m_in.readByte();
      List var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         if(var1 != 9) {
            StringBuilder var3 = (new StringBuilder()).append("not a string: ");
            Object var4 = this.decodeObject(var1);
            String var5 = var3.append(var4).toString();
            throw new IOException(var5);
         }

         var2 = this.readListData();
      }

      return var2;
   }

   public List readListData() throws IOException {
      int var1 = this.m_in.readInt();
      if(var1 > 262144) {
         String var2 = "Data structure exceeds maximum (" + var1 + " > " + 262144;
         throw new IOException(var2);
      } else {
         ArrayList var3 = new ArrayList(var1);
         int var4 = var1;

         while(true) {
            var4 += -1;
            if(var4 < 0) {
               return var3;
            }

            Object var5 = this.read();
            var3.add(var5);
         }
      }
   }

   public long readLong() throws IOException {
      byte var1 = this.m_in.readByte();
      if(var1 == 5) {
         return this.m_in.readLong();
      } else {
         StringBuilder var2 = (new StringBuilder()).append("not a long: ");
         Object var3 = this.decodeObject(var1);
         String var4 = var2.append(var3).toString();
         throw new IOException(var4);
      }
   }

   public short readShort() throws IOException {
      byte var1 = this.m_in.readByte();
      if(var1 == 3) {
         return this.m_in.readShort();
      } else {
         StringBuilder var2 = (new StringBuilder()).append("not a short: ");
         Object var3 = this.decodeObject(var1);
         String var4 = var2.append(var3).toString();
         throw new IOException(var4);
      }
   }

   public String readString() throws IOException {
      byte var1 = this.m_in.readByte();
      String var2;
      if(var1 == 0) {
         var2 = null;
      } else {
         if(var1 != 6) {
            StringBuilder var3 = (new StringBuilder()).append("not a string: ");
            Object var4 = this.decodeObject(var1);
            String var5 = var3.append(var4).toString();
            throw new IOException(var5);
         }

         var2 = this.readStringData();
      }

      return var2;
   }

   public String readStringData() throws IOException {
      byte var1 = this.m_in.readByte();
      if(var1 != 1 && var1 != 2 && var1 != 3) {
         String var2 = "unsupported encoding " + var1;
         throw new IOException(var2);
      } else {
         int var3 = this.m_in.readInt();
         if(var3 > 262144) {
            String var4 = "Data structure exceeds maximum (" + var3 + " > " + 262144;
            throw new IOException(var4);
         } else {
            byte[] var5 = new byte[var3];
            this.m_in.readFully(var5);
            String var6;
            if(var1 == 1) {
               var6 = new String(var5, "UTF-8");
            } else if(var1 == 2) {
               var6 = new String(var5, "UTF-16LE");
            } else {
               var6 = new String(var5, "UTF-16BE");
            }

            return var6;
         }
      }
   }

   public void write(Object var1) throws IOException {
      if(var1 == null) {
         this.writeNull();
      } else if(var1 instanceof IntArrayMap) {
         IntArrayMap var2 = (IntArrayMap)var1;
         this.writeIntArrayMap(var2);
      } else if(var1 instanceof String) {
         String var3 = (String)var1;
         this.writeString(var3);
      } else if(var1 instanceof Boolean) {
         Boolean var4 = (Boolean)var1;
         this.writeBoolean(var4);
      } else if(var1 instanceof Short) {
         Short var5 = (Short)var1;
         this.writeShort(var5);
      } else if(var1 instanceof Integer) {
         Integer var6 = (Integer)var1;
         this.writeInt(var6);
      } else if(var1 instanceof Long) {
         Long var7 = (Long)var1;
         this.writeLong(var7);
      } else if(var1 instanceof Date) {
         Date var8 = (Date)var1;
         this.writeDate(var8);
      } else if(var1 instanceof List) {
         List var9 = (List)var1;
         this.writeList(var9);
      } else if(var1 instanceof byte[]) {
         byte[] var10 = (byte[])((byte[])var1);
         this.writeBlobData(var10);
      } else {
         StringBuilder var11 = (new StringBuilder()).append("Cannot marshal object: ").append(var1).append(" of type:");
         String var12 = var1.getClass().getName();
         String var13 = var11.append(var12).toString();
         throw new IOException(var13);
      }
   }

   public void writeBlobData(byte[] var1) throws IOException {
      this.m_out.writeByte(8);
      DataOutputStream var2 = this.m_out;
      int var3 = var1.length;
      var2.writeInt(var3);
      this.m_out.write(var1);
   }

   public void writeBoolean(Boolean var1) throws IOException {
      DataOutputStream var2 = this.m_out;
      byte var3;
      if(var1.booleanValue()) {
         var3 = 1;
      } else {
         var3 = 2;
      }

      var2.writeByte(var3);
   }

   public void writeBoolean(boolean var1) throws IOException {
      DataOutputStream var2 = this.m_out;
      byte var3;
      if(var1) {
         var3 = 1;
      } else {
         var3 = 2;
      }

      var2.writeByte(var3);
   }

   public void writeDate(Date var1) throws IOException {
      this.m_out.writeByte(7);
      DataOutputStream var2 = this.m_out;
      long var3 = var1.getTime();
      var2.writeLong(var3);
   }

   public void writeInt(int var1) throws IOException {
      this.m_out.writeByte(4);
      this.m_out.writeInt(var1);
   }

   public void writeInt(Integer var1) throws IOException {
      this.m_out.writeByte(4);
      DataOutputStream var2 = this.m_out;
      int var3 = var1.intValue();
      var2.writeInt(var3);
   }

   public void writeIntArrayMap(IntArrayMap var1) throws IOException {
      int var2 = var1.size();
      this.m_out.writeByte(11);
      this.m_out.writeInt(var2);

      for(int var3 = 0; var3 < var2; ++var3) {
         DataOutputStream var4 = this.m_out;
         int var5 = var1.getKeyAt(var3);
         var4.writeInt(var5);
         Object var6 = var1.getAt(var3);
         this.write(var6);
      }

   }

   public void writeList(List var1) throws IOException {
      this.m_out.writeByte(9);
      int var2 = var1.size();
      this.m_out.writeInt(var2);

      for(int var3 = 0; var3 < var2; ++var3) {
         Object var4 = var1.get(var3);
         this.write(var4);
      }

   }

   public void writeLong(long var1) throws IOException {
      this.m_out.writeByte(5);
      this.m_out.writeLong(var1);
   }

   public void writeLong(Long var1) throws IOException {
      this.m_out.writeByte(5);
      DataOutputStream var2 = this.m_out;
      long var3 = var1.longValue();
      var2.writeLong(var3);
   }

   public void writeNull() throws IOException {
      this.m_out.writeByte(0);
   }

   public void writeShort(Short var1) throws IOException {
      this.m_out.writeByte(3);
      DataOutputStream var2 = this.m_out;
      short var3 = var1.shortValue();
      var2.writeShort(var3);
   }

   public void writeShort(short var1) throws IOException {
      this.m_out.writeByte(3);
      this.m_out.writeShort(var1);
   }

   public void writeString(String var1) throws IOException {
      this.m_out.writeByte(6);
      this.m_out.writeByte(1);
      byte[] var2 = var1.getBytes("UTF-8");
      DataOutputStream var3 = this.m_out;
      int var4 = var2.length;
      var3.writeInt(var4);
      this.m_out.write(var2);
   }

   // $FF: synthetic class
   static class 1 {
   }

   private static class CountOutputStream extends OutputStream {

      int count;


      private CountOutputStream() {
         this.count = 0;
      }

      // $FF: synthetic method
      CountOutputStream(Z7Marshaller.1 var1) {
         this();
      }

      public void write(int var1) {
         int var2 = this.count + 1;
         this.count = var2;
      }

      public void write(byte[] var1, int var2, int var3) {
         int var4 = this.count + var3;
         this.count = var4;
      }
   }
}
