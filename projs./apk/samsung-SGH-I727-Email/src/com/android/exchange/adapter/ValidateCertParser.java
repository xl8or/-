package com.android.exchange.adapter;

import com.android.exchange.adapter.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ValidateCertParser extends Parser {

   public static final int VALIDATE_CERT_STATUS_SUCCESS = 1;
   private ArrayList<Integer> mResult;
   private int mStatus = 0;


   public ValidateCertParser(InputStream var1) throws IOException {
      super(var1);
      ArrayList var2 = new ArrayList();
      this.mResult = var2;
   }

   public ArrayList<Integer> getResult() {
      return this.mResult;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public boolean parse() throws IOException {
      if(this.nextTag(0) != 709) {
         throw new IOException();
      } else {
         while(this.nextTag(0) != 3) {
            if(this.tag == 711) {
               this.parseCertificate();
            } else if(this.tag == 714) {
               int var1 = this.getValueInt();
               this.mStatus = var1;
            } else {
               this.skipTag();
            }
         }

         boolean var2;
         if(this.mStatus == 1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   public void parseCertificate() throws IOException {
      while(this.nextTag(711) != 3) {
         if(this.tag == 714) {
            ArrayList var1 = this.mResult;
            int var2 = this.getValueInt();
            Integer var3 = new Integer(var2);
            var1.add(var3);
         } else {
            this.skipTag();
         }
      }

   }
}
