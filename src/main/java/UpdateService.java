import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet(
    name = "UpdateService",
    urlPatterns = {"/update"}
)
public class UpdateService extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Parse the input
			
			JSONObject requestObj = null;
			StringBuffer stringBuffer = new StringBuffer();
			
			BufferedReader reader = request.getReader();
			String bufferline = null;
			
		    while ((bufferline = reader.readLine()) != null)
		    	stringBuffer.append(bufferline);

		    requestObj = new JSONObject(stringBuffer.toString());
			
			int lineId = requestObj.getInt("lineId");
			
			//Connect to the database
			
			//Connection dbCnx = DriverManager.getConnection("jdbc:mysql://google/cyberminer?cloudSqlInstance=cyberminer-shae:us-central1:cyberminer&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=cyberminer&useSSL=false");
			Connection dbCnx = DriverManager.getConnection("jdbc:mysql://35.188.65.89/cyberminer","root","cyberminer");
			
			//Update the line access frequency
			
			Statement updateLineStmt = dbCnx.createStatement();

			updateLineStmt.executeUpdate(
					"UPDATE line " +
					"SET access_frequency = access_frequency + 1 " +
					"WHERE id = " + lineId + ";"
			);
			
			updateLineStmt.close();
			
			//Return the response
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		    response.getWriter().write("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}