package com.yulintu.thematic;

import org.joda.time.DateTime;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.UUID;

public class JavaTypeConverter extends TypeConverter {

    private static final JavaTypeConverter instance = new JavaTypeConverter();

    public JavaTypeConverter() {
    }

    public static JavaTypeConverter getInstance() {
        return instance;
    }

    @TypeConverterHandler(value = String.class)
    public String toString(Object value) {
        return value.toString();
    }

    @TypeConverterHandler(byte.class)
    @TypeConverterHandler(Byte.class)
    public Byte toByte(Object value) {
        String s = value.toString();
        int i = s.indexOf(".");
        return Byte.valueOf(i >= 0 ? s.substring(0, i) : s);
    }

    @TypeConverterHandler(short.class)
    @TypeConverterHandler(Short.class)
    public Short toInt16(Object value) {
        String s = value.toString();
        int i = s.indexOf(".");
        return Short.valueOf(i >= 0 ? s.substring(0, i) : s);
    }

    @TypeConverterHandler(int.class)
    @TypeConverterHandler(Integer.class)
    public Integer toInt32(Object value) {
        String s = value.toString();
        int i = s.indexOf(".");
        return Integer.valueOf(i >= 0 ? s.substring(0, i) : s);
    }

    @TypeConverterHandler(long.class)
    @TypeConverterHandler(Long.class)
    public Long toInt64(Object value) {
        return value instanceof Number ? Long.valueOf(String.format("%d", value)) : Long.valueOf(value.toString());
    }

    @TypeConverterHandler(float.class)
    @TypeConverterHandler(Float.class)
    public Float toFloat(Object value) {
        return Float.valueOf(value.toString());
    }

    @TypeConverterHandler(double.class)
    @TypeConverterHandler(Double.class)
    public Double toDouble(Object value) {

        return Double.valueOf(value.toString());
    }

    @TypeConverterHandler(boolean.class)
    @TypeConverterHandler(Boolean.class)
    public Boolean toBoolean(Object value) {
        if (value instanceof String) {
            String str = (String) value;
            return !str.equalsIgnoreCase("false") &&
                    !str.equalsIgnoreCase("no") &&
                    !str.equalsIgnoreCase("0");
        }

        if (value instanceof Number)
            return ((Number) value).intValue() != 0;

        return Boolean.valueOf(value.toString());
    }

    @TypeConverterHandler(value = UUID.class)
    public UUID toGuid(Object value) {
        if (value instanceof String)
            return UUID.fromString((String) value);
        if (value instanceof byte[])
            return UUID.nameUUIDFromBytes((byte[]) value);

        return UUID.fromString(value.toString());
    }

    @TypeConverterHandler(value = DateTime.class)
    public DateTime toDateTime(Object value) {
        if (value instanceof String)
            return DateTime.parse((String) value);

        return DateTime.parse(value.toString());
    }

    @TypeConverterHandler(value = Date.class)
    public Date toDate(Object value) {
        if (value instanceof String)
            return DateTime.parse((String) value).toDate();

        return DateTime.parse(value.toString()).toDate();
    }
}
