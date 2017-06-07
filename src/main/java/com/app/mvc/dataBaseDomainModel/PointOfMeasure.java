package com.app.mvc.dataBaseDomainModel;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "point_of_measure",
        uniqueConstraints = @UniqueConstraint( columnNames = {"quantity_id", "row_num", "data_set_id"}))
public class PointOfMeasure implements Serializable {

    private Long id;
    private Double quantityValue;
    private Long rowNum;
    private DataSource dataSource;
    private PhysicalQuantity physicalQuantity;
    private Dimension dimension;
    private DataSet dataSet;

    @JsonIgnore
    private Set<MeasurementUncertainty> measurementUncertainties = new HashSet<MeasurementUncertainty>();

    public PointOfMeasure(){}

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id =id;
    }

    @ManyToOne
    @JoinColumn(name = "data_set_id")
    public DataSet getDataSet() { return this.dataSet; }
    public void setDataSet(DataSet dataSet) {
        this.dataSet =dataSet;
    }

    @Column(name = "row_num")
    public Long getRowCount() { return rowNum; }
    public void setRowCount(Long rowNum) {
        this.rowNum = rowNum;
    }

    @Column(name = "quantity_value")
    public Double getQuantityValue() { return this.quantityValue; }
    public void setQuantityValue(Double value) { this.quantityValue =value; }

    @ManyToOne
    @JoinColumn(name = "data_source_id")
    public DataSource getDataSource(){
        return  this.dataSource;
    }
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @ManyToOne
    @JoinColumn(name = "quantity_id")
    public PhysicalQuantity getPhysicalQuantity(){
        return  this.physicalQuantity;
    }
    public void setPhysicalQuantity(PhysicalQuantity physicalQuantity){
        this.physicalQuantity = physicalQuantity;
    }


    @OneToMany(mappedBy = "pointOfMeasure", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<MeasurementUncertainty> getMeasurementUncertainties() { return this.measurementUncertainties; }
    public void setMeasurementUncertainties(Set<MeasurementUncertainty> measureUncertainties) {
        this.measurementUncertainties = measureUncertainties;
    }
    public  void addMeasurementUncertainty(MeasurementUncertainty measurementUncertainty){
        measurementUncertainty.setPointOfMeasure(this);
        getMeasurementUncertainties().add(measurementUncertainty);
    }
    public void removeMeasurementUncertainty(MeasurementUncertainty measurementUncertainty){
        getMeasurementUncertainties().remove(measurementUncertainty);
    }

    @ManyToOne
    @JoinColumn(name = "dimension_id")
    public Dimension getDimension(){
        return  this.dimension;
    }
    public void setDimension(Dimension dimension){
        this.dimension = dimension;
    }
}