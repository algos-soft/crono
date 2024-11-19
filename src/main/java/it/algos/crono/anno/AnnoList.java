package it.algos.crono.anno;

import it.algos.crono.list.CronoList;
import it.algos.vbase.annotation.IList;
import it.algos.vbase.searchfield.CheckBoxSearch;
import it.algos.vbase.searchfield.ComboSearch;
import it.algos.vbase.searchfield.SearchFieldListener;
import it.algos.vbase.service.CriteriaService;
import it.algos.vbase.ui.wrapper.ASpan;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;

import static it.algos.vbase.boot.BaseCost.*;

@Slf4j
@IList(columns = {
        "ordine",
        "nome",
        "secolo",
        "dopoCristo",
        "bisestile"},
        espandiUltimaColonnaVisibile = false,
        sortProperty = "ordine")
public class AnnoList extends CronoList<AnnoEntity> {

    @Autowired
    CriteriaService criteriaService;

    public AnnoList() {
        this(null);
    }

    /**
     * @param parentView che crea questa istanza
     */
    public AnnoList(final AnnoView parentView) {
        super(parentView);
    }

    protected void fixPreferenze() {
        super.readOnly = true;
    }

    @PostConstruct
    public void postConstruct() {
        CheckBoxSearch box = (CheckBoxSearch) getGrid().getSearchField("dopoCristo");
        ComboSearch combo = (ComboSearch) getGrid().getSearchField("secolo");
        combo.setBaseFilter(Criteria.where("dopoCristo").is(true));

        box.addSearchFieldListener(new SearchFieldListener() {
            @Override
            public void doSearch(Object key, Criteria criteria) {
                getCriteria(box);
            }
        });
    }

    public  Criteria getCriteria(CheckBoxSearch box) {
        Criteria comboCriteria = null;

        switch (box.getTriState()) {
            case indeterminate -> {
            }
            case vero -> {
                comboCriteria = Criteria.where("dopoCristo").is(true);

            }
            case falso -> {
                comboCriteria = Criteria.where("dopoCristo").is(false);
            }
        }

        return comboCriteria;
    }

    @Override
    public void fixHeader() {
        headerPlaceHolder.add(ASpan.text(String.format(TEXT_TAVOLA + SPAZIO + TEXT_CODE, "Anno")).verde().bold());

        super.infoCreazione = TEXT_HARD;
        super.infoReset = TEXT_RESET_DELETE;

        headerPlaceHolder.add(ASpan.text("Previsti 1000 anni anteCristo e 2030 dopoCristo").blue().bold());
        headerPlaceHolder.add(ASpan.text(AnnoService.ORDINE).blue().bold());
        headerPlaceHolder.add(ASpan.text("L'anno [zero] non esiste").blue().bold());
    }


}// end of AList class
