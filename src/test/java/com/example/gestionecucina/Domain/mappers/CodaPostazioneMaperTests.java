package com.example.gestionecucina.Domain.mappers;

import com.example.gestionecucina.Domain.Entity.CodaPostazioneEntity;
import com.example.gestionecucina.Domain.dto.CodaPostazioneDTO;
import com.example.gestionecucina.Domain.mappers.impl.CodaPostazioneMapper;
import com.example.gestionecucina.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * In questa classe di test si verifica il corretto funzionamento dei mapper per la classe CodaPostazioneEntity
 * e CodaPostazioneDTO
 */
@SpringBootTest
public class CodaPostazioneMaperTests {

    @Autowired
    private CodaPostazioneMapper codaPostazioneMapper;

    @Test
    public void testMapTo(){
        CodaPostazioneEntity codaPostazioneEntity = TestDataUtil.createCodaPostazioneEntityA();
        CodaPostazioneDTO codaPostazioneDTO = codaPostazioneMapper.mapTo(codaPostazioneEntity);

        assertThat(codaPostazioneDTO.getIngredientePrincipale()).isEqualTo(codaPostazioneEntity.getIngredientePrincipale());
        assertThat(codaPostazioneDTO.getNumeroOrdiniPresenti()).isEqualTo(codaPostazioneEntity.getNumeroOrdiniPresenti());
        assertThat(codaPostazioneDTO.getGradoRiempimento()).isEqualTo(codaPostazioneEntity.getGradoRiempimento());

        while(codaPostazioneDTO.getNumeroOrdiniPresenti()>0) {
            assertThat(codaPostazioneDTO.remove().get().getId()).isEqualTo(codaPostazioneEntity.remove().get().getId());
        }

    }

    @Test
    public void testMapFrom(){
        CodaPostazioneDTO codaPostazioneDTO  = TestDataUtil.createCodaPostazioneDTOA();
        CodaPostazioneEntity codaPostazioneEntity = codaPostazioneMapper.mapFrom(codaPostazioneDTO);

        assertThat(codaPostazioneDTO.getIngredientePrincipale()).isEqualTo(codaPostazioneEntity.getIngredientePrincipale());
        assertThat(codaPostazioneDTO.getNumeroOrdiniPresenti()).isEqualTo(codaPostazioneEntity.getNumeroOrdiniPresenti());
        assertThat(codaPostazioneDTO.getGradoRiempimento()).isEqualTo(codaPostazioneEntity.getGradoRiempimento());

        while(codaPostazioneDTO.getNumeroOrdiniPresenti()>0) {
            assertThat(codaPostazioneDTO.remove().get().getId()).isEqualTo(codaPostazioneEntity.remove().get().getId());
        }

    }

}
