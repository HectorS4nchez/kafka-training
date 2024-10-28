package co.example.kafkatraining.handler;

import co.example.kafkatraining.jpa.entity.ItemEntity;
import co.example.kafkatraining.jpa.repository.ItemRepository;
import co.example.kafkatraining.kafka.producers.HighStockProducer;
import co.example.kafkatraining.kafka.producers.InsufficientStockProducer;
import co.example.kafkatraining.kafka.producers.LowStockProducer;
import co.example.kafkatraining.schemas.HighStock;
import co.example.kafkatraining.schemas.InsufficientStock;
import co.example.kafkatraining.schemas.Item;
import co.example.kafkatraining.schemas.LowStock;
import co.example.kafkatraining.schemas.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesHandler {

    private final ItemRepository repository;
    private final LowStockProducer lowStockProducer;
    private final HighStockProducer highStockProducer;
    private final InsufficientStockProducer insufficientStockProducer;


    public void process(Sale sale) {

        for (Item item: sale.items()){

            Optional<ItemEntity> entityOpt = repository.findById(item.id());

            if (entityOpt.isPresent()) {

                ItemEntity itemEntity = entityOpt.get();

                try{

                    itemEntity.decreaseQuantity(item.quantity());

                } catch (Exception e) {
                    InsufficientStock insufficientStock = InsufficientStock.builder()
                            .id(item.id())
                            .saleId(sale.saleId())
                            .customerId(sale.customerId())
                            .descripcion("Inventory insufficient stock for sale with %s quantity".formatted(itemEntity.getQuantity()))
                            .build();

                    insufficientStockProducer.send(insufficientStock);
                }

                repository.save(itemEntity);

                if (itemEntity.getQuantity() < 100 ){
                    LowStock lowStock = LowStock.builder()
                            .id(item.id())
                            .saleId(sale.saleId())
                            .customerId(sale.customerId())
                            .descripcion("Inventory near out of stock %s".formatted(itemEntity.getQuantity()))
                            .build();

                    lowStockProducer.send(lowStock);
                }

                if (itemEntity.getQuantity() > 500){
                    HighStock highStock = HighStock.builder()
                            .id(item.id())
                            .saleId(sale.saleId())
                            .customerId(sale.customerId())
                            .descripcion("Inventory at maximum %s".formatted(itemEntity.getQuantity()))
                            .build();

                    highStockProducer.send(highStock);
                }

            }
        }
    }
}
