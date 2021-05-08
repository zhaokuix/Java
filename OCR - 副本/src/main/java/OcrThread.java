import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class OcrThread implements Runnable{
    File file;
    public OcrThread(File file){
        this.file = file;
    }

    public void run() {
        if (file != null){
            AipOcr client = new AipOcr("20735905", "8DFogsgm052lBiTlpGyeMyQF", "1butOYn6GrS3dCKI7I43DldOw5sisg4S");
            client.setConnectionTimeoutInMillis(10000);
            client.setSocketTimeoutInMillis(100000);
            File[] fs = file.listFiles();
            if (fs != null)
            for(File f:fs)
            {
                if(f.isFile())
                {

                    JSONObject res = client.basicGeneral(f.getPath(), new HashMap<String, String>());//获取链接信息
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //处理识别信息
                    JSONArray res1 = null;
                    try{
                        res1 = res.getJSONArray("words_result");
                    }catch (Exception e){
                        System.out.println(f.getName());
                    }
                    assert res1 != null;
                    String code=res1.getJSONObject(1).getString("words");
                    int codeStart = code.indexOf(":");
                    code = code.substring(codeStart + 1);
                    String name1=res1.getJSONObject(3).getString("words");
                    String name2=res1.getJSONObject(4).getString("words");
                    String name3=res1.getJSONObject(5).getString("words");
                    String name = name1 + name2 + name3;
                    int left = name.indexOf("权");
                    int right = name.indexOf("为");
                    name = name.substring(left + 1, right);
                    //System.out.println(res1);//拼接字符串中的字符 处理错误信息
//                name=name.replaceAll("/", "1");
//                name=name.replaceAll("\\.", "");
                    System.out.println(name);
                    //处理后缀名
                    String path=f.getParent();
                    String Old_name=f.getName();
                    String[] strArray=Old_name.split("\\.", 2);
                    String type=strArray[1];
                    String currentName = strArray[0];
                    name=name+"."+type;

                    String newFile=path+"\\" +code+"-" + name;

                    File newName = new File(newFile);
                    if(f.renameTo(newName)) {
                        System.out.println("已重命名");
                    } else {
                        System.out.println("Error");
                    }
                }
            }
        }else {
            return;
        }
    }
}
