package it.algos.crono.mese;

import it.algos.crono.logic.CronoService;
import it.algos.vbase.enumeration.MeseEnum;
import it.algos.vbase.enumeration.RisultatoReset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public MeseService() {
        super(MeseEntity.class);
    }


    @Override
    public RisultatoReset reset(MongoTemplate mongoTemplate) {
        List<MeseEntity> listaBeans = new ArrayList<>();
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

            newBean = MeseEntity.builder()
                    .ordine(ordine)
                    .sigla(sigla)
                    .nome(nome)
                    .giorni(giorni)
                    .primo(primo)
                    .ultimo(ultimo)
                    .build();
            listaBeans.add(newBean);

        }

        return super.bulkInsertEntitiesDelete(listaBeans);
    }


}// end of CrudService class
