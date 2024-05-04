package it.algos.crono.giorno;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.crono.list.*;
import it.algos.crono.mese.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.list.*;
import org.springframework.beans.factory.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class GiornoList extends CronoList {

    @Autowired
    public MeseService meseModulo;

    private ComboBox comboMese;



    /**
     * @param parentCrudView che crea questa istanza
     */
    public GiornoList(final GiornoView parentCrudView) {
        super(parentCrudView);
    }



    @Override
    public void syncHeader() {
        super.infoScopo = String.format(typeList.getInfoScopo(), "Giorno");
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeaderPost();
    }


    /**
     * Aggiunge componenti al Top della Lista <br>
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
     */
    @Override
    protected void fixTop() {
        super.fixTop();

        comboMese = new ComboBox<>();
        comboMese.setPlaceholder( "Mesi...");
        comboMese.setClearButtonVisible(true);
        comboMese.setWidth("12rem");
        comboMese.setItems(meseModulo.findAll());
        comboMese.addValueChangeListener(event -> sync());
        topPlaceHolder.add(comboMese);
    }

    @Override
    protected void syncFiltri() {
        if (comboMese != null) {
            if (comboMese.getValue() != null) {
                if (comboMese.getValue() instanceof MeseEntity mese) {
                    filtri.uguale("mese", mese);
                }
            }
            else {
                filtri.remove("mese");
            }
        }
    }

}// end of CrudList class
