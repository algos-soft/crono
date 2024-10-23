package it.algos.vbase;

import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.RisultatoDelete;
import it.algos.vbase.backend.enumeration.RisultatoReset;
import it.algos.vbase.backend.enumeration.TypeList;
import it.algos.vbase.backend.enumeration.TypeLog;
import it.algos.vbase.backend.list.AList;
import it.algos.vbase.backend.logic.ModuloService;
import it.algos.vbase.backend.service.LoggerService;
import it.algos.vbase.backend.service.MongoService;
import it.algos.vbase.backend.service.ReflectionService;
import it.algos.vbase.backend.service.TextService;
import it.algos.vbase.backend.wrapper.WrapLog;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: ven, 27-ott-2023
 * Time: 07:18
 */
public abstract class ModuloTest extends AlgosTest {

    @Autowired
    protected TextService textService;

    @Autowired
    protected ReflectionService reflectionService;

    @Autowired
    protected MongoService mongoService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    protected LoggerService logger;

    public static final String TAG_RESET_ONLY = "resetOnlyEmpty";

    public static final String TAG_RESET_FORCING = "resetForcing";

    protected ModuloService currentService;

    protected String dbName;

    protected String backendName;

    protected AbstractEntity entityBean;

    protected List<AbstractEntity> listaBeans;

    protected List<String> propertyListNames;

    protected Class moduloClazz;

    protected Class entityClazz;

    protected Class listClazz;

    protected Class viewClazz;

    protected String moduloClazzName;

    protected String collectionName;

    protected List<Method> methods;

    protected RisultatoDelete risultatoDelete;

    protected RisultatoReset risultatoReset;

    protected TypeList typeList;

