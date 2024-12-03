package it.algos.crono.mese;

import it.algos.crono.list.CronoList;
import it.algos.vbase.annotation.IList;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.boot.BaseCost.*;

@IList(columns = {
        "ordine",
        "nome",
        "giorni",
        "primo",
        "ultimo"},
        sortProperty = "ordine")
public class MeseList extends CronoList<MeseEntity> {


    /**
     * @param parentView che crea questa istanza
     */
    public MeseList(final MeseView parentView) {
        super(parentView);
    }


    @Override
    protected void preInit() {
        super.preInit();
        super.readOnly = true;
    }


    @Override
    public void fixHeader() {

        headerPlaceHolder.add(ASpan.text(String.format(TEXT_TAVOLA + SPAZIO + TEXT_ENUM, "Mese", "Mese")).verde().bold());
        headerPlaceHolder.add(ASpan.text("Previsti 366 giorni per gestire il 29 febbraio degli anni bisestili").blue().bold());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;
    }


}// end of AList class
