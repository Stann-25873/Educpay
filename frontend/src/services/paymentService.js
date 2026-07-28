import { api } from "./api";

export const paymentService = {
  list: async (params = {}) => {
    const response = await api.get("/payments", { params });
    return response.data;
  },
  getById: async (id) => {
    const response = await api.get(`/payments/${id}`);
    return response.data;
  },
  create: async (data) => {
    const response = await api.post("/payments", data);
    return response.data;
  },
  getByStudent: async (studentId) => {
    const response = await api.get(`/payments/by-student/${studentId}`);
    return response.data;
  },
  getByMethod: async (method) => {
    const response = await api.get(`/payments/by-method/${method}`);
    return response.data;
  },
  getByDateRange: async (startDate, endDate) => {
    const response = await api.get("/payments/by-date-range", { params: { startDate, endDate } });
    return response.data;
  }
};