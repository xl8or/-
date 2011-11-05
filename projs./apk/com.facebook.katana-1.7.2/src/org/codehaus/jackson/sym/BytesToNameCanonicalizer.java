package org.codehaus.jackson.sym;

import org.codehaus.jackson.sym.Name;
import org.codehaus.jackson.sym.Name1;
import org.codehaus.jackson.sym.Name2;
import org.codehaus.jackson.sym.Name3;
import org.codehaus.jackson.sym.NameN;
import org.codehaus.jackson.util.InternCache;

public final class BytesToNameCanonicalizer {

   protected static final int DEFAULT_TABLE_SIZE = 64;
   static final int INITIAL_COLLISION_LEN = 32;
   static final int LAST_VALID_BUCKET = 254;
   static final int MAX_TABLE_SIZE = 6000;
   static final int MIN_HASH_SIZE = 16;
   private int _collCount;
   private int _collEnd;
   private BytesToNameCanonicalizer.Bucket[] _collList;
   private boolean _collListShared;
   private int _count;
   private int[] _mainHash;
   private int _mainHashMask;
   private boolean _mainHashShared;
   private Name[] _mainNames;
   private boolean _mainNamesShared;
   private transient boolean _needRehash;
   final BytesToNameCanonicalizer _parent;


   private BytesToNameCanonicalizer(int var1) {
      this._parent = null;
      int var2;
      if(var1 < 16) {
         var2 = 16;
      } else if((var1 - 1 & var1) != 0) {
         for(var2 = 16; var2 < var1; var2 += var2) {
            ;
         }
      } else {
         var2 = var1;
      }

      this.initTables(var2);
   }

   private BytesToNameCanonicalizer(BytesToNameCanonicalizer var1) {
      this._parent = var1;
      int var2 = var1._count;
      this._count = var2;
      int var3 = var1._mainHashMask;
      this._mainHashMask = var3;
      int[] var4 = var1._mainHash;
      this._mainHash = var4;
      Name[] var5 = var1._mainNames;
      this._mainNames = var5;
      BytesToNameCanonicalizer.Bucket[] var6 = var1._collList;
      this._collList = var6;
      int var7 = var1._collCount;
      this._collCount = var7;
      int var8 = var1._collEnd;
      this._collEnd = var8;
      this._needRehash = (boolean)0;
      this._mainHashShared = (boolean)1;
      this._mainNamesShared = (boolean)1;
      this._collListShared = (boolean)1;
   }

   private void _addSymbol(int var1, Name var2) {
      if(this._mainHashShared) {
         this.unshareMain();
      }

      if(this._needRehash) {
         this.rehash();
      }

      int var3 = this._count + 1;
      this._count = var3;
      int var4 = this._mainHashMask & var1;
      if(this._mainNames[var4] == false) {
         int[] var5 = this._mainHash;
         int var6 = var1 << 8;
         var5[var4] = var6;
         if(this._mainNamesShared) {
            this.unshareNames();
         }

         this._mainNames[var4] = var2;
      } else {
         if(this._collListShared) {
            this.unshareCollision();
         }

         int var13 = this._collCount + 1;
         this._collCount = var13;
         int var14 = this._mainHash[var4];
         int var15 = var14 & 255;
         if(var15 == 0) {
            if(this._collEnd <= 254) {
               var15 = this._collEnd;
               int var16 = this._collEnd + 1;
               this._collEnd = var16;
               int var17 = this._collList.length;
               if(var15 >= var17) {
                  this.expandCollision();
               }
            } else {
               var15 = this.findBestBucket();
            }

            int[] var18 = this._mainHash;
            int var19 = var14 & -256;
            int var20 = var15 + 1;
            int var21 = var19 | var20;
            var18[var4] = var21;
            var4 = var15;
         } else {
            var4 = var15 + -1;
         }

         BytesToNameCanonicalizer.Bucket[] var22 = this._collList;
         BytesToNameCanonicalizer.Bucket var23 = this._collList[var4];
         BytesToNameCanonicalizer.Bucket var24 = new BytesToNameCanonicalizer.Bucket(var2, var23);
         var22[var4] = var24;
      }

      int var7 = this._mainHash.length;
      int var8 = this._count;
      int var9 = var7 >> 1;
      if(var8 > var9) {
         int var10 = var7 >> 2;
         int var11 = this._count;
         int var12 = var7 - var10;
         if(var11 > var12) {
            this._needRehash = (boolean)1;
         } else if(this._collCount >= var10) {
            this._needRehash = (boolean)1;
         }
      }
   }

