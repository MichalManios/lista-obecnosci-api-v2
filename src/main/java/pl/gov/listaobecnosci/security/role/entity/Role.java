package pl.gov.listaobecnosci.security.role.entity;

import pl.gov.listaobecnosci.common.baseentity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.gov.listaobecnosci.security.users.entity.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table(name = "roles", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "description" })})
@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Role extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Size(max = 50)
    private String name;

    @NotNull
    @Column(unique = true)
    @Size(max = 100)
    private String description;

    @OneToOne(mappedBy = "role")
    private User user;
}
