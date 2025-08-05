package it.algos.crono.mese;

import com.vaadin.flow.data.provider.SortDirection;
import it.algos.crono.list.CronoList;
import it.algos.vbase.annotation.IList;
import it.algos.vbase.form.AForm;
import it.algos.vbase.service.ModuloService;
import it.algos.vbase.ui.view.AView;
import it.algos.vbase.ui.wrapper.ASpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static it.algos.vbase.boot.BaseCost.*;

@Component
@IList(columns = {"ordine", "nome", "giorni", "primo", "ultimo"},
        sortProperty = "ordine",
        sortDirection = SortDirection.ASCENDING)
public class MeseList extends CronoList<MeseEntity> {


    /**
     * BaseLista creata dalla view
     *
     * @param parentView che crea questa istanza
     */
    public MeseList(final AView parentView) {
        super(parentView);
    }


    /**
     * BaseLista autonoma dalla view
     *
     * @param moduloService specifico di quest modulo
     */
    public MeseList(final ModuloService moduloService) {
        super(MeseEntity.class, moduloService);
    }


    @Override
    protected void preInit() {
        super.preInit();
        super.readOnly = true;
    }


    @Override
    public void fixHeader() {
        headerPlaceHolder.add(ASpan.text(String.format(TEXT_TAVOLA + SPAZIO + TEXT_ENUM, "Mese", "Mese")).verde().bold());
        headerPlaceHolder.add(ASpan.text("Previsti 366 giorni per gestire il 29 febbraio degli anni bisestili").blue().bold());

        headerPlaceHolder.add(ASpan.text(TEXT_HARD).rosso().bold());
        headerPlaceHolder.add(ASpan.text(TEXT_RESET_DELETE).rosso().bold());
    }


}// end of AList class
