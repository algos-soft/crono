package it.algos.crono.prova;

import it.algos.crono.mese.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@AEntity(collectionName = "provacrono")
public class ProvaCronoEntity extends AbstractEntity {


    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AField(type = TypeField.text, headerText = "Nome", caption = "Nome")
    private String code;


    @ASearch(type = TypeSearch.textContains)
    @AField(type = TypeField.text)
    public String descrizione;

    @DBRef
    @ASearch(type = TypeSearch.comboClazz)
    @AField(type = TypeField.linkDBRef, linkClazz = MeseEntity.class)
    private MeseEntity meseCon;

    @ASearch(type = TypeSearch.comboClazz)
    @AField(type = TypeField.linkDBRef, linkClazz = MeseEntity.class)
    private MeseEntity meseSenza;

    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