   public static final int calcHash(int var0) {
      int var1 = var0 >>> 16 ^ var0;
      int var2 = var1 >>> 8;
      return var1 ^ var2;
   }

   public static final int calcHash(int var0, int var1) {
      int var2 = var0 * 31 + var1;
      int var3 = var2 >>> 16;
      int var4 = var2 ^ var3;
      int var5 = var4 >>> 8;
      return var4 ^ var5;
   }

   public static final int calcHash(int[] var0, int var1) {
      int var2 = var0[0];
      byte var3 = 1;
      int var4 = var2;

      for(int var5 = var3; var5 < var1; ++var5) {
         int var6 = var4 * 31;
         int var7 = var0[var5];
         var4 = var6 + var7;
      }

      int var8 = var4 >>> 16 ^ var4;
      int var9 = var8 >>> 8;
      return var8 ^ var9;
   }

   public static Name constructName(int var0, String var1, int var2, int var3) {
      String var4 = InternCache.instance.intern(var1);
      Object var5;
      if(var3 == 0) {
         var5 = new Name1(var4, var0, var2);
      } else {
         var5 = new Name2(var4, var0, var2, var3);
      }

      return (Name)var5;
   }

   public static Name constructName(int var0, String var1, int[] var2, int var3) {
      int var4 = 0;
      String var5 = InternCache.instance.intern(var1);
      Object var9;
      if(var3 < 4) {
         switch(var3) {
         case 1:
            int var8 = var2[0];
            var9 = new Name1(var5, var0, var8);
            return (Name)var9;
         case 2:
            int var10 = var2[0];
            int var11 = var2[1];
            var9 = new Name2(var5, var0, var10, var11);
            return (Name)var9;
         case 3:
            int var12 = var2[0];
            int var13 = var2[1];
            int var14 = var2[2];
            var9 = new Name3(var5, var0, var12, var13, var14);
            return (Name)var9;
         }
      }

      int[] var6;
      for(var6 = new int[var3]; var4 < var3; ++var4) {
         int var7 = var2[var4];
         var6[var4] = var7;
      }

      var9 = new NameN(var5, var0, var6, var3);
      return (Name)var9;
   }

   public static BytesToNameCanonicalizer createRoot() {
      return new BytesToNameCanonicalizer(64);
   }

   private void expandCollision() {
      BytesToNameCanonicalizer.Bucket[] var1 = this._collList;
      int var2 = var1.length;
      BytesToNameCanonicalizer.Bucket[] var3 = new BytesToNameCanonicalizer.Bucket[var2 + var2];
      this._collList = var3;
      BytesToNameCanonicalizer.Bucket[] var4 = this._collList;
      System.arraycopy(var1, 0, var4, 0, var2);
   }

   private int findBestBucket() {
      BytesToNameCanonicalizer.Bucket[] var1 = this._collList;
      byte var2 = -1;
      int var3 = this._collEnd;
      byte var4 = 0;
      int var5 = Integer.MAX_VALUE;
      int var6 = var4;

      int var8;
      while(true) {
         if(var6 >= var3) {
            var8 = var2;
            break;
         }

         int var7 = var1[var6].length();
         if(var7 < var5) {
            var2 = 1;
            if(var7 == var2) {
               var8 = var6;
               break;
            }

            var5 = var7;
         }

         ++var6;
      }

      return var8;
   }

