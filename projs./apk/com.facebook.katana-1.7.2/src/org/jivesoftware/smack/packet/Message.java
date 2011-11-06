// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Message.java

package org.jivesoftware.smack.packet;

import java.util.*;
import org.jivesoftware.smack.util.StringUtils;

// Referenced classes of package org.jivesoftware.smack.packet:
//            Packet, XMPPError

public class Message extends Packet
{
    public static final class Type extends Enum
    {

        public static Type fromString(String s)
        {
            Type type2 = valueOf(s);
            Type type1 = type2;
_L2:
            return type1;
            Exception exception;
            exception;
            type1 = normal;
            if(true) goto _L2; else goto _L1
_L1:
        }

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(org/jivesoftware/smack/packet/Message$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type chat;
        public static final Type error;
        public static final Type groupchat;
        public static final Type headline;
        public static final Type normal;

        static 
        {
            normal = new Type("normal", 0);
            chat = new Type("chat", 1);
            groupchat = new Type("groupchat", 2);
            headline = new Type("headline", 3);
            error = new Type("error", 4);
            Type atype[] = new Type[5];
            atype[0] = normal;
            atype[1] = chat;
            atype[2] = groupchat;
            atype[3] = headline;
            atype[4] = error;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }

    public static class Body
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if(this == obj)
                flag = true;
            else
            if(obj == null || getClass() != obj.getClass())
            {
                flag = false;
            } else
            {
                Body body = (Body)obj;
                if(language == null ? body.language != null : !language.equals(body.language))
                    flag = false;
                else
                    flag = message.equals(body.message);
            }
            return flag;
        }

        public String getLanguage()
        {
            return language;
        }

        public String getMessage()
        {
            return message;
        }

        public int hashCode()
        {
            int i = 31 * message.hashCode();
            int j;
            if(language != null)
                j = language.hashCode();
            else
                j = 0;
            return i + j;
        }

        private String language;
        private String message;



        private Body(String s, String s1)
        {
            if(s == null)
                throw new NullPointerException("Language cannot be null.");
            if(s1 == null)
            {
                throw new NullPointerException("Message cannot be null.");
            } else
            {
                language = s;
                message = s1;
                return;
            }
        }

    }

    public static class Subject
    {

        public boolean equals(Object obj)
        {
            boolean flag;
            if(this == obj)
                flag = true;
            else
            if(obj == null || getClass() != obj.getClass())
            {
                flag = false;
            } else
            {
                Subject subject1 = (Subject)obj;
                if(!language.equals(subject1.language))
                    flag = false;
                else
                    flag = subject.equals(subject1.subject);
            }
            return flag;
        }

        public String getLanguage()
        {
            return language;
        }

        public String getSubject()
        {
            return subject;
        }

        public int hashCode()
        {
            return 31 * subject.hashCode() + language.hashCode();
        }

        private String language;
        private String subject;



        private Subject(String s, String s1)
        {
            if(s == null)
                throw new NullPointerException("Language cannot be null.");
            if(s1 == null)
            {
                throw new NullPointerException("Subject cannot be null.");
            } else
            {
                language = s;
                subject = s1;
                return;
            }
        }

    }


    public Message()
    {
        type = Type.normal;
        thread = null;
        subjects = new HashSet();
        bodies = new HashSet();
    }

    public Message(String s)
    {
        type = Type.normal;
        thread = null;
        subjects = new HashSet();
        bodies = new HashSet();
        setTo(s);
    }

    public Message(String s, Type type1)
    {
        type = Type.normal;
        thread = null;
        subjects = new HashSet();
        bodies = new HashSet();
        setTo(s);
        type = type1;
    }

    private String determineLanguage(String s)
    {
        String s1;
        if("".equals(s))
            s1 = null;
        else
            s1 = s;
        if(s1 != null || language == null) goto _L2; else goto _L1
_L1:
        s1 = language;
_L4:
        return s1;
_L2:
        if(s1 == null)
            s1 = getDefaultLanguage();
        if(true) goto _L4; else goto _L3
_L3:
    }

