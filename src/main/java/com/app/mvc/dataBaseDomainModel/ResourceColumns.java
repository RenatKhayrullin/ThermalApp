package com.app.mvc.dataBaseDomainModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Катерина on 11.04.2017.
 */

@Entity
@Table(schema = "ont", name = "resource_columns")
public class ResourceColumns implements Serializable{

    private Long id;
    private ThirdPartyResource resourceNumber;
    private String columnName;
    private String columnComment;

    public ResourceColumns() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "resource_number")
    public ThirdPartyResource getResourceNumber() { return this.resourceNumber; }
    public void setResourceNumber(ThirdPartyResource resourceNumber) { this.resourceNumber = resourceNumber; }

    @Column(name = "column_name")
    public String getColumnName() { return this.columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }

    @Column(name = "column_comment")
    public String getColumnComment() { return this.columnComment; }
    public void setColumnComment(String columnComment) { this.columnComment = columnComment; }
}
