package com.yuntongxun.acd.common;

public class LineDistributePart {

    private LineElement lineElement;
    private LineServant lineServant;

    public LineDistributePart(LineElement lineElement, LineServant lineServant) {
        this.lineElement = lineElement;
        this.lineServant = lineServant;
    }

    public LineElement getLineElement() {
        return lineElement;
    }

    public void setLineElement(LineElement lineElement) {
        this.lineElement = lineElement;
    }

    public LineServant getLineServant() {
        return lineServant;
    }

    public void setLineServant(LineServant lineServant) {
        this.lineServant = lineServant;
    }
}
