package org.jivesoftware.smack.packet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.util.StringUtils;

public class Message extends Packet {

   private final Set<Message.Body> bodies;
   private String language;
   private final Set<Message.Subject> subjects;
   private String thread;
   private Message.Type type;


   public Message() {
      Message.Type var1 = Message.Type.normal;
      this.type = var1;
      this.thread = null;
      HashSet var2 = new HashSet();
      this.subjects = var2;
      HashSet var3 = new HashSet();
      this.bodies = var3;
   }

   public Message(String var1) {
      Message.Type var2 = Message.Type.normal;
      this.type = var2;
      this.thread = null;
      HashSet var3 = new HashSet();
      this.subjects = var3;
      HashSet var4 = new HashSet();
      this.bodies = var4;
      this.setTo(var1);
   }

   public Message(String var1, Message.Type var2) {
      Message.Type var3 = Message.Type.normal;
      this.type = var3;
      this.thread = null;
      HashSet var4 = new HashSet();
      this.subjects = var4;
      HashSet var5 = new HashSet();
      this.bodies = var5;
      this.setTo(var1);
      this.type = var2;
   }

   private String determineLanguage(String var1) {
      String var2 = "".equals(var1);
      if(var2 != 0) {
         var2 = 0;
      }

      if(var2 == 0 && this.language != null) {
         var2 = this.language;
      } else if(var2 == false) {
         var2 = getDefaultLanguage();
      }

      return var2;
   }

   private Message.Body getMessageBody(String var1) {
      String var2 = this.determineLanguage(var1);
      Iterator var3 = this.bodies.iterator();

      Message.Body var6;
      while(true) {
         if(var3.hasNext()) {
            Message.Body var5 = (Message.Body)var3.next();
            String var4 = var5.language;
            if(!var2.equals(var4)) {
               continue;
            }

            var6 = var5;
            break;
         }

         var6 = null;
         break;
      }

      return var6;
   }

   private Message.Subject getMessageSubject(String var1) {
      String var2 = this.determineLanguage(var1);
      Iterator var3 = this.subjects.iterator();

      Message.Subject var6;
      while(true) {
         if(var3.hasNext()) {
            Message.Subject var5 = (Message.Subject)var3.next();
            String var4 = var5.language;
            if(!var2.equals(var4)) {
               continue;
            }

            var6 = var5;
            break;
         }

         var6 = null;
         break;
      }

      return var6;
   }

   public Message.Body addBody(String var1, String var2) {
      String var3 = this.determineLanguage(var1);
      Message.Body var4 = new Message.Body(var3, var2, (Message.1)null);
      this.bodies.add(var4);
      return var4;
   }

   public Message.Subject addSubject(String var1, String var2) {
      String var3 = this.determineLanguage(var1);
      Message.Subject var4 = new Message.Subject(var3, var2, (Message.1)null);
      this.subjects.add(var4);
      return var4;
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
               Message var19 = (Message)var1;
               if(!super.equals(var19)) {
                  var2 = false;
               } else {
                  int var5 = this.bodies.size();
                  int var6 = var19.bodies.size();
                  if(var5 == var6) {
                     Set var7 = this.bodies;
                     Set var8 = var19.bodies;
                     if(var7.containsAll(var8)) {
                        label76: {
                           if(this.language != null) {
                              String var9 = this.language;
                              String var10 = var19.language;
                              if(var9.equals(var10)) {
                                 break label76;
                              }
                           } else if(var19.language == null) {
                              break label76;
                           }

                           var2 = false;
                           return var2;
                        }

                        int var11 = this.subjects.size();
                        int var12 = var19.subjects.size();
                        if(var11 == var12) {
                           Set var13 = this.subjects;
                           Set var14 = var19.subjects;
                           if(var13.containsAll(var14)) {
                              label78: {
                                 if(this.thread != null) {
                                    String var15 = this.thread;
                                    String var16 = var19.thread;
                                    if(!var15.equals(var16)) {
                                       break label78;
                                    }
                                 } else if(var19.thread != null) {
                                    break label78;
                                 }

                                 Message.Type var17 = this.type;
                                 Message.Type var18 = var19.type;
                                 if(var17 == var18) {
                                    var2 = true;
                                 } else {
                                    var2 = false;
                                 }

                                 return var2;
                              }

                              var2 = false;
                              return var2;
                           }
                        }

                        var2 = false;
                        return var2;
                     }
                  }

                  var2 = false;
               }

               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   public Collection<Message.Body> getBodies() {
      return Collections.unmodifiableCollection(this.bodies);
   }

   public String getBody() {
      return this.getBody((String)null);
   }

   public String getBody(String var1) {
      Message.Body var2 = this.getMessageBody(var1);
      String var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = var2.message;
      }

      return var3;
   }

