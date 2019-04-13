package com.lssj.zmn.server.app.utils.util;


import com.lssj.zmn.server.app.utils.model.DateFormat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lancec on 2015/1/26.
 */
public class JSONUtil {
    public static <T> T convertToObject(JSONObject jsonObject, Class<T> clazz) {
        if (ReflectionUtil.isBasicType(clazz)) {
            String strValue = jsonObject.toString();
            return (T) ReflectionUtil.getBasicFieldValue(strValue, clazz, null);
        }
        T instance = ReflectionUtil.newInstance(clazz);
        Method[] setterMethods = ReflectionUtil.getSetterMethods(clazz);
        for (Method setterMethod : setterMethods) {
            String methodName = setterMethod.getName();
            String fieldName = methodName.substring(3);
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            Class fieldClass = setterMethod.getParameterTypes()[0];
            Object fieldValue = null;

            String datePattern = getDatePattern(setterMethod, fieldName, clazz);
            if (ReflectionUtil.isBasicType(fieldClass)) {
                String strValue = jsonObject.optString(fieldName);
                fieldValue = ReflectionUtil.getBasicFieldValue(strValue, fieldClass, datePattern);
            } else if (fieldClass.isEnum()) {
                JSONObject jsonValue = jsonObject.optJSONObject(fieldName);
                if (jsonValue == null) {
                    String strValue = jsonObject.optString(fieldName);
                    try {
                        fieldValue = Enum.valueOf(fieldClass, strValue);
                    }catch (Exception e){
                    }
                } else {
                    String strValue = jsonValue.optString("value");
                    try {
                        fieldValue = Enum.valueOf(fieldClass, strValue);
                    }catch (Exception ex){
                    }
                }
            } else if (fieldClass.isArray()) {
                JSONArray jsonArray = jsonObject.optJSONArray(fieldName);
                if (jsonArray != null) {
                    Class componentType = fieldClass.getComponentType();
                    Object objArray = Array.newInstance(componentType, jsonArray.size());
                    if (ReflectionUtil.isBasicType(componentType)) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            String strValue = jsonArray.optString(i);
                            Object arrayValue = ReflectionUtil.getBasicFieldValue(strValue, componentType, datePattern);
                            Array.set(objArray, i, arrayValue);
                        }
                    } else if (fieldClass.isEnum()) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            String elValue = jsonArray.optString(i);
                            Object arrayValue = null;
                            JSONObject jsonValue = jsonObject.optJSONObject(elValue);
                            if (jsonValue == null) {
                                try {
                                    arrayValue = Enum.valueOf(fieldClass, elValue);
                                }catch (Exception ex){
                                }
                            } else {
                                String strValue = jsonValue.optString("value");
                                try {
                                    arrayValue = Enum.valueOf(fieldClass, strValue);
                                }catch (Exception ex){
                                }
                            }
                            Array.set(objArray, i, arrayValue);
                        }
                    } else {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject arrayJsonObj = jsonArray.optJSONObject(i);
                            if (arrayJsonObj == null) {
                                Array.set(objArray, i, null);
                            } else {
                                Object subObj = convertToObject(arrayJsonObj, componentType);
                                Array.set(objArray, i, subObj);
                            }
                        }
                    }
                    fieldValue = objArray;
                }
            } else {
                JSONObject subJsonObj = jsonObject.optJSONObject(fieldName);
                if (subJsonObj != null) {
                    Object subObj = convertToObject(subJsonObj, fieldClass);
                    fieldValue = subObj;
                }
            }
            try {
                setterMethod.invoke(instance, fieldValue);
            } catch (Exception e) {
            }
        }
        return instance;
    }


    public static <T> T convertToObjectEx(JSONObject jsonObject, Class<T> clazz) {
        if (ReflectionUtil.isBasicType(clazz)) {
            String strValue = jsonObject.toString();
            return (T) ReflectionUtil.getBasicFieldValue(strValue, clazz, null);
        }
        T instance = ReflectionUtil.newInstance(clazz);
        Method[] setterMethods = ReflectionUtil.getSetterMethods(clazz);
        for (Method setterMethod : setterMethods) {
            String methodName = setterMethod.getName();
            String fieldNameOrg = methodName.substring(3);
            String fieldName = methodName.substring(3);
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            Class fieldClass = setterMethod.getParameterTypes()[0];
            Object fieldValue = null;

            String datePattern = getDatePattern(setterMethod, fieldName, clazz);
            if (ReflectionUtil.isBasicType(fieldClass)) {
                String strValue = jsonObject.optString(fieldName);
                if(StringUtil.isEmpty(strValue)){
                     strValue = jsonObject.optString(fieldNameOrg);
                }
                fieldValue = ReflectionUtil.getBasicFieldValue(strValue, fieldClass, datePattern);
            } else if (fieldClass.isEnum()) {
                JSONObject jsonValue = jsonObject.optJSONObject(fieldName);
                if(jsonValue == null){
                    jsonValue = jsonObject.optJSONObject(fieldNameOrg);
                }
                if (jsonValue == null) {
                    String strValue = jsonObject.optString(fieldName);
                    if(StringUtil.isEmpty(strValue)){
                        strValue = jsonObject.optString(fieldNameOrg);
                    }
                    try {
                        fieldValue = Enum.valueOf(fieldClass, strValue);
                    }catch (Exception e){
                    }
                } else {
                    String strValue = jsonValue.optString("value");
                    try {
                        fieldValue = Enum.valueOf(fieldClass, strValue);
                    }catch (Exception ex){
                    }
                }
            } else if (fieldClass.isArray()) {
                JSONArray jsonArray = jsonObject.optJSONArray(fieldName);
                if(jsonArray==null){
                    jsonArray = jsonObject.optJSONArray(fieldNameOrg);
                }
                if (jsonArray != null) {
                    Class componentType = fieldClass.getComponentType();
                    Object objArray = Array.newInstance(componentType, jsonArray.size());
                    if (ReflectionUtil.isBasicType(componentType)) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            String strValue = jsonArray.optString(i);
                            Object arrayValue = ReflectionUtil.getBasicFieldValue(strValue, componentType, datePattern);
                            Array.set(objArray, i, arrayValue);
                        }
                    } else if (fieldClass.isEnum()) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            String elValue = jsonArray.optString(i);
                            Object arrayValue = null;
                            JSONObject jsonValue = jsonObject.optJSONObject(elValue);
                            if (jsonValue == null) {
                                try {
                                    arrayValue = Enum.valueOf(fieldClass, elValue);
                                }catch (Exception ex){
                                }
                            } else {
                                String strValue = jsonValue.optString("value");
                                try {
                                    arrayValue = Enum.valueOf(fieldClass, strValue);
                                }catch (Exception ex){
                                }
                            }
                            Array.set(objArray, i, arrayValue);
                        }
                    } else {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject arrayJsonObj = jsonArray.optJSONObject(i);
                            if (arrayJsonObj == null) {
                                Array.set(objArray, i, null);
                            } else {
                                Object subObj = convertToObject(arrayJsonObj, componentType);
                                Array.set(objArray, i, subObj);
                            }
                        }
                    }
                    fieldValue = objArray;
                }
            } else {
                JSONObject subJsonObj = jsonObject.optJSONObject(fieldName);
                if(subJsonObj==null){
                     subJsonObj = jsonObject.optJSONObject(fieldNameOrg);
                }
                if (subJsonObj != null) {
                    Object subObj = convertToObject(subJsonObj, fieldClass);
                    fieldValue = subObj;
                }
            }
            try {
                setterMethod.invoke(instance, fieldValue);
            } catch (Exception e) {
            }
        }
        return instance;
    }

    private static String getDatePattern(Method setterMethod, String fieldName, Class objClass) {
        DateFormat dateFormatAnnotation = null;
        Annotation[] paramAnnotations = setterMethod.getParameterAnnotations()[0];
        if (paramAnnotations != null && paramAnnotations.length > 0) {
            for (Annotation paramAnnotation : paramAnnotations) {
                if (paramAnnotation.getClass() == DateFormat.class) {
                    dateFormatAnnotation = (DateFormat) paramAnnotation;
                    break;
                }
            }
        }
        if (dateFormatAnnotation == null) {
            Field field = ReflectionUtil.getField(fieldName, objClass);
            if (field != null) {
                dateFormatAnnotation = field.getAnnotation(DateFormat.class);
            }
        }
        String datePattern = null;
        if (dateFormatAnnotation != null) {
            datePattern = dateFormatAnnotation.pattern();
        }
        return datePattern;
    }
}
