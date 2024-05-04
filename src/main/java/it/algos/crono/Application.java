package it.algos.crono;

import com.vaadin.flow.component.page.*;
import com.vaadin.flow.theme.*;
import it.algos.vbase.backend.boot.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.backend.wrapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;
import org.springframework.context.event.*;
import org.springframework.scheduling.annotation.*;

import javax.inject.*;

/**
 * The entry point of the Spring Boot application.
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"it.algos"})
@Theme(value = "crono")
public class Application implements AppShellConfigurator {

    @Inject
    ApplicationContext applicationContext;

    @Inject
    protected LoggerService logger;

    @Value("${algos.project.boot.qualifier}")
    private String bootClazzQualifier;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Primo ingresso nel programma <br>
     * <p>
     * 1) Prima SpringBoot crea tutte le classi SINGLETON, col metodo constructor() <br>
     * 2) Dopo SpringBoot esegue tutti i metodi con l'annotation @PostConstruct delle classi SINGLETON appena costruite <br>
     * 3) Infine SpringBoot arriva qui <br>
     */
    @EventListener(ContextRefreshedEvent.class)
    private void doSomethingAfterStartup() {
        BaseBoot currentBoot = null;

        try {
            currentBoot = (BaseBoot) applicationContext.getBean(bootClazzQualifier);
        } catch (Exception unErrore) {
            logger.error(new WrapLog().exception(new Exception(unErrore)).type(TypeLog.startup));
        }

        if (currentBoot != null) {
            currentBoot.inizia();
        }
        else {
            String message = String.format("Non ho trovato nessuna classe di Boot di nome [%s]", bootClazzQualifier);
            logger.error(new WrapLog().exception(new Exception(message)).type(TypeLog.startup));
        }
    }

}
