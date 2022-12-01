package pl.gov.listaobecnosci.workers.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import pl.gov.listaobecnosci.common.baseentity.BaseEntity;
import pl.gov.listaobecnosci.functions.model.Function;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.gov.listaobecnosci.sections.entity.Section;

@Table(name = "workers")
@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Worker extends BaseEntity {

    @NotNull
    @Length(max = 25, min = 3)
    private String name;

    @NotNull
    @Length(max = 50, min = 3)
    private String surname;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private Section section;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Function function;
}
