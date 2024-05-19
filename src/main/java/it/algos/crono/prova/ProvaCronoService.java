package it.algos.crono.prova;

import it.algos.crono.anno.*;
import it.algos.crono.giorno.*;
import it.algos.crono.mese.*;
import it.algos.crono.secolo.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.logic.*;
import jakarta.annotation.*;
import org.springframework.beans.factory.annotation.*;
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

    @Autowired
    AnnoService annoService;

    @Autowired
    GiornoService giornoService;

    @Autowired
    MeseService meseService;

    @Autowired
    SecoloService secoloService;

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public ProvaCronoService() {
        super(ProvaCronoEntity.class, ProvaCronoView.class);
    }

    /**
     * Performing the initialization in a constructor is not suggested as the state of the UI is not properly set up when the constructor is invoked. <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     * Se viene implementata una sottoclasse, passa di qui per ogni sottoclasse oltre che per questa istanza <br>
     * Se esistono delle sottoclassi, passa di qui per ognuna di esse (oltre a questa classe madre) <br>
     */
    @PostConstruct
    public void postConstruct() {
        AnnoEntity annoId = annoService.findByKey("aprile");
        AnnoEntity annoKey = annoService.findByKey("aprile");

        MeseEntity meseId = meseService.findByKey("aprile");
        MeseEntity meseKey = meseService.findByKey("aprile");

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
    public List<ProvaCronoEntity> findAll() {
        return super.findAll();
    }


    @Override
    public RisultatoReset reset() {
        MeseEntity meseBeanCon;
        MeseEntity meseBeanSenza;
        ProvaCronoEntity newEntityBean;

        if (meseService.count() < 1) {
            return null;
        }

        meseBeanCon = meseService.findByKey("aprile");
        meseBeanSenza = meseService.findByKey("novembre");
        newEntityBean = newEntity("alfa", "lunga", meseBeanCon, meseBeanSenza);
        if (newEntityBean != null) {
            mappaBeans.put("alfa", newEntityBean);
        }

        meseBeanCon = meseService.findByKey("marzo");
        meseBeanSenza = meseService.findByKey("gennaio");
        newEntityBean = newEntity("beta", "aspprossimata", meseBeanCon, meseBeanSenza);
        if (newEntityBean != null) {
            mappaBeans.put("beta", newEntityBean);
        }

        mappaBeans.values().stream().forEach(bean -> creaIfNotExists(bean));
        return RisultatoReset.vuotoMaCostruito;
    }

}// end of CrudService class
