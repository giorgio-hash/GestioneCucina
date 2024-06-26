package com.example.gestionecucina.Domain.ports;

import ch.qos.logback.classic.Logger;
import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;
import com.example.gestionecucina.util.TestDataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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

/**
 * Questa classe è un test di integrazione per la porta MessagePort
 */
@EnableKafka
@SpringBootTest()
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EmbeddedKafka(partitions = 1,
        controlledShutdown = false,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
        topics = {"${spring.kafka.producer.topic}"})
class MessagePortTests {

    @Autowired
    private MessagePort<NotificaPrepOrdineDTO> messagePort;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${spring.kafka.producer.topic}")
    private String topic;
    private Logger logger;

    @Test
    public void testSendingMessage() throws JsonProcessingException {

        NotificaPrepOrdineDTO notificaPrepOrdineDTO = TestDataUtil.createotificaPrepOrdineDTOA();

        messagePort.send(notificaPrepOrdineDTO);
        String message = objectMapper.writeValueAsString(notificaPrepOrdineDTO);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false", embeddedKafka);
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<Integer, String> consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, topic);
        ConsumerRecord<Integer, String> received = KafkaTestUtils.getSingleRecord(consumer, topic);

        assertThat(received.offset()).isEqualTo(0);
        assertThat(received.topic()).isEqualTo(topic);
        assertThat(received.partition()).isEqualTo(0);
        NotificaPrepOrdineDTO notificaPrepOrdineDTOReceived = objectMapper.readValue(received.value(),NotificaPrepOrdineDTO.class);
        assertThat(notificaPrepOrdineDTOReceived).isEqualTo(notificaPrepOrdineDTO);
    }

}