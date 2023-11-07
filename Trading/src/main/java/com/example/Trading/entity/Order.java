package com.example.Trading.entity;

import com.example.Trading.constants.ErrorConstants;
import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.OrderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@Table(name = StringConstants.TABLE_NAME)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = ErrorConstants.Stock_Symbol_Not_Blank)
    @Size(min = 10, max = 25, message = ErrorConstants.Stock_Symbol_Length)
    private String stockSymbol;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    @Positive(message = ErrorConstants.Price_Positive)
    @DecimalMin(value = "0.0", message = ErrorConstants.Price_Constraint)
    private double price;
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = ErrorConstants.Quantity_Constraint)
    private int quantity;

    private String status;

    private LocalDateTime timestamp;
}
