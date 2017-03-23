package com.app.mvc.dataBaseDomainModel;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "uncertainty_types")
public class UncertaintyType {
    
	private Long id;
    private String uncertaintyType;
    @JsonIgnore
    private Set<MeasurementUncertainty> measurementUncertainties = new HashSet<MeasurementUncertainty>();

    public UncertaintyType() {}

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id=id;
    }

    @Column(name = "uncertainty_type")
    public String getUncertaintyType(){
        return this.uncertaintyType;
    }
    public void setUncertaintyType(String uncertaintyType){
        this.uncertaintyType = uncertaintyType;
    }

    @OneToMany(mappedBy = "uncertaintyType", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<MeasurementUncertainty> getMeasurementUncertainties(){
        return this.measurementUncertainties;
    }
    public void setMeasurementUncertainties(Set<MeasurementUncertainty> measurementUncertainties){
        this.measurementUncertainties = measurementUncertainties;
    }

    public  void addMeasurementUncertainty(MeasurementUncertainty measurementUncertainty){
    	measurementUncertainty.setUncertaintyType(this);
        getMeasurementUncertainties().add(measurementUncertainty);
    }

    public void removeMeasurementUncertainty(MeasurementUncertainty measurementUncertainty){
        getMeasurementUncertainties().remove(measurementUncertainty);
    }
}
