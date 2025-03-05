package it.algos.crono.giorno;

import it.algos.crono.logic.CronoService;
import it.algos.crono.mese.MeseEntity;
import it.algos.crono.mese.MeseService;
import it.algos.vbase.enumeration.RisultatoReset;
import it.algos.vbase.enumeration.TypeLog;
import it.algos.vbase.exception.AlgosException;
import it.algos.vbase.wrapper.WrapLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.algos.vbase.boot.BaseCost.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 15:34
 */
@Slf4j
@Service
public class GiornoService extends CronoService<GiornoEntity> {


    public static final String ORDINE = "Ordinamento da inizio anno";

    @Autowired
    public MeseService meseService;

    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public GiornoService() {
        super(GiornoEntity.class);
        super.collectionNameParent = "mese";
    }


    @Override
    public RisultatoReset reset() {
        String collectionName = getMongoTemplate().getCollectionName(GiornoEntity.class);
        List<GiornoEntity> listaBeans = new ArrayList<>();
        GiornoEntity newBean;
        String message;
        int ordine;
        String nome;
        int trascorsi;
        int mancanti;
        String meseTxt;
        MeseEntity mese;
        List<HashMap<String, Object>> lista = dateService.getAllGiorni();

        if (meseService.count() < 1 && annotationService.usaResetStartup(MeseEntity.class)) {
            meseService.reset();
        }

        if (meseService.count() < 1) {
            message = String.format("Collection [%s] non costruita. Probabilmente manca la collection [%s].", collectionName, collectionNameParent);
            log.warn(message);
            return RisultatoReset.nonCostruito;
            //@todo valutare se 'rompere' il programma
        }

        if (lista != null && lista.size() == NUM_GIORNI_ANNO) {
            for (HashMap<String, Object> mappaGiorno : lista) {
                nome = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
                meseTxt = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_SIGLA);
                mese = meseService.findByKey(meseTxt);
                if (mese == null) {
                    break;
                }
                ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
                trascorsi = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_NORMALE);
                mancanti = NUM_GIORNI_ANNO - trascorsi;

                newBean = GiornoEntity.builder()
                        .ordine(ordine)
                        .nome(nome)
                        .mese(mese)
                        .trascorsi(trascorsi)
                        .mancanti(mancanti)
                        .build();
                listaBeans.add(newBean);
            }
        }

        return super.bulkInsertEntitiesDelete(listaBeans);
    }


}// end of CrudService class
