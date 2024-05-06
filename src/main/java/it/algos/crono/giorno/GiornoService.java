package it.algos.crono.giorno;

import it.algos.crono.mese.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.boot.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.exception.*;
import it.algos.vbase.backend.logic.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 15:34
 */
@Service
public class GiornoService extends ModuloService {

    @Value("${algos.project.crea.directory.crono}")
    private String creaDirectoryCronoTxt;

    @Inject
    public MeseService meseModulo;

    @Inject
    public DateService dateService;


    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public GiornoService() {
        super(GiornoEntity.class, GiornoView.class);
    }



    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine    di presentazione nel popup/combobox (obbligatorio, unico)
     * @param code      corrente
     * @param mese      di appartenenza
     * @param trascorsi di inizio anno
     * @param mancanti  alla fine dell'anno
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public GiornoEntity newEntity(final int ordine, final String code, final MeseEntity mese, final int trascorsi, final int mancanti) {
        GiornoEntity newEntityBean = GiornoEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .code(textService.isValid(code) ? code : null)
                .mese(mese)
                .trascorsi(trascorsi)
                .mancanti(mancanti)
                .build();

        return (GiornoEntity) fixKey(newEntityBean);
    }


    @Override
    public List<GiornoEntity> findAll() {
        return super.findAll();
    }


    @Override
    public GiornoEntity findByCode(final String keyCodeValue) {
        return (GiornoEntity) super.findByCode(keyCodeValue);
    }

    @Override
    public RisultatoReset reset() {
        int ordine;
        String nome;
        String meseTxt;
        MeseEntity mese;
        int trascorsi;
        int mancanti;
        String message;
        GiornoEntity newBean;
        List<HashMap<String, Object>> lista;

        if (!Boolean.parseBoolean(creaDirectoryCronoTxt)) {
            return RisultatoReset.nonCostruito;
        }

        if (meseModulo.count() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione [mese]")).usaDb().type(TypeLog.startup));
            return RisultatoReset.nonCostruito;
        }

        lista = dateService.getAllGiorni();
        if (lista != null && lista.size() == NUM_GIORNI_ANNO) {
            for (HashMap<String, Object> mappaGiorno : lista) {
                nome = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
                meseTxt = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO);
                mese = (MeseEntity) meseModulo.findOneByCode(meseTxt);
                if (mese == null) {
                    message = String.format("Manca il mese di %s", meseTxt);
                    logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
                }

                ordine = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
                trascorsi = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_NORMALE);
                mancanti = NUM_GIORNI_ANNO - trascorsi;

                newBean = newEntity(ordine, nome, mese, trascorsi, mancanti);
                if (newBean != null) {
                    mappaBeans.put(nome, newBean);
                }
            }
        }

        mappaBeans.values().stream().forEach(bean -> creaIfNotExists(bean));
        return RisultatoReset.vuotoMaCostruito;
    }

}// end of CrudService class
