package ua.com.manometer.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;


public class MyBigDecimalType implements UserType {
        public int[] sqlTypes() {
             int[] typeList = {Types.VARCHAR};
                   return typeList;
        }
        public Class returnedClass() {
            return BigDecimal.class;
        }
        public boolean equals(Object x, Object y) throws HibernateException {
            return ((BigDecimal)x).equals(y);
        }


        public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
                throws HibernateException, SQLException {
             // Start by looking up the value name
             String name = (String) Hibernate.STRING.nullSafeGet(rs, names[0]);
             if (name == null) {
             return null;
             }
             // Then find the corresponding enumeration value
             try {
             return new BigDecimal(name);
             }
             catch (java.util.NoSuchElementException e) {
            throw new HibernateException("Bad BigDecimal value in UserTyped: " + name, e);
           }
        }


        public void nullSafeSet(PreparedStatement st, Object value, int index)
                throws HibernateException, SQLException {
            String name = null;
            if (value != null) {
              name = value.toString();
            }
            Hibernate.STRING.nullSafeSet(st, name, index);
            //Hibernate.

        }
        public Object deepCopy(Object value) throws HibernateException {
            return value;
        }
        public boolean isMutable() {
            return false;
        }

        public int hashCode(Object x) throws HibernateException {
        // TODO Auto-generated method stub
        return x.hashCode();
        }
        public Object replace(Object original, Object target, Object owner) throws HibernateException {
            return original;
        }
        public Object assemble(Serializable cached, Object owner) throws HibernateException {
            return cached;
        }



        public Serializable disassemble(Object value) throws HibernateException {
            return (Serializable) value;
        }


    }