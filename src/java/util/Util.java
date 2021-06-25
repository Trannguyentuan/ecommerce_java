/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vdtru
 */
public class Util {

    public static String[] getFiledName(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            return null;
        }
    }

    @SuppressWarnings("StringEquality")
    public static List<String> getParameterValues(Object obj) {
        List<String> paramValues = new ArrayList<>();

        String[] fieldNames = getFiledName(obj);

        for (String name : fieldNames) {
            Object value = getFieldValueByName(name, obj);

            switch (name) {
                case "createdAt":
                    paramValues.add(value == null ? new Timestamp(System.currentTimeMillis()).toString() : value.toString());
                    break;
                case "updatedAt":
                    paramValues.add(new Timestamp(System.currentTimeMillis()).toString());
                    break;
                case "price":
                case "total":
                case "subTotal":
                case "discountPercent":
                case "isFinished":
                    paramValues.add(value == null ? "0" : value.toString());
                    break;
                default:
                    paramValues.add(value == null ? "" : value.toString());
                    break;
            }
        }

        return paramValues;
    }
}
