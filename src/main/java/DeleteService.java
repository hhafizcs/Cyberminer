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
    name = "DeleteService",
    urlPatterns = {"/delete"}
)
public class DeleteService extends HttpServlet {
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
			
			//Delete the shifts from the database
			
			Statement deleteShiftsStmt = dbCnx.createStatement();

			deleteShiftsStmt.executeUpdate(
					"DELETE FROM shift " + 
					"WHERE line_id = " + lineId + ";"
			);
			
			deleteShiftsStmt.close();
			
			//Delete the line from the database
			
			Statement deleteLineStmt = dbCnx.createStatement();

			deleteLineStmt.executeUpdate(
					"DELETE FROM line " + 
					"WHERE id = " + lineId + ";"
			);
			
			deleteLineStmt.close();
			
			//Return the response
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		    response.getWriter().write("Line Deleted!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}