package org.codehaus.jackson.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.codehaus.jackson.util.BufferRecycler;

public final class TextBuffer {

   static final char[] NO_CHARS = new char[0];
   private final BufferRecycler _allocator;
   private char[] _currentSegment;
   private int _currentSize;
   private boolean _hasSegments = 0;
   private char[] _inputBuffer;
   private int _inputLen;
   private int _inputStart;
   private char[] _resultArray;
   private String _resultString;
   private int _segmentSize;
   private ArrayList<char[]> _segments;


   public TextBuffer(BufferRecycler var1) {
      this._allocator = var1;
   }

   private final char[] allocBuffer(int var1) {
      BufferRecycler var2 = this._allocator;
      BufferRecycler.CharBufferType var3 = BufferRecycler.CharBufferType.TEXT_BUFFER;
      return var2.allocCharBuffer(var3, var1);
   }

   private char[] buildResultArray() {
      Object var1 = null;
      char[] var2;
      if(this._resultString != null) {
         var2 = this._resultString.toCharArray();
      } else if(this._inputStart >= 0) {
         if(this._inputLen < 1) {
            var2 = NO_CHARS;
         } else {
            var2 = new char[this._inputLen];
            char[] var3 = this._inputBuffer;
            int var4 = this._inputStart;
            int var5 = this._inputLen;
            System.arraycopy(var3, var4, var2, 0, var5);
         }
      } else {
         int var6 = this.size();
         if(var6 < 1) {
            var2 = NO_CHARS;
         } else {
            char[] var16 = new char[var6];
            int var13;
            if(this._segments != null) {
               int var7 = this._segments.size();
               int var8 = 0;

               int var9;
               int var12;
               for(var9 = 0; var8 < var7; var9 = var12) {
                  char[] var10 = (char[])((char[])this._segments.get(var8));
                  int var11 = var10.length;
                  System.arraycopy(var10, 0, var16, var9, var11);
                  var12 = var9 + var11;
                  ++var8;
               }

               var13 = var9;
            } else {
               var13 = 0;
            }

            char[] var14 = this._currentSegment;
            int var15 = this._currentSize;
            System.arraycopy(var14, 0, var16, var13, var15);
            var2 = var16;
         }
      }

      return var2;
   }

   private final void clearSegments() {
      this._hasSegments = (boolean)0;
      ArrayList var1 = this._segments;
      int var2 = this._segments.size() - 1;
      char[] var3 = (char[])var1.get(var2);
      this._currentSegment = var3;
      this._segments.clear();
      this._segmentSize = 0;
      this._currentSize = 0;
   }

   private void expand(int var1) {
      if(this._segments == null) {
         ArrayList var2 = new ArrayList();
         this._segments = var2;
      }

      char[] var3 = this._currentSegment;
      this._hasSegments = (boolean)1;
      this._segments.add(var3);
      int var5 = this._segmentSize;
      int var6 = var3.length;
      int var7 = var5 + var6;
      this._segmentSize = var7;
      int var8 = var3.length;
      int var9 = var8 >> 1;
      if(var9 < var1) {
         var9 = var1;
      }

      char[] var10 = new char[var8 + var9];
      this._currentSize = 0;
      this._currentSegment = var10;
   }

   private void unshare(int var1) {
      int var2;
      char[] var3;
      int var4;
      label15: {
         var2 = this._inputLen;
         this._inputLen = 0;
         var3 = this._inputBuffer;
         this._inputBuffer = null;
         var4 = this._inputStart;
         this._inputStart = -1;
         int var5 = var2 + var1;
         if(this._currentSegment != null) {
            int var6 = this._currentSegment.length;
            if(var5 <= var6) {
               break label15;
            }
         }

         char[] var7 = this.allocBuffer(var5);
         this._currentSegment = var7;
      }

      if(var2 > 0) {
         char[] var8 = this._currentSegment;
         System.arraycopy(var3, var4, var8, 0, var2);
      }

      this._segmentSize = 0;
      this._currentSize = var2;
   }

