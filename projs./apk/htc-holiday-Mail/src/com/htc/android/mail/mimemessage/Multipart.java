package com.htc.android.mail.mimemessage;

import com.htc.android.mail.mimemessage.Body;
import com.htc.android.mail.mimemessage.BodyPart;
import com.htc.android.mail.mimemessage.MessagingException;
import com.htc.android.mail.mimemessage.Part;
import java.util.ArrayList;

public abstract class Multipart implements Body {

   protected String mContentType;
   protected Part mParent;
   protected ArrayList<BodyPart> mParts;


   public Multipart() {
      ArrayList var1 = new ArrayList();
      this.mParts = var1;
   }

   public void addBodyPart(BodyPart var1) throws MessagingException {
      this.mParts.add(var1);
   }

   public void addBodyPart(BodyPart var1, int var2) throws MessagingException {
      this.mParts.add(var2, var1);
   }

   public BodyPart getBodyPart(int var1) throws MessagingException {
      return (BodyPart)this.mParts.get(var1);
   }

   public String getContentType() throws MessagingException {
      return this.mContentType;
   }

   public int getCount() throws MessagingException {
      return this.mParts.size();
   }

   public Part getParent() throws MessagingException {
      return this.mParent;
   }

   public void removeBodyPart(int var1) throws MessagingException {
      this.mParts.remove(var1);
   }

   public boolean removeBodyPart(BodyPart var1) throws MessagingException {
      return this.mParts.remove(var1);
   }

   public void setParent(Part var1) throws MessagingException {
      this.mParent = var1;
   }
}
