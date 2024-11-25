package com.neil.xml.nodes;

import java.io.Serializable;

/*
 * @Classname BusinessTribe1
 * @Version information V1.0
 * @Date 2024/9/22
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class BusinessTribe1 implements Serializable {
    private java.lang.String spID;

    private java.lang.String btName;

    public BusinessTribe1() {
    }

    public BusinessTribe1(
            java.lang.String spID,
            java.lang.String btName) {
        this.spID = spID;
        this.btName = btName;
    }
    public String toString(){
        return "{[spID,"+spID+"]"+
                "[btName,"+btName+"]}";
    }


    /**
     * Gets the spID value for this BusinessTribe1.
     *
     * @return spID
     */
    public java.lang.String getSpID() {
        return spID;
    }


    /**
     * Sets the spID value for this BusinessTribe1.
     *
     * @param spID
     */
    public void setSpID(java.lang.String spID) {
        this.spID = spID;
    }


    /**
     * Gets the btName value for this BusinessTribe1.
     *
     * @return btName
     */
    public java.lang.String getBtName() {
        return btName;
    }


    /**
     * Sets the btName value for this BusinessTribe1.
     *
     * @param btName
     */
    public void setBtName(java.lang.String btName) {
        this.btName = btName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BusinessTribe1)) return false;
        BusinessTribe1 other = (BusinessTribe1) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.spID==null && other.getSpID()==null) ||
                        (this.spID!=null &&
                                this.spID.equals(other.getSpID()))) &&
                ((this.btName==null && other.getBtName()==null) ||
                        (this.btName!=null &&
                                this.btName.equals(other.getBtName())));
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
        if (getSpID() != null) {
            _hashCode += getSpID().hashCode();
        }
        if (getBtName() != null) {
            _hashCode += getBtName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(BusinessTribe1.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.huawei.com/ngin/vpncentrex/domain", "BusinessTribe1"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("btName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "btName"));
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
