package org.codehaus.jackson.sym;

import org.codehaus.jackson.util.InternCache;

public final class CharsToNameCanonicalizer {

   protected static final int DEFAULT_TABLE_SIZE = 64;
   protected static final boolean INTERN_STRINGS = true;
   static final int MAX_SYMBOL_TABLE_SIZE = 6000;
   static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer(64);
   protected CharsToNameCanonicalizer.Bucket[] _buckets;
   protected boolean _dirty;
   protected int _indexMask;
   protected CharsToNameCanonicalizer _parent;
   protected int _size;
   protected int _sizeThreshold;
   protected String[] _symbols;


   public CharsToNameCanonicalizer(int var1) {
      this._dirty = (boolean)1;
      if(var1 < 1) {
         String var2 = "Can not use negative/zero initial size: " + var1;
         throw new IllegalArgumentException(var2);
      } else {
         int var3;
         for(var3 = 4; var3 < var1; var3 += var3) {
            ;
         }

         this.initTables(var3);
      }
   }

   private CharsToNameCanonicalizer(CharsToNameCanonicalizer var1, String[] var2, CharsToNameCanonicalizer.Bucket[] var3, int var4) {
      this._parent = var1;
      this._symbols = var2;
      this._buckets = var3;
      this._size = var4;
      int var5 = var2.length;
      int var6 = var5 >> 2;
      int var7 = var5 - var6;
      this._sizeThreshold = var7;
      int var8 = var5 - 1;
      this._indexMask = var8;
      this._dirty = (boolean)0;
   }

   public static int calcHash(String var0) {
      char var1 = var0.charAt(0);
      int var2 = var0.length();
      byte var3 = 1;
      int var4 = var1;

      for(int var5 = var3; var5 < var2; ++var5) {
         int var6 = var4 * 31;
         char var7 = var0.charAt(var5);
         var4 = var6 + var7;
      }

      return var4;
   }

   public static int calcHash(char[] var0, int var1, int var2) {
      char var3 = var0[0];
      byte var4 = 1;
      int var5 = var3;

      for(int var6 = var4; var6 < var2; ++var6) {
         int var7 = var5 * 31;
         char var8 = var0[var6];
         var5 = var7 + var8;
      }

      return var5;
   }

   private void copyArrays() {
      String[] var1 = this._symbols;
      int var2 = var1.length;
      String[] var3 = new String[var2];
      this._symbols = var3;
      String[] var4 = this._symbols;
      System.arraycopy(var1, 0, var4, 0, var2);
      CharsToNameCanonicalizer.Bucket[] var5 = this._buckets;
      int var6 = var5.length;
      CharsToNameCanonicalizer.Bucket[] var7 = new CharsToNameCanonicalizer.Bucket[var6];
      this._buckets = var7;
      CharsToNameCanonicalizer.Bucket[] var8 = this._buckets;
      System.arraycopy(var5, 0, var8, 0, var6);
   }

   public static CharsToNameCanonicalizer createRoot() {
      return sBootstrapSymbolTable.makeOrphan();
   }

   private void initTables(int var1) {
      String[] var2 = new String[var1];
      this._symbols = var2;
      CharsToNameCanonicalizer.Bucket[] var3 = new CharsToNameCanonicalizer.Bucket[var1 >> 1];
      this._buckets = var3;
      int var4 = var1 - 1;
      this._indexMask = var4;
      this._size = 0;
      int var5 = var1 >> 2;
      int var6 = var1 - var5;
      this._sizeThreshold = var6;
   }

   private CharsToNameCanonicalizer makeOrphan() {
      String[] var1 = this._symbols;
      CharsToNameCanonicalizer.Bucket[] var2 = this._buckets;
      int var3 = this._size;
      return new CharsToNameCanonicalizer((CharsToNameCanonicalizer)null, var1, var2, var3);
   }

   private void mergeChild(CharsToNameCanonicalizer var1) {
      synchronized(this){}

      try {
         if(var1.size() > 6000) {
            this.initTables(64);
         } else {
            int var2 = var1.size();
            int var3 = this.size();
            if(var2 <= var3) {
               return;
            }

            String[] var4 = var1._symbols;
            this._symbols = var4;
            CharsToNameCanonicalizer.Bucket[] var5 = var1._buckets;
            this._buckets = var5;
            int var6 = var1._size;
            this._size = var6;
            int var7 = var1._sizeThreshold;
            this._sizeThreshold = var7;
            int var8 = var1._indexMask;
            this._indexMask = var8;
         }

         this._dirty = (boolean)0;
      } finally {
         ;
      }

   }

