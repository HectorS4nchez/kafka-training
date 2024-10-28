package co.example.kafkatraining.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefundEntity {
    @Id
    private String refundId;
    private String orderId;
    private BigDecimal amount;
    private LocalDateTime refundDate;
    private String status;

}
