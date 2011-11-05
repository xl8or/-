package org.jivesoftware.smack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.UserAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.sasl.SASLAnonymous;
import org.jivesoftware.smack.sasl.SASLCramMD5Mechanism;
import org.jivesoftware.smack.sasl.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.sasl.SASLExternalMechanism;
import org.jivesoftware.smack.sasl.SASLGSSAPIMechanism;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.SASLPlainMechanism;

public class SASLAuthentication implements UserAuthentication {

   private static Map<String, Class> implementedMechanisms = new HashMap();
   private static List<String> mechanismsPreferences = new ArrayList();
   private Connection connection;
   private SASLMechanism currentMechanism;
   private String errorCondition;
   private boolean resourceBinded;
   private boolean saslFailed;
   private boolean saslNegotiated;
   private Collection<String> serverMechanisms;
   private boolean sessionSupported;


   static {
      registerSASLMechanism("EXTERNAL", SASLExternalMechanism.class);
      registerSASLMechanism("GSSAPI", SASLGSSAPIMechanism.class);
      registerSASLMechanism("DIGEST-MD5", SASLDigestMD5Mechanism.class);
      registerSASLMechanism("CRAM-MD5", SASLCramMD5Mechanism.class);
      registerSASLMechanism("PLAIN", SASLPlainMechanism.class);
      registerSASLMechanism("ANONYMOUS", SASLAnonymous.class);
      supportSASLMechanism("DIGEST-MD5", 0);
      supportSASLMechanism("PLAIN", 1);
      supportSASLMechanism("ANONYMOUS", 2);
   }

   SASLAuthentication(Connection var1) {
      ArrayList var2 = new ArrayList();
      this.serverMechanisms = var2;
      this.currentMechanism = null;
      this.connection = var1;
      this.init();
   }

   private String bindResourceAndEstablishSession(String param1) throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   public static List<Class> getRegisterSASLMechanisms() {
      ArrayList var0 = new ArrayList();
      Iterator var1 = mechanismsPreferences.iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         Object var3 = implementedMechanisms.get(var2);
         var0.add(var3);
      }

      return var0;
   }

   public static void registerSASLMechanism(String var0, Class var1) {
      implementedMechanisms.put(var0, var1);
   }

   public static void supportSASLMechanism(String var0) {
      mechanismsPreferences.add(0, var0);
   }

   public static void supportSASLMechanism(String var0, int var1) {
      mechanismsPreferences.add(var1, var0);
   }

   public static void unregisterSASLMechanism(String var0) {
      Object var1 = implementedMechanisms.remove(var0);
      boolean var2 = mechanismsPreferences.remove(var0);
   }

   public static void unsupportSASLMechanism(String var0) {
      boolean var1 = mechanismsPreferences.remove(var0);
   }

   public String authenticate(String param1, String param2, String param3) throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   public String authenticate(String param1, String param2, CallbackHandler param3) throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   public String authenticateAnonymously() throws XMPPException {
      // $FF: Couldn't be decompiled
   }

   void authenticated() {
      synchronized(this) {
         this.saslNegotiated = (boolean)1;
         this.notify();
      }
   }

   void authenticationFailed() {
      this.authenticationFailed((String)null);
   }

   void authenticationFailed(String var1) {
      synchronized(this) {
         this.saslFailed = (boolean)1;
         this.errorCondition = var1;
         this.notify();
      }
   }

   void bindingRequired() {
      synchronized(this) {
         this.resourceBinded = (boolean)1;
         this.notify();
      }
   }

   void challengeReceived(String var1) throws IOException {
      this.currentMechanism.challengeReceived(var1);
   }

   public boolean hasAnonymousAuthentication() {
      return this.serverMechanisms.contains("ANONYMOUS");
   }

   public boolean hasNonAnonymousAuthentication() {
      boolean var1;
      if(!this.serverMechanisms.isEmpty() && (this.serverMechanisms.size() != 1 || !this.hasAnonymousAuthentication())) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   protected void init() {
      this.saslNegotiated = (boolean)0;
      this.saslFailed = (boolean)0;
      this.resourceBinded = (boolean)0;
      this.sessionSupported = (boolean)0;
   }

   public boolean isAuthenticated() {
      return this.saslNegotiated;
   }

   public void send(Packet var1) {
      this.connection.sendPacket(var1);
   }

   void sessionsSupported() {
      this.sessionSupported = (boolean)1;
   }

   void setAvailableSASLMethods(Collection<String> var1) {
      this.serverMechanisms = var1;
   }
}
