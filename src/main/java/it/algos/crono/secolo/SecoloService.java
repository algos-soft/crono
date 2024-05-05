package it.algos.crono.secolo;

import it.algos.vbase.backend.boot.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.exception.*;
import it.algos.vbase.backend.logic.*;
import it.algos.vbase.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: sab, 04-mag-2024
 * Time: 19:59
 */
@Service
public class SecoloService extends ModuloService {

    @Value("${algos.project.crea.directory.crono}")
    private String creaDirectoryCronoTxt;

    public static final String INIZIO = "inizio";

    public static final String FINE = "fine";

    public static final String CRISTO = "dopoCristo";


    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public SecoloService() {
        super(SecoloEntity.class, SecoloView.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param ordine     di presentazione nel popup/combobox (obbligatorio, unico)
     * @param code       descrittivo e visualizzabile
     * @param inizio     primo anno del secolo
     * @param fine       ultimo anno del secolo
     * @param dopoCristo secolo prima o dopo Cristo
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public SecoloEntity newEntity(final int ordine, final String code, final int inizio, final int fine, final boolean dopoCristo) {
        SecoloEntity newEntityBean = SecoloEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .code(textService.isValid(code) ? code : null)
                .inizio(inizio)
                .fine(fine)
                .dopoCristo(dopoCristo)
                .build();

        return newEntityBean;
    }

    @Override
    //casting only dalla superclasse
    public List<SecoloEntity> findAll() {
        return super.findAll();
    }


    @Override
    //casting only dalla superclasse
    public SecoloEntity findByKey(final Object keyPropertyValue) {
        return (SecoloEntity) super.findByKey(keyPropertyValue);
    }

    /**
     * Seleziona un secolo dal field 'nome' dell'anno (String) <br>
     *
     * @param nomeAnno indicato per la selezione del secolo
     *
     * @return secolo selezionato
     */
    public SecoloEntity getSecolo(String nomeAnno) {
        int annoInt;
        if (textService.isEmpty(nomeAnno)) {
            return null;
        }

        if (nomeAnno.endsWith(ANNI_AC)) {
            nomeAnno = textService.levaCoda(nomeAnno, ANNI_AC);
            annoInt = Integer.parseInt(nomeAnno);
            return getSecoloAC(annoInt);
        }
        else {
            annoInt = Integer.parseInt(nomeAnno);
            return getSecoloDC(annoInt);
        }
    }

    public SecoloEntity getSecoloAC(final int annoInt) {
        return getSecolo(annoInt, false);
    }

    public SecoloEntity getSecoloDC(final int annoInt) {
        return getSecolo(annoInt, true);
    }

    /**
     * Seleziona un secolo dal field 'ordine' dell'anno (int) <br>
     * L'ordine degli anni inizia con 1 per l'anno 1000 a.C. <br>
     * Gli anni totali sono -convenzionalmente- 3030 <br>
     *
     * @param annoInt indicato per la selezione del secolo
     *
     * @return secolo selezionato
     */
    public SecoloEntity getSecolo(final int annoInt, boolean dopoCristo) {
        Query query = new Query();
        String collectionName = annotationService.getCollectionName(SecoloEntity.class);

        if (dopoCristo) {
            query.addCriteria(Criteria.where(INIZIO).lte(annoInt));
            query.addCriteria(Criteria.where(FINE).gte(annoInt));
            query.addCriteria(Criteria.where(CRISTO).is(dopoCristo));
        }
        else {
            query.addCriteria(Criteria.where(INIZIO).gte(annoInt));
            query.addCriteria(Criteria.where(FINE).lte(annoInt));
            query.addCriteria(Criteria.where(CRISTO).is(dopoCristo));
        }

        return mongoService.mongoTemplate.findOne(query, SecoloEntity.class, collectionName);
    }



    @Override
    public RisultatoReset reset() {
        String nomeFileCSV = "secoli.csv";
        int ordine;
        String nome;
        int inizio;
        int fine;
        boolean anteCristo;
        String anteCristoText;
        String message;
        SecoloEntity newBean;

        if (!Boolean.parseBoolean(creaDirectoryCronoTxt)) {
            return RisultatoReset.nonCostruito;
        }

        Map<String, List<String>> mappaSource = resourceService.leggeMappa(nomeFileCSV);
        if (mappaSource != null) {
            for (List<String> riga : mappaSource.values()) {
                if (riga.size() == 5) {
                    try {
                        ordine = Integer.decode(riga.get(0));
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb().type(TypeLog.startup));
                        ordine = 0;
                    }
                    nome = riga.get(1);
                    try {
                        inizio = Integer.decode(riga.get(2));
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb().type(TypeLog.startup));
                        inizio = 0;
                    }
                    try {
                        fine = Integer.decode(riga.get(3));
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb().type(TypeLog.startup));
                        fine = 0;
                    }
                    anteCristoText = riga.get(4);
                    anteCristo = anteCristoText.equals("true") || anteCristoText.equals("vero") || anteCristoText.equals("si");
                }
                else {
                    logger.error(new WrapLog().exception(new AlgosException("I dati non sono congruenti")).usaDb().type(TypeLog.startup));
                    return RisultatoReset.nonCostruito;
                }
                nome += anteCristo ? " secolo a.C." : " secolo";

                newBean = newEntity(ordine, nome, inizio, fine, !anteCristo);
                if (newBean != null) {
                    mappaBeans.put(nome, newBean);
                }
                else {
                    message = String.format("La entity %s non è stata salvata", nome);
                    logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
                }
            }
        }
        else {
            message = String.format("Manca il file [%s] nella directory /config o sul server", nomeFileCSV);
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
            return RisultatoReset.nonCostruito;
        }

        mappaBeans.values().stream().forEach(bean -> creaIfNotExists(bean));
        return RisultatoReset.vuotoMaCostruito;
    }


}// end of CrudService class
