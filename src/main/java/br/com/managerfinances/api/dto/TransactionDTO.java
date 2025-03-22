package br.com.managerfinances.api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class TransactionDTO {
    private String id;
    private String description;
    private String date;
    private BigDecimal value;
    private String category;
}
