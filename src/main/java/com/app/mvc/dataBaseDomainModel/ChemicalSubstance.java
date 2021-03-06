package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "chemical_substances")
public class ChemicalSubstance implements Serializable
{
    public ChemicalSubstance() {}

    private Long id;
    private String substanceName;
    private String chemicalFormula;
    private String substanceType;

    @JsonIgnore
    private Set<SubstanceInState> substanceInStates;
    @JsonIgnore
    private Set<PhysicalQuantity> physicalQuantities;

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id=id;
    }

    @Column(name = "substance_name")
    public String getSubstanceName(){
        return this.substanceName;
    }
    public void setSubstanceName(String name){
        this.substanceName =name;
    }

    @Column(name = "chemical_formula")
    public String getChemicalFormula(){
        return this.chemicalFormula;
    }
    public void setChemicalFormula(String subst_formula){
        this.chemicalFormula =subst_formula;
    }

    @Column(name = "substance_type")
    public String getSubstanceType() { return this.substanceType; }
    public void setSubstanceType(String subs_type) { this.substanceType =subs_type; }


    @OneToMany(mappedBy = "chemicalSubstance", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<SubstanceInState> getSubstanceInStates() {
        return substanceInStates;
    }
    public void setSubstanceInStates(Set<SubstanceInState> substanceInStates) { this.substanceInStates = substanceInStates; }

    public  void addSubstanceInState(SubstanceInState substanceInState){
        substanceInState.setChemicalSubstance(this);
        getSubstanceInStates().add(substanceInState);
    }
    public void removeSubstanceInState(SubstanceInState substanceInState){
        getSubstanceInStates().remove(substanceInState);
    }

    @ManyToMany
    @JoinTable(name = "chem_subst_quantities",
            joinColumns = @JoinColumn(name = "substance_id"),
            inverseJoinColumns = @JoinColumn(name = "quantity_id"))
    public Set<PhysicalQuantity> getPhysicalQuantities(){
        return this.physicalQuantities;
    }
    public void setPhysicalQuantities(Set<PhysicalQuantity> physicalQuantities) { this.physicalQuantities = physicalQuantities; }

}
