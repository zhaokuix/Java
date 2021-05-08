import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

public class App {
    public static final String APP_ID = "20735905";
    public static final String API_KEY = "8DFogsgm052lBiTlpGyeMyQF";
    public static final String SECRET_KEY = "1butOYn6GrS3dCKI7I43DldOw5sisg4S";
    public static final String Url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic\r\n";
    public static void main( String[] args )
    {
        System.out.println( "程序启动" );
        @SuppressWarnings("resource")
//        Scanner ReadPath = new Scanner(System.in);
//        System.out.println( "请输入图片文件夹路径" );
//        String path=ReadPath.nextLine();
        String path = "C:\\Users\\xuan_\\Desktop\\file";
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        File file = new File(path);
        func(file,client);
        System.out.println( "程序运行完毕" );

    }
    public static void func(File file,AipOcr client)
    {
        File[] fs = file.listFiles();
        if (fs != null)
        for(File f:fs)
        {
            if(f.isDirectory())
                func(f,client);

            if(f.isFile())
            {

                JSONObject res = client.basicGeneral(f.getPath(), new HashMap<String, String>());//获取链接信息
                //处理识别信息
                JSONArray res1 =res.getJSONArray("words_result");
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
                    System.out.println("Error" + newName.getName());
                }
            }
        }
    }

}
