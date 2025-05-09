package it.algos.crono.list;

import it.algos.crono.mese.MeseEntity;
import it.algos.vbase.annotation.IList;
import it.algos.vbase.constant.Bottone;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.form.AForm;
import it.algos.vbase.grid.AGrid;
import it.algos.vbase.list.AList;
import it.algos.vbase.list.RecordCounter;
import it.algos.vbase.list.SelectedRecordCounter;
import it.algos.vbase.service.ModuloService;
import it.algos.vbase.ui.view.AView;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 18:32
 */
@IList(bottoni = {Bottone.RESET_DELETE, Bottone.SHOW})
public class CronoList<T extends AbstractEntity> extends AList<T> {

    protected String infoCreazione;

    protected String infoReset;


    public CronoList() {
        this(null);
    }

    /**
     * @param parentView che crea questa istanza
     */
    public CronoList(final AView parentView) {
        super(parentView);
    }

    public CronoList(Class<T> entityClass, ModuloService<T> moduloService) {
        super(entityClass, moduloService);
    }


    @Override
    protected Class<? extends AGrid> getGridClass() {
        return CronoGrid.class;
    }

    @Override
    protected RecordCounter creaRecordCounter() {
        return new SelectedRecordCounter(grid);
    }

}



