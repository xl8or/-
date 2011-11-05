package com.facebook.katana.util;


public interface Factory<T extends Object> {

   T make();
}
