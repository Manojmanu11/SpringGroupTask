package com.example.Trading.constants;

public class ErrorConstants {
    public static final String Quantity_Constraint = "Quantity must be an integer";
    public static final String Order_Operation = "Please add correct operation";
    public static final String Stock_Symbol_Not_Blank = "Stock symbol should not be blank.";
    public static final String Stock_Symbol_Length = "Stock symbol length should be between 10 and 25 characters.";
    public static final String Order_Type = "Invalid order type. It should be 'buy' or 'sell'.";
    public static final String Price_Positive = "Price must be a positive value.";
    public static final String Price_Constraint = "Price should be greater than or equal to 0.0.";
    public static final String DataIntegrity_Message = "Data interity";
    public static final String Failure = "FAILED";
    public static final String Symbols_Import_Error = "Error importing symbols: ";
    public static final String Symbl_Not_Found = "The mentioned symbol does not exist add correct symbol";
    public static final String Symbl_Already_Exists = "The mentioned symbol already exists in watchList .";

    public static final String Order_NotFound = "Order not found";
    public static final String Order_Integer = "Enter integer value for orderId";
}
