package it.algos.crono.giorno;

import it.algos.crono.anno.AnnoEntity;
import it.algos.crono.anno.AnnoService;
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
public class GiornoList extends CronoList<GiornoEntity> {


    public GiornoList() {
        this((AView) null);
    }

    public GiornoList(final AView parentView) {
        super(parentView);
    }

    /**
     * Costruttore per creare una list autonoma dalla view
     *
     * @param moduloService specifico di quest modulo
     */
    public GiornoList(final GiornoService moduloService) {
        super(GiornoEntity.class, moduloService);
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
