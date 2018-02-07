package com.yw.databus.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SystemUtil {
    public static Map<Object, Object> getSystemConf() {
        HashMap<Object, Object> SysConfig = new HashMap<>();
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException();
        }
        Map<String, String> sys = System.getenv();
        SysConfig.put("用户名", sys.get("USERNAME"));
        SysConfig.put("计算机名", sys.get("COMPUTERNAME"));
        SysConfig.put("计算机域名", sys.get("USERDOMAIN"));
        SysConfig.put("ip", addr.getHostAddress());
        SysConfig.put("host", addr.getHostName());
        SysConfig.put("JVM可以使用的总内存", r.totalMemory());
        SysConfig.put("JVM可以使用的剩余内存", r.freeMemory());
        SysConfig.put("JVM可以使用的处理器个数", r.availableProcessors());
        SysConfig.put("Java的运行环境版本", props.getProperty("java.version"));
        SysConfig.put("Java的运行环境供应商", props.getProperty("java.vendor"));
        SysConfig.put("Java供应商的URL", props.getProperty("java.vendor.url"));
        SysConfig.put("Java的安装路径", props.getProperty("java.home"));
        SysConfig.put("Java的虚拟机规范版本", props.getProperty("java.vm.specification.version"));
        SysConfig.put("Java的虚拟机规范供应商", props.getProperty("java.vm.specification.vendor"));
        SysConfig.put("Java的虚拟机规范名称", props.getProperty("java.vm.specification.name"));
        SysConfig.put("Java的虚拟机实现版本", props.getProperty("java.vm.version"));
        SysConfig.put("Java的虚拟机实现供应商", props.getProperty("java.vm.vendor"));
        SysConfig.put("Java的虚拟机实现名称", props.getProperty("java.vm.name"));
        SysConfig.put("Java运行时环境规范版本", props.getProperty("java.specification.version"));
        SysConfig.put("Java运行时环境规范供应商", props.getProperty("java.specification.vender"));
        SysConfig.put("Java运行时环境规范名称", props.getProperty("java.specification.name"));
        SysConfig.put("Java的类格式版本号", props.getProperty("java.class.version"));
        SysConfig.put("Java的类路径", props.getProperty("java.class.path"));
        SysConfig.put("加载库时搜索的路径列表", props.getProperty("java.library.path"));
        SysConfig.put("默认的临时文件路径", props.getProperty("java.io.tmpdir"));
        SysConfig.put("一个或多个扩展目录的路径", props.getProperty("java.ext.dirs"));
        SysConfig.put("操作系统的名称", props.getProperty("os.name"));
        SysConfig.put("操作系统的构架", props.getProperty("os.arch"));
        SysConfig.put("操作系统的版本", props.getProperty("os.version"));
        SysConfig.put("文件分隔符", props.getProperty("file.separator"));
        SysConfig.put("路径分隔符", props.getProperty("path.separator"));
        SysConfig.put("行分隔符", props.getProperty("line.separator"));
        SysConfig.put("用户的账户名称", props.getProperty("user.name"));
        SysConfig.put("用户的主目录", props.getProperty("user.home"));
        SysConfig.put("用户的当前工作目录", props.getProperty("user.dir"));
        return SysConfig;
    }
}
