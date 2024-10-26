package it.algos.crono;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import it.algos.crono.giorno.GiornoEntity;
import it.algos.crono.giorno.GiornoList;
import it.algos.crono.giorno.GiornoService;
import it.algos.crono.giorno.GiornoView;
import it.algos.vbase.ModuloTest;
import it.algos.vbase.entity.AbstractEntity;
import org.bson.Document;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        super.currentService = service;

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


    @Test
    @Order(211)
    @DisplayName("211 - getLista")
    void getMappa() {
        System.out.println("211 - getLista");
        System.out.println(VUOTA);

        List<GiornoEntity> lista = service.getLista();
        assertNotNull(lista);
        message = String.format("Lista di tutte le [%s] entities creata in %s", lista.size(), dateService.deltaTextEsatto(inizio));
        System.out.println(message);
    }


    @Test
    @Order(212)
    @DisplayName("212 - bulkReset")
    void bulkReset() {
        System.out.println("212 - bulkReset");
        System.out.println(VUOTA);

        service.deleteAll();
        ottenutoIntero = service.count();
        assertTrue(ottenutoIntero == 0);

        List<GiornoEntity> lista = service.getLista();
        assertNotNull(lista);

        assertNotNull(collection);
        inizio = System.currentTimeMillis();
        BulkWriteResult result = bulkInsertEntities(collection, lista);

        assertNotNull(result);
        ottenuto = entityClazz.getSimpleName();
        message = String.format("Bulk reset per la classe [%s] del modulo [%s]", ottenuto, moduloName);
        System.out.println(message);

        message = String.format("Aggiunte [%s] entities in %s", result.getInsertedCount(), dateService.deltaTextEsatto(inizio));
        System.out.println(message);
    }


    BulkWriteResult bulkInsertEntities(MongoCollection<Document> collection, List<GiornoEntity> entities) {
        BulkWriteResult result;

        // Converti ogni Entity in un InsertOneModel
        List<WriteModel<Document>> operations = entities.stream()
                .map(entity -> new InsertOneModel<>(entity.toDocument()))
                .collect(Collectors.toList());

        // Esegui il bulkWrite con la lista di operazioni
        return collection.bulkWrite(operations);
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
