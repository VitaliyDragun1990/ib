package com.revenat.iblog.infrastructure.service.impl;

import java.util.Properties;

import com.revenat.iblog.infrastructure.service.AvatarService;
import com.revenat.iblog.infrastructure.service.NotificationService;
import com.revenat.iblog.infrastructure.service.SocialService;

public final class ServiceFactory {

	public static AvatarService createAvatarService(String appRootDirectoryPath) {
		return new FileStorageAvatarService(appRootDirectoryPath);
	}
	
	public static SocialService createSocialService(String clientId) {
		return new GooglePlusSocialService(clientId);
	}
	
	public static NotificationService createNotificationService(Properties notificationProps) {
		return new /*AsyncEmailNotificationService(notificationProps)*/DummyNotificationService();
	}
	
	private ServiceFactory() {}
}
