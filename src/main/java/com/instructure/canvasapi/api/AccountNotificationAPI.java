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

        @GET("/accounts/self/users/self/account_notifications")
        void getAccountNotifications(CanvasCallback<AccountNotification[]> callback);

        @DELETE("/accounts/self/users/self/account_notifications/{account_notification_id}")
        void deleteAccountNotification(@Path("account_notification_id") long account_notification_id, CanvasCallback<AccountNotification> callback);

    }


    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static AccountNotificationInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(AccountNotificationInterface.class);
    }

    public static void getAccountNotifications(final CanvasCallback<AccountNotification[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).getAccountNotifications(callback);
    }

    public static void deleteAccountNotification(long accountNotificationId, CanvasCallback<AccountNotification> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).deleteAccountNotification(accountNotificationId, callback);
    }
}
