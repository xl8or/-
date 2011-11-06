// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package org.codehaus.jackson.schema;

import java.lang.annotation.Annotation;

public interface JsonSerializableSchema
    extends Annotation
{

    public abstract String schemaItemDefinition();

    public abstract String schemaObjectPropertiesDefinition();

    public abstract String schemaType();
}
