package doctorhoai.learn.inventoryservice.utils;

import doctorhoai.learn.inventoryservice.model.enums.EUnitType;

public class ExchangeUnit {

    public static Float convertUnit(Float value, EUnitType fromUnit, EUnitType toUnit) {
        Float valueInGrams = switch (fromUnit){
            case G -> value;
            case KG -> value*1000;
            case MG -> value/1000;
            default -> throw new IllegalArgumentException("Unsupported unit: " + fromUnit);
        };
        return switch (toUnit){
            case G -> valueInGrams;
            case KG -> valueInGrams/1000;
            case MG -> valueInGrams*1000;
            default -> throw new IllegalArgumentException("Unsupported unit: " + toUnit);
        };
    }
}
