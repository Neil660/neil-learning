package com.neil.network.protocol.ldap;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.SimpleBindRequest;
import java.util.Scanner;

/*
 * @Classname LdapServer
 * @Version information V1.0
 * @Date 2024/6/24
 * @Copyright notice iWhaleCloud
 * @userName NEIL
 */
public class LdapServer {
    public static void main(String[] args) {
        try {
            // 设置LDAP服务器运行的端口号
            int port = 20389;

            // 设置LDAP服务器的根Dn
            String rootDn = "dc=telcel,dc=com";

            // 实例化InMemoryDirectoryServerConfig
            InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(rootDn);
            config.setListenerConfigs(
                    InMemoryListenerConfig.createLDAPConfig("ldap", 20389)
            );

            // 创建InMemoryDirectoryServer实例
            InMemoryDirectoryServer ds = new InMemoryDirectoryServer(config);

            // 启动服务器
            ds.startListening();

            // 添加根条目
//            ds.add("dn: dc=telcel,dc=com",
//                    "objectClass: top",
//                    "objectClass: domain",
//                    "dc: telcel");
//
//            // 添加指定的DN
//            ds.add("dn: dc=C,dc=MX,dc=Serv,dc=SPR,dc=telcel,dc=com",
//                    "objectClass: top",
//                    "objectClass: organization",
//                    "o: Organization",
//                    "description: MX SPR Service");

            System.out.println("LDAP服务器运行在端口：" + port);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
