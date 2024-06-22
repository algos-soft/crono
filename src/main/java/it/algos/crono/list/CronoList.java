package it.algos.crono.list;

import it.algos.vbase.backend.list.CrudList;
import it.algos.vbase.ui.view.CrudView;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Tue, 28-Nov-2023
 * Time: 18:32
 */
public abstract class CronoList extends CrudList {


    /**
     * @param parentView che crea questa istanza
     */
    public CronoList(final CrudView parentView) {
        super(parentView);
    }


    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaSelettoreColonne = true;
        super.usaVariantCompact = false;
        super.usaBottoneResetDelete = true;
        super.usaBottoneNew = false;
        super.usaBottoneEdit = false;
        super.usaBottoneShow = true;
        super.usaBottoneDeleteEntity = false;
    }

}



