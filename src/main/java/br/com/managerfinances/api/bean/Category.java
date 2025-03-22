package br.com.managerfinances.api.bean;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
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
    private String name;

    @NotBlank(message = "Cor é obrigatória!")
    private String color;

    @NotBlank(message = "É necessário informar a categoria!")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category categoria;

    @NotNull(message = "É necessário informar se a categoria é uma despesa!")
    private Boolean expense;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> transactions = new ArrayList<>();


}

