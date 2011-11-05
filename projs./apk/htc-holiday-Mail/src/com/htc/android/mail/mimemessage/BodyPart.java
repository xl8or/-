package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.Multipart;
import com.htc.android.mail.mimemessage.Part;

public abstract class BodyPart implements Part {

   protected Multipart mParent;


   public BodyPart() {}

   public Multipart getParent() {
      return this.mParent;
   }
}