    private Body getMessageBody(String s)
    {
        String s1;
        Iterator iterator;
        s1 = determineLanguage(s);
        iterator = bodies.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Body body1 = (Body)iterator.next();
        if(!s1.equals(body1.language)) goto _L4; else goto _L3
_L3:
        Body body = body1;
_L6:
        return body;
_L2:
        body = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    private Subject getMessageSubject(String s)
    {
        String s1;
        Iterator iterator;
        s1 = determineLanguage(s);
        iterator = subjects.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Subject subject1 = (Subject)iterator.next();
        if(!s1.equals(subject1.language)) goto _L4; else goto _L3
_L3:
        Subject subject = subject1;
_L6:
        return subject;
_L2:
        subject = null;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public Body addBody(String s, String s1)
    {
        Body body = new Body(determineLanguage(s), s1);
        bodies.add(body);
        return body;
    }

    public Subject addSubject(String s, String s1)
    {
        Subject subject = new Subject(determineLanguage(s), s1);
        subjects.add(subject);
        return subject;
    }

    public boolean equals(Object obj)
    {
        boolean flag;
        if(this == obj)
            flag = true;
        else
        if(obj == null || getClass() != obj.getClass())
        {
            flag = false;
        } else
        {
            Message message = (Message)obj;
            if(!super.equals(message))
                flag = false;
            else
            if(bodies.size() != message.bodies.size() || !bodies.containsAll(message.bodies))
                flag = false;
            else
            if(language == null ? message.language != null : !language.equals(message.language))
                flag = false;
            else
            if(subjects.size() != message.subjects.size() || !subjects.containsAll(message.subjects))
                flag = false;
            else
            if(thread == null ? message.thread != null : !thread.equals(message.thread))
                flag = false;
            else
            if(type == message.type)
                flag = true;
            else
                flag = false;
        }
        return flag;
    }

    public Collection getBodies()
    {
        return Collections.unmodifiableCollection(bodies);
    }

    public String getBody()
    {
        return getBody(null);
    }

    public String getBody(String s)
    {
        Body body = getMessageBody(s);
        String s1;
        if(body == null)
            s1 = null;
        else
            s1 = body.message;
        return s1;
    }

    public Collection getBodyLanguages()
    {
        Body body = getMessageBody(null);
        ArrayList arraylist = new ArrayList();
        Iterator iterator = bodies.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Body body1 = (Body)iterator.next();
            if(!body1.equals(body))
                arraylist.add(body1.language);
        } while(true);
        return Collections.unmodifiableCollection(arraylist);
    }

    public String getLanguage()
    {
        return language;
    }

    public String getSubject()
    {
        return getSubject(null);
    }

    public String getSubject(String s)
    {
        Subject subject = getMessageSubject(s);
        String s1;
        if(subject == null)
            s1 = null;
        else
            s1 = subject.subject;
        return s1;
    }

    public Collection getSubjectLanguages()
    {
        Subject subject = getMessageSubject(null);
        ArrayList arraylist = new ArrayList();
        Iterator iterator = subjects.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Subject subject1 = (Subject)iterator.next();
            if(!subject1.equals(subject))
                arraylist.add(subject1.language);
        } while(true);
        return Collections.unmodifiableCollection(arraylist);
    }

    public Collection getSubjects()
    {
        return Collections.unmodifiableCollection(subjects);
    }

    public String getThread()
    {
        return thread;
    }

    public Type getType()
    {
        return type;
    }

    public int hashCode()
    {
        int i;
        int j;
        int k;
        int l;
        int i1;
        if(type != null)
            i = type.hashCode();
        else
            i = 0;
        j = 31 * (i * 31 + subjects.hashCode());
        if(thread != null)
            k = thread.hashCode();
        else
            k = 0;
        l = 31 * (j + k);
        if(language != null)
            i1 = language.hashCode();
        else
            i1 = 0;
        return 31 * (l + i1) + bodies.hashCode();
    }

