import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

public class Alibaba {
   static String host = "https://stamp.market.alicloudapi.com";
    static String path = "/api/predict/ocr_official_seal";
    static String method = "POST";
    static String appcode = "1d1e32180869403bac01f63d9d4d87df";
//    static String imgFile = "C:\\Users\\xuan_\\Desktop\\公章";
    static Map<String, String> headers = new HashMap<String, String>();
    static Map<String, String> queries = new HashMap<String, String>();
    public static void main(String[] args) {
        System.out.println( "程序启动" );
        Scanner readPath = new Scanner(System.in);
        System.out.println( "请将图片所在文件夹拖入此处" );
        String imgFile = readPath.nextLine();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        File file = new File(imgFile);
        func(file);

    }
    public static void func(File file)
    {
        File[] fs = file.listFiles();
        if (fs != null)
            for(File f:fs)
            {
                if(f.isDirectory())
                    func(f);

                if(f.isFile())
                {
                    String bodies = getBodies(f);
                    JSONObject res = getJsonObject(bodies);
                    assert res != null;
                    JSONArray array = res.getJSONArray("result");
                    String name = "";
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject json = array.getJSONObject(i);
                        JSONObject text = json.getJSONObject("text");
                        String context = text.getString("content");
                        Double prob = text.getDouble("prob");
                        String pre2Char = context.substring(0,1);
                        if (!pre2Char.equals("联") && !pre2Char.equals("云")){
                            if (prob < 0.5 && i < array.size() - 1){
                                continue;
                            }
                            if (prob < 0.5){
                                String[] strArray=f.getName().split("\\.", 2);
                                name = strArray[0];
                                System.out.println("识别率低：" + context);
                            }else {
                                name = context;
                            }
                            break;
                        }
                    }
                    //处理后缀名
                    String path = f.getParent();
                    String oldName = f.getName();
                    String[] strArray = oldName.split("\\.", 2);
                    String type = strArray[1];
                    name = name+"."+type;
                    String newFile=path+"\\"+ name;
                    File newName = new File(newFile);
                    if(f.renameTo(newName)) {
                        System.out.println(oldName + " 已重命名为：" + newName.getName());
                    } else {
                        String newFile2=path+"\\"+ name + "2";
                        File newName2 = new File(newFile2);
                        if(f.renameTo(newName2)) {
                            System.out.println(oldName + " 已重命名2: " + newName2.getName());
                        }else{
                            System.out.println("Error" + newName2.getName());
                        }
                    }
                }
            }
    }
    public static JSONObject getJsonObject(String bodies){
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, queries, bodies);
//            System.out.println(response.toString());
            //获取response的body
            String result = EntityUtils.toString(response.getEntity());
            return JSONObject.parseObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBodies(File file){
        //String bodys = "{\"image\":\"图片二进制数据的base64编码或者图片url\"}";

        // 对图像进行base64编码
        String imgBase64 = "";
        try {
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(encodeBase64(content));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("image", imgBase64);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj.toString();
    }


}
