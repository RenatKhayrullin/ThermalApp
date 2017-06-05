package com.app.mvc.dataBaseDomainModel;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(schema = "ont", name = "data_source")
public class DataSource implements Serializable {

	private Long id;
    private String bibliographicReference;

    @JsonIgnore
    public Set<PointOfMeasure> pointsOfMeasure = new HashSet<PointOfMeasure>();

    public DataSource(){}

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id=id; }

    @Column(name = "bibliographic_reference", length = 10000)
    public String getBibliographicReference() { return this.bibliographicReference; }
    public void setBibliographicReference(String bibliographicReference) { this.bibliographicReference = bibliographicReference; }


    @OneToMany(mappedBy = "dataSource", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<PointOfMeasure> getPointsOfMeasure() { return this.pointsOfMeasure; }
    public void setPointsOfMeasure(Set<PointOfMeasure> pointsOfMeasure) { this.pointsOfMeasure = pointsOfMeasure; }

    public  void addData(PointOfMeasure pointOfMeasure){
        pointOfMeasure.setDataSource(this);
        getPointsOfMeasure().add(pointOfMeasure);
    }
    public void removeData(PointOfMeasure pointOfMeasure) { getPointsOfMeasure().remove(pointOfMeasure); }
}
