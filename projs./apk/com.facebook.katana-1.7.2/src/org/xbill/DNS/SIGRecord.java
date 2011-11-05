package org.xbill.DNS;

import java.util.Date;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.SIGBase;

public class SIGRecord extends SIGBase {

   private static final long serialVersionUID = 4963556060953589058L;


   SIGRecord() {}

   public SIGRecord(Name param1, int param2, long param3, int param5, int param6, long param7, Date param9, Date param10, int param11, Name param12, byte[] param13) {
      // $FF: Couldn't be decompiled
   }

   Record getObject() {
      return new SIGRecord();
   }
}
