package com.wdd.mybatch.core.common.service;

import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ParamClassResolver {

    private static final Map<String, Type> MESSAGE_CLASS_CACHE=new ConcurrentHashMap<>();

    public Type resolveClassName(Class currentClass,int index){
        String key =currentClass.getCanonicalName()+"-"+index;
        Type clazz=MESSAGE_CLASS_CACHE.get(key);

        if(null ==clazz){
            synchronized (MESSAGE_CLASS_CACHE){
                while (currentClass!=null){
                    Type parentType=currentClass.getGenericSuperclass();
                    if(parentType instanceof ParameterizedType){
                        clazz =((ParameterizedType)parentType).getActualTypeArguments()[index];
                        MESSAGE_CLASS_CACHE.putIfAbsent(key,clazz);
                        break;
                    }else{
                        currentClass=currentClass.getSuperclass();
                    }
                }
            }

            if(clazz==null){
                throw new RuntimeException("类参数解析异常");
            }
        }
        return clazz;
    }
}
