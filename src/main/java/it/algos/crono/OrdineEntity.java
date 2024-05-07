package it.algos.crono;

import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import lombok.*;

/**
 * Project crono
 * Created by Algos
 * User: gac
 * Date: mar, 07-mag-2024
 * Time: 06:40
 */

@Data
public abstract class OrdineEntity extends AbstractEntity {

    @AField(type = TypeField.integer, headerText = "#", widthList = 4)
    private int ordine;

}
