package myorg.bouncycastle.i18n;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.TimeZone;
import myorg.bouncycastle.i18n.LocaleString;
import myorg.bouncycastle.i18n.MissingEntryException;
import myorg.bouncycastle.i18n.filter.Filter;
import myorg.bouncycastle.i18n.filter.TrustedInput;
import myorg.bouncycastle.i18n.filter.UntrustedInput;
import myorg.bouncycastle.i18n.filter.UntrustedUrlInput;

public class LocalizedMessage {

   public static final String DEFAULT_ENCODING = "ISO-8859-1";
   protected LocalizedMessage.FilteredArguments arguments;
   protected String encoding = "ISO-8859-1";
   protected LocalizedMessage.FilteredArguments extraArgs = null;
   protected Filter filter = null;
   protected final String id;
   protected ClassLoader loader = null;
   protected final String resource;


   public LocalizedMessage(String var1, String var2) throws NullPointerException {
      if(var1 != null && var2 != null) {
         this.id = var2;
         this.resource = var1;
         LocalizedMessage.FilteredArguments var3 = new LocalizedMessage.FilteredArguments();
         this.arguments = var3;
      } else {
         throw new NullPointerException();
      }
   }

   public LocalizedMessage(String var1, String var2, String var3) throws NullPointerException, UnsupportedEncodingException {
      if(var1 != null && var2 != null) {
         this.id = var2;
         this.resource = var1;
         LocalizedMessage.FilteredArguments var4 = new LocalizedMessage.FilteredArguments();
         this.arguments = var4;
         if(!Charset.isSupported(var3)) {
            String var5 = "The encoding \"" + var3 + "\" is not supported.";
            throw new UnsupportedEncodingException(var5);
         } else {
            this.encoding = var3;
         }
      } else {
         throw new NullPointerException();
      }
   }

   public LocalizedMessage(String var1, String var2, String var3, Object[] var4) throws NullPointerException, UnsupportedEncodingException {
      if(var1 != null && var2 != null && var4 != null) {
         this.id = var2;
         this.resource = var1;
         LocalizedMessage.FilteredArguments var5 = new LocalizedMessage.FilteredArguments(var4);
         this.arguments = var5;
         if(!Charset.isSupported(var3)) {
            String var6 = "The encoding \"" + var3 + "\" is not supported.";
            throw new UnsupportedEncodingException(var6);
         } else {
            this.encoding = var3;
         }
      } else {
         throw new NullPointerException();
      }
   }

   public LocalizedMessage(String var1, String var2, Object[] var3) throws NullPointerException {
      if(var1 != null && var2 != null && var3 != null) {
         this.id = var2;
         this.resource = var1;
         LocalizedMessage.FilteredArguments var4 = new LocalizedMessage.FilteredArguments(var3);
         this.arguments = var4;
      } else {
         throw new NullPointerException();
      }
   }

   protected String addExtraArgs(String var1, Locale var2) {
      if(this.extraArgs != null) {
         StringBuffer var3 = new StringBuffer(var1);
         Object[] var4 = this.extraArgs.getFilteredArgs(var2);
         int var5 = 0;

         while(true) {
            int var6 = var4.length;
            if(var5 >= var6) {
               var1 = var3.toString();
               break;
            }

            Object var7 = var4[var5];
            var3.append(var7);
            ++var5;
         }
      }

      return var1;
   }

   protected String formatWithTimeZone(String var1, Object[] var2, Locale var3, TimeZone var4) {
      MessageFormat var5 = new MessageFormat(" ");
      var5.setLocale(var3);
      var5.applyPattern(var1);
      TimeZone var6 = TimeZone.getDefault();
      if(!var4.equals(var6)) {
         Format[] var7 = var5.getFormats();
         int var8 = 0;

         while(true) {
            int var9 = var7.length;
            if(var8 >= var9) {
               break;
            }

            if(var7[var8] instanceof DateFormat) {
               DateFormat var10 = (DateFormat)var7[var8];
               var10.setTimeZone(var4);
               var5.setFormat(var8, var10);
            }

            ++var8;
         }
      }

      return var5.format(var2);
   }

   public Object[] getArguments() {
      return this.arguments.getArguments();
   }

   public ClassLoader getClassLoader() {
      return this.loader;
   }

   public String getEntry(String param1, Locale param2, TimeZone param3) throws MissingEntryException {
      // $FF: Couldn't be decompiled
   }

   public Object[] getExtraArgs() {
      Object[] var1;
      if(this.extraArgs == null) {
         var1 = null;
      } else {
         var1 = this.extraArgs.getArguments();
      }

      return var1;
   }

   public Filter getFilter() {
      return this.filter;
   }

   public String getId() {
      return this.id;
   }

   public String getResource() {
      return this.resource;
   }

   public void setClassLoader(ClassLoader var1) {
      this.loader = var1;
   }

   public void setExtraArgument(Object var1) {
      Object[] var2 = new Object[]{var1};
      this.setExtraArguments(var2);
   }

   public void setExtraArguments(Object[] var1) {
      if(var1 != null) {
         LocalizedMessage.FilteredArguments var2 = new LocalizedMessage.FilteredArguments(var1);
         this.extraArgs = var2;
         LocalizedMessage.FilteredArguments var3 = this.extraArgs;
         Filter var4 = this.filter;
         var3.setFilter(var4);
      } else {
         this.extraArgs = null;
      }
   }

