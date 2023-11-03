package com.example.Trading.exceptionhandler;

import com.example.Trading.constants.ErrorConstants;
import com.example.Trading.dto.OrderType;
import com.example.Trading.exception.InvalidOrderTypeException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class OrderTypeDeserializer extends StdDeserializer<OrderType> {
    public OrderTypeDeserializer() {
        super(OrderType.class);
    }

    @Override
    public OrderType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String value = node.asText();
        if (OrderType.SELL.name().equalsIgnoreCase(value)) {
            return OrderType.SELL;
        } else if (OrderType.BUY.name().equalsIgnoreCase(value)) {
            return OrderType.BUY;
        } else {
            throw new InvalidOrderTypeException(ErrorConstants.Invalid_Order_Type);
        }
    }
}

