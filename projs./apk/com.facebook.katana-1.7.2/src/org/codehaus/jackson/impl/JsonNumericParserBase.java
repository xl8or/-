package org.codehaus.jackson.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.impl.JsonParserBase;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.io.NumberInput;

public abstract class JsonNumericParserBase extends JsonParserBase {

   static final BigDecimal BD_MAX_INT = new BigDecimal(Long.MAX_VALUE);
   static final BigDecimal BD_MAX_LONG = new BigDecimal(Long.MAX_VALUE);
   static final BigDecimal BD_MIN_INT = new BigDecimal(Long.MIN_VALUE);
   static final BigDecimal BD_MIN_LONG = new BigDecimal(Long.MIN_VALUE);
   protected static final char CHAR_NULL = '\u0000';
   protected static final int INT_0 = 48;
   protected static final int INT_1 = 49;
   protected static final int INT_2 = 50;
   protected static final int INT_3 = 51;
   protected static final int INT_4 = 52;
   protected static final int INT_5 = 53;
   protected static final int INT_6 = 54;
   protected static final int INT_7 = 55;
   protected static final int INT_8 = 56;
   protected static final int INT_9 = 57;
   protected static final int INT_DECIMAL_POINT = 46;
   protected static final int INT_E = 69;
   protected static final int INT_MINUS = 45;
   protected static final int INT_PLUS = 43;
   protected static final int INT_e = 101;
   static final int MAX_BYTE_I = 127;
   static final double MAX_INT_D = 2.147483647E9D;
   static final double MAX_LONG_D = 9.223372036854776E18D;
   static final int MAX_SHORT_I = 32767;
   static final int MIN_BYTE_I = 128;
   static final double MIN_INT_D = -2.147483648E9D;
   static final double MIN_LONG_D = -9.223372036854776E18D;
   static final int MIN_SHORT_I = 32768;
   protected static final int NR_BIGDECIMAL = 16;
   protected static final int NR_BIGINT = 4;
   protected static final int NR_DOUBLE = 8;
   protected static final int NR_INT = 1;
   protected static final int NR_LONG = 2;
   protected static final int NR_UNKNOWN;
   protected int _numTypesValid = 0;
   protected BigDecimal _numberBigDecimal;
   protected BigInteger _numberBigInt;
   protected double _numberDouble;
   protected int _numberInt;
   protected long _numberLong;
   protected boolean _numberNegative;
   protected int mExpLength;
   protected int mFractLength;
   protected int mIntLength;


   protected JsonNumericParserBase(IOContext var1, int var2) {
      super(var1, var2);
   }

   protected void convertNumberToBigDecimal() throws IOException, JsonParseException {
      if((this._numTypesValid & 8) != 0) {
         String var1 = this.getText();
         BigDecimal var2 = new BigDecimal(var1);
         this._numberBigDecimal = var2;
      } else if((this._numTypesValid & 4) != 0) {
         BigInteger var4 = this._numberBigInt;
         BigDecimal var5 = new BigDecimal(var4);
         this._numberBigDecimal = var5;
      } else if((this._numTypesValid & 2) != 0) {
         BigDecimal var6 = BigDecimal.valueOf(this._numberLong);
         this._numberBigDecimal = var6;
      } else if((this._numTypesValid & 1) != 0) {
         BigDecimal var7 = BigDecimal.valueOf((long)this._numberInt);
         this._numberBigDecimal = var7;
      } else {
         this._throwInternal();
      }

      int var3 = this._numTypesValid | 16;
      this._numTypesValid = var3;
   }

   protected void convertNumberToBigInteger() throws IOException, JsonParseException {
      if((this._numTypesValid & 16) != 0) {
         BigInteger var1 = this._numberBigDecimal.toBigInteger();
         this._numberBigInt = var1;
      } else if((this._numTypesValid & 2) != 0) {
         BigInteger var3 = BigInteger.valueOf(this._numberLong);
         this._numberBigInt = var3;
      } else if((this._numTypesValid & 1) != 0) {
         BigInteger var4 = BigInteger.valueOf((long)this._numberInt);
         this._numberBigInt = var4;
      } else if((this._numTypesValid & 8) != 0) {
         BigInteger var5 = BigDecimal.valueOf(this._numberDouble).toBigInteger();
         this._numberBigInt = var5;
      } else {
         this._throwInternal();
      }

      int var2 = this._numTypesValid | 4;
      this._numTypesValid = var2;
   }