   public Collection<String> getBodyLanguages() {
      Message.Body var1 = this.getMessageBody((String)null);
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.bodies.iterator();

      while(var3.hasNext()) {
         Message.Body var6 = (Message.Body)var3.next();
         if(!var6.equals(var1)) {
            String var4 = var6.language;
            var2.add(var4);
         }
      }

      return Collections.unmodifiableCollection(var2);
   }

   public String getLanguage() {
      return this.language;
   }

   public String getSubject() {
      return this.getSubject((String)null);
   }

   public String getSubject(String var1) {
      Message.Subject var2 = this.getMessageSubject(var1);
      String var3;
      if(var2 == null) {
         var3 = null;
      } else {
         var3 = var2.subject;
      }

      return var3;
   }

   public Collection<String> getSubjectLanguages() {
      Message.Subject var1 = this.getMessageSubject((String)null);
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.subjects.iterator();

      while(var3.hasNext()) {
         Message.Subject var6 = (Message.Subject)var3.next();
         if(!var6.equals(var1)) {
            String var4 = var6.language;
            var2.add(var4);
         }
      }

      return Collections.unmodifiableCollection(var2);
   }

   public Collection<Message.Subject> getSubjects() {
      return Collections.unmodifiableCollection(this.subjects);
   }

   public String getThread() {
      return this.thread;
   }

   public Message.Type getType() {
      return this.type;
   }

   public int hashCode() {
      int var1;
      if(this.type != null) {
         var1 = this.type.hashCode();
      } else {
         var1 = 0;
      }

      int var2 = var1 * 31;
      int var3 = this.subjects.hashCode();
      int var4 = (var2 + var3) * 31;
      int var5;
      if(this.thread != null) {
         var5 = this.thread.hashCode();
      } else {
         var5 = 0;
      }

      int var6 = (var4 + var5) * 31;
      int var7;
      if(this.language != null) {
         var7 = this.language.hashCode();
      } else {
         var7 = 0;
      }

      int var8 = (var6 + var7) * 31;
      int var9 = this.bodies.hashCode();
      return var8 + var9;
   }

   public boolean removeBody(String var1) {
      String var2 = this.determineLanguage(var1);
      Iterator var3 = this.bodies.iterator();

      boolean var6;
      while(true) {
         if(var3.hasNext()) {
            Message.Body var4 = (Message.Body)var3.next();
            String var5 = var4.language;
            if(!var2.equals(var5)) {
               continue;
            }

            var6 = this.bodies.remove(var4);
            break;
         }

         var6 = false;
         break;
      }

      return var6;
   }

   public boolean removeBody(Message.Body var1) {
      return this.bodies.remove(var1);
   }

   public boolean removeSubject(String var1) {
      String var2 = this.determineLanguage(var1);
      Iterator var3 = this.subjects.iterator();

      boolean var6;
      while(true) {
         if(var3.hasNext()) {
            Message.Subject var4 = (Message.Subject)var3.next();
            String var5 = var4.language;
            if(!var2.equals(var5)) {
               continue;
            }

            var6 = this.subjects.remove(var4);
            break;
         }

         var6 = false;
         break;
      }

      return var6;
   }

