package it.algos.crono.secolo;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.backend.components.BAnchor;
import it.algos.vbase.ui.dialog.BSpan;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.backend.boot.BaseCost.*;

@AList(espandiUltimaColonnaVisibile = false,
        columns = {
                "ordine",
                "nome",
                "inizio",
                "fine",
                "dopoCristo"
        }
)
public class SecoloList extends CronoList {

    public SecoloList() {
        this(null);
    }

    /**
     * @param parentView che crea questa istanza
     */
    public SecoloList(final SecoloView parentView) {
        super(parentView);
    }


    @Override
    public void fixHeader() {
        String link = "secoli";
        BAnchor anchor = BAnchor.build(LINK_SERVER_ALGOS + link, textService.setQuadre("algos -> " + link));
        BSpan testo = BSpan.text(TEXT_TAVOLA + SPAZIO + TEXT_CSV).bold().verde();
        headerPlaceHolder.add(new Span(testo, new Text(SPAZIO), anchor));

        super.infoScopo = VUOTA;
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        headerPlaceHolder.add(ASpan.text("Previsti 10 secoli anteCristo e 21 dopoCristo").blue().bold());
        headerPlaceHolder.add(ASpan.text(SecoloService.ORDINE).blue().bold());
        headerPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }


}// end of CrudList class

