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
    public static void main( String[] args )
    {
        System.out.println( "程序启动" );
        Scanner ReadPath = new Scanner(System.in);
        System.out.println( "请将图片所在文件夹拖入此处" );
        String path=ReadPath.nextLine();
//        String path = "C:\\Users\\xuan_\\Desktop\\测试";
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        File file = new File(path);
        func(file,client,APP_ID,API_KEY,SECRET_KEY);
        System.out.println( "程序启动完毕" );

    }
    private static void func(File file, AipOcr client, String appId, String apiKey, String secretKey)
    {
        new Thread(new OcrThread(file,client,appId,apiKey,secretKey)).start();
        File[] fs = file.listFiles();
        if (fs != null)
        for(File f:fs)
        {
            if(f.isDirectory()) {
                func(f, client, appId, apiKey, secretKey);
            }
        }
    }

}
