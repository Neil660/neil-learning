package com.neil;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @Decription Bloom Filter
 * 由二进制矢量和一些列的hash函数组成，不存储key，存储时通过hash函数转换的bit数组的值1
 * 优点：空间效率和查询时间 缺点：有一定的误识别率和删除困难
 * 使用场景：①检查单子拼写正确性 ②检测海量名单嫌疑人 ③垃圾邮件过滤 ④索爬虫URL去重 ⑤缓存穿透过滤
 *
 * @Author NEIL
 * @Date 2023/3/6 14:00
 * @Version 1.0
 */
public class TestBloomFilter {
    private static int total = 1000000; // 100万数据

    /**
     * BloomFilter.create()的入参
     * funnel：由Funnels直接获取
     * expectedInsertions：期待插入的数据数量
     * fpp：期望的假阳性概率，<1.0
     */
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), total, 0.0003);

    public static void main(String[] args) {
        for (int i = 0; i < total; i++) {
            bloomFilter.put(i); // 初始化100万条数据到过滤器中
        }

        for (int i = 0; i < total; i++) {
            if (!bloomFilter.mightContain(i)) {
                System.out.println("超过了布隆过滤器，直接进入了数据库");
            }
        }

        // 匹配不要再过滤器中的1万个值，有多少匹配出来
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
            }
        }
        System.out.println("误伤数量：" + count);

    }
}
