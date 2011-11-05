package com.android.email.mail;

import com.android.email.mail.Multipart;
import com.android.email.mail.Part;

public abstract class BodyPart implements Part {

   protected Multipart mParent;


   public BodyPart() {}

   public Multipart getParent() {
      return this.mParent;
   }
}
