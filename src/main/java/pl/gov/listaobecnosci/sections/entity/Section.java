package pl.gov.listaobecnosci.sections.entity;

import org.hibernate.validator.constraints.Length;
import pl.gov.listaobecnosci.common.baseentity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.gov.listaobecnosci.workers.entity.Worker;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table(name = "sections", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" })})
@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Section extends BaseEntity {

    @NotNull
    @Length(max = 50, min = 3)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Worker> workers;
}
