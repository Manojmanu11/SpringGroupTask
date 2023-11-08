package com.example.Trading.entity;

import com.example.Trading.constants.ErrorConstants;
import com.example.Trading.constants.StringConstants;
import com.example.Trading.dto.OrderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Positive(message = "Price must be a positive value.")
    @DecimalMin(value = "0.0", message = ErrorConstants.Price_Constraint)
    private double price;
    @NotNull(message = "Quantity must not be null")
    private Integer  quantity;

    private String status;
    private LocalDateTime timestamp;
}
