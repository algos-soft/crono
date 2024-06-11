package it.algos.crono.mese;

import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.MeseEnum;
import it.algos.vbase.backend.enumeration.RisultatoReset;
import it.algos.vbase.backend.logic.ModuloService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vbase.backend.boot.BaseCost.FIELD_NAME_NOME;

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
@Service
public class MeseService extends ModuloService {

    private static final String KEY_NAME = FIELD_NAME_NOME;

    @Value("${algos.project.usa.dir.crono:true}")
    private boolean usaDirCronoTxt;


    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public MeseService() {
        super(MeseEntity.class, MeseView.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param nome (obbligatorio, unico)
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public MeseEntity newEntity(int ordine, String sigla, String nome, int giorni, int primo, int ultimo) {
        MeseEntity newEntityBean = MeseEntity.builder()
                .sigla(textService.isValid(sigla) ? sigla : null)
                .nome(textService.isValid(nome) ? nome : null)
                .giorni(giorni)
                .primo(primo)
                .ultimo(ultimo)
                .build();

        newEntityBean.setOrdine(ordine == 0 ? nextOrdine() : ordine);
        return newEntityBean;
    }

    @Override
    public ObjectId getObjectId(AbstractEntity newEntityBean) {
        return super.getObjectId(((MeseEntity) newEntityBean).getNome());
    }

    @Override
    public MeseEntity findById(final String idStringValue) {
        return (MeseEntity) super.findById(idStringValue);
    }

    public MeseEntity findByKey(final String keyValue) {
        return (MeseEntity) super.findOneByProperty(KEY_NAME, keyValue);
    }

    @Override
    public List<MeseEntity> findAll() {
        return super.findAll();
    }


    @Override
    public RisultatoReset reset() {
        MeseEntity newBean;
        int ordine;
        String sigla;
        String nome;
        int giorni;
        int primo;
        int ultimo = 0;

        if (!usaDirCronoTxt) {
            return RisultatoReset.nonCostruito;
        }

        for (MeseEnum meseEnum : MeseEnum.values()) {
            ordine = meseEnum.ordinal() + 1;
            sigla = meseEnum.getSigla();
            nome = meseEnum.getNome();
            primo = ultimo + 1;
            giorni = meseEnum.getGiorniBisestili();
            ultimo = primo + giorni - 1;
            newBean = newEntity(ordine, sigla, nome, giorni, primo, ultimo);
            if (newBean != null) {
                mappaBeans.put(sigla, newBean);
            }
        }

        mappaBeans.values().stream().forEach(bean -> creaIfNotExists(bean));
        return RisultatoReset.vuotoMaCostruito;
    }

}// end of CrudService class
