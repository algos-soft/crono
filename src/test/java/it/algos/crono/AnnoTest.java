package it.algos.crono;

import it.algos.crono.anno.AnnoEntity;
import it.algos.crono.anno.AnnoList;
import it.algos.crono.anno.AnnoService;
import it.algos.crono.anno.AnnoView;
import it.algos.vbase.ModuloTest;
import it.algos.vbase.backend.entity.AbstractEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static it.algos.vbase.backend.boot.BaseCost.*;
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
@Tag("modulo")
@DisplayName("Modulo Anno")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnoTest extends ModuloTest {

    @Autowired
    private AnnoService modulo;

    private AnnoEntity annoBean;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = AnnoEntity.class;
        super.listClazz = AnnoList.class;
        super.viewClazz = AnnoView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModulo = modulo;

        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        super.moduloClazz = AnnoService.class;
        super.moduloClazzName = AnnoService.class.getSimpleName();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(150)
    @DisplayName("150 - anno dal nome")
    void findByKey() {
        System.out.println(("150 - anno dal nome"));
        System.out.println(VUOTA);

        String tagPrima = " a.C.";

        for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
            sorgente = DELTA_ANNI - k + 1 + tagPrima;
            annoBean = modulo.findByKey(sorgente);
            assertNotNull(annoBean);
            message = String.format("%s%s%s", sorgente, FORWARD, annoBean.getNome());
            System.out.println(message);
        }

        System.out.println(VUOTA);

        for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
            sorgente = k + VUOTA;
            annoBean = modulo.findByKey(sorgente);
            assertNotNull(annoBean);
            message = String.format("%s%s%s", sorgente, FORWARD, annoBean.getNome());
            System.out.println(message);
        }

    }


    protected void printBeans(List<AbstractEntity> listaBeans) {
        int k = 0;

        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof AnnoEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean);
                System.out.println();
            }
        }
    }

}
