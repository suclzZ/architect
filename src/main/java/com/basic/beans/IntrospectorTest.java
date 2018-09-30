package com.basic.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * 内省
 */
public class IntrospectorTest {

    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Tom tom = new Tom("001",3);

        BeanInfo beanInfo = Introspector.getBeanInfo(Tom.class,Introspector.IGNORE_IMMEDIATE_BEANINFO);
        PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor propertyDescriptor : properties){
            Object value = propertyDescriptor.getReadMethod().invoke(tom);
            System.out.println(propertyDescriptor.getDisplayName() + " : "+value);
        }

    }

    public static void introspector(Class<?> clazz) throws IntrospectionException {
        Introspector.getBeanInfo(clazz);
        new PropertyDescriptor("age",clazz);
        /**
         * Introspector.USE_ALL_BEANINFO 自己还有父级
         * Introspector.IGNORE_ALL_BEANINFO 父级
         * Introspector.IGNORE_IMMEDIATE_BEANINFO
         */
        BeanInfo beanInfo1 = Introspector.getBeanInfo(clazz, Introspector.USE_ALL_BEANINFO);
        BeanInfo beanInfo2 = Introspector.getBeanInfo(clazz, Introspector.IGNORE_ALL_BEANINFO);
        BeanInfo beanInfo3 = Introspector.getBeanInfo(clazz, Introspector.IGNORE_IMMEDIATE_BEANINFO);

        beanInfo1.getPropertyDescriptors();
        beanInfo1.getAdditionalBeanInfo();
        beanInfo1.getBeanDescriptor();
        beanInfo1.getDefaultEventIndex();
        beanInfo1.getDefaultPropertyIndex();
        beanInfo1.getEventSetDescriptors();
        beanInfo1.getMethodDescriptors();
        beanInfo1.getIcon(BeanInfo.ICON_COLOR_16x16);
    }

}
