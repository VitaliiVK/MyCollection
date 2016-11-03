package myCollectionService;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//класс для обработки изображений перед загрузкой в базу данных
@Service
public class PhotoService {
    public byte[] cropPhoto(byte[] originArr){
        byte[] cropArr = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(originArr);
            BufferedImage img = ImageIO.read(is);
            int height = img.getHeight();
            int width = img.getWidth();
            int scaledWidth = 0;
            int scaledHeight = 0;
            if(height > 1000 || width > 1000){
                if(height>width){
                    scaledWidth = (int)(width/(height/1000.0));
                    scaledHeight = 1000;
                }
                else {
                    scaledHeight = (int)(height/(width/1000.0));
                    scaledWidth = 1000;
                }
            }
            else {
                scaledWidth = width;
                scaledHeight = height;
            }

            BufferedImage bimage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D bGr = bimage.createGraphics();

            Image scaled = img.getScaledInstance(scaledWidth, scaledHeight, BufferedImage.SCALE_SMOOTH);
            bGr.drawImage(scaled, 0, 0, null);
            bGr.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bimage, "jpg", os);
            cropArr = os.toByteArray();
        } catch (IOException e) {
            System.out.println("photoCropError");
        }
        return cropArr;
    }
}
