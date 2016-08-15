package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.AccountNotification;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.RestAdapter;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Path;
/**
* Copyright (c) 2015 Instructure. All rights reserved.
*/
public class AccountNotificationAPI extends BuildInterfaceAPI {

    public interface AccountNotificationInterface {

        @GET("/accounts/self/users/self/account_notifications")
        void getAccountNotifications(CanvasCallback<AccountNotification[]> callback);

        @DELETE("/accounts/self/users/self/account_notifications/{account_notification_id}")
        void deleteAccountNotification(@Path("account_notification_id") long account_notification_id, CanvasCallback<AccountNotification> callback);

    }

    public static void getAccountNotifications(final CanvasCallback<AccountNotification[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildCacheInterface(AccountNotificationInterface.class, callback, null).getAccountNotifications(callback);
        buildInterface(AccountNotificationInterface.class, callback, null).getAccountNotifications(callback);
    }

    public static void getAccountNotificationsChained(final CanvasCallback<AccountNotification[]> callback, boolean isCached) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        if (isCached) {
            buildCacheInterface(AccountNotificationInterface.class, callback, null).getAccountNotifications(callback);
        } else {
            buildInterface(AccountNotificationInterface.class, callback, null).getAccountNotifications(callback);
        }
    }

    public static void deleteAccountNotification(long accountNotificationId, CanvasCallback<AccountNotification> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(AccountNotificationInterface.class, callback, null).deleteAccountNotification(accountNotificationId, callback);
    }
}
