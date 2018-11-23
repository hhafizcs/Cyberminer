import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import KWIC.KWIC;
import KWIC.Line;

@SuppressWarnings("serial")
@WebServlet(
    name = "AddService",
    urlPatterns = {"/add"}
)
public class AddService extends HttpServlet {
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
			
			String descriptor = requestObj.getString("descriptor");
			String url = requestObj.getString("url");
			int payment = requestObj.getInt("payment");
			
			//Connect to the database
			
			//Connection dbCnx = DriverManager.getConnection("jdbc:mysql://google/cyberminer?cloudSqlInstance=cyberminer-shae:us-central1:cyberminer&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=cyberminer&useSSL=false");
			Connection dbCnx = DriverManager.getConnection("jdbc:mysql://35.188.65.89/cyberminer","root","cyberminer");
			
			//Insert the line to the database
			
			Statement insertLineStmt = dbCnx.createStatement();

			insertLineStmt.executeUpdate(
					"INSERT INTO line " + 
					"(url, descriptor, payment) " + 
					"VALUES ('" + url + "', '" + descriptor + "', " + payment + ");"
			, Statement.RETURN_GENERATED_KEYS);
			
            ResultSet insertLineResSet = insertLineStmt.getGeneratedKeys();
			
            insertLineResSet.next();
			
			int lineId = insertLineResSet.getInt(1);
			
			insertLineStmt.close();
			
			//Generate the Circular Shifts
			
			Line line = new Line(descriptor, url);
			KWIC kwic = new KWIC(line);
			kwic.setup();
			
			//Insert the Circular Shifts to the database
			
			ArrayList<Line> sortedLines = kwic.getAlphabetizer().getSortedLines();
			
			for(int i = 0; i < sortedLines.size(); i++) {
				Statement insertShiftStmt = dbCnx.createStatement();

				insertShiftStmt.executeUpdate(
						"INSERT INTO shift " +
						"VALUES (" + lineId + ", '" + sortedLines.get(i).getDescriptor() + "');"
				);
				
				insertShiftStmt.close();
			}
			
			//Return the response
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		    response.getWriter().write("Line Added!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}