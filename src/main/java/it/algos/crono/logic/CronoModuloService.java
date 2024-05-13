package it.algos.crono.logic;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.logic.*;
import org.apache.commons.lang3.*;
import org.bson.types.*;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: lun, 13-mag-2024
 * Time: 11:40
 */
public abstract class CronoModuloService extends ModuloService {


    public CronoModuloService(final Class entityClazz, final Class viewClazz) {
        super(entityClazz, viewClazz);
    }

    protected ObjectId getObjectId(final String idStringValue) {
        ObjectId objectId;
        String idTextValue12Char;
        byte[] bytes;

        idTextValue12Char = idStringValue.substring(0, Math.min(idStringValue.length(), ID_LENGTH));
        idTextValue12Char = StringUtils.stripAccents(idTextValue12Char);
        idTextValue12Char = idTextValue12Char.replace("º", "o");
        idTextValue12Char = textService.fixSize(idTextValue12Char, ID_LENGTH);
        bytes = idTextValue12Char.getBytes();
        objectId = new ObjectId(bytes);

        return objectId;
    }

    //--eventuale - da discutere
    public AbstractEntity findById(final String idStringValue) {
        return findById(getObjectId(idStringValue));
    }

}
