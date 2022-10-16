package pl.gov.listaobecnosci.freedays;

import pl.gov.listaobecnosci.freedays.dto.FreedayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/freedays")
@RequiredArgsConstructor
public class FreedayController {

    private final IFreedayService service;

    private final IFreedayMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<List<FreedayDTO>> getAllFreedays() {
        var freedayDTOs = mapper.mapToFreedayDTOs(service.getAllFreedays());
        return new ResponseEntity<>(freedayDTOs, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<FreedayDTO> addFreeday(@RequestBody FreedayDTO freedayDTO) {
        var newFreeday = mapper.mapToFreeday(freedayDTO);
        var newFreedayDTO = mapper.mapToFreedayDTO(service.addNewFreeday(newFreeday));
        return new ResponseEntity<>(newFreedayDTO, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<FreedayDTO> updateFreeday(@RequestBody FreedayDTO freedayDTO) {
        var freedayToUpdate = mapper.mapToFreeday(freedayDTO);
        var updatedFreedayDTO = mapper.mapToFreedayDTO(service.updateFreeday(freedayToUpdate));
        return new ResponseEntity<>(updatedFreedayDTO, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<FreedayDTO> deleteFreeday(@RequestBody FreedayDTO freedayDTO) {
        service.deleteFreeday(mapper.mapToFreeday(freedayDTO));
        return new ResponseEntity<>(freedayDTO, HttpStatus.OK);
    }
}
