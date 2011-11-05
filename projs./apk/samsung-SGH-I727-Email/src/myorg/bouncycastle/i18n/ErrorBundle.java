package myorg.bouncycastle.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.TimeZone;
import myorg.bouncycastle.i18n.MessageBundle;
import myorg.bouncycastle.i18n.MissingEntryException;

public class ErrorBundle extends MessageBundle {

   public static final String DETAIL_ENTRY = "details";
   public static final String SUMMARY_ENTRY = "summary";


   public ErrorBundle(String var1, String var2) throws NullPointerException {
      super(var1, var2);
   }

   public ErrorBundle(String var1, String var2, String var3) throws NullPointerException, UnsupportedEncodingException {
      super(var1, var2, var3);
   }

   public ErrorBundle(String var1, String var2, String var3, Object[] var4) throws NullPointerException, UnsupportedEncodingException {
      super(var1, var2, var3, var4);
   }

   public ErrorBundle(String var1, String var2, Object[] var3) throws NullPointerException {
      super(var1, var2, var3);
   }

   public String getDetail(Locale var1) throws MissingEntryException {
      TimeZone var2 = TimeZone.getDefault();
      return this.getEntry("details", var1, var2);
   }

   public String getDetail(Locale var1, TimeZone var2) throws MissingEntryException {
      return this.getEntry("details", var1, var2);
   }

   public String getSummary(Locale var1) throws MissingEntryException {
      TimeZone var2 = TimeZone.getDefault();
      return this.getEntry("summary", var1, var2);
   }

   public String getSummary(Locale var1, TimeZone var2) throws MissingEntryException {
      return this.getEntry("summary", var1, var2);
   }
}
