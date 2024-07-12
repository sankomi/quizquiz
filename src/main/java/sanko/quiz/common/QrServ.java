package sanko.quiz.common;

import java.util.Base64;
import java.io.*; //ByteArrayOutputStream, IOException

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.google.zxing.*; //MultiFormatWriter, BarcodeFormat, WriterException
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@RequiredArgsConstructor
@Service
public class QrServ {

	private final MultiFormatWriter writer = new MultiFormatWriter();

	public String create(String text) {
		try {
			BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 200, 200);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(matrix, "png", stream);
			return Base64.getEncoder().encodeToString(stream.toByteArray());
		} catch (WriterException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
