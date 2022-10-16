package pl.gov.listaobecnosci.freedays;

import pl.gov.listaobecnosci.freedays.entity.Freeday;

import java.util.List;

interface IFreedayService {

    List<Freeday> getAllFreedays();

    Freeday addNewFreeday(Freeday freeday);

    Freeday updateFreeday(Freeday freeday);

    Freeday deleteFreeday(Freeday freeday);
}
