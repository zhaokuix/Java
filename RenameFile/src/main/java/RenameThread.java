import java.io.File;
import java.util.Map;

/**
 * @author kouzk
 */
public class RenameThread implements Runnable {

    File file;
    Map<String, String> codeMapName;

    public RenameThread(File file, Map<String, String> map) {
        this.file = file;
        this.codeMapName = map;
    }

    @Override
    public void run() {
        renameIt(file);
    }

    public void renameIt(File file){
        if (file != null) {
            //获取文件夹下的数据
            File[] fs = file.listFiles();
            assert fs != null;
            for (File f : fs) {
                if (f.isFile()) {
                    //读取路径
                    String path = f.getParent();
                    //获取文件名
                    String oldName = f.getName();
                    String [] oldNames = oldName.split("-");
                    String code = oldNames[0].toLowerCase();
                    //获取扩展名
                    oldNames = oldName.split("\\.");
                    String extName = oldNames[oldNames.length - 1];
                    //新文件名
                    String name = codeMapName.get(code);
                    String newFile = path + "\\" + oldNames[0] + "-" + name + "." +extName;
                    File newName = new File(newFile);
                    //重命名
                    if (f.renameTo(newName)) {
                        System.out.println(oldName + " 已重命名为 " + name);
                    } else {
                        System.out.println(oldName + "Error");
                    }
                }else {
                    renameIt(f);
                }
            }
        }
    }
}
