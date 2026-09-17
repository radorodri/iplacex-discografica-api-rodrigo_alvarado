// Creado por: Rodrigo Alvarado Sánchez

package org.iplacex.proyectos.discografia.artistas;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    private final IArtistaRepository artistaRepository;

    public ArtistaController(IArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    // POST
    @PostMapping(
            value = "/artista",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(
            @RequestBody Artista artista) {

        try {
            Artista artistaGuardado = artistaRepository.save(artista);

            return ResponseEntity
                    .status(201)
                    .body(artistaGuardado);

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Error al crear el artista");
        }
    }

    // GET todos
    @GetMapping(
            value = "/artistas",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {

        try {
            List<Artista> artistas = artistaRepository.findAll();

            return ResponseEntity.ok(artistas);

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    // GET por ID
    @GetMapping(
            value = "/artista/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(
            @PathVariable String id) {

        try {

            return artistaRepository.findById(id)
                    .map(artista -> ResponseEntity.ok().body((Object) artista))
                    .orElseGet(() ->
                            ResponseEntity
                                    .notFound()
                                    .build()
                    );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Error al obtener el artista");
        }
    }

    // UPDATE
    @PutMapping(
            value = "/artista/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        try {

            if (!artistaRepository.existsById(id)) {
                return ResponseEntity
                        .notFound()
                        .build();
            }

            artista._id = id;

            Artista artistaActualizado =
                    artistaRepository.save(artista);

            return ResponseEntity.ok(artistaActualizado);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Error al actualizar el artista");
        }
    }

    // DELETE
    @DeleteMapping(
            value = "/artista/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(
            @PathVariable String id) {

        try {

            if (!artistaRepository.existsById(id)) {
                return ResponseEntity
                        .notFound()
                        .build();
            }

            artistaRepository.deleteById(id);

            return ResponseEntity.ok(
                    "Artista eliminado correctamente"
            );

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Error al eliminar el artista");
        }
    }
}