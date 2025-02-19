package com.neil.xml;

import com.neil.xml.nodes.PBXMember3;
import com.neil.xml.nodes.PBXMember4;
import com.neil.xml.nodes.PBXMember4ArrayHolder;
import com.neil.xml.nodes.RequestHeader;
import com.neil.xml.nodes.ResponseHeaderHolder;
import com.neil.xml.nodes.ReturnDesHolder;
import com.neil.xml.returnparams.ReturnCSDLParam;
import com.neil.xml.returnparams.ReturnInfoParam;
import java.util.Vector;
import javax.xml.rpc.holders.StringHolder;

/*
 * @Classname PBXMemberProvisioning
 * @Version information V1.0
 * @Date 2024/8/18
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class PBXMemberProvisioning {
    protected VPNCentrexServiceSOAP11BindingStub biding = new VPNCentrexServiceSOAP11BindingStub();
    private String paramGroup = "";
    private Vector returnParams = new Vector();
    private String asdlName = "CMD_PARAM[ASDL_CMD]";

    public void getListPBXMember() throws Exception {
        String strLog = "[sequenceID,CMD_PARAM[SRQ_ID]] ";

        //============================================================================================
        RequestHeader requestHeader = new RequestHeader();
        ResponseHeaderHolder responseHeader = new ResponseHeaderHolder();
        ReturnDesHolder returnDes = new ReturnDesHolder();
        StringHolder totalSize = new StringHolder();
        StringHolder rowsPerPage = new StringHolder();
        StringHolder totalPageNum = new StringHolder();
        String currentPage = null;

        String responseCode = "";
        String respuestaNodo = "";
        PBXMember4ArrayHolder members = new PBXMember4ArrayHolder();
        PBXMember4 pBXMember4 = new PBXMember4();
        PBXMember3 pBXMember3 = new PBXMember3();

        pBXMember3 = creaGetPBXMember();

        try {

            requestHeader.setPassWord("password");
            requestHeader.setUserName("user");
            requestHeader.setSerialno(strLog);

            //======================================= CALL CLIENTE WS==================================================

            biding.listPBXMember(requestHeader, getCurrentPage(), pBXMember3, responseHeader, returnDes, totalSize, rowsPerPage, totalPageNum, members);
            responseCode = returnDes.value.getReturnCode();
            respuestaNodo = returnDes.value.getResultDesc();
            PBXMember4[] listaPBXMember4 = members.value;

            //String fullNumber1					 = null;
            String gmNumber1 = null;
            String enterpriseID1 = null;
            String groupID1 = null;
            String pbxLinkName1 = null;

            String networkType1 = null;
            String terminalType1 = null;
            String userType1 = null;
            String chargeType1 = null;
            String privateNumberRange1 = null;
            String publicNumberRange1 = null;
            String lengthPBX1 = null;
            String destinationNumberRange1 = null;

            if (responseCode.equals("0000000000")) {
                returnInfoParam("totalSize", totalSize.value);
                returnInfoParam("rowsPerPage", rowsPerPage.value);
                returnInfoParam("totalPageNum", totalPageNum.value);

                if (listaPBXMember4 != null) {
                    for (int i = 0; i < listaPBXMember4.length; i++) {
                        pBXMember4 = listaPBXMember4[i];


                        gmNumber1 = pBXMember4.getGmNumber() == null ? "" : pBXMember4.getGmNumber();
                        enterpriseID1 = pBXMember4.getEnterpriseID() == null ? "" : pBXMember4.getEnterpriseID();
                        groupID1 = pBXMember4.getGroupID() == null ? "" : pBXMember4.getGroupID();
                        pbxLinkName1 = pBXMember4.getPbxLinkName() == null ? "" : pBXMember4.getPbxLinkName();
                        networkType1 = pBXMember4.getNetworkType() == null ? "" : pBXMember4.getNetworkType();
                        terminalType1 = pBXMember4.getTerminalType() == null ? "" : pBXMember4.getTerminalType();
                        userType1 = pBXMember4.getUserType() == null ? "" : pBXMember4.getUserType();
                        chargeType1 = pBXMember4.getChargeType() == null ? "" : pBXMember4.getChargeType();
                        privateNumberRange1 = pBXMember4.getPrivateNumberRange() == null ? "" : pBXMember4.getPrivateNumberRange();
                        publicNumberRange1 = pBXMember4.getPublicNumberRange() == null ? "" : pBXMember4.getPublicNumberRange();
                        lengthPBX1 = pBXMember4.getLength() == null ? "" : pBXMember4.getLength();
                        destinationNumberRange1 = pBXMember4.getDestinationNumberRange() == null ? "" : pBXMember4.getDestinationNumberRange();

                        returnInfoParam("gmNumber" + "[" + i + "]", gmNumber1);
                        returnInfoParam("enterpriseID" + "[" + i + "]", enterpriseID1);
                        returnInfoParam("groupID" + "[" + i + "]", groupID1);
                        returnInfoParam("pbxLinkName" + "[" + i + "]", pbxLinkName1);
                        returnInfoParam("networkType" + "[" + i + "]", networkType1);
                        returnInfoParam("terminalType" + "[" + i + "]", terminalType1);
                        returnInfoParam("userType" + "[" + i + "]", userType1);
                        returnInfoParam("chargeType" + "[" + i + "]", chargeType1);
                        returnInfoParam("privateNumberRange" + "[" + i + "]", privateNumberRange1);
                        returnInfoParam("publicNumberRange" + "[" + i + "]", publicNumberRange1);
                        returnInfoParam("lengthPBX" + "[" + i + "]", lengthPBX1);
                        returnInfoParam("destinationNumberRange" + "[" + i + "]", destinationNumberRange1);

                    }
                }
                else {
                }
            }
            else {
                responseCode = "6" + responseCode;
            }


        }
        catch (Exception e) {
            throw e;
        }
        finally {
            checkResponse(responseCode, respuestaNodo);
        }
    }

    public String checkResponse(String reply,String respuestaNodo) {
        String usrExit = "exit";
        String baseExit = "exit";
        String errorText = "error";
        String retMsg = reply;

        this.paramGroup = asdlName;
        String csdlLabel = "ERR_VPN_HUAWEI";
        if (usrExit == null) {
            returnCSDLParam(csdlLabel, "FAIL");
            returnInfoParam("ERRORINFO", retMsg);
            baseExit = "FAIL";
        }
        else if (!(baseExit.equals("SUCCEED"))) {
            returnCSDLParam(csdlLabel, usrExit);
            returnInfoParam("ERRORINFO", retMsg);
        }
        return baseExit;
    }

    public void returnCSDLParam(String var1, String var2) {
        ReturnCSDLParam var3 = new ReturnCSDLParam(var1, var2);
        this.returnParams.add(var3);
    }

    public void returnInfoParam(String var1, String var2) {
        ReturnInfoParam var3 = new ReturnInfoParam(this.paramGroup, var1, var2);
        this.returnParams.add(var3);
    }

    private PBXMember3 creaGetPBXMember() {
        PBXMember3 pbxMember3= new PBXMember3();

        pbxMember3.setEnterpriseID(getEntId());
        pbxMember3.setGmNumber(getGmNumber());
        pbxMember3.setGroupID(getGroupID());

        if (getUserType()!=null)						pbxMember3.setUserType(getUserType());
        if (getNetworkType()!=null)						pbxMember3.setNetworkType(getNetworkType());
        if (getTerminalType()!=null)					pbxMember3.setTerminalType(getTerminalType());
        if (getPrivateNumberRange()!=null)	            pbxMember3.setPrivateNumberRange(getPrivateNumberRange());
        if (getPublicNumberRange()!=null)				pbxMember3.setPublicNumberRange(getPublicNumberRange());
        if (getDestinationNumberRange()!=null)			pbxMember3.setDestinationNumberRange(getDestinationNumberRange());
        if (getLength()!=null)							pbxMember3.setLength(getLength());
        if (getPnpn()!=null)							pbxMember3.setPnpn(getPnpn());
        if (getFullnumber()!=null)						pbxMember3.setFullNumber(getFullnumber());

        return pbxMember3;
    }

    public String getEntId() {
        return "CMD_PARAM[ENT_ID]";
    }
    public String getGmNumber() {
        return "CMD_PARAM[GM_NUM]";
    }
    public String getGroupID() {
        return "CMD_PARAM[GROUP_ID]";
    }
    public String getUserType() {
        return null;
    }
    public String getNetworkType() {
        return "CMD_PARAM[NETWORK_TYPE]";
    }
    public String getTerminalType() {
        return "CMD_PARAM[TERMINAL_TYPE]";
    }
    public String getPrivateNumberRange() {
        return "CMD_PARAM[PRIVATE_NUM_RANGE]";
    }
    public String getPublicNumberRange() {
        return "CMD_PARAM[PUBLIC_NUM_RANGE]";
    }
    public String getDestinationNumberRange() {
        return "CMD_PARAM[DEST_NUM_RANGE]";
    }
    public String getLength() {
        return "CMD_PARAM[LENGTH_PBXMEMBER]";
    }
    public String getPnpn() {
        return "CMD_PARAM[PNPN]";
    }
    public String getFullnumber() {
        return "CMD_PARAM[FULL_NUMBER]";
    }
    public String getCurrentPage() {
        return "CMD_PARAM[CURRENT_PAGE]";
    }
}
