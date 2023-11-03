package com.example.Trading.dto;

public enum OrderType {
    BUY,SELL;
    public static boolean isValid(String value) {
        for (OrderType orderType : values()) {
            if (orderType.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
