package model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.enumeration.Category;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Data // Lombok annotation for getters and setters
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String title;

    @NotNull
    private List<Author> author;

    @NotNull
    private Double price;

    @NotNull
    private Integer stock;

    @ManyToOne
    private Category category;

    private String coverImageUrl;  // URL or file path for image

    /**
     * Method to reduce stock
     * @param quantity
     */
    public void buyBook(int quantity) {
        if (this.stock >= quantity) {
            this.stock -= quantity;
        } else {
            throw new IllegalArgumentException("Not enough stock on this book");
        }
    }

    public void setCoverImagePath(String toString) {
    }
}
