import { api } from "./api";

export const invoiceService = {
  list: async (params = {}) => {
    const response = await api.get("/invoices", { params });
    return response.data;
  },
  getById: async (id) => {
    const response = await api.get(`/invoices/${id}`);
    return response.data;
  },
  getByStudent: async (studentId) => {
    const response = await api.get(`/invoices/by-student/${studentId}`);
    return response.data;
  },
  getByStatus: async (status) => {
    const response = await api.get(`/invoices/by-status/${status}`);
    return response.data;
  },
  getByDateRange: async (startDate, endDate) => {
    const response = await api.get("/invoices/by-date-range", { params: { startDate, endDate } });
    return response.data;
  }
};