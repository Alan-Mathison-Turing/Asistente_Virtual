/** 
 * This code is copyright (c) Mathias Markl 2016
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package edu.unlam.asistente.giphy.dao;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import edu.unlam.asistente.giphy.Giphy4JConstants;
import edu.unlam.asistente.giphy.http.Request;
import edu.unlam.asistente.giphy.http.Response;

/**
 * This class provides methods to send request to the web.
 * 
 * @author Mathias Markl
 */
public class HttpRequestSender implements RequestSender {

    @Override
    public Response sendRequest(Request request) throws IOException {

	HttpURLConnection connection = createConnection(request);
	Response response = send(connection);

	return response;
    }

    private HttpURLConnection createConnection(Request request) throws IOException {
	URL url = new URL(request.getUrl());
	HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	connection.setConnectTimeout(Giphy4JConstants.TIMEOUT);
	connection.setReadTimeout(Giphy4JConstants.TIMEOUT);

	connection.setRequestMethod(Giphy4JConstants.SEND_METHOD);

	return connection;
    }

    private Response send(HttpURLConnection connection) throws IOException {
	connection.connect();

	if (connection.getResponseCode() != 200) {
	    throw new IOException("Bad response! Code: " + connection.getResponseCode());
	}

	Map<String, String> headers = new HashMap<String, String>();
	for (String key : connection.getHeaderFields().keySet()) {
	    headers.put(key, connection.getHeaderFields().get(key).get(0));
	}

	String body = null;
	InputStream inputStream = null;

	try {
	    inputStream = connection.getInputStream();

	    String encoding = connection.getContentEncoding();
	    encoding = encoding == null ? Giphy4JConstants.ENCODING : encoding;

	    body = IOUtils.toString(inputStream, encoding);
	} catch (IOException e) {
	    throw new IOException(e);
	} finally {
	    if (inputStream != null) {
		inputStream.close();
	    }
	}

	if (body == null) {
	    throw new IOException("Unparseable response body! \n {" + body + "}");
	}

	Response response = new Response(headers, body);

	return response;
    }

}