   public void append(char[] var1, int var2, int var3) {
      if(this._inputStart >= 0) {
         this.unshare(var3);
      }

      this._resultString = null;
      this._resultArray = null;
      char[] var4 = this._currentSegment;
      int var5 = var4.length;
      int var6 = this._currentSize;
      int var7 = var5 - var6;
      if(var7 >= var3) {
         int var8 = this._currentSize;
         System.arraycopy(var1, var2, var4, var8, var3);
         int var9 = this._currentSize + var3;
         this._currentSize = var9;
      } else {
         int var13;
         int var14;
         if(var7 > 0) {
            int var10 = this._currentSize;
            System.arraycopy(var1, var2, var4, var10, var7);
            int var11 = var2 + var7;
            int var12 = var3 - var7;
            var13 = var11;
            var14 = var12;
         } else {
            var14 = var3;
            var13 = var2;
         }

         this.expand(var14);
         char[] var15 = this._currentSegment;
         System.arraycopy(var1, var13, var15, 0, var14);
         this._currentSize = var14;
      }
   }

   public char[] contentsAsArray() {
      char[] var1 = this._resultArray;
      if(var1 == null) {
         var1 = this.buildResultArray();
         this._resultArray = var1;
      }

      return var1;
   }

   public BigDecimal contentsAsDecimal() throws NumberFormatException {
      BigDecimal var2;
      if(this._resultArray != null) {
         char[] var1 = this._resultArray;
         var2 = new BigDecimal(var1);
      } else if(this._inputStart >= 0) {
         char[] var3 = this._inputBuffer;
         int var4 = this._inputStart;
         int var5 = this._inputLen;
         var2 = new BigDecimal(var3, var4, var5);
      } else if(this._segmentSize == 0) {
         char[] var6 = this._currentSegment;
         int var7 = this._currentSize;
         var2 = new BigDecimal(var6, 0, var7);
      } else {
         char[] var8 = this.contentsAsArray();
         var2 = new BigDecimal(var8);
      }

      return var2;
   }

   public double contentsAsDouble() throws NumberFormatException {
      return Double.parseDouble(this.contentsAsString());
   }

   public String contentsAsString() {
      String var3;
      if(this._resultString == null) {
         if(this._resultArray != null) {
            char[] var1 = this._resultArray;
            String var2 = new String(var1);
            this._resultString = var2;
         } else if(this._inputStart >= 0) {
            if(this._inputLen < 1) {
               var3 = "";
               this._resultString = var3;
               return var3;
            }

            char[] var4 = this._inputBuffer;
            int var5 = this._inputStart;
            int var6 = this._inputLen;
            String var7 = new String(var4, var5, var6);
            this._resultString = var7;
         } else {
            int var8 = this._segmentSize;
            int var9 = this._currentSize;
            if(var8 == 0) {
               String var10;
               if(var9 == 0) {
                  var10 = "";
               } else {
                  char[] var11 = this._currentSegment;
                  var10 = new String(var11, 0, var9);
               }

               this._resultString = var10;
            } else {
               int var12 = var8 + var9;
               StringBuilder var13 = new StringBuilder(var12);
               if(this._segments != null) {
                  var9 = this._segments.size();

                  int var18;
                  for(byte var14 = 0; var14 < var9; var18 = var14 + 1) {
                     char[] var15 = (char[])this._segments.get(var14);
                     int var16 = var15.length;
                     var13.append(var15, 0, var16);
                  }
               }

               char[] var19 = this._currentSegment;
               int var20 = this._currentSize;
               var13.append(var19, 0, var20);
               String var22 = var13.toString();
               this._resultString = var22;
            }
         }
      }

      var3 = this._resultString;
      return var3;
   }

   public char[] emptyAndGetCurrentSegment() {
      this.resetWithEmpty();
      char[] var1 = this._currentSegment;
      if(var1 == null) {
         var1 = this.allocBuffer(0);
         this._currentSegment = var1;
      }

      return var1;
   }

