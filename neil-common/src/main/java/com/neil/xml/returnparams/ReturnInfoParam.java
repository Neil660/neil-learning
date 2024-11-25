package com.neil.xml.returnparams;

/*
 * @Classname ReturnInfoParam
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class ReturnInfoParam extends ReturnParam {
    private static String fileID = "@(#) $Id: ReturnInfoParam.java,v 1.2 2000/02/14 23:47:55 jdeviss Exp $";
    private String paramGroup;
    public static final String TYPE = "I";

    public ReturnInfoParam(String var1, String var2, String var3) {
        super(var2, var3);
        this.paramGroup = var1;
    }

    public String getParamType() {
        return "I";
    }

    public String getParamGroup() {
        return this.paramGroup;
    }

    public void addExtraInfo(CDU var1) {
        var1.addADU(this.paramGroup);
    }
}
