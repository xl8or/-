// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMPPException.java

package org.jivesoftware.smack;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.jivesoftware.smack.packet.StreamError;
import org.jivesoftware.smack.packet.XMPPError;

public class XMPPException extends Exception
{

    public XMPPException()
    {
        streamError = null;
        error = null;
        wrappedThrowable = null;
    }

    public XMPPException(String s)
    {
        super(s);
        streamError = null;
        error = null;
        wrappedThrowable = null;
    }

    public XMPPException(String s, Throwable throwable)
    {
        super(s);
        streamError = null;
        error = null;
        wrappedThrowable = null;
        wrappedThrowable = throwable;
    }

    public XMPPException(String s, XMPPError xmpperror)
    {
        super(s);
        streamError = null;
        error = null;
        wrappedThrowable = null;
        error = xmpperror;
    }

    public XMPPException(String s, XMPPError xmpperror, Throwable throwable)
    {
        super(s);
        streamError = null;
        error = null;
        wrappedThrowable = null;
        error = xmpperror;
        wrappedThrowable = throwable;
    }

    public XMPPException(Throwable throwable)
    {
        streamError = null;
        error = null;
        wrappedThrowable = null;
        wrappedThrowable = throwable;
    }

    public XMPPException(StreamError streamerror)
    {
        streamError = null;
        error = null;
        wrappedThrowable = null;
        streamError = streamerror;
    }

    public XMPPException(XMPPError xmpperror)
    {
        streamError = null;
        error = null;
        wrappedThrowable = null;
        error = xmpperror;
    }

    public String getMessage()
    {
        String s = super.getMessage();
        if(s != null || error == null) goto _L2; else goto _L1
_L1:
        s = error.toString();
_L4:
        return s;
_L2:
        if(s == null && streamError != null)
            s = streamError.toString();
        if(true) goto _L4; else goto _L3
_L3:
    }

    public StreamError getStreamError()
    {
        return streamError;
    }

    public Throwable getWrappedThrowable()
    {
        return wrappedThrowable;
    }

    public XMPPError getXMPPError()
    {
        return error;
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream printstream)
    {
        super.printStackTrace(printstream);
        if(wrappedThrowable != null)
        {
            printstream.println("Nested Exception: ");
            wrappedThrowable.printStackTrace(printstream);
        }
    }

    public void printStackTrace(PrintWriter printwriter)
    {
        super.printStackTrace(printwriter);
        if(wrappedThrowable != null)
        {
            printwriter.println("Nested Exception: ");
            wrappedThrowable.printStackTrace(printwriter);
        }
    }

    public String toString()
    {
        StringBuilder stringbuilder = new StringBuilder();
        String s = super.getMessage();
        if(s != null)
            stringbuilder.append(s).append(": ");
        if(error != null)
            stringbuilder.append(error);
        if(streamError != null)
            stringbuilder.append(streamError);
        if(wrappedThrowable != null)
            stringbuilder.append("\n  -- caused by: ").append(wrappedThrowable);
        return stringbuilder.toString();
    }

    private XMPPError error;
    private StreamError streamError;
    private Throwable wrappedThrowable;
}
