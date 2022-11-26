"use client";

import { AuthProvider } from "../contexts/AuthContext";

export default function Providers({ children }: any) {
  return <AuthProvider>{children}</AuthProvider>;
}
