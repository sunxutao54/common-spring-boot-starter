package com.axe.common.core.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: TODO 集合操作工具类
 * @Date: 2025/7/10
 * @Author: Sxt
 * @Version: v1.0
 */
public class CollectionUtils {

    private CollectionUtils(){}

    /**
     * TODO 判断集合是否为空
     * @param collection 集合
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 16:12:04
     */
    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    /**
     * TODO 判断集合是否不为空
     * @param collection 集合
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 16:12:04
     */
    public static boolean isNotEmpty(Collection<?> collection){
        return !isEmpty(collection);
    }


    /**
     * TODO 判断映射是否不为空
     * @param map 映射
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 16:12:04
     */
    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }

    /**
     * TODO 判断映射是否为空
     * @param map 映射
     * @return boolean
     * @author: sxt
     * @date: 2025/07/10 16:12:04
     */
    public static boolean isEmpty(Map<?,?> map){
        return map == null || map.isEmpty();
    }


    /**
     * TODO 将多个元素组成List
     * @param e 元素
     * @return 列表<t>
     * @author: sxt
     * @date: 2025/07/10 16:13:58
     */
    @SafeVarargs
    public static <E> List<E> ofList(E ... e){
        return Stream.of(e).collect(Collectors.toList());
    }

    /**
     * TODO 将多个元素组成Set
     * @param e 元素
     * @return 列表<t>
     * @author: sxt
     * @date: 2025/07/10 16:13:58
     */
    @SafeVarargs
    public static <E> Set<E> ofSet(E ... e){
        return Stream.of(e).collect(Collectors.toSet());
    }

    /**
     * TODO 将指定的key-value放入一个HashMap中
     * @param key   钥匙
     * @param value 值
     * @return 哈希映射<k ， v>
     * @author: sxt
     * @date: 2025/07/17 13:22:50
     */
    public static <K,V> HashMap<K,V> ofMap(K key , V value){
        HashMap<K,V> map = new HashMap<>();
        map.put(key,value);
        return map;
    }

    public static <E> Collection<E> cut (Collection<E> collection , int start , int end){
        return collection.stream().skip(start).limit(end - start).collect(Collectors.toList());
    }
}
