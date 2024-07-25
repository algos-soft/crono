package it.algos.crono.mese;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;
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
public class MeseList extends CronoList {

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


}// end of CrudList class
