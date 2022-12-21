package pl.gov.listaobecnosci.sections;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gov.listaobecnosci.sections.dto.SectionDTO;
import pl.gov.listaobecnosci.sections.dto.SectionWithWorkersDTO;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionController {

    private final ISectionService service;

    private final ISectionMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<List<SectionDTO>> getAllSections() {
        var sectionDTOs = mapper.mapToSectionDTOs(service.getAllSections());
        return new ResponseEntity<>(sectionDTOs, HttpStatus.OK);
    }

    @GetMapping("/{sectionName}")
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<SectionWithWorkersDTO> getSectionByName(@PathVariable String sectionName) {
        return new ResponseEntity<>(mapper.mapToSectionWithWorkersDTO(service.getSectionByName(sectionName)),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<SectionDTO> addSection(@RequestBody SectionDTO sectionDTO) {
        var newSection = mapper.mapToSection(sectionDTO);
        var newSectionDTO = mapper.mapToSectionDTO(service.addNewSection(newSection));
        return new ResponseEntity<>(newSectionDTO, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<SectionDTO> updateSection(@RequestBody SectionDTO sectionDTO) {
        var sectionToUpdate = mapper.mapToSection(sectionDTO);
        var updatedSectionDTO = mapper.mapToSectionDTO(service.updateSection(sectionToUpdate));
        return new ResponseEntity<>(updatedSectionDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{sectionId}")
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<SectionDTO> deleteSection(@PathVariable Long sectionId) {
        return new ResponseEntity<>(mapper.mapToSectionDTO(service.deleteSection(sectionId)), HttpStatus.OK);
    }
}
