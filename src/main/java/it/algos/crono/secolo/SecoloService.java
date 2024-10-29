package it.algos.crono.secolo;

import it.algos.crono.logic.CronoService;
import it.algos.vbase.enumeration.RisultatoReset;
import it.algos.vbase.enumeration.TypeLog;
import it.algos.vbase.exception.AlgosException;
import it.algos.vbase.wrapper.WrapLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.algos.vbase.boot.BaseCost.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: sab, 04-mag-2024
 * Time: 19:59
 */
@Slf4j
@Service
public class SecoloService extends CronoService<SecoloEntity> {

    private static final String KEY_NAME = FIELD_NAME_NOME;

    public static final String PRIMO = "primo";

    public static final String ULTIMO = "ultimo";

    public static final String CRISTO = "dopoCristo";

    public static final String ORDINE = "Ordinamento a partire dal secolo X a.C.";

    private List<SecoloEntity> listaBeans = new ArrayList<>();

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     */
    public SecoloService() {
        super(SecoloEntity.class, SecoloView.class);
    }

    protected void fixPreferenze() {
        super.keyPropertyName = KEY_NAME;
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param ordine     di presentazione nel popup/combobox (obbligatorio, unico)
     * @param nome       descrittivo e visualizzabile
     * @param primo      primo anno del secolo
     * @param ultimo     ultimo anno del secolo
     * @param dopoCristo secolo prima o dopo Cristo
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public SecoloEntity newEntity(final int ordine, final String nome, final int primo, final int ultimo, final boolean dopoCristo) {
        SecoloEntity newEntityBean = SecoloEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .nome(textService.isValid(nome) ? nome : null)
                .primo(primo)
                .ultimo(ultimo)
                .dopoCristo(dopoCristo)
                .build();

        return newEntityBean;
    }


    public List<SecoloEntity> findAll() {
        return mongoService.findAll(SecoloEntity.class, Sort.by(Sort.Direction.ASC, FIELD_NAME_ORDINE));
    }

    public List<SecoloEntity> findAllReverse() {
        return mongoService.findAll(SecoloEntity.class, Sort.by(Sort.Direction.DESC, FIELD_NAME_ORDINE));
    }


    public List<String> findAllForNome() {
        return findAll().stream().map(secolo -> secolo.getNome()).toList();
    }


    public List<String> findAllForNomeReverse() {
        return findAllReverse().stream().map(secolo -> secolo.getNome()).toList();
    }

    /**
     * Seleziona un secolo dal field 'nome' dell'anno (String) <br>
     *
     * @param nomeAnno indicato per la selezione del secolo
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
        } else {
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
     * @return secolo selezionato
     */
    public SecoloEntity getSecolo(final int annoInt, boolean dopoCristo) {
        Query query = new Query();

        if (dopoCristo) {
            query.addCriteria(Criteria.where(PRIMO).lte(annoInt));
            query.addCriteria(Criteria.where(ULTIMO).gte(annoInt));
            query.addCriteria(Criteria.where(CRISTO).is(dopoCristo));
        } else {
            query.addCriteria(Criteria.where(PRIMO).gte(annoInt));
            query.addCriteria(Criteria.where(ULTIMO).lte(annoInt));
            query.addCriteria(Criteria.where(CRISTO).is(dopoCristo));
        }

        return mongoService.findOne(query, SecoloEntity.class);
    }


    @Override
    public RisultatoReset reset() {
        String nomeFileCSV = "secoli.csv";
        String collectionName = annotationService.getCollectionName(SecoloEntity.class);
        int ordine;
        String nome;
        int inizio2;
        int fine;
        boolean anteCristo;
        String anteCristoText;
        String message;
        SecoloEntity newBean;

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
                        inizio2 = Integer.decode(riga.get(2));
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb().type(TypeLog.startup));
                        inizio2 = 0;
                    }
                    try {
                        fine = Integer.decode(riga.get(3));
                    } catch (Exception unErrore) {
                        logger.error(new WrapLog().exception(unErrore).usaDb().type(TypeLog.startup));
                        fine = 0;
                    }
                    anteCristoText = riga.get(4);
                    anteCristo = anteCristoText.equals("true") || anteCristoText.equals("vero") || anteCristoText.equals("si");
                } else {
                    logger.error(new WrapLog().exception(new AlgosException("I dati non sono congruenti")).usaDb().type(TypeLog.startup));
                    return RisultatoReset.nonCostruito;
                }
                nome += anteCristo ? " secolo a.C." : " secolo";

                newBean = newEntity(ordine, nome, inizio2, fine, !anteCristo);
                if (newBean != null) {
                    listaBeans.add(newBean);
                } else {
                    message = String.format("La entity %s non è stata salvata", nome);
                    logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
                }
            }
        } else {
            message = String.format("Manca il file [%s] nella directory /config o sul server", nomeFileCSV);
            logger.error(new WrapLog().exception(new AlgosException(message)).usaDb().type(TypeLog.startup));
            return RisultatoReset.nonCostruito;
        }

        return super.bulkInsertEntities(listaBeans, collectionName);
    }


}// end of CrudService class
