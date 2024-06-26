package com.example.gestionecucina.Domain.mappers;

import com.example.gestionecucina.Domain.Entity.OrdineEntity;
import com.example.gestionecucina.Domain.mappers.impl.OrdineMapper;
import com.example.gestionecucina.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * In questa classe di test si verifica il corretto funzionamento dei mapper per la classe OrdineEntity e OrdineDTO
 */
@SpringBootTest
public class OrdineMapperTests {

    @Autowired
    private OrdineMapper ordineMapper;

    @Test
    public void testMapTo(){

        OrdineEntity ordineEntity = TestDataUtil.createOrdineEntityA();
        com.example.gestionecucina.Domain.dto.OrdineDTO ordineDTO = ordineMapper.mapTo(ordineEntity);
        assertThat(ordineDTO.getId()).isEqualTo(ordineEntity.getId());
        assertThat(ordineDTO.getIdComanda()).isEqualTo(ordineEntity.getIdComanda());

    }

    @Test
    public void testMapFrom(){

        com.example.gestionecucina.Domain.dto.OrdineDTO ordineDTO = TestDataUtil.createOrdineDtoB();
        OrdineEntity ordineEntity = ordineMapper.mapFrom(ordineDTO);
        assertThat(ordineEntity.getId()).isEqualTo(ordineDTO.getId());
        assertThat(ordineEntity.getIdComanda()).isEqualTo(ordineDTO.getIdComanda());

    }

}
