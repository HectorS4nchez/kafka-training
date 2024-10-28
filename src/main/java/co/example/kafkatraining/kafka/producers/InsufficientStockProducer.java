package co.example.kafkatraining.kafka.producers;

import co.example.kafkatraining.schemas.InsufficientStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InsufficientStockProducer {

    private static final String TOPIC_NAME = "INSUFFICIENT_STOCK";

    private final KafkaTemplate<String, InsufficientStock> kafkaTemplate;

    public void send(InsufficientStock message) {

        kafkaTemplate.send(TOPIC_NAME, message.id(), message);

    }

}
