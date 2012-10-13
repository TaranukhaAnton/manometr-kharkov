package application;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

public class SessionListen implements HttpSessionListener{


//	public static void main(String[] args) {
//		
//		PostService postService = new PostService();
//		Post post = new Post();
//		post.setTitle("title");
//		post.setContent("content");
//		postService.insertPost(post);
//		
//		
//
//	}

	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		Date date = new Date();
		System.out.println("Session created + "+date+" id "+session.getId());
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		Date date = new Date();
		System.out.println("Session delete + "+date+" id "+session.getId());
		
	}

}