   private void rehash() {
      int var1 = this._symbols.length;
      int var2 = var1 + var1;
      String[] var3 = this._symbols;
      CharsToNameCanonicalizer.Bucket[] var4 = this._buckets;
      String[] var5 = new String[var2];
      this._symbols = var5;
      CharsToNameCanonicalizer.Bucket[] var6 = new CharsToNameCanonicalizer.Bucket[var2 >> 1];
      this._buckets = var6;
      int var7 = var2 - 1;
      this._indexMask = var7;
      int var8 = this._sizeThreshold;
      int var9 = this._sizeThreshold;
      int var10 = var8 + var9;
      this._sizeThreshold = var10;
      int var11 = 0;

      int var12;
      for(var12 = 0; var11 < var1; ++var11) {
         String var13 = var3[var11];
         if(var13 != null) {
            ++var12;
            int var14 = calcHash(var13);
            int var15 = this._indexMask;
            int var16 = var14 & var15;
            if(this._symbols[var16] == false) {
               this._symbols[var16] = var13;
            } else {
               int var17 = var16 >> 1;
               CharsToNameCanonicalizer.Bucket[] var18 = this._buckets;
               CharsToNameCanonicalizer.Bucket var19 = this._buckets[var17];
               CharsToNameCanonicalizer.Bucket var20 = new CharsToNameCanonicalizer.Bucket(var13, var19);
               var18[var17] = var20;
            }
         }
      }

      int var21 = var1 >> 1;
      int var22 = 0;

      int var23;
      for(var23 = var12; var22 < var21; var23 = var12) {
         CharsToNameCanonicalizer.Bucket var24 = var4[var22];
         var12 = var23;

         for(CharsToNameCanonicalizer.Bucket var25 = var24; var25 != null; var25 = var25.getNext()) {
            int var26 = var12 + 1;
            String var27 = var25.getSymbol();
            int var28 = calcHash(var27);
            int var29 = this._indexMask;
            int var30 = var28 & var29;
            if(this._symbols[var30] == false) {
               this._symbols[var30] = var27;
            } else {
               int var31 = var30 >> 1;
               CharsToNameCanonicalizer.Bucket[] var32 = this._buckets;
               CharsToNameCanonicalizer.Bucket var33 = this._buckets[var31];
               CharsToNameCanonicalizer.Bucket var34 = new CharsToNameCanonicalizer.Bucket(var27, var33);
               var32[var31] = var34;
            }
         }

         ++var22;
      }

      int var35 = this._size;
      if(var23 != var35) {
         StringBuilder var36 = (new StringBuilder()).append("Internal error on SymbolTable.rehash(): had ");
         int var37 = this._size;
         String var38 = var36.append(var37).append(" entries; now have ").append(var23).append(".").toString();
         throw new Error(var38);
      }
   }

   public String findSymbol(char[] var1, int var2, int var3, int var4) {
      String var5;
      if(var3 < 1) {
         var5 = "";
      } else {
         int var6 = this._indexMask & var4;
         String var7 = this._symbols[var6];
         if(var7 != null) {
            if(var7.length() == var3) {
               int var8 = 0;

               do {
                  char var9 = var7.charAt(var8);
                  int var10 = var2 + var8;
                  char var11 = var1[var10];
                  if(var9 != var11) {
                     break;
                  }

                  ++var8;
               } while(var8 < var3);

               if(var8 == var3) {
                  var5 = var7;
                  return var5;
               }
            }

            CharsToNameCanonicalizer.Bucket[] var12 = this._buckets;
            int var13 = var6 >> 1;
            CharsToNameCanonicalizer.Bucket var24 = var12[var13];
            if(var24 != null) {
               var7 = var24.find(var1, var2, var3);
               if(var7 != null) {
                  var5 = var7;
                  return var5;
               }
            }
         }

         if(!this._dirty) {
            this.copyArrays();
            this._dirty = (boolean)1;
         } else {
            int var16 = this._size;
            int var17 = this._sizeThreshold;
            if(var16 >= var17) {
               this.rehash();
               int var18 = calcHash(var1, var2, var3);
               int var19 = this._indexMask;
               var6 = var18 & var19;
            }
         }

         int var14 = this._size + 1;
         this._size = var14;
         String var15 = new String(var1, var2, var3);
         var7 = InternCache.instance.intern(var15);
         if(this._symbols[var6] == false) {
            this._symbols[var6] = var7;
         } else {
            int var20 = var6 >> 1;
            CharsToNameCanonicalizer.Bucket[] var21 = this._buckets;
            CharsToNameCanonicalizer.Bucket var22 = this._buckets[var20];
            CharsToNameCanonicalizer.Bucket var23 = new CharsToNameCanonicalizer.Bucket(var7, var22);
            var21[var20] = var23;
         }

         var5 = var7;
      }

      return var5;
   }

   public CharsToNameCanonicalizer makeChild() {
      synchronized(this){}

      CharsToNameCanonicalizer var4;
      try {
         String[] var1 = this._symbols;
         CharsToNameCanonicalizer.Bucket[] var2 = this._buckets;
         int var3 = this._size;
         var4 = new CharsToNameCanonicalizer(this, var1, var2, var3);
      } finally {
         ;
      }

      return var4;
   }

   public boolean maybeDirty() {
      return this._dirty;
   }

   public void release() {
      if(this.maybeDirty()) {
         if(this._parent != null) {
            this._parent.mergeChild(this);
            this._dirty = (boolean)0;
         }
      }
   }

   public int size() {
      return this._size;
   }

   static final class Bucket {

      private final String _symbol;
      private final CharsToNameCanonicalizer.Bucket mNext;


      public Bucket(String var1, CharsToNameCanonicalizer.Bucket var2) {
         this._symbol = var1;
         this.mNext = var2;
      }

      public String find(char[] var1, int var2, int var3) {
         String var4 = this._symbol;
         CharsToNameCanonicalizer.Bucket var5 = this.mNext;
         String var6 = var4;
         CharsToNameCanonicalizer.Bucket var7 = var5;

         String var12;
         while(true) {
            if(var6.length() == var3) {
               int var8 = 0;

               do {
                  char var9 = var6.charAt(var8);
                  int var10 = var2 + var8;
                  char var11 = var1[var10];
                  if(var9 != var11) {
                     break;
                  }

                  ++var8;
               } while(var8 < var3);

               if(var8 == var3) {
                  var12 = var6;
                  break;
               }
            }

            if(var7 == null) {
               var12 = null;
               break;
            }

            var6 = var7.getSymbol();
            var7 = var7.getNext();
         }

         return var12;
      }

      public CharsToNameCanonicalizer.Bucket getNext() {
         return this.mNext;
      }

      public String getSymbol() {
         return this._symbol;
      }
   }
}
