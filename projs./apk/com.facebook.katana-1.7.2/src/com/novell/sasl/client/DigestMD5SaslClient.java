package com.novell.sasl.client;

import com.novell.sasl.client.DigestChallenge;
import com.novell.sasl.client.ResponseAuth;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.NameCallback;
import org.apache.harmony.javax.security.auth.callback.PasswordCallback;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.harmony.javax.security.sasl.RealmCallback;
import org.apache.harmony.javax.security.sasl.RealmChoiceCallback;
import org.apache.harmony.javax.security.sasl.SaslClient;
import org.apache.harmony.javax.security.sasl.SaslException;

public class DigestMD5SaslClient implements SaslClient {

   private static final String DIGEST_METHOD = "AUTHENTICATE";
   private static final int NONCE_BYTE_COUNT = 32;
   private static final int NONCE_HEX_COUNT = 64;
   private static final int STATE_DIGEST_RESPONSE_SENT = 1;
   private static final int STATE_DISPOSED = 4;
   private static final int STATE_INITIAL = 0;
   private static final int STATE_INVALID_SERVER_RESPONSE = 3;
   private static final int STATE_VALID_SERVER_RESPONSE = 2;
   private char[] m_HA1 = null;
   private String m_authorizationId = "";
   private CallbackHandler m_cbh;
   private String m_clientNonce = "";
   private DigestChallenge m_dc;
   private String m_digestURI;
   private String m_name = "";
   private Map m_props;
   private String m_protocol = "";
   private String m_qopValue = "";
   private String m_realm = "";
   private String m_serverName = "";
   private int m_state;


   private DigestMD5SaslClient(String var1, String var2, String var3, Map var4, CallbackHandler var5) {
      this.m_authorizationId = var1;
      this.m_protocol = var2;
      this.m_serverName = var3;
      this.m_props = var4;
      this.m_cbh = var5;
      this.m_state = 0;
   }

