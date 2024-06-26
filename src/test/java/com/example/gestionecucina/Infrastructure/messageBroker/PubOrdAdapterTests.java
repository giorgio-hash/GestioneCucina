package com.example.gestionecucina.Infrastructure.messageBroker;

import ch.qos.logback.classic.Logger;
import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;
import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.example.gestionecucina.Infrastructure.MessageBrokers.PubOrdAdapter;
import com.example.gestionecucina.util.TestAppender;
import com.example.gestionecucina.util.TestDataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Questa classe è un test di integrazione che utilizza un'istanza Embedded Kafka.
 * @EmbeddedKafka inietta una istanza di EmbeddedKafkaBroker nella nostra classe di test.
 * Embedded Kafka è una libreria che fornisce istanze di Kafka e Confluent Schema Registry in memoria
 * per eseguire i test, in modo da non dipendere da un server Kafka esterno.
 *
 * Viene testato specificamente il PRODUCER di kafka, nello specifico la classe CucinaPubService,
 * si inviano messaggi dal nostro producer e si verifica che siano stati inviati sul topic specifico
 * utilizzando un consumer creato con embedded kafka
 */
@EnableKafka
@SpringBootTest()
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EmbeddedKafka(partitions = 1,
        controlledShutdown = false,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
        topics = {"${spring.kafka.producer.topic}"})
class PubOrdAdapterTests {

    @Autowired
    private PubOrdAdapter producer;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${spring.kafka.producer.topic}")
    private String topic;
    private Logger logger;
    private TestAppender testAppender;

    @BeforeEach
    public void setup() {
        logger = (Logger) LoggerFactory.getLogger(PubOrdAdapter.class);
        testAppender = new TestAppender();
        testAppender.start();
        logger.addAppender(testAppender);
    }

    @Test
    public void testSendingMessage() throws JsonProcessingException {

        NotificaPrepOrdineDTO notificaPrepOrdineDTO = TestDataUtil.createotificaPrepOrdineDTOA();

        producer.send(notificaPrepOrdineDTO);
        String message = objectMapper.writeValueAsString(notificaPrepOrdineDTO);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false", embeddedKafka);
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<Integer, String> consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic);
        ConsumerRecord<Integer, String> received = KafkaTestUtils.getSingleRecord(consumer, topic);

        // testo il corretto invio
        assertFalse(testAppender.events.isEmpty());
        assertEquals("Sent Message=[" + message + "] with offset=[0]", testAppender.events.get(0).getFormattedMessage());

        // testo la corretta ricezione
        assertThat(received.offset()).isEqualTo(0);
        assertThat(received.topic()).isEqualTo(topic);
        assertThat(received.partition()).isEqualTo(0);
        NotificaPrepOrdineDTO notificaPrepOrdineDTOReceived = objectMapper.readValue(received.value(),NotificaPrepOrdineDTO.class);
        assertThat(notificaPrepOrdineDTO).isEqualTo(notificaPrepOrdineDTOReceived);

        logger.detachAppender(testAppender);
    }

}