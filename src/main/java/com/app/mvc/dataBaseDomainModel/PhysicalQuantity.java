package com.app.mvc.dataBaseDomainModel;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "physical_quantity")
public class PhysicalQuantity implements Serializable {

	private Long id;
    private String quantityName;
    private String quantityDesignation;
    private String quantityType;

    @JsonIgnore
    private Set<Dimension> dimensions;//= new HashSet<Dimension>();
    @JsonIgnore
    private Set<QuantityState> funcQuantity;// = new HashSet<QuantityState>();
    @JsonIgnore
    private Set<QuantityState> funcArgument;// = new HashSet<QuantityState>();
    @JsonIgnore
    private Set<PointOfMeasure> pointsOfMeasure;// = new HashSet<PointOfMeasure>();
    @JsonIgnore
    private Set<ChemicalSubstance> chemicalSubstances;// = new HashSet<ChemicalSubstance>();

    public PhysicalQuantity() {}

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    @Column(name = "quantity_name")
    public String getQuantityName(){
        return this.quantityName;
    }
    public void setQuantityName(String quantityName){
        this.quantityName = quantityName;
    }

    @Column(name = "quantity_designation")
    public String getQuantityDesignation(){
        return this.quantityDesignation;
    }
    public void setQuantityDesignation(String quantityDesignation){
        this.quantityDesignation = quantityDesignation;
    }

    @Column(name = "quantity_type")
    public String getQuantityType() { return this.quantityType; }
    public void setQuantityType(String quantityType) { this.quantityType =quantityType; }


    @ManyToMany
    @JoinTable(name = "quantity_dimension",
    			joinColumns = @JoinColumn(name = "quantity_id"),
    			inverseJoinColumns = @JoinColumn(name = "dimension_id"))
    public Set<Dimension> getDimensions() { return this.dimensions; }
    public void setDimensions(Set<Dimension> dimensions) { this.dimensions = dimensions; }

    @ManyToMany
    @JoinTable(name = "substance_quantity",
            joinColumns = @JoinColumn(name = "quantity_id"),
            inverseJoinColumns = @JoinColumn(name = "substance_id"))
    public Set<ChemicalSubstance> getChemicalSubstances() { return this.chemicalSubstances; }
    public void setChemicalSubstances(Set<ChemicalSubstance> chemicalSubstances) { this.chemicalSubstances = chemicalSubstances; }

    @OneToMany(mappedBy = "funcQuantity", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<QuantityState> getFuncQuantity() { return this.funcQuantity; }
    public void setFuncQuantity(Set<QuantityState> funcQuantity) { this.funcQuantity =funcQuantity; }

    public void addFuncQuantity(QuantityState funcQuantity) {
        funcQuantity.setFuncQuantity(this);
        getFuncQuantity().add(funcQuantity);
    }
    public void removeFuncQuantity(QuantityState funcQuantity) { getFuncQuantity().remove(funcQuantity); }


    @OneToMany(mappedBy = "funcArgument", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<QuantityState> getFuncArgument() { return this.funcArgument; }
    public void setFuncArgument(Set<QuantityState> funcArgument) { this.funcArgument =funcArgument; }

    public void addFuncArgument(QuantityState funcArgument) {
        funcArgument.setFuncArgument(this);
        getFuncArgument().add(funcArgument);
    }
    public void removeFuncArgument(QuantityState funcArgument) {getFuncArgument().remove(funcArgument); }


    @OneToMany(mappedBy = "physicalQuantity", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<PointOfMeasure> getPointsOfMeasure(){
        return this.pointsOfMeasure;
    }
    public void setPointsOfMeasure(Set<PointOfMeasure> physicalQuantities) { this.pointsOfMeasure = physicalQuantities; }

    public  void addData(PointOfMeasure pointOfMeasure){
        pointOfMeasure.setPhysicalQuantity(this);
        getPointsOfMeasure().add(pointOfMeasure);
    }
    public void removeData(PointOfMeasure pointOfMeasure){
        getPointsOfMeasure().remove(pointOfMeasure);
    }

}
