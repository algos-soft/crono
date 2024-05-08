package it.algos.crono.prova;

import it.algos.crono.mese.*;
import it.algos.vbase.backend.logic.*;
import org.springframework.stereotype.*;

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


    @Override
    //casting only dalla superclasse
    public ProvaCronoEntity findByCode(final String keyCodeValue) {
        return (ProvaCronoEntity) super.findByCode(keyCodeValue);
    }


}// end of CrudService class
