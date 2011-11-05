package com.novell.sasl.client;

import com.novell.sasl.client.DirectiveList;
import com.novell.sasl.client.ParsedDirective;
import com.novell.sasl.client.TokenParser;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.harmony.javax.security.sasl.SaslException;

class DigestChallenge {

   private static final int CIPHER_3DES = 1;
   private static final int CIPHER_DES = 2;
   private static final int CIPHER_RC4 = 8;
   private static final int CIPHER_RC4_40 = 4;
   private static final int CIPHER_RC4_56 = 16;
   private static final int CIPHER_RECOGNIZED_MASK = 31;
   private static final int CIPHER_UNRECOGNIZED = 32;
   public static final int QOP_AUTH = 1;
   public static final int QOP_AUTH_CONF = 4;
   public static final int QOP_AUTH_INT = 2;
   public static final int QOP_UNRECOGNIZED = 8;
   private String m_algorithm;
   private String m_characterSet;
   private int m_cipherOptions;
   private int m_maxBuf;
   private String m_nonce;
   private int m_qop;
   private ArrayList m_realms;
   private boolean m_staleFlag;


   DigestChallenge(byte[] var1) throws SaslException {
      ArrayList var2 = new ArrayList(5);
      this.m_realms = var2;
      this.m_nonce = null;
      this.m_qop = 0;
      this.m_staleFlag = (boolean)0;
      this.m_maxBuf = -1;
      this.m_characterSet = null;
      this.m_algorithm = null;
      this.m_cipherOptions = 0;
      DirectiveList var3 = new DirectiveList(var1);

      try {
         var3.parseDirectives();
         this.checkSemantics(var3);
      } catch (SaslException var5) {
         ;
      }
   }

   void checkSemantics(DirectiveList var1) throws SaslException {
      Iterator var2 = var1.getIterator();

      while(var2.hasNext()) {
         ParsedDirective var3 = (ParsedDirective)var2.next();
         String var4 = var3.getName();
         if(var4.equals("realm")) {
            this.handleRealm(var3);
         } else if(var4.equals("nonce")) {
            this.handleNonce(var3);
         } else if(var4.equals("qop")) {
            this.handleQop(var3);
         } else if(var4.equals("maxbuf")) {
            this.handleMaxbuf(var3);
         } else if(var4.equals("charset")) {
            this.handleCharset(var3);
         } else if(var4.equals("algorithm")) {
            this.handleAlgorithm(var3);
         } else if(var4.equals("cipher")) {
            this.handleCipher(var3);
         } else if(var4.equals("stale")) {
            this.handleStale(var3);
         }
      }

      int var5 = this.m_maxBuf;
      if(-1 == var5) {
         this.m_maxBuf = 65536;
      }

      if(this.m_qop == 0) {
         this.m_qop = 1;
      } else if((this.m_qop & 1) != 1) {
         throw new SaslException("Only qop-auth is supported by client");
      } else if((this.m_qop & 4) == 4 && (this.m_cipherOptions & 31) == 0) {
         throw new SaslException("Invalid cipher options");
      } else if(this.m_nonce == null) {
         throw new SaslException("Missing nonce directive");
      } else if(this.m_staleFlag) {
         throw new SaslException("Unexpected stale flag");
      } else if(this.m_algorithm == null) {
         throw new SaslException("Missing algorithm directive");
      }
   }

   public String getAlgorithm() {
      return this.m_algorithm;
   }

   public String getCharacterSet() {
      return this.m_characterSet;
   }

   public int getCipherOptions() {
      return this.m_cipherOptions;
   }

   public int getMaxBuf() {
      return this.m_maxBuf;
   }

   public String getNonce() {
      return this.m_nonce;
   }

   public int getQop() {
      return this.m_qop;
   }

   public ArrayList getRealms() {
      return this.m_realms;
   }

   public boolean getStaleFlag() {
      return this.m_staleFlag;
   }

