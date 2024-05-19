package it.algos.crono.anno;

import com.vaadin.flow.spring.annotation.*;
import it.algos.crono.list.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.ui.wrapper.*;
import jakarta.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class AnnoList extends CronoList {


    /**
     * @param parentView che crea questa istanza
     */
    public AnnoList(final AnnoView parentView) {
        super(parentView);
    }

    protected void fixPreferenze() {
        super.fixPreferenze();
        super.basicSort = Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE);
    }


    @Override
    public void syncHeader() {
        String service = "Anno";

        super.infoScopo = String.format(TEXT_TAVOLA + SPAZIO + TEXT_CODE, service); ;
        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        super.fixHeaderPost();
        headerPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }

    @PostConstruct
    public void postConstruct() {
        super.fixWidth(FIELD_NAME_ORDINE,6);
    }

}// end of CrudList class
