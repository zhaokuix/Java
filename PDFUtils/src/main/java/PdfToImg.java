import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class PdfToImg {
    public static void pdf2Pic(String pdfPath, String path, String fileName) throws Exception {
        Document document = new Document();
        document.setFile(pdfPath);
        //缩放比例
        float scale = 1f;
        //旋转角度
        float rotation = 0f;

        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage)
                    document.getPageImage(i, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
            RenderedImage rendImage = image;
            try {
                String imgName = fileName + ".png";
                System.out.println(imgName);
                File file = new File(path + imgName);
                ImageIO.write(rendImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.flush();
        }
        document.dispose();
    }
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\xuan_\\Desktop\\file\\img";
//        String imgPath = "C:\\Users\\xuan_\\Desktop\\file\\img\\";
        File file = new File(filePath);
        process(file);
    }
    static void process(File file) throws Exception {
        File[] fs = file.listFiles();
        if (fs != null)
            for (File f : fs) {
                if (f.isFile()){
                    String path = f.getPath();
                    String[] strArray=f.getName().split("\\.", 2);
                    pdf2Pic(path, f.getParent() + "\\", strArray[0]);
                    if (f.delete()){
                        System.out.println("删除成功：" + f.getName());
                    }
                }else {
                    process(f);
                }

            }
    }
}
