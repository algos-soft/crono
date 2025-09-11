package it.algos.crono;

import it.algos.crono.giorno.GiornoCronoEntity;
import it.algos.crono.giorno.GiornoCronoList;
import it.algos.crono.giorno.GiornoCronoService;
import it.algos.crono.giorno.GiornoCronoView;
import it.algos.vbase.ModuloTest;
import it.algos.vbase.entity.AbstractEntity;
import org.bson.Document;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@DisplayName("Modulo Giorno")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiornoCronoTest extends ModuloTest {

    @Autowired
    private GiornoCronoService service;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        assertNotNull(service);
        super.moduloClazz = GiornoCronoService.class;
        super.entityClazz = GiornoCronoEntity.class;
        super.viewClazz = GiornoCronoView.class;
        super.listClazz = GiornoCronoList.class;

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
    @Order(1150)
    @DisplayName("1150 - giorno dal nome")
    void findByKey() {
        System.out.println(("1150 - giorno dal nome"));
        System.out.println(VUOTA);

        List<HashMap<String, Object>> lista = dateService.getAllGiorni();
        for (HashMap<String, Object> mappaGiorno : lista) {

            String sorgente = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
            GiornoCronoEntity istanza = service.findByKey(sorgente);
            assertNotNull(istanza);
            String message = String.format("%s%s%s", sorgente, FORWARD, istanza.getNome());
            System.out.println(message);
        }

    }


    protected void printBeans(List<? extends AbstractEntity> listaBeans) {
        int k = 0;

        System.out.println(VUOTA);

        for (AbstractEntity genericBean : listaBeans) {
            if (genericBean instanceof GiornoCronoEntity bean) {
                System.out.print(++k);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.print(bean);
                System.out.println();
            }
        }
    }

    protected void printDocument(Document document) {
        int k = 0;
        Document doc;
        System.out.println(VUOTA);

        for (Object key : document.keySet()) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.print(key);
            System.out.print(FORWARD);
            Object value = document.get(key);
            if (isComplexType(value)) {
                System.out.print(convertToString(value));
            } else {
                System.out.print(value);
            }
            System.out.println();
        }
    }


    public Document toDocument(Map<String, Object> fields) {
        Document doc = new Document();
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (isComplexType(value)) {
                // Converti l'oggetto custom in un Document
                doc.append(key, convertToDocument(value));
            } else {
                doc.append(key, value);  // Campi semplici o primitivi
            }
        }
        return doc;
    }

    private boolean isComplexType(Object obj) {
        return obj != null && !(obj.getClass().isPrimitive() || obj instanceof String || obj instanceof Number || obj instanceof Boolean);
    }

    private Document convertToDocument(Object obj) {
        Document nestedDoc = new Document();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                nestedDoc.append(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return nestedDoc;
    }

    private String convertToString(Object obj) {
        Document nestedDoc = convertToDocument(obj);
        return nestedDoc.toString();
    }
}
