package it.algos.crono.prova;

import com.vaadin.flow.spring.annotation.*;
import it.algos.vbase.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

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

        super.usaBottoneResetDelete = true;
    }


}// end of CrudList class
