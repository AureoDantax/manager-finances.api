package br.com.managerfinances.api.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "categories", schema = "api")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Titulo é obrigatório!")
    private String title;

    @NotBlank(message = "Cor é obrigatória!")
    private String color;

    @NotNull(message = "É necessário informar se a categoria é uma despesa!")
    private Boolean expense;



}

