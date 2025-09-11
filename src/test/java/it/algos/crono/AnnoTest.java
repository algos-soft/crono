package it.algos.crono;

import it.algos.crono.anno.AnnoCronoEntity;
import it.algos.crono.anno.AnnoCronoList;
import it.algos.crono.anno.AnnoCronoService;
import it.algos.crono.anno.AnnoCronoView;
import it.algos.vbase.ModuloTest;
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
    private AnnoCronoService service;


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
        assertNotNull(service);
        super.moduloClazz = AnnoCronoService.class;
        super.entityClazz = AnnoCronoEntity.class;
        super.viewClazz = AnnoCronoView.class;
        super.listClazz = AnnoCronoList.class;

        //--reindirizzo l'istanza della superclasse
        super.service = service;

        super.setUpAll();
        super.usaTestDebug = true;
//        super.usaTestResetStartup = true;
//        super.usaTestResetDelete = true;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    //    @Test
    @Order(150)
    @DisplayName("150 - anno dal nome (all validi)")
    void findByKey() {
        System.out.println(("150 - anno dal nome (all validi)"));
        System.out.println(VUOTA);
        AnnoCronoEntity istanza;
        String tagPrima = " a.C.";

        for (int k = 1; k <= ANTE_CRISTO_MAX; k++) {
            String sorgente = DELTA_ANNI - k + 1 + tagPrima;
            istanza = service.findByKey(sorgente);
            assertNotNull(istanza);
            String message = String.format("%s%s%s", sorgente, FORWARD, istanza.getNome());
            System.out.println(message);
        }

        System.out.println(VUOTA);

        for (int k = 1; k <= DOPO_CRISTO_MAX; k++) {
            String sorgente = k + VUOTA;
            istanza = service.findByKey(sorgente);
            assertNotNull(istanza);
            String message = String.format("%s%s%s", sorgente, FORWARD, istanza.getNome());
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
        String sorgente = (String) mat[0];

        AnnoCronoEntity istanza = service.findByKey(sorgente);
        String message = String.format("%s%s%s", sorgente, FORWARD, istanza != null ? istanza.getNome() : NULLO);
        System.out.println(message);
    }


    //    @Test
    @Order(211)
    @DisplayName("211 - reset Check")
    void resetCheck() {
        System.out.println(VUOTA);
        System.out.println("211 - reset Check");
        System.out.println(VUOTA);

        String sorgente = service.getCollectionNameParent();
        String message = String.format("Il reset dell classe [%s] necessita del preliminare reset di [%s]", collectionName, sorgente);
        System.out.println(message);
        message = String.format("Controllo che esista il valore di [%s.%s] nel primo record", collectionName, sorgente);
        System.out.println(message);

        AnnoCronoEntity istanza = service.findAll().get(0);
        AbstractEntity entityBean = istanza.getSecolo();
        assertNotNull(entityBean);
        message = String.format("Nel primo record di anno [%s.%s] esiste il link a [%s]", collectionName, istanza, entityBean);
        System.out.println(message);
    }

    protected void printBeans(List<? extends AbstractEntity> listaBeans) {
        int k = 0;

        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof AnnoCronoEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean);
                System.out.println();
            }
        }
    }

}
