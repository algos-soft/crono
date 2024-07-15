package it.algos.crono.giorno;

import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.crono.list.*;
import it.algos.crono.mese.*;
import static it.algos.vbase.backend.boot.BaseCost.*;

import it.algos.vbase.backend.annotation.AList;
import org.springframework.beans.factory.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;

@AList()
public class GiornoList extends CronoList {


    /**
     * @param parentView che crea questa istanza
     */
    public GiornoList(final GiornoView parentView) {
        super(parentView);
    }


    @Override
    public void fixHeader() {
        String service = "Giorno";

        super.infoScopo = String.format(TEXT_TAVOLA + SPAZIO + TEXT_CODE, service); ;
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeaderPost();
    }



}// end of CrudList class
