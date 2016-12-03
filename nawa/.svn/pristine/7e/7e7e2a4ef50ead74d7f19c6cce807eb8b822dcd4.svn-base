package org.nawa.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.projectodd.sockjs.SockJsConnection;
import org.projectodd.sockjs.SockJsConnection.OnCloseHandler;
import org.projectodd.sockjs.SockJsConnection.OnDataHandler;
import org.projectodd.sockjs.SockJsServer;
import org.projectodd.sockjs.SockJsServer.OnConnectionHandler;
import org.projectodd.sockjs.servlet.SockJsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.uri.UriTemplate;

public class Noti extends SockJsServlet {
	private static final Logger logger = LoggerFactory.getLogger(Noti.class);
	private static Map<String, SockJsConnection> sessionMap = new HashMap<String, SockJsConnection>();

	@Override
	public void init() throws ServletException {
		SockJsServer server = new SockJsServer();
		server.options.responseLimit = 4 * 1024;
		server.onConnection(new NotiOnConnectionHandler());
		setServer(server);
		super.init();
	} //init

	class NotiOnConnectionHandler implements OnConnectionHandler {
		private UriTemplate registerEmailTemplte = new UriTemplate("registerEmail;{email}");
		private String email;
		
		@Override
		public void handle(final SockJsConnection conn) {
			logger.info("connected, id: {}, pathname: {}, prefix: {}, "
					+ "protocol: {}, remoteAddr: {}, remotePort: {}, "
					+ "url: {}",
					conn.id, conn.pathname, conn.prefix,
					conn.protocol, conn.remoteAddress, conn.remotePort,
					conn.url);

			conn.onData(new OnDataHandler() {
				@Override
				public void handle(String msg) {
					logger.info("msg : {}", msg);
					
					Map<String, String> params = new HashMap<String, String>();
					if(registerEmailTemplte.match(msg, params)){
						registerEmail(params, conn);
					} else{
						logger.error("invalid request msg on sockjs : {}", msg);
					} //if
					
				} //handle
			});

			conn.onClose(new OnCloseHandler() {
				@Override
				public void handle() {
					logger.info("session({}) on sockjs closed", email);
				} //handle
			});
		} //handle
		
		private void registerEmail(Map<String, String> params, SockJsConnection conn){
			this.email = params.get("email");
			logger.info("registerEmail: {}", this.email);
			if(sessionMap.get(email) != null)
				sessionMap.get(email).close();
			sessionMap.put(email, conn);
		} //registerEmail
	} //class
} //class