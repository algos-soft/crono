package it.algos.crono;

import it.algos.vbase.backend.annotation.*;
import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.list.*;
import it.algos.vbase.backend.logic.*;
import it.algos.vbase.backend.service.*;
import it.algos.vbase.backend.wrapper.*;
import it.algos.vbase.ui.view.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: ven, 27-ott-2023
 * Time: 07:18
 */
public abstract class ModuloServiceTest {

    @Autowired
    public ApplicationContext appContext;

    @Autowired
    protected TextService textService;

    @Autowired
    protected AnnotationService annotationService;

    @Autowired
    protected ReflectionService reflectionService;

    @Autowired
    protected MongoService mongoService;

    @Autowired
    protected LoggerService logger;

    public static final String TAG_RESET_ONLY = "resetOnlyEmpty";

    public static final String TAG_RESET_FORCING = "resetForcing";

    protected ModuloService currentModuloService;

    protected String dbName;

    protected String backendName;

    protected AbstractEntity entityBean;

    protected List<AbstractEntity> listaBeans;

    protected List<String> propertyListNames;

    protected Class moduloServiceClazz;

    protected Class entityClazz;

    protected Class listClazz;

    protected Class viewClazz;

    protected String moduloServiceClazzName;

    protected List<Method> methods;

    protected RisultatoDelete risultatoDelete;

    protected RisultatoReset risultatoReset;

    protected TypeList typeList;

    protected CrudView moduloView;

    protected CrudList moduloList;

    protected String message;

    protected List<Field> fieldsArray = null;

    protected String ottenuto;

    protected int ottenutoIntero;

