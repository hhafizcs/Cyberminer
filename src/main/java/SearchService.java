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

import KWIC.Line;

@SuppressWarnings("serial")
@WebServlet(
    name = "SearchService",
    urlPatterns = {"/search"}
)
public class SearchService extends HttpServlet {
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
			
			String searchTerm = requestObj.getString("searchTxt");
			//String searchType = requestObj.getString("type");
			String sortType = requestObj.getString("sort");
			int page = requestObj.getInt("page");
			int pageSize = requestObj.getInt("pageSize");
			int offset = (page - 1) * pageSize;
			
			//Connect to the database
			
			//Connection dbCnx = DriverManager.getConnection("jdbc:mysql://google/cyberminer?cloudSqlInstance=cyberminer-shae:us-central1:cyberminer&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=cyberminer&useSSL=false");
			Connection dbCnx = DriverManager.getConnection("jdbc:mysql://35.188.65.89/cyberminer","root","cyberminer");
			
			//Get the number of results
			
			Statement numOfResultsStmt = dbCnx.createStatement();

			ResultSet numOfResultsResSet = numOfResultsStmt.executeQuery(
					"SELECT COUNT(DISTINCT line_id) AS numOfResults " + 
					"FROM shift " + 
					"WHERE shifted_descriptor LIKE '%" + searchTerm + "%';"
			);
			
			numOfResultsResSet.next();
			
			int numOfResults = numOfResultsResSet.getInt("numOfResults");
			
			numOfResultsStmt.close();
			
			//Get the lines
			
			ArrayList<Line> lines = new ArrayList<Line>();
			
			Statement linesStmt = dbCnx.createStatement();
			
			String linesQuery = 
					"SELECT DISTINCT line_id, id, url, descriptor, access_frequency, payment " + 
					"FROM line, shift " + 
					"WHERE shifted_descriptor LIKE '%" + searchTerm + "%' AND line_id = id ";
			
			if(sortType.equals("alpha")) {
				linesQuery += "ORDER BY descriptor ASC ";
			} else if(sortType.equals("access")) {
				linesQuery += "ORDER BY access_frequency DESC ";
			} else if(sortType.equals("payment")) {
				linesQuery += "ORDER BY payment DESC ";
			}
			
			if(page != 0 && pageSize != 0) {
				linesQuery += "LIMIT " + offset + ", " + pageSize + ";";
			} else {
				linesQuery += ";";
			}

			ResultSet linesResSet = linesStmt.executeQuery(linesQuery);
			
			while (linesResSet.next())
			{
				int id = linesResSet.getInt("id");
				String descriptor = linesResSet.getString("descriptor");
				String url = linesResSet.getString("url");
				Line line = new Line(id, descriptor, url);
				lines.add(line);
			}
			
			linesStmt.close();
			
			//Create the json response object
			
			String linesString = "";
			for(int i = 0; i < lines.size(); i++) {
				linesString += "{ \"id\": " + lines.get(i).getId() + ", \"descriptor\": \"" + lines.get(i).getDescriptor() + "\", \"url\": \"" + lines.get(i).getUrl() + "\" }";
				
				if(i < lines.size() - 1) {
					linesString += ",";
				}
			}
			linesString = "[" + linesString + "]";
			
			String result =  "{ \"lines\": " + linesString + ", \"numOfResults\": " + numOfResults + " }";
			
			//Return the response
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}