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

    private static final String KEY_NAME = FIELD_NAME_NOME;

    public static final String ORDINE = "Ordinamento a partire dall'anno 1.000 a.C.";

    private List<AnnoEntity> listaBeans = new ArrayList<>();

    @Autowired
    public SecoloService secoloService;


    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public AnnoService() {
        super(AnnoEntity.class, AnnoView.class);
    }

    protected void fixPreferenze() {
        super.collectionNameParent = "secolo";
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


    @Override
    public RisultatoReset reset() {
        String collectionName = annotationService.getCollectionName(AnnoEntity.class);
        String message;

        if (secoloService.count() < 1 && annotationService.usaResetStartup(SecoloEntity.class)) {
            secoloService.reset();
        }
        if (secoloService.count() < 1) {
            message = String.format("Collection [%s] non costruita. Probabilmente manca la collection [%s].", collectionName, collectionNameParent);
            logger.warn(new WrapLog().exception(new AlgosException(message)).type(TypeLog.startup));
            return RisultatoReset.nonCostruito;
            //@todo valutare se 'rompere' il programma
        }

        //--costruisce gli anni prima di cristo partendo da ANTE_CRISTO_MAX che coincide con DELTA_ANNI
        for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
            creaPrima(k);
        }

        //--costruisce gli anni dopo cristo fino all'anno DOPO_CRISTO_MAX
        for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
            creaDopo(k);
        }

        return super.bulkInsertEntities(listaBeans, collectionName);
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
            listaBeans.add(newBean);
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
            listaBeans.add(newBean);
        }
    }

}// end of CrudService class