package com.app.mvc.controllers.FormClasses;

import com.complexible.stardog.plan.filter.functions.rdfterm.Str;

/**
 * Created by Катерина on 06.06.2017.
 */
public class NumericData {

    private Long dataset;
    private Long rowNum;
    private String substance;
    private String state;
    private String quantity;
    private String dimension;
    private Long referenceId;
    private Double quantityValue;
    private Long uncertaintyType;
    private Double uncertaintyValue;

    public NumericData(Object dataset, Object rowNum, Object substance, Object state, Object quantity, Object dimension,
                       Object reference, Object quantityValue, Object uncertaintyType, Object uncertaintyValue) {
        this.referenceId = new Long(reference.toString());
        this.quantityValue = new Double(quantityValue.toString());
        this.uncertaintyType = new Long(uncertaintyType.toString());
        this.uncertaintyValue = new Double(uncertaintyValue.toString());
        this.substance = substance.toString();
        this.state = state.toString();
        this.quantity = quantity.toString();
        this.dimension = dimension.toString();
        this.dataset = new Long(dataset.toString());
        this.rowNum = new Long (rowNum.toString());
    }

    public  NumericData() {}

    public Long getReference() { return this.referenceId; }
    public void setReference(Long referenceId) { this.referenceId = referenceId; }

    public Double getQuantityValue() { return this.quantityValue; }
    public void setQuantityValue(Double quantityValue) { this.quantityValue = quantityValue; }

    public Long getUncertaintyType() { return this.uncertaintyType; }
    public void setUncertaintyType(Long uncertaintyType) { this.uncertaintyType = uncertaintyType; }

    public Double getUncertaintyValue() { return this.uncertaintyValue; }
    public void setUncertaintyValue(Double uncertaintyValue) {this.uncertaintyValue = uncertaintyValue; }

    public String getSubstance() { return this.substance; }
    public void setSubstance(String substance) { this.substance = substance; }

    public String getState() { return this.state; }
    public void setState(String state) { this.state = state; }

    public String getQuantity() { return this.quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getDimension() { return this.dimension; }
    public void setDimension(String dimension) { this.dimension = dimension; }

    public Long getDataset() { return  this.dataset; }
    public void setDataset(Long dataset) { this.dataset = dataset; }

    public Long getRowNum() { return this.rowNum; }
    public void setRowNum(Long rowNum) { this.rowNum = rowNum; }
}
