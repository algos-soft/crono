package it.algos.crono;

import it.algos.crono.secolo.SecoloEntity;
import it.algos.crono.secolo.SecoloList;
import it.algos.crono.secolo.SecoloService;
import it.algos.crono.secolo.SecoloView;
import it.algos.vbase.ModuloTest;
import it.algos.vbase.entity.AbstractEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static it.algos.vbase.boot.BaseCost.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
@DisplayName("Modulo Secolo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecoloTest extends ModuloTest {

    @Autowired
    private SecoloService modulo;

    private SecoloEntity secoloBean;

    private List<SecoloEntity> listaSecoli;

    //--anno sorgente (intero)
    //--secolo previsto
    private static Stream<Arguments> anniPrima() {
        return Stream.of(
                Arguments.of(45, "I secolo a.C."),
                Arguments.of(3, "I secolo a.C."),
                Arguments.of(400, "IV secolo a.C."),
                Arguments.of(401, "V secolo a.C."),
                Arguments.of(450, "V secolo a.C."),
                Arguments.of(499, "V secolo a.C."),
                Arguments.of(500, "V secolo a.C."),
                Arguments.of(501, "VI secolo a.C.")
        );
    }

    //--anno sorgente (intero)
    //--secolo previsto
    private static Stream<Arguments> anniDopo() {
        return Stream.of(
                Arguments.of(1845, "XIX secolo"),
                Arguments.of(1800, "XVIII secolo"),
                Arguments.of(1801, "XIX secolo"),
                Arguments.of(1899, "XIX secolo"),
                Arguments.of(1900, "XIX secolo"),
                Arguments.of(1901, "XX secolo")
        );
    }


    //--anno sorgente (intero)
    //--dopo cristo (booleano)
    //--secolo previsto
    private static Stream<Arguments> anni() {
        return Stream.of(
                Arguments.of(45, false, "I secolo a.C."),
                Arguments.of(3, false, "I secolo a.C."),
                Arguments.of(400, false, "IV secolo a.C."),
                Arguments.of(401, false, "V secolo a.C."),
                Arguments.of(450, false, "V secolo a.C."),
                Arguments.of(499, false, "V secolo a.C."),
                Arguments.of(500, false, "V secolo a.C."),
                Arguments.of(501, false, "VI secolo a.C."),
                Arguments.of(1845, true, "XIX secolo"),
                Arguments.of(1800, true, "XVIII secolo"),
                Arguments.of(1801, true, "XIX secolo"),
                Arguments.of(1899, true, "XIX secolo"),
                Arguments.of(1900, true, "XIX secolo"),
                Arguments.of(1901, true, "XX secolo")
        );
    }

    //--anno sorgente (string)
    //--secolo previsto
    private static Stream<Arguments> anni2() {
        return Stream.of(
                Arguments.of("45 a.C.", "I secolo a.C."),
                Arguments.of("3 a.C.", "I secolo a.C."),
                Arguments.of("400 a.C.", "IV secolo a.C."),
                Arguments.of("401 a.C.", "V secolo a.C."),
                Arguments.of("450 a.C.", "V secolo a.C."),
                Arguments.of("499 a.C.", "V secolo a.C."),
                Arguments.of("500 a.C.", "V secolo a.C."),
                Arguments.of("501 a.C.", "VI secolo a.C."),
                Arguments.of("1845", "XIX secolo"),
                Arguments.of("1800", "XVIII secolo"),
                Arguments.of("1801", "XIX secolo"),
                Arguments.of("1899", "XIX secolo"),
                Arguments.of("1900", "XIX secolo"),
                Arguments.of("1901", "XX secolo")
        );
    }

    //--nome secolo (string)
    private static Stream<Arguments> nome() {
        return Stream.of(
                Arguments.of("X secolo a.C."),
                Arguments.of("IX secolo a.C."),
                Arguments.of("VIII secolo a.C."),
                Arguments.of("VII secolo a.C."),
                Arguments.of("VI secolo a.C."),
                Arguments.of("V secolo a.C."),
                Arguments.of("IV secolo a.C."),
                Arguments.of("III secolo a.C."),
                Arguments.of("II secolo a.C."),
                Arguments.of("I secolo a.C."),
                Arguments.of("I secolo"),
                Arguments.of("II secolo"),
                Arguments.of("II secolo"),
                Arguments.of("IV secolo"),
                Arguments.of("V secolo"),
                Arguments.of("VI secolo"),
                Arguments.of("VII secolo"),
                Arguments.of("VIII secolo"),
                Arguments.of("IX secolo"),
                Arguments.of("X secolo"),
                Arguments.of("XI secolo"),
                Arguments.of("XII secolo"),
                Arguments.of("XIII secolo"),
                Arguments.of("XIV secolo"),
                Arguments.of("XIV secolo"),
                Arguments.of("XV secolo"),
                Arguments.of("XVI secolo"),
                Arguments.of("XVI secolo"),
                Arguments.of("XVII secolo"),
                Arguments.of("XVIII secolo"),
                Arguments.of("XIX secolo"),
                Arguments.of("XX secolo"),
                Arguments.of("XXI secolo")
        );
    }

    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = SecoloEntity.class;
        super.listClazz = SecoloList.class;
        super.viewClazz = SecoloView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentService = modulo;

        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        super.moduloClazz = SecoloService.class;
        super.moduloClazzName = SecoloService.class.getSimpleName();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(101)
    @DisplayName("101 - findAll (ascendente)")
    void findAllAscendente() {
        System.out.println("101 - findAll (ascendente)");
        System.out.println(VUOTA);

        listaSecoli = modulo.findAll();
        assertNotNull(listaSecoli);
        printSecoli(listaSecoli);
    }

    @Test
    @Order(102)
    @DisplayName("102 - findAll (discendente)")
    void findAllReverse() {
        System.out.println("102 - findAll (discendente)");
        System.out.println(VUOTA);

        listaSecoli = modulo.findAllReverse();
        assertNotNull(listaSecoli);
        printSecoli(listaSecoli);
    }

    @Test
    @Order(103)
    @DisplayName("103 - findAllForNome (ascendente)")
    void findAllForNome() {
        System.out.println("103 - findAllForNome (ascendente)");
        System.out.println(VUOTA);

        listaStr = modulo.findAllForNome();
        assertNotNull(listaStr);
        print(listaStr);
    }


    @Test
    @Order(104)
    @DisplayName("104 - findAllForNome (discendente)")
    void findAllForNomeReverse() {
        System.out.println("104 - findAllForNome (discendente)");
        System.out.println(VUOTA);

        listaStr = modulo.findAllForNomeReverse();
        assertNotNull(listaStr);
        print(listaStr);
    }

    @Test
    @Order(110)
    @DisplayName("110 - secolo dell'anno prima di cristo")
    void getSecoloAC() {
        System.out.println("110 - secolo dell'anno prima di cristo");
        System.out.println(VUOTA);

        //--anno sorgente (intero)
        //--secolo previsto
        anniPrima().forEach(this::fixAnniPrima);
    }

    //--anno sorgente (intero)
    //--secolo previsto
    void fixAnniPrima(Arguments arg) {
        Object[] mat = arg.get();
        sorgenteIntero = (int) mat[0];
        previsto = (String) mat[1];

        sorgente = sorgenteIntero + ANNI_AC;
        secoloBean = modulo.getSecolo(sorgente);
        assertNotNull(secoloBean);
        assertEquals(secoloBean.getNome(), previsto);

        message = String.format("%s%s%s", sorgenteIntero, FORWARD, secoloBean.getNome());
        System.out.println(message);
    }


    @Test
    @Order(120)
    @DisplayName("120 - secolo dell'anno dopo cristo")
    void getSecoloDC() {
        System.out.println("120 - secolo dell'anno dopo cristo");
        System.out.println(VUOTA);

        //--anno sorgente (intero)
        //--secolo previsto
        anniDopo().forEach(this::fixAnniDopo);
    }

    //--anno sorgente (intero)
    //--secolo previsto
    void fixAnniDopo(Arguments arg) {
        Object[] mat = arg.get();
        sorgenteIntero = (int) mat[0];
        previsto = (String) mat[1];

        secoloBean = modulo.getSecoloDC(sorgenteIntero);
        assertNotNull(secoloBean);
        assertEquals(secoloBean.getNome(), previsto);

        message = String.format("%s%s%s", sorgenteIntero, FORWARD, secoloBean.getNome());
        System.out.println(message);
    }

    @Test
    @Order(130)
    @DisplayName("130 - secolo dal numero e dal flag")
    void getSecolo2() {
        System.out.println("130 - secolo dal numero e dal flag");
        System.out.println(VUOTA);

        //--anno sorgente (intero)
        //--dopo cristo (booleano)
        //--secolo previsto
        anni().forEach(this::fixAnni);
    }

    //--anno sorgente (intero)
    //--dopo cristo (booleano)
    //--secolo previsto
    void fixAnni(Arguments arg) {
        Object[] mat = arg.get();
        sorgenteIntero = (int) mat[0];
        sorgenteBooleano = (Boolean) mat[1];
        previsto = (String) mat[2];

        secoloBean = modulo.getSecolo(sorgenteIntero, sorgenteBooleano);
        assertNotNull(secoloBean);
        assertEquals(secoloBean.getNome(), previsto);

        message = String.format("%s (%s)%s%s", sorgenteIntero, sorgenteBooleano, FORWARD, secoloBean.getNome());
        System.out.println(message);
    }

    @Test
    @Order(140)
    @DisplayName("140 - secolo dal nome dell'anno")
    void getSecolo() {
        System.out.println("140 - secolo dal nome dell'anno");
        System.out.println(VUOTA);

        //--anno sorgente (stringa)
        //--secolo previsto
        anni2().forEach(this::fixAnni2);
    }


    //--anno sorgente (stringa)
    //--secolo previsto
    void fixAnni2(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];
        previsto = (String) mat[1];

        secoloBean = modulo.getSecolo(sorgente);
        assertNotNull(secoloBean);
        assertEquals(secoloBean.getNome(), previsto);

        message = String.format("%s%s%s", sorgente, FORWARD, secoloBean.getNome());
        System.out.println(message);
    }


    @Test
    @Order(150)
    @DisplayName("150 - secolo dal nome")
    void findByKey() {
        System.out.println("150 - secolo dal nome");
        System.out.println(VUOTA);

        //--nome secolo (string)
        nome().forEach(this::fixNome);
    }


    //--nome secolo (string)
    void fixNome(Arguments arg) {
        Object[] mat = arg.get();
        sorgente = (String) mat[0];

        secoloBean = modulo.findByKey(sorgente);
        assertNotNull(secoloBean);

        message = String.format("%s%s%s", sorgente, FORWARD, secoloBean.getNome());
        System.out.println(message);
    }

    protected void printSecoli(List<SecoloEntity> listaBeans) {
        int k = 0;

        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof SecoloEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean);
                System.out.println();
            }
        }
    }

}
