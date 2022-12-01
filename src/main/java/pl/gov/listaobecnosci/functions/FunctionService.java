package pl.gov.listaobecnosci.functions;

import org.springframework.stereotype.Service;
import pl.gov.listaobecnosci.functions.model.Function;

import java.util.List;

@Service
public class FunctionService {

    public List<Function> getAllFunctions() {
            return List.of(Function.values());
        }
}
