package isiteam.TwitterCrawler.database.bean;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_info", catalog = "twittercrawler", uniqueConstraints = @UniqueConstraint(columnNames = {
		"userId", "crawledNum" }))
public class UserInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userId;
	private String screenName;
	private String name;
	private String location;
	private String timeZone;
	private String lang;
	private String description;
	private Timestamp createdAt;
	private String profileImageUrl;
	private String webExtendurl;
	private Integer isProtected;
	private Integer followersCount;
	private Integer friendsCount;
	private Integer statusesCount;
	private Integer favouritesCount;
	private Integer isVerified;
	private Integer isGeoEnabled;
	private Timestamp currentStatuscreatedAt;
	private String currentStatusId;
	private Integer crawledNum;
	private String allText;
	private Timestamp insertTime;

	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** full constructor */
	public UserInfo(String userId, String screenName, String name,
			String location, String timeZone, String lang, String description,
			Timestamp createdAt, String profileImageUrl, String webExtendurl,
			Integer isProtected, Integer followersCount, Integer friendsCount,
			Integer statusesCount, Integer favouritesCount, Integer isVerified,
			Integer isGeoEnabled, Timestamp currentStatuscreatedAt,
			String currentStatusId, Integer crawledNum, String allText,
			Timestamp insertTime) {
		this.userId = userId;
		this.screenName = screenName;
		this.name = name;
		this.location = location;
		this.timeZone = timeZone;
		this.lang = lang;
		this.description = description;
		this.createdAt = createdAt;
		this.profileImageUrl = profileImageUrl;
		this.webExtendurl = webExtendurl;
		this.isProtected = isProtected;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.statusesCount = statusesCount;
		this.favouritesCount = favouritesCount;
		this.isVerified = isVerified;
		this.isGeoEnabled = isGeoEnabled;
		this.currentStatuscreatedAt = currentStatuscreatedAt;
		this.currentStatusId = currentStatusId;
		this.crawledNum = crawledNum;
		this.allText = allText;
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

	@Column(name = "screenName", length = 500)
	public String getScreenName() {
		return this.screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	@Column(name = "name", length = 500)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "location", length = 500)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "timeZone", length = 500)
	public String getTimeZone() {
		return this.timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	@Column(name = "lang", length = 100)
	public String getLang() {
		return this.lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Column(name = "description", length = 5000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "createdAt", length = 19)
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "profileImageUrl", length = 5000)
	public String getProfileImageUrl() {
		return this.profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	@Column(name = "webExtendurl", length = 1000)
	public String getWebExtendurl() {
		return this.webExtendurl;
	}

	public void setWebExtendurl(String webExtendurl) {
		this.webExtendurl = webExtendurl;
	}

	@Column(name = "isProtected")
	public Integer getIsProtected() {
		return this.isProtected;
	}

	public void setIsProtected(Integer isProtected) {
		this.isProtected = isProtected;
	}

	@Column(name = "followersCount")
	public Integer getFollowersCount() {
		return this.followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	@Column(name = "friendsCount")
	public Integer getFriendsCount() {
		return this.friendsCount;
	}

	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	@Column(name = "statusesCount")
	public Integer getStatusesCount() {
		return this.statusesCount;
	}

	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}

	@Column(name = "favouritesCount")
	public Integer getFavouritesCount() {
		return this.favouritesCount;
	}

	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	@Column(name = "isVerified")
	public Integer getIsVerified() {
		return this.isVerified;
	}

	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}

	@Column(name = "isGeoEnabled")
	public Integer getIsGeoEnabled() {
		return this.isGeoEnabled;
	}

	public void setIsGeoEnabled(Integer isGeoEnabled) {
		this.isGeoEnabled = isGeoEnabled;
	}

	@Column(name = "currentStatuscreatedAt", length = 19)
	public Timestamp getCurrentStatuscreatedAt() {
		return this.currentStatuscreatedAt;
	}

	public void setCurrentStatuscreatedAt(Timestamp currentStatuscreatedAt) {
		this.currentStatuscreatedAt = currentStatuscreatedAt;
	}

	@Column(name = "currentStatusId", length = 20)
	public String getCurrentStatusId() {
		return this.currentStatusId;
	}

	public void setCurrentStatusId(String currentStatusId) {
		this.currentStatusId = currentStatusId;
	}

	@Column(name = "crawledNum")
	public Integer getCrawledNum() {
		return this.crawledNum;
	}

	public void setCrawledNum(Integer crawledNum) {
		this.crawledNum = crawledNum;
	}

	@Column(name = "allText", length = 5000)
	public String getAllText() {
		return this.allText;
	}

	public void setAllText(String allText) {
		this.allText = allText;
	}

	@Column(name = "insertTime", length = 19)
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

}