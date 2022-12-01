package pl.gov.listaobecnosci.functions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gov.listaobecnosci.functions.model.Function;

import java.util.List;

@RestController
@RequestMapping("/api/functions")
@RequiredArgsConstructor
public class FunctionController {

    private final FunctionService service;

    @GetMapping
    ResponseEntity<List<Function>> getAllFunctions() {
        return ResponseEntity.ok(service.getAllFunctions());
    }
}
