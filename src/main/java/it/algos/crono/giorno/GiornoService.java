package it.algos.crono.giorno;

import it.algos.crono.logic.CronoService;
import it.algos.crono.mese.MeseEntity;
import it.algos.crono.mese.MeseService;
import it.algos.vbase.enumeration.RisultatoReset;
import it.algos.vbase.enumeration.TypeLog;
import it.algos.vbase.exception.AlgosException;
import it.algos.vbase.service.DateService;
import it.algos.vbase.wrapper.WrapLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class GiornoService extends CronoService<GiornoEntity> {

    private static final String KEY_NAME = FIELD_NAME_NOME;

    public static final String ORDINE = "Ordinamento da inizio anno";

    @Autowired
    public MeseService meseService;

    @Autowired
    public DateService dateService;


    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public GiornoService() {
        super(GiornoEntity.class, GiornoView.class);
        super.collectionNameParent = "mese";
    }


    protected void fixPreferenze() {
        super.keyPropertyName = KEY_NAME;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine    di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome      corrente
     * @param mese      di appartenenza
     * @param trascorsi di inizio anno
     * @param mancanti  alla fine dell'anno
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public GiornoEntity newEntity(final int ordine, final String nome, final MeseEntity mese, final int trascorsi, final int mancanti) {
        GiornoEntity newEntityBean = GiornoEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .mese(mese)
                .trascorsi(trascorsi)
                .mancanti(mancanti)
                .build();

        return newEntityBean;
    }


    @Override
    public RisultatoReset reset() {
        String collectionName = mongoTemplate.getCollectionName(GiornoEntity.class);
        int ordine;
        String nome;
        String meseTxt;
        MeseEntity mese;
        int trascorsi;
        int mancanti;
        String message;
        GiornoEntity newBean;
        List<HashMap<String, Object>> lista;

        if (meseService.count() < 1 && annotationService.usaResetStartup(MeseEntity.class)) {
            meseService.reset();
        }
        if (meseService.count() < 1) {
            message = String.format("Collection [%s] non costruita. Probabilmente manca la collection [%s].", collectionName, collectionNameParent);
            logger.warn(new WrapLog().exception(new AlgosException(message)).type(TypeLog.startup));
            return RisultatoReset.nonCostruito;
            //@todo valutare se 'rompere' il programma
        }

        lista = dateService.getAllGiorni();
        if (lista != null && lista.size() == NUM_GIORNI_ANNO) {
            mappaBeans = new HashMap<>();
            for (HashMap<String, Object> mappaGiorno : lista) {
                nome = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
                meseTxt = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO);
                mese = meseService.findByKey(meseTxt);
                if (mese == null) {
                    break;
                }
                ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
                trascorsi = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_NORMALE);
                mancanti = NUM_GIORNI_ANNO - trascorsi;

                newBean = newEntity(ordine, nome, mese, trascorsi, mancanti);
                mappaBeans.put(nome, newBean);
            }
        }

        if (mappaBeans.size() > 0) {
            mappaBeans.values().stream().forEach(bean -> creaIfNotExists(bean));
            return RisultatoReset.vuotoMaCostruito;
        } else {
            message = String.format("Collection [%s] non costruita. Probabilmente manca la collection [%s].", collectionName, collectionNameParent);
            logger.warn(new WrapLog().exception(new AlgosException(message)).type(TypeLog.startup));
            return RisultatoReset.nonCostruito;
        }


    }

}// end of CrudService class
