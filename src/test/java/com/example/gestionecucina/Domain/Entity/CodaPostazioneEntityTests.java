package com.example.gestionecucina.Domain.Entity;

import com.example.gestionecucina.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CodaPostazioneEntityTests {

    @Test
    public void testConstructionOfCodaPostazioneEntity(){
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity("RISO");
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(0);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(0.0);
        assertThat(codaPostazioneEntity.getQueue()).isEmpty();
    }

    @Test
    public void testInsertInCodaPostazioneEntity(){
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity("RISO");
        OrdineEntity ordineEntityA = TestDataUtil.createOrdineEntityA();
        assertThat(codaPostazioneEntity.insert(ordineEntityA)).isTrue();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(1);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(1/(codaPostazioneEntity.getCapacita()));
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA);
    }

    @Test
    public void testRemoveFromCodaPostazioneEntity(){
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity("RISO");
        OrdineEntity ordineEntityA = TestDataUtil.createOrdineEntityA();
        codaPostazioneEntity.insert(ordineEntityA);
        assertThat(codaPostazioneEntity.remove().get()).isEqualTo(ordineEntityA);
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(0);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(0.0);
        assertThat(codaPostazioneEntity.getQueue()).isEmpty();
    }

    @Test
    public void testElementOfCodaPostazioneEntity(){
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity("RISO");
        OrdineEntity ordineEntityA = TestDataUtil.createOrdineEntityA();
        codaPostazioneEntity.insert(ordineEntityA);
        assertThat(codaPostazioneEntity.element().get()).isEqualTo(ordineEntityA);
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(1);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(1/(codaPostazioneEntity.getCapacita()));
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA);
    }

    @Test
    public void testMultipleInsertionsInPostazioneEntity(){
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity("RISO");
        assertThat(codaPostazioneEntity.getCapacita()).isEqualTo(5);

        OrdineEntity ordineEntityA1 = TestDataUtil.createOrdineEntityA();
        OrdineEntity ordineEntityB1 = TestDataUtil.createOrdineEntityB();
        OrdineEntity ordineEntityC1 = TestDataUtil.createOrdineEntityC();
        OrdineEntity ordineEntityA2 = TestDataUtil.createOrdineEntityA();
        OrdineEntity ordineEntityB2 = TestDataUtil.createOrdineEntityB();

        assertThat(codaPostazioneEntity.insert(ordineEntityA1)).isTrue();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(1);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(1/5);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA1);

        assertThat(codaPostazioneEntity.insert(ordineEntityB1)).isTrue();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(2);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(2/5);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA1);

        assertThat(codaPostazioneEntity.insert(ordineEntityC1)).isTrue();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(3);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(3/5);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA1);

        assertThat(codaPostazioneEntity.insert(ordineEntityA2)).isTrue();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(4);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(4/5);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA1);

        assertThat(codaPostazioneEntity.insert(ordineEntityB2)).isTrue();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(5);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(1);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA1);
    }

    @Test
    public void testMultipleRemovalsFromPostazioneEntity(){
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity("RISO");
        assertThat(codaPostazioneEntity.getCapacita()).isEqualTo(5);

        OrdineEntity ordineEntityA1 = TestDataUtil.createOrdineEntityA();
        codaPostazioneEntity.insert(ordineEntityA1);
        OrdineEntity ordineEntityB1 = TestDataUtil.createOrdineEntityB();
        codaPostazioneEntity.insert(ordineEntityB1);
        OrdineEntity ordineEntityC1 = TestDataUtil.createOrdineEntityC();
        codaPostazioneEntity.insert(ordineEntityC1);
        OrdineEntity ordineEntityA2 = TestDataUtil.createOrdineEntityA();
        codaPostazioneEntity.insert(ordineEntityA2);
        OrdineEntity ordineEntityB2 = TestDataUtil.createOrdineEntityB();
        codaPostazioneEntity.insert(ordineEntityB2);
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(5);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(1);

        assertThat(codaPostazioneEntity.remove().get()).isEqualTo(ordineEntityA1);
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(4);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(4/5);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityB1);

        assertThat(codaPostazioneEntity.remove().get()).isEqualTo(ordineEntityB1);
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(3);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(3/5);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityC1);

        assertThat(codaPostazioneEntity.remove().get()).isEqualTo(ordineEntityC1);
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(2);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(2/5);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA2);

        assertThat(codaPostazioneEntity.remove().get()).isEqualTo(ordineEntityA2);
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(1);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(1/5);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityB2);

        assertThat(codaPostazioneEntity.remove().get()).isEqualTo(ordineEntityB2);
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(0);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(0);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(null);
    }

    // Considerando upper bound = 5
    @Test
    public void testUpperBoundOfPostazioneEntity() {
        CodaPostazioneEntity codaPostazioneEntity = TestDataUtil.createCodaPostazioneEntityB();
        assertThat(codaPostazioneEntity.getCapacita()).isEqualTo(5);
        assertThat(codaPostazioneEntity.isFull()).isTrue();
        OrdineEntity ordineEntityA1 = codaPostazioneEntity.element().get();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(5);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(1);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA1);

        OrdineEntity ordineEntityC2 = TestDataUtil.createOrdineEntityC();
        assertThat(codaPostazioneEntity.insert(ordineEntityC2)).isFalse();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(5);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(1);
        assertThat(codaPostazioneEntity.getQueue().peek()).isEqualTo(ordineEntityA1);
    }

    @Test
    public void testLowerBoundOfPostazioneEntity(){
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity("RISO");
        assertThat(codaPostazioneEntity.remove().isPresent()).isFalse();
        assertThat(codaPostazioneEntity.getNumeroOrdiniPresenti()).isEqualTo(0);
        assertThat(codaPostazioneEntity.getGradoRiempimento()).isEqualTo(0.0);
        assertThat(codaPostazioneEntity.getQueue()).isEmpty();
    }

    }
