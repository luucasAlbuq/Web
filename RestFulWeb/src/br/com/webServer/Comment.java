package br.com.webServer;

public class Comment {

	private Integer sequence;
	private String msg;

	public Comment(String msg, Integer sequence) {
		this.setMsg(msg);
		this.sequence = sequence;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Override
	public String toString() {
		return "Comment N: " + sequence + "\n Message : " + this.msg;
	}
}
