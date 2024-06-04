package it.algos.crono.mese;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;
import org.springframework.context.annotation.Scope;

import static it.algos.vbase.backend.boot.BaseCost.*;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
@AList()
public class MeseList extends CronoList {


    /**
     * @param parentView che crea questa istanza
     */
    public MeseList(final MeseView parentView) {
        super(parentView);
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
