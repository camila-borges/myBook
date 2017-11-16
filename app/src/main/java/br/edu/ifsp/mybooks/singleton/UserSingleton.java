package br.edu.ifsp.mybooks.singleton;

import br.edu.ifsp.mybooks.model.Usuario;

/**
 * Created by Camila on 16/11/2017.
 */

public class UserSingleton {

    private static UserSingleton userSingleton;
    public static Usuario user;

    private UserSingleton(){

    }

    public static UserSingleton getUserSingleton(){
        if(userSingleton == null){
            userSingleton = new UserSingleton();
        }
        return userSingleton;
    }
}
