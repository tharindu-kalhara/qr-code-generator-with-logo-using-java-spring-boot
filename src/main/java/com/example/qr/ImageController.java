package com.example.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author kalhara
 * @version 1.0
 * @since 2018-12-21
 */
@CrossOrigin(value = "*")
@RestController
@RequestMapping("qr")
public class ImageController {

    @Value("${am.qr.file.path}")
    private String path;

    @GetMapping("/login/dynamic.png")
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException, WriterException {
        Date date = new Date();
        Map hints = new HashMap();
        // Load file as Resource
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        bitMatrix = writer.encode(request.getRemoteAddr().replace(":", "").replace(".", "") + date.getTime(), BarcodeFormat.QR_CODE, 250, 250, hints);
        // Create a qr code with the url as content and a size of 250x250 px
        MatrixToImageConfig config = new MatrixToImageConfig(Colors.BLACK.getArgb(), Colors.WHITE.getArgb());
        // Load QR image
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
        // Load logo image
        BufferedImage logoImage = ImageIO.read(new File("src/main/resources/icon.png"));
        // Calculate the delta height and width between QR code and logo
        int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
        int deltaWidth = qrImage.getWidth() - logoImage.getWidth();
        // Initialize combined image
        BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) combined.getGraphics();
        // Write QR code to new image at position 0/0
        g.drawImage(qrImage, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        // Write logo into combine image at position (deltaWidth / 2) and
        // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
        // the same space for the logo to be centered
        g.drawImage(logoImage, Math.round(deltaWidth / 2), Math.round(deltaHeight / 2), null);
        // Write combined image as PNG to OutputStream
        File qrFile = new File(path + request.getRemoteAddr().replace(":", "").replace(".", "") + date.getTime() + ".png");
        ImageIO.write(combined, "png", qrFile);
        FileUrlResource fileUrlResource = new FileUrlResource(qrFile.getPath());
        response.addHeader("fileName", fileUrlResource.getFile().getName());
        System.out.println("done");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(fileUrlResource);
    }
}
