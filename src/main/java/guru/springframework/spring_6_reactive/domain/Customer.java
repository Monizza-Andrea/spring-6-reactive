package guru.springframework.spring_6_reactive.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private Integer id;

    @NotBlank
    @Size(min = 5, max = 255)
    private String email;

    @NotBlank
    @Size(min = 5, max = 255)
    private String customerName;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}