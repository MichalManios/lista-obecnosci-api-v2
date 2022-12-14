package pl.gov.listaobecnosci.workers;

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
import pl.gov.listaobecnosci.workers.dto.WorkerDTO;
import pl.gov.listaobecnosci.workers.dto.WorkerFlattened;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final IWorkerService service;

    private final IWorkerMapper mapper;

    @GetMapping("/{sectionName}")
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<List<WorkerFlattened>> getAllWorkersBySectionName(@PathVariable String sectionName) {
        var workersFlattened = mapper
                .mapToWorkersFlattened(service.getAllWorkersBySectionName(sectionName));
        return new ResponseEntity<>(workersFlattened, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<WorkerFlattened> addWorker(@RequestBody WorkerDTO workerDTO) {
        var newWorker = mapper.mapToWorker(workerDTO);
        var newWorkerFlattened = mapper.mapToWorkerFlattened(service.addNewWorker(newWorker));
        return new ResponseEntity<>(newWorkerFlattened, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<WorkerFlattened> updateWorker(@RequestBody WorkerDTO workerDTO) {
        var workerToUpdate = mapper.mapToWorker(workerDTO);
        var updatedWorkerFlattened = mapper.mapToWorkerFlattened(service.updateWorker(workerToUpdate));
        return new ResponseEntity<>(updatedWorkerFlattened, HttpStatus.OK);
    }

    @DeleteMapping("/{workerId}")
    @PreAuthorize("hasAnyRole('AUTHORIZED_USER', 'ADMINISTRATOR')")
    public ResponseEntity<WorkerFlattened> deleteWorker(@PathVariable Long workerId) {
        return new ResponseEntity<>(mapper.mapToWorkerFlattened(service.deleteWorker(workerId)), HttpStatus.OK);
    }
}
