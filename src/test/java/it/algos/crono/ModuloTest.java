package it.algos.crono;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import it.algos.utility.StyleExtractor;
import it.algos.utility.UtilityService;
import it.algos.utility.schedule.ASchedule;
import it.algos.vbase.annotation.IAnchor;
import it.algos.vbase.annotation.IReset;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.form.DefaultForm;
import it.algos.vbase.list.AList;
import it.algos.vbase.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;

import static it.algos.vbase.boot.BaseCost.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: gio, 01-mag-2025
 * Time: 09:35
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModuloTest {
    public static final String NO_TEST = "Test non effettuato perché siamo nella superclasse chiamata da un tag";
    protected static final String TAG_ELA_UNO = "elaboraUno";
    protected static final String TAG_ELA_UNO_BULK = "elaboraUnoBulk";
    protected static final String TAG_ELA_DUE = "elaboraDue";
    //    public boolean usaTestDebug = false; //[NOTE] cancella tutti i dati reali - usare con attenzione
    public boolean usaTestReset = false; //[NOTE] cancella tutti i dati reali - usare con attenzione
    public boolean usaTestDownload = false; //[NOTE] potrebbe impiegare troppo tempo - usare con attenzione
    @Autowired
    protected ApplicationContext appContext;
    @Autowired
    protected TextService textService;
    @Autowired
    protected AnnotationService annotationService;
    @Autowired
    protected ReflectionService reflectionService;
    @Autowired
    protected UtilityService utilityService;
    @Autowired
    protected DateService dateService;
    @Autowired
    protected ModuloUtilityService moduloUtilityService;

    //--utilizzata in setUpEach()
    protected long inizio;
    protected Class moduloClazz;
    protected Class entityClazz;
    protected Class listClazz;
    protected Class viewClazz;
    protected Class formClazz;
    protected String entityClazzName;
    protected ModuloService service;
    protected String moduloName;
    protected String moduloClazzName;
    protected String viewClazzName;
    protected String listClazzName;
    protected String collectionName;
    protected String message;

    protected boolean usaTestElaboraUno = false;
    protected boolean usaTestElaboraDue = false;
    protected boolean usaTestUpload = false;
    protected boolean usaFindAll = false;
    protected boolean usaTestDebug = false;
    protected boolean usaTestResetStartup = false;
    protected boolean usaTestResetDelete = false;
    protected boolean usaRestoreFinale = false;

    @Value("${spring.data.mongodb.database}")
    protected String dataBaseName;

    /**
     * Qui passa una volta sola, chiamato dalle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * Si devono aggiungere regolazioni specifiche PRIMA <br>
     * Si possono aggiungere regolazioni specifiche DOPO <br>
     */
    protected void setUpAll() {
        //        super.setUpAll();

        assertNotNull(appContext);
        assertNotNull(textService);
        assertNotNull(annotationService);
        assertNotNull(reflectionService);
        assertNotNull(utilityService);
    }


    protected void setUpInit() {
        if (service == null) {
            log.error("Manca il {} nel setUpAll di {}", "service", this.getClass().getSimpleName());
            assertTrue(false);
        }
        if (moduloClazz == null) {
            log.error("Manca la {} nel setUpAll di {}", "moduloClazz", this.getClass().getSimpleName());
            assertTrue(false);
        }

        if (entityClazz == null) {
            log.error("Manca la {} nel setUpAll di {}", "entityClazz", this.getClass().getSimpleName());
            assertTrue(false);
        }
        entityClazzName = entityClazz.getSimpleName();
        assertTrue(textService.isValid(entityClazzName));
        collectionName = annotationService.getCollectionName(entityClazz);
        assertTrue(textService.isValid(collectionName));

        if (viewClazz == null) {
            log.error("Manca la {} nel setUpAll di {}", "viewClazz", this.getClass().getSimpleName());
            assertTrue(false);
        }
        viewClazzName = viewClazz.getSimpleName();
        assertTrue(textService.isValid(viewClazzName));

        if (listClazz == null) {
            log.error("Manca la {} nel setUpAll di {}", "listClazz", this.getClass().getSimpleName());
            assertTrue(false);
        }
        listClazzName = listClazz.getSimpleName();
        assertTrue(textService.isValid(listClazzName));

        if (formClazz == null) {
            formClazz = DefaultForm.class;
        }

        moduloClazzName = moduloClazz.getSimpleName();
        assertTrue(StringUtils.isNotBlank(moduloClazzName));

        moduloName = moduloClazzName.substring(0, moduloClazzName.length() - SUFFIX_MODULO.length());
        assertTrue(StringUtils.isNotBlank(moduloClazzName));

        assertTrue(textService.isValid(dataBaseName));
    }


    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Deve essere sovrascritto, invocando ANCHE il metodo della superclasse <br>
     * L'ultima sottoclasse DEVE utilizzare l'annotation -> @BeforeEach
     * Si possono aggiungere regolazioni specifiche DOPO la chiamata alla superclasse <br>
     */
    @BeforeEach
    protected void setUpEach() {
        inizio = System.currentTimeMillis();
        message = VUOTA;
    }

    @Test
    @Order(0)
    @DisplayName("0 - checkTest")
    protected void checkTest() {
        System.out.println(VUOTA);
        System.out.println("0 - checkTest");
        System.out.println("Parametri di questa classe di test per un modulo (se non dà errore ci sono tutti quelli che servono)");
        System.out.println(VUOTA);

        setUpInit();

        System.out.println(String.format("%s%s%s", "moduloName", UGUALE_SPAZIATO, moduloName));
        System.out.println(String.format("%s%s%s", "moduloClazz", UGUALE_SPAZIATO, moduloClazz.getSimpleName()));
        System.out.println(String.format("%s%s%s", "entityClazz", UGUALE_SPAZIATO, entityClazz.getSimpleName()));
        System.out.println(String.format("%s%s%s", "viewClazz", UGUALE_SPAZIATO, viewClazz.getSimpleName()));
        System.out.println(String.format("%s%s%s", "listClazz", UGUALE_SPAZIATO, listClazz.getSimpleName()));
        System.out.println(String.format("%s%s%s", "formClazz", UGUALE_SPAZIATO, formClazz.getSimpleName()));
        System.out.println(String.format("%s%s%s", "moduloClazzName", UGUALE_SPAZIATO, moduloClazzName));
        System.out.println(String.format("%s%s%s", "collectionName", UGUALE_SPAZIATO, collectionName));
        System.out.println(String.format("%s%s%s", "usaTestReset", UGUALE_SPAZIATO, usaTestReset));
        System.out.println(String.format("%s%s%s", "usaTestDownload", UGUALE_SPAZIATO, usaTestDownload));

        System.out.println(String.format("%s%s%s", "usaFindAll", UGUALE_SPAZIATO, usaFindAll));
        System.out.println(String.format("%s%s%s", "usaTestElaboraUno", UGUALE_SPAZIATO, usaTestElaboraUno));
        System.out.println(String.format("%s%s%s", "usaTestElaboraDue", UGUALE_SPAZIATO, usaTestElaboraDue));
        System.out.println(String.format("%s%s%s", "usaTestUpload", UGUALE_SPAZIATO, usaTestUpload));
    }



    protected void printField(List<Field> listaFields) {
        int k = 0;
        int modifiers;

        for (Field field : listaFields) {
            modifiers = field.getModifiers();
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.print(field.getName());
            System.out.print(SPAZIO);
            System.out.print(textService.setQuadre(field.getType().getSimpleName()));
            System.out.print(SPAZIO);
            System.out.print(textService.setQuadre(Modifier.isPrivate(modifiers) ? "private" : "public"));
            System.out.print(field.isAnnotationPresent(Transient.class) ? SPAZIO + textService.setQuadre("transient") : VUOTA);
            System.out.println(VUOTA);
        }
    }

    protected void numMetodi(String clazzName, int numero) {
        String message = switch (numero) {
            case 0 -> String.format("Nella classe [%s] non c'è nessun metodo:", clazzName);
            case 1 -> String.format("Nella classe [%s] c'è un solo metodo:", clazzName);
            default -> String.format("Nella classe [%s] ci sono %d metodi:", clazzName, numero);
        };

        System.out.println(message);
    }

    protected void print(List<String> listaString) {
        print(listaString, listaString.size());
    }

    protected void print(List<String> listaString, int max) {
        int cont = 0;

        if (listaString != null) {
            System.out.println(VUOTA);
            System.out.println(String.format("Valori (%d):", listaString.size()));
            for (String riga : listaString.subList(0, Math.min(max, listaString.size()))) {
                cont++;
                System.out.print(cont);
                System.out.print(PARENTESI_TONDA_END);
                System.out.print(SPAZIO);
                System.out.println(String.format("%s", riga));
            }
        }
    }

    protected void printMethod(List<Method> listaMetodi) {
        int k = 0;

        for (Method method : listaMetodi) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.print(method.getName());
            System.out.print(getParameters(method));
            System.out.println(VUOTA);
        }
    }

    protected String getParameters(Method method) {
        String message = PARENTESI_TONDA_INI;

        for (Parameter par : method.getParameters()) {
            message += par.getType().getSimpleName();
            message += SPAZIO;
            message += par.getName();
            message += VIRGOLA_SPAZIO;
        }
        message = message.trim();
        message = textService.levaCoda(message, VIRGOLA);
        message += PARENTESI_TONDA_END;

        return message;
    }

    protected boolean checkUsaReset() {
        int limitTestFind = 100;
        if (!usaTestReset && service.count() > limitTestFind) {
            log.warn("Test disabilitato perché potrebbe cancellare dei dati (o impiegare troppo tempo)");
            log.info("C'è un limite di {} entities per effettuare alcune query", limitTestFind);
            log.info("Per attivare il test modifica la property [usaTestReset] in {}.setUpAll()", this.getClass().getSimpleName());
            return true;
        }
        return false;
    }

    protected boolean checkUsaDownload() {
        int limitTestFind = 100;
        if (!usaTestDownload && service.count() > limitTestFind) {
            log.warn("Test disabilitato perché potrebbe impiegare troppo tempo (o cancellare dei dati)");
            log.info("C'è un limite di {} entities per effettuare alcune query", limitTestFind);
            log.info("Per attivare il test modifica la property [usaTestDownload] in {}.setUpAll()", this.getClass().getSimpleName());
            return true;
        }
        return false;
    }


    protected void collectionVuota() {
        String message = String.format("La collection [%s] è vuota.", collectionName);
        System.out.println(message);

        if (!entityClazz.isAnnotationPresent(IReset.class)) {
            message = String.format("La entity [%s] non prevede il reset della collection", entityClazz.getSimpleName());
            log.warn(message);
            return;
        }

        String moduloClazzName = moduloClazz.getSimpleName();
        if (reflectionService.isEsisteFilteredMethod(moduloClazz, METHOD_RESET)) {
            message = String.format("Controlla il metodo %s.%s()", moduloClazzName, METHOD_RESET);
            log.warn(message);
        } else {
            message = String.format("Manca il metodo %s.%s()", moduloClazzName, METHOD_RESET);
            log.warn(message);
        }
    }

    protected void collectionPiena() {
        String message = String.format("Entities (parziali) della collection [%s] nel database %s", collectionName, dataBaseName);
        System.out.println(message);
    }

    protected void collectionPiena(int totale) {
        String message = String.format("Ci sono in totale [%s] entities della collection [%s] nel database %s", textService.format(totale), collectionName, dataBaseName);
        System.out.println(message);
    }

    protected void printFewBeans(List<AbstractEntity> listaBeans) {
        System.out.println(VUOTA);
        int max = 10;
        int k = 0;
        int size = listaBeans.size();

        if (size > max) {
            String message = String.format("Nella lista ci sono [%s] entities. Ne stampo SOLO le prime %d", listaBeans.size(), max);
            System.out.println(message);
            System.out.print(VUOTA);
        }

        for (AbstractEntity genericBean : listaBeans.subList(0, Math.min(max, size))) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.print(genericBean);
            System.out.println();
        }
    }

    protected boolean usaTestElaboraUno() {
        if (usaTestElaboraUno) {
            if (esisteMetodoSenzaParametri(TAG_ELA_UNO_BULK)) {
                return true;
            } else {
                log.error("Il test è abilitato ma manca il metodo [elaboraUno] nella classe {}", moduloClazzName);
                assertTrue(false);
                return false;
            }
        } else {
            log.info("Test disabilitato perché usaTestElaboraUno=false");
            log.warn("Per abilitare questo metodo, devi modificare il flag {} in {}.setUpAll().", "[usaTestElaboraUno]", getClass().getSimpleName());
            return false;
        }
    }

    protected boolean usaTestElaboraDue() {
        if (usaTestElaboraDue) {
            return true;
        }
        log.info("Test disabilitato perché usaTestElaboraDue=false");
        log.warn("Per abilitare questo metodo, devi modificare il flag {} in {}.setUpAll().", "[usaTestElaboraDue]", getClass().getSimpleName());
        return false;
    }

    //controllo che esista il metodo nel test specifico
    protected boolean checkElaboraUnoAnte() {
        if (esisteMetodoUnParametro(this.getClass(), "elaboraUnoAnte")) {
            return true;
        } else {
            log.warn("Per regolare la entity in modo che abbia senso testare elaboraUno(), devi");
            log.warn("sovrascrivere il metodo elaboraUnoAnte() nel test [{}]", this.getClass().getSimpleName());
            return false;
        }
    }

    //controllo che esista il metodo nel test specifico
    protected boolean checkElaboraUnoPost() {
        if (esisteMetodoUnParametro(this.getClass(), "elaboraUnoPost")) {
            return true;
        } else {
            log.warn("Per regolare la entity in modo che abbia senso testare elaboraUno(), devi");
            log.warn("sovrascrivere il metodo elaboraUnoPost() nel test [{}]", this.getClass().getSimpleName());
            return false;
        }
    }

    protected void checkElaboraUno() {
        System.out.println("Possibile un ulteriore controllo di congruità del DB. Sovrascrivi il metodo checkElaboraUno() nel test specifico.");
    }

    protected void checkElaboraDueAnte() {
        System.out.println("Per regolare la entity in modo che abbia senso testare elaboraUno(), devi sovrascrivi il metodo checkElaboraDueAnte() nel test specifico.");
    }

    protected void checkElaboraDue() {
        System.out.println("Possibile un ulteriore controllo di congruità del DB. Sovrascrivi il metodo checkElaboraDue() nel test specifico.");
    }

    protected boolean esisteMetodoSenzaParametri(String nomeMetodo) {
        return esisteMetodoSenzaParametri(moduloClazz, nomeMetodo);
    }

    protected boolean esisteMetodoSenzaParametri(Class clazz, String nomeMetodo) {
        for (Method method : moduloClazz.getDeclaredMethods()) {
            if (method.getName().equals(nomeMetodo) && method.getParameterCount() == 0) {
                return true;
            }
        }

        return false;
    }

    protected boolean esisteMetodoUnParametro(String nomeMetodo) {
        return esisteMetodoUnParametro(moduloClazz, nomeMetodo);
    }

    protected boolean esisteMetodoUnParametro(Class clazz, String nomeMetodo) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(nomeMetodo) && method.getParameterCount() == 1) {
                return true;
            }
        }

        return false;
    }

    public void elaboraUnoAnte(AbstractEntity istanzaSuper) {
    }

    public void elaboraUnoPost(AbstractEntity istanzaSuper) {
    }

    protected void checkReset() {
        System.out.println("Possibile un ulteriore controllo di congruità del DB. Sovrascrivi il metodo checkReset() nel test specifico.");
    }

    protected boolean usaTestReset() {
        boolean usaResetStartup = annotationService.usaResetStartup(entityClazz);
        boolean esisteButtonResetDelete = annotationService.getButtons(listClazz).contains(METHOD_RESET_DELETE);
        boolean esisteMethodReset = reflectionService.isEsisteFilteredMethod(moduloClazz, METHOD_RESET);
        boolean esisteMethodDownload = reflectionService.isEsisteFilteredMethod(moduloClazz, METHOD_DOWNLOAD);

        String message;
        if (usaResetStartup) {
            message = String.format("La classe [%s] di questo modulo ha il flag usaResetStartup=true", entityClazzName, METHOD_RESET_STARTUP, collectionName);
            log.info(message);
        } else {
            message = String.format("La classe [%s] di questo modulo ha il flag usaResetStartup=false", entityClazzName, collectionName);
            log.info(message);
        }

        if (esisteButtonResetDelete) {
            message = String.format("La classe [%s] di questo modulo usa il bottone [%s]", listClazz.getSimpleName(), METHOD_RESET_DELETE);
            log.info(message);
        } else {
            message = String.format("La classe [%s] di questo modulo non usa il bottone [%s]", listClazz.getSimpleName(), METHOD_RESET_DELETE);
            log.info(message);
        }

        if (esisteMethodDownload) {
            message = String.format("La classe [%s] di questo modulo implementa il metodo %s()", moduloClazzName, METHOD_DOWNLOAD);
            log.info(message);
        } else {
            message = String.format("La classe [%s] di questo modulo non implementa il metodo %s()", moduloClazzName, METHOD_DOWNLOAD);
            log.info(message);
        }

        if (esisteMethodReset) {
            if (esisteMethodDownload) {
                message = String.format("Le funzionalità del metodo %s() vengono implementate dal metodo %s()", METHOD_RESET, METHOD_DOWNLOAD);
                log.info(message);
                return false;
            } else {
                if (usaTestResetDelete) {
                    return true;
                } else {
                    infoAbilitaResetDelete();
                    return false;
                }
            }
        } else {
            if (usaTestResetDelete) {
                infoDisabilitaResetDelete();
            }
            return false;
        }
    }


    protected void infoAbilitaResetDelete() {
        String message = "Test disabilitato perché questo test ha il flag usaResetDelete=false";
        log.warn(message);
        String clazz = this.getClass().getSimpleName();
        String metodo = "setUpAll";
        String property = "usaResetDelete=true";
        message = String.format("Per abilitarlo, vai a %s.%s()%s%s", clazz, metodo, FORWARD, property);
        log.info(message);
    }

    protected void infoDisabilitaResetDelete() {
        String message = "Questo test ha il flag usaResetStartup=true mentre dovrebbe essere usaResetDelete=false";
        log.warn(message);
        String clazz = this.getClass().getSimpleName();
        String metodo = "setUpAll";
        String property = "usaResetDelete=false";
        message = String.format("Per disabilitarlo, vai a %s.%s()%s%s", clazz, metodo, FORWARD, property);
        log.info(message);
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
        System.out.println(VUOTA);
        String message = String.format("Test eseguito in %s", dateService.deltaTextEsatto(inizio));
        System.out.println(message);
    }


    @Nested
    @Order(10)
    @Tag("entity")
    @DisplayName("10 - entity")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class entity {

        @Test
        @Order(10)
        @DisplayName("10 - newEntity")
        void newEntity() {
            System.out.println("10 - newEntity");
            System.out.println(VUOTA);

            if (service == null) { //[NOTE] controllo che serve per i tests chiamati da tags
                System.out.println(NO_TEST);
                return;
            }

            AbstractEntity entityBean = service.newEntity();
            assertNotNull(entityBean);
            assertTrue(entityBean.getId() == null || entityBean.getId().equals("0"));
            String message = String.format("Creata (in memoria) una nuova entity (vuota) di classe [%s] ", entityClazzName);
            System.out.println(message);
            message = String.format("Viene creata con constructor.newInstance() per evitare di usare l'annotation @SpringComponent in [%s]", entityClazzName);
            System.out.println(message);
        }

        @Test
        @Order(11)
        @DisplayName("11 - entityAnnotation")
        void entityAnnotation() {
            System.out.println("11 - entityAnnotation");
            System.out.println(String.format("Annotation della POJO/Entity [%s]:", entityClazzName));
            String message;

            if (textService == null) { //[NOTE] controllo che serve per i tests chiamati da tags
                System.out.println(NO_TEST);
                return;
            }

            System.out.println(VUOTA);
            System.out.println(textService.setQuadre("@Document"));
            String ottenuto = annotationService.getCollectionName(entityClazz);
            message = String.format("%s%s%s", "collection", FORWARD, ottenuto);
            System.out.println(message);

            System.out.println(VUOTA);
            System.out.println(textService.setQuadre("@IReset"));
            boolean booleano = annotationService.usaResetStartup(entityClazz);
            message = String.format("%s%s%s", "usaStartup", FORWARD, booleano);
            System.out.println(message);

            System.out.println(VUOTA);
            System.out.println(textService.setQuadre("@ILog"));
            booleano = annotationService.usaLogModifiche(entityClazz);
            message = String.format("%s%s%s", "usaModifiche", FORWARD, booleano);
            System.out.println(message);

            int numero = (int) annotationService.getGiorniValiditaLog(entityClazz).orElse(0);
            message = String.format("%s%s%s", "giorniValidita", FORWARD, numero);
            System.out.println(message);

            System.out.println(VUOTA);
            System.out.println(textService.setQuadre("@IEntity"));
            ottenuto = (String) annotationService.getEntitySortProperty(entityClazz).orElse(VUOTA);
            message = String.format("%s%s%s", "sortProperty", FORWARD, ottenuto);
            System.out.println(message);

            SortDirection sortDirection = annotationService.getEntitySortDirection(entityClazz);
            message = String.format("%s%s%s", "sortDirection", FORWARD, sortDirection);
            System.out.println(message);

            ottenuto = annotationService.getFocusField(entityClazz).orElse(VUOTA);
            message = String.format("%s%s%s", "focus", FORWARD, textService.isValid(ottenuto) ? ottenuto : NULLO);
            System.out.println(message);

            ottenuto = annotationService.getSingularName(entityClazz);
            message = String.format("%s%s%s", "singularName", FORWARD, ottenuto);
            System.out.println(message);

            ottenuto = annotationService.getPluralName(entityClazz);
            message = String.format("%s%s%s", "pluralName", FORWARD, ottenuto);
            System.out.println(message);

            ottenuto = (String) annotationService.getKeyUniqueProperty(entityClazz).orElse(VUOTA);
            message = String.format("%s%s%s", "keyProperty", FORWARD, ottenuto);
            System.out.println(message);
        }

        @Test
        @Order(12)
        @DisplayName("12 - entityProperty")
        void entityProperty() {
            System.out.println("12 - entityProperty");
            System.out.println(String.format("Fields della POJO/Entity [%s]:", entityClazzName));
            System.out.println(VUOTA);
            String message;

            if (reflectionService == null) { //[NOTE] controllo che serve per i tests chiamati da tags
                System.out.println(NO_TEST);
                return;
            }

            List<String> stringArray = reflectionService.getFilteredPropertyNames(entityClazz);
            message = String.format("Nella classe [%s] (e superclassi) ci sono %d fields (String):", entityClazzName, stringArray.size());
            System.out.println(message);
            System.out.println(stringArray);
            System.out.println(VUOTA);

            List<Field> fieldArray = reflectionService.getAllProperties(entityClazz);
            message = String.format("Nella classe [%s] ci sono %d fields (property):", entityClazzName, fieldArray.size());
            System.out.println(message);
            printField(fieldArray);
        }

        @Test
        @Order(13)
        @DisplayName("13 - fieldAnnotation")
        void fieldAnnotation() {
            System.out.println("13 - fieldAnnotation");
            System.out.println(String.format("Fields della POJO/Entity [%s]:", entityClazzName));
            System.out.println(VUOTA);

            if (reflectionService == null) { //[NOTE] controllo che serve per i tests chiamati da tags
                System.out.println(NO_TEST);
                return;
            }

            List<String> fieldArray = reflectionService
                    .getFilteredProperties(entityClazz)
                    .stream()
                    .map(field -> field.getName())
                    .toList();
            message = String.format("Nella classe [%s] ci sono %d fields (property):", entityClazzName, fieldArray.size());
            System.out.println(message);
            System.out.println(fieldArray);
            print(fieldArray);
        }


        @Test
        @Tag("prefix")
        @Order(14)
        @DisplayName("14 - anchorPrefix")
        void anchorPrefix() {
            System.out.println("14 - anchorPrefix");
            String annotation = IAnchor.class.getSimpleName();
            System.out.println(String.format("Fields della POJO/Entity [%s] che usano l'annotation %s:", entityClazzName, annotation));
            System.out.println(VUOTA);
            String prefix;

            if (reflectionService == null) { //[NOTE] controllo che serve per i tests chiamati da tags
                System.out.println(NO_TEST);
                return;
            }

            List<String> fieldArrayAnchor = reflectionService
                    .getFilteredProperties(entityClazz)
                    .stream()
                    .map(field -> field.getName())
                    .filter(property -> annotationService.isAnchorField(entityClazz, property))
                    .toList();
            message = String.format("Nella classe [%s] ci sono %d fields (property) che usano l'annotation %s:", entityClazzName, fieldArrayAnchor.size(), annotation);
            System.out.println(message);
            System.out.println(fieldArrayAnchor);
            System.out.println(VUOTA);

            System.out.println("Prefix:");
            for (String anchorField : fieldArrayAnchor) {
                prefix = annotationService.getPrefix(entityClazz, anchorField).orElse(VUOTA);
                System.out.print(anchorField);
                System.out.print(FORWARD);
                System.out.println(prefix);
            }
        }


        @Test
        @Order(15)
        @DisplayName("15 - entityMethods")
        void entityMethods() {
            System.out.println("15 - entityMethods");
            System.out.println(String.format("Methods della POJO/Entity [%s]:", entityClazzName));
            System.out.println("Esclude i get(), set() e is() generati da Lombok");
            System.out.println("Esclude builder(), equals(), canEqual() e hashCode() generati da Lombok");
            System.out.println(VUOTA);
            String message;

            if (reflectionService == null) { //[NOTE] controllo che serve per i tests chiamati da tags
                System.out.println(NO_TEST);
                return;
            }

            List<Method> methodArray = reflectionService.getFilteredMethods(entityClazz);
            numMetodi(entityClazzName, methodArray.size());

            assertNotNull(methodArray);
            for (Method metodo : methodArray) {
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
        }
    }

    @Nested
    @Order(20)
    @Tag("view")
    @DisplayName("20 - view")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class view {
        @Test
        @Order(20)
        @DisplayName("20 - viewAnnotation")
        void viewAnnotation() {
            System.out.println("20 - viewAnnotation");
            System.out.println(String.format("Annotation della view/route [%s]:", viewClazzName));
            System.out.println(VUOTA);
            String message;

            System.out.println(textService.setQuadre("@IView"));

            String ottenuto = (String) annotationService.getMenuGroup(viewClazz).orElse(VUOTA);
            message = String.format("%s%s%s", "menuGroup", FORWARD, ottenuto);
            System.out.println(message);

            ottenuto = annotationService.getMenuName(viewClazz);
            message = String.format("%s%s%s", "menuName", FORWARD, ottenuto);
            System.out.println(message);

            boolean booleano = annotationService.usaMenuAutomatico(viewClazz);
            message = String.format("%s%s%s", "menuAutomatico", FORWARD, booleano);
            System.out.println(message);

            Icon icona = annotationService.getLumoIcon(viewClazz).orElse(null);
            message = String.format("%s%s%s", "lumo", FORWARD, icona);
            System.out.println(message);

            Icon icona2 = annotationService.getVaadinIcon(viewClazz).orElse(null);
            message = String.format("%s%s%s", "vaadin", FORWARD, icona2);
            System.out.println(message);

            Image image = annotationService.getModuleImage(viewClazz).orElse(null);
            message = String.format("%s%s%s", "image", FORWARD, image != null ? image : NULLO);
            System.out.println(message);
        }

        @Test
        @Order(21)
        @DisplayName("21 - viewProperty")
        void viewProperty() {
            System.out.println("21 - viewProperty");
            System.out.println(String.format("Fields della view/route [%s]:", viewClazzName));
            System.out.println(VUOTA);
            String message;

            List<String> stringArray = reflectionService.getFilteredPropertyNames(viewClazz);
            message = String.format("Nella classe [%s] (e superclassi) ci sono %d fields (String):", viewClazzName, stringArray.size());
            System.out.println(message);
            print(stringArray);
        }

        @Test
        @Order(22)
        @DisplayName("22 - viewMethods")
        void viewMethods() {
            System.out.println("22 - viewMethods");
            System.out.println(String.format("Methods della view/route [%s]:", viewClazzName));
            System.out.println(VUOTA);

            List<Method> methodArray = reflectionService.getFilteredMethods(viewClazz);
            numMetodi(viewClazzName, methodArray.size());
            printMethod(methodArray);
        }
    }

    @Nested
    @Order(30)
    @Tag("list")
    @DisplayName("30 - list")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class list {
        @Test
        @Tag("header")
        @Order(30)
        @DisplayName("30 - headerList")
        void headerList() {
            System.out.println("30 - headerList");
            message = String.format("Visualizzazione header della List [%s]", moduloName);
            System.out.println(message);
            System.out.println(VUOTA);

            if (listClazz == null) { //[NOTE] controllo che serve per i tests chiamati da tags
                System.out.println(NO_TEST);
                return;
            }

            AList lista = null;
            try {
                lista = (AList) appContext.getBean(listClazz);
            } catch (Exception exception) {
                log.error(exception.getMessage() + " nel test [headerList]");
            }
            assertNotNull(lista);

            VerticalLayout header;
            String riga;
//            header = wikiList.getHeaderPrimo();
//            riga = StyleExtractor.getStyledText(header);
//            System.out.println(riga);

//            header = wikiList.getHeaderSecondo();
//            riga = StyleExtractor.getStyledText(header);
//            System.out.println(riga);

//            header = wikiList.getHeaderTerzo();
//            riga = StyleExtractor.getStyledText(header);
//            System.out.println(riga);

//            header = wikiList.getHeaderQuarto();
//            riga = StyleExtractor.getStyledText(header);
//            System.out.println(riga);
        }
    }

    @Nested
    @Order(40)
    @Tag("form")
    @DisplayName("40 - form")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class form {
        @Test
        @Order(40)
        @DisplayName("40 - scheda")
        void scheda() {
            System.out.println("40 - scheda");
            System.out.println("Test per una scheda");
        }
    }

    @Nested
    @Order(50)
    @Tag("service")
    @DisplayName("50 - service")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class service {

        @Test
        @Order(50)
        @DisplayName("50 - count")
        void count() {
            System.out.println("50 - count");
            System.out.println(VUOTA);
            message = String.format("Nella collection [%s] ci sono in totale [%s] entities", collectionName, service.count());
            System.out.println(message);
        }

        @Test
        @Order(52)
        @DisplayName("52 - findAllNoSort")
        void findAllNoSort() {
            System.out.println("52 - findAllNoSort");
            System.out.println("Ricerca di tutte le entities della collection");
            System.out.println("Ordinamento hardcoded del database");
            System.out.println("Recupera tutte le entities della collection ma visualizza solo le prime 10");

            if (checkUsaDownload()) {
                return;
            }

            List<AbstractEntity> listaBeans = service.findAll();
            assertNotNull(listaBeans);

            if (listaBeans.isEmpty()) {
                collectionVuota();
            } else {
                int intero = listaBeans.size();
                collectionPiena(intero);
                printFewBeans(listaBeans);
            }
        }


        @Test
        @Order(53)
        @DisplayName("53 - findAllSort")
        void findAllSort() {
            System.out.println("53 - findAllSort");
            System.out.println("Ricerca di tutte le entities della collection");
            System.out.println(String.format("Ordinamento secondo la sortProperty di [%s]", listClazzName));
            System.out.println("Potrebbe essere uguale all'ordinamento hardcoded");
            System.out.println("Recupera tutte le entities della collection ma visualizza solo le prime 10");

            if (checkUsaDownload()) {
                return;
            }

            List<AbstractEntity> listaBeans = moduloUtilityService.findAllSort(entityClazz, listClazz);
            assertNotNull(listaBeans);

            if (listaBeans.isEmpty()) {
                collectionVuota();
            } else {
                int intero = listaBeans.size();
                collectionPiena(intero);
                printFewBeans(listaBeans);
            }
        }

        @Test
        @Order(54)
        @DisplayName("54 - findAllSortVariabile")
        void findAllSortVariabile() {
            System.out.println("54 - findAllSortVariabile");
            System.out.println("Ricerca di tutte le entities della collection");
            System.out.println("Ordinamento variabile, deciso al volo dal metodo chiamante");
            System.out.println("Recupera tutte le entities della collection ma visualizza solo le prime 10");

            if (checkUsaDownload()) {
                return;
            }

            //--per simulare un ordinamento secondo una property della entity
            //--cerco la prima property di tipo String ed eseguo il test solo se esiste
            Optional<String> property = moduloUtilityService.getFirstPropertyString(entityClazz);
            if (!property.isPresent()) {
                System.out.println(String.format("Non posso eseguire questo test di ordinamento perché nella classe [%s] non esiste nessuna property di tipo String", listClazzName));
                return;
            }
            Sort sort = Sort.by(property.get());
            List<AbstractEntity> listaBeans = moduloUtilityService.findAllSort(entityClazz, sort);
            assertNotNull(listaBeans);

            if (listaBeans.isEmpty()) {
                collectionVuota();
            } else {
                int intero = listaBeans.size();
                collectionPiena(intero);
                System.out.println(String.format("La lista è ordinata secondo la sortProperty [%s] (la prima property di tipo String)", property.get()));
                printFewBeans(listaBeans);
            }
        }


        @Test
        @Order(55)
        @DisplayName("55 - findLimitNoSort")
        void findLimitNoSort() {
            System.out.println("55 - findLimitNoSort");
            int limite = 5;
            System.out.println(String.format("Ricerca di [%s] entities della collection", limite));
            System.out.println("Ordinamento hardcoded del database");

            List<AbstractEntity> listaBeans = moduloUtilityService.findLimitNoSort(entityClazz, limite);
            assertNotNull(listaBeans);

            if (listaBeans.isEmpty()) {
                collectionVuota();
            } else {
                collectionPiena();
                printFewBeans(listaBeans);
            }
        }


        @Test
        @Order(56)
        @DisplayName("56 - findLimitSort")
        void findLimitSort() {
            System.out.println("56 - findLimitSort");
            int limite = 5;
            System.out.println(String.format("Ricerca di [%s] entities della collection", limite));
            System.out.println(String.format("Ordinamento secondo la sortProperty di [%s]", listClazzName));
            System.out.println("Potrebbe essere uguale all'ordinamento hardcoded");

            List<AbstractEntity> listaBeans = moduloUtilityService.findLimitSort(entityClazz, listClazz, limite);
            assertNotNull(listaBeans);

            if (listaBeans.isEmpty()) {
                collectionVuota();
            } else {
                collectionPiena();
                printFewBeans(listaBeans);
            }
        }


        @Test
        @Order(57)
        @DisplayName("57 - findLimitSortVariabile")
        void findLimitSortVariabile() {
            System.out.println("57 - findLimitSortVariabile");
            int limite = 5;
            System.out.println(String.format("Ricerca di [%s] entities della collection", limite));
            System.out.println("Ordinamento variabile, deciso al volo dal metodo chiamante");

            //--per simulare un ordinamento secondo una property della entity
            //--cerco la prima property di tipo String ed eseguo il test solo se esiste
            Optional<String> property = moduloUtilityService.getFirstPropertyString(entityClazz);
            if (!property.isPresent()) {
                System.out.println(String.format("Non posso eseguire questo test di ordinamento perché nella classe [%s] non esiste nessuna property di tipo String", entityClazzName));
                return;
            }
            Sort sort = Sort.by(property.get());
            List<AbstractEntity> listaBeans = moduloUtilityService.findLimitSort(entityClazz, limite, sort);
            assertNotNull(listaBeans);

            if (listaBeans.isEmpty()) {
                collectionVuota();
            } else {
                int intero = listaBeans.size();
                collectionPiena(intero);
                System.out.println(String.format("La lista è ordinata secondo la sortProperty [%s] (la prima property di tipo String)", property.get()));
                printFewBeans(listaBeans);
            }
        }
    }

    @Nested
    @Order(60)
    @DisplayName("60 - reset")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class reset {
    }

    @Nested
    @Order(70)
    @DisplayName("70 - download")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class download {

//        @Test
//        @Tag("download")
//        @Order(70)
//        @DisplayName("70 - download")
//        void download() {
//            System.out.println("70 - download");
//            System.out.println(VUOTA);
//
//            service.deleteAll();
//            assertTrue(service.count() == 0);
//            service.download();
//            assertTrue(service.count() > 0);
//            System.out.println(VUOTA);
//            message = String.format("Download dei dati di [%s]", moduloName);
//            System.out.println(message);
//        }
    }


    @Nested
    @Order(90)
    @Tag("upload")
    @DisplayName("90 - upload")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class upload {
        @Test
        @Order(90)
        @DisplayName("90 - upload")
        void task2() {
            System.out.println("90 - upload");
            System.out.println(VUOTA);
        }
    }

    @Nested
    @Order(200)
    @Tag("task")
    @DisplayName("200 - task")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class task {
        @Test
        @Order(200)
        @DisplayName("200 - task")
        void task2() {
            System.out.println("200 - task");
            System.out.println(VUOTA);
            Class annotation = ASchedule.class;
            Optional<String> optInfo = null;
            String suffix = SUFFIX_SERVICE;
            String message = String.format("Check della annotation %s nelle classi %s dei moduli", annotation.getSimpleName(), suffix);
            System.out.println(message);
            System.out.println(VUOTA);

            if (moduloClazz == null) {  //[NOTE] controllo che serve per i tests chiamati da tags
                return;
            }

            List<Method> metodi = utilityService.getAnnotatedMethods(moduloClazz, annotation);
            for (Method method : metodi) {
                optInfo = utilityService.getCronInfoDesc(method);
                log.info(optInfo.isPresent() ? optInfo.get() : NULLO);
            }
        }
    }

}
