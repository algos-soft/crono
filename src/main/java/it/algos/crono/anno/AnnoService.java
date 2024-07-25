package it.algos.crono.anno;

import it.algos.crono.secolo.SecoloEntity;
import it.algos.crono.secolo.SecoloService;
import it.algos.vbase.backend.enumeration.RisultatoReset;
import it.algos.vbase.backend.enumeration.TypeLog;
import it.algos.vbase.backend.exception.AlgosException;
import it.algos.vbase.backend.logic.ModuloService;
import it.algos.vbase.backend.service.DateService;
import it.algos.vbase.backend.wrapper.WrapLog;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vbase.backend.boot.BaseCost.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 18:52
 */
@Service
public class AnnoService extends ModuloService<AnnoEntity> {

    private static final String KEY_NAME = FIELD_NAME_NOME;

    public static final String ORDINE = "Ordinamento a partire dal 1.000 a.C.";


//    @Value("${algos.project.usa.dir.crono:true}")
//    private boolean usaDirCrono;

    @Autowired
    public SecoloService secoloService;

    @Autowired
    public DateService dateService;

    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public AnnoService() {
        super(AnnoEntity.class, AnnoView.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Usa il @Builder di Lombok <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine     di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome       corrente
     * @param secolo     di appartenenza
     * @param dopoCristo flag per gli anni prima/dopo cristo
     * @param bisestile  flag per gli anni bisestili
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AnnoEntity newEntity(final int ordine, final String nome, final SecoloEntity secolo, final boolean dopoCristo, final boolean bisestile) {
        AnnoEntity newEntityBean = AnnoEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .secolo(secolo)
                .dopoCristo(dopoCristo)
                .bisestile(bisestile)
                .build();

        return newEntityBean;
    }

//    @Override
//    public ObjectId getObjectId(AnnoEntity newEntityBean) {
//        return null;
//    }
//
//    @Override
//    public AnnoEntity findById(final String idStringValue) {
//        return (AnnoEntity) super.findById(idStringValue);
//    }
//
//    public AnnoEntity findByKey(final String keyValue) {
//        return (AnnoEntity) super.findOneByProperty(KEY_NAME, keyValue);
//    }

//    @Override
//    public List<AnnoEntity> findAll() {
//        return super.findAll();
//    }


    @Override
    public RisultatoReset reset() {
        String collectionName = annotationService.getCollectionName(AnnoEntity.class);
        String collectionNameParent = annotationService.getCollectionName(SecoloEntity.class);

        if (secoloService.count() < 1 && annotationService.usaResetStartup(SecoloEntity.class)) {
            secoloService.reset();
        }
        if (secoloService.count() < 1) {
            String message = String.format("Collection [%s] non costruita. Probabilmente manca la collection [%s].", collectionName, collectionNameParent);
            logger.warn(new WrapLog().exception(new AlgosException(message)).type(TypeLog.startup));
            return RisultatoReset.nonCostruito;
        }

        //--costruisce gli anni prima di cristo partendo da ANTE_CRISTO_MAX che coincide con DELTA_ANNI
        for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
            creaPrima(k);
        }

        //--costruisce gli anni dopo cristo fino all'anno DOPO_CRISTO_MAX
        for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
            creaDopo(k);
        }

        mappaBeans.values().stream().forEach(bean -> creaIfNotExists(bean));
        return RisultatoReset.vuotoMaCostruito;
    }

    public void creaPrima(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = delta - numeroProgressivo + 1;
        int ordine = numeroProgressivo;
        String tagPrima = " a.C.";
        String nome = numeroAnno + tagPrima;
        SecoloEntity secolo = secoloService.getSecolo(nome);
        AnnoEntity newBean;

        newBean = newEntity(ordine, nome, secolo, false, false);
        if (newBean != null) {
            mappaBeans.put(nome, newBean);
        }
    }

    public void creaDopo(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = numeroProgressivo;
        int ordine = numeroProgressivo + delta;
        String nome = numeroProgressivo + VUOTA;
        SecoloEntity secolo = secoloService.getSecolo(nome);
        boolean bisestile = dateService.isBisestile(numeroAnno);
        AnnoEntity newBean;

        newBean = newEntity(ordine, nome, secolo, true, bisestile);
        if (newBean != null) {
            mappaBeans.put(nome, newBean);
        }
    }

}// end of CrudService class