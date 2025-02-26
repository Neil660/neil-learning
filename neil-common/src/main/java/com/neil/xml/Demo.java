package com.neil.xml;

import com.neil.xml.nodes.MemberAccountLmt;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;

/*
 * @Classname Demo
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        // 完全模拟ASAP，目前还不成功
//        PBXMemberProvisioning prov = new PBXMemberProvisioning();
//        prov.getListPBXMember();

        OperationDesc oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setGrpShrdMember");
        ParameterDesc param = new ParameterDesc(new javax.xml.namespace.QName("", "memberAccountLmt"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "MemberAccountLmt"), com.huawei.www.ngin.vpncentrex.domain.MemberAccountLmt.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("", "gPRSsharingFlag"), ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("", "responseHeader"), ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "ResponseHeader"), com.huawei.www.ngin.vpncentrex.domain.ResponseHeader.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new javax.xml.namespace.QName("", "returnDes"), ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "ReturnDes"), com.huawei.www.ngin.vpncentrex.domain.ReturnDes.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);

        MemberAccountLmt 		memberAccountLmt	= new MemberAccountLmt();
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(oper);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:setGrpShrdMember");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/mgr", "setGrpShrdMember"));

        setRequestHeaders(_call);
        setAttachments(_call);
        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {memberAccountLmt});
        java.util.Map _output;
        _output = _call.getOutputParams();
    }


}
