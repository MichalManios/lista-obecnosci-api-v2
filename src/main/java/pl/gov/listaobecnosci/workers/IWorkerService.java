package pl.gov.listaobecnosci.workers;

import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.List;

interface IWorkerService {

    List<Worker> getAllWorkersBySectionName(String sectionName);

    Worker addNewWorker(Worker worker);

    Worker updateWorker(Worker worker);

    Worker deleteWorker(Worker worker);
}
