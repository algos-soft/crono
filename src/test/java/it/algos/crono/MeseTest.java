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
        out.println("[nome, giorni]");
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
        out.println("[nome, primo, ultimo]");
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


    protected void printMesiTabella(List<MeseEntity> listMesi) {
        out.println("[sigla, nome, giorni, primo, ultimo]");

        for (MeseEntity bean : listMesi) {
            printMeseTabella(bean);
        }
    }

    protected void printMeseTabella(MeseEntity meseBean) {
        out.print(meseBean.getOrdine());
        out.print(SPAZIO);
        out.print(meseBean.getSigla());
        out.print(SPAZIO);
        out.print(meseBean.getNome());
        out.print(SPAZIO);
        out.print(meseBean.getGiorni());
        out.print(SPAZIO);
        out.print(meseBean.getPrimo());
        out.print(SPAZIO);
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
        @DisplayName("2 - posizionamento")
        void posizionamento() {
            out.println("2 - posizionamento nell'anno");
            out.println(VUOTA);
            log.info("Tabella posizionamento come giorni nell'anno");
            log.info("Considerando febbraio con 29 giorni");

            listMesi = service.findAll();
            assertNotNull(listMesi);
            out.println(VUOTA);
            printMesiPosizione(listMesi);
        }

        @Test
        @Order(3)
        @DisplayName("3 - tabella")
        void tabella() {
            out.println("3 - tabella");
            out.println(VUOTA);
            log.info("Tabella");

            listMesi = service.findAll();
            assertNotNull(listMesi);
            out.println(VUOTA);
            printMesiTabella(listMesi);
        }


        @Test
        @Order(4)
        @DisplayName("4 - sortDatabase")
        void sortDatabase() {
            out.println("4 - sortDatabase");
            out.println("Mesi estratti nell'ordine in cui sono registrati nel database (senza ordine)");

            listaBeans = service.findAll();
            assertNotNull(listMesi);
            printAllBeans(listaBeans);
        }

        @Test
        @Order(5)
        @DisplayName("5 - sortOrdine")
        void sortOrdine() {
            out.println("4 - sortOrdine");
            out.println("Mesi ordinati secondo la property [ordine]");

            listaBeans = meseService.findAllOrdine();
            assertNotNull(listMesi);
            printAllBeans(listaBeans);
        }

        @Test
        @Order(6)
        @DisplayName("6 - sortAlfabetico")
        void sortAlfabetico() {
            out.println("6 - sortAlfabetico");
            out.println("Mesi ordinati secondo la property [nome] - ordine alfabetico");

            listaBeans = meseService.findAllAlfabetici();
            assertNotNull(listMesi);
            printAllBeans(listaBeans);
        }
    }


}

