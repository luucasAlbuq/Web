package br.com.webServer;

import java.util.ArrayList;
import java.util.List;

public class Post {

	private Integer id;
	private String msg;
	private List<Comment> comments;

	public Post(String msg) {
		this.comments = new ArrayList<Comment>();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void addComment(String msg) {
		this.comments.add(new Comment(msg, this.comments.size() + 1));
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Post)) {
			return false;
		}
		Post otherPost = (Post) obj;
		return otherPost.getMsg().equals(this.msg);
	}

	public Comment getComment(String sequence) {
		for (Comment c : this.comments) {
			if (c.getSequence().toString().equals(sequence)) {
				return c;
			}
		}
		return null;
	}

	public void removeComment(String sequence) {
		Comment c = getComment(sequence);
		comments.remove(c);
	}

	@Override
	public String toString() {
		return "Post N: " + getId() + "\n Message: " + this.msg;
	}
}
