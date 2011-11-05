package com.htc.android.mail.eassvc.provision;

import com.htc.android.mail.eassvc.provision.EASProvisionDoc;
import java.io.Serializable;

public class EASPolicySet implements Serializable {

   public boolean needWipe = 0;
   public EASProvisionDoc provisionDoc;
   public boolean wipeOK = 0;


   public EASPolicySet() {
      EASProvisionDoc var1 = new EASProvisionDoc();
      this.provisionDoc = var1;
   }
}
