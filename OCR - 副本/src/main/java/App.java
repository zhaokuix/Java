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
        @SuppressWarnings("resource")
//        Scanner ReadPath = new Scanner(System.in);
//        System.out.println( "请输入图片文件夹路径" );
//        String path=ReadPath.nextLine();
        String path = "C:\\Users\\xuan_\\Desktop\\福建";
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        File file = new File(path);
        func(file,client);
        System.out.println( "程序运行完毕" );

    }
    private static void func(File file,AipOcr client)
    {
        new Thread(new OcrThread(file)).start();
        File[] fs = file.listFiles();
        if (fs != null)
        for(File f:fs)
        {
            if(f.isDirectory()) {
                func(f, client);
            }
        }
    }

}
