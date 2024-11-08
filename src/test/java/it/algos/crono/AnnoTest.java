package it.algos.crono;

import it.algos.base.ModuloTest;
import it.algos.crono.anno.AnnoEntity;
import it.algos.crono.anno.AnnoList;
import it.algos.crono.anno.AnnoService;
import it.algos.crono.anno.AnnoView;
import it.algos.vbase.entity.AbstractEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static it.algos.vbase.boot.BaseCost.*;
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
    private AnnoService service;

    private AnnoEntity annoBean;


    //--anno sorgente (string)
    private static Stream<Arguments> anni() {
        return Stream.of(
                Arguments.of("45 a.C."),
                Arguments.of("1801 a.C."),
                Arguments.of("2985 a.C."),
                Arguments.of("2064 a.C.")
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = AnnoEntity.class;
        super.listClazz = AnnoList.class;
        super.viewClazz = AnnoView.class;

        //--reindirizzo l'istanza della superclasse
//        super.currentService = service;

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
    @DisplayName("150 - anno dal nome (all validi)")
    void findByKey() {
        System.out.println(("150 - anno dal nome (all validi)"));
        System.out.println(VUOTA);

        String tagPrima = " a.C.";

        for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
            sorgente = DELTA_ANNI - k + 1 + tagPrima;
            annoBean = service.findByKey(sorgente);
            assertNotNull(annoBean);
            message = String.format("%s%s%s", sorgente, FORWARD, annoBean.getNome());
            System.out.println(message);
        }

        System.out.println(VUOTA);

        for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
            sorgente = k + VUOTA;
            annoBean = service.findByKey(sorgente);
            assertNotNull(annoBean);
            message = String.format("%s%s%s", sorgente, FORWARD, annoBean.getNome());
            System.out.println(message);
        }

    }


    @Test
    @Order(160)
    @DisplayName("160 - anno dal nome")
    void getSecoloAC() {
        System.out.println(("160 - anno dal nome"));
        System.out.println(VUOTA);

        //--anno sorgente (string)
        anni().forEach(this::fixAnni);
    }

    //--anno sorgente (string)
    void fixAnni(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];

        annoBean = service.findByKey(sorgente);
        message = String.format("%s%s%s", sorgente, FORWARD, annoBean != null ? annoBean.getNome() : NULLO);
        System.out.println(message);
    }


    @Test
    @Order(211)
    @DisplayName("211 - reset Check")
    void resetCheck() {
        System.out.println(VUOTA);
        System.out.println("211 - reset Check");
        System.out.println(VUOTA);

        sorgente = service.getCollectionNameParent();
        message = String.format("Il reset dell classe [%s] necessita del preliminare reset di [%s]", collectionName, sorgente);
        System.out.println(message);
        message = String.format("Controllo che esista il valore di [%s.%s] nel primo record", collectionName, sorgente);
        System.out.println(message);

        annoBean = service.findAll().get(0);
        entityBean = annoBean.getSecolo();
        assertNotNull(entityBean);
        message = String.format("Nel primo record di anno [%s.%s] esiste il link a [%s]", collectionName, annoBean,entityBean);
        System.out.println(message);
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
