import { createContext, useState, useEffect, useCallback } from "react";
import { authService } from "../services/authService";

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const isAuthenticated = !!user;
  const roles = user?.roles || [];
  const tenantId = user?.tenantId || null;

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      authService
        .getMe()
        .then((data) => {
          setUser(data.user || data);
        })
        .catch(() => {
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
        })
        .finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  const login = useCallback(async (email, password) => {
    setError(null);
    try {
      const response = await authService.login({ email, password });
      const { accessToken, refreshToken, user } = response;
      localStorage.setItem("accessToken", accessToken);
      if (refreshToken) {
        localStorage.setItem("refreshToken", refreshToken);
      }
      setUser(user || response);
      return response;
    } catch (err) {
      const message =
        err.response?.data?.message ||
        err.response?.data?.error ||
        "Login failed. Please check your credentials.";
      setError(message);
      throw err;
    }
  }, []);

  const logout = useCallback(async () => {
    try {
      await authService.logout();
    } catch {
      // swallow
    } finally {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      setUser(null);
      setError(null);
    }
  }, []);

  const refreshUser = useCallback(async () => {
    try {
      const data = await authService.getMe();
      setUser(data.user || data);
    } catch {
      setUser(null);
    }
  }, []);

  const value = {
    user,
    isAuthenticated,
    roles,
    tenantId,
    loading,
    error,
    login,
    logout,
    refreshUser,
    hasRole: (role) => roles.includes(role),
    hasAnyRole: (roleList) => roleList.some((r) => roles.includes(r)),
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
