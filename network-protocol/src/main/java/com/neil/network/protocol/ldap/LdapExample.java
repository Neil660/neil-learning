package com.neil.network.protocol.ldap;

import com.alibaba.fastjson.JSONObject;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/*
 * 常见标签：
    DN（Distinguished Name）：用于唯一标识一个对象的名称，它由多个RDN（Relative Distinguished Name）组成，RDN之间使用逗号分隔。
        例如，"cn=John Doe,ou=Users,dc=example,dc=com"表示一个用户对象的DN，其中"cn=John Doe"是RDN，"ou=Users"是上一级组织单位的RDN，"dc=example,dc=com"是更高级域的RDN。
        CN（Common Name）：通常用于表示对象的名称或标识符，例如用户的姓名或组的名称。在一个DN中，CN是RDN的一部分。
        OU（Organizational Unit）：组织单位，用于对对象进行分组或分类。它可以用于组织用户、组、设备等对象。
        DC（Domain Component）：域组件，用于表示一个域的名称。多个DC可以组成一个完整的域名。
        SN（Surname）：姓氏，用于表示对象的姓氏或姓。
        C（国家）：表示条目所在的国家
        O（组织）：表示条目所属的组织或公司。
        L（位置）：表示条目所在的位置。
        ST（州/省）：表示条目所在的州或省份
 */
public class LdapExample {
    public static void main(String[] args) {
        // Set up the connection properties
        String ldapUrl = "ldap://192.168.129.128:389"; // URL of the Ldap server
        String ldapUser = "cn=admin,dc=node3,dc=com"; // Ldap username
        String ldapPassword = "123456"; // Ldap password

        // Set up the environment properties
        java.util.Hashtable<String, String> env = new java.util.Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // Use the Ldap context factory
        env.put(Context.PROVIDER_URL, ldapUrl); // Set the Ldap server URL
        env.put(Context.SECURITY_AUTHENTICATION, "simple"); // Use simple authentication
        env.put(Context.SECURITY_PRINCIPAL, ldapUser); // Set the Ldap username
        env.put(Context.SECURITY_CREDENTIALS, ldapPassword); // Set the Ldap password
        env.put("java.naming.ldap.factory.socket", "com.neil.network.protocol.ldap.CustomSocketFactory"); // 自定义socket
        env.put("com.sun.jndi.ldap.connect.timeout", "5000");
        env.put("com.sun.jndi.ldap.read.timeout", "5000");

        LdapContext ldapContext = null;
        try {
//            // Create the Ldap context
//            DirContext ctx = new InitialDirContext(env);
//            // Create a new Ldap entry
//            Attributes attrs = new BasicAttributes();
//            attrs.put(new BasicAttribute("objectClass", "inetOrgPerson")); // Specify the object class
//            attrs.put(new BasicAttribute("cn", "John Doe")); // Set the common name
//            attrs.put(new BasicAttribute("sn", "Doe")); // Set the surname
//            attrs.put(new BasicAttribute("givenName", "John")); // Set the given name
//            attrs.put(new BasicAttribute("mail", "johndoe@example.com")); // Set the email
//            attrs.put(new BasicAttribute("telephoneNumber", "1234567890")); // Set the telephone number
//            ctx.createSubcontext("cn=John Doe,ou=Users,dc=example,dc=com", attrs); // Add the entry to the Ldap database
//            // Close the Ldap context
//            ctx.close();

            ldapContext = new InitialLdapContext(env, null);

            Scanner sc;
            while (true) {
                sc = new Scanner(System.in);
                String enter = sc.nextLine();
                if (enter.equalsIgnoreCase("close")) {
                    ldapContext.close();
                    System.out.println("LDAP客户端正在关闭");
                    break;
                }
                else if (enter.equalsIgnoreCase("query")) {
                    try {
                        String dn = "MSISDN=123,cn=admin,dc=node3,dc=com";
                        SearchControls searchCtrls = new SearchControls();
                        searchCtrls.setSearchScope(1);
                        NamingEnumeration<SearchResult> sr = ldapContext.search(dn, "objectclass=*", searchCtrls);
                        StringBuilder sb = new StringBuilder();
                        while (sr.hasMore()) {
                            SearchResult result = sr.next();
                            NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                            while (attrs.hasMore()) {
                                Attribute attr = attrs.next();
                                sb.append(attr.getID()).append(": ").append((String) attr.get()).append("\n");
                            }
                            sb.append("\n");
                        }
                    }
                    catch (NamingException e) {
                        e.printStackTrace();
                    }
                }
            }


        } catch (NamingException e) {
            e.printStackTrace();
            if (ldapContext != null) {
                try { //释放连接失败的资源
                    ldapContext.close();
                }
                catch (Exception closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
}
