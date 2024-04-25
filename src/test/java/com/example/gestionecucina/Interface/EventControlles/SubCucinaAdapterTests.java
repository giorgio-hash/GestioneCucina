package com.example.gestionecucina.Interface.EventControlles;

import ch.qos.logback.classic.Logger;
import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.example.gestionecucina.util.TestAppender;
import com.example.gestionecucina.util.TestDataUtil;
import com.example.gestionecucina.util.TestUtil;
import com.example.gestionecucina.Interface.EventControllers.impl.SubOrderAdapter;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Questa classe è un test di integrazione che utilizza un'istanza Embedded Kafka.
 * @EmbeddedKafka inietta una istanza di EmbeddedKafkaBroker nella nostra classe di test.
 * Embedded Kafka è una libreria che fornisce istanze di Kafka e Confluent Schema Registry in memoria
 * per eseguire i test, in modo da non dipendere da un server Kafka esterno.
 *
 * Viene testato specificamente il CONSUMER di kafka, nello specifico la classe SubCucinaAdapter,
 * si inviano messaggi da un producer di embeddedkafka e si verifica che siano stati inviati sul
 * topic specifico utilizzando il nostro consumer di SubCucinaAdapter
 */
@EnableKafka
@SpringBootTest
@DirtiesContext
@Log
@EmbeddedKafka(partitions = 1,
        controlledShutdown = false,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
        topics = {"${spring.kafka.consumer.topic}"})
public class SubCucinaAdapterTests {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;
    @Autowired
    private SubOrderAdapter subOrderAdapter;
    @Value("${spring.kafka.consumer.topic}")
    private String topic;
    private Logger logger;
    private TestAppender testAppender;

    @BeforeEach
    public void setup() {
        subOrderAdapter.resetLatch();

        logger = (Logger) LoggerFactory.getLogger(SubOrderAdapter.class);
        testAppender = new TestAppender();
        testAppender.start();
        logger.addAppender(testAppender);
    }

    @Test
    @Order(1)
    public void testLogOutput1() throws Exception {

        OrdineDTO ordineDTO = TestDataUtil.createOrdineDtoA();
        String notifica = TestUtil.serialize(ordineDTO);

        CompletableFuture<SendResult<Integer, String>> future = TestUtil.sendMessageToTopic(topic,notifica,embeddedKafka);
        log.info("Sent Message=[" + notifica + "] with offset=[0]");

        boolean messageConsumed = subOrderAdapter.getLatch().await(10, TimeUnit.SECONDS);

        //testo il corretto invio
        future.whenComplete((result,ex)->{
            assertThat(ex).isNull(); // verifica che non è stata sollevata alcuna eccezione
        });

        // testo la corretta ricezione
        assertTrue(messageConsumed);
        assertFalse(testAppender.events.isEmpty());
        assertEquals("Received a message " + notifica + ", from " + topic + " topic, 0 partition, and 0 offset", testAppender.events.get(0).getFormattedMessage());

        OrdineDTO ordineDTOReceived = subOrderAdapter.getLastMessageReceived();
        assertEquals(ordineDTO, ordineDTOReceived);
        logger.detachAppender(testAppender);
    }

    @Test
    @Order(2)
    public void testLogOutput2() throws Exception {

        OrdineDTO ordineDTO = TestDataUtil.createOrdineDtoB();
        String notifica = TestUtil.serialize(ordineDTO);

        CompletableFuture<SendResult<Integer, String>> future = TestUtil.sendMessageToTopic(topic,notifica,embeddedKafka);
        log.info("Sent Message=[" + notifica + "] with offset=[0]");

        boolean messageConsumed = subOrderAdapter.getLatch().await(10, TimeUnit.SECONDS);

        //testo il corretto invio
        future.whenComplete((result,ex)->{
            assertThat(ex).isNull(); // verifica che non è stata sollevata alcuna eccezione
        });

        // testo la corretta ricezione
        assertTrue(messageConsumed);
        assertFalse(testAppender.events.isEmpty());
        assertEquals("Received a message " + notifica + ", from " + topic + " topic, 0 partition, and 1 offset", testAppender.events.get(0).getFormattedMessage());

        OrdineDTO ordineDTOReceived = subOrderAdapter.getLastMessageReceived();
        assertEquals(ordineDTO, ordineDTOReceived);
        logger.detachAppender(testAppender);
    }

}