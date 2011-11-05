package com.facebook.katana;

import android.app.Application;
import com.facebook.katana.Constants;
import com.facebook.katana.util.Utils;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpPostSender;

@ReportsCrashes(
   formKey = ""
)
public class FacebookApplication extends Application {

   public FacebookApplication() {}

   public void onCreate() {
      ACRA.init(this);
      String var1 = Constants.URL.getCrashReportUrl(this);
      ErrorReporter var2 = ErrorReporter.getInstance();
      HttpPostSender var3 = new HttpPostSender(var1, (Map)null);
      var2.setReportSender(var3);
      Utils.updateErrorReportingUserId(this);
      super.onCreate();
   }
}
