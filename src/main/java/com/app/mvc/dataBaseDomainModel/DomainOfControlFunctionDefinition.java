package com.app.mvc.dataBaseDomainModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "ont", name = "domain_of_control_function_definition")
public class DomainOfControlFunctionDefinition implements Serializable{

    private Long id;
    private ControlFunctionDefinition controlFunction;
    private PhysicalQuantity argument;
    private Dimension standartDimension;

    public DomainOfControlFunctionDefinition() {}

    @Id
    @GeneratedValue
    @Column(name = "control_domain_id")
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = this.id; }

    @ManyToOne
    @JoinColumn(name = "control_function_id")
    public ControlFunctionDefinition getControlFunction() { return controlFunction; }
    public void setControlFunction(ControlFunctionDefinition controlFunction) { this.controlFunction = controlFunction; }

    @ManyToOne
    @JoinColumn(name = "argument_id")
    public PhysicalQuantity getArgument() { return argument; }
    public void setArgument(PhysicalQuantity argument) { this.argument = argument; }

    @ManyToOne
    @JoinColumn(name = "standart_dimension_id")
    public Dimension getStandartDimension() { return standartDimension; }
    public void setStandartDimension(Dimension standartDimension) { this.standartDimension = standartDimension; }
}
