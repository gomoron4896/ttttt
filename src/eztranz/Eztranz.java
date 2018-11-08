package eztranz;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Eztranz {

	public void fromENG() {
		String clientId = "PAJB3lOr1FswKu03wzvF";
		String clientSecret = "Sjnkx94vbg";

		HttpsURLConnection huc = null;
		BufferedReader br = null;

		try {
			Scanner scan = new Scanner(System.in);
			System.out.print("번역할 한글 문장 입력 후 엔터 : ");
			String qText = scan.nextLine();

			if (qText.isEmpty()) {
				System.out.println("문장을 입력하십시오.");
				return;
			} else {
				System.out.println("번역 시작");
			}

			URL url = new URL("https://openapi.naver.com/v1/papago/n2mt");
			huc = (HttpsURLConnection) url.openConnection();
			huc.setRequestMethod("POST");
			huc.setRequestProperty("Accept-Encoding", "text/html"); 
			huc.setRequestProperty("X-Naver-Client-Id", clientId);
			huc.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			String param = "source=ko&target=en&text=" + qText;
			huc.setDoOutput(true);
			DataOutputStream dos = new DataOutputStream(huc.getOutputStream());
			dos.writeBytes(param);
			dos.flush();
			dos.close();
			

			int status = huc.getResponseCode();

			if (status == 200) {
				br = new BufferedReader(new InputStreamReader(huc.getInputStream(),"UTF-8"));
			} else if (status != 200) {
				br = new BufferedReader(new InputStreamReader(huc.getInputStream()));
				throw new IOException(br.toString());
			}
			String result;
			StringBuffer sb = new StringBuffer();
			while ((result = br.readLine()) != null) {
				sb.append(result);
			}
			br.close();

			ObjectMapper objMapper = new ObjectMapper();
			Map<String, Map<String, Map<String, String>>> tranziMap = objMapper.readValue(sb.toString(), HashMap.class);
//			String rText = tranziMap.get("message").get("result").get("translatedText");
//			System.out.println("입력한 문장 : " + qText);
//			System.out.println("번역된 문장 : " + rText);
			System.out.println(tranziMap);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Trazi().fromENG();
	}

}
