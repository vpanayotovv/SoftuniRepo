package mostwanted.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "districts")
@Getter
@Setter
@NoArgsConstructor
public class District  extends  BaseEntity{

    @Column(nullable = false,unique = true)
    private String name;

    @ManyToOne
    private Town town;

}
