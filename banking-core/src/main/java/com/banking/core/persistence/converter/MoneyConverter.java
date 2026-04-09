package com.banking.core.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * JPA converter for monetary amounts.
 * Ensures consistent scale (2 decimal places) and banker's rounding.
 */
@Converter(autoApply = false)
public class MoneyConverter implements AttributeConverter<BigDecimal, BigDecimal> {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_EVEN;

    @Override
    public BigDecimal convertToDatabaseColumn(BigDecimal attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.setScale(SCALE, ROUNDING);
    }

    @Override
    public BigDecimal convertToEntityAttribute(BigDecimal dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData.setScale(SCALE, ROUNDING);
    }
}
