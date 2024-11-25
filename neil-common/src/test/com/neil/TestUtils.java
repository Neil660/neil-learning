package com.neil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neil.annotation.HighRequest;
import com.neil.utils.HighRequestPerSecondUtil;
import com.neil.utils.Tools;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Decription
 * @Author NEIL
 * @Date 2023/3/4 21:27
 * @Version 1.0
 */
@Slf4j
public class TestUtils  {

    @Test
    public void test3() {
        //最大堆
        PriorityQueue<Integer> bpq = new PriorityQueue<>((a, b) -> b - a);
        bpq.offer(1);
        bpq.offer(2);
        bpq.offer(3);
        // 最小堆
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(1);
        pq.offer(2);
        pq.offer(3);

        System.out.println();
    }

    @Test
    public void test2() {
        JSONObject obj = JSONObject.parseObject(json);
        Map<String, String> mppath = new HashMap<>();
        Map<String, String> mpvalue = new HashMap<>();
        Object[] args = {obj, "/root", "", "1", mppath, mpvalue};
        HighRequestPerSecondUtil.runTest(TestUtils.class, "getValue", args);
    }


    @Test
    public void test1() throws Exception {
        AtomicInteger ai = new AtomicInteger(0);
        ai.set(1); //volatile变量
        ai.getAndSet(5);

        // 通过反射的方式获取Unsafe实例
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        User user = new User(1); //private final int id;
        long offset = unsafe.objectFieldOffset(User.class.getDeclaredField("id"));
        // 直接通过offset可获得字段的相对地址
        System.out.println("修改前" + unsafe.getInt(user, offset));
        unsafe.putInt(user, offset, 5);
        // 不用修改访问权限直接获取到
        System.out.println("修改后" + user.getId());


        System.out.println();
    }

    @Test
    public void test() throws Exception {
        String str="编程mvc";
        System.out.println(str.length()); // 在Java的Unicoed编码中，一个汉字长度是1
        // UTF-8编码中，汉字长度是3；GBK是2
        System.out.println(str.getBytes(Charset.forName("GBK")).length);
    }