   public void setFilter(Filter var1) {
      this.arguments.setFilter(var1);
      if(this.extraArgs != null) {
         this.extraArgs.setFilter(var1);
      }

      this.filter = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      StringBuffer var2 = var1.append("Resource: \"");
      String var3 = this.resource;
      var2.append(var3);
      StringBuffer var5 = var1.append("\" Id: \"");
      String var6 = this.id;
      StringBuffer var7 = var5.append(var6).append("\"");
      StringBuffer var8 = var1.append(" Arguments: ");
      int var9 = this.arguments.getArguments().length;
      StringBuffer var10 = var8.append(var9).append(" normal");
      if(this.extraArgs != null && this.extraArgs.getArguments().length > 0) {
         StringBuffer var11 = var1.append(", ");
         int var12 = this.extraArgs.getArguments().length;
         StringBuffer var13 = var11.append(var12).append(" extra");
      }

      StringBuffer var14 = var1.append(" Encoding: ");
      String var15 = this.encoding;
      var14.append(var15);
      StringBuffer var17 = var1.append(" ClassLoader: ");
      ClassLoader var18 = this.loader;
      var17.append(var18);
      return var1.toString();
   }

   protected class FilteredArguments {

      protected static final int FILTER = 1;
      protected static final int FILTER_URL = 2;
      protected static final int NO_FILTER;
      protected int[] argFilterType;
      protected Object[] arguments;
      protected Filter filter;
      protected Object[] filteredArgs;
      protected boolean[] isLocaleSpecific;
      protected Object[] unpackedArgs;


      FilteredArguments() {
         Object[] var2 = new Object[0];
         this(var2);
      }

      FilteredArguments(Object[] var2) {
         this.filter = null;
         this.arguments = var2;
         Object[] var3 = new Object[var2.length];
         this.unpackedArgs = var3;
         Object[] var4 = new Object[var2.length];
         this.filteredArgs = var4;
         boolean[] var5 = new boolean[var2.length];
         this.isLocaleSpecific = var5;
         int[] var6 = new int[var2.length];
         this.argFilterType = var6;
         int var7 = 0;

         while(true) {
            int var8 = var2.length;
            if(var7 >= var8) {
               return;
            }

            if(var2[var7] instanceof TrustedInput) {
               Object[] var9 = this.unpackedArgs;
               Object var10 = ((TrustedInput)var2[var7]).getInput();
               var9[var7] = var10;
               this.argFilterType[var7] = 0;
            } else if(var2[var7] instanceof UntrustedInput) {
               Object[] var13 = this.unpackedArgs;
               Object var14 = ((UntrustedInput)var2[var7]).getInput();
               var13[var7] = var14;
               if(var2[var7] instanceof UntrustedUrlInput) {
                  this.argFilterType[var7] = 2;
               } else {
                  this.argFilterType[var7] = 1;
               }
            } else {
               Object[] var15 = this.unpackedArgs;
               Object var16 = var2[var7];
               var15[var7] = var16;
               this.argFilterType[var7] = 1;
            }

            boolean[] var11 = this.isLocaleSpecific;
            boolean var12 = this.unpackedArgs[var7] instanceof LocaleString;
            var11[var7] = var12;
            ++var7;
         }
      }

      private Object filter(int var1, Object var2) {
         Object var4;
         if(this.filter != null) {
            Object var3;
            if(var2 == null) {
               var3 = "null";
            } else {
               var3 = var2;
            }

            switch(var1) {
            case 0:
               var4 = var3;
               break;
            case 1:
               Filter var5 = this.filter;
               String var6 = var3.toString();
               var4 = var5.doFilter(var6);
               break;
            case 2:
               Filter var7 = this.filter;
               String var8 = var3.toString();
               var4 = var7.doFilterUrl(var8);
               break;
            default:
               var4 = null;
            }
         } else {
            var4 = var2;
         }

         return var4;
      }

      public Object[] getArguments() {
         return this.arguments;
      }

      public Filter getFilter() {
         return this.filter;
      }

      public Object[] getFilteredArgs(Locale var1) {
         Object[] var2 = new Object[this.unpackedArgs.length];
         int var3 = 0;

         while(true) {
            int var4 = this.unpackedArgs.length;
            if(var3 >= var4) {
               return var2;
            }

            Object var5;
            if(this.filteredArgs[var3] != false) {
               var5 = this.filteredArgs[var3];
            } else {
               Object var6 = this.unpackedArgs[var3];
               if(this.isLocaleSpecific[var3]) {
                  String var7 = ((LocaleString)var6).getLocaleString(var1);
                  int var8 = this.argFilterType[var3];
                  var5 = this.filter(var8, var7);
               } else {
                  int var9 = this.argFilterType[var3];
                  var5 = this.filter(var9, var6);
                  this.filteredArgs[var3] = var5;
               }
            }

            var2[var3] = var5;
            ++var3;
         }
      }

      public boolean isEmpty() {
         boolean var1;
         if(this.unpackedArgs.length == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void setFilter(Filter var1) {
         Filter var2 = this.filter;
         if(var1 != var2) {
            int var3 = 0;

            while(true) {
               int var4 = this.unpackedArgs.length;
               if(var3 >= var4) {
                  break;
               }

               this.filteredArgs[var3] = false;
               ++var3;
            }
         }

         this.filter = var1;
      }
   }
}
