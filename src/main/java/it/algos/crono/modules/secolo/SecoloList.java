package it.algos.crono.modules.secolo;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.backend.list.*;
import it.algos.vbase.ui.dialog.*;
import it.algos.vbase.ui.wrapper.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class SecoloList extends CrudList {

    private IndeterminateCheckbox boxCristo;

    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
    public SecoloList() {
        super();
    }

    /**
     * @param parentCrudView che crea questa istanza
     */
    public SecoloList(final SecoloView parentCrudView) {
        super(parentCrudView);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
    }


    @Override
    public void syncHeader() {
        String link = "secoli";
        BAnchor anchor = BAnchor.build(LINK_SERVER_ALGOS + link, textService.setQuadre("algos -> " + link));
        BSpan testo = BSpan.text(TEXT_TAVOLA + SPAZIO + TEXT_CSV).bold().verde();
        headerPlaceHolder.add(new Span(testo, new Text(SPAZIO), anchor));

        super.infoScopo = VUOTA;
        super.infoCreazione = TEXT_NEWS;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeaderPost();
        headerPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }


    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        boxCristo = new IndeterminateCheckbox();
        boxCristo.setLabel("DopoCristo");
        boxCristo.setIndeterminate(false);
        boxCristo.setValue(true);
        boxCristo.addValueChangeListener(event -> sync());
        HorizontalLayout layout1 = new HorizontalLayout(boxCristo);
        layout1.setAlignItems(Alignment.CENTER);
        topPlaceHolder.add(layout1);
    }

    @Override
    protected void syncFiltri() {
        if (boxCristo != null && !boxCristo.isIndeterminate()) {
            filtri.uguale("dopoCristo", boxCristo.getValue());
        }
        else {
            filtri.remove("dopoCristo");
        }
    }

}// end of CrudList class

