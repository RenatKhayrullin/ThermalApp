package com.app.mvc.dataBaseDomainModel;


import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(schema = "ont", name = "physical_quantity_roles")
class PhysicalQuantityRole implements Serializable {

	private Long id;
	private String roleType;
    @JsonIgnore
    private Set<PhysicalQuantity> physicalQuantities;

    public PhysicalQuantityRole() {}

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
    
    @Column(name = "role_type", length = 5)
    public String getRoleType(){
        return this.roleType;
    }
    public void setRoleType(String name){
        this.roleType = name;
    }

    @OneToMany(mappedBy = "quantityRole", cascade = CascadeType.ALL,
            orphanRemoval = true)
    public Set<PhysicalQuantity> getPhysicalQuantities(){
        return this.physicalQuantities;
    }
    public void setPhysicalQuantities(Set<PhysicalQuantity> properties){
        this.physicalQuantities = properties;
    }

    public  void addPhysicalQuantity(PhysicalQuantity physicalQuantity){
        physicalQuantity.setQuantityRole(this);
        getPhysicalQuantities().add(physicalQuantity);
    }
    public void removePhysicalQuantity(PhysicalQuantity physicalQuantity){
        getPhysicalQuantities().remove(physicalQuantity);
    }
}