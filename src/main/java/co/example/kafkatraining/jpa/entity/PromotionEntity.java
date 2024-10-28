package co.example.kafkatraining.jpa.entity;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PromotionEntity {
    private String id;
    private BigDecimal discountPercentage;
    private boolean twoForOne;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> itemIds;
    private String category;
}
