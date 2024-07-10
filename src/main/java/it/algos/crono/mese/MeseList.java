package it.algos.crono.mese;

import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.backend.searchfield.QuickSearch;
import it.algos.vbase.backend.searchfield.TextSearch;
import jakarta.annotation.PostConstruct;

import static it.algos.vbase.backend.boot.BaseCost.*;

@AList()
public class MeseList extends CronoList {


    /**
     * @param parentView che crea questa istanza
     */
    public MeseList(final MeseView parentView) {
        super(parentView);
    }

    @PostConstruct
    private void init() {

        getListToolbar().add(new QuickSearch<>(grid));

        grid.addSearchField(new TextSearch(entityClass, "sigla", annotationService.typeTextSearch(entityClass, "sigla")));
        grid.addSearchField(new TextSearch(entityClass, "nome"));
    }

    @Override
    public void syncHeader() {
        String enumeration = "Mese";

        super.infoScopo = String.format(TEXT_TAVOLA + SPAZIO + TEXT_ENUM, enumeration, enumeration);
        ;
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeaderPost();
    }


}// end of CrudList class
