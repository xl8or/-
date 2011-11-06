// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SIG0.java

package org.xbill.DNS;

import java.security.PrivateKey;
import java.util.Date;

// Referenced classes of package org.xbill.DNS:
//            Options, DNSSEC, Message, Record, 
//            SIGRecord, KEYRecord

public class SIG0
{

    private SIG0()
    {
    }

    public static void signMessage(Message message, KEYRecord keyrecord, PrivateKey privatekey, SIGRecord sigrecord)
        throws DNSSEC.DNSSECException
    {
        int i = Options.intValue("sig0validity");
        if(i < 0)
            i = 300;
        long l = System.currentTimeMillis();
        message.addRecord(DNSSEC.signMessage(message, sigrecord, keyrecord, privatekey, new Date(l), new Date(l + (long)(i * 1000))), 3);
    }

    public static void verifyMessage(Message message, byte abyte0[], KEYRecord keyrecord, SIGRecord sigrecord)
        throws DNSSEC.DNSSECException
    {
        Record arecord[];
        int i;
        arecord = message.getSectionArray(3);
        i = 0;
_L3:
        if(i >= arecord.length)
            break; /* Loop/switch isn't completed */
          goto _L1
_L5:
        i++;
        if(true) goto _L3; else goto _L2
_L1:
        if(arecord[i].getType() != 24 || ((SIGRecord)arecord[i]).getTypeCovered() != 0) goto _L5; else goto _L4
_L4:
        SIGRecord sigrecord1 = (SIGRecord)arecord[i];
_L7:
        DNSSEC.verifyMessage(message, abyte0, sigrecord1, sigrecord, keyrecord);
        return;
_L2:
        sigrecord1 = null;
        if(true) goto _L7; else goto _L6
_L6:
    }

    private static final short VALIDITY = 300;
}
