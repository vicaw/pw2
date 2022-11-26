import { createContext, useEffect, useState } from "react";
import { setCookie, parseCookies, destroyCookie } from "nookies";
import Router from "next/router";

import { recoverUserInformation, signInRequest } from "../services/auth";
import { api } from "../services/api";
import { UserType } from "../types/noticia";
import { ServerStreamFileResponseOptionsWithError } from "http2";

type SignInData = {
  email: string;
  password: string;
};

type AuthContextType = {
  isAuthenticated: boolean;
  user: UserType | null;
  signIn: (data: SignInData) => Promise<void>;
  signOut: () => void;
};

type LoginResponse = {
  token: string;
  user: UserType;
};

function parseJwt(token: string) {
  if (!token) {
    return;
  }
  const base64Url = token.split(".")[1];
  const base64 = base64Url.replace("-", "+").replace("_", "/");
  return JSON.parse(window.atob(base64));
}

export const AuthContext = createContext({} as AuthContextType);

export function AuthProvider({ children }: any) {
  const [user, setUser] = useState<UserType | null>(null);

  const isAuthenticated = !!user;

  useEffect(() => {
    const { "nextauth.token": token } = parseCookies();

    if (token) {
      const parsedToken = parseJwt(token);
      console.log(parsedToken);
      recoverUserInformation(parsedToken.sub).then((response) => {
        setUser(response);
        console.log(user);
      });
    }
  }, []);

  async function signIn({ email, password }: SignInData) {
    const data: LoginResponse = await signInRequest({
      email,
      password,
    });

    const token = data.token;
    const user = data.user;

    setCookie(undefined, "nextauth.token", token, {
      maxAge: 60 * 60 * 1, // 1 hour
    });

    api.defaults.headers["Authorization"] = `Bearer ${token}`;

    setUser(user);

    //Router.push("/");
  }

  function signOut() {
    destroyCookie(undefined, "nextauth.token");
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
}
