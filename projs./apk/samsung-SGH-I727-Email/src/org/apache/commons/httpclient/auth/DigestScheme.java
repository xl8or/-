package org.apache.commons.httpclient.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthenticationException;
import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.apache.commons.httpclient.auth.MalformedChallengeException;
import org.apache.commons.httpclient.auth.RFC2617Scheme;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.apache.commons.httpclient.util.ParameterFormatter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DigestScheme extends RFC2617Scheme {

   private static final char[] HEXADECIMAL = new char[]{(char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null, (char)null};
   private static final Log LOG = LogFactory.getLog(DigestScheme.class);
   private static final String NC = "00000001";
   private static final int QOP_AUTH = 2;
   private static final int QOP_AUTH_INT = 1;
   private static final int QOP_MISSING;
   private String cnonce;
   private boolean complete;
   private final ParameterFormatter formatter;
   private int qopVariant;


   public DigestScheme() {
      this.qopVariant = 0;
      this.complete = (boolean)0;
      ParameterFormatter var1 = new ParameterFormatter();
      this.formatter = var1;
   }

   public DigestScheme(String var1) throws MalformedChallengeException {
      this();
      this.processChallenge(var1);
   }

   public static String createCnonce() {
      LOG.trace("enter DigestScheme.createCnonce()");

      MessageDigest var0;
      try {
         var0 = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException var4) {
         throw new HttpClientError("Unsupported algorithm in HTTP Digest authentication: MD5");
      }

      byte[] var2 = EncodingUtil.getAsciiBytes(Long.toString(System.currentTimeMillis()));
      return encode(var0.digest(var2));
   }

   private String createDigest(String var1, String var2) throws AuthenticationException {
      LOG.trace("enter DigestScheme.createDigest(String, String, Map)");
      String var4 = "uri";
      String var5 = this.getParameter(var4);
      String var7 = "realm";
      String var8 = this.getParameter(var7);
      String var10 = "nonce";
      String var11 = this.getParameter(var10);
      String var13 = "qop";
      String var14 = this.getParameter(var13);
      String var16 = "methodname";
      String var17 = this.getParameter(var16);
      String var19 = "algorithm";
      String var20 = this.getParameter(var19);
      if(var20 == null) {
         var20 = "MD5";
      }

      String var22 = "charset";
      String var23 = this.getParameter(var22);
      if(var23 == null) {
         var23 = "ISO-8859-1";
      }

      int var24 = this.qopVariant;
      byte var25 = 1;
      if(var24 == var25) {
         LOG.warn("qop=auth-int is not supported");
         throw new AuthenticationException("Unsupported qop in HTTP Digest authentication");
      } else {
         MessageDigest var26;
         try {
            var26 = MessageDigest.getInstance("MD5");
         } catch (Exception var184) {
            throw new AuthenticationException("Unsupported algorithm in HTTP Digest authentication: MD5");
         }

         StringBuffer var28 = new StringBuffer;
         int var29 = var1.length();
         int var30 = var8.length();
         int var31 = var29 + var30;
         int var32 = var2.length();
         int var33 = var31 + var32 + 2;
         var28.<init>(var33);
         StringBuffer var38 = var28.append(var1);
         char var40 = 58;
         var28.append(var40);
         StringBuffer var44 = var28.append(var8);
         char var46 = 58;
         var28.append(var46);
         StringBuffer var50 = var28.append(var2);
         String var51 = var28.toString();
         String var53 = "MD5-sess";
         if(var20.equals(var53)) {
            byte[] var54 = EncodingUtil.getBytes(var51, var23);
            String var57 = encode(var26.digest(var54));
            StringBuffer var58 = new StringBuffer;
            int var59 = var57.length();
            int var60 = var11.length();
            int var61 = var59 + var60;
            int var62 = this.cnonce.length();
            int var63 = var61 + var62 + 2;
            var58.<init>(var63);
            StringBuffer var68 = var58.append(var57);
            char var70 = 58;
            var58.append(var70);
            StringBuffer var74 = var58.append(var11);
            char var76 = 58;
            var58.append(var76);
            String var78 = this.cnonce;
            StringBuffer var81 = var58.append(var78);
            var51 = var58.toString();
         } else {
            String var122 = "MD5";
            if(!var20.equals(var122)) {
               Log var123 = LOG;
               StringBuilder var124 = (new StringBuilder()).append("Unhandled algorithm ");
               String var126 = var124.append(var20).append(" requested").toString();
               var123.warn(var126);
            }
         }

         byte[] var82 = EncodingUtil.getBytes(var51, var23);
         String var85 = encode(var26.digest(var82));
         String var86 = null;
         int var87 = this.qopVariant;
         byte var88 = 1;
         if(var87 == var88) {
            LOG.error("Unhandled qop auth-int");
         } else {
            StringBuilder var127 = new StringBuilder();
            StringBuilder var129 = var127.append(var17).append(":");
            var86 = var129.append(var5).toString();
         }

         byte[] var89 = EncodingUtil.getAsciiBytes(var86);
         String var92 = encode(var26.digest(var89));
         String var116;
         if(this.qopVariant == 0) {
            LOG.debug("Using null qop method");
            StringBuffer var93 = new StringBuffer;
            int var94 = var85.length();
            int var95 = var11.length();
            int var96 = var94 + var95;
            int var97 = var92.length();
            int var98 = var96 + var97;
            var93.<init>(var98);
            StringBuffer var103 = var93.append(var85);
            char var105 = 58;
            var93.append(var105);
            StringBuffer var109 = var93.append(var11);
            char var111 = 58;
            var93.append(var111);
            StringBuffer var115 = var93.append(var92);
            var116 = var93.toString();
         } else {
            if(LOG.isDebugEnabled()) {
               Log var131 = LOG;
               StringBuilder var132 = (new StringBuilder()).append("Using qop method ");
               String var134 = var132.append(var14).toString();
               var131.debug(var134);
            }

            String var135 = this.getQopVariantString();
            StringBuffer var136 = new StringBuffer;
            int var137 = var85.length();
            int var138 = var11.length();
            int var139 = var137 + var138;
            int var140 = "00000001".length();
            int var141 = var139 + var140;
            int var142 = this.cnonce.length();
            int var143 = var141 + var142;
            int var144 = var135.length();
            int var145 = var143 + var144;
            int var146 = var92.length();
            int var147 = var145 + var146 + 5;
            var136.<init>(var147);
            StringBuffer var152 = var136.append(var85);
            char var154 = 58;
            var136.append(var154);
            StringBuffer var158 = var136.append(var11);
            char var160 = 58;
            var136.append(var160);
            String var163 = "00000001";
            var136.append(var163);
            char var166 = 58;
            var136.append(var166);
            String var168 = this.cnonce;
            StringBuffer var171 = var136.append(var168);
            char var173 = 58;
            var136.append(var173);
            StringBuffer var177 = var136.append(var135);
            char var179 = 58;
            var136.append(var179);
            StringBuffer var183 = var136.append(var92);
            var116 = var136.toString();
         }

         byte[] var117 = EncodingUtil.getAsciiBytes(var116);
         return encode(var26.digest(var117));
      }
   }

   private String createDigestHeader(String var1, String var2) throws AuthenticationException {
      LOG.trace("enter DigestScheme.createDigestHeader(String, Map, String)");
      String var3 = this.getParameter("uri");
      String var4 = this.getParameter("realm");
      String var5 = this.getParameter("nonce");
      String var6 = this.getParameter("opaque");
      String var8 = this.getParameter("algorithm");
      ArrayList var9 = new ArrayList(20);
      NameValuePair var10 = new NameValuePair("username", var1);
      var9.add(var10);
      NameValuePair var12 = new NameValuePair("realm", var4);
      var9.add(var12);
      NameValuePair var14 = new NameValuePair("nonce", var5);
      var9.add(var14);
      NameValuePair var16 = new NameValuePair("uri", var3);
      var9.add(var16);
      NameValuePair var18 = new NameValuePair("response", var2);
      var9.add(var18);
      if(this.qopVariant != 0) {
         String var20 = this.getQopVariantString();
         NameValuePair var21 = new NameValuePair("qop", var20);
         var9.add(var21);
         NameValuePair var23 = new NameValuePair("nc", "00000001");
         var9.add(var23);
         String var25 = this.cnonce;
         NameValuePair var26 = new NameValuePair("cnonce", var25);
         var9.add(var26);
      }

      if(var8 != null) {
         NameValuePair var28 = new NameValuePair("algorithm", var8);
         var9.add(var28);
      }

      if(var6 != null) {
         NameValuePair var30 = new NameValuePair("opaque", var6);
         var9.add(var30);
      }

      StringBuffer var32 = new StringBuffer();
      int var33 = 0;

      while(true) {
         int var34 = var9.size();
         if(var33 >= var34) {
            return var32.toString();
         }

         NameValuePair var35 = (NameValuePair)var9.get(var33);
         if(var33 > 0) {
            StringBuffer var36 = var32.append(", ");
         }

         boolean var39;
         label38: {
            String var37 = var35.getName();
            if(!"nc".equals(var37)) {
               String var38 = var35.getName();
               if(!"qop".equals(var38)) {
                  var39 = false;
                  break label38;
               }
            }

            var39 = true;
         }

         ParameterFormatter var40 = this.formatter;
         byte var41;
         if(!var39) {
            var41 = 1;
         } else {
            var41 = 0;
         }

         var40.setAlwaysUseQuotes((boolean)var41);
         this.formatter.format(var32, var35);
         ++var33;
      }
   }

   private static String encode(byte[] var0) {
      LOG.trace("enter DigestScheme.encode(byte[])");
      String var1;
      if(var0.length != 16) {
         var1 = null;
      } else {
         char[] var2 = new char[32];

         for(int var3 = 0; var3 < 16; ++var3) {
            int var4 = var0[var3] & 15;
            int var5 = (var0[var3] & 240) >> 4;
            int var6 = var3 * 2;
            char var7 = HEXADECIMAL[var5];
            var2[var6] = var7;
            int var8 = var3 * 2 + 1;
            char var9 = HEXADECIMAL[var4];
            var2[var8] = var9;
         }

         var1 = new String(var2);
      }

      return var1;
   }

   private String getQopVariantString() {
      String var1;
      if(this.qopVariant == 1) {
         var1 = "auth-int";
      } else {
         var1 = "auth";
      }

      return var1;
   }

   public String authenticate(Credentials var1, String var2, String var3) throws AuthenticationException {
      LOG.trace("enter DigestScheme.authenticate(Credentials, String, String)");

      UsernamePasswordCredentials var4;
      try {
         var4 = (UsernamePasswordCredentials)var1;
      } catch (ClassCastException var17) {
         StringBuilder var14 = (new StringBuilder()).append("Credentials cannot be used for digest authentication: ");
         String var15 = var1.getClass().getName();
         String var16 = var14.append(var15).toString();
         throw new InvalidCredentialsException(var16);
      }

      Object var5 = this.getParameters().put("methodname", var2);
      Object var6 = this.getParameters().put("uri", var3);
      String var7 = var4.getUserName();
      String var8 = var4.getPassword();
      String var9 = this.createDigest(var7, var8);
      StringBuilder var10 = (new StringBuilder()).append("Digest ");
      String var11 = var4.getUserName();
      String var12 = this.createDigestHeader(var11, var9);
      return var10.append(var12).toString();
   }

   public String authenticate(Credentials var1, HttpMethod var2) throws AuthenticationException {
      LOG.trace("enter DigestScheme.authenticate(Credentials, HttpMethod)");

      UsernamePasswordCredentials var3;
      try {
         var3 = (UsernamePasswordCredentials)var1;
      } catch (ClassCastException var29) {
         StringBuilder var26 = (new StringBuilder()).append("Credentials cannot be used for digest authentication: ");
         String var27 = var1.getClass().getName();
         String var28 = var26.append(var27).toString();
         throw new InvalidCredentialsException(var28);
      }

      Map var4 = this.getParameters();
      String var5 = var2.getName();
      var4.put("methodname", var5);
      String var7 = var2.getPath();
      StringBuffer var8 = new StringBuffer(var7);
      String var9 = var2.getQueryString();
      if(var9 != null) {
         if(var9.indexOf("?") != 0) {
            StringBuffer var10 = var8.append("?");
         }

         String var11 = var2.getQueryString();
         var8.append(var11);
      }

      Map var13 = this.getParameters();
      String var14 = var8.toString();
      var13.put("uri", var14);
      if(this.getParameter("charset") == null) {
         Map var16 = this.getParameters();
         String var17 = var2.getParams().getCredentialCharset();
         var16.put("charset", var17);
      }

      String var19 = var3.getUserName();
      String var20 = var3.getPassword();
      String var21 = this.createDigest(var19, var20);
      StringBuilder var22 = (new StringBuilder()).append("Digest ");
      String var23 = var3.getUserName();
      String var24 = this.createDigestHeader(var23, var21);
      return var22.append(var24).toString();
   }

   public String getID() {
      String var1 = this.getRealm();
      String var2 = this.getParameter("nonce");
      if(var2 != null) {
         var1 = var1 + "-" + var2;
      }

      return var1;
   }

   public String getSchemeName() {
      return "digest";
   }

   public boolean isComplete() {
      String var1 = this.getParameter("stale");
      byte var2;
      if("true".equalsIgnoreCase(var1)) {
         var2 = 0;
      } else {
         var2 = this.complete;
      }

      return (boolean)var2;
   }

   public boolean isConnectionBased() {
      return false;
   }

   public void processChallenge(String var1) throws MalformedChallengeException {
      super.processChallenge(var1);
      if(this.getParameter("realm") == null) {
         throw new MalformedChallengeException("missing realm in challange");
      } else if(this.getParameter("nonce") == null) {
         throw new MalformedChallengeException("missing nonce in challange");
      } else {
         boolean var2 = false;
         String var3 = this.getParameter("qop");
         if(var3 != null) {
            StringTokenizer var4 = new StringTokenizer(var3, ",");

            while(var4.hasMoreTokens()) {
               String var5 = var4.nextToken().trim();
               if(var5.equals("auth")) {
                  this.qopVariant = 2;
                  break;
               }

               if(var5.equals("auth-int")) {
                  this.qopVariant = 1;
               } else {
                  var2 = true;
                  StringBuffer var6 = new StringBuffer();
                  StringBuffer var7 = var6.append("Unsupported qop detected: ").append(var5);
                  Log var8 = LOG;
                  String var9 = var6.toString();
                  var8.warn(var9);
               }
            }
         }

         if(var2 && this.qopVariant == 0) {
            throw new MalformedChallengeException("None of the qop methods is supported");
         } else {
            String var10 = createCnonce();
            this.cnonce = var10;
            this.complete = (boolean)1;
         }
      }
   }
}
