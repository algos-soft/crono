package it.algos.crono.anno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.crono.list.CronoList;
import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.ui.wrapper.ASpan;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;

import static it.algos.vbase.backend.boot.BaseCost.*;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
@AList(espandiUltimaColonnaVisibile = false)
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
