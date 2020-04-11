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

package online.pandasoft.miadmin.api.http.parameters;

import online.pandasoft.miadmin.core.http.IRequestParameter;
import online.pandasoft.miadmin.core.http.RequestParameter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class RequestParameters {
    private static final Map<String, Class<? extends IRequestParameter>> classes;

    // Admin
    public static final String ADMIN_DELETE_ALL_FILES_OF_A_USER = "admin/delete-all-files-of-a-user";
    public static final String ADMIN_INVITE = "admin/invite";
    public static final String ADMIN_SILENCE_USER = "admin/silence-user";
    public static final String ADMIN_SUSPEND_USER = "admin/suspend-user";
    public static final String ADMIN_UNSILENCE_USER = "admin/unsilence-user";
    public static final String ADMIN_UNSUSPEND_USER = "admin/unsuspend-user";

    // App
    public static final String APP_CREATE = "app/create";

    //Auth
    public static final String AUTH_SESSION_GENERATE = "auth/session/generate";
    public static final String AUTH_SESSION_USERKEY = "auth/session/userkey";

    private static final String I = "i";

    // Note
    public static final String NOTES_DELETE = "notes/delete";
    public static final String NOTE_SHOW = "notes/show";

    // Users
    public static final String USERS_NOTES = "users/notes";
    public static final String USERS_SHOW = "users/show";

    static {
        classes = new HashMap<>();
        classes.put("admin/delete-all-files-of-a-user", AdminDeleteAllFilesOfAUserParameter.class);
        classes.put("admin/invite", AdminInviteParameter.class);
        classes.put("admin/silence-user", AdminSilenceSuspendParameter.class);
        classes.put("admin/suspend-user", AdminSilenceSuspendParameter.class);
        classes.put("admin/unsilence-user", AdminSilenceSuspendParameter.class);
        classes.put("admin/unsuspend-user", AdminSilenceSuspendParameter.class);

        classes.put("app/create", AppCreateParameter.class);

        classes.put("auth/session/generate", AuthSessionGenerateParameter.class);
        classes.put("auth/session/userkey", AuthSessionUserkeyParameter.class);

        classes.put("i", ApiIParameter.class);

        classes.put("notes/delete", GeneralNotesParameter.class);
        classes.put("notes/show", NotesShowParameter.class);

        classes.put("users/notes", UsersNotesParameter.class);
        classes.put("users/show", UsersShowParameter.class);
    }

    public static RequestParameter newInstance(String endpoint) {
        try {
            return (RequestParameter) classes.get(endpoint).getDeclaredConstructor(String.class).newInstance(endpoint);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
