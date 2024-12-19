package com.upiiz.superheroes.controllers;

import com.upiiz.superheroes.entities.CustomResponse;
import com.upiiz.superheroes.entities.HeroeEntity;
import com.upiiz.superheroes.services.HeroeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/heroes")
@CrossOrigin(origins = "*", methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE
})
public class HeroeController {
    @Autowired
    private HeroeService heroeService;

    @PostMapping
    public ResponseEntity<CustomResponse<HeroeEntity>> saveHeroe(@RequestBody HeroeEntity heroe){
        Link heroesLinks = linkTo(HeroeController.class).withSelfRel();
        List<Link> links = List.of(heroesLinks);

        try {
            HeroeEntity nuevoHeroe = heroeService.save(heroe);

            if (nuevoHeroe == null) {
                CustomResponse<HeroeEntity> response = new CustomResponse<>(0, "Heroe no guardado", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<HeroeEntity> response = new CustomResponse<>(1, "Heroe guardado con éxito", nuevoHeroe, links);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            CustomResponse<HeroeEntity> response = new CustomResponse<>(0, "Error al guardar el heroe", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //GET
    @GetMapping
    public ResponseEntity<CustomResponse<List<HeroeEntity>>> getAllHeroes(){
        Link heroesLinks = linkTo(HeroeController.class).withSelfRel();
        List<Link> links = List.of(heroesLinks);

        try{
            List<HeroeEntity> heroes = heroeService.findAll();

            if(heroes.isEmpty()){
                CustomResponse<List<HeroeEntity>> response = new CustomResponse<>(0, "No hay heroes registrados", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<List<HeroeEntity>> response = new CustomResponse<>(1, "Heroes encontrados", heroes, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<List<HeroeEntity>> response = new CustomResponse<>(0, "Error al obtener los heroes", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<HeroeEntity>> getHeroeById(@PathVariable Long id){
        Link heroesLinks = linkTo(HeroeController.class).withSelfRel();
        List<Link> links = List.of(heroesLinks);

        try{
            HeroeEntity heroe = heroeService.findById(id);

            if(heroe == null){
                CustomResponse<HeroeEntity> response = new CustomResponse<>(0, "Heroe no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            CustomResponse<HeroeEntity> response = new CustomResponse<>(1, "Heroe encontrado", heroe, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<HeroeEntity> response = new CustomResponse<>(0, "Error al obtener el heroe", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<HeroeEntity>> updateHeroe(@RequestBody HeroeEntity heroe, @PathVariable Long id) {
        Link heroesLinks = linkTo(HeroeController.class).withSelfRel();
        List<Link> links = List.of(heroesLinks);

        try {
            heroe.setId(id);
            HeroeEntity heroeActualizado = heroeService.update(heroe);

            if (heroeActualizado == null) {
                CustomResponse<HeroeEntity> response = new CustomResponse<>(0, "Heroe no actualizado", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<HeroeEntity> response = new CustomResponse<>(1, "Heroe actualizado con éxito", heroeActualizado, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            CustomResponse<HeroeEntity> response = new CustomResponse<>(0, "Error al actualizar el heroe", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<HeroeEntity>> deleteHeroe(@PathVariable Long id){
        Link heroesLinks = linkTo(HeroeController.class).withSelfRel();
        List<Link> links = List.of(heroesLinks);

        try{
            boolean eliminado = heroeService.delete(id);

            if (!eliminado){
                CustomResponse<HeroeEntity> response = new CustomResponse<>(0, "Heroe no eliminado", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            CustomResponse<HeroeEntity> response = new CustomResponse<>(1, "Heroe eliminado con éxito", null, links);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            CustomResponse<HeroeEntity> response = new CustomResponse<>(0, "Error al eliminar el heroe", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
