package org.jivesoftware.smack.packet;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

public abstract class Packet {

   protected static final String DEFAULT_LANGUAGE = Locale.getDefault().getLanguage().toLowerCase();
   private static String DEFAULT_XML_NS = null;
   public static final String ID_NOT_AVAILABLE = "ID_NOT_AVAILABLE";
   public static final DateFormat XEP_0082_UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'");
   private static long id;
   private static String prefix;
   private XMPPError error;
   private String from;
   private final List<PacketExtension> packetExtensions;
   private String packetID;
   private final Map<String, Object> properties;
   private String to;
   private String xmlns;


   static {
      DateFormat var0 = XEP_0082_UTC_FORMAT;
      TimeZone var1 = TimeZone.getTimeZone("UTC");
      var0.setTimeZone(var1);
      StringBuilder var2 = new StringBuilder();
      String var3 = StringUtils.randomString(5);
      prefix = var2.append(var3).append("-").toString();
      id = 0L;
   }

   public Packet() {
      String var1 = DEFAULT_XML_NS;
      this.xmlns = var1;
      this.packetID = null;
      this.to = null;
      this.from = null;
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();
      this.packetExtensions = var2;
      HashMap var3 = new HashMap();
      this.properties = var3;
      this.error = null;
   }

   public static String getDefaultLanguage() {
      return DEFAULT_LANGUAGE;
   }

   public static String nextID() {
      synchronized(Packet.class){}
      boolean var10 = false;

      String var6;
      try {
         var10 = true;
         StringBuilder var0 = new StringBuilder();
         String var1 = prefix;
         StringBuilder var2 = var0.append(var1);
         long var3 = id;
         id = 1L + var3;
         String var5 = Long.toString(var3);
         var6 = var2.append(var5).toString();
         var10 = false;
      } finally {
         if(var10) {
            ;
         }
      }

      return var6;
   }

   public static void setDefaultXmlns(String var0) {
      DEFAULT_XML_NS = var0;
   }

   public void addExtension(PacketExtension var1) {
      this.packetExtensions.add(var1);
   }

