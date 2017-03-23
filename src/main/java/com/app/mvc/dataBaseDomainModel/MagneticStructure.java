package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "magnetic_structure")
public class MagneticStructure implements Serializable{

    private Long id;
    private String magneticType;

    @JsonIgnore
    private Set<SubstanceInState> substanceInStates;

    public MagneticStructure() {}

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id =id; }

    @Column(name = "magnetic_type")
    public String getMagneticType() { return this.magneticType; }
    public void setMagneticType(String magneticType) {this.magneticType =magneticType; }

    @OneToMany(mappedBy = "magneticStructure", cascade = CascadeType.ALL,
        orphanRemoval = true)
    public Set<SubstanceInState> getSubstanceInStates() { return this.substanceInStates; }
    public void setSubstanceInStates(Set<SubstanceInState> substanceInStates) { this.substanceInStates =substanceInStates; }

    public void addData(SubstanceInState substanceInState){
        substanceInState.setMagneticStructure(this);
        getSubstanceInStates().add(substanceInState);
    }
    public void removeData(SubstanceInState substanceInState) { getSubstanceInStates().remove(substanceInState); }
}
