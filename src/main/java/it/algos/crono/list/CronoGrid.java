package it.algos.crono.list;

import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.grid.AGrid;
import it.algos.vbase.backend.grid.ColumnDefinition;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: gio, 25-lug-2024
 * Time: 14:39
 */
@Component
@Scope(value = SCOPE_PROTOTYPE)
public class CronoGrid extends AGrid<AbstractEntity> {

    public CronoGrid(Class<AbstractEntity> entityClazz, List<ColumnDefinition> columnDefinitions) {
        super(entityClazz, columnDefinitions);
    }

    @PostConstruct
    private void init() {
        setSelectionMode(SelectionMode.SINGLE);
    }
}


