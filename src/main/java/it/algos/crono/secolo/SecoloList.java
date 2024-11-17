package it.algos.crono.secolo;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import it.algos.crono.list.CronoList;
import it.algos.vbase.annotation.IList;
import it.algos.vbase.components.BAnchor;
import it.algos.vbase.ui.dialog.BSpan;
import it.algos.vbase.ui.wrapper.ASpan;

import static it.algos.vbase.boot.BaseCost.*;

@IList(columns = {
        "ordine",
        "nome",
        "primo",
        "ultimo",
        "dopoCristo"},
        espandiUltimaColonnaVisibile = false,
        sortProperty = "ordine")
public class SecoloList extends CronoList<SecoloEntity> {

    public SecoloList() {
        this(null);
    }

    /**
     * @param parentView che crea questa istanza
     */
    public SecoloList(final SecoloView parentView) {
        super(parentView);
    }

    protected void fixPreferenze() {
        super.readOnly = true;
    }

    @Override
    public void fixHeader() {
        BAnchor anchor = BAnchor.build(LINK_SERVER_ALGOS + "secoli", textService.setQuadre("algos -> " + "secoli"));
        BSpan testo = BSpan.text(TEXT_TAVOLA + SPAZIO + TEXT_CSV).bold().verde();
        headerPlaceHolder.add(new Span(testo, new Text(SPAZIO), anchor));

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        headerPlaceHolder.add(ASpan.text("Previsti 10 secoli anteCristo e 21 dopoCristo").blue().bold());
        headerPlaceHolder.add(ASpan.text(SecoloService.ORDINE).blue().bold());
        headerPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }

}// end of AList class

