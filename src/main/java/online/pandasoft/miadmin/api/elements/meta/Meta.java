/*
 * Copyright 2020 PandaSoft.Dev Social Networks.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package online.pandasoft.miadmin.api.elements.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Meta {
    @JsonProperty("maintainerName")
    private String maintainerName;
    @JsonProperty("maintainerEmail")
    private String maintainerEmail;
    @JsonProperty("version")
    private String version;
    @JsonProperty("name")
    private String name;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("description")
    private String description;
    @JsonProperty("langs")
    private List<Object> langs; // TODO: 2020/04/10 形式不明
    @JsonProperty("tosUrl")
    private String tosURL;
    @JsonProperty("repositoryUrl")
    private String repositoryURL;
    @JsonProperty("feedbackUrl")
    private String feedbackURL;
    @JsonProperty("secure")
    private boolean secure;
    @JsonProperty("disableRegistration")
    private boolean disableRegistration;
    @JsonProperty("disableLocalTimeline")
    private boolean disableLocalTimeline;
    @JsonProperty("disableGlobalTimeline")
    private boolean disableGlobalTimeline;
    @JsonProperty("driveCapacityPerLocalUserMb")
    private long driveCapacityPerLocalUserMB;
    @JsonProperty("driveCapacityPerRemoteUserMb")
    private long driveCapacityPerRemoteUserMB;
    @JsonProperty("cacheRemoteFiles")
    private boolean cacheRemoteFiles;
    @JsonProperty("proxyRemoteFiles")
    private boolean proxyRemoteFiles;
    @JsonProperty("enableRecaptcha")
    private boolean enableRecaptcha;
    @JsonProperty("recaptchaSiteKey")
    private String recaptchaSiteKey;
    @JsonProperty("swPublickey")
    private String swPublickey;
    @JsonProperty("mascotImageUrl")
    private String mascotImageURL;
    @JsonProperty("bannerUrl")
    private Object bannerURL;
    @JsonProperty("errorImageUrl")
    private String errorImageURL;
    @JsonProperty("iconUrl")
    private Object iconURL;
    @JsonProperty("maxNoteTextLength")
    private long maxNoteTextLength;
    @JsonProperty("emojis")
    private List<EmojiMeta> emojis;
    @JsonProperty("requireSetup")
    private boolean requireSetup;
    @JsonProperty("enableEmail")
    private boolean enableEmail;
    @JsonProperty("enableTwitterIntegration")
    private boolean enableTwitterIntegration;
    @JsonProperty("enableGithubIntegration")
    private boolean enableGithubIntegration;
    @JsonProperty("enableDiscordIntegration")
    private boolean enableDiscordIntegration;
    @JsonProperty("enableServiceWorker")
    private boolean enableServiceWorker;
    @JsonProperty("features")
    private Features features;
    @JsonProperty("useStarForReactionFallback")
    private boolean useStarForReactionFallback;
    @JsonProperty("pinnedUsers")
    private List<String> pinnedUsers;
    @JsonProperty("hiddenTags")
    private List<String> hiddenTags;
    @JsonProperty("blockedHosts")
    private List<String> blockedHosts;
    @JsonProperty("recaptchaSecretKey")
    private String recaptchaSecretKey;
    @JsonProperty("proxyAccountId")
    private String proxyAccountID;
    @JsonProperty("twitterConsumerKey")
    private String twitterConsumerKey;
    @JsonProperty("twitterConsumerSecret")
    private String twitterConsumerSecret;
    @JsonProperty("githubClientId")
    private String githubClientID;
    @JsonProperty("githubClientSecret")
    private String githubClientSecret;
    @JsonProperty("discordClientId")
    private String discordClientID;
    @JsonProperty("discordClientSecret")
    private String discordClientSecret;
    @JsonProperty("summalyProxy")
    private Object summalyProxy;
    @JsonProperty("email")
    private String email;
    @JsonProperty("smtpSecure")
    private boolean smtpSecure;
    @JsonProperty("smtpHost")
    private String smtpHost;
    @JsonProperty("smtpPort")
    private long smtpPort;
    @JsonProperty("smtpUser")
    private String smtpUser;
    @JsonProperty("smtpPass")
    private String smtpPass;
    @JsonProperty("swPrivateKey")
    private String swPrivateKey;
    @JsonProperty("useObjectStorage")
    private boolean useObjectStorage;
    @JsonProperty("objectStorageBaseUrl")
    private String objectStorageBaseURL;
    @JsonProperty("objectStorageBucket")
    private String objectStorageBucket;
    @JsonProperty("objectStoragePrefix")
    private String objectStoragePrefix;
    @JsonProperty("objectStorageEndpoint")
    private String objectStorageEndpoint;
    @JsonProperty("objectStorageRegion")
    private String objectStorageRegion;
    @JsonProperty("objectStoragePort")
    private long objectStoragePort;
    @JsonProperty("objectStorageAccessKey")
    private String objectStorageAccessKey;
    @JsonProperty("objectStorageSecretKey")
    private String objectStorageSecretKey;
    @JsonProperty("objectStorageUseSSL")
    private boolean objectStorageUseSSL;

    public String getMaintainerName() {
        return maintainerName;
    }

    public String getMaintainerEmail() {
        return maintainerEmail;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getURI() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    public List<Object> getLangs() {
        return langs;
    }

    public String getTosURL() {
        return tosURL;
    }

    public String getRepositoryURL() {
        return repositoryURL;
    }

    public String getFeedbackURL() {
        return feedbackURL;
    }

    public boolean getSecure() {
        return secure;
    }

    public boolean getDisableRegistration() {
        return disableRegistration;
    }

    public boolean getDisableLocalTimeline() {
        return disableLocalTimeline;
    }

    public boolean getDisableGlobalTimeline() {
        return disableGlobalTimeline;
    }

    public long getDriveCapacityPerLocalUserMB() {
        return driveCapacityPerLocalUserMB;
    }

    public long getDriveCapacityPerRemoteUserMB() {
        return driveCapacityPerRemoteUserMB;
    }

    public boolean getCacheRemoteFiles() {
        return cacheRemoteFiles;
    }

    public boolean getProxyRemoteFiles() {
        return proxyRemoteFiles;
    }

    public boolean getEnableRecaptcha() {
        return enableRecaptcha;
    }

    public String getRecaptchaSiteKey() {
        return recaptchaSiteKey;
    }

    public String getSwPublickey() {
        return swPublickey;
    }

    public String getMascotImageURL() {
        return mascotImageURL;
    }

    public Object getBannerURL() {
        return bannerURL;
    }

    public String getErrorImageURL() {
        return errorImageURL;
    }

    public Object getIconURL() {
        return iconURL;
    }

    public long getMaxNoteTextLength() {
        return maxNoteTextLength;
    }

    public List<EmojiMeta> getEmojis() {
        return emojis;
    }

    public boolean getRequireSetup() {
        return requireSetup;
    }

    public boolean getEnableEmail() {
        return enableEmail;
    }

    public boolean getEnableTwitterIntegration() {
        return enableTwitterIntegration;
    }

    public boolean getEnableGithubIntegration() {
        return enableGithubIntegration;
    }

    public boolean getEnableDiscordIntegration() {
        return enableDiscordIntegration;
    }

    public boolean getEnableServiceWorker() {
        return enableServiceWorker;
    }

    public Features getFeatures() {
        return features;
    }

    public boolean getUseStarForReactionFallback() {
        return useStarForReactionFallback;
    }

    public List<String> getPinnedUsers() {
        return pinnedUsers;
    }

    public List<String> getHiddenTags() {
        return hiddenTags;
    }

    public List<String> getBlockedHosts() {
        return blockedHosts;
    }

    public String getRecaptchaSecretKey() {
        return recaptchaSecretKey;
    }

    public String getProxyAccountID() {
        return proxyAccountID;
    }

    public String getTwitterConsumerKey() {
        return twitterConsumerKey;
    }

    public String getTwitterConsumerSecret() {
        return twitterConsumerSecret;
    }

    public String getGithubClientID() {
        return githubClientID;
    }

    public String getGithubClientSecret() {
        return githubClientSecret;
    }

    public String getDiscordClientID() {
        return discordClientID;
    }

    public String getDiscordClientSecret() {
        return discordClientSecret;
    }

    public Object getSummalyProxy() {
        return summalyProxy;
    }

    public String getEmail() {
        return email;
    }

    public boolean getSMTPSecure() {
        return smtpSecure;
    }

    public String getSMTPHost() {
        return smtpHost;
    }

    public long getSMTPPort() {
        return smtpPort;
    }

    public String getSMTPUser() {
        return smtpUser;
    }

    public String getSMTPPass() {
        return smtpPass;
    }

    public String getSwPrivateKey() {
        return swPrivateKey;
    }

    public boolean getUseObjectStorage() {
        return useObjectStorage;
    }

    public String getObjectStorageBaseURL() {
        return objectStorageBaseURL;
    }

    public String getObjectStorageBucket() {
        return objectStorageBucket;
    }

    public Object getObjectStoragePrefix() {
        return objectStoragePrefix;
    }

    public String getObjectStorageEndpoint() {
        return objectStorageEndpoint;
    }

    public Object getObjectStorageRegion() {
        return objectStorageRegion;
    }

    public long getObjectStoragePort() {
        return objectStoragePort;
    }

    public String getObjectStorageAccessKey() {
        return objectStorageAccessKey;
    }

    public String getObjectStorageSecretKey() {
        return objectStorageSecretKey;
    }

    public boolean getObjectStorageUseSSL() {
        return objectStorageUseSSL;
    }
}
