package ua.com.manometer.util;


import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 06.07.2010
 * Time: 22:31:20
 * To change this template use File | Settings | File Templates.
 */
public class LongArrayCustomType implements UserType {


    private static final int[] SQL_TYPES = new int[]{Types.VARCHAR};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }


    public Class returnedClass() {
        return Long[].class;
    }


    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }


    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {

//          String value = (String) Hibernate.TEXT.nullSafeGet(rs, names,owner);
        String value = rs.getString(names[0]);
        if (value == null) {
            return new Long[0];
        } else {
            String[] array = StringUtils.split(value, '|');
            Long[] res = new Long[array.length];
            for (int i = 0; i < array.length; i++) {
                res[i] = new Long(array[i]);
            }
            return res;
//            return null;
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            Hibernate.TEXT.nullSafeSet(st, null, index);
        } else {
            Long[] array = (Long[]) value;
            Hibernate.TEXT.nullSafeSet(st, StringUtils.join(array, '|'), index);
        }
    }


    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }


    public boolean isMutable() {
        return false;
    }


    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }


    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }


    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }


}