   private String createDigestResponse(byte[] var1) throws SaslException {
      StringBuffer var2 = new StringBuffer(512);
      DigestChallenge var3 = new DigestChallenge(var1);
      this.m_dc = var3;
      StringBuilder var4 = new StringBuilder();
      String var5 = this.m_protocol;
      StringBuilder var6 = var4.append(var5).append("/");
      String var7 = this.m_serverName;
      String var8 = var6.append(var7).toString();
      this.m_digestURI = var8;
      if((this.m_dc.getQop() & 1) == 1) {
         this.m_qopValue = "auth";
         Callback[] var9 = new Callback[3];
         ArrayList var10 = this.m_dc.getRealms();
         int var11 = var10.size();
         if(var11 == 0) {
            RealmCallback var12 = new RealmCallback("Realm");
            var9[0] = var12;
         } else if(var11 == 1) {
            String var21 = (String)var10.get(0);
            RealmCallback var22 = new RealmCallback("Realm", var21);
            var9[0] = var22;
         } else {
            String[] var23 = new String[var11];
            String[] var24 = (String[])((String[])var10.toArray(var23));
            RealmChoiceCallback var25 = new RealmChoiceCallback("Realm", var24, 0, (boolean)0);
            var9[0] = var25;
         }

         PasswordCallback var13 = new PasswordCallback("Password", (boolean)0);
         var9[1] = var13;
         if(this.m_authorizationId != null && this.m_authorizationId.length() != 0) {
            String var26 = this.m_authorizationId;
            NameCallback var27 = new NameCallback("Name", var26);
            var9[2] = var27;
         } else {
            NameCallback var14 = new NameCallback("Name");
            var9[2] = var14;
         }

         try {
            this.m_cbh.handle(var9);
         } catch (UnsupportedCallbackException var69) {
            throw new SaslException("Handler does not support necessary callbacks", var69);
         } catch (IOException var70) {
            throw new SaslException("IO exception in CallbackHandler.", var70);
         }

         if(var11 > 1) {
            int[] var71 = ((RealmChoiceCallback)var9[0]).getSelectedIndexes();
            if(var71.length > 0) {
               String[] var15 = ((RealmChoiceCallback)var9[0]).getChoices();
               int var16 = var71[0];
               String var17 = var15[var16];
               this.m_realm = var17;
            } else {
               String var30 = ((RealmChoiceCallback)var9[0]).getChoices()[0];
               this.m_realm = var30;
            }
         } else {
            String var31 = ((RealmCallback)var9[0]).getText();
            this.m_realm = var31;
         }

         String var18 = this.getClientNonce();
         this.m_clientNonce = var18;
         String var19 = ((NameCallback)var9[2]).getName();
         this.m_name = var19;
         if(this.m_name == null) {
            String var20 = ((NameCallback)var9[2]).getDefaultName();
            this.m_name = var20;
         }

         if(this.m_name == null) {
            throw new SaslException("No user name was specified.");
         } else {
            String var32 = this.m_dc.getAlgorithm();
            String var33 = this.m_name;
            String var34 = this.m_realm;
            char[] var35 = ((PasswordCallback)var9[1]).getPassword();
            String var36 = new String(var35);
            String var37 = this.m_dc.getNonce();
            String var38 = this.m_clientNonce;
            char[] var39 = this.DigestCalcHA1(var32, var33, var34, var36, var37, var38);
            this.m_HA1 = var39;
            char[] var40 = this.m_HA1;
            String var41 = this.m_dc.getNonce();
            String var42 = this.m_clientNonce;
            String var43 = this.m_qopValue;
            String var44 = this.m_digestURI;
            char[] var45 = this.DigestCalcResponse(var40, var41, "00000001", var42, var43, "AUTHENTICATE", var44, (boolean)1);
            StringBuffer var46 = var2.append("username=\"");
            String var47 = this.m_authorizationId;
            var2.append(var47);
            if(this.m_realm.length() != 0) {
               StringBuffer var49 = var2.append("\",realm=\"");
               String var50 = this.m_realm;
               var2.append(var50);
            }

            StringBuffer var52 = var2.append("\",cnonce=\"");
            String var53 = this.m_clientNonce;
            var2.append(var53);
            StringBuffer var55 = var2.append("\",nc=");
            StringBuffer var56 = var2.append("00000001");
            StringBuffer var57 = var2.append(",qop=");
            String var58 = this.m_qopValue;
            var2.append(var58);
            StringBuffer var60 = var2.append(",digest-uri=\"");
            String var61 = this.m_digestURI;
            var2.append(var61);
            StringBuffer var63 = var2.append("\",response=");
            var2.append(var45);
            StringBuffer var65 = var2.append(",charset=utf-8,nonce=\"");
            String var66 = this.m_dc.getNonce();
            var2.append(var66);
            StringBuffer var68 = var2.append("\"");
            return var2.toString();
         }
      } else {
         throw new SaslException("Client only supports qop of \'auth\'");
      }
   }

   public static SaslClient getClient(String var0, String var1, String var2, Map var3, CallbackHandler var4) {
      String var5 = (String)var3.get("javax.security.sasl.qop");
      String var6 = (String)var3.get("javax.security.sasl.strength");
      String var7 = (String)var3.get("javax.security.sasl.server.authentication");
      DigestMD5SaslClient var13;
      if(var5 != null && !"auth".equals(var5)) {
         var13 = null;
      } else if(var7 != null && !"false".equals(var7)) {
         var13 = null;
      } else if(var4 == null) {
         var13 = null;
      } else {
         var13 = new DigestMD5SaslClient(var0, var1, var2, var3, var4);
      }

      return var13;
   }

   private static char getHexChar(byte var0) {
      char var1;
      switch(var0) {
      case 0:
         var1 = 48;
         break;
      case 1:
         var1 = 49;
         break;
      case 2:
         var1 = 50;
         break;
      case 3:
         var1 = 51;
         break;
      case 4:
         var1 = 52;
         break;
      case 5:
         var1 = 53;
         break;
      case 6:
         var1 = 54;
         break;
      case 7:
         var1 = 55;
         break;
      case 8:
         var1 = 56;
         break;
      case 9:
         var1 = 57;
         break;
      case 10:
         var1 = 97;
         break;
      case 11:
         var1 = 98;
         break;
      case 12:
         var1 = 99;
         break;
      case 13:
         var1 = 100;
         break;
      case 14:
         var1 = 101;
         break;
      case 15:
         var1 = 102;
         break;
      default:
         var1 = 90;
      }

      return var1;
   }

