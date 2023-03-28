package br.com.managerfinances.api.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "CATEGORIA", schema = "api")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Categoria extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String tag;

    @NotBlank
    private String cor;

    private Integer sequentialId;


    private Boolean despesa;


}

