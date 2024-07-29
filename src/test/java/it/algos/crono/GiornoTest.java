package it.algos.crono;

import it.algos.crono.giorno.GiornoEntity;
import it.algos.crono.giorno.GiornoList;
import it.algos.crono.giorno.GiornoService;
import it.algos.crono.giorno.GiornoView;
import it.algos.crono.mese.MeseEntity;
import it.algos.crono.mese.MeseList;
import it.algos.crono.mese.MeseService;
import it.algos.crono.mese.MeseView;
import it.algos.vbase.ModuloTest;
import it.algos.vbase.backend.entity.AbstractEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

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
@DisplayName("Modulo Giorno")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoTest extends ModuloTest {

    @Autowired
    private GiornoService modulo;

    private GiornoEntity giornoBean;



    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = GiornoEntity.class;
        super.listClazz = GiornoList.class;
        super.viewClazz = GiornoView.class;

        //--reindirizzo l'istanza della superclasse
        super.currentModulo = modulo;

        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        super.moduloClazz = GiornoService.class;
        super.moduloClazzName = GiornoService.class.getSimpleName();
    }

    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }





    @Test
    @Order(150)
    @DisplayName("150 - giorno dal nome")
    void findByKey() {
        System.out.println(("150 - giorno dal nome"));
        System.out.println(VUOTA);

        List<HashMap<String, Object>> lista= dateService.getAllGiorni();
        for (HashMap<String, Object> mappaGiorno : lista) {

            sorgente = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
            giornoBean = modulo.findByKey(sorgente);
                    assertNotNull(giornoBean);
            message = String.format("%s%s%s", sorgente, FORWARD, giornoBean.getNome());
            System.out.println(message);
        }

    }







    protected void printBeans(List<AbstractEntity> listaBeans) {
        int k = 0;

        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof GiornoEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean);
                System.out.println();
            }
        }
    }

}
