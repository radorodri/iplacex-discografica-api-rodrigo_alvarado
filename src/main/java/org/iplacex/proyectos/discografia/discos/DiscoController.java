// Creado por: Rodrigo Alvarado Sánchez

package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository discoRepository;

    private final IArtistaRepository artistaRepository;

    public DiscoController(
            IDiscoRepository discoRepository,
            IArtistaRepository artistaRepository) {

        this.discoRepository = discoRepository;
        this.artistaRepository = artistaRepository;
    }

    // POST disco
    @PostMapping(
            value = "/disco",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(
            @RequestBody Disco disco) {

        try {

            boolean artistaExiste =
                    artistaRepository.existsById(disco.idArtista);

            if (!artistaExiste) {

                return ResponseEntity
                        .notFound()
                        .build();
            }

            Disco discoGuardado =
                    discoRepository.save(disco);

            return ResponseEntity
                    .status(201)
                    .body(discoGuardado);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Error al crear el disco");
        }
    }

    // GET todos los discos
    @GetMapping(
            value = "/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        try {

            List<Disco> discos =
                    discoRepository.findAll();

            return ResponseEntity.ok(discos);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    // GET disco por ID
    @GetMapping(
            value = "/disco/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(
            @PathVariable String id) {

        try {

            return discoRepository.findById(id)
                    .map(disco ->
                            ResponseEntity
                                    .ok()
                                    .body((Object) disco))
                    .orElseGet(() ->
                            ResponseEntity
                                    .notFound()
                                    .build());

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body("Error al obtener el disco");
        }
    }

    // GET discos de un artista
    @GetMapping(
            value = "/artista/{id}/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>>
    HandleGetDiscosByArtistaRequest(
            @PathVariable String id) {

        try {

            List<Disco> discos =
                    discoRepository.findDiscosByIdArtista(id);

            return ResponseEntity.ok(discos);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}