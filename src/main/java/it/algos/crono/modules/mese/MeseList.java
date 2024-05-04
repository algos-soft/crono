package it.algos.crono.modules.mese;

import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.list.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class MeseList extends CrudList {



    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
    public MeseList() {
        super();
    }

    /**
     * @param parentCrudView che crea questa istanza
     */
    public MeseList(final MeseView parentCrudView) {
        super(parentCrudView);
    }


    @Override
    public void syncHeader() {
        String enumeration = "Mese";

        super.infoScopo = String.format(TEXT_TAVOLA + SPAZIO + TEXT_ENUM, enumeration, enumeration); ;
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeaderPost();
    }


}// end of CrudList class
