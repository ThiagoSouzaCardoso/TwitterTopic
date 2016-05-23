package br.com.fiap.app;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import twitter4j.Status;
import twitter4j.User;

public class TwitterVo {

	private LocalDate createdAt;
	private int favoriteCount;
	private int retweetCount;
	private User user;
	private Long id;

	public TwitterVo(Status status) {
		createdAt = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(status.getCreatedAt()));
		favoriteCount = status.getFavoriteCount();
		retweetCount = status.getRetweetCount();
		user = status.getUser();
		id = status.getId();
	}
	
	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public int getRetweetCount() {
		return retweetCount;
	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwitterVo other = (TwitterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}