   char[] DigestCalcHA1(String var1, String var2, String var3, String var4, String var5, String var6) throws SaslException {
      byte[] var19;
      byte[] var18;
      label27: {
         byte[] var13;
         try {
            MessageDigest var7 = MessageDigest.getInstance("MD5");
            byte[] var8 = var2.getBytes("UTF-8");
            var7.update(var8);
            byte[] var9 = ":".getBytes("UTF-8");
            var7.update(var9);
            byte[] var10 = var3.getBytes("UTF-8");
            var7.update(var10);
            byte[] var11 = ":".getBytes("UTF-8");
            var7.update(var11);
            byte[] var12 = var4.getBytes("UTF-8");
            var7.update(var12);
            var13 = var7.digest();
            if("md5-sess".equals(var1)) {
               var7.update(var13);
               byte[] var14 = ":".getBytes("UTF-8");
               var7.update(var14);
               byte[] var15 = var5.getBytes("UTF-8");
               var7.update(var15);
               byte[] var16 = ":".getBytes("UTF-8");
               var7.update(var16);
               byte[] var17 = var6.getBytes("UTF-8");
               var7.update(var17);
               var18 = var7.digest();
               break label27;
            }
         } catch (NoSuchAlgorithmException var22) {
            throw new SaslException("No provider found for MD5 hash", var22);
         } catch (UnsupportedEncodingException var23) {
            throw new SaslException("UTF-8 encoding not supported by platform.", var23);
         }

         var19 = var13;
         return this.convertToHex(var19);
      }

      var19 = var18;
      return this.convertToHex(var19);
   }

   char[] DigestCalcResponse(char[] var1, String var2, String var3, String var4, String var5, String var6, String var7, boolean var8) throws SaslException {
      byte[] var28;
      try {
         MessageDigest var9 = MessageDigest.getInstance("MD5");
         if(var8) {
            byte[] var10 = var6.getBytes("UTF-8");
            var9.update(var10);
         }

         byte[] var11 = ":".getBytes("UTF-8");
         var9.update(var11);
         byte[] var12 = var7.getBytes("UTF-8");
         var9.update(var12);
         if("auth-int".equals(var5)) {
            byte[] var13 = ":".getBytes("UTF-8");
            var9.update(var13);
            byte[] var14 = "00000000000000000000000000000000".getBytes("UTF-8");
            var9.update(var14);
         }

         byte[] var15 = var9.digest();
         char[] var16 = this.convertToHex(var15);
         byte[] var17 = (new String(var1)).getBytes("UTF-8");
         var9.update(var17);
         byte[] var18 = ":".getBytes("UTF-8");
         var9.update(var18);
         byte[] var19 = var2.getBytes("UTF-8");
         var9.update(var19);
         byte[] var20 = ":".getBytes("UTF-8");
         var9.update(var20);
         if(var5.length() > 0) {
            byte[] var21 = var3.getBytes("UTF-8");
            var9.update(var21);
            byte[] var22 = ":".getBytes("UTF-8");
            var9.update(var22);
            byte[] var23 = var4.getBytes("UTF-8");
            var9.update(var23);
            byte[] var24 = ":".getBytes("UTF-8");
            var9.update(var24);
            byte[] var25 = var5.getBytes("UTF-8");
            var9.update(var25);
            byte[] var26 = ":".getBytes("UTF-8");
            var9.update(var26);
         }

         byte[] var27 = (new String(var16)).getBytes("UTF-8");
         var9.update(var27);
         var28 = var9.digest();
      } catch (NoSuchAlgorithmException var32) {
         throw new SaslException("No provider found for MD5 hash", var32);
      } catch (UnsupportedEncodingException var33) {
         throw new SaslException("UTF-8 encoding not supported by platform.", var33);
      }

      return this.convertToHex(var28);
   }

