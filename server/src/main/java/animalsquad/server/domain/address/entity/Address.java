package animalsquad.server.domain.address.entity;

import animalsquad.server.domain.infomap.entity.InfoMap;
import animalsquad.server.domain.pet.entity.Pet;
import animalsquad.server.global.audit.Auditable;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address extends Auditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int code;
    private int name;

    @OneToOne(mappedBy = "pet")
    private Pet pet;

    @OneToMany(mappedBy = "address")
    private List<InfoMap> infoMaps = new ArrayList<>();

}