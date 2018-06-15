package com.kg.convert.convert.service;

import java.util.List;

import com.kg.convert.convert.model.Convert;
import com.kg.convert.convert.repository.convertrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("convertService")
public class convertserviceimp implements convertservice{

    @Autowired 
    private convertrepository convertRepository;

    @Override
    public List<Convert> getAll(){
        return convertRepository.findAll();
    }

    @Override
    public Convert saveRegister(Convert register){
        Convert c=new Convert();
        // c.setCode(register);
        return convertRepository.save(register);
    }

    @Override
    public Convert findOne(long id){
        return convertRepository.findOne(id);
    }

}