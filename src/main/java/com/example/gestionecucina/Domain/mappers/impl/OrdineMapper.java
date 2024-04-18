package com.example.GestioneCucina.Domain.mappers.impl;

import com.example.GestioneCucina.Domain.dto.OrdineDTO;
import com.example.GestioneCucina.Domain.Entity.OrdineEntity;
import com.example.GestioneCucina.Domain.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrdineMapper implements Mapper<OrdineEntity, OrdineDTO> {

    private ModelMapper modelMapper;

    public OrdineMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public OrdineDTO mapTo(OrdineEntity ordineEntity) {
        return modelMapper.map(ordineEntity,OrdineDTO.class);
    }

    @Override
    public OrdineEntity mapFrom(OrdineDTO ordineDTO) {
        return modelMapper.map(ordineDTO, OrdineEntity.class);
    }
}
