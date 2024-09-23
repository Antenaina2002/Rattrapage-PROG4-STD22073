package com.std22073.patrimoine.model;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patrimoine {
    private String id;
    private String possessor;
    private LocalDateTime lastUpdate;
}
