package com.android.common;

import android.database.AbstractCursor;
import android.database.CursorWindow;
import java.util.ArrayList;

public class ArrayListCursor extends AbstractCursor {

   private String[] mColumnNames;
   private ArrayList<Object>[] mRows;


   public ArrayListCursor(String[] var1, ArrayList<ArrayList> var2) {
      int var3 = var1.length;
      boolean var4 = false;

      for(int var5 = 0; var5 < var3; ++var5) {
         if(var1[var5].compareToIgnoreCase("_id") == 0) {
            this.mColumnNames = var1;
            var4 = true;
            break;
         }
      }

      if(!var4) {
         String[] var6 = new String[var3 + 1];
         this.mColumnNames = var6;
         String[] var7 = this.mColumnNames;
         int var8 = var1.length;
         System.arraycopy(var1, 0, var7, 0, var8);
         this.mColumnNames[var3] = "_id";
      }

      int var9 = var2.size();
      ArrayList[] var10 = new ArrayList[var9];
      this.mRows = var10;

      for(int var11 = 0; var11 < var9; ++var11) {
         ArrayList[] var12 = this.mRows;
         ArrayList var13 = (ArrayList)var2.get(var11);
         var12[var11] = var13;
         if(!var4) {
            ArrayList var14 = this.mRows[var11];
            Integer var15 = Integer.valueOf(var11);
            var14.add(var15);
         }
      }

   }

   public void fillWindow(int var1, CursorWindow var2) {
      if(var1 >= 0) {
         int var3 = this.getCount();
         if(var1 <= var3) {
            var2.acquireReference();

            try {
               int var4 = this.mPos;
               int var5 = var1 + -1;
               this.mPos = var5;
               var2.clear();
               var2.setStartPosition(var1);
               int var6 = this.getColumnCount();
               var2.setNumColumns(var6);

               label108:
               while(true) {
                  if(this.moveToNext() && var2.allocRow()) {
                     int var8 = 0;

                     while(true) {
                        if(var8 >= var6) {
                           continue label108;
                        }

                        ArrayList[] var9 = this.mRows;
                        int var10 = this.mPos;
                        Object var11 = var9[var10].get(var8);
                        if(var11 != null) {
                           if(var11 instanceof byte[]) {
                              byte[] var12 = (byte[])((byte[])var11);
                              int var13 = this.mPos;
                              if(!var2.putBlob(var12, var13, var8)) {
                                 var2.freeLastRow();
                                 continue label108;
                              }
                           } else {
                              String var15 = var11.toString();
                              int var16 = this.mPos;
                              if(!var2.putString(var15, var16, var8)) {
                                 var2.freeLastRow();
                                 continue label108;
                              }
                           }
                        } else {
                           int var18 = this.mPos;
                           if(!var2.putNull(var18, var8)) {
                              var2.freeLastRow();
                              continue label108;
                           }
                        }

                        ++var8;
                     }
                  }

                  this.mPos = var4;
                  return;
               }
            } catch (IllegalStateException var21) {
               ;
            } finally {
               var2.releaseReference();
            }

         }
      }
   }

   public byte[] getBlob(int var1) {
      ArrayList[] var2 = this.mRows;
      int var3 = this.mPos;
      return (byte[])((byte[])var2[var3].get(var1));
   }

   public String[] getColumnNames() {
      return this.mColumnNames;
   }

   public int getCount() {
      return this.mRows.length;
   }

   public double getDouble(int var1) {
      ArrayList[] var2 = this.mRows;
      int var3 = this.mPos;
      return ((Number)var2[var3].get(var1)).doubleValue();
   }

   public float getFloat(int var1) {
      ArrayList[] var2 = this.mRows;
      int var3 = this.mPos;
      return ((Number)var2[var3].get(var1)).floatValue();
   }

   public int getInt(int var1) {
      ArrayList[] var2 = this.mRows;
      int var3 = this.mPos;
      return ((Number)var2[var3].get(var1)).intValue();
   }

   public long getLong(int var1) {
      ArrayList[] var2 = this.mRows;
      int var3 = this.mPos;
      return ((Number)var2[var3].get(var1)).longValue();
   }

   public short getShort(int var1) {
      ArrayList[] var2 = this.mRows;
      int var3 = this.mPos;
      return ((Number)var2[var3].get(var1)).shortValue();
   }

   public String getString(int var1) {
      ArrayList[] var2 = this.mRows;
      int var3 = this.mPos;
      Object var4 = var2[var3].get(var1);
      String var5;
      if(var4 == null) {
         var5 = null;
      } else {
         var5 = var4.toString();
      }

      return var5;
   }

   public boolean isNull(int var1) {
      ArrayList[] var2 = this.mRows;
      int var3 = this.mPos;
      boolean var4;
      if(var2[var3].get(var1) == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }
}