    @HighRequest(threadNum = 10)
    public void getValue(Object json, String curNodePath, String parNodePath, String curNodeLevel, Map<String, String> mppath,
                         Map<String, String> mpvalue) {
        // 创建一个队列，将要处理的节点加入队列中
        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[]{json, curNodePath, parNodePath, curNodeLevel});
        // 遍历队列，处理其中的节点
        while (!queue.isEmpty()) {
            // 获取队列中的节点
            Object[] currObj = queue.poll();
            json = currObj[0];
            curNodePath = (String) currObj[1];
            //pxmlpath = (String) currObj[2];
            curNodeLevel = (String) currObj[3];
            if (json instanceof JSONObject) { // 如果节点是JSONObject类型，则遍历其中的键值对
                JSONObject currJson = (JSONObject) json;
                int i = 1;
                for (Map.Entry entry : currJson.entrySet()) {
                    String clevel = curNodeLevel + (i < 10 ? "0" + i : i);
                    i++;
                    // 如果值是JSONObject或JSONArray类型，则将其加入队列
                    if (entry.getValue() instanceof JSONObject) {
                        queue.offer(new Object[]{entry.getValue(), curNodePath + "/" + entry.getKey(), curNodePath, clevel});
                    }
                    else if (entry.getValue() instanceof JSONArray) {
                        queue.offer(new Object[]{entry.getValue(), curNodePath + "/" + entry.getKey(), curNodePath, clevel});
                    }
                    else if (null != entry.getValue().toString() && !"".equals(entry.getValue().toString())) {
                        // 如果值不为空，则将路径信息存储到mppath中，并将值存储到mpvalue中
                        String pathStr = "#" + curNodePath + "$" + entry.getKey() + "=" + entry.getValue().toString();
                        if (mppath.get(pathStr) != null) {
                            String tmpv = mppath.get(pathStr);
                            String[] len = tmpv.split("@_");
                            int k = 1;
                            if (len.length > 1) {
                                k = Integer.parseInt(len[1]);
                            }
                            if (k == 1) {
                                mppath.put(pathStr + "@_" + (k - 1), tmpv);
                            }
                            mppath.put(pathStr + "@_" + k, curNodeLevel);
                            mppath.put(pathStr, "dub@_" + (k + 1));
                        }
                        else {
                            mppath.put(pathStr, curNodeLevel);
                        }
                        String valueStr = curNodeLevel + "#" + entry.getKey();
                        mpvalue.put(valueStr, entry.getValue().toString());
                    }
                }
            }
            else if (json instanceof JSONArray) {  // 如果节点是JSONArray类型，则遍历其中的元素
                JSONArray array = (JSONArray) json;
                for (int i = 0; i < array.size(); i++) {
                    String clevel = curNodeLevel + (i < 10 ? "0" + i : i);
                    Object nextObj = array.get(i);
                    // 将下一个元素加入队列中
                    queue.offer(new Object[]{nextObj, curNodePath, curNodePath, clevel});
                }
            }
        }
    }

    private String json = "{\n" +
            "\t\"msgHead\":{\n" +
            "\t\t\"from\":\"OM\",\n" +
            "\t\t\"time\":\"2023-03-09 16:37:13.881\",\n" +
            "\t\t\"to\":\"ISAP\"\n" +
            "\t},\n" +
            "\t\"msgBody\":{\n" +
            "\t\t\"mainCfs\":{\n" +
            "\t\t\t\"cfsSpecCode\":\"EUC_ACCESS_GPON\",\n" +
            "\t\t\t\"mainCfsAttrList\":{\n" +
            "\t\t\t\t\"mainCfsAttr\":[\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"IS_GOVERNMENT\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"N\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"IsGovernment\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"PLAN_TYPE\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"Residential CA Customised\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"PlanType\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"USER_NAME\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"SHOMB2BPROD1\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"User Name\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"DEPARTMENT\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"Access Network Services (221)\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Department\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"ACCESS_MODE\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Technology\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"SERVICE_PORT_TYPE\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"Gigabit Ethernet (1Gbps) over XGS-PON\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Service Port Type\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"NO_OF_SERVICE_PORT\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"1\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"No Of Service Port\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"LaLAYER2_OPTION\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"IEEE802.1q\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Layer 2 Protocol\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"LaLAYER3_OPTION\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Layer 3 Protocol\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"PIR_DL\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"PIR Download\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"PIR_UL\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"PIR Upload\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"EIR_DL\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"10000\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"EIR Download\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"EIR_UL\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"10000\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"EIR Upload\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"CONTRACTTERM\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"12\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Contract Term\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"CONTRACT_END_DATE\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"2024-03-02\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Contract End Date\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"CONTRACT_START_DATE\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"2023-03-02\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Contract Start Date\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"SERVICE_TOTAL_BANDWIDTH\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"1\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Service Total Bandwidth\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"BANDWIDTH_TYPE\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"Shared\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Bandwidth Type\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"NLT_ACCOUNT_PWD\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"NLT Account Password\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"NLT_ACCOUNT\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"NLT Account\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"NLT_ORI\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"01-1677745515974-A\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"NLT order Request Identifier\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"NLT_CIRCUIT\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"01-1677745515974-A\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"NLT circuit ID Number\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"SplitterRatio\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"1:24\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Splitter Ratio\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"NLT_FTP_PORT_ID\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"SF018985194851212\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"FTP Port ID\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"NLT_TP_NAME\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"SF018985194851212\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"TP Name\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"NLT_TIE_CABLE_ID\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"OCD/CR37-AM05/SH-23/ROW3/COL42/TERM999\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"Tie Cable Port\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"templateName\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"templateName\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"subscprofstr\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"subscprofstr\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"attrCode\":\"MN\",\n" +
            "\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\"attrName\":\"MN\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t]\n" +
            "\t\t\t},\n" +
            "\t\t\t\"prodEventName\":\"PES_NEW\",\n" +
            "\t\t\t\"cfsSpecName\":\"REUC Access (GPON)\",\n" +
            "\t\t\t\"actionCode\":\"New\",\n" +
            "\t\t\t\"accessMode\":\"GPON\"\n" +
            "\t\t},\n" +
            "\t\t\"orderAttrInfo\":{\n" +
            "\t\t\t\"subCfs\":[\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"cfsSpecCode\":\"EUC_SUB_SERVICE\",\n" +
            "\t\t\t\t\t\"subCfsAttrList\":{\n" +
            "\t\t\t\t\t\t\"subOldRfsAttr\":[\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"SERVICE_PROFILE_NAME\",\n" +
            "\t\t\t\t\t\t\t\t\"rfsAct\":\"CT\",\n" +
            "\t\t\t\t\t\t\t\t\"rfsId\":\"230302162600077648\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"Gamer\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"Service Profile Name\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"SERVICE_PORT\",\n" +
            "\t\t\t\t\t\t\t\t\"rfsAct\":\"CT\",\n" +
            "\t\t\t\t\t\t\t\t\"rfsId\":\"230302162600077648\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"A\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"Service Port\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t],\n" +
            "\t\t\t\t\t\t\"subNewRfsAttr\":[\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"SERVICE_PROFILE_NAME\",\n" +
            "\t\t\t\t\t\t\t\t\"rfsAct\":\"N\",\n" +
            "\t\t\t\t\t\t\t\t\"rfsId\":\"230302162600077648\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"Gamer\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"Service Profile Name\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"SERVICE_PORT\",\n" +
            "\t\t\t\t\t\t\t\t\"rfsAct\":\"N\",\n" +
            "\t\t\t\t\t\t\t\t\"rfsId\":\"230302162600077648\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"A\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"Service Port\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t],\n" +
            "\t\t\t\t\t\t\"cfsId\":\"230302162500077621\",\n" +
            "\t\t\t\t\t\t\"subCfsAttr\":[\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"EUC_SERVICE_PROFILE_NAME\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"Gamer\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"EUC Service Profile Name\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"COS\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"D\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"SH CoS Map\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"CVLAN_ID\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"1492\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"Vlan ID\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"DHCP_OPTION_82\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"Enable\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"DHCP Option 82\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"IGMP_PROXY\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"IGMP Proxy\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"IP_ASSIGNMENT\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"IP Assignment\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"L2L3_SERVICE\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"L2L3 Service\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"L3_OPTION\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"L3 Option\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"SMART_VLAN\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"Smart VLAN\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"SVLAN_ID\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"100\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"SVLAN ID\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"TPID\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"TP ID\"\n" +
            "\t\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\t\"attrCode\":\"VLAN_MANIPULATION\",\n" +
            "\t\t\t\t\t\t\t\t\"oldValue\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"value\":\"\",\n" +
            "\t\t\t\t\t\t\t\t\"attrName\":\"VLAN Manipulation\"\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t\t]\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t\"cfsSpecName\":\"EUC Sub Service\",\n" +
            "\t\t\t\t\t\"actionCode\":\"A\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t]\n" +
            "\t\t}\n" +
            "\t}\n" +
            "}";
}


class User {
    private final int id;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}