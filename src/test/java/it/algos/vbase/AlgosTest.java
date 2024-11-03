package it.algos.vbase;

import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.logic.ModuloService;
import it.algos.vbase.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.Transient;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import static it.algos.vbase.boot.BaseCost.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: ven, 27-ott-2023
 * Time: 07:16
 */
public abstract class AlgosTest {

    @Autowired
    protected ApplicationContext appContext;

    @Autowired
    protected LoggerService logger;

    @Autowired
    protected TextService textService;

    @Autowired
    protected AnnotationService annotationService;

    @Autowired
    protected ReflectionService reflectionService;

    @Autowired
    protected DateService dateService;

    protected String message;

    public static final String GIUSTO = "(giusto)";

    public static final String SBAGLIATO = "(sbagliato)";

    protected Class clazz;

    protected Object object;

    protected String clazzName;


    protected String sorgente;

    protected String sorgente2;

    protected String sorgente3;

    protected String sorgente4;

    protected String previsto;

    protected String previsto2;

    protected String ottenuto;

    protected String ottenuto2;

    protected long sorgenteLong;

    protected boolean sorgenteBooleano;

    protected boolean previstoBooleano;

    protected boolean ottenutoBooleano;

    protected int sorgenteIntero;

    protected int previstoIntero;

    protected int ottenutoIntero;

    protected long inizio;

    protected List<String> sorgenteArray;

    protected List<String> previstoArray;

    protected List<String> ottenutoArray;

    protected List<String> listaStr;

    protected List<List<String>> listaTable;

    protected Map<String, List<String>> mappa;

    protected List<AbstractEntity> listaEntity;

    protected List<Field> fieldsArray;

    protected List<Class<?>> listaClazz;
    protected int pos;

