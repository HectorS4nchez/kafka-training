package co.example.kafkatraining.kafka.producers;

import co.example.kafkatraining.schemas.LowStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class PriceDemandProducer {

    private static final String TOPIC_NAME = "PRICE_DEMAND";

    private final KafkaTemplate<String, LowStock> kafkaTemplate;

    public void send(LowStock message) {

        kafkaTemplate.send(TOPIC_NAME, message.id(), message);

    }


}
