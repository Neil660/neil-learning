package files;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * Order Dynamic Map :
 * IS_REUSE_SPEED_PROFILE=N
 * IS_REUSE_SERVICE_PROFILE=Y
 * IS_REUSE_ONTMODEL=Y
 * IS_REUSE_TIECABLE=Y
 */
public class DynamicJava {
    public static String convert(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONObject msgBody = jsonObject.getJSONObject("msgBody");
        JSONObject orderAttrInfo = msgBody.getJSONObject("orderAttrInfo");
        JSONObject subCfsList = msgBody.getJSONObject("subCfsList");
        JSONArray subCfs = changeToJSONArray(subCfsList.get("subCfs"));
        String chaneSpeedProfileList = "";

        // 拿到订单属性 Change Speed Profile List，修改了Speed的cfsid
        JSONArray orderAttrs = changeToJSONArray(orderAttrInfo.get("attr"));
        for (int i = 0; i < orderAttrs.size(); i++) {
            JSONObject jo = (JSONObject) orderAttrs.get(i);
            if (jo.getString("attrCode").equals("CHG_SPEED_PROFILE_LIST")) {
                chaneSpeedProfileList = jo.getString("attrValue");
                break;
            }
        }

        if (StringUtils.isNotEmpty(chaneSpeedProfileList)) {
            for (int i = 0; i < subCfs.size(); i++) {
                JSONObject subCfsJo = (JSONObject) subCfs.get(i);
                JSONArray subCfsAttrList = changeToJSONArray(subCfsJo.get("subCfsAttrList"));
                for (int j = 0; j < subCfsAttrList.size(); j++) {
                    JSONObject subCfsAttrListJo = (JSONObject) subCfsAttrList.get(j);
                    // 不在chaneSpeedProfileList的cfsid，没修改速率，不发指令，把指令下所有组参属性attrCode改名
                    // 指令涉及到的组参：EMS_SPEED_PROFILE、UNI_PORT、EMS_L2_INFRA
                    // EMS_SPEED_PROFILE、EMS_L2_INFRA都在subCfsAttr下面
                    if (chaneSpeedProfileList.indexOf(subCfsAttrListJo.getString("cfsId")) < 0) {
                        JSONArray subCfsAttr = changeToJSONArray(subCfsAttrListJo.get("subCfsAttr"));
                        for (int k = 0; k < subCfsAttr.size(); k++) {
                            JSONObject subCfsAttrJo = (JSONObject) subCfsAttr.get(k);
                            if ("EMS_SPEED_PROFILE".equals(subCfsAttrJo.getString("attrCode"))) {
                                subCfsAttrJo.remove("attrCode");
                                subCfsAttrJo.put("attrCode", "EMS_SPEED_PROFILE_SpeedNotChange");
                            }
                            if ("EMS_L2_INFRA".equals(subCfsAttrJo.getString("attrCode"))) {
                                subCfsAttrJo.remove("attrCode");
                                subCfsAttrJo.put("attrCode", "EMS_L2_INFRA_SpeedNotChange");
                            }
                        }

                        // UNI_PORT当动作是A时，在subNewRfsAttr下面，D时在subOldRfsAttr下面
                        JSONArray subRfsAttr = null;
                        if ("A".equals(subCfsJo.getString("actionCode"))) {
                            subRfsAttr = changeToJSONArray(subCfsAttrListJo.get("subNewRfsAttr"));

                        }
                        if ("D".equals(subCfsJo.getString("actionCode"))) {
                            subRfsAttr = changeToJSONArray(subCfsAttrListJo.get("subOldRfsAttr"));
                        }
                        if (null != subRfsAttr) {
                            for (int k = 0; k < subRfsAttr.size(); k++) {
                                JSONObject subRfsAttrJo = (JSONObject) subRfsAttr.get(k);
                                if ("UNI_PORT".equals(subRfsAttrJo.getString("attrCode"))) {
                                    subRfsAttrJo.remove("attrCode");
                                    subRfsAttrJo.put("attrCode", "UNI_PORT_SpeedNotChange");
                                }
                            }
                        }
                    }
                }
            }
        }

        return JSON.toJSONString(jsonObject);
    }

    public static JSONArray changeToJSONArray(Object obj) {
        JSONArray result = new JSONArray();
        if (obj instanceof JSONObject) {
            result.add(obj);
        }
        else if (obj instanceof JSONArray) {
            result = (JSONArray) obj;
        }
        return result;
    }
}