   protected void convertNumberToDouble() throws IOException, JsonParseException {
      if((this._numTypesValid & 16) != 0) {
         double var1 = this._numberBigDecimal.doubleValue();
         this._numberDouble = var1;
      } else if((this._numTypesValid & 4) != 0) {
         double var4 = this._numberBigInt.doubleValue();
         this._numberDouble = var4;
      } else if((this._numTypesValid & 2) != 0) {
         double var6 = (double)this._numberLong;
         this._numberDouble = var6;
      } else if((this._numTypesValid & 1) != 0) {
         double var8 = (double)this._numberInt;
         this._numberDouble = var8;
      } else {
         this._throwInternal();
      }

      int var3 = this._numTypesValid | 8;
      this._numTypesValid = var3;
   }

   protected void convertNumberToInt() throws IOException, JsonParseException {
      if((this._numTypesValid & 2) != 0) {
         int var1 = (int)this._numberLong;
         long var2 = (long)var1;
         long var4 = this._numberLong;
         if(var2 != var4) {
            StringBuilder var6 = (new StringBuilder()).append("Numeric value (");
            String var7 = this.getText();
            String var8 = var6.append(var7).append(") out of range of int").toString();
            this._reportError(var8);
         }

         this._numberInt = var1;
      } else if((this._numTypesValid & 4) != 0) {
         int var10 = this._numberBigInt.intValue();
         this._numberInt = var10;
      } else if((this._numTypesValid & 8) != 0) {
         if(this._numberDouble < -2.147483648E9D || this._numberDouble > 2.147483647E9D) {
            this.reportOverflowInt();
         }

         int var11 = (int)this._numberDouble;
         this._numberInt = var11;
      } else if((this._numTypesValid & 16) != 0) {
         label27: {
            BigDecimal var12 = BD_MIN_INT;
            BigDecimal var13 = this._numberBigDecimal;
            if(var12.compareTo(var13) <= 0) {
               BigDecimal var14 = BD_MAX_INT;
               BigDecimal var15 = this._numberBigDecimal;
               if(var14.compareTo(var15) >= 0) {
                  break label27;
               }
            }

            this.reportOverflowInt();
         }

         int var16 = this._numberBigDecimal.intValue();
         this._numberInt = var16;
      } else {
         this._throwInternal();
      }

      int var9 = this._numTypesValid | 1;
      this._numTypesValid = var9;
   }

   protected void convertNumberToLong() throws IOException, JsonParseException {
      if((this._numTypesValid & 1) != 0) {
         long var1 = (long)this._numberInt;
         this._numberLong = var1;
      } else if((this._numTypesValid & 4) != 0) {
         long var4 = this._numberBigInt.longValue();
         this._numberLong = var4;
      } else if((this._numTypesValid & 8) != 0) {
         if(this._numberDouble < -9.223372036854776E18D || this._numberDouble > 9.223372036854776E18D) {
            this.reportOverflowLong();
         }

         long var6 = (long)this._numberDouble;
         this._numberLong = var6;
      } else if((this._numTypesValid & 16) != 0) {
         label23: {
            BigDecimal var8 = BD_MIN_LONG;
            BigDecimal var9 = this._numberBigDecimal;
            if(var8.compareTo(var9) <= 0) {
               BigDecimal var10 = BD_MAX_LONG;
               BigDecimal var11 = this._numberBigDecimal;
               if(var10.compareTo(var11) >= 0) {
                  break label23;
               }
            }

            this.reportOverflowLong();
         }

         long var12 = this._numberBigDecimal.longValue();
         this._numberLong = var12;
      } else {
         this._throwInternal();
      }

      int var3 = this._numTypesValid | 2;
      this._numTypesValid = var3;
   }

