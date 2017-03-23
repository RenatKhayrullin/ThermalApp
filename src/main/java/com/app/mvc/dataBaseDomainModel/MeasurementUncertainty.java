package com.app.mvc.dataBaseDomainModel;


import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(schema = "ont", name = "measurement_uncertainties")
public class MeasurementUncertainty {
	
	private Long id;
	private String uncertaintyValue;
    private UncertaintyType uncertaintyTypeId;
    private PointOfMeasure pointOfMeasure;

    public MeasurementUncertainty() {}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id =id;
    }
    
    @Column(name = "uncertainty_value")
    public String getUncertaintyValue(){
        return this.uncertaintyValue;
    }
    public void setUncertaintyValue(String uncertaintyValue){
        this.uncertaintyValue =uncertaintyValue;
    }

    @ManyToOne
    @JoinColumn(name = "uncertainty_type_id")
    public UncertaintyType getUncertaintyType() { return this.uncertaintyTypeId; }
    public void setUncertaintyType(UncertaintyType uncertaintyTypeId){
    	this.uncertaintyTypeId = uncertaintyTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "point_of_measure_id")
    public PointOfMeasure getPointOfMeasure() {
    	return this.pointOfMeasure;
    }
    public void setPointOfMeasure(PointOfMeasure pointOfMeasure){
    	this.pointOfMeasure = pointOfMeasure;
    }
    
}