   public void ensureNotShared() {
      if(this._inputStart >= 0) {
         this.unshare(16);
      }
   }

   public char[] expandCurrentSegment() {
      char[] var1 = this._currentSegment;
      int var2 = var1.length;
      char[] var3 = new char[var2 + var2];
      this._currentSegment = var3;
      char[] var4 = this._currentSegment;
      System.arraycopy(var1, 0, var4, 0, var2);
      return this._currentSegment;
   }

   public char[] finishCurrentSegment() {
      if(this._segments == null) {
         ArrayList var1 = new ArrayList();
         this._segments = var1;
      }

      this._hasSegments = (boolean)1;
      ArrayList var2 = this._segments;
      char[] var3 = this._currentSegment;
      var2.add(var3);
      int var5 = this._currentSegment.length;
      int var6 = this._segmentSize + var5;
      this._segmentSize = var6;
      int var7 = var5 >> 1;
      char[] var8 = new char[var5 + var7];
      this._currentSize = 0;
      this._currentSegment = var8;
      return var8;
   }

   public char[] getCurrentSegment() {
      if(this._inputStart >= 0) {
         this.unshare(1);
      } else {
         char[] var1 = this._currentSegment;
         if(var1 == null) {
            char[] var2 = this.allocBuffer(0);
            this._currentSegment = var2;
         } else {
            int var3 = this._currentSize;
            int var4 = var1.length;
            if(var3 >= var4) {
               this.expand(1);
            }
         }
      }

      return this._currentSegment;
   }

   public int getCurrentSegmentSize() {
      return this._currentSize;
   }

   public char[] getTextBuffer() {
      char[] var1;
      if(this._inputStart >= 0) {
         var1 = this._inputBuffer;
      } else if(!this._hasSegments) {
         var1 = this._currentSegment;
      } else {
         var1 = this.contentsAsArray();
      }

      return var1;
   }

   public int getTextOffset() {
      int var1;
      if(this._inputStart >= 0) {
         var1 = this._inputStart;
      } else {
         var1 = 0;
      }

      return var1;
   }

   public void releaseBuffers() {
      if(this._allocator != null) {
         if(this._currentSegment != null) {
            this.resetWithEmpty();
            char[] var1 = this._currentSegment;
            this._currentSegment = null;
            BufferRecycler var2 = this._allocator;
            BufferRecycler.CharBufferType var3 = BufferRecycler.CharBufferType.TEXT_BUFFER;
            var2.releaseCharBuffer(var3, var1);
         }
      }
   }

   public void resetWithCopy(char[] var1, int var2, int var3) {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if(this._hasSegments) {
         this.clearSegments();
      } else if(this._currentSegment == null) {
         char[] var4 = this.allocBuffer(var3);
         this._currentSegment = var4;
      }

      this._segmentSize = 0;
      this._currentSize = 0;
      this.append(var1, var2, var3);
   }

   public void resetWithEmpty() {
      this._inputBuffer = null;
      this._inputStart = -1;
      this._inputLen = 0;
      this._resultString = null;
      this._resultArray = null;
      if(this._hasSegments) {
         this.clearSegments();
      }

      this._currentSize = 0;
   }

   public void resetWithShared(char[] var1, int var2, int var3) {
      this._resultString = null;
      this._resultArray = null;
      this._inputBuffer = var1;
      this._inputStart = var2;
      this._inputLen = var3;
      if(this._hasSegments) {
         this.clearSegments();
      }
   }

   public void setCurrentLength(int var1) {
      this._currentSize = var1;
   }

   public int size() {
      int var1;
      if(this._inputStart >= 0) {
         var1 = this._inputLen;
      } else {
         int var2 = this._segmentSize;
         int var3 = this._currentSize;
         var1 = var2 + var3;
      }

      return var1;
   }

   public String toString() {
      return this.contentsAsString();
   }
}
