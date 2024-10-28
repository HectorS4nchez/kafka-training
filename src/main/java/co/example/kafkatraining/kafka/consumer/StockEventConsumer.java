package co.example.kafkatraining.kafka.consumer;

import co.example.kafkatraining.handler.PromotionHandler;
import co.example.kafkatraining.kafka.producers.PriceDemandProducer;
import co.example.kafkatraining.kafka.producers.SalesPromotionsProducer;
import co.example.kafkatraining.schemas.HighStock;
import co.example.kafkatraining.schemas.InsufficientStock;
import co.example.kafkatraining.schemas.LowStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockEventConsumer {

    private final PriceDemandProducer priceDemandProducer; //pocas unidades
    private final SalesPromotionsProducer salesPromotionsProducer; //muchas unidades
    private final PromotionHandler promotionHandler;

    @KafkaListener(topics = "LOW_STOCK", groupId = "inventory-group")
    public void consumeLowStock(LowStock lowStock) {
        log.info("LowStock event received: {}", lowStock);
        priceDemandProducer.send(lowStock);
        handleLowStockEvent(lowStock);
    }

    @KafkaListener(topics = "HIGH_STOCK", groupId = "inventory-group")
    public void consumeHighStock(HighStock highStock) {
        log.info("HighStock event received: {}", highStock);
        promotionHandler.createPromotion(highStock);
        salesPromotionsProducer.send(highStock);
        handleHighStockEvent(highStock);
    }

    @KafkaListener(topics = "INSUFFICIENT_STOCK", groupId = "inventory-group")
    public void consumeInsufficientStock(InsufficientStock insufficientStock) {
        log.info("InsufficientStock event received: {}", insufficientStock);
        handleInsufficientStockEvent(insufficientStock);
    }

    private void handleLowStockEvent(LowStock lowStock) {
        log.warn("Low stock detected for item: {}", lowStock.id());
    }

    private void handleHighStockEvent(HighStock highStock) {
        // Implementar lógica para manejar el alto inventario, por ejemplo, sugerir promociones o almacenamiento.
        log.warn("High stock detected for item: {}", highStock.id());
    }

    private void handleInsufficientStockEvent(InsufficientStock insufficientStock) {
        log.error("Insufficient stock detected for sale: {}, item: {}",
                insufficientStock.saleId(), insufficientStock.id());
    }
}
