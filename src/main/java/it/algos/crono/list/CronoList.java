package it.algos.crono.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import it.algos.vbase.backend.annotation.AViewList;
import it.algos.vbase.backend.constant.Bottone;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.grid.AGrid;
import it.algos.vbase.backend.list.AList;
import it.algos.vbase.ui.view.AView;

import java.text.DecimalFormat;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 18:32
 */
@AViewList(bottoni = {Bottone.RESET_DELETE, Bottone.SHOW})
public class CronoList<T extends AbstractEntity> extends AList<T> {

    protected String infoCreazione;

    protected String infoReset;


    public CronoList() {
        this(null);
    }

    /**
     * @param parentView che crea questa istanza
     */
    public CronoList(final AView parentView) {
        super(parentView);
    }

    @Override
    protected Class<? extends AGrid> getGridClass() {
        return CronoGrid.class;
    }

//    @Override
    protected Component buildCounter(int current, int total) {
        String counterString;
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        if (current == total) {
            counterString = String.format("In totale ci sono %s elementi non filtrati", decimalFormat.format(current));
        } else {
            counterString = String.format("Filtrati %s elementi sul totale di %s", decimalFormat.format(current), decimalFormat.format(total));
        }

        Span textSpan = new Span(counterString);
        textSpan.getElement().setProperty("innerHTML", counterString);
        textSpan.getElement().getStyle().set("color", "green");
        textSpan.getElement().getStyle().set("font-weight", "bold");
        textSpan.getElement().getStyle().set("font-size", "0.7em");
        return textSpan;
    }

}



