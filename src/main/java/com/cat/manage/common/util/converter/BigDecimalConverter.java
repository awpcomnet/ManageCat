package com.cat.manage.common.util.converter;

import java.math.BigDecimal;

import org.apache.commons.beanutils.Converter;

import com.google.common.base.Strings;

public class BigDecimalConverter implements Converter {

	@Override
    public Object convert(Class type, Object value) {
        if (value == null)
            return null;
        
        String literal = "" + value;
        if (Strings.isNullOrEmpty(literal)) {
            literal = "0";
        } else if ("null".equalsIgnoreCase(literal)) {
            literal = "0";
        }
        
        BigDecimal decimal = new BigDecimal(literal);
        return decimal;
    }

}
