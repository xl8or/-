package com.google.wireless.gdata2;

import com.google.wireless.gdata2.GDataException;
import com.google.wireless.gdata2.data.Entry;

public class ConflictDetectedException extends GDataException {

   private final Entry conflictingEntry;


   public ConflictDetectedException(Entry var1) {
      this.conflictingEntry = var1;
   }

   public Entry getConflictingEntry() {
      return this.conflictingEntry;
   }
}
