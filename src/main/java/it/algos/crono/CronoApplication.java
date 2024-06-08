package it.algos.crono;

import com.vaadin.flow.component.page.*;
import com.vaadin.flow.spring.annotation.*;
import com.vaadin.flow.theme.*;
import it.algos.vbase.backend.boot.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.backend.wrapper.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.scheduling.annotation.*;

/**
 * The entry point of the Spring Boot application.
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"it.algos"})
@EnableVaadin(value = "it.algos")
@Theme(value = "crono")
public class CronoApplication implements AppShellConfigurator {

    //--identifica la specifica classe Boot di questo programma
    private static final String BOOT_CLASS_QUALIFIER = "cronoBoot";

    @Autowired
    ApplicationContext applicationContext;

    @Autowired @Qualifier("baseBoot")
    BaseBoot baseBoot;

    @Autowired
    LoggerService logger;

    BaseBoot projectBoot = null;


    public static void main(String[] args) {
        SpringApplication.run(CronoApplication.class, args);
    }

    /**
     * Questo metodo viene chiamato dopo che Spring Boot
     * ha eseguito tutte le inizializzazioni
     */
    @EventListener(ContextRefreshedEvent.class)
    private void doSomethingAfterStartup() {

        //--intercetta l'errore che altrimenti bloccherebbe il programma
        try {
            projectBoot = (BaseBoot) applicationContext.getBean(BOOT_CLASS_QUALIFIER);
            projectBoot.inizia();
        } catch (BeansException e) {
            //--se non ha trovato la classe di Boot specifica, avvisa in fase di runtime
            String message = String.format("Non ho trovato nessuna classe di Boot col nome [%s]", BOOT_CLASS_QUALIFIER);
            logger.warn(new WrapLog().message(message).type(TypeLog.startup));
            System.out.println(VUOTA);

            //--le funzionalità potrebbero essere potenzialmente limitate (-preferenze, -routes, -demoData)
            //--prosegue comunque il programma col metodo della classe di BaseBoot
            baseBoot.inizia();

        }

    }

}