   public void deleteProperty(String param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(this == var1) {
         var2 = true;
      } else {
         if(var1 != null) {
            Class var3 = this.getClass();
            Class var4 = var1.getClass();
            if(var3 == var4) {
               label110: {
                  Packet var19 = (Packet)var1;
                  if(this.error != null) {
                     XMPPError var5 = this.error;
                     XMPPError var6 = var19.error;
                     if(!var5.equals(var6)) {
                        break label110;
                     }
                  } else if(var19.error != null) {
                     break label110;
                  }

                  label111: {
                     if(this.from != null) {
                        String var7 = this.from;
                        String var8 = var19.from;
                        if(var7.equals(var8)) {
                           break label111;
                        }
                     } else if(var19.from == null) {
                        break label111;
                     }

                     var2 = false;
                     return var2;
                  }

                  List var9 = this.packetExtensions;
                  List var10 = var19.packetExtensions;
                  if(!var9.equals(var10)) {
                     var2 = false;
                  } else {
                     label112: {
                        if(this.packetID != null) {
                           String var11 = this.packetID;
                           String var12 = var19.packetID;
                           if(!var11.equals(var12)) {
                              break label112;
                           }
                        } else if(var19.packetID != null) {
                           break label112;
                        }

                        label113: {
                           if(this.properties != null) {
                              Map var13 = this.properties;
                              Map var14 = var19.properties;
                              if(var13.equals(var14)) {
                                 break label113;
                              }
                           } else if(var19.properties == null) {
                              break label113;
                           }

                           var2 = false;
                           return var2;
                        }

                        label114: {
                           if(this.to != null) {
                              String var15 = this.to;
                              String var16 = var19.to;
                              if(var15.equals(var16)) {
                                 break label114;
                              }
                           } else if(var19.to == null) {
                              break label114;
                           }

                           var2 = false;
                           return var2;
                        }

                        label115: {
                           if(this.xmlns != null) {
                              String var17 = this.xmlns;
                              String var18 = var19.xmlns;
                              if(var17.equals(var18)) {
                                 break label115;
                              }
                           } else if(var19.xmlns == null) {
                              break label115;
                           }

                           var2 = false;
                           return var2;
                        }

                        var2 = true;
                        return var2;
                     }

                     var2 = false;
                  }

                  return var2;
               }

               var2 = false;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public XMPPError getError() {
      return this.error;
   }

   public PacketExtension getExtension(String var1) {
      return this.getExtension((String)null, var1);
   }

   public PacketExtension getExtension(String var1, String var2) {
      PacketExtension var3;
      if(var2 == null) {
         var3 = null;
      } else {
         Iterator var4 = this.packetExtensions.iterator();

         while(true) {
            if(var4.hasNext()) {
               PacketExtension var7 = (PacketExtension)var4.next();
               if(var1 != null) {
                  String var5 = var7.getElementName();
                  if(!var1.equals(var5)) {
                     continue;
                  }
               }

               String var6 = var7.getNamespace();
               if(!var2.equals(var6)) {
                  continue;
               }

               var3 = var7;
               break;
            }

            var3 = null;
            break;
         }
      }

      return var3;
   }

   public Collection<PacketExtension> getExtensions() {
      synchronized(this){}
      boolean var6 = false;

      List var1;
      List var2;
      label55: {
         try {
            var6 = true;
            if(this.packetExtensions == null) {
               var1 = Collections.emptyList();
               var6 = false;
               break label55;
            }

            List var3 = this.packetExtensions;
            var1 = Collections.unmodifiableList(new ArrayList(var3));
            var6 = false;
         } finally {
            if(var6) {
               ;
            }
         }

         var2 = var1;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   protected String getExtensionsXML() {
      // $FF: Couldn't be decompiled
   }

   public String getFrom() {
      return this.from;
   }

   public String getPacketID() {
      String var1 = this.packetID;
      String var2;
      if("ID_NOT_AVAILABLE".equals(var1)) {
         var2 = null;
      } else {
         if(this.packetID == null) {
            String var3 = nextID();
            this.packetID = var3;
         }

         var2 = this.packetID;
      }

      return var2;
   }

   public Object getProperty(String param1) {
      // $FF: Couldn't be decompiled
   }

   public Collection<String> getPropertyNames() {
      synchronized(this){}
      boolean var6 = false;

      Set var1;
      Set var2;
      label55: {
         try {
            var6 = true;
            if(this.properties == null) {
               var1 = Collections.emptySet();
               var6 = false;
               break label55;
            }

            Set var3 = this.properties.keySet();
            var1 = Collections.unmodifiableSet(new HashSet(var3));
            var6 = false;
         } finally {
            if(var6) {
               ;
            }
         }

         var2 = var1;
         return var2;
      }

      var2 = var1;
      return var2;
   }

   public String getTo() {
      return this.to;
   }

   public String getXmlns() {
      return this.xmlns;
   }

   public int hashCode() {
      int var1;
      if(this.xmlns != null) {
         var1 = this.xmlns.hashCode();
      } else {
         var1 = 0;
      }

      int var2 = var1 * 31;
      int var3;
      if(this.packetID != null) {
         var3 = this.packetID.hashCode();
      } else {
         var3 = 0;
      }

      int var4 = (var2 + var3) * 31;
      int var5;
      if(this.to != null) {
         var5 = this.to.hashCode();
      } else {
         var5 = 0;
      }

      int var6 = (var4 + var5) * 31;
      int var7;
      if(this.from != null) {
         var7 = this.from.hashCode();
      } else {
         var7 = 0;
      }

      int var8 = (var6 + var7) * 31;
      int var9 = this.packetExtensions.hashCode();
      int var10 = (var8 + var9) * 31;
      int var11 = this.properties.hashCode();
      int var12 = (var10 + var11) * 31;
      int var13;
      if(this.error != null) {
         var13 = this.error.hashCode();
      } else {
         var13 = 0;
      }

      return var12 + var13;
   }

   public void removeExtension(PacketExtension var1) {
      this.packetExtensions.remove(var1);
   }

   public void setError(XMPPError var1) {
      this.error = var1;
   }

   public void setFrom(String var1) {
      this.from = var1;
   }

   public void setPacketID(String var1) {
      this.packetID = var1;
   }

   public void setProperty(String var1, Object var2) {
      synchronized(this){}

      try {
         if(!(var2 instanceof Serializable)) {
            throw new IllegalArgumentException("Value must be serialiazble");
         }

         this.properties.put(var1, var2);
      } finally {
         ;
      }

   }

   public void setTo(String var1) {
      this.to = var1;
   }

   public abstract String toXML();
}
