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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class IntegerListCustomType implements UserType {


    private static final int[] SQL_TYPES = new int[]{Types.VARCHAR};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }


    public Class returnedClass() {
        return List.class;
    }


    public boolean equals(Object x, Object y) throws HibernateException {
        return ((List)x).equals((List)y);
    }


    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        List<Integer> result = new LinkedList<Integer>();
        if (value == null) {
            return  result;
        } else {
            String[] array = StringUtils.split(value, '|');
            for (String s : array) {
                result.add(new Integer(s));
            }
            return result;
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            st.setObject(index, null, Types.VARCHAR);
        } else {
            st.setObject(index, StringUtils.join((List) value, '|'), Types.VARCHAR);
        }
    }


    public Object deepCopy(Object value) throws HibernateException {
        return new LinkedList((List)value);
    }


    public boolean isMutable() {
        return true;
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