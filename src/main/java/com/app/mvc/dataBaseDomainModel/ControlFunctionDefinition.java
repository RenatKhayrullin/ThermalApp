package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "control_function_definition")
public class ControlFunctionDefinition implements Serializable{

    private Long id;
    private SubstanceInState substanceInState;
    private String functionFormula;
    private UncertaintyType uncertaintyType;
    private String uncertaintyValue;

    @JsonIgnore
    private Set<DomainOfControlFunctionDefinition> domainsOfControlFunctionDefinitions;

    public ControlFunctionDefinition() {}

    @Id
    @GeneratedValue
    @Column(name = "control_function_id")
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = this.id; }

    @ManyToOne
    @JoinColumn(name = "substance_in_state_id")
    public SubstanceInState getSubstanceInState() { return substanceInState; }
    public void setSubstanceInState(SubstanceInState substanceInState) { this.substanceInState = substanceInState; }

    @Column(name = "function_formula")
    public String getFunctionFormula() { return functionFormula; }
    public void setFunctionFormula(String functionFormula) { this.functionFormula = functionFormula; }

    @ManyToOne
    @JoinColumn(name = "uncertainty_type_id")
    public UncertaintyType getUncertaintyType() { return uncertaintyType; }
    public void setUncertaintyType(UncertaintyType uncertaintyType) { this.uncertaintyType = uncertaintyType; }

    @Column(name = "uncertainty_value")
    public String getUncertaintyValue() { return uncertaintyValue; }
    public void setUncertaintyValue(String uncertaintyValue) { this.uncertaintyValue = uncertaintyValue; }

    @OneToMany(mappedBy = "controlFunction", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<DomainOfControlFunctionDefinition> getDomainsOfControlFunctionDefinitions() { return domainsOfControlFunctionDefinitions; }
    public void setDomainsOfControlFunctionDefinitions(Set<DomainOfControlFunctionDefinition> domainsOfControlFunctionDefinitions) {
        this.domainsOfControlFunctionDefinitions = domainsOfControlFunctionDefinitions;
    }

    public void addDomainOfControlFunctionDefinition(DomainOfControlFunctionDefinition domainOfControlFunctionDefinition){
        domainOfControlFunctionDefinition.setControlFunction(this);
        getDomainsOfControlFunctionDefinitions().add(domainOfControlFunctionDefinition);
    }

    public void removeDomainOfControlFunctionDefinition(DomainOfControlFunctionDefinition domainOfControlFunctionDefinition) {
        getDomainsOfControlFunctionDefinitions().remove(domainOfControlFunctionDefinition);
    }
}
