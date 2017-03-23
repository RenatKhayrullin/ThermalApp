package com.app.mvc.dataBaseDomainModel;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "ont", name = "data_sets")
public class DataSet implements Serializable {

    private Long id;
    private SubstanceInState substanceInState;
    private String dataDescription;
    private String dataType;
    private String dataFormat;

    @JsonIgnore
    private Set<PointOfMeasure> pointsOfMeasure;// = new HashSet<PointOfMeasure>();

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "data_description")
    public String getDataDescription() { return this.dataDescription; }
    public void setDataDescription(String dataDescription) { this.dataDescription =dataDescription; }

    @Column(name = "data_type")
    public String getDataType() { return this.dataType; }
    public void setDataType(String dataType) { this.dataType =dataType; }

    @Column(name = "data_format")
    public String getDataFormat() { return this.dataFormat; }
    public void setDataFormat(String dataFormat) { this.dataFormat =dataFormat; }

    @ManyToOne
    @JoinColumn(name = "substance_in_state_id")
    public SubstanceInState getSubstanceInState() { return this.substanceInState; }
    public void setSubstanceInState(SubstanceInState substanceInState) { this.substanceInState = substanceInState; }

    @OneToMany(mappedBy = "dataSet", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<PointOfMeasure> getPointsOfMeasure() { return this.pointsOfMeasure;}
    public void setPointsOfMeasure(Set<PointOfMeasure> pointsOfMeasure) { this.pointsOfMeasure = pointsOfMeasure;
    }

    public void addPointOfMeasure(PointOfMeasure pointOfMeasure){
        pointOfMeasure.setDataSet(this);
        getPointsOfMeasure().add(pointOfMeasure);
    }

    public void removePointOfMeasure(PointOfMeasure pointOfMeasure) { getPointsOfMeasure().remove(pointOfMeasure); }
}
