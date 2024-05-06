package it.algos.crono.secolo;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.*;
import it.algos.crono.list.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.components.*;
import it.algos.vbase.ui.dialog.*;
import it.algos.vbase.ui.wrapper.*;
import jakarta.annotation.*;
import static org.springframework.beans.factory.config.BeanDefinition.*;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class SecoloList extends CronoList {


    /**
     * @param parentCrudView che crea questa istanza
     */
    public SecoloList(final SecoloView parentCrudView) {
        super(parentCrudView);
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


}// end of CrudList class

