package com.wu.admin.base;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Description : Created by intelliJ IDEA
 * @author  : wubo
 * @version  : 2017/10/10下午5:28
 */
public class AddScalar {
    /**
     * 将field type 和 Hibernate的类型进行了对应。这里其实不是多余的，如果不进行一定的对应可能会有问题。 问题有两个： 1.
     * 在oracle中我们可能把一些字段设为NUMBER(%)，而在Bean中的字段定的是long。那么查询时可能会报：
     * java.math.BeigDecimal不能转换成long等错误 2.
     * 如果不这样写的话，可能Bean中的field就得是大写的，如：name就得写成NAME,userCount就得写成USERCOUNT
     * 这样是不是很扯(V_V)
     *
     * @param <T>
     * @param sqlQuery
     *            SQLQuery
     * @param clazz
     *            T.class
     */
    public static <T> void addSclar(SQLQuery sqlQuery, Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("[clazz] could not be null!");
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if ((field.getType() == long.class) || (field.getType() == Long.class)) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.LONG);
            } else if ((field.getType() == int.class) || (field.getType() == Integer.class)) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.INTEGER);
            } else if ((field.getType() == char.class) || (field.getType() == Character.class)) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.CHARACTER);
            } else if ((field.getType() == short.class) || (field.getType() == Short.class)) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.SHORT);
            } else if ((field.getType() == double.class) || (field.getType() == Double.class)) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.DOUBLE);
            } else if ((field.getType() == float.class) || (field.getType() == Float.class)) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.FLOAT);
            } else if ((field.getType() == boolean.class) || (field.getType() == Boolean.class)) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.BOOLEAN);
            } else if (field.getType() == String.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.STRING);
            } else if (field.getType() == Date.class) {
                sqlQuery.addScalar(field.getName(), StandardBasicTypes.TIMESTAMP);
            }
        }

        sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
    }
}
