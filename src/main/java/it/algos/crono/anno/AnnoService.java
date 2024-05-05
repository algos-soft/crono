package it.algos.crono.anno;

import it.algos.crono.secolo.*;
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

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 18:52
 */
@Service
public class AnnoService extends ModuloService {

    @Value("${algos.project.crea.directory.crono}")
    private String creaDirectoryCronoTxt;

    @Inject
    public SecoloService secoloModulo;

    @Inject
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
     * @param code       corrente
     * @param secolo     di appartenenza
     * @param dopoCristo flag per gli anni prima/dopo cristo
     * @param bisestile  flag per gli anni bisestili
     *
     * @return la nuova entity appena creata (non salvata e senza keyID)
     */
    public AnnoEntity newEntity(final int ordine, final String code, final SecoloEntity secolo, final boolean dopoCristo, final boolean bisestile) {
        AnnoEntity newEntityBean = AnnoEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .code(textService.isValid(code) ? code : null)
                .secolo(secolo)
                .dopoCristo(dopoCristo)
                .bisestile(bisestile)
                .build();

        return (AnnoEntity) fixKey(newEntityBean);
    }


    @Override
    public RisultatoReset reset() {
        if (!Boolean.parseBoolean(creaDirectoryCronoTxt)) {
            return RisultatoReset.nonCostruito;
        }

        if (secoloModulo.count() < 1) {
            logger.error(new WrapLog().exception(new AlgosException("Manca la collezione [secolo]")).usaDb().type(TypeLog.startup));
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

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));
        return RisultatoReset.vuotoMaCostruito;
    }

    public void creaPrima(int numeroProgressivo) {
        int delta = DELTA_ANNI;
        int numeroAnno = delta - numeroProgressivo + 1;
        int ordine = numeroProgressivo;
        String tagPrima = " a.C.";
        String nome = numeroAnno + tagPrima;
        SecoloEntity secolo = secoloModulo.getSecolo(nome);
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
        SecoloEntity secolo = secoloModulo.getSecolo(nome);
        boolean bisestile = dateService.isBisestile(numeroAnno);
        AnnoEntity newBean;

        newBean = newEntity(ordine, nome, secolo, true, bisestile);
        if (newBean != null) {
            mappaBeans.put(nome, newBean);
        }
    }

}// end of CrudService class