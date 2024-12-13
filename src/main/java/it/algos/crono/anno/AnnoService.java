package it.algos.crono.anno;

import it.algos.crono.logic.CronoService;
import it.algos.crono.secolo.SecoloEntity;
import it.algos.crono.secolo.SecoloService;
import it.algos.vbase.enumeration.RisultatoReset;
import it.algos.vbase.enumeration.TypeLog;
import it.algos.vbase.exception.AlgosException;
import it.algos.vbase.wrapper.WrapLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vbase.boot.BaseCost.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 18:52
 */
@Slf4j
@Service
public class AnnoService extends CronoService<AnnoEntity> {


    public static final String ORDINE = "Ordinamento a partire dall'anno 1.000 a.C.";

    @Autowired
    public SecoloService secoloService;


    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public AnnoService() {
        super(AnnoEntity.class);
    }

    protected void preInit() {
        super.preInit();
        super.collectionNameParent = "secolo";
    }


    @Override
    public RisultatoReset reset() {
        String collectionName = annotationService.getCollectionName(AnnoEntity.class);
        List<AnnoEntity> listaBeans = new ArrayList<>();
        String message;

        if (secoloService.count() < 1 && annotationService.usaResetStartup(SecoloEntity.class)) {
            secoloService.reset();
        }
        if (secoloService.count() < 1) {
            message = String.format("Collection [%s] non costruita. Probabilmente manca la collection [%s].", collectionName, collectionNameParent);
            log.warn(message);
            return RisultatoReset.nonCostruito;
            //@todo valutare se 'rompere' il programma
        }

        //--costruisce gli anni prima di cristo partendo da ANTE_CRISTO_MAX che coincide con DELTA_ANNI
        for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
            listaBeans.add(creaPrima(k));
        }

        //--costruisce gli anni dopo cristo fino all'anno DOPO_CRISTO_MAX
        for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
            listaBeans.add(creaDopo(k));
        }

        return super.bulkInsertEntitiesDelete(listaBeans);
    }

    public AnnoEntity creaPrima(int numeroProgressivo) {
        AnnoEntity newBean;
        int delta = DELTA_ANNI;
        int numeroAnno = delta - numeroProgressivo + 1;
        int ordine = numeroProgressivo;
        String tagPrima = " a.C.";
        String nome = numeroAnno + tagPrima;
        SecoloEntity secolo = secoloService.getSecolo(nome);

        newBean = AnnoEntity.builder()
                .ordine(ordine)
                .nome(nome)
                .secolo(secolo)
                .dopoCristo(false)
                .bisestile(false)
                .build();


//        newBean = newEntity(ordine, nome, secolo, false, false);
//        if (newBean != null) {
//            listaBeans.add(newBean);
//        }

        return newBean;
    }

    public AnnoEntity creaDopo(int numeroProgressivo) {
        AnnoEntity newBean;
        int delta = DELTA_ANNI;
        int numeroAnno = numeroProgressivo;
        int ordine = numeroProgressivo + delta;
        String nome = numeroProgressivo + VUOTA;
        SecoloEntity secolo = secoloService.getSecolo(nome);
        boolean bisestile = dateService.isBisestile(numeroAnno);

        newBean = AnnoEntity.builder()
                .ordine(ordine)
                .nome(nome)
                .secolo(secolo)
                .dopoCristo(true)
                .bisestile(bisestile)
                .build();

//        newBean = newEntity(ordine, nome, secolo, true, bisestile);
//        if (newBean != null) {
//            listaBeans.add(newBean);
//        }

        return newBean;
    }

}// end of CrudService class