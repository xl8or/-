package org.kxml2.wap.syncml;

import org.kxml2.wap.WbxmlParser;
import org.kxml2.wap.WbxmlSerializer;

public abstract class SyncML {

   public static final String[] TAG_TABLE_0;
   public static final String[] TAG_TABLE_1;
   public static final String[] TAG_TABLE_2_DM;


   static {
      String[] var0 = new String[]{"Add", "Alert", "Archive", "Atomic", "Chal", "Cmd", "CmdID", "CmdRef", "Copy", "Cred", "Data", "Delete", "Exec", "Final", "Get", "Item", "Lang", "LocName", "LocURI", "Map", "MapItem", "Meta", "MsgID", "MsgRef", "NoResp", "NoResults", "Put", "Replace", "RespURI", "Results", "Search", "Sequence", "SessionID", "SftDel", "Source", "SourceRef", "Status", "Sync", "SyncBody", "SyncHdr", "SyncML", "Target", "TargetRef", "Reserved for future use", "VerDTD", "VerProto", "NumberOfChanged", "MoreData", "Field", "Filter", "Record", "FilterType", "SourceParent", "TargetParent", "Move", "Correlator"};
      TAG_TABLE_0 = var0;
      String[] var1 = new String[]{"Anchor", "EMI", "Format", "FreeID", "FreeMem", "Last", "Mark", "MaxMsgSize", "Mem", "MetInf", "Next", "NextNonce", "SharedMem", "Size", "Type", "Version", "MaxObjSize", "FieldLevel"};
      TAG_TABLE_1 = var1;
      String[] var2 = new String[]{"AccessType", "ACL", "Add", "b64", "bin", "bool", "chr", "CaseSense", "CIS", "Copy", "CS", "date", "DDFName", "DefaultValue", "Delete", "Description", "DDFFormat", "DFProperties", "DFTitle", "DFType", "Dynamic", "Exec", "float", "Format", "Get", "int", "Man", "MgmtTree", "MIME", "Mod", "Name", "Node", "node", "NodeName", "null", "Occurence", "One", "OneOrMore", "OneOrN", "Path", "Permanent", "Replace", "RTProperties", "Scope", "Size", "time", "Title", "TStamp", "Type", "Value", "VerDTD", "VerNo", "xml", "ZeroOrMore", "ZeroOrN", "ZeroOrOne"};
      TAG_TABLE_2_DM = var2;
   }

   public SyncML() {}

   public static WbxmlParser createDMParser() {
      WbxmlParser var0 = createParser();
      String[] var1 = TAG_TABLE_2_DM;
      var0.setTagTable(2, var1);
      return var0;
   }

   public static WbxmlSerializer createDMSerializer() {
      WbxmlSerializer var0 = createSerializer();
      String[] var1 = TAG_TABLE_2_DM;
      var0.setTagTable(2, var1);
      return var0;
   }

   public static WbxmlParser createParser() {
      WbxmlParser var0 = new WbxmlParser();
      String[] var1 = TAG_TABLE_0;
      var0.setTagTable(0, var1);
      String[] var2 = TAG_TABLE_1;
      var0.setTagTable(1, var2);
      return var0;
   }

   public static WbxmlSerializer createSerializer() {
      WbxmlSerializer var0 = new WbxmlSerializer();
      String[] var1 = TAG_TABLE_0;
      var0.setTagTable(0, var1);
      String[] var2 = TAG_TABLE_1;
      var0.setTagTable(1, var2);
      return var0;
   }
}
