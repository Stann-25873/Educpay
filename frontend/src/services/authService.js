import { api } from "./api";

export const authService = {
  login: async (credentials) => {
    const response = await api.post("/auth/login", credentials);
    return response.data;
  },

  refresh: async () => {
    const response = await api.post("/auth/refresh", {});
    return response.data;
  },

  logout: async () => {
    const response = await api.post("/auth/logout", {});
    return response.data;
  },

  getMe: async () => {
    const response = await api.get("/auth/me");
    return response.data;
  },
};