   void handleAlgorithm(ParsedDirective var1) throws SaslException {
      if(this.m_algorithm != null) {
         throw new SaslException("Too many algorithm directives.");
      } else {
         String var2 = var1.getValue();
         this.m_algorithm = var2;
         String var3 = this.m_algorithm;
         if(!"md5-sess".equals(var3)) {
            StringBuilder var4 = (new StringBuilder()).append("Invalid algorithm directive value: ");
            String var5 = this.m_algorithm;
            String var6 = var4.append(var5).toString();
            throw new SaslException(var6);
         }
      }
   }

   void handleCharset(ParsedDirective var1) throws SaslException {
      if(this.m_characterSet != null) {
         throw new SaslException("Too many charset directives.");
      } else {
         String var2 = var1.getValue();
         this.m_characterSet = var2;
         if(!this.m_characterSet.equals("utf-8")) {
            throw new SaslException("Invalid character encoding directive");
         }
      }
   }

   void handleCipher(ParsedDirective var1) throws SaslException {
      if(this.m_cipherOptions != 0) {
         throw new SaslException("Too many cipher directives.");
      } else {
         String var2 = var1.getValue();
         TokenParser var3 = new TokenParser(var2);
         String var4 = var3.parseToken();

         for(String var5 = var3.parseToken(); var5 != null; var5 = var3.parseToken()) {
            if("3des".equals(var5)) {
               int var6 = this.m_cipherOptions | 1;
               this.m_cipherOptions = var6;
            } else if("des".equals(var5)) {
               int var7 = this.m_cipherOptions | 2;
               this.m_cipherOptions = var7;
            } else if("rc4-40".equals(var5)) {
               int var8 = this.m_cipherOptions | 4;
               this.m_cipherOptions = var8;
            } else if("rc4".equals(var5)) {
               int var9 = this.m_cipherOptions | 8;
               this.m_cipherOptions = var9;
            } else if("rc4-56".equals(var5)) {
               int var10 = this.m_cipherOptions | 16;
               this.m_cipherOptions = var10;
            } else {
               int var11 = this.m_cipherOptions | 32;
               this.m_cipherOptions = var11;
            }
         }

         if(this.m_cipherOptions == 0) {
            this.m_cipherOptions = 32;
         }
      }
   }

   void handleMaxbuf(ParsedDirective var1) throws SaslException {
      int var2 = this.m_maxBuf;
      if(-1 != var2) {
         throw new SaslException("Too many maxBuf directives.");
      } else {
         int var3 = Integer.parseInt(var1.getValue());
         this.m_maxBuf = var3;
         if(this.m_maxBuf == 0) {
            throw new SaslException("Max buf value must be greater than zero.");
         }
      }
   }

   void handleNonce(ParsedDirective var1) throws SaslException {
      if(this.m_nonce != null) {
         throw new SaslException("Too many nonce values.");
      } else {
         String var2 = var1.getValue();
         this.m_nonce = var2;
      }
   }

   void handleQop(ParsedDirective var1) throws SaslException {
      if(this.m_qop != 0) {
         throw new SaslException("Too many qop directives.");
      } else {
         String var2 = var1.getValue();
         TokenParser var3 = new TokenParser(var2);

         for(String var4 = var3.parseToken(); var4 != null; var4 = var3.parseToken()) {
            if(var4.equals("auth")) {
               int var5 = this.m_qop | 1;
               this.m_qop = var5;
            } else if(var4.equals("auth-int")) {
               int var6 = this.m_qop | 2;
               this.m_qop = var6;
            } else if(var4.equals("auth-conf")) {
               int var7 = this.m_qop | 4;
               this.m_qop = var7;
            } else {
               int var8 = this.m_qop | 8;
               this.m_qop = var8;
            }
         }

      }
   }

   void handleRealm(ParsedDirective var1) {
      ArrayList var2 = this.m_realms;
      String var3 = var1.getValue();
      var2.add(var3);
   }

   void handleStale(ParsedDirective var1) throws SaslException {
      if(this.m_staleFlag) {
         throw new SaslException("Too many stale directives.");
      } else {
         String var2 = var1.getValue();
         if("true".equals(var2)) {
            this.m_staleFlag = (boolean)1;
         } else {
            StringBuilder var3 = (new StringBuilder()).append("Invalid stale directive value: ");
            String var4 = var1.getValue();
            String var5 = var3.append(var4).toString();
            throw new SaslException(var5);
         }
      }
   }
}
