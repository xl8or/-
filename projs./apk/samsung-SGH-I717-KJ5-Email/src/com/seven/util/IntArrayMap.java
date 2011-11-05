package com.seven.util;

import com.seven.util.Z7Error;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IntArrayMap implements Cloneable {

   protected static final int KEY_NOT_FOUND = 255;
   protected static final int MINIMUM_CAPACITY = 10;
   protected int m_count;
   protected int[] m_keys;
   protected Object[] m_values;


   public IntArrayMap() {
      this(10);
   }

   public IntArrayMap(int var1) {
      if(var1 < 10) {
         var1 = 10;
      }

      int[] var2 = new int[var1];
      this.m_keys = var2;
      Object[] var3 = new Object[var1];
      this.m_values = var3;
      this.m_count = 0;
   }

   public IntArrayMap(IntArrayMap var1) {
      int var2;
      if(var1 == null) {
         var2 = 10;
      } else {
         var2 = var1.size();
      }

      this(var2);
      if(var1 != null) {
         this.uncheckedAddAll(var1);
      }
   }

   public void clear() {
      int var1 = this.m_count;

      while(true) {
         var1 += -1;
         if(var1 < 0) {
            this.m_count = 0;
            return;
         }

         this.m_values[var1] = false;
      }
   }

   public Object clone() {
      int var1 = this.m_count;
      IntArrayMap var2 = new IntArrayMap(var1);
      var2.uncheckedAddAll(this);
      return var2;
   }

   public boolean containsKey(int var1) {
      boolean var2;
      if(this.indexOfKey(var1) != -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean containsValue(Object var1) {
      boolean var2;
      if(var1 == null) {
         var2 = true;
      } else {
         var2 = false;
      }

      int var3 = this.m_count;

      boolean var5;
      while(true) {
         var3 += -1;
         if(var3 >= 0) {
            Object var4 = this.m_values[var3];
            if(var2) {
               if(var4 == null) {
                  var5 = true;
               } else {
                  var5 = false;
               }
               break;
            }

            if(!var1.equals(var4)) {
               continue;
            }

            var5 = true;
            break;
         }

         var5 = false;
         break;
      }

      return var5;
   }

   public Object get(int var1) {
      int var2 = this.indexOfKey(var1);
      Object var3;
      if(var2 == -1) {
         var3 = null;
      } else {
         var3 = this.m_values[var2];
      }

      return var3;
   }

   public Object getAt(int var1) {
      return this.m_values[var1];
   }

   public boolean getBoolean(int var1, boolean var2) {
      Object var3 = this.get(var1);
      boolean var4;
      if(!(var3 instanceof Boolean)) {
         var4 = var2;
      } else {
         var4 = ((Boolean)var3).booleanValue();
      }

      return var4;
   }

   public byte getByte(int var1, byte var2) {
      Object var3 = this.get(var1);
      byte var4;
      if(var3 instanceof Byte) {
         var4 = ((Byte)var3).byteValue();
      } else {
         var4 = var2;
      }

      return var4;
   }

   public byte[] getBytes(int var1) {
      Object var2 = this.get(var1);
      byte[] var3;
      if(!(var2 instanceof byte[])) {
         var3 = null;
      } else {
         var3 = (byte[])((byte[])var2);
      }

      return var3;
   }

   public Date getDate(int var1) {
      Object var2 = this.get(var1);
      Date var3;
      if(!(var2 instanceof Date)) {
         var3 = null;
      } else {
         var3 = (Date)var2;
      }

      return var3;
   }

   public Z7Error getError(int var1) {
      Object var2 = this.get(var1);
      Z7Error var3;
      if(!(var2 instanceof Z7Error)) {
         var3 = null;
      } else {
         var3 = (Z7Error)var2;
      }

      return var3;
   }

   public int getInt(int var1, int var2) {
      Object var3 = this.get(var1);
      int var4;
      if(var3 instanceof Integer) {
         var4 = ((Integer)var3).intValue();
      } else if(var3 instanceof Short) {
         var4 = ((Short)var3).shortValue();
      } else if(var3 instanceof Byte) {
         var4 = ((Byte)var3).byteValue();
      } else {
         var4 = var2;
      }

      return var4;
   }

   public IntArrayMap getIntArrayMap(int var1) {
      Object var2 = this.get(var1);
      IntArrayMap var3;
      if(!(var2 instanceof IntArrayMap)) {
         var3 = null;
      } else {
         var3 = (IntArrayMap)var2;
      }

      return var3;
   }

   public int getKeyAt(int var1) {
      return this.m_keys[var1];
   }

   public int[] getKeys() {
      int[] var1;
      if(this.m_count == 0) {
         var1 = null;
      } else {
         int[] var2 = new int[this.m_count];
         int[] var3 = this.m_keys;
         int var4 = this.m_count;
         System.arraycopy(var3, 0, var2, 0, var4);
         var1 = var2;
      }

      return var1;
   }

   public List getList(int var1) {
      Object var2 = this.get(var1);
      List var3;
      if(!(var2 instanceof List)) {
         var3 = null;
      } else {
         var3 = (List)var2;
      }

      return var3;
   }

   public long getLong(int var1, long var2) {
      Object var4 = this.get(var1);
      long var5;
      if(var4 instanceof Long) {
         var5 = ((Long)var4).longValue();
      } else if(var4 instanceof Integer) {
         var5 = (long)((Integer)var4).intValue();
      } else if(var4 instanceof Short) {
         var5 = (long)((Short)var4).shortValue();
      } else if(var4 instanceof Byte) {
         var5 = (long)((Byte)var4).byteValue();
      } else {
         var5 = var2;
      }

      return var5;
   }

   public short getShort(int var1, short var2) {
      Object var3 = this.get(var1);
      short var4;
      if(var3 instanceof Short) {
         var4 = ((Short)var3).shortValue();
      } else if(var3 instanceof Byte) {
         var4 = (short)((Byte)var3).byteValue();
      } else {
         var4 = var2;
      }

      return var4;
   }

   public String getString(int var1) {
      return this.getString(var1, (String)null);
   }

   public String getString(int var1, String var2) {
      Object var3 = this.get(var1);
      String var4;
      if(!(var3 instanceof String)) {
         var4 = var2;
      } else {
         var4 = (String)var3;
      }

      return var4;
   }

   public List getValues() {
      int var1 = this.m_count;
      ArrayList var2 = new ArrayList(var1);

      for(int var3 = 0; var3 < var1; ++var3) {
         Object var4 = this.m_values[var3];
         var2.add(var4);
      }

      return var2;
   }

   protected void growIfNeeded(int var1) {
      int var2 = this.m_count + var1;
      int var3 = this.m_keys.length;
      if(var2 > var3) {
         int var4 = (this.m_count * 3 >> 1) + 1;
         if(var4 < var2) {
            var4 = var2;
         }

         int[] var5 = this.m_keys;
         int[] var6 = new int[var4];
         this.m_keys = var6;
         int var7 = this.m_count;
         System.arraycopy(var5, 0, var6, 0, var7);
         Object[] var8 = this.m_values;
         Object[] var9 = new Object[var4];
         this.m_values = var9;
         int var10 = this.m_count;
         System.arraycopy(var8, 0, var9, 0, var10);
      }
   }

   protected int indexOfKey(int var1) {
      int var2 = this.m_count;

      int var4;
      while(true) {
         var2 += -1;
         if(var2 >= 0) {
            int var3 = this.m_keys[var2];
            if(var1 != var3) {
               continue;
            }

            var4 = var2;
            break;
         }

         var4 = -1;
         break;
      }

      return var4;
   }

   public boolean isEmpty() {
      boolean var1;
      if(this.m_count == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Object put(int var1, Object var2) {
      int var3 = this.indexOfKey(var1);
      Object var5;
      if(var3 != -1) {
         Object var4 = this.m_values[var3];
         this.m_values[var3] = var2;
         var5 = var4;
      } else {
         this.growIfNeeded(1);
         int[] var6 = this.m_keys;
         int var7 = this.m_count;
         var6[var7] = var1;
         Object[] var8 = this.m_values;
         int var9 = this.m_count;
         int var10 = var9 + 1;
         this.m_count = var10;
         var8[var9] = var2;
         var5 = null;
      }

      return var5;
   }

   public void putAll(IntArrayMap var1) {
      int var2 = var1.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = var1.m_keys[var3];
         Object var5 = var1.m_values[var3];
         this.put(var4, var5);
      }

   }

   public Object putAt(int var1, Object var2) {
      Object var3 = this.m_values[var1];
      this.m_values[var1] = var2;
      return var3;
   }

   public Object putIfNotNull(int var1, Object var2) {
      Object var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = this.put(var1, var2);
      }

      return var3;
   }

   public int putKeyAt(int var1, int var2) {
      int var3 = this.m_keys[var1];
      this.m_keys[var1] = var2;
      return var3;
   }

   public Object remove(int var1) {
      int var2 = this.indexOfKey(var1);
      Object var3;
      if(var2 == -1) {
         var3 = null;
      } else {
         var3 = this.removeAt(var2);
      }

      return var3;
   }

   public Object removeAt(int var1) {
      Object var2 = this.m_values[var1];
      int var3 = this.m_count - var1 - 1;
      if(var3 > 0) {
         int[] var4 = this.m_keys;
         int var5 = var1 + 1;
         int[] var6 = this.m_keys;
         System.arraycopy(var4, var5, var6, var1, var3);
         Object[] var7 = this.m_values;
         int var8 = var1 + 1;
         Object[] var9 = this.m_values;
         System.arraycopy(var7, var8, var9, var1, var3);
      }

      int var10 = this.m_count - 1;
      this.m_count = var10;
      int[] var11 = this.m_keys;
      int var12 = this.m_count;
      var11[var12] = 0;
      Object[] var13 = this.m_values;
      int var14 = this.m_count;
      var13[var14] = false;
      return var2;
   }

   public int size() {
      return this.m_count;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{");
      int var2 = this.m_count;

      for(int var3 = 0; var3 < var2; ++var3) {
         if(var3 != 0) {
            StringBuffer var4 = var1.append(", ");
         }

         int var5 = this.m_keys[var3];
         StringBuffer var6 = var1.append(var5).append('=');
         Object var7 = this.m_values[var3];
         if(var7 instanceof String) {
            StringBuffer var8 = var1.append('\"').append(var7).append('\"');
         } else {
            var1.append(var7);
         }
      }

      return var1.append('}').toString();
   }

   public void uncheckedAdd(int var1, Object var2) {
      this.growIfNeeded(1);
      int[] var3 = this.m_keys;
      int var4 = this.m_count;
      var3[var4] = var1;
      Object[] var5 = this.m_values;
      int var6 = this.m_count;
      int var7 = var6 + 1;
      this.m_count = var7;
      var5[var6] = var2;
   }

   public void uncheckedAddAll(IntArrayMap var1) {
      int var2 = var1.m_count;
      this.growIfNeeded(var2);
      int[] var3 = var1.m_keys;
      int[] var4 = this.m_keys;
      int var5 = this.m_count;
      System.arraycopy(var3, 0, var4, var5, var2);
      Object[] var6 = var1.m_values;
      Object[] var7 = this.m_values;
      int var8 = this.m_count;
      System.arraycopy(var6, 0, var7, var8, var2);
      int var9 = this.m_count + var2;
      this.m_count = var9;
   }
}
