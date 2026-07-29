import { useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";

export function useAuth() {
  const context = useContext(AuthContext);
  
  const hasToken = typeof window !== "undefined" && Boolean(localStorage.getItem("token") || localStorage.getItem("edu_user_session"));

  if (!context || !context.isAuthenticated) {
    return {
      isAuthenticated: hasToken,
      roles: [],
      user: null,
      loading: false,
      error: null,
      login: async () => {},
      logout: async () => {},
      refreshUser: async () => {},
      hasRole: () => false,
      hasAnyRole: () => false,
    };
  }
  
  return context;
}