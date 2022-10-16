package pl.gov.listaobecnosci.freedays;

import pl.gov.listaobecnosci.common.exception.IncorrectDataException;
import pl.gov.listaobecnosci.freedays.entity.Freeday;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class FreedayService implements IFreedayService {

    private final IFreedayRepository repository;

    public List<Freeday> getAllFreedays() {
        return repository.findAll();
    }

    public Freeday addNewFreeday(Freeday freeday) {
        return repository.save(freeday);
    }

    public Freeday updateFreeday(Freeday freeday) {
        var freedayToUpdate = getFreedayById(freeday.getId());
        freedayToUpdate.setDay(freeday.getDay());
        freedayToUpdate.setMonth(freeday.getMonth());
        freedayToUpdate.setNameOfHoliday(freeday.getNameOfHoliday());
        return repository.save(freedayToUpdate);
    }

    public Freeday deleteFreeday(Freeday freeday) {
        repository.delete(freeday);
        return freeday;
    }

    private Freeday getFreedayById(Long freedayId) {
        return repository.findById(freedayId)
                .orElseThrow(() -> new IncorrectDataException("Nie ma takiego święta!!!"));
    }
}
