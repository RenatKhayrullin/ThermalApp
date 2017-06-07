package com.app.mvc.dataBaseDomainModel;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "uncertainty_type")
public class UncertaintyType implements Serializable {

    private Long id;
    private String uncertaintyName;
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

    @Column(name = "uncertainty_name")
    public String getUncertaintyName(){
        return this.uncertaintyName;
    }
    public void setUncertaintyName(String uncertaintyName){
        this.uncertaintyName = uncertaintyName;
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