package it.algos.crono.mese;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Span;
import it.algos.crono.giorno.GiornoEntity;
import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.backend.grid.AGrid;
import it.algos.vbase.ui.dialog.BSpan;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.backend.boot.BaseCost.*;

@AList(espandiUltimaColonnaVisibile = false,
        columns = {
                "ordine",
                "sigla",
                "nome",
                "giorni",
                "primo",
                "ultimo"
        }
)
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


    @Override
    public void fixHeader() {

        headerPlaceHolder.add(ASpan.text(String.format(TEXT_TAVOLA + SPAZIO + TEXT_ENUM, "Mese", "Mese")).verde().bold());
        headerPlaceHolder.add(ASpan.text("Previsti 366 giorni per gestire il 29 febbraio degli anni bisestili").blue().bold());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;
    }

    @Override
    protected void fixGrid(AGrid grid) {
        HeaderRow headerRow = grid.prependHeaderRow();
        headerRow.join(grid.getColumnByKey("primo"), grid.getColumnByKey("ultimo")).setText("Giorni da inizio anno");
    }

}// end of CrudList class
