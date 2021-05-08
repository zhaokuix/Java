import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;


public class Img2PDF {
    /*** @param picturePath 图片地址*/
    private static void createPic(Document document, String picturePath) {
        try {
            Image image = Image.getInstance(picturePath);
//            float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
            float documentWidth = document.getPageSize().getWidth();
//            float documentHeight = documentWidth / 580 * 320;//重新设置宽高
            float documentHeight = document.getPageSize().getHeight();//重新设置宽高
            image.scaleAbsolute(documentWidth, documentHeight);//重新设置宽高
            document.add(image);
        } catch (Exception ex) {
        }
    }
    public static void image2pdf(String text, String pdf) throws DocumentException, IOException {
        Document document = new Document();
        document.setMargins(0,0,0,0);
        OutputStream os = new FileOutputStream(new File(pdf));
        PdfWriter.getInstance(document,os);
        document.open();
        createPic(document,text);
        document.close();
    }

        public static void main(String[] args) throws Exception {
            File file = new File("C:\\Users\\xuan_\\Desktop\\file\\已完成");
            process(file);
    }
    static void process(File file) throws Exception {
        File[] fs = file.listFiles();
        if (fs != null)
            for (File f : fs) {
                if (f.isFile()){
                    String[] strArray=f.getName().split("\\.", 2);
                    image2pdf(f.getPath(), f.getParent() + "\\" + strArray[0] + ".pdf");
                    if (f.delete()){
                        System.out.println("删除成功：" + f.getName());
                    }
                }else {
                    process(f);
                }

            }
    }

}
