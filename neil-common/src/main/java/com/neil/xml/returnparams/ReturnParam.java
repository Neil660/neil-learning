package com.neil.xml.returnparams;

/*
 * @Classname ReturnParam
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public abstract class ReturnParam {
    private String paramName;
    private String paramValue;

    protected ReturnParam(String var1, String var2) {
        this.paramName = var1;
        this.paramValue = var2;
    }

    public String getParamName() {
        return this.paramName;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public abstract String getParamType();

    protected void addExtraInfo(CDU var1) {
    }

    public void addTo(CDU var1) {
        CDU var2 = new CDU();
        var2.addADU(this.getParamType());
        var2.addADU(this.getParamName());
        var2.addADU(this.getParamValue());
        this.addExtraInfo(var2);
        var1.add(var2);
    }
}