    protected int cont;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si possono aggiungere regolazioni specifiche PRIMA o DOPO <br>
     */
    protected void setUpAll() {
        assertNotNull(currentModuloService);
        assertNotNull(entityClazz);
        assertNotNull(listClazz);
        assertNotNull(viewClazz);
        assertNotNull(mongoService);

        this.moduloServiceClazz = currentModuloService.getClass();
        assertNotNull(moduloServiceClazz);
//        dbName = mongoTemplate.getCollectionName(entityClazz);
        assertTrue(textService.isValid(dbName));
        propertyListNames = currentModuloService.getPropertyNames();
        assertNotNull(propertyListNames);
        moduloServiceClazzName = currentModuloService.getClass().getSimpleName();
        assertTrue(textService.isValid(moduloServiceClazzName));

        moduloView = (CrudView) appContext.getBean(viewClazz);
        assertNotNull(moduloView);
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpEach() {
        this.methods = null;
        this.risultatoDelete = null;
        this.risultatoReset = null;
        this.typeList = null;
        this.message = VUOTA;
        this.ottenuto = VUOTA;
        this.ottenutoIntero = 0;
        this.cont = 0;
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

        try {
            ottenutoIntero = currentModuloService.count();
        } catch (Exception unErrore) {
            logger.error(new WrapLog().message(unErrore.getMessage()));
        }

        if (ottenutoIntero > 0) {
            message = String.format("Ci sono in totale %s entities di [%s] nel database mongoDB", textService.format(ottenutoIntero), dbName);
        }
        else {
            if (reflectionService.isEsisteMetodo(currentModuloService.getClass(), TAG_RESET_ONLY)) {
                message = String.format("La collection '%s' è ancora vuota. Usa il metodo %s.%s()", dbName, backendName, TAG_RESET_ONLY);
            }
            else {
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

        listaBeans = currentModuloService.findAll();
        assertNotNull(listaBeans);
        message = String.format("Ci sono in totale %s entities di [%s]", textService.format(listaBeans.size()), dbName);
        System.out.println(message);
        printBeans(listaBeans);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

    @Test
    @Order(3)
    @DisplayName("3 - newEntity")
    void newEntity() {
        System.out.println("3 - newEntity");
        System.out.println(VUOTA);

        entityBean = currentModuloService.newEntity();
        assertNotNull(entityBean);
        assertTrue(entityBean.getId() == null);
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
    void getMethodsEntity() {
        System.out.println("5 - getMethods (entityClazz)");
        System.out.println(VUOTA);

        methods = reflectionService.getMethods(entityClazz);
        message = String.format("Nella classe [%s] ci sono %d metodi:", entityClazz.getSimpleName(), methods.size());
        System.out.println(message);

        assertNotNull(methods);
        System.out.println(VUOTA);
        for (Method metodo : methods) {
            System.out.print(++cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

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
    @DisplayName("6 - getMethods (serviceClazz)")
    void getMethodsService() {
        System.out.println("6 - getMethods (serviceClazz)");
        System.out.println(VUOTA);

        methods = reflectionService.getMethods(moduloServiceClazz);
        message = String.format("Nella classe [%s] ci sono %d metodi:", moduloServiceClazzName, methods.size());
        System.out.println(message);

        assertNotNull(methods);
        System.out.println(VUOTA);
        for (Method metodo : methods) {
            System.out.print(++cont);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);

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
    @Order(7)
    @DisplayName("7 - fixPreferenzeList")
    void fixPreferenzeList() {
        System.out.println("7 - fixPreferenzeList");
        System.out.println(VUOTA);

        message = String.format("Preferenze della lista/view [%s]:", viewClazz.getSimpleName());
        System.out.println(message);
        printPreferenze(moduloList);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    @Test
    @Order(8)
    @DisplayName("8 - usaBottoni")
    void usaBottoni() {
        System.out.println("8 - usaBottoni");
        System.out.println(VUOTA);

        message = String.format("Stato dei bottoni della lista/view [%s]:", listClazz.getSimpleName());
        System.out.println(message);
//        printBottoni(listaView);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

    @Test
    @Order(9)
    @DisplayName("9 - annotationView")
    void annotationView() {
        System.out.println("9 - annotationView");
        System.out.println(VUOTA);
        boolean ottenutoBooleano;

        message = String.format("Annotation della view/route [%s]:", viewClazz.getSimpleName());
        System.out.println(message);
        System.out.println(VUOTA);

        ottenuto = annotationService.getMenuName(viewClazz);
        message = String.format("%s%s%s", "getMenuName", FORWARD, ottenuto);
        System.out.println(message);

        MenuGroup group = annotationService.getMenuGroup(viewClazz);
        message = String.format("%s%s%s", "getMenuGroup", FORWARD, group.tag);
        System.out.println(message);

        ottenutoBooleano = annotationService.usaMenuAutomatico(viewClazz);
        message = String.format("%s%s%s", "usaMenuAutomatico", FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    @Test
    @Order(10)
    @DisplayName("10 - annotationEntity")
    void annotationEntity() {
        System.out.println("10 - annotationEntity");
        System.out.println(VUOTA);
        boolean ottenutoBooleano;

        message = String.format("Annotation della POJO/Entity [%s]:", entityClazz.getSimpleName());
        System.out.println(message);
        System.out.println(VUOTA);

        ottenuto = mongoTemplate.getCollectionName(entityClazz);
        message = String.format("%s%s%s", "getCollectionName", FORWARD, ottenuto);
        System.out.println(message);

        //        ottenuto = annotationService.getKeyPropertyName(entityClazz);
        //        message = String.format("%s%s%s", "getKeyPropertyName", FORWARD, ottenuto);
        //        System.out.println(message);

        //        ottenuto = annotationService.getSearchPropertyName(entityClazz);
        //        message = String.format("%s%s%s", "getSearchPropertyName", FORWARD, ottenuto);
        //        System.out.println(message);

        ottenuto = annotationService.getSortPropertyName(entityClazz);
        message = String.format("%s%s%s", "getSortPropertyName", FORWARD, ottenuto);
        System.out.println(message);

        //        typeList = annotationService.getTypeList(entityClazz);
        //        message = String.format("%s%s%s", "getTypeReset", FORWARD, typeList);
        //        System.out.println(message);

        ottenutoBooleano = annotationService.usaResetStartup(entityClazz);
        message = String.format("%s%s%s", "usaStartupReset", FORWARD, ottenutoBooleano);
        System.out.println(message);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    @Test
    @Order(50)
    @DisplayName("50 - resetStartup")
    void resetStartup() {
        System.out.println("50 - resetStartup");
        System.out.println(VUOTA);
        RisultatoReset typeResetPrevisto;
        String collectionName = mongoTemplate.getCollectionName(entityClazz);
        long inizio;

        if (annotationService.usaResetStartup(entityClazz)) {
            message = String.format("La classe [%s] di questo modulo prevede il reset della collection '%s' all'avvio. Flag usaStartupReset=true", entityClazz.getSimpleName(), collectionName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("La classe [%s] di questo modulo non prevede il reset della collection '%s' all'avvio. Flag usaStartupReset=false", entityClazz.getSimpleName(), collectionName);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            return;
        }

        currentModuloService.deleteAll();
        if (currentModuloService.count() == 0) {
            typeResetPrevisto = RisultatoReset.vuotoMaCostruito;
            message = String.format("La collection [%s] è stata svuotata per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_STARTUP, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        }
        else {
            message = String.format("Non sono riuscito a cancellare la collection [%s]", collectionName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        inizio = System.currentTimeMillis();
        risultatoReset = currentModuloService.resetStartup();
        assertNotNull(risultatoReset);
        logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        message = String.format("Risultato di '%s' per la classe [%s] (vuota) è%s%s", "resetStartup", entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        //        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        //        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);

        //
        //

        if (currentModuloService.count() != 0) {
            //            typeResetPrevisto = RisultatoReset.esistenteNonModificato;
            typeResetPrevisto = RisultatoReset.nessuno;
//            if (listaView.usaBottoneResetDelete) {
//                typeResetPrevisto = RisultatoReset.esistenteNonModificato;
//            }
//            if (listaView.usaBottoneResetAdd) {
//                typeResetPrevisto = RisultatoReset.esistenteNonModificato;
                                typeResetPrevisto = RisultatoReset.esistenteIntegrato;
//            }
            message = String.format("La collection [%s] è piena come previsto per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_STARTUP, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
        }
        else {
            message = String.format("La collection [%s] è vuota mentre avrebbe dovuto essere piena. Qualcosa non ha funzionato nel test.", collectionName);
            logger.warn(new WrapLog().message(message).type(TypeLog.test));
            return;
        }
        inizio = System.currentTimeMillis();
        risultatoReset = currentModuloService.resetStartup();
        assertNotNull(risultatoReset);
        message = String.format("Risultato di '%s' per la classe [%s] (piena) è%s%s", "resetStartup", entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        //        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        //        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }


    @Test
    @Order(60)
    @DisplayName("60 - resetDelete")
    void resetDelete() {
        System.out.println("60 - resetDelete");
        System.out.println(VUOTA);
        RisultatoReset typeResetPrevisto;
        String collectionName = mongoTemplate.getCollectionName(entityClazz);
        String viewName = moduloList.getClass().getSimpleName();

        if (moduloList.usaBottoneResetDelete) {
            message = String.format("La view [%s] di questo modulo prevede il bottone '%s'", viewName, METHOD_RESET_DELETE);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            if (reflectionService.isEsisteMetodo(moduloServiceClazz, METHOD_RESET_DELETE)) {
                message = String.format("ed esiste il metodo %s() nella classe [%s]", METHOD_RESET_DELETE, moduloServiceClazzName);
                logger.info(new WrapLog().message(message).type(TypeLog.test));
            }
            else {
                message = String.format("ma non esiste il metodo %s() nella classe [%s]", METHOD_RESET_DELETE, moduloServiceClazzName);
                logger.warn(new WrapLog().message(message).type(TypeLog.test));
            }
        }
        else {
            message = String.format("La view [%s] di questo modulo non prevede il bottone '%s'", viewName, METHOD_RESET_DELETE);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            return;
        }

        if (currentModuloService.count() != 0) {
            typeResetPrevisto = RisultatoReset.vuotoIntegrato;
            message = String.format("La collection [%s] è piena come previsto per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_DELETE, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        }
        else {
            message = String.format("La collection [%s] è vuota. Controlla il test.", collectionName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        //        inizio = System.currentTimeMillis();
        risultatoReset = currentModuloService.resetDelete();
        assertNotNull(risultatoReset);
        message = String.format("Risultato di '%s' per la classe [%s] (piena) è%s%s", METHOD_RESET_DELETE, entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        //        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        //        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);

        //
        //

        currentModuloService.deleteAll();
        if (currentModuloService.count() == 0) {
            typeResetPrevisto = RisultatoReset.vuotoMaCostruito;
            message = String.format("La collection [%s] è stata svuotata per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_DELETE, FORWARD, typeResetPrevisto);
            logger.info(new WrapLog().message(message).type(TypeLog.test));
            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
        }
        else {
            message = String.format("Non sono riuscito a cancellare la collection [%s]", collectionName);
            logger.warn(new WrapLog().message(message));
            return;
        }
        //        inizio = System.currentTimeMillis();
        risultatoReset = currentModuloService.resetDelete();
        assertNotNull(risultatoReset);
        message = String.format("Risultato di '%s' per la classe [%s] (vuota) è%s%s", METHOD_RESET_DELETE, entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
        logger.info(new WrapLog().message(message).type(TypeLog.test));
        //        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
        //        logger.info(new WrapLog().message(message).type(TypeLog.test));
        assertEquals(typeResetPrevisto, risultatoReset);
        System.out.println(VUOTA);
        System.out.println(VUOTA);
    }

    //    @Test
    //    @Order(70)
    //    @DisplayName("70 - resetAdd")
    //    void resetAdd() {
    //        System.out.println("70 - resetAdd");
    //        System.out.println(VUOTA);
    //        RisultatoReset typeResetPrevisto = RisultatoReset.nessuno;
    //        String collectionName = mongoTemplate.getCollectionName(entityClazz);
    //        String viewName = listaView.getClass().getSimpleName();
    //
    //        if (listaView.usaBottoneResetAdd) {
    //            message = String.format("La view [%s] di questo modulo prevede il bottone '%s'", viewName, METHOD_RESET_ADD);
    //            logger.info(new WrapLog().message(message).type(TypeLog.test));
    //            if (reflectionService.isEsisteMetodo(moduloClazz, METHOD_RESET_ADD)) {
    //                message = String.format("ed esiste il metodo %s() nella classe [%s]", METHOD_RESET_ADD, moduloClazzName);
    //                logger.info(new WrapLog().message(message).type(TypeLog.test));
    //            }
    //            else {
    //                message = String.format("ma non esiste il metodo %s() nella classe [%s]", METHOD_RESET_ADD, moduloClazzName);
    //                logger.warn(new WrapLog().message(message).type(TypeLog.test));
    //            }
    //        }
    //        else {
    //            message = String.format("La view [%s] di questo modulo non prevede il bottone '%s'", viewName, METHOD_RESET_ADD);
    //            logger.info(new WrapLog().message(message).type(TypeLog.test));
    //            if (reflectionService.isEsisteMetodo(moduloClazz, METHOD_RESET_ADD)) {
    //                message = String.format("ma esiste il metodo %s() nella classe [%s]", METHOD_RESET_ADD, moduloClazzName);
    //                logger.warn(new WrapLog().message(message).type(TypeLog.test));
    //            }
    //            else {
    //                message = String.format("e non esiste il metodo %s() nella classe [%s]", METHOD_RESET_ADD, moduloClazzName);
    //                logger.info(new WrapLog().message(message).type(TypeLog.test));
    //            }
    //            return;
    //        }
    //
    //        if (currentModulo.count() != 0) {
    //            typeResetPrevisto = RisultatoReset.esistenteIntegrato;
    //            message = String.format("La collection [%s] è piena come previsto per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_ADD, FORWARD, typeResetPrevisto);
    //            logger.info(new WrapLog().message(message).type(TypeLog.test));
    //            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
    //        }
    //        else {
    //            message = String.format("La collection [%s] è vuota. Controlla il test.", collectionName);
    //            logger.warn(new WrapLog().message(message));
    //            return;
    //        }
    //        inizio = System.currentTimeMillis();
    //        risultatoReset = currentModulo.resetAdd();
    //        assertNotNull(risultatoReset);
    //        message = String.format("Risultato di '%s' per la classe [%s] (piena) è%s%s", METHOD_RESET_ADD, entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
    //        logger.info(new WrapLog().message(message).type(TypeLog.test));
    //        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
    //        logger.info(new WrapLog().message(message).type(TypeLog.test));
    //        assertEquals(typeResetPrevisto, risultatoReset);
    //        System.out.println(VUOTA);
    //
    //        //
    //        //
    //
    //        currentModulo.deleteAll();
    //        if (currentModulo.count() == 0) {
    //            typeResetPrevisto = RisultatoReset.vuotoIntegrato;
    //            message = String.format("La collection [%s] è stata svuotata per effettuare il test di %s che prevede %s%s", collectionName, METHOD_RESET_ADD, FORWARD, typeResetPrevisto);
    //            logger.info(new WrapLog().message(message).type(TypeLog.test));
    //            logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
    //        }
    //        else {
    //            message = String.format("Non sono riuscito a cancellare la collection [%s]", collectionName);
    //            logger.warn(new WrapLog().message(message));
    //            return;
    //        }
    //        inizio = System.currentTimeMillis();
    //        risultatoReset = currentModulo.resetAdd();
    //        assertNotNull(risultatoReset);
    //        logger.info(new WrapLog().message(VUOTA).type(TypeLog.test));
    //        message = String.format("Risultato di '%s' per la classe [%s] (vuota) è%s%s", METHOD_RESET_ADD, entityClazz.getSimpleName(), FORWARD, risultatoReset.name());
    //        logger.info(new WrapLog().message(message).type(TypeLog.test));
    //        message = String.format("Tempo trascorso %s", dateService.deltaTextEsatto(inizio));
    //        logger.info(new WrapLog().message(message).type(TypeLog.test));
    //        assertEquals(typeResetPrevisto, risultatoReset);
    //        System.out.println(VUOTA);
    //        System.out.println(VUOTA);
    //    }

    //    protected void fixEquals(Object obj1, Object obj2, Object obj3) {
    //        ottenutoBooleano = obj1.equals(obj2);
    //        message = String.format("%s[%s] vs %s[%s]%s%s", "obj1", obj1, "obj2", obj2, FORWARD, ottenutoBooleano);
    //        assertTrue(ottenutoBooleano);
    //        System.out.println(message);
    //        ottenutoBooleano = obj2.equals(obj1);
    //        message = String.format("%s[%s] vs %s[%s]%s%s", "obj2", obj2, "obj1", obj1, FORWARD, ottenutoBooleano);
    //        assertTrue(ottenutoBooleano);
    //        System.out.println(message);
    //        System.out.println(VUOTA);
    //
    //        ottenutoBooleano = obj1.equals(obj3);
    //        message = String.format("%s[%s] vs %s[%s]%s%s", "obj1", obj1, "obj3", obj3, FORWARD, ottenutoBooleano);
    //        assertFalse(ottenutoBooleano);
    //        System.out.println(message);
    //        ottenutoBooleano = obj3.equals(obj1);
    //        message = String.format("%s[%s] vs %s[%s]%s%s", "obj3", obj3, "obj1", obj1, FORWARD, ottenutoBooleano);
    //        assertFalse(ottenutoBooleano);
    //        System.out.println(message);
    //        System.out.println(VUOTA);
    //
    //        ottenutoBooleano = obj3.equals(obj1);
    //        message = String.format("%s[%s] vs %s[%s]%s%s", "obj3", obj3, "obj1", obj1, FORWARD, ottenutoBooleano);
    //        assertFalse(ottenutoBooleano);
    //        System.out.println(message);
    //        ottenutoBooleano = obj1.equals(obj3);
    //        message = String.format("%s[%s] vs %s[%s]%s%s", "obj1", obj1, "obj3", obj3, FORWARD, ottenutoBooleano);
    //        assertFalse(ottenutoBooleano);
    //        System.out.println(message);
    //        System.out.println(VUOTA);
    //    }


    protected void printField(List<Field> listaField) {
        AField annotation;
        System.out.println(VUOTA);
        int k = 0;

        for (Field field : listaField) {
            annotation = field.getAnnotation(AField.class);
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.print(field.getName());
            System.out.print(SPAZIO);
            System.out.print(QUADRA_INI);
            System.out.print(field.getType().getSimpleName());
            System.out.print(QUADRA_END);
            System.out.println(VUOTA);

            if (annotation != null) {
                message = String.format("%s%s%s%s", TAB_SPAZIO, "type", FORWARD, annotation.type());
                System.out.println(message);
                message = String.format("%s%s%s%d", TAB_SPAZIO, "widthRem", FORWARD, annotation.widthList());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "headerText", FORWARD, annotation.headerText());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "headerIcon", FORWARD, annotation.headerIcon());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "caption", FORWARD, annotation.caption());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "linkClazz", FORWARD, annotation.linkClazz());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "enumClazz", FORWARD, annotation.enumClazz());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "typeBool", FORWARD, annotation.typeBool());
                System.out.println(message);
                message = String.format("%s%s%s%s", TAB_SPAZIO, "typeDate", FORWARD, annotation.typeDate());
                System.out.println(message);
            }
        }
    }

    protected void printBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);
        int k = 0;

        System.out.println(propertyListNames);
        System.out.println(VUOTA);

        for (AbstractEntity bean : listaBeans) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(bean);
        }
    }

    protected void printPreferenze(CrudList list) {
        System.out.println(VUOTA);
        //        message = String.format("%s%s%s", "propertyListNames", FORWARD, list.propertyListNames);
        System.out.println(message);

        message = String.format("%s%s%s", "usaDataProvider", FORWARD, list.usaDataProvider);
        System.out.println(message);

        message = String.format("%s%s%s", "basicSortOrder", FORWARD, list.basicSort);
        System.out.println(message);

        //        message = String.format("%s%s%s", "searchFieldName", FORWARD, list.searchFieldName);
        //        System.out.println(message);
    }

    protected void printBottoni(CrudList list) {
        System.out.println(VUOTA);

        message = String.format("%s%s%s", "usaBottoneDeleteAll", FORWARD, list.usaBottoneDeleteAll);
        System.out.println(message);

        message = String.format("%s%s%s", "usaBottoneResetDelete", FORWARD, list.usaBottoneResetDelete);
        System.out.println(message);

        message = String.format("%s%s%s", "usaBottoneResetAdd", FORWARD, list.usaBottoneResetAdd);
        System.out.println(message);

        message = String.format("%s%s%s", "usaBottoneNew", FORWARD, list.usaBottoneNew);
        System.out.println(message);

        message = String.format("%s%s%s", "usaBottoneEdit", FORWARD, list.usaBottoneEdit);
        System.out.println(message);

        message = String.format("%s%s%s", "usaBottoneShows", FORWARD, list.usaBottoneShow);
        System.out.println(message);

        message = String.format("%s%s%s", "usaBottoneDeleteEntity", FORWARD, list.usaBottoneDeleteEntity);
        System.out.println(message);

        //        message = String.format("%s%s%s", "usaBottoneSearch", FORWARD, list.usaBottoneSearchField);
        //        System.out.println(message);

        message = String.format("%s%s%s", "usaBottoneExport", FORWARD, list.usaBottoneExport);
        System.out.println(message);
    }

}
