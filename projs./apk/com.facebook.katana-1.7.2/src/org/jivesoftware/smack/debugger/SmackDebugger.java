package org.jivesoftware.smack.debugger;

import java.io.Reader;
import java.io.Writer;
import org.jivesoftware.smack.PacketListener;

public interface SmackDebugger {

   Reader getReader();

   PacketListener getReaderListener();

   Writer getWriter();

   PacketListener getWriterListener();

   Reader newConnectionReader(Reader var1);

   Writer newConnectionWriter(Writer var1);

   void userHasLogged(String var1);
}
