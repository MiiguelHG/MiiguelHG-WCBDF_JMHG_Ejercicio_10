package com.upiiz.superheroes.services;

import com.upiiz.superheroes.entities.HeroeEntity;
import com.upiiz.superheroes.repositories.HeroeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroeService {
    @Autowired
    private HeroeRepository heroeRepository;

    public HeroeEntity save(HeroeEntity heroe){
        return heroeRepository.save(heroe);
    }

    public List<HeroeEntity> findAll(){
        return heroeRepository.findAll();
    }

    public HeroeEntity findById(Long id){
        return heroeRepository.findById(id).orElse(null);
    }

    public HeroeEntity update(HeroeEntity heroe){
        if (heroeRepository.existsById(heroe.getId())){
            return heroeRepository.save(heroe);
        }
        return null;
    }

    public boolean delete(Long id){
        if (heroeRepository.existsById(id)){
            heroeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
