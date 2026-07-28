import { useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    return {
      isAuthenticated: false,
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
