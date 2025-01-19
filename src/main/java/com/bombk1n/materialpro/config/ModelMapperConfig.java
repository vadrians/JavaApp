package com.bombk1n.materialpro.config;

import com.bombk1n.materialpro.dto.MovieDTO;
import com.bombk1n.materialpro.model.MovieDocument;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(MovieDTO.ShowtimeDTO.class, MovieDocument.ShowtimeDocument.class);

        return modelMapper;

    }

}
