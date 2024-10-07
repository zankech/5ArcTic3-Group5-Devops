package tn.esprit.devops_project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProduct;

    String title;
    float price;
    int quantity;

    @Enumerated(EnumType.STRING)
    ProductCategory category;

    @ManyToOne
    @JsonIgnore
    Stock stock;

    // Nouveau champ pour la remise
    float discount = 0;  // Par défaut à 0, c'est-à-dire pas de remise
}
