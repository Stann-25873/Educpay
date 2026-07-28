import { api } from "./api";

export const studentService = {
  list: async (params = {}) => {
    const response = await api.get("/students", { params });
    return response.data;
  },
  getById: async (id) => {
    const response = await api.get(`/students/${id}`);
    return response.data;
  },
  create: async (data) => {
    const response = await api.post("/students", data);
    return response.data;
  },
  update: async (id, data) => {
    const response = await api.put(`/students/${id}`, data);
    return response.data;
  },
  delete: async (id) => {
    const response = await api.delete(`/students/${id}`);
    return response.data;
  },
  getByLevel: async (level) => {
    const response = await api.get("/students", { params: { level } });
    return response.data;
  },
  linkParent: async (studentId, parentId) => {
    const response = await api.post(`/students/${studentId}/parents/${parentId}`);
    return response.data;
  },
  unlinkParent: async (studentId, parentId) => {
    const response = await api.delete(`/students/${studentId}/parents/${parentId}`);
    return response.data;
  }
};