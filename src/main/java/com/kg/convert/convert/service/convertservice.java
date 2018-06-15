package com.kg.convert.convert.service;

import java.util.List;

import com.kg.convert.convert.model.Convert;

import org.springframework.stereotype.Service;

@Service
public interface convertservice {
    public List<Convert> getAll();

    public Convert saveRegister(Convert register);

    public Convert findOne(long id);
}