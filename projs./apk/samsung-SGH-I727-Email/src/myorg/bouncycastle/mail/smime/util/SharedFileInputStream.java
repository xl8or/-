package myorg.bouncycastle.mail.smime.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.mail.internet.SharedInputStream;

public class SharedFileInputStream extends FilterInputStream implements SharedInputStream {

   private final File _file;
   private final long _length;
   private long _markedPosition;
   private final SharedFileInputStream _parent;
   private long _position;
   private final long _start;
   private List _subStreams;


   public SharedFileInputStream(File var1) throws IOException {
      long var2 = var1.length();
      this(var1, 0L, var2);
   }

   private SharedFileInputStream(File var1, long var2, long var4) throws IOException {
      FileInputStream var6 = new FileInputStream(var1);
      BufferedInputStream var7 = new BufferedInputStream(var6);
      super(var7);
      LinkedList var8 = new LinkedList();
      this._subStreams = var8;
      this._parent = null;
      this._file = var1;
      this._start = var2;
      this._length = var4;
      this.in.skip(var2);
   }

   public SharedFileInputStream(String var1) throws IOException {
      File var2 = new File(var1);
      this(var2);
   }

   private SharedFileInputStream(SharedFileInputStream var1, long var2, long var4) throws IOException {
      File var6 = var1._file;
      FileInputStream var7 = new FileInputStream(var6);
      BufferedInputStream var8 = new BufferedInputStream(var7);
      super(var8);
      LinkedList var9 = new LinkedList();
      this._subStreams = var9;
      this._parent = var1;
      File var10 = var1._file;
      this._file = var10;
      this._start = var2;
      this._length = var4;
      this.in.skip(var2);
   }

   public void dispose() throws IOException {
      Iterator var1 = this._subStreams.iterator();

      while(var1.hasNext()) {
         try {
            ((SharedFileInputStream)var1.next()).dispose();
         } catch (IOException var3) {
            ;
         }
      }

      this.in.close();
   }

   public long getPosition() {
      return this._position;
   }

   public SharedFileInputStream getRoot() {
      SharedFileInputStream var1;
      if(this._parent != null) {
         var1 = this._parent.getRoot();
      } else {
         var1 = this;
      }

      return var1;
   }

   public void mark(int var1) {
      long var2 = this._position;
      this._markedPosition = var2;
      this.in.mark(var1);
   }

   public boolean markSupported() {
      return true;
   }

   public InputStream newStream(long param1, long param3) {
      // $FF: Couldn't be decompiled
   }

   public int read() throws IOException {
      long var1 = this._position;
      long var3 = this._length;
      int var5;
      if(var1 == var3) {
         var5 = -1;
      } else {
         long var6 = this._position + 1L;
         this._position = var6;
         var5 = this.in.read();
      }

      return var5;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = var1.length;
      return this.read(var1, 0, var2);
   }

   public int read(byte[] param1, int param2, int param3) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public void reset() throws IOException {
      long var1 = this._markedPosition;
      this._position = var1;
      this.in.reset();
   }

   public long skip(long var1) throws IOException {
      long var3;
      for(var3 = 0L; var3 != var1 && this.read() >= 0; ++var3) {
         ;
      }

      return var3;
   }
}
