import { api } from "./api";

export const feeService = {
  list: async (params = {}) => {
    const response = await api.get("/fees", { params });
    return response.data;
  },
  getById: async (id) => {
    const response = await api.get(`/fees/${id}`);
    return response.data;
  },
  create: async (data) => {
    const response = await api.post("/fees", data);
    return response.data;
  },
  update: async (id, data) => {
    const response = await api.put(`/fees/${id}`, data);
    return response.data;
  },
  delete: async (id) => {
    const response = await api.delete(`/fees/${id}`);
    return response.data;
  }
};