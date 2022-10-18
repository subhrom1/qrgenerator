package com.example.qrgenerator.controller;

import com.example.qrgenerator.core.KeyUtil;
import com.example.qrgenerator.core.ScQRCodeWriter;
import com.google.zxing.*;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * The controller class which accepts requests for QR Code
 * Generation. This class uses the 256-bit encryption key to
 * generate mask secure QR codes.
 */
@RestController
public class QRGenController {

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";

    @Autowired
    private Environment environment;

    /**
     * Secure QR code generation endpoint. Generates secure QR codes by using a 256 bit
     * encryption key. The secure QR code can only be detected and scanned by a reader which
     * has the encrypted key.
     *
     * @param payload The QR message
     * @return QR generation response
     * @throws WriterException if QR code cannot be generated
     * @throws IOException     if there is an exection in creating QR code.
     */
    @GetMapping("/generateQR")
    public ResponseEntity index(@RequestParam String payload) throws WriterException, IOException {

        //Generates QR
        generateQR(payload);

        //Returns the QR image to caller
        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
        Resource resource = new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

    /*
     * Fetches secure key and uses it for secure QR code generation.
     */
    private void generateQR(String payload) throws WriterException, IOException {
        if (payload != null) {
            ScQRCodeWriter writer = new ScQRCodeWriter();
            String key = environment.getProperty("key");
            int[] keyArr = KeyUtil.hexToBinary(Hex.encodeHexString(key.getBytes()));
            BitMatrix matrix = writer.encode(payload, BarcodeFormat.QR_CODE, keyArr, 1000, 1000);

            Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH);
            File file = new File(QR_CODE_IMAGE_PATH);
            file.createNewFile();
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);
        }
    }
}