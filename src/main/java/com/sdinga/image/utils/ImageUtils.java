package com.sdinga.image.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);

    public static InputStream download(String url) {
        return download(url, null);
    }

    /**
     * @param url         图片url
     * @param cookieStore 下载图片需要的cookie,没有传递null
     * @return InputStream io流
     */
    public static InputStream download(String url, CookieStore cookieStore) {
        try {
            HttpGet request = new HttpGet(url);
            HttpClientContext context = HttpClientContext.create();
            context.setCookieStore(cookieStore);
            HttpClient httpClient = HttpClients.custom().build();
            RequestConfig requestBuilder = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
                    .build();
            request.setConfig(requestBuilder);
            HttpResponse response = httpClient.execute(request, context);
            return response.getEntity().getContent();
        } catch (IOException e) {
            LOGGER.error("download file image error", e);
        }
        return null;
    }

    /**
     * 处理文件格式大小
     *
     * @param srcImagePath url<本地>
     */
    public static byte[] resizeImageFile(String srcImagePath, int width, int height) {
        File file = new File(srcImagePath);
        try {
            InputStream input = new FileInputStream(file);
            return resizeImageFile(input, width, height);
        } catch (FileNotFoundException e) {
            LOGGER.error("resizeImageFile error", e);
        }
        return new byte[0];
    }

    /**
     * 处理文件格式大小
     *
     * @param srcImageIo InputStream 文件
     * @param width      宽
     * @param height     高
     */
    public static byte[] resizeImageFile(InputStream srcImageIo, int width, int height) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Image srcImg = ImageIO.read(srcImageIo);
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            ImageIO.write(buffImg, "JPEG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            LOGGER.error("image type error", e);
        }
        return null;
    }
}
