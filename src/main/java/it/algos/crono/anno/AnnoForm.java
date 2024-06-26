package it.algos.crono.anno;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.spring.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.list.*;
import it.algos.vbase.ui.form.*;
import jakarta.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AnnoForm extends CrudForm {


    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
    public AnnoForm() {
        super();
    }

    //--new entityBean and update existing entityBean
    public AnnoForm(final CrudList parentCrudList, AbstractEntity entityBean, CrudOperation operation) {
        super(parentCrudList, entityBean, operation);
    }


    @PostConstruct
    public void postConstruct() {
        super.fixLabel(FIELD_NAME_ORDINE, "Ordine a partire dal 1.000 a.C.");
    }



}// end of CrudForm class
