package it.algos.crono.anno;

import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.backend.boot.BaseCost.*;

@AList(espandiUltimaColonnaVisibile = false,
        columns = {
                "ordine",
                "nome",
                "secolo.nome",
                "dopoCristo",
                "bisestile"
        }
)
public class AnnoList extends CronoList<AnnoEntity> {

    public AnnoList() {
        this(null);
    }

    /**
     * @param parentView che crea questa istanza
     */
    public AnnoList(final AnnoView parentView) {
        super(parentView);
    }

    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    @Override
    public void fixHeader() {
        String service = "Anno";

        super.infoScopo = String.format(TEXT_TAVOLA + SPAZIO + TEXT_CODE, service); ;
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeaderPost();
        headerPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }


}// end of CrudList class
