package it.algos.crono.mese;

import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;

import static it.algos.vbase.backend.boot.BaseCost.*;

@AList()
public class MeseList extends CronoList {


    /**
     * @param parentView che crea questa istanza
     */
    public MeseList(final MeseView parentView) {
        super(parentView);
    }


    @Override
    public void fixHeader() {
        String enumeration = "Mese";

        super.infoScopo = String.format(TEXT_TAVOLA + SPAZIO + TEXT_ENUM, enumeration, enumeration);
        ;
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeaderPost();
    }


}// end of CrudList class
