package it.algos.crono;

import it.algos.base.ModuloTest;
import it.algos.crono.mese.MeseEntity;
import it.algos.crono.mese.MeseList;
import it.algos.crono.mese.MeseService;
import it.algos.crono.mese.MeseView;
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
@DisplayName("Modulo Mese")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseTest extends ModuloTest {

    @Autowired
    private MeseService service;


    //--nome mese (string)
    private static Stream<Arguments> nome() {
        return Stream.of(
                Arguments.of("gennaio"),
                Arguments.of("febbraio"),
                Arguments.of("marzo"),
                Arguments.of("aprile"),
                Arguments.of("maggio"),
                Arguments.of("giugno"),
                Arguments.of("agosto"),
                Arguments.of("settembre"),
                Arguments.of("ottobre"),
                Arguments.of("novembre"),
                Arguments.of("dicembre")
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(service);
        super.moduloClazz = MeseService.class;
        super.entityClazz = MeseEntity.class;
        super.viewClazz = MeseView.class;
        super.listClazz = MeseList.class;

        //--reindirizzo l'istanza della superclasse
        super.moduloService = service;

        super.setUpAll();
        super.usaTestDebug = true;
        super.usaTestResetStartup = true;
        super.usaTestResetDelete = true;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    //    @Test
    @Order(150)
    @DisplayName("150 - mese dal nome")
    void findByKey() {
        System.out.println(("150 - mese dal nome"));
        System.out.println(VUOTA);

        //--nome secolo (string)
//        nome().forEach(this::fixNome);
    }


    //--nome secolo (string)
    void fixNome(Arguments arg) {
        Object[] mat = arg.get();
        String sorgente = (String) mat[0];

        MeseEntity istanza = service.findByKey(sorgente);
        assertNotNull(istanza);

        String message = String.format("%s%s%s", sorgente, FORWARD, istanza.getNome());
        System.out.println(message);
    }


    protected void printBeans(List<AbstractEntity> listaBeans) {
        int k = 0;

        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof MeseEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean);
                System.out.println();
            }
        }
    }

}
