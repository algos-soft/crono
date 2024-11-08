package it.algos.crono;

import it.algos.base.BaseTest;
import it.algos.crono.secolo.SecoloEntity;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.logic.ModuloService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.algos.vbase.boot.BaseCost.VUOTA;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 13:51
 */
@SpringBootTest(classes = {CronoApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("crono")
@DisplayName("Modulo CronoBoot")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CronoBootTest extends BaseTest {


    private SecoloEntity secoloBean;

    private List<SecoloEntity> listaSecoli;

    private List<Class<? extends ModuloService>> listClazzService;

    private List<Class<? extends AbstractEntity>> listClazzEntity;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();

        this.listClazzService = null;
    }


    @Test
    @Order(10)
    @DisplayName("10 - getClazzService")
    void getClazzService() {
        System.out.println("10 - getClazzService");
        System.out.println(VUOTA);
        sorgente = "Service";

//        listClazzService = reflectionService.getClazzService();
//        assertNotNull(listClazzService);
//        ottenuto = listClazzService.size() + VUOTA;
//        message = String.format("Ci sono [%s] classi di tipo %s in questo progetto:", ottenuto, sorgente);
//        System.out.println(message);
//        printClazzService(listClazzService);
    }

    @Test
    @Order(11)
    @DisplayName("11 - getClazzServiceProject")
    void getClazzServiceProject() {
        System.out.println("11 - getClazzServiceProject");
        System.out.println(VUOTA);
        sorgente = "Service";

//        listClazzService = reflectionService.getClazzService(BaseVar.projectModulo);
//        assertNotNull(listClazzService);
//        ottenuto = listClazzService.size() + VUOTA;
//        message = String.format("Ci sono [%s] classi di tipo %s nel progetto/libreria [%s]:", ottenuto, sorgente, BaseVar.projectModulo);
//        System.out.println(message);
//        printClazzService(listClazzService);
    }

    @Test
    @Order(12)
    @DisplayName("12 - getAnnotatedService")
    void getAnnotatedService() {
        System.out.println("12 - getAnnotatedService");
        System.out.println(VUOTA);
        sorgente = "@Service";

//        listaClazz = annotationService.getAnnotatedClasses(Service.class);
//        assertNotNull(listaClazz);
//        ottenuto = listaClazz.size() + VUOTA;
//        message = String.format("Ci sono [%s] classi con l'annotation %s in questo progetto:", ottenuto, sorgente);
//        System.out.println(message);
//        printClazz(listaClazz);
    }

    @Test
    @Order(13)
    @DisplayName("13 - getAnnotatedServiceClasses")
    void getAnnotatedServiceClasses() {
        System.out.println("13 - getAnnotatedServiceClasses");
        System.out.println(VUOTA);
        sorgente = "@Service";

//        listaClazz = annotationService.getAnnotatedServiceClasses();
//        assertNotNull(listaClazz);
//        ottenuto = listaClazz.size() + VUOTA;
//        message = String.format("Ci sono [%s] classi dei Modules con l'annotation %s in questo progetto:", ottenuto, sorgente);
//        System.out.println(message);
//        printClazz(listaClazz);
    }


    @Test
    @Order(20)
    @DisplayName("20 - getClazzEntity")
    void getClazzEntity() {
        System.out.println("20 - getClazzEntity");
        System.out.println(VUOTA);
        sorgente = "AEntity";

//        listClazzEntity = reflectionService.getClazzEntity();
//        assertNotNull(listClazzEntity);
//        ottenuto = listClazzEntity.size() + VUOTA;
//        message = String.format("Ci sono complessivamente [%s] classi di tipo %s in questo progetto/applicazione:", ottenuto, sorgente);
//        System.out.println(message);
//        printClazzEntity(listClazzEntity);
    }

    @Test
    @Order(21)
    @DisplayName("21 - getClazzEntityProject")
    void getClazzEntityProject() {
        System.out.println("21 - getClazzEntityProject");
        System.out.println(VUOTA);
        sorgente = "AEntity";

//        listClazzEntity = reflectionService.getClazzEntity(BaseVar.projectModulo);
//        assertNotNull(listClazzEntity);
//        ottenuto = listClazzEntity.size() + VUOTA;
//        message = String.format("Ci sono [%s] classi di tipo %s nel progetto/libreria [%s]:", ottenuto, sorgente, BaseVar.projectModulo);
//        System.out.println(message);
//        printClazzEntity(listClazzEntity);
    }

    @Test
    @Order(22)
    @DisplayName("22 - getAnnotatedEntity")
    void getAnnotatedEntity() {
        System.out.println("22 - getAnnotatedEntity");
        System.out.println(VUOTA);
        sorgente = "@AEntity";

//        listaClazz = annotationService.getAnnotatedClasses(IEntity.class);
//        assertNotNull(listaClazz);
//        ottenuto = listaClazz.size() + VUOTA;
//        message = String.format("Ci sono [%s] classi con l'annotation %s in questo progetto/applicazione:", ottenuto, sorgente);
//        System.out.println(message);
//        printClazz(listaClazz);
    }

    @Test
    @Order(23)
    @DisplayName("23 - getAnnotatedEntityClasses")
    void getAnnotatedEntityClasses() {
        System.out.println("23 - getAnnotatedEntityClasses");
        System.out.println(VUOTA);
        sorgente = "AEntity";

//        listaClazz = annotationService.getAnnotatedEntityClasses();
//        assertNotNull(listaClazz);
//        ottenuto = listaClazz.size() + VUOTA;
//        message = String.format("Ci sono [%s] classi di AbstractEntity con l'annotation %s in questo progetto/applicazione:", ottenuto, sorgente);
//        System.out.println(message);
//        printClazz(listaClazz);
    }

    @Test
    @Order(24)
    @DisplayName("24 - classiCheUsanoReset")
    void classiCheUsanoReset() {
        System.out.println("24 - classiCheUsanoReset");
        System.out.println(VUOTA);
        sorgente = "AReset";

//        listaClazz = annotationService.classiCheUsanoReset().orElse(null);
//        if (listaClazz != null) {
//            ottenuto = listaClazz.size() + VUOTA;
//            message = String.format("In questo progetto/applicazione ci sono [%s] classi di tipo Entity che usano l'annotation %s:", ottenuto, sorgente);
//            System.out.println(message);
////            printClazzEntity(listClazzEntity);
//        } else {
//            message = String.format("In questo progetto/applicazione nessuna classe di tipo Entity usa l'annotation %s", sorgente);
//            System.out.println(message);
//        }
    }

}
