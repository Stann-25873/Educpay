import { api } from "./api";

export const parentService = {
  list: async (params = {}) => {
    const response = await api.get("/parents", { params });
    return response.data;
  },
  getById: async (id) => {
    const response = await api.get(`/parents/${id}`);
    return response.data;
  },
  create: async (data) => {
    const response = await api.post("/parents", data);
    return response.data;
  },
  update: async (id, data) => {
    const response = await api.put(`/parents/${id}`, data);
    return response.data;
  },
  delete: async (id) => {
    const response = await api.delete(`/parents/${id}`);
    return response.data;
  }
};