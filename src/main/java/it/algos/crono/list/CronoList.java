package it.algos.crono.list;

import it.algos.vbase.backend.list.*;
import it.algos.vbase.ui.view.*;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 18:32
 */
public abstract class CronoList extends CrudList {


    /**
     * @param parentCrudView che crea questa istanza
     */
    public CronoList(final CrudView parentCrudView) {
        super(parentCrudView);
    }


    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaVariantCompact = false;
        super.usaBottoneResetDelete = true;
        super.usaBottoneNew = false;
        super.usaBottoneEdit = false;
        super.usaBottoneShow = true;
        super.usaBottoneDeleteEntity = false;
    }

}



