package co.example.kafkatraining.schemas;

import lombok.Builder;

@Builder
public record HighStock(String id, String saleId, String customerId, String descripcion) {
}
