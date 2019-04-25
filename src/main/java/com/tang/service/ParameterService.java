package com.tang.service;

import com.tang.dao.ParameterRepository;
import com.tang.entity.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ParameterService {
    
    @Autowired
    private ParameterRepository parameterRepository;

    public Optional<Parameter> findById(Integer id) {
        return parameterRepository.findById(id);
    }


    public Parameter save(Parameter parameter) {
        return  parameterRepository.save(parameter);
    }


    public void delete(Integer id) {
        parameterRepository.deleteById(id);
    }

}
