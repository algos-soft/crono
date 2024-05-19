package it.algos.crono;

import it.algos.crono.anno.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 21:03
 */
@SpringBootTest(classes = {Application.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("modulo")
@DisplayName("Anno Modulo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnoModuloTest extends ModuloServiceTest {

    @Autowired
    private AnnoService modulo;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = AnnoEntity.class;
        super.listClazz = AnnoList.class;
        super.viewClazz = AnnoView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModuloService = modulo;

        super.setUpAll();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof AnnoEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                // System.out.print(bean.code);
                System.out.println();
            }
        }
    }

}