   public BigInteger getBigIntegerValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 4) == 0) {
         if(this._numTypesValid == 0) {
            this.parseNumericValue(4);
         }

         if((this._numTypesValid & 4) == 0) {
            this.convertNumberToBigInteger();
         }
      }

      return this._numberBigInt;
   }

   public byte getByteValue() throws IOException, JsonParseException {
      int var1 = this.getIntValue();
      if(var1 < '\uff80' || var1 > 127) {
         StringBuilder var2 = (new StringBuilder()).append("Numeric value (");
         String var3 = this.getText();
         String var4 = var2.append(var3).append(") out of range of Java byte").toString();
         this._reportError(var4);
      }

      return (byte)var1;
   }

   public BigDecimal getDecimalValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 16) == 0) {
         if(this._numTypesValid == 0) {
            this.parseNumericValue(16);
         }

         if((this._numTypesValid & 16) == 0) {
            this.convertNumberToBigDecimal();
         }
      }

      return this._numberBigDecimal;
   }

   public double getDoubleValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 8) == 0) {
         if(this._numTypesValid == 0) {
            this.parseNumericValue(8);
         }

         if((this._numTypesValid & 8) == 0) {
            this.convertNumberToDouble();
         }
      }

      return this._numberDouble;
   }

   public float getFloatValue() throws IOException, JsonParseException {
      return (float)this.getDoubleValue();
   }

   public int getIntValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 1) == 0) {
         if(this._numTypesValid == 0) {
            this.parseNumericValue(1);
         }

         if((this._numTypesValid & 1) == 0) {
            this.convertNumberToInt();
         }
      }

      return this._numberInt;
   }

   public long getLongValue() throws IOException, JsonParseException {
      if((this._numTypesValid & 2) == 0) {
         if(this._numTypesValid == 0) {
            this.parseNumericValue(2);
         }

         if((this._numTypesValid & 2) == 0) {
            this.convertNumberToLong();
         }
      }

      return this._numberLong;
   }

   public JsonParser.NumberType getNumberType() throws IOException, JsonParseException {
      if(this._numTypesValid == 0) {
         this.parseNumericValue(0);
      }

      JsonToken var1 = this._currToken;
      JsonToken var2 = JsonToken.VALUE_NUMBER_INT;
      JsonParser.NumberType var3;
      if(var1 == var2) {
         if((this._numTypesValid & 1) != 0) {
            var3 = JsonParser.NumberType.INT;
         } else if((this._numTypesValid & 2) != 0) {
            var3 = JsonParser.NumberType.LONG;
         } else {
            var3 = JsonParser.NumberType.BIG_INTEGER;
         }
      } else if((this._numTypesValid & 16) != 0) {
         var3 = JsonParser.NumberType.BIG_DECIMAL;
      } else {
         var3 = JsonParser.NumberType.DOUBLE;
      }

      return var3;
   }

   public Number getNumberValue() throws IOException, JsonParseException {
      if(this._numTypesValid == 0) {
         this.parseNumericValue(0);
      }

      JsonToken var1 = this._currToken;
      JsonToken var2 = JsonToken.VALUE_NUMBER_INT;
      Object var3;
      if(var1 == var2) {
         if((this._numTypesValid & 1) != 0) {
            var3 = Integer.valueOf(this._numberInt);
         } else if((this._numTypesValid & 2) != 0) {
            var3 = Long.valueOf(this._numberLong);
         } else if((this._numTypesValid & 4) != 0) {
            var3 = this._numberBigInt;
         } else {
            var3 = this._numberBigDecimal;
         }
      } else if((this._numTypesValid & 16) != 0) {
         var3 = this._numberBigDecimal;
      } else {
         if((this._numTypesValid & 8) == 0) {
            this._throwInternal();
         }

         var3 = Double.valueOf(this._numberDouble);
      }

      return (Number)var3;
   }

   public short getShortValue() throws IOException, JsonParseException {
      int var1 = this.getIntValue();
      if(var1 < '\u8000' || var1 > 32767) {
         StringBuilder var2 = (new StringBuilder()).append("Numeric value (");
         String var3 = this.getText();
         String var4 = var2.append(var3).append(") out of range of Java short").toString();
         this._reportError(var4);
      }

      return (short)var1;
   }

   protected abstract JsonToken parseNumberText(int var1) throws IOException, JsonParseException;

   protected final void parseNumericValue(int var1) throws JsonParseException {
      if(this._currToken == null || !this._currToken.isNumeric()) {
         StringBuilder var2 = (new StringBuilder()).append("Current token (");
         JsonToken var3 = this._currToken;
         String var4 = var2.append(var3).append(") not numeric, can not use numeric value accessors").toString();
         this._reportError(var4);
      }

      try {
         JsonToken var5 = this._currToken;
         JsonToken var6 = JsonToken.VALUE_NUMBER_INT;
         if(var5 == var6) {
            char[] var7 = this._textBuffer.getTextBuffer();
            int var8 = this._textBuffer.getTextOffset();
            if(this._numberNegative) {
               ++var8;
            }

            if(this.mIntLength <= 9) {
               int var9 = this.mIntLength;
               int var22 = NumberInput.parseInt(var7, var8, var9);
               if(this._numberNegative) {
                  var22 = -var22;
               }

               this._numberInt = var22;
               this._numTypesValid = 1;
            } else if(this.mIntLength <= 18) {
               int var10 = this.mIntLength;
               long var21 = NumberInput.parseLong(var7, var8, var10);
               if(this._numberNegative) {
                  var21 = -var21;
               }

               this._numberLong = var21;
               this._numTypesValid = 2;
            } else {
               String var15 = this._textBuffer.contentsAsString();
               BigInteger var16 = new BigInteger(var15);
               this._numberBigInt = var16;
               this._numTypesValid = 4;
            }
         } else if(var1 == 16) {
            BigDecimal var17 = this._textBuffer.contentsAsDecimal();
            this._numberBigDecimal = var17;
            this._numTypesValid = 16;
         } else {
            double var18 = this._textBuffer.contentsAsDouble();
            this._numberDouble = var18;
            this._numTypesValid = 8;
         }
      } catch (NumberFormatException var20) {
         StringBuilder var12 = (new StringBuilder()).append("Malformed numeric value \'");
         String var13 = this._textBuffer.contentsAsString();
         String var14 = var12.append(var13).append("\'").toString();
         this._wrapError(var14, var20);
      }
   }

   protected void reportInvalidNumber(String var1) throws JsonParseException {
      String var2 = "Invalid numeric value: " + var1;
      this._reportError(var2);
   }

   protected void reportOverflowInt() throws IOException, JsonParseException {
      StringBuilder var1 = (new StringBuilder()).append("Numeric value (");
      String var2 = this.getText();
      String var3 = var1.append(var2).append(") out of range of int (").append(Integer.MIN_VALUE).append(" - ").append(Integer.MAX_VALUE).append(")").toString();
      this._reportError(var3);
   }

   protected void reportOverflowLong() throws IOException, JsonParseException {
      StringBuilder var1 = (new StringBuilder()).append("Numeric value (");
      String var2 = this.getText();
      String var3 = var1.append(var2).append(") out of range of long (").append(Long.MIN_VALUE).append(" - ").append(Long.MAX_VALUE).append(")").toString();
      this._reportError(var3);
   }

   protected void reportUnexpectedNumberChar(int var1, String var2) throws JsonParseException {
      StringBuilder var3 = (new StringBuilder()).append("Unexpected character (");
      String var4 = _getCharDesc(var1);
      String var5 = var3.append(var4).append(") in numeric value").toString();
      if(var2 != null) {
         var5 = var5 + ": " + var2;
      }

      this._reportError(var5);
   }

   protected final JsonToken reset(boolean var1, int var2, int var3, int var4) {
      this._numberNegative = var1;
      this.mIntLength = var2;
      this.mFractLength = var3;
      this.mExpLength = var4;
      this._numTypesValid = 0;
      JsonToken var5;
      if(var3 < 1 && var4 < 1) {
         var5 = JsonToken.VALUE_NUMBER_INT;
      } else {
         var5 = JsonToken.VALUE_NUMBER_FLOAT;
      }

      return var5;
   }
}