   public boolean removeSubject(Message.Subject var1) {
      return this.subjects.remove(var1);
   }

   public void setBody(String var1) {
      if(var1 == null) {
         boolean var2 = this.removeBody("");
      } else {
         this.addBody((String)null, var1);
      }
   }

   public void setLanguage(String var1) {
      this.language = var1;
   }

   public void setSubject(String var1) {
      if(var1 == null) {
         boolean var2 = this.removeSubject("");
      } else {
         this.addSubject((String)null, var1);
      }
   }

   public void setThread(String var1) {
      this.thread = var1;
   }

   public void setType(Message.Type var1) {
      if(var1 == null) {
         throw new IllegalArgumentException("Type cannot be null.");
      } else {
         this.type = var1;
      }
   }

   public String toXML() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("<message");
      if(this.getXmlns() != null) {
         StringBuilder var3 = var1.append(" xmlns=\"");
         String var4 = this.getXmlns();
         StringBuilder var5 = var3.append(var4).append("\"");
      }

      if(this.language != null) {
         StringBuilder var6 = var1.append(" xml:lang=\"");
         String var7 = this.getLanguage();
         StringBuilder var8 = var6.append(var7).append("\"");
      }

      if(this.getPacketID() != null) {
         StringBuilder var9 = var1.append(" id=\"");
         String var10 = this.getPacketID();
         StringBuilder var11 = var9.append(var10).append("\"");
      }

      if(this.getTo() != null) {
         StringBuilder var12 = var1.append(" to=\"");
         String var13 = StringUtils.escapeForXML(this.getTo());
         StringBuilder var14 = var12.append(var13).append("\"");
      }

      if(this.getFrom() != null) {
         StringBuilder var15 = var1.append(" from=\"");
         String var16 = StringUtils.escapeForXML(this.getFrom());
         StringBuilder var17 = var15.append(var16).append("\"");
      }

      Message.Type var18 = this.type;
      Message.Type var19 = Message.Type.normal;
      if(var18 != var19) {
         StringBuilder var20 = var1.append(" type=\"");
         Message.Type var21 = this.type;
         StringBuilder var22 = var20.append(var21).append("\"");
      }

      StringBuilder var23 = var1.append(">");
      Message.Subject var24 = this.getMessageSubject((String)null);
      if(var24 != null) {
         StringBuilder var25 = var1.append("<subject>");
         String var26 = StringUtils.escapeForXML(var24.getSubject());
         var25.append(var26);
         StringBuilder var28 = var1.append("</subject>");
      }

      StringBuilder var37;
      for(Iterator var29 = this.getSubjects().iterator(); var29.hasNext(); var37 = var1.append("</subject>")) {
         Message.Subject var30 = (Message.Subject)var29.next();
         StringBuilder var31 = (new StringBuilder()).append("<subject xml:lang=\"");
         String var32 = var30.getLanguage();
         String var33 = var31.append(var32).append("\">").toString();
         var1.append(var33);
         String var35 = StringUtils.escapeForXML(var30.getSubject());
         var1.append(var35);
      }

      Message.Body var38 = this.getMessageBody((String)null);
      if(var38 != null) {
         StringBuilder var39 = var1.append("<body>");
         String var40 = StringUtils.escapeForXML(var38.message);
         StringBuilder var41 = var39.append(var40).append("</body>");
      }

      Iterator var42 = this.getBodies().iterator();

      while(var42.hasNext()) {
         Message.Body var43 = (Message.Body)var42.next();
         if(!var43.equals(var38)) {
            StringBuilder var44 = var1.append("<body xml:lang=\"");
            String var45 = var43.getLanguage();
            StringBuilder var46 = var44.append(var45).append("\">");
            String var47 = StringUtils.escapeForXML(var43.getMessage());
            var1.append(var47);
            StringBuilder var49 = var1.append("</body>");
         }
      }

