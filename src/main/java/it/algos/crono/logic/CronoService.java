package it.algos.crono.logic;

import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.logic.ModuloService;
import it.algos.vbase.backend.wrapper.WrapLog;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import static it.algos.vbase.backend.boot.BaseCost.ID_LENGTH;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: lun, 13-mag-2024
 * Time: 11:40
 */
public abstract class CronoService<T extends AbstractEntity> extends ModuloService<T> {

    protected String keyPropertyName;

    @Getter
    protected String collectionNameParent;

    public CronoService(final Class entityClazz, final Class viewClazz) {
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
    public T findById(final String idStringValue) {
        return findById(getObjectId(idStringValue));
    }

    public T findByKey(final String keyValue) {
        if (textService.isValid(keyPropertyName)) {
            return super.findOneByProperty(keyPropertyName, keyValue);
        } else {
            logger.warn(new WrapLog().message("Manca la keyPropertyName del modulo"));
            return null;
        }
    }

}
