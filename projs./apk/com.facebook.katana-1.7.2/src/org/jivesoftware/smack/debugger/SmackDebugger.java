// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmackDebugger.java

package org.jivesoftware.smack.debugger;

import java.io.Reader;
import java.io.Writer;
import org.jivesoftware.smack.PacketListener;

public interface SmackDebugger
{

    public abstract Reader getReader();

    public abstract PacketListener getReaderListener();

    public abstract Writer getWriter();

    public abstract PacketListener getWriterListener();

    public abstract Reader newConnectionReader(Reader reader);

    public abstract Writer newConnectionWriter(Writer writer);

    public abstract void userHasLogged(String s);
}