    public boolean removeBody(String s)
    {
        String s1;
        Iterator iterator;
        s1 = determineLanguage(s);
        iterator = bodies.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Body body = (Body)iterator.next();
        if(!s1.equals(body.language)) goto _L4; else goto _L3
_L3:
        boolean flag = bodies.remove(body);
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean removeBody(Body body)
    {
        return bodies.remove(body);
    }

    public boolean removeSubject(String s)
    {
        String s1;
        Iterator iterator;
        s1 = determineLanguage(s);
        iterator = subjects.iterator();
_L4:
        if(!iterator.hasNext()) goto _L2; else goto _L1
_L1:
        Subject subject = (Subject)iterator.next();
        if(!s1.equals(subject.language)) goto _L4; else goto _L3
_L3:
        boolean flag = subjects.remove(subject);
_L6:
        return flag;
_L2:
        flag = false;
        if(true) goto _L6; else goto _L5
_L5:
    }

    public boolean removeSubject(Subject subject)
    {
        return subjects.remove(subject);
    }

    public void setBody(String s)
    {
        if(s == null)
            removeBody("");
        else
            addBody(null, s);
    }

    public void setLanguage(String s)
    {
        language = s;
    }

    public void setSubject(String s)
    {
        if(s == null)
            removeSubject("");
        else
            addSubject(null, s);
    }

    public void setThread(String s)
    {
        thread = s;
    }

    public void setType(Type type1)
    {
        if(type1 == null)
        {
            throw new IllegalArgumentException("Type cannot be null.");
        } else
        {
            type = type1;
            return;
        }
    }

    public String toXML()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<message");
        if(getXmlns() != null)
            stringbuilder.append(" xmlns=\"").append(getXmlns()).append("\"");
        if(language != null)
            stringbuilder.append(" xml:lang=\"").append(getLanguage()).append("\"");
        if(getPacketID() != null)
            stringbuilder.append(" id=\"").append(getPacketID()).append("\"");
        if(getTo() != null)
            stringbuilder.append(" to=\"").append(StringUtils.escapeForXML(getTo())).append("\"");
        if(getFrom() != null)
            stringbuilder.append(" from=\"").append(StringUtils.escapeForXML(getFrom())).append("\"");
        if(type != Type.normal)
            stringbuilder.append(" type=\"").append(type).append("\"");
        stringbuilder.append(">");
        Subject subject = getMessageSubject(null);
        if(subject != null)
        {
            stringbuilder.append("<subject>").append(StringUtils.escapeForXML(subject.getSubject()));
            stringbuilder.append("</subject>");
        }
        for(Iterator iterator = getSubjects().iterator(); iterator.hasNext(); stringbuilder.append("</subject>"))
        {
            Subject subject1 = (Subject)iterator.next();
            stringbuilder.append((new StringBuilder()).append("<subject xml:lang=\"").append(subject1.getLanguage()).append("\">").toString());
            stringbuilder.append(StringUtils.escapeForXML(subject1.getSubject()));
        }

        Body body = getMessageBody(null);
        if(body != null)
            stringbuilder.append("<body>").append(StringUtils.escapeForXML(body.message)).append("</body>");
        Iterator iterator1 = getBodies().iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            Body body1 = (Body)iterator1.next();
            if(!body1.equals(body))
            {
                stringbuilder.append("<body xml:lang=\"").append(body1.getLanguage()).append("\">");
                stringbuilder.append(StringUtils.escapeForXML(body1.getMessage()));
                stringbuilder.append("</body>");
            }
        } while(true);
        if(thread != null)
            stringbuilder.append("<thread>").append(thread).append("</thread>");
        if(type == Type.error)
        {
            XMPPError xmpperror = getError();
            if(xmpperror != null)
                stringbuilder.append(xmpperror.toXML());
        }
        stringbuilder.append(getExtensionsXML());
        stringbuilder.append("</message>");
        return stringbuilder.toString();
    }

    private final Set bodies;
    private String language;
    private final Set subjects;
    private String thread;
    private Type type;
}
