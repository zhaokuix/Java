import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

public class Alibaba {
   static String host = "https://stamp.market.alicloudapi.com";
    static String path = "/api/predict/ocr_official_seal";
    static String method = "POST";
    static String appcode = "1d1e32180869403bac01f63d9d4d87df";
    static String imgFile = "C:\\Users\\xuan_\\Desktop\\file\\img";
    static Map<String, String> headers = new HashMap<String, String>();
    static Map<String, String> querys = new HashMap<String, String>();
    public static void main(String[] args) {
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

//                    org.json.JSONObject res = client.basicGeneral(f.getPath(), new HashMap<String, String>());//获取链接信息
                    //处理识别信息
//                    JSONArray res1 =res.getJSONArray("words_result");
//                    String code=res1.getJSONObject(1).getString("words");
//                    int codeStart = code.indexOf(":");
//                    code = code.substring(codeStart + 1);
//                    String name1=res1.getJSONObject(3).getString("words");
//                    String name2=res1.getJSONObject(4).getString("words");
//                    String name3=res1.getJSONObject(5).getString("words");
//                    String name = name1 + name2 + name3;
//                    int left = name.indexOf("权");
//                    int right = name.indexOf("为");
//                    name = name.substring(left + 1, right);
//                    //System.out.println(res1);//拼接字符串中的字符 处理错误信息
////                name=name.replaceAll("/", "1");
////                name=name.replaceAll("\\.", "");
//                    System.out.println(name);
                    //处理后缀名
                    String path=f.getParent();
                    String Old_name=f.getName();
                    String[] strArray=Old_name.split("\\.", 2);
                    String type=strArray[1];
                    name=name+"."+type;

                    System.out.println(Old_name + "--" + name);

                    String newFile=path+"\\"+ name;

                    File newName = new File(newFile);
                    if(f.renameTo(newName)) {
                        System.out.println("已重命名");
                    } else {
                        String newFile2=path+"\\"+ name + "2";

                        File newName2 = new File(newFile2);
                        if(f.renameTo(newName2)) {
                            System.out.println("已重命名2");
                        }else{
                            System.out.println("Error" + newName2.getName());
                        }
                    }
                }
            }
    }
    public static JSONObject getJsonObject(String bodys){
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
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
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
