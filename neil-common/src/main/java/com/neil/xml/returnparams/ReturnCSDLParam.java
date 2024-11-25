package com.neil.xml.returnparams;

/*
 * @Classname ReturnCSDLParam
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class ReturnCSDLParam extends ReturnParam {
    private static String fileID = "@(#) $Id: ReturnCSDLParam.java,v 1.2 2000/02/14 23:47:55 jdeviss Exp $";
    public static final String TYPE = "C";

    public ReturnCSDLParam(String var1, String var2) {
        super(var1, var2);
    }

    public String getParamType() {
        return "C";
    }
}