      if(this.thread != null) {
         StringBuilder var50 = var1.append("<thread>");
         String var51 = this.thread;
         StringBuilder var52 = var50.append(var51).append("</thread>");
      }

      Message.Type var53 = this.type;
      Message.Type var54 = Message.Type.error;
      if(var53 == var54) {
         XMPPError var55 = this.getError();
         if(var55 != null) {
            String var56 = var55.toXML();
            var1.append(var56);
         }
      }

      String var58 = this.getExtensionsXML();
      var1.append(var58);
      StringBuilder var60 = var1.append("</message>");
      return var1.toString();
   }

   public static class Body {

      private String language;
      private String message;


      private Body(String var1, String var2) {
         if(var1 == null) {
            throw new NullPointerException("Language cannot be null.");
         } else if(var2 == null) {
            throw new NullPointerException("Message cannot be null.");
         } else {
            this.language = var1;
            this.message = var2;
         }
      }

      // $FF: synthetic method
      Body(String var1, String var2, Message.1 var3) {
         this(var1, var2);
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
                  label35: {
                     Message.Body var9 = (Message.Body)var1;
                     if(this.language != null) {
                        String var5 = this.language;
                        String var6 = var9.language;
                        if(!var5.equals(var6)) {
                           break label35;
                        }
                     } else if(var9.language != null) {
                        break label35;
                     }

                     String var7 = this.message;
                     String var8 = var9.message;
                     var2 = var7.equals(var8);
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

      public String getLanguage() {
         return this.language;
      }

      public String getMessage() {
         return this.message;
      }

      public int hashCode() {
         int var1 = this.message.hashCode() * 31;
         int var2;
         if(this.language != null) {
            var2 = this.language.hashCode();
         } else {
            var2 = 0;
         }

         return var1 + var2;
      }
   }

   public static class Subject {

      private String language;
      private String subject;


      private Subject(String var1, String var2) {
         if(var1 == null) {
            throw new NullPointerException("Language cannot be null.");
         } else if(var2 == null) {
            throw new NullPointerException("Subject cannot be null.");
         } else {
            this.language = var1;
            this.subject = var2;
         }
      }

      // $FF: synthetic method
      Subject(String var1, String var2, Message.1 var3) {
         this(var1, var2);
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
                  Message.Subject var9 = (Message.Subject)var1;
                  String var5 = this.language;
                  String var6 = var9.language;
                  if(!var5.equals(var6)) {
                     var2 = false;
                  } else {
                     String var7 = this.subject;
                     String var8 = var9.subject;
                     var2 = var7.equals(var8);
                  }

                  return var2;
               }
            }

            var2 = false;
         }

         return var2;
      }

      public String getLanguage() {
         return this.language;
      }

      public String getSubject() {
         return this.subject;
      }

      public int hashCode() {
         int var1 = this.subject.hashCode() * 31;
         int var2 = this.language.hashCode();
         return var1 + var2;
      }
   }

   public static enum Type {

      // $FF: synthetic field
      private static final Message.Type[] $VALUES;
      chat("chat", 1),
      error("error", 4),
      groupchat("groupchat", 2),
      headline("headline", 3),
      normal("normal", 0);


      static {
         Message.Type[] var0 = new Message.Type[5];
         Message.Type var1 = normal;
         var0[0] = var1;
         Message.Type var2 = chat;
         var0[1] = var2;
         Message.Type var3 = groupchat;
         var0[2] = var3;
         Message.Type var4 = headline;
         var0[3] = var4;
         Message.Type var5 = error;
         var0[4] = var5;
         $VALUES = var0;
      }

      private Type(String var1, int var2) {}

      public static Message.Type fromString(String var0) {
         Message.Type var1;
         Message.Type var2;
         try {
            var1 = valueOf(var0);
         } catch (Exception var4) {
            var2 = normal;
            return var2;
         }

         var2 = var1;
         return var2;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
