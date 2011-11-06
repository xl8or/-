// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacebookChatMessage.java

package com.facebook.katana.model;

import org.jivesoftware.smack.packet.Message;

// Referenced classes of package com.facebook.katana.model:
//            FacebookChatUser

public class FacebookChatMessage
{
    public static final class Type extends Enum
    {

        public static Type valueOf(String s)
        {
            return (Type)Enum.valueOf(com/facebook/katana/model/FacebookChatMessage$Type, s);
        }

        public static Type[] values()
        {
            return (Type[])$VALUES.clone();
        }

        private static final Type $VALUES[];
        public static final Type ACTIVE;
        public static final Type COMPOSING;
        public static final Type NORMAL;

        static 
        {
            NORMAL = new Type("NORMAL", 0);
            COMPOSING = new Type("COMPOSING", 1);
            ACTIVE = new Type("ACTIVE", 2);
            Type atype[] = new Type[3];
            atype[0] = NORMAL;
            atype[1] = COMPOSING;
            atype[2] = ACTIVE;
            $VALUES = atype;
        }

        private Type(String s, int i)
        {
            super(s, i);
        }
    }


    public FacebookChatMessage(long l, long l1, String s, Long long1)
    {
        this(l, l1, s, long1, Type.NORMAL);
    }

    public FacebookChatMessage(long l, long l1, String s, Long long1, Type type)
    {
        mSenderUid = l;
        mRecipientUid = l1;
        mBody = s;
        mTimestamp = long1;
        mMessageType = type;
    }

    public FacebookChatMessage(Message message)
    {
        mSenderUid = FacebookChatUser.getUid(message.getFrom());
        mRecipientUid = FacebookChatUser.getUid(message.getTo());
        mBody = message.getBody();
        mTimestamp = Long.valueOf(System.currentTimeMillis());
        if(message.getBody() == null)
        {
            if(message.toXML().contains("composing"))
                mMessageType = Type.COMPOSING;
            else
                mMessageType = Type.ACTIVE;
        } else
        {
            mMessageType = Type.NORMAL;
        }
    }

    public final String mBody;
    public final Type mMessageType;
    public final long mRecipientUid;
    public final long mSenderUid;
    public final Long mTimestamp;
}
