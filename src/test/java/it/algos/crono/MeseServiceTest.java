package it.algos.crono;

import it.algos.crono.mese.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: sab, 18-mag-2024
 * Time: 22:37
 */
@SpringBootTest(classes = {CronoApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("service")
@DisplayName("Mese Service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseServiceTest extends ModuloServiceTest {

    @Autowired
    private MeseService localModuloService;

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = MeseEntity.class;
        super.listClazz = MeseList.class;
        super.viewClazz = MeseView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModuloService = localModuloService;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

}