    /**
     * Qui passa a ogni test delle sottoclassi <br>
     * Invocare PRIMA il metodo setUp() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    protected void setUpEach() {
        inizio = System.currentTimeMillis();
        sorgente = VUOTA;
        sorgente2 = VUOTA;
        sorgente3 = VUOTA;
        sorgente4 = VUOTA;
        previsto = VUOTA;
        previsto2 = VUOTA;
        ottenuto = VUOTA;
        sorgenteIntero = 0;
        previstoIntero = 0;
        ottenutoIntero = 0;
        clazz = null;
        sorgenteArray = null;
        previstoArray = null;
        ottenutoArray = null;
        listaStr = null;
        mappa = null;
        message = VUOTA;
        fieldsArray = null;
        listaTable = null;
        listaEntity = null;
        object = null;
        listaClazz = null;
        pos = 0;
    }

    protected void printClazz(List<Class<?>> listaClazz) {
        int k = 0;

        for (Class clazz : listaClazz) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(clazz.getSimpleName());
        }
    }

    protected void printClazzService(List<Class<? extends ModuloService>> listaClazz) {
        int k = 0;

        for (Class clazz : listaClazz) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(clazz.getSimpleName());
        }
    }

    protected void printClazzEntity(List<Class<? extends AbstractEntity>> listaClazz) {
        int k = 0;

        for (Class clazz : listaClazz) {
            System.out.print(++k);
            System.out.print(PARENTESI_TONDA_END);
            System.out.print(SPAZIO);
            System.out.println(clazz.getSimpleName());
        }
    }

    protected void printFieldName(List<Field> listaField) {
        for (Field field : listaField) {
            System.out.println(field.getName());
        }
    }

    protected void printField(List<Field> listaField) {
        System.out.println(VUOTA);
        int k = 0;
        int modifiers;

        for (Field field : listaField) {
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

//            if (annotation != null) {
//                message = String.format("%s%s%s%s", TAB_SPAZIO, "type", FORWARD, annotation.type());
//                System.out.println(message);
//                message = String.format("%s%s%s%d", TAB_SPAZIO, "widthRem", FORWARD, annotation.widthList());
//                System.out.println(message);
//                message = String.format("%s%s%s%s", TAB_SPAZIO, "headerText", FORWARD, annotation.headerText());
//                System.out.println(message);
//                message = String.format("%s%s%s%s", TAB_SPAZIO, "headerIcon", FORWARD, annotation.headerIcon());
//                System.out.println(message);
//                message = String.format("%s%s%s%s", TAB_SPAZIO, "caption", FORWARD, annotation.caption());
//                System.out.println(message);
//                message = String.format("%s%s%s%s", TAB_SPAZIO, "linkClazz", FORWARD, annotation.linkClazz());
//                System.out.println(message);
//                message = String.format("%s%s%s%s", TAB_SPAZIO, "enumClazz", FORWARD, annotation.enumClazz());
//                System.out.println(message);
//                message = String.format("%s%s%s%s", TAB_SPAZIO, "typeBool", FORWARD, annotation.typeBool());
//                System.out.println(message);
//                message = String.format("%s%s%s%s", TAB_SPAZIO, "typeDate", FORWARD, annotation.typeDate());
//                System.out.println(message);
//            }
        }
    }


//    protected void printAField(final Class clazz, final String sorgente, final AField annotation) {
//        if (clazz == null || sorgente == null || sorgente == VUOTA || annotation == null) {
//            return;
//        }
//
//        message = String.format("Annotation della property '%s' della classe [%s]:", sorgente, clazz.getSimpleName());
//        System.out.println(message);
//
//        message = String.format("Annotation%s%s", FORWARD, annotation.annotationType());
//        System.out.println(message);
//        message = String.format("type%s%s", FORWARD, annotation.type());
//        System.out.println(message);
//        message = String.format("widthRem%s%s", FORWARD, annotation.widthList());
//        System.out.println(message);
//        message = String.format("header%s%s", FORWARD, annotation.headerText());
//        System.out.println(message);
//    }


    /**
     * Check generico dei parametri.
     *
     * @param genericClazz da controllare
     * @param testName     the test name
     * @return false se mancano parametri o non sono validi
     */
    protected boolean check(final Class genericClazz, final String testName) {
        String message;

        // Controlla che la classe in ingresso non sia nulla
        if (genericClazz == null) {
            message = String.format("Nel test '%s' di [%s], manca la model clazz '%s'", testName, this.getClass().getSimpleName(), "(null)");
            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
            return false;
        }

        // Controlla che la classe in ingresso implementi AlgosModel
        //        if (CrudModel.class.isAssignableFrom(genericClazz)) {
        //        }
        //        else {
        //            message = String.format("Nel test '%s' di [%s], la classe '%s' non implementa AlgosModel", testName, this.getClass().getSimpleName(), genericClazz.getSimpleName());
        //            System.out.println(message);//@todo Da modificare dopo aver realizzato logService
        //            return false;
        //        }

        return true;
    }

    protected void printLista(final List lista) {
        int cont = 0;

        if (lista != null) {
            if (lista.size() > 0) {
                System.out.println(String.format("La lista contiene %d elementi", lista.size()));
                System.out.println(SPAZIO);
                for (Object obj : lista) {
                    cont++;
                    System.out.print(cont);
                    System.out.print(PARENTESI_TONDA_END);
                    System.out.print(SPAZIO);
                    System.out.println(obj);
                }
            } else {
                System.out.println("Non ci sono elementi nella lista");
            }
        } else {
            System.out.println("Manca la lista");
        }
    }

    protected void printMappa(Map<String, Object> mappa) {
        if (mappa != null && mappa.size() > 0) {
            for (String key : mappa.keySet()) {
                System.out.println(String.format("%s%s%s", key, FORWARD, mappa.get(key)));
            }
        }
    }

//    protected void printClazz(List<Class<?>> listaClazz) {
//        if (listaClazz != null && listaClazz.size() > 0) {
//            for (Class clazz : listaClazz) {
//                System.out.println(clazz.getSimpleName());
//            }
//        }
//    }

}
