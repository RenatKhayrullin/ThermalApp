package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "crystal_structure")
public class CrystalStructure implements Serializable{

    private Long id;
    private String crystalStructure;

    @JsonIgnore
    private Set<SubstanceInState> substanceInStates = new HashSet<SubstanceInState>();

    public CrystalStructure() {}

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long getId() {return this.id; }
    public void setId(Long id) { this.id =id; }

    @Column(name = "crystal_structure")
    public String getCrystalStructure() { return this.crystalStructure; }
    public void setCrystalStructure(String crystalStructure) { this.crystalStructure =crystalStructure; }

    @OneToMany(mappedBy = "crystalStructure", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<SubstanceInState> getSubstanceInStates() { return this.substanceInStates; }
    public void setSubstanceInStates(Set<SubstanceInState> substanceInStates) { this.substanceInStates =substanceInStates; }

    public void addData(SubstanceInState substanceInState) {
        substanceInState.setCrystalStructure(this);
        getSubstanceInStates().add(substanceInState);
    }
    public void removeData(SubstanceInState substanceInState) {getSubstanceInStates().remove(substanceInState); }
}
