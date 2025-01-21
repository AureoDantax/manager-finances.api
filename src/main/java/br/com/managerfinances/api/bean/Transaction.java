package br.com.managerfinances.api.bean;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table( schema = "api")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "O nome do item é obrigatorio!")
    private String name;

    @ManyToOne
    private Category category;

    @NotNull(message = "O valor do item é obrigatorio!")
    private BigDecimal value;

    private LocalDate registerDate;
}
