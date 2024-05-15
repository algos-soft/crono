package it.algos.crono.prova;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vbase.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

import java.util.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class ProvaCronoList extends CrudList {


    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
    public ProvaCronoList() {
        super();
    }

    /**
     * @param parentCrudView che crea questa istanza
     */
    public ProvaCronoList(final ProvaCronoView parentCrudView) {
        super(parentCrudView);
    }


    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();
        //        Object alfa = moduloModuloService.findByCode("alfa");
        List lista = new ArrayList();
        lista.add(23);
        lista.add("mario");
        lista.add(78);
        Object alfa = lista.getFirst();
        Object beta = lista.getLast();

        super.usaBottoneResetDelete = true;
    }


}// end of CrudList class
