package it.algos.crono.prova;

import it.algos.crono.mese.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.logic.*;
import org.springframework.stereotype.*;

import javax.inject.*;
import java.util.*;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: mer, 08-mag-2024
 * Time: 08:49
 */
@Service
public class ProvaCronoService extends ModuloService {

    @Inject
    MeseService meseService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ProvaCronoService() {
        super(ProvaCronoEntity.class, ProvaCronoView.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param code        (obbligatorio)
     * @param descrizione (facoltativa)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public ProvaCronoEntity newEntity(String code, String descrizione, MeseEntity meseCon, MeseEntity meseSenza) {
        ProvaCronoEntity newEntityBean = ProvaCronoEntity.builder()
                .code(textService.isValid(code) ? code : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .meseCon(meseCon)
                .meseSenza(meseSenza)
                .build();

        return newEntityBean;
    }

    @Override
    //casting only dalla superclasse
    public List<ProvaCronoEntity> findAll() {
        return super.findAll();
    }


//    @Override
//    //casting only dalla superclasse
//    public ProvaCronoEntity findByCode(final String keyCodeValue) {
//        return (ProvaCronoEntity) super.findByCode(keyCodeValue);
//    }
//
    @Override
    public RisultatoReset reset() {
        MeseEntity meseBeanCon;
        MeseEntity meseBeanSenza;
        ProvaCronoEntity newEntityBean;

//        meseBeanCon = meseService.findByCode("aprile");
//        meseBeanSenza = meseService.findByCode("novembre");
//        newEntityBean = newEntity("alfa", "lunga", meseBeanCon, meseBeanSenza);
//        if (newEntityBean != null) {
//            mappaBeans.put("alfa", newEntityBean);
//        }

//        meseBeanCon = meseService.findByCode("marzo");
//        meseBeanSenza = meseService.findByCode("gennaio");
//        newEntityBean = newEntity("beta", "aspprossimata", meseBeanCon, meseBeanSenza);
//        if (newEntityBean != null) {
//            mappaBeans.put("beta", newEntityBean);
//        }

        mappaBeans.values().stream().forEach(bean -> creaIfNotExists(bean));
        return RisultatoReset.vuotoMaCostruito;
    }

}// end of CrudService class
