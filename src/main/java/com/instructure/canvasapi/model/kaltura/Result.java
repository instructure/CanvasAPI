package com.instructure.canvasapi.model.kaltura;

import org.simpleframework.xml.Element;

/**
 * Created by nbutton on 5/23/14.
 */
@Element
public class Result {

    /*
    <objectType>KalturaMediaEntry</objectType>
      <id>0_e3ropkdb</id>
      <name>zach test 3</name>
      <description />
      <partnerId>156652</partnerId>
      <userId>231890_10</userId>
      <tags />
      <adminTags />
      <categories />
      <status>1</status>
      <moderationStatus>6</moderationStatus>
      <moderationCount>0</moderationCount>
      <type>1</type>
      <createdAt>1279571324</createdAt>
      <rank>0</rank>
      <totalRank>0</totalRank>
      <votes>0</votes>
      <groupId />
      <partnerData />
      <downloadUrl>http://cdnbakmi.kaltura.com/p/156652/sp/15665200/raw/entry_id/0_e3ropkdb/version/0</downloadUrl>
      <searchText>zach test 3</searchText>
      <licenseType>-1</licenseType>
      <version>0</version>
      <thumbnailUrl>http://cdnbakmi.kaltura.com/p/156652/sp/15665200/thumbnail/entry_id/0_e3ropkdb/version/0</thumbnailUrl>
      <accessControlId>63362</accessControlId>
      <startDate />
      <endDate />
      <plays>0</plays>
      <views>0</views>
      <width />
      <height />
      <duration>0</duration>
      <msDuration>0</msDuration>
      <durationType />
      <mediaType>5</mediaType>
      <conversionQuality />
      <sourceType>1</sourceType>
      <searchProviderType />
      <searchProviderId />
      <creditUserName />
      <creditUrl />
      <mediaDate />
      <dataUrl>http://cdnbakmi.kaltura.com/p/156652/sp/15665200/flvclipper/entry_id/0_e3ropkdb/version/0</dataUrl>
      <flavorParamsIds />
     */

    @Element
    private String objectType;

    @Element(required = false)
    private String id;

    @Element(required = false)
    private long partnerId;

    @Element(required = false)
    private String userId;

    @Element(required = false)
    private String status;

    @Element(required = false)
    private String fileName;

    @Element(required = false)
    private String fileSize;

    @Element(required = false)
    private long uploadedFileSize;

    @Element(required = false)
    private long createdAt;

    @Element(required = false)
    private long updatedAt;


    @Element(required = false)
    private String name;

    @Element(required = false)
    private String description;

    @Element(required = false)
    private String tags;

    @Element(required = false)
    private String adminTags;

    @Element(required = false)
    private String categories;

    @Element(required = false)
    private String partnerData;

    @Element(required = false)
    private String downloadUrl;

    @Element(required = false)
    private long moderationStatus;

    @Element(required = false)
    private long moderationCount;

    @Element(required = false)
    private long type;

    @Element(required = false)
    private long totalRank;

    @Element(required = false)
    private long rank;

    @Element(required = false)
    private long votes;

    @Element(required = false)
    private long groupId;

    @Element(required = false)
    private String searchText;

    @Element(required = false)
    private long licenseType;

    @Element(required = false)
    private long version;

    @Element(required = false)
    private String thumbnailUrl;

    @Element(required = false)
    private long accessControlId;

    @Element(required = false)
    private long startDate;

    @Element(required = false)
    private long endDate;

    @Element(required = false)
    private long plays;

    @Element(required = false)
    private long views;

    @Element(required = false)
    private long width;

    @Element(required = false)
    private long height;

    @Element(required = false)
    private long duration;

    @Element(required = false)
    private long durationType;

    @Element(required = false)
    private long mediaType;

    @Element(required = false)
    private long conversionQuality;

    @Element(required = false)
    private long sourceType;

    @Element(required = false)
    private long searchProviderType;

    @Element(required = false)
    private long searchProviderId;

    @Element(required = false)
    private String creditUserName;

    @Element(required = false)
    private String creditUrl;

    @Element(required = false)
    private String mediaDate;

    @Element(required = false)
    private String dataUrl;

    @Element(required = false)
    private String flavorParamsIds;


    public Result() {
    }

    public Result(String objectType, String id, long partnerId, String userId, String status, String fileName, String fileSize, long uploadedFileSize, long createdAt, long updatedAt, float executionTime) {
        this.objectType = objectType;
        this.id = id;
        this.partnerId = partnerId;
        this.userId = userId;
        this.status = status;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadedFileSize = uploadedFileSize;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getUploadedFileSize() {
        return uploadedFileSize;
    }

    public void setUploadedFileSize(long uploadedFileSize) {
        this.uploadedFileSize = uploadedFileSize;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAdminTags() {
        return adminTags;
    }

    public void setAdminTags(String adminTags) {
        this.adminTags = adminTags;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getPartnerData() {
        return partnerData;
    }

    public void setPartnerData(String partnerData) {
        this.partnerData = partnerData;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(long moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public long getModerationCount() {
        return moderationCount;
    }

    public void setModerationCount(long moderationCount) {
        this.moderationCount = moderationCount;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getTotalRank() {
        return totalRank;
    }

    public void setTotalRank(long totalRank) {
        this.totalRank = totalRank;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public long getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(long licenseType) {
        this.licenseType = licenseType;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getAccessControlId() {
        return accessControlId;
    }

    public void setAccessControlId(long accessControlId) {
        this.accessControlId = accessControlId;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Result{" +
                "objectType='" + objectType + '\'' +
                ", id='" + id + '\'' +
                ", partnerId=" + partnerId +
                ", userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", uploadedFileSize=" + uploadedFileSize +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", adminTags='" + adminTags + '\'' +
                ", categories='" + categories + '\'' +
                ", partnerData='" + partnerData + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", moderationStatus=" + moderationStatus +
                ", moderationCount=" + moderationCount +
                ", type=" + type +
                ", totalRank=" + totalRank +
                ", rank=" + rank +
                ", votes=" + votes +
                ", groupId=" + groupId +
                ", searchText='" + searchText + '\'' +
                ", licenseType=" + licenseType +
                ", version=" + version +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", accessControlId=" + accessControlId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", plays=" + plays +
                ", views=" + views +
                ", width=" + width +
                ", height=" + height +
                ", duration=" + duration +
                ", durationType=" + durationType +
                ", mediaType=" + mediaType +
                ", conversionQuality=" + conversionQuality +
                ", sourceType=" + sourceType +
                ", searchProviderType=" + searchProviderType +
                ", searchProviderId=" + searchProviderId +
                ", creditUserName='" + creditUserName + '\'' +
                ", creditUrl='" + creditUrl + '\'' +
                ", mediaDate='" + mediaDate + '\'' +
                ", dataUrl='" + dataUrl + '\'' +
                ", flavorParamsIds='" + flavorParamsIds + '\'' +
                '}';
    }
}

