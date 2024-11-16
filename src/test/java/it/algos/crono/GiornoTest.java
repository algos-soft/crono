package it.algos.crono;

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
        assertNotNull(service);
        super.moduloClazz = GiornoService.class;
        super.entityClazz = GiornoEntity.class;
        super.viewClazz = GiornoView.class;
        super.listClazz = GiornoList.class;

        //--reindirizzo l'istanza della superclasse
        super.moduloService = service;

        super.setUpAll();
        super.usaDebug = true;
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
            giornoBean = service.findByKey(sorgente);
            assertNotNull(giornoBean);
            String  message = String.format("%s%s%s", sorgente, FORWARD, giornoBean.getNome());
            System.out.println(message);
        }

    }



//    @Test
    @Order(1213)
    @DisplayName("1213 - getLista")
    void getLista() {
        System.out.println("1213 - getLista");
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


//    @Test
    @Order(1215)
    @DisplayName("1215 - bulkReset")
    void bulkReset() {
        System.out.println("1215 - bulkReset");
        System.out.println(VUOTA);

        service.deleteAll();
      int  ottenutoIntero = service.count();
        assertTrue(ottenutoIntero == 0);

        List<? extends AbstractEntity> listaBeans = service.getLista();
        assertNotNull(listaBeans);

        inizio = System.currentTimeMillis();
        RisultatoReset risultato = service.bulkInsertEntities((List<? extends GiornoEntity>) listaBeans);

        assertNotNull(risultato);
        String  ottenuto = dateService.deltaTextEsatto(inizio);
        String  ottenuto2 = entityClazz.getSimpleName();
        String  message = String.format("Bulk reset eseguito in %s per la classe [%s] del modulo [%s]", ottenuto, ottenuto2, moduloName);
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
