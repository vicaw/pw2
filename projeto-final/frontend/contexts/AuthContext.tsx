import { createContext, useEffect, useState } from "react";
import { setCookie, parseCookies, destroyCookie } from "nookies";

import { recoverUserInformation, signInRequest } from "../services/auth";
import { api } from "../services/api";
import { UserType } from "../types/";

type SignInData = {
  email: string;
  password: string;
};

type AuthContextType = {
  isAuthenticated: boolean;
  user: UserType | null;
  signIn: (data: SignInData) => Promise<void>;
  setSession: (token: string, user: UserType) => void;
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
      });
    }
  }, []);

  async function signIn({ email, password }: SignInData) {
    const data = await signInRequest({
      email,
      password,
    })
      .then((res) => res)
      .catch((err) => {
        throw err.response.data.message;
      });

    setSession(data.token, data.user);

    //Router.push("/");
  }

  function setSession(token: string, user: UserType) {
    setCookie(undefined, "nextauth.token", token, {
      maxAge: 60 * 60 * 1, // 1 hour
    });

    api.defaults.headers["Authorization"] = `Bearer ${token}`;

    setUser(user);
  }

  function signOut() {
    destroyCookie(undefined, "nextauth.token");
    setUser(null);
  }

  return (
    <AuthContext.Provider
      value={{ user, isAuthenticated, signIn, signOut, setSession }}
    >
      {children}
    </AuthContext.Provider>
  );
}
