package com.jgk.springrecipes.simple.converters;

import java.util.Arrays;

public class ArraysConverterDemo {
	byte[] byteArray;
	String[] stringArray;
	@Override
	public String toString() {
		return "ArraysConverterDemo [byteArray=" + Arrays.toString(byteArray)
				+ ", stringArray=" + Arrays.toString(stringArray) + "]";
	}
	public byte[] getByteArray() {
		return byteArray;
	}
	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}
	public String[] getStringArray() {
		return stringArray;
	}
	public void setStringArray(String[] stringArray) {
		this.stringArray = stringArray;
	}
	
	
}