    protected AList listaView;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    protected void setUpAll() {
        assertNotNull(currentService);
        assertNotNull(entityClazz);
        assertNotNull(listClazz);
        assertNotNull(viewClazz);
        assertNotNull(mongoService);

        this.moduloClazz = currentService.getClass();
        assertNotNull(moduloClazz);
        dbName = mongoTemplate.getCollectionName(entityClazz);
        assertTrue(textService.isValid(dbName));
//        propertyListNames = currentModulo.getPropertyNamesList();
//        assertNotNull(propertyListNames);
        moduloClazzName = currentService.getClass().getSimpleName();
        assertTrue(textService.isValid(moduloClazzName));

        collectionName = annotationService.getCollectionName(entityClazz);
        assertTrue(textService.isValid(collectionName));

        listaView = (AList) appContext.getBean(listClazz);
        assertNotNull(listaView);
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpEach() {
        super.setUpEach();
        this.methods = null;
        this.risultatoDelete = null;
        this.risultatoReset = null;
        this.typeList = null;
    }

    @Test
    @Order(0)
    @DisplayName("0")
    void partenza() {
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

    @Test
    @Order(1)
    @DisplayName("1 - count")
    void count() {
        System.out.println("1 - count");
        String message;

        try {
            ottenutoIntero = currentService.count();
        } catch (Exception unErrore) {
            logger.error(new WrapLog().message(unErrore.getMessage()));
        }

        if (ottenutoIntero > 0) {
            message = String.format("Ci sono in totale %s entities di [%s] nel database mongoDB", textService.format(ottenutoIntero), dbName);
        } else {
            if (reflectionService.isEsisteMetodo(currentService.getClass(), TAG_RESET_ONLY)) {
                message = String.format("La collection '%s' è ancora vuota. Usa il metodo %s.%s()", dbName, backendName, TAG_RESET_ONLY);
            } else {
                message = String.format("Nel database mongoDB la collection '%s' è ancora vuota", dbName);
            }
        }
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    @Test
    @Order(2)
    @DisplayName("2 - findAll")
    void findAll() {
        System.out.println("2 - findAll");
        String message;

        listaBeans = currentService.findAll();
        assertNotNull(listaBeans);
        message = String.format("Ci sono in totale %s entities di [%s]", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printBeans(listaBeans);
    }

    @Test
    @Order(3)
    @DisplayName("3 - newEntity")
    void newEntity() {
        System.out.println("3 - newEntity");
        System.out.println(VUOTA);

        entityBean = currentService.newEntity();
        assertNotNull(entityBean);
        assertTrue(entityBean.getId() == null || entityBean.getId().equals("0"));
        message = String.format("Creata (in memoria) una nuova entity (vuota) di classe [%s] ", entityBean.getClass().getSimpleName());
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

    @Test
    @Order(4)
    @DisplayName("4 - getFields")
    void getFields() {
        System.out.println("4 - getFields");
        System.out.println(VUOTA);

        fieldsArray = reflectionService.getAllFields(entityClazz);
        message = String.format("Nella classe [%s] di questo modulo ci sono %d fields (property):", entityClazz.getSimpleName(), fieldsArray.size());
        System.out.println(message);
        printField(fieldsArray);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    @Test
    @Order(5)
    @DisplayName("5 - getMethods (entityClazz)")
    void getMethods() {
        System.out.println("5 - getMethods (entityClazz)");
        System.out.println(VUOTA);

        methods = reflectionService.getMethods(entityClazz);
        message = String.format("Nella classe [%s] ci sono %d metodi:", entityClazz.getSimpleName(), methods.size());
        System.out.println(message);

        assertNotNull(methods);
        for (Method metodo : methods) {
            System.out.print(metodo.getName());
            System.out.print(PARENTESI_TONDA_INI);
            if (metodo.getParameterCount() > 0) {
                message = VUOTA;
                for (Parameter par : metodo.getParameters()) {
                    message += par.getType().getSimpleName();
                    message += SPAZIO;
                    message += par.getName();
                    message += VIRGOLA_SPAZIO;
                }
                message = message.trim();
                message = textService.levaCoda(message, VIRGOLA);
                System.out.print(message);
            }
            System.out.println(PARENTESI_TONDA_END);
        }
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    @Test
    @Order(6)
    @DisplayName("6 - getMethods (moduloClazz)")
    void getMethods2() {
        System.out.println("6 - getMethods (moduloClazz)");
        System.out.println(VUOTA);

        methods = reflectionService.getMethods(moduloClazz);
        message = String.format("Nella classe [%s] ci sono %d metodi:", moduloClazzName, methods.size());
        System.out.println(message);
        System.out.println(VUOTA);

        assertNotNull(methods);
        for (Method metodo : methods) {
            System.out.print(metodo.getName());
            System.out.print(PARENTESI_TONDA_INI);
            if (metodo.getParameterCount() > 0) {
                message = VUOTA;
                for (Parameter par : metodo.getParameters()) {
                    message += par.getType().getSimpleName();
                    message += SPAZIO;
                    message += par.getName();
                    message += VIRGOLA_SPAZIO;
                }
                message = message.trim();
                message = textService.levaCoda(message, VIRGOLA);
                System.out.print(message);
            }
            System.out.println(PARENTESI_TONDA_END);
        }
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    @Test
    @Order(9)
    @DisplayName("9 - annotationEntity")
    void annotationEntity() {
        System.out.println("9 - annotationEntity");
        System.out.println(VUOTA);

        message = String.format("Annotation (4) della POJO/Entity [%s]:", entityClazz.getSimpleName());
        System.out.println(message);
        System.out.println(VUOTA);

        ottenuto = annotationService.getCollectionName(entityClazz);
        message = String.format("%s%s%s", "getCollectionName", FORWARD, ottenuto);
        System.out.println(message);

        ottenuto = (String) annotationService.getSortProperty(entityClazz).orElse(VUOTA);
        message = String.format("%s%s%s", "getSortPropertyDeprecated", FORWARD, ottenuto);
        System.out.println(message);

        ottenuto = (String) annotationService.getFocusField(entityClazz).orElse(VUOTA);
        message = String.format("%s%s%s", "getFocusField", FORWARD, ottenuto);
        System.out.println(message);

        ottenutoBooleano = annotationService.usaResetStartup(entityClazz);
        message = String.format("%s%s%s", "usaStartupReset", FORWARD, ottenutoBooleano);
        System.out.println(message);
    }

    @Test
    @Order(10)
    @DisplayName("10 - annotationList")
    void annotationList() {
        System.out.println("10 - annotationList");
        System.out.println(VUOTA);

        message = String.format("Annotation (3) della List [%s]:", listClazz.getSimpleName());
        System.out.println(message);
        System.out.println(VUOTA);

        listaStr = annotationService.getButtons(listClazz);
        message = String.format("%s%s%s", "getButtons", FORWARD, listaStr);
        System.out.println(message);

        listaStr = (List) annotationService.getListColumns(listClazz).orElse(null);
        message = String.format("%s%s%s", "getListColumns", FORWARD, listaStr);
        System.out.println(message);

        ottenutoBooleano = annotationService.espandiUltimaColonnaVisibile(viewClazz);
        message = String.format("%s%s%s", "espandiUltimaColonnaVisibile", FORWARD, ottenutoBooleano);
        System.out.println(message);
    }


    @Test
    @Order(11)
    @DisplayName("11 - annotationView")
    void annotationView() {
        System.out.println("11 - annotationView");
        System.out.println(VUOTA);

        message = String.format("Annotation (17) della view/route [%s]:", viewClazz.getSimpleName());
        System.out.println(message);
        System.out.println(VUOTA);

        ottenuto = (String) annotationService.getMenuGroup(viewClazz).orElse(VUOTA);
        message = String.format("%s%s%s", "getMenuGroup", FORWARD, ottenuto);
        System.out.println(message);

        ottenuto = annotationService.getMenuName(viewClazz);
        message = String.format("%s%s%s", "getMenuName", FORWARD, ottenuto);
        System.out.println(message);

        ottenutoBooleano = annotationService.usaMenuAutomatico(viewClazz);
        message = String.format("%s%s%s", "usaMenuAutomatico", FORWARD, ottenutoBooleano);
        System.out.println(message);
    }

    @Test
    @Order(12)
    @DisplayName("12 - getCollectionName")
    void getCollectionName() {
        System.out.println("12 - getCollectionName");
        System.out.println(VUOTA);

        ottenuto = mongoTemplate.getCollectionName(entityClazz);
        message = String.format("CollectionName ricavata dal dataBase%s[%s]", FORWARD, ottenuto);
        System.out.println(message);

        System.out.println(VUOTA);
        ottenuto2 = annotationService.getCollectionName(entityClazz);
        message = String.format("CollectionName ricavata dalla Annotation%s[%s]", FORWARD, ottenuto2);
        System.out.println(message);

        if (ottenuto2.equals(ottenuto)) {
            message = String.format("Il nome della collection è lo stesso sia ricavato dal database che dall'annotation della Entity");
        } else {
            message = String.format("Il nome della collection è diverso nel database rispetto all'annotation della Entity");
        }
        System.out.println(VUOTA);
        System.out.println(message);
    }


    //    @Test
    @Order(50)
    @DisplayName("50 - resetStartup")
    void resetStartup() {
        System.out.println("50 - resetStartup");
        System.out.println(VUOTA);
        RisultatoReset typeResetPrevisto;
        String collectionName = mongoTemplate.getCollectionName(entityClazz);

        if (annotationService.usaResetStartup(entityClazz)) {
            message = String.format("La classe [%s] di questo modulo prevede il reset della collection '%s' all'avvio. Flag usaResetStartup=true", entityClazz.getSimpleName(), collectionName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        } else {
            message = String.format("La classe [%s] di questo modulo non prevede il reset della collection '%s' all'avvio. Flag usaResetStartup=false", entityClazz.getSimpleName(), collectionName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            return;
        }

        currentService.deleteAll();
        if (currentService.count() == 0) {
            typeResetPrevisto = RisultatoReset.vuotoMaCostruito;
            message = String.format("La collection [%s] è stata svuotata per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_STARTUP, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        } else {
            message = String.format("Non sono riuscito a cancellare la collection [%s]", collectionName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        inizio = System.currentTimeMillis();
        risultatoReset = currentService.resetStartup();
        assertNotNull(risultatoReset);
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        message = String.format("Risultato di '%s' per la classe [%s] (vuota) è%s%s", "resetStartup", entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);

        //
        //

        if (currentService.count() != 0) {
            //            typeResetPrevisto = RisultatoReset.esistenteNonModificato;
            typeResetPrevisto = RisultatoReset.nessuno;
//            if (listaView.usaBottoneResetDelete) {
//                typeResetPrevisto = RisultatoReset.esistenteNonModificato;
//            }
//            if (listaView.usaBottoneResetAdd) {
//                typeResetPrevisto = RisultatoReset.esistenteNonModificato;
//                //                typeResetPrevisto = RisultatoReset.esistenteIntegrato;
//            }
            message = String.format("La collection [%s] è piena come previsto per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_STARTUP, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        } else {
            message = String.format("La collection [%s] è vuota mentre avrebbe dovuto essere piena. Qualcosa non ha funzionato nel test.", collectionName);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
            return;
        }
        inizio = System.currentTimeMillis();
        risultatoReset = currentService.resetStartup();
        assertNotNull(risultatoReset);
        message = String.format("Risultato di '%s' per la classe [%s] (piena) è%s%s", "resetStartup", entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.test));
//        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


//    @Test
    @Order(60)
    @DisplayName("60 - resetDelete")
    void resetDelete() {
        System.out.println("60 - resetDelete");
        System.out.println(VUOTA);
        RisultatoReset typeResetPrevisto;
        String collectionName = mongoTemplate.getCollectionName(entityClazz);
        String viewName = listaView.getClass().getSimpleName();

        if (true) {
//            if (listaView.usaBottoneEdit) {
            message = String.format("La view [%s] di questo modulo prevede il bottone '%s'", viewName, METHOD_RESET_DELETE);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            if (reflectionService.isEsisteMetodo(moduloClazz, METHOD_RESET_DELETE)) {
                message = String.format("ed esiste il metodo %s() nella classe [%s]", METHOD_RESET_DELETE, moduloClazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));
            } else {
                message = String.format("ma non esiste il metodo %s() nella classe [%s]", METHOD_RESET_DELETE, moduloClazzName);
                logger.warn(new WrapLog().message(message).type(TypeLog.test));
            }
        } else {
            message = String.format("La view [%s] di questo modulo non prevede il bottone '%s'", viewName, METHOD_RESET_DELETE);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            return;
        }

        if (currentService.count() != 0) {
            typeResetPrevisto = RisultatoReset.vuotoIntegrato;
            message = String.format("La collection [%s] è piena come previsto per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_DELETE, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        } else {
            message = String.format("La collection [%s] è vuota. Controlla il test.", collectionName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        inizio = System.currentTimeMillis();
        risultatoReset = currentService.resetDelete();
        assertNotNull(risultatoReset);
        typeResetPrevisto = RisultatoReset.vuotoMaCostruito;
        message = String.format("Risultato di '%s' per la classe [%s] (piena) è%s%s", METHOD_RESET_DELETE, entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);

        //
        //

        currentService.deleteAll();
        if (currentService.count() == 0) {
            typeResetPrevisto = RisultatoReset.vuotoMaCostruito;
            message = String.format("La collection [%s] è stata svuotata per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_DELETE, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        } else {
            message = String.format("Non sono riuscito a cancellare la collection [%s]", collectionName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        inizio = System.currentTimeMillis();
        risultatoReset = currentService.resetDelete();
        assertNotNull(risultatoReset);
        message = String.format("Risultato di '%s' per la classe [%s] (vuota) è%s%s", METHOD_RESET_DELETE, entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    //    @Test
    @Order(70)
    @DisplayName("70 - resetAdd")
    void resetAdd() {
        System.out.println("70 - resetAdd");
        System.out.println(VUOTA);
        RisultatoReset typeResetPrevisto = RisultatoReset.nessuno;
        String collectionName = mongoTemplate.getCollectionName(entityClazz);
        String viewName = listaView.getClass().getSimpleName();

        if (true) {
//            if (listaView.usaSelettoreColonne) {
            message = String.format("La view [%s] di questo modulo prevede il bottone '%s'", viewName, METHOD_RESET_ADD);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            if (reflectionService.isEsisteMetodo(moduloClazz, METHOD_RESET_ADD)) {
                message = String.format("ed esiste il metodo %s() nella classe [%s]", METHOD_RESET_ADD, moduloClazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));
            } else {
                message = String.format("ma non esiste il metodo %s() nella classe [%s]", METHOD_RESET_ADD, moduloClazzName);
                logger.warn(new WrapLog().message(message).type(TypeLog.test));
            }
        } else {
            message = String.format("La view [%s] di questo modulo non prevede il bottone '%s'", viewName, METHOD_RESET_ADD);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            if (reflectionService.isEsisteMetodo(moduloClazz, METHOD_RESET_ADD)) {
                message = String.format("ma esiste il metodo %s() nella classe [%s]", METHOD_RESET_ADD, moduloClazzName);
                logger.warn(new WrapLog().message(message).type(TypeLog.test));
            } else {
                message = String.format("e non esiste il metodo %s() nella classe [%s]", METHOD_RESET_ADD, moduloClazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));
            }
            return;
        }

        if (currentService.count() != 0) {
            typeResetPrevisto = RisultatoReset.esistenteIntegrato;
            message = String.format("La collection [%s] è piena come previsto per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_ADD, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        } else {
            message = String.format("La collection [%s] è vuota. Controlla il test.", collectionName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        inizio = System.currentTimeMillis();
        risultatoReset = currentService.resetAdd();
        assertNotNull(risultatoReset);
        message = String.format("Risultato di '%s' per la classe [%s] (piena) è%s%s", METHOD_RESET_ADD, entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);

        //
        //

        currentService.deleteAll();
        if (currentService.count() == 0) {
            typeResetPrevisto = RisultatoReset.vuotoIntegrato;
            message = String.format("La collection [%s] è stata svuotata per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_ADD, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        } else {
            message = String.format("Non sono riuscito a cancellare la collection [%s]", collectionName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        inizio = System.currentTimeMillis();
        risultatoReset = currentService.resetAdd();
        assertNotNull(risultatoReset);
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        message = String.format("Risultato di '%s' per la classe [%s] (vuota) è%s%s", METHOD_RESET_ADD, entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

    @Test
    @Order(210)
//    @Disabled("Disabilitato temporaneamente per risparmiare tempo")
    @DisplayName("210 - reset")
    void reset() {
        System.out.println(VUOTA);
        System.out.println("210 - reset");
        System.out.println(VUOTA);

        if (reflectionService.isEsisteMetodo(currentService.getClass(), "reset")) {
            currentService.reset();
            message = String.format("Reset eseguito in %s per la classe [%s]", dateService.deltaText(inizio), clazzName);
            System.out.println(message);

            ottenutoIntero = currentService.count();
            message = String.format("Sono state create [%s] entities con il reset", textService.format(ottenutoIntero));
            System.out.println(message);
        } else {
            message = String.format("Nella classe [%s] non esiste il metodo reset", clazzName);
            System.out.println(message);
        }

    }

    protected void fixEquals(Object obj1, Object obj2, Object obj3) {
        ottenutoBooleano = obj1.equals(obj2);
        message = String.format("%s[%s] vs %s[%s]%s%s", "obj1", obj1, "obj2", obj2, FORWARD, ottenutoBooleano);
        assertTrue(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = obj2.equals(obj1);
        message = String.format("%s[%s] vs %s[%s]%s%s", "obj2", obj2, "obj1", obj1, FORWARD, ottenutoBooleano);
        assertTrue(ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);

        ottenutoBooleano = obj1.equals(obj3);
        message = String.format("%s[%s] vs %s[%s]%s%s", "obj1", obj1, "obj3", obj3, FORWARD, ottenutoBooleano);
        assertFalse(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = obj3.equals(obj1);
        message = String.format("%s[%s] vs %s[%s]%s%s", "obj3", obj3, "obj1", obj1, FORWARD, ottenutoBooleano);
        assertFalse(ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);

        ottenutoBooleano = obj3.equals(obj1);
        message = String.format("%s[%s] vs %s[%s]%s%s", "obj3", obj3, "obj1", obj1, FORWARD, ottenutoBooleano);
        assertFalse(ottenutoBooleano);
        System.out.println(message);
        ottenutoBooleano = obj1.equals(obj3);
        message = String.format("%s[%s] vs %s[%s]%s%s", "obj1", obj1, "obj3", obj3, FORWARD, ottenutoBooleano);
        assertFalse(ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);
    }


    protected void print(List<String> lista) {
        int k = 0;

        for (String nome : lista) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(nome);
        }
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        int k = 0;

        for (AbstractEntity bean : listaBeans) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(bean);
        }
    }

    protected void printPreferenze(AList list) {
        System.out.println(VUOTA);
//        message = String.format("%s%s%s", "propertyListNames", FORWARD, list.getPropertyNames());
//        System.out.println(message);

//        message = String.format("%s%s%s", "usaDataProvider", FORWARD, list.usaDataProvider);
//        System.out.println(message);

        //        message = String.format("%s%s%s", "basicSortOrder", FORWARD, list.basicSortOrder);
        System.out.println(message);

        //        message = String.format("%s%s%s", "searchFieldName", FORWARD, list.searchFieldName);
        //        System.out.println(message);
    }

    protected void printBottoni(AList list) {
        System.out.println(VUOTA);

//        message = String.format("%s%s%s", "usaBottoneDeleteAll", FORWARD, list.usaBottoneDeleteAll);
//        System.out.println(message);

//        message = String.format("%s%s%s", "usaBottoneResetDelete", FORWARD, list.usaBottoneResetDelete);
//        System.out.println(message);

//        message = String.format("%s%s%s", "usaBottoneResetAdd", FORWARD, list.usaBottoneResetAdd);
//        System.out.println(message);

//        message = String.format("%s%s%s", "usaBottoneNew", FORWARD, list.usaBottoneNew);
//        System.out.println(message);

//        message = String.format("%s%s%s", "usaBottoneEdit", FORWARD, list.usaBottoneEdit);
//        System.out.println(message);
//
//        message = String.format("%s%s%s", "usaBottoneShows", FORWARD, list.usaBottoneShow);
//        System.out.println(message);

//        message = String.format("%s%s%s", "usaBottoneDeleteEntity", FORWARD, list.usaBottoneDeleteEntity);
//        System.out.println(message);

        //        message = String.format("%s%s%s", "usaBottoneSearch", FORWARD, list.usaBottoneSearch);
        //        System.out.println(message);

//        message = String.format("%s%s%s", "usaBottoneExport", FORWARD, list.usaBottoneExport);
//        System.out.println(message);
    }

}
