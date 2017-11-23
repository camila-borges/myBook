package br.edu.ifsp.mybooks;

/**
 * Created by Camila on 22/11/2017.
 */

/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
public class ClientCredentials {

    /** Value of the "API key" shown under "Simple API Access". */
    static final String API_KEY ="AIzaSyAVX46ypQhP5LS_Rhs77saD5xRe2H1jPgg"
                    + ClientCredentials.class;

    static void errorIfNotSpecified() {
        if (API_KEY.startsWith("Enter ")) {
            System.err.println(API_KEY);
            System.exit(1);
        }
    }
}