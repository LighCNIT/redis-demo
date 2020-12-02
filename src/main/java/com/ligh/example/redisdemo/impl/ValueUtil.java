package com.ligh.example.redisdemo.impl;

import java.util.*;

/**
 * 包内可见的类和方法
 *
 */
 class ValueUtil {

    static String parseString(Object v) {
        if (v == null) {
            return null;
        }
        if(v instanceof String){
            return (String) v;
        }
        return v.toString();
    }

    static int parseInt(Number v) {
        return v == null ? 0 : v.intValue();
    }

    static long parseLong(Number v) {
        return v == null ? 0 : v.longValue();
    }

    static double parseDouble(Number v) {
        return v == null ? 0 : v.doubleValue();
    }

    static boolean parseBoolean(Boolean v) {
        return v != null && v;
    }

    /**
     * 不为0即为真
     *
     * @param v
     * @return
     */
    static boolean parseBoolean(Number v) {
        return v != null && v.doubleValue() != 0;
    }

    static int parseInt(Object v) {
        if (v == null) {
            return 0;
        }
        try {
            return Integer.parseInt(v.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    static long parseLong(Object v) {
        if (v == null) {
            return 0;
        }
        try {
            return Long.parseLong(v.toString());
        } catch (Exception e) {
            return 0L;
        }
    }

    static double parseDouble(Object v) {
        if (v == null) {
            return 0;
        }
        try {
            return Double.parseDouble(v.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    static boolean parseBoolean(Object v) {
        if (v == null) {
            return false;
        }
        try {
            return Boolean.parseBoolean(v.toString());
        } catch (Exception e) {
            return false;
        }
    }

    static <T> T parse(Object obj, Class<T> clazz) {
        if (clazz.isInstance(obj)) {
            T t = (T) obj;
            return t;
        }
        return null;
    }

    static int getValue(Integer value) {
        return value == null ? 0 : value;
    }

    static long getValue(Long value) {
        return value == null ? 0 : value;
    }

    static double getValue(Double value) {
        return value == null ? 0 : value;
    }

    static boolean getValue(Boolean value) {
        return value == null ? false : value;
    }

    static Set<String> parseSet(Set<Object> objList) {
        Set<String> sets = new TreeSet<>();
        if (!canAndParse(sets, objList, String.class)) {
            return null;
        }
        return sets;
    }

    static <T> List<T> parseList(List<Object> objList, Class<T> clazz) {
        List<T> list = new ArrayList<>(objList.size());
        if (!canAndParse(list, objList, clazz)) {
            return null;
        }
        return list;
    }

    static <T> Set<T> parseSet(Set<Object> objList, Class<T> clazz) {
        Set<T> sets = new TreeSet<>();
        if (!canAndParse(sets, objList, clazz)) {
            return null;
        }
        return sets;
    }

    static <T> Map<String, T> parseMap(Map<String, Object> objMap, Class<T> clazz) {
        if (objMap == null || objMap.size() <= 0) {
            return null;
        }
        Map<String, T> map = new HashMap<>(objMap.size());
        for (Map.Entry<String, Object> entry : objMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                map.put(key, null);
            } else if (clazz.isInstance(value)) {
                T t = (T) value;
                map.put(key, t);
            } else {
                //元素类型已经发生变化，返回空列表
                return null;
            }

        }
        return map;
    }
    /**
     * 把source的元素全部转为T类型后，添加到target当中。
     * 全部转化成功，返回true
     * 如果存在非T类型的数据，导致转化失败，则返回false
     *
     * @param target
     * @param source
     * @param clazz
     * @param <T>
     * @return 转化成功，返回true，转化失败，返回false
     */
    private static <T> boolean canAndParse(Collection<T> target, Collection<Object> source, Class<T> clazz) {
        if (source == null || source.size() <= 0) {
            return false;
        }
        for (Object obj : source) {
            if (obj == null) {
                target.add(null);
            } else if (clazz.isInstance(obj)) {
                T t = (T) obj;
                target.add(t);
            } else {
                //元素类型已经发生变化，返回空列表
                return false;
            }
        }
        return true;
    }
}
