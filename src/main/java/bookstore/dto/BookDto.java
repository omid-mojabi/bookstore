package bookstore.dto;

import bookstore.model.enumeration.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @NotNull
    @Size(min = 1, max = 255)
    private String title;

    @NotNull
    private Set<AuthorDto> author;

    @NotNull
    @Min(value = 1, message = "price should be greater than zero")
    private BigDecimal price;

    @NotNull
    @Min(value = 1, message = "Stock should be greater than zero")
    private Integer stock;

    private Category category;

    private byte[] coverImage;

}

