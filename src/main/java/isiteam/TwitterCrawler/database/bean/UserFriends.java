package isiteam.TwitterCrawler.database.bean;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * UserFriends entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_friends", catalog = "twittercrawler", uniqueConstraints = @UniqueConstraint(columnNames = {
		"userId", "friendsId", "crawledNum" }))
public class UserFriends implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userId;
	private String friendsId;
	private Integer crawledNum;
	private Timestamp insertTime;

	// Constructors

	/** default constructor */
	public UserFriends() {
	}

	/** full constructor */
	public UserFriends(String userId, String friendsId, Integer crawledNum,
			Timestamp insertTime) {
		this.userId = userId;
		this.friendsId = friendsId;
		this.crawledNum = crawledNum;
		this.insertTime = insertTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "userId", length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "friendsId", length = 20)
	public String getFriendsId() {
		return this.friendsId;
	}

	public void setFriendsId(String friendsId) {
		this.friendsId = friendsId;
	}

	@Column(name = "crawledNum")
	public Integer getCrawledNum() {
		return this.crawledNum;
	}

	public void setCrawledNum(Integer crawledNum) {
		this.crawledNum = crawledNum;
	}

	@Column(name = "insertTime", length = 19)
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

}