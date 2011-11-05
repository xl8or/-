package com.htc.android.mail.eassvc.pim;

import android.net.Uri;

public class EASMail {

   public static final String AUTHORITY = "mail";
   public static final Uri CONTENT_URI = Uri.parse("content://mail");
   public static final Uri EASACCOUNTS_URI = Uri.parse("content://mail/accounts");


   private EASMail() {}
}
