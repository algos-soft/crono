package it.algos.crono.mese;

import it.algos.crono.list.CronoList;
import it.algos.vbase.annotation.IList;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.boot.BaseCost.*;

@IList(espandiUltimaColonnaVisibile = false,
        columns = {"ordine",
                "sigla",
                "nome",
                "giorni",
                "primo",
                "ultimo"},
        sortProperty = "ordine")
public class MeseList extends CronoList<MeseEntity> {

    public MeseList() {
        this(null);
    }

    /**
     * @param parentView che crea questa istanza
     */
    public MeseList(final MeseView parentView) {
        super(parentView);
    }

    protected void fixPreferenze() {
//        super.readOnly = true;
    }


    @Override
    public void fixHeader() {

        headerPlaceHolder.add(ASpan.text(String.format(TEXT_TAVOLA + SPAZIO + TEXT_ENUM, "Mese", "Mese")).verde().bold());
        headerPlaceHolder.add(ASpan.text("Previsti 366 giorni per gestire il 29 febbraio degli anni bisestili").blue().bold());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;
    }

//    @Override
//    protected void fixGrid(AGrid grid) {
//        HeaderRow headerRow = grid.prependHeaderRow();
//        headerRow.join(grid.getColumnByKey("primo"), grid.getColumnByKey("ultimo")).setText("Giorni da inizio anno");
//    }

}// end of AList class
