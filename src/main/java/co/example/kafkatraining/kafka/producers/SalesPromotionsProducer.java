package co.example.kafkatraining.kafka.producers;

import co.example.kafkatraining.schemas.HighStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class SalesPromotionsProducer {

    private static final String TOPIC_NAME = "SALES_PROMOTION";

    private final KafkaTemplate<String, HighStock> kafkaTemplate;

    public void send(HighStock message) {

        kafkaTemplate.send(TOPIC_NAME, message.id(), message);

    }


}
