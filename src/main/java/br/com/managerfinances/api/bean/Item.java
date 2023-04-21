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
@Table(name = "ITEM", schema = "api")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "O nome do item é obrigatorio!")
    private String nome;

    @ManyToOne
    private Categoria categoria;

    @NotNull(message = "O valor do item é obrigatorio!")
    private BigDecimal valor;

    private LocalDate dataRegistro;
}
