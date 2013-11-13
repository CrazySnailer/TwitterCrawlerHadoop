package isiteam.TwitterCrawler.database.bean;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TweetInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tweet_info", catalog = "twittercrawler", uniqueConstraints = @UniqueConstraint(columnNames = "tweetId"))
public class TweetInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String tweetId;
	private String userId;
	private String screenName;
	private Timestamp createdAt;
	private String text;
	private Integer retweetCount;
	private String source;
	private Integer isFavorited;
	private String geoLocation;
	private String place;
	private String inReplyToStatusId;
	private String inReplyToUserId;
	private String inReplyToScreenName;
	private String inRetweetToStatusId;
	private String inRetweetToUserId;
	private String inRetweetToScreenName;
	private Timestamp inRetweetCreatedAt;
	private Integer inRetweetCount;
	private String allText;
	private Timestamp insertTime;

	// Constructors

	/** default constructor */
	public TweetInfo() {
	}

	/** minimal constructor */
	public TweetInfo(String tweetId) {
		this.tweetId = tweetId;
	}

	/** full constructor */
	public TweetInfo(String tweetId, String userId, String screenName,
			Timestamp createdAt, String text, Integer retweetCount,
			String source, Integer isFavorited, String geoLocation,
			String place, String inReplyToStatusId, String inReplyToUserId,
			String inReplyToScreenName, String inRetweetToStatusId,
			String inRetweetToUserId, String inRetweetToScreenName,
			Timestamp inRetweetCreatedAt, Integer inRetweetCount,
			String allText, Timestamp insertTime) {
		this.tweetId = tweetId;
		this.userId = userId;
		this.screenName = screenName;
		this.createdAt = createdAt;
		this.text = text;
		this.retweetCount = retweetCount;
		this.source = source;
		this.isFavorited = isFavorited;
		this.geoLocation = geoLocation;
		this.place = place;
		this.inReplyToStatusId = inReplyToStatusId;
		this.inReplyToUserId = inReplyToUserId;
		this.inReplyToScreenName = inReplyToScreenName;
		this.inRetweetToStatusId = inRetweetToStatusId;
		this.inRetweetToUserId = inRetweetToUserId;
		this.inRetweetToScreenName = inRetweetToScreenName;
		this.inRetweetCreatedAt = inRetweetCreatedAt;
		this.inRetweetCount = inRetweetCount;
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

	@Column(name = "tweetId", unique = true, nullable = false, length = 20)
	public String getTweetId() {
		return this.tweetId;
	}

	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
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

	@Column(name = "createdAt", length = 19)
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "text", length = 1000)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "retweetCount")
	public Integer getRetweetCount() {
		return this.retweetCount;
	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	@Column(name = "source", length = 500)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "isFavorited")
	public Integer getIsFavorited() {
		return this.isFavorited;
	}

	public void setIsFavorited(Integer isFavorited) {
		this.isFavorited = isFavorited;
	}

	@Column(name = "geoLocation", length = 500)
	public String getGeoLocation() {
		return this.geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	@Column(name = "place", length = 500)
	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name = "inReplyToStatusId", length = 20)
	public String getInReplyToStatusId() {
		return this.inReplyToStatusId;
	}

	public void setInReplyToStatusId(String inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	@Column(name = "inReplyToUserId", length = 20)
	public String getInReplyToUserId() {
		return this.inReplyToUserId;
	}

	public void setInReplyToUserId(String inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	@Column(name = "inReplyToScreenName", length = 500)
	public String getInReplyToScreenName() {
		return this.inReplyToScreenName;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	@Column(name = "inRetweetToStatusId", length = 20)
	public String getInRetweetToStatusId() {
		return this.inRetweetToStatusId;
	}

	public void setInRetweetToStatusId(String inRetweetToStatusId) {
		this.inRetweetToStatusId = inRetweetToStatusId;
	}

	@Column(name = "inRetweetToUserId", length = 20)
	public String getInRetweetToUserId() {
		return this.inRetweetToUserId;
	}

	public void setInRetweetToUserId(String inRetweetToUserId) {
		this.inRetweetToUserId = inRetweetToUserId;
	}

	@Column(name = "inRetweetToScreenName", length = 500)
	public String getInRetweetToScreenName() {
		return this.inRetweetToScreenName;
	}

	public void setInRetweetToScreenName(String inRetweetToScreenName) {
		this.inRetweetToScreenName = inRetweetToScreenName;
	}

	@Column(name = "inRetweetCreatedAt", length = 19)
	public Timestamp getInRetweetCreatedAt() {
		return this.inRetweetCreatedAt;
	}

	public void setInRetweetCreatedAt(Timestamp inRetweetCreatedAt) {
		this.inRetweetCreatedAt = inRetweetCreatedAt;
	}

	@Column(name = "inRetweetCount")
	public Integer getInRetweetCount() {
		return this.inRetweetCount;
	}

	public void setInRetweetCount(Integer inRetweetCount) {
		this.inRetweetCount = inRetweetCount;
	}

	@Column(name = "allText", length = 10000)
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