package it.algos.crono.mese;

import it.algos.crono.logic.CronoService;
import it.algos.vbase.enumeration.MeseEnum;
import it.algos.vbase.enumeration.RisultatoReset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vbase.boot.BaseCost.FIELD_NAME_NOME;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Sun, 05-Nov-2023
 * Time: 18:38
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 * Mantiene lo stato della entityClazz <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, esiste già @Service) <br>
 */
@Slf4j
@Service
public class MeseService extends CronoService<MeseEntity> {

    private static final String KEY_NAME = FIELD_NAME_NOME;

    private  List<MeseEntity> listaBeans = new ArrayList<>();

    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public MeseService() {
        super(MeseEntity.class, MeseView.class);
    }

    protected void fixPreferenze() {
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome (obbligatorio, unico)
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public MeseEntity newEntity(int ordine, String sigla, String nome, int giorni, int primo, int ultimo) {
        MeseEntity newEntityBean = MeseEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .sigla(textService.isValid(sigla) ? sigla : null)
                .nome(textService.isValid(nome) ? nome : null)
                .giorni(giorni)
                .primo(primo)
                .ultimo(ultimo)
                .build();

        return newEntityBean;
    }


    @Override
    public RisultatoReset reset() {
        String collectionName = annotationService.getCollectionName(MeseEntity.class);
        MeseEntity newBean;
        int ordine;
        String sigla;
        String nome;
        int giorni;
        int primo;
        int ultimo = 0;

        for (MeseEnum meseEnum : MeseEnum.values()) {
            ordine = meseEnum.ordinal() + 1;
            sigla = meseEnum.getSigla();
            nome = meseEnum.getNome();
            primo = ultimo + 1;
            giorni = meseEnum.getGiorniBisestili();
            ultimo = primo + giorni - 1;
            newBean = newEntity(ordine, sigla, nome, giorni, primo, ultimo);
            if (newBean != null) {
                listaBeans.add(newBean);
            }
        }

        return super.bulkInsertEntities(listaBeans, collectionName);
    }


}// end of CrudService class
