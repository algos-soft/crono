package it.algos.crono.anno;

import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AViewList;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.backend.boot.BaseCost.*;

@AViewList(espandiUltimaColonnaVisibile = false,
        columns = {
                "ordine",
                "nome",
                "secolo.nome",
                "dopoCristo",
                "bisestile"
        },
        sortProperty = "ordine")
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
        super.readOnly = true;
    }


    @Override
    public void fixHeader() {
        headerPlaceHolder.add(ASpan.text(String.format(TEXT_TAVOLA + SPAZIO + TEXT_CODE, "Anno")).verde().bold());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        headerPlaceHolder.add(ASpan.text("Previsti 1000 anni anteCristo e 2030 dopoCristo").blue().bold());
        headerPlaceHolder.add(ASpan.text(AnnoService.ORDINE).blue().bold());
        headerPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }


}// end of AList class
