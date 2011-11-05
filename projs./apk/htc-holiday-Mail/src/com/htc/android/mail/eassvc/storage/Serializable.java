package com.htc.android.mail.eassvc.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Serializable {

   void deserialize(DataInputStream var1) throws IOException;

   void serialize(DataOutputStream var1) throws IOException;
}
