package com.example.gestionecucina.Interface.HTTPControlles;

/**
 * Test di Integrazione della classe in HTTPControllers usando MockMVC
 * MockMvc permette di testare i controller in un ambiente simulato senza avviare un server.
 */

import com.example.gestionecucina.Domain.Entity.CodaPostazioneEntity;
import com.example.gestionecucina.Domain.GestioneCode;
import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;
import com.example.gestionecucina.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableKafka
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(partitions = 1,
        controlledShutdown = false,
        brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
        topics = {"${spring.kafka.producer.topic}",
                "${spring.kafka.consumer.topic}"})
@AutoConfigureMockMvc
public class RestControllerTests {

    @Autowired
    private GestioneCode gestioneCode;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;
    @Value("${spring.kafka.producer.topic}")
    private String producerTopic;

    // Indice API:
    /* getQueue */
    @Test
    public void testThatGetQueueSuccessfullyReturnsHttp200WhenExist() throws Exception {
        String key = "riso";
        HashMap<String, CodaPostazioneEntity> postazioni = TestDataUtil.createHashMapPostazioniA();
        gestioneCode.setPostazioni(postazioni);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cucina/codapostazione/" + key)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetQueueSuccessfullyReturnsQueueWhenExist() throws Exception {
        String key = "riso";
        HashMap<String, CodaPostazioneEntity> postazioni = TestDataUtil.createHashMapPostazioniA();
        gestioneCode.setPostazioni(postazioni);
        String expectedJson = objectMapper.writeValueAsString(postazioni.get(key.toUpperCase()));

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/cucina/codapostazione/" + key)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, false);
    }

    @Test
    public void testThatGetQueueSuccessfullyReturnsHttp404WhenNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/cucina/codapostazione/notexists")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    /* selectOrder */

    @Test
    public void testThatSelectOrderSuccessfullyReturnsHttp200WhenExist() throws Exception {
        String key = "riso";
        HashMap<String, CodaPostazioneEntity> postazioni = TestDataUtil.createHashMapPostazioniA();
        gestioneCode.setPostazioni(postazioni);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cucina/codapostazione/" + key + "/nextorder")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatSelectOrderSuccessfullyReturnsOrderWhenExist() throws Exception {
        String key = "riso";
        HashMap<String, CodaPostazioneEntity> postazioni = TestDataUtil.createHashMapPostazioniA();
        gestioneCode.setPostazioni(postazioni);
        String expectedJson = objectMapper.writeValueAsString(postazioni.get(key.toUpperCase()).element().get());

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/cucina/codapostazione/" + key + "/nextorder")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, false);
    }

    @Test
    public void testThatSelectOrderSuccessfullyReturnsHttp404WhenNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/cucina/codapostazione/" + "notexists" + "/nextorder")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatSelectOrderSuccessfullyReturnsHttp404WhenNoOrderExists() throws Exception {
        String key = "riso";
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity(key);
        HashMap<String, CodaPostazioneEntity> postazioni = new HashMap<>();
        postazioni.put(key, codaPostazioneEntity); // coda vuota
        gestioneCode.setPostazioni(postazioni);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cucina/codapostazione/" + key + "/nextorder")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    /* completeOrder */
    @Test
    public void testThatCompleteOrderSuccessfullyReturnsHttp200WhenExist() throws Exception {
        String key = "riso";
        HashMap<String, CodaPostazioneEntity> postazioni = TestDataUtil.createHashMapPostazioniA();
        gestioneCode.setPostazioni(postazioni);
        NotificaPrepOrdineDTO notificaPrepOrdineDTO = TestDataUtil.createotificaPrepOrdineDTOA();
        String json = objectMapper.writeValueAsString(notificaPrepOrdineDTO);
        String expectedJson = objectMapper.writeValueAsString(postazioni.get(key.toUpperCase()).element().get());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/cucina/codapostazione/" + key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCompleteOrderSuccessfullyReturnsOrderWhenExist() throws Exception {
        String key = "riso";
        HashMap<String, CodaPostazioneEntity> postazioni = TestDataUtil.createHashMapPostazioniA();
        gestioneCode.setPostazioni(postazioni);
        NotificaPrepOrdineDTO notificaPrepOrdineDTO = TestDataUtil.createotificaPrepOrdineDTOA();
        String json = objectMapper.writeValueAsString(notificaPrepOrdineDTO);
        String expectedJson = objectMapper.writeValueAsString(postazioni.get(key.toUpperCase()).element().get());

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/cucina/codapostazione/" + key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn();

        String actualJson = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedJson, actualJson, false);

        // Testo il corretto invio della notifica sul topic del message broker
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false", embeddedKafka);
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<Integer, String> consumer = cf.createConsumer();
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, producerTopic);
        ConsumerRecord<Integer, String> received = KafkaTestUtils.getSingleRecord(consumer, producerTopic);

        assertThat(received.offset()).isEqualTo(0);
        assertThat(received.topic()).isEqualTo(producerTopic);
        assertThat(received.partition()).isEqualTo(0);
        NotificaPrepOrdineDTO notificaPrepOrdineDTOReceived = objectMapper.readValue(received.value(),NotificaPrepOrdineDTO.class);
        assertThat(notificaPrepOrdineDTO).isEqualTo(notificaPrepOrdineDTOReceived);
    }

    @Test
    public void testThatCompleteOrderSuccessfullyReturnsHttp404WhenNotExists() throws Exception {
        NotificaPrepOrdineDTO notificaPrepOrdineDTO = TestDataUtil.createotificaPrepOrdineDTOA();
        String json = objectMapper.writeValueAsString(notificaPrepOrdineDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/cucina/codapostazione/" + "notexists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatCompleteOrderSuccessfullyReturnsHttp404WhenNoOrderExists() throws Exception {
        String key = "riso";
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity(key);
        HashMap<String, CodaPostazioneEntity> postazioni = new HashMap<>();
        postazioni.put(key, codaPostazioneEntity); // coda vuota
        gestioneCode.setPostazioni(postazioni);
        NotificaPrepOrdineDTO notificaPrepOrdineDTO = TestDataUtil.createotificaPrepOrdineDTOA();
        String json = objectMapper.writeValueAsString(notificaPrepOrdineDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/cucina/codapostazione/" + key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }


}
