package com.angMetal.orders.kafka;

import models.FactureEvent;
import org.apache.kafka.common.serialization.Serdes;


public class FactureEventSerde extends Serdes.WrapperSerde<FactureEvent> {
    public FactureEventSerde() {
        super(new FactureEventSerializer(), new FactureEventDeserializer());
    }
}
