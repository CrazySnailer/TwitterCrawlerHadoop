package isiteam.TwitterCrawler.database.bean;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * SeedsQueue entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "seeds_queue", catalog = "twittercrawler", uniqueConstraints = @UniqueConstraint(columnNames = "userId"))
public class SeedsQueue implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userId;
	private Integer isUserInfo;
	private Boolean isUserStatus;
	private Integer isFriendsInfo;
	private Boolean isFriendsStatus;
	private Integer isTweetsInfo;
	private Boolean isTweetsStatus;
	private Integer level;
	private Integer isDeal;
	private Timestamp insertTime;

	// Constructors

	/** default constructor */
	public SeedsQueue() {
	}

	/** full constructor */
	public SeedsQueue(String userId, Integer isUserInfo, Boolean isUserStatus,
			Integer isFriendsInfo, Boolean isFriendsStatus,
			Integer isTweetsInfo, Boolean isTweetsStatus, Integer level,
			Integer isDeal, Timestamp insertTime) {
		this.userId = userId;
		this.isUserInfo = isUserInfo;
		this.isUserStatus = isUserStatus;
		this.isFriendsInfo = isFriendsInfo;
		this.isFriendsStatus = isFriendsStatus;
		this.isTweetsInfo = isTweetsInfo;
		this.isTweetsStatus = isTweetsStatus;
		this.level = level;
		this.isDeal = isDeal;
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

	@Column(name = "userId", unique = true, length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "isUserInfo")
	public Integer getIsUserInfo() {
		return this.isUserInfo;
	}

	public void setIsUserInfo(Integer isUserInfo) {
		this.isUserInfo = isUserInfo;
	}

	@Column(name = "isUserStatus")
	public Boolean getIsUserStatus() {
		return this.isUserStatus;
	}

	public void setIsUserStatus(Boolean isUserStatus) {
		this.isUserStatus = isUserStatus;
	}

	@Column(name = "isFriendsInfo")
	public Integer getIsFriendsInfo() {
		return this.isFriendsInfo;
	}

	public void setIsFriendsInfo(Integer isFriendsInfo) {
		this.isFriendsInfo = isFriendsInfo;
	}

	@Column(name = "isFriendsStatus")
	public Boolean getIsFriendsStatus() {
		return this.isFriendsStatus;
	}

	public void setIsFriendsStatus(Boolean isFriendsStatus) {
		this.isFriendsStatus = isFriendsStatus;
	}

	@Column(name = "isTweetsInfo")
	public Integer getIsTweetsInfo() {
		return this.isTweetsInfo;
	}

	public void setIsTweetsInfo(Integer isTweetsInfo) {
		this.isTweetsInfo = isTweetsInfo;
	}

	@Column(name = "isTweetsStatus")
	public Boolean getIsTweetsStatus() {
		return this.isTweetsStatus;
	}

	public void setIsTweetsStatus(Boolean isTweetsStatus) {
		this.isTweetsStatus = isTweetsStatus;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "isDeal")
	public Integer getIsDeal() {
		return this.isDeal;
	}

	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}

	@Column(name = "insertTime", length = 19)
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

}