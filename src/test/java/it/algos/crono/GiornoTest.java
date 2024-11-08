package it.algos.crono;

import com.mongodb.bulk.BulkWriteResult;
import it.algos.base.ModuloTest;
import it.algos.crono.giorno.GiornoEntity;
import it.algos.crono.giorno.GiornoList;
import it.algos.crono.giorno.GiornoService;
import it.algos.crono.giorno.GiornoView;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.enumeration.RisultatoReset;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private GiornoService service;

    private GiornoEntity giornoBean;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.entityClazz = GiornoEntity.class;
        super.listClazz = GiornoList.class;
        super.viewClazz = GiornoView.class;
        super.moduloClazz = GiornoService.class;

        //--reindirizzo l'istanza della superclasse
//        super.currentService = service;

        super.setUpAll();
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

        List<HashMap<String, Object>> lista = dateService.getAllGiorni();
        for (HashMap<String, Object> mappaGiorno : lista) {

            sorgente = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
            giornoBean = service.findByKey(sorgente);
            assertNotNull(giornoBean);
            message = String.format("%s%s%s", sorgente, FORWARD, giornoBean.getNome());
            System.out.println(message);
        }

    }

    //    @Test
    @Order(211)
    @DisplayName("211 - toDocument")
    void toDocument() {
        System.out.println("211 - toDocument dalla entity");
        System.out.println(VUOTA);
        Document document;

        sorgente = "4 marzo";
        giornoBean = service.findByKey(sorgente);
        assertNotNull(giornoBean);
        document = giornoBean.toDocument();

        assertNotNull(document);
        ottenutoIntero = document.size();
        assertTrue(ottenutoIntero == 6);
        System.out.println(String.format("Ci sono [%d] chiavi nel documento", ottenutoIntero));
        print(document.keySet().stream().toList());
        System.out.println(VUOTA);
        System.out.println(document.toJson());
        System.out.println(document);
    }

    @Test
    @Order(212)
    @DisplayName("212 - getDocument")
    void getDocument() {
        System.out.println("212 - getDocument dal service");
        System.out.println(VUOTA);
        Document document;

        sorgente = "4 marzo";
        giornoBean = service.findByKey(sorgente);
        assertNotNull(giornoBean);
        document = service.getDocument(giornoBean);

        assertNotNull(document);
        ottenutoIntero = document.size();
        assertTrue(ottenutoIntero == 6);
        System.out.println(String.format("Ci sono [%d] chiavi nel documento", ottenutoIntero));
        print(document.keySet().stream().toList());
        System.out.println(VUOTA);
        System.out.println(document.toJson());
        System.out.println(document);
    }

    @Test
    @Order(213)
    @DisplayName("213 - getLista")
    void getLista() {
        System.out.println("213 - getLista");
        System.out.println(VUOTA);

//        listaBeans = service.getLista();
//        assertNotNull(listaBeans);
//        message = String.format("Lista di tutte le [%s] entities creata in %s", listaBeans.size(), dateService.deltaTextEsatto(inizio));
//        System.out.println(message);
    }

//    @Test
//    @Order(214)
//    @DisplayName("214 - oldReset")
//    void oldReset() {
//        System.out.println("214 - oldReset");
//        System.out.println(VUOTA);
//
//        service.deleteAll();
//        ottenutoIntero = service.count();
//        assertTrue(ottenutoIntero == 0);
//
//        List<? extends AbstractEntity> listaBeans = service.getLista();
//        assertNotNull(listaBeans);
//
//        assertNotNull(collection);
//        inizio = System.currentTimeMillis();
//        listaBeans.stream().forEach(bean -> mongoService.insert(bean));
//
//        ottenuto = dateService.deltaTextEsatto(inizio);
//        ottenuto2 = entityClazz.getSimpleName();
//        message = String.format("Old reset eseguito in %s per la classe [%s] del modulo [%s]", ottenuto, ottenuto2, moduloName);
//        System.out.println(message);
//
//        message = String.format("Sono state create [%s] entities con old reset", service.count());
//        System.out.println(message);
//    }


    @Test
    @Order(215)
    @DisplayName("215 - bulkReset")
    void bulkReset() {
        System.out.println("215 - bulkReset");
        System.out.println(VUOTA);

        service.deleteAll();
        ottenutoIntero = service.count();
        assertTrue(ottenutoIntero == 0);

        List<? extends AbstractEntity> listaBeans = service.getLista();
        assertNotNull(listaBeans);

        inizio = System.currentTimeMillis();
        RisultatoReset risultato = service.bulkInsertEntities((List<? extends GiornoEntity>) listaBeans);

        assertNotNull(risultato);
        ottenuto = dateService.deltaTextEsatto(inizio);
        ottenuto2 = entityClazz.getSimpleName();
        message = String.format("Bulk reset eseguito in %s per la classe [%s] del modulo [%s]", ottenuto, ottenuto2, moduloName);
        System.out.println(message);

//        message = String.format("Sono state create [%s] entities con il bulk reset", risultato.getInsertedCount());
        System.out.println(message);
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
