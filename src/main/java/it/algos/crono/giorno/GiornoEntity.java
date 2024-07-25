package it.algos.crono.giorno;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.crono.mese.MeseEntity;
import it.algos.crono.secolo.SecoloService;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.entity.OrdineEntity;
import it.algos.vbase.backend.enumeration.TypeSearch;
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
@AEntity()
public class GiornoEntity extends AbstractEntity {

    @Indexed(unique = true)
    @AFieldList(width = 4, headerText = "#")
    @AFieldForm(label = GiornoService.ORDINE)
    private int ordine;

    @Indexed(unique = true)
    @ASearch()
    @AFieldList(width = 12)
    @AFieldForm(label = "Nome corrente")
    private String nome;

    @DBRef
    @ASearch(type = TypeSearch.comboClazz, linkClazz = MeseEntity.class, comboPlaceHolder = "Mesi")
    @AFieldList(width = 10)
    private MeseEntity mese;

    @AFieldList(width = 6, headerIcon = VaadinIcon.STEP_BACKWARD)
    @AFieldForm(label = "Progressivo da inizio anno")
    private int trascorsi;

    @AFieldList(width = 6, headerIcon = VaadinIcon.STEP_FORWARD)
    @AFieldForm(label = "Mancanti alla fine dell'anno")
    private int mancanti;


    @Override
    public String toString() {
        return nome;
    }

}// end of Entity class
