package it.algos.crono;

import it.algos.crono.mese.MeseEntity;
import it.algos.crono.mese.MeseList;
import it.algos.crono.mese.MeseService;
import it.algos.crono.mese.MeseView;
import it.algos.vbase.entity.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static it.algos.vbase.boot.BaseCost.*;
import static java.lang.System.out;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Nov-2023
 * Time: 13:51
 */
@Slf4j
@SpringBootTest(classes = {CronoApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("crono")
@Tag("modulo")
@DisplayName("Modulo Mese")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseTest extends ModuloTest {

    @Autowired
    private MeseService meseService;


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
        super.moduloClazz = MeseService.class;
        super.entityClazz = MeseEntity.class;
        super.viewClazz = MeseView.class;
        super.listClazz = MeseList.class;

        //--reindirizzo l'istanza della superclasse
        super.service = this.meseService;

        super.setUpAll();
        super.usaTestDebug = true;
        super.usaTestResetStartup = true;
        super.usaTestResetDelete = true;
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }

    @Nested
    @Order(51)
    @Tag("service")
    @DisplayName("51 - serviceMese")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class serviceMese {


        @Test
        @Order(1)
        @DisplayName("1 - count")
        void count() {
            out.println("1 - count");
            out.println(VUOTA);
            log.info("Mesi totali nel database -> [{}]", service.count());
        }
    }


}

