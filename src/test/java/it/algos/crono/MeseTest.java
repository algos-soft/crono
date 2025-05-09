package it.algos.crono;

import it.algos.base.ModuloTest;
import it.algos.crono.mese.MeseEntity;
import it.algos.crono.mese.MeseList;
import it.algos.crono.mese.MeseService;
import it.algos.crono.mese.MeseView;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static it.algos.vbase.boot.BaseCost.*;
import static java.lang.System.out;
import static org.junit.Assert.assertNotNull;

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

    protected List<MeseEntity> listMesi;
    @Autowired
    private MeseService meseService;
    private MeseEntity istanza;

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
        istanza = null;
    }


    protected void printMesiGiorni(List<MeseEntity> listMesi) {
        for (MeseEntity bean : listMesi) {
            printMeseGiorni(bean);
        }
    }

    protected void printMeseGiorni(MeseEntity meseBean) {
        out.print(meseBean.getNome());
        out.print(FORWARD);
        out.print("giorni: ");
        out.print(meseBean.getGiorni());
        out.println(VUOTA);
    }

    protected void printMesiPosizione(List<MeseEntity> listMesi) {
        for (MeseEntity bean : listMesi) {
            printMesePosizione(bean);
        }
    }

    protected void printMesePosizione(MeseEntity meseBean) {
        out.print(meseBean.getNome());
        out.print(FORWARD);
        out.print("da ");
        out.print(meseBean.getPrimo());
        out.print(VUOTA);
        out.print(SPAZIO);
        out.print("a ");
        out.print(meseBean.getUltimo());
        out.println(VUOTA);
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
        @DisplayName("1 - durata")
        void durata() {
            out.println("1 - durata");
            out.println(VUOTA);
            log.info("Tabella durata");

            listMesi = service.findAll();
            assertNotNull(listMesi);
            out.println(VUOTA);
            printMesiGiorni(listMesi);
        }


        @Test
        @Order(2)
        @DisplayName("2 - collocazione")
        void collocazione() {
            out.println("2 - collocazione");
            out.println(VUOTA);
            log.info("Tabella posizionamento come giorni nell'anno");
            log.info("Considerando febbraio con 29 giorni");

            listMesi = service.findAll();
            assertNotNull(listMesi);
            out.println(VUOTA);
            printMesiPosizione(listMesi);
        }
    }


}

