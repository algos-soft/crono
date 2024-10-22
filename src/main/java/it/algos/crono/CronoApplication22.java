package it.algos.crono;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.theme.Theme;
import it.algos.vbase.backend.boot.BaseBoot;
import it.algos.vbase.backend.enumeration.TypeLog;
import it.algos.vbase.backend.service.LoggerService;
import it.algos.vbase.backend.wrapper.WrapLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point of the Spring Boot application..
 */
@Push
@EnableScheduling
@EnableVaadin(value = "it.algos")
@SpringBootApplication(scanBasePackages = {"it.algos"})
@Theme(value = "crono")
public class CronoApplication22 implements AppShellConfigurator {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    protected LoggerService logger;

    @Value("${algos.project.boot.qualifier}")
    private String bootClazzQualifier;


    public static void main(String[] args) {
        SpringApplication.run(CronoApplication22.class, args);
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

//    @Bean
//    @Autowired
//    public VaadinServiceInitListener vaadinServiceInitListener(@Value("${algos.project.modulo}") String projectModulo) {
//        return new VaadinServiceInitListener() {
//            @Override
//            public void serviceInit(ServiceInitEvent event) {
//                event.getSource().addUIInitListener(uiEvent -> {
//                    UI ui = uiEvent.getUI();
//                    ui.access((Command) () -> {
////                        ui.navigate("x");
//                    });
//                });
//            }
//        };
//    }
}
