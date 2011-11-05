package com.android.email.mail.store.imap;


public abstract class ImapElement {

   public static final ImapElement NONE = new ImapElement.1();
   private boolean mDestroyed = 0;


   public ImapElement() {}

   protected final void checkNotDestroyed() {
      if(this.mDestroyed) {
         throw new RuntimeException("Already destroyed");
      }
   }

   public void destroy() {
      this.mDestroyed = (boolean)1;
   }

   public boolean equalsForTest(ImapElement var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         Class var3 = this.getClass();
         Class var4 = var1.getClass();
         if(var3 == var4) {
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   protected boolean isDestroyed() {
      return this.mDestroyed;
   }

   public abstract boolean isList();

   public abstract boolean isString();

   public abstract String toString();

   static class 1 extends ImapElement {

      1() {}

      public void destroy() {}

      public boolean equalsForTest(ImapElement var1) {
         return super.equalsForTest(var1);
      }

      public boolean isList() {
         return false;
      }

      public boolean isString() {
         return false;
      }

      public String toString() {
         return "[NO ELEMENT]";
      }
   }
}
