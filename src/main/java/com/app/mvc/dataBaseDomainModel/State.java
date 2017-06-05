package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "\"state\"")
public class State implements Serializable {

	private Long id;
    private String phaseName;
    private String phaseType;
    @JsonIgnore
    private Set<QuantityState> quantityState = new HashSet<QuantityState>();
    @JsonIgnore
    private Set<SubstanceInState> substanceInStates = new HashSet<SubstanceInState>();

    public State() {}

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id=id;
    }

    @Column(name = "phase_name")
    public String getPhaseName(){
        return this.phaseName;
    }
    public void setPhaseName(String name){
        this.phaseName =name;
    }


    @Column(name = "phase_type")
    public String getPhaseType() { return this.phaseType; }
    public void setPhaseType(String phaseType) {
        this.phaseType = phaseType;
    }

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<QuantityState> getQuantityState() { return this.quantityState; }
    public void setQuantityState(Set<QuantityState> quantityState) { this.quantityState =quantityState; }
    public void addData(QuantityState quantityState) {
        quantityState.setState(this);
        getQuantityState().add(quantityState);
    }
    public void removeData(QuantityState quantityState) { getQuantityState().remove(quantityState); }


    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<SubstanceInState> getSubstanceInStates() {
        return substanceInStates;
    }
    public void setSubstanceInStates(Set<SubstanceInState> substanceInStates) { this.substanceInStates = substanceInStates; }
    public  void addMetaData(SubstanceInState substanceInState){
        substanceInState.setState(this);
        getSubstanceInStates().add(substanceInState);
    }
    public void removeMetaData(SubstanceInState substanceInState){
        getSubstanceInStates().remove(substanceInState);
    }
}
