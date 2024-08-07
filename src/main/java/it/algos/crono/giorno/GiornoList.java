package it.algos.crono.giorno;

import com.vaadin.flow.component.grid.HeaderRow;
import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.backend.grid.AGrid;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.backend.boot.BaseCost.*;

@AList(espandiUltimaColonnaVisibile = false,
        columns = {
                "ordine",
                "nome",
                "mese.nome",
                "trascorsi",
                "mancanti"
        }
)
public class GiornoList extends CronoList<GiornoEntity> {


    public GiornoList() {
        this(null);
    }

    public GiornoList(final GiornoView parentView) {
        super(parentView);
    }


    @Override
    public void fixHeader() {
        headerPlaceHolder.add(ASpan.text(String.format(TEXT_TAVOLA + SPAZIO + TEXT_CODE, "Giorno")).verde().bold());
        headerPlaceHolder.add(ASpan.text("Previsti 366 giorni per gestire il 29 febbraio degli anni bisestili").blue().bold());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;
    }

    @Override
    protected void fixGrid(AGrid grid) {
        HeaderRow headerRow = grid.prependHeaderRow();
        headerRow.join(grid.getColumnByKey("trascorsi"), grid.getColumnByKey("mancanti")).setText("Giorni da inizio anno");
    }


}// end of CrudList class
