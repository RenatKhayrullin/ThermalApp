package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.transform.sax.SAXTransformerFactory;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "substance_in_state",
        uniqueConstraints = @UniqueConstraint(columnNames = {"substance_id", "state_id"}))
public class SubstanceInState implements Serializable {

    private Long id;
    private CrystalStructure crystalStructure;
    private MagneticStructure magneticStructure;
    private ChemicalSubstance chemicalSubstance;
    private State state;
    private String additionalName;

    @JsonIgnore
    private Set<DataSet> dataSets;// = new HashSet<DataSet>();

    @JsonIgnore
    private Set<ControlFunctionDefinition> controlFunctionDefinitions;

    public SubstanceInState(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Column(name = "additional_name")
    public String getAdditionalName() { return this.additionalName; }
    public void setAdditionalName(String additionalName) { this.additionalName =additionalName; }

    @ManyToOne
    @JoinColumn(name = "substance_id")
    public ChemicalSubstance getChemicalSubstance(){return this.chemicalSubstance;}
    public void setChemicalSubstance(ChemicalSubstance chemicalSubstance) { this.chemicalSubstance = chemicalSubstance; }

    @ManyToOne
    @JoinColumn(name = "state_id")
    public State getState(){return  this.state;}
    public void setState(State state){
        this.state = state;
    }

    @ManyToOne
    @JoinColumn(name = "crystal_struct_id")
    public CrystalStructure getCrystalStructure() { return this.crystalStructure; }
    public void setCrystalStructure(CrystalStructure crystalStructure) { this.crystalStructure =crystalStructure; }

    @ManyToOne
    @JoinColumn(name = "magnetic_struct_id")
    public MagneticStructure getMagneticStructure() { return this.magneticStructure; }
    public void setMagneticStructure(MagneticStructure magneticStructure) { this.magneticStructure =magneticStructure; }

    @OneToMany(mappedBy = "substanceInState", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<DataSet> getDataSets() {return dataSets;}
    public void setDataSets(Set<DataSet> dataSets) {
        this.dataSets = dataSets;
    }

    public void addDataSet(DataSet dataSet){
        dataSet.setSubstanceInState(this);
        getDataSets().add(dataSet);
    }
    public void removeDataSet(DataSet dataSet){
        getDataSets().remove(dataSet);
    }

    @OneToMany(mappedBy = "substanceInState", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<ControlFunctionDefinition> getControlFunctionDefinitions() { return controlFunctionDefinitions; }
    public void setControlFunctionDefinitions(Set<ControlFunctionDefinition> controlFunctionDefinitions) {
        this.controlFunctionDefinitions = controlFunctionDefinitions;
    }

}
