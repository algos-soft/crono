package it.algos.crono.giorno;

import it.algos.crono.anno.AnnoService;
import it.algos.crono.mese.MeseEntity;
import it.algos.crono.mese.MeseService;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.RefSearchType;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "giorno")
@AReset()
@AEntity(sortProperty = "ordine")
public class GiornoEntity extends AbstractEntity {

    @Indexed(unique = true)
    @AFieldList(width = 4, headerText = "#")
    @AFieldForm(label = GiornoService.ORDINE, width = 20)
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList(headerText = "Giorno")
    @AFieldForm(label = "Giorno corrente")
    private String nome;

    @DBRef
    @ARef(linkClazz = MeseService.class, linkedProperty = "nome")
    @ASearch(refSearchType = RefSearchType.combo, placeholder = "Mesi")
    @AFieldList(headerText = "Mese", width = 10)
    @AFieldForm(clearButtonCombo = false)
    private MeseEntity mese;

    @AFieldForm(label = "Progressivo da inizio anno")
    private int trascorsi;

    @AFieldForm(label = "Mancanti a fine anno")
    private int mancanti;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
