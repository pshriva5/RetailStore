/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thoughtw.retail.sample.exception;

import android.content.Context;

import com.thoughtw.retail.sample.R;


/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

  private ErrorMessageFactory() {
    //empty
  }


  public static String create(Context context, Exception exception) {
    String message = context.getString(R.string.app_name);

    if (exception instanceof NetworkConnectionException) {
      message = context.getString(R.string.app_name);
    }
    return message;
  }
}
