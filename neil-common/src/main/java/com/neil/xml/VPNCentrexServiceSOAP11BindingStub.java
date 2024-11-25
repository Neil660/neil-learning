package com.neil.xml;

import com.neil.xml.nodes.PBXMember3;
import com.neil.xml.nodes.PBXMember4;
import com.neil.xml.nodes.PBXMember4ArrayHolder;
import com.neil.xml.nodes.RequestHeader;
import com.neil.xml.nodes.ResponseHeader;
import com.neil.xml.nodes.ResponseHeaderHolder;
import com.neil.xml.nodes.ReturnDes;
import com.neil.xml.nodes.ReturnDesHolder;
import com.neil.xml.returnparams.ReturnInfoParam;
import java.rmi.RemoteException;
import javax.xml.namespace.QName;
import javax.xml.rpc.holders.StringHolder;
import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

/*
 * @Classname Demo
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class VPNCentrexServiceSOAP11BindingStub extends Stub {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc oper;

    public VPNCentrexServiceSOAP11BindingStub() {
        super.service = new org.apache.axis.client.Service();
        ((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.1");
        java.lang.Class cls;
        javax.xml.namespace.QName qName;
        java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
        java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;

        qName = new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "PBXMember3");
        cachedSerQNames.add(qName);
        cls = PBXMember3.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "PBXMember4");
        cachedSerQNames.add(qName);
        cls = PBXMember4.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "RequestHeader");
        cachedSerQNames.add(qName);
        cls = RequestHeader.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "ResponseHeader");
        cachedSerQNames.add(qName);
        cls = ResponseHeader.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "ReturnDes");
        cachedSerQNames.add(qName);
        cls = ReturnDes.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);
        initOperationDesc();
    }

    private static void initOperationDesc() {
        OperationDesc oper;
        ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("listPBXMember");
        param = new ParameterDesc(new QName("", "requestHeader"), ParameterDesc.IN, new QName("http://www.huawei.com/ngin/vpncentrex/domain", "RequestHeader"), RequestHeader.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "currentPage"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "PBXMember"), ParameterDesc.IN, new QName("http://www.huawei.com/ngin/vpncentrex/domain", "PBXMember3"), PBXMember3.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "responseHeader"), ParameterDesc.OUT, new QName("http://www.huawei.com/ngin/vpncentrex/domain", "ResponseHeader"), ResponseHeader.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "returnDes"), ParameterDesc.OUT, new QName("http://www.huawei.com/ngin/vpncentrex/domain", "ReturnDes"), ReturnDes.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "totalSize"), ParameterDesc.OUT, new QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "rowsPerPage"), ParameterDesc.OUT, new QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "totalPageNum"), ParameterDesc.OUT, new QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("", "members"), ParameterDesc.OUT, new QName("http://www.huawei.com/ngin/vpncentrex/domain", "PBXMember4"), PBXMember4[].class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(XMLType.AXIS_VOID);
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
    }

    public void listPBXMember(RequestHeader requestHeader, String currentPage, PBXMember3 PBXMember, ResponseHeaderHolder responseHeader, ReturnDesHolder returnDes, javax.xml.rpc.holders.StringHolder totalSize, javax.xml.rpc.holders.StringHolder rowsPerPage, javax.xml.rpc.holders.StringHolder totalPageNum, PBXMember4ArrayHolder members) throws Exception {
        Call _call = createCall();
        _call.setOperation(oper);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("urn:listPBXMember");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://www.huawei.com/ngin/vpncentrex/mgr", "listPBXMember"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{requestHeader, currentPage, PBXMember});

            if (_resp instanceof RemoteException) {
                throw (RemoteException) _resp;
            }
            else {
                extractAttachments(_call);
                java.util.Map _output;
                _output = _call.getOutputParams();
                try {
                    responseHeader.value = (ResponseHeader) _output.get(new QName("", "responseHeader"));
                }
                catch (java.lang.Exception _exception) {
                    responseHeader.value = (ResponseHeader) JavaUtils.convert(_output.get(new QName("", "responseHeader")), ResponseHeader.class);
                }
                try {
                    returnDes.value = (ReturnDes) _output.get(new QName("", "returnDes"));
                }
                catch (java.lang.Exception _exception) {
                    returnDes.value = (ReturnDes) JavaUtils.convert(_output.get(new QName("", "returnDes")), ReturnDes.class);
                }
                try {
                    totalSize.value = (java.lang.String) _output.get(new QName("", "totalSize"));
                }
                catch (java.lang.Exception _exception) {
                    totalSize.value = (java.lang.String) JavaUtils.convert(_output.get(new QName("", "totalSize")), String.class);
                }
                try {
                    rowsPerPage.value = (java.lang.String) _output.get(new QName("", "rowsPerPage"));
                }
                catch (java.lang.Exception _exception) {
                    rowsPerPage.value = (java.lang.String) JavaUtils.convert(_output.get(new QName("", "rowsPerPage")), String.class);
                }
                try {
                    totalPageNum.value = (java.lang.String) _output.get(new QName("", "totalPageNum"));
                }
                catch (java.lang.Exception _exception) {
                    totalPageNum.value = (java.lang.String) JavaUtils.convert(_output.get(new QName("", "totalPageNum")), String.class);
                }
                try {
                    members.value = (PBXMember4[]) _output.get(new QName("", "members"));
                }
                catch (java.lang.Exception _exception) {
                    members.value = (PBXMember4[]) JavaUtils.convert(_output.get(new QName("", "members")), PBXMember4[].class);
                }
            }
        }
        catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    protected Call createCall() throws RemoteException {
        try {
            Call _call = super._createCall();
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress("http://xxx");
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(9000);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        Class cls = (Class) cachedSerClasses.get(i);
                        QName qName = (QName) cachedSerQNames.get(i);
                        Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            Class sf = (Class) cachedSerFactories.get(i);
                            Class df = (Class) cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }
}