   public static Name getEmptyName() {
      return Name1.getEmptyName();
   }

   private void initTables(int var1) {
      this._count = 0;
      int[] var2 = new int[var1];
      this._mainHash = var2;
      Name[] var3 = new Name[var1];
      this._mainNames = var3;
      this._mainHashShared = (boolean)0;
      this._mainNamesShared = (boolean)0;
      int var4 = var1 - 1;
      this._mainHashMask = var4;
      this._collListShared = (boolean)1;
      this._collList = null;
      this._collEnd = 0;
      this._needRehash = (boolean)0;
   }

   private void markAsShared() {
      this._mainHashShared = (boolean)1;
      this._mainNamesShared = (boolean)1;
      this._collListShared = (boolean)1;
   }

   private void mergeChild(BytesToNameCanonicalizer param1) {
      // $FF: Couldn't be decompiled
   }

   private void rehash() {
      this._needRehash = (boolean)0;
      this._mainNamesShared = (boolean)0;
      int var1 = this._mainHash.length;
      int[] var2 = new int[var1 + var1];
      this._mainHash = var2;
      int var3 = var1 + var1 - 1;
      this._mainHashMask = var3;
      Name[] var4 = this._mainNames;
      Name[] var5 = new Name[var1 + var1];
      this._mainNames = var5;
      int var6 = 0;

      int var7;
      for(var7 = 0; var6 < var1; ++var6) {
         Name var8 = var4[var6];
         if(var8 != null) {
            ++var7;
            int var9 = var8.hashCode();
            int var10 = this._mainHashMask & var9;
            this._mainNames[var10] = var8;
            int[] var11 = this._mainHash;
            int var12 = var9 << 8;
            var11[var10] = var12;
         }
      }

      int var13 = this._collEnd;
      if(var13 != 0) {
         this._collCount = 0;
         this._collEnd = 0;
         this._collListShared = (boolean)0;
         BytesToNameCanonicalizer.Bucket[] var41 = this._collList;
         BytesToNameCanonicalizer.Bucket[] var14 = new BytesToNameCanonicalizer.Bucket[var41.length];
         this._collList = var14;

         int var16;
         for(byte var42 = 0; var42 < var13; var7 = var16) {
            BytesToNameCanonicalizer.Bucket var15 = var41[var42];
            var16 = var7;

            for(BytesToNameCanonicalizer.Bucket var17 = var15; var17 != null; var17 = var17.mNext) {
               ++var16;
               Name var18 = var17.mName;
               int var19 = var18.hashCode();
               int var20 = this._mainHashMask & var19;
               int var21 = this._mainHash[var20];
               if(this._mainNames[var20] == false) {
                  int[] var22 = this._mainHash;
                  int var23 = var19 << 8;
                  var22[var20] = var23;
                  this._mainNames[var20] = var18;
               } else {
                  int var24 = this._collCount + 1;
                  this._collCount = var24;
                  int var25 = var21 & 255;
                  int var26;
                  if(var25 == 0) {
                     if(this._collEnd <= 254) {
                        var26 = this._collEnd;
                        int var27 = this._collEnd + 1;
                        this._collEnd = var27;
                        int var28 = this._collList.length;
                        if(var26 >= var28) {
                           this.expandCollision();
                        }
                     } else {
                        var26 = this.findBestBucket();
                     }

                     int[] var29 = this._mainHash;
                     int var30 = var21 & -256;
                     int var31 = var26 + 1;
                     int var32 = var30 | var31;
                     var29[var20] = var32;
                  } else {
                     var26 = var25 + -1;
                  }

                  BytesToNameCanonicalizer.Bucket[] var33 = this._collList;
                  BytesToNameCanonicalizer.Bucket var34 = this._collList[var26];
                  BytesToNameCanonicalizer.Bucket var35 = new BytesToNameCanonicalizer.Bucket(var18, var34);
                  var33[var26] = var35;
               }
            }

            int var36 = var42 + 1;
         }

         int var37 = this._count;
         if(var7 != var37) {
            StringBuilder var38 = (new StringBuilder()).append("Internal error: count after rehash ").append(var7).append("; should be ");
            int var39 = this._count;
            String var40 = var38.append(var39).toString();
            throw new RuntimeException(var40);
         }
      }
   }

