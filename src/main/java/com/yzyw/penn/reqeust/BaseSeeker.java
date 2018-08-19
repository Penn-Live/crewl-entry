package com.yzyw.penn.reqeust;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/** 基础选取器 **/
public  abstract class BaseSeeker<T> extends PropertiesConfiguration{


    public BaseSeeker(String configPath) throws ConfigurationException {
        super(configPath);
    }


    protected abstract T getRequestResult(String url, String params);


    protected Set<String> doSeek(Function<T, Set<String>> processDate) {
        String url=getString("url");
        List<T> resultJsonObjet = configParams().stream()
                .map(params -> {
                    System.out.println("get data form singUrl-->" +url);
                    System.out.println("params-->" + formatParms(params));
                    return getRequestResult(url,params);
                }).collect(Collectors.toList());

        System.out.println("seek done!");
        //处理数据
        if (!CollectionUtils.isEmpty(resultJsonObjet)) {
            return resultJsonObjet.stream()
                    .map(jsonObject -> processDate.apply(jsonObject))
                    .collect(Collectors.toSet())
                    .stream().reduce((pre, next) -> {
                        if (pre!=null) {
                            pre.addAll(next);
                        }
                        return pre;
                    }).get();
        }

        return Sets.newHashSet();

    }

    //格式化
    protected  String formatParms(String params){
       return "\n【\n"+Lists.newArrayList(StringUtils.split(params,"&"))
                .stream().reduce((pre,next)->{
                    pre+="\n"+next;
                    return pre;
        }).get()+"\n】";

    }

    //配置get的url或者params
    protected  List<String> configParams(){
        List<String> ss = Lists.newArrayList();
        String pageName = getString("pageName");
        String pageSizeName = getString("pageSizeName");
        String baseParams = getString("params");
        Integer endPage = getInteger("endPage", 100);
        for (Integer i = getInteger("startPage",1); i <=endPage; i++) {
            String paddingParams = "&" + pageName + "=" + i + "&" + pageSizeName + "=" + getString("pageSize");
            String copyOfBaseParams=baseParams;
            copyOfBaseParams += paddingParams;
            ss.add(copyOfBaseParams);
        }
        return ss;
    }

    //转化为单字符
    protected static String transforSetToSingStr(Set<String> set){
        String resultStr = set.toString();
        return resultStr.replace("\"", "\'");

    }

    //保存到文件
    protected static void saveToFile(String pathAndName,String contet){
        try {
            FileUtils.write(new File(pathAndName), contet,"utf8",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
