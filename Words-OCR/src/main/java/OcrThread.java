import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class OcrThread implements Runnable {
    File file;
    AipOcr client;
    String appId;
    String apiKey;
    String secretKey;

    public OcrThread(File file, AipOcr client, String appId, String apiKey, String secretKey) {
        this.file = file;
        this.client = client;
        this.appId = appId;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public void run() {
        if (file != null) {
            AipOcr client = new AipOcr(appId, apiKey, secretKey);
            client.setConnectionTimeoutInMillis(10000);
            client.setSocketTimeoutInMillis(100000);
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("language_type", "CHN_ENG");
            options.put("detect_direction", "true");
            options.put("detect_language", "true");
            options.put("probability", "true");
            File[] fs = file.listFiles();
            if (fs != null){
                for (File f : fs) {
                    if (f.isFile()) {
                        JSONObject res = client.basicGeneral(f.getPath(), options);//获取链接信息
                        try {
                            //睡眠一秒
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //处理识别信息
                        JSONArray res1 = null;
                        try {
                            res1 = res.getJSONArray("words_result");
                        } catch (Exception e) {
                            System.out.println(f.getName());
                        }
                        assert res1 != null;
                        String code = res1.getJSONObject(1).getString("words");
                        int codeStart = code.indexOf(":");
                        code = code.substring(codeStart + 1);
                        String name1 = res1.getJSONObject(3).getString("words");
                        String name2 = res1.getJSONObject(4).getString("words");
                        String name3 = res1.getJSONObject(5).getString("words");
                        String name = name1 + name2 + name3;
                        int left = name.indexOf("权");
                        int right = name.indexOf("为");
                        name = name.substring(left + 1, right);
                        //处理后缀名
                        String path = f.getParent();
                        String oldName = f.getName();
                        String[] strArray = oldName.split("\\.", 2);
                        String type = strArray[1];
//                    String currentName = strArray[0];
                        name = name + "." + type;
                        String newFile = path + "\\" + code + "-" + name;
                        File newName = new File(newFile);
                        if (f.renameTo(newName)) {
                            System.out.println("已重命名: " + newName.getName());
                        } else {
                            System.out.println("Error：" + oldName + "重命名失败");
                        }
                    }
                }
            }
        }
    }
}
