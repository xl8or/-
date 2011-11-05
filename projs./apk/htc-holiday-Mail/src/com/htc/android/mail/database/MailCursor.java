package com.htc.android.mail.database;

import android.database.AbstractCursor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import com.htc.android.mail.AbsRequestController;
import com.htc.android.mail.MailMessage;
import com.htc.android.mail.util.SparseArray;
import com.htc.android.mail.util.SparseLongArray;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

public class MailCursor extends AbstractCursor {

   private int mCountDeleted;
   private Cursor mCursor;
   private SparseArray<HashMap<String, Integer>> mGroupReadNums = null;
   private SparseArray<Map<String, Integer>> mGroupToPosMap = null;
   private SparseLongArray mIdToPosMap = null;
   private DataSetObserver mObserver;
   private LinkedList<Integer> mOffsetToNewList;


   public MailCursor(Cursor var1) {
      LinkedList var2 = new LinkedList();
      this.mOffsetToNewList = var2;
      this.mCountDeleted = 0;
      MailCursor.1 var3 = new MailCursor.1();
      this.mObserver = var3;
      this.mCursor = var1;
      if(this.mCursor != null) {
         Cursor var4 = this.mCursor;
         DataSetObserver var5 = this.mObserver;
         var4.registerDataSetObserver(var5);
      }
   }

   public void close() {
      if(this.mCursor != null) {
         this.mCursor.close();
      }
   }

   public boolean commitUpdates() {
      boolean var1 = this.mCursor.commitUpdates();
      this.onChange((boolean)1);
      return true;
   }

   public void deactivate() {
      if(this.mCursor != null) {
         this.mCursor.deactivate();
      }
   }

   public void delete(MailMessage var1) {
      if(this.mIdToPosMap != null) {
         SparseLongArray var2 = this.mIdToPosMap;
         long var3 = var1.id;
         int var5 = (int)var2.get(var3, 65535L);
         if(var5 != -1) {
            SparseLongArray var6 = this.mIdToPosMap;
            long var7 = var1.id;
            var6.put(var7, 65535L);
            int var9 = this.mCountDeleted + 1;
            this.mCountDeleted = var9;
            int var10 = 0;

            for(ListIterator var11 = this.mOffsetToNewList.listIterator(0); var11.hasNext(); ++var10) {
               Integer var12 = (Integer)var11.next();
               int var13 = var12.intValue();
               if(var5 < var13) {
                  break;
               }

               int var18 = var12.intValue();
               if(var5 == var18) {
                  var10 = -1;
                  break;
               }
            }

            if(var10 != -1) {
               LinkedList var14 = this.mOffsetToNewList;
               Integer var15 = new Integer(var5);
               var14.add(var10, var15);
               int var10000 = var5 - var10;
            }

            this.mPos = -1;
            boolean var17 = this.mCursor.moveToPosition(-1);
         }
      }
   }

   public boolean deleteRow() {
      return this.mCursor.deleteRow();
   }

   public byte[] getBlob(int var1) {
      return this.mCursor.getBlob(var1);
   }

   public String[] getColumnNames() {
      String[] var1;
      if(this.mCursor != null) {
         var1 = this.mCursor.getColumnNames();
      } else {
         var1 = new String[0];
      }

      return var1;
   }

   public int getCount() {
      int var1 = this.mCursor.getCount();
      int var2 = this.mCountDeleted;
      int var3 = var1 - var2;
      if(var3 < 0) {
         var3 = 0;
      }

      return var3;
   }

   public double getDouble(int var1) {
      return this.mCursor.getDouble(var1);
   }

   public float getFloat(int var1) {
      return this.mCursor.getFloat(var1);
   }

   public int getGroupReadNum(long var1, String var3) {
      int var5;
      if(this.mGroupReadNums != null) {
         HashMap var4 = (HashMap)this.mGroupReadNums.get(var1);
         if(var4 == null) {
            var5 = 0;
            return var5;
         }

         Integer var6 = (Integer)var4.get(var3);
         if(var6 != null) {
            var5 = var6.intValue();
            return var5;
         }
      }

      var5 = 0;
      return var5;
   }

   public int getInt(int var1) {
      return this.mCursor.getInt(var1);
   }

   public long getLong(int var1) {
      return this.mCursor.getLong(var1);
   }

   public int getPositionByGroup(long var1, String var3) {
      int var5;
      if(this.mGroupToPosMap != null) {
         Map var4 = (Map)this.mGroupToPosMap.get(var1);
         if(var4 == null) {
            var5 = -1;
         } else {
            Integer var6 = (Integer)var4.get(var3);
            if(var6 == null) {
               var5 = -1;
            } else {
               var5 = var6.intValue();
            }
         }
      } else {
         var5 = -1;
      }

      return var5;
   }

   public int getPositionById(long var1) {
      int var3;
      if(this.mIdToPosMap != null) {
         var3 = (int)this.mIdToPosMap.get(var1, 65535L);
      } else {
         var3 = -1;
      }

      return var3;
   }

