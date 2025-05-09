package it.algos.crono;

import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.list.AList;
import it.algos.vbase.service.AnnotationService;
import it.algos.vbase.service.ReflectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: ven, 08-nov-2024
 * Time: 18:38
 * <p>
 * Supporto per i test
 * Sperimento metodi che possono/potrebbero essere spostati in [ModuloService]
 */
@Slf4j
@Service
public class ModuloUtilityService {

    @Autowired
    private AnnotationService annotationService;

    @Autowired
    private ReflectionService reflectionService;

    @Autowired
    private MongoTemplate mongoTemplate;


    //--metodo già esistente
    //--messo qui solo per omogeneità con gli altri metodi di questa classe usati nei test
    //--eventualmente (se si spostano gli altri metodi in ModuloService) si potrebbe rinominarlo [findAllNoSort]
    public List<? extends AbstractEntity> findAll(Class<? extends AbstractEntity> entityClazz) {
        return mongoTemplate.findAll(entityClazz);

    }


    //--se spostato in ModuloService, questo metodo ha come parametro in ingresso solo la [listClazz]
    //--e usa la variabile d'istanza [entityClazz] già presente nel modulo stesso
    public List<? extends AbstractEntity> findAllSort(Class<? extends AbstractEntity> entityClazz, Class<? extends AList> listClazz) {
        Optional<Sort> sort = annotationService.getListSort(listClazz);
        return findAllSort(entityClazz, sort.orElse(null));
    }


    //--se spostato in ModuloService, questo metodo ha solo Sort come parametro in ingresso
    //--e usa la variabile d'istanza [entityClazz] già presente nel modulo stesso
    public List<? extends AbstractEntity> findAllSort(Class<? extends AbstractEntity> entityClazz, Sort sort) {
        Query query = new Query();
        if (sort != null) {
            query.with(sort);
        }
        return mongoTemplate.find(query, entityClazz);
    }


    //--se spostato in ModuloService, questo metodo ha come parametri in ingresso solo [limit]
    //--e usa la variabile d'istanza [entityClazz] già presente nel modulo stesso
    public List<? extends AbstractEntity> findLimitNoSort(Class<? extends AbstractEntity> entityClazz, int limit) {
        Query query = new Query();
        query.limit(limit);
        return this.mongoTemplate.find(query, entityClazz);
    }


    //--se spostato in ModuloService, questo metodo ha come parametri in ingresso la [listClazz] e [limit]
    //--e usa la variabile d'istanza [listClazz] già presente e il modulo stesso
    public List<? extends AbstractEntity> findLimitSort(Class<? extends AbstractEntity> entityClazz, Class<? extends AList> listClazz, int limit) {
        Optional<Sort> sort = annotationService.getListSort(listClazz);
        return findLimitSort(entityClazz, limit, sort.orElse(null));
    }


    //--se spostato in ModuloService, questo metodo ha come parametri in ingresso [limit] e [sort]
    //--e usa la variabile d'istanza [listClazz] già presente e il modulo stesso
    public List<? extends AbstractEntity> findLimitSort(Class<? extends AbstractEntity> entityClazz, int limit, Sort sort) {
        Query query = new Query();
        if (sort != null) {
            query.with(sort);
        }
        query.limit(limit);
        return this.mongoTemplate.find(query, entityClazz);
    }


    //--prima property di tipo [String] presente (eventualmente) in una entity
    public Optional<String> getFirstPropertyString(Class<? extends AbstractEntity> entityClazz) {
        return Arrays.stream(entityClazz.getDeclaredFields())
                .filter(field -> field.getType().equals(String.class))
                .map(Field::getName)
                .findFirst();
    }

}