   boolean checkServerResponseAuth(byte[] var1) throws SaslException {
      ResponseAuth var2 = new ResponseAuth(var1);
      char[] var3 = this.m_HA1;
      String var4 = this.m_dc.getNonce();
      String var5 = this.m_clientNonce;
      String var6 = this.m_qopValue;
      String var7 = this.m_digestURI;
      char[] var8 = this.DigestCalcResponse(var3, var4, "00000001", var5, var6, "AUTHENTICATE", var7, (boolean)0);
      String var9 = new String(var8);
      String var10 = var2.getResponseValue();
      return var9.equals(var10);
   }

   char[] convertToHex(byte[] var1) {
      char[] var2 = new char[32];

      for(int var3 = 0; var3 < 16; ++var3) {
         int var4 = var3 * 2;
         char var5 = getHexChar((byte)((var1[var3] & 240) >> 4));
         var2[var4] = var5;
         int var6 = var3 * 2 + 1;
         char var7 = getHexChar((byte)(var1[var3] & 15));
         var2[var6] = var7;
      }

      return var2;
   }

   public void dispose() throws SaslException {
      if(this.m_state != 4) {
         this.m_state = 4;
      }
   }

   public byte[] evaluateChallenge(byte[] var1) throws SaslException {
      byte[] var2 = null;
      switch(this.m_state) {
      case 0:
         if(var1.length == 0) {
            throw new SaslException("response = byte[0]");
         }

         try {
            var2 = this.createDigestResponse(var1).getBytes("UTF-8");
            this.m_state = 1;
            break;
         } catch (UnsupportedEncodingException var4) {
            throw new SaslException("UTF-8 encoding not suppported by platform", var4);
         }
      case 1:
         if(!this.checkServerResponseAuth(var1)) {
            this.m_state = 3;
            throw new SaslException("Could not validate response-auth value from server");
         }

         this.m_state = 2;
         break;
      case 2:
      case 3:
         throw new SaslException("Authentication sequence is complete");
      case 4:
         throw new SaslException("Client has been disposed");
      default:
         throw new SaslException("Unknown client state.");
      }

      return var2;
   }

   String getClientNonce() throws SaslException {
      byte[] var1 = new byte[32];
      char[] var2 = new char[64];

      try {
         SecureRandom.getInstance("SHA1PRNG").nextBytes(var1);

         for(int var3 = 0; var3 < 32; ++var3) {
            int var4 = var3 * 2;
            char var5 = getHexChar((byte)(var1[var3] & 15));
            var2[var4] = var5;
            int var6 = var3 * 2 + 1;
            char var7 = getHexChar((byte)((var1[var3] & 240) >> 4));
            var2[var6] = var7;
         }

         String var10 = new String(var2);
         return var10;
      } catch (NoSuchAlgorithmException var9) {
         throw new SaslException("No random number generator available", var9);
      }
   }

   public String getMechanismName() {
      return "DIGEST-MD5";
   }

   public Object getNegotiatedProperty(String var1) {
      if(this.m_state != 2) {
         throw new IllegalStateException("getNegotiatedProperty: authentication exchange not complete.");
      } else {
         String var2;
         if("javax.security.sasl.qop".equals(var1)) {
            var2 = "auth";
         } else {
            var2 = null;
         }

         return var2;
      }
   }

   public boolean hasInitialResponse() {
      return false;
   }

   public boolean isComplete() {
      boolean var1;
      if(this.m_state != 2 && this.m_state != 3 && this.m_state != 4) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public byte[] unwrap(byte[] var1, int var2, int var3) throws SaslException {
      throw new IllegalStateException("unwrap: QOP has neither integrity nor privacy>");
   }

   public byte[] wrap(byte[] var1, int var2, int var3) throws SaslException {
      throw new IllegalStateException("wrap: QOP has neither integrity nor privacy>");
   }
}