   public short getShort(int var1) {
      return this.mCursor.getShort(var1);
   }

   public String getString(int var1) {
      return this.mCursor.getString(var1);
   }

   public void increaseGroupReadNum(long var1, String var3, int var4) {
      if(this.mGroupReadNums != null) {
         HashMap var5 = (HashMap)this.mGroupReadNums.get(var1);
         if(var5 != null) {
            Integer var6 = (Integer)var5.get(var3);
            if(var6 != null) {
               int var7 = var6.intValue() + var4;
               if(var7 < 0) {
                  var7 = 0;
               }

               Integer var8 = Integer.valueOf(var7);
               var5.put(var3, var8);
            }
         }
      }
   }

   public boolean isClosed() {
      boolean var1;
      if(this.mCursor != null) {
         var1 = this.mCursor.isClosed();
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isNull(int var1) {
      return this.mCursor.isNull(var1);
   }

   public boolean onMove(int var1, int var2) {
      boolean var12;
      if(this.mCursor != null) {
         ListIterator var3 = this.mOffsetToNewList.listIterator(0);
         new Integer(0);
         int var5 = 0;
         int var6 = -1;

         Integer var8;
         for(int var7 = 0; var3.hasNext(); var6 = var8.intValue()) {
            var8 = (Integer)var3.next();
            int var9 = var8.intValue() - var6 - 1;
            var7 += var9;
            int var10 = var2 + 1;
            if(var7 >= var10) {
               break;
            }

            ++var5;
         }

         int var11 = var2 + var5;
         var12 = this.mCursor.moveToPosition(var11);
      } else {
         var12 = false;
      }

      return var12;
   }

   public void prepareIdToPosMap(boolean var1) {
      int var2 = this.mCursor.getColumnIndexOrThrow("_id");
      int var3 = -1;
      int var4 = this.mCursor.getColumnIndexOrThrow("_account");
      int var5 = this.mCursor.getColumnIndexOrThrow("_group");
      if(var1) {
         var3 = this.mCursor.getColumnIndexOrThrow("readcount");
         SparseArray var6 = new SparseArray();
         this.mGroupReadNums = var6;
         SparseArray var7 = new SparseArray();
         this.mGroupToPosMap = var7;
      }

      boolean var8 = this.mCursor.moveToPosition(-1);
      int var9 = 0;
      int var10 = this.mCursor.getCount();
      SparseLongArray var11 = new SparseLongArray(var10);

      for(this.mIdToPosMap = var11; this.mCursor.moveToNext(); ++var9) {
         long var12 = this.mCursor.getLong(var2);
         SparseLongArray var14 = this.mIdToPosMap;
         long var15 = (long)var9;
         var14.put(var12, var15);
         long var17 = this.mCursor.getLong(var4);
         String var19 = this.mCursor.getString(var5);
         if(var1) {
            int var20 = this.mCursor.getInt(var3);
            this.setGroupReadNum(var17, var19, var20);
            Object var21 = (Map)this.mGroupToPosMap.get(var17);
            if(var21 == null) {
               var21 = new HashMap();
               this.mGroupToPosMap.put(var17, var21);
            }

            Integer var22 = new Integer(var9);
            ((Map)var21).put(var19, var22);
         }
      }

      boolean var24 = this.mCursor.moveToPosition(-1);
   }

   public void registerContentObserver(ContentObserver var1) {
      if(this.mCursor != null) {
         this.mCursor.registerContentObserver(var1);
      }
   }

   public void registerDataSetObserver(DataSetObserver var1) {
      if(this.mCursor != null) {
         this.mCursor.registerDataSetObserver(var1);
      }
   }

   public boolean requery() {
      boolean var1;
      if(this.mCursor != null && !this.mCursor.requery()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void setGroupReadNum(long var1, String var3, int var4) {
      if(this.mGroupReadNums != null) {
         HashMap var5 = (HashMap)this.mGroupReadNums.get(var1);
         if(var5 == null) {
            var5 = new HashMap();
            this.mGroupReadNums.put(var1, var5);
         }

         Integer var6 = new Integer(var4);
         var5.put(var3, var6);
      }
   }

   public void setMessageStatus(AbsRequestController.MessageStatus var1) {
      if(var1 != null) {
         MailMessage[] var2 = var1.getIdList();
         int var3 = 0;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               return;
            }

            MailMessage var5 = var2[var3];
            this.delete(var5);
            ++var3;
         }
      }
   }

   public void unregisterContentObserver(ContentObserver var1) {
      if(this.mCursor != null) {
         this.mCursor.unregisterContentObserver(var1);
      }
   }

   public void unregisterDataSetObserver(DataSetObserver var1) {
      if(this.mCursor != null) {
         this.mCursor.unregisterDataSetObserver(var1);
      }
   }

   class 1 extends DataSetObserver {

      1() {}

      public void onChanged() {
         int var1 = MailCursor.this.mPos = -1;
      }

      public void onInvalidated() {
         int var1 = MailCursor.this.mPos = -1;
      }
   }
}
