package it.algos.crono.giorno;

import it.algos.crono.list.CronoList;
import it.algos.vbase.annotation.IList;
import it.algos.vbase.ui.view.AView;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.boot.BaseCost.*;

@IList(columns = {
        "ordine",
        "nome",
        "mese.nome",
        "trascorsi",
        "mancanti"},
        sortProperty = "ordine")
public class GiornoCronoList extends CronoList<GiornoCronoEntity> {


    public GiornoCronoList() {
        this((AView) null);
    }

    public GiornoCronoList(final AView parentView) {
        super(parentView);
    }

    /**
     * Costruttore per creare una list autonoma dalla view
     *
     * @param moduloService specifico di quest modulo
     */
    public GiornoCronoList(final GiornoCronoService moduloService) {
        super(GiornoCronoEntity.class, moduloService);
    }

    @Override
    protected void preInit() {
        super.preInit();
        super.readOnly = true;
    }

    @Override
    public void fixHeader() {
        headerPlaceHolder.add(ASpan.text(String.format(TEXT_TAVOLA + SPAZIO + TEXT_CODE, "Giorno")).verde().bold());
        headerPlaceHolder.add(ASpan.text("Previsti 366 giorni per gestire il 29 febbraio degli anni bisestili").blue().bold());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;
    }


}// end of AList class
