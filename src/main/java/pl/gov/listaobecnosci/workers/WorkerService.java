package pl.gov.listaobecnosci.workers;

import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class WorkerService implements IWorkerService {

    private final IWorkerRepository repository;

    public List<Worker> getAllWorkersBySectionName(String sectionName) {
        return repository.findAllBySectionName(sectionName);
    }

    public Worker addNewWorker(Worker worker) {
        return repository.save(worker);
    }

    public Worker updateWorker(Worker worker) {
        var workerToUpdate = getWorkerById(worker.getId());
        workerToUpdate.setFunction(worker.getFunction());
        workerToUpdate.setName(worker.getName());
        workerToUpdate.setSection(worker.getSection());
        workerToUpdate.setSurname(worker.getSurname());
        return repository.save(workerToUpdate);
    }

    public Worker deleteWorker(Worker worker) {
        repository.delete(worker);
        return worker;
    }

    private Worker getWorkerById(Long workerId) {
        return repository.findById(workerId)
                .orElseThrow(() -> new IncorrectDataException("Nie ma takiego pracownika!!!"));
    }
}
