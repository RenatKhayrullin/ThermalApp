package com.app.mvc.dataBaseDomainModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "ont", name = "domain_of_function_definition")
public class DomainOfFunctionDefinition implements Serializable{

    private Long id;
    private FunctionDefinition function;
    private PhysicalQuantity argument;
    private Dimension standartDimension;
    private String funcConnectionType;
    private Double lRangeOfDefinition;
    private Double uRangeOfDefinition;

    public DomainOfFunctionDefinition() {}

    @Id
    @GeneratedValue
    @Column(name = "func_domain_id")
    public Long getId() { return this.id; }
    public void setId(Long id) { this.id = this.id; }

    @ManyToOne
    @JoinColumn(name = "function_id")
    public FunctionDefinition getFunction() { return function; }
    public void setFunction(FunctionDefinition function) { this.function = function; }

    @ManyToOne
    @JoinColumn(name = "argument_id")
    public PhysicalQuantity getArgument() { return argument; }
    public void setArgument(PhysicalQuantity argument) { this.argument = argument; }

    @ManyToOne
    @JoinColumn(name = "standart_dimension_id")
    public Dimension getStandartDimension() { return standartDimension; }
    public void setStandartDimension(Dimension standartDimension) { this.standartDimension = standartDimension; }

    @Column(name = "func_connection_type")
    public String getFuncConnectionType() { return funcConnectionType; }
    public void setFuncConnectionType(String funcConnectionType) { this.funcConnectionType = funcConnectionType; }

    @Column(name = "lrange_of_definition")
    public Double getlRangeOfDefinition() { return this.lRangeOfDefinition; }
    public void setlRangeOfDefinition(Double lRangeOfDefinition) { this.lRangeOfDefinition =lRangeOfDefinition; }

    @Column(name = "urange_of_definition")
    public Double getuRangeOfDefinition() { return uRangeOfDefinition; }
    public void setuRangeOfDefinition(Double uRangeOfDefinition) { this.uRangeOfDefinition =uRangeOfDefinition; }

}