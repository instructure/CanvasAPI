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

public class AccountNotificationAPI {

    public interface AccountNotificationInterface {

        @GET("/accounts/{account_id}/users/{user_id}/account_notifications")
        void getAccountNotifications(@Path("account_id") long account_id, @Path("user_id") long user_id, CanvasCallback<AccountNotification[]> callback);

        @DELETE("/accounts/{account_id}/users/{user_id}/account_notifications/{account_notification_id}")
        void deleteAccountNotification(@Path("account_id") long account_id, @Path("user_id") long user_id, @Path("account_notification_id") long account_notification_id, CanvasCallback<AccountNotification> callback);

    }


    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static AccountNotificationInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(AccountNotificationInterface.class);
    }

    public static void getAccountNotifications(long accountId, long userId, final CanvasCallback<AccountNotification[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).getAccountNotifications(accountId, userId, callback);
    }

    public static void deleteAccountNotification(long accountId, long userId, long accountNotificationId, CanvasCallback<AccountNotification> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).deleteAccountNotification(accountId, userId, accountNotificationId, callback);
    }
}
