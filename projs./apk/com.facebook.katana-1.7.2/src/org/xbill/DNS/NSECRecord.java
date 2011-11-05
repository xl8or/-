package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSInput;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Tokenizer;
import org.xbill.DNS.Type;
import org.xbill.DNS.TypeBitmap;

public class NSECRecord extends Record {

   private static final long serialVersionUID = -5165065768816265385L;
   private Name next;
   private TypeBitmap types;


   NSECRecord() {}

   public NSECRecord(Name var1, int var2, long var3, Name var5, int[] var6) {
      super(var1, 47, var2, var3);
      Name var12 = checkName("next", var5);
      this.next = var12;
      int var13 = 0;

      while(true) {
         int var14 = var6.length;
         if(var13 >= var14) {
            TypeBitmap var15 = new TypeBitmap(var6);
            this.types = var15;
            return;
         }

         Type.check(var6[var13]);
         ++var13;
      }
   }

   public Name getNext() {
      return this.next;
   }

   Record getObject() {
      return new NSECRecord();
   }

   public int[] getTypes() {
      return this.types.toArray();
   }

   public boolean hasType(int var1) {
      return this.types.contains(var1);
   }

   void rdataFromString(Tokenizer var1, Name var2) throws IOException {
      Name var3 = var1.getName(var2);
      this.next = var3;
      TypeBitmap var4 = new TypeBitmap(var1);
      this.types = var4;
   }

   void rrFromWire(DNSInput var1) throws IOException {
      Name var2 = new Name(var1);
      this.next = var2;
      TypeBitmap var3 = new TypeBitmap(var1);
      this.types = var3;
   }

   String rrToString() {
      StringBuffer var1 = new StringBuffer();
      Name var2 = this.next;
      var1.append(var2);
      if(!this.types.empty()) {
         StringBuffer var4 = var1.append(' ');
         String var5 = this.types.toString();
         var1.append(var5);
      }

      return var1.toString();
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.next.toWire(var1, (Compression)null, (boolean)0);
      this.types.toWire(var1);
   }
}
