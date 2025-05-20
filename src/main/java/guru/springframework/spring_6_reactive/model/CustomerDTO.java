package guru.springframework.spring_6_reactive.model;


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
public class CustomerDTO {

    private Integer id;

    private String email;
    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
