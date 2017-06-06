package com.app.mvc.dataBaseDomainModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "ont", name = "quantity_state",
        uniqueConstraints = @UniqueConstraint(columnNames = {"func_quantity_id", "func_argument_id", "state_id", "dimension_id"}))
class QuantityState implements Serializable{
    private Long id;
    private PhysicalQuantity funcQuantity;
    private PhysicalQuantity funcArgument;
    private State state;

    private Dimension dimension;
    private Double lRangeOfVariation;
    private Double uRangeOfVariation;
    private Double lRangeOfDefinition;
    private Double uRangeOfDefinition;

    public QuantityState() {}

    @Id
    @GeneratedValue()
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }

    @Column(name = "lrange_of_variation")
    public Double getlRangeOfVariation() { return this.lRangeOfVariation; }
    public void setlRangeOfVariation(Double lRangeOfVariation) { this.lRangeOfVariation =lRangeOfVariation; }

    @Column(name = "urange_of_variation")
    public Double getuRangeOfVariation() { return this.uRangeOfVariation; }
    public void setuRangeOfVariation(Double uRangeOfVariation) { this.uRangeOfVariation =uRangeOfVariation; }

    @Column(name = "lrange_of_definition")
    public Double getlRangeOfDefinition() { return this.lRangeOfDefinition; }
    public void setlRangeOfDefinition(Double lRangeOfDefinition) { this.lRangeOfDefinition =lRangeOfDefinition; }

    @Column(name = "urange_of_definition")
    public Double getuRangeOfDefinition() { return uRangeOfDefinition; }
    public void setuRangeOfDefinition(Double uRangeOfDefinition) { this.uRangeOfDefinition =uRangeOfDefinition; }

    @ManyToOne
    @JoinColumn(name = "func_quantity_id")
    public PhysicalQuantity getFuncQuantity() { return this.funcQuantity; }
    public void setFuncQuantity(PhysicalQuantity funcQuantity) { this.funcQuantity =funcQuantity; }

    @ManyToOne
    @JoinColumn(name = "func_argument_id")
    public PhysicalQuantity getFuncArgument() { return this.funcArgument; }
    public void setFuncArgument(PhysicalQuantity funcArgument) { this.funcArgument =funcArgument; }

    @ManyToOne
    @JoinColumn(name = "state_id", nullable = true)
    public State getState() { return this.state; }
    public void setState(State state) { this.state =state; }

    @ManyToOne
    @JoinColumn(name = "dimension_id", nullable = true)
    public Dimension getDimension() { return dimension; }
    public void setDimension(Dimension dimension) { this.dimension = dimension; }
}
