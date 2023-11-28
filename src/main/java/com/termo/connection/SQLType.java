package com.termo.connection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

// Forma que encontrei de permitir passar List<GameStatistics> como tipo.
// Forma simplificada do que o Gson faz: https://github.com/google/gson/blob/main/gson/src/main/java/com/google/gson/reflect/TypeToken.java
public abstract class SQLType<T> {

    private final Type type;

    protected SQLType() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return this.type;
    }
}
