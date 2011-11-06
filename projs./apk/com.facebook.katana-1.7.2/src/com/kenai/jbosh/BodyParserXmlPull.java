// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BodyParserXmlPull.java

package com.kenai.jbosh;

import java.io.IOException;
import java.io.StringReader;
import java.lang.ref.SoftReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmlpull.v1.*;

// Referenced classes of package com.kenai.jbosh:
//            BodyParser, BOSHException, BodyParserResults, QName, 
//            AbstractBody, BodyQName

final class BodyParserXmlPull
    implements BodyParser
{

    BodyParserXmlPull()
    {
    }

    private static XmlPullParser getXmlPullParser()
    {
        XmlPullParser xmlpullparser = (XmlPullParser)((SoftReference)XPP_PARSER.get()).get();
        if(xmlpullparser == null)
            try
            {
                XmlPullParserFactory xmlpullparserfactory = XmlPullParserFactory.newInstance();
                xmlpullparserfactory.setNamespaceAware(true);
                xmlpullparserfactory.setValidating(false);
                xmlpullparser = xmlpullparserfactory.newPullParser();
                SoftReference softreference = new SoftReference(xmlpullparser);
                XPP_PARSER.set(softreference);
            }
            catch(Exception exception)
            {
                throw new IllegalStateException("Could not create XmlPull parser", exception);
            }
        return xmlpullparser;
    }

    public BodyParserResults parse(String s)
        throws BOSHException
    {
        BodyParserResults bodyparserresults = new BodyParserResults();
        XmlPullParser xmlpullparser;
        int i;
        xmlpullparser = getXmlPullParser();
        xmlpullparser.setInput(new StringReader(s));
        i = xmlpullparser.getEventType();
_L4:
        if(i == 1) goto _L2; else goto _L1
_L1:
        if(i == 2)
        {
            if(LOG.isLoggable(Level.FINEST))
                LOG.finest((new StringBuilder()).append("Start tag: ").append(xmlpullparser.getName()).toString());
            String s1 = xmlpullparser.getPrefix();
            if(s1 == null)
                s1 = "";
            String s2 = xmlpullparser.getNamespace();
            String s3 = xmlpullparser.getName();
            QName qname = new QName(s2, s3, s1);
            if(LOG.isLoggable(Level.FINEST))
            {
                LOG.finest("Start element: ");
                LOG.finest((new StringBuilder()).append("    prefix: ").append(s1).toString());
                LOG.finest((new StringBuilder()).append("    URI: ").append(s2).toString());
                LOG.finest((new StringBuilder()).append("    local: ").append(s3).toString());
            }
            BodyQName bodyqname = AbstractBody.getBodyQName();
            if(!bodyqname.equalsQName(qname))
                throw new IllegalStateException((new StringBuilder()).append("Root element was not '").append(bodyqname.getLocalPart()).append("' in the '").append(bodyqname.getNamespaceURI()).append("' namespace.  (Was '").append(s3).append("' in '").append(s2).append("')").toString());
            break MISSING_BLOCK_LABEL_547;
        }
          goto _L3
        Object obj;
        obj;
_L7:
        throw new BOSHException((new StringBuilder()).append("Could not parse body:\n").append(s).toString(), ((Throwable) (obj)));
_L3:
        i = xmlpullparser.next();
          goto _L4
_L6:
        int j;
        if(j >= xmlpullparser.getAttributeCount()) goto _L2; else goto _L5
_L5:
        String s4 = xmlpullparser.getAttributeNamespace(j);
        if(s4.length() == 0)
            s4 = xmlpullparser.getNamespace(null);
        String s5 = xmlpullparser.getAttributePrefix(j);
        if(s5 == null)
            s5 = "";
        String s6 = xmlpullparser.getAttributeName(j);
        String s7 = xmlpullparser.getAttributeValue(j);
        BodyQName bodyqname1 = BodyQName.createWithPrefix(s4, s6, s5);
        if(LOG.isLoggable(Level.FINEST))
            LOG.finest((new StringBuilder()).append("        Attribute: {").append(s4).append("}").append(s6).append(" = '").append(s7).append("'").toString());
        bodyparserresults.addBodyAttributeValue(bodyqname1, s7);
        j++;
          goto _L6
_L2:
        return bodyparserresults;
        obj;
          goto _L7
        obj;
          goto _L7
        j = 0;
          goto _L6
    }

    private static final Logger LOG = Logger.getLogger(com/kenai/jbosh/BodyParserXmlPull.getName());
    private static final ThreadLocal XPP_PARSER = new ThreadLocal() {

        protected volatile Object initialValue()
        {
            return initialValue();
        }

        protected SoftReference initialValue()
        {
            return new SoftReference(null);
        }

    }
;

}
