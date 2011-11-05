package org.xbill.DNS;

import org.xbill.DNS.Compression;
import org.xbill.DNS.DNSOutput;
import org.xbill.DNS.Name;
import org.xbill.DNS.SingleNameBase;

abstract class SingleCompressedNameBase extends SingleNameBase {

   private static final long serialVersionUID = -236435396815460677L;


   protected SingleCompressedNameBase() {}

   protected SingleCompressedNameBase(Name var1, int var2, int var3, long var4, Name var6, String var7) {
      super(var1, var2, var3, var4, var6, var7);
   }

   void rrToWire(DNSOutput var1, Compression var2, boolean var3) {
      this.singleName.toWire(var1, var2, var3);
   }
}
