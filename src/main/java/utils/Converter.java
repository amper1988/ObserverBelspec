package utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import model.CarDataForLists;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit.Api;
import retrofit.model.CarDataShort;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.ParseException;

public class Converter {
    public static String convertResponseToSting(ResponseBody responseBody) {
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Buffer buffer = source.buffer();
        Charset charset = Api.UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(Api.UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }

        if (contentLength != 0) {
            return buffer.clone().readString(charset);
        }
        try {
            return responseBody.bytes().toString();
        } catch (IOException e) {
            return "";
        }
    }

    public static byte[] convertBase64StringToByteArray(String base64string) {
        if (base64string == null || base64string.equals(""))
            return null;
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            return base64Decoder.decodeBuffer(base64string);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CarDataForLists convertCarDataShortToCarDataForLists(CarDataShort carDataShort, int index) throws ParseException {
        if (carDataShort != null) {
            CarDataForLists carDataForLists = new CarDataForLists();
            carDataForLists.setIdentifier(carDataShort.getIdentifier());
            carDataForLists.setManufacture(carDataShort.getManufacture());
            carDataForLists.setCarId(carDataShort.getCarId());
            carDataForLists.setClause(carDataShort.getClause());
            carDataForLists.setModel(carDataShort.getModel());
            carDataForLists.setEvacuationDate(carDataShort.getEvacuationDate());
            carDataForLists.setProtocolNumber(carDataShort.getProtocolNumber());
            carDataForLists.setPoliceDepartment(carDataShort.getPoliceDepartment());
            carDataForLists.setParkingDate(carDataShort.getParkingDate());
            carDataForLists.setBlock(carDataShort.isBlock());
            carDataForLists.setPoliceOfficer(carDataShort.getPoliceOfficer());
            carDataForLists.setRoadLawPoint(carDataShort.getRoadLawPoint());
            carDataForLists.setParking(carDataShort.getParking());
            carDataForLists.setEvacuationAddress(carDataShort.getEvacuationAddress());
            carDataForLists.setIndexInTable(index);
            return carDataForLists;
        }
        return null;

    }

    public static Image convertBase64StringToImage(String str) {
        if (str == null || str.equals(""))
            return null;
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            ByteArrayInputStream rocketInputStream = new ByteArrayInputStream(base64Decoder.decodeBuffer(str));
            return new Image(rocketInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertImageToBase64String(Image img) {
        if (img != null) {
            try {
                BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", baos);
                baos.flush();
                BASE64Encoder encoders = new BASE64Encoder();
                String result = encoders.encode(baos.toByteArray());
                baos.close();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }
}
