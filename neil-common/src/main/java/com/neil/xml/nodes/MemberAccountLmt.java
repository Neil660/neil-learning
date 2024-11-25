package com.neil.xml.nodes;

import java.io.Serializable;

/*
 * @Classname MemberAccountLmt
 * @Version information V1.0
 * @Date 2024/9/22
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class MemberAccountLmt implements Serializable {
    private java.lang.String mVGPRSVolLmt;

    private java.lang.String mVGPRSDurLmt;

    private java.lang.String mVGPRSIntRoamingVolLmt;

    private java.lang.String mVGPRSIntRoamingDurLmt;

    public MemberAccountLmt() {
    }

    public String toString(){
        return "{[mVGPRSVolLmt,"+mVGPRSVolLmt+
                "][mVGPRSDurLmt,"+mVGPRSDurLmt+
                "][mVGPRSIntRoamingVolLmt,"+mVGPRSIntRoamingVolLmt+
                "][mVGPRSIntRoamingDurLmt,"+mVGPRSIntRoamingDurLmt+"]}";
    }

    public MemberAccountLmt(
            java.lang.String mVGPRSVolLmt,
            java.lang.String mVGPRSDurLmt,
            java.lang.String mVGPRSIntRoamingVolLmt,
            java.lang.String mVGPRSIntRoamingDurLmt) {
        this.mVGPRSVolLmt = mVGPRSVolLmt;
        this.mVGPRSDurLmt = mVGPRSDurLmt;
        this.mVGPRSIntRoamingVolLmt = mVGPRSIntRoamingVolLmt;
        this.mVGPRSIntRoamingDurLmt = mVGPRSIntRoamingDurLmt;
    }


    /**
     * Gets the mVGPRSVolLmt value for this MemberAccountLmt.
     *
     * @return mVGPRSVolLmt
     */
    public java.lang.String getMVGPRSVolLmt() {
        return mVGPRSVolLmt;
    }


    /**
     * Sets the mVGPRSVolLmt value for this MemberAccountLmt.
     *
     * @param mVGPRSVolLmt
     */
    public void setMVGPRSVolLmt(java.lang.String mVGPRSVolLmt) {
        this.mVGPRSVolLmt = mVGPRSVolLmt;
    }


    /**
     * Gets the mVGPRSDurLmt value for this MemberAccountLmt.
     *
     * @return mVGPRSDurLmt
     */
    public java.lang.String getMVGPRSDurLmt() {
        return mVGPRSDurLmt;
    }


    /**
     * Sets the mVGPRSDurLmt value for this MemberAccountLmt.
     *
     * @param mVGPRSDurLmt
     */
    public void setMVGPRSDurLmt(java.lang.String mVGPRSDurLmt) {
        this.mVGPRSDurLmt = mVGPRSDurLmt;
    }


    /**
     * Gets the mVGPRSIntRoamingVolLmt value for this MemberAccountLmt.
     *
     * @return mVGPRSIntRoamingVolLmt
     */
    public java.lang.String getMVGPRSIntRoamingVolLmt() {
        return mVGPRSIntRoamingVolLmt;
    }


    /**
     * Sets the mVGPRSIntRoamingVolLmt value for this MemberAccountLmt.
     *
     * @param mVGPRSIntRoamingVolLmt
     */
    public void setMVGPRSIntRoamingVolLmt(java.lang.String mVGPRSIntRoamingVolLmt) {
        this.mVGPRSIntRoamingVolLmt = mVGPRSIntRoamingVolLmt;
    }


    /**
     * Gets the mVGPRSIntRoamingDurLmt value for this MemberAccountLmt.
     *
     * @return mVGPRSIntRoamingDurLmt
     */
    public java.lang.String getMVGPRSIntRoamingDurLmt() {
        return mVGPRSIntRoamingDurLmt;
    }


    /**
     * Sets the mVGPRSIntRoamingDurLmt value for this MemberAccountLmt.
     *
     * @param mVGPRSIntRoamingDurLmt
     */
    public void setMVGPRSIntRoamingDurLmt(java.lang.String mVGPRSIntRoamingDurLmt) {
        this.mVGPRSIntRoamingDurLmt = mVGPRSIntRoamingDurLmt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MemberAccountLmt)) return false;
        MemberAccountLmt other = (MemberAccountLmt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.mVGPRSVolLmt==null && other.getMVGPRSVolLmt()==null) ||
                        (this.mVGPRSVolLmt!=null &&
                                this.mVGPRSVolLmt.equals(other.getMVGPRSVolLmt()))) &&
                ((this.mVGPRSDurLmt==null && other.getMVGPRSDurLmt()==null) ||
                        (this.mVGPRSDurLmt!=null &&
                                this.mVGPRSDurLmt.equals(other.getMVGPRSDurLmt()))) &&
                ((this.mVGPRSIntRoamingVolLmt==null && other.getMVGPRSIntRoamingVolLmt()==null) ||
                        (this.mVGPRSIntRoamingVolLmt!=null &&
                                this.mVGPRSIntRoamingVolLmt.equals(other.getMVGPRSIntRoamingVolLmt()))) &&
                ((this.mVGPRSIntRoamingDurLmt==null && other.getMVGPRSIntRoamingDurLmt()==null) ||
                        (this.mVGPRSIntRoamingDurLmt!=null &&
                                this.mVGPRSIntRoamingDurLmt.equals(other.getMVGPRSIntRoamingDurLmt())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getMVGPRSVolLmt() != null) {
            _hashCode += getMVGPRSVolLmt().hashCode();
        }
        if (getMVGPRSDurLmt() != null) {
            _hashCode += getMVGPRSDurLmt().hashCode();
        }
        if (getMVGPRSIntRoamingVolLmt() != null) {
            _hashCode += getMVGPRSIntRoamingVolLmt().hashCode();
        }
        if (getMVGPRSIntRoamingDurLmt() != null) {
            _hashCode += getMVGPRSIntRoamingDurLmt().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(MemberAccountLmt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "MemberAccountLmt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MVGPRSVolLmt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mVGPRSVolLmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MVGPRSDurLmt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mVGPRSDurLmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MVGPRSIntRoamingVolLmt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mVGPRSIntRoamingVolLmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MVGPRSIntRoamingDurLmt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mVGPRSIntRoamingDurLmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  org.apache.axis.encoding.ser.BeanSerializer(
                        _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new  org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }
}