   private void unshareCollision() {
      BytesToNameCanonicalizer.Bucket[] var1 = this._collList;
      if(var1 == null) {
         BytesToNameCanonicalizer.Bucket[] var2 = new BytesToNameCanonicalizer.Bucket[32];
         this._collList = var2;
      } else {
         int var3 = var1.length;
         BytesToNameCanonicalizer.Bucket[] var4 = new BytesToNameCanonicalizer.Bucket[var3];
         this._collList = var4;
         BytesToNameCanonicalizer.Bucket[] var5 = this._collList;
         System.arraycopy(var1, 0, var5, 0, var3);
      }

      this._collListShared = (boolean)0;
   }

   private void unshareMain() {
      int[] var1 = this._mainHash;
      int var2 = this._mainHash.length;
      int[] var3 = new int[var2];
      this._mainHash = var3;
      int[] var4 = this._mainHash;
      System.arraycopy(var1, 0, var4, 0, var2);
      this._mainHashShared = (boolean)0;
   }

   private void unshareNames() {
      Name[] var1 = this._mainNames;
      int var2 = var1.length;
      Name[] var3 = new Name[var2];
      this._mainNames = var3;
      Name[] var4 = this._mainNames;
      System.arraycopy(var1, 0, var4, 0, var2);
      this._mainNamesShared = (boolean)0;
   }

   public Name addName(String var1, int[] var2, int var3) {
      int var4 = calcHash(var2, var3);
      Name var5 = constructName(var4, var1, var2, var3);
      this._addSymbol(var4, var5);
      return var5;
   }

   public Name findName(int var1) {
      int var2 = calcHash(var1);
      int var3 = this._mainHashMask & var2;
      int var4 = this._mainHash[var3];
      Name var6;
      if((var4 >> 8 ^ var2) << 8 == 0) {
         Name var7 = this._mainNames[var3];
         if(var7 == null) {
            var6 = null;
            return var6;
         }

         if(var7.equals(var1)) {
            var6 = var7;
            return var6;
         }
      } else if(var4 == 0) {
         var6 = null;
         return var6;
      }

      var3 = var4 & 255;
      if(var3 > 0) {
         int var5 = var3 + -1;
         BytesToNameCanonicalizer.Bucket var8 = this._collList[var5];
         if(var8 != false) {
            var6 = var8.find(var2, var1, 0);
            return var6;
         }
      }

      var6 = null;
      return var6;
   }

   public Name findName(int var1, int var2) {
      int var3 = calcHash(var1, var2);
      int var4 = this._mainHashMask & var3;
      int var5 = this._mainHash[var4];
      Name var7;
      if((var5 >> 8 ^ var3) << 8 == 0) {
         Name var8 = this._mainNames[var4];
         if(var8 == null) {
            var7 = null;
            return var7;
         }

         if(var8.equals(var1, var2)) {
            var7 = var8;
            return var7;
         }
      } else if(var5 == 0) {
         var7 = null;
         return var7;
      }

      var4 = var5 & 255;
      if(var4 > 0) {
         int var6 = var4 + -1;
         BytesToNameCanonicalizer.Bucket var9 = this._collList[var6];
         if(var9 != false) {
            var7 = var9.find(var3, var1, var2);
            return var7;
         }
      }

      var7 = null;
      return var7;
   }

