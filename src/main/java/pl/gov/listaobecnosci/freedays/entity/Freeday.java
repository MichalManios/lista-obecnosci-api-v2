package pl.gov.listaobecnosci.freedays.entity;

import pl.gov.listaobecnosci.common.baseentity.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Table(name = "freeday", uniqueConstraints = { @UniqueConstraint(columnNames = { "nameOfHoliday" })})
@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Freeday extends BaseEntity {

    @NotNull
    private int day;

    @NotNull
    private int month;

    @NotNull
    private String nameOfHoliday;
}
