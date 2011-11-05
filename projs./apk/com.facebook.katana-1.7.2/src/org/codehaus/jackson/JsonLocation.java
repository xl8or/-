package org.codehaus.jackson;


public class JsonLocation {

   final int _columnNr;
   final int _lineNr;
   final Object _sourceRef;
   final long _totalChars;


   public JsonLocation(Object var1, long var2, int var4, int var5) {
      this._totalChars = var2;
      this._lineNr = var4;
      this._columnNr = var5;
      this._sourceRef = var1;
   }

   public long getByteOffset() {
      return 65535L;
   }

   public long getCharOffset() {
      return this._totalChars;
   }

   public int getColumnNr() {
      return this._columnNr;
   }

   public int getLineNr() {
      return this._lineNr;
   }

   public Object getSourceRef() {
      return this._sourceRef;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(80);
      StringBuilder var2 = var1.append("[Source: ");
      if(this._sourceRef == null) {
         StringBuilder var3 = var1.append("UNKNOWN");
      } else {
         String var11 = this._sourceRef.toString();
         var1.append(var11);
      }

      StringBuilder var4 = var1.append("; line: ");
      int var5 = this._lineNr;
      var1.append(var5);
      StringBuilder var7 = var1.append(", column: ");
      int var8 = this._columnNr;
      var1.append(var8);
      StringBuilder var10 = var1.append(']');
      return var1.toString();
   }
}
