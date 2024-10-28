package co.example.kafkatraining.handler;

import co.example.kafkatraining.schemas.HighStock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PromotionHandler {

    public void createPromotion(HighStock highStock) {
            log.info("Promotion created: {}", highStock);
    }

}