package pl.gov.listaobecnosci.workers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.gov.listaobecnosci.functions.Function;
import pl.gov.listaobecnosci.workers.dto.WorkerDTO;
import pl.gov.listaobecnosci.workers.dto.WorkerFlattened;
import pl.gov.listaobecnosci.workers.entity.Worker;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IWorkerMapper {

    IWorkerMapper INSTANCE = Mappers.getMapper(IWorkerMapper.class);

    @Mapping(target = "function", source = "function.description")
    WorkerFlattened mapToWorkerFlattened(Worker worker);

    List<WorkerFlattened> mapToWorkersFlattened(List<Worker> workers);

    Worker mapToWorker(WorkerDTO workerDTO);

    @Mapping(target = "section", ignore = true)
    @Mapping(target = "function", expression = "java(getFunction(workerFlattened.getFunction()))")
    Worker mapToWorkerFromWorkerFlattened(WorkerFlattened workerFlattened);

    WorkerDTO mapToWorkerDTO(Worker worker);

    default Function getFunction(String functionName) {
        return Function.findByKey(functionName);
    }
}
