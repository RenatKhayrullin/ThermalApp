package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "function_definition")
public class FunctionDefinition implements Serializable{

    private Long id;
    private SubstanceInState substanceInState;
    private String functionFormula;
    private Dimension standartDimension;
    private UncertaintyType uncertaintyType;
    private String uncertaintyValue;
    private PhysicalQuantity funcQuantity;

    @JsonIgnore
    private Set<DomainOfFunctionDefinition> domainsOfFunctionDefinitions;

    public FunctionDefinition() {}

    @Id
    @GeneratedValue
    @Column(name = "function_id")
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
    @JoinColumn(name = "standart_dimension_id")
    public Dimension getStandartDimension() { return standartDimension; }
    public void setStandartDimension(Dimension standartDimension) { this.standartDimension = standartDimension; }

    @ManyToOne
    @JoinColumn(name = "uncertainty_type_id")
    public UncertaintyType getUncertaintyType() { return uncertaintyType; }
    public void setUncertaintyType(UncertaintyType uncertaintyType) { this.uncertaintyType = uncertaintyType; }

    @Column(name = "uncertainty_value")
    public String getUncertaintyValue() { return uncertaintyValue; }
    public void setUncertaintyValue(String uncertaintyValue) { this.uncertaintyValue = uncertaintyValue; }

    @ManyToOne
    @JoinColumn(name = "func_quantity_id")
    public PhysicalQuantity getFuncQuantity() { return funcQuantity; }
    public void setFuncQuantity(PhysicalQuantity funcQuantity) { this.funcQuantity = funcQuantity; }

    @OneToMany(mappedBy = "function", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<DomainOfFunctionDefinition> getDomainsOfFunctionDefinitions() { return domainsOfFunctionDefinitions; }
    public void setDomainsOfFunctionDefinitions(Set<DomainOfFunctionDefinition> domainsOfFunctionDefinitions) {
        this.domainsOfFunctionDefinitions = domainsOfFunctionDefinitions;
    }

    public void addDomainOfFunctionDefinition(DomainOfFunctionDefinition domainOfFunctionDefinition){
        domainOfFunctionDefinition.setFunction(this);
        getDomainsOfFunctionDefinitions().add(domainOfFunctionDefinition);
    }

    public void removeDomainOfFunctionDefinition(DomainOfFunctionDefinition domainOfFunctionDefinition) {
        getDomainsOfFunctionDefinitions().remove(domainOfFunctionDefinition);
    }
}