   public Name findName(int[] var1, int var2) {
      int var3 = calcHash(var1, var2);
      int var4 = this._mainHashMask & var3;
      int var5 = this._mainHash[var4];
      Name var7;
      if((var5 >> 8 ^ var3) << 8 == 0) {
         Name var8 = this._mainNames[var4];
         if(var8 == null || var8.equals(var1, var2)) {
            var7 = var8;
            return var7;
         }
      } else if(var5 == 0) {
         var7 = null;
         return var7;
      }

      var4 = var5 & 255;
      if(var4 > 0) {
         int var6 = var4 + -1;
         BytesToNameCanonicalizer.Bucket var9 = this._collList[var6];
         if(var9 != false) {
            var7 = var9.find(var3, var1, var2);
            return var7;
         }
      }

      var7 = null;
      return var7;
   }

   public BytesToNameCanonicalizer makeChild() {
      synchronized(this){}

      BytesToNameCanonicalizer var1;
      try {
         var1 = new BytesToNameCanonicalizer(this);
      } finally {
         ;
      }

      return var1;
   }

   public boolean maybeDirty() {
      boolean var1;
      if(!this._mainHashShared) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void release() {
      if(this.maybeDirty()) {
         if(this._parent != null) {
            this._parent.mergeChild(this);
            this.markAsShared();
         }
      }
   }

   public int size() {
      return this._count;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("[NameCanonicalizer, size: ");
      int var3 = this._count;
      var1.append(var3);
      StringBuilder var5 = var1.append('/');
      int var6 = this._mainHash.length;
      var1.append(var6);
      StringBuilder var8 = var1.append(", ");
      int var9 = this._collCount;
      var1.append(var9);
      StringBuilder var11 = var1.append(" coll; avg length: ");
      int var12 = this._count;
      byte var13 = 0;
      int var14 = var12;
      int var15 = var13;

      while(true) {
         int var16 = this._collEnd;
         if(var15 >= var16) {
            double var21;
            if(this._count == 0) {
               var21 = 0.0D;
            } else {
               double var25 = (double)var14;
               double var27 = (double)this._count;
               var21 = var25 / var27;
            }

            var1.append(var21);
            StringBuilder var24 = var1.append(']');
            return var1.toString();
         }

         int var17 = this._collList[var15].length();
         byte var18 = 1;
         int var19 = var14;

         for(int var20 = var18; var20 <= var17; ++var20) {
            var19 += var20;
         }

         ++var15;
         var14 = var19;
      }
   }

   static final class Bucket {

      final Name mName;
      final BytesToNameCanonicalizer.Bucket mNext;


      Bucket(Name var1, BytesToNameCanonicalizer.Bucket var2) {
         this.mName = var1;
         this.mNext = var2;
      }

      public Name find(int var1, int var2, int var3) {
         Name var4;
         if(this.mName.hashCode() == var1 && this.mName.equals(var2, var3)) {
            var4 = this.mName;
         } else {
            BytesToNameCanonicalizer.Bucket var5 = this.mNext;

            while(true) {
               if(var5 == null) {
                  var4 = null;
                  break;
               }

               Name var6 = var5.mName;
               if(var6.hashCode() == var1 && var6.equals(var2, var3)) {
                  var4 = var6;
                  break;
               }

               var5 = var5.mNext;
            }
         }

         return var4;
      }

      public Name find(int var1, int[] var2, int var3) {
         Name var4;
         if(this.mName.hashCode() == var1 && this.mName.equals(var2, var3)) {
            var4 = this.mName;
         } else {
            BytesToNameCanonicalizer.Bucket var5 = this.mNext;

            while(true) {
               if(var5 == null) {
                  var4 = null;
                  break;
               }

               Name var6 = var5.mName;
               if(var6.hashCode() == var1 && var6.equals(var2, var3)) {
                  var4 = var6;
                  break;
               }

               var5 = var5.mNext;
            }
         }

         return var4;
      }

      public int length() {
         BytesToNameCanonicalizer.Bucket var1 = this.mNext;
         int var2 = 1;

         for(BytesToNameCanonicalizer.Bucket var3 = var1; var3 != null; var3 = var3.mNext) {
            ++var2;
         }

         return var2;
      }
   }
}
