package br.com.managerfinances.api.bean;


import br.com.managerfinances.api.repository.CategoriaRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ITEM", schema = "api")
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String nome;

    @NotBlank
    @ManyToOne
    private Categoria categoria;

    @NotBlank
    private BigDecimal valor;

    @NotBlank
    private Boolean despesa;

    @Column(name = "sequential_id")

    private Integer sequentialId;


}
