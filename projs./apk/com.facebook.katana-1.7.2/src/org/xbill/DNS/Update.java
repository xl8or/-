package org.xbill.DNS;

import java.io.IOException;
import java.util.Iterator;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.RRset;
import org.xbill.DNS.Record;
import org.xbill.DNS.RelativeNameException;
import org.xbill.DNS.Tokenizer;

public class Update extends Message {

   private int dclass;
   private Name origin;


   public Update(Name var1) {
      this(var1, 1);
   }

   public Update(Name var1, int var2) {
      if(!var1.isAbsolute()) {
         throw new RelativeNameException(var1);
      } else {
         DClass.check(var2);
         this.getHeader().setOpcode(5);
         Record var3 = Record.newRecord(var1, 6, 1);
         this.addRecord(var3, 0);
         this.origin = var1;
         this.dclass = var2;
      }
   }

   private void newPrereq(Record var1) {
      this.addRecord(var1, 1);
   }

   private void newUpdate(Record var1) {
      this.addRecord(var1, 2);
   }

   public void absent(Name var1) {
      Record var2 = Record.newRecord(var1, 255, 254, 0L);
      this.newPrereq(var2);
   }

   public void absent(Name var1, int var2) {
      Record var3 = Record.newRecord(var1, var2, 254, 0L);
      this.newPrereq(var3);
   }

   public void add(Name var1, int var2, long var3, String var5) throws IOException {
      int var6 = this.dclass;
      Name var7 = this.origin;
      Record var13 = Record.fromString(var1, var2, var6, var3, var5, var7);
      this.newUpdate(var13);
   }

   public void add(Name var1, int var2, long var3, Tokenizer var5) throws IOException {
      int var6 = this.dclass;
      Name var7 = this.origin;
      Record var13 = Record.fromString(var1, var2, var6, var3, var5, var7);
      this.newUpdate(var13);
   }

   public void add(RRset var1) {
      Iterator var2 = var1.rrs();

      while(var2.hasNext()) {
         Record var3 = (Record)var2.next();
         this.add(var3);
      }

   }

   public void add(Record var1) {
      this.newUpdate(var1);
   }

   public void add(Record[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         Record var4 = var1[var2];
         this.add(var4);
         ++var2;
      }
   }

   public void delete(Name var1) {
      Record var2 = Record.newRecord(var1, 255, 255, 0L);
      this.newUpdate(var2);
   }

   public void delete(Name var1, int var2) {
      Record var3 = Record.newRecord(var1, var2, 255, 0L);
      this.newUpdate(var3);
   }

   public void delete(Name var1, int var2, String var3) throws IOException {
      Name var4 = this.origin;
      Record var8 = Record.fromString(var1, var2, 254, 0L, var3, var4);
      this.newUpdate(var8);
   }

   public void delete(Name var1, int var2, Tokenizer var3) throws IOException {
      Name var4 = this.origin;
      Record var8 = Record.fromString(var1, var2, 254, 0L, var3, var4);
      this.newUpdate(var8);
   }

   public void delete(RRset var1) {
      Iterator var2 = var1.rrs();

      while(var2.hasNext()) {
         Record var3 = (Record)var2.next();
         this.delete(var3);
      }

   }

   public void delete(Record var1) {
      Record var2 = var1.withDClass(254, 0L);
      this.newUpdate(var2);
   }

   public void delete(Record[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         Record var4 = var1[var2];
         this.delete(var4);
         ++var2;
      }
   }

   public void present(Name var1) {
      Record var2 = Record.newRecord(var1, 255, 255, 0L);
      this.newPrereq(var2);
   }

   public void present(Name var1, int var2) {
      Record var3 = Record.newRecord(var1, var2, 255, 0L);
      this.newPrereq(var3);
   }

   public void present(Name var1, int var2, String var3) throws IOException {
      int var4 = this.dclass;
      Name var5 = this.origin;
      Record var9 = Record.fromString(var1, var2, var4, 0L, var3, var5);
      this.newPrereq(var9);
   }

   public void present(Name var1, int var2, Tokenizer var3) throws IOException {
      int var4 = this.dclass;
      Name var5 = this.origin;
      Record var9 = Record.fromString(var1, var2, var4, 0L, var3, var5);
      this.newPrereq(var9);
   }

   public void present(Record var1) {
      this.newPrereq(var1);
   }

   public void replace(Name var1, int var2, long var3, String var5) throws IOException {
      this.delete(var1, var2);
      this.add(var1, var2, var3, var5);
   }

   public void replace(Name var1, int var2, long var3, Tokenizer var5) throws IOException {
      this.delete(var1, var2);
      this.add(var1, var2, var3, var5);
   }

   public void replace(RRset var1) {
      Name var2 = var1.getName();
      int var3 = var1.getType();
      this.delete(var2, var3);
      Iterator var4 = var1.rrs();

      while(var4.hasNext()) {
         Record var5 = (Record)var4.next();
         this.add(var5);
      }

   }

   public void replace(Record var1) {
      Name var2 = var1.getName();
      int var3 = var1.getType();
      this.delete(var2, var3);
      this.add(var1);
   }

   public void replace(Record[] var1) {
      int var2 = 0;

      while(true) {
         int var3 = var1.length;
         if(var2 >= var3) {
            return;
         }

         Record var4 = var1[var2];
         this.replace(var4);
         ++var2;
      }
   }